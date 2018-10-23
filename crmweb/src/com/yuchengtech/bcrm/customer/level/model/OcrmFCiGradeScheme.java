package com.yuchengtech.bcrm.customer.level.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the OCRM_F_CI_GRADE_SCHEME database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_GRADE_SCHEME")
public class OcrmFCiGradeScheme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_GRADE_SCHEME_SCHEMEID_GENERATOR" , sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_GRADE_SCHEME_SCHEMEID_GENERATOR")
	@Column(name="SCHEME_ID")
	private Long schemeId;
	
	@Column(name="SCHEME_NAME")
	private String schemeName;
	
	
	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="CREATE_ORG_ID")
	private String createOrgId;

	@Column(name="CREATE_ORG_NAME")
	private String createOrgName;

	@Column(name="CREATE_USER_ID")
	private String createUserId;

	@Column(name="CREATE_USER_NAME")
	private String createUserName;

    @Temporal( TemporalType.DATE)
	@Column(name="GRADE_BEGIN_DATE")
	private Date gradeBeginDate;

    @Temporal( TemporalType.DATE)
	@Column(name="GRADE_END_DATE")
	private Date gradeEndDate;

	@Column(name="GRADE_FORMULA")
	private String gradeFormula;

	@Column(name="GRADE_FORMULA_EXPLAIN")
	private String gradeFormulaExplain;

	@Column(name="GRADE_FREQUENCY")
	private String gradeFrequency;

	@Column(name="GRADE_TYPE")
	private String gradeType;

	@Column(name="GRADE_USEAGE")
	private String gradeUseage;
	
	@Column(name="IS_USED")
	private String isUsed;

	@Column(name="LAST_UPDATE_DATE")
	private Timestamp lastUpdateDate;

	@Column(name="LAST_UPDATE_ORG_ID")
	private String lastUpdateOrgId;

	@Column(name="LAST_UPDATE_ORG_NAME")
	private String lastUpdateOrgName;

	@Column(name="LAST_UPDATE_USER_ID")
	private String lastUpdateUserId;

	@Column(name="LAST_UPDATE_USER_NAME")
	private String lastUpdateUserName;

	private String memo;



    public OcrmFCiGradeScheme() {
    }

	public Long getSchemeId() {
		return this.schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreateOrgId() {
		return this.createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

	public String getCreateOrgName() {
		return this.createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getGradeBeginDate() {
		return this.gradeBeginDate;
	}

	public void setGradeBeginDate(Date gradeBeginDate) {
		this.gradeBeginDate = gradeBeginDate;
	}

	public Date getGradeEndDate() {
		return this.gradeEndDate;
	}

	public void setGradeEndDate(Date gradeEndDate) {
		this.gradeEndDate = gradeEndDate;
	}

	public String getGradeFormula() {
		return this.gradeFormula;
	}

	public void setGradeFormula(String gradeFormula) {
		this.gradeFormula = gradeFormula;
	}

	public String getGradeFormulaExplain() {
		return this.gradeFormulaExplain;
	}

	public void setGradeFormulaExplain(String gradeFormulaExplain) {
		this.gradeFormulaExplain = gradeFormulaExplain;
	}

	public String getGradeFrequency() {
		return this.gradeFrequency;
	}

	public void setGradeFrequency(String gradeFrequency) {
		this.gradeFrequency = gradeFrequency;
	}

	public String getGradeType() {
		return this.gradeType;
	}

	public void setGradeType(String gradeType) {
		this.gradeType = gradeType;
	}

	public String getGradeUseage() {
		return gradeUseage;
	}

	public void setGradeUseage(String gradeUseage) {
		this.gradeUseage = gradeUseage;
	}
	
	public String getIsUsed() {
		return this.isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public Timestamp getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdateOrgId() {
		return this.lastUpdateOrgId;
	}

	public void setLastUpdateOrgId(String lastUpdateOrgId) {
		this.lastUpdateOrgId = lastUpdateOrgId;
	}

	public String getLastUpdateOrgName() {
		return this.lastUpdateOrgName;
	}

	public void setLastUpdateOrgName(String lastUpdateOrgName) {
		this.lastUpdateOrgName = lastUpdateOrgName;
	}

	public String getLastUpdateUserId() {
		return this.lastUpdateUserId;
	}

	public void setLastUpdateUserId(String lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public String getLastUpdateUserName() {
		return this.lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}