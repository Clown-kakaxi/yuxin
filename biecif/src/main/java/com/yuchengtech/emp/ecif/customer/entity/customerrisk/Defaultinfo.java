package com.yuchengtech.emp.ecif.customer.entity.customerrisk;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the DEFAULTINFO database table.
 * 
 */
@Entity
@Table(name="DEFAULTINFO")
public class Defaultinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DEFAULT_INFO_ID", unique=true, nullable=false)
	private Long defaultInfoId;

	@Column(name="BADRECDATE",length=20)
	private String badrecdate;

	@Column(name="BADRECDESC",length=100)
	private String badrecdesc;

	@Column(name="BADRECTYPE",length=1)
	private String badrectype;

	@Column(name="CONFORGA",length=60)
	private String conforga;

	@Column(name="CURRSIGN",length=20)
	private String currsign;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="DATASRCDESC",length=60)
	private String datasrcdesc;

	@Column(name="DEALINFO",length=100)
	private String dealinfo;

	@Column(name="DEFAULT_BAL", precision=17, scale=2)
	private BigDecimal defaultBal;

	@Column(name="DEFAULT_DATE",length=20)
	private Date defaultDate;

	@Column(name="DEFAULT_FLAG", length=1)
	private String defaultFlag;

	@Column(name="ESCDEBTAMT",precision=17, scale=2)
	private BigDecimal escdebtamt;

	@Column(name="KEYNO",length=20)
	private String keyno;

	@Column(name="OCCURORGA",length=60)
	private String occurorga;

	@Column(name="PLACE",length=40)
	private String place;

    public Defaultinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getDefaultInfoId() {
		return this.defaultInfoId;
	}

	public void setDefaultInfoId(Long defaultInfoId) {
		this.defaultInfoId = defaultInfoId;
	}

	public String getBadrecdate() {
		return this.badrecdate;
	}

	public void setBadrecdate(String badrecdate) {
		this.badrecdate = badrecdate;
	}

	public String getBadrecdesc() {
		return this.badrecdesc;
	}

	public void setBadrecdesc(String badrecdesc) {
		this.badrecdesc = badrecdesc;
	}

	public String getBadrectype() {
		return this.badrectype;
	}

	public void setBadrectype(String badrectype) {
		this.badrectype = badrectype;
	}

	public String getConforga() {
		return this.conforga;
	}

	public void setConforga(String conforga) {
		this.conforga = conforga;
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

	public String getDatasrcdesc() {
		return this.datasrcdesc;
	}

	public void setDatasrcdesc(String datasrcdesc) {
		this.datasrcdesc = datasrcdesc;
	}

	public String getDealinfo() {
		return this.dealinfo;
	}

	public void setDealinfo(String dealinfo) {
		this.dealinfo = dealinfo;
	}

	public BigDecimal getDefaultBal() {
		return this.defaultBal;
	}

	public void setDefaultBal(BigDecimal defaultBal) {
		this.defaultBal = defaultBal;
	}

	public Date getDefaultDate() {
		return this.defaultDate;
	}

	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
	}

	public String getDefaultFlag() {
		return this.defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public BigDecimal getEscdebtamt() {
		return this.escdebtamt;
	}

	public void setEscdebtamt(BigDecimal escdebtamt) {
		this.escdebtamt = escdebtamt;
	}

	public String getKeyno() {
		return this.keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getOccurorga() {
		return this.occurorga;
	}

	public void setOccurorga(String occurorga) {
		this.occurorga = occurorga;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}