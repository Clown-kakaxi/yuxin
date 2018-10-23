/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����AuthModel.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-9:34:24
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.base.bo;

/**
 * The Class AuthModel.
 * 
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�AuthModel
 * @���������ͻ�����Ȩ������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-16 ����8:02:40
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-16 ����8:02:40
 * @�޸ı�ע��
 * @�޸�����    �޸���Ա                      �޸�ԭ�� 
 *  -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class AuthModel {
	
	/**
	 * The src sys cd.
	 * 
	 * @��������:Դϵͳ����
	 */
	private String srcSysCd;
	
	/**
	 * The user name.
	 * 
	 * @��������:Դϵͳƾ֤
	 */
	private String userName;
	
	/**
	 * The password.
	 * 
	 * @��������:Դϵͳ����
	 */
	private String password;
	
	/**
	 * The tx code.
	 * 
	 * @��������:���ױ���
	 */
	private String txCode;
	
	/**
	 * The client auth id.
	 * 
	 * @��������:�ͻ�����ȨID
	 */
	private Long clientAuthId;
	
	
	/**
	 * Gets the src sys cd.
	 * 
	 * @return the src sys cd
	 */
	public String getSrcSysCd() {
		return srcSysCd;
	}

	/**
	 * Sets the src sys cd.
	 * 
	 * @param srcSysCd
	 *            the new src sys cd
	 */
	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the tx code.
	 * 
	 * @return the tx code
	 */
	public String getTxCode() {
		return txCode;
	}

	/**
	 * Sets the tx code.
	 * 
	 * @param txCode
	 *            the new tx code
	 */
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	/**
	 * Gets the client auth id.
	 * 
	 * @return the client auth id
	 */
	public Long getClientAuthId() {
		return clientAuthId;
	}

	/**
	 * Sets the client auth id.
	 * 
	 * @param clientAuthId
	 *            the new client auth id
	 */
	public void setClientAuthId(Long clientAuthId) {
		this.clientAuthId = clientAuthId;
	}
	
}
