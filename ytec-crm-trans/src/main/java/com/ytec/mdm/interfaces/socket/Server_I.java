/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����Server_I.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-24-����3:00:16
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�Server_I
 * @�������� nio ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:45:47   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:45:47
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class Server_I implements Runnable,NioProcessor{
	protected static Logger log = LoggerFactory.getLogger(Server_I.class);
	/**
	 * @��������:selector
	 * @��������:ѡ��������
	 * @since 1.0.0
	 */
	protected Selector selector;
	protected boolean nonstop=true;
	/**
	 * @��������:pool
	 * @��������:�����̳߳�
	 * @since 1.0.0
	 */
	protected ExecutorService pool;
	/**
	 * @��������:poolSize
	 * @��������:�̳߳ش�С
	 * @since 1.0.0
	 */
	protected int poolSize;
	protected static int SELECTTIMEOUT=ServerConfiger.getIntArg("selectTimeOut");
	protected String serverId;
	/**
	 * @��������:wakenUp
	 * @��������:���ѿ��Ʊ�ʶ
	 * @since 1.0.0
	 */
	protected final AtomicBoolean wakenUp = new AtomicBoolean(false);  
	/**
	 * @��������:waitRegQueue
	 * @��������:�ȴ�����
	 * @since 1.0.0
	 */
	protected ConcurrentLinkedQueue<SocketChannel> waitRegQueue; 
	/**
	 * @��������:socketOpenMap
	 * @��������:�˿���socket��ӦMAP
	 * @since 1.0.0
	 */
	protected ConcurrentHashMap<Integer,IoSession> socketOpenMap;
	/**
	 * @��������:svcTread
	 * @��������:�����߳�
	 * @since 1.0.0
	 */
	protected Thread svcTread;
	/**
	 * @��������:clazz
	 * @��������:ִ����
	 * @since 1.0.0
	 */
	protected Class clazz=null;

	/**
	 * ���캯��
	 * 
	 */
	public Server_I() {
		this.poolSize=Runtime.getRuntime().availableProcessors()*2;
		this.pool = Executors.newFixedThreadPool(this.poolSize);
		this.waitRegQueue=new ConcurrentLinkedQueue<SocketChannel>();
		this.socketOpenMap=new ConcurrentHashMap<Integer,IoSession>();
	}
	/**
	 * ���캯��
	 * 
	 * @param poolsize �̳߳ش�С
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
	 * �����̣߳���������˿ڣ����ú�̨��������
	 */
	public void run() {
		try{
			this.init();
			this.listen();
		}catch (Exception e) {
			log.error("nio����������",e);
		}catch(Throwable ex){
			log.error("nio���������ش���",ex);
		}finally {
			try{
				pool.shutdownNow();
				selector.close();
			}catch (Exception e){
				log.error("�ر���Դ����",e);
			}
		}
		log.info("!> [STOP] Server_I[{}] stop running.",serverId);
	}

	/**
	 * ֹͣ����
	 */
	public void stop() {
		log.info("!> [STOP] Server[{}] begin stop",serverId);
		this.nonstop = false;
		if(this.selector!=null){
			this.selector.wakeup();
		}
	}

	/**
	 * ��ʼ������������������������
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
	 * ��������˿ڣ����ú�̨��������
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
								//���÷�����
								client.configureBlocking(false);
								opSocketChannel(client);
								//ע����¼�
								client.register(this.selector, SelectionKey.OP_READ);
							}catch(SslRecordException e){
								log.warn("����ʧ��:[{}]",e.getLocalizedMessage());
								removeOpenSocket(client);
							}catch(Exception e){
								log.error("ע��ͻ����¼�����,",e);
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
							//�����¼�
							handleKey(selectionKey);
						}
					}//else{
					//����ʱ������
					if(!socketOpenMap.entrySet().isEmpty()){
						it=socketOpenMap.entrySet().iterator();
						while(it.hasNext()){
							bc=(IoSession)it.next().getValue();
							if(bc!=null){
								try{
									if(bc.isTimeOut()){
										log.warn("�ͻ��˳�ʱ,�Զ��ر�����");
										bc.getClient().close();
										it.remove();
									}
								}catch (Exception e){
									log.error("�ر��쳣�˿ڴ���:",e);
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
	 * ��ȡ����״̬���׽��ֽ��н��״���
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
				//���������̳߳�
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
					//����
					selector.wakeup();  
				}  

			}
		}catch(Exception e){
			log.error("ע��ͻ��˳���:",e);
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
			log.error("�ر�socket����",e);
		}
	}
	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.socket.NioProcessor#isStop()
	 */
	public boolean isStop() {
		// TODO �Զ����ɵķ������
		if(svcTread!=null&&svcTread.isAlive() && this.nonstop==true){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * @��������:buildExecutor
	 * @��������:����ִ����
	 * @�����뷵��˵��:
	 * 		@param session
	 * 		@return
	 * 		@throws Exception
	 * @�㷨����:
	 */
	protected NioExecutor buildExecutor(IoSession session) throws Exception{
		NioExecutor executor=(NioExecutor)clazz.newInstance();
		executor.init(session, this);
		return executor;
	}
	
	/**
	 * @��������:buildIoSession
	 * @��������:����Session
	 * @�����뷵��˵��:
	 * 		@param client
	 * 		@return
	 * @�㷨����:
	 */
	protected IoSession buildIoSession(SocketChannel client)throws Exception{
		return new IoSession(client);
	}
	protected void opSocketChannel(SocketChannel client)throws Exception{
		
	}

}