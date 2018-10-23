/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server
 * @�ļ�����EcifTradingLauncher.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:51:28
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.server;
import java.net.URISyntaxException;

import com.ytec.mdm.interfaces.common.IServer;
import com.ytec.mdm.server.common.AbsServerLauncher;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�EcifTradingLauncher
 * @�����������׷���������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:51:31   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:51:31
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class EcifTradingLauncher extends AbsServerLauncher{
	/**
	 * @��������:serverApp
	 * @��������:����������
	 * @since 1.0.0
	 */
	private String serverApp;
	/**
	 * @��������:cfgPath_
	 * @��������:�����ļ�·��
	 * @since 1.0.0
	 */
	private String cfgPath_;
	public void start(String args[]){
		if (args.length == 2 ){
			/**
			 * ��ȡƽ�����ļ�·���������ӿ�����
			 */
			cfgPath_=args[0];
			serverApp=args[1];
		}if (args.length == 1 ){
			try {
				/**
				 * ��ȡĬ�������ļ�·����
				 * ʹ�á�toURI()����Ϊ�˽��·���е�������������
				 */
				cfgPath_=Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cfgPath_=Thread.currentThread().getContextClassLoader().getResource("").getPath();
			};
			serverApp=args[0];
		}else{
			try {
				cfgPath_=Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cfgPath_=Thread.currentThread().getContextClassLoader().getResource("").getPath();
			};
			serverApp=null;
		}
		/**������־,һ������Ӧ�õ�������־**/
		initLog(serverApp,"DEFAULT");
		if(!initServer()){
			log.info("��������ʼ��ʧ��");
			createFileOk();
			return;
		}
		/**ע���ź���**/
		regeditSignal();
		try{
			log.info("��������ʼ...");
			Class clazz = Class.forName(ServerConfiger.serverImpl);
			launcherAppServer=(IServer)clazz.newInstance();
			/**
			 * ���׷����ʼ��
			 */
			launcherAppServer.init(ServerConfiger.serverArg);
			/**
			 * ӦΪ��̨�����ں�̨����,�����ű��޷���֪�����Ƿ������ɹ���
			 * �������ɹ��󣬸��������ļ���֪ͨ���ƽű�������������
			 */
			createFileOk();
			/**
			 * ���׽ӿ�����
			 */
			launcherAppServer.start();
			//log.info("��������ɹ�");
		}catch(Exception e){
			log.error("����������ʧ��",e);
			createFileOk();
			return ;
		}catch(Throwable ex){
			log.error("���������ش���",ex);
			return ;
		}
	}
	public void stop(){
		ServerConfiger.serverRun=false;
		launcherAppServer.stop();
	}
	
	/**
	 * @��������:main
	 * @��������:���׷�����ں���
	 * @�����뷵��˵��:
	 * 		@param args
	 * @�㷨����:
	 */
	public static void main(String args[]){
		EcifTradingLauncher ecifServer=new EcifTradingLauncher();
		/**
		 * *����jar***/
		AbsServerLauncher.libJarLoader();
		/**
		 * ���׽ӿ�����
		 */
		ecifServer.start(args);
	}
	/* (non-Javadoc)
	 * @see com.ytec.mdm.server.common.AbsServerLauncher#initServer()
	 */
	@Override
	protected boolean initServer() {
		// TODO Auto-generated method stub
		/**
		 * ���׷����ʼ��
		 */
		return ServerConfiger.initServer(cfgPath_, serverApp);
	}
}
