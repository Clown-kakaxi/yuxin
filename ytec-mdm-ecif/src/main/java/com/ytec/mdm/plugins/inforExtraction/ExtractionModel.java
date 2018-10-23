/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.inforExtraction
 * @文件名：ExtractionModel.java
 * @版本信息：1.0.0
 * @日期：2014-2-20-16:17:25
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.plugins.inforExtraction;

import java.util.List;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ExtractionModel
 * @类描述：信息提取模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-20 下午4:17:34   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-20 下午4:17:34
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ExtractionModel {
	/**
	 * @属性名称:modelClass
	 * @属性描述:模型类
	 * @since 1.0.0
	 */
	private String modelClass;
	/**
	 * @属性名称:isRequest
	 * @属性描述:是否必须
	 * @since 1.0.0
	 */
	private boolean isRequest;
    /**
     * @属性名称:keyProperty
     * @属性描述:必须属性映射链表
     * @since 1.0.0
     */
    private List<PropertyMapping> keyProperty;
	/**
	 * @属性名称:norProperty
	 * @属性描述:非必须属性映射链表
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
