/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����DataChangeModel.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-15-����3:45:09
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.base.bo;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�DataChangeModel
 * @�����������ݱ����¼����
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-15 ����3:45:09   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-15 ����3:45:09
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class DataChangeModel {
	/**
	 * @��������:entityName
	 * @��������:ʵ������
	 * @since 1.0.0
	 */
	private String entityName;
	
	/**
	 * @��������:keyName
	 * @��������:��������
	 * @since 1.0.0
	 */
	private String keyName;
	/**
	 * @��������:keyValue
	 * @��������:����ֵ
	 * @since 1.0.0
	 */
	private String keyValue;
	/**
	 * @��������:fieldChangeList
	 * @��������:��������б�
	 * @since 1.0.0
	 */
	private List<FieldChangeModle> fieldChangeList;
	
	/**
	 * @��������:changeType
	 * @��������:�������
	 * 0:�޸�
	 * 1:����
	 * 2:ɾ��
	 * @since 1.0.0
	 */
	private int changeType;
	
	private boolean newChange;
	
	
	
	public DataChangeModel(String entityName, String keyValue) {
		this.entityName = entityName;
		this.keyValue = keyValue;
	}
	public DataChangeModel() {
		// TODO Auto-generated constructor stub
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public int getChangeType() {
		return changeType;
	}
	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}
	public void addChangeField(String fieldName, Object oldVal, Object newval,Class typeClass){
		if(fieldChangeList==null){
			fieldChangeList=new LinkedList<FieldChangeModle>();
		}
		int fieldType=0;
		if(java.util.Date.class.equals(typeClass)){
			fieldType=5;
		}else if(Timestamp.class.equals(typeClass)){
			fieldType=7;
		}
		fieldChangeList.add(new FieldChangeModle(fieldName, oldVal, newval,fieldType));
	}
	
	public List<FieldChangeModle> getFieldChangeList() {
		return fieldChangeList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entityName == null) ? 0 : entityName.hashCode());
		result = prime * result
				+ ((keyValue == null) ? 0 : keyValue.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataChangeModel other = (DataChangeModel) obj;
		if (entityName == null) {
			if (other.entityName != null)
				return false;
		} else if (!entityName.equals(other.entityName))
			return false;
		if (keyValue == null) {
			if (other.keyValue != null)
				return false;
		} else if (!keyValue.equals(other.keyValue))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		if(changeType==1){
			sb.append("����ʵ��:");
		}else if(changeType==2){
			sb.append("ɾ��ʵ��:");
		}else{
			sb.append("���ʵ��:");
		}
		sb.append(entityName).append("[����(").append(keyName).append(")=").append(keyValue).append("]");
		if(fieldChangeList!=null&&!fieldChangeList.isEmpty()){
			for(FieldChangeModle fieldChangeModle:fieldChangeList){
				sb.append("\n\t").append(fieldChangeModle.toString());
			}
		}
		return sb.toString();
	}
	public boolean isNewChange() {
		return newChange;
	}
	public void setNewChange(boolean newChange) {
		this.newChange = newChange;
	}
	
}
