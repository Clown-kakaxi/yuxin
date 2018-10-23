package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiCustrelSupplyChain entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_CUSTREL_SUPPLY_CHAIN")
public class MCiCustrelSupplyChain implements java.io.Serializable {

	// Fields

	private String custRelId;
	private String isMainProvider;
	private String isMainSaler;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiCustrelSupplyChain() {
	}

	/** minimal constructor */
	public MCiCustrelSupplyChain(String custRelId) {
		this.custRelId = custRelId;
	}

	/** full constructor */
	public MCiCustrelSupplyChain(String custRelId, String isMainProvider,
			String isMainSaler, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custRelId = custRelId;
		this.isMainProvider = isMainProvider;
		this.isMainSaler = isMainSaler;
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

	@Column(name = "IS_MAIN_PROVIDER", length = 1)
	public String getIsMainProvider() {
		return this.isMainProvider;
	}

	public void setIsMainProvider(String isMainProvider) {
		this.isMainProvider = isMainProvider;
	}

	@Column(name = "IS_MAIN_SALER", length = 1)
	public String getIsMainSaler() {
		return this.isMainSaler;
	}

	public void setIsMainSaler(String isMainSaler) {
		this.isMainSaler = isMainSaler;
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