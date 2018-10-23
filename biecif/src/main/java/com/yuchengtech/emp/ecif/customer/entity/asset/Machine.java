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
 * The persistent class for the MACHINE database table.
 * 
 */
@Entity
@Table(name="M_HL_MACHINE")
public class Machine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="BUY_DATE",length=20)
	private String buyDate;

	@Column(name="LAST_MAINTAIN_TIME",length=20)
	private String lastMaintainTime;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM",length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="MACHINE_STAT", length=20)
	private String machineStat;

	@Column(name="MODEL",length=40)
	private String model;

	@Column(name="PRICE",precision=17, scale=2)
	private BigDecimal price;

	@Column(name="PRODUCE_DATE",length=20)
	private String produceDate;

	@Column(name="PROVIDER",length=100)
	private String provider;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="USEDATE", length=32)
	private String usedate;

    public Machine() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public String getBuyDate() {
		return this.buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public String getLastMaintainTime() {
		return this.lastMaintainTime;
	}

	public void setLastMaintainTime(String lastMaintainTime) {
		this.lastMaintainTime = lastMaintainTime;
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

	public String getMachineStat() {
		return this.machineStat;
	}

	public void setMachineStat(String machineStat) {
		this.machineStat = machineStat;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProduceDate() {
		return this.produceDate;
	}

	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getUsedate() {
		return this.usedate;
	}

	public void setUsedate(String usedate) {
		this.usedate = usedate;
	}

}