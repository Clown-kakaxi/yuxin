package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxServiceStatus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_SERVICE_STATUS")
public class TxServiceStatus implements java.io.Serializable {

	// Fields

	private Long serviceId;
	private Long nodeId;
	private String hostName;
	private String ipAddr;
	private Long servicePort;
	private String serviceName;
	private String instName;
	private Long processId;
	private Long threadId;
	private String serviceStat;
	private String serviceVer;
	private String startMethod;
	private Timestamp startTime;
	private Timestamp stopTime;

	// Constructors

	/** default constructor */
	public TxServiceStatus() {
	}

	/** full constructor */
	public TxServiceStatus(Long nodeId, String hostName, String ipAddr,
			Long servicePort, String serviceName, String instName,
			Long processId, Long threadId, String serviceStat,
			String serviceVer, String startMethod, Timestamp startTime,
			Timestamp stopTime) {
		this.nodeId = nodeId;
		this.hostName = hostName;
		this.ipAddr = ipAddr;
		this.servicePort = servicePort;
		this.serviceName = serviceName;
		this.instName = instName;
		this.processId = processId;
		this.threadId = threadId;
		this.serviceStat = serviceStat;
		this.serviceVer = serviceVer;
		this.startMethod = startMethod;
		this.startTime = startTime;
		this.stopTime = stopTime;
	}

	// Property accessors
	@Id
		@Column(name = "SERVICE_ID", unique = true, nullable = false)
	public Long getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	@Column(name = "NODE_ID")
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "HOST_NAME", length = 128)
	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@Column(name = "IP_ADDR", length = 128)
	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	@Column(name = "SERVICE_PORT", precision = 22)
	public Long getServicePort() {
		return this.servicePort;
	}

	public void setServicePort(Long servicePort) {
		this.servicePort = servicePort;
	}

	@Column(name = "SERVICE_NAME", length = 128)
	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "INST_NAME", length = 128)
	public String getInstName() {
		return this.instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	@Column(name = "PROCESS_ID", precision = 22)
	public Long getProcessId() {
		return this.processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	@Column(name = "THREAD_ID", precision = 22)
	public Long getThreadId() {
		return this.threadId;
	}

	public void setThreadId(Long threadId) {
		this.threadId = threadId;
	}

	@Column(name = "SERVICE_STAT", length = 20)
	public String getServiceStat() {
		return this.serviceStat;
	}

	public void setServiceStat(String serviceStat) {
		this.serviceStat = serviceStat;
	}

	@Column(name = "SERVICE_VER", length = 20)
	public String getServiceVer() {
		return this.serviceVer;
	}

	public void setServiceVer(String serviceVer) {
		this.serviceVer = serviceVer;
	}

	@Column(name = "START_METHOD", length = 20)
	public String getStartMethod() {
		return this.startMethod;
	}

	public void setStartMethod(String startMethod) {
		this.startMethod = startMethod;
	}

	@Column(name = "START_TIME", length = 11)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "STOP_TIME", length = 11)
	public Timestamp getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(Timestamp stopTime) {
		this.stopTime = stopTime;
	}

}