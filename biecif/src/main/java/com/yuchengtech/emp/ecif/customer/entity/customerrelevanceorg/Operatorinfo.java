package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the OPERATORINFO database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_OPERATORINFO")
public class Operatorinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="OPERATOR_INFO_ID", unique=true, nullable=false)
	private Long operatorInfoId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="OPERATOR_ID")
	private Long operatorId;

	@Column(name="OPERATOR_IDENT_NO", length=40)
	private String operatorIdentNo;

	@Column(name="OPERATOR_IDENT_TYPE", length=20)
	private String operatorIdentType;

	@Column(name="OPERATOR_NAME", length=20)
	private String operatorName;

    public Operatorinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOperatorInfoId() {
		return this.operatorInfoId;
	}

	public void setOperatorInfoId(Long operatorInfoId) {
		this.operatorInfoId = operatorInfoId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorIdentNo() {
		return this.operatorIdentNo;
	}

	public void setOperatorIdentNo(String operatorIdentNo) {
		this.operatorIdentNo = operatorIdentNo;
	}

	public String getOperatorIdentType() {
		return this.operatorIdentType;
	}

	public void setOperatorIdentType(String operatorIdentType) {
		this.operatorIdentType = operatorIdentType;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}