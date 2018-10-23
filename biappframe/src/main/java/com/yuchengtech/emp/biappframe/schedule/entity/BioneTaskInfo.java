package com.yuchengtech.emp.biappframe.schedule.entity;

import java.io.Serializable;
import javax.persistence.*;


import java.sql.Timestamp;

/**
 * The persistent class for the BIONE_TASK_INFO database table.
 * 
 */
@Entity
@Table(name = "BIONE_TASK_INFO")
public class BioneTaskInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TASK_ID", unique = true, nullable = false, length = 32)
	private String taskId;

	@Column(name = "BEAN_NAME", length = 100)
	private String beanName;

	@Column(name = "CREATE_TIME")
	private Timestamp createTime;
	
	@Column(name = "TASK_TYPE")
	private String taskType;
	
	@Column(name = "TASK_NAME")
	private String taskName;

	@Column(name = "LOGIC_SYS_NO", nullable = false, length = 32)
	private String logicSysNo;

	@Column(name = "TASK_STS", length = 1)
	private String taskSts;

	@Column(name = "TRIGGER_ID", length = 32)
	private String triggerId;

	public BioneTaskInfo() {
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getBeanName() {
		return this.beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getLogicSysNo() {
		return this.logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getTaskSts() {
		return this.taskSts;
	}

	public void setTaskSts(String taskSts) {
		this.taskSts = taskSts;
	}

	public String getTriggerId() {
		return this.triggerId;
	}

	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


}