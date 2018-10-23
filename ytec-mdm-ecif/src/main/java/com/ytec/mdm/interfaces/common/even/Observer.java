/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.even
 * @�ļ�����Observer.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:32:38
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common.even;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�Observer
 * @���������¼��ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:32:38   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:32:38
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public interface Observer {
	/**
	 * @��������:executeObserver
	 * @��������:ִ���¼�֪ͨ
	 * @�����뷵��˵��:
	 * 		@param ecifData ecif���ݶ���
	 * @�㷨����:
	 */
	public void executeObserver(EcifData ecifData);
	/**
	 * @��������:init
	 * @��������:�¼����ʼ��
	 * @�����뷵��˵��:
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public void init() throws Exception;
}
