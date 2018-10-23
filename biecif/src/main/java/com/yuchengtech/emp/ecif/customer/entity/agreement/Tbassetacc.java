package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the TBASSETACC database table.
 * 
 */
@Entity
@Table(name="TBASSETACC")
public class Tbassetacc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="ASSET_ACC", length=20)
	private String assetAcc;

	@Column(name="BANK_ACC", length=32)
	private String bankAcc;

	@Column(name="BRANCH_NO", length=16)
	private String branchNo;

	@Column(name="CLIENT_MANAGER", length=16)
	private String clientManager;

	@Column(name="CLIENT_NO", length=20)
	private String clientNo;

	@Column(name="CLIENT_TYPE", length=1)
	private String clientType;

	@Column(name="DIV_MODE", length=1)
	private String divMode;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="OPEN_DATE", length=20)
	private String openDate;

	@Column(name="OPEN_FLAG", length=1)
	private String openFlag;

	@Column(name="PRD_TYPE", length=1)
	private String prdType;

	@Column(name="SEND_FREQ", length=1)
	private String sendFreq;

	@Column(name="SEND_MODE", length=8)
	private String sendMode;

	@Column(name="STATUS",length=1)
	private String status;

	@Column(name="TA_CLIENT", length=20)
	private String taClient;

	@Column(name="TA_CODE", length=6)
	private String taCode;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Tbassetacc() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getAssetAcc() {
		return this.assetAcc;
	}

	public void setAssetAcc(String assetAcc) {
		this.assetAcc = assetAcc;
	}

	public String getBankAcc() {
		return this.bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	public String getBranchNo() {
		return this.branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getClientManager() {
		return this.clientManager;
	}

	public void setClientManager(String clientManager) {
		this.clientManager = clientManager;
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

	public String getDivMode() {
		return this.divMode;
	}

	public void setDivMode(String divMode) {
		this.divMode = divMode;
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

	public String getOpenDate() {
		return this.openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getOpenFlag() {
		return this.openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public String getPrdType() {
		return this.prdType;
	}

	public void setPrdType(String prdType) {
		this.prdType = prdType;
	}

	public String getSendFreq() {
		return this.sendFreq;
	}

	public void setSendFreq(String sendFreq) {
		this.sendFreq = sendFreq;
	}

	public String getSendMode() {
		return this.sendMode;
	}

	public void setSendMode(String sendMode) {
		this.sendMode = sendMode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaClient() {
		return this.taClient;
	}

	public void setTaClient(String taClient) {
		this.taClient = taClient;
	}

	public String getTaCode() {
		return this.taCode;
	}

	public void setTaCode(String taCode) {
		this.taCode = taCode;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}