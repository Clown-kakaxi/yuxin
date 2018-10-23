package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxColPriv entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_COL_PRIV")
public class TxColPriv implements java.io.Serializable {

	// Fields

	private Long colPrivId;
	private String srcSysCd;
	private Long tabId;
	private Long colId;
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
	public TxColPriv() {
	}

	/** full constructor */
	public TxColPriv(String srcSysCd, Long tabId, Long colId,
			String operType, String priv, Long priority, String state,
			Timestamp createTm, String createUser, Timestamp updateTm,
			String updateUser) {
		this.srcSysCd = srcSysCd;
		this.tabId = tabId;
		this.colId = colId;
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
	@Column(name = "COL_PRIV_ID", unique = true, nullable = false)
	public Long getColPrivId() {
		return this.colPrivId;
	}

	public void setColPrivId(Long colPrivId) {
		this.colPrivId = colPrivId;
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

	@Column(name = "COL_ID", precision = 22)
	public Long getColId() {
		return this.colId;
	}

	public void setColId(Long colId) {
		this.colId = colId;
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