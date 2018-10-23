package com.yuchengtech.emp.ecif.transaction.entity;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the TX_SERVICE_AUTH database table.
 * 
 */
@Entity
@Table(name="TX_SERVICE_AUTH")
public class TxServiceAuth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_SERVICE_AUTH_SERVICEAUTHID_GENERATOR")
	@GenericGenerator(name = "TX_SERVICE_AUTH_SERVICEAUTHID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_SERVICE_AUTH") })
	@Column(name="SERVICE_AUTH_ID")
	private Long serviceAuthId;

	@Column(name="AUTH_TYPE")
	private String authType;

	@Column(name="CLIENT_AUTH_ID")
	private Long clientAuthId;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DT")
	private Date endDt;

	private String flag;

    @Temporal( TemporalType.DATE)
	@Column(name="START_DT")
	private Date startDt;

	@Column(name="STATE")
	private String state;

	@Column(name="TX_CODE")
	private String txCode;

	@Column(name="TX_NAME")
	private String txName;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public TxServiceAuth() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getServiceAuthId() {
		return this.serviceAuthId;
	}

	public void setServiceAuthId(Long serviceAuthId) {
		this.serviceAuthId = serviceAuthId;
	}

	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getClientAuthId() {
		return this.clientAuthId;
	}

	public void setClientAuthId(Long clientAuthId) {
		this.clientAuthId = clientAuthId;
	}

	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getEndDt() {
		return this.endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getStartDt() {
		return this.startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getTxName() {
		return this.txName;
	}

	public void setTxName(String txName) {
		this.txName = txName;
	}

	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}