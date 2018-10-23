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
 * The persistent class for the CUSTOMER_VEHICLE database table.
 * 
 */
@Entity
@Table(name="M_HL_VEHICLE")
public class CustomerVehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="ENGINEID",length=32)
	private String engineid;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="LICENSENO",length=40)
	private String licenseno;

	@Column(name="MONTHREPAYMENT",precision=17, scale=2)
	private BigDecimal monthrepayment;

	@Column(name="PURCHASEDATE",length=20)
	private String purchasedate;

	@Column(name="PURCHASESTATE",length=18)
	private String purchasestate;

	@Column(name="PURCHASESUM",precision=17, scale=2)
	private BigDecimal purchasesum;

	@Column(name="RUNKM",precision=17, scale=2)
	private BigDecimal runkm;

	@Column(name="SALEDATE",length=20)
	private String saledate;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="UNDERSPANID",length=32)
	private String underspanid;

	@Column(name="UPDATEDATE", length=20)
	private String updatedate;

	@Column(name="UPTODATE", length=20)
	private String uptodate;

	@Column(name="VEHICLEBRAND",length=80)
	private String vehiclebrand;

	@Column(name="VEHICLECERT",length=40)
	private String vehiclecert;

	@Column(name="VEHICLELICENSE",length=32)
	private String vehiclelicense;

	@Column(name="VEHICLEREGFLAG",length=1)
	private String vehicleregflag;

	@Column(name="VEHICLESITUATION",length=200)
	private String vehiclesituation;

    public CustomerVehicle() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public String getEngineid() {
		return this.engineid;
	}

	public void setEngineid(String engineid) {
		this.engineid = engineid;
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

	public String getLicenseno() {
		return this.licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public BigDecimal getMonthrepayment() {
		return this.monthrepayment;
	}

	public void setMonthrepayment(BigDecimal monthrepayment) {
		this.monthrepayment = monthrepayment;
	}

	public String getPurchasedate() {
		return this.purchasedate;
	}

	public void setPurchasedate(String purchasedate) {
		this.purchasedate = purchasedate;
	}

	public String getPurchasestate() {
		return this.purchasestate;
	}

	public void setPurchasestate(String purchasestate) {
		this.purchasestate = purchasestate;
	}

	public BigDecimal getPurchasesum() {
		return this.purchasesum;
	}

	public void setPurchasesum(BigDecimal purchasesum) {
		this.purchasesum = purchasesum;
	}

	public BigDecimal getRunkm() {
		return this.runkm;
	}

	public void setRunkm(BigDecimal runkm) {
		this.runkm = runkm;
	}

	public String getSaledate() {
		return this.saledate;
	}

	public void setSaledate(String saledate) {
		this.saledate = saledate;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getUnderspanid() {
		return this.underspanid;
	}

	public void setUnderspanid(String underspanid) {
		this.underspanid = underspanid;
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

	public String getVehiclebrand() {
		return this.vehiclebrand;
	}

	public void setVehiclebrand(String vehiclebrand) {
		this.vehiclebrand = vehiclebrand;
	}

	public String getVehiclecert() {
		return this.vehiclecert;
	}

	public void setVehiclecert(String vehiclecert) {
		this.vehiclecert = vehiclecert;
	}

	public String getVehiclelicense() {
		return this.vehiclelicense;
	}

	public void setVehiclelicense(String vehiclelicense) {
		this.vehiclelicense = vehiclelicense;
	}

	public String getVehicleregflag() {
		return this.vehicleregflag;
	}

	public void setVehicleregflag(String vehicleregflag) {
		this.vehicleregflag = vehicleregflag;
	}

	public String getVehiclesituation() {
		return this.vehiclesituation;
	}

	public void setVehiclesituation(String vehiclesituation) {
		this.vehiclesituation = vehiclesituation;
	}

}