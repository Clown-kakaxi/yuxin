package com.yuchengtech.bcrm.custmanager.model;

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
 * 营销团队与客户经理关联关系表
 */
@Entity
@Table(name="OCRM_F_CM_TEAM_CUST_MANAGER")
public class TeamCustomerManager implements Serializable {

	private static final long serialVersionUID = -6421781049795828401L;

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
	private String marketTeamId;

	/**用户ID*/
	@Column(name="USER_ID", nullable=false, length=100)
	private String userId;

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

	public String getMarketTeamId() {
		return marketTeamId;
	}

	public void setMarketTeamId(String marketTeamId) {
		this.marketTeamId = marketTeamId;
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