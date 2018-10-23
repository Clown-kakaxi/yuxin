package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxMsgNodeAttrCt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_MSG_NODE_ATTR_CT")
public class TxMsgNodeAttrCt implements java.io.Serializable {

	// Fields

	private Long ctId;
	private Long attrId;
	private String ctFlag;
	private String ctRule;
	private String ctDesc;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxMsgNodeAttrCt() {
	}

	/** full constructor */
	public TxMsgNodeAttrCt(Long attrId, String ctFlag, String ctRule,
			String ctDesc, String state, Timestamp createTm, String createUser,
			Timestamp updateTm, String updateUser) {
		this.attrId = attrId;
		this.ctFlag = ctFlag;
		this.ctRule = ctRule;
		this.ctDesc = ctDesc;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
		@Column(name = "CT_ID", unique = true, nullable = false)
	public Long getCtId() {
		return this.ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	@Column(name = "ATTR_ID")
	public Long getAttrId() {
		return this.attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	@Column(name = "CT_FLAG", length = 1)
	public String getCtFlag() {
		return this.ctFlag;
	}

	public void setCtFlag(String ctFlag) {
		this.ctFlag = ctFlag;
	}

	@Column(name = "CT_RULE", length = 6)
	public String getCtRule() {
		return this.ctRule;
	}

	public void setCtRule(String ctRule) {
		this.ctRule = ctRule;
	}

	@Column(name = "CT_DESC")
	public String getCtDesc() {
		return this.ctDesc;
	}

	public void setCtDesc(String ctDesc) {
		this.ctDesc = ctDesc;
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