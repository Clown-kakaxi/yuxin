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
 * The persistent class for the STOCK database table.
 * 
 */
@Entity
@Table(name="M_HL_STOCK")
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="STOCK_CODE", length=20)
	private String stockCode;

	@Column(name="STOCK_CURR_VALUE", precision=17, scale=2)
	private BigDecimal stockCurrValue;

	@Column(name="STOCK_CURRENCY", length=20)
	private String stockCurrency;

	@Column(name="STOCK_NAME", length=80)
	private String stockName;

	@Column(name="STOCK_NUM")
	private Long stockNum;

	@Column(name="STOCK_TYPE", length=20)
	private String stockType;

	@Column(name="STOCKHOLD_CERT_NO", length=32)
	private String stockholdCertNo;

	@Column(name="STOCKHOLD_CERT_TYPE", length=20)
	private String stockholdCertType;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Stock() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
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

	public String getStockCode() {
		return this.stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public BigDecimal getStockCurrValue() {
		return this.stockCurrValue;
	}

	public void setStockCurrValue(BigDecimal stockCurrValue) {
		this.stockCurrValue = stockCurrValue;
	}

	public String getStockCurrency() {
		return this.stockCurrency;
	}

	public void setStockCurrency(String stockCurrency) {
		this.stockCurrency = stockCurrency;
	}

	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Long getStockNum() {
		return this.stockNum;
	}

	public void setStockNum(Long stockNum) {
		this.stockNum = stockNum;
	}

	public String getStockType() {
		return this.stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String getStockholdCertNo() {
		return this.stockholdCertNo;
	}

	public void setStockholdCertNo(String stockholdCertNo) {
		this.stockholdCertNo = stockholdCertNo;
	}

	public String getStockholdCertType() {
		return this.stockholdCertType;
	}

	public void setStockholdCertType(String stockholdCertType) {
		this.stockholdCertType = stockholdCertType;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}