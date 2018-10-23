/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.base.bo
 * @�ļ�����InfoGrantObjectModel.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-9:57:24
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

package com.ytec.mdm.base.bo;


/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�InfoGrantObjectModel
 * @����������Ϣ��Ȩģ��
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����9:58:30   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����9:58:30
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class InfoGrantObjectModel {
	
	/**
	 * The auth type.
	 * 
	 * @��������:��Ȩ��������
	 */
	private String authType;
	
	/**
	 * The auth code.
	 * 
	 * @��������:��Ȩ�������
	 */
	private String authCode;
	
	/**
	 * The ctrl type.
	 * 
	 * @��������:��Ȩ����
	 */
	private String ctrlType;
	
	/**
	 * The tab id.
	 * 
	 * @��������:��ID
	 */
	private Long tabId;			
	
	/**
	 * The col id.
	 * 
	 * @��������:�ֶ�ID
	 */
	private Long colId;
	
	
	
	/**
	 *@���캯�� 
	 */
	public InfoGrantObjectModel() {
		
	}
	
	
	/**
	 *@���캯�� 
	 * @param authType  ��Ȩ����
	 * @param authCode  ��Ȩ��
	 * @param ctrlType  ��������
	 * @param tabId     ��ID
	 * @param colId     �ֶ�ID
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
