package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiPerIncomePayId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerIncomePayId implements java.io.Serializable {

	// Fields

	private String ioId;
	private String custId;
	private String ioFreq;
	private String ioType;
	private String detailType;
	private String curr;
	private Double amt;
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
	public HMCiPerIncomePayId() {
	}

	/** minimal constructor */
	public HMCiPerIncomePayId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerIncomePayId(String ioId, String custId, String ioFreq,
			String ioType, String detailType, String curr, Double amt,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.ioId = ioId;
		this.custId = custId;
		this.ioFreq = ioFreq;
		this.ioType = ioType;
		this.detailType = detailType;
		this.curr = curr;
		this.amt = amt;
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

	@Column(name = "IO_ID", length = 20)
	public String getIoId() {
		return this.ioId;
	}

	public void setIoId(String ioId) {
		this.ioId = ioId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "IO_FREQ", length = 20)
	public String getIoFreq() {
		return this.ioFreq;
	}

	public void setIoFreq(String ioFreq) {
		this.ioFreq = ioFreq;
	}

	@Column(name = "IO_TYPE", length = 20)
	public String getIoType() {
		return this.ioType;
	}

	public void setIoType(String ioType) {
		this.ioType = ioType;
	}

	@Column(name = "DETAIL_TYPE", length = 20)
	public String getDetailType() {
		return this.detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	@Column(name = "CURR", length = 20)
	public String getCurr() {
		return this.curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	@Column(name = "AMT", precision = 17)
	public Double getAmt() {
		return this.amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
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
		if (!(other instanceof HMCiPerIncomePayId))
			return false;
		HMCiPerIncomePayId castOther = (HMCiPerIncomePayId) other;

		return ((this.getIoId() == castOther.getIoId()) || (this.getIoId() != null
				&& castOther.getIoId() != null && this.getIoId().equals(
				castOther.getIoId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getIoFreq() == castOther.getIoFreq()) || (this
						.getIoFreq() != null
						&& castOther.getIoFreq() != null && this.getIoFreq()
						.equals(castOther.getIoFreq())))
				&& ((this.getIoType() == castOther.getIoType()) || (this
						.getIoType() != null
						&& castOther.getIoType() != null && this.getIoType()
						.equals(castOther.getIoType())))
				&& ((this.getDetailType() == castOther.getDetailType()) || (this
						.getDetailType() != null
						&& castOther.getDetailType() != null && this
						.getDetailType().equals(castOther.getDetailType())))
				&& ((this.getCurr() == castOther.getCurr()) || (this.getCurr() != null
						&& castOther.getCurr() != null && this.getCurr()
						.equals(castOther.getCurr())))
				&& ((this.getAmt() == castOther.getAmt()) || (this.getAmt() != null
						&& castOther.getAmt() != null && this.getAmt().equals(
						castOther.getAmt())))
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
				+ (getIoId() == null ? 0 : this.getIoId().hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37 * result
				+ (getIoFreq() == null ? 0 : this.getIoFreq().hashCode());
		result = 37 * result
				+ (getIoType() == null ? 0 : this.getIoType().hashCode());
		result = 37
				* result
				+ (getDetailType() == null ? 0 : this.getDetailType()
						.hashCode());
		result = 37 * result
				+ (getCurr() == null ? 0 : this.getCurr().hashCode());
		result = 37 * result
				+ (getAmt() == null ? 0 : this.getAmt().hashCode());
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