/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����SSLNioClient.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:47:12
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.interfaces.common.ClientResponse;
import com.ytec.mdm.interfaces.common.IClient;
import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;
import com.ytec.mdm.interfaces.socket.ssl.SslContextFactory;
import com.ytec.mdm.interfaces.socket.ssl.SslHandler;
import com.ytec.mdm.interfaces.socket.ssl.SslRecordException;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�SSLNioClient
 * @��������SSL/TLS NIO�ͻ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:47:08
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:47:08
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class SSLNioClient implements IClient {
	private static Logger log = LoggerFactory.getLogger(SSLNioClient.class);
	protected ByteBuffer receivebuffer;
	protected String ip;
	protected int port;
	protected Long SELECTTIMEOUT = 3000L;
	protected Long TIMEOUT = 60000L;
	protected int sleepTime = 50; // ���粻�ȶ�ʱ���������ȴ�ʱ��
	protected int readMaxNum = 100; // ���粻�ȶ�ʱ��һ��û�н����꣬��ȡ���ٴ�
	protected String charset;
	protected String responseMsg;
	protected SocketChannel client;
	protected SslContextFactory sslContextFactory;
	protected SslHandler sslHandler;

	public SSLNioClient() {
	}

	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		this.ip = (String) arg.get("ip");
		this.port = Integer.parseInt((String) arg.get("port"));
		this.charset = (String) arg.get("charset");
		if (this.charset == null) {
			charset = "GB18030";
		}
		this.TIMEOUT = Long.valueOf((String) arg.get("timeout"));
		this.SELECTTIMEOUT = Long.valueOf((String) arg.get("selecttimeout"));

		/*** �������? �ɲ���com.ytec.mdm.base.util.PropertyPlaceholderConfigurerExt ****/
		sslContextFactory = new SslContextFactory(
				(String) arg.get("keyPassWord"),
				(String) arg.get("keyStorePath"),
				(String) arg.get("trustPassWord"),
				(String) arg.get("trustStorePath"));
		if (arg.get("trustManager") != null) {
			sslContextFactory.setClientTrustManager((String) arg
					.get("trustManager"));
		}
		sslContextFactory.newInstance();
		sslHandler = new SslHandler(sslContextFactory.getSslContext());
		sslHandler.init(true);
	}

	public ClientResponse sendMsg(String sendmsg) {
		Selector selector = null;
		SocketChannel socketChannel = null;
		ClientResponse clientResponse = new ClientResponse();
		try {
			Set<SelectionKey> selectionKeys;
			Iterator<SelectionKey> iterator;
			SelectionKey selectionKey;
			Long sendtime = 0L;
			log.info("SSL/TLS NIO �ͻ�������ʼ");
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			selector = Selector.open();
			InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(this.ip,this.port);
			socketChannel.connect(SERVER_ADDRESS);
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			while (true) {
				if (selector.select(SELECTTIMEOUT) <= 0) {
					if (sendtime > 0
							&& System.currentTimeMillis() - sendtime > TIMEOUT) {
						log.warn(">>>[TIMEOUT] Client[{}:{}] timeout({}ms)",this.ip, this.port, TIMEOUT);
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
						/** ���� ***/
						if (!sslHandler.isHandshakeComplete()) {
							sslHandler.doHandShake(client);
						}
						client.register(selector, SelectionKey.OP_WRITE);
					} else if (selectionKey.isReadable()) {
						int read = sslHandler.read(this.client);
						if (read > 0) {
							/************* ���� *************/
							receivebuffer = sslHandler.getAppBuffer();
							/***************************/
							receivebuffer.flip(); // flip for read
							do {
								if (decode()) {
									if (responseMsg != null) {
										log.info(">>>[RECE] Client[{}]", client.socket().getRemoteSocketAddress());
										log.info(SensitHelper.getInstance().doInforFilter(responseMsg,null));
									}
									clientResponse.setResponseMsg(responseMsg);
									return clientResponse;
								} else {
									log.warn("���ݽ�������");
									clientResponse.setSuccess(false);
									return clientResponse;
								}

							} while (receivebuffer.hasRemaining()); // consume
						} else if (read == -1) {
							log.warn("����˶Ͽ�����,����ʧ��");
							clientResponse.setSuccess(false);
							return clientResponse;
						}
					} else if (selectionKey.isWritable()) {

						client = (SocketChannel) selectionKey.channel();
						String msg = packing(sendmsg);
						sslHandler.write(msg.getBytes(charset), this.client);
						sendtime = System.currentTimeMillis();
						log.info(">>>[SEND] Client[{}]", client.socket()
								.getRemoteSocketAddress());
						log.info(SensitHelper.getInstance().doInforFilter(
								msg.toString(), null));
						client.register(selector, SelectionKey.OP_READ);
					}
				}
			}
		} catch (SslRecordException e) {
			log.warn("����ʧ��:[{}]", e.getLocalizedMessage());
			clientResponse.setSuccess(false);
			return clientResponse;
		} catch (Exception e) {
			log.error("SSL/TLS NIO�ͻ��˴���", e);
			clientResponse.setSuccess(false);
			return clientResponse;
		} finally {
			try {
				if (sslHandler != null) {
					sslHandler.destroy(client);
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
			log.info("SSL/TLS NIO �ͻ����������");
		}
	}

	protected abstract String packing(String sendmsg) throws Exception;

	protected abstract boolean decode() throws Exception;

}
