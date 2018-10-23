package com.yuchengtech.bcrm.customer.model;

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
 * The persistent class for the OCRM_A_CI_CUSTLOSS_REMIND database table.
 * 
 */
@Entity
@Table(name="OCRM_A_CI_CUSTLOSS_REMIND")
public class OcrmACiCustlossRemind implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_A_CI_CUSTLOSS_REMIND_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_A_CI_CUSTLOSS_REMIND_ID_GENERATOR")
	private Long id;

	@Column(name="AO_NAME")
	private String aoName;

	@Column(name="AREA_CENTER")
	private String areaCenter;

	@Column(name="BRANCH_NO")
	private String branchNo;

	@Column(name="BUSI_GROUP")
	private String busiGroup;

	@Column(name="BUSI_LINE")
	private String busiLine;

	@Column(name="CURR_STATUS")
	private String currStatus;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="DECLARE_AMT")
	private BigDecimal declareAmt;

	@Column(name="DECLARE_CUR_TYPE")
	private String declareCurType;

    @Temporal( TemporalType.DATE)
	@Column(name="DECLARE_DATE")
	private Date declareDate;

	@Column(name="TRAD_AMT")
	private BigDecimal tradAmt;

    @Temporal( TemporalType.DATE)
	@Column(name="TRAD_DATE")
	private Date tradDate;

    public OcrmACiCustlossRemind() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAoName() {
		return this.aoName;
	}

	public void setAoName(String aoName) {
		this.aoName = aoName;
	}

	public String getAreaCenter() {
		return this.areaCenter;
	}

	public void setAreaCenter(String areaCenter) {
		this.areaCenter = areaCenter;
	}

	public String getBranchNo() {
		return this.branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getBusiGroup() {
		return this.busiGroup;
	}

	public void setBusiGroup(String busiGroup) {
		this.busiGroup = busiGroup;
	}

	public String getBusiLine() {
		return this.busiLine;
	}

	public void setBusiLine(String busiLine) {
		this.busiLine = busiLine;
	}

	public String getCurrStatus() {
		return this.currStatus;
	}

	public void setCurrStatus(String currStatus) {
		this.currStatus = currStatus;
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

	public BigDecimal getDeclareAmt() {
		return this.declareAmt;
	}

	public void setDeclareAmt(BigDecimal declareAmt) {
		this.declareAmt = declareAmt;
	}

	public String getDeclareCurType() {
		return this.declareCurType;
	}

	public void setDeclareCurType(String declareCurType) {
		this.declareCurType = declareCurType;
	}

	public Date getDeclareDate() {
		return this.declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public BigDecimal getTradAmt() {
		return this.tradAmt;
	}

	public void setTradAmt(BigDecimal tradAmt) {
		this.tradAmt = tradAmt;
	}

	public Date getTradDate() {
		return this.tradDate;
	}

	public void setTradDate(Date tradDate) {
		this.tradDate = tradDate;
	}

}