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
 * The persistent class for the SME_INVEINFO database table.
 * 
 */
@Entity
@Table(name="M_HL_INVEINFO")
public class SmeInveinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="ASSETADDRESS",length=80)
	private String assetaddress;

	@Column(name="ASSETAREA",precision=17, scale=2)
	private BigDecimal assetarea;

	@Column(name="ASSETNAME",length=40)
	private String assetname;

	@Column(name="ASSETTYPE",length=20)
	private String assettype;

	@Column(name="EVALUATEPRICE",precision=17, scale=2)
	private BigDecimal evaluateprice;

	@Column(name="GUARANTYINFO",length=100)
	private String guarantyinfo;

	@Column(name="INSUREENTNAME",length=40)
	private String insureentname;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM",length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="PRORIGHTNO",length=40)
	private String prorightno;

	@Column(name="PURCHASEDATE", length=32)
	private String purchasedate;

	@Column(name="PURCHASEPRICE",precision=17, scale=2)
	private BigDecimal purchaseprice;

	@Column(name="SALEDATE", length=20)
	private String saledate;

    @Column(name="STATDATE", length=20)
	private String statdate;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public SmeInveinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public String getAssetaddress() {
		return this.assetaddress;
	}

	public void setAssetaddress(String assetaddress) {
		this.assetaddress = assetaddress;
	}

	public BigDecimal getAssetarea() {
		return this.assetarea;
	}

	public void setAssetarea(BigDecimal assetarea) {
		this.assetarea = assetarea;
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

	public BigDecimal getEvaluateprice() {
		return this.evaluateprice;
	}

	public void setEvaluateprice(BigDecimal evaluateprice) {
		this.evaluateprice = evaluateprice;
	}

	public String getGuarantyinfo() {
		return this.guarantyinfo;
	}

	public void setGuarantyinfo(String guarantyinfo) {
		this.guarantyinfo = guarantyinfo;
	}

	public String getInsureentname() {
		return this.insureentname;
	}

	public void setInsureentname(String insureentname) {
		this.insureentname = insureentname;
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

	public String getProrightno() {
		return this.prorightno;
	}

	public void setProrightno(String prorightno) {
		this.prorightno = prorightno;
	}

	public String getPurchasedate() {
		return this.purchasedate;
	}

	public void setPurchasedate(String purchasedate) {
		this.purchasedate = purchasedate;
	}

	public BigDecimal getPurchaseprice() {
		return this.purchaseprice;
	}

	public void setPurchaseprice(BigDecimal purchaseprice) {
		this.purchaseprice = purchaseprice;
	}

	public String getSaledate() {
		return this.saledate;
	}

	public void setSaledate(String saledate) {
		this.saledate = saledate;
	}

	public String getStatdate() {
		return this.statdate;
	}

	public void setStatdate(String statdate) {
		this.statdate = statdate;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}