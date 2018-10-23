package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxAlarmUserGroupRel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_ALARM_USER_GROUP_REL")
public class TxAlarmUserGroupRel implements java.io.Serializable {

	// Fields

	private Long relId;
	private Long userId;
	private Long groupId;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxAlarmUserGroupRel() {
	}

	/** minimal constructor */
	public TxAlarmUserGroupRel(Long relId) {
		this.relId = relId;
	}

	/** full constructor */
	public TxAlarmUserGroupRel(Long relId, Long userId,
			Long groupId, Timestamp createTm, String createUser,
			Timestamp updateTm, String updateUser) {
		this.relId = relId;
		this.userId = userId;
		this.groupId = groupId;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
	@Column(name = "REL_ID", unique = true, nullable = false)
	public Long getRelId() {
		return this.relId;
	}

	public void setRelId(Long relId) {
		this.relId = relId;
	}

	@Column(name = "USER_ID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "GROUP_ID")
	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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