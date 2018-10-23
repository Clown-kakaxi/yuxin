package com.yuchengtech.emp.ecif.customer.entity.event;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TXEVENT")
public class TxEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EVENT_ID", unique=true, nullable=false)
	private Long eventId;
	@Column(name = "EVENT_NO", length = 20)
	private String eventNo;
	@Column(name = "EVENT_OCCUR_DATE")
	private Date eventOccurDate;
	@Column(name = "EVENT_REG_DATE")
	private Date eventRegDate;
	@Column(name = "EVENT_REG_TELLER_NO", length = 20)
	private String eventRegTellerNo;
	@Column(name = "EVENT_NAME", length = 40)
	private String eventName;
	@Column(name = "EVENT_TYPE", length = 20)
	private String eventType;
	@Column(name = "EVENT_DESC", length = 80)
	private String eventDesc;
	@Column(name = "EVENT_AMT")
	private Double eventAmt;
	@Column(name = "EVENT_END_TIME")
	private String eventEndTime;
	@Column(name = "EVENT_TYPE_RURAL", length = 20)
	private String eventTypeRural;
	@Column(name = "STATE_BEFORE_CHANGE", length = 20)
	private String stateBeforeChange;
	@Column(name = "STATE_AFTER_CHANGE", length = 20)
	private String stateAfterChange;
	@Column(name = "EVENT_AMT_SUM")
	private Double eventAmtSum;
	@Column(name = "EVENT_CURRENCY", length = 20)
	private String eventCurrency;
	@Column(name = "LAST_UPDATE_SYS", length = 20)
	private String lastUpdateSys;
	@Column(name = "LAST_UPDATE_USER", length = 20)
	private String lastUpdateUser;
	@Column(name = "LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;
	@Column(name = "TX_SEQ_NO", length = 32)
	private String txSeqNo;
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public String getEventNo() {
		return eventNo;
	}
	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}
	public Date getEventOccurDate() {
		return eventOccurDate;
	}
	public void setEventOccurDate(Date eventOccurDate) {
		this.eventOccurDate = eventOccurDate;
	}
	public Date getEventRegDate() {
		return eventRegDate;
	}
	public void setEventRegDate(Date eventRegDate) {
		this.eventRegDate = eventRegDate;
	}
	public String getEventRegTellerNo() {
		return eventRegTellerNo;
	}
	public void setEventRegTellerNo(String eventRegTellerNo) {
		this.eventRegTellerNo = eventRegTellerNo;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public Double getEventAmt() {
		return eventAmt;
	}
	public void setEventAmt(Double eventAmt) {
		this.eventAmt = eventAmt;
	}
	public String getEventEndTime() {
		return eventEndTime;
	}
	public void setEventEndTime(String eventEndTime) {
		this.eventEndTime = eventEndTime;
	}
	public String getEventTypeRural() {
		return eventTypeRural;
	}
	public void setEventTypeRural(String eventTypeRural) {
		this.eventTypeRural = eventTypeRural;
	}
	public String getStateBeforeChange() {
		return stateBeforeChange;
	}
	public void setStateBeforeChange(String stateBeforeChange) {
		this.stateBeforeChange = stateBeforeChange;
	}
	public String getStateAfterChange() {
		return stateAfterChange;
	}
	public void setStateAfterChange(String stateAfterChange) {
		this.stateAfterChange = stateAfterChange;
	}
	public Double getEventAmtSum() {
		return eventAmtSum;
	}
	public void setEventAmtSum(Double eventAmtSum) {
		this.eventAmtSum = eventAmtSum;
	}
	public String getEventCurrency() {
		return eventCurrency;
	}
	public void setEventCurrency(String eventCurrency) {
		this.eventCurrency = eventCurrency;
	}
	public String getLastUpdateSys() {
		return lastUpdateSys;
	}
	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public Timestamp getLastUpdateTm() {
		return lastUpdateTm;
	}
	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}
	public String getTxSeqNo() {
		return txSeqNo;
	}
	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}
	
	
}
