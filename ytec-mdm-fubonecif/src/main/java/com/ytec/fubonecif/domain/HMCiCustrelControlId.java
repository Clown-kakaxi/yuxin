package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiCustrelControlId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiCustrelControlId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiCustrelControlId() {
	}

	/** minimal constructor */
	public HMCiCustrelControlId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiCustrelControlId(String custRelId, BigDecimal stockNum,
			Double stockAmt, String stockCurr, Double stockPercent,
			Date stockDate, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "CUST_REL_ID", length = 20)
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

	@Column(name = "HIS_OPER_SYS", length = 20)
	public String getHisOperSys() {
		return this.hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	@Column(name = "HIS_OPER_TYPE", length = 2)
	public String getHisOperType() {
		return this.hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	@Column(name = "HIS_DATA_DATE", length = 10)
	public String getHisDataDate() {
		return this.hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HMCiCustrelControlId))
			return false;
		HMCiCustrelControlId castOther = (HMCiCustrelControlId) other;

		return ((this.getCustRelId() == castOther.getCustRelId()) || (this
				.getCustRelId() != null
				&& castOther.getCustRelId() != null && this.getCustRelId()
				.equals(castOther.getCustRelId())))
				&& ((this.getStockNum() == castOther.getStockNum()) || (this
						.getStockNum() != null
						&& castOther.getStockNum() != null && this
						.getStockNum().equals(castOther.getStockNum())))
				&& ((this.getStockAmt() == castOther.getStockAmt()) || (this
						.getStockAmt() != null
						&& castOther.getStockAmt() != null && this
						.getStockAmt().equals(castOther.getStockAmt())))
				&& ((this.getStockCurr() == castOther.getStockCurr()) || (this
						.getStockCurr() != null
						&& castOther.getStockCurr() != null && this
						.getStockCurr().equals(castOther.getStockCurr())))
				&& ((this.getStockPercent() == castOther.getStockPercent()) || (this
						.getStockPercent() != null
						&& castOther.getStockPercent() != null && this
						.getStockPercent().equals(castOther.getStockPercent())))
				&& ((this.getStockDate() == castOther.getStockDate()) || (this
						.getStockDate() != null
						&& castOther.getStockDate() != null && this
						.getStockDate().equals(castOther.getStockDate())))
				&& ((this.getLastUpdateSys() == castOther.getLastUpdateSys()) || (this
						.getLastUpdateSys() != null
						&& castOther.getLastUpdateSys() != null && this
						.getLastUpdateSys()
						.equals(castOther.getLastUpdateSys())))
				&& ((this.getLastUpdateUser() == castOther.getLastUpdateUser()) || (this
						.getLastUpdateUser() != null
						&& castOther.getLastUpdateUser() != null && this
						.getLastUpdateUser().equals(
								castOther.getLastUpdateUser())))
				&& ((this.getLastUpdateTm() == castOther.getLastUpdateTm()) || (this
						.getLastUpdateTm() != null
						&& castOther.getLastUpdateTm() != null && this
						.getLastUpdateTm().equals(castOther.getLastUpdateTm())))
				&& ((this.getTxSeqNo() == castOther.getTxSeqNo()) || (this
						.getTxSeqNo() != null
						&& castOther.getTxSeqNo() != null && this.getTxSeqNo()
						.equals(castOther.getTxSeqNo())))
				&& ((this.getHisOperSys() == castOther.getHisOperSys()) || (this
						.getHisOperSys() != null
						&& castOther.getHisOperSys() != null && this
						.getHisOperSys().equals(castOther.getHisOperSys())))
				&& ((this.getHisOperType() == castOther.getHisOperType()) || (this
						.getHisOperType() != null
						&& castOther.getHisOperType() != null && this
						.getHisOperType().equals(castOther.getHisOperType())))
				&& ((this.getHisOperTime() == castOther.getHisOperTime()) || (this
						.getHisOperTime() != null
						&& castOther.getHisOperTime() != null && this
						.getHisOperTime().equals(castOther.getHisOperTime())))
				&& ((this.getHisDataDate() == castOther.getHisDataDate()) || (this
						.getHisDataDate() != null
						&& castOther.getHisDataDate() != null && this
						.getHisDataDate().equals(castOther.getHisDataDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCustRelId() == null ? 0 : this.getCustRelId().hashCode());
		result = 37 * result
				+ (getStockNum() == null ? 0 : this.getStockNum().hashCode());
		result = 37 * result
				+ (getStockAmt() == null ? 0 : this.getStockAmt().hashCode());
		result = 37 * result
				+ (getStockCurr() == null ? 0 : this.getStockCurr().hashCode());
		result = 37
				* result
				+ (getStockPercent() == null ? 0 : this.getStockPercent()
						.hashCode());
		result = 37 * result
				+ (getStockDate() == null ? 0 : this.getStockDate().hashCode());
		result = 37
				* result
				+ (getLastUpdateSys() == null ? 0 : this.getLastUpdateSys()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateUser() == null ? 0 : this.getLastUpdateUser()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateTm() == null ? 0 : this.getLastUpdateTm()
						.hashCode());
		result = 37 * result
				+ (getTxSeqNo() == null ? 0 : this.getTxSeqNo().hashCode());
		result = 37
				* result
				+ (getHisOperSys() == null ? 0 : this.getHisOperSys()
						.hashCode());
		result = 37
				* result
				+ (getHisOperType() == null ? 0 : this.getHisOperType()
						.hashCode());
		result = 37
				* result
				+ (getHisOperTime() == null ? 0 : this.getHisOperTime()
						.hashCode());
		result = 37
				* result
				+ (getHisDataDate() == null ? 0 : this.getHisDataDate()
						.hashCode());
		return result;
	}

}