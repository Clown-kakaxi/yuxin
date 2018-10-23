package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiOrgIssuebond entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG_ISSUEBOND")
public class MCiOrgIssuebond implements java.io.Serializable {

	// Fields

	private String issueBondId;
	private String custId;
	private String exchangeCountryCode;
	private String exchangeCode;
	private String exchangeName;
	private String bondType;
	private String bondKind;
	private String bondState;
	private BigDecimal bondTerm;
	private String bondName;
	private String bondCode;
	private String bondGrade;
	private String bondSeller;
	private String bondWarrantor;
	private String isMarket;
	private Date issueDate;
	private Double issueAmt;
	private String bondCurr;
	private String bondIntr;
	private String evalOrg;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiOrgIssuebond() {
	}

	/** minimal constructor */
	public MCiOrgIssuebond(String issueBondId) {
		this.issueBondId = issueBondId;
	}

	/** full constructor */
	public MCiOrgIssuebond(String issueBondId, String custId,
			String exchangeCountryCode, String exchangeCode,
			String exchangeName, String bondType, String bondKind,
			String bondState, BigDecimal bondTerm, String bondName,
			String bondCode, String bondGrade, String bondSeller,
			String bondWarrantor, String isMarket, Date issueDate,
			Double issueAmt, String bondCurr, String bondIntr, String evalOrg,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.issueBondId = issueBondId;
		this.custId = custId;
		this.exchangeCountryCode = exchangeCountryCode;
		this.exchangeCode = exchangeCode;
		this.exchangeName = exchangeName;
		this.bondType = bondType;
		this.bondKind = bondKind;
		this.bondState = bondState;
		this.bondTerm = bondTerm;
		this.bondName = bondName;
		this.bondCode = bondCode;
		this.bondGrade = bondGrade;
		this.bondSeller = bondSeller;
		this.bondWarrantor = bondWarrantor;
		this.isMarket = isMarket;
		this.issueDate = issueDate;
		this.issueAmt = issueAmt;
		this.bondCurr = bondCurr;
		this.bondIntr = bondIntr;
		this.evalOrg = evalOrg;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "ISSUE_BOND_ID", unique = true, nullable = false, length = 20)
	public String getIssueBondId() {
		return this.issueBondId;
	}

	public void setIssueBondId(String issueBondId) {
		this.issueBondId = issueBondId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "EXCHANGE_COUNTRY_CODE", length = 20)
	public String getExchangeCountryCode() {
		return this.exchangeCountryCode;
	}

	public void setExchangeCountryCode(String exchangeCountryCode) {
		this.exchangeCountryCode = exchangeCountryCode;
	}

	@Column(name = "EXCHANGE_CODE", length = 20)
	public String getExchangeCode() {
		return this.exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	@Column(name = "EXCHANGE_NAME", length = 80)
	public String getExchangeName() {
		return this.exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	@Column(name = "BOND_TYPE", length = 20)
	public String getBondType() {
		return this.bondType;
	}

	public void setBondType(String bondType) {
		this.bondType = bondType;
	}

	@Column(name = "BOND_KIND", length = 20)
	public String getBondKind() {
		return this.bondKind;
	}

	public void setBondKind(String bondKind) {
		this.bondKind = bondKind;
	}

	@Column(name = "BOND_STATE", length = 20)
	public String getBondState() {
		return this.bondState;
	}

	public void setBondState(String bondState) {
		this.bondState = bondState;
	}

	@Column(name = "BOND_TERM", precision = 22, scale = 0)
	public BigDecimal getBondTerm() {
		return this.bondTerm;
	}

	public void setBondTerm(BigDecimal bondTerm) {
		this.bondTerm = bondTerm;
	}

	@Column(name = "BOND_NAME", length = 80)
	public String getBondName() {
		return this.bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	@Column(name = "BOND_CODE", length = 32)
	public String getBondCode() {
		return this.bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	@Column(name = "BOND_GRADE", length = 20)
	public String getBondGrade() {
		return this.bondGrade;
	}

	public void setBondGrade(String bondGrade) {
		this.bondGrade = bondGrade;
	}

	@Column(name = "BOND_SELLER", length = 80)
	public String getBondSeller() {
		return this.bondSeller;
	}

	public void setBondSeller(String bondSeller) {
		this.bondSeller = bondSeller;
	}

	@Column(name = "BOND_WARRANTOR", length = 80)
	public String getBondWarrantor() {
		return this.bondWarrantor;
	}

	public void setBondWarrantor(String bondWarrantor) {
		this.bondWarrantor = bondWarrantor;
	}

	@Column(name = "IS_MARKET", length = 1)
	public String getIsMarket() {
		return this.isMarket;
	}

	public void setIsMarket(String isMarket) {
		this.isMarket = isMarket;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ISSUE_DATE", length = 7)
	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	@Column(name = "ISSUE_AMT", precision = 17)
	public Double getIssueAmt() {
		return this.issueAmt;
	}

	public void setIssueAmt(Double issueAmt) {
		this.issueAmt = issueAmt;
	}

	@Column(name = "BOND_CURR", length = 20)
	public String getBondCurr() {
		return this.bondCurr;
	}

	public void setBondCurr(String bondCurr) {
		this.bondCurr = bondCurr;
	}

	@Column(name = "BOND_INTR", length = 200)
	public String getBondIntr() {
		return this.bondIntr;
	}

	public void setBondIntr(String bondIntr) {
		this.bondIntr = bondIntr;
	}

	@Column(name = "EVAL_ORG", length = 40)
	public String getEvalOrg() {
		return this.evalOrg;
	}

	public void setEvalOrg(String evalOrg) {
		this.evalOrg = evalOrg;
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