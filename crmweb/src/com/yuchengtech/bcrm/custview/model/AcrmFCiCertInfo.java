package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 对公客户证件主要信息 model
 * @author WuDi
 * @since 2013-7-17
 */
@Entity
@Table(name="OCRM_F_CI_CERT_INFO")
public class AcrmFCiCertInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name="IDENT_ID")	
	private Long identId;//证件ID

	@Column(name="COUNTRY_OR_REGION")
	private String countryOrRegion;

	@Column(name="CUST_ID")
	private String custId;//客户编号

	private String field1;//预留字段1

	private String field2;//预留字段2

	private String field3;//预留字段3

	private String field4;//预留字段4

	private String field5;//预留字段5

	private String field6;//预留字段6

	@Column(name="IDENT_APPROVE_UNIT")
	private String identApproveUnit;//证件批准单位

	@Column(name="IDENT_CHECK_FLAG")
	private String identCheckFlag;//证件年检标志

    @Temporal( TemporalType.DATE)
	@Column(name="IDENT_CHECKED_DATE")
	private Date identCheckedDate;//证件年检日期

    @Temporal( TemporalType.DATE)
	@Column(name="IDENT_CHECKING_DATE")
	private Date identCheckingDate;//证件年检到期日

	@Column(name="IDENT_CUST_NAME")
	private String identCustName;//证件户名

    @Temporal( TemporalType.DATE)
	@Column(name="IDENT_EFFECTIVE_DATE")
	private Date identEffectiveDate;//证件生效日期

    @Temporal( TemporalType.DATE)
	@Column(name="IDENT_EXPRIED_DATE")
	private Date identExpriedDate;//证件失效日期

	@Column(name="IDENT_NO")
	private String identNo;//证件号码

	@Column(name="IDENT_ORG")
	private String identOrg;//发证机构

	@Column(name="IDENT_PERIOD")
	private Integer identPeriod;//证件期限

	@Column(name="IDENT_TYPE")
	private String identType;//证件类型

	@Column(name="IDENT_VALID_FLAG")
	private String identValidFlag;//证件有效标志

    @Temporal( TemporalType.DATE)
	@Column(name="IDENT_VALID_PERIOD")
	private Date identValidPeriod;//证件有效期

    @Temporal( TemporalType.DATE)
	@Column(name="LASUPD_DATE")
	private Date lasupdDate;//维护日期

	@Column(name="LASUPD_OPR")
	private String lasupdOpr;//维护人

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="LASUPD_TIME")
	private Date lasupdTime;//维护时间

	public String getCountryOrRegion() {
		return this.countryOrRegion;
	}

	public void setCountryOrRegion(String countryOrRegion) {
		this.countryOrRegion = countryOrRegion;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getField1() {
		return this.field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return this.field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return this.field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return this.field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public String getField5() {
		return this.field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

	public String getField6() {
		return this.field6;
	}

	public void setField6(String field6) {
		this.field6 = field6;
	}

	public String getIdentApproveUnit() {
		return this.identApproveUnit;
	}

	public void setIdentApproveUnit(String identApproveUnit) {
		this.identApproveUnit = identApproveUnit;
	}

	public String getIdentCheckFlag() {
		return this.identCheckFlag;
	}

	public void setIdentCheckFlag(String identCheckFlag) {
		this.identCheckFlag = identCheckFlag;
	}

	public Date getIdentCheckedDate() {
		return this.identCheckedDate;
	}

	public void setIdentCheckedDate(Date identCheckedDate) {
		this.identCheckedDate = identCheckedDate;
	}

	public Date getIdentCheckingDate() {
		return this.identCheckingDate;
	}

	public void setIdentCheckingDate(Date identCheckingDate) {
		this.identCheckingDate = identCheckingDate;
	}

	public String getIdentCustName() {
		return this.identCustName;
	}

	public void setIdentCustName(String identCustName) {
		this.identCustName = identCustName;
	}

	public Date getIdentEffectiveDate() {
		return this.identEffectiveDate;
	}

	public void setIdentEffectiveDate(Date identEffectiveDate) {
		this.identEffectiveDate = identEffectiveDate;
	}

	public Date getIdentExpriedDate() {
		return this.identExpriedDate;
	}

	public void setIdentExpriedDate(Date identExpriedDate) {
		this.identExpriedDate = identExpriedDate;
	}

	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentOrg() {
		return this.identOrg;
	}

	public void setIdentOrg(String identOrg) {
		this.identOrg = identOrg;
	}

	public Integer getIdentPeriod() {
		return this.identPeriod;
	}

	public void setIdentPeriod(Integer identPeriod) {
		this.identPeriod = identPeriod;
	}

	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIdentValidFlag() {
		return this.identValidFlag;
	}

	public void setIdentValidFlag(String identValidFlag) {
		this.identValidFlag = identValidFlag;
	}

	public Date getIdentValidPeriod() {
		return this.identValidPeriod;
	}

	public void setIdentValidPeriod(Date identValidPeriod) {
		this.identValidPeriod = identValidPeriod;
	}

	public Date getLasupdDate() {
		return this.lasupdDate;
	}

	public void setLasupdDate(Date lasupdDate) {
		this.lasupdDate = lasupdDate;
	}

	public String getLasupdOpr() {
		return this.lasupdOpr;
	}

	public void setLasupdOpr(String lasupdOpr) {
		this.lasupdOpr = lasupdOpr;
	}

	public Date getLasupdTime() {
		return this.lasupdTime;
	}

	public void setLasupdTime(Date lasupdTime) {
		this.lasupdTime = lasupdTime;
	}

	public Long getIdentId() {
		return identId;
	}

	public void setIdentId(Long identId) {
		this.identId = identId;
	}
	
}