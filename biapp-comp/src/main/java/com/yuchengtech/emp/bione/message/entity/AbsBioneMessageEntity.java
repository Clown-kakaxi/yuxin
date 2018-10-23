package com.yuchengtech.emp.bione.message.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * <pre>
 * Title: 通用的消息实体超类
 * Description: 通用的消息实体超类
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
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbsBioneMessageEntity implements java.io.Serializable {

	
	@Id
	@Column(name = "MSG_ID", unique = true, nullable = false, length = 32)
	private String msgId;

	@Column(name = "LOGIC_SYS_NO", nullable = false, length = 32)
	private String logicSysNo;

	@Column(name = "MSG_TYPE_NO", precision = 12)
	private String msgTypeNo;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgTypeNo() {
		return msgTypeNo;
	}

	public void setMsgTypeNo(String msgTypeNo) {
		this.msgTypeNo = msgTypeNo;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}
}
