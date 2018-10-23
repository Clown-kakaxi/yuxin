package com.yuchengtech.emp.biappframe.message.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <pre>
 * Title: 消息与权限的对照关系
 * Description: 消息与权限的对照关系
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
@Table(name = "BIONE_MSG_AUTH_REL")
public class BioneMsgAuthRel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8880844659855362409L;

	@EmbeddedId
	private BioneMsgAuthRelPK id;

	public BioneMsgAuthRel() {

	}

	public BioneMsgAuthRelPK getId() {
		return this.id;
	}

	public void setId(BioneMsgAuthRelPK id) {
		this.id = id;
	}

}
