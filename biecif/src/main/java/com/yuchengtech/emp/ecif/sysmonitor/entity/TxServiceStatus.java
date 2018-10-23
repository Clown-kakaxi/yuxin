package com.yuchengtech.emp.ecif.sysmonitor.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * The persistent class for the SERVICE_INFO database table.
 * 
 */
@Entity
@Table(name="TX_SERVICE_STATUS")
public class TxServiceStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6778461214258614295L;
	/**
	 * 服务标识
	 */
	@Id
	@GeneratedValue(generator = "TX_SERVICE_STATUS_GENERATOR")
	@GenericGenerator(name = "TX_SERVICE_STATUS_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_TX_SERVICE_STATUS") })
	@Column(name="SERVICE_ID")
	private Long serviceID;
	/**
	 * 节点名
	 */
	@Column(name="NODE_ID")
	private Long nodeID ;
	/**
	 * 主机名
	 */
	@Column(name="HOST_NAME")
	private String hostName ;
	
	/**
	 * IP地址
	 */
	@Column(name="IP_ADDR")
	private String ipAddr ;
	/**
	 * 端口号
	 */
	@Column(name="SERVICE_PORT")
	private Integer servicePort ;
	/**
	 * 服务名
	 */
	@Column(name="SERVICE_NAME")
	private String serviceName ;
	/**
	 * 实例名
	 */
	@Column(name="INST_NAME")
	private String instName ;
	/**
	 * 进程号
	 */
	@Column(name="PROCESS_ID")
	private Integer processID ;
	/**
	 * 线程号
	 */
	@Column(name="THREAD_ID")
	private Integer threadID;
	/**
	 * 服务状态
	 */
	@Column(name="SERVICE_STAT")
	private String serviceStart;
	/**
	 * 服务版本
	 */
	@Column(name="SERVICE_VER")
	private String servicever;
	/**
	 * 启动方式
	 */
	@Column(name="START_METHOD")
	private String startMethod;
	/**
	 * 启动时间
	 */
	@Column(name="START_TIME")
	private Timestamp startTime;
	/**
	 * 停止时间
	 */
	@Column(name="STOP_TIME")
	private Timestamp stopTime;
	
	public  TxServiceStatus(){
		
	}
	public Long getServiceID() {
		return serviceID;
	}
	public void setServiceID(Long serviceID) {
		this.serviceID = serviceID;
	}
	public Long getNodeID() {
		return nodeID;
	}
	public void setNodeID(Long nodeID) {
		this.nodeID = nodeID;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public Integer getServicePort() {
		return servicePort;
	}
	public void setServicePort(Integer servicePort) {
		this.servicePort = servicePort;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getInstName() {
		return instName;
	}
	public void setInstName(String instName) {
		this.instName = instName;
	}
	public Integer getProcessID() {
		return processID;
	}
	public void setProcessID(Integer processID) {
		this.processID = processID;
	}
	public Integer getThreadID() {
		return threadID;
	}
	public void setThreadID(Integer threadID) {
		this.threadID = threadID;
	}
	public String getServiceStart() {
		return serviceStart;
	}
	public void setServiceStart(String serviceStart) {
		this.serviceStart = serviceStart;
	}
	public String getServicever() {
		return servicever;
	}
	public void setServicever(String servicever) {
		this.servicever = servicever;
	}
	public String getStartMethod() {
		return startMethod;
	}
	public void setStartMethod(String startMethod) {
		this.startMethod = startMethod;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getStopTime() {
		return stopTime;
	}
	public void setStopTime(Timestamp stopTime) {
		this.stopTime = stopTime;
	}
}
