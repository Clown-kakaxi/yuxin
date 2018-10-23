package com.yuchengtech.emp.biappframe.message;

import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;

/**
 * 消息模块的静态变量
 * 
 * @author charles
 * 
 */
public class MessageConstants extends GlobalConstants {

	/** 消息状态 - 草稿 */
	public static final String MESSAGE_ANNOUNCEMENT_STATUS_DRAFT = "1";

	/** 消息状态 - 已发布 */
	public static final String MESSAGE_ANNOUNCEMENT_STATUS_PUBLISHED = "2";

	/** 消息状态 - 已过期 */
	public static final String MESSAGE_ANNOUNCEMENT_STATUS_EXPIRED = "3";

	/** 消息状态 - 已删除 */
	public static final String MESSAGE_ANNOUNCEMENT_STATUS_DELETED = "0";

	
	/** 消息状态 - 已读 */
	public static final String MESSAGE_COMMON_STATUS_READ = "2";

	/** 消息状态 - 未读 */
	public static final String MESSAGE_COMMON_STATUS_UNREAD = "3";
	
	/**  */
	public static final String PUSH_JOIN_LISTEN_ID = "BIONE_NOTICE";
	
	/**  */
	public static final String PUSH_MESSAGE_KEY = "BIONE_MSG_KEY";
	
}
