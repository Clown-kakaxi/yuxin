package com.yuchengtech.emp.ecif.customer.entity.event;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name = "CUSTEVENTREL")
public class CustEventRel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_EVENT_REL_ID", unique=true, nullable=false)
	private Long custEventRelId;
	@Column(name = "CUST_ID")
	private Long custId;
	@Column(name = "EVENT_ID")
	private Long eventId;
	@Column(name = "CUST_EVENT_REL_TYPE", length = 20)
	private String custEventRelType;
	@Column(name = "LAST_UPDATE_SYS", length = 20)
	private String lastUpdateSys;
	@Column(name = "LAST_UPDATE_USER", length = 20)
	private String lastUpdateUser;
	@Column(name = "LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;
	@Column(name = "TX_SEQ_NO", length = 32)
	private String txSeqNo;
	public Long getCustEventRelId() {
		return custEventRelId;
	}
	public void setCustEventRelId(Long custEventRelId) {
		this.custEventRelId = custEventRelId;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public String getCustEventRelType() {
		return custEventRelType;
	}
	public void setCustEventRelType(String custEventRelType) {
		this.custEventRelType = custEventRelType;
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
