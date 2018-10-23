package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
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
 * The persistent class for the OCRM_F_CI_GRAPH database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_GRAPH")
public class OcrmFCiGraph implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_GRAPH_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_GRAPH_ID_GENERATOR")
	private Long id;

	private String author;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_ORG")
	private String createOrg;

	@Column(name="GRAPH_DESCRIBE")
	private String graphDescribe;

	@Column(name="GRAPH_NAME")
	private String graphName;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	private String updater;

    public OcrmFCiGraph() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateOrg() {
		return this.createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public String getGraphDescribe() {
		return this.graphDescribe;
	}

	public void setGraphDescribe(String graphDescribe) {
		this.graphDescribe = graphDescribe;
	}

	public String getGraphName() {
		return this.graphName;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdater() {
		return this.updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

}