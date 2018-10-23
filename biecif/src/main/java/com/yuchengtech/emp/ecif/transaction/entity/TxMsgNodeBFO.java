package com.yuchengtech.emp.ecif.transaction.entity;

public class TxMsgNodeBFO {
	private String nodeId; //
	private String nodeCode; // 
	private String attrId; // 
	private String attrCode; // 
	private String upNodeId; // 
	private String nodeName; // 
	private String attrName; // 
	private String nulls;     //
	private String dataType;  //
	private String dataLen;   //
	private String nodeGroup;
	private String checkRule;
	private String nodeLabel;

	public String getNodeLabel() {
		return nodeLabel;
	}

	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}

	private boolean flag = false;

	public String getCheckRule() {
		return checkRule;
	}

	public void setCheckRule(String checkRule) {
		this.checkRule = checkRule;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public String getUpNodeId() {
		return upNodeId;
	}

	public void setUpNodeId(String upNodeId) {
		this.upNodeId = upNodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getNodeGroup() {
		return nodeGroup;
	}

	public void setNodeGroup(String nodeGroup) {
		this.nodeGroup = nodeGroup;
	}

	public String getNulls() {
		return nulls;
	}

	public void setNulls(String nulls) {
		this.nulls = nulls;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataLen() {
		return dataLen;
	}

	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}
	
	
}
