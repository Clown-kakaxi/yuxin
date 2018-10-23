/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.server
 * @文件名：EcifSynchroLauncher.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:52:00
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.server;
import com.ytec.mdm.interfaces.common.IServer;
import com.ytec.mdm.server.common.AbsServerLauncher;
import com.ytec.mdm.server.common.DataSynchroConfiger;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：EcifSynchroLauncher
 * @类描述：数据同步服务启动
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:52:01   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:52:01
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class EcifSynchroLauncher extends AbsServerLauncher{
	
	public void start(String args[]){
		/**设置日志**/
		initLog("SYNC","SYNC");
		if(!DataSynchroConfiger.initDataSynchroServer()){
			log.info("同步服务器初始化失败");
			createFileOk();
			return;
		}
		/**注册信号量**/
		regeditSignal();
		try{
			log.info("启动服务开始...");
			Class clazz = Class.forName(DataSynchroConfiger.listenerImpl);
			launcherAppServer=(IServer)clazz.newInstance();
			/**
			 * 服务接口初始化
			 */
			launcherAppServer.init(null);
			/**
			 * 应为后台进程在后台运行,启动脚本无法得知服务是否启动成功，
			 * 当启动成功后，给个就绪文件，通知控制脚本服务启动好了
			 */
			createFileOk();
			/**
			 * 服务接口启动
			 */
			launcherAppServer.start();
		}catch(Exception e){
			log.error("服务器启动失败",e);
			createFileOk();
			return ;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.server.common.IServerLauncher#stop()
	 */
	public void stop(){
		/**
		 * 设置启动停止控制变量
		 */
		DataSynchroConfiger.serverRun=false;
		/**
		 * 调用服务通知函数
		 */
		launcherAppServer.stop();
	}
	
	/**
	 * @函数名称:main
	 * @函数描述:入口函数
	 * @参数与返回说明:
	 * 		@param args
	 * @算法描述:
	 */
	public static void main(String args[]){
		EcifSynchroLauncher synchroLauncher =new EcifSynchroLauncher();
		/***
		 * 加载jar
		 * ***/
		AbsServerLauncher.libJarLoader();
		/**
		 * 同步服务启动
		 */
		synchroLauncher.start(args);
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.server.common.AbsServerLauncher#initServer()
	 */
	@Override
	protected boolean initServer() {
		/**
		 * 调用初始化函数，加载配置文件
		 */
		return DataSynchroConfiger.initDataSynchroServer();
	}
	
}
