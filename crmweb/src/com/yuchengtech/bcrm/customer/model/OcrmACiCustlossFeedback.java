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
 * The persistent class for the OCRM_A_CI_CUSTLOSS_FEEDBACK database table.
 * 
 */
@Entity
@Table(name="OCRM_A_CI_CUSTLOSS_FEEDBACK")
public class OcrmACiCustlossFeedback implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_A_CI_CUSTLOSS_FEEDBACK_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_A_CI_CUSTLOSS_FEEDBACK_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="F_REMARK_DATE")
	private Date fRemarkDate;

	@Column(name="F_USER_ID")
	private String fUserId;

	@Column(name="F_USER_NAME")
	private String fUserName;

	@Column(name="F_USER_REMARK")
	private String fUserRemark;

	@Column(name="YJ_ID")
	private String yjId;

    public OcrmACiCustlossFeedback() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFRemarkDate() {
		return this.fRemarkDate;
	}

	public void setFRemarkDate(Date fRemarkDate) {
		this.fRemarkDate = fRemarkDate;
	}

	public String getFUserId() {
		return this.fUserId;
	}

	public void setFUserId(String fUserId) {
		this.fUserId = fUserId;
	}

	public String getFUserName() {
		return this.fUserName;
	}

	public void setFUserName(String fUserName) {
		this.fUserName = fUserName;
	}

	public String getFUserRemark() {
		return this.fUserRemark;
	}

	public void setFUserRemark(String fUserRemark) {
		this.fUserRemark = fUserRemark;
	}

	public String getYjId() {
		return this.yjId;
	}

	public void setYjId(String yjId) {
		this.yjId = yjId;
	}

}