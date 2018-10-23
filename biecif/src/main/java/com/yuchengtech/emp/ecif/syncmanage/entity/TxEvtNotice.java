package com.yuchengtech.emp.ecif.syncmanage.entity;

//import Column;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the TX_EVT_NOTICE database table.
 * 
 */
@Entity
@Table(name="TX_EVT_NOTICE")
public class TxEvtNotice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="TX_EVT_NOTICE_EVENTID_GENERATOR", sequenceName="SEQ_TX_EVT_NOTICE")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TX_EVT_NOTICE_EVENTID_GENERATOR")
	@Column(name="EVENT_ID")
	private long eventId;

	@Column(name="CUST_ID")
	private BigDecimal custId;

	@Column(name="CUST_NO")
	private String custNo;

	@Column(name="EVENT_DEAL_INFO")
	private String eventDealInfo;

	@Column(name="EVENT_DEAL_RESULT")
	private String eventDealResult;

	@Column(name="EVENT_DEAL_STAT")
	private String eventDealStat;

	@Column(name="EVENT_DEAL_TIME")
	private Timestamp eventDealTime;

	@Column(name="EVENT_DEAL_TYPE")
	private String eventDealType;

	@Column(name="EVENT_DESC")
	private String eventDesc;

	@Column(name="EVENT_KIND")
	private String eventKind;

	@Column(name="EVENT_LOCATION")
	private String eventLocation;

	@Column(name="EVENT_NAME")
	private String eventName;

	@Column(name="EVENT_SYS_NO")
	private String eventSysNo;

	@Column(name="EVENT_TIME")
	private Timestamp eventTime;

	@Column(name="EVENT_TYPE")
	private String eventType;

	@Column(name="KEY_INFO")
	private String keyInfo;

	@Column(name="TX_CODE")
	private String txCode;

	@Column(name="TX_FW_ID")
	private String txFwId;
	@Column(name="MANUAL_EVT_ID")
	private BigDecimal manualEvtId;

	@Column(name="MANUAL_FLAG")
	private String manualFlag;

	@Column(name="MANUAL_STAT")
	private String manualStat;
	
    public TxEvtNotice() {
    }
    @JsonSerialize(using=BioneLongSerializer.class)
	public long getEventId() {
		return this.eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public BigDecimal getCustId() {
		return this.custId;
	}

	public void setCustId(BigDecimal custId) {
		this.custId = custId;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getEventDealInfo() {
		return this.eventDealInfo;
	}

	public void setEventDealInfo(String eventDealInfo) {
		this.eventDealInfo = eventDealInfo;
	}

	public String getEventDealResult() {
		return this.eventDealResult;
	}

	public void setEventDealResult(String eventDealResult) {
		this.eventDealResult = eventDealResult;
	}

	public String getEventDealStat() {
		return this.eventDealStat;
	}

	public void setEventDealStat(String eventDealStat) {
		this.eventDealStat = eventDealStat;
	}

	public Timestamp getEventDealTime() {
		return this.eventDealTime;
	}

	public void setEventDealTime(Timestamp eventDealTime) {
		this.eventDealTime = eventDealTime;
	}

	public String getEventDealType() {
		return this.eventDealType;
	}

	public void setEventDealType(String eventDealType) {
		this.eventDealType = eventDealType;
	}

	public String getEventDesc() {
		return this.eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public String getEventKind() {
		return this.eventKind;
	}

	public void setEventKind(String eventKind) {
		this.eventKind = eventKind;
	}

	public String getEventLocation() {
		return this.eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventSysNo() {
		return this.eventSysNo;
	}

	public void setEventSysNo(String eventSysNo) {
		this.eventSysNo = eventSysNo;
	}

	public Timestamp getEventTime() {
		return this.eventTime;
	}

	public void setEventTime(Timestamp eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventType() {
		return this.eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getKeyInfo() {
		return this.keyInfo;
	}

	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}

	public String getTxCode() {
		return this.txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getTxFwId() {
		return this.txFwId;
	}

	public void setTxFwId(String txFwId) {
		this.txFwId = txFwId;
	}
	
	public BigDecimal getManualEvtId() {
		return this.manualEvtId;
	}

	public void setManualEvtId(BigDecimal manualEvtId) {
		this.manualEvtId = manualEvtId;
	}
	
	public String getManualFlag() {
		return this.manualFlag;
	}

	public void setManualFlag(String manualFlag) {
		this.manualFlag = manualFlag;
	}

	public String getManualStat() {
		return this.manualStat;
	}

	public void setManualStat(String manualStat) {
		this.manualStat = manualStat;
	}
}