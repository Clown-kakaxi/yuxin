package com.yuchengtech.emp.ecif.customer.entity.event;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT_INFO")
public class EventInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	EVENT_ID	客户标识	BIGINT
	CUST_ID	事件类型	VARCHAR
	EVENT_TYPE	事件子类型	VARCHAR
	SUB_EVENT_TYPE	事件描述	VARCHAR
	EVENT_DESC	事件发生日期	DATE
	EVENT_OCCUR_DATE	事件登记日期	DATE
	EVENT_REG_DATE	事件登记人员编号	VARCHAR
	LAST_UPDATE_SYS	最后更新系统	VARCHAR
	LAST_UPDATE_USER	最后更新人	VARCHAR
	LAST_UPDATE_TM	最后更新时间	TIMESTAMP
	TX_SEQ_NO	交易流水号	VARCHAR
	*/
	@Id
	@Column(name="EVENT_ID", unique=true, nullable=false)
	private Long eventId;
	@Column(name = "CUST_ID")
	private Long custId;
	@Column(name = "EVENT_TYPE")
	private String eventType;
	@Column(name = "SUB_EVENT_TYPE")
	private String subEventType;
	@Column(name = "EVENT_DESC")
	private String eventDesc;
	@Column(name = "EVENT_OCCUR_DATE")
	private String eventOccurDate;
	@Column(name = "EVENT_REG_DATE")
	private String eventRegDate;
	@Column(name = "EVENT_REG_TELLER_NO")
	private String eventRegTellerNo;
	@Column(name = "EVENT_REG_BRC")
	private String eventRegBrc;
	
//	@Column(name = "LAST_UPDATE_SYS")
//	private String lastUpdateSys;
//	@Column(name = "LAST_UPDATE_USER")
//	private String lastUpdateUser;
//	@Column(name = "LAST_UPDATE_TM")
//	private Timestamp lastUpdateTm;
//	@Column(name = "TX_SEQ_NO")
//	private String txSeqNo;
	
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public String getEventOccurDate() {
		return eventOccurDate;
	}
	public void setEventOccurDate(String eventOccurDate) {
		this.eventOccurDate = eventOccurDate;
	}
	public String getEventRegDate() {
		return eventRegDate;
	}
	public void setEventRegDate(String eventRegDate) {
		this.eventRegDate = eventRegDate;
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
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getSubEventType() {
		return subEventType;
	}
	public void setSubEventType(String subEventType) {
		this.subEventType = subEventType;
	}
	public String getEventRegTellerNo() {
		return eventRegTellerNo;
	}
	public void setEventRegTellerNo(String eventRegTellerNo) {
		this.eventRegTellerNo = eventRegTellerNo;
	}
	public String getEventRegBrc() {
		return eventRegBrc;
	}
	public void setEventRegBrc(String eventRegBrc) {
		this.eventRegBrc = eventRegBrc;
	}
}
