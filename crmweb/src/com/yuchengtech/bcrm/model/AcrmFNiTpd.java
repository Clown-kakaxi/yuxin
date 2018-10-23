package com.yuchengtech.bcrm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_F_NI_TPD database table.
 * 
 */
@Entity
@Table(name="ACRM_F_NI_TPD")
public class AcrmFNiTpd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_NI_TPD_AGREEMENTID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_NI_TPD_AGREEMENTID_GENERATOR")
	@Column(name="AGREEMENT_ID",unique=true, nullable=false)
	private Long agreementId;

	@Column(name="ACCTNO")
	private String acctno;

	@Column(name="BAL")
	private BigDecimal bal;

	@Column(name="BTC_TRANAMT")
	private BigDecimal btcTranamt;

	@Column(name="CTB_TRANAMT")
	private BigDecimal ctbTranamt;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUSTOM_ID")
	private String customId;

	@Column(name="NAME")
	private String name;

	@Column(name="ODS_ST_DATE")
	private String odsStDate;

	@Column(name="PRD_TYP")
	private String prdTyp;

	@Column(name="STATUS")
	private String status;

	public Long getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(Long agreementId) {
		this.agreementId = agreementId;
	}

	public String getAcctno() {
		return acctno;
	}

	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}

	public BigDecimal getBtcTranamt() {
		return btcTranamt;
	}

	public void setBtcTranamt(BigDecimal btcTranamt) {
		this.btcTranamt = btcTranamt;
	}

	public BigDecimal getCtbTranamt() {
		return ctbTranamt;
	}

	public void setCtbTranamt(BigDecimal ctbTranamt) {
		this.ctbTranamt = ctbTranamt;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOdsStDate() {
		return odsStDate;
	}

	public void setOdsStDate(String odsStDate) {
		this.odsStDate = odsStDate;
	}

	public String getPrdTyp() {
		return prdTyp;
	}

	public void setPrdTyp(String prdTyp) {
		this.prdTyp = prdTyp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}