package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxTabPriv entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_TAB_PRIV")
public class TxTabPriv implements java.io.Serializable {

	// Fields

	private Long tabPrivId;
	private String srcSysCd;
	private Long tabId;
	private String operType;
	private String priv;
	private Long priority;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxTabPriv() {
	}

	/** full constructor */
	public TxTabPriv(String srcSysCd, Long tabId, String operType,
			String priv, Long priority, String state, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.srcSysCd = srcSysCd;
		this.tabId = tabId;
		this.operType = operType;
		this.priv = priv;
		this.priority = priority;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "TAB_PRIV_ID", unique = true, nullable = false)
	public Long getTabPrivId() {
		return this.tabPrivId;
	}

	public void setTabPrivId(Long tabPrivId) {
		this.tabPrivId = tabPrivId;
	}

	@Column(name = "SRC_SYS_CD", length = 20)
	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	@Column(name = "TAB_ID", precision = 22)
	public Long getTabId() {
		return this.tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	@Column(name = "OPER_TYPE", length = 10)
	public String getOperType() {
		return this.operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	@Column(name = "PRIV", length = 1)
	public String getPriv() {
		return this.priv;
	}

	public void setPriv(String priv) {
		this.priv = priv;
	}

	@Column(name = "PRIORITY", precision = 22)
	public Long getPriority() {
		return this.priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "CREATE_TM", length = 11)
	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	@Column(name = "CREATE_USER", length = 20)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "UPDATE_TM", length = 11)
	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	@Column(name = "UPDATE_USER", length = 20)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}