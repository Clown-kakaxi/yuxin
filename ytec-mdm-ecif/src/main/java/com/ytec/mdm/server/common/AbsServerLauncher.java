/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.server.common
 * @文件名：AbsServerLauncher.java
 * @版本信息：1.0.0
 * @日期：2014-4-30-上午10:09:36
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.server.common;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.interfaces.common.IServer;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：AbsServerLauncher
 * @类描述：服务器启动抽象类
 * @功能描述:负责启动服务器，加载服务器配置信息。
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-4-30 上午10:09:36   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-4-30 上午10:09:36
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public abstract class AbsServerLauncher implements IServerLauncher {
	/**
	 * @属性名称:log4jpro
	 * @属性描述:log4j配置文件名称
	 * @since 1.0.0
	 */
	private final String log4jpro="log4j.properties";
	/**
	 * @属性名称:log
	 * @属性描述:日志对象
	 * @since 1.0.0
	 */
	protected Logger log;
	/**
	 * @属性名称:launcherAppServer
	 * @属性描述:服务器代理对象
	 * @since 1.0.0
	 */
	protected IServer launcherAppServer=null;
	/* (non-Javadoc)
	 * @see com.ytec.mdm.server.common.IServerLauncher#start(java.lang.String[])
	 */
	/**
	 * @函数名称:showEnv
	 * @函数描述:显示JAVA环境信息
	 * @参数与返回说明:
	 * @算法描述:
	 */
	private void showEnv(){
		Properties props=System.getProperties(); 
		/**
		 * 打印系统属性，供查询问题使用
		 */
		log.info(StringUtils.center(" JVM Properties ", 50, "="));
		log.info("Java的安装路径:{}",props.getProperty("java.home"));
		log.info("Java的运行环境版本:{}",props.getProperty("java.version"));
		log.info("Java的运行环境供应商:{}",props.getProperty("java.vendor"));
		log.info("Java的虚拟机实现版本:{}",props.getProperty("java.vm.version"));
		log.info("服务器操作系统的名称:{}",props.getProperty("os.name"));
		log.info("服务器操作系统的构架:{}",props.getProperty("os.arch"));
		log.info("服务器操作系统的版本:{}",props.getProperty("os.version"));
		log.info("用户的主目录:{}",props.getProperty("user.home"));
		log.info("应用的当前工作目录:{}",props.getProperty("user.dir"));
		log.info(StringUtils.repeat("=", 50));
	}
	
	/**
	 * @函数名称:regeditSignal
	 * @函数描述:注册信号量，让程序接到信号后，做相应的动作。
	 * @参数与返回说明:
	 * @算法描述:
	 */
	protected void regeditSignal(){
		try {
			/**
			 * 服务器停止。
			 * */
			SignalHandler stopHandler = new SignalHandler(){
				public void handle(Signal signal) {
					log.info("Received signal:{},stop ECIF Synchro Server...",signal.getName());
					stop();
				}
			};
			/***
			 * 服务器重新加载配置
			 */
			SignalHandler reConfigHandler = new SignalHandler(){
				public void handle(Signal signal) {
					log.info("Received signal:{},reConfig ECIF Synchro Server...",signal.getName());
					try{
						if(!initServer()){
							log.info("服务器初始化失败,需要重启");
							stop();
							return;
						}
						log.info("重新配置服务器成功");
					}catch(Exception e){
						log.error("重新配置服务器失败:", e);
						stop();
					}
				}
			};
			try{
				/***
				 * 注册停止信号
				 */
				Signal.handle(new Signal("TERM"), stopHandler);//相当于kill -15
			}catch(Exception e){
				log.warn("注册信号量TERM失败:{}",e.getLocalizedMessage());
			}
//			try{
//				Signal.handle(new Signal("INT"),  stopHandler);//相当于Ctrl+C
//			}catch(Exception e){
//				log.warn("注册信号量INT失败:{}",e.getLocalizedMessage());
//			}
			String osName=System.getProperty("os.name");
			String reConfigSv=BusinessCfg.getString("reconfigsv");
			if(!StringUtil.isEmpty(reConfigSv)){
				if(osName!=null && !osName.contains("Windows")){
					log.debug("当前操作系统为{},可注册信号量{},该信号量用于服务器重新读取配置信息。",osName,reConfigSv);
					Signal.handle(new Signal(reConfigSv), reConfigHandler);//
				}else{
					log.debug("当前操作系统为{},不可注册信号量{},该信号量用于服务器重新读取配置信息。",osName,reConfigSv);
				}
			}
		}catch(Exception e){
			log.error("注册信号量失败:",e);
		}
	}
	
	/**
	 * @函数名称:initLog
	 * @函数描述:服务器日志设置
	 * @参数与返回说明:
	 * 		@param serverApp 服务接口名称
	 * 		@param defauteName 默认服务接口名称
	 * @算法描述:
	 */
	protected void initLog(String serverApp,String defauteName){
		/**设置日志,一个服务应用单独的日志**/
		if(StringUtil.isEmpty(System.getenv("LOG_PATH"))){
			/**
			 * 设置日志路径，默认在当前目录的log文件夹下
			 */
			System.setProperty ("LOG_PATH", "./log");
		}
		if(serverApp!=null){
			/**
			 * 设置日志文件名称后缀,已服务名相同
			 */
			System.setProperty ("APP", serverApp);
			PropertyConfigurator.configure(Thread.currentThread().getContextClassLoader().getResource(log4jpro)); 
			log = LoggerFactory.getLogger(AbsServerLauncher.class);
			showEnv();
			log.info("启动服务器应用{}",serverApp);
		}else{
			/**
			 * 设置默认日志文件
			 */
			System.setProperty ("APP", defauteName);
			PropertyConfigurator.configure(Thread.currentThread().getContextClassLoader().getResource(log4jpro)); 
			log = LoggerFactory.getLogger(AbsServerLauncher.class);
			showEnv();
			log.info("启动默认服务器应用");
		}
	}
	
	/**
	 * @函数名称:createFileOk
	 * @函数描述:在工作目录下写服务就绪文件，供启动脚本用
	 * @参数与返回说明:
	 * @算法描述:
	 */
	protected void createFileOk(){
		/**
		 * 获取就绪文件路径
		 */
		File myFilePath = new File(System.getProperty("user.dir")+"/"+System.getProperty("APP"));
		if (!myFilePath.exists()) {
			try {
				/**
				 * 应为后台进程在后台运行,启动脚本无法得知服务是否启动成功，
				 * 当启动成功后，给个就绪文件，通知控制脚本服务启动好了
				 */
				myFilePath.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
		}
	}
	
	/**
	 * @函数名称:libJarLoader
	 * @函数描述:第三方lib jar加载-Decif.ext.dirs=XXX目录，也可以显示用java -cp xxx.jar:xx.jar....
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public static void libJarLoader(){
		/***
		 * 加载jar
		 * 如果java 启动时不用-cp 告诉jvm所用到的jar，
		 * 那么可以使用-Decif.ext.dirs=XXX目录，程序自动加载jar文件
		 * ***/
		String lib_dir=System.getProperty("ecif.ext.dirs");
		if(lib_dir!=null&&!lib_dir.isEmpty()){
			JarLoader jarLoader = new JarLoader((URLClassLoader) ClassLoader.getSystemClassLoader());
			try {
				/**
				 * 加载第三方jar文件
				 */
				jarLoader.loadjar(lib_dir);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	/**
	 * @函数名称:initServer
	 * @函数描述:初始化服务器
	 * @参数与返回说明:
	 * 		@return
	 * @算法描述:
	 */
	protected abstract boolean initServer();
	
	
	

}
