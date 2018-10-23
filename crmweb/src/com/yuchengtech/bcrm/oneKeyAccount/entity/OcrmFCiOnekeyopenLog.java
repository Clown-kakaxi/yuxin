package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the OCRM_F_CI_ONEKEYOPEN_LOG database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_ONEKEYOPEN_LOG")
@NamedQuery(name="OcrmFCiOnekeyopenLog.findAll", query="SELECT o FROM OcrmFCiOnekeyopenLog o")
public class OcrmFCiOnekeyopenLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_ONEKEYOPEN_LOG")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="LOG_ID")
	private String logId;

	private String reason;

	@Lob
	@Column(name="REQ_MSG")
	private String reqMsg;

	@Lob
	@Column(name="RES_MSG")
	private String resMsg;

	private String status;

	private String step;

	public OcrmFCiOnekeyopenLog() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReqMsg() {
		return this.reqMsg;
	}

	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}

	public String getResMsg() {
		return this.resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStep() {
		return this.step;
	}

	public void setStep(String step) {
		this.step = step;
	}

}