package com.yuchengtech.bcrm.customer.potentialMkt.model;

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
 * The persistent class for the OCRM_F_CI_MKT_APPROVED_DB database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_MKT_APPROVED_DB")
public class OcrmFCiMktApprovedDb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_MKT_APPROVED_DB_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_MKT_APPROVED_DB_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="DB_DATE")
	private Date dbDate;

	@Column(name="DB_AMT")
	private BigDecimal dbAmt;



	@Column(name="DB_USER")
	private String dbUser;

	
    
    /**
	 * PIPELINE_ID
	 */
	@Column(name="PIPELINE_ID")
	private Long pipelineId;
	    
    
    
    public OcrmFCiMktApprovedDb() {
    }



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Date getDbDate() {
		return dbDate;
	}



	public void setDbDate(Date dbDate) {
		this.dbDate = dbDate;
	}



	public BigDecimal getDbAmt() {
		return dbAmt;
	}



	public void setDbAmt(BigDecimal dbAmt) {
		this.dbAmt = dbAmt;
	}



	public String getDbUser() {
		return dbUser;
	}



	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}



	public Long getPipelineId() {
		return pipelineId;
	}



	public void setPipelineId(Long pipelineId) {
		this.pipelineId = pipelineId;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	

}