package com.yuchengtech.emp.ecif.customer.entity.customerfinanceperson;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONINVESTMENT database table.
 * 
 */
@Entity
@Table(name="PERSONINVESTMENT")
public class Personinvestment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="FINANCEFLAG",length=1)
	private String financeflag;

	@Column(name="FUNDFLAG",length=1)
	private String fundflag;

	@Column(name="INVESTAIM",length=2)
	private String investaim;

	@Column(name="INVESTEXPECT",length=2)
	private String investexpect;
	
	@Column(name="INVESTMENT",length=20)
	private String investment;

	@Column(name="STOCKFLAG",length=1)
	private String stockflag;

    public Personinvestment() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getFinanceflag() {
		return this.financeflag;
	}

	public void setFinanceflag(String financeflag) {
		this.financeflag = financeflag;
	}

	public String getFundflag() {
		return this.fundflag;
	}

	public void setFundflag(String fundflag) {
		this.fundflag = fundflag;
	}

	public String getInvestaim() {
		return this.investaim;
	}

	public void setInvestaim(String investaim) {
		this.investaim = investaim;
	}

	public String getInvestexpect() {
		return this.investexpect;
	}

	public void setInvestexpect(String investexpect) {
		this.investexpect = investexpect;
	}

	public String getStockflag() {
		return this.stockflag;
	}

	public void setStockflag(String stockflag) {
		this.stockflag = stockflag;
	}

	public String getInvestment() {
		return investment;
	}

	public void setInvestment(String investment) {
		this.investment = investment;
	}

}