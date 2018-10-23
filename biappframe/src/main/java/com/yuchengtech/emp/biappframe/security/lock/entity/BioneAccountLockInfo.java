package com.yuchengtech.emp.biappframe.security.lock.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="BIONE_ACCOUNT_LOCK_INFO")
public class BioneAccountLockInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1313141557223946678L;
	
	
	@Id
	@Column(name="LOCK_ID", nullable=false, length=32, unique=true)
	private String lockId ;
	
	@Column(name="USER_NO", nullable=false, length=32, unique=true)
    private String userNo ;
	
	@Column(name="ERROR_TIMES", nullable=false, precision=12)
    private BigDecimal errorTimes ;
	
	@Column(name="USER_IP", length=15)
    private String userIp ;
	
	@Column(name="CREATE_TIME", nullable=false)
    private Timestamp createTime ;
	
	@Column(name="LAST_UPDATE_TIME", nullable=false)
	private Timestamp lastUpdateTime ;

	
	public String getLockId() {
		return lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public BigDecimal getErrorTimes() {
		return errorTimes;
	}

	public void setErrorTimes(BigDecimal errorTimes) {
		this.errorTimes = errorTimes;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
}
/*
CREATE TABLE BIONE_ACCOUNT_LOCK_INFO (
    LOCK_ID VARCHAR2(32) NOT NULL PRIMARY KEY,
    USER_NO VARCHAR2(32) NOT NULL UNIQUE,
    ERROR_TIMES NUMBER(12,0) NOT NULL,
    IP VARCHAR2(15),
    CREATE_TIME TIMESTAMP NOT NULL,
    LAST_UPDATE_TIME TIMESTAMP  NOT NULL
); 
 */