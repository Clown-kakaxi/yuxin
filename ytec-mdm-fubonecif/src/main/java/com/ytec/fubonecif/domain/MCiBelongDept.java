package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiBelongDept entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_BELONG_DEPT")
public class MCiBelongDept implements java.io.Serializable {

	// Fields

	private String belongDeptId;
	private String custId;
	private String belongDept;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiBelongDept() {
	}

	/** minimal constructor */
	public MCiBelongDept(String belongDeptId) {
		this.belongDeptId = belongDeptId;
	}

	/** full constructor */
	public MCiBelongDept(String belongDeptId, String custId, String belongDept,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.belongDeptId = belongDeptId;
		this.custId = custId;
		this.belongDept = belongDept;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "BELONG_DEPT_ID", unique = true, nullable = false, length = 20)
	public String getBelongDeptId() {
		return this.belongDeptId;
	}

	public void setBelongDeptId(String belongDeptId) {
		this.belongDeptId = belongDeptId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "BELONG_DEPT", length = 20)
	public String getBelongDept() {
		return this.belongDept;
	}

	public void setBelongDept(String belongDept) {
		this.belongDept = belongDept;
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