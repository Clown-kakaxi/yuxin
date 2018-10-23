/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：AuthModel.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-9:34:24
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.base.bo;

/**
 * The Class AuthModel.
 * 
 * @项目名称：ytec-mdm-ecif
 * @类名称：AuthModel
 * @类描述：客户端授权数据类
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-16 下午8:02:40
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-16 下午8:02:40
 * @修改备注：
 * @修改日期    修改人员                      修改原因 
 *  -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class AuthModel {
	
	/**
	 * The src sys cd.
	 * 
	 * @属性描述:源系统代码
	 */
	private String srcSysCd;
	
	/**
	 * The user name.
	 * 
	 * @属性描述:源系统凭证
	 */
	private String userName;
	
	/**
	 * The password.
	 * 
	 * @属性描述:源系统密码
	 */
	private String password;
	
	/**
	 * The tx code.
	 * 
	 * @属性描述:交易编码
	 */
	private String txCode;
	
	/**
	 * The client auth id.
	 * 
	 * @属性描述:客户端授权ID
	 */
	private Long clientAuthId;
	
	
	/**
	 * Gets the src sys cd.
	 * 
	 * @return the src sys cd
	 */
	public String getSrcSysCd() {
		return srcSysCd;
	}

	/**
	 * Sets the src sys cd.
	 * 
	 * @param srcSysCd
	 *            the new src sys cd
	 */
	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the tx code.
	 * 
	 * @return the tx code
	 */
	public String getTxCode() {
		return txCode;
	}

	/**
	 * Sets the tx code.
	 * 
	 * @param txCode
	 *            the new tx code
	 */
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	/**
	 * Gets the client auth id.
	 * 
	 * @return the client auth id
	 */
	public Long getClientAuthId() {
		return clientAuthId;
	}

	/**
	 * Sets the client auth id.
	 * 
	 * @param clientAuthId
	 *            the new client auth id
	 */
	public void setClientAuthId(Long clientAuthId) {
		this.clientAuthId = clientAuthId;
	}
	
}
