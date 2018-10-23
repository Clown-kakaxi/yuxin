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
 * �ͻ������ʷ��
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CF_HIS")
public class OcrmFCiCfHi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CF_HIS_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CF_HIS_GENERATOR")
	@Column(name = "ID",unique=true, nullable=false)
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="HHB_DT")
	private Date hhbDt;

	@Column(name="OPP_USER")
	private String oppUser;

	@Column(name="OPP_USER_ORG")
	private String oppUserOrg;

	@Column(name="SOURCE_CUST_ID")
	private String sourceCustId;

	@Column(name="TARGET_CUST_ID")
	private String targetCustId;

    public OcrmFCiCfHi() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getHhbDt() {
		return this.hhbDt;
	}

	public void setHhbDt(Date hhbDt) {
		this.hhbDt = hhbDt;
	}

	public String getOppUser() {
		return this.oppUser;
	}

	public void setOppUser(String oppUser) {
		this.oppUser = oppUser;
	}

	public String getOppUserOrg() {
		return this.oppUserOrg;
	}

	public void setOppUserOrg(String oppUserOrg) {
		this.oppUserOrg = oppUserOrg;
	}

	public String getSourceCustId() {
		return this.sourceCustId;
	}

	public void setSourceCustId(String sourceCustId) {
		this.sourceCustId = sourceCustId;
	}

	public String getTargetCustId() {
		return this.targetCustId;
	}

	public void setTargetCustId(String targetCustId) {
		this.targetCustId = targetCustId;
	}

}