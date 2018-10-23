package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxAlarmEmail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_ALARM_EMAIL")
public class TxAlarmEmail implements java.io.Serializable {

	// Fields

	private Long alarmId;
	private Long userId;
	private String userName;
	private String userTitle;
	private String emailAddr;
	private String alarmSys;
	private String alarmModule;
	private String errorCode;
	private String alarmLevel;
	private String alarmInfo;
	private Date occurDate;
	private Timestamp occurTime;
	private String srcSysCd;
	private String alarmStat;
	private Date sendDate;
	private Timestamp sendTime;
	private Long sendNum;

	// Constructors

	/** default constructor */
	public TxAlarmEmail() {
	}

	/** minimal constructor */
	public TxAlarmEmail(Long alarmId) {
		this.alarmId = alarmId;
	}

	/** full constructor */
	public TxAlarmEmail(Long alarmId, Long userId, String userName,
			String userTitle, String emailAddr, String alarmSys,
			String alarmModule, String errorCode, String alarmLevel,
			String alarmInfo, Date occurDate, Timestamp occurTime,
			String srcSysCd, String alarmStat, Date sendDate,
			Timestamp sendTime, Long sendNum) {
		this.alarmId = alarmId;
		this.userId = userId;
		this.userName = userName;
		this.userTitle = userTitle;
		this.emailAddr = emailAddr;
		this.alarmSys = alarmSys;
		this.alarmModule = alarmModule;
		this.errorCode = errorCode;
		this.alarmLevel = alarmLevel;
		this.alarmInfo = alarmInfo;
		this.occurDate = occurDate;
		this.occurTime = occurTime;
		this.srcSysCd = srcSysCd;
		this.alarmStat = alarmStat;
		this.sendDate = sendDate;
		this.sendTime = sendTime;
		this.sendNum = sendNum;
	}

	// Property accessors
	@Id
	@Column(name = "ALARM_ID", unique = true, nullable = false)
	public Long getAlarmId() {
		return this.alarmId;
	}

	public void setAlarmId(Long alarmId) {
		this.alarmId = alarmId;
	}

	@Column(name = "USER_ID")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "USER_TITLE", length = 20)
	public String getUserTitle() {
		return this.userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

	@Column(name = "EMAIL_ADDR")
	public String getEmailAddr() {
		return this.emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	@Column(name = "ALARM_SYS", length = 20)
	public String getAlarmSys() {
		return this.alarmSys;
	}

	public void setAlarmSys(String alarmSys) {
		this.alarmSys = alarmSys;
	}

	@Column(name = "ALARM_MODULE", length = 20)
	public String getAlarmModule() {
		return this.alarmModule;
	}

	public void setAlarmModule(String alarmModule) {
		this.alarmModule = alarmModule;
	}

	@Column(name = "ERROR_CODE", length = 20)
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "ALARM_LEVEL", length = 20)
	public String getAlarmLevel() {
		return this.alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	@Column(name = "ALARM_INFO")
	public String getAlarmInfo() {
		return this.alarmInfo;
	}

	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "OCCUR_DATE", length = 7)
	public Date getOccurDate() {
		return this.occurDate;
	}

	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}

	@Column(name = "OCCUR_TIME", length = 11)
	public Timestamp getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Timestamp occurTime) {
		this.occurTime = occurTime;
	}

	@Column(name = "SRC_SYS_CD", length = 20)
	public String getSrcSysCd() {
		return this.srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	@Column(name = "ALARM_STAT", length = 20)
	public String getAlarmStat() {
		return this.alarmStat;
	}

	public void setAlarmStat(String alarmStat) {
		this.alarmStat = alarmStat;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SEND_DATE", length = 7)
	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Column(name = "SEND_TIME", length = 11)
	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	@Column(name = "SEND_NUM", precision = 22)
	public Long getSendNum() {
		return this.sendNum;
	}

	public void setSendNum(Long sendNum) {
		this.sendNum = sendNum;
	}

}