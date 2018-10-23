package com.yuchengtech.bcrm.custview.model;

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
 * The persistent class for the ACRM_F_CI_CUST_LAW_INFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CUST_LAW_INFO")
public class AcrmFCiCustLawInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_CUST_LAW_INFO_ID_GENERATOR", sequenceName="ID_SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_CUST_LAW_INFO_ID_GENERATOR")
	private String id;

	@Column(name="BAL_AMT")
	private BigDecimal balAmt;

	@Column(name="BORR_NAME")
	private String borrName;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="LITI_STA")
	private String litiSta;

	@Column(name="MA_CASE_NO")
	private String maCaseNo;

	@Column(name="ORI_BORR_AMT")
	private BigDecimal oriBorrAmt;

	@Column(name="OTH_PROSEC")
	private String othProsec;

    @Temporal( TemporalType.DATE)
	@Column(name="RECORD_DATE")
	private Date recordDate;

    public AcrmFCiCustLawInfo() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getBalAmt() {
		return this.balAmt;
	}

	public void setBalAmt(BigDecimal balAmt) {
		this.balAmt = balAmt;
	}

	public String getBorrName() {
		return this.borrName;
	}

	public void setBorrName(String borrName) {
		this.borrName = borrName;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getLitiSta() {
		return this.litiSta;
	}

	public void setLitiSta(String litiSta) {
		this.litiSta = litiSta;
	}

	public String getMaCaseNo() {
		return this.maCaseNo;
	}

	public void setMaCaseNo(String maCaseNo) {
		this.maCaseNo = maCaseNo;
	}

	public BigDecimal getOriBorrAmt() {
		return this.oriBorrAmt;
	}

	public void setOriBorrAmt(BigDecimal oriBorrAmt) {
		this.oriBorrAmt = oriBorrAmt;
	}

	public String getOthProsec() {
		return this.othProsec;
	}

	public void setOthProsec(String othProsec) {
		this.othProsec = othProsec;
	}

	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

}