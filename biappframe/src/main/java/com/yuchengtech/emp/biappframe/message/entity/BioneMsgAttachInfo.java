package com.yuchengtech.emp.biappframe.message.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <pre>
 * Title: 附件信息
 * Description: 附件信息实体类
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
@Table(name = "BIONE_MSG_ATTACH_INFO")
public class BioneMsgAttachInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3912357429527887816L;

	/** 消息ID */
	@Id
	@Column(name = "ATTACH_ID", unique = true, nullable = false, length = 32)
	private String attachId;

	/** 所属的消息ID */
	@Column(name = "MSG_ID", nullable = false, length = 32)
	private String msgId;

	@Column(name = "ATTACH_NAME", nullable = false, length = 200)
	private String attachName;

	/** 附件类型 */
	@Column(name = "ATTACH_TYPE_NO", nullable = false, length = 2000)
	private String attachTypeNo;

	/** 位置 */
	@Column(name = "ATTACH_SRC", nullable = false, length = 2000)
	private String attachSrc;
	
	/** 状态：/正常/已失效/已删除/ */
	@Column(name = "ATTACH_STS", nullable = false, precision = 1)
	private String attachSts;

	/** 大小 */
	@Column(name = "ATTACH_SIZE", length = 2000)
	private Long attachSize;

	/** 创建时间 */
	@Column(name = "CREATE_TIME")
	private java.util.Date createTime;

	/** 最后修改时间 */
	@Column(name = "LAST_UPDATE_TIME")
	private java.util.Date lastUpdateTime;

	/** 创建人 */
	@Column(name = "CREATE_USER", length = 32)
	private String createUser;

	/** 最后修改人 */
	@Column(name = "LAST_UPDATE_USER", length = 32)
	private String lastUpdateUser;

	/** 备注 */
	@Column(name = "REMARK", length = 500)
	private String remark;

	
	
	
	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getAttachTypeNo() {
		return attachTypeNo;
	}

	public void setAttachTypeNo(String attachTypeNo) {
		this.attachTypeNo = attachTypeNo;
	}

	public String getAttachSrc() {
		return attachSrc;
	}

	public void setAttachSrc(String attachSrc) {
		this.attachSrc = attachSrc;
	}

	public String getAttachSts() {
		return attachSts;
	}

	public void setAttachSts(String attachSts) {
		this.attachSts = attachSts;
	}

	public Long getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(Long attachSize) {
		this.attachSize = attachSize;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(java.util.Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

}
