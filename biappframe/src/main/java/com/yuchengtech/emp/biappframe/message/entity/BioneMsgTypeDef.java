package com.yuchengtech.emp.biappframe.message.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <pre>
 * Title: 消息类型定义实体
 * Description: 消息类型定义实体
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
@Table(name = "BIONE_MSG_TYPE_DEF")
public class BioneMsgTypeDef implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4444018225875352950L;

	/** 类型ID */
	@Id
	@Column(name = "MSG_TYPE_ID", length = 32, unique = true, nullable = false)
	private String msgTypeId;

	/** 类型编号 */
	@Column(name = "MSG_TYPE_NO", length = 32, unique = true, nullable = false)
	private String msgTypeNo;

	/** 类型名称 */
	@Column(name = "MSG_TYPE_NAME", length = 100, nullable = false)
	private String msgTypeName;

	/** 类型描述 */
	@Column(name = "MSG_TYPE_DESC", length = 200)
	private String msgTypeDesc;

	public String getMsgTypeId() {
		return msgTypeId;
	}

	/** @TODO getters && setters */

	public void setMsgTypeId(String msgTypeId) {
		this.msgTypeId = msgTypeId;
	}

	public String getMsgTypeNo() {
		return msgTypeNo;
	}

	public void setMsgTypeNo(String msgTypeNo) {
		this.msgTypeNo = msgTypeNo;
	}

	public String getMsgTypeName() {
		return msgTypeName;
	}

	public void setMsgTypeName(String msgTypeName) {
		this.msgTypeName = msgTypeName;
	}

	public String getMsgTypeDesc() {
		return msgTypeDesc;
	}

	public void setMsgTypeDesc(String msgTypeDesc) {
		this.msgTypeDesc = msgTypeDesc;
	}

}
