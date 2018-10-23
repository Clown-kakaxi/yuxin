package com.yuchengtech.emp.ecif.transaction.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the TX_MSG_NODE database table.
 * 
 */
@Entity
@Table(name="TX_MSG_NODE")
public class TxMsgNode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TX_MSG_NODE_NODEID_GENERATOR")
//	@GenericGenerator(name = "TX_MSG_NODE_NODEID_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_MSG_NODE") })
	@GenericGenerator(name = "TX_MSG_NODE_NODEID_GENERATOR", strategy = "com.yuchengtech.emp.ecif.base.util.IncrementGenerator")
	@Column(name="NODE_ID", unique=true)
	private Long nodeId;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER", length=20)
	private String createUser;

	@Column(name="MSG_ID", nullable=false)
	private Long msgId;

	@Column(name="NODE_CODE", nullable=false, length=20)
	private String nodeCode;

	@Column(name="NODE_DESC", length=255)
	private String nodeDesc;

	@Column(name="NODE_GROUP", length=1)
	private String nodeGroup;
	
	@Column(name="NODE_LABEL")
	private String nodeLabel;

	public String getNodeLabel() {
		return nodeLabel;
	}
	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}

	@Column(name="NODE_NAME", nullable=false, length=40)
	private String nodeName;

	@Column(name="NODE_SEQ", nullable=false)
	private Integer nodeSeq;

	@Column(name="NODE_TP", nullable=false, length=2)
	private String nodeTp;

	@Column(name="STATE", length=1)
	private String state;

	@Column(name="UP_NODE_ID", nullable=false)
	private Long upNodeId;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER", length=20)
	private String updateUser;
	
	@Column(name="NODE_DISPLAY", length=1)
	private String nodeDisplay;

    public String getNodeDisplay() {
		return nodeDisplay;
	}
	public void setNodeDisplay(String nodeDisplay) {
		this.nodeDisplay = nodeDisplay;
	}
	public TxMsgNode() {
    }
    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getNodeCode() {
		return this.nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getNodeDesc() {
		return this.nodeDesc;
	}

	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}

	public String getNodeGroup() {
		return this.nodeGroup;
	}

	public void setNodeGroup(String nodeGroup) {
		this.nodeGroup = nodeGroup;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Integer getNodeSeq() {
		return this.nodeSeq;
	}

	public void setNodeSeq(Integer nodeSeq) {
		this.nodeSeq = nodeSeq;
	}

	public String getNodeTp() {
		return this.nodeTp;
	}

	public void setNodeTp(String nodeTp) {
		this.nodeTp = nodeTp;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getUpNodeId() {
		return this.upNodeId;
	}

	public void setUpNodeId(Long upNodeId) {
		this.upNodeId = upNodeId;
	}

	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}