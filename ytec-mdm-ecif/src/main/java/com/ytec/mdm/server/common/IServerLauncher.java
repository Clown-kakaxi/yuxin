/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.server.common
 * @�ļ�����IServerLauncher.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-4-30-����10:05:38
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.server.common;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IServerLauncher
 * @�������������������ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-4-30 ����10:05:38   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-4-30 ����10:05:38
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface IServerLauncher {
	/**
	 * @��������:start
	 * @��������:����������
	 * @�����뷵��˵��:
	 * 		@param args
	 * @�㷨����:
	 */
	public void start(String args[]);
	/**
	 * @��������:stop
	 * @��������:������ֹͣ
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void stop();
}
