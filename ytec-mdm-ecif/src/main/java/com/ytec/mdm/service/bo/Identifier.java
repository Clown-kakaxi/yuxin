/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.bo
 * @�ļ�����MCiIdentifier.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-7-8-����9:52:27
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.service.bo;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�MCiIdentifier
 * @����������֤��Ϣ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-7-8 ����9:52:27   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-7-8 ����9:52:27
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class Identifier {
	/**
	 * @��������:identType
	 * @��������:֤������
	 * @since 1.0.0
	 */
	private String identType;
	/**
	 * @��������:identNo
	 * @��������:֤������
	 * @since 1.0.0
	 */
	private String identNo;
	/**
	 * @��������:identCustName
	 * @��������:����
	 * @since 1.0.0
	 */
	private String identCustName;
	
	/**
	 *@���캯�� 
	 */
	public Identifier() {
	}
	/**
	 *@���캯�� 
	 * @param identType
	 * @param identNo
	 * @param identCustName
	 */
	public Identifier(String identType, String identNo, String identCustName) {
		super();
		this.identType = identType;
		this.identNo = identNo;
		this.identCustName = identCustName;
	}
	public String getIdentType() {
		return identType;
	}
	public void setIdentType(String identType) {
		this.identType = identType;
	}
	public String getIdentNo() {
		return identNo;
	}
	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}
	public String getIdentCustName() {
		return identCustName;
	}
	public void setIdentCustName(String identCustName) {
		this.identCustName = identCustName;
	}
	
}
