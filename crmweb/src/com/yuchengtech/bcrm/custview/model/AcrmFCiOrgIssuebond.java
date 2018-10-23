package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_ORG_ISSUEBOND database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ORG_ISSUEBOND")
public class AcrmFCiOrgIssuebond implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_ORG_ISSUEBOND_ISSUEBONDID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_ORG_ISSUEBOND_ISSUEBONDID_GENERATOR")
	@Column(name="ISSUE_BOND_ID")
	private String issueBondId;

	@Column(name="BOND_CODE")
	private String bondCode;

	@Column(name="BOND_CURR")
	private String bondCurr;

	@Column(name="BOND_GRADE")
	private String bondGrade;

	@Column(name="BOND_INTR")
	private String bondIntr;

	@Column(name="BOND_KIND")
	private String bondKind;

	@Column(name="BOND_NAME")
	private String bondName;

	@Column(name="BOND_SELLER")
	private String bondSeller;

	@Column(name="BOND_STATE")
	private String bondState;

	@Column(name="BOND_TERM")
	private BigDecimal bondTerm;

	@Column(name="BOND_TYPE")
	private String bondType;

	@Column(name="BOND_WARRANTOR")
	private String bondWarrantor;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="EVAL_ORG")
	private String evalOrg;

	@Column(name="EXCHANGE_CODE")
	private String exchangeCode;

	@Column(name="EXCHANGE_COUNTRY_CODE")
	private String exchangeCountryCode;

	@Column(name="EXCHANGE_NAME")
	private String exchangeName;

	@Column(name="IS_MARKET")
	private String isMarket;

	@Column(name="ISSUE_AMT")
	private BigDecimal issueAmt;

    @Temporal( TemporalType.DATE)
	@Column(name="ISSUE_DATE")
	private Date issueDate;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
	
	@Column(name="REMARK")
	private String remark;

    public AcrmFCiOrgIssuebond() {
    }

	public String getIssueBondId() {
		return this.issueBondId;
	}

	public void setIssueBondId(String issueBondId) {
		this.issueBondId = issueBondId;
	}

	public String getBondCode() {
		return this.bondCode;
	}

	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}

	public String getBondCurr() {
		return this.bondCurr;
	}

	public void setBondCurr(String bondCurr) {
		this.bondCurr = bondCurr;
	}

	public String getBondGrade() {
		return this.bondGrade;
	}

	public void setBondGrade(String bondGrade) {
		this.bondGrade = bondGrade;
	}

	public String getBondIntr() {
		return this.bondIntr;
	}

	public void setBondIntr(String bondIntr) {
		this.bondIntr = bondIntr;
	}

	public String getBondKind() {
		return this.bondKind;
	}

	public void setBondKind(String bondKind) {
		this.bondKind = bondKind;
	}

	public String getBondName() {
		return this.bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public String getBondSeller() {
		return this.bondSeller;
	}

	public void setBondSeller(String bondSeller) {
		this.bondSeller = bondSeller;
	}

	public String getBondState() {
		return this.bondState;
	}

	public void setBondState(String bondState) {
		this.bondState = bondState;
	}

	public BigDecimal getBondTerm() {
		return this.bondTerm;
	}

	public void setBondTerm(BigDecimal bondTerm) {
		this.bondTerm = bondTerm;
	}

	public String getBondType() {
		return this.bondType;
	}

	public void setBondType(String bondType) {
		this.bondType = bondType;
	}

	public String getBondWarrantor() {
		return this.bondWarrantor;
	}

	public void setBondWarrantor(String bondWarrantor) {
		this.bondWarrantor = bondWarrantor;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getEvalOrg() {
		return this.evalOrg;
	}

	public void setEvalOrg(String evalOrg) {
		this.evalOrg = evalOrg;
	}

	public String getExchangeCode() {
		return this.exchangeCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public String getExchangeCountryCode() {
		return this.exchangeCountryCode;
	}

	public void setExchangeCountryCode(String exchangeCountryCode) {
		this.exchangeCountryCode = exchangeCountryCode;
	}

	public String getExchangeName() {
		return this.exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getIsMarket() {
		return this.isMarket;
	}

	public void setIsMarket(String isMarket) {
		this.isMarket = isMarket;
	}

	public BigDecimal getIssueAmt() {
		return this.issueAmt;
	}

	public void setIssueAmt(BigDecimal issueAmt) {
		this.issueAmt = issueAmt;
	}

	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}