/**
 * 
 */
package com.yuchengtech.emp.ecif.customer.entity.other;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

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
@Table(name="APP_SPECIALLIST_APPROVAL")
public class SpecialListApproval implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "SPECIALLIST_APPROVAL_ID_GENERATOR")
	@GenericGenerator(name = "SPECIALLIST_APPROVAL_ID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_SPECIAL_LIST_ID_APPROVAL") })	
	@Column(name="SPECIALLIST_APPROVAL_ID", unique=true, nullable=false)
	private Long specialListApprovalId;
//	特殊名单标识	special_list_id	BIGINT
	@Column(name="SPECIAL_LIST_ID")
	private Long specialListId;
//	客户标识	cust_id	BIGINT
	@Column(name="CUST_ID")
	private Long custId;
//	特殊名单类型	special_list_type	VARCHAR(20)
	@Column(name="SPECIAL_LIST_TYPE", length=20)
	private String specialListType;
//	特殊名单标志	special_list_flag	CHAR(1)
	@Column(name="SPECIAL_LIST_FLAG", length=1)
	private String specialListFlag;
//	证件类型	ident_type	VARCHAR(20)
	@Column(name="IDENT_TYPE", length=20)
	private String identType;
//	证件号码	ident_no	VARCHAR(40)
	@Column(name="IDENT_NO", length=40)
	private String identNo;
//	证件户名	ident_cust_name	VARCHAR(80)
	@Column(name="IDENT_CUST_NAME", length=80)
	private String identCustName;
//	列入原因	enter_reason	VARCHAR(18)
	@Column(name="ENTER_REASON", length=18)
	private String enterReason;
//	状态标志	stat_flag	VARCHAR(18)
	@Column(name="STAT_FLAG", length=18)
	private String statFlag;
//	起始日期	start_date	DATE
	@Column(name="START_DATE")
	private Date startDate;
//	结束日期	end_date	DATE
	@Column(name="END_DATE")
	private Date endDate;
//	提交人	operator	VARCHAR(20)	OPERATOR
	@Column(name="OPERATOR", length=20)
	private String operator;
//	提交时间	oper_time	DATE	OPER_TIME
	@Column(name="OPER_TIME")
	private Timestamp operTime;
//	操作状态	oper_stat	VARCHAR(2)	OPER_STAT
	@Column(name="OPER_STAT", length=2)
	private String operStat;
//	审批人	approval_operator	VARCHAR(20)	APPROVAL_OPERATOR
	@Column(name="APPROVAL_OPERATOR", length=20)
	private String approvalOperator;
//	审批时间	approval_time	DATE	APPROVAL_TIME
	@Column(name="APPROVAL_TIME", nullable=true)
	private Timestamp approvalTime;
//	审批状态	approval_stat	VARCHAR(2)	APPROVAL_STAT
	@Column(name="APPROVAL_STAT", length=2)
	private String approvalStat;
//	审批意见	approval_note	VARCHAR(200)	APPROVAL_NOTE
	@Column(name="APPROVAL_NOTE", length=200)
	private String approvalNote;
//	黑名单类型	SPECIAL_LIST_KIND	VARCHAR(20)
	@Column(name="SPECIAL_LIST_KIND", length=20)
	private String specialListKind;
	
	public Long getSpecialListId() {
		return specialListId;
	}
	public void setSpecialListId(Long specialListId) {
		this.specialListId = specialListId;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getSpecialListType() {
		return specialListType;
	}
	public void setSpecialListType(String specialListType) {
		this.specialListType = specialListType;
	}
	public String getSpecialListFlag() {
		return specialListFlag;
	}
	public void setSpecialListFlag(String specialListFlag) {
		this.specialListFlag = specialListFlag;
	}
	public String getIdentType() {
		return identType;
	}
	public void setIdentType(String identType) {
		this.identType = identType;
	}
	public String getIdentNo() {
		return identNo;
	}
	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}
	public String getIdentCustName() {
		return identCustName;
	}
	public void setIdentCustName(String identCustName) {
		this.identCustName = identCustName;
	}
	public String getEnterReason() {
		return enterReason;
	}
	public void setEnterReason(String enterReason) {
		this.enterReason = enterReason;
	}
	public String getStatFlag() {
		return statFlag;
	}
	public void setStatFlag(String statFlag) {
		this.statFlag = statFlag;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Timestamp getOperTime() {
		return operTime;
	}
	public void setOperTime(Timestamp operTime) {
		this.operTime = operTime;
	}
	public String getOperStat() {
		return operStat;
	}
	public void setOperStat(String operStat) {
		this.operStat = operStat;
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
	public Long getSpecialListApprovalId() {
		return specialListApprovalId;
	}
	public void setSpecialListApprovalId(Long specialListApprovalId) {
		this.specialListApprovalId = specialListApprovalId;
	}
	public String getSpecialListKind() {
		return specialListKind;
	}
	public void setSpecialListKind(String specialListKind) {
		this.specialListKind = specialListKind;
	}
}
