package com.yuchengtech.emp.ecif.customer.entity.customerfinanceorg;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the TAXINFO database table.
 * 
 */
@Entity
@Table(name="M_CI_TAXINFO")
public class Taxinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TAX_INFO_ID", unique=true, nullable=false)
	private Long taxInfoId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="END_DATE",length=20)
	private String endDate;

	@Column(name="START_DATE",length=20)
	private String startDate;

	@Column(name="TAX_AMT", precision=17, scale=2)
	private BigDecimal taxAmt;

	@Column(name="TAX_CURRENCY", length=20)
	private String taxCurrency;

	@Column(name="TAX_DATE",length=20)
	private String taxDate;

	@Column(name="TAX_TYPE", length=20)
	private String taxType;

    public Taxinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getTaxInfoId() {
		return this.taxInfoId;
	}

	public void setTaxInfoId(Long taxInfoId) {
		this.taxInfoId = taxInfoId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getTaxAmt() {
		return this.taxAmt;
	}

	public void setTaxAmt(BigDecimal taxAmt) {
		this.taxAmt = taxAmt;
	}

	public String getTaxCurrency() {
		return this.taxCurrency;
	}

	public void setTaxCurrency(String taxCurrency) {
		this.taxCurrency = taxCurrency;
	}

	public String getTaxDate() {
		return this.taxDate;
	}

	public void setTaxDate(String taxDate) {
		this.taxDate = taxDate;
	}

	public String getTaxType() {
		return this.taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

}