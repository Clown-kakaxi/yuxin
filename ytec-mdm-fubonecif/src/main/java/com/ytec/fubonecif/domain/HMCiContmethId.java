package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiContmethId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiContmethId implements java.io.Serializable {

	// Fields

	private String contmethId;
	private String custId;
	private String isPriori;
	private String contmethType;
	private String contmethInfo;
	private BigDecimal contmethSeq;
	private String remark;
	private String stat;
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
	public HMCiContmethId() {
	}

	/** minimal constructor */
	public HMCiContmethId(String contmethId, Timestamp hisOperTime) {
		this.contmethId = contmethId;
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiContmethId(String contmethId, String custId, String isPriori,
			String contmethType, String contmethInfo, BigDecimal contmethSeq,
			String remark, String stat, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.contmethId = contmethId;
		this.custId = custId;
		this.isPriori = isPriori;
		this.contmethType = contmethType;
		this.contmethInfo = contmethInfo;
		this.contmethSeq = contmethSeq;
		this.remark = remark;
		this.stat = stat;
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

	@Column(name = "CONTMETH_ID", nullable = false, length = 20)
	public String getContmethId() {
		return this.contmethId;
	}

	public void setContmethId(String contmethId) {
		this.contmethId = contmethId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "IS_PRIORI", length = 1)
	public String getIsPriori() {
		return this.isPriori;
	}

	public void setIsPriori(String isPriori) {
		this.isPriori = isPriori;
	}

	@Column(name = "CONTMETH_TYPE", length = 20)
	public String getContmethType() {
		return this.contmethType;
	}

	public void setContmethType(String contmethType) {
		this.contmethType = contmethType;
	}

	@Column(name = "CONTMETH_INFO", length = 100)
	public String getContmethInfo() {
		return this.contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

	@Column(name = "CONTMETH_SEQ", precision = 22, scale = 0)
	public BigDecimal getContmethSeq() {
		return this.contmethSeq;
	}

	public void setContmethSeq(BigDecimal contmethSeq) {
		this.contmethSeq = contmethSeq;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "STAT", length = 20)
	public String getStat() {
		return this.stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
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

	@Column(name = "LAST_UPDATE_TM")
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

	@Column(name = "HIS_OPER_TIME", nullable = false)
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
		if (!(other instanceof HMCiContmethId))
			return false;
		HMCiContmethId castOther = (HMCiContmethId) other;

		return ((this.getContmethId() == castOther.getContmethId()) || (this
				.getContmethId() != null
				&& castOther.getContmethId() != null && this.getContmethId()
				.equals(castOther.getContmethId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getIsPriori() == castOther.getIsPriori()) || (this
						.getIsPriori() != null
						&& castOther.getIsPriori() != null && this
						.getIsPriori().equals(castOther.getIsPriori())))
				&& ((this.getContmethType() == castOther.getContmethType()) || (this
						.getContmethType() != null
						&& castOther.getContmethType() != null && this
						.getContmethType().equals(castOther.getContmethType())))
				&& ((this.getContmethInfo() == castOther.getContmethInfo()) || (this
						.getContmethInfo() != null
						&& castOther.getContmethInfo() != null && this
						.getContmethInfo().equals(castOther.getContmethInfo())))
				&& ((this.getContmethSeq() == castOther.getContmethSeq()) || (this
						.getContmethSeq() != null
						&& castOther.getContmethSeq() != null && this
						.getContmethSeq().equals(castOther.getContmethSeq())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())))
				&& ((this.getStat() == castOther.getStat()) || (this.getStat() != null
						&& castOther.getStat() != null && this.getStat()
						.equals(castOther.getStat())))
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

		result = 37
				* result
				+ (getContmethId() == null ? 0 : this.getContmethId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37 * result
				+ (getIsPriori() == null ? 0 : this.getIsPriori().hashCode());
		result = 37
				* result
				+ (getContmethType() == null ? 0 : this.getContmethType()
						.hashCode());
		result = 37
				* result
				+ (getContmethInfo() == null ? 0 : this.getContmethInfo()
						.hashCode());
		result = 37
				* result
				+ (getContmethSeq() == null ? 0 : this.getContmethSeq()
						.hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
		result = 37 * result
				+ (getStat() == null ? 0 : this.getStat().hashCode());
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