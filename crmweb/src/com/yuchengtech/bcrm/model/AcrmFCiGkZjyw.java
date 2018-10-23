package com.yuchengtech.bcrm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_F_CI_GK_ZJYW database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_GK_ZJYW")
public class AcrmFCiGkZjyw implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_GK_ZJYW_CUSTID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_GK_ZJYW_CUSTID_GENERATOR")
	@Column(name="CUST_ID")
	private String custId;
	
	@Column(name="AMOUNT")
	private BigDecimal amount;
	
	@Column(name="COUNT")
	private Integer count;

	@Column(name="CUST_TYP")
	private String custTyp;
	
	@Column(name="INCOME")
	private BigDecimal income;
	
	@Column(name="TYPE")
	private String type;

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getCustTyp() {
		return custTyp;
	}

	public void setCustTyp(String custTyp) {
		this.custTyp = custTyp;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}