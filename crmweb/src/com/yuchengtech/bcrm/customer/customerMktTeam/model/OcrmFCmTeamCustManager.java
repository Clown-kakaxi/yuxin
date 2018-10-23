package com.yuchengtech.bcrm.customer.customerMktTeam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_CM_TEAM_CUST_MANAGER database table.
 * 客户经理营销团队成员表
 */
@Entity
@Table(name="OCRM_F_CM_TEAM_CUST_MANAGER")
public class OcrmFCmTeamCustManager implements Serializable {
	private static final long serialVersionUID = 5112428719618384947L;

	/**主键*/
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(nullable=false)
	private Long id;

	/**加入时间*/
    @Temporal( TemporalType.DATE)
	@Column(name="JOIN_DATE")
	private Date joinDate;

	/**营销团队ID*/
    @Column(name="MKT_TEAM_ID",nullable=false,length=100)
	private String mktTeamId;

	/**用户ID*/
	@Column(name="USER_ID", nullable=false, length=100)
	private String userId;
	
	/**团队名称*/
	@Column(name="TEAM_NAME")
	private String teamName;
	
	/**归属机构*/
	@Column(name="BELONG_ORG")
	private String belongOrg;
	
	/**团队类型*/
	@Column(name="TEAM_TYPE")
	private String teamType;
	
	/**客户经理名称*/
	@Column(name="CUST_MANAGER_NAME")
	private String custManagerName;
	
	/**客户经理归属机构*/
	@Column(name="CUST_MANAGER_ORG")
	private String custManagerOrg;
	
	/**客户经理工号*/
	@Column(name="CUST_MANAGER_ID")
	private String custManagerId;
	
	/**客户经理状态*/
	@Column(name="CUST_MANAGER_STATE")
	private String custManagerState;
	
	/**批准人*/
	@Column(name="APPROVER")
	private String approver;

	public String getMktTeamId() {
		return mktTeamId;
	}

	public void setMktTeamId(String mktTeamId) {
		this.mktTeamId = mktTeamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getBelongOrg() {
		return belongOrg;
	}

	public void setBelongOrg(String belongOrg) {
		this.belongOrg = belongOrg;
	}

	public String getTeamType() {
		return teamType;
	}

	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}

	public String getCustManagerName() {
		return custManagerName;
	}

	public void setCustManagerName(String custManagerName) {
		this.custManagerName = custManagerName;
	}

	public String getCustManagerOrg() {
		return custManagerOrg;
	}

	public void setCustManagerOrg(String custManagerOrg) {
		this.custManagerOrg = custManagerOrg;
	}

	public String getCustManagerId() {
		return custManagerId;
	}

	public void setCustManagerId(String custManagerId) {
		this.custManagerId = custManagerId;
	}

	public String getCustManagerState() {
		return custManagerState;
	}

	public void setCustManagerState(String custManagerState) {
		this.custManagerState = custManagerState;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}