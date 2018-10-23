package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiCustrelControl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_CUSTREL_CONTROL")
public class MCiCustrelControl implements java.io.Serializable {

	// Fields

	private String custRelId;
	private BigDecimal stockNum;
	private Double stockAmt;
	private String stockCurr;
	private Double stockPercent;
	private Date stockDate;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiCustrelControl() {
	}

	/** minimal constructor */
	public MCiCustrelControl(String custRelId) {
		this.custRelId = custRelId;
	}

	/** full constructor */
	public MCiCustrelControl(String custRelId, BigDecimal stockNum,
			Double stockAmt, String stockCurr, Double stockPercent,
			Date stockDate, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custRelId = custRelId;
		this.stockNum = stockNum;
		this.stockAmt = stockAmt;
		this.stockCurr = stockCurr;
		this.stockPercent = stockPercent;
		this.stockDate = stockDate;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "CUST_REL_ID", unique = true, nullable = false, length = 20)
	public String getCustRelId() {
		return this.custRelId;
	}

	public void setCustRelId(String custRelId) {
		this.custRelId = custRelId;
	}

	@Column(name = "STOCK_NUM", scale = 0)
	public BigDecimal getStockNum() {
		return this.stockNum;
	}

	public void setStockNum(BigDecimal stockNum) {
		this.stockNum = stockNum;
	}

	@Column(name = "STOCK_AMT", precision = 17)
	public Double getStockAmt() {
		return this.stockAmt;
	}

	public void setStockAmt(Double stockAmt) {
		this.stockAmt = stockAmt;
	}

	@Column(name = "STOCK_CURR", length = 20)
	public String getStockCurr() {
		return this.stockCurr;
	}

	public void setStockCurr(String stockCurr) {
		this.stockCurr = stockCurr;
	}

	@Column(name = "STOCK_PERCENT", precision = 10, scale = 4)
	public Double getStockPercent() {
		return this.stockPercent;
	}

	public void setStockPercent(Double stockPercent) {
		this.stockPercent = stockPercent;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "STOCK_DATE", length = 7)
	public Date getStockDate() {
		return this.stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}