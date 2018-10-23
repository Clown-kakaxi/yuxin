package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxMsgCheckinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_MSG_CHECKINFO")
public class TxMsgCheckinfo implements java.io.Serializable {

	// Fields

	private Long checkId;
	private Long msgId;
	private String checkinfo;
	private String descinfo;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxMsgCheckinfo() {
	}

	/** full constructor */
	public TxMsgCheckinfo(Long msgId, String checkinfo, String descinfo,
			String state, Timestamp createTm, String createUser,
			Timestamp updateTm, String updateUser) {
		this.msgId = msgId;
		this.checkinfo = checkinfo;
		this.descinfo = descinfo;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "CHECK_ID", unique = true, nullable = false)
	public Long getCheckId() {
		return this.checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	@Column(name = "MSG_ID")
	public Long getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	@Column(name = "CHECKINFO")
	public String getCheckinfo() {
		return this.checkinfo;
	}

	public void setCheckinfo(String checkinfo) {
		this.checkinfo = checkinfo;
	}

	@Column(name = "DESCINFO")
	public String getDescinfo() {
		return this.descinfo;
	}

	public void setDescinfo(String descinfo) {
		this.descinfo = descinfo;
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