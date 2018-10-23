package com.yuchengtech.bcrm.custmanager.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * 
 * @description : 客户等级信息调整历史表 
 *
 * @author : zhaolong
 * @date : 2016-1-29 下午4:33:40
 */
@Entity
@Table(name="ACRM_A_ANTI_CHANGE_GRADE_HIS")
public class AcrmAAntiChangeGradeHi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_A_ANTI_CHANGE_GRADE_HIS_ID_INDEX_INSTRUCTION", sequenceName="ID_INDEX_INSTRUCTION" ,allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_A_ANTI_CHANGE_GRADE_HIS_ID_INDEX_INSTRUCTION")
	@Column(name="GRADE_HIS_ID")
	private String gradeHisId;

	@Column(name="CUST_GRADE_NEW")
	private String custGradeNew;

	@Column(name="CUST_GRADE_OLD")
	private String custGradeOld;

	@Column(name="CUST_GRADE_TYPE")
	private String custGradeType;

	@Column(name="CUST_ID")
	private String custId;


	@Column(name="EVALUATE_DATE_NEW")
	private String evaluateDateNew;

	@Column(name="EVALUATE_DATE_OLD")
	private String evaluateDateOld;

	@Column(name="LAST_UPDATE_USER_NEW")
	private String lastUpdateUserNew;

	@Column(name="LAST_UPDATE_USER_OLD")
	private String lastUpdateUserOld;

	@Temporal(TemporalType.DATE)
	@Column(name="OPERATE_TIME")
	private Date operateTime;

	public AcrmAAntiChangeGradeHi() {
	}

	public String getGradeHisId() {
		return this.gradeHisId;
	}

	public void setGradeHisId(String gradeHisId) {
		this.gradeHisId = gradeHisId;
	}

	public String getCustGradeNew() {
		return this.custGradeNew;
	}

	public void setCustGradeNew(String custGradeNew) {
		this.custGradeNew = custGradeNew;
	}

	public String getCustGradeOld() {
		return this.custGradeOld;
	}

	public void setCustGradeOld(String custGradeOld) {
		this.custGradeOld = custGradeOld;
	}

	public String getCustGradeType() {
		return this.custGradeType;
	}

	public void setCustGradeType(String custGradeType) {
		this.custGradeType = custGradeType;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getEvaluateDateNew() {
		return evaluateDateNew;
	}

	public void setEvaluateDateNew(String evaluateDateNew) {
		this.evaluateDateNew = evaluateDateNew;
	}

	public String getEvaluateDateOld() {
		return evaluateDateOld;
	}

	public void setEvaluateDateOld(String evaluateDateOld) {
		this.evaluateDateOld = evaluateDateOld;
	}

	public String getLastUpdateUserNew() {
		return this.lastUpdateUserNew;
	}

	public void setLastUpdateUserNew(String lastUpdateUserNew) {
		this.lastUpdateUserNew = lastUpdateUserNew;
	}

	public String getLastUpdateUserOld() {
		return this.lastUpdateUserOld;
	}

	public void setLastUpdateUserOld(String lastUpdateUserOld) {
		this.lastUpdateUserOld = lastUpdateUserOld;
	}

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}