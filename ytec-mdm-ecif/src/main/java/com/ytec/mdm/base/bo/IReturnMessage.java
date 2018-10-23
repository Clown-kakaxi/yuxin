/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����IReturnMessage.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:00:45
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.bo;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�IReturnMessage
 * @�����������ز���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:00:56   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:00:56
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class IReturnMessage {
	
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
	private Error error=ErrorCode.SUCCESS;

	
	/**
	 * @��������:isSuccessFlag
	 * @��������:
	 * @�����뷵��˵��:
	 * 		@return
	 * @�㷨����:
	 */
	public boolean isSuccessFlag() {
		return successFlag;
	}

	/**
	 * Sets the success flag.
	 * 
	 * @param successFlag
	 *            the new success flag
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
	 *            the new error
	 */
	public void setError(Error error) {
		this.error = error;
	}

}
