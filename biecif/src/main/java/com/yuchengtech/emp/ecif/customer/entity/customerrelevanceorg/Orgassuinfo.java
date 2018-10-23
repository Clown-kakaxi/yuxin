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
 * The persistent class for the ORGASSUINFO database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_ASSUINFO")
public class Orgassuinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_ASSU_INFO_ID", unique=true, nullable=false)
	private Long orgAssuInfoId;

	@Column(name="ASSUAMT",precision=17, scale=2)
	private BigDecimal assuamt;

	@Column(name="ASSUBAL",precision=17, scale=2)
	private BigDecimal assubal;

	@Column(name="ASSUCUSTID",length=80)
	private Long assucustid;

	@Column(name="ASSUCUSTNAME",length=60)
	private String assucustname;

	@Column(name="ASSUCUSTTYPE",length=20)
	private String assucusttype;

	@Column(name="ASSUFLAG",length=1)
	private String assuflag;

	@Column(name="ASSUIDENTID",length=40)
	private String assuidentid;

	@Column(name="ASSUIDENTTYPE",length=20)
	private String assuidenttype;

	@Column(name="ASSUKINDNAME",length=80)
	private String assukindname;

	@Column(name="ASSUNUM")
	private Long assunum;

	@Column(name="ASSUPROD",length=80)
	private String assuprod;
	
	@Column(name="BEGINDATE",length=20)
	private String begindate;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="DEBTBANK",length=40)
	private String debtbank;

	@Column(name="EACHASSUFLAG",length=1)
	private String eachassuflag;

	@Column(name="ENDDATE",length=20)
	private String enddate;

	@Column(name="HAS_LOAN", length=1)
	private String hasLoan;

	@Column(name="MAXASSUFLAG",length=1)
	private String maxassuflag;

    public Orgassuinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOrgAssuInfoId() {
		return this.orgAssuInfoId;
	}

	public void setOrgAssuInfoId(Long orgAssuInfoId) {
		this.orgAssuInfoId = orgAssuInfoId;
	}

	public BigDecimal getAssuamt() {
		return this.assuamt;
	}

	public void setAssuamt(BigDecimal assuamt) {
		this.assuamt = assuamt;
	}

	public BigDecimal getAssubal() {
		return this.assubal;
	}

	public void setAssubal(BigDecimal assubal) {
		this.assubal = assubal;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getAssucustid() {
		return this.assucustid;
	}

	public void setAssucustid(Long assucustid) {
		this.assucustid = assucustid;
	}

	public String getAssucustname() {
		return this.assucustname;
	}

	public void setAssucustname(String assucustname) {
		this.assucustname = assucustname;
	}

	public String getAssucusttype() {
		return this.assucusttype;
	}

	public void setAssucusttype(String assucusttype) {
		this.assucusttype = assucusttype;
	}

	public String getAssuflag() {
		return this.assuflag;
	}

	public void setAssuflag(String assuflag) {
		this.assuflag = assuflag;
	}

	public String getAssuidentid() {
		return this.assuidentid;
	}

	public void setAssuidentid(String assuidentid) {
		this.assuidentid = assuidentid;
	}

	public String getAssuidenttype() {
		return this.assuidenttype;
	}

	public void setAssuidenttype(String assuidenttype) {
		this.assuidenttype = assuidenttype;
	}

	public String getAssukindname() {
		return this.assukindname;
	}

	public void setAssukindname(String assukindname) {
		this.assukindname = assukindname;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getAssunum() {
		return this.assunum;
	}

	public void setAssunum(Long assunum) {
		this.assunum = assunum;
	}

	public String getAssuprod() {
		return this.assuprod;
	}

	public void setAssuprod(String assuprod) {
		this.assuprod = assuprod;
	}

	public String getBegindate() {
		return this.begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getDebtbank() {
		return this.debtbank;
	}

	public void setDebtbank(String debtbank) {
		this.debtbank = debtbank;
	}

	public String getEachassuflag() {
		return this.eachassuflag;
	}

	public void setEachassuflag(String eachassuflag) {
		this.eachassuflag = eachassuflag;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getHasLoan() {
		return this.hasLoan;
	}

	public void setHasLoan(String hasLoan) {
		this.hasLoan = hasLoan;
	}

	public String getMaxassuflag() {
		return this.maxassuflag;
	}

	public void setMaxassuflag(String maxassuflag) {
		this.maxassuflag = maxassuflag;
	}

}