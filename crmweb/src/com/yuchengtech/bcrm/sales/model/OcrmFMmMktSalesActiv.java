package com.yuchengtech.bcrm.sales.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the OCRM_F_MM_MKT_SALES_ACTIV database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MM_MKT_SALES_ACTIV")
public class OcrmFMmMktSalesActiv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_MM_MKT_SALES_ACTIV_SALESACTIVID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_MM_MKT_SALES_ACTIV_SALESACTIVID_GENERATOR")
	@Column(name="SALES_ACTIV_ID")
	private String salesActivId;

	@Column(name="ACTIV_CONTENT")
	private String activContent;

	@Column(name="ACTIV_MEMO")
	private String activMemo;

	@Temporal(TemporalType.DATE)
	@Column(name="EXEC_DATE")
	private Date execDate;

	@Column(name="EXEC_ORG_ID")
	private String execOrgId;

	@Column(name="EXEC_ORG_NAME")
	private String execOrgName;

	@Column(name="EXEC_USER_ID")
	private String execUserId;

	@Column(name="EXEC_USER_NAME")
	private String execUserName;

	@Column(name="EXEC_WAY")
	private String execWay;

	@Column(name="NEXT_CONTACT_TIME")
	private Timestamp nextContactTime;

	@Column(name="NEXT_EXEC_CONTENT")
	private String nextExecContent;

	@Column(name="NEXT_EXEC_WAY")
	private String nextExecWay;

	@Column(name="OPPOR_ID")
	private String opporId;

	@Column(name="SALES_ACTIV_NAME")
	private String salesActivName;

	@Column(name="SALES_STAGE")
	private String salesStage;

	public OcrmFMmMktSalesActiv() {
	}

	public String getSalesActivId() {
		return this.salesActivId;
	}

	public void setSalesActivId(String salesActivId) {
		this.salesActivId = salesActivId;
	}

	public String getActivContent() {
		return this.activContent;
	}

	public void setActivContent(String activContent) {
		this.activContent = activContent;
	}

	public String getActivMemo() {
		return this.activMemo;
	}

	public void setActivMemo(String activMemo) {
		this.activMemo = activMemo;
	}

	public Date getExecDate() {
		return this.execDate;
	}

	public void setExecDate(Date execDate) {
		this.execDate = execDate;
	}

	public String getExecOrgId() {
		return this.execOrgId;
	}

	public void setExecOrgId(String execOrgId) {
		this.execOrgId = execOrgId;
	}

	public String getExecOrgName() {
		return this.execOrgName;
	}

	public void setExecOrgName(String execOrgName) {
		this.execOrgName = execOrgName;
	}

	public String getExecUserId() {
		return this.execUserId;
	}

	public void setExecUserId(String execUserId) {
		this.execUserId = execUserId;
	}

	public String getExecUserName() {
		return this.execUserName;
	}

	public void setExecUserName(String execUserName) {
		this.execUserName = execUserName;
	}

	public String getExecWay() {
		return this.execWay;
	}

	public void setExecWay(String execWay) {
		this.execWay = execWay;
	}

	public Timestamp getNextContactTime() {
		return this.nextContactTime;
	}

	public void setNextContactTime(Timestamp nextContactTime) {
		this.nextContactTime = nextContactTime;
	}

	public String getNextExecContent() {
		return this.nextExecContent;
	}

	public void setNextExecContent(String nextExecContent) {
		this.nextExecContent = nextExecContent;
	}

	public String getNextExecWay() {
		return this.nextExecWay;
	}

	public void setNextExecWay(String nextExecWay) {
		this.nextExecWay = nextExecWay;
	}

	public String getOpporId() {
		return this.opporId;
	}

	public void setOpporId(String opporId) {
		this.opporId = opporId;
	}

	public String getSalesActivName() {
		return this.salesActivName;
	}

	public void setSalesActivName(String salesActivName) {
		this.salesActivName = salesActivName;
	}

	public String getSalesStage() {
		return this.salesStage;
	}

	public void setSalesStage(String salesStage) {
		this.salesStage = salesStage;
	}

}