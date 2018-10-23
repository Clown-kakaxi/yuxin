package com.yuchengtech.emp.biappframe.message.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.yuchengtech.emp.bione.message.entity.AbsBioneMessageEntity;

/**
 * <pre>
 * Title: 邮件实体类
 * Description: 邮件实体类
 * </pre>
 * 
 * @author liucheng2@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Entity
@Table(name = "BIONE_MSG_EMAIL_INFO")
public class BioneMsgEmailInfo extends AbsBioneMessageEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6545460182390390571L;

	/**
	 * 定义邮件的优先级.
	 */
	@Transient
	public transient static final int HIGHER_PRIORITY = 1;
	@Transient
	public transient static final int NORMAL_PRIORITY = 3;
	@Transient
	public transient static final int LOWER_PRIORITY = 5;

	@Transient
	public transient static final String MSMAIL_HIGHER_PRIORITY = "High";
	@Transient
	public transient static final String MSMAIL_NORMAL_PRIORITY = "Normal";
	@Transient
	public transient static final String MSMAIL_LOWER_PRIORITY = "Low";

	/**
	 * 定义邮件的发送格式.
	 */
	@Transient
	public transient static final String TEXT_MIME = "text/plain";
	@Transient
	public transient static final String HTML_MIME = "text/html";

	/**
	 * 定义邮件发送的协议.
	 */
	@Transient
	public transient static final String PROTOCOL_SMTP = "smtp";
	@Transient
	public transient static final String PROTOCOL_IMAP = "imap";
	@Transient
	public transient static final String PROTOCOL_POP3 = "pop3";

	/** 优先级 */
	@Column(name = "PRIORITY", length = 10)
	private int priority = NORMAL_PRIORITY;

	/** mime-type */
	@Column(name = "MIME_TYPE", length = 10)
	private String mimeType = TEXT_MIME;

	/** 标题 */
	@Column(name = "SUBJECT", length = 200)
	private String subject;

	/** 内容 */
	@Column(name = "MAIL_BODY", length = 4000)
	private String mailBody;

	/** 收件人 */
	@Column(name = "SEND_TO", length = 2000)
	private String sendTo;

	/** 抄送人 */
	@Column(name = "CC", length = 2000)
	private String cc;

	/** 密送人 */
	@Column(name = "BCC", length = 2000)
	private String bcc;

	/** 发件人 */
	@Column(name = "SEND_FROM", length = 100)
	private String sendFrom;

	/** 服务器地址 */
	@Column(name = "SERVER", length = 100)
	private String server;
	
	/** 创建时间 */
	@Column(name = "CREATE_TIME")
	private java.sql.Timestamp createTime ;
	
	/** 最后修改时间 */
	@Column(name = "LAST_UPDATE_TIME")
	private java.sql.Timestamp lastUpdateTime ;
	
	/** 发送时间 */
	@Column(name = "SEND_TIME")
	private java.sql.Timestamp sendTime ;
	
	/** 创建人 */
	@Column(name = "CREATE_USER", length = 32)
	private String createUser ;
	
	/** 最后修改人 */
	@Column(name = "LAST_UPDATE_USER", length = 32)
	private String lastUpdateUser ;
	
	/** 邮件状态:0-已删除；1-草稿；2-已发送 */
	@Column(name = "EMAIL_STS", length = 10)
	private String emailSts ;
	
	
	

	/** @Constructor */
	public BioneMsgEmailInfo() {
		this.setMsgTypeNo("email");
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getSendFrom() {
		return sendFrom;
	}

	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}

	public java.sql.Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(java.sql.Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public java.sql.Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(java.sql.Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getEmailSts() {
		return emailSts;
	}

	public void setEmailSts(String emailSts) {
		this.emailSts = emailSts;
	}

}
