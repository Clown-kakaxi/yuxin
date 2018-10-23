package com.yuchengtech.emp.biappframe.mtool.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BIONE_DRIVER_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_DRIVER_INFO")
public class BioneDriverInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DRIVER_ID", unique=true, nullable=false, length=32)
	private String driverId;

	@Column(name="DRIVER_NAME", length=100)
	private String driverName;

	@Column(name="DRIVER_TYPE", length=100)
	private String driverType;

	@Column(name="CONN_URL", length=500)
	private String connUrl;
	
    public BioneDriverInfo() {
    }

	public String getDriverId() {
		return this.driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return this.driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverType() {
		return this.driverType;
	}

	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}
	
	public String getConnUrl() {
		return this.connUrl;
	}

	public void setConnUrl(String connUrl) {
		this.connUrl = connUrl;
	}

}