package com.yuchengtech.bcrm.customer.level.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_CI_ANTI_INDEX_INFO database table.
 * 
 */
@Entity
@Table(name="ACRM_A_ANTI_HIGH_INDEX")
public class AcrmAAntiHighIndex implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	@SequenceGenerator(name="ACRM_A_ANTI_HIGH_INDEX_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_A_ANTI_HIGH_INDEX_ID_GENERATOR")
//	private Long id;
	@Id
	@Column(name="INDEX_ID")
	private String indexId;

	@Column(name="INDEX_CODE")
	private String indexCode;

	public AcrmAAntiHighIndex() {
	  
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

}