package com.yuchengtech.emp.ecif.transaction.entity;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the TX_MSG database table.
 * 
 */
@Entity
@Table(name="TX_MSG")
public class TxMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_MSG_MSGID_GENERATOR")
//	@GenericGenerator(name = "TX_MSG_MSGID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_MSG") })
	@GenericGenerator(name = "TX_MSG_MSGID_GENERATOR", strategy = "com.yuchengtech.emp.ecif.base.util.IncrementGenerator")
	@Column(name="MSG_ID")
	private Long msgId;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER", length=20)
	private String createUser;

	@Column(name="MAIN_MSG_ROOT", length=40)
	private String mainMsgRoot;

	@Column(name="MSG_DESC", length=80)
	private String msgDesc;

	@Column(name="MSG_NAME", nullable=false, length=40)
	private String msgName;

	@Column(name="MSG_TP", nullable=false, length=1)
	private String msgTp;

	@Column(name="STATE", length=1)
	private String state;

	@Column(name="TX_ID", nullable=false)
	private Long txId;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER", length=20)
	private String updateUser;

    public TxMsg() {
    }
    
    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
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

	public String getMainMsgRoot() {
		return this.mainMsgRoot;
	}

	public void setMainMsgRoot(String mainMsgRoot) {
		this.mainMsgRoot = mainMsgRoot;
	}

	public String getMsgDesc() {
		return this.msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

	public String getMsgName() {
		return this.msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	public String getMsgTp() {
		return this.msgTp;
	}

	public void setMsgTp(String msgTp) {
		this.msgTp = msgTp;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getTxId() {
		return this.txId;
	}

	public void setTxId(Long txId) {
		this.txId = txId;
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