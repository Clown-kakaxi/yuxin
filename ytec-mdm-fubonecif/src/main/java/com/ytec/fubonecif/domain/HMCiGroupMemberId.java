package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiGroupMemberId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiGroupMemberId implements java.io.Serializable {

	// Fields

	private BigDecimal memberId;
	private BigDecimal groupId;
	private String custId;
	private Double stockRate;
	private String memberType;
	private String custName;
	private String cropCode;
	private String corpNameUp;
	private String relationshipUp;
	private String custStat;
	private String industry;
	private BigDecimal custScale;
	private BigDecimal custScaleCheck;
	private String taxCertNo;
	private String licenseNo;
	private String memberShip;
	private String mainBrId;
	private String cusManagerId;
	private String grpCorreType;
	private String grpCorreDetail;
	private String inputUserId;
	private Date inputDate;
	private String inputBrId;
	private String remark;
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
	public HMCiGroupMemberId() {
	}

	/** minimal constructor */
	public HMCiGroupMemberId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiGroupMemberId(BigDecimal memberId, BigDecimal groupId,
			String custId, Double stockRate, String memberType,
			String custName, String cropCode, String corpNameUp,
			String relationshipUp, String custStat, String industry,
			BigDecimal custScale, BigDecimal custScaleCheck, String taxCertNo,
			String licenseNo, String memberShip, String mainBrId,
			String cusManagerId, String grpCorreType, String grpCorreDetail,
			String inputUserId, Date inputDate, String inputBrId,
			String remark, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
		this.memberId = memberId;
		this.groupId = groupId;
		this.custId = custId;
		this.stockRate = stockRate;
		this.memberType = memberType;
		this.custName = custName;
		this.cropCode = cropCode;
		this.corpNameUp = corpNameUp;
		this.relationshipUp = relationshipUp;
		this.custStat = custStat;
		this.industry = industry;
		this.custScale = custScale;
		this.custScaleCheck = custScaleCheck;
		this.taxCertNo = taxCertNo;
		this.licenseNo = licenseNo;
		this.memberShip = memberShip;
		this.mainBrId = mainBrId;
		this.cusManagerId = cusManagerId;
		this.grpCorreType = grpCorreType;
		this.grpCorreDetail = grpCorreDetail;
		this.inputUserId = inputUserId;
		this.inputDate = inputDate;
		this.inputBrId = inputBrId;
		this.remark = remark;
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

	@Column(name = "MEMBER_ID", precision = 22, scale = 0)
	public BigDecimal getMemberId() {
		return this.memberId;
	}

	public void setMemberId(BigDecimal memberId) {
		this.memberId = memberId;
	}

	@Column(name = "GROUP_ID", precision = 22, scale = 0)
	public BigDecimal getGroupId() {
		return this.groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "STOCK_RATE", precision = 10, scale = 4)
	public Double getStockRate() {
		return this.stockRate;
	}

	public void setStockRate(Double stockRate) {
		this.stockRate = stockRate;
	}

	@Column(name = "MEMBER_TYPE", length = 20)
	public String getMemberType() {
		return this.memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	@Column(name = "CUST_NAME", length = 80)
	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Column(name = "CROP_CODE", length = 40)
	public String getCropCode() {
		return this.cropCode;
	}

	public void setCropCode(String cropCode) {
		this.cropCode = cropCode;
	}

	@Column(name = "CORP_NAME_UP", length = 80)
	public String getCorpNameUp() {
		return this.corpNameUp;
	}

	public void setCorpNameUp(String corpNameUp) {
		this.corpNameUp = corpNameUp;
	}

	@Column(name = "RELATIONSHIP_UP", length = 20)
	public String getRelationshipUp() {
		return this.relationshipUp;
	}

	public void setRelationshipUp(String relationshipUp) {
		this.relationshipUp = relationshipUp;
	}

	@Column(name = "CUST_STAT", length = 20)
	public String getCustStat() {
		return this.custStat;
	}

	public void setCustStat(String custStat) {
		this.custStat = custStat;
	}

	@Column(name = "INDUSTRY", length = 20)
	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@Column(name = "CUST_SCALE", precision = 22, scale = 0)
	public BigDecimal getCustScale() {
		return this.custScale;
	}

	public void setCustScale(BigDecimal custScale) {
		this.custScale = custScale;
	}

	@Column(name = "CUST_SCALE_CHECK", precision = 22, scale = 0)
	public BigDecimal getCustScaleCheck() {
		return this.custScaleCheck;
	}

	public void setCustScaleCheck(BigDecimal custScaleCheck) {
		this.custScaleCheck = custScaleCheck;
	}

	@Column(name = "TAX_CERT_NO", length = 40)
	public String getTaxCertNo() {
		return this.taxCertNo;
	}

	public void setTaxCertNo(String taxCertNo) {
		this.taxCertNo = taxCertNo;
	}

	@Column(name = "LICENSE_NO", length = 40)
	public String getLicenseNo() {
		return this.licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	@Column(name = "MEMBER_SHIP", length = 20)
	public String getMemberShip() {
		return this.memberShip;
	}

	public void setMemberShip(String memberShip) {
		this.memberShip = memberShip;
	}

	@Column(name = "MAIN_BR_ID", length = 20)
	public String getMainBrId() {
		return this.mainBrId;
	}

	public void setMainBrId(String mainBrId) {
		this.mainBrId = mainBrId;
	}

	@Column(name = "CUS_MANAGER_ID", length = 20)
	public String getCusManagerId() {
		return this.cusManagerId;
	}

	public void setCusManagerId(String cusManagerId) {
		this.cusManagerId = cusManagerId;
	}

	@Column(name = "GRP_CORRE_TYPE", length = 20)
	public String getGrpCorreType() {
		return this.grpCorreType;
	}

	public void setGrpCorreType(String grpCorreType) {
		this.grpCorreType = grpCorreType;
	}

	@Column(name = "GRP_CORRE_DETAIL", length = 200)
	public String getGrpCorreDetail() {
		return this.grpCorreDetail;
	}

	public void setGrpCorreDetail(String grpCorreDetail) {
		this.grpCorreDetail = grpCorreDetail;
	}

	@Column(name = "INPUT_USER_ID", length = 20)
	public String getInputUserId() {
		return this.inputUserId;
	}

	public void setInputUserId(String inputUserId) {
		this.inputUserId = inputUserId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INPUT_DATE", length = 7)
	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	@Column(name = "INPUT_BR_ID", length = 20)
	public String getInputBrId() {
		return this.inputBrId;
	}

	public void setInputBrId(String inputBrId) {
		this.inputBrId = inputBrId;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		if (!(other instanceof HMCiGroupMemberId))
			return false;
		HMCiGroupMemberId castOther = (HMCiGroupMemberId) other;

		return ((this.getMemberId() == castOther.getMemberId()) || (this
				.getMemberId() != null
				&& castOther.getMemberId() != null && this.getMemberId()
				.equals(castOther.getMemberId())))
				&& ((this.getGroupId() == castOther.getGroupId()) || (this
						.getGroupId() != null
						&& castOther.getGroupId() != null && this.getGroupId()
						.equals(castOther.getGroupId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getStockRate() == castOther.getStockRate()) || (this
						.getStockRate() != null
						&& castOther.getStockRate() != null && this
						.getStockRate().equals(castOther.getStockRate())))
				&& ((this.getMemberType() == castOther.getMemberType()) || (this
						.getMemberType() != null
						&& castOther.getMemberType() != null && this
						.getMemberType().equals(castOther.getMemberType())))
				&& ((this.getCustName() == castOther.getCustName()) || (this
						.getCustName() != null
						&& castOther.getCustName() != null && this
						.getCustName().equals(castOther.getCustName())))
				&& ((this.getCropCode() == castOther.getCropCode()) || (this
						.getCropCode() != null
						&& castOther.getCropCode() != null && this
						.getCropCode().equals(castOther.getCropCode())))
				&& ((this.getCorpNameUp() == castOther.getCorpNameUp()) || (this
						.getCorpNameUp() != null
						&& castOther.getCorpNameUp() != null && this
						.getCorpNameUp().equals(castOther.getCorpNameUp())))
				&& ((this.getRelationshipUp() == castOther.getRelationshipUp()) || (this
						.getRelationshipUp() != null
						&& castOther.getRelationshipUp() != null && this
						.getRelationshipUp().equals(
								castOther.getRelationshipUp())))
				&& ((this.getCustStat() == castOther.getCustStat()) || (this
						.getCustStat() != null
						&& castOther.getCustStat() != null && this
						.getCustStat().equals(castOther.getCustStat())))
				&& ((this.getIndustry() == castOther.getIndustry()) || (this
						.getIndustry() != null
						&& castOther.getIndustry() != null && this
						.getIndustry().equals(castOther.getIndustry())))
				&& ((this.getCustScale() == castOther.getCustScale()) || (this
						.getCustScale() != null
						&& castOther.getCustScale() != null && this
						.getCustScale().equals(castOther.getCustScale())))
				&& ((this.getCustScaleCheck() == castOther.getCustScaleCheck()) || (this
						.getCustScaleCheck() != null
						&& castOther.getCustScaleCheck() != null && this
						.getCustScaleCheck().equals(
								castOther.getCustScaleCheck())))
				&& ((this.getTaxCertNo() == castOther.getTaxCertNo()) || (this
						.getTaxCertNo() != null
						&& castOther.getTaxCertNo() != null && this
						.getTaxCertNo().equals(castOther.getTaxCertNo())))
				&& ((this.getLicenseNo() == castOther.getLicenseNo()) || (this
						.getLicenseNo() != null
						&& castOther.getLicenseNo() != null && this
						.getLicenseNo().equals(castOther.getLicenseNo())))
				&& ((this.getMemberShip() == castOther.getMemberShip()) || (this
						.getMemberShip() != null
						&& castOther.getMemberShip() != null && this
						.getMemberShip().equals(castOther.getMemberShip())))
				&& ((this.getMainBrId() == castOther.getMainBrId()) || (this
						.getMainBrId() != null
						&& castOther.getMainBrId() != null && this
						.getMainBrId().equals(castOther.getMainBrId())))
				&& ((this.getCusManagerId() == castOther.getCusManagerId()) || (this
						.getCusManagerId() != null
						&& castOther.getCusManagerId() != null && this
						.getCusManagerId().equals(castOther.getCusManagerId())))
				&& ((this.getGrpCorreType() == castOther.getGrpCorreType()) || (this
						.getGrpCorreType() != null
						&& castOther.getGrpCorreType() != null && this
						.getGrpCorreType().equals(castOther.getGrpCorreType())))
				&& ((this.getGrpCorreDetail() == castOther.getGrpCorreDetail()) || (this
						.getGrpCorreDetail() != null
						&& castOther.getGrpCorreDetail() != null && this
						.getGrpCorreDetail().equals(
								castOther.getGrpCorreDetail())))
				&& ((this.getInputUserId() == castOther.getInputUserId()) || (this
						.getInputUserId() != null
						&& castOther.getInputUserId() != null && this
						.getInputUserId().equals(castOther.getInputUserId())))
				&& ((this.getInputDate() == castOther.getInputDate()) || (this
						.getInputDate() != null
						&& castOther.getInputDate() != null && this
						.getInputDate().equals(castOther.getInputDate())))
				&& ((this.getInputBrId() == castOther.getInputBrId()) || (this
						.getInputBrId() != null
						&& castOther.getInputBrId() != null && this
						.getInputBrId().equals(castOther.getInputBrId())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())))
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
				+ (getMemberId() == null ? 0 : this.getMemberId().hashCode());
		result = 37 * result
				+ (getGroupId() == null ? 0 : this.getGroupId().hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37 * result
				+ (getStockRate() == null ? 0 : this.getStockRate().hashCode());
		result = 37
				* result
				+ (getMemberType() == null ? 0 : this.getMemberType()
						.hashCode());
		result = 37 * result
				+ (getCustName() == null ? 0 : this.getCustName().hashCode());
		result = 37 * result
				+ (getCropCode() == null ? 0 : this.getCropCode().hashCode());
		result = 37
				* result
				+ (getCorpNameUp() == null ? 0 : this.getCorpNameUp()
						.hashCode());
		result = 37
				* result
				+ (getRelationshipUp() == null ? 0 : this.getRelationshipUp()
						.hashCode());
		result = 37 * result
				+ (getCustStat() == null ? 0 : this.getCustStat().hashCode());
		result = 37 * result
				+ (getIndustry() == null ? 0 : this.getIndustry().hashCode());
		result = 37 * result
				+ (getCustScale() == null ? 0 : this.getCustScale().hashCode());
		result = 37
				* result
				+ (getCustScaleCheck() == null ? 0 : this.getCustScaleCheck()
						.hashCode());
		result = 37 * result
				+ (getTaxCertNo() == null ? 0 : this.getTaxCertNo().hashCode());
		result = 37 * result
				+ (getLicenseNo() == null ? 0 : this.getLicenseNo().hashCode());
		result = 37
				* result
				+ (getMemberShip() == null ? 0 : this.getMemberShip()
						.hashCode());
		result = 37 * result
				+ (getMainBrId() == null ? 0 : this.getMainBrId().hashCode());
		result = 37
				* result
				+ (getCusManagerId() == null ? 0 : this.getCusManagerId()
						.hashCode());
		result = 37
				* result
				+ (getGrpCorreType() == null ? 0 : this.getGrpCorreType()
						.hashCode());
		result = 37
				* result
				+ (getGrpCorreDetail() == null ? 0 : this.getGrpCorreDetail()
						.hashCode());
		result = 37
				* result
				+ (getInputUserId() == null ? 0 : this.getInputUserId()
						.hashCode());
		result = 37 * result
				+ (getInputDate() == null ? 0 : this.getInputDate().hashCode());
		result = 37 * result
				+ (getInputBrId() == null ? 0 : this.getInputBrId().hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
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