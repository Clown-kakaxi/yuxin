package com.ytec.mdm.domain.biz;

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
 * The persistent class for the OCRM_F_CI_CUSTINFO_UPHIS database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CUSTINFO_UPHIS")
public class OcrmFCiCustinfoUphi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CommonSequnce", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CommonSequnce")

	@Column(name="UP_ID")
	private Long upId;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="UPDATE_AF_CONT")
	private String updateAfCont;

	@Column(name="UPDATE_BE_CONT")
	private String updateBeCont;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="UPDATE_ITEM")
	private String updateItem;

	@Column(name="UPDATE_USER")
	private String updateUser;
	
	@Column(name="UPDATE_FLAG")
	private String updateFlag;
	
	@Column(name="UPDATE_ITEM_EN")
	private String updateItemEn;
	
	@Column(name="UPDATE_TABLE")
	private String updateTable;
	
	@Column(name="UPDATE_AF_CONT_VIEW")
	private String updateAfContView;
	
	@Column(name="FIELD_TYPE")
	private String fieldType;
	
	@Column(name="UPDATE_TABLE_ID")
	private String updateTableId;
	
	@Column(name="APPR_FLAG")
	private String apprFlag;
	
	@Column(name="APPR_USER")
	private String apprUser;
	
	@Column(name="APPR_DATE")
	private String apprDate;
	
	@Column(name="UPDATE_BE_CONT_VIEW")
	private String updateBeContView;

    public String getUpdateTable() {
		return updateTable;
	}

	public void setUpdateTable(String updateTable) {
		this.updateTable = updateTable;
	}

	public String getUpdateItemEn() {
		return updateItemEn;
	}

	public void setUpdateItemEn(String updateItemEn) {
		this.updateItemEn = updateItemEn;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public OcrmFCiCustinfoUphi() {
    }

	public Long getUpId() {
		return this.upId;
	}

	public void setUpId(Long upId) {
		this.upId = upId;
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

	public String getUpdateAfContView() {
		return updateAfContView;
	}

	public void setUpdateAfContView(String updateAfContView) {
		this.updateAfContView = updateAfContView;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getUpdateTableId() {
		return updateTableId;
	}

	public void setUpdateTableId(String updateTableId) {
		this.updateTableId = updateTableId;
	}

	public String getApprFlag() {
		return apprFlag;
	}

	public void setApprFlag(String apprFlag) {
		this.apprFlag = apprFlag;
	}

	public String getApprUser() {
		return apprUser;
	}

	public void setApprUser(String apprUser) {
		this.apprUser = apprUser;
	}

	public String getApprDate() {
		return apprDate;
	}

	public void setApprDate(String apprDate) {
		this.apprDate = apprDate;
	}

	public String getUpdateBeContView() {
		return updateBeContView;
	}

	public void setUpdateBeContView(String updateBeContView) {
		this.updateBeContView = updateBeContView;
	}

	
}