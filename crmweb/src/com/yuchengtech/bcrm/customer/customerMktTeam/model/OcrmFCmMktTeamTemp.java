package com.yuchengtech.bcrm.customer.customerMktTeam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the OCRM_F_CM_MKT_TEAM_TEMP database table. 
 * 营销团队表_临时表
 */
@Entity
@Table(name = "OCRM_F_CM_MKT_TEAM_TEMP")
public class OcrmFCmMktTeamTemp implements Serializable {

	private static final long serialVersionUID = -6733928454609401975L;
    @Id
	@Column(name = "MKT_TEAM_ID", nullable = false)
	private Long mktTeamId;

	/** 创建时间 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE")
	private Date createDate;

	/** 创建人 */
	@Column(name = "CREATE_USER", length = 100)
	private String createUser;
	
	/** 创建人姓名 */
	@Column(name = "CREATE_USER_NAME", length = 100)
	private String createUserName;
	
	/** 创建人ID */
	@Column(name = "CREATE_USER_ID", length = 100)
	private String createUserId;
	
	/** 创建人所属机构ID */
	@Column(name = "CREATE_USER_ORG_ID", length = 100)
	private String createUserOrgId;
	
	/**团队成员数*/
	@Column(name = "TEAM_NO")
	private Integer teamNo;
	
	/**团队客户数*/
	@Column(name = "TEAM_CUS_NO")
	private Integer teamCusNo;

//	/** 是否中小企业营销团队 */
//	@Column(name = "IS_SMALL", length = 20)
//	private String isSmall;

	/** 营销团队名称 */
	@Column(name = "MKT_TEAM_NAME", length = 200)
	private String mktTeamName;

	/** 所属机构 */
	@Column(name = "ORG_ID", length = 100)
	private String orgId;

	/** 团队负责人 */
	@Column(name = "TEAM_LEADER", length = 100)
	private String teamLeader;
	

	/** 团队负责人 */
	@Column(name = "TEAM_LEADER_ID", length = 100)
	private String teamLeaderId;
	
	/** 团队状态 */
	@Column(name = "TEAM_STATUS", length = 100)
	private String teamStatus;
	

	/** 团队负责人联系电话 */
	@Column(name = "LEAD_TELEPHONE", length = 100)
	private String leadTelephone;

	/** 团队类型 */
	@Column(name = "TEAM_TYPE", length = 100)
	private String teamType;
	
	/** 最后维护时间*/ 
	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_MAINTAIN_TIME")
	private Date lastMaintainTime;

	/** 团队人数 */
	@Column(name = "TEAM_SCALE", length = 100)
	private String teamScale;

	public String getTeamType() {
		return teamType;
	}

	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}

	public Date getLastMaintainTime() {
		return lastMaintainTime;
	}

	public void setLastMaintainTime(Date lastMaintainTime) {
		this.lastMaintainTime = lastMaintainTime;
	}

	public String getTeamScale() {
		return teamScale;
	}

	public void setTeamScale(String teamScale) {
		this.teamScale = teamScale;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getMktTeamName() {
		return mktTeamName;
	}

	public void setMktTeamName(String mktTeamName) {
		this.mktTeamName = mktTeamName;
	}

	public String getLeadTelephone() {
		return leadTelephone;
	}

	public void setLeadTelephone(String leadTelephone) {
		this.leadTelephone = leadTelephone;
	}

	public String getTeamLeader() {
		return teamLeader;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getTeamLeaderId() {
		return teamLeaderId;
	}

	public void setTeamLeaderId(String teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}

	public String getCreateUserOrgId() {
		return createUserOrgId;
	}

	public void setCreateUserOrgId(String createUserOrgId) {
		this.createUserOrgId = createUserOrgId;
	}

	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getMktTeamId() {
		return mktTeamId;
	}

	public void setMktTeamId(Long mktTeamId) {
		this.mktTeamId = mktTeamId;
	}

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	public Integer getTeamCusNo() {
		return teamCusNo;
	}

	public void setTeamCusNo(Integer teamCusNo) {
		this.teamCusNo = teamCusNo;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getTeamStatus() {
		return teamStatus;
	}

	public void setTeamStatus(String teamStatus) {
		this.teamStatus = teamStatus;
	}
	
	
}