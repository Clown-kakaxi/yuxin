package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxMsgNode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_MSG_NODE")
public class TxMsgNode implements java.io.Serializable {

	// Fields

	private Long nodeId;
	private Long msgId;
	private Long upNodeId;
	private String nodeCode;
	private String nodeName;
	private String nodeDesc;
	private String nodeTp;
	private Long nodeSeq;
	private String nodeGroup;
	private String nodeLabel;
	private String nodeDisplay;
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxMsgNode() {
	}

	/** full constructor */
	public TxMsgNode(Long msgId, Long upNodeId, String nodeCode,
			String nodeName, String nodeDesc, String nodeTp,
			Long nodeSeq, String nodeGroup, String nodeLabel, String nodeDisplay,String state,
			Timestamp createTm, String createUser, Timestamp updateTm,
			String updateUser) {
		this.msgId = msgId;
		this.upNodeId = upNodeId;
		this.nodeCode = nodeCode;
		this.nodeName = nodeName;
		this.nodeDesc = nodeDesc;
		this.nodeTp = nodeTp;
		this.nodeSeq = nodeSeq;
		this.nodeGroup = nodeGroup;
		this.nodeLabel = nodeLabel;
		this.nodeDisplay = nodeDisplay;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@Id
	@Column(name = "NODE_ID", unique = true, nullable = false)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "MSG_ID")
	public Long getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	@Column(name = "UP_NODE_ID")
	public Long getUpNodeId() {
		return this.upNodeId;
	}

	public void setUpNodeId(Long upNodeId) {
		this.upNodeId = upNodeId;
	}

	@Column(name = "NODE_CODE", length = 20)
	public String getNodeCode() {
		return this.nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	@Column(name = "NODE_NAME", length = 40)
	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Column(name = "NODE_DESC")
	public String getNodeDesc() {
		return this.nodeDesc;
	}

	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}

	@Column(name = "NODE_TP", length = 2)
	public String getNodeTp() {
		return this.nodeTp;
	}

	public void setNodeTp(String nodeTp) {
		this.nodeTp = nodeTp;
	}

	@Column(name = "NODE_SEQ", precision = 22)
	public Long getNodeSeq() {
		return this.nodeSeq;
	}

	public void setNodeSeq(Long nodeSeq) {
		this.nodeSeq = nodeSeq;
	}

	@Column(name = "NODE_GROUP", length = 1)
	public String getNodeGroup() {
		return this.nodeGroup;
	}

	public void setNodeGroup(String nodeGroup) {
		this.nodeGroup = nodeGroup;
	}
	
	@Column(name = "NODE_LABEL", length = 1)
	public String getNodeLabel() {
		return nodeLabel;
	}

	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}
	
	@Column(name = "NODE_DISPLAY", length = 1)
	public String getNodeDisplay() {
		return nodeDisplay;
	}

	public void setNodeDisplay(String nodeDisplay) {
		this.nodeDisplay = nodeDisplay;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "CREATE_TM", length = 11)
	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	@Column(name = "CREATE_USER", length = 20)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "UPDATE_TM", length = 11)
	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	@Column(name = "UPDATE_USER", length = 20)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}