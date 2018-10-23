package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AppSpeciallistApproval entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_SPECIALLIST_APPROVAL")
public class AppSpeciallistApproval implements java.io.Serializable {

	// Fields

	private Long speciallistApprovalId;
	private Long specialListId;
	private Long custId;
	private String specialListType;
	private String specialListKind;
	private String specialListFlag;
	private String identType;
	private String identNo;
	private String identCustName;
	private String enterReason;
	private String statFlag;
	private Date startDate;
	private Date endDate;
	private String operator;
	private Date operTime;
	private String operStat;
	private String approvalOperator;
	private Date approvalTime;
	private String approvalStat;
	private String approvalNote;

	// Constructors

	/** default constructor */
	public AppSpeciallistApproval() {
	}

	/** minimal constructor */
	public AppSpeciallistApproval(Long speciallistApprovalId) {
		this.speciallistApprovalId = speciallistApprovalId;
	}

	/** full constructor */
	public AppSpeciallistApproval(Long speciallistApprovalId,
			Long specialListId, Long custId, String specialListType,
			String specialListKind, String specialListFlag, String identType,
			String identNo, String identCustName, String enterReason,
			String statFlag, Date startDate, Date endDate, String operator,
			Date operTime, String operStat, String approvalOperator,
			Date approvalTime, String approvalStat, String approvalNote) {
		this.speciallistApprovalId = speciallistApprovalId;
		this.specialListId = specialListId;
		this.custId = custId;
		this.specialListType = specialListType;
		this.specialListKind = specialListKind;
		this.specialListFlag = specialListFlag;
		this.identType = identType;
		this.identNo = identNo;
		this.identCustName = identCustName;
		this.enterReason = enterReason;
		this.statFlag = statFlag;
		this.startDate = startDate;
		this.endDate = endDate;
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
	@Column(name = "SPECIALLIST_APPROVAL_ID", unique = true, nullable = false, scale = 0)
	public Long getSpeciallistApprovalId() {
		return this.speciallistApprovalId;
	}

	public void setSpeciallistApprovalId(Long speciallistApprovalId) {
		this.speciallistApprovalId = speciallistApprovalId;
	}

	@Column(name = "SPECIAL_LIST_ID", scale = 0)
	public Long getSpecialListId() {
		return this.specialListId;
	}

	public void setSpecialListId(Long specialListId) {
		this.specialListId = specialListId;
	}

	@Column(name = "CUST_ID", scale = 0)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	@Column(name = "SPECIAL_LIST_TYPE", length = 20)
	public String getSpecialListType() {
		return this.specialListType;
	}

	public void setSpecialListType(String specialListType) {
		this.specialListType = specialListType;
	}

	@Column(name = "SPECIAL_LIST_KIND", length = 20)
	public String getSpecialListKind() {
		return this.specialListKind;
	}

	public void setSpecialListKind(String specialListKind) {
		this.specialListKind = specialListKind;
	}

	@Column(name = "SPECIAL_LIST_FLAG", length = 1)
	public String getSpecialListFlag() {
		return this.specialListFlag;
	}

	public void setSpecialListFlag(String specialListFlag) {
		this.specialListFlag = specialListFlag;
	}

	@Column(name = "IDENT_TYPE", length = 20)
	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	@Column(name = "IDENT_NO", length = 40)
	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	@Column(name = "IDENT_CUST_NAME", length = 70)
	public String getIdentCustName() {
		return this.identCustName;
	}

	public void setIdentCustName(String identCustName) {
		this.identCustName = identCustName;
	}

	@Column(name = "ENTER_REASON", length = 18)
	public String getEnterReason() {
		return this.enterReason;
	}

	public void setEnterReason(String enterReason) {
		this.enterReason = enterReason;
	}

	@Column(name = "STAT_FLAG", length = 18)
	public String getStatFlag() {
		return this.statFlag;
	}

	public void setStatFlag(String statFlag) {
		this.statFlag = statFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "OPERATOR", length = 20)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "OPER_TIME", length = 7)
	public Date getOperTime() {
		return this.operTime;
	}

	public void setOperTime(Date operTime) {
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

	@Temporal(TemporalType.DATE)
	@Column(name = "APPROVAL_TIME", length = 7)
	public Date getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
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