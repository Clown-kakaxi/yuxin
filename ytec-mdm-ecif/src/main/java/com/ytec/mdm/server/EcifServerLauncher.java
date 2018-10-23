/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.server
 * @文件名：EcifServerLauncher.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:51:28
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.server;
import com.ytec.mdm.server.common.AbsServerLauncher;
import com.ytec.mdm.server.common.CommandLineHelper;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：EcifServerLauncher
 * @类描述：服务启动类,参数加“-s” 启动同步服务，默认启动交易服务
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:51:31
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:51:31
 * @修改备注：
 * @修改日期 修改人员 修改原因 -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class EcifServerLauncher {

	/**
	 * @函数名称:main
	 * @函数描述:入口函数
	 * @参数与返回说明:
	 * 		@param args 服务参数
	 * @算法描述:
	 */
	public static void main(String args[]) {
		AbsServerLauncher serverLauncher = null;
		/***
		 * 如果启动参数中有ecif.ext.dirs，加载第三方jar
		 * ***/
		AbsServerLauncher.libJarLoader();
		/**
		 * 使用apache.commons.cli解析参数
		 */
		CommandLineHelper commandLineHelper=new CommandLineHelper();
		if(!commandLineHelper.optionsParser(args)){
			return ;
		}
		/**
		 * 判断是否有-s或--sync参数
		 */
		if (commandLineHelper.getCl().hasOption("s")) {
			/**
			 * -s或--sync参数 启动同步服务
			 */
			serverLauncher = new EcifSynchroLauncher();
		} else {
			/**
			 * 默认启动交易服务
			 */
			serverLauncher = new EcifTradingLauncher();
		}
		/**
		 * 获取参数值，这里主要是服务参数
		 */
		String[] arg = commandLineHelper.getCl().getArgs();
		commandLineHelper=null;
		/**
		 * 服务接口启动，java没有C和C++的后台进程的概念，
		 * 部署服务后台守护进程用UNIX的nohup或其他方式
		 */
		serverLauncher.start(arg);
	}
}
