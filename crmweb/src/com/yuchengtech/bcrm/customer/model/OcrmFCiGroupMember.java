package com.yuchengtech.bcrm.customer.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_CI_GROUP_MEMBER database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_GROUP_MEMBER")
public class OcrmFCiGroupMember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_GROUP_MEMBER_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_GROUP_MEMBER_ID_GENERATOR")
	private Long id;

	@Column(name="CORE_CUST_NO")
	private String coreCustNo;

	@Column(name="CORP_NAME_UP")
	private String corpNameUp;

	@Column(name="CROP_CODE")
	private String cropCode;

	@Column(name="CUS_MANAGER")
	private String cusManager;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_SCALE")
	private BigDecimal custScale;

	@Column(name="CUST_SCALE_CHECK")
	private BigDecimal custScaleCheck;

	@Column(name="CUST_STAT")
	private String custStat;

	@Column(name="GROUP_NO")
	private String groupNo;

	@Column(name="GRP_CORRE_DETAIL")
	private String grpCorreDetail;

	@Column(name="GRP_CORRE_TYPE")
	private String grpCorreType;

	private String industry;

	@Column(name="INPUT_BR_ID")
	private String inputBrId;

	@Temporal(TemporalType.DATE)
	@Column(name="INPUT_DATE")
	private Date inputDate;

	@Column(name="INPUT_USER_ID")
	private String inputUserId;

	@Column(name="LICENSE_NO")
	private String licenseNo;

	@Column(name="MAIN_BR_ID")
	private String mainBrId;

	@Column(name="MEMBER_SHIP")
	private String memberShip;

	@Column(name="MEMBER_TYPE")
	private String memberType;

	@Column(name="RELATIONSHIP_UP")
	private String relationshipUp;

	private String remark;

	@Column(name="STOCK_RATE")
	private BigDecimal stockRate;

	@Column(name="TAX_CERT_NO")
	private String taxCertNo;

	public OcrmFCiGroupMember() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCoreCustNo() {
		return this.coreCustNo;
	}

	public void setCoreCustNo(String coreCustNo) {
		this.coreCustNo = coreCustNo;
	}

	public String getCorpNameUp() {
		return this.corpNameUp;
	}

	public void setCorpNameUp(String corpNameUp) {
		this.corpNameUp = corpNameUp;
	}

	public String getCropCode() {
		return this.cropCode;
	}

	public void setCropCode(String cropCode) {
		this.cropCode = cropCode;
	}

	public String getCusManager() {
		return this.cusManager;
	}

	public void setCusManager(String cusManager) {
		this.cusManager = cusManager;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public BigDecimal getCustScale() {
		return this.custScale;
	}

	public void setCustScale(BigDecimal custScale) {
		this.custScale = custScale;
	}

	public BigDecimal getCustScaleCheck() {
		return this.custScaleCheck;
	}

	public void setCustScaleCheck(BigDecimal custScaleCheck) {
		this.custScaleCheck = custScaleCheck;
	}

	public String getCustStat() {
		return this.custStat;
	}

	public void setCustStat(String custStat) {
		this.custStat = custStat;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGrpCorreDetail() {
		return this.grpCorreDetail;
	}

	public void setGrpCorreDetail(String grpCorreDetail) {
		this.grpCorreDetail = grpCorreDetail;
	}

	public String getGrpCorreType() {
		return this.grpCorreType;
	}

	public void setGrpCorreType(String grpCorreType) {
		this.grpCorreType = grpCorreType;
	}

	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getInputBrId() {
		return this.inputBrId;
	}

	public void setInputBrId(String inputBrId) {
		this.inputBrId = inputBrId;
	}

	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputUserId() {
		return this.inputUserId;
	}

	public void setInputUserId(String inputUserId) {
		this.inputUserId = inputUserId;
	}

	public String getLicenseNo() {
		return this.licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getMainBrId() {
		return this.mainBrId;
	}

	public void setMainBrId(String mainBrId) {
		this.mainBrId = mainBrId;
	}

	public String getMemberShip() {
		return this.memberShip;
	}

	public void setMemberShip(String memberShip) {
		this.memberShip = memberShip;
	}

	public String getMemberType() {
		return this.memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getRelationshipUp() {
		return this.relationshipUp;
	}

	public void setRelationshipUp(String relationshipUp) {
		this.relationshipUp = relationshipUp;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getStockRate() {
		return this.stockRate;
	}

	public void setStockRate(BigDecimal stockRate) {
		this.stockRate = stockRate;
	}

	public String getTaxCertNo() {
		return this.taxCertNo;
	}

	public void setTaxCertNo(String taxCertNo) {
		this.taxCertNo = taxCertNo;
	}

}