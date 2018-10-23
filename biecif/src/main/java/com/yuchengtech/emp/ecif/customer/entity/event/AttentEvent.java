package com.yuchengtech.emp.ecif.customer.entity.event;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ATTENTEVENT")
public class AttentEvent implements Serializable {
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
