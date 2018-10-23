package com.yuchengtech.emp.ecif.customer.simplegroup.web.vo;

import java.util.Date;


public class PerFocusVO{
	private String custNo;
	
	private String name;
	
//	private String eventOccurDate;
//	
//	private String eventRegDate;
//	
//	private String eventRegTellerNo;
//	
//	private String eventName;
//	
//	private String eventType;
//	
//	private String eventDesc;
	
	private String eventType;
	private String subEventType;
	private String eventDesc;
	private String eventOccurDate;
	private String eventRegDate;
	private String eventRegTellerNo;
	private String eventRegBrc;

	private String custId;

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getSubEventType() {
		return subEventType;
	}

	public void setSubEventType(String subEventType) {
		this.subEventType = subEventType;
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