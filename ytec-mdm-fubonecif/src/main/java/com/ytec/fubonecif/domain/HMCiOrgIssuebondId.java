package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiOrgIssuebondId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgIssuebondId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiOrgIssuebondId() {
	}

	/** minimal constructor */
	public HMCiOrgIssuebondId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgIssuebondId(String issueBondId, String custId,
			String exchangeCountryCode, String exchangeCode,
			String exchangeName, String bondType, String bondKind,
			String bondState, BigDecimal bondTerm, String bondName,
			String bondCode, String bondGrade, String bondSeller,
			String bondWarrantor, String isMarket, Date issueDate,
			Double issueAmt, String bondCurr, String bondIntr, String evalOrg,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "ISSUE_BOND_ID", length = 20)
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

	@Column(name = "HIS_OPER_SYS", length = 20)
	public String getHisOperSys() {
		return this.hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	@Column(name = "HIS_OPER_TYPE", length = 2)
	public String getHisOperType() {
		return this.hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	@Column(name = "HIS_DATA_DATE", length = 10)
	public String getHisDataDate() {
		return this.hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HMCiOrgIssuebondId))
			return false;
		HMCiOrgIssuebondId castOther = (HMCiOrgIssuebondId) other;

		return ((this.getIssueBondId() == castOther.getIssueBondId()) || (this
				.getIssueBondId() != null
				&& castOther.getIssueBondId() != null && this.getIssueBondId()
				.equals(castOther.getIssueBondId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getExchangeCountryCode() == castOther
						.getExchangeCountryCode()) || (this
						.getExchangeCountryCode() != null
						&& castOther.getExchangeCountryCode() != null && this
						.getExchangeCountryCode().equals(
								castOther.getExchangeCountryCode())))
				&& ((this.getExchangeCode() == castOther.getExchangeCode()) || (this
						.getExchangeCode() != null
						&& castOther.getExchangeCode() != null && this
						.getExchangeCode().equals(castOther.getExchangeCode())))
				&& ((this.getExchangeName() == castOther.getExchangeName()) || (this
						.getExchangeName() != null
						&& castOther.getExchangeName() != null && this
						.getExchangeName().equals(castOther.getExchangeName())))
				&& ((this.getBondType() == castOther.getBondType()) || (this
						.getBondType() != null
						&& castOther.getBondType() != null && this
						.getBondType().equals(castOther.getBondType())))
				&& ((this.getBondKind() == castOther.getBondKind()) || (this
						.getBondKind() != null
						&& castOther.getBondKind() != null && this
						.getBondKind().equals(castOther.getBondKind())))
				&& ((this.getBondState() == castOther.getBondState()) || (this
						.getBondState() != null
						&& castOther.getBondState() != null && this
						.getBondState().equals(castOther.getBondState())))
				&& ((this.getBondTerm() == castOther.getBondTerm()) || (this
						.getBondTerm() != null
						&& castOther.getBondTerm() != null && this
						.getBondTerm().equals(castOther.getBondTerm())))
				&& ((this.getBondName() == castOther.getBondName()) || (this
						.getBondName() != null
						&& castOther.getBondName() != null && this
						.getBondName().equals(castOther.getBondName())))
				&& ((this.getBondCode() == castOther.getBondCode()) || (this
						.getBondCode() != null
						&& castOther.getBondCode() != null && this
						.getBondCode().equals(castOther.getBondCode())))
				&& ((this.getBondGrade() == castOther.getBondGrade()) || (this
						.getBondGrade() != null
						&& castOther.getBondGrade() != null && this
						.getBondGrade().equals(castOther.getBondGrade())))
				&& ((this.getBondSeller() == castOther.getBondSeller()) || (this
						.getBondSeller() != null
						&& castOther.getBondSeller() != null && this
						.getBondSeller().equals(castOther.getBondSeller())))
				&& ((this.getBondWarrantor() == castOther.getBondWarrantor()) || (this
						.getBondWarrantor() != null
						&& castOther.getBondWarrantor() != null && this
						.getBondWarrantor()
						.equals(castOther.getBondWarrantor())))
				&& ((this.getIsMarket() == castOther.getIsMarket()) || (this
						.getIsMarket() != null
						&& castOther.getIsMarket() != null && this
						.getIsMarket().equals(castOther.getIsMarket())))
				&& ((this.getIssueDate() == castOther.getIssueDate()) || (this
						.getIssueDate() != null
						&& castOther.getIssueDate() != null && this
						.getIssueDate().equals(castOther.getIssueDate())))
				&& ((this.getIssueAmt() == castOther.getIssueAmt()) || (this
						.getIssueAmt() != null
						&& castOther.getIssueAmt() != null && this
						.getIssueAmt().equals(castOther.getIssueAmt())))
				&& ((this.getBondCurr() == castOther.getBondCurr()) || (this
						.getBondCurr() != null
						&& castOther.getBondCurr() != null && this
						.getBondCurr().equals(castOther.getBondCurr())))
				&& ((this.getBondIntr() == castOther.getBondIntr()) || (this
						.getBondIntr() != null
						&& castOther.getBondIntr() != null && this
						.getBondIntr().equals(castOther.getBondIntr())))
				&& ((this.getEvalOrg() == castOther.getEvalOrg()) || (this
						.getEvalOrg() != null
						&& castOther.getEvalOrg() != null && this.getEvalOrg()
						.equals(castOther.getEvalOrg())))
				&& ((this.getLastUpdateSys() == castOther.getLastUpdateSys()) || (this
						.getLastUpdateSys() != null
						&& castOther.getLastUpdateSys() != null && this
						.getLastUpdateSys()
						.equals(castOther.getLastUpdateSys())))
				&& ((this.getLastUpdateUser() == castOther.getLastUpdateUser()) || (this
						.getLastUpdateUser() != null
						&& castOther.getLastUpdateUser() != null && this
						.getLastUpdateUser().equals(
								castOther.getLastUpdateUser())))
				&& ((this.getLastUpdateTm() == castOther.getLastUpdateTm()) || (this
						.getLastUpdateTm() != null
						&& castOther.getLastUpdateTm() != null && this
						.getLastUpdateTm().equals(castOther.getLastUpdateTm())))
				&& ((this.getTxSeqNo() == castOther.getTxSeqNo()) || (this
						.getTxSeqNo() != null
						&& castOther.getTxSeqNo() != null && this.getTxSeqNo()
						.equals(castOther.getTxSeqNo())))
				&& ((this.getHisOperSys() == castOther.getHisOperSys()) || (this
						.getHisOperSys() != null
						&& castOther.getHisOperSys() != null && this
						.getHisOperSys().equals(castOther.getHisOperSys())))
				&& ((this.getHisOperType() == castOther.getHisOperType()) || (this
						.getHisOperType() != null
						&& castOther.getHisOperType() != null && this
						.getHisOperType().equals(castOther.getHisOperType())))
				&& ((this.getHisOperTime() == castOther.getHisOperTime()) || (this
						.getHisOperTime() != null
						&& castOther.getHisOperTime() != null && this
						.getHisOperTime().equals(castOther.getHisOperTime())))
				&& ((this.getHisDataDate() == castOther.getHisDataDate()) || (this
						.getHisDataDate() != null
						&& castOther.getHisDataDate() != null && this
						.getHisDataDate().equals(castOther.getHisDataDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIssueBondId() == null ? 0 : this.getIssueBondId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getExchangeCountryCode() == null ? 0 : this
						.getExchangeCountryCode().hashCode());
		result = 37
				* result
				+ (getExchangeCode() == null ? 0 : this.getExchangeCode()
						.hashCode());
		result = 37
				* result
				+ (getExchangeName() == null ? 0 : this.getExchangeName()
						.hashCode());
		result = 37 * result
				+ (getBondType() == null ? 0 : this.getBondType().hashCode());
		result = 37 * result
				+ (getBondKind() == null ? 0 : this.getBondKind().hashCode());
		result = 37 * result
				+ (getBondState() == null ? 0 : this.getBondState().hashCode());
		result = 37 * result
				+ (getBondTerm() == null ? 0 : this.getBondTerm().hashCode());
		result = 37 * result
				+ (getBondName() == null ? 0 : this.getBondName().hashCode());
		result = 37 * result
				+ (getBondCode() == null ? 0 : this.getBondCode().hashCode());
		result = 37 * result
				+ (getBondGrade() == null ? 0 : this.getBondGrade().hashCode());
		result = 37
				* result
				+ (getBondSeller() == null ? 0 : this.getBondSeller()
						.hashCode());
		result = 37
				* result
				+ (getBondWarrantor() == null ? 0 : this.getBondWarrantor()
						.hashCode());
		result = 37 * result
				+ (getIsMarket() == null ? 0 : this.getIsMarket().hashCode());
		result = 37 * result
				+ (getIssueDate() == null ? 0 : this.getIssueDate().hashCode());
		result = 37 * result
				+ (getIssueAmt() == null ? 0 : this.getIssueAmt().hashCode());
		result = 37 * result
				+ (getBondCurr() == null ? 0 : this.getBondCurr().hashCode());
		result = 37 * result
				+ (getBondIntr() == null ? 0 : this.getBondIntr().hashCode());
		result = 37 * result
				+ (getEvalOrg() == null ? 0 : this.getEvalOrg().hashCode());
		result = 37
				* result
				+ (getLastUpdateSys() == null ? 0 : this.getLastUpdateSys()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateUser() == null ? 0 : this.getLastUpdateUser()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateTm() == null ? 0 : this.getLastUpdateTm()
						.hashCode());
		result = 37 * result
				+ (getTxSeqNo() == null ? 0 : this.getTxSeqNo().hashCode());
		result = 37
				* result
				+ (getHisOperSys() == null ? 0 : this.getHisOperSys()
						.hashCode());
		result = 37
				* result
				+ (getHisOperType() == null ? 0 : this.getHisOperType()
						.hashCode());
		result = 37
				* result
				+ (getHisOperTime() == null ? 0 : this.getHisOperTime()
						.hashCode());
		result = 37
				* result
				+ (getHisDataDate() == null ? 0 : this.getHisDataDate()
						.hashCode());
		return result;
	}

}