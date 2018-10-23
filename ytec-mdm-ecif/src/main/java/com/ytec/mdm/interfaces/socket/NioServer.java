/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：NioServer.java
 * @版本信息：1.0.0
 * @日期：2014-5-30-11:38:37
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif
 * @类名称：NioServer
 * @类描述： nio 监听
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:45:40
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:45:40
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class NioServer implements IServer {
	protected static Logger log = LoggerFactory.getLogger(NioServer.class);
	/**
	 * @属性名称:selector
	 * @属性描述:选择器对象
	 * @since 1.0.0
	 */
	protected Selector selector;
	/**
	 * @属性名称:serverSocketChannel
	 * @属性描述:服务套接字通道
	 * @since 1.0.0
	 */
	protected ServerSocketChannel[] serverSocketChannel;

	/**
	 * @属性名称:ip
	 * @属性描述:IP地址
	 * @since 1.0.0
	 */
	protected String ip;

	/**
	 * @属性名称:port
	 * @属性描述:端口号
	 * @since 1.0.0
	 */
	protected int[] port;
	/**
	 * @属性名称:poolSize
	 * @属性描述:动作线程池大小
	 * @since 1.0.0
	 */
	protected int poolSize;
	protected boolean nonstop = true;
	protected static final int DEFAULT_SIZE = 2;
	protected int selectSize;
	/**
	 * @属性名称:SELECTTIMEOUT
	 * @属性描述:选择器超时时间
	 * @since 1.0.0
	 */
	protected static int SELECTTIMEOUT = ServerConfiger.getIntArg("selectTimeOut");
	protected Server_I[] ioProcessors;
	protected int nextIndex = 0;

	/***
	 * 构造函数
	 */
	public NioServer() {

	}

	/**
	 * * 服务器停止.
	 * 
	 * @函数名称:void stop()
	 * @函数描述:
	 * @参数与返回说明: void stop()
	 * @算法描述:
	 */
	public void stop() {
		log.info("!> [STOP] nio server Acceptor begin stop");
		this.nonstop = false;
		this.nextIndex = 0;
		if (this.selector != null) {
			// 选择器唤醒
			selector.wakeup();
		}
	}

	/**
	 * 初始化服务器参数，启动服务器
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
			// 设置非阻塞
			serverSocketChannel[i].configureBlocking(false);
			serverSocket = serverSocketChannel[i].socket();
			serverSocket.bind(new InetSocketAddress(this.ip, this.port[i]));
			// 注册接收连接事件
			serverSocketChannel[i].register(this.selector, SelectionKey.OP_ACCEPT);
			log.info("!> [START] Server Acceptor Start at {}:{}", this.ip, port[i]);
		}
		this.nonstop = true;
	}

	/**
	 * @函数名称:listen
	 * @函数描述:端口监听
	 * @参数与返回说明:
	 * @throws Exception
	 * @算法描述:
	 */
	protected void listen() throws Exception {
		Set<SelectionKey> selectionKeys = null;
		Iterator<SelectionKey> iterator = null;
		SelectionKey selectionKey = null;
		while (true) {
			try {
				while (this.nonstop) {
					if (selector.select(SELECTTIMEOUT) > 0) {
						// 获取事件
						selectionKeys = selector.selectedKeys();
						iterator = selectionKeys.iterator();
						while (iterator.hasNext()) {
							selectionKey = iterator.next();
							iterator.remove();
							// 处理事件
							handleKey(selectionKey);
						}
					}
				}
			} catch (ClosedSelectorException ignore) {
				log.error("nio server Acceptor 严重错误:ClosedSelectorException error", ignore);
				log.info("重新启用新的接收器");
				if (this.selector != null && this.selector.isOpen()) {
					this.selector.close();
				}
				this.selector = Selector.open();
				for (int i = 0; i < serverSocketChannel.length; i++) {
					serverSocketChannel[i].register(this.selector, SelectionKey.OP_ACCEPT);
				}
				log.info("重新启用新的接收器成功");
			} catch (Exception e) {
				log.error("nio server Acceptor loop error, should not happen", e);
			}
			if (!this.nonstop) {
				break;
			}
		}
	}

	/**
	 * @函数名称:handleKey
	 * @函数描述:处理事件
	 * @参数与返回说明:
	 * @param selectionKey
	 * @throws IOException
	 * @算法描述:
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
				/** 分发 ***/
				if (this.nextIndex >= this.ioProcessors.length) {
					this.nextIndex = 0;
				}
				if (this.ioProcessors[nextIndex] == null || this.ioProcessors[nextIndex].isStop()) {
					log.error("服务器严重内部错误:线程死掉.请联系系统管理员，查看操作系统。");
					/** 重新新建处理线程 ***/
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
				// 构建会话对象
				IoSession session = buildIoSession(client, server);
				this.ioProcessors[nextIndex].registerChannel(session);
				this.nextIndex++;
			}
		}
	}

	/**
	 * 处理线程，监听服务端口，调用后台服务处理交易
	 */
	public void run() {
		try {
			// 初始化
			this.init();
			// 监听
			this.listen();
		} catch (Exception e) {
			log.error("操作系统严重错误导致ECIF服务器严重内部错误:主线程死掉.请联系系统管理员，查看操作系统。");
			log.error("nio服务器错误：", e);
		} catch (Throwable ex) {
			log.error("操作系统严重错误(如内存溢出)导致ECIF服务器严重内部错误:主线程死掉.请联系系统管理员，查看操作系统。");
			log.error("nio服务器严重错误", ex);
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
				log.error("关闭资源错误：", e);
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
			throw new Exception("没有配置端口号");
		}
		this.selectSize = Integer.parseInt((String) arg.get("nioServerMaxSelects"));
		this.poolSize = Integer.parseInt((String) arg.get("nioServerMaxExecuter"));
		if (this.selectSize == 0) {
			this.selectSize = DEFAULT_SIZE;
		}
	}

	/**
	 * @函数名称:getServer_I
	 * @函数描述:获取信息接收服务实例
	 * @参数与返回说明:
	 * @return
	 * @算法描述:
	 */
	protected Server_I getServer_I() {
		return new Server_I(this.poolSize);
	}

	/**
	 * @函数名称:buildIoSession
	 * @函数描述:构建Session
	 * @参数与返回说明:
	 * @param client
	 * @param server
	 * @return
	 * @算法描述:
	 */
	protected IoSession buildIoSession(SocketChannel client, ServerSocketChannel server) throws IOException {
		return new IoSession(client);
	}
}
