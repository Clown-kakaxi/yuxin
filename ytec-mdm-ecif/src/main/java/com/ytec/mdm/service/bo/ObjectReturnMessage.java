/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.bo
 * @�ļ�����ObjectReturnMessage.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:55:02
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.bo;

import com.ytec.mdm.base.bo.IReturnMessage;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ObjectReturnMessage
 * @�����������ݼ�����ҵ���������Ҽ�¼����ֵ
 * @��������:successFlagΪ��ʱ��object��ֵ��successFlagΪ��ʱ��erroMessage��ֵ
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:55:02   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:55:02
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ObjectReturnMessage extends IReturnMessage {
	/**
	 * @��������:object
	 * @��������:���ض���
	 * @since 1.0.0
	 */
	private Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
