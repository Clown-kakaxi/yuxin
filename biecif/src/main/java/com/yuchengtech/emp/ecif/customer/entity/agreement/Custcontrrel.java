package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

@Entity
@Table(name="CUST_CONTR_REL")
public class Custcontrrel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/*
CUST_CONTR_REL_ID	客户与协议关系标识	BIGINT
CUST_ID	客户标识	BIGINT
CONTR_ID	协议标识	VARCHAR
CUST_CONTR_REL_TYPE	客户与协议关系类型	VARCHAR
	 */
	@Id
	@Column(name="CUST_CONTR_REL_ID", unique=true, nullable=false)
	private Long custContrRelId;
	
	@Column(name="CUST_ID")
	private Long custId;
	
	@Column(name="CONTR_ID")
	private String contrId;
	
	@Column(name="CUST_CONTR_REL_TYPE",length=20)
	private String custContrRelType;
	
	@Column(name="LAST_UPDATE_SYS",length=20)
	private Long lastUpdate;
	
	@Column(name="LAST_UPDATE_USER",length=20)
	private Long lastUpdateUser;
	
	@Column(name="LAST_UPDATE_TM",length=20)
	private Long lastUpdateTm;
	
	@Column(name="TX_SEQ_NO",length=32)
	private Long txSeqNo;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustContrRelId() {
		return custContrRelId;
	}

	public void setCustContrRelId(Long custContrRelId) {
		this.custContrRelId = custContrRelId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public String getContrId() {
		return contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getCustContrRelType() {
		return custContrRelType;
	}

	public void setCustContrRelType(String custContrRelType) {
		this.custContrRelType = custContrRelType;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(Long lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getLastUpdateTm() {
		return lastUpdateTm;
	}

	public void setLastUpdateTm(Long lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getTxSeqNo() {
		return txSeqNo;
	}

	public void setTxSeqNo(Long txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}