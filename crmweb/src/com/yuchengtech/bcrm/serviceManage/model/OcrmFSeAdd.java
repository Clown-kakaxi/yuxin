package com.yuchengtech.bcrm.serviceManage.model;

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
 * The persistent class for the OCRM_F_SE_ADD database table.
 * 
 */
@Entity
@Table(name="OCRM_F_SE_ADD")
public class OcrmFSeAdd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_SE_ADD_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_SE_ADD_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="ADD_DATE")
	private Date addDate;

	@Column(name="ADD_RESON")
	private String addReson;

	@Column(name="SCORE_ADD")
	private BigDecimal scoreAdd;

	@Column(name="SCORE_ID")
	private String scoreId;
	
	@Column(name="STATE")
	private String state;
	
	@Column(name="ADD_FILE")
	private String addFile;

    public OcrmFSeAdd() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAddDate() {
		return this.addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getAddReson() {
		return this.addReson;
	}

	public void setAddReson(String addReson) {
		this.addReson = addReson;
	}

	public BigDecimal getScoreAdd() {
		return this.scoreAdd;
	}

	public void setScoreAdd(BigDecimal scoreAdd) {
		this.scoreAdd = scoreAdd;
	}

	public String getScoreId() {
		return this.scoreId;
	}

	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddFile() {
		return addFile;
	}

	public void setAddFile(String addFile) {
		this.addFile = addFile;
	}
	
	

}