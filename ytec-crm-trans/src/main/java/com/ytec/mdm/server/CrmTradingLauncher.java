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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.integration.transaction.bs.TxConfigBS4CRM;
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
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class CrmTradingLauncher extends AbsServerLauncher {
	private String serverAppDaulft = "SOCKET_Server";
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

	public void start(String args[]) {
		String resourceUrl = null;
		resourceUrl = System.getProperty("user.dir");
		if (args.length == 2) {
			/**
			 * ��ȡƽ�����ļ�·���������ӿ�����
			 */
			cfgPath_ = args[0];
			serverApp = args[1];
		}
		if (resourceUrl == null || "".equals(resourceUrl)) {
			log.error("�޷���ȡĬ�������ļ�·������������ʧ��");
			serverApp = serverAppDaulft;
			log.info("����Ĭ�Ϸ����� " + serverApp);
		} else {
			if (args.length == 1) {
				cfgPath_ = resourceUrl;
				serverApp = args[0];
			} else {
				cfgPath_ = resourceUrl;
				serverApp = null;
			}
		}
		/** ������־,һ������Ӧ�õ�������־ **/
		initLog(serverApp, "DEFAULT");
		if (!initServer()) {
			log.info("��������ʼ��ʧ��");
			createFileOk();
			return;
		}
		/** ע���ź��� **/
		regeditSignal();
		try {
			log.info("��������ʼ...");
			Class clazz = Class.forName(ServerConfiger.serverImpl);
			launcherAppServer = (IServer) clazz.newInstance();
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
			log.info("��������ɹ�");
		} catch (Exception e) {
			log.error("����������ʧ��", e);
			createFileOk();
			return;
		} catch (Throwable ex) {
			log.error("���������ش���", ex);
			return;
		}
	}

	public void stop() {
		ServerConfiger.serverRun = false;
		launcherAppServer.stop();
	}

	/**
	 * @��������:main
	 * @��������:���׷�����ں���
	 * @�����뷵��˵��:
	 * @param args
	 * @�㷨����:
	 */
	public static void main(String args[]) {
		CrmTradingLauncher ecifServer = new CrmTradingLauncher();
		/**
		 * *����jar
		 ***/
		AbsServerLauncher.libJarLoader();
		/**
		 * ���׽ӿ�����
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
		 * ���׷����ʼ��
		 */
		return ServerConfiger.initServer(cfgPath_, serverApp) && TxConfigBS4CRM.initConfigBS(cfgPath_);
	}
}
