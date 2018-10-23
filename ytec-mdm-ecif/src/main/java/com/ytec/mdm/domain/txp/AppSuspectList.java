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
 * AppSuspectList entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_SUSPECT_LIST")
public class AppSuspectList implements java.io.Serializable {

	// Fields

	private Long suspectListId;
	private Long suspectGroupId;
	private Long custId;
	private String reserveFlag;
	private String suspectConfirmFlag;
	private String suspectConfirmResult;
	private String suspectConfirmOperator;
	private String mergeDealFlag;
	private Timestamp mergeDealDate;
	private String custNo;
	private String custType;
	private String custName;
	private Date createDate;

	// Constructors

	/** default constructor */
	public AppSuspectList() {
	}

	/** minimal constructor */
	public AppSuspectList(Long suspectListId) {
		this.suspectListId = suspectListId;
	}

	/** full constructor */
	public AppSuspectList(Long suspectListId, Long suspectGroupId, Long custId,
			String reserveFlag, String suspectConfirmFlag,
			String suspectConfirmResult, String suspectConfirmOperator,
			String mergeDealFlag, Timestamp mergeDealDate, String custNo,
			String custType, String custName, Date createDate) {
		this.suspectListId = suspectListId;
		this.suspectGroupId = suspectGroupId;
		this.custId = custId;
		this.reserveFlag = reserveFlag;
		this.suspectConfirmFlag = suspectConfirmFlag;
		this.suspectConfirmResult = suspectConfirmResult;
		this.suspectConfirmOperator = suspectConfirmOperator;
		this.mergeDealFlag = mergeDealFlag;
		this.mergeDealDate = mergeDealDate;
		this.custNo = custNo;
		this.custType = custType;
		this.custName = custName;
		this.createDate = createDate;
	}

	// Property accessors
	@Id
	@Column(name = "SUSPECT_LIST_ID", unique = true, nullable = false, scale = 0)
	public Long getSuspectListId() {
		return this.suspectListId;
	}

	public void setSuspectListId(Long suspectListId) {
		this.suspectListId = suspectListId;
	}

	@Column(name = "SUSPECT_GROUP_ID", scale = 0)
	public Long getSuspectGroupId() {
		return this.suspectGroupId;
	}

	public void setSuspectGroupId(Long suspectGroupId) {
		this.suspectGroupId = suspectGroupId;
	}

	@Column(name = "CUST_ID", scale = 0)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	@Column(name = "RESERVE_FLAG", length = 18)
	public String getReserveFlag() {
		return this.reserveFlag;
	}

	public void setReserveFlag(String reserveFlag) {
		this.reserveFlag = reserveFlag;
	}

	@Column(name = "SUSPECT_CONFIRM_FLAG", length = 1)
	public String getSuspectConfirmFlag() {
		return this.suspectConfirmFlag;
	}

	public void setSuspectConfirmFlag(String suspectConfirmFlag) {
		this.suspectConfirmFlag = suspectConfirmFlag;
	}

	@Column(name = "SUSPECT_CONFIRM_RESULT", length = 20)
	public String getSuspectConfirmResult() {
		return this.suspectConfirmResult;
	}

	public void setSuspectConfirmResult(String suspectConfirmResult) {
		this.suspectConfirmResult = suspectConfirmResult;
	}

	@Column(name = "SUSPECT_CONFIRM_OPERATOR", length = 20)
	public String getSuspectConfirmOperator() {
		return this.suspectConfirmOperator;
	}

	public void setSuspectConfirmOperator(String suspectConfirmOperator) {
		this.suspectConfirmOperator = suspectConfirmOperator;
	}

	@Column(name = "MERGE_DEAL_FLAG", length = 1)
	public String getMergeDealFlag() {
		return this.mergeDealFlag;
	}

	public void setMergeDealFlag(String mergeDealFlag) {
		this.mergeDealFlag = mergeDealFlag;
	}

	@Column(name = "MERGE_DEAL_DATE", length = 11)
	public Timestamp getMergeDealDate() {
		return this.mergeDealDate;
	}

	public void setMergeDealDate(Timestamp mergeDealDate) {
		this.mergeDealDate = mergeDealDate;
	}

	@Column(name = "CUST_NO", length = 32)
	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	@Column(name = "CUST_TYPE", length = 20)
	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	@Column(name = "CUST_NAME", length = 70)
	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}