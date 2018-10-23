package com.yuchengtech.bcrm.model;

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
 * The persistent class for the OCRM_F_CI_ALIANCE_PROGRAM database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_ALIANCE_PROGRAM")
public class OcrmFCiAlianceProgram implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_ALIANCE_PROGRAM_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_ALIANCE_PROGRAM_ID_GENERATOR")
	@Column(name="ID",unique=true, nullable=false)
	private Long id;
	
	@Column(name="ALIANCE_PROGRAM_ID")
	private String alianceProgramId;

	@Column(name="ALIANCE_EN_NAME")
	private String alianceEnName;

	@Column(name="ALIANCE_PROG_LEVEL")
	private String alianceProgLevel;

	@Column(name="ALIANCE_PROGRAM_NAME")
	private String alianceProgramName;

	@Column(name="APPLI_MAN")
	private String appliMan;

	@Column(name="CALL_NUM")
	private String callNum;

	@Column(name="COMPANY_TYPE")
	private String companyType;

	@Column(name="CORP_NO")
	private String corpNo;

	@Temporal(TemporalType.DATE)
	@Column(name="DELAY_END_DATE")
	private Date delayEndDate;

	@Column(name="ECONOMIC_TYPE")
	private String economicType;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="OPERATE_SITE")
	private String operateSite;

	@Column(name="ORGAN_CODE")
	private String organCode;

	@Column(name="OUT_REASON")
	private String outReason;

	@Column(name="POST_NO")
	private String postNo;

	@Column(name="REGIST_ADDRESS")
	private String registAddress;

	@Column(name="REGIST_CAPITAL")
	private BigDecimal registCapital;

	private String remark;

	@Column(name="SERVICE_CHARACT")
	private String serviceCharact;

	@Column(name="SERVICE_RANGE")
	private String serviceRange;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="STATE")
	private String state;
	
	@Column(name="SERVICE_RANGE_NAME")
	private String serviceRangeName;

	@Column(name="WORK_ADDRESS")
	private String workAddress;

	public OcrmFCiAlianceProgram() {
	}

	public String getAlianceEnName() {
		return this.alianceEnName;
	}

	public String getServiceRangeName() {
		return serviceRangeName;
	}

	public void setServiceRangeName(String serviceRangeName) {
		this.serviceRangeName = serviceRangeName;
	}

	public void setAlianceEnName(String alianceEnName) {
		this.alianceEnName = alianceEnName;
	}

	public String getAlianceProgLevel() {
		return this.alianceProgLevel;
	}

	public void setAlianceProgLevel(String alianceProgLevel) {
		this.alianceProgLevel = alianceProgLevel;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAlianceProgramId() {
		return this.alianceProgramId;
	}

	public void setAlianceProgramId(String alianceProgramId) {
		this.alianceProgramId = alianceProgramId;
	}

	public String getAlianceProgramName() {
		return this.alianceProgramName;
	}

	public void setAlianceProgramName(String alianceProgramName) {
		this.alianceProgramName = alianceProgramName;
	}

	public String getAppliMan() {
		return this.appliMan;
	}

	public void setAppliMan(String appliMan) {
		this.appliMan = appliMan;
	}

	public String getCallNum() {
		return this.callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	public String getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCorpNo() {
		return this.corpNo;
	}

	public void setCorpNo(String corpNo) {
		this.corpNo = corpNo;
	}

	public Date getDelayEndDate() {
		return this.delayEndDate;
	}

	public void setDelayEndDate(Date delayEndDate) {
		this.delayEndDate = delayEndDate;
	}

	public String getEconomicType() {
		return this.economicType;
	}

	public void setEconomicType(String economicType) {
		this.economicType = economicType;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOperateSite() {
		return this.operateSite;
	}

	public void setOperateSite(String operateSite) {
		this.operateSite = operateSite;
	}

	public String getOrganCode() {
		return this.organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getOutReason() {
		return this.outReason;
	}

	public void setOutReason(String outReason) {
		this.outReason = outReason;
	}

	public String getPostNo() {
		return this.postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getRegistAddress() {
		return this.registAddress;
	}

	public void setRegistAddress(String registAddress) {
		this.registAddress = registAddress;
	}

	public BigDecimal getRegistCapital() {
		return this.registCapital;
	}

	public void setRegistCapital(BigDecimal registCapital) {
		this.registCapital = registCapital;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getServiceCharact() {
		return this.serviceCharact;
	}

	public void setServiceCharact(String serviceCharact) {
		this.serviceCharact = serviceCharact;
	}

	public String getServiceRange() {
		return this.serviceRange;
	}

	public void setServiceRange(String serviceRange) {
		this.serviceRange = serviceRange;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWorkAddress() {
		return this.workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

}