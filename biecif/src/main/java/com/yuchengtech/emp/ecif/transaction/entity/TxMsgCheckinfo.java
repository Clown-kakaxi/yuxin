package com.yuchengtech.emp.ecif.transaction.entity;


import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.sql.Timestamp;


/**
 * The persistent class for the TX_MSG_CHECKINFO database table.
 * 
 */
@Entity
@Table(name="TX_MSG_CHECKINFO")
public class TxMsgCheckinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CHECK_ID")
	private Long checkId;

    @Lob()
	private String checkinfo;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

    @Lob()
	private String descinfo;

	@Column(name="MSG_ID")
	private Long msgId;

	@Column(name="STATE")
	private String state;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public TxMsgCheckinfo() {
    }
    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCheckId() {
		return this.checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	public String getCheckinfo() {
		return this.checkinfo;
	}

	public void setCheckinfo(String checkinfo) {
		this.checkinfo = checkinfo;
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

	public String getDescinfo() {
		return this.descinfo;
	}

	public void setDescinfo(String descinfo) {
		this.descinfo = descinfo;
	}

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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