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
 * The persistent class for the OCRM_F_WP_REMIND database table.
 * 
 */
@Entity
@Table(name="OCRM_F_WP_REMIND")
public class OcrmFWpRemindLast implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_WP_REMIND_INFOID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_WP_REMIND_INFOID_GENERATOR")
	@Column(name="INFO_ID", unique=true, nullable=false, precision=22)
	private Long infoId;
	@Column(name="ACCOUNT_AMT")
	private BigDecimal accountAmt;

	@Column(name="ACORE_AMT")
	private BigDecimal acoreAmt;

	@Column(name="ACTIVE_NAME")
	private String activeName;

	@Column(name="AFTER_LEVEL")
	private String afterLevel;

	@Column(name="BEFORE_LEVEL")
	private String beforeLevel;

	@Column(name="BIRTHDAY_M")
	private String birthdayM;

	@Column(name="BIRTHDAY_S")
	private String birthdayS;

	@Column(name="CHANGE_ACCOUNT")
	private String changeAccount;

	@Column(name="CHANGE_AMT")
	private BigDecimal changeAmt;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="DUE_AMT")
	private BigDecimal dueAmt;

	@Column(name="FUND_AMT")
	private BigDecimal fundAmt;

	@Column(name="HAPPENED_DATE")
	private String happenedDate;

	@Column(name="IF_CALL")
	private String ifCall;

	@Column(name="IF_MAIL")
	private String ifMail;

	@Column(name="IF_MESSAGE")
	private String ifMessage;

	@Column(name="IF_MICRO")
	private String ifMicro;


	@Column(name="LAST_DATE")
	private BigDecimal lastDate;

	@Column(name="MAIL_REMARK")
	private String mailRemark;

	@Column(name="MESSAGE_REMARK")
	private String messageRemark;

	@Column(name="MICRO_REMARK")
	private String microRemark;

	@Temporal(TemporalType.DATE)
	@Column(name="MSG_CRT_DATE")
	private Date msgCrtDate;

	@Temporal(TemporalType.DATE)
	@Column(name="MSG_END_DATE")
	private Date msgEndDate;

	@Column(name="NEW_MGR")
	private String newMgr;

	@Column(name="OLD_MGR")
	private String oldMgr;

	@Column(name="OPERATE_MGR")
	private String operateMgr;

	@Column(name="PRODUCT_AMT")
	private BigDecimal productAmt;

	@Column(name="PRODUCT_NAME")
	private String productName;

	@Column(name="PRODUCT_NO")
	private String productNo;

	@Column(name="REMIND_REMARK")
	private String remindRemark;

	@Column(name="RULE_CODE")
	private String ruleCode;

	@Column(name="RULE_ID")
	private BigDecimal ruleId;

	@Column(name="SCORE_CHANGE")
	private BigDecimal scoreChange;

	@Column(name="USER_ID")
	private String userId;

	public OcrmFWpRemindLast() {
	}

	public BigDecimal getAccountAmt() {
		return this.accountAmt;
	}

	public void setAccountAmt(BigDecimal accountAmt) {
		this.accountAmt = accountAmt;
	}

	public BigDecimal getAcoreAmt() {
		return this.acoreAmt;
	}

	public void setAcoreAmt(BigDecimal acoreAmt) {
		this.acoreAmt = acoreAmt;
	}

	public String getActiveName() {
		return this.activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public String getAfterLevel() {
		return this.afterLevel;
	}

	public void setAfterLevel(String afterLevel) {
		this.afterLevel = afterLevel;
	}

	public String getBeforeLevel() {
		return this.beforeLevel;
	}

	public void setBeforeLevel(String beforeLevel) {
		this.beforeLevel = beforeLevel;
	}

	public String getBirthdayM() {
		return this.birthdayM;
	}

	public void setBirthdayM(String birthdayM) {
		this.birthdayM = birthdayM;
	}

	public String getBirthdayS() {
		return this.birthdayS;
	}

	public void setBirthdayS(String birthdayS) {
		this.birthdayS = birthdayS;
	}

	public String getChangeAccount() {
		return this.changeAccount;
	}

	public void setChangeAccount(String changeAccount) {
		this.changeAccount = changeAccount;
	}

	public BigDecimal getChangeAmt() {
		return this.changeAmt;
	}

	public void setChangeAmt(BigDecimal changeAmt) {
		this.changeAmt = changeAmt;
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

	public BigDecimal getDueAmt() {
		return this.dueAmt;
	}

	public void setDueAmt(BigDecimal dueAmt) {
		this.dueAmt = dueAmt;
	}

	public BigDecimal getFundAmt() {
		return this.fundAmt;
	}

	public void setFundAmt(BigDecimal fundAmt) {
		this.fundAmt = fundAmt;
	}

	public String getHappenedDate() {
		return this.happenedDate;
	}

	public void setHappenedDate(String happenedDate) {
		this.happenedDate = happenedDate;
	}

	public String getIfCall() {
		return this.ifCall;
	}

	public void setIfCall(String ifCall) {
		this.ifCall = ifCall;
	}

	public String getIfMail() {
		return this.ifMail;
	}

	public void setIfMail(String ifMail) {
		this.ifMail = ifMail;
	}

	public String getIfMessage() {
		return this.ifMessage;
	}

	public void setIfMessage(String ifMessage) {
		this.ifMessage = ifMessage;
	}

	public String getIfMicro() {
		return this.ifMicro;
	}

	public void setIfMicro(String ifMicro) {
		this.ifMicro = ifMicro;
	}

	public Long getInfoId() {
		return this.infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public BigDecimal getLastDate() {
		return this.lastDate;
	}

	public void setLastDate(BigDecimal lastDate) {
		this.lastDate = lastDate;
	}

	public String getMailRemark() {
		return this.mailRemark;
	}

	public void setMailRemark(String mailRemark) {
		this.mailRemark = mailRemark;
	}

	public String getMessageRemark() {
		return this.messageRemark;
	}

	public void setMessageRemark(String messageRemark) {
		this.messageRemark = messageRemark;
	}

	public String getMicroRemark() {
		return this.microRemark;
	}

	public void setMicroRemark(String microRemark) {
		this.microRemark = microRemark;
	}

	public Date getMsgCrtDate() {
		return this.msgCrtDate;
	}

	public void setMsgCrtDate(Date msgCrtDate) {
		this.msgCrtDate = msgCrtDate;
	}

	public Date getMsgEndDate() {
		return this.msgEndDate;
	}

	public void setMsgEndDate(Date msgEndDate) {
		this.msgEndDate = msgEndDate;
	}

	public String getNewMgr() {
		return this.newMgr;
	}

	public void setNewMgr(String newMgr) {
		this.newMgr = newMgr;
	}

	public String getOldMgr() {
		return this.oldMgr;
	}

	public void setOldMgr(String oldMgr) {
		this.oldMgr = oldMgr;
	}

	public String getOperateMgr() {
		return this.operateMgr;
	}

	public void setOperateMgr(String operateMgr) {
		this.operateMgr = operateMgr;
	}

	public BigDecimal getProductAmt() {
		return this.productAmt;
	}

	public void setProductAmt(BigDecimal productAmt) {
		this.productAmt = productAmt;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNo() {
		return this.productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getRemindRemark() {
		return this.remindRemark;
	}

	public void setRemindRemark(String remindRemark) {
		this.remindRemark = remindRemark;
	}

	public String getRuleCode() {
		return this.ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public BigDecimal getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(BigDecimal ruleId) {
		this.ruleId = ruleId;
	}

	public BigDecimal getScoreChange() {
		return this.scoreChange;
	}

	public void setScoreChange(BigDecimal scoreChange) {
		this.scoreChange = scoreChange;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}