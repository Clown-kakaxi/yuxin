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
 * The persistent class for the CUSTOMER_REALTY database table.
 * 
 */
@Entity
@Table(name="M_HL_REALTY")
public class CustomerRealty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="BUILDPRICE",precision=17, scale=2)
	private BigDecimal buildprice;

	@Column(name="CERTIFICATENO",length=32)
	private String certificateno;

	@Column(name="EVALUATEPRICE",precision=17, scale=2)
	private BigDecimal evaluateprice;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="MORTAGAGE",length=18)
	private String mortagage;

	@Column(name="PURCHASEDATE",length=20)
	private String purchasedate;

	@Column(name="REALTYADD",length=80)
	private String realtyadd;

	@Column(name="REALTYAREA",precision=17, scale=2)
	private BigDecimal realtyarea;

	@Column(name="REALTYATTRIBUTE",length=18)
	private String realtyattribute;

	@Column(name="REALTYFLOOR")
	private Long realtyfloor;

	@Column(name="REALTYNAME",length=80)
	private String realtyname;

	@Column(name="SALEDATE",length=20)
	private String saledate;

	@Column(name="SHAREPROP",precision=5, scale=2)
	private BigDecimal shareprop;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="UPDATEDATE", length=20)
	private String updatedate;

	@Column(name="UPTODATE", length=20)
	private String uptodate;

    public CustomerRealty() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public BigDecimal getBuildprice() {
		return this.buildprice;
	}

	public void setBuildprice(BigDecimal buildprice) {
		this.buildprice = buildprice;
	}

	public String getCertificateno() {
		return this.certificateno;
	}

	public void setCertificateno(String certificateno) {
		this.certificateno = certificateno;
	}

	public BigDecimal getEvaluateprice() {
		return this.evaluateprice;
	}

	public void setEvaluateprice(BigDecimal evaluateprice) {
		this.evaluateprice = evaluateprice;
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

	public String getMortagage() {
		return this.mortagage;
	}

	public void setMortagage(String mortagage) {
		this.mortagage = mortagage;
	}

	public String getPurchasedate() {
		return this.purchasedate;
	}

	public void setPurchasedate(String purchasedate) {
		this.purchasedate = purchasedate;
	}

	public String getRealtyadd() {
		return this.realtyadd;
	}

	public void setRealtyadd(String realtyadd) {
		this.realtyadd = realtyadd;
	}

	public BigDecimal getRealtyarea() {
		return this.realtyarea;
	}

	public void setRealtyarea(BigDecimal realtyarea) {
		this.realtyarea = realtyarea;
	}

	public String getRealtyattribute() {
		return this.realtyattribute;
	}

	public void setRealtyattribute(String realtyattribute) {
		this.realtyattribute = realtyattribute;
	}

	public Long getRealtyfloor() {
		return this.realtyfloor;
	}

	public void setRealtyfloor(Long realtyfloor) {
		this.realtyfloor = realtyfloor;
	}

	public String getRealtyname() {
		return this.realtyname;
	}

	public void setRealtyname(String realtyname) {
		this.realtyname = realtyname;
	}

	public String getSaledate() {
		return this.saledate;
	}

	public void setSaledate(String saledate) {
		this.saledate = saledate;
	}

	public BigDecimal getShareprop() {
		return this.shareprop;
	}

	public void setShareprop(BigDecimal shareprop) {
		this.shareprop = shareprop;
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

}