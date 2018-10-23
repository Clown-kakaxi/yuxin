package com.yuchengtech.bcrm.finService.model;

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
 * The persistent class for the OCRM_F_FIN_CUST_IO database table.
 * 
 */
@Entity
@Table(name="OCRM_F_FIN_CUST_IO")
public class OcrmFFinCustIo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_FIN_CUST_IO_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_FIN_CUST_IO_ID_GENERATOR")
	private Long id;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="DETIAL_TYPE")
	private String detialType;

	@Column(name="IO_FREQ")
	private String ioFreq;

	@Column(name="IO_TYPE")
	private String ioType;

	private BigDecimal money;

    public OcrmFFinCustIo() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getDetialType() {
		return this.detialType;
	}

	public void setDetialType(String detialType) {
		this.detialType = detialType;
	}

	public String getIoFreq() {
		return this.ioFreq;
	}

	public void setIoFreq(String ioFreq) {
		this.ioFreq = ioFreq;
	}

	public String getIoType() {
		return this.ioType;
	}

	public void setIoType(String ioType) {
		this.ioType = ioType;
	}

	public BigDecimal getMoney() {
		return this.money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

}