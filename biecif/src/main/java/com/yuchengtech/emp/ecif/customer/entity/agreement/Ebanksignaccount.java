package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the EBANKSIGNACCOUNT database table.
 * 
 */
@Entity
@Table(name="EBANKSIGNACCOUNT")
public class Ebanksignaccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="BANK_ACC", length=32)
	private String bankAcc;

	@Column(name="BANK_NO", length=20)
	private String bankNo;

	@Column(name="BRANCH_NO", length=16)
	private String branchNo;

	@Column(name="CLIENT_NO", length=20)
	private String clientNo;

	@Column(name="CLIENT_TYPE", length=20)
	private String clientType;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="OPEN_BRANCH", length=16)
	private String openBranch;

	@Column(name="SIGNOFF_DATE", length=20)
	private String signoffDate;

	@Column(name="STATUS",length=1)
	private String status;

	@Column(name="TRANS_DATE",length=20)
	private String transDate;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Ebanksignaccount() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getBankAcc() {
		return this.bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	public String getBankNo() {
		return this.bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBranchNo() {
		return this.branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getClientNo() {
		return this.clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getClientType() {
		return this.clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getOpenBranch() {
		return this.openBranch;
	}

	public void setOpenBranch(String openBranch) {
		this.openBranch = openBranch;
	}

	public String getSignoffDate() {
		return this.signoffDate;
	}

	public void setSignoffDate(String signoffDate) {
		this.signoffDate = signoffDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransDate() {
		return this.transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}