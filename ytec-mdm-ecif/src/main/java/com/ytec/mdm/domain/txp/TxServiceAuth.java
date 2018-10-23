package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxServiceAuth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_SERVICE_AUTH")
public class TxServiceAuth implements java.io.Serializable {

	// Fields

	private Long serviceAuthId;
	private Long clientAuthId;
	private String txCode;
	private String txName;
	private String authType;
	private Date startDt;
	private Date endDt;
	private String flag;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxServiceAuth() {
	}

	/** full constructor */
	public TxServiceAuth(Long clientAuthId, String txCode, String txName,
			String authType, Date startDt, Date endDt, String flag,
			String state, Timestamp createTm, String createUser,
			Timestamp updateTm, String updateUser) {
		this.clientAuthId = clientAuthId;
		this.txCode = txCode;
		this.txName = txName;
		this.authType = authType;
		this.startDt = startDt;
		this.endDt = endDt;
		this.flag = flag;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "SERVICE_AUTH_ID", unique = true, nullable = false)
	public Long getServiceAuthId() {
		return this.serviceAuthId;
	}

	public void setServiceAuthId(Long serviceAuthId) {
		this.serviceAuthId = serviceAuthId;
	}

	@Column(name = "CLIENT_AUTH_ID")
	public Long getClientAuthId() {
		return this.clientAuthId;
	}

	public void setClientAuthId(Long clientAuthId) {
		this.clientAuthId = clientAuthId;
	}

	@Column(name = "TX_CODE", length = 20)
	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	@Column(name = "TX_NAME", length = 40)
	public String getTxName() {
		return this.txName;
	}

	public void setTxName(String txName) {
		this.txName = txName;
	}

	@Column(name = "AUTH_TYPE", length = 10)
	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DT", length = 7)
	public Date getStartDt() {
		return this.startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DT", length = 7)
	public Date getEndDt() {
		return this.endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	@Column(name = "FLAG", length = 1)
	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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