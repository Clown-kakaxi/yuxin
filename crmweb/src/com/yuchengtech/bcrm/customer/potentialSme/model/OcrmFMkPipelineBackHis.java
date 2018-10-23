package com.yuchengtech.bcrm.customer.potentialSme.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the OCRM_F_MK_PIPELINE_HIS database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MK_PIPELINE_BACK_HIS")
public class OcrmFMkPipelineBackHis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

    @Temporal( TemporalType.DATE)
	@Column(name="BACK_DATE")
	private Date backDate;

	@Column(name="PIPELINE_ID")
	private long pipelineId;
	
	@Column(name="BACK_TYPE")
	private String backType;
	
	@Column(name="BACK_REASON")
	private String backReason;

    public OcrmFMkPipelineBackHis() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getBackDate() {
		return this.backDate;
	}

	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}

	public long getPipelineId() {
		return this.pipelineId;
	}

	public void setPipelineId(long pipelineId) {
		this.pipelineId = pipelineId;
	}

	public String getBackType() {
		return backType;
	}

	public void setBackType(String backType) {
		this.backType = backType;
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

}