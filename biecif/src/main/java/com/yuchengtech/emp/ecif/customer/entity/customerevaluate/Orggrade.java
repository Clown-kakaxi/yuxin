package com.yuchengtech.emp.ecif.customer.entity.customerevaluate;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ORGGRADE database table.
 * 
 */
@Entity
@Table(name="ORGGRADE")
public class Orggrade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_GRADE_ID", unique=true, nullable=false)
	private Long orgGradeId;

	@Column(name="CUST_ID")
	private Long custId;

    @Temporal( TemporalType.DATE)
	@Column(name="EFFECTIVE_DATE")
	private Date effectiveDate;

    @Temporal( TemporalType.DATE)
	@Column(name="EVALUTE_DATE")
	private Date evaluteDate;

    @Temporal( TemporalType.DATE)
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="ORG_GRADE", length=20)
	private String orgGrade;

	@Column(name="ORG_GRADE_TYPE", length=20)
	private String orgGradeType;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Orggrade() {
    }

	public Long getOrgGradeId() {
		return this.orgGradeId;
	}

	public void setOrgGradeId(Long orgGradeId) {
		this.orgGradeId = orgGradeId;
	}

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getEvaluteDate() {
		return this.evaluteDate;
	}

	public void setEvaluteDate(Date evaluteDate) {
		this.evaluteDate = evaluteDate;
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

	public String getOrgGrade() {
		return this.orgGrade;
	}

	public void setOrgGrade(String orgGrade) {
		this.orgGrade = orgGrade;
	}

	public String getOrgGradeType() {
		return this.orgGradeType;
	}

	public void setOrgGradeType(String orgGradeType) {
		this.orgGradeType = orgGradeType;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}