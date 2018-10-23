package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiIdentityVerify entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_IDENTITY_VERIFY")
public class MCiIdentityVerify implements java.io.Serializable {

	// Fields

	private String custId;
	private String verifyStat;
	private String verifyResult;
	private String reason;
	private String dealWay;
	private String verifyBranchNo;
	private String verifyTellerNo;
	private Date verifyDate;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiIdentityVerify() {
	}

	/** minimal constructor */
	public MCiIdentityVerify(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiIdentityVerify(String custId, String verifyStat,
			String verifyResult, String reason, String dealWay,
			String verifyBranchNo, String verifyTellerNo, Date verifyDate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custId = custId;
		this.verifyStat = verifyStat;
		this.verifyResult = verifyResult;
		this.reason = reason;
		this.dealWay = dealWay;
		this.verifyBranchNo = verifyBranchNo;
		this.verifyTellerNo = verifyTellerNo;
		this.verifyDate = verifyDate;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "CUST_ID", unique = true, nullable = false, length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "VERIFY_STAT", length = 20)
	public String getVerifyStat() {
		return this.verifyStat;
	}

	public void setVerifyStat(String verifyStat) {
		this.verifyStat = verifyStat;
	}

	@Column(name = "VERIFY_RESULT", length = 20)
	public String getVerifyResult() {
		return this.verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	@Column(name = "REASON", length = 80)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "DEAL_WAY", length = 80)
	public String getDealWay() {
		return this.dealWay;
	}

	public void setDealWay(String dealWay) {
		this.dealWay = dealWay;
	}

	@Column(name = "VERIFY_BRANCH_NO", length = 20)
	public String getVerifyBranchNo() {
		return this.verifyBranchNo;
	}

	public void setVerifyBranchNo(String verifyBranchNo) {
		this.verifyBranchNo = verifyBranchNo;
	}

	@Column(name = "VERIFY_TELLER_NO", length = 20)
	public String getVerifyTellerNo() {
		return this.verifyTellerNo;
	}

	public void setVerifyTellerNo(String verifyTellerNo) {
		this.verifyTellerNo = verifyTellerNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VERIFY_DATE", length = 7)
	public Date getVerifyDate() {
		return this.verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
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