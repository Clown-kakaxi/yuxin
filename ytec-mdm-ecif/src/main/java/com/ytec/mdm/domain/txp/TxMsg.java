package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxMsg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_MSG")
public class TxMsg implements java.io.Serializable {

	// Fields

	private Long msgId;
	private Long txId;
	private String msgName;
	private String msgDesc;
	private String msgTp;
	private String mainMsgRoot;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxMsg() {
	}

	/** full constructor */
	public TxMsg(Long txId, String msgName, String msgDesc, String msgTp,
			String mainMsgRoot, String state, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.txId = txId;
		this.msgName = msgName;
		this.msgDesc = msgDesc;
		this.msgTp = msgTp;
		this.mainMsgRoot = mainMsgRoot;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "MSG_ID", unique = true, nullable = false)
	public Long getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	@Column(name = "TX_ID")
	public Long getTxId() {
		return this.txId;
	}

	public void setTxId(Long txId) {
		this.txId = txId;
	}

	@Column(name = "MSG_NAME", length = 40)
	public String getMsgName() {
		return this.msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	@Column(name = "MSG_DESC", length = 80)
	public String getMsgDesc() {
		return this.msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

	@Column(name = "MSG_TP", length = 1)
	public String getMsgTp() {
		return this.msgTp;
	}

	public void setMsgTp(String msgTp) {
		this.msgTp = msgTp;
	}

	@Column(name = "MAIN_MSG_ROOT", length = 40)
	public String getMainMsgRoot() {
		return this.mainMsgRoot;
	}

	public void setMainMsgRoot(String mainMsgRoot) {
		this.mainMsgRoot = mainMsgRoot;
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