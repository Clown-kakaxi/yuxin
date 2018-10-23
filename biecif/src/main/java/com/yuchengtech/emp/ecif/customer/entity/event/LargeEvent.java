package com.yuchengtech.emp.ecif.customer.entity.event;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "M_EV_LARGE_EVENT")
public class LargeEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EVENT_ID", unique=true, nullable=false)
	private String eventId;
	@Column(name = "EVENT_TYPE", length = 20)
	private String eventType;
	@Column(name = "STATE_BEFORE_CHANGE", length = 20)
	private String stateBeforeChange;
	@Column(name = "STATE_AFTER_CHANGE", length = 20)
	private String stateAfterChange;
	@Column(name = "EVENT_NAME", length = 40)
	private String eventName;
	@Column(name = "EVENT_AMT_SUM")
	private Long eventAmtSum;
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
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
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
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Long getEventAmtSum() {
		return eventAmtSum;
	}
	public void setEventAmtSum(Long eventAmtSum) {
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
