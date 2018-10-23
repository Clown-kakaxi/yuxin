package com.yuchengtech.emp.biappframe.mtool.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_DS_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_DS_INFO")
public class BioneDsInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DS_ID", unique=true, nullable=false, length=32)
	private String dsId;

	@Column(name="CONN_PWD", nullable=false, length=100)
	private String connPwd;

	@Column(name="CONN_URL", length=500)
	private String connUrl;

	@Column(name="CONN_USER", length=100)
	private String connUser;

	@Column(name="DRIVER_ID", nullable=false, length=32)
	private String driverId;

	@Column(name="DS_NAME", length=100)
	private String dsName;

	@Column(name="LOGIC_SYS_NO", nullable=false, length=32)
	private String logicSysNo;

	@Column(length=500)
	private String remark;

    public BioneDsInfo() {
    }

	public String getDsId() {
		return this.dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getConnPwd() {
		return this.connPwd;
	}

	public void setConnPwd(String connPwd) {
		this.connPwd = connPwd;
	}

	public String getConnUrl() {
		return this.connUrl;
	}

	public void setConnUrl(String connUrl) {
		this.connUrl = connUrl;
	}

	public String getConnUser() {
		return this.connUser;
	}

	public void setConnUser(String connUser) {
		this.connUser = connUser;
	}

	public String getDriverId() {
		return this.driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getDsName() {
		return this.dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getLogicSysNo() {
		return this.logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}