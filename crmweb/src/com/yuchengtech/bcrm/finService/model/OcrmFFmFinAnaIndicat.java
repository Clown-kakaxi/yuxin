package com.yuchengtech.bcrm.finService.model;

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
 * The persistent class for the OCRM_F_FM_FIN_ANA_INDICAT database table.
 * 
 */
@Entity
@Table(name="OCRM_F_FM_FIN_ANA_INDICAT")
public class OcrmFFmFinAnaIndicat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_FM_FIN_ANA_INDICAT_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_FM_FIN_ANA_INDICAT_ID_GENERATOR")
	private Long id;

	@Column(name="CUST_ID")
	private String custId;

	private String proposal;

	private String target;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="UPDATER_ID")
	private String updaterId;

    public OcrmFFmFinAnaIndicat() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getProposal() {
		return this.proposal;
	}

	public void setProposal(String proposal) {
		this.proposal = proposal;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdaterId() {
		return this.updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

}