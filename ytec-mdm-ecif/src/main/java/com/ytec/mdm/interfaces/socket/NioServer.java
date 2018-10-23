/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����NioServer.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-30-11:38:37
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.interfaces.common.IServer;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�NioServer
 * @�������� nio ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:45:40
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:45:40
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class NioServer implements IServer {
	protected static Logger log = LoggerFactory.getLogger(NioServer.class);
	/**
	 * @��������:selector
	 * @��������:ѡ��������
	 * @since 1.0.0
	 */
	protected Selector selector;
	/**
	 * @��������:serverSocketChannel
	 * @��������:�����׽���ͨ��
	 * @since 1.0.0
	 */
	protected ServerSocketChannel[] serverSocketChannel;

	/**
	 * @��������:ip
	 * @��������:IP��ַ
	 * @since 1.0.0
	 */
	protected String ip;

	/**
	 * @��������:port
	 * @��������:�˿ں�
	 * @since 1.0.0
	 */
	protected int[] port;
	/**
	 * @��������:poolSize
	 * @��������:�����̳߳ش�С
	 * @since 1.0.0
	 */
	protected int poolSize;
	protected boolean nonstop = true;
	protected static final int DEFAULT_SIZE = 2;
	protected int selectSize;
	/**
	 * @��������:SELECTTIMEOUT
	 * @��������:ѡ������ʱʱ��
	 * @since 1.0.0
	 */
	protected static int SELECTTIMEOUT = ServerConfiger.getIntArg("selectTimeOut");
	protected Server_I[] ioProcessors;
	protected int nextIndex = 0;

	/***
	 * ���캯��
	 */
	public NioServer() {

	}

	/**
	 * * ������ֹͣ.
	 * 
	 * @��������:void stop()
	 * @��������:
	 * @�����뷵��˵��: void stop()
	 * @�㷨����:
	 */
	public void stop() {
		log.info("!> [STOP] nio server Acceptor begin stop");
		this.nonstop = false;
		this.nextIndex = 0;
		if (this.selector != null) {
			// ѡ��������
			selector.wakeup();
		}
	}

	/**
	 * ��ʼ������������������������
	 * 
	 * @throws IOException
	 */
	protected void init() throws Exception {
		if (this.selector != null) {
			this.selector.close();
		}
		this.selector = Selector.open();
		if (serverSocketChannel != null) {
			for (int i = 0; i < serverSocketChannel.length; i++) {
				if (serverSocketChannel[i] != null) {
					serverSocketChannel[i].close();
					serverSocketChannel[i] = null;
				}
			}
		}
		serverSocketChannel = new ServerSocketChannel[port.length];
		ServerSocket serverSocket = null;
		for (int i = 0; i < port.length; i++) {
			serverSocketChannel[i] = ServerSocketChannel.open();
			// ���÷�����
			serverSocketChannel[i].configureBlocking(false);
			serverSocket = serverSocketChannel[i].socket();
			serverSocket.bind(new InetSocketAddress(this.ip, this.port[i]));
			// ע����������¼�
			serverSocketChannel[i].register(this.selector, SelectionKey.OP_ACCEPT);
			log.info("!> [START] Server Acceptor Start at {}:{}", this.ip, port[i]);
		}
		this.nonstop = true;
	}

	/**
	 * @��������:listen
	 * @��������:�˿ڼ���
	 * @�����뷵��˵��:
	 * @throws Exception
	 * @�㷨����:
	 */
	protected void listen() throws Exception {
		Set<SelectionKey> selectionKeys = null;
		Iterator<SelectionKey> iterator = null;
		SelectionKey selectionKey = null;
		while (true) {
			try {
				while (this.nonstop) {
					if (selector.select(SELECTTIMEOUT) > 0) {
						// ��ȡ�¼�
						selectionKeys = selector.selectedKeys();
						iterator = selectionKeys.iterator();
						while (iterator.hasNext()) {
							selectionKey = iterator.next();
							iterator.remove();
							// �����¼�
							handleKey(selectionKey);
						}
					}
				}
			} catch (ClosedSelectorException ignore) {
				log.error("nio server Acceptor ���ش���:ClosedSelectorException error", ignore);
				log.info("���������µĽ�����");
				if (this.selector != null && this.selector.isOpen()) {
					this.selector.close();
				}
				this.selector = Selector.open();
				for (int i = 0; i < serverSocketChannel.length; i++) {
					serverSocketChannel[i].register(this.selector, SelectionKey.OP_ACCEPT);
				}
				log.info("���������µĽ������ɹ�");
			} catch (Exception e) {
				log.error("nio server Acceptor loop error, should not happen", e);
			}
			if (!this.nonstop) {
				break;
			}
		}
	}

	/**
	 * @��������:handleKey
	 * @��������:�����¼�
	 * @�����뷵��˵��:
	 * @param selectionKey
	 * @throws IOException
	 * @�㷨����:
	 */
	protected void handleKey(SelectionKey selectionKey) throws IOException {
		ServerSocketChannel server = null;
		SocketChannel client = null;
		if (selectionKey.isValid() && selectionKey.isAcceptable()) {
			server = (ServerSocketChannel) selectionKey.channel();
			client = server.accept();
			if (client != null) {
				/**
				 * deleted by wangtb@yuchengtech.com for nginx
				 * log.info("!> [ Acceptor:{} ACCEPT] Client[{}]",server.socket().getLocalPort(),client.socket().getRemoteSocketAddress());
				 */
				/** �ַ� ***/
				if (this.nextIndex >= this.ioProcessors.length) {
					this.nextIndex = 0;
				}
				if (this.ioProcessors[nextIndex] == null || this.ioProcessors[nextIndex].isStop()) {
					log.error("�����������ڲ�����:�߳�����.����ϵϵͳ����Ա���鿴����ϵͳ��");
					/** �����½������߳� ***/
					ioProcessors[nextIndex] = getServer_I();
					ioProcessors[nextIndex].start();
					if (this.ioProcessors.length == 1) {
						try {
							Thread.sleep(10);
						} catch (Exception e) {

						}
					} else {
						nextIndex++;
						if (this.nextIndex >= this.ioProcessors.length) {
							this.nextIndex = 0;
						}
					}

				}
				// �����Ự����
				IoSession session = buildIoSession(client, server);
				this.ioProcessors[nextIndex].registerChannel(session);
				this.nextIndex++;
			}
		}
	}

	/**
	 * �����̣߳���������˿ڣ����ú�̨��������
	 */
	public void run() {
		try {
			// ��ʼ��
			this.init();
			// ����
			this.listen();
		} catch (Exception e) {
			log.error("����ϵͳ���ش�����ECIF�����������ڲ�����:���߳�����.����ϵϵͳ����Ա���鿴����ϵͳ��");
			log.error("nio����������", e);
		} catch (Throwable ex) {
			log.error("����ϵͳ���ش���(���ڴ����)����ECIF�����������ڲ�����:���߳�����.����ϵϵͳ����Ա���鿴����ϵͳ��");
			log.error("nio���������ش���", ex);
		} finally {
			try {
				if (selector != null && selector.isOpen()) {
					try {
						selector.close();
					} catch (Exception e) {
						log.error("nio server Acceptor stop selector", e);
					}
				}
				if (serverSocketChannel != null) {
					for (int i = 0; i < serverSocketChannel.length; i++) {
						if (serverSocketChannel[i] != null && serverSocketChannel[i].isOpen()) {
							try {
								serverSocketChannel[i].close();
							} catch (Exception e) {
								log.error("nio server Acceptor stop serverSocketChannel", e);
							}
						}
					}
				}

				try {
					Thread.sleep(50);
				} catch (Exception ei) {

				}
				for (Server_I Server_I : ioProcessors) {
					if (Server_I != null) {
						Server_I.stop();
					}
				}
			} catch (Exception e) {
				log.error("�ر���Դ����", e);
			}
		}
		log.info("!> [STOP] NioServer Acceptor stop running.");
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.IServer#start()
	 */
	public void start() {
		try {
			if (this.ioProcessors != null) {
				for (Server_I Server_I : ioProcessors) {
					if (Server_I != null) {
						Server_I.stop();
					}
				}
			}
			this.ioProcessors = new Server_I[this.selectSize];
			for (int i = 0; i < selectSize; i++) {
				ioProcessors[i] = getServer_I();
				ioProcessors[i].start();
			}
			run();
		} catch (Exception e) {
			log.error("server Acceptor errer", e);
			try {
				Thread.sleep(50);
			} catch (Exception ei) {

			}
			this.stop();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.IServer#init(java.util.Map)
	 */
	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		this.ip = (String) arg.get("nioServerIp");
		if (StringUtil.isEmpty(this.ip)) {
			this.ip = StringUtil.getLocalIp();
		}
		String ports = (String) arg.get("nioServerPort");
		if (!StringUtil.isEmpty(ports)) {
			String[] port_i = ports.split("\\,");
			this.port = new int[port_i.length];
			for (int i = 0; i < port_i.length; i++) {
				this.port[i] = Integer.valueOf(port_i[i]);
			}
		} else {
			throw new Exception("û�����ö˿ں�");
		}
		this.selectSize = Integer.parseInt((String) arg.get("nioServerMaxSelects"));
		this.poolSize = Integer.parseInt((String) arg.get("nioServerMaxExecuter"));
		if (this.selectSize == 0) {
			this.selectSize = DEFAULT_SIZE;
		}
	}

	/**
	 * @��������:getServer_I
	 * @��������:��ȡ��Ϣ���շ���ʵ��
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
	 */
	protected Server_I getServer_I() {
		return new Server_I(this.poolSize);
	}

	/**
	 * @��������:buildIoSession
	 * @��������:����Session
	 * @�����뷵��˵��:
	 * @param client
	 * @param server
	 * @return
	 * @�㷨����:
	 */
	protected IoSession buildIoSession(SocketChannel client, ServerSocketChannel server) throws IOException {
		return new IoSession(client);
	}
}
