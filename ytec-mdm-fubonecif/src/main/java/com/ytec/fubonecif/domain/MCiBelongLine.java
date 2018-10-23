package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiBelongLine entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_BELONG_LINE")
public class MCiBelongLine implements java.io.Serializable {

	// Fields

	private String belongLineId;
	private String custId;
	private String belongLineType;
	private String belongLineNo;
	private String mainType;
	private String validFlag;
	private Date startDate;
	private Date endDate;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiBelongLine() {
	}

	/** minimal constructor */
	public MCiBelongLine(String belongLineId) {
		this.belongLineId = belongLineId;
	}

	/** full constructor */
	public MCiBelongLine(String belongLineId, String custId,
			String belongLineType, String belongLineNo, String mainType,
			String validFlag, Date startDate, Date endDate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.belongLineId = belongLineId;
		this.custId = custId;
		this.belongLineType = belongLineType;
		this.belongLineNo = belongLineNo;
		this.mainType = mainType;
		this.validFlag = validFlag;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "BELONG_LINE_ID", unique = true, nullable = false, length = 20)
	public String getBelongLineId() {
		return this.belongLineId;
	}

	public void setBelongLineId(String belongLineId) {
		this.belongLineId = belongLineId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "BELONG_LINE_TYPE", length = 20)
	public String getBelongLineType() {
		return this.belongLineType;
	}

	public void setBelongLineType(String belongLineType) {
		this.belongLineType = belongLineType;
	}

	@Column(name = "BELONG_LINE_NO", length = 20)
	public String getBelongLineNo() {
		return this.belongLineNo;
	}

	public void setBelongLineNo(String belongLineNo) {
		this.belongLineNo = belongLineNo;
	}

	@Column(name = "MAIN_TYPE", length = 20)
	public String getMainType() {
		return this.mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	@Column(name = "VALID_FLAG", length = 1)
	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
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

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}