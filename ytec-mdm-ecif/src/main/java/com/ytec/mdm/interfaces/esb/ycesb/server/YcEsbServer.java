/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.esb.ycesb.server
 * @文件名：YcEsbServer.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:39:27
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.esb.ycesb.server;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spc.webos.endpoint.ESB2;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.interfaces.common.IServer;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：YcEsbServer
 * @类描述：YC ESB2 监听程序多线程版
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:39:28   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:39:28
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class YcEsbServer implements IServer{
	private static Logger log = LoggerFactory.getLogger(YcEsbServer.class);
	/**
	 * @属性名称:esb2
	 * @属性描述:Esb2
	 * @since 1.0.0
	 */
	private ESB2 esb2;
	/**
	 * @属性名称:pool
	 * @属性描述:工作线程池
	 * @since 1.0.0
	 */
	private ExecutorService pool;
	/**
	 * @属性名称:poolSize
	 * @属性描述:工作线程池大小
	 * @since 1.0.0
	 */
	private int poolSize;
	
	/**
	 * @属性名称:currentThread
	 * @属性描述:当前线程
	 * @since 1.0.0
	 */
	private Thread currentThread;
	
	public YcEsbServer() {
	}
	public void init(Map arg) throws Exception {
		esb2=ESB2.getInstance();
		Class clazz = Class.forName(ServerConfiger.adapter);
		this.poolSize=ServerConfiger.getIntArg("poolSize");
		if(this.poolSize>0){
			pool=Executors.newFixedThreadPool(this.poolSize);
			log.info("工作线程池大小{}",this.poolSize);
		}
		BizOnMessage bizOnMessage=new BizOnMessage();
		bizOnMessage.init(clazz,pool);
		esb2.setRequestOnMessage(bizOnMessage);
		if(!arg.containsKey("jvm")){
			arg.put("jvm", String.format("%02d", MdmConstants.SYSTEMJVMID));
		}
		esb2.init(arg);
	}

	public void stop() {
		// TODO Auto-generated method stub
		log.info("!> [STOP] ESB2 server begin stop");
		if(pool!=null){
			pool.shutdownNow();
		}
		esb2.destory();
		try{
			/***
			 * 需要停止服务器时，先打断sleep，让其退出，不然要等待很长时间。
			 */
			currentThread.interrupt();
		}catch(Exception e){
			log.warn("interrupt errer",e);
		}
		log.info("!> [STOP] ESB2 server stop");
	}

	public void start() {
		// TODO Auto-generated method stub
		try {
			log.info("!> [START] ESB2 server begin start");
			/****
			 * 因为YCESB 的ESB2客户端使用的是Thread 的Daemon模式，主程序不能退出，用sleep空操作，
			 * 需要停止服务器时，先打断sleep，让其退出，不然要等待很长时间。
			 * ****/
			currentThread=Thread.currentThread();
			while(ServerConfiger.serverRun){
				try{
					Thread.sleep(30000L);
				}catch (Exception e) {
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("ESB2服务器异常",e);
		}
	}

}
