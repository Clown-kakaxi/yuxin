package com.yuchengtech.bcrm.custview.model;

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
 * The persistent class for the OCRM_F_CI_CONTRACT_INFO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CONTRACT_INFO")
public class OcrmFCiContractInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CONTRACT_INFO_ID_GENERATOR", sequenceName="ID_SEQUENCE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CONTRACT_INFO_ID_GENERATOR")
	private Long id;

	private String annex;

	private String attn;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="OGR_NAME")
	private String ogrName;

	@Column(name="SIGN_CHANEL")
	private String signChanel;

    @Temporal( TemporalType.DATE)
	@Column(name="SIGN_DATE")
	private Date signDate;

    @Temporal( TemporalType.DATE)
	@Column(name="SIGN_END_DATE")
	private Date signEndDate;

	@Column(name="SIGN_NAME")
	private String signName;

	@Column(name="SIGN_ORG")
	private String signOrg;

	@Column(name="SIGN_STS")
	private String signSts;

    public OcrmFCiContractInfo() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnnex() {
		return this.annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}

	public String getAttn() {
		return this.attn;
	}

	public void setAttn(String attn) {
		this.attn = attn;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOgrName() {
		return this.ogrName;
	}

	public void setOgrName(String ogrName) {
		this.ogrName = ogrName;
	}

	public String getSignChanel() {
		return this.signChanel;
	}

	public void setSignChanel(String signChanel) {
		this.signChanel = signChanel;
	}

	public Date getSignDate() {
		return this.signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Date getSignEndDate() {
		return this.signEndDate;
	}

	public void setSignEndDate(Date signEndDate) {
		this.signEndDate = signEndDate;
	}

	public String getSignName() {
		return this.signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getSignOrg() {
		return this.signOrg;
	}

	public void setSignOrg(String signOrg) {
		this.signOrg = signOrg;
	}

	public String getSignSts() {
		return this.signSts;
	}

	public void setSignSts(String signSts) {
		this.signSts = signSts;
	}

}