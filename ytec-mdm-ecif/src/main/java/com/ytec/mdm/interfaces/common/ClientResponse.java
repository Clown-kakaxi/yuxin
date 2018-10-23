/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common
 * @�ļ�����ClientResponse.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-14-����10:46:58
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ClientResponse
 * @���������ͻ�����Ӧ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-14 ����10:46:58   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-14 ����10:46:58
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ClientResponse {
	
	/**
	 * @��������:success
	 * @��������:�Ƿ��ͳɹ�
	 * @since 1.0.0
	 */
	private boolean success=true;
	/**
	 * @��������:responseMsg
	 * @��������:��Ӧ����
	 * @since 1.0.0
	 */
	private String responseMsg;
	
	/**
	 * @��������:timeout
	 * @��������:�Ƿ�ʱ
	 * @since 1.0.0
	 */
	private boolean timeout;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public boolean isTimeout() {
		return timeout;
	}
	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}
	
	@Override
	public String toString(){
		return "success:"+success+";"+
				"responseMsg:"+responseMsg+";"+
				"timeout:"+timeout+";";
	}
	
}
