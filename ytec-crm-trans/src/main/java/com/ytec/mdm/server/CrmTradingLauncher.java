/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.server
 * @文件名：EcifTradingLauncher.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:51:28
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.integration.transaction.bs.TxConfigBS4CRM;
import com.ytec.mdm.interfaces.common.IServer;
import com.ytec.mdm.server.common.AbsServerLauncher;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：EcifTradingLauncher
 * @类描述：交易服务启动类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:51:31
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:51:31
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class CrmTradingLauncher extends AbsServerLauncher {
	private String serverAppDaulft = "SOCKET_Server";
	/**
	 * @属性名称:serverApp
	 * @属性描述:服务器名称
	 * @since 1.0.0
	 */
	private String serverApp;
	/**
	 * @属性名称:cfgPath_
	 * @属性描述:配置文件路径
	 * @since 1.0.0
	 */
	private String cfgPath_;

	public void start(String args[]) {
		String resourceUrl = null;
		resourceUrl = System.getProperty("user.dir");
		if (args.length == 2) {
			/**
			 * 获取平配置文件路径和启动接口名称
			 */
			cfgPath_ = args[0];
			serverApp = args[1];
		}
		if (resourceUrl == null || "".equals(resourceUrl)) {
			log.error("无法获取默认配置文件路径，服务启动失败");
			serverApp = serverAppDaulft;
			log.info("启动默认服务器 " + serverApp);
		} else {
			if (args.length == 1) {
				cfgPath_ = resourceUrl;
				serverApp = args[0];
			} else {
				cfgPath_ = resourceUrl;
				serverApp = null;
			}
		}
		/** 设置日志,一个服务应用单独的日志 **/
		initLog(serverApp, "DEFAULT");
		if (!initServer()) {
			log.info("服务器初始化失败");
			createFileOk();
			return;
		}
		/** 注册信号量 **/
		regeditSignal();
		try {
			log.info("启动服务开始...");
			Class clazz = Class.forName(ServerConfiger.serverImpl);
			launcherAppServer = (IServer) clazz.newInstance();
			/**
			 * 交易服务初始化
			 */
			launcherAppServer.init(ServerConfiger.serverArg);

			/**
			 * 应为后台进程在后台运行,启动脚本无法得知服务是否启动成功，
			 * 当启动成功后，给个就绪文件，通知控制脚本服务启动好了
			 */
			createFileOk();

			/**
			 * 交易接口启动
			 */
			launcherAppServer.start();
			log.info("启动服务成功");
		} catch (Exception e) {
			log.error("服务器启动失败", e);
			createFileOk();
			return;
		} catch (Throwable ex) {
			log.error("服务器严重错误", ex);
			return;
		}
	}

	public void stop() {
		ServerConfiger.serverRun = false;
		launcherAppServer.stop();
	}

	/**
	 * @函数名称:main
	 * @函数描述:交易服务入口函数
	 * @参数与返回说明:
	 * @param args
	 * @算法描述:
	 */
	public static void main(String args[]) {
		CrmTradingLauncher ecifServer = new CrmTradingLauncher();
		/**
		 * *加载jar
		 ***/
		AbsServerLauncher.libJarLoader();
		/**
		 * 交易接口启动
		 */
		ecifServer.start(args);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ytec.mdm.server.common.AbsServerLauncher#initServer()
	 */
	@Override
	protected boolean initServer() {
		// TODO Auto-generated method stub
		/**
		 * 交易服务初始化
		 */
		return ServerConfiger.initServer(cfgPath_, serverApp) && TxConfigBS4CRM.initConfigBS(cfgPath_);
	}
}
