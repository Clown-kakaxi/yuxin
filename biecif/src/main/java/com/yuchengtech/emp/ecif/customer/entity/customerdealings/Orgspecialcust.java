package com.yuchengtech.emp.ecif.customer.entity.customerdealings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the ORGSPECIALCUST database table.
 * 
 */
@Entity
@Table(name="ORGSPECIALCUST")
public class Orgspecialcust implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SPECIAL_CUST_ID", unique=true, nullable=false)
	private Long specialCustId;

	@Column(name="CASH_ADMIN_CUST_TYPE", length=20)
	private String cashAdminCustType;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="IS_AGENT_BANK_CUST", length=1)
	private String isAgentBankCust;

	@Column(name="IS_PARTNER", length=1)
	private String isPartner;

	@Column(name="IS_RELATED_PARTY", length=1)
	private String isRelatedParty;

    public Orgspecialcust() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getSpecialCustId() {
		return this.specialCustId;
	}

	public void setSpecialCustId(long specialCustId) {
		this.specialCustId = specialCustId;
	}

	public String getCashAdminCustType() {
		return this.cashAdminCustType;
	}

	public void setCashAdminCustType(String cashAdminCustType) {
		this.cashAdminCustType = cashAdminCustType;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public String getIsAgentBankCust() {
		return this.isAgentBankCust;
	}

	public void setIsAgentBankCust(String isAgentBankCust) {
		this.isAgentBankCust = isAgentBankCust;
	}

	public String getIsPartner() {
		return this.isPartner;
	}

	public void setIsPartner(String isPartner) {
		this.isPartner = isPartner;
	}

	public String getIsRelatedParty() {
		return this.isRelatedParty;
	}

	public void setIsRelatedParty(String isRelatedParty) {
		this.isRelatedParty = isRelatedParty;
	}

}