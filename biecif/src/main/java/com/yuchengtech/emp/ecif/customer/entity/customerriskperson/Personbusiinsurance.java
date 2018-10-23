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
 * The persistent class for the PERSONBUSIINSURANCE database table.
 * 
 */
@Entity
@Table(name="PERSONBUSIINSURANCE")
public class Personbusiinsurance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PERSON_BI_ID", unique=true, nullable=false)
	private Long personBiId;

	@Column(name="BINAME",length=80)
	private String biname;

	@Column(name="BITYPE",length=20)
	private String bitype;

	@Column(name="CANCELDATE",length=20)
	private String canceldate;

	@Column(name="CASHVALUE",precision=17, scale=2)
	private BigDecimal cashvalue;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="CUSTOMERID",length=32)
	private String customerid;

	@Column(name="INSUREDATE",length=20)
	private String insuredate;

	@Column(name="INSUREDSUM",precision=17, scale=2)
	private BigDecimal insuredsum;

	@Column(name="MATURITY",length=20)
	private String maturity;

	@Column(name="UNDERWRITER",length=80)
	private String underwriter;
	
	@Column(name="SERIAL_NO",length=40)
	private String serialNo;

    public Personbusiinsurance() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getPersonBiId() {
		return this.personBiId;
	}

	public void setPersonBiId(Long personBiId) {
		this.personBiId = personBiId;
	}

	public String getBiname() {
		return this.biname;
	}

	public void setBiname(String biname) {
		this.biname = biname;
	}

	public String getBitype() {
		return this.bitype;
	}

	public void setBitype(String bitype) {
		this.bitype = bitype;
	}

	public String getCanceldate() {
		return this.canceldate;
	}

	public void setCanceldate(String canceldate) {
		this.canceldate = canceldate;
	}

	public BigDecimal getCashvalue() {
		return this.cashvalue;
	}

	public void setCashvalue(BigDecimal cashvalue) {
		this.cashvalue = cashvalue;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getInsuredate() {
		return this.insuredate;
	}

	public void setInsuredate(String insuredate) {
		this.insuredate = insuredate;
	}

	public BigDecimal getInsuredsum() {
		return this.insuredsum;
	}

	public void setInsuredsum(BigDecimal insuredsum) {
		this.insuredsum = insuredsum;
	}

	public String getMaturity() {
		return this.maturity;
	}

	public void setMaturity(String maturity) {
		this.maturity = maturity;
	}

	public String getUnderwriter() {
		return this.underwriter;
	}

	public void setUnderwriter(String underwriter) {
		this.underwriter = underwriter;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}