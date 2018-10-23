/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：Server_I.java
 * @版本信息：1.0.0
 * @日期：2014-4-24-下午3:00:16
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket;

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.interfaces.socket.ssl.SslRecordException;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：Server_I
 * @类描述： nio 服务
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:45:47   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:45:47
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class Server_I implements Runnable,NioProcessor{
	protected static Logger log = LoggerFactory.getLogger(Server_I.class);
	/**
	 * @属性名称:selector
	 * @属性描述:选择器对象
	 * @since 1.0.0
	 */
	protected Selector selector;
	protected boolean nonstop=true;
	/**
	 * @属性名称:pool
	 * @属性描述:工作线程池
	 * @since 1.0.0
	 */
	protected ExecutorService pool;
	/**
	 * @属性名称:poolSize
	 * @属性描述:线程池大小
	 * @since 1.0.0
	 */
	protected int poolSize;
	protected static int SELECTTIMEOUT=ServerConfiger.getIntArg("selectTimeOut");
	protected String serverId;
	/**
	 * @属性名称:wakenUp
	 * @属性描述:唤醒控制标识
	 * @since 1.0.0
	 */
	protected final AtomicBoolean wakenUp = new AtomicBoolean(false);  
	/**
	 * @属性名称:waitRegQueue
	 * @属性描述:等待队列
	 * @since 1.0.0
	 */
	protected ConcurrentLinkedQueue<SocketChannel> waitRegQueue; 
	/**
	 * @属性名称:socketOpenMap
	 * @属性描述:端口与socket对应MAP
	 * @since 1.0.0
	 */
	protected ConcurrentHashMap<Integer,IoSession> socketOpenMap;
	/**
	 * @属性名称:svcTread
	 * @属性描述:服务线程
	 * @since 1.0.0
	 */
	protected Thread svcTread;
	/**
	 * @属性名称:clazz
	 * @属性描述:执行类
	 * @since 1.0.0
	 */
	protected Class clazz=null;

	/**
	 * 构造函数
	 * 
	 */
	public Server_I() {
		this.poolSize=Runtime.getRuntime().availableProcessors()*2;
		this.pool = Executors.newFixedThreadPool(this.poolSize);
		this.waitRegQueue=new ConcurrentLinkedQueue<SocketChannel>();
		this.socketOpenMap=new ConcurrentHashMap<Integer,IoSession>();
	}
	/**
	 * 构造函数
	 * 
	 * @param poolsize 线程池大小
	 */
	public Server_I(int poolsize) {
		this.poolSize=poolsize;
		if(this.poolSize<0){
			this.poolSize=Runtime.getRuntime().availableProcessors()*2;
		}
		this.pool = Executors.newFixedThreadPool(this.poolSize);
		this.waitRegQueue=new ConcurrentLinkedQueue<SocketChannel>();
		this.socketOpenMap=new ConcurrentHashMap<Integer,IoSession>();
	}

	/**
	 * 处理线程，监听服务端口，调用后台服务处理交易
	 */
	public void run() {
		try{
			this.init();
			this.listen();
		}catch (Exception e) {
			log.error("nio服务器错误：",e);
		}catch(Throwable ex){
			log.error("nio服务器严重错误",ex);
		}finally {
			try{
				pool.shutdownNow();
				selector.close();
			}catch (Exception e){
				log.error("关闭资源错误：",e);
			}
		}
		log.info("!> [STOP] Server_I[{}] stop running.",serverId);
	}

	/**
	 * 停止服务
	 */
	public void stop() {
		log.info("!> [STOP] Server[{}] begin stop",serverId);
		this.nonstop = false;
		if(this.selector!=null){
			this.selector.wakeup();
		}
	}

	/**
	 * 初始化服务器参数，启动服务器
	 * 
	 * @throws IOException
	 */
	protected void init() throws Exception {
		this.selector = Selector.open();
		long id = (long) (Math.random() * 10000000)
				+ System.currentTimeMillis() + this.hashCode();
		serverId=this.getClass().getSimpleName() + "-" + id;
		this.nonstop=true;
		clazz = Class.forName(ServerConfiger.adapter);
		log.info("!> [START] Server[{}] Start",serverId);
	}


	/**
	 * 监听服务端口，调用后台服务处理交易
	 * 
	 * @throws Exception
	 */
	protected void listen() throws Exception {
		SocketChannel client=null;
		Set<SelectionKey> selectionKeys=null;
		Iterator<SelectionKey> iterator=null;
		SelectionKey selectionKey=null;
		IoSession bc=null;
		Iterator<Entry<Integer,IoSession>> it=null;
		while (true) {
			try{
				while (this.nonstop) {
					while((client=waitRegQueue.poll())!=null){
						if(client.isOpen()){
							try{
								//设置非阻塞
								client.configureBlocking(false);
								opSocketChannel(client);
								//注册读事件
								client.register(this.selector, SelectionKey.OP_READ);
							}catch(SslRecordException e){
								log.warn("握手失败:[{}]",e.getLocalizedMessage());
								removeOpenSocket(client);
							}catch(Exception e){
								log.error("注册客户端事件错误,",e);
								removeOpenSocket(client);
							}
						}
					}
					wakenUp.compareAndSet(true, false);
					if ( selector.select(SELECTTIMEOUT)> 0 ) {
						selectionKeys = selector.selectedKeys();
						iterator = selectionKeys.iterator();
						while (iterator.hasNext()) {
							selectionKey = iterator.next();
							iterator.remove();
							//处理事件
							handleKey(selectionKey);
						}
					}//else{
					//处理超时的连接
					if(!socketOpenMap.entrySet().isEmpty()){
						it=socketOpenMap.entrySet().iterator();
						while(it.hasNext()){
							bc=(IoSession)it.next().getValue();
							if(bc!=null){
								try{
									if(bc.isTimeOut()){
										log.warn("客户端超时,自动关闭连接");
										bc.getClient().close();
										it.remove();
									}
								}catch (Exception e){
									log.error("关闭异常端口错误:",e);
									try{
										it.remove();
									}catch (Exception e1){}
								}
							}else{
								it.remove();
							}

						}
					}
				}
			}catch (ClosedSelectorException ignore) {
				log.error("nio server["+serverId+"] ClosedSelectorException error", ignore);
				//throw new Exception(ignore.getMessage());
				if(this.selector!=null&&this.selector.isOpen()){
					this.selector.close();
				}
				this.selector = Selector.open();
			} catch (Exception e) {
				log.error("nio server["+serverId+"] loop error, should not happen", e);
			}
			if(!this.nonstop){
				break;
			}
		}
	}

	/**
	 * 获取符合状态的套接字进行交易处理
	 * 
	 * @param selectionKey
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected void handleKey(SelectionKey selectionKey) throws Exception {
		SocketChannel client = null;
		if (selectionKey.isValid()&&selectionKey.isReadable()) {
			client = (SocketChannel) selectionKey.channel();
			selectionKey.cancel();
			if(client!=null){
				IoSession stemp=null;
				if((stemp=socketOpenMap.get(client.socket().getPort()))!=null){
					stemp.reSetAccessTime();
				}else{
					stemp=buildIoSession(client);
					socketOpenMap.put(client.socket().getPort(),stemp);
				}
				//交给工作线程池
				pool.execute(buildExecutor(stemp));
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.NioProcessor#registerChannel(com.ytec.mdm.interfaces.socket.IoSession)
	 */
	public void registerChannel(IoSession session){
		SocketChannel client=null;
		try{
			if((client=session.getClient())!=null){
				int port=client.socket().getPort();
				if(socketOpenMap.get(port)!=null){
					session.reSetAccessTime();
				}else{
					socketOpenMap.put(port,session);
				}
				this.waitRegQueue.offer(client);
				if (this.wakenUp.compareAndSet(false, true)) { 
					//唤醒
					selector.wakeup();  
				}  

			}
		}catch(Exception e){
			log.error("注册客户端出错:",e);
			if(client!=null){
				try{
					removeOpenSocket(client);
				}catch(Exception ei){}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.NioProcessor#start()
	 */
	public void start(){
		try{
			svcTread=new Thread(this);
			svcTread.start();
		}catch(Exception e){
			log.error("server["+serverId+"] errer,",e);
			this.stop();
		}
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.NioProcessor#removeOpenSocket(java.nio.channels.SocketChannel)
	 */
	public void removeOpenSocket(SocketChannel client){
		try{
			int port=client.socket().getPort();
			client.close();
			if(socketOpenMap.get(port)!=null){
				socketOpenMap.remove(port);
			}
		}catch(Exception e){
			log.error("关闭socket错误",e);
		}
	}
	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.NioProcessor#isStop()
	 */
	public boolean isStop() {
		// TODO 自动生成的方法存根
		if(svcTread!=null&&svcTread.isAlive() && this.nonstop==true){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * @函数名称:buildExecutor
	 * @函数描述:构建执行器
	 * @参数与返回说明:
	 * 		@param session
	 * 		@return
	 * 		@throws Exception
	 * @算法描述:
	 */
	protected NioExecutor buildExecutor(IoSession session) throws Exception{
		NioExecutor executor=(NioExecutor)clazz.newInstance();
		executor.init(session, this);
		return executor;
	}
	
	/**
	 * @函数名称:buildIoSession
	 * @函数描述:构建Session
	 * @参数与返回说明:
	 * 		@param client
	 * 		@return
	 * @算法描述:
	 */
	protected IoSession buildIoSession(SocketChannel client)throws Exception{
		return new IoSession(client);
	}
	protected void opSocketChannel(SocketChannel client)throws Exception{
		
	}

}