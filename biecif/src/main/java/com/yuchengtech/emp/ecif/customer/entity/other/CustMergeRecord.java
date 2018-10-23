/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.entity.other;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
@Table(name="APP_CUST_MERGE_RECORD")
public class CustMergeRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	疑似客户合并标识	merge_record_id	BIGINT	MERGE_RECORD_ID
	@Id
	@GeneratedValue(generator = "MERGE_RECORD_ID_GENERATOR")
	@GenericGenerator(name = "MERGE_RECORD_ID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_MERGE_RECORD_ID") })	
	@Column(name="MERGE_RECORD_ID", unique=true, nullable=false)
	private Long mergeRecordId;
//	合并编码	merge_code	VARCHAR(20)	MERGE_CODE
	@Column(name="MERGE_CODE", length=20)
	private String mergeCode;
//	保留客户	reserve_cust_id	BIGINT	RESERVE_CUST_ID
	@Column(name="RESERVE_CUST_ID")
	private Long reserveCustId;
//	被合并客户	merged_cust_id	BIGINT	MERGED_CUST_ID
	@Column(name="MERGED_CUST_ID")
	private Long mergedCustId;
	
//	保留客户号	reserve_cust_no	BIGINT	RESERVE_CUST_ID
	@Column(name="RESERVE_CUST_NO", length=32)
	private String reserveCustNo;
//	被合并客户号	merged_cust_no	BIGINT	MERGED_CUST_ID
	@Column(name="MERGED_CUST_NO", length=32)
	private String mergedCustNo;
	
//	合并提交人	merge_operator	VARCHAR(20)	MERGE_OPERATOR
	@Column(name="MERGE_OPERATOR", length=20)
	private String mergeOperator;
//	合并提交时间	merge_oper_time	DATE	MERGE_OPER_TIME
	@Column(name="MERGE_OPER_TIME")
	private Timestamp mergeOperTime;
//	导入人员	import_operator	VARCHAR(20)	IMPORT_OPERATOR
	@Column(name="IMPORT_OPERATOR", length=20)
	private String importOperator;
//	导入时间	import_oper_time	DATE	IMPORT_OPER_TIME
	@Column(name="IMPORT_OPER_TIME")
	private Timestamp importOperTime;
//	合并状态	merge_stat	CHAR(1)	MERGE_STAT
	@Column(name="MERGE_STAT", length=1)
	private String mergeStat;
	//疑似客户标志
	@Column(name="SUSPECT_FLAG", length=1)
	private String suspectFlag;
	
//	MERGED_DESC
//	MERGE_OPERBRC
//	IMPORT_MERGE_OPERBRC
	@Column(name="MERGE_DESC")
	private String mergedDesc;
	@Column(name="MERGE_OPERBRC")
	private String mergeOperBrc;
	@Column(name="IMPORT_OPERBRC")
	private String importMergeOperBrc;
	
	public Long getMergeRecordId() {
		return mergeRecordId;
	}
	public void setMergeRecordId(Long mergeRecordId) {
		this.mergeRecordId = mergeRecordId;
	}
	public String getMergeCode() {
		return mergeCode;
	}
	public void setMergeCode(String mergeCode) {
		this.mergeCode = mergeCode;
	}
	public Long getReserveCustId() {
		return reserveCustId;
	}
	public void setReserveCustId(Long reserveCustId) {
		this.reserveCustId = reserveCustId;
	}
	public Long getMergedCustId() {
		return mergedCustId;
	}
	public void setMergedCustId(Long mergedCustId) {
		this.mergedCustId = mergedCustId;
	}
	public String getMergeOperator() {
		return mergeOperator;
	}
	public void setMergeOperator(String mergeOperator) {
		this.mergeOperator = mergeOperator;
	}
	public Timestamp getMergeOperTime() {
		return mergeOperTime;
	}
	public void setMergeOperTime(Timestamp mergeOperTime) {
		this.mergeOperTime = mergeOperTime;
	}
	public String getImportOperator() {
		return importOperator;
	}
	public void setImportOperator(String importOperator) {
		this.importOperator = importOperator;
	}
	public Timestamp getImportOperTime() {
		return importOperTime;
	}
	public void setImportOperTime(Timestamp importOperTime) {
		this.importOperTime = importOperTime;
	}
	public String getMergeStat() {
		return mergeStat;
	}
	public void setMergeStat(String mergeStat) {
		this.mergeStat = mergeStat;
	}
	public String getSuspectFlag() {
		return suspectFlag;
	}
	public void setSuspectFlag(String suspectFlag) {
		this.suspectFlag = suspectFlag;
	}
	public String getReserveCustNo() {
		return reserveCustNo;
	}
	public void setReserveCustNo(String reserveCustNo) {
		this.reserveCustNo = reserveCustNo;
	}
	public String getMergedCustNo() {
		return mergedCustNo;
	}
	public void setMergedCustNo(String mergedCustNo) {
		this.mergedCustNo = mergedCustNo;
	}
	public String getMergedDesc() {
		return mergedDesc;
	}
	public void setMergedDesc(String mergedDesc) {
		this.mergedDesc = mergedDesc;
	}
	public String getMergeOperBrc() {
		return mergeOperBrc;
	}
	public void setMergeOperBrc(String mergeOperBrc) {
		this.mergeOperBrc = mergeOperBrc;
	}
	public String getImportMergeOperBrc() {
		return importMergeOperBrc;
	}
	public void setImportMergeOperBrc(String importMergeOperBrc) {
		this.importMergeOperBrc = importMergeOperBrc;
	}

}
