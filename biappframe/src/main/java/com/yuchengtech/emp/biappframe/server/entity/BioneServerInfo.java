package com.yuchengtech.emp.biappframe.server.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_SERVER_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_SERVER_INFO")
public class BioneServerInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SERVER_ID")
	private String serverId;

	private String remark;

	@Column(name="SERVER_IP")
	private String serverIp;
	
	@Column(name="SERVER_PORT")
	private String serverPort;
	
	@Column(name="SERVER_PATH")
	private String serverPath;
	
	@Column(name="SERVER_NO")
	private String serverNo;
	
	@Column(name="SERVER_NAME")
	private String serverName;

    public BioneServerInfo() {
    }

	public String getServerId() {
		return this.serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return 返回 serverNo。
	 */
	public String getServerNo() {
		return serverNo;
	}

	/**
	 * @param serverNo 设置 serverNo。
	 */
	public void setServerNo(String serverNo) {
		this.serverNo = serverNo;
	}

	/**
	 * @return 返回 serverName。
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName 设置 serverName。
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	
}