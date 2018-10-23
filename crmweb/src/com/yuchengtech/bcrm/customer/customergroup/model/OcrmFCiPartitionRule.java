package com.yuchengtech.bcrm.customer.customergroup.model;

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
 * The persistent class for the OCRM_F_CI_PARTITION_RULE database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_PARTITION_RULE")
public class OcrmFCiPartitionRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_PARTITION_RULE_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_PARTITION_RULE_ID_GENERATOR")
	private Long id;

	private String condition;

	private String connector;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_ORG")
	private String createOrg;

	@Column(name="CREATE_USER_ID")
	private String createUserId;

	@Column(name="CUST_BASE_ID")
	private String custBaseId;

	@Column(name="INDEX_ID")
	private Long indexId;

	@Column(name="LEFT_PART_LIST")
	private String leftPartList;

	@Column(name="RIGHT_PART_LIST")
	private String rightPartList;

	@Column(name="SQL_STR")
	private String sqlStr;

	private String threshold;

    public OcrmFCiPartitionRule() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getConnector() {
		return this.connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
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

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCustBaseId() {
		return this.custBaseId;
	}

	public void setCustBaseId(String custBaseId) {
		this.custBaseId = custBaseId;
	}

	public Long getIndexId() {
		return this.indexId;
	}

	public void setIndexId(Long indexId) {
		this.indexId = indexId;
	}

	public String getLeftPartList() {
		return this.leftPartList;
	}

	public void setLeftPartList(String leftPartList) {
		this.leftPartList = leftPartList;
	}

	public String getRightPartList() {
		return this.rightPartList;
	}

	public void setRightPartList(String rightPartList) {
		this.rightPartList = rightPartList;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getThreshold() {
		return this.threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

}