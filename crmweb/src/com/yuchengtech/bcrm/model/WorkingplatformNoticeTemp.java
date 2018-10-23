package com.yuchengtech.bcrm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "OCRM_F_WP_NOTICE_TEMP")
public class WorkingplatformNoticeTemp implements Serializable {

	/**
	 * for warning!
	 */
	private static final long serialVersionUID = -4896335541713574523L;

	/** 公告id */
	@Id
	@Column(name = "NOTICE_ID")
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long noticeId;


	/** 公告标题 */
	@Column(name = "NOTICE_TITLE", length = 200)
	private String noticeTitle;

	/** 公告内容 */
	@Column(name = "NOTICE_CONTENT", length = 2000)
	private String noticeContent;

	/** 公告重要程度 */
	@Column(name = "NOTICE_LEVEL", length = 20)
	private String noticeLevel;

	/** 公告发布人id */
	@Column(name = "PUBLISHER", length = 30)
	private String publisher;

	/** 公告发布机构id */
	@Column(name = "PUBLISH_ORG", length = 32)
	private String publishOrg;

	/** 是否置顶 */
	@Column(name = "IS_TOP", length = 20)
	private String isTop;

	/** 公告发布时间 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PUBLISH_TIME")
	private Date publishTime;

	/** 功能类型 */
	@Column(name = "MOD_TYPE", length = 50)
	private String modType;

	/** 是否发布 */
	@Column(name = "PUBLISHED", length = 20)
	private String published;

	/** 新增修改标识位 */
	@Column(name = "FLAG")
	private String flag;
	
	/** 接收机构id */
	@Column(name = "RECEIVE_ORG")
	private String receiveOrg;
	
	@Column(name = "CREATOR", length = 20)
	private String creator;
	
	public String getPublishOrg() {
		return publishOrg;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setPublishOrg(String publishOrg) {
		this.publishOrg = publishOrg;
	}

	public String getModType() {
		return modType;
	}

	public void setModType(String modType) {
		this.modType = modType;
	}

	public String getReceiveOrg() {
		return receiveOrg;
	}

	public void setReceiveOrg(String receiveOrg) {
		this.receiveOrg = receiveOrg;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	/** 有效期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ACTIVE_DATE")
	private Date activeDate;

	public Date getPublishTime() {
		return publishTime;
	}

	/** 置顶有效期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "TOP_ACTIVE_DATE")
	private Date topActiveDate;

	/** 文档类型 */
	@Column(name = "NOTICE_TYPE", length = 20)
	private String noticeType;
	
	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeLevel() {
		return noticeLevel;
	}

	public void setNoticeLevel(String noticeLevel) {
		this.noticeLevel = noticeLevel;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public Date getTopActiveDate() {
		return topActiveDate;
	}

	public void setTopActiveDate(Date topActiveDate) {
		this.topActiveDate = topActiveDate;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getPublished() {
		return published;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public Long getNoticeId() {
		return noticeId;
	}

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

}
