package com.yuchengtech.bcrm.customer.customerView.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * @description : 客户等级信息表
 *
 * @author : zhaolong
 * @date : 2016-1-7 下午5:00:37
 */
@Entity
@Table(name="ACRM_F_CI_GRADE")
public class AcrmFCiGrade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_GRADE_ID")
	private String custGradeId;

	@Column(name="CUST_GRADE")
	private String custGrade;


	@Column(name="CUST_GRADE_TYPE")
	private String custGradeType;

	@Column(name="CUST_ID")
	private String custId;

	@Temporal(TemporalType.DATE)
	@Column(name="EFFECTIVE_DATE")
	private Date effectiveDate;

	@Temporal(TemporalType.DATE)
	@Column(name="ETL_DATE")
	private Date etlDate;

	@Temporal(TemporalType.DATE)
	@Column(name="EVALUATE_DATE")
	private Date evaluateDate;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;



	@Column(name="ORG_CODE")
	private String orgCode;

	@Column(name="ORG_NAME")
	private String orgName;
//	@Column(name="MGR_ID_FP")
//	private String mgrIdFp;
	
//	@Column(name="STAT_FP")
//	private String statFp;

//	@Temporal(TemporalType.DATE)
//	@Column(name="TIME_FP")
//	private Date timeFp;


//	@Column(name="CUST_GRADE_FP")
//	private String custGradeFp;	
	
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	public AcrmFCiGrade() {
	}

	public String getCustGradeId() {
		return this.custGradeId;
	}

	public void setCustGradeId(String custGradeId) {
		this.custGradeId = custGradeId;
	}

	public String getCustGrade() {
		return this.custGrade;
	}

	public void setCustGrade(String custGrade) {
		this.custGrade = custGrade;
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

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getEtlDate() {
		return this.etlDate;
	}

	public void setEtlDate(Date etlDate) {
		this.etlDate = etlDate;
	}

	public Date getEvaluateDate() {
		return this.evaluateDate;
	}

	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	public Date getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}


	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}