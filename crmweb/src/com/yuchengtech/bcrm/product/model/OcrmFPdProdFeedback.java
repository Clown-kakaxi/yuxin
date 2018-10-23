package com.yuchengtech.bcrm.product.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_PD_PROD_FEEDBACK database table.
* @author hujun
 */
@Entity
@Table(name="OCRM_F_PD_PROD_FEEDBACK")
public class OcrmFPdProdFeedback implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name="FEEDBACK_ID")
	private Long feedbackId;

	@Column(name="FEEDBACK_CONT")
	private String feedbackCont;

    @Temporal( TemporalType.DATE)
	@Column(name="FEEDBACK_DATE")
	private Date feedbackDate;

	@Column(name="FEEDBACK_USER")
	private String feedbackUser;

	@Column(name="PRODUCT_ID")
	private String productId;

    public OcrmFPdProdFeedback() {
    }

	public Long getFeedbackId() {
		return this.feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getFeedbackCont() {
		return this.feedbackCont;
	}

	public void setFeedbackCont(String feedbackCont) {
		this.feedbackCont = feedbackCont;
	}

	public Date getFeedbackDate() {
		return this.feedbackDate;
	}

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getFeedbackUser() {
		return this.feedbackUser;
	}

	public void setFeedbackUser(String feedbackUser) {
		this.feedbackUser = feedbackUser;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}