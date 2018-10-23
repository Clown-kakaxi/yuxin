package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxEvtNotice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_EVT_NOTICE")
public class TxEvtNotice implements java.io.Serializable {

	// Fields

	private Long eventId;
	private String eventName;
	private String eventType;
	private String eventKind;
	private String eventDesc;
	private Timestamp eventTime;
	private String eventLocation;
	private String eventSysNo;
	private String txCode;
	private String txFwId;
	private Long custId;
	private String custNo;
	private String keyInfo;
	private String eventDealType;
	private String eventDealStat;
	private Timestamp eventDealTime;
	private String eventDealResult;
	private String eventDealInfo;
	private Long manualEvtId;
	private String manualFlag;
	private String manualStat;
	// Constructors

	/** default constructor */
	public TxEvtNotice() {
	}

	/** full constructor */
	public TxEvtNotice(String eventName, String eventType, String eventKind,
			String eventDesc, Timestamp eventTime, String eventLocation,
			String eventSysNo, String txCode, String txFwId, Long custId,
			String custNo, String keyInfo, String eventDealType,
			String eventDealStat, Timestamp eventDealTime,
			String eventDealResult, String eventDealInfo,Long manualEvtId,String manualFlag,String manualStat) {
		this.eventName = eventName;
		this.eventType = eventType;
		this.eventKind = eventKind;
		this.eventDesc = eventDesc;
		this.eventTime = eventTime;
		this.eventLocation = eventLocation;
		this.eventSysNo = eventSysNo;
		this.txCode = txCode;
		this.txFwId = txFwId;
		this.custId = custId;
		this.custNo = custNo;
		this.keyInfo = keyInfo;
		this.eventDealType = eventDealType;
		this.eventDealStat = eventDealStat;
		this.eventDealTime = eventDealTime;
		this.eventDealResult = eventDealResult;
		this.eventDealInfo = eventDealInfo;
		this.manualEvtId = manualEvtId;
		this.manualFlag = manualFlag;
		this.manualStat = manualStat;
	}

	// Property accessors
	@Id
		@Column(name = "EVENT_ID", unique = true, nullable = false)
	public Long getEventId() {
		return this.eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@Column(name = "EVENT_NAME", length = 40)
	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	@Column(name = "EVENT_TYPE", length = 20)
	public String getEventType() {
		return this.eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Column(name = "EVENT_KIND", length = 20)
	public String getEventKind() {
		return this.eventKind;
	}

	public void setEventKind(String eventKind) {
		this.eventKind = eventKind;
	}

	@Column(name = "EVENT_DESC")
	public String getEventDesc() {
		return this.eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	@Column(name = "EVENT_TIME", length = 11)
	public Timestamp getEventTime() {
		return this.eventTime;
	}

	public void setEventTime(Timestamp eventTime) {
		this.eventTime = eventTime;
	}

	@Column(name = "EVENT_LOCATION")
	public String getEventLocation() {
		return this.eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	@Column(name = "EVENT_SYS_NO", length = 20)
	public String getEventSysNo() {
		return this.eventSysNo;
	}

	public void setEventSysNo(String eventSysNo) {
		this.eventSysNo = eventSysNo;
	}

	@Column(name = "TX_CODE", length = 32)
	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	@Column(name = "TX_FW_ID", length = 20)
	public String getTxFwId() {
		return this.txFwId;
	}

	public void setTxFwId(String txFwId) {
		this.txFwId = txFwId;
	}

	@Column(name = "CUST_ID")
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	@Column(name = "CUST_NO", length = 32)
	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	@Column(name = "KEY_INFO")
	public String getKeyInfo() {
		return this.keyInfo;
	}

	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}

	@Column(name = "EVENT_DEAL_TYPE", length = 20)
	public String getEventDealType() {
		return this.eventDealType;
	}

	public void setEventDealType(String eventDealType) {
		this.eventDealType = eventDealType;
	}

	@Column(name = "EVENT_DEAL_STAT", length = 20)
	public String getEventDealStat() {
		return this.eventDealStat;
	}

	public void setEventDealStat(String eventDealStat) {
		this.eventDealStat = eventDealStat;
	}

	@Column(name = "EVENT_DEAL_TIME", length = 11)
	public Timestamp getEventDealTime() {
		return this.eventDealTime;
	}

	public void setEventDealTime(Timestamp eventDealTime) {
		this.eventDealTime = eventDealTime;
	}

	@Column(name = "EVENT_DEAL_RESULT", length = 20)
	public String getEventDealResult() {
		return this.eventDealResult;
	}

	public void setEventDealResult(String eventDealResult) {
		this.eventDealResult = eventDealResult;
	}

	@Column(name = "EVENT_DEAL_INFO")
	public String getEventDealInfo() {
		return this.eventDealInfo;
	}

	public void setEventDealInfo(String eventDealInfo) {
		this.eventDealInfo = eventDealInfo;
	}

	@Column(name = "MANUAL_EVT_ID")
	public Long getManualEvtId() {
		return manualEvtId;
	}

	public void setManualEvtId(Long manualEvtId) {
		this.manualEvtId = manualEvtId;
	}

	@Column(name = "MANUAL_FLAG")
	public String getManualFlag() {
		return manualFlag;
	}

	public void setManualFlag(String manualFlag) {
		this.manualFlag = manualFlag;
	}

	@Column(name = "MANUAL_STAT")
	public String getManualStat() {
		return manualStat;
	}

	public void setManualStat(String manualStat) {
		this.manualStat = manualStat;
	}

}