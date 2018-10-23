/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：FieldChangeModle.java
 * @版本信息：1.0.0
 * @日期：2014-5-15-下午3:49:48
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.base.bo;

import java.text.SimpleDateFormat;

import com.ytec.mdm.interfaces.common.sensitinfo.SensitHelper;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：FieldChangeModle
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-15 下午3:49:48   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-15 下午3:49:48
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class FieldChangeModle {
	/**
	 * @属性名称:fieldName
	 * @属性描述:属性名称
	 * @since 1.0.0
	 */
	private String fieldName;
	/**
	 * @属性名称:oldVal
	 * @属性描述:变更前数据
	 * @since 1.0.0
	 */
	private Object oldVal;
	/**
	 * @属性名称:newval
	 * @属性描述:变更后数据
	 * @since 1.0.0
	 */
	private Object newval;
	/**
	 * @属性名称:fieldType
	 * @属性描述:属性类型
	 * 0:其他
	 * 1:定长字符串
	 * 2:可变长度
	 * 3:整形
	 * 4:浮点
	 * 5:日期
	 * 6:时间
	 * 7:时间戳
	 * 8:大文本
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
	 * @函数名称:object2String
	 * @函数描述:对象转字符串
	 * @参数与返回说明:
	 * 		@param value
	 * 		@return
	 * @算法描述:
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
			return "***(敏感信息)";
		}
	}
	
	@Override
	public String toString() {
		if(SensitHelper.getInstance().isDbSensitiveInfor(fieldName)){
			return "属性:" + fieldName + "==>变更前:"+ sensitFormat(oldVal) + ", 变更后:" + sensitFormat(newval);
		}else{
			return "属性:" + fieldName + "==>变更前:"+ object2String(oldVal) + ", 变更后:" + object2String(newval);
		}
	}
}
