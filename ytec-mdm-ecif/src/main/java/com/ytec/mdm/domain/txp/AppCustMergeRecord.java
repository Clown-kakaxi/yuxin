package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppCustMergeRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_CUST_MERGE_RECORD")
public class AppCustMergeRecord implements java.io.Serializable {

	// Fields

	private Long mergeRecordId;
	private String mergeCode;
	private Long reserveCustId;
	private String reserveCustNo;
	private Long mergedCustId;
	private String mergedCustNo;
	private String mergeOperator;
	private Timestamp mergeOperTime;
	private String importOperator;
	private Timestamp importOperTime;
	private String mergeStat;
	private String suspectFlag;
	private String mergeOperbrc;
	private String importOperbrc;
	private String mergeDesc;

	// Constructors

	/** default constructor */
	public AppCustMergeRecord() {
	}

	/** minimal constructor */
	public AppCustMergeRecord(Long mergeRecordId) {
		this.mergeRecordId = mergeRecordId;
	}

	/** full constructor */
	public AppCustMergeRecord(Long mergeRecordId, String mergeCode,
			Long reserveCustId, String reserveCustNo,
			Long mergedCustId, String mergedCustNo, String mergeOperator,
			Timestamp mergeOperTime, String importOperator,
			Timestamp importOperTime, String mergeStat, String suspectFlag,
			String mergeOperbrc, String importOperbrc, String mergeDesc) {
		this.mergeRecordId = mergeRecordId;
		this.mergeCode = mergeCode;
		this.reserveCustId = reserveCustId;
		this.reserveCustNo = reserveCustNo;
		this.mergedCustId = mergedCustId;
		this.mergedCustNo = mergedCustNo;
		this.mergeOperator = mergeOperator;
		this.mergeOperTime = mergeOperTime;
		this.importOperator = importOperator;
		this.importOperTime = importOperTime;
		this.mergeStat = mergeStat;
		this.suspectFlag = suspectFlag;
		this.mergeOperbrc = mergeOperbrc;
		this.importOperbrc = importOperbrc;
		this.mergeDesc = mergeDesc;
	}

	// Property accessors
	@Id
	@Column(name = "MERGE_RECORD_ID", unique = true, nullable = false)
	public Long getMergeRecordId() {
		return this.mergeRecordId;
	}

	public void setMergeRecordId(Long mergeRecordId) {
		this.mergeRecordId = mergeRecordId;
	}

	@Column(name = "MERGE_CODE", length = 20)
	public String getMergeCode() {
		return this.mergeCode;
	}

	public void setMergeCode(String mergeCode) {
		this.mergeCode = mergeCode;
	}

	@Column(name = "RESERVE_CUST_ID")
	public Long getReserveCustId() {
		return this.reserveCustId;
	}

	public void setReserveCustId(Long reserveCustId) {
		this.reserveCustId = reserveCustId;
	}

	@Column(name = "RESERVE_CUST_NO", length = 32)
	public String getReserveCustNo() {
		return this.reserveCustNo;
	}

	public void setReserveCustNo(String reserveCustNo) {
		this.reserveCustNo = reserveCustNo;
	}

	@Column(name = "MERGED_CUST_ID")
	public Long getMergedCustId() {
		return this.mergedCustId;
	}

	public void setMergedCustId(Long mergedCustId) {
		this.mergedCustId = mergedCustId;
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

	@Column(name = "MERGE_STAT", length = 1)
	public String getMergeStat() {
		return this.mergeStat;
	}

	public void setMergeStat(String mergeStat) {
		this.mergeStat = mergeStat;
	}

	@Column(name = "SUSPECT_FLAG", length = 1)
	public String getSuspectFlag() {
		return this.suspectFlag;
	}

	public void setSuspectFlag(String suspectFlag) {
		this.suspectFlag = suspectFlag;
	}

	@Column(name = "MERGE_OPERBRC", length = 20)
	public String getMergeOperbrc() {
		return this.mergeOperbrc;
	}

	public void setMergeOperbrc(String mergeOperbrc) {
		this.mergeOperbrc = mergeOperbrc;
	}

	@Column(name = "IMPORT_OPERBRC", length = 20)
	public String getImportOperbrc() {
		return this.importOperbrc;
	}

	public void setImportOperbrc(String importOperbrc) {
		this.importOperbrc = importOperbrc;
	}

	@Column(name = "MERGE_DESC")
	public String getMergeDesc() {
		return this.mergeDesc;
	}

	public void setMergeDesc(String mergeDesc) {
		this.mergeDesc = mergeDesc;
	}

}