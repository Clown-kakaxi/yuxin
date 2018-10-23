package com.yuchengtech.emp.ecif.customer.entity.customerevaluate;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONGRADE database table.
 * 
 */
@Entity
@Table(name="PERSONGRADE")
public class Persongrade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PERSON_GRADE_ID", unique=true, nullable=false)
	private Long personGradeId;

//	@Column(name="AML_RISK_GRADE", length=20)
//	private String amlRiskGrade;
//
//	@Column(name="ASSET_GRADE", length=20)
//	private String assetGrade;

	@Column(name="CUST_ID")
	private Long custId;

//	@Column(name="EBANK_GRADE", length=20)
//	private String ebankGrade;
//
//	@Column(name="EBANK_KIND", length=20)
//	private String ebankKind;


	@Column(name="EFFECTIVE_DATE",length=20)
	private String effectiveDate;

	@Column(name="EVALUATE_DATE",length=20)
	private String evaluateDate;

	@Column(name="EXPIRED_DATE",length=20)
	private String expiredDate;

	@Column(name="GRADE",length=20)
	private String grade;

	@Column(name="GRADE_TYPE", length=20)
	private String gradeType;
//
//	@Column(name="LOAN_RISK_GRADE", length=20)
//	private String loanRiskGrade;
//
//	@Column(name="MOBILE_BANK_KIND", length=20)
//	private String mobileBankKind;
//
//	@Column(name="RURAL_ASSET_GRADE", length=20)
//	private String ruralAssetGrade;
//
//	@Column(name="SERVICE_STAR_LEVEL", length=20)
//	private String serviceStarLevel;
//
//	@Column(name="VIP_GRADE", length=20)
//	private String vipGrade;

    public Persongrade() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getPersonGradeId() {
		return this.personGradeId;
	}

	public void setPersonGradeId(Long personGradeId) {
		this.personGradeId = personGradeId;
	}

//	public String getAmlRiskGrade() {
//		return this.amlRiskGrade;
//	}
//
//	public void setAmlRiskGrade(String amlRiskGrade) {
//		this.amlRiskGrade = amlRiskGrade;
//	}
//
//	public String getAssetGrade() {
//		return this.assetGrade;
//	}
//
//	public void setAssetGrade(String assetGrade) {
//		this.assetGrade = assetGrade;
//	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

//	public String getEbankGrade() {
//		return this.ebankGrade;
//	}
//
//	public void setEbankGrade(String ebankGrade) {
//		this.ebankGrade = ebankGrade;
//	}
//
//	public String getEbankKind() {
//		return this.ebankKind;
//	}
//
//	public void setEbankKind(String ebankKind) {
//		this.ebankKind = ebankKind;
//	}

	public String getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEvaluateDate() {
		return this.evaluateDate;
	}

	public void setEvaluateDate(String evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	public String getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGradeType() {
		return this.gradeType;
	}

	public void setGradeType(String gradeType) {
		this.gradeType = gradeType;
	}
//
//	public String getLoanRiskGrade() {
//		return this.loanRiskGrade;
//	}
//
//	public void setLoanRiskGrade(String loanRiskGrade) {
//		this.loanRiskGrade = loanRiskGrade;
//	}
//
//	public String getMobileBankKind() {
//		return this.mobileBankKind;
//	}
//
//	public void setMobileBankKind(String mobileBankKind) {
//		this.mobileBankKind = mobileBankKind;
//	}
//
//	public String getRuralAssetGrade() {
//		return this.ruralAssetGrade;
//	}
//
//	public void setRuralAssetGrade(String ruralAssetGrade) {
//		this.ruralAssetGrade = ruralAssetGrade;
//	}
//
//	public String getServiceStarLevel() {
//		return this.serviceStarLevel;
//	}
//
//	public void setServiceStarLevel(String serviceStarLevel) {
//		this.serviceStarLevel = serviceStarLevel;
//	}
//
//	public String getVipGrade() {
//		return this.vipGrade;
//	}
//
//	public void setVipGrade(String vipGrade) {
//		this.vipGrade = vipGrade;
//	}

}