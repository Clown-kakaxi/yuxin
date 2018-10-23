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
 * The persistent class for the OCRM_F_CI_CUST_FEEDBACK_INFO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CUST_FEEDBACK_INFO")
public class OcrmFCiCustFeedbackInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUST_FEEDBACK_INFO_ID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUST_FEEDBACK_INFO_ID_GENERATOR")
	private Long id;

	@Column(name="CUST_NO")
	private String custNo;

	@Column(name="FEEDBACK_CONT")
	private String feedbackCont;

	@Column(name="FEEDBACK_NAIYOO")
	private String feedbackNaiyoo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEEDBACK_TIME")
	private Date feedbackTime;

	@Column(name="FEEDBACK_TITLE")
	private String feedbackTitle;

	@Column(name="FEEDBACK_TYPE")
	private String feedbackType;

    public OcrmFCiCustFeedbackInfo() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getFeedbackCont() {
		return this.feedbackCont;
	}

	public void setFeedbackCont(String feedbackCont) {
		this.feedbackCont = feedbackCont;
	}

	public String getFeedbackNaiyoo() {
		return this.feedbackNaiyoo;
	}

	public void setFeedbackNaiyoo(String feedbackNaiyoo) {
		this.feedbackNaiyoo = feedbackNaiyoo;
	}

	public Date getFeedbackTime() {
		return this.feedbackTime;
	}

	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public String getFeedbackTitle() {
		return this.feedbackTitle;
	}

	public void setFeedbackTitle(String feedbackTitle) {
		this.feedbackTitle = feedbackTitle;
	}

	public String getFeedbackType() {
		return this.feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

}