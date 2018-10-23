package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the CREDITCONTRACT database table.
 * 
 */
@Entity
@Table(name="CREDITCONTRACT")
public class Creditcontract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="BRANCH_NO", length=9)
	private String branchNo;

	@Column(name="BUSINESS_TYPE", length=20)
	private String businessType;

	@Column(name="CONTR_END_DATE", length=20)
	private String contrEndDate;

	@Column(name="CONTR_START_DATE", length=20)
	private String contrStartDate;

	@Column(name="CREDIT_BAL", precision=17, scale=2)
	private BigDecimal creditBal;

	@Column(name="CREDIT_CONTR_ID")
	private Long creditContrId;

	@Column(name="CREDIT_CONTR_NO", length=32)
	private String creditContrNo;

	@Column(name="CREDIT_CURR", length=20)
	private String creditCurr;

	@Column(name="CREDIT_KIND", length=20)
	private String creditKind;

	@Column(name="CREDIT_LIMIT", precision=17, scale=2)
	private BigDecimal creditLimit;

	@Column(name="CREDIT_STAT", length=20)
	private String creditStat;

	@Column(name="CREDIT_TYPE", length=20)
	private String creditType;

	@Column(name="IS_LOOP", length=1)
	private String isLoop;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="LINEID",length=32)
	private String lineid;

	@Column(name="TELLER_NO", length=20)
	private String tellerNo;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="USABLE_CREDIT", precision=17, scale=2)
	private BigDecimal usableCredit;

	@Column(name="USED_CREDIT", precision=17, scale=2)
	private BigDecimal usedCredit;

    public Creditcontract() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getBranchNo() {
		return this.branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getContrEndDate() {
		return this.contrEndDate;
	}

	public void setContrEndDate(String contrEndDate) {
		this.contrEndDate = contrEndDate;
	}

	public String getContrStartDate() {
		return this.contrStartDate;
	}

	public void setContrStartDate(String contrStartDate) {
		this.contrStartDate = contrStartDate;
	}

	public BigDecimal getCreditBal() {
		return this.creditBal;
	}

	public void setCreditBal(BigDecimal creditBal) {
		this.creditBal = creditBal;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCreditContrId() {
		return this.creditContrId;
	}

	public void setCreditContrId(Long creditContrId) {
		this.creditContrId = creditContrId;
	}

	public String getCreditContrNo() {
		return this.creditContrNo;
	}

	public void setCreditContrNo(String creditContrNo) {
		this.creditContrNo = creditContrNo;
	}

	public String getCreditCurr() {
		return this.creditCurr;
	}

	public void setCreditCurr(String creditCurr) {
		this.creditCurr = creditCurr;
	}

	public String getCreditKind() {
		return this.creditKind;
	}

	public void setCreditKind(String creditKind) {
		this.creditKind = creditKind;
	}

	public BigDecimal getCreditLimit() {
		return this.creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getCreditStat() {
		return this.creditStat;
	}

	public void setCreditStat(String creditStat) {
		this.creditStat = creditStat;
	}

	public String getCreditType() {
		return this.creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public String getIsLoop() {
		return this.isLoop;
	}

	public void setIsLoop(String isLoop) {
		this.isLoop = isLoop;
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

	public String getLineid() {
		return this.lineid;
	}

	public void setLineid(String lineid) {
		this.lineid = lineid;
	}

	public String getTellerNo() {
		return this.tellerNo;
	}

	public void setTellerNo(String tellerNo) {
		this.tellerNo = tellerNo;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public BigDecimal getUsableCredit() {
		return this.usableCredit;
	}

	public void setUsableCredit(BigDecimal usableCredit) {
		this.usableCredit = usableCredit;
	}

	public BigDecimal getUsedCredit() {
		return this.usedCredit;
	}

	public void setUsedCredit(BigDecimal usedCredit) {
		this.usedCredit = usedCredit;
	}

}