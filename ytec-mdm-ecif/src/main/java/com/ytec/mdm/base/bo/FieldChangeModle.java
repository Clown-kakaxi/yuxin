/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����FieldChangeModle.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-15-����3:49:48
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.bo;

import java.text.SimpleDateFormat;

import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�FieldChangeModle
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-15 ����3:49:48   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-15 ����3:49:48
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class FieldChangeModle {
	/**
	 * @��������:fieldName
	 * @��������:��������
	 * @since 1.0.0
	 */
	private String fieldName;
	/**
	 * @��������:oldVal
	 * @��������:���ǰ����
	 * @since 1.0.0
	 */
	private Object oldVal;
	/**
	 * @��������:newval
	 * @��������:���������
	 * @since 1.0.0
	 */
	private Object newval;
	/**
	 * @��������:fieldType
	 * @��������:��������
	 * 0:����
	 * 1:�����ַ���
	 * 2:�ɱ䳤��
	 * 3:����
	 * 4:����
	 * 5:����
	 * 6:ʱ��
	 * 7:ʱ���
	 * 8:���ı�
	 * @since 1.0.0
	 */
	private int fieldType;
	
	public FieldChangeModle(String fieldName, Object oldVal, Object newval,
			int fieldType) {
		this.fieldName = fieldName;
		this.oldVal = oldVal;
		this.newval = newval;
		this.fieldType = fieldType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getOldVal() {
		return oldVal;
	}
	public void setOldVal(Object oldVal) {
		this.oldVal = oldVal;
	}
	public Object getNewval() {
		return newval;
	}
	public void setNewval(Object newval) {
		this.newval = newval;
	}
	public int getFieldType() {
		return fieldType;
	}
	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}
	
	/**
	 * @��������:object2String
	 * @��������:����ת�ַ���
	 * @�����뷵��˵��:
	 * 		@param value
	 * 		@return
	 * @�㷨����:
	 */
	private String object2String(Object value){
		if(value==null){
			return "NULL";
		}else{
			switch (fieldType){
			case 5:
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				return dateFormat.format(value);
			case 7:
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return dateFormat1.format(value);
			default:
				return value.toString();
			}
		}
	}
	private String sensitFormat(Object value){
		if(value==null){
			return "NULL";
		}else{
			return "***(������Ϣ)";
		}
	}
	
	@Override
	public String toString() {
		if(SensitHelper.getInstance().isDbSensitiveInfor(fieldName)){
			return "����:" + fieldName + "==>���ǰ:"+ sensitFormat(oldVal) + ", �����:" + sensitFormat(newval);
		}else{
			return "����:" + fieldName + "==>���ǰ:"+ object2String(oldVal) + ", �����:" + object2String(newval);
		}
	}
}
