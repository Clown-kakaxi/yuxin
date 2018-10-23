package com.yuchengtech.emp.ecif.customer.entity.customerrisk;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the OVERINTEREST database table.
 * 
 */
@Entity
@Table(name="OVERINTEREST")
public class Overinterest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="OVER_INTEREST_ID", unique=true, nullable=false)
	private Long overInterestId;

	@Column(name="BEGINDATE",length=20)
	private String begindate;

	@Column(name="CURRSIGN",length=20)
	private String currsign;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="DEBTORGA",length=60)
	private String debtorga;

	@Column(name="ENDDATE",length=20)
	private String enddate;

	@Column(name="OVERAMT",precision=12, scale=2)
	private BigDecimal overamt;

	@Column(name="PLACE",length=40)
	private String place;

    public Overinterest() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOverInterestId() {
		return this.overInterestId;
	}

	public void setOverInterestId(Long overInterestId) {
		this.overInterestId = overInterestId;
	}

	public String getBegindate() {
		return this.begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getCurrsign() {
		return this.currsign;
	}

	public void setCurrsign(String currsign) {
		this.currsign = currsign;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getDebtorga() {
		return this.debtorga;
	}

	public void setDebtorga(String debtorga) {
		this.debtorga = debtorga;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public BigDecimal getOveramt() {
		return this.overamt;
	}

	public void setOveramt(BigDecimal overamt) {
		this.overamt = overamt;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}