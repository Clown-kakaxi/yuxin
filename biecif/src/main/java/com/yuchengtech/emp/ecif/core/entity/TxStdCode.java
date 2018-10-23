package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TX_STD_CODE database table.
 * 
 */
@Entity
@Table(name="TX_STD_CODE")
public class TxStdCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TxStdCodePK id;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="ORDER_ID")
	private BigDecimal orderId;

	@Column(name="PARENT_STD_CODE")
	private String parentStdCode;

	@Column(name="STATE")
	private String state;

	@Column(name="STD_CODE_DESC")
	private String stdCodeDesc;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public TxStdCode() {
    }

	public TxStdCodePK getId() {
		return this.id;
	}

	public void setId(TxStdCodePK id) {
		this.id = id;
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

	public BigDecimal getOrderId() {
		return this.orderId;
	}

	public void setOrderId(BigDecimal orderId) {
		this.orderId = orderId;
	}

	public String getParentStdCode() {
		return this.parentStdCode;
	}

	public void setParentStdCode(String parentStdCode) {
		this.parentStdCode = parentStdCode;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStdCodeDesc() {
		return this.stdCodeDesc;
	}

	public void setStdCodeDesc(String stdCodeDesc) {
		this.stdCodeDesc = stdCodeDesc;
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