package com.yuchengtech.emp.biappframe.message.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjUserRelPK;

/**
 * <pre>
 * Title: 消息与权限的对照关系主键
 * Description: 消息与权限的对照关系主键
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
@Embeddable
public class BioneMsgAuthRelPK implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7981064616953145885L;

	/* 逻辑系统编号 */
	@Column(name = "LOGIC_SYS_NO", nullable = false, length = 32)
	private String logicSysNo;

	/* 消息ID */
	@Column(name = "MSG_ID", nullable = false, length = 32)
	private String msgId;

	/* 消息类型编号 */
	@Column(name = "MSG_TYPE_NO", nullable = false, length = 32)
	private String msgTypeNo;

	/* 授权对象ID */
	@Column(name = "OBJ_ID", nullable = false, length = 32)
	private String objId;

	/* 授权对象类型（机构/部门/角色/授权对象组/用户） */
	@Column(name = "OBJ_DEF_NO", nullable = false, length = 32)
	private String objDefNo;

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

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

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getObjDefNo() {
		return objDefNo;
	}

	public void setObjDefNo(String objDefNo) {
		this.objDefNo = objDefNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BioneAuthObjUserRelPK)) {
			return false;
		}
		BioneMsgAuthRelPK castOther = (BioneMsgAuthRelPK) other;
		return this.logicSysNo.equals(castOther.logicSysNo)
				&& this.objDefNo.equals(castOther.objDefNo)
				&& this.objId.equals(castOther.objId)
				&& this.msgId.equals(castOther.msgId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.logicSysNo.hashCode();
		hash = hash * prime + this.objDefNo.hashCode();
		hash = hash * prime + this.objId.hashCode();
		hash = hash * prime + this.msgId.hashCode();
		return hash;
	}
}
