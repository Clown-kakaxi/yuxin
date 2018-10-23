/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.even
 * @�ļ�����Subject.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:33:11
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.interfaces.common.even;

import com.ytec.mdm.base.bo.EcifData;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�Subject
 * @���������¼�����ӿ�
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:33:12
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:33:12
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
public interface Subject {
	/**
	 * �����¼�
	 * @param o �¼�����
	 */
	 public void addObserver(Observer o);

	 /**
	 * @��������:eventNotify
	 * @��������:�����¼�֪ͨͬ��
	 * @�����뷵��˵��:
	 * 		@param ecifData
	 * @�㷨����:
	 */
	public void eventNotify(EcifData ecifData);

	 /**
	 * @��������:clearObserver
	 * @��������:����¼��б�
	 * @�����뷵��˵��:
	 * @�㷨����:
	 */
	public void clearObserver();
}
