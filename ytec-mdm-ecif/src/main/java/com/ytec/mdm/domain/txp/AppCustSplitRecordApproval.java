package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppCustSplitRecordApproval entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_CUST_SPLIT_RECORD_APPROVAL")
public class AppCustSplitRecordApproval implements java.io.Serializable {

	// Fields

	private Long custSplitRecordApprovalId;
	private String reserveCustNo;
	private String mergedCustNo;
	private String splitOperator;
	private Timestamp splitOperTime;
	private String importOperator;
	private Timestamp importOperTime;
	private String approvalOperator;
	private Timestamp approvalTime;
	private String approvalStat;
	private String approvalNote;

	// Constructors

	/** default constructor */
	public AppCustSplitRecordApproval() {
	}

	/** minimal constructor */
	public AppCustSplitRecordApproval(Long custSplitRecordApprovalId) {
		this.custSplitRecordApprovalId = custSplitRecordApprovalId;
	}

	/** full constructor */
	public AppCustSplitRecordApproval(Long custSplitRecordApprovalId,
			String reserveCustNo, String mergedCustNo, String splitOperator,
			Timestamp splitOperTime, String importOperator,
			Timestamp importOperTime, String approvalOperator,
			Timestamp approvalTime, String approvalStat, String approvalNote) {
		this.custSplitRecordApprovalId = custSplitRecordApprovalId;
		this.reserveCustNo = reserveCustNo;
		this.mergedCustNo = mergedCustNo;
		this.splitOperator = splitOperator;
		this.splitOperTime = splitOperTime;
		this.importOperator = importOperator;
		this.importOperTime = importOperTime;
		this.approvalOperator = approvalOperator;
		this.approvalTime = approvalTime;
		this.approvalStat = approvalStat;
		this.approvalNote = approvalNote;
	}

	// Property accessors
	@Id
	@Column(name = "CUST_SPLIT_RECORD_APPROVAL_ID", unique = true, nullable = false)
	public Long getCustSplitRecordApprovalId() {
		return this.custSplitRecordApprovalId;
	}

	public void setCustSplitRecordApprovalId(
			Long custSplitRecordApprovalId) {
		this.custSplitRecordApprovalId = custSplitRecordApprovalId;
	}

	@Column(name = "RESERVE_CUST_NO", length = 32)
	public String getReserveCustNo() {
		return this.reserveCustNo;
	}

	public void setReserveCustNo(String reserveCustNo) {
		this.reserveCustNo = reserveCustNo;
	}

	@Column(name = "MERGED_CUST_NO", length = 32)
	public String getMergedCustNo() {
		return this.mergedCustNo;
	}

	public void setMergedCustNo(String mergedCustNo) {
		this.mergedCustNo = mergedCustNo;
	}

	@Column(name = "SPLIT_OPERATOR", length = 20)
	public String getSplitOperator() {
		return this.splitOperator;
	}

	public void setSplitOperator(String splitOperator) {
		this.splitOperator = splitOperator;
	}

	@Column(name = "SPLIT_OPER_TIME", length = 11)
	public Timestamp getSplitOperTime() {
		return this.splitOperTime;
	}

	public void setSplitOperTime(Timestamp splitOperTime) {
		this.splitOperTime = splitOperTime;
	}

	@Column(name = "IMPORT_OPERATOR", length = 20)
	public String getImportOperator() {
		return this.importOperator;
	}

	public void setImportOperator(String importOperator) {
		this.importOperator = importOperator;
	}

	@Column(name = "IMPORT_OPER_TIME", length = 11)
	public Timestamp getImportOperTime() {
		return this.importOperTime;
	}

	public void setImportOperTime(Timestamp importOperTime) {
		this.importOperTime = importOperTime;
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