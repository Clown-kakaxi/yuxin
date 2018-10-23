/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.transaction.core
 * @�ļ�����ErrorEcifDealEngine.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:10:04
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
package com.ytec.mdm.integration.transaction.core;


import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�ErrorEcifDealEngine
 * @��������Ĭ�ϴ���������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:10:04
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:10:04
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 *
 */
public class ErrorEcifDealEngine extends AbstractEcifDealEngine {

	/**
	 * @��������:errorMsg
	 * @��������:��������
	 * @since 1.0.0
	 */
	private String errorMsg;

	/**
	 *@���캯��
	 */
	public ErrorEcifDealEngine() {
		super();
	}
	/**
	 *@���캯��
	 * @param errorMsg
	 */
	public ErrorEcifDealEngine(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}


	/* (non-Javadoc)
	 * @see com.ytec.mdm.transaction.core.AbstractTxDealEngine#execute(java.lang.String)
	 */
	@Override
	public void execute(EcifData data) {
		data.setSuccess(false);
		data.setStatus (ErrorCode.ERR_ADAPTER_TRANSFORM_ERROR.getCode(),errorMsg);
		return;
	}

}
