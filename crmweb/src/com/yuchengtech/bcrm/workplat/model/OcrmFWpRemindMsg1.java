package com.yuchengtech.bcrm.workplat.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the OCRM_F_WP_REMIND_MSG database table.
 * 
 */
@Entity
@Table(name="OCRM_F_WP_REMIND_MSG")
public class OcrmFWpRemindMsg1 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_WP_REMIND_MSG_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_WP_REMIND_MSG_ID_GENERATOR")
	private Long id;

	@Column(name="CELL_NUMBER")
	private String cellNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="CTR_DATE")
	private Date ctrDate;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="IF_SEND")
	private String ifSend;

	@Column(name="MAIL_ADDR")
	private String mailAddr;

	@Column(name="MESSAGE_REMARK")
	private String messageRemark;

	@Column(name="MICRO_NUMBER")
	private String microNumber;

	@Column(name="MODEL_ID")
	private String modelId;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="PROD_ID")
	private String prodId;

	@Column(name="PROD_NAME")
	private String prodName;

	@Column(name="REMIND_ID")
	private BigDecimal remindId;

	@Temporal(TemporalType.DATE)
	@Column(name="SEND_DATE")
	private Date sendDate;

	@Column(name="USER_ID")
	private String userId;

	public OcrmFWpRemindMsg1() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCellNumber() {
		return this.cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public Date getCtrDate() {
		return this.ctrDate;
	}

	public void setCtrDate(Date ctrDate) {
		this.ctrDate = ctrDate;
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

	public String getIfSend() {
		return this.ifSend;
	}

	public void setIfSend(String ifSend) {
		this.ifSend = ifSend;
	}

	public String getMailAddr() {
		return this.mailAddr;
	}

	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}

	public String getMessageRemark() {
		return this.messageRemark;
	}

	public void setMessageRemark(String messageRemark) {
		this.messageRemark = messageRemark;
	}

	public String getMicroNumber() {
		return this.microNumber;
	}

	public void setMicroNumber(String microNumber) {
		this.microNumber = microNumber;
	}

	public String getModelId() {
		return this.modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return this.prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public BigDecimal getRemindId() {
		return this.remindId;
	}

	public void setRemindId(BigDecimal remindId) {
		this.remindId = remindId;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}