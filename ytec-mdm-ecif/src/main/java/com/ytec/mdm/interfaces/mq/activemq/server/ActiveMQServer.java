/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.mq.activemq.server
 * @文件名：ActiveMQServer.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:42:13
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ActiveMQServer
 * @类描述：ActiveMQ服务端
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:42:14   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:42:14
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
			log.error("找不到适配器", e);
			return;
		}
		for (int threadnum = 0; threadnum < corePoolSize; threadnum++) {
			try {
				ActiveMQExecutor executor = null;
				executor = (ActiveMQExecutor) clazz.newInstance();
				executor.init();
				threadPool.execute(executor);
			} catch (Exception e) {
				log.error("实例化适配器错误", e);
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
