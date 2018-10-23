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
 * The persistent class for the CUSTOMER_IMASSET database table.
 * 
 */
@Entity
@Table(name="M_HL_IMASSETS")
public class CustomerImasset implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="ACCOUNTVALUE",precision=17, scale=2)
	private BigDecimal accountvalue;

	@Column(name="ASSETDESCRIBE",length=200)
	private String assetdescribe;

	@Column(name="ASSETNAME",length=80)
	private String assetname;

	@Column(name="ASSETTYPE",length=18)
	private String assettype;

	@Column(name="AUTHDATE",length=20)
	private String authdate;

	@Column(name="AUTHNO",length=32)
	private String authno;

	@Column(name="AUTHORG",length=80)
	private String authorg;

	@Column(name="EVALUATEMETHOD",length=200)
	private String evaluatemethod;

	@Column(name="EVALUATEVALUE",precision=17, scale=2)
	private BigDecimal evaluatevalue;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="UPDATEDATE", length=20)
	private String updatedate;

	@Column(name="UPTODATE", length=20)
	private String uptodate;

    public CustomerImasset() {
    }

	public Long getHoldingId() {
		return this.holdingId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public BigDecimal getAccountvalue() {
		return this.accountvalue;
	}

	public void setAccountvalue(BigDecimal accountvalue) {
		this.accountvalue = accountvalue;
	}

	public String getAssetdescribe() {
		return this.assetdescribe;
	}

	public void setAssetdescribe(String assetdescribe) {
		this.assetdescribe = assetdescribe;
	}

	public String getAssetname() {
		return this.assetname;
	}

	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}

	public String getAssettype() {
		return this.assettype;
	}

	public void setAssettype(String assettype) {
		this.assettype = assettype;
	}

	public String getAuthdate() {
		return this.authdate;
	}

	public void setAuthdate(String authdate) {
		this.authdate = authdate;
	}

	public String getAuthno() {
		return this.authno;
	}

	public void setAuthno(String authno) {
		this.authno = authno;
	}

	public String getAuthorg() {
		return this.authorg;
	}

	public void setAuthorg(String authorg) {
		this.authorg = authorg;
	}

	public String getEvaluatemethod() {
		return this.evaluatemethod;
	}

	public void setEvaluatemethod(String evaluatemethod) {
		this.evaluatemethod = evaluatemethod;
	}

	public BigDecimal getEvaluatevalue() {
		return this.evaluatevalue;
	}

	public void setEvaluatevalue(BigDecimal evaluatevalue) {
		this.evaluatevalue = evaluatevalue;
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