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
 * MCiPerJobresume entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_JOBRESUME")
public class MCiPerJobresume implements java.io.Serializable {

	// Fields

	private String jobresumeId;
	private String custId;
	private Date startDate;
	private Date endDate;
	private String unitChar;
	private String unitName;
	private String workDept;
	private String position;
	private String unitTel;
	private String unitAddress;
	private String unitZipcode;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiPerJobresume() {
	}

	/** minimal constructor */
	public MCiPerJobresume(String jobresumeId) {
		this.jobresumeId = jobresumeId;
	}

	/** full constructor */
	public MCiPerJobresume(String jobresumeId, String custId, Date startDate,
			Date endDate, String unitChar, String unitName, String workDept,
			String position, String unitTel, String unitAddress,
			String unitZipcode, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.jobresumeId = jobresumeId;
		this.custId = custId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.unitChar = unitChar;
		this.unitName = unitName;
		this.workDept = workDept;
		this.position = position;
		this.unitTel = unitTel;
		this.unitAddress = unitAddress;
		this.unitZipcode = unitZipcode;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "JOBRESUME_ID", unique = true, nullable = false, length = 20)
	public String getJobresumeId() {
		return this.jobresumeId;
	}

	public void setJobresumeId(String jobresumeId) {
		this.jobresumeId = jobresumeId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	@Column(name = "UNIT_CHAR", length = 20)
	public String getUnitChar() {
		return this.unitChar;
	}

	public void setUnitChar(String unitChar) {
		this.unitChar = unitChar;
	}

	@Column(name = "UNIT_NAME", length = 200)
	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name = "WORK_DEPT", length = 80)
	public String getWorkDept() {
		return this.workDept;
	}

	public void setWorkDept(String workDept) {
		this.workDept = workDept;
	}

	@Column(name = "POSITION", length = 80)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "UNIT_TEL", length = 20)
	public String getUnitTel() {
		return this.unitTel;
	}

	public void setUnitTel(String unitTel) {
		this.unitTel = unitTel;
	}

	@Column(name = "UNIT_ADDRESS", length = 200)
	public String getUnitAddress() {
		return this.unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	@Column(name = "UNIT_ZIPCODE", length = 32)
	public String getUnitZipcode() {
		return this.unitZipcode;
	}

	public void setUnitZipcode(String unitZipcode) {
		this.unitZipcode = unitZipcode;
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