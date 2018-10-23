package com.yuchengtech.emp.biappframe.message.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yuchengtech.emp.bione.message.entity.AbsBioneMessageEntity;

/**
 * <pre>
 * Title: 公告信息
 * Description: 公告信息实体类
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
@Table(name = "BIONE_MSG_ANNOUNCEMENT_INFO")
public class BioneMsgAnnouncementInfo extends AbsBioneMessageEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3912357429527887816L;

	/** 消息内容 */
	@Column(name = "MSG_TITLE", nullable = false, length = 2000)
	private String msgTitle;

	/** 消息内容 */
	@Column(name = "MSG_DETAIL", nullable = false, length = 2000)
	private String msgDetail;

	/** 消息来源(可能是人，也可能是部门、机构) */
	@Column(name = "MSG_SRC", nullable = false, length = 2000)
	private String msgSrc;

	/** 状态：/草稿/已发布/已失效/已删除/ */
	@Column(name = "MSG_STS", nullable = false, precision = 1)
	private String msgSts;

	/** 创建时间 */
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;

	/** 最后修改时间 */
	@Column(name = "LAST_UPDATE_TIME")
	private Timestamp lastUpdateTime;

	/** 创建人 */
	@Column(name = "CREATE_USER")
	private String createUser;

	/** 最后修改人 */
	@Column(name = "LAST_UPDATE_USER")
	private String lastUpdateUser;

	/** 过期时间 */
	@Column(name = "EXPIRATION_TIME", nullable = false)
	private String expirationTime;

	/** @TODO getters && setters */

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgDetail() {
		return msgDetail;
	}

	public void setMsgDetail(String msgDetail) {
		this.msgDetail = msgDetail;
	}

	public String getMsgSts() {
		return msgSts;
	}

	public void setMsgSts(String msgSts) {
		this.msgSts = msgSts;
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

	public String getMsgSrc() {
		return msgSrc;
	}

	public void setMsgSrc(String msgSrc) {
		this.msgSrc = msgSrc;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

}
