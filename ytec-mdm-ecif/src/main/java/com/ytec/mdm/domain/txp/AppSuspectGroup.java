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
 * AppSuspectGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_SUSPECT_GROUP")
public class AppSuspectGroup implements java.io.Serializable {

	// Fields

	private Long suspectGroupId;
	private String suspectGroupDesc;
	private Date suspectDataDate;
	private Date suspectSearchDate;
	private String suspectExportFlag;
	private String suspectConfirmFlag;
	private String suspectConfirmResult;
	private String suspectConfirmOperator;
	private String mergeDealFlag;
	private Timestamp mergeDealDate;
	private Long reserveCustId;
	private String enterReason;

	// Constructors

	/** default constructor */
	public AppSuspectGroup() {
	}

	/** minimal constructor */
	public AppSuspectGroup(Long suspectGroupId) {
		this.suspectGroupId = suspectGroupId;
	}

	/** full constructor */
	public AppSuspectGroup(Long suspectGroupId, String suspectGroupDesc,
			Date suspectDataDate, Date suspectSearchDate,
			String suspectExportFlag, String suspectConfirmFlag,
			String suspectConfirmResult, String suspectConfirmOperator,
			String mergeDealFlag, Timestamp mergeDealDate, Long reserveCustId,
			String enterReason) {
		this.suspectGroupId = suspectGroupId;
		this.suspectGroupDesc = suspectGroupDesc;
		this.suspectDataDate = suspectDataDate;
		this.suspectSearchDate = suspectSearchDate;
		this.suspectExportFlag = suspectExportFlag;
		this.suspectConfirmFlag = suspectConfirmFlag;
		this.suspectConfirmResult = suspectConfirmResult;
		this.suspectConfirmOperator = suspectConfirmOperator;
		this.mergeDealFlag = mergeDealFlag;
		this.mergeDealDate = mergeDealDate;
		this.reserveCustId = reserveCustId;
		this.enterReason = enterReason;
	}

	// Property accessors
	@Id
	@Column(name = "SUSPECT_GROUP_ID", unique = true, nullable = false, scale = 0)
	public Long getSuspectGroupId() {
		return this.suspectGroupId;
	}

	public void setSuspectGroupId(Long suspectGroupId) {
		this.suspectGroupId = suspectGroupId;
	}

	@Column(name = "SUSPECT_GROUP_DESC", length = 80)
	public String getSuspectGroupDesc() {
		return this.suspectGroupDesc;
	}

	public void setSuspectGroupDesc(String suspectGroupDesc) {
		this.suspectGroupDesc = suspectGroupDesc;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SUSPECT_DATA_DATE", length = 7)
	public Date getSuspectDataDate() {
		return this.suspectDataDate;
	}

	public void setSuspectDataDate(Date suspectDataDate) {
		this.suspectDataDate = suspectDataDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SUSPECT_SEARCH_DATE", length = 7)
	public Date getSuspectSearchDate() {
		return this.suspectSearchDate;
	}

	public void setSuspectSearchDate(Date suspectSearchDate) {
		this.suspectSearchDate = suspectSearchDate;
	}

	@Column(name = "SUSPECT_EXPORT_FLAG", length = 1)
	public String getSuspectExportFlag() {
		return this.suspectExportFlag;
	}

	public void setSuspectExportFlag(String suspectExportFlag) {
		this.suspectExportFlag = suspectExportFlag;
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

	@Column(name = "RESERVE_CUST_ID", scale = 0)
	public Long getReserveCustId() {
		return this.reserveCustId;
	}

	public void setReserveCustId(Long reserveCustId) {
		this.reserveCustId = reserveCustId;
	}

	@Column(name = "ENTER_REASON", length = 200)
	public String getEnterReason() {
		return this.enterReason;
	}

	public void setEnterReason(String enterReason) {
		this.enterReason = enterReason;
	}

}