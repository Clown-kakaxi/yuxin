package com.yuchengtech.bcrm.finService.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the OCRM_F_FIN_TARGET database table.
 * 
 */
@Entity
@Table(name="OCRM_F_FIN_TARGET")
public class OcrmFFinTarget implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_FIN_TARGET_TARGETID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_FIN_TARGET_TARGETID_GENERATOR")
	@Column(name="TARGET_ID")
	private Long targetId;

	private String available;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="DEMAND_ID")
	private BigDecimal demandId;

	@Column(name="DEMAND_TYPE")
	private String demandType;

	@Column(name="TAEGET_DESC")
	private String taegetDesc;

	@Column(name="TARGET_NAME")
	private String targetName;

	@Column(name="TARGET_SCALE")
	private BigDecimal targetScale;

    public OcrmFFinTarget() {
    }

	public Long getTargetId() {
		return this.targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getAvailable() {
		return this.available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public BigDecimal getDemandId() {
		return this.demandId;
	}

	public void setDemandId(BigDecimal demandId) {
		this.demandId = demandId;
	}

	public String getDemandType() {
		return this.demandType;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}

	public String getTaegetDesc() {
		return this.taegetDesc;
	}

	public void setTaegetDesc(String taegetDesc) {
		this.taegetDesc = taegetDesc;
	}

	public String getTargetName() {
		return this.targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public BigDecimal getTargetScale() {
		return this.targetScale;
	}

	public void setTargetScale(BigDecimal targetScale) {
		this.targetScale = targetScale;
	}

}