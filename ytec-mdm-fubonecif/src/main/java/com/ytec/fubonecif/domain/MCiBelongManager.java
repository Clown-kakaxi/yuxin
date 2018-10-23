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
 * MCiBelongManager entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_BELONG_MANAGER")
public class MCiBelongManager implements java.io.Serializable {

	// Fields

	private String belongManagerId;
	private String custId;
	private String custManagerType;
	private String custManagerNo;
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
	public MCiBelongManager() {
	}

	/** minimal constructor */
	public MCiBelongManager(String belongManagerId) {
		this.belongManagerId = belongManagerId;
	}

	/** full constructor */
	public MCiBelongManager(String belongManagerId, String custId,
			String custManagerType, String custManagerNo, String mainType,
			String validFlag, Date startDate, Date endDate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.belongManagerId = belongManagerId;
		this.custId = custId;
		this.custManagerType = custManagerType;
		this.custManagerNo = custManagerNo;
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
	@Column(name = "BELONG_MANAGER_ID", unique = true, nullable = false, length = 20)
	public String getBelongManagerId() {
		return this.belongManagerId;
	}

	public void setBelongManagerId(String belongManagerId) {
		this.belongManagerId = belongManagerId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "CUST_MANAGER_TYPE", length = 20)
	public String getCustManagerType() {
		return this.custManagerType;
	}

	public void setCustManagerType(String custManagerType) {
		this.custManagerType = custManagerType;
	}

	@Column(name = "CUST_MANAGER_NO", length = 20)
	public String getCustManagerNo() {
		return this.custManagerNo;
	}

	public void setCustManagerNo(String custManagerNo) {
		this.custManagerNo = custManagerNo;
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