package com.yuchengtech.bcrm.serviceManage.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 客户服务信息表
 * @author yuyz
 * @since 2012-12-06
 * 
 */
@Entity
@Table(name="OCRM_F_SE_CUST_SERVICE")
public class CustServiceInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ServiceSequnce", sequenceName = "SERVICESEQUNCE",allocationSize = 1)
	@GeneratedValue(generator = "ServiceSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "SERVICE_ID",nullable = false)
	private Long serviceId;//服务信息编号

	@Temporal(TemporalType.DATE)
	@Column(name="ACTUAL_DATE")
	private Date actualDate;

	@Column(name="AIM_PROD")
	private String aimProd;

	@Column(name="CANTACT_CHANNEL")
	private String cantactChannel;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_ORG")
	private String createOrg;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	private String executor;

	@Column(name="NEED_EVENT")
	private String needEvent;

	@Column(name="NEED_RESOURCE")
	private String needResource;

	@Column(name="P_OR_C")
	private String pOrC;

	@Temporal(TemporalType.DATE)
	@Column(name="PEND_DATE")
	private Date pendDate;

	@Column(name="PLAN_CHANNEL")
	private String planChannel;

//	@Column(name="PLAN_DESC")
//	private String planDesc;

	@Temporal(TemporalType.DATE)
	@Column(name="PSTART_DATE")
	private Date pstartDate;

	@Column(name="SERVICE_CONT")
	private String serviceCont;

	@Column(name="SERVICE_KIND")
	private String serviceKind;

	@Column(name="SERVICE_NODE")
	private String serviceNode;
//
//	@Column(name="SERVICE_PERD")
//	private String servicePerd;

	@Column(name="SERVICE_RESULT")
	private String serviceResult;

	@Column(name="SERVICE_STAT")
	private String serviceStat;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;

	public CustServiceInfo() {
	}

	public Long getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Date getActualDate() {
		return this.actualDate;
	}

	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}

	public String getAimProd() {
		return this.aimProd;
	}

	public void setAimProd(String aimProd) {
		this.aimProd = aimProd;
	}

	public String getCantactChannel() {
		return this.cantactChannel;
	}

	public void setCantactChannel(String cantactChannel) {
		this.cantactChannel = cantactChannel;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateOrg() {
		return this.createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getExecutor() {
		return this.executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getNeedEvent() {
		return this.needEvent;
	}

	public void setNeedEvent(String needEvent) {
		this.needEvent = needEvent;
	}

	public String getNeedResource() {
		return this.needResource;
	}

	public void setNeedResource(String needResource) {
		this.needResource = needResource;
	}

	public String getPOrC() {
		return this.pOrC;
	}

	public void setPOrC(String pOrC) {
		this.pOrC = pOrC;
	}

	public Date getPendDate() {
		return this.pendDate;
	}

	public void setPendDate(Date pendDate) {
		this.pendDate = pendDate;
	}

	public String getPlanChannel() {
		return this.planChannel;
	}

	public void setPlanChannel(String planChannel) {
		this.planChannel = planChannel;
	}

//	public String getPlanDesc() {
//		return this.planDesc;
//	}
//
//	public void setPlanDesc(String planDesc) {
//		this.planDesc = planDesc;
//	}

	public Date getPstartDate() {
		return this.pstartDate;
	}

	public void setPstartDate(Date pstartDate) {
		this.pstartDate = pstartDate;
	}

	public String getServiceCont() {
		return this.serviceCont;
	}

	public void setServiceCont(String serviceCont) {
		this.serviceCont = serviceCont;
	}

	public String getServiceKind() {
		return this.serviceKind;
	}

	public void setServiceKind(String serviceKind) {
		this.serviceKind = serviceKind;
	}

	public String getServiceNode() {
		return this.serviceNode;
	}

	public void setServiceNode(String serviceNode) {
		this.serviceNode = serviceNode;
	}

//	public String getServicePerd() {
//		return this.servicePerd;
//	}
//
//	public void setServicePerd(String servicePerd) {
//		this.servicePerd = servicePerd;
//	}

	public String getServiceResult() {
		return this.serviceResult;
	}

	public void setServiceResult(String serviceResult) {
		this.serviceResult = serviceResult;
	}

	public String getServiceStat() {
		return this.serviceStat;
	}

	public void setServiceStat(String serviceStat) {
		this.serviceStat = serviceStat;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}