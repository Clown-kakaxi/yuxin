/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.bo
 * @文件名：MCiIdentifier.java
 * @版本信息：1.0.0
 * @日期：2014-7-8-上午9:52:27
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.bo;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：MCiIdentifier
 * @类描述：三证信息类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-7-8 上午9:52:27   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-7-8 上午9:52:27
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class Identifier {
	/**
	 * @属性名称:identType
	 * @属性描述:证件类型
	 * @since 1.0.0
	 */
	private String identType;
	/**
	 * @属性名称:identNo
	 * @属性描述:证件号码
	 * @since 1.0.0
	 */
	private String identNo;
	/**
	 * @属性名称:identCustName
	 * @属性描述:户名
	 * @since 1.0.0
	 */
	private String identCustName;
	
	/**
	 *@构造函数 
	 */
	public Identifier() {
	}
	/**
	 *@构造函数 
	 * @param identType
	 * @param identNo
	 * @param identCustName
	 */
	public Identifier(String identType, String identNo, String identCustName) {
		super();
		this.identType = identType;
		this.identNo = identNo;
		this.identCustName = identCustName;
	}
	public String getIdentType() {
		return identType;
	}
	public void setIdentType(String identType) {
		this.identType = identType;
	}
	public String getIdentNo() {
		return identNo;
	}
	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}
	public String getIdentCustName() {
		return identCustName;
	}
	public void setIdentCustName(String identCustName) {
		this.identCustName = identCustName;
	}
	
}
