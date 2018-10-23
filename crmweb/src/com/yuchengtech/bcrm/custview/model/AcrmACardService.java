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
 * The persistent class for the ACRM_A_CARD_SERVICE database table.
 * 
 */
@Entity
@Table(name="ACRM_A_CARD_SERVICE")
public class AcrmACardService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_A_CARD_SERVICE_ID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_A_CARD_SERVICE_ID_GENERATOR")
	@Column(name="ID")
	private Long id;
	
	@Column(name="CARD_NO")
	private BigDecimal cardNo;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="FREE_SERVICE_TIMES")
	private BigDecimal freeServiceTimes;

    @Temporal( TemporalType.DATE)
	@Column(name="INSURANCE_START_DATE")
	private Date insuranceStartDate;

	@Column(name="MEMBER_CARD_NO")
	private BigDecimal memberCardNo;

    @Temporal( TemporalType.DATE)
	@Column(name="MEMBER_USING_DATE")
	private Date memberUsingDate;

    @Temporal( TemporalType.DATE)
    @Column(name="SERVICE_DATE")
	private Date serviceDate;

	@Column(name="USED_TIMES")
	private BigDecimal usedTimes;

    public AcrmACardService() {
    }
    
    public Long getId(){
    	return this.id;
    }
    
    public void setId(Long id){
    	this.id = id;
    }

	public BigDecimal getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(BigDecimal cardNo) {
		this.cardNo = cardNo;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public BigDecimal getFreeServiceTimes() {
		return this.freeServiceTimes;
	}

	public void setFreeServiceTimes(BigDecimal freeServiceTimes) {
		this.freeServiceTimes = freeServiceTimes;
	}

	public Date getInsuranceStartDate() {
		return this.insuranceStartDate;
	}

	public void setInsuranceStartDate(Date insuranceStartDate) {
		this.insuranceStartDate = insuranceStartDate;
	}

	public BigDecimal getMemberCardNo() {
		return this.memberCardNo;
	}

	public void setMemberCardNo(BigDecimal memberCardNo) {
		this.memberCardNo = memberCardNo;
	}

	public Date getMemberUsingDate() {
		return this.memberUsingDate;
	}

	public void setMemberUsingDate(Date memberUsingDate) {
		this.memberUsingDate = memberUsingDate;
	}

	public Date getServiceDate() {
		return this.serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public BigDecimal getUsedTimes() {
		return this.usedTimes;
	}

	public void setUsedTimes(BigDecimal usedTimes) {
		this.usedTimes = usedTimes;
	}

}