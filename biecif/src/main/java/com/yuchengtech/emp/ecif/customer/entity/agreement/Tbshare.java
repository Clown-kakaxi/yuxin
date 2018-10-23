package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the TBSHARE database table.
 * 
 */
@Entity
@Table(name="TBSHARE")
public class Tbshare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="APPEND_FLAG", length=1)
	private String appendFlag;

	@Column(name="ASSET_ACC", length=20)
	private String assetAcc;

	@Column(name="CLIENT_NO", length=20)
	private String clientNo;

	@Column(name="CLIENT_TYPE", length=20)
	private String clientType;

	@Column(name="COST",precision=17, scale=2)
	private BigDecimal cost;

	@Column(name="DIV_MODE", length=2)
	private String divMode;

	@Column(name="DIV_RATE", precision=5, scale=2)
	private BigDecimal divRate;

	@Column(name="FROZEN_VOL", precision=17, scale=2)
	private BigDecimal frozenVol;

	@Column(name="GROUP_VOL", precision=17, scale=2)
	private BigDecimal groupVol;

	@Column(name="INCOME",precision=17, scale=2)
	private BigDecimal income;

	@Column(name="INTERNAL_CODE", length=12)
	private String internalCode;

	@Column(name="LAST_DATE", length=20)
	private String lastDate;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="LONG_FROZEN_VOL", precision=17, scale=2)
	private BigDecimal longFrozenVol;

	@Column(name="OPEN_BRANCH", length=9)
	private String openBranch;

	@Column(name="OTHER_FROZEN", precision=17, scale=2)
	private BigDecimal otherFrozen;

	@Column(name="PRD_CODE", length=6)
	private String prdCode;

	@Column(name="SELLER_CODE", length=3)
	private String sellerCode;

	@Column(name="TA_CODE", length=6)
	private String taCode;

	@Column(name="TOT_INCOME", precision=17, scale=2)
	private BigDecimal totIncome;

	@Column(name="TOT_VOL", precision=17, scale=2)
	private BigDecimal totVol;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="YSTDY_TOT_VOL", precision=17, scale=2)
	private BigDecimal ystdyTotVol;

    public Tbshare() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getAppendFlag() {
		return this.appendFlag;
	}

	public void setAppendFlag(String appendFlag) {
		this.appendFlag = appendFlag;
	}

	public String getAssetAcc() {
		return this.assetAcc;
	}

	public void setAssetAcc(String assetAcc) {
		this.assetAcc = assetAcc;
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

	public BigDecimal getCost() {
		return this.cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getDivMode() {
		return this.divMode;
	}

	public void setDivMode(String divMode) {
		this.divMode = divMode;
	}

	public BigDecimal getDivRate() {
		return this.divRate;
	}

	public void setDivRate(BigDecimal divRate) {
		this.divRate = divRate;
	}

	public BigDecimal getFrozenVol() {
		return this.frozenVol;
	}

	public void setFrozenVol(BigDecimal frozenVol) {
		this.frozenVol = frozenVol;
	}

	public BigDecimal getGroupVol() {
		return this.groupVol;
	}

	public void setGroupVol(BigDecimal groupVol) {
		this.groupVol = groupVol;
	}

	public BigDecimal getIncome() {
		return this.income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getInternalCode() {
		return this.internalCode;
	}

	public void setInternalCode(String internalCode) {
		this.internalCode = internalCode;
	}

	public String getLastDate() {
		return this.lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
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

	public BigDecimal getLongFrozenVol() {
		return this.longFrozenVol;
	}

	public void setLongFrozenVol(BigDecimal longFrozenVol) {
		this.longFrozenVol = longFrozenVol;
	}

	public String getOpenBranch() {
		return this.openBranch;
	}

	public void setOpenBranch(String openBranch) {
		this.openBranch = openBranch;
	}

	public BigDecimal getOtherFrozen() {
		return this.otherFrozen;
	}

	public void setOtherFrozen(BigDecimal otherFrozen) {
		this.otherFrozen = otherFrozen;
	}

	public String getPrdCode() {
		return this.prdCode;
	}

	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}

	public String getSellerCode() {
		return this.sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getTaCode() {
		return this.taCode;
	}

	public void setTaCode(String taCode) {
		this.taCode = taCode;
	}

	public BigDecimal getTotIncome() {
		return this.totIncome;
	}

	public void setTotIncome(BigDecimal totIncome) {
		this.totIncome = totIncome;
	}

	public BigDecimal getTotVol() {
		return this.totVol;
	}

	public void setTotVol(BigDecimal totVol) {
		this.totVol = totVol;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public BigDecimal getYstdyTotVol() {
		return this.ystdyTotVol;
	}

	public void setYstdyTotVol(BigDecimal ystdyTotVol) {
		this.ystdyTotVol = ystdyTotVol;
	}

}