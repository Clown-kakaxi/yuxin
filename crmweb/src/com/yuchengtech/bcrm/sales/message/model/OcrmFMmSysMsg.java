package com.yuchengtech.bcrm.sales.message.model;

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
 * The persistent class for the OCRM_F_MM_SYS_MSG database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MM_SYS_MSG")
public class OcrmFMmSysMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_MM_SYS_MSG_ID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_MM_SYS_MSG_ID_GENERATOR")
	private long id;

	@Column(name="APPROVE_STATE")
	private String approveState;

	@Column(name="CELL_NUMBER")
	private String cellNumber;

    @Temporal( TemporalType.DATE)
	@Column(name="CRT_DATE")
	private Date crtDate;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="FEEDBACK_CONT")
	private String feedbackCont;

    @Temporal( TemporalType.DATE)
	@Column(name="FEEDBACK_DATE")
	private Date feedbackDate;

	@Column(name="FEEDBACK_USER_ID")
	private String feedbackUserId;

	@Column(name="FEEDBACK_USER_NAME")
	private String feedbackUserName;

	@Column(name="IF_APPROVE")
	private String ifApprove;

	@Column(name="IS_FEEDBACK")
	private String isFeedback;

	@Column(name="MAIL_ADDRESS")
	private String mailAddress;

	@Column(name="MESSAGE_REMARK")
	private String messageRemark;

	@Column(name="MODEL_ID")
	private String modelId;

	@Column(name="MODEL_NAME")
	private String modelName;

	@Column(name="MODEL_SOURCE")
	private String modelSource;

	@Column(name="MODEL_TYPE")
	private String modelType;

	@Column(name="PROD_ID")
	private String prodId;

	@Column(name="PROD_NAME")
	private String prodName;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="WX_ACCOUNT")
	private String wxAccount;
	
	@Column(name="MSGSENDTIME")
	private String msgsendtime;

    public String getMsgsendtime() {
		return msgsendtime;
	}

	public void setMsgsendtime(String msgsendtime) {
		this.msgsendtime = msgsendtime;
	}

	public OcrmFMmSysMsg() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApproveState() {
		return this.approveState;
	}

	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}

	public String getCellNumber() {
		return this.cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public Date getCrtDate() {
		return this.crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
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

	public String getFeedbackCont() {
		return this.feedbackCont;
	}

	public void setFeedbackCont(String feedbackCont) {
		this.feedbackCont = feedbackCont;
	}

	public Date getFeedbackDate() {
		return this.feedbackDate;
	}

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getFeedbackUserId() {
		return this.feedbackUserId;
	}

	public void setFeedbackUserId(String feedbackUserId) {
		this.feedbackUserId = feedbackUserId;
	}

	public String getFeedbackUserName() {
		return this.feedbackUserName;
	}

	public void setFeedbackUserName(String feedbackUserName) {
		this.feedbackUserName = feedbackUserName;
	}

	public String getIfApprove() {
		return this.ifApprove;
	}

	public void setIfApprove(String ifApprove) {
		this.ifApprove = ifApprove;
	}

	public String getIsFeedback() {
		return this.isFeedback;
	}

	public void setIsFeedback(String isFeedback) {
		this.isFeedback = isFeedback;
	}

	public String getMailAddress() {
		return this.mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getMessageRemark() {
		return this.messageRemark;
	}

	public void setMessageRemark(String messageRemark) {
		this.messageRemark = messageRemark;
	}

	public String getModelId() {
		return this.modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelSource() {
		return this.modelSource;
	}

	public void setModelSource(String modelSource) {
		this.modelSource = modelSource;
	}

	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWxAccount() {
		return this.wxAccount;
	}

	public void setWxAccount(String wxAccount) {
		this.wxAccount = wxAccount;
	}

}