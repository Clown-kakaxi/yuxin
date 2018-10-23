package com.yuchengtech.emp.ecif.transaction.entity;

public class TxMsgSimulate {
	private String serverType;
	private String reqMsg;
	private String resMsg;
	private String ip;
	private String port;
	private String params;

	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getServerType() {
		return serverType;
	}
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	public String getReqMsg() {
		return reqMsg;
	}
	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	
}
