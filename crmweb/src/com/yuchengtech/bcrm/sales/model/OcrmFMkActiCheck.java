package com.yuchengtech.bcrm.sales.model;

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
 * The persistent class for the OCRM_F_MK_ACTI_CHECK database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MK_ACTI_CHECK")
public class OcrmFMkActiCheck implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_MK_ACTI_CHECK_ACTICHECKID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_MK_ACTI_CHECK_ACTICHECKID_GENERATOR")
	@Column(name="ACTI_CHECK_ID")
	private Long actiCheckId;

    @Temporal( TemporalType.DATE)
	@Column(name="CHECK_DATE")
	private Date checkDate;

	@Column(name="CHECK_IDEA")
	private String checkIdea;
	
	@Column(name="CHECK_STATUS")
	private String checkStatus;
	
	@Column(name="APP_REASON")
	private String appReason;

	@Column(name="CHECK_USER")
	private String checkUser;

	@Column(name="MKT_ACTI_ID")
	private BigDecimal mktActiId;

	public Long getActiCheckId() {
		return actiCheckId;
	}

	public void setActiCheckId(Long actiCheckId) {
		this.actiCheckId = actiCheckId;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckIdea() {
		return checkIdea;
	}

	public void setCheckIdea(String checkIdea) {
		this.checkIdea = checkIdea;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getAppReason() {
		return appReason;
	}

	public void setAppReason(String appReason) {
		this.appReason = appReason;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public BigDecimal getMktActiId() {
		return mktActiId;
	}

	public void setMktActiId(BigDecimal mktActiId) {
		this.mktActiId = mktActiId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}