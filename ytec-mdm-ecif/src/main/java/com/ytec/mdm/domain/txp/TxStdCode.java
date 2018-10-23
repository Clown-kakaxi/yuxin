package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxStdCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_STD_CODE")
public class TxStdCode implements java.io.Serializable {

	// Fields

	private TxStdCodeId id;
	private String stdCodeDesc;
	private String parentStdCode;
	private Long orderId;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxStdCode() {
	}

	/** minimal constructor */
	public TxStdCode(TxStdCodeId id) {
		this.id = id;
	}

	/** full constructor */
	public TxStdCode(TxStdCodeId id, String stdCodeDesc, String parentStdCode,
			Long orderId, String state, Timestamp createTm,
			String createUser, Timestamp updateTm, String updateUser) {
		this.id = id;
		this.stdCodeDesc = stdCodeDesc;
		this.parentStdCode = parentStdCode;
		this.orderId = orderId;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "stdCode", column = @Column(name = "STD_CODE", nullable = false, length = 20)),
			@AttributeOverride(name = "stdCate", column = @Column(name = "STD_CATE", nullable = false, length = 10)) })
	public TxStdCodeId getId() {
		return this.id;
	}

	public void setId(TxStdCodeId id) {
		this.id = id;
	}

	@Column(name = "STD_CODE_DESC", length = 200)
	public String getStdCodeDesc() {
		return this.stdCodeDesc;
	}

	public void setStdCodeDesc(String stdCodeDesc) {
		this.stdCodeDesc = stdCodeDesc;
	}

	@Column(name = "PARENT_STD_CODE", length = 20)
	public String getParentStdCode() {
		return this.parentStdCode;
	}

	public void setParentStdCode(String parentStdCode) {
		this.parentStdCode = parentStdCode;
	}

	@Column(name = "ORDER_ID", precision = 22)
	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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