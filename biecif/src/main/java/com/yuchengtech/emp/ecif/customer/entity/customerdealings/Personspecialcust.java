package com.yuchengtech.emp.ecif.customer.entity.customerdealings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONSPECIALCUST database table.
 * 
 */
@Entity
@Table(name="PERSONSPECIALCUST")
public class Personspecialcust implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SPECIAL_CUST_ID", unique=true, nullable=false)
	private Long specialCustId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="PRIVATE_BANK", length=20)
	private String privateBank;

	@Column(name="RELATED_PARTY", length=20)
	private String relatedParty;

    public Personspecialcust() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getSpecialCustId() {
		return this.specialCustId;
	}

	public void setSpecialCustId(Long specialCustId) {
		this.specialCustId = specialCustId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getPrivateBank() {
		return this.privateBank;
	}

	public void setPrivateBank(String privateBank) {
		this.privateBank = privateBank;
	}

	public String getRelatedParty() {
		return this.relatedParty;
	}

	public void setRelatedParty(String relatedParty) {
		this.relatedParty = relatedParty;
	}

}