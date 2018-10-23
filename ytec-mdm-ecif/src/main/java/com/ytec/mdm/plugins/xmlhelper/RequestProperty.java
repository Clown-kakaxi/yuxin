/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.xmlhelper
 * @文件名：RequestProperty.java
 * @版本信息：1.0.0
 * @日期：2013-12-18-下午4:00:15
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.plugins.xmlhelper;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：RequestProperty
 * @类描述：ECIFDATA属性提取模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-18 下午4:00:15   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-18 下午4:00:15
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class RequestProperty {
	/**
	 * @属性名称:propertyName
	 * @属性描述:EcifData中属性名称
	 * @since 1.0.0
	 */
	private String propertyName;
	/**
	 * @属性名称:propertyXpath
	 * @属性描述:请求报文中对应的Xpath
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
