package com.yuchengtech.emp.ecif.customer.entity.asset;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;



/**
 * The persistent class for the ENT_FIXEDASSETS database table.
 * 
 */
@Entity
@Table(name="M_HL_FIXEDASSETS")
public class EntFixedasset implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="AREA",precision=17, scale=2)
	private BigDecimal area;

	@Column(name="CERTIFICATENO",length=32)
	private String certificateno;

	@Column(name="CURRENCY",length=20)
	private String currency;

	@Column(name="DEPRECIATION",length=200)
	private String depreciation;

	@Column(name="DISCOUNTRATE",precision=5, scale=2)
	private BigDecimal discountrate;

	@Column(name="EVALDATE",length=20)
	private String evaldate;

	@Column(name="EVALORG",length=200)
	private String evalorg;

	@Column(name="EVALVALUE",precision=17, scale=2)
	private BigDecimal evalvalue;

	@Column(name="FIXEDASSETSNAME",length=80)
	private String fixedassetsname;

	@Column(name="FIXEDASSETSSTATUS",length=18)
	private String fixedassetsstatus;

	@Column(name="FIXEDASSETSTYPE",length=18)
	private String fixedassetstype;

	@Column(name="FORMERVALUE",precision=17, scale=2)
	private BigDecimal formervalue;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="LOCATION",length=200)
	private String location;

	@Column(name="RATE",precision=17, scale=2)
	private BigDecimal rate;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="UPDATEDATE", length=20)
	private String updatedate;

	@Column(name="UPTODATE", length=20)
	private String uptodate;

	@Column(name="USEMETHOD",length=18)
	private String usemethod;

    public EntFixedasset() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public BigDecimal getArea() {
		return this.area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getCertificateno() {
		return this.certificateno;
	}

	public void setCertificateno(String certificateno) {
		this.certificateno = certificateno;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDepreciation() {
		return this.depreciation;
	}

	public void setDepreciation(String depreciation) {
		this.depreciation = depreciation;
	}

	public BigDecimal getDiscountrate() {
		return this.discountrate;
	}

	public void setDiscountrate(BigDecimal discountrate) {
		this.discountrate = discountrate;
	}

	public String getEvaldate() {
		return this.evaldate;
	}

	public void setEvaldate(String evaldate) {
		this.evaldate = evaldate;
	}

	public String getEvalorg() {
		return this.evalorg;
	}

	public void setEvalorg(String evalorg) {
		this.evalorg = evalorg;
	}

	public BigDecimal getEvalvalue() {
		return this.evalvalue;
	}

	public void setEvalvalue(BigDecimal evalvalue) {
		this.evalvalue = evalvalue;
	}

	public String getFixedassetsname() {
		return this.fixedassetsname;
	}

	public void setFixedassetsname(String fixedassetsname) {
		this.fixedassetsname = fixedassetsname;
	}

	public String getFixedassetsstatus() {
		return this.fixedassetsstatus;
	}

	public void setFixedassetsstatus(String fixedassetsstatus) {
		this.fixedassetsstatus = fixedassetsstatus;
	}

	public String getFixedassetstype() {
		return this.fixedassetstype;
	}

	public void setFixedassetstype(String fixedassetstype) {
		this.fixedassetstype = fixedassetstype;
	}

	public BigDecimal getFormervalue() {
		return this.formervalue;
	}

	public void setFormervalue(BigDecimal formervalue) {
		this.formervalue = formervalue;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public String getUptodate() {
		return this.uptodate;
	}

	public void setUptodate(String uptodate) {
		this.uptodate = uptodate;
	}

	public String getUsemethod() {
		return this.usemethod;
	}

	public void setUsemethod(String usemethod) {
		this.usemethod = usemethod;
	}

}