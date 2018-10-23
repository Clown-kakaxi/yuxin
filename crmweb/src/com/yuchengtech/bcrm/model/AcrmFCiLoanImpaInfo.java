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
 * The persistent class for the ACRM_F_CI_LOAN_IMPA_INFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_LOAN_IMPA_INFO")
public class AcrmFCiLoanImpaInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_LOAN_IMPA_INFO_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_LOAN_IMPA_INFO_ID_GENERATOR")
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

	@Column(name="BORROWER_CUST_NAME", length=200)
	private String borrowerCustName;

	@Column(name="BUSI_SERIAL_NUM", length=18)
	private String busiSerialNum;

	@Column(name="CONTRACT_NO", length=18)
	private String contractNo;
	
	@Column(name="COUNT", length=18)
	private Long count;

	@Column(name="CUST_ID", nullable=false, length=21)
	private String custId;

	@Column(name="CUST_NAME", length=200)
	private String custName;

	@Column(name="EXCHANGE_RATE", precision=24, scale=6)
	private BigDecimal exchangeRate;

	@Column(name="FINDING_MONEY", precision=24, scale=6)
	private BigDecimal findingMoney;

	@Column(name="FIRST_RESPONSIBLE", length=200)
	private String firstResponsible;

	@Column(name="FREEZE_STAT", length=13)
	private String freezeStat;

	@Column(name="IMPA_CANT_REASON", length=200)
	private String impaCantReason;

	@Column(name="IMPA_IS_EFFECT", length=13)
	private String impaIsEffect;

	@Column(name="IMPA_NAME", length=200)
	private String impaName;

	@Column(name="IMPA_RATE", precision=24, scale=6)
	private BigDecimal impaRate;

	@Column(name="IMPA_THING_NO", length=18)
	private String impaThingNo;

	@Column(name="IMPA_TYPE", length=13)
	private String impaType;

	@Column(name="IMPA_UPLIMIT_MONEY", precision=24, scale=6)
	private BigDecimal impaUplimitMoney;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name="MANAGEMENT_INST", length=200)
	private String managementInst;

	@Column(name="MGR_INST", length=18)
	private String mgrInst;

	@Column(name="MGR_NO", length=18)
	private String mgrNo;

	@Column(name="MONEY_TYPE", length=13)
	private String moneyType;

	@Column(name="NOTARI_THING", length=200)
	private String notariThing;

	@Column(name="OWNER_CARD_ID", length=18)
	private String ownerCardId;

	@Column(name="OWNER_CARD_TYPE", length=13)
	private String ownerCardType;

	@Column(name="OWNER_LOAN_CARD_ID", length=18)
	private String ownerLoanCardId;

	@Column(name="OWNER_USER", length=13)
	private String ownerUser;

	@Column(name="POSTING_STAT", length=13)
	private String postingStat;

    @Temporal( TemporalType.DATE)
	@Column(name="REG_DATE")
	private Date regDate;

	@Column(name="REG_USER", length=200)
	private String regUser;

	@Column(name="REMARK",length=800)
	private String remark;

	@Column(name="SED_RESPONSIBLE", length=200)
	private String sedResponsible;

	@Column(name="STATUS",length=13)
	private String status;

	@Column(name="WARRANT_ID", length=18)
	private String warrantId;

	@Column(name="WARRANT_NAME", length=200)
	private String warrantName;
	
	@Column(name="ODS_ST_DATE", length=10)
	private String odsStDate;

    public String getOdsStDate() {
		return odsStDate;
	}


	public void setOdsStDate(String odsStDate) {
		this.odsStDate = odsStDate;
	}


	public AcrmFCiLoanImpaInfo() {
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

	public String getBorrowerCustName() {
		return this.borrowerCustName;
	}

	public void setBorrowerCustName(String borrowerCustName) {
		this.borrowerCustName = borrowerCustName;
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

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCount() {
		return count;
	}


	public void setCount(Long count) {
		this.count = count;
	}


	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
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

	public String getFreezeStat() {
		return this.freezeStat;
	}

	public void setFreezeStat(String freezeStat) {
		this.freezeStat = freezeStat;
	}

	public String getImpaCantReason() {
		return this.impaCantReason;
	}

	public void setImpaCantReason(String impaCantReason) {
		this.impaCantReason = impaCantReason;
	}

	public String getImpaIsEffect() {
		return this.impaIsEffect;
	}

	public void setImpaIsEffect(String impaIsEffect) {
		this.impaIsEffect = impaIsEffect;
	}

	public String getImpaName() {
		return this.impaName;
	}

	public void setImpaName(String impaName) {
		this.impaName = impaName;
	}

	public BigDecimal getImpaRate() {
		return this.impaRate;
	}

	public void setImpaRate(BigDecimal impaRate) {
		this.impaRate = impaRate;
	}

	public String getImpaThingNo() {
		return this.impaThingNo;
	}

	public void setImpaThingNo(String impaThingNo) {
		this.impaThingNo = impaThingNo;
	}

	public String getImpaType() {
		return this.impaType;
	}

	public void setImpaType(String impaType) {
		this.impaType = impaType;
	}

	public BigDecimal getImpaUplimitMoney() {
		return this.impaUplimitMoney;
	}

	public void setImpaUplimitMoney(BigDecimal impaUplimitMoney) {
		this.impaUplimitMoney = impaUplimitMoney;
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

	public String getMoneyType() {
		return this.moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
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

	public String getWarrantId() {
		return this.warrantId;
	}

	public void setWarrantId(String warrantId) {
		this.warrantId = warrantId;
	}

	public String getWarrantName() {
		return this.warrantName;
	}

	public void setWarrantName(String warrantName) {
		this.warrantName = warrantName;
	}

}