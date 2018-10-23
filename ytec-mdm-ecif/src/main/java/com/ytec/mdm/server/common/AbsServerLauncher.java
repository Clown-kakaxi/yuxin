/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server.common
 * @�ļ�����AbsServerLauncher.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-30-����10:09:36
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�AbsServerLauncher
 * @������������������������
 * @��������:�������������������ط�����������Ϣ��
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-30 ����10:09:36   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-30 ����10:09:36
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class AbsServerLauncher implements IServerLauncher {
	/**
	 * @��������:log4jpro
	 * @��������:log4j�����ļ�����
	 * @since 1.0.0
	 */
	private final String log4jpro="log4j.properties";
	/**
	 * @��������:log
	 * @��������:��־����
	 * @since 1.0.0
	 */
	protected Logger log;
	/**
	 * @��������:launcherAppServer
	 * @��������:�������������
	 * @since 1.0.0
	 */
	protected IServer launcherAppServer=null;
	/* (non-Javadoc)
	 * @see com.ytec.mdm.server.common.IServerLauncher#start(java.lang.String[])
	 */
	/**
	 * @��������:showEnv
	 * @��������:��ʾJAVA������Ϣ
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	private void showEnv(){
		Properties props=System.getProperties(); 
		/**
		 * ��ӡϵͳ���ԣ�����ѯ����ʹ��
		 */
		log.info(StringUtils.center(" JVM Properties ", 50, "="));
		log.info("Java�İ�װ·��:{}",props.getProperty("java.home"));
		log.info("Java�����л����汾:{}",props.getProperty("java.version"));
		log.info("Java�����л�����Ӧ��:{}",props.getProperty("java.vendor"));
		log.info("Java�������ʵ�ְ汾:{}",props.getProperty("java.vm.version"));
		log.info("����������ϵͳ������:{}",props.getProperty("os.name"));
		log.info("����������ϵͳ�Ĺ���:{}",props.getProperty("os.arch"));
		log.info("����������ϵͳ�İ汾:{}",props.getProperty("os.version"));
		log.info("�û�����Ŀ¼:{}",props.getProperty("user.home"));
		log.info("Ӧ�õĵ�ǰ����Ŀ¼:{}",props.getProperty("user.dir"));
		log.info(StringUtils.repeat("=", 50));
	}
	
	/**
	 * @��������:regeditSignal
	 * @��������:ע���ź������ó���ӵ��źź�����Ӧ�Ķ�����
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	protected void regeditSignal(){
		try {
			/**
			 * ������ֹͣ��
			 * */
			SignalHandler stopHandler = new SignalHandler(){
				public void handle(Signal signal) {
					log.info("Received signal:{},stop ECIF Synchro Server...",signal.getName());
					stop();
				}
			};
			/***
			 * ���������¼�������
			 */
			SignalHandler reConfigHandler = new SignalHandler(){
				public void handle(Signal signal) {
					log.info("Received signal:{},reConfig ECIF Synchro Server...",signal.getName());
					try{
						if(!initServer()){
							log.info("��������ʼ��ʧ��,��Ҫ����");
							stop();
							return;
						}
						log.info("�������÷������ɹ�");
					}catch(Exception e){
						log.error("�������÷�����ʧ��:", e);
						stop();
					}
				}
			};
			try{
				/***
				 * ע��ֹͣ�ź�
				 */
				Signal.handle(new Signal("TERM"), stopHandler);//�൱��kill -15
			}catch(Exception e){
				log.warn("ע���ź���TERMʧ��:{}",e.getLocalizedMessage());
			}
//			try{
//				Signal.handle(new Signal("INT"),  stopHandler);//�൱��Ctrl+C
//			}catch(Exception e){
//				log.warn("ע���ź���INTʧ��:{}",e.getLocalizedMessage());
//			}
			String osName=System.getProperty("os.name");
			String reConfigSv=BusinessCfg.getString("reconfigsv");
			if(!StringUtil.isEmpty(reConfigSv)){
				if(osName!=null && !osName.contains("Windows")){
					log.debug("��ǰ����ϵͳΪ{},��ע���ź���{},���ź������ڷ��������¶�ȡ������Ϣ��",osName,reConfigSv);
					Signal.handle(new Signal(reConfigSv), reConfigHandler);//
				}else{
					log.debug("��ǰ����ϵͳΪ{},����ע���ź���{},���ź������ڷ��������¶�ȡ������Ϣ��",osName,reConfigSv);
				}
			}
		}catch(Exception e){
			log.error("ע���ź���ʧ��:",e);
		}
	}
	
	/**
	 * @��������:initLog
	 * @��������:��������־����
	 * @�����뷵��˵��:
	 * 		@param serverApp ����ӿ�����
	 * 		@param defauteName Ĭ�Ϸ���ӿ�����
	 * @�㷨����:
	 */
	protected void initLog(String serverApp,String defauteName){
		/**������־,һ������Ӧ�õ�������־**/
		if(StringUtil.isEmpty(System.getenv("LOG_PATH"))){
			/**
			 * ������־·����Ĭ���ڵ�ǰĿ¼��log�ļ�����
			 */
			System.setProperty ("LOG_PATH", "./log");
		}
		if(serverApp!=null){
			/**
			 * ������־�ļ����ƺ�׺,�ѷ�������ͬ
			 */
			System.setProperty ("APP", serverApp);
			PropertyConfigurator.configure(Thread.currentThread().getContextClassLoader().getResource(log4jpro)); 
			log = LoggerFactory.getLogger(AbsServerLauncher.class);
			showEnv();
			log.info("����������Ӧ��{}",serverApp);
		}else{
			/**
			 * ����Ĭ����־�ļ�
			 */
			System.setProperty ("APP", defauteName);
			PropertyConfigurator.configure(Thread.currentThread().getContextClassLoader().getResource(log4jpro)); 
			log = LoggerFactory.getLogger(AbsServerLauncher.class);
			showEnv();
			log.info("����Ĭ�Ϸ�����Ӧ��");
		}
	}
	
	/**
	 * @��������:createFileOk
	 * @��������:�ڹ���Ŀ¼��д��������ļ����������ű���
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	protected void createFileOk(){
		/**
		 * ��ȡ�����ļ�·��
		 */
		File myFilePath = new File(System.getProperty("user.dir")+"/"+System.getProperty("APP"));
		if (!myFilePath.exists()) {
			try {
				/**
				 * ӦΪ��̨�����ں�̨����,�����ű��޷���֪�����Ƿ������ɹ���
				 * �������ɹ��󣬸��������ļ���֪ͨ���ƽű�������������
				 */
				myFilePath.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
		}
	}
	
	/**
	 * @��������:libJarLoader
	 * @��������:������lib jar����-Decif.ext.dirs=XXXĿ¼��Ҳ������ʾ��java -cp xxx.jar:xx.jar....
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public static void libJarLoader(){
		/***
		 * ����jar
		 * ���java ����ʱ����-cp ����jvm���õ���jar��
		 * ��ô����ʹ��-Decif.ext.dirs=XXXĿ¼�������Զ�����jar�ļ�
		 * ***/
		String lib_dir=System.getProperty("ecif.ext.dirs");
		if(lib_dir!=null&&!lib_dir.isEmpty()){
			JarLoader jarLoader = new JarLoader((URLClassLoader) ClassLoader.getSystemClassLoader());
			try {
				/**
				 * ���ص�����jar�ļ�
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
	 * @��������:initServer
	 * @��������:��ʼ��������
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	protected abstract boolean initServer();
	
	
	

}
