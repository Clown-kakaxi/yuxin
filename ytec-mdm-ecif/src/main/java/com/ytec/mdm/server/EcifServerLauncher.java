/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server
 * @�ļ�����EcifServerLauncher.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:51:28
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.server;
import com.ytec.mdm.server.common.AbsServerLauncher;
import com.ytec.mdm.server.common.CommandLineHelper;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�EcifServerLauncher
 * @������������������,�����ӡ�-s�� ����ͬ������Ĭ���������׷���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:51:31
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:51:31
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ�� -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class EcifServerLauncher {

	/**
	 * @��������:main
	 * @��������:��ں���
	 * @�����뷵��˵��:
	 * 		@param args �������
	 * @�㷨����:
	 */
	public static void main(String args[]) {
		AbsServerLauncher serverLauncher = null;
		/***
		 * ���������������ecif.ext.dirs�����ص�����jar
		 * ***/
		AbsServerLauncher.libJarLoader();
		/**
		 * ʹ��apache.commons.cli��������
		 */
		CommandLineHelper commandLineHelper=new CommandLineHelper();
		if(!commandLineHelper.optionsParser(args)){
			return ;
		}
		/**
		 * �ж��Ƿ���-s��--sync����
		 */
		if (commandLineHelper.getCl().hasOption("s")) {
			/**
			 * -s��--sync���� ����ͬ������
			 */
			serverLauncher = new EcifSynchroLauncher();
		} else {
			/**
			 * Ĭ���������׷���
			 */
			serverLauncher = new EcifTradingLauncher();
		}
		/**
		 * ��ȡ����ֵ��������Ҫ�Ƿ������
		 */
		String[] arg = commandLineHelper.getCl().getArgs();
		commandLineHelper=null;
		/**
		 * ����ӿ�������javaû��C��C++�ĺ�̨���̵ĸ��
		 * ��������̨�ػ�������UNIX��nohup��������ʽ
		 */
		serverLauncher.start(arg);
	}
}
