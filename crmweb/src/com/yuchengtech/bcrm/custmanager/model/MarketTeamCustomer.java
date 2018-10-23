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
 * The persistent class for the OCRM_F_CM_MKT_TEAM_CUST database table.
 * 营销团队与客户关系表
 */
@Entity
@Table(name="OCRM_F_CM_MKT_TEAM_CUST")
public class MarketTeamCustomer implements Serializable {

	private static final long serialVersionUID = 1240581747217264880L;

	/**主键*/
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column( nullable=false)
	private Long id;

	/**客户ID*/
	@Column(name="CUST_ID", nullable=false, length=40)
	private String custId;

	/**加入时间*/
    @Temporal( TemporalType.DATE)
	@Column(name="JOIN_DATE")
	private Date joinDate;

	/**营销团队ID*/
    @Column(name="MKT_TEAM_ID", nullable=false, length=40)
	private String mktTeamId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getMktTeamId() {
		return mktTeamId;
	}

	public void setMktTeamId(String mktTeamId) {
		this.mktTeamId = mktTeamId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}