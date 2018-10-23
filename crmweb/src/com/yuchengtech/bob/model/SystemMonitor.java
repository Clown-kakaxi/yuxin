package com.yuchengtech.bob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统监控管理
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "OCRM_F_SM_SYS_MONITOR")
public class SystemMonitor {

	//序列ID
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	//监控类型
	@Column(name = "SYSTEM_MONITORTYPE",length=100)
	private String monitorType;
	
	//监控模型
	@Column(name = "SYSTEM_MONITORMODEL",length=100)
	private String monitormodel;
	
	//监控动作
	@Column(name = "SYSTEM_MONITORACTION",length=100)
	private String monitoraction;

//	//监控动作
//	@Column(name = "SYSTEM_MONITORDATE",length=100)
//	private Date monitordate;

	//监控备注
	@Column(name = "SYSTEM_MONITORMEMO",length=100)	
	private String monitormemo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public String getMonitormodel() {
		return monitormodel;
	}

	public void setMonitormodel(String monitormodel) {
		this.monitormodel = monitormodel;
	}

	public String getMonitoraction() {
		return monitoraction;
	}

	public void setMonitoraction(String monitoraction) {
		this.monitoraction = monitoraction;
	}

//	public Date getMonitordate() {
//		return monitordate;
//	}
//
//	public void setMonitordate(Date monitordate) {
//		this.monitordate = monitordate;
//	}

	public String getMonitormemo() {
		return monitormemo;
	}

	public void setMonitormemo(String monitormemo) {
		this.monitormemo = monitormemo;
	}
	
	
}
