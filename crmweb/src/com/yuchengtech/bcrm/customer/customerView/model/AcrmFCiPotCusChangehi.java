package com.yuchengtech.bcrm.customer.customerView.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the ACRM_F_CI_POT_CUS_CHANGEHIS database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_POT_CUS_CHANGEHIS")
public class AcrmFCiPotCusChangehi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUS_ID")
	private String cusId;

	@Column(name="CUS_FOR_MGR")
	private String cusForMgr;
	
	@Column(name="EDIT_AFTER_CONTENT")
	private String editAfterContent;

	@Column(name="EDIT_AFTER_ITEM")
	private String editAfterItem;

	@Column(name="EDIT_BEFORE_CONTENT")
	private String editBeforeContent;

	@Column(name="EDIT_BEFORE_ITEM")
	private String editBeforeItem;

	@Column(name="EDIT_DATE")
	private String editDate;

	@Column(name="EDIT_USER")
	private String editUser;

	public AcrmFCiPotCusChangehi() {
	}

	public String getCusForMgr() {
		return this.cusForMgr;
	}

	public void setCusForMgr(String cusForMgr) {
		this.cusForMgr = cusForMgr;
	}

	public String getCusId() {
		return this.cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getEditAfterContent() {
		return this.editAfterContent;
	}

	public void setEditAfterContent(String editAfterContent) {
		this.editAfterContent = editAfterContent;
	}

	public String getEditAfterItem() {
		return this.editAfterItem;
	}

	public void setEditAfterItem(String editAfterItem) {
		this.editAfterItem = editAfterItem;
	}

	public String getEditBeforeContent() {
		return this.editBeforeContent;
	}

	public void setEditBeforeContent(String editBeforeContent) {
		this.editBeforeContent = editBeforeContent;
	}

	public String getEditBeforeItem() {
		return this.editBeforeItem;
	}

	public void setEditBeforeItem(String editBeforeItem) {
		this.editBeforeItem = editBeforeItem;
	}

	public String getEditDate() {
		return this.editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

	public String getEditUser() {
		return this.editUser;
	}

	public void setEditUser(String editUser) {
		this.editUser = editUser;
	}

}