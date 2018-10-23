package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TX_STD_CODE database table.
 * 
 */

public class TxStdCodeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String stdCode;

	public String getStdCode() {
		return stdCode;
	}

	public void setStdCode(String stdCode) {
		this.stdCode = stdCode;
	}

	public String getStdCate() {
		return stdCate;
	}

	public void setStdCate(String stdCate) {
		this.stdCate = stdCate;
	}

	private String stdCate;

	private Timestamp createTm;

	private String createUser;

	private BigDecimal orderId;

	private String parentStdCode;

	private String state;

	private String stdCodeDesc;

	private Timestamp updateTm;

	private String updateUser;

    public TxStdCodeVO() {
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