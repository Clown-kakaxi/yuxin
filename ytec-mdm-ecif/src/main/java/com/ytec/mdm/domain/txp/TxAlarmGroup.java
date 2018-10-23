package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxAlarmGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_ALARM_GROUP")
public class TxAlarmGroup implements java.io.Serializable {

	// Fields

	private Long groupId;
	private String groupName;
	private String groupDesc;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxAlarmGroup() {
	}

	/** minimal constructor */
	public TxAlarmGroup(Long groupId) {
		this.groupId = groupId;
	}

	/** full constructor */
	public TxAlarmGroup(Long groupId, String groupName, String groupDesc,
			Timestamp createTm, String createUser, Timestamp updateTm,
			String updateUser) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.groupDesc = groupDesc;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
	@Column(name = "GROUP_ID", unique = true, nullable = false)
	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Column(name = "GROUP_NAME", length = 40)
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "GROUP_DESC")
	public String getGroupDesc() {
		return this.groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
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