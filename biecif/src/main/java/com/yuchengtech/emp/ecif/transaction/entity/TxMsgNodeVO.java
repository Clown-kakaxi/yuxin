package com.yuchengtech.emp.ecif.transaction.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.sql.Timestamp;


/**
 * The persistent class for the TX_MSG_NODE database table.
 * 
 */

public class TxMsgNodeVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long nodeId;
	private Timestamp createTm;
	private String createUser;
	private Long msgId;
	private String nodeCode;
	private String nodeDesc;
	private String nodeGroup;
	private String nodeLabel;
	private String nodeDisplay;
	
	public String getNodeDisplay() {
		return nodeDisplay;
	}

	public void setNodeDisplay(String nodeDisplay) {
		this.nodeDisplay = nodeDisplay;
	}

	public String getNodeLabel() {
		return nodeLabel;
	}

	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}

	private String nodeName;
	private int nodeSeq;
	private String nodeTp;
	private String state;
	private Long upNodeId;
	private Timestamp updateTm;
	private String updateUser;
    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Timestamp getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getNodeDesc() {
		return nodeDesc;
	}

	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}

	public String getNodeGroup() {
		return nodeGroup;
	}

	public void setNodeGroup(String nodeGroup) {
		this.nodeGroup = nodeGroup;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getNodeSeq() {
		return nodeSeq;
	}

	public void setNodeSeq(int nodeSeq) {
		this.nodeSeq = nodeSeq;
	}

	public String getNodeTp() {
		return nodeTp;
	}

	public void setNodeTp(String nodeTp) {
		this.nodeTp = nodeTp;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JsonSerialize(using=BioneLongSerializer.class)
	public Long getUpNodeId() {
		return upNodeId;
	}

	public void setUpNodeId(Long upNodeId) {
		this.upNodeId = upNodeId;
	}

	public Timestamp getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getNodeNamexx() {
		return nodeNamexx;
	}

	public void setNodeNamexx(String nodeNamexx) {
		this.nodeNamexx = nodeNamexx;
	}

	private String nodeNamexx;

    public TxMsgNodeVO() {
    }


}