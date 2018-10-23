/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.ssl
 * @�ļ�����SslHandler.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-2-����5:08:10
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�SslHandler
 * @��������SSL/TLS����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-8 ����4:01:24
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-8 ����4:01:24
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class SslHandler {
	private static Logger log = LoggerFactory.getLogger(SslHandler.class);
	// private final static int httpServerBlock =
	// ServerConfiger.getIntArg("httpServerBlock"); // ���Ļ�������С
	private final static int writeTimeout = ServerConfiger
			.getIntArg("writeTimeout"); // ��Ӧ��ʱʱ��
	private final static int handshakeTimeoutInMillis = ServerConfiger
			.getIntArg("handshakeTimeoutInMillis"); // SSL/TLS���ֳ�ʱʱ��(����);

	private static final BufferPool bufferPool = new BufferPool();
	private SSLEngine sslEngine;

	private ByteBuffer inNetBuffer;

	private ByteBuffer outNetBuffer;

	private ByteBuffer appBuffer;

	private final ByteBuffer hsBB = ByteBuffer.allocate(0);

	private boolean handshakeComplete;

	private final int sleepTime = 50; // ���粻�ȶ�ʱ���������ȴ�ʱ��
	private final int readMaxNum = 100; // ���粻�ȶ�ʱ��һ��û�н����꣬��ȡ���ٴ�

	public SslHandler(SSLContext sslc) {
		sslEngine = sslc.createSSLEngine();
	}

	
	/**
	 * @��������:init
	 * @��������:��ʼ��
	 * @�����뷵��˵��:
	 * 		@param useClientMode   �Ƿ�ͻ���ģʽ
	 * 		@throws SSLException
	 * @�㷨����:
	 */
	public void init(boolean useClientMode) throws SSLException {
		handshakeComplete = false;
		sslEngine.setUseClientMode(useClientMode);
		if(!useClientMode){//���Ϊ������
			sslEngine.setWantClientAuth(true);
			// sslEngine.setNeedClientAuth(true);
		}
		/** �Զ����С ***/
		// SSLSession session = sslEngine.getSession();
		// int netBufferMax = session.getPacketBufferSize();
		// int appBufferMax = session.getApplicationBufferSize();
		// appBuffer = ByteBuffer.allocate(httpServerBlock);
		// inNetBuffer = ByteBuffer.allocate(netBufferMax);
		// outNetBuffer = ByteBuffer.allocate(httpServerBlock)
		/** ʹ���ڴ���� **/
		appBuffer = bufferPool.acquireBuffer();
		inNetBuffer = bufferPool.acquireBuffer();
		outNetBuffer = bufferPool.acquireBuffer();
	}
	/**
	 * @��������:doHandShake
	 * @��������:Э������
	 * @�����뷵��˵��:
	 * @throws IOException
	 * @�㷨����:
	 */
	public void doHandShake(SocketChannel client) throws IOException {

		if (!handshakeComplete) {
			String ip = client.socket().getInetAddress().getHostAddress();
			int port = client.socket().getPort();
			inNetBuffer.clear();
			log.info("IP({}):({})SSL/TLS����  START", ip, port);
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
					/**** ������ ****/
					if (inNetBuffer.position() == inNetBuffer.limit()) {
						inNetBuffer.clear();
					}
					int count = client.read(inNetBuffer);
					if (count < 0) {
						/****************/
						log.warn("�ͻ��˹ر�");
						throw new SslRecordException("doHandShake:��ȡ����:���ӱ��Ͽ�");
					}
					/***** ���� ******/
					hsStatus = doUnwrap();
					break;
				case NEED_WRAP:
					/********* ���� ************/
					hsStatus = doWrap();
					/****** д ************/
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
					log.warn("SSL/TLS���ֳ�ʱ({})",handshakeTimeoutInMillis);
					throw new SslRecordException("SSL/TLS���ֳ�ʱ");
				}
			}
			log.info("IP({}):({})SSL/TLS����  END", ip, port);
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
				throw new SslRecordException("handshakeUnwrap:���ӱ��Ͽ�");
			} else if (status == Status.BUFFER_UNDERFLOW) {
				if (inNetBuffer.position() == inNetBuffer.limit()) {
					log.warn("��������̫С,��Ҫ������");
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
				throw new SslRecordException("handshakeWrap:���ӱ��Ͽ�");
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
	 * @��������:destroy
	 * @��������:����
	 * @�����뷵��˵��:
	 * @param client
	 * @�㷨����:
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
		/*** �����ͷ� **/
		bufferPool.releaseBuffer(appBuffer);
		bufferPool.releaseBuffer(inNetBuffer);
		bufferPool.releaseBuffer(outNetBuffer);
		appBuffer = null;
		inNetBuffer = null;
		outNetBuffer = null;

	}

	/**
	 * @��������:decrypt
	 * @��������:��ȡ���Ĳ�����
	 * @�����뷵��˵��:
	 * @return
	 * @throws IOException
	 * @�㷨����:
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
						log.warn("��������̫С,��Ҫ������");
						inNetBuffer = resizeRequestBuffer(inNetBuffer);
					} else {
						log.warn("decrypt:{}", unwrap.getStatus());
						log.warn("����״̬���ã���Ҫ��ȡ��Ρ�����");
						if (rcount > readMaxNum) {
							log.error("http���Ľ�����ʱ");
							throw new SslRecordException("http���Ľ�����ʱ");
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
				log.info("�����:{}", unwrap.getStatus());
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
	 * @��������:encrypt
	 * @��������:���ܱ���
	 * @�����뷵��˵��:
	 * @param src
	 * @return
	 * @throws IOException
	 * @�㷨����:
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
				throw new SslRecordException("encrypt:���ӱ��Ͽ�");
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
	 * ������Ϣ ��������״̬���õ����
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
					log.warn("����״̬���ã���Ҫ���Ͷ�Ρ�����");
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
	 * @��������:resizeRequestBuffer
	 * @��������:����������������
	 * @�����뷵��˵��:
	 * @param requestBuffer
	 * @�㷨����:
	 */
	private ByteBuffer resizeRequestBuffer(ByteBuffer requestBuffer) {
		ByteBuffer bb = ByteBuffer.allocate(requestBuffer.capacity() * 2);
		requestBuffer.flip(); // ����ǰ��Ҫflip()һ��
		bb.put(requestBuffer); // ��ԭ���������е����ݿ������µĻ�����
		return bb;
	}

	public boolean isHandshakeComplete() {
		return handshakeComplete;
	}
}
