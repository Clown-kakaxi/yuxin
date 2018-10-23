package com.yuchengtech.emp.ecif.sysmonitor.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class ServiceInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7365158147062475102L;

	
	private String ip;
	

	private String userName;
	

	private String passWord;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	
}
