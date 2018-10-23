package com.yuchengtech.bcrm.customer.level.model;

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
 * The persistent class for the OCRM_F_CI_ANTI_INDEX_APPLY database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_ANTI_INDEX_APPLY")
public class OcrmFCiAntiIndexApply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_ANTI_INDEX_APPLY_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_ANTI_INDEX_APPLY_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="APPLY_DATE")
	private Date applyDate;

	@Column(name="INDEX_ID")
	private String indexId;

	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="APPLY_STATE")
	private String applyState;

    public OcrmFCiAntiIndexApply() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getIndexId() {
		return this.indexId;
	}

	public String getApplyState() {
		return applyState;
	}

	public void setApplyState(String applyState) {
		this.applyState = applyState;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}