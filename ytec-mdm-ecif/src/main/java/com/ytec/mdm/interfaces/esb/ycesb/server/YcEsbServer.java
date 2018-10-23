/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.esb.ycesb.server
 * @�ļ�����YcEsbServer.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:39:27
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�YcEsbServer
 * @��������YC ESB2 ����������̰߳�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:39:28   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:39:28
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class YcEsbServer implements IServer{
	private static Logger log = LoggerFactory.getLogger(YcEsbServer.class);
	/**
	 * @��������:esb2
	 * @��������:Esb2
	 * @since 1.0.0
	 */
	private ESB2 esb2;
	/**
	 * @��������:pool
	 * @��������:�����̳߳�
	 * @since 1.0.0
	 */
	private ExecutorService pool;
	/**
	 * @��������:poolSize
	 * @��������:�����̳߳ش�С
	 * @since 1.0.0
	 */
	private int poolSize;
	
	/**
	 * @��������:currentThread
	 * @��������:��ǰ�߳�
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
			log.info("�����̳߳ش�С{}",this.poolSize);
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
			 * ��Ҫֹͣ������ʱ���ȴ��sleep�������˳�����ȻҪ�ȴ��ܳ�ʱ�䡣
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
			 * ��ΪYCESB ��ESB2�ͻ���ʹ�õ���Thread ��Daemonģʽ�����������˳�����sleep�ղ�����
			 * ��Ҫֹͣ������ʱ���ȴ��sleep�������˳�����ȻҪ�ȴ��ܳ�ʱ�䡣
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
			log.error("ESB2�������쳣",e);
		}
	}

}
