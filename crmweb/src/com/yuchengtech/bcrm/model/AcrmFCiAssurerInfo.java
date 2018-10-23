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
 * The persistent class for the ACRM_F_CI_ASSURER_INFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ASSURER_INFO")
public class AcrmFCiAssurerInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_ASSURER_INFO_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_ASSURER_INFO_ID_GENERATOR")
	@Column(name="ID",unique=true, nullable=false)
	private Long id;

	@Column(name="ASS_CAPA_REP_ID", length=13)
	private String assCapaRepId;

    @Temporal( TemporalType.DATE)
	@Column(name="ASSURE_AGING_END_DATE")
	private Date assureAgingEndDate;

	@Column(name="ASSURE_CAN_MON_MEA", length=200)
	private String assureCanMonMea;

	@Column(name="ASSURE_CON_NO", length=18)
	private String assureConNo;

    @Temporal( TemporalType.DATE)
	@Column(name="ASSURE_END_DATE")
	private Date assureEndDate;

	@Column(name="ASSURE_IS_EFFECT", length=13)
	private String assureIsEffect;

	@Column(name="ASSURE_MONEY", precision=24, scale=6)
	private BigDecimal assureMoney;

	@Column(name="ASSURE_OTH_BANK_MON", precision=24, scale=6)
	private BigDecimal assureOthBankMon;

	@Column(name="ASSURE_RELA", length=13)
	private String assureRela;

    @Temporal( TemporalType.DATE)
	@Column(name="ASSURE_START_DATE")
	private Date assureStartDate;

	@Column(name="ASSURE_TYPE", length=13)
	private String assureType;

	@Column(name="ASSURE_WAY", length=13)
	private String assureWay;

	@Column(name="ASSURER",length=200)
	private String assurer;

	@Column(name="ASSURER_CARD_NUM", length=18)
	private String assurerCardNum;

	@Column(name="ASSURER_CARD_TYPE", length=13)
	private String assurerCardType;

	@Column(name="ASSURER_LOAN_CARD_ID", length=18)
	private String assurerLoanCardId;

	@Column(name="ASSURER_NAME", length=100)
	private String assurerName;

	@Column(name="ASSURER_TYPE", length=13)
	private String assurerType;

	@Column(name="BORROWER_CUST_NAME", length=200)
	private String borrowerCustName;

	@Column(name="BUSI_SERIAL_NUM", length=18)
	private String busiSerialNum;

	@Column(name="CONTRACT_NO", length=18)
	private String contractNo;

	@Column(name="CUST_ID", nullable=false, length=21)
	private String custId;

	@Column(name="CUST_NAME", length=200)
	private String custName;

	@Column(name="GTR_OTH_BANK_GRNT_AMT", precision=17, scale=10)
	private BigDecimal gtrOthBankGrntAmt;

	@Column(name="INVALID_REASON", length=800)
	private String invalidReason;

	@Column(name="MGR_NO", length=18)
	private String mgrNo;

	@Column(name="REMARK",length=800)
	private String remark;

	@Column(name="ASSLOAN_CARD_ID",length=20)
	private String assloanCardId;
	
	@Temporal( TemporalType.DATE)
	@Column(name="ASSURE_VAL_DT")
	private Date assureValDt;
	
	@Column(name="GTR_GRNT_AMT", precision=17, scale=10)
	private BigDecimal gtrGrntAmt;
	
	@Column(name="ODS_ST_DATE",length=10)
	private String odsStDate;
	
    public String getAssloanCardId() {
		return assloanCardId;
	}

	public void setAssloanCardId(String assloanCardId) {
		this.assloanCardId = assloanCardId;
	}

	public Date getAssureValDt() {
		return assureValDt;
	}

	public void setAssureValDt(Date assureValDt) {
		this.assureValDt = assureValDt;
	}

	public BigDecimal getGtrGrntAmt() {
		return gtrGrntAmt;
	}

	public void setGtrGrntAmt(BigDecimal gtrGrntAmt) {
		this.gtrGrntAmt = gtrGrntAmt;
	}

	public String getOdsStDate() {
		return odsStDate;
	}

	public void setOdsStDate(String odsStDate) {
		this.odsStDate = odsStDate;
	}

	public AcrmFCiAssurerInfo() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getAssCapaRepId() {
		return this.assCapaRepId;
	}

	public void setAssCapaRepId(String assCapaRepId) {
		this.assCapaRepId = assCapaRepId;
	}

	public Date getAssureAgingEndDate() {
		return this.assureAgingEndDate;
	}

	public void setAssureAgingEndDate(Date assureAgingEndDate) {
		this.assureAgingEndDate = assureAgingEndDate;
	}

	public String getAssureCanMonMea() {
		return this.assureCanMonMea;
	}

	public void setAssureCanMonMea(String assureCanMonMea) {
		this.assureCanMonMea = assureCanMonMea;
	}

	public String getAssureConNo() {
		return this.assureConNo;
	}

	public void setAssureConNo(String assureConNo) {
		this.assureConNo = assureConNo;
	}

	public Date getAssureEndDate() {
		return this.assureEndDate;
	}

	public void setAssureEndDate(Date assureEndDate) {
		this.assureEndDate = assureEndDate;
	}

	public String getAssureIsEffect() {
		return this.assureIsEffect;
	}

	public void setAssureIsEffect(String assureIsEffect) {
		this.assureIsEffect = assureIsEffect;
	}

	public BigDecimal getAssureMoney() {
		return this.assureMoney;
	}

	public void setAssureMoney(BigDecimal assureMoney) {
		this.assureMoney = assureMoney;
	}

	public BigDecimal getAssureOthBankMon() {
		return this.assureOthBankMon;
	}

	public void setAssureOthBankMon(BigDecimal assureOthBankMon) {
		this.assureOthBankMon = assureOthBankMon;
	}

	public String getAssureRela() {
		return this.assureRela;
	}

	public void setAssureRela(String assureRela) {
		this.assureRela = assureRela;
	}

	public Date getAssureStartDate() {
		return this.assureStartDate;
	}

	public void setAssureStartDate(Date assureStartDate) {
		this.assureStartDate = assureStartDate;
	}

	public String getAssureType() {
		return this.assureType;
	}

	public void setAssureType(String assureType) {
		this.assureType = assureType;
	}

	public String getAssureWay() {
		return this.assureWay;
	}

	public void setAssureWay(String assureWay) {
		this.assureWay = assureWay;
	}

	public String getAssurer() {
		return this.assurer;
	}

	public void setAssurer(String assurer) {
		this.assurer = assurer;
	}

	public String getAssurerCardNum() {
		return this.assurerCardNum;
	}

	public void setAssurerCardNum(String assurerCardNum) {
		this.assurerCardNum = assurerCardNum;
	}

	public String getAssurerCardType() {
		return this.assurerCardType;
	}

	public void setAssurerCardType(String assurerCardType) {
		this.assurerCardType = assurerCardType;
	}

	public String getAssurerLoanCardId() {
		return this.assurerLoanCardId;
	}

	public void setAssurerLoanCardId(String assurerLoanCardId) {
		this.assurerLoanCardId = assurerLoanCardId;
	}

	public String getAssurerName() {
		return this.assurerName;
	}

	public void setAssurerName(String assurerName) {
		this.assurerName = assurerName;
	}

	public String getAssurerType() {
		return this.assurerType;
	}

	public void setAssurerType(String assurerType) {
		this.assurerType = assurerType;
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

	public BigDecimal getGtrOthBankGrntAmt() {
		return this.gtrOthBankGrntAmt;
	}

	public void setGtrOthBankGrntAmt(BigDecimal gtrOthBankGrntAmt) {
		this.gtrOthBankGrntAmt = gtrOthBankGrntAmt;
	}

	public String getInvalidReason() {
		return this.invalidReason;
	}

	public void setInvalidReason(String invalidReason) {
		this.invalidReason = invalidReason;
	}

	public String getMgrNo() {
		return this.mgrNo;
	}

	public void setMgrNo(String mgrNo) {
		this.mgrNo = mgrNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}