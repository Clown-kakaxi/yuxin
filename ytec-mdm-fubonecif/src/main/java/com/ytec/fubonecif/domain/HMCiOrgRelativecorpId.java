package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiOrgRelativecorpId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgRelativecorpId implements java.io.Serializable {

	// Fields

	private String relativeCorpId;
	private String custId;
	private String relativeCorpName;
	private String orgCode;
	private String relationType;
	private String relationDesc;
	private Double totalAssets;
	private Double netAssets;
	private Double totalDebt;
	private Double netProfit;
	private String mainBusinessType;
	private Double mainBusinessIncome;
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
	public HMCiOrgRelativecorpId() {
	}

	/** minimal constructor */
	public HMCiOrgRelativecorpId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgRelativecorpId(String relativeCorpId, String custId,
			String relativeCorpName, String orgCode, String relationType,
			String relationDesc, Double totalAssets, Double netAssets,
			Double totalDebt, Double netProfit, String mainBusinessType,
			Double mainBusinessIncome, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.relativeCorpId = relativeCorpId;
		this.custId = custId;
		this.relativeCorpName = relativeCorpName;
		this.orgCode = orgCode;
		this.relationType = relationType;
		this.relationDesc = relationDesc;
		this.totalAssets = totalAssets;
		this.netAssets = netAssets;
		this.totalDebt = totalDebt;
		this.netProfit = netProfit;
		this.mainBusinessType = mainBusinessType;
		this.mainBusinessIncome = mainBusinessIncome;
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

	@Column(name = "RELATIVE_CORP_ID", length = 20)
	public String getRelativeCorpId() {
		return this.relativeCorpId;
	}

	public void setRelativeCorpId(String relativeCorpId) {
		this.relativeCorpId = relativeCorpId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "RELATIVE_CORP_NAME", length = 80)
	public String getRelativeCorpName() {
		return this.relativeCorpName;
	}

	public void setRelativeCorpName(String relativeCorpName) {
		this.relativeCorpName = relativeCorpName;
	}

	@Column(name = "ORG_CODE", length = 40)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "RELATION_TYPE", length = 20)
	public String getRelationType() {
		return this.relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	@Column(name = "RELATION_DESC", length = 100)
	public String getRelationDesc() {
		return this.relationDesc;
	}

	public void setRelationDesc(String relationDesc) {
		this.relationDesc = relationDesc;
	}

	@Column(name = "TOTAL_ASSETS", precision = 17)
	public Double getTotalAssets() {
		return this.totalAssets;
	}

	public void setTotalAssets(Double totalAssets) {
		this.totalAssets = totalAssets;
	}

	@Column(name = "NET_ASSETS", precision = 17)
	public Double getNetAssets() {
		return this.netAssets;
	}

	public void setNetAssets(Double netAssets) {
		this.netAssets = netAssets;
	}

	@Column(name = "TOTAL_DEBT", precision = 17)
	public Double getTotalDebt() {
		return this.totalDebt;
	}

	public void setTotalDebt(Double totalDebt) {
		this.totalDebt = totalDebt;
	}

	@Column(name = "NET_PROFIT", precision = 17)
	public Double getNetProfit() {
		return this.netProfit;
	}

	public void setNetProfit(Double netProfit) {
		this.netProfit = netProfit;
	}

	@Column(name = "MAIN_BUSINESS_TYPE", length = 20)
	public String getMainBusinessType() {
		return this.mainBusinessType;
	}

	public void setMainBusinessType(String mainBusinessType) {
		this.mainBusinessType = mainBusinessType;
	}

	@Column(name = "MAIN_BUSINESS_INCOME", precision = 17)
	public Double getMainBusinessIncome() {
		return this.mainBusinessIncome;
	}

	public void setMainBusinessIncome(Double mainBusinessIncome) {
		this.mainBusinessIncome = mainBusinessIncome;
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
		if (!(other instanceof HMCiOrgRelativecorpId))
			return false;
		HMCiOrgRelativecorpId castOther = (HMCiOrgRelativecorpId) other;

		return ((this.getRelativeCorpId() == castOther.getRelativeCorpId()) || (this
				.getRelativeCorpId() != null
				&& castOther.getRelativeCorpId() != null && this
				.getRelativeCorpId().equals(castOther.getRelativeCorpId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getRelativeCorpName() == castOther
						.getRelativeCorpName()) || (this.getRelativeCorpName() != null
						&& castOther.getRelativeCorpName() != null && this
						.getRelativeCorpName().equals(
								castOther.getRelativeCorpName())))
				&& ((this.getOrgCode() == castOther.getOrgCode()) || (this
						.getOrgCode() != null
						&& castOther.getOrgCode() != null && this.getOrgCode()
						.equals(castOther.getOrgCode())))
				&& ((this.getRelationType() == castOther.getRelationType()) || (this
						.getRelationType() != null
						&& castOther.getRelationType() != null && this
						.getRelationType().equals(castOther.getRelationType())))
				&& ((this.getRelationDesc() == castOther.getRelationDesc()) || (this
						.getRelationDesc() != null
						&& castOther.getRelationDesc() != null && this
						.getRelationDesc().equals(castOther.getRelationDesc())))
				&& ((this.getTotalAssets() == castOther.getTotalAssets()) || (this
						.getTotalAssets() != null
						&& castOther.getTotalAssets() != null && this
						.getTotalAssets().equals(castOther.getTotalAssets())))
				&& ((this.getNetAssets() == castOther.getNetAssets()) || (this
						.getNetAssets() != null
						&& castOther.getNetAssets() != null && this
						.getNetAssets().equals(castOther.getNetAssets())))
				&& ((this.getTotalDebt() == castOther.getTotalDebt()) || (this
						.getTotalDebt() != null
						&& castOther.getTotalDebt() != null && this
						.getTotalDebt().equals(castOther.getTotalDebt())))
				&& ((this.getNetProfit() == castOther.getNetProfit()) || (this
						.getNetProfit() != null
						&& castOther.getNetProfit() != null && this
						.getNetProfit().equals(castOther.getNetProfit())))
				&& ((this.getMainBusinessType() == castOther
						.getMainBusinessType()) || (this.getMainBusinessType() != null
						&& castOther.getMainBusinessType() != null && this
						.getMainBusinessType().equals(
								castOther.getMainBusinessType())))
				&& ((this.getMainBusinessIncome() == castOther
						.getMainBusinessIncome()) || (this
						.getMainBusinessIncome() != null
						&& castOther.getMainBusinessIncome() != null && this
						.getMainBusinessIncome().equals(
								castOther.getMainBusinessIncome())))
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
				+ (getRelativeCorpId() == null ? 0 : this.getRelativeCorpId()
						.hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getRelativeCorpName() == null ? 0 : this
						.getRelativeCorpName().hashCode());
		result = 37 * result
				+ (getOrgCode() == null ? 0 : this.getOrgCode().hashCode());
		result = 37
				* result
				+ (getRelationType() == null ? 0 : this.getRelationType()
						.hashCode());
		result = 37
				* result
				+ (getRelationDesc() == null ? 0 : this.getRelationDesc()
						.hashCode());
		result = 37
				* result
				+ (getTotalAssets() == null ? 0 : this.getTotalAssets()
						.hashCode());
		result = 37 * result
				+ (getNetAssets() == null ? 0 : this.getNetAssets().hashCode());
		result = 37 * result
				+ (getTotalDebt() == null ? 0 : this.getTotalDebt().hashCode());
		result = 37 * result
				+ (getNetProfit() == null ? 0 : this.getNetProfit().hashCode());
		result = 37
				* result
				+ (getMainBusinessType() == null ? 0 : this
						.getMainBusinessType().hashCode());
		result = 37
				* result
				+ (getMainBusinessIncome() == null ? 0 : this
						.getMainBusinessIncome().hashCode());
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