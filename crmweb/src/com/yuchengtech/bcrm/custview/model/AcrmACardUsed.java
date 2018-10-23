package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the ACRM_A_CARD_USED database table.
 * 
 */
@Entity
@Table(name="ACRM_A_CARD_USED")
public class AcrmACardUsed implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="ACRM_A_CARD_USED_ID_GENERATOR", sequenceName="ID_SEQUENCE",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_A_CARD_USED_ID_GENERATOR")
	@Column(name="ID")
	private Long id;

	@Column(name="CARD_TYPE")
	private String cardType;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="INNER_DIFF_ATM")
	private BigDecimal innerDiffAtm;

	@Column(name="INNER_SAME_ATM")
	private BigDecimal innerSameAtm;

	@Column(name="INNER_USED")
	private BigDecimal innerUsed;

	@Column(name="OUT_ATM")
	private BigDecimal outAtm;

	@Column(name="OUT_USED")
	private BigDecimal outUsed;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

    public AcrmACardUsed() {
    }
    
    public Long getId(){
    	return this.id;
    }
    
    public void setId(Long id){
    	this.id = id;
    }

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public BigDecimal getInnerDiffAtm() {
		return this.innerDiffAtm;
	}

	public void setInnerDiffAtm(BigDecimal innerDiffAtm) {
		this.innerDiffAtm = innerDiffAtm;
	}

	public BigDecimal getInnerSameAtm() {
		return this.innerSameAtm;
	}

	public void setInnerSameAtm(BigDecimal innerSameAtm) {
		this.innerSameAtm = innerSameAtm;
	}

	public BigDecimal getInnerUsed() {
		return this.innerUsed;
	}

	public void setInnerUsed(BigDecimal innerUsed) {
		this.innerUsed = innerUsed;
	}

	public BigDecimal getOutAtm() {
		return this.outAtm;
	}

	public void setOutAtm(BigDecimal outAtm) {
		this.outAtm = outAtm;
	}

	public BigDecimal getOutUsed() {
		return this.outUsed;
	}

	public void setOutUsed(BigDecimal outUsed) {
		this.outUsed = outUsed;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}