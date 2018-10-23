package com.yuchengtech.bcrm.customer.customerView.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_CI_CUSTINFO_UPHIS_TEMP database table.
 * 客户基本信息调整临时表
 */
@Entity
@Table(name="OCRM_F_CI_CUSTINFO_UPHIS_TEMP")
public class OcrmFCiCustinfoUphisTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUSTINFO_UPHIS_TEMP_UPID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUSTINFO_UPHIS_TEMP_UPID_GENERATOR")
	@Column(name="UP_ID")
	private long upId;

	@Column(name="APPROVAL_STATUS")
	private String approvalStatus;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="IS_APPROVAL")
	private String isApproval;
	
	@Column(name="UPDATE_ITEM_CODE")
	private String updateItemCode;

	@Column(name="UPDATE_AF_CONT")
	private String updateAfCont;

	@Column(name="UPDATE_BE_CONT")
	private String updateBeCont;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="UPDATE_ITEM")
	private String updateItem;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public OcrmFCiCustinfoUphisTemp() {
    }

	public String getUpdateItemCode() {
		return updateItemCode;
	}

	public void setUpdateItemCode(String updateItemCode) {
		this.updateItemCode = updateItemCode;
	}

	public long getUpId() {
		return this.upId;
	}

	public void setUpId(long upId) {
		this.upId = upId;
	}

	public String getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getIsApproval() {
		return this.isApproval;
	}

	public void setIsApproval(String isApproval) {
		this.isApproval = isApproval;
	}

	public String getUpdateAfCont() {
		return this.updateAfCont;
	}

	public void setUpdateAfCont(String updateAfCont) {
		this.updateAfCont = updateAfCont;
	}

	public String getUpdateBeCont() {
		return this.updateBeCont;
	}

	public void setUpdateBeCont(String updateBeCont) {
		this.updateBeCont = updateBeCont;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateItem() {
		return this.updateItem;
	}

	public void setUpdateItem(String updateItem) {
		this.updateItem = updateItem;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}