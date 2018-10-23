/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.plugins.inforExtraction
 * @�ļ�����ExtractionModel.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-2-20-16:17:25
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.plugins.inforExtraction;

import java.util.List;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ExtractionModel
 * @����������Ϣ��ȡģ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-2-20 ����4:17:34   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-2-20 ����4:17:34
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ExtractionModel {
	/**
	 * @��������:modelClass
	 * @��������:ģ����
	 * @since 1.0.0
	 */
	private String modelClass;
	/**
	 * @��������:isRequest
	 * @��������:�Ƿ����
	 * @since 1.0.0
	 */
	private boolean isRequest;
    /**
     * @��������:keyProperty
     * @��������:��������ӳ������
     * @since 1.0.0
     */
    private List<PropertyMapping> keyProperty;
	/**
	 * @��������:norProperty
	 * @��������:�Ǳ�������ӳ������
	 * @since 1.0.0
	 */
	private List<PropertyMapping> norProperty;
	public String getModelClass() {
		return modelClass;
	}
	public void setModelClass(String modelClass) {
		this.modelClass = modelClass;
	}
	public boolean isRequest() {
		return isRequest;
	}
	public void setRequest(boolean isRequest) {
		this.isRequest = isRequest;
	}
	public List<PropertyMapping> getKeyProperty() {
		return keyProperty;
	}
	public void setKeyProperty(List<PropertyMapping> keyProperty) {
		this.keyProperty = keyProperty;
	}
	public List<PropertyMapping> getNorProperty() {
		return norProperty;
	}
	public void setNorProperty(List<PropertyMapping> norProperty) {
		this.norProperty = norProperty;
	}
	
}
