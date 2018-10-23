package com.yuchengtech.bcrm.customer.model;


import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the OCRM_F_CI_CONTRIBUTION_PARM database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CONTRIBUTION_PARM")
public class OcrmFCiContributionParm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name="PARM_ID")
	private Long parmId;

	@Column(name="BRID")
	private String brid;

    @Temporal( TemporalType.DATE)
	private Date datedt;

    @Column(name="OWENERID")
	private String owenerid;

	@Column(name="PARM_LEVEL")
	private String parmLevel;

	@Column(name="PARM_NAME")
	private String parmName;

	@Column(name="PARM_NUM")
	private BigDecimal parmNum;

	@Column(name="PARM_TYPE")
	private String parmType;

    public OcrmFCiContributionParm() {
    }

	public Long getParmId() {
		return this.parmId;
	}

	public void setParmId(Long parmId) {
		this.parmId = parmId;
	}

	public String getBrid() {
		return this.brid;
	}

	public void setBrid(String brid) {
		this.brid = brid;
	}

	public Date getDatedt() {
		return this.datedt;
	}

	public void setDatedt(Date datedt) {
		this.datedt = datedt;
	}

	public String getOwenerid() {
		return this.owenerid;
	}

	public void setOwenerid(String owenerid) {
		this.owenerid = owenerid;
	}

	public String getParmLevel() {
		return this.parmLevel;
	}

	public void setParmLevel(String parmLevel) {
		this.parmLevel = parmLevel;
	}

	public String getParmName() {
		return this.parmName;
	}

	public void setParmName(String parmName) {
		this.parmName = parmName;
	}

	public BigDecimal getParmNum() {
		return this.parmNum;
	}

	public void setParmNum(BigDecimal parmNum) {
		this.parmNum = parmNum;
	}

	public String getParmType() {
		return this.parmType;
	}

	public void setParmType(String parmType) {
		this.parmType = parmType;
	}

}