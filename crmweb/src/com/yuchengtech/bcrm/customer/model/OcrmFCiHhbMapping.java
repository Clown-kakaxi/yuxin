package com.yuchengtech.bcrm.customer.model;

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
 * The persistent class for the OCRM_F_CI_HHB_MAPPING database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_HHB_MAPPING")
public class OcrmFCiHhbMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_HHB_MAPPING_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_HHB_MAPPING_ID_GENERATOR")
	@Column(name = "ID",unique=true, nullable=false)
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="HHB_DT")
	private Date hhbDt;

	
	@Column(name="SOURCE_CUST_ID")
	private String sourceCustId;

	@Column(name="TARGET_CUST_ID")
	private String targetCustId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getHhbDt() {
		return hhbDt;
	}

	public void setHhbDt(Date hhbDt) {
		this.hhbDt = hhbDt;
	}

	public String getSourceCustId() {
		return sourceCustId;
	}

	public void setSourceCustId(String sourceCustId) {
		this.sourceCustId = sourceCustId;
	}

	public String getTargetCustId() {
		return targetCustId;
	}

	public void setTargetCustId(String targetCustId) {
		this.targetCustId = targetCustId;
	}
	
	
}