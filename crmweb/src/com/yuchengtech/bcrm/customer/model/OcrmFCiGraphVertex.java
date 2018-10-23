package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_CI_GRAPH_VERTEX database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_GRAPH_VERTEX")
public class OcrmFCiGraphVertex implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_GRAPH_VERTEX_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_GRAPH_VERTEX_ID_GENERATOR")
	private Long id;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="GRAPH_ID")
	private Long graphId;

	@Column(name="CUST_NAME")
	private String custName;
	
    public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public OcrmFCiGraphVertex() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Long getGraphId() {
		return this.graphId;
	}

	public void setGraphId(Long graphId) {
		this.graphId = graphId;
	}

}