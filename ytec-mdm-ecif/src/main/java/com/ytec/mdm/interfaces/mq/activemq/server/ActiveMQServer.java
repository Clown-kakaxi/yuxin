/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.mq.activemq.server
 * @�ļ�����ActiveMQServer.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:42:13
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.mq.activemq.server;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.interfaces.common.IServer;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ActiveMQServer
 * @��������ActiveMQ�����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:42:14   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:42:14
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ActiveMQServer implements IServer {
	private static Logger log = LoggerFactory.getLogger(ActiveMQServer.class);
	private ExecutorService threadPool;
	private int corePoolSize;

	public void stop() {
		// TODO Auto-generated method stub
		threadPool.shutdown();
		if (!threadPool.isShutdown()) {
			threadPool.shutdownNow();
		}
	}

	public void start() {
		// TODO Auto-generated method stub
		Class clazz = null;
		try {
			clazz = Class.forName(ServerConfiger.adapter);
		} catch (Exception e) {
			log.error("�Ҳ���������", e);
			return;
		}
		for (int threadnum = 0; threadnum < corePoolSize; threadnum++) {
			try {
				ActiveMQExecutor executor = null;
				executor = (ActiveMQExecutor) clazz.newInstance();
				executor.init();
				threadPool.execute(executor);
			} catch (Exception e) {
				log.error("ʵ��������������", e);
				return;
			}
		}

	}

	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		corePoolSize = new Integer((String)arg.get("corePoolSize"));
		if(this.corePoolSize<0){
			this.corePoolSize=Runtime.getRuntime().availableProcessors();
		}
		threadPool = Executors.newFixedThreadPool(this.corePoolSize);
	}

}
