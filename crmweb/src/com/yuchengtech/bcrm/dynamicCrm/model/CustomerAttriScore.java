package com.yuchengtech.bcrm.dynamicCrm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 客户属性指标分值表
 * @author liliang5
 *
 */
@Entity
@Table(name="OCRM_F_CI_CUST_ATTRI_SCORE")
public class CustomerAttriScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_CUST_ATTRI_SCORE_INDEX_ID_GENERATOR", sequenceName="SEQUENCE_ATTRI_CONF" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_CUST_ATTRI_SCORE_INDEX_ID_GENERATOR")
	@Column(name="INDEX_ID")
	private String indexId;

	@Column(name="ATTRI_ID")
	private String attriId;
	
	@Column(name="INDEX_NAME")
	private String indexName;
	
	@Column(name="INDEX_VALUE")
	private String indexValue;
	
	@Column(name="INDEX_SCORE")
	private String indexScore;
	
	@Column(name="INDEX_STATE")
	private String indexState;

	public String getAttriId() {
		return attriId;
	}

	public void setAttriId(String attriId) {
		this.attriId = attriId;
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexValue() {
		return indexValue;
	}

	public void setIndexValue(String indexValue) {
		this.indexValue = indexValue;
	}

	public String getIndexScore() {
		return indexScore;
	}

	public void setIndexScore(String indexScore) {
		this.indexScore = indexScore;
	}

	public String getIndexState() {
		return indexState;
	}

	public void setIndexState(String indexState) {
		this.indexState = indexState;
	}
	
}
