package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiCustrelCorp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_CUSTREL_CORP")
public class MCiCustrelCorp implements java.io.Serializable {

	// Fields

	private String custRelId;
	private String corpItemName;
	private String corpItemDesc;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiCustrelCorp() {
	}

	/** minimal constructor */
	public MCiCustrelCorp(String custRelId) {
		this.custRelId = custRelId;
	}

	/** full constructor */
	public MCiCustrelCorp(String custRelId, String corpItemName,
			String corpItemDesc, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custRelId = custRelId;
		this.corpItemName = corpItemName;
		this.corpItemDesc = corpItemDesc;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "CUST_REL_ID", unique = true, nullable = false, length = 20)
	public String getCustRelId() {
		return this.custRelId;
	}

	public void setCustRelId(String custRelId) {
		this.custRelId = custRelId;
	}

	@Column(name = "CORP_ITEM_NAME", length = 80)
	public String getCorpItemName() {
		return this.corpItemName;
	}

	public void setCorpItemName(String corpItemName) {
		this.corpItemName = corpItemName;
	}

	@Column(name = "CORP_ITEM_DESC")
	public String getCorpItemDesc() {
		return this.corpItemDesc;
	}

	public void setCorpItemDesc(String corpItemDesc) {
		this.corpItemDesc = corpItemDesc;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}