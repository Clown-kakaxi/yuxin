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
 * The persistent class for the ENT_INVENTORY database table.
 * 
 */
@Entity
@Table(name="M_HL_INVENTORY")
public class EntInventory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="BOOKUNITPRICE",precision=17, scale=2)
	private BigDecimal bookunitprice;

	@Column(name="DEPOTADD",length=80)
	private String depotadd;

	@Column(name="EVALDATE",length=20)
	private String evaldate;

	@Column(name="EVALORG",length=200)
	private String evalorg;

	@Column(name="EVALVALUE",precision=17, scale=2)
	private BigDecimal evalvalue;

	@Column(name="INDEPOTDATE",length=20)
	private String indepotdate;

	@Column(name="INVENTORYAMOUNT",length=32)
	private String inventoryamount;

	@Column(name="INVENTORYNAME",length=80)
	private String inventoryname;

	@Column(name="INVENTORYTYPE",length=18)
	private String inventorytype;

	@Column(name="INVENTORYUNIT",length=18)
	private String inventoryunit;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="MARKETUNITPRICE",precision=17, scale=2)
	private BigDecimal marketunitprice;

	@Column(name="QUALITYSTATUS",length=200)
	private String qualitystatus;

	@Column(name="RATE",precision=17, scale=2)
	private BigDecimal rate;

	@Column(name="RESERVESUM",precision=17, scale=2)
	private BigDecimal reservesum;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="UPDATEDATE", length=20)
	private String updatedate;

	@Column(name="UPTODATE", length=20)
	private String uptodate;

	@Column(name="VALUECURRENCY",length=20)
	private String valuecurrency;

	@Column(name="VALUESUM",precision=17, scale=2)
	private BigDecimal valuesum;

    public EntInventory() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public BigDecimal getBookunitprice() {
		return this.bookunitprice;
	}

	public void setBookunitprice(BigDecimal bookunitprice) {
		this.bookunitprice = bookunitprice;
	}

	public String getDepotadd() {
		return this.depotadd;
	}

	public void setDepotadd(String depotadd) {
		this.depotadd = depotadd;
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

	public String getIndepotdate() {
		return this.indepotdate;
	}

	public void setIndepotdate(String indepotdate) {
		this.indepotdate = indepotdate;
	}

	public String getInventoryamount() {
		return this.inventoryamount;
	}

	public void setInventoryamount(String inventoryamount) {
		this.inventoryamount = inventoryamount;
	}

	public String getInventoryname() {
		return this.inventoryname;
	}

	public void setInventoryname(String inventoryname) {
		this.inventoryname = inventoryname;
	}

	public String getInventorytype() {
		return this.inventorytype;
	}

	public void setInventorytype(String inventorytype) {
		this.inventorytype = inventorytype;
	}

	public String getInventoryunit() {
		return this.inventoryunit;
	}

	public void setInventoryunit(String inventoryunit) {
		this.inventoryunit = inventoryunit;
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

	public BigDecimal getMarketunitprice() {
		return this.marketunitprice;
	}

	public void setMarketunitprice(BigDecimal marketunitprice) {
		this.marketunitprice = marketunitprice;
	}

	public String getQualitystatus() {
		return this.qualitystatus;
	}

	public void setQualitystatus(String qualitystatus) {
		this.qualitystatus = qualitystatus;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getReservesum() {
		return this.reservesum;
	}

	public void setReservesum(BigDecimal reservesum) {
		this.reservesum = reservesum;
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

	public String getValuecurrency() {
		return this.valuecurrency;
	}

	public void setValuecurrency(String valuecurrency) {
		this.valuecurrency = valuecurrency;
	}

	public BigDecimal getValuesum() {
		return this.valuesum;
	}

	public void setValuesum(BigDecimal valuesum) {
		this.valuesum = valuesum;
	}

}