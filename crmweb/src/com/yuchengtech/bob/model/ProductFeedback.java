package com.yuchengtech.bob.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.yuchengtech.bcrm.product.model.ProductInfo;

/**
 * The persistent class for the OCRM_F_PD_PROD_FEEDBACK database table.
 * 
 */
@Entity
@Table(name = "OCRM_F_PD_PROD_FEEDBACK")
public class ProductFeedback implements Serializable {

	/**
	 * FDM层(基础数据层)，归属于产品管理的第二主题，数据为产品反馈信息
	 */
	private static final long serialVersionUID = -4034110366581517596L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	/**反馈信息编号*/
	@Column(name = "FEEDBACK_ID", nullable = false)
	private Long feedbackId;

	/** 反馈内容 */
	@Column(name = "FEEDBACK_CONT", length = 500)
	private String feedbackContent;

	/** 反馈日期 */
	@Temporal(TemporalType.DATE)
	@Column(name = "FEEDBACK_DATE")
	private Date feedbackDate;

	/** 反馈人 */
	@Column(name = "FEEDBACK_USER", length = 50)
	private String feedbackUser;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private ProductInfo ocrmFPdProduct;

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

	public ProductInfo getOcrmFPdProduct() {
		return this.ocrmFPdProduct;
	}

	public void setOcrmFPdProduct(ProductInfo ocrmFPdProduct) {
		this.ocrmFPdProduct = ocrmFPdProduct;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}

	public String getFeedbackContent() {
		return feedbackContent;
	}

}