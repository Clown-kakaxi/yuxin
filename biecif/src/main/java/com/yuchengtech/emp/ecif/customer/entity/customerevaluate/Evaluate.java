package com.yuchengtech.emp.ecif.customer.entity.customerevaluate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONEVALUATE database table.
 * 
 */
@Entity
@Table(name="EVALUATE")
public class Evaluate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EVALUATE_ID", unique=true, nullable=false)
	private Long evaluateId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="EVALUATE_DATE",length=20)
	private String evaluateDate;

	@Column(name="EVALUATE_INFO", length=100)
	private String evaluateInfo;

	@Column(name="EVALUATE_TYPE", length=20)
	private String evaluateType;

    public Evaluate() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getEvaluateDate() {
		return this.evaluateDate;
	}

	public void setEvaluateDate(String evaluateDate) {
		this.evaluateDate = evaluateDate;
	}

	public String getEvaluateInfo() {
		return this.evaluateInfo;
	}

	public void setEvaluateInfo(String evaluateInfo) {
		this.evaluateInfo = evaluateInfo;
	}

	public String getEvaluateType() {
		return this.evaluateType;
	}

	public void setEvaluateType(String evaluateType) {
		this.evaluateType = evaluateType;
	}

}