package com.yuchengtech.emp.ecif.customer.entity.asset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;



/**
 * The persistent class for the BOND database table.
 * 
 */
@Entity
@Table(name="M_HL_BOND")
public class Bond implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="AMT",length=18)
	private String amt;

	@Column(name="BOND_KIND", length=18)
	private String bondKind;

	@Column(name="BOND_NAME", length=18)
	private String bondName;

	@Column(name="CURRENCY",length=20)
	private String currency;

	@Column(name="END_DATE",length=20)
	private String endDate;

	@Column(name="EXCHANGE_NAME", length=18)
	private String exchangeName;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM",length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="NUM", length=18)
	private String num;

	@Column(name="RATE",length=18)
	private String rate;

	@Column(name="SALE_DATE",length=20)
	private String saleDate;

	@Column(name="START_DATE", length=18)
	private String startDate;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Bond() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public String getAmt() {
		return this.amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getBondKind() {
		return this.bondKind;
	}

	public void setBondKind(String bondKind) {
		this.bondKind = bondKind;
	}

	public String getBondName() {
		return this.bondName;
	}

	public void setBondName(String bondName) {
		this.bondName = bondName;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getExchangeName() {
		return this.exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
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

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getRate() {
		return this.rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getSaleDate() {
		return this.saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}