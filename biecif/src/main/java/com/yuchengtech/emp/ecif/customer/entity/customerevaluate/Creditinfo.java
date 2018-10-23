package com.yuchengtech.emp.ecif.customer.entity.customerevaluate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the CREDITINFO database table.
 * 
 */
@Entity
@Table(name="CREDITINFO")
public class Creditinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CREDIT_INFO_ID", unique=true, nullable=false)
	private Long creditInfoId;

	@Column(name="BUSI_LOAN_NUM")
	private Long busiLoanNum;

	@Column(name="CREDIT_LOAN_NUM")
	private Long creditLoanNum;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="LOAN_NUM")
	private Long loanNum;

	@Column(name="MAX_OVERDUE_12_MON")
	private Long maxOverdue12Mon;

	@Column(name="MAX_OVERDUE_24_MON")
	private Long maxOverdue24Mon;

	@Column(name="ONE_OVERDUE_12_MON")
	private Long oneOverdue12Mon;

	@Column(name="ONE_OVERDUE_24_MON")
	private Long oneOverdue24Mon;

    public Creditinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCreditInfoId() {
		return this.creditInfoId;
	}

	public void setCreditInfoId(Long creditInfoId) {
		this.creditInfoId = creditInfoId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getBusiLoanNum() {
		return this.busiLoanNum;
	}

	public void setBusiLoanNum(Long busiLoanNum) {
		this.busiLoanNum = busiLoanNum;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCreditLoanNum() {
		return this.creditLoanNum;
	}

	public void setCreditLoanNum(Long creditLoanNum) {
		this.creditLoanNum = creditLoanNum;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getLoanNum() {
		return this.loanNum;
	}

	public void setLoanNum(Long loanNum) {
		this.loanNum = loanNum;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getMaxOverdue12Mon() {
		return this.maxOverdue12Mon;
	}

	public void setMaxOverdue12Mon(Long maxOverdue12Mon) {
		this.maxOverdue12Mon = maxOverdue12Mon;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getMaxOverdue24Mon() {
		return this.maxOverdue24Mon;
	}

	public void setMaxOverdue24Mon(Long maxOverdue24Mon) {
		this.maxOverdue24Mon = maxOverdue24Mon;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOneOverdue12Mon() {
		return this.oneOverdue12Mon;
	}

	public void setOneOverdue12Mon(Long oneOverdue12Mon) {
		this.oneOverdue12Mon = oneOverdue12Mon;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOneOverdue24Mon() {
		return this.oneOverdue24Mon;
	}

	public void setOneOverdue24Mon(Long oneOverdue24Mon) {
		this.oneOverdue24Mon = oneOverdue24Mon;
	}

}