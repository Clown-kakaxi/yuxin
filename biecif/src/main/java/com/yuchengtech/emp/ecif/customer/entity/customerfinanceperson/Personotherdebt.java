package com.yuchengtech.emp.ecif.customer.entity.customerfinanceperson;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONOTHERDEBT database table.
 * 
 */
@Entity
@Table(name="PERSONOTHERDEBT")
public class Personotherdebt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PERSON_OTHER_DEBT_ID", unique=true, nullable=false)
	private Long personOtherDebtId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="CUST_NO", length=32)
	private String custNo;

	@Column(name="DEBT_BAL", precision=17, scale=2)
	private BigDecimal debtBal;

	@Column(name="DEBT_DESC", length=200)
	private String debtDesc;

	@Column(name="DEBT_TYPE", length=20)
	private String debtType;

    public Personotherdebt() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getPersonOtherDebtId() {
		return this.personOtherDebtId;
	}

	public void setPersonOtherDebtId(Long personOtherDebtId) {
		this.personOtherDebtId = personOtherDebtId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public BigDecimal getDebtBal() {
		return this.debtBal;
	}

	public void setDebtBal(BigDecimal debtBal) {
		this.debtBal = debtBal;
	}

	public String getDebtDesc() {
		return this.debtDesc;
	}

	public void setDebtDesc(String debtDesc) {
		this.debtDesc = debtDesc;
	}

	public String getDebtType() {
		return this.debtType;
	}

	public void setDebtType(String debtType) {
		this.debtType = debtType;
	}

}