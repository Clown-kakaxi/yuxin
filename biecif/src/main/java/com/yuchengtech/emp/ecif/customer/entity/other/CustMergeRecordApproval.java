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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


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
@Table(name="APP_CUST_MERGE_RECORD_APPROVAL")
public class CustMergeRecordApproval implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	CUST_MERGE_RECORD_APPROVAL_ID	客户合并标识	BIGINT	19
	@Id
	@GeneratedValue(generator = "MERGE_RECORD_ID_GENERATOR_APP")
	@GenericGenerator(name = "MERGE_RECORD_ID_GENERATOR_APP", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_APP_CUST_MERGE_RECORD_APPROVAL_ID") })	
	@Column(name="CUST_MERGE_RECORD_APPROVAL_ID", unique=true, nullable=false)
	private Long mergeRecordApprovalId;
//	RESERVE_CUST_NO	保留客户号	VARCHAR	32
	@Column(name="RESERVE_CUST_NO", length=32)
	private String reserveCustNo;
//	MERGED_CUST_NO	被合并客户号	VARCHAR	32
	@Column(name="MERGED_CUST_NO", length=32)
	private String mergedCustNo;
//	MERGE_OPERATOR	合并提交人	VARCHAR	20
	@Column(name="MERGE_OPERATOR", length=20)
	private String mergeOperator;
//	MERGE_OPER_TIME	合并提交时间	DATE	10
	@Column(name="MERGE_OPER_TIME")
	private Timestamp mergeOperTime;
//	IMPORT_OPERATOR	导入人员	VARCHAR	20
	@Column(name="IMPORT_OPERATOR", length=20)
	private String importOperator;
//	IMPORT_OPER_TIME	导入时间	DATE	10
	@Column(name="IMPORT_OPER_TIME")
	private Timestamp importOperTime;
//	APPROVAL_OPERATOR	审批人	VARCHAR	20
//	APPROVAL_TIME	审批时间	DATE	10
//	APPROVAL_STAT	审批状态	VARCHAR	2
//	APPROVAL_NOTE	审批意见	VARCHAR	200
	@Column(name="APPROVAL_OPERATOR", length=20)
	private String approvalOperator;
	@Column(name="APPROVAL_TIME")
	private Timestamp approvalTime;
	@Column(name="APPROVAL_STAT", length=2)
	private String approvalStat;
	@Column(name="APPROVAL_NOTE", length=200)
	private String approvalNote;
	
	//
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
	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getMergeRecordApprovalId() {
		return mergeRecordApprovalId;
	}
	public void setMergeRecordApprovalId(Long mergeRecordApprovalId) {
		this.mergeRecordApprovalId = mergeRecordApprovalId;
	}
	public String getApprovalOperator() {
		return approvalOperator;
	}
	public void setApprovalOperator(String approvalOperator) {
		this.approvalOperator = approvalOperator;
	}
	public Timestamp getApprovalTime() {
		return approvalTime;
	}
	public void setApprovalTime(Timestamp approvalTime) {
		this.approvalTime = approvalTime;
	}
	public String getApprovalStat() {
		return approvalStat;
	}
	public void setApprovalStat(String approvalStat) {
		this.approvalStat = approvalStat;
	}
	public String getApprovalNote() {
		return approvalNote;
	}
	public void setApprovalNote(String approvalNote) {
		this.approvalNote = approvalNote;
	}

}
