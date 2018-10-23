package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_CI_CUSTGROUP_GRAPH_VERT database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CUSTGROUP_GRAPH_VERT")
public class OcrmFCiCustgroupGraphVert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUSTGROUP_GRAPH_VERT_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUSTGROUP_GRAPH_VERT_ID_GENERATOR")
	private long id;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUSTGROUP_ID")
	private BigDecimal custgroupId;

	public OcrmFCiCustgroupGraphVert() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public BigDecimal getCustgroupId() {
		return this.custgroupId;
	}

	public void setCustgroupId(BigDecimal custgroupId) {
		this.custgroupId = custgroupId;
	}

}