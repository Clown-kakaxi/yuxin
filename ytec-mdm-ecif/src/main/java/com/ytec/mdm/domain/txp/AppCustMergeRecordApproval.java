package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppCustMergeRecordApproval entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_CUST_MERGE_RECORD_APPROVAL")
public class AppCustMergeRecordApproval implements java.io.Serializable {

	// Fields

	private Long custMergeRecordApprovalId;
	private String reserveCustNo;
	private String mergedCustNo;
	private String mergeOperator;
	private Timestamp mergeOperTime;
	private String importOperator;
	private Timestamp importOperTime;
	private String approvalOperator;
	private Timestamp approvalTime;
	private String approvalStat;
	private String approvalNote;

	// Constructors

	/** default constructor */
	public AppCustMergeRecordApproval() {
	}

	/** minimal constructor */
	public AppCustMergeRecordApproval(Long custMergeRecordApprovalId) {
		this.custMergeRecordApprovalId = custMergeRecordApprovalId;
	}

	/** full constructor */
	public AppCustMergeRecordApproval(Long custMergeRecordApprovalId,
			String reserveCustNo, String mergedCustNo, String mergeOperator,
			Timestamp mergeOperTime, String importOperator,
			Timestamp importOperTime, String approvalOperator,
			Timestamp approvalTime, String approvalStat, String approvalNote) {
		this.custMergeRecordApprovalId = custMergeRecordApprovalId;
		this.reserveCustNo = reserveCustNo;
		this.mergedCustNo = mergedCustNo;
		this.mergeOperator = mergeOperator;
		this.mergeOperTime = mergeOperTime;
		this.importOperator = importOperator;
		this.importOperTime = importOperTime;
		this.approvalOperator = approvalOperator;
		this.approvalTime = approvalTime;
		this.approvalStat = approvalStat;
		this.approvalNote = approvalNote;
	}

	// Property accessors
	@Id
	@Column(name = "CUST_MERGE_RECORD_APPROVAL_ID", unique = true, nullable = false)
	public Long getCustMergeRecordApprovalId() {
		return this.custMergeRecordApprovalId;
	}

	public void setCustMergeRecordApprovalId(
			Long custMergeRecordApprovalId) {
		this.custMergeRecordApprovalId = custMergeRecordApprovalId;
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

	@Column(name = "MERGE_OPERATOR", length = 20)
	public String getMergeOperator() {
		return this.mergeOperator;
	}

	public void setMergeOperator(String mergeOperator) {
		this.mergeOperator = mergeOperator;
	}

	@Column(name = "MERGE_OPER_TIME", length = 11)
	public Timestamp getMergeOperTime() {
		return this.mergeOperTime;
	}

	public void setMergeOperTime(Timestamp mergeOperTime) {
		this.mergeOperTime = mergeOperTime;
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