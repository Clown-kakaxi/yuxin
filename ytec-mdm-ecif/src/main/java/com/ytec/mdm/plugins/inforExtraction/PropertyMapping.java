/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.plugins.inforExtraction
 * @文件名：PropertyMapping.java
 * @版本信息：1.0.0
 * @日期：2014-2-20-16:18:57
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.plugins.inforExtraction;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：PropertyMapping
 * @类描述：属性模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-2-20 下午4:19:01   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-2-20 下午4:19:01
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class PropertyMapping {
	/**
	 * @属性名称:proCode
	 * @属性描述:模型属性名称
	 * @since 1.0.0
	 */
	private String proCode;
	/**
	 * @属性名称:attRoot
	 * @属性描述:提取节点
	 * @since 1.0.0
	 */
	private String attRoot;
	/**
	 * @属性名称:attName
	 * @属性描述:报文属性名
	 * @since 1.0.0
	 */
	private String attName;
	/**
	 * @属性名称:defaut
	 * @属性描述:默认值
	 * @since 1.0.0
	 */
	private String defaut;
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getAttRoot() {
		return attRoot;
	}
	public void setAttRoot(String attRoot) {
		this.attRoot = attRoot;
	}
	public String getAttName() {
		return attName;
	}
	public void setAttName(String attName) {
		this.attName = attName;
	}
	public String getDefaut() {
		return defaut;
	}
	public void setDefaut(String defaut) {
		this.defaut = defaut;
	}
}
