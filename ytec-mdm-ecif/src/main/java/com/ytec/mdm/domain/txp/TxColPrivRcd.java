package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxColPrivRcd entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_COL_PRIV_RCD")
public class TxColPrivRcd implements java.io.Serializable {

	// Fields

	private Long colPrivRcdId;
	private Long tabId;
	private Long colId;
	private Long pkId;
	private String srcSysCd;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxColPrivRcd() {
	}

	/** full constructor */
	public TxColPrivRcd(Long tabId, Long colId, Long pkId,
			String srcSysCd, String state, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.tabId = tabId;
		this.colId = colId;
		this.pkId = pkId;
		this.srcSysCd = srcSysCd;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
	@Column(name = "COL_PRIV_RCD_ID", unique = true, nullable = false)
	public Long getColPrivRcdId() {
		return this.colPrivRcdId;
	}

	public void setColPrivRcdId(Long colPrivRcdId) {
		this.colPrivRcdId = colPrivRcdId;
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

	@Column(name = "PK_ID")
	public Long getPkId() {
		return this.pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	@Column(name = "SRC_SYS_CD", length = 20)
	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
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