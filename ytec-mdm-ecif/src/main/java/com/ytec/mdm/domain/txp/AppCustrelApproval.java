package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AppCustrelApproval entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_CUSTREL_APPROVAL")
public class AppCustrelApproval implements java.io.Serializable {

	// Fields

	private Long custrelApprovalId;
	private Long custRelId;
	private String custRelType;
	private String custRelDesc;
	private String custRelStat;
	private Long srcCustId;
	private Long destCustId;
	private Date relStartDate;
	private Date relEndDate;
	private String operator;
	private Timestamp operTime;
	private String operStat;
	private String approvalOperator;
	private Timestamp approvalTime;
	private String approvalStat;
	private String approvalNote;

	// Constructors

	/** default constructor */
	public AppCustrelApproval() {
	}

	/** minimal constructor */
	public AppCustrelApproval(Long custrelApprovalId) {
		this.custrelApprovalId = custrelApprovalId;
	}

	/** full constructor */
	public AppCustrelApproval(Long custrelApprovalId, Long custRelId,
			String custRelType, String custRelDesc, String custRelStat,
			Long srcCustId, Long destCustId, Date relStartDate,
			Date relEndDate, String operator, Timestamp operTime,
			String operStat, String approvalOperator, Timestamp approvalTime,
			String approvalStat, String approvalNote) {
		this.custrelApprovalId = custrelApprovalId;
		this.custRelId = custRelId;
		this.custRelType = custRelType;
		this.custRelDesc = custRelDesc;
		this.custRelStat = custRelStat;
		this.srcCustId = srcCustId;
		this.destCustId = destCustId;
		this.relStartDate = relStartDate;
		this.relEndDate = relEndDate;
		this.operator = operator;
		this.operTime = operTime;
		this.operStat = operStat;
		this.approvalOperator = approvalOperator;
		this.approvalTime = approvalTime;
		this.approvalStat = approvalStat;
		this.approvalNote = approvalNote;
	}

	// Property accessors
	@Id
	@Column(name = "CUSTREL_APPROVAL_ID", unique = true, nullable = false, scale = 0)
	public Long getCustrelApprovalId() {
		return this.custrelApprovalId;
	}

	public void setCustrelApprovalId(Long custrelApprovalId) {
		this.custrelApprovalId = custrelApprovalId;
	}

	@Column(name = "CUST_REL_ID", scale = 0)
	public Long getCustRelId() {
		return this.custRelId;
	}

	public void setCustRelId(Long custRelId) {
		this.custRelId = custRelId;
	}

	@Column(name = "CUST_REL_TYPE", length = 20)
	public String getCustRelType() {
		return this.custRelType;
	}

	public void setCustRelType(String custRelType) {
		this.custRelType = custRelType;
	}

	@Column(name = "CUST_REL_DESC", length = 40)
	public String getCustRelDesc() {
		return this.custRelDesc;
	}

	public void setCustRelDesc(String custRelDesc) {
		this.custRelDesc = custRelDesc;
	}

	@Column(name = "CUST_REL_STAT", length = 1)
	public String getCustRelStat() {
		return this.custRelStat;
	}

	public void setCustRelStat(String custRelStat) {
		this.custRelStat = custRelStat;
	}

	@Column(name = "SRC_CUST_ID", scale = 0)
	public Long getSrcCustId() {
		return this.srcCustId;
	}

	public void setSrcCustId(Long srcCustId) {
		this.srcCustId = srcCustId;
	}

	@Column(name = "DEST_CUST_ID", scale = 0)
	public Long getDestCustId() {
		return this.destCustId;
	}

	public void setDestCustId(Long destCustId) {
		this.destCustId = destCustId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REL_START_DATE", length = 7)
	public Date getRelStartDate() {
		return this.relStartDate;
	}

	public void setRelStartDate(Date relStartDate) {
		this.relStartDate = relStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REL_END_DATE", length = 7)
	public Date getRelEndDate() {
		return this.relEndDate;
	}

	public void setRelEndDate(Date relEndDate) {
		this.relEndDate = relEndDate;
	}

	@Column(name = "OPERATOR", length = 20)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "OPER_TIME", length = 11)
	public Timestamp getOperTime() {
		return this.operTime;
	}

	public void setOperTime(Timestamp operTime) {
		this.operTime = operTime;
	}

	@Column(name = "OPER_STAT", length = 2)
	public String getOperStat() {
		return this.operStat;
	}

	public void setOperStat(String operStat) {
		this.operStat = operStat;
	}

	@Column(name = "APPROVAL_OPERATOR", length = 20)
	public String getApprovalOperator() {
		return this.approvalOperator;
	}

	public void setApprovalOperator(String approvalOperator) {
		this.approvalOperator = approvalOperator;
	}

	@Column(name = "APPROVAL_TIME", length = 11)
	public Timestamp getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(Timestamp approvalTime) {
		this.approvalTime = approvalTime;
	}

	@Column(name = "APPROVAL_STAT", length = 2)
	public String getApprovalStat() {
		return this.approvalStat;
	}

	public void setApprovalStat(String approvalStat) {
		this.approvalStat = approvalStat;
	}

	@Column(name = "APPROVAL_NOTE", length = 200)
	public String getApprovalNote() {
		return this.approvalNote;
	}

	public void setApprovalNote(String approvalNote) {
		this.approvalNote = approvalNote;
	}

}