/**
 * @��Ŀ����ytec-mdm-trans
 * @������com.ytec.mdm.service.bo
 * @�ļ�����ObjectReturnMessage.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:55:02
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.service.bo;

import com.ytec.mdm.base.bo.Error;
import com.ytec.mdm.base.bo.ErrorCode;

/**
 * @��Ŀ���ƣ�ytec-mdm-trans(CRM)
 * @�����ƣ�ObjectReturnMessage
 * @�����������ݼ�����ҵ���������Ҽ�¼����ֵ
 * @��������:successFlagΪ��ʱ��object��ֵ��successFlagΪ��ʱ��erroMessage��ֵ
 * @�����ˣ�wangtb@yuchengtech.com
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class ObjectReturnMessage {
	/**
	 * @��������:object
	 * @��������:���ض���
	 * @since 1.0.0
	 */
	private Object object;

	/**
	 * The success flag.
	 *
	 * @��������:�Ƿ�ɹ�
	 */
	private boolean successFlag;

	/**
	 * The error.
	 *
	 * @��������:������
	 */
	private Error error = ErrorCode.SUCCESS;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	/**
	 * @��������:isSuccessFlag
	 * @��������:
	 * @�����뷵��˵��:
	 * @return
	 * @�㷨����:
	 */
	public boolean isSuccessFlag() {
		return successFlag;
	}

	/**
	 * Sets the success flag.
	 *
	 * @param successFlag
	 *        the new success flag
	 */
	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public Error getError() {
		return error;
	}

	/**
	 * Sets the error.
	 *
	 * @param error
	 *        the new error
	 */
	public void setError(Error error) {
		this.error = error;
	}
}
