/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.ext
 * @�ļ�����FlowServer_I.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-24-����3:56:49
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.ytec.mdm.interfaces.socket.IoSession;
import com.ytec.mdm.interfaces.socket.NioExecutor;
import com.ytec.mdm.interfaces.socket.Server_I;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�FlowServer_I
 * @�����������������Ƶķ����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-24 ����3:56:49   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-24 ����3:56:49
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class FlowServer_I extends Server_I{
	private int maximumPoolSize;
	private int queueSize;

	/**
	 * ���캯��
	 * 
	 */
	public FlowServer_I() {
		this(ServerConfiger.getIntArg("nioServerMaxExecuter"),ServerConfiger.getIntArg("maximumPoolSize"),ServerConfiger.getIntArg("queueSize"));
	}
	/**
	 * ���캯��
	 * 
	 * @param poolsize �̳߳ش�С
	 */
	public FlowServer_I(int poolsize) {
		this(poolsize,ServerConfiger.getIntArg("maximumPoolSize"),ServerConfiger.getIntArg("queueSize"));
	}
	
	/**
	 *@���캯�� 
	 * @param poolsize �����߳���
	 * @param maximumPoolSize ����߳���
	 */
	public FlowServer_I(int poolsize,int maximumPoolSize) {
		this(poolsize,maximumPoolSize,ServerConfiger.getIntArg("queueSize"));
	}
	
	/**
	 *@���캯�� 
	 * @param poolsize  �����߳���
	 * @param maximumPoolSize  ����߳���
	 * @param queueSize ���д�С
	 */
	public FlowServer_I(int poolsize,int maximumPoolSize,int queueSize) {
		this.poolSize=poolsize;
		if(this.poolSize<=0){
			this.poolSize=Runtime.getRuntime().availableProcessors();
		}
		this.maximumPoolSize=maximumPoolSize;
		if(this.maximumPoolSize<=0){
			this.maximumPoolSize=this.poolSize*2;
		}
		this.queueSize=queueSize;
		if(this.queueSize<=0){
			this.queueSize=this.poolSize*4;
		}
		this.pool = new ThreadPoolExecutor(this.poolSize,
				this.maximumPoolSize,
				300L,TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(this.queueSize));
		this.waitRegQueue=new ConcurrentLinkedQueue<SocketChannel>();
		this.socketOpenMap=new ConcurrentHashMap<Integer,IoSession>();
	}

	

	/**
	 * ��ȡ����״̬���׽��ֽ��н��״���
	 * 
	 * @param selectionKey
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected void handleKey(SelectionKey selectionKey) throws IOException, InstantiationException, IllegalAccessException {
		SocketChannel client = null;
		if (selectionKey.isValid()&&selectionKey.isReadable()) {
			client = (SocketChannel) selectionKey.channel();
			selectionKey.cancel();
			if(client!=null){
				IoSession stemp=null;
				if((stemp=socketOpenMap.get(client.socket().getPort()))!=null){
					stemp.reSetAccessTime();
				}else{
					stemp=new IoSession(client);
					socketOpenMap.put(client.socket().getPort(),stemp);
				}
				NioExecutor executor=(NioExecutor)clazz.newInstance();
				executor.init(stemp, this);
				try{
					pool.execute(executor);
				}catch(Exception e){
					executor.rejectedExecution();
				}
			}
		}
	}
}
