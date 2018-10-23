/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.ssl
 * @文件名：SslHandler.java
 * @版本信息：1.0.0
 * @日期：2014-4-2-下午5:08:10
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.ssl;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;

import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：SslHandler
 * @类描述：SSL/TLS握手
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-8 下午4:01:24
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-8 下午4:01:24
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class SslHandler {
	private static Logger log = LoggerFactory.getLogger(SslHandler.class);
	// private final static int httpServerBlock =
	// ServerConfiger.getIntArg("httpServerBlock"); // 报文缓冲区大小
	private final static int writeTimeout = ServerConfiger
			.getIntArg("writeTimeout"); // 响应超时时间
	private final static int handshakeTimeoutInMillis = ServerConfiger
			.getIntArg("handshakeTimeoutInMillis"); // SSL/TLS握手超时时间(毫秒);

	private static final BufferPool bufferPool = new BufferPool();
	private SSLEngine sslEngine;

	private ByteBuffer inNetBuffer;

	private ByteBuffer outNetBuffer;

	private ByteBuffer appBuffer;

	private final ByteBuffer hsBB = ByteBuffer.allocate(0);

	private boolean handshakeComplete;

	private final int sleepTime = 50; // 网络不稳定时，解析器等待时间
	private final int readMaxNum = 100; // 网络不稳定时，一次没有解析完，读取多少次

	public SslHandler(SSLContext sslc) {
		sslEngine = sslc.createSSLEngine();
	}

	
	/**
	 * @函数名称:init
	 * @函数描述:初始化
	 * @参数与返回说明:
	 * 		@param useClientMode   是否客户端模式
	 * 		@throws SSLException
	 * @算法描述:
	 */
	public void init(boolean useClientMode) throws SSLException {
		handshakeComplete = false;
		sslEngine.setUseClientMode(useClientMode);
		if(!useClientMode){//如果为服务器
			sslEngine.setWantClientAuth(true);
			// sslEngine.setNeedClientAuth(true);
		}
		/** 自定义大小 ***/
		// SSLSession session = sslEngine.getSession();
		// int netBufferMax = session.getPacketBufferSize();
		// int appBufferMax = session.getApplicationBufferSize();
		// appBuffer = ByteBuffer.allocate(httpServerBlock);
		// inNetBuffer = ByteBuffer.allocate(netBufferMax);
		// outNetBuffer = ByteBuffer.allocate(httpServerBlock)
		/** 使用内存管理 **/
		appBuffer = bufferPool.acquireBuffer();
		inNetBuffer = bufferPool.acquireBuffer();
		outNetBuffer = bufferPool.acquireBuffer();
	}
	/**
	 * @函数名称:doHandShake
	 * @函数描述:协议握手
	 * @参数与返回说明:
	 * @throws IOException
	 * @算法描述:
	 */
	public void doHandShake(SocketChannel client) throws IOException {

		if (!handshakeComplete) {
			String ip = client.socket().getInetAddress().getHostAddress();
			int port = client.socket().getPort();
			inNetBuffer.clear();
			log.info("IP({}):({})SSL/TLS握手  START", ip, port);
			long beginTime = System.currentTimeMillis();
			sslEngine.beginHandshake();
			HandshakeStatus hsStatus = sslEngine.getHandshakeStatus();
			while (!handshakeComplete) {
				switch (hsStatus) {
				case FINISHED:
					handshakeComplete = true;
					break;
				case NEED_TASK:
					hsStatus = doTask();
					break;
				case NEED_UNWRAP:
					/**** 读数据 ****/
					if (inNetBuffer.position() == inNetBuffer.limit()) {
						inNetBuffer.clear();
					}
					int count = client.read(inNetBuffer);
					if (count < 0) {
						/****************/
						log.warn("客户端关闭");
						throw new SslRecordException("doHandShake:读取数据:连接被断开");
					}
					/***** 解密 ******/
					hsStatus = doUnwrap();
					break;
				case NEED_WRAP:
					/********* 加密 ************/
					hsStatus = doWrap();
					/****** 写 ************/
					outNetBuffer.flip();
					client.write(outNetBuffer);
					/*********************/
					break;
				case NOT_HANDSHAKING:
					handshakeComplete = true;
					break;
				}
				if(handshakeTimeoutInMillis!=0&&(System.currentTimeMillis()-beginTime>handshakeTimeoutInMillis)){
					/****************/
					log.warn("SSL/TLS握手超时({})",handshakeTimeoutInMillis);
					throw new SslRecordException("SSL/TLS握手超时");
				}
			}
			log.info("IP({}):({})SSL/TLS握手  END", ip, port);
		}
	}

	protected HandshakeStatus doUnwrap() throws IOException {
		HandshakeStatus hsStatus = null;
		SSLEngineResult result = null;
		Status status = null;
		appBuffer.clear();
		do {
			inNetBuffer.flip();
			result = sslEngine.unwrap(inNetBuffer, appBuffer);
			inNetBuffer.compact();
			hsStatus = result.getHandshakeStatus();
			status = result.getStatus();
			if (status == Status.BUFFER_OVERFLOW) {
				log.warn("doUnwrap status:{}", status);
				appBuffer = resizeRequestBuffer(appBuffer);
				continue;
			} else if (status == Status.CLOSED) {
				log.warn("doUnwrap status:{}", status);
				throw new SslRecordException("handshakeUnwrap:连接被断开");
			} else if (status == Status.BUFFER_UNDERFLOW) {
				if (inNetBuffer.position() == inNetBuffer.limit()) {
					log.warn("读缓冲区太小,需要看扩大");
					inNetBuffer = resizeRequestBuffer(inNetBuffer);
				}
			}
			if (result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_TASK) {
				hsStatus = doTask();
			}

		} while (status == SSLEngineResult.Status.OK
				&& hsStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP);
		return hsStatus;
	}

	private HandshakeStatus doWrap() throws IOException {
		SSLEngineResult engineResult = null;
		Status status = null;
		HandshakeStatus hsStatus = null;
		outNetBuffer.clear();
		while (true) {
			engineResult = sslEngine.wrap(hsBB, outNetBuffer);
			hsStatus = engineResult.getHandshakeStatus();
			status = engineResult.getStatus();
			if (status == Status.BUFFER_OVERFLOW) {
				log.warn("doWrap status:{}", status);
				outNetBuffer = resizeRequestBuffer(outNetBuffer);
			} else if (status == Status.CLOSED) {
				log.warn("doWrap status:{}", status);
				throw new SslRecordException("handshakeWrap:连接被断开");
			} else {
				return hsStatus;
			}
		}
	}

	private HandshakeStatus doTask() {
		Runnable runnable;
		while ((runnable = sslEngine.getDelegatedTask()) != null) {
			runnable.run();
		}
		return sslEngine.getHandshakeStatus();
	}

	/**
	 * @函数名称:destroy
	 * @函数描述:销毁
	 * @参数与返回说明:
	 * @param client
	 * @算法描述:
	 */
	public void destroy(SocketChannel client) {
		if (sslEngine == null) {
			return;
		}
		try {
			sslEngine.closeOutbound();
			outNetBuffer.clear();
			SSLEngineResult handshake = sslEngine.wrap(hsBB, outNetBuffer);
			if (handshake.getStatus() != SSLEngineResult.Status.CLOSED) {
				throw new SslRecordException(
						"Invalid close state, will not send network data.");
			}
			outNetBuffer.flip();
			client.write(outNetBuffer);
			if ((outNetBuffer.hasRemaining() || (handshake.getHandshakeStatus() == HandshakeStatus.NEED_WRAP))) {
				log.warn("channel closed false");
			}
		} catch (Exception e) {
			log.error("destroy error", e);
		}
		try {
			client.close();
		} catch (Exception e) {

		}
		sslEngine = null;
		/*** 缓冲释放 **/
		bufferPool.releaseBuffer(appBuffer);
		bufferPool.releaseBuffer(inNetBuffer);
		bufferPool.releaseBuffer(outNetBuffer);
		appBuffer = null;
		inNetBuffer = null;
		outNetBuffer = null;

	}

	/**
	 * @函数名称:decrypt
	 * @函数描述:读取报文并解密
	 * @参数与返回说明:
	 * @return
	 * @throws IOException
	 * @算法描述:
	 */
	public int read(SocketChannel client) throws IOException {
		if (!handshakeComplete) {
			throw new SslRecordException(
					"Handshake incomplete, you must complete handshake before reading data.");
		}
		int netread;
		int read = 0;
		SSLEngineResult unwrap;
		int rcount = 0;
		appBuffer.clear();
		inNetBuffer.clear();
		while (true) {
			netread = client.read(inNetBuffer);
			if (netread < 0) {
				return -1;
			}
			inNetBuffer.flip();
			unwrap = sslEngine.unwrap(inNetBuffer, appBuffer);
			inNetBuffer.compact();
			if (unwrap.getStatus() == Status.OK
					|| unwrap.getStatus() == Status.BUFFER_UNDERFLOW) {
				read += unwrap.bytesProduced();
				if (unwrap.getHandshakeStatus() == HandshakeStatus.NEED_TASK) {
					doTask();
				}
				if (unwrap.getStatus() == Status.BUFFER_UNDERFLOW) {
					if (inNetBuffer.position() == inNetBuffer.limit()) {
						log.warn("读缓冲区太小,需要看扩大");
						inNetBuffer = resizeRequestBuffer(inNetBuffer);
					} else {
						log.warn("decrypt:{}", unwrap.getStatus());
						log.warn("网络状态不好，需要读取多次。。。");
						if (rcount > readMaxNum) {
							log.error("http报文解析超时");
							throw new SslRecordException("http报文解析超时");
						}
						try {
							Thread.sleep(sleepTime);
						} catch (Exception e) {
							log.warn(e.getMessage());
						}
						rcount++;
					}
				} else {
					break;
				}
			} else if (unwrap.getStatus() == Status.BUFFER_OVERFLOW && read > 0) {
				log.warn("decrypt:{}", unwrap.getStatus());
				appBuffer = resizeRequestBuffer(appBuffer);

			} else {
				log.info("请求端:{}", unwrap.getStatus());
				return -1;
			}
		}
		return (read);
	}

	public int write(byte[] src, SocketChannel client) throws IOException {
		if (!handshakeComplete) {
			throw new SslRecordException(
					"Handshake incomplete, you must complete handshake before write data.");
		}
		appBuffer.clear();
		appBuffer.put(src);
		encrypt(appBuffer);
		int l = sendChannel(client);
		return l;
	}

	/**
	 * @函数名称:encrypt
	 * @函数描述:加密报文
	 * @参数与返回说明:
	 * @param src
	 * @return
	 * @throws IOException
	 * @算法描述:
	 */
	private ByteBuffer encrypt(ByteBuffer src) throws IOException {
		SSLEngineResult engineResult = null;
		Status status = null;
		outNetBuffer.clear();
		appBuffer.flip();
		while (true) {
			engineResult = sslEngine.wrap(src, outNetBuffer);
			status = engineResult.getStatus();
			if (status == Status.BUFFER_OVERFLOW) {
				log.warn("encrypt status:{}", status);
				outNetBuffer = resizeRequestBuffer(outNetBuffer);
			} else if (status == Status.CLOSED) {
				log.warn("encrypt status:{}", status);
				throw new SslRecordException("encrypt:连接被断开");
			} else {
				if (engineResult.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_TASK) {
					doTask();
				}
				outNetBuffer.flip();
				break;
			}
		}
		return outNetBuffer;
	}

	public ByteBuffer getAppBuffer() {
		return appBuffer;
	}

	/****
	 * 发送消息 用于网络状态不好的情况
	 * 
	 * @param bb
	 * @param writeTimeout
	 * @return
	 * @throws IOException
	 */
	private int sendChannel(SocketChannel client) throws IOException {

		SelectionKey key = null;
		Selector writeSelector = null;
		int attempts = 0;
		int bytesProduced = 0;
		try {
			while (outNetBuffer.hasRemaining()) {

				int len = client.write(outNetBuffer);
				attempts++;
				if (len < 0) {
					throw new EOFException();
				}
				bytesProduced += len;
				if (len == 0) {
					log.warn("网络状态不好，需要发送多次。。。");
					if (writeSelector == null) {
						writeSelector = Selector.open();
						if (writeSelector == null) {
							continue;
						}
					}
					key = client.register(writeSelector, key.OP_WRITE);
					if (writeSelector.select(this.writeTimeout) == 0) {
						if (attempts > 2)
							throw new SslRecordException("Client disconnected");
					} else {
						attempts--;
					}
				} else {
					attempts = 0;
				}
			}
		} finally {
			if (key != null) {
				key.cancel();
				key = null;
			}
			if (writeSelector != null) {
				writeSelector.close();
			}
		}
		return bytesProduced;
	}

	/**
	 * @函数名称:resizeRequestBuffer
	 * @函数描述:将缓冲区容量翻倍
	 * @参数与返回说明:
	 * @param requestBuffer
	 * @算法描述:
	 */
	private ByteBuffer resizeRequestBuffer(ByteBuffer requestBuffer) {
		ByteBuffer bb = ByteBuffer.allocate(requestBuffer.capacity() * 2);
		requestBuffer.flip(); // 复制前都要flip()一下
		bb.put(requestBuffer); // 把原来缓冲区中的数据拷贝到新的缓冲区
		return bb;
	}

	public boolean isHandshakeComplete() {
		return handshakeComplete;
	}
}
