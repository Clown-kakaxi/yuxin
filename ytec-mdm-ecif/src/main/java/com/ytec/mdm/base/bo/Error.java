/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����Error.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-9:54:25
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.base.bo;

/**
 * The Class Error.
 * 
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ� Error
 * @�����������󷵻���ģ��
 * @��������:
 * @�����ˣ� wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 9:54:25
 * @�޸��ˣ� wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 9:54:25
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class Error {
	
	/**
	 * The code.
	 * 
	 * @��������:������
	 */
	private String code;
	
	/**
	 * The en desc.
	 * 
	 * @��������:Ӣ������
	 */
	private String enDesc;
	
	/**
	 * The ch desc.
	 * 
	 * @��������:��������
	 */
	private String chDesc;

	/**
	 * The Constructor.
	 * 
	 * @���캯�� error
	 */
	public Error() {
	}

	
	/**
	 *@���캯�� 
	 * @param value
	 */
	public Error(String value) {
		String value0[] = value.split("\\|");
		this.code = value0[0];
		this.enDesc = value0[1];
		this.chDesc = value0[2];
	}

	
	/**
	 *@���캯�� 
	 * @param code
	 * @param enDesc
	 * @param chDesc
	 */
	public Error(String code, String enDesc, String chDesc) {
		this.code = code;
		this.enDesc = enDesc;
		this.chDesc = chDesc;

	}

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 * 
	 * @param code
	 *            the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the en desc.
	 * 
	 * @return the en desc
	 */
	public String getEnDesc() {
		return enDesc;
	}

	/**
	 * Sets the en desc.
	 * 
	 * @param enDesc
	 *            the new en desc
	 */
	public void setEnDesc(String enDesc) {
		this.enDesc = enDesc;
	}

	/**
	 * Gets the ch desc.
	 * 
	 * @return the ch desc
	 */
	public String getChDesc() {
		return chDesc;
	}

	/**
	 * Sets the ch desc.
	 * 
	 * @param chDesc
	 *            the new ch desc
	 */
	public void setChDesc(String chDesc) {
		this.chDesc = chDesc;
	}

}
