/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.base.bo
 * @文件名：InfoGrantObjectModel.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-9:57:24
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */

package com.ytec.mdm.base.bo;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：InfoGrantObjectModel
 * @类描述：信息授权模型
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午9:58:30   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午9:58:30
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class InfoGrantObjectModel {
	
	/**
	 * The auth type.
	 * 
	 * @属性描述:授权对象类型
	 */
	private String authType;
	
	/**
	 * The auth code.
	 * 
	 * @属性描述:授权对象代码
	 */
	private String authCode;
	
	/**
	 * The ctrl type.
	 * 
	 * @属性描述:授权类型
	 */
	private String ctrlType;
	
	/**
	 * The tab id.
	 * 
	 * @属性描述:表ID
	 */
	private Long tabId;			
	
	/**
	 * The col id.
	 * 
	 * @属性描述:字段ID
	 */
	private Long colId;
	
	
	
	/**
	 *@构造函数 
	 */
	public InfoGrantObjectModel() {
		
	}
	
	
	/**
	 *@构造函数 
	 * @param authType  授权类型
	 * @param authCode  授权码
	 * @param ctrlType  控制类型
	 * @param tabId     表ID
	 * @param colId     字段ID
	 */
	public InfoGrantObjectModel(String authType, String authCode,
			String ctrlType, Long tabId, Long colId) {
		this.authType = authType;
		this.authCode = authCode;
		this.ctrlType = ctrlType;
		this.tabId = tabId;
		this.colId = colId;
	}

	/**
	 * Gets the auth type.
	 * 
	 * @return the auth type
	 */
	public String getAuthType() {
		return authType;
	}
	
	/**
	 * Sets the auth type.
	 * 
	 * @param authType
	 *            the new auth type
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
	/**
	 * Gets the auth code.
	 * 
	 * @return the auth code
	 */
	public String getAuthCode() {
		return authCode;
	}
	
	/**
	 * Sets the auth code.
	 * 
	 * @param authCode
	 *            the new auth code
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	/**
	 * Gets the ctrl type.
	 * 
	 * @return the ctrl type
	 */
	public String getCtrlType() {
		return ctrlType;
	}
	
	/**
	 * Sets the ctrl type.
	 * 
	 * @param ctrlType
	 *            the new ctrl type
	 */
	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}
	
	/**
	 * Gets the tab id.
	 * 
	 * @return the tab id
	 */
	public Long getTabId() {
		return tabId;
	}
	
	/**
	 * Sets the tab id.
	 * 
	 * @param tabId
	 *            the new tab id
	 */
	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}
	
	/**
	 * Gets the col id.
	 * 
	 * @return the col id
	 */
	public Long getColId() {
		return colId;
	}
	
	/**
	 * Sets the col id.
	 * 
	 * @param colId
	 *            the new col id
	 */
	public void setColId(Long colId) {
		this.colId = colId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authCode == null) ? 0 : authCode.hashCode());
		result = prime * result
				+ ((authType == null) ? 0 : authType.hashCode());
		result = prime * result + ((colId == null) ? 0 : colId.hashCode());
		result = prime * result
				+ ((ctrlType == null) ? 0 : ctrlType.hashCode());
		result = prime * result + ((tabId == null) ? 0 : tabId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoGrantObjectModel other = (InfoGrantObjectModel) obj;
		if (authCode == null) {
			if (other.authCode != null)
				return false;
		} else if (!authCode.equals(other.authCode))
			return false;
		if (authType == null) {
			if (other.authType != null)
				return false;
		} else if (!authType.equals(other.authType))
			return false;
		if (colId == null) {
			if (other.colId != null)
				return false;
		} else if (!colId.equals(other.colId))
			return false;
		if (ctrlType == null) {
			if (other.ctrlType != null)
				return false;
		} else if (!ctrlType.equals(other.ctrlType))
			return false;
		if (tabId == null) {
			if (other.tabId != null)
				return false;
		} else if (!tabId.equals(other.tabId))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InfoGrantObjectModel [authType=" + authType + ", authCode="
				+ authCode + ", ctrlType=" + ctrlType + ", tabId=" + tabId
				+ ", colId=" + colId + "]";
	}
	
	
	
	
	
	
}
