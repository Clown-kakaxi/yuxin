package com.yuchengtech.bcrm.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the ACRM_F_CI_LOAN_MORT_INFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_LOAN_MORT_INFO")
public class AcrmFCiLoanMortInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_LOAN_MORT_INFO_ID_GENERATOR" , sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_LOAN_MORT_INFO_ID_GENERATOR")
	@Column(name="ID",unique=true, nullable=false)
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="ASSURE_END_DATE")
	private Date assureEndDate;

	@Column(name="ASSURE_MONEY", precision=24, scale=6)
	private BigDecimal assureMoney;

    @Temporal( TemporalType.DATE)
	@Column(name="ASSURE_START_DATE")
	private Date assureStartDate;

	@Column(name="ASSURE_UPLIMIT_MONEY", precision=24, scale=6)
	private BigDecimal assureUplimitMoney;

	@Column(name="BORROW_CUST_NAME", length=200)
	private String borrowCustName;

	@Column(name="BORROWER_NAME", length=200)
	private String borrowerName;

	@Column(name="BUSI_SERIAL_NUM", length=18)
	private String busiSerialNum;

	@Column(name="CONTRACT_NO", length=20)
	private String contractNo;
	
	@Column(name="COUNT", length=20)
	private Long count;

	@Column(name="CUST_ID", nullable=false, length=21)
	private String custId;

	@Column(name="EVALUATE_MONEY", precision=24, scale=6)
	private BigDecimal evaluateMoney;

	@Column(name="FINDING_MONEY", precision=24, scale=6)
	private BigDecimal findingMoney;

	@Column(name="FIRST_RESPONSIBLE", length=200)
	private String firstResponsible;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name="MANAGEMENT_INST", length=200)
	private String managementInst;

	@Column(name="MGR_INST", length=200)
	private String mgrInst;

	@Column(name="MGR_NO", length=18)
	private String mgrNo;

    @Temporal( TemporalType.DATE)
	@Column(name="MORT_REG_DATE")
	private Date mortRegDate;

	@Column(name="MORT_REG_INST", length=200)
	private String mortRegInst;

	@Column(name="MORT_THING_NAME", length=200)
	private String mortThingName;

	@Column(name="MORT_THING_NO", length=18)
	private String mortThingNo;

	@Column(name="MORT_THING_TYPE", length=13)
	private String mortThingType;

	@Column(name="NOTARI_THING", length=200)
	private String notariThing;

	@Column(name="OWNER_CARD_ID", length=18)
	private String ownerCardId;

	@Column(name="OWNER_CARD_TYPE", length=13)
	private String ownerCardType;

	@Column(name="OWNER_LOAN_CARD_ID", length=18)
	private String ownerLoanCardId;

	@Column(name="OWNER_USER", length=200)
	private String ownerUser;

	@Column(name="POSTING_STAT", length=13)
	private String postingStat;

    @Temporal( TemporalType.DATE)
	@Column(name="REG_DATE")
	private Date regDate;

	@Column(name="REG_NO", length=18)
	private String regNo;

	@Column(name="REG_STAT", length=13)
	private String regStat;

	@Column(name="REG_USER", length=200)
	private String regUser;

	@Column(name="REMARK",length=800)
	private String remark;

	@Column(name="SED_RESPONSIBLE", length=200)
	private String sedResponsible;

	@Column(name="STATUS",length=13)
	private String status;

	@Column(name="WARRANT_CARD_NO", length=18)
	private String warrantCardNo;

	@Column(name="WARRANT_CARD_TYPE", length=13)
	private String warrantCardType;
	
	@Column(name="ODS_ST_DATE", length=10)
	private String odsStDate;
	
	@Column(name="ASSURE_UPLIMIT_AMT", precision=24, scale=6)
	private BigDecimal assureUplimitAmt;

    public String getOdsStDate() {
		return odsStDate;
	}

	public void setOdsStDate(String odsStDate) {
		this.odsStDate = odsStDate;
	}

	public BigDecimal getAssureUplimitAmt() {
		return assureUplimitAmt;
	}

	public void setAssureUplimitAmt(BigDecimal assureUplimitAmt) {
		this.assureUplimitAmt = assureUplimitAmt;
	}

	public Long getCount() {
		return count;
	}

	public AcrmFCiLoanMortInfo() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public void setCount(Long count) {
		this.count = count;
	}


	public Date getAssureEndDate() {
		return this.assureEndDate;
	}

	public void setAssureEndDate(Date assureEndDate) {
		this.assureEndDate = assureEndDate;
	}

	public BigDecimal getAssureMoney() {
		return this.assureMoney;
	}

	public void setAssureMoney(BigDecimal assureMoney) {
		this.assureMoney = assureMoney;
	}

	public Date getAssureStartDate() {
		return this.assureStartDate;
	}

	public void setAssureStartDate(Date assureStartDate) {
		this.assureStartDate = assureStartDate;
	}

	public BigDecimal getAssureUplimitMoney() {
		return this.assureUplimitMoney;
	}

	public void setAssureUplimitMoney(BigDecimal assureUplimitMoney) {
		this.assureUplimitMoney = assureUplimitMoney;
	}

	public String getBorrowCustName() {
		return this.borrowCustName;
	}

	public void setBorrowCustName(String borrowCustName) {
		this.borrowCustName = borrowCustName;
	}

	public String getBorrowerName() {
		return this.borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBusiSerialNum() {
		return this.busiSerialNum;
	}

	public void setBusiSerialNum(String busiSerialNum) {
		this.busiSerialNum = busiSerialNum;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public BigDecimal getEvaluateMoney() {
		return this.evaluateMoney;
	}

	public void setEvaluateMoney(BigDecimal evaluateMoney) {
		this.evaluateMoney = evaluateMoney;
	}

	public BigDecimal getFindingMoney() {
		return this.findingMoney;
	}

	public void setFindingMoney(BigDecimal findingMoney) {
		this.findingMoney = findingMoney;
	}

	public String getFirstResponsible() {
		return this.firstResponsible;
	}

	public void setFirstResponsible(String firstResponsible) {
		this.firstResponsible = firstResponsible;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getManagementInst() {
		return this.managementInst;
	}

	public void setManagementInst(String managementInst) {
		this.managementInst = managementInst;
	}

	public String getMgrInst() {
		return this.mgrInst;
	}

	public void setMgrInst(String mgrInst) {
		this.mgrInst = mgrInst;
	}

	public String getMgrNo() {
		return this.mgrNo;
	}

	public void setMgrNo(String mgrNo) {
		this.mgrNo = mgrNo;
	}

	public Date getMortRegDate() {
		return this.mortRegDate;
	}

	public void setMortRegDate(Date mortRegDate) {
		this.mortRegDate = mortRegDate;
	}

	public String getMortRegInst() {
		return this.mortRegInst;
	}

	public void setMortRegInst(String mortRegInst) {
		this.mortRegInst = mortRegInst;
	}

	public String getMortThingName() {
		return this.mortThingName;
	}

	public void setMortThingName(String mortThingName) {
		this.mortThingName = mortThingName;
	}

	public String getMortThingNo() {
		return this.mortThingNo;
	}

	public void setMortThingNo(String mortThingNo) {
		this.mortThingNo = mortThingNo;
	}

	public String getMortThingType() {
		return this.mortThingType;
	}

	public void setMortThingType(String mortThingType) {
		this.mortThingType = mortThingType;
	}

	public String getNotariThing() {
		return this.notariThing;
	}

	public void setNotariThing(String notariThing) {
		this.notariThing = notariThing;
	}

	public String getOwnerCardId() {
		return this.ownerCardId;
	}

	public void setOwnerCardId(String ownerCardId) {
		this.ownerCardId = ownerCardId;
	}

	public String getOwnerCardType() {
		return this.ownerCardType;
	}

	public void setOwnerCardType(String ownerCardType) {
		this.ownerCardType = ownerCardType;
	}

	public String getOwnerLoanCardId() {
		return this.ownerLoanCardId;
	}

	public void setOwnerLoanCardId(String ownerLoanCardId) {
		this.ownerLoanCardId = ownerLoanCardId;
	}

	public String getOwnerUser() {
		return this.ownerUser;
	}

	public void setOwnerUser(String ownerUser) {
		this.ownerUser = ownerUser;
	}

	public String getPostingStat() {
		return this.postingStat;
	}

	public void setPostingStat(String postingStat) {
		this.postingStat = postingStat;
	}

	public Date getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getRegNo() {
		return this.regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRegStat() {
		return this.regStat;
	}

	public void setRegStat(String regStat) {
		this.regStat = regStat;
	}

	public String getRegUser() {
		return this.regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSedResponsible() {
		return this.sedResponsible;
	}

	public void setSedResponsible(String sedResponsible) {
		this.sedResponsible = sedResponsible;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWarrantCardNo() {
		return this.warrantCardNo;
	}

	public void setWarrantCardNo(String warrantCardNo) {
		this.warrantCardNo = warrantCardNo;
	}

	public String getWarrantCardType() {
		return this.warrantCardType;
	}

	public void setWarrantCardType(String warrantCardType) {
		this.warrantCardType = warrantCardType;
	}

}