package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxAlarmUserCont entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_ALARM_USER_CONT")
public class TxAlarmUserCont implements java.io.Serializable {

	// Fields

	private Long contmethId;
	private Long userId;
	private String contmethType;
	private String contmethInfo;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxAlarmUserCont() {
	}

	/** minimal constructor */
	public TxAlarmUserCont(Long contmethId) {
		this.contmethId = contmethId;
	}

	/** full constructor */
	public TxAlarmUserCont(Long contmethId, Long userId,
			String contmethType, String contmethInfo, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.contmethId = contmethId;
		this.userId = userId;
		this.contmethType = contmethType;
		this.contmethInfo = contmethInfo;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
	@Column(name = "CONTMETH_ID", unique = true, nullable = false)
	public Long getContmethId() {
		return this.contmethId;
	}

	public void setContmethId(Long contmethId) {
		this.contmethId = contmethId;
	}

	@Column(name = "USER_ID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "CONTMETH_TYPE", length = 20)
	public String getContmethType() {
		return this.contmethType;
	}

	public void setContmethType(String contmethType) {
		this.contmethType = contmethType;
	}

	@Column(name = "CONTMETH_INFO", length = 40)
	public String getContmethInfo() {
		return this.contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

	@Column(name = "CREATE_TM", length = 11)
	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	@Column(name = "CREATE_USER", length = 20)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "UPDATE_TM", length = 11)
	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	@Column(name = "UPDATE_USER", length = 20)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}