package com.yuchengtech.emp.ecif.customer.entity.customerriskperson;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONGJJ database table.
 * 
 */
@Entity
@Table(name="PERSONGJJ")
public class Persongjj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PERSON_GJJ_INFO_ID", unique=true, nullable=false)
	private Long personGjjInfoId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="GJJ_ACCT_NO", length=30)
	private String gjjAcctNo;

	@Column(name="GJJ_AMT_PER_MONTH", precision=12, scale=2)
	private BigDecimal gjjAmtPerMonth;

    public Persongjj() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getPersonGjjInfoId() {
		return this.personGjjInfoId;
	}

	public void setPersonGjjInfoId(Long personGjjInfoId) {
		this.personGjjInfoId = personGjjInfoId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getGjjAcctNo() {
		return this.gjjAcctNo;
	}

	public void setGjjAcctNo(String gjjAcctNo) {
		this.gjjAcctNo = gjjAcctNo;
	}

	public BigDecimal getGjjAmtPerMonth() {
		return this.gjjAmtPerMonth;
	}

	public void setGjjAmtPerMonth(BigDecimal gjjAmtPerMonth) {
		this.gjjAmtPerMonth = gjjAmtPerMonth;
	}

}