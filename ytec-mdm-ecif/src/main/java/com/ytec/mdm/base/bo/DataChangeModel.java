/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：DataChangeModel.java
 * @版本信息：1.0.0
 * @日期：2014-5-15-下午3:45:09
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.bo;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：DataChangeModel
 * @类描述：数据变更记录对象
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-15 下午3:45:09   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-15 下午3:45:09
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class DataChangeModel {
	/**
	 * @属性名称:entityName
	 * @属性描述:实体名称
	 * @since 1.0.0
	 */
	private String entityName;
	
	/**
	 * @属性名称:keyName
	 * @属性描述:主键名称
	 * @since 1.0.0
	 */
	private String keyName;
	/**
	 * @属性名称:keyValue
	 * @属性描述:主键值
	 * @since 1.0.0
	 */
	private String keyValue;
	/**
	 * @属性名称:fieldChangeList
	 * @属性描述:变更属性列表
	 * @since 1.0.0
	 */
	private List<FieldChangeModle> fieldChangeList;
	
	/**
	 * @属性名称:changeType
	 * @属性描述:变更类型
	 * 0:修改
	 * 1:新增
	 * 2:删除
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
			sb.append("新增实体:");
		}else if(changeType==2){
			sb.append("删除实体:");
		}else{
			sb.append("变更实体:");
		}
		sb.append(entityName).append("[主键(").append(keyName).append(")=").append(keyValue).append("]");
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
