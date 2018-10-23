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
 * The persistent class for the OCRM_F_CI_GROUP_MEMBERSHIP database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_GROUP_MEMBERSHIP")
public class OcrmFCiGroupMembership implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_CI_GROUP_MEMBERSHIP_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_CI_GROUP_MEMBERSHIP_ID_GENERATOR")
	private Long id;

	@Column(name="FROM_VERTEX")
	private String fromVertex;

	@Column(name="GROUP_NO")
	private String groupNo;

	@Column(name="RELATION_CODE")
	private String relationCode;

	@Column(name="RELATION_NAME")
	private String relationName;

	@Column(name="TO_VERTEX")
	private String toVertex;

	public OcrmFCiGroupMembership() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromVertex() {
		return this.fromVertex;
	}

	public void setFromVertex(String fromVertex) {
		this.fromVertex = fromVertex;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getRelationCode() {
		return this.relationCode;
	}

	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	public String getRelationName() {
		return this.relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getToVertex() {
		return this.toVertex;
	}

	public void setToVertex(String toVertex) {
		this.toVertex = toVertex;
	}
}