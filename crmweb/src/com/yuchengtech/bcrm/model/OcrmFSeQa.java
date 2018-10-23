package com.yuchengtech.bcrm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_SE_QA database table.
 * 
 */
@Entity
@Table(name="OCRM_F_SE_QA")
public class OcrmFSeQa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_SE_QA_QAID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_SE_QA_QAID_GENERATOR")
	@Column(name="QA_ID", unique=true, nullable=false)
	private Long qaId;

	@Column(name="QA_NAME", length=200)
	private String qaName;

	@Column(name="QA_REMARK", length=800)
	private String qaRemark;

//	//bi-directional many-to-one association to OcrmFSeTitle
//	@OneToMany(mappedBy="ocrmFSeQa")
//	private Set<OcrmFSeTitle> ocrmFSeTitles;

	public Long getQaId() {
		return qaId;
	}


	public void setQaId(Long qaId) {
		this.qaId = qaId;
	}


	public String getQaName() {
		return this.qaName;
	}

	public void setQaName(String qaName) {
		this.qaName = qaName;
	}

	public String getQaRemark() {
		return this.qaRemark;
	}

	public void setQaRemark(String qaRemark) {
		this.qaRemark = qaRemark;
	}
	
}