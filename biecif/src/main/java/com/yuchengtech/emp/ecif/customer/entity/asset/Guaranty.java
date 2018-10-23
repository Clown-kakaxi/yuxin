package com.yuchengtech.emp.ecif.customer.entity.asset;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the GUARANTY database table.
 * 
 */
@Entity
@Table(name="GUARANTY")
public class Guaranty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="BANK_ID", length=9)
	private String bankId;

	@Column(name="EVAL_CORP_NAME", length=60)
	private String evalCorpName;

	@Column(name="GUARANTY_CALSS", length=20)
	private String guarantyCalss;

	@Column(name="GUARANTY_CERT_NO", length=200)
	private String guarantyCertNo;

	@Column(name="GUARANTY_CERT_TYPE", length=20)
	private String guarantyCertType;

	@Column(name="GUARANTY_DESC", length=100)
	private String guarantyDesc;

	@Column(name="GUARANTY_ESTIM_KIND", length=20)
	private String guarantyEstimKind;

	@Column(name="GUARANTY_EVAL_VALUE", precision=12, scale=2)
	private BigDecimal guarantyEvalValue;

	@Column(name="GUARANTY_NAME", length=60)
	private String guarantyName;

	@Column(name="GUARANTY_NUM")
	private Long guarantyNum;

	@Column(name="GUARANTY_NUM_UNIT", length=20)
	private String guarantyNumUnit;

	@Column(name="GUARANTY_RATE", precision=5, scale=2)
	private BigDecimal guarantyRate;

	@Column(name="GUARANTY_STAT", length=20)
	private String guarantyStat;

	@Column(name="GUARANTY_TRUN_CASH", length=20)
	private String guarantyTrunCash;

	@Column(name="GUARANTY_TYPE", length=20)
	private String guarantyType;

	@Column(name="GUARANTY_VALUE", precision=17, scale=2)
	private BigDecimal guarantyValue;

	@Column(name="IS_INSU_FLAG", length=1)
	private String isInsuFlag;

	@Column(name="IS_NOTA_FLAG", length=1)
	private String isNotaFlag;

	@Column(name="IS_PART_OWNER_FLAG", length=1)
	private String isPartOwnerFlag;

	@Column(name="IS_REGI_FLAG", length=1)
	private String isRegiFlag;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM",length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="OPER_ID", length=20)
	private String operId;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Guaranty() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public String getBankId() {
		return this.bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getEvalCorpName() {
		return this.evalCorpName;
	}

	public void setEvalCorpName(String evalCorpName) {
		this.evalCorpName = evalCorpName;
	}

	public String getGuarantyCalss() {
		return this.guarantyCalss;
	}

	public void setGuarantyCalss(String guarantyCalss) {
		this.guarantyCalss = guarantyCalss;
	}

	public String getGuarantyCertNo() {
		return this.guarantyCertNo;
	}

	public void setGuarantyCertNo(String guarantyCertNo) {
		this.guarantyCertNo = guarantyCertNo;
	}

	public String getGuarantyCertType() {
		return this.guarantyCertType;
	}

	public void setGuarantyCertType(String guarantyCertType) {
		this.guarantyCertType = guarantyCertType;
	}

	public String getGuarantyDesc() {
		return this.guarantyDesc;
	}

	public void setGuarantyDesc(String guarantyDesc) {
		this.guarantyDesc = guarantyDesc;
	}

	public String getGuarantyEstimKind() {
		return this.guarantyEstimKind;
	}

	public void setGuarantyEstimKind(String guarantyEstimKind) {
		this.guarantyEstimKind = guarantyEstimKind;
	}

	public BigDecimal getGuarantyEvalValue() {
		return this.guarantyEvalValue;
	}

	public void setGuarantyEvalValue(BigDecimal guarantyEvalValue) {
		this.guarantyEvalValue = guarantyEvalValue;
	}

	public String getGuarantyName() {
		return this.guarantyName;
	}

	public void setGuarantyName(String guarantyName) {
		this.guarantyName = guarantyName;
	}

	public Long getGuarantyNum() {
		return this.guarantyNum;
	}

	public void setGuarantyNum(Long guarantyNum) {
		this.guarantyNum = guarantyNum;
	}

	public String getGuarantyNumUnit() {
		return this.guarantyNumUnit;
	}

	public void setGuarantyNumUnit(String guarantyNumUnit) {
		this.guarantyNumUnit = guarantyNumUnit;
	}

	public BigDecimal getGuarantyRate() {
		return this.guarantyRate;
	}

	public void setGuarantyRate(BigDecimal guarantyRate) {
		this.guarantyRate = guarantyRate;
	}

	public String getGuarantyStat() {
		return this.guarantyStat;
	}

	public void setGuarantyStat(String guarantyStat) {
		this.guarantyStat = guarantyStat;
	}

	public String getGuarantyTrunCash() {
		return this.guarantyTrunCash;
	}

	public void setGuarantyTrunCash(String guarantyTrunCash) {
		this.guarantyTrunCash = guarantyTrunCash;
	}

	public String getGuarantyType() {
		return this.guarantyType;
	}

	public void setGuarantyType(String guarantyType) {
		this.guarantyType = guarantyType;
	}

	public BigDecimal getGuarantyValue() {
		return this.guarantyValue;
	}

	public void setGuarantyValue(BigDecimal guarantyValue) {
		this.guarantyValue = guarantyValue;
	}

	public String getIsInsuFlag() {
		return this.isInsuFlag;
	}

	public void setIsInsuFlag(String isInsuFlag) {
		this.isInsuFlag = isInsuFlag;
	}

	public String getIsNotaFlag() {
		return this.isNotaFlag;
	}

	public void setIsNotaFlag(String isNotaFlag) {
		this.isNotaFlag = isNotaFlag;
	}

	public String getIsPartOwnerFlag() {
		return this.isPartOwnerFlag;
	}

	public void setIsPartOwnerFlag(String isPartOwnerFlag) {
		this.isPartOwnerFlag = isPartOwnerFlag;
	}

	public String getIsRegiFlag() {
		return this.isRegiFlag;
	}

	public void setIsRegiFlag(String isRegiFlag) {
		this.isRegiFlag = isRegiFlag;
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

	public String getOperId() {
		return this.operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}