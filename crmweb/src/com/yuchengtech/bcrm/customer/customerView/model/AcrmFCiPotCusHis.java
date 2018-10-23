package com.yuchengtech.bcrm.customer.customerView.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 分配回收历史记录表
 * @author sony
 *
 */
@Entity
@Table(name="ACRM_F_CI_POT_CUS_HIS")
public class AcrmFCiPotCusHis implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUS_ID")
	private String cusId;

	@Column(name="CUS_NAME")
	private String cusName;
	
	@Column(name="STATE")
	private String state;
	
	@Column(name="MOVER_USER")
	private String moverUser;
	
	@Column(name="MOVER_DATE")
	private String moverDate;

	 public AcrmFCiPotCusHis() {
	 }

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMoverUser() {
		return moverUser;
	}

	public void setMoverUser(String moverUser) {
		this.moverUser = moverUser;
	}

	public String getMoverDate() {
		return moverDate;
	}

	public void setMoverDate(String moverDate) {
		this.moverDate = moverDate;
	}
	 
	 
}
