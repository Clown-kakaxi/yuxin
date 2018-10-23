/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����NioClient.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:47:12
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�NioClient
 * @��������NIO�ͻ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:47:08
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:47:08
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public abstract class NioClient implements IClient {
	private static Logger log = LoggerFactory.getLogger(NioClient.class);
	/**
	 * @��������:sendbuffer
	 * @��������:���ͻ�����
	 * @since 1.0.0
	 */
	protected ByteBuffer sendbuffer = ByteBuffer.allocate(1 * 1024 * 1024);
	/**
	 * @��������:receivebuffer
	 * @��������:���ջ�����
	 * @since 1.0.0
	 */
	protected ByteBuffer receivebuffer = ByteBuffer.allocate(1 * 1024 * 1024);
	/**
	 * @��������:ip
	 * @��������:����IP
	 * @since 1.0.0
	 */
	protected String ip;
	/**
	 * @��������:port
	 * @��������:����˿�
	 * @since 1.0.0
	 */
	protected int port;
	/**
	 * @��������:SELECTTIMEOUT
	 * @��������:ѡ������ʱʱ��
	 * @since 1.0.0
	 */
	/**
	 * @��������:SELECTTIMEOUT
	 * @��������:����ʱʱ��
	 * @since 1.0.0
	 */
	protected Long SELECTTIMEOUT = 3000L;
	protected Long TIMEOUT = 60000L;
	protected int sleepTime = 50; // ���粻�ȶ�ʱ���������ȴ�ʱ��
	protected int readMaxNum = 100; // ���粻�ȶ�ʱ��һ��û�н����꣬��ȡ���ٴ�
	/**
	 * @��������:charset
	 * @��������:�ַ���
	 * @since 1.0.0
	 */
	protected String charset;
	/**
	 * @��������:responseMsg
	 * @��������:��Ӧ����
	 * @since 1.0.0
	 */
	protected String responseMsg;
	/**
	 * @��������:client
	 * @��������:�ͻ���socket
	 * @since 1.0.0
	 */
	protected SocketChannel client;

	/**
	 * @���캯��
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
			log.info("���ò���charsetΪ��,ʹ��Ĭ��GB18030");
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
			log.info("NIO �ͻ�������ʼ,���ӵ�������[{}:{}]", this.ip, this.port);
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			selector = Selector.open();
			InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(this.ip, this.port);
			socketChannel.connect(SERVER_ADDRESS);
			socketChannel.register(selector, SelectionKey.OP_CONNECT);

			while (true) {
				// ��ʱ�ж�
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
						// ע����¼�
						client.register(selector, SelectionKey.OP_WRITE);
					} else if (selectionKey.isReadable()) {
						receivebuffer.clear();
						len = client.read(receivebuffer);
						if (len > 0) {
							receivebuffer.flip();
							int rcount = 0;
							do {
								// ����
								if (decode()) {
									if (responseMsg != null) {
										log.info(">>>[RECE] Client[{}]\n\n{}\n", client.socket()
												.getRemoteSocketAddress(),
												SensitHelper.getInstance().doInforFilter(responseMsg, null));
									}
									clientResponse.setResponseMsg(responseMsg);
									return clientResponse;
								} else {
									if (rcount > readMaxNum) { throw new Exception("Nio���Ľ�����ʱ"); }
									if (HttpUtils.BUFFER_SIZE != len) {
										log.warn("����״̬���ã���Ҫ��ȡ��Ρ�����");
										try {
											Thread.sleep(sleepTime);
										} catch (Exception e) {
											log.error("NIO�ͻ��˴���", e);
										}
									}
									rcount++;
									log.warn("��ȡ����" + len + "B,��Ҫ��ȡ��Ρ�����");
									this.receivebuffer.clear(); // �ٶ�
									len = client.read(receivebuffer);
									receivebuffer.flip(); // flip for read
									if (len < 0) {
										log.info("!>exit");
										clientResponse.setSuccess(false);
										clientResponse.setTimeout(true);
										return clientResponse;
									} else if (len == 0) {
										log.warn("��ȡ0Byte����");
									}
								}

							} while (receivebuffer.hasRemaining()); // consume
						} else if (len == -1) {
							log.warn("����˶Ͽ�����,����ʧ��");
							clientResponse.setSuccess(false);
							return clientResponse;
						}
					} else if (selectionKey.isWritable()) {// ������¼�
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
			log.error("NIO�ͻ��˴���", e);
			clientResponse.setSuccess(false);

			if (e instanceof TimeoutException) {
				clientResponse.setTimeout(true);
				clientResponse.setResponseMsg(String.format("�����[%s:%s]��ʱ������Ӧ", this.ip, this.port));
			} else if (e instanceof ConnectionException) {
				clientResponse.setResponseMsg(String.format("���ӷ����[%s:%s]�쳣", this.ip, this.port));
			} else if (e.getLocalizedMessage().contains("Connection timed out")) {
				clientResponse.setTimeout(true);
				clientResponse.setResponseMsg(String.format("���ӷ����[%s:%s]��ʱ", this.ip, this.port));
			} else {
				clientResponse.setResponseMsg(String.format("�������[%s:%s]���ͱ����쳣,%s", this.ip, this.port,
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
				log.error("�رն˿��쳣", ie);
			}
			log.info("NIO �ͻ����������");
		}
	}

	/****
	 * ������Ϣ ��������״̬���õ����
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
					log.warn("����״̬���ã���Ҫ���Ͷ�Ρ�����");
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
	 * @��������:packing
	 * @��������:��װ����
	 * @�����뷵��˵��:
	 * @param sendmsg
	 * @return
	 * @throws Exception
	 * @�㷨����:
	 */
	protected abstract String packing(String sendmsg) throws Exception;

	/**
	 * @��������:decode
	 * @��������:���뱨��
	 * @�����뷵��˵��:
	 * @return
	 * @throws Exception
	 * @�㷨����:
	 */
	protected abstract boolean decode() throws Exception;

}
