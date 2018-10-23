package com.yuchengtech.emp.ecif.customer.entity.customerbaseorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the ORGKEYNO database table.
 * 
 */
@Entity
@Table(name="ORGKEYNO")
public class Orgkeyno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="LOAN_CARD_NO", length=32)
	private String loanCardNo;

    public Orgkeyno() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getLoanCardNo() {
		return this.loanCardNo;
	}

	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}

}