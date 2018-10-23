package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiPerIncomePay entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_INCOME_PAY")
public class MCiPerIncomePay implements java.io.Serializable {

	// Fields

	private String ioId;
	private String custId;
	private String ioFreq;
	private String ioType;
	private String detailType;
	private String curr;
	private Double amt;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiPerIncomePay() {
	}

	/** minimal constructor */
	public MCiPerIncomePay(String ioId) {
		this.ioId = ioId;
	}

	/** full constructor */
	public MCiPerIncomePay(String ioId, String custId, String ioFreq,
			String ioType, String detailType, String curr, Double amt,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.ioId = ioId;
		this.custId = custId;
		this.ioFreq = ioFreq;
		this.ioType = ioType;
		this.detailType = detailType;
		this.curr = curr;
		this.amt = amt;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "IO_ID", unique = true, nullable = false, length = 20)
	public String getIoId() {
		return this.ioId;
	}

	public void setIoId(String ioId) {
		this.ioId = ioId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "IO_FREQ", length = 20)
	public String getIoFreq() {
		return this.ioFreq;
	}

	public void setIoFreq(String ioFreq) {
		this.ioFreq = ioFreq;
	}

	@Column(name = "IO_TYPE", length = 20)
	public String getIoType() {
		return this.ioType;
	}

	public void setIoType(String ioType) {
		this.ioType = ioType;
	}

	@Column(name = "DETAIL_TYPE", length = 20)
	public String getDetailType() {
		return this.detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	@Column(name = "CURR", length = 20)
	public String getCurr() {
		return this.curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	@Column(name = "AMT", precision = 17)
	public Double getAmt() {
		return this.amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
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