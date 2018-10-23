package com.yuchengtech.emp.ecif.customer.entity.customerriskorg;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the CUSTCREDITINFO database table.
 * 
 */
@Entity
@Table(name="CUSTCREDITINFO")
public class Custcreditinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CREDIT_ID", unique=true, nullable=false)
	private Long creditId;

	@Column(name="CREDIT_CURRENCY", length=20)
	private String creditCurrency;

	@Column(name="CREDIT_END_DATE",length=20)
	private String creditEndDate;

	@Column(name="CREDIT_START_DATE",length=20)
	private String creditStartDate;

	@Column(name="CREDIT_TYPE", length=20)
	private String creditType;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="CUST_NAME", length=40)
	private String custName;

	@Column(name="CREDIT_NO", length=20)
	private String creditNo;
	
	@Column(name="IS_LOOP", length=1)
	private String isLoop;

	@Column(name="IS_VALID", length=1)
	private String isValid;

	@Column(name="LAST_CREDIT_LIMIT", precision=17, scale=2)
	private BigDecimal lastCreditLimit;

	@Column(name="MEASURE_CREDIT_LIMIT", precision=17, scale=2)
	private BigDecimal measureCreditLimit;

	@Column(name="SEND_CREDIT_LIMIT", precision=17, scale=2)
	private BigDecimal sendCreditLimit;

    public Custcreditinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCreditId() {
		return this.creditId;
	}

	public void setCreditId(Long creditId) {
		this.creditId = creditId;
	}

	public String getCreditCurrency() {
		return this.creditCurrency;
	}

	public void setCreditCurrency(String creditCurrency) {
		this.creditCurrency = creditCurrency;
	}

	public String getCreditEndDate() {
		return this.creditEndDate;
	}

	public void setCreditEndDate(String creditEndDate) {
		this.creditEndDate = creditEndDate;
	}

	public String getCreditStartDate() {
		return this.creditStartDate;
	}

	public void setCreditStartDate(String creditStartDate) {
		this.creditStartDate = creditStartDate;
	}

	public String getCreditType() {
		return this.creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	public String getIsLoop() {
		return this.isLoop;
	}

	public void setIsLoop(String isLoop) {
		this.isLoop = isLoop;
	}

	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public BigDecimal getLastCreditLimit() {
		return this.lastCreditLimit;
	}

	public void setLastCreditLimit(BigDecimal lastCreditLimit) {
		this.lastCreditLimit = lastCreditLimit;
	}

	public BigDecimal getMeasureCreditLimit() {
		return this.measureCreditLimit;
	}

	public void setMeasureCreditLimit(BigDecimal measureCreditLimit) {
		this.measureCreditLimit = measureCreditLimit;
	}

	public BigDecimal getSendCreditLimit() {
		return this.sendCreditLimit;
	}

	public void setSendCreditLimit(BigDecimal sendCreditLimit) {
		this.sendCreditLimit = sendCreditLimit;
	}

}