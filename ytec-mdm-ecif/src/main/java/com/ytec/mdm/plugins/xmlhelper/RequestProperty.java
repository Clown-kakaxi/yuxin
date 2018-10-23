/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.xmlhelper
 * @�ļ�����RequestProperty.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-18-����4:00:15
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.plugins.xmlhelper;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�RequestProperty
 * @��������ECIFDATA������ȡģ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-18 ����4:00:15   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-18 ����4:00:15
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class RequestProperty {
	/**
	 * @��������:propertyName
	 * @��������:EcifData����������
	 * @since 1.0.0
	 */
	private String propertyName;
	/**
	 * @��������:propertyXpath
	 * @��������:�������ж�Ӧ��Xpath
	 * @since 1.0.0
	 */
	private String propertyXpath;
	
	
	public RequestProperty() {
		// TODO Auto-generated constructor stub
	}
	
	public RequestProperty(String propertyName, String propertyXpath) {
		super();
		this.propertyName = propertyName;
		this.propertyXpath = propertyXpath;
	}


	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyXpath() {
		return propertyXpath;
	}
	public void setPropertyXpath(String propertyXpath) {
		this.propertyXpath = propertyXpath;
	}
	
	
	

}
