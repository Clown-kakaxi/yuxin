package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the UPDOWNSTREAMINFO database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_UPDOWNSTREAMINFO")
public class Updownstreaminfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="UP_DOWN_STREAM_ID", unique=true, nullable=false)
	private Long upDownStreamId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="FINATEL",length=20)
	private String finatel;

	@Column(name="SALESUPPLYCUSTID")
	private Long salesupplycustid;

	@Column(name="SALESUPPLYCUSTNAME",length=100)
	private String salesupplycustname;

	@Column(name="SALESUPPLYTYPE",length=20)
	private String salesupplytype;

	@Column(name="YEARAMT",precision=17, scale=2)
	private BigDecimal yearamt;

    public Updownstreaminfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getUpDownStreamId() {
		return this.upDownStreamId;
	}

	public void setUpDownStreamId(Long upDownStreamId) {
		this.upDownStreamId = upDownStreamId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getFinatel() {
		return this.finatel;
	}

	public void setFinatel(String finatel) {
		this.finatel = finatel;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getSalesupplycustid() {
		return this.salesupplycustid;
	}

	public void setSalesupplycustid(Long salesupplycustid) {
		this.salesupplycustid = salesupplycustid;
	}

	public String getSalesupplycustname() {
		return this.salesupplycustname;
	}

	public void setSalesupplycustname(String salesupplycustname) {
		this.salesupplycustname = salesupplycustname;
	}

	public String getSalesupplytype() {
		return this.salesupplytype;
	}

	public void setSalesupplytype(String salesupplytype) {
		this.salesupplytype = salesupplytype;
	}

	public BigDecimal getYearamt() {
		return this.yearamt;
	}

	public void setYearamt(BigDecimal yearamt) {
		this.yearamt = yearamt;
	}

}