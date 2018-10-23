/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：NioClient.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:47:12
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.iap.ConnectionException;
import com.ytec.mdm.interfaces.common.ClientResponse;
import com.ytec.mdm.interfaces.common.IClient;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;
import com.ytec.mdm.interfaces.socket.http.tools.HttpUtils;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：NioClient
 * @类描述：NIO客户端
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:47:08
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:47:08
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public abstract class NioClient implements IClient {
	private static Logger log = LoggerFactory.getLogger(NioClient.class);
	/**
	 * @属性名称:sendbuffer
	 * @属性描述:发送缓冲区
	 * @since 1.0.0
	 */
	protected ByteBuffer sendbuffer = ByteBuffer.allocate(1 * 1024 * 1024);
	/**
	 * @属性名称:receivebuffer
	 * @属性描述:接收缓冲区
	 * @since 1.0.0
	 */
	protected ByteBuffer receivebuffer = ByteBuffer.allocate(1 * 1024 * 1024);
	/**
	 * @属性名称:ip
	 * @属性描述:请求IP
	 * @since 1.0.0
	 */
	protected String ip;
	/**
	 * @属性名称:port
	 * @属性描述:请求端口
	 * @since 1.0.0
	 */
	protected int port;
	/**
	 * @属性名称:SELECTTIMEOUT
	 * @属性描述:选择器超时时间
	 * @since 1.0.0
	 */
	/**
	 * @属性名称:SELECTTIMEOUT
	 * @属性描述:请求超时时间
	 * @since 1.0.0
	 */
	protected Long SELECTTIMEOUT = 3000L;
	protected Long TIMEOUT = 60000L;
	protected int sleepTime = 50; // 网络不稳定时，解析器等待时间
	protected int readMaxNum = 100; // 网络不稳定时，一次没有解析完，读取多少次
	/**
	 * @属性名称:charset
	 * @属性描述:字符集
	 * @since 1.0.0
	 */
	protected String charset;
	/**
	 * @属性名称:responseMsg
	 * @属性描述:响应报文
	 * @since 1.0.0
	 */
	protected String responseMsg;
	/**
	 * @属性名称:client
	 * @属性描述:客户端socket
	 * @since 1.0.0
	 */
	protected SocketChannel client;

	/**
	 * @构造函数
	 */
	public NioClient() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.IClient#init(java.util.Map)
	 */
	public void init(Map arg) throws Exception {
		this.ip = (String) arg.get("ip");
		this.port = Integer.parseInt((String) arg.get("port"));
		this.charset = (String) arg.get("charset");
		if (this.charset == null) {
			charset = "GB18030";
			log.info("配置参数charset为空,使用默认GB18030");
		}
		this.TIMEOUT = Long.valueOf((String) arg.get("timeout"));
		this.SELECTTIMEOUT = Long.valueOf((String) arg.get("selecttimeout"));
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.IClient#sendMsg(java.lang.String)
	 */
	public ClientResponse sendMsg(String sendmsg) {
		Selector selector = null;
		SocketChannel socketChannel = null;
		ClientResponse clientResponse = new ClientResponse();
		try {
			Set<SelectionKey> selectionKeys;
			Iterator<SelectionKey> iterator;
			SelectionKey selectionKey;
			long len = 0;
			Long sendtime = 0L;
			log.info("NIO 客户端请求开始,连接到服务器[{}:{}]", this.ip, this.port);
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			selector = Selector.open();
			InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(this.ip, this.port);
			socketChannel.connect(SERVER_ADDRESS);
			socketChannel.register(selector, SelectionKey.OP_CONNECT);

			while (true) {
				// 超时判定
				if (selector.select(SELECTTIMEOUT) <= 0) {
					if (sendtime > 0 && System.currentTimeMillis() - sendtime > TIMEOUT) {
						log.warn(">>>[TIMEOUT] Client[{}:{}] timeout({}ms)", this.ip, this.port, TIMEOUT);
						clientResponse.setSuccess(false);
						clientResponse.setTimeout(true);
						return clientResponse;
					}
					continue;
				}
				selectionKeys = selector.selectedKeys();
				iterator = selectionKeys.iterator();
				while (iterator.hasNext()) {
					selectionKey = iterator.next();
					iterator.remove();
					client = (SocketChannel) selectionKey.channel();
					if (selectionKey.isConnectable()) {
						if (client.isConnectionPending()) {
							client.finishConnect();
						}
						// 注册读事件
						client.register(selector, SelectionKey.OP_WRITE);
					} else if (selectionKey.isReadable()) {
						receivebuffer.clear();
						len = client.read(receivebuffer);
						if (len > 0) {
							receivebuffer.flip();
							int rcount = 0;
							do {
								// 解码
								if (decode()) {
									if (responseMsg != null) {
										log.info(">>>[RECE] Client[{}]\n\n{}\n", client.socket()
												.getRemoteSocketAddress(),
												SensitHelper.getInstance().doInforFilter(responseMsg, null));
									}
									clientResponse.setResponseMsg(responseMsg);
									return clientResponse;
								} else {
									if (rcount > readMaxNum) { throw new Exception("Nio报文解析超时"); }
									if (HttpUtils.BUFFER_SIZE != len) {
										log.warn("网络状态不好，需要读取多次。。。");
										try {
											Thread.sleep(sleepTime);
										} catch (Exception e) {
											log.error("NIO客户端错误", e);
										}
									}
									rcount++;
									log.warn("读取数据" + len + "B,需要读取多次。。。");
									this.receivebuffer.clear(); // 再读
									len = client.read(receivebuffer);
									receivebuffer.flip(); // flip for read
									if (len < 0) {
										log.info("!>exit");
										clientResponse.setSuccess(false);
										clientResponse.setTimeout(true);
										return clientResponse;
									} else if (len == 0) {
										log.warn("读取0Byte数据");
									}
								}

							} while (receivebuffer.hasRemaining()); // consume
						} else if (len == -1) {
							log.warn("服务端断开连接,请求失败");
							clientResponse.setSuccess(false);
							return clientResponse;
						}
					} else if (selectionKey.isWritable()) {// 处理读事件
						sendbuffer.clear();
						client = (SocketChannel) selectionKey.channel();
						String msg = packing(sendmsg);
						sendbuffer.put(msg.getBytes(charset));
						sendbuffer.flip();
						len = sendChannel(sendbuffer); // client.write(sendbuffer);
						sendtime = System.currentTimeMillis();
						log.info(">>>[SEND] Client[{}]\n\n{}\n", client.socket().getRemoteSocketAddress(), SensitHelper
								.getInstance().doInforFilter(msg.toString(), null));
						client.register(selector, SelectionKey.OP_READ);
					}
				}
			}
		} catch (Exception e) {
			log.error("NIO客户端错误", e);
			clientResponse.setSuccess(false);

			if (e instanceof TimeoutException) {
				clientResponse.setTimeout(true);
				clientResponse.setResponseMsg(String.format("服务端[%s:%s]长时间无响应", this.ip, this.port));
			} else if (e instanceof ConnectionException) {
				clientResponse.setResponseMsg(String.format("连接服务端[%s:%s]异常", this.ip, this.port));
			} else if (e.getLocalizedMessage().contains("Connection timed out")) {
				clientResponse.setTimeout(true);
				clientResponse.setResponseMsg(String.format("连接服务端[%s:%s]超时", this.ip, this.port));
			} else {
				clientResponse.setResponseMsg(String.format("向服务器[%s:%s]发送报文异常,%s", this.ip, this.port,
						e.getLocalizedMessage()));
			}
			return clientResponse;
		} finally {
			try {
				if (client != null) {
					client.close();
				}
				if (socketChannel != null) {
					socketChannel.close();
				}
				if (selector != null) {
					selector.close();
				}
			} catch (Exception ie) {
				log.error("关闭端口异常", ie);
			}
			log.info("NIO 客户端请求结束");
		}
	}

	/****
	 * 发送消息 用于网络状态不好的情况
	 * 
	 * @param bb
	 * @param writeTimeout
	 * @return
	 * @throws IOException
	 */
	private long sendChannel(ByteBuffer buf) throws IOException {
		SelectionKey key = null;
		Selector writeSelector = null;
		int attempts = 0;
		long bytesProduced = 0;
		long len;
		try {
			while (buf.hasRemaining()) {
				len = this.client.write(buf);
				attempts++;
				if (len < 0) { throw new EOFException(); }
				bytesProduced += len;
				if (len == 0) {
					log.warn("网络状态不好，需要发送多次。。。");
					if (writeSelector == null) {
						writeSelector = Selector.open();
						if (writeSelector == null) {
							continue;
						}
					}
					key = this.client.register(writeSelector, key.OP_WRITE);
					if (writeSelector.select(this.SELECTTIMEOUT) == 0) {
						if (attempts > 2)
							throw new IOException("Client disconnected");
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
	 * @函数名称:packing
	 * @函数描述:封装报文
	 * @参数与返回说明:
	 * @param sendmsg
	 * @return
	 * @throws Exception
	 * @算法描述:
	 */
	protected abstract String packing(String sendmsg) throws Exception;

	/**
	 * @函数名称:decode
	 * @函数描述:解码报文
	 * @参数与返回说明:
	 * @return
	 * @throws Exception
	 * @算法描述:
	 */
	protected abstract boolean decode() throws Exception;

}
