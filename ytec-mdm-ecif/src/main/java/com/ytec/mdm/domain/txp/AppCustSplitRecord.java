package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppCustSplitRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_CUST_SPLIT_RECORD")
public class AppCustSplitRecord implements java.io.Serializable {

	// Fields

	private Long splitRecordId;
	private String splitCode;
	private Long reserveCustId;
	private String reserveCustNo;
	private Long mergedCustId;
	private String mergedCustNo;
	private String splitOperator;
	private Timestamp splitOperTime;
	private String importOperator;
	private Timestamp importOperTime;
	private String splitStat;
	private String splitDesc;
	private String splitOperbrc;
	private String importOperbrc;

	// Constructors

	/** default constructor */
	public AppCustSplitRecord() {
	}

	/** minimal constructor */
	public AppCustSplitRecord(Long splitRecordId) {
		this.splitRecordId = splitRecordId;
	}

	/** full constructor */
	public AppCustSplitRecord(Long splitRecordId, String splitCode,
			Long reserveCustId, String reserveCustNo,
			Long mergedCustId, String mergedCustNo, String splitOperator,
			Timestamp splitOperTime, String importOperator,
			Timestamp importOperTime, String splitStat, String splitDesc,
			String splitOperbrc, String importOperbrc) {
		this.splitRecordId = splitRecordId;
		this.splitCode = splitCode;
		this.reserveCustId = reserveCustId;
		this.reserveCustNo = reserveCustNo;
		this.mergedCustId = mergedCustId;
		this.mergedCustNo = mergedCustNo;
		this.splitOperator = splitOperator;
		this.splitOperTime = splitOperTime;
		this.importOperator = importOperator;
		this.importOperTime = importOperTime;
		this.splitStat = splitStat;
		this.splitDesc = splitDesc;
		this.splitOperbrc = splitOperbrc;
		this.importOperbrc = importOperbrc;
	}

	// Property accessors
	@Id
	@Column(name = "SPLIT_RECORD_ID", unique = true, nullable = false)
	public Long getSplitRecordId() {
		return this.splitRecordId;
	}

	public void setSplitRecordId(Long splitRecordId) {
		this.splitRecordId = splitRecordId;
	}

	@Column(name = "SPLIT_CODE", length = 20)
	public String getSplitCode() {
		return this.splitCode;
	}

	public void setSplitCode(String splitCode) {
		this.splitCode = splitCode;
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

	@Column(name = "SPLIT_STAT", length = 1)
	public String getSplitStat() {
		return this.splitStat;
	}

	public void setSplitStat(String splitStat) {
		this.splitStat = splitStat;
	}

	@Column(name = "SPLIT_DESC")
	public String getSplitDesc() {
		return this.splitDesc;
	}

	public void setSplitDesc(String splitDesc) {
		this.splitDesc = splitDesc;
	}

	@Column(name = "SPLIT_OPERBRC", length = 20)
	public String getSplitOperbrc() {
		return this.splitOperbrc;
	}

	public void setSplitOperbrc(String splitOperbrc) {
		this.splitOperbrc = splitOperbrc;
	}

	@Column(name = "IMPORT_OPERBRC", length = 20)
	public String getImportOperbrc() {
		return this.importOperbrc;
	}

	public void setImportOperbrc(String importOperbrc) {
		this.importOperbrc = importOperbrc;
	}

}