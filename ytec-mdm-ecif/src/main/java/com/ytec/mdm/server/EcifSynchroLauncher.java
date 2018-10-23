/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server
 * @�ļ�����EcifSynchroLauncher.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:52:00
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.server;
import com.ytec.mdm.interfaces.common.IServer;
import com.ytec.mdm.server.common.AbsServerLauncher;
import com.ytec.mdm.server.common.DataSynchroConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�EcifSynchroLauncher
 * @������������ͬ����������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:52:01   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:52:01
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class EcifSynchroLauncher extends AbsServerLauncher{
	
	public void start(String args[]){
		/**������־**/
		initLog("SYNC","SYNC");
		if(!DataSynchroConfiger.initDataSynchroServer()){
			log.info("ͬ����������ʼ��ʧ��");
			createFileOk();
			return;
		}
		/**ע���ź���**/
		regeditSignal();
		try{
			log.info("��������ʼ...");
			Class clazz = Class.forName(DataSynchroConfiger.listenerImpl);
			launcherAppServer=(IServer)clazz.newInstance();
			/**
			 * ����ӿڳ�ʼ��
			 */
			launcherAppServer.init(null);
			/**
			 * ӦΪ��̨�����ں�̨����,�����ű��޷���֪�����Ƿ������ɹ���
			 * �������ɹ��󣬸��������ļ���֪ͨ���ƽű�������������
			 */
			createFileOk();
			/**
			 * ����ӿ�����
			 */
			launcherAppServer.start();
		}catch(Exception e){
			log.error("����������ʧ��",e);
			createFileOk();
			return ;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ytec.mdm.server.common.IServerLauncher#stop()
	 */
	public void stop(){
		/**
		 * ��������ֹͣ���Ʊ���
		 */
		DataSynchroConfiger.serverRun=false;
		/**
		 * ���÷���֪ͨ����
		 */
		launcherAppServer.stop();
	}
	
	/**
	 * @��������:main
	 * @��������:��ں���
	 * @�����뷵��˵��:
	 * 		@param args
	 * @�㷨����:
	 */
	public static void main(String args[]){
		EcifSynchroLauncher synchroLauncher =new EcifSynchroLauncher();
		/***
		 * ����jar
		 * ***/
		AbsServerLauncher.libJarLoader();
		/**
		 * ͬ����������
		 */
		synchroLauncher.start(args);
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.server.common.AbsServerLauncher#initServer()
	 */
	@Override
	protected boolean initServer() {
		/**
		 * ���ó�ʼ�����������������ļ�
		 */
		return DataSynchroConfiger.initDataSynchroServer();
	}
	
}
