package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiGrade entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_GRADE")
public class MCiGrade implements java.io.Serializable {

	// Fields

	private String custGradeId;
	private String custId;
	private String orgCode;
	private String orgName;
	private String custGradeType;
	private String custGrade;
	private Date evaluateDate;
	private Date effectiveDate;
	private Date expiredDate;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiGrade() {
	}

	/** minimal constructor */
	public MCiGrade(String custGradeId) {
		this.custGradeId = custGradeId;
	}

	/** full constructor */
	public MCiGrade(String custGradeId, String custId, String orgCode,
			String orgName, String custGradeType, String custGrade,
			Date evaluateDate, Date effectiveDate, Date expiredDate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custGradeId = custGradeId;
		this.custId = custId;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.custGradeType = custGradeType;
		this.custGrade = custGrade;
		this.evaluateDate = evaluateDate;
		this.effectiveDate = effectiveDate;
		this.expiredDate = expiredDate;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "CUST_GRADE_ID", unique = true, nullable = false, length = 20)
	public String getCustGradeId() {
		return this.custGradeId;
	}

	public void setCustGradeId(String custGradeId) {
		this.custGradeId = custGradeId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "ORG_CODE", length = 20)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "ORG_NAME", length = 20)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "CUST_GRADE_TYPE", length = 20)
	public String getCustGradeType() {
		return this.custGradeType;
	}

	public void setCustGradeType(String custGradeType) {
		this.custGradeType = custGradeType;
	}

	@Column(name = "CUST_GRADE", length = 20)
	public String getCustGrade() {
		return this.custGrade;
	}

	public void setCustGrade(String custGrade) {
		this.custGrade = custGrade;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EVALUATE_DATE", length = 7)
	public Date getEvaluateDate() {
		return this.evaluateDate;
	}

	public void setEvaluateDate(Date evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECTIVE_DATE", length = 7)
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRED_DATE", length = 7)
	public Date getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
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

}