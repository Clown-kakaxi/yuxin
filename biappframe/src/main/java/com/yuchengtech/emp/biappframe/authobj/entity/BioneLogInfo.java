package com.yuchengtech.emp.biappframe.authobj.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the BIONE_LOG_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_LOG_INFO")
public class BioneLogInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LOG_NO", unique=true, nullable=false, length=32)
	private String logNo;

	@Column(name="LOG_EVENT", length=500)
	private String logEvent;

	@Column(name="LOGIC_SYS_NO", nullable=false, length=32)
	private String logicSysNo;

	@Column(name="OCCUR_TIME")
	private Timestamp occurTime;

	@Column(name="OPER_USER", nullable=false, length=32)
	private String operUser;

	@Column(name="LOGIN_IP",nullable=true,length=50)
	private String loginIP;
	
    public BioneLogInfo() {
    }

	public String getLogNo() {
		return this.logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	public String getLogEvent() {
		return this.logEvent;
	}

	public void setLogEvent(String logEvent) {
		this.logEvent = logEvent;
	}

	public String getLogicSysNo() {
		return this.logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public Timestamp getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(Timestamp occurTime) {
		this.occurTime = occurTime;
	}

	public String getOperUser() {
		return this.operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

}