/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.ext
 * @文件名：FlowServer_I.java
 * @版本信息：1.0.0
 * @日期：2014-4-24-下午3:56:49
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：FlowServer_I
 * @类描述：带流量控制的服务端
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-24 下午3:56:49   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-24 下午3:56:49
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class FlowServer_I extends Server_I{
	private int maximumPoolSize;
	private int queueSize;

	/**
	 * 构造函数
	 * 
	 */
	public FlowServer_I() {
		this(ServerConfiger.getIntArg("nioServerMaxExecuter"),ServerConfiger.getIntArg("maximumPoolSize"),ServerConfiger.getIntArg("queueSize"));
	}
	/**
	 * 构造函数
	 * 
	 * @param poolsize 线程池大小
	 */
	public FlowServer_I(int poolsize) {
		this(poolsize,ServerConfiger.getIntArg("maximumPoolSize"),ServerConfiger.getIntArg("queueSize"));
	}
	
	/**
	 *@构造函数 
	 * @param poolsize 核心线程数
	 * @param maximumPoolSize 最大线程数
	 */
	public FlowServer_I(int poolsize,int maximumPoolSize) {
		this(poolsize,maximumPoolSize,ServerConfiger.getIntArg("queueSize"));
	}
	
	/**
	 *@构造函数 
	 * @param poolsize  核心线程数
	 * @param maximumPoolSize  最大线程数
	 * @param queueSize 队列大小
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
	 * 获取符合状态的套接字进行交易处理
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
