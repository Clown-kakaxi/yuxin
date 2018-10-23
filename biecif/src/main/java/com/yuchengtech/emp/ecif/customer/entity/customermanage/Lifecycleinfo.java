package com.yuchengtech.emp.ecif.customer.entity.customermanage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the LIFECYCLEINFO database table.
 * 
 */
@Entity
@Table(name="LIFECYCLEINFO")
public class Lifecycleinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="LIFECYCLE_STAT_DESC", length=80)
	private String lifecycleStatDesc;

	@Column(name="LIFECYCLE_STAT_TYPE", length=20)
	private String lifecycleStatType;

    public Lifecycleinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getLifecycleStatDesc() {
		return this.lifecycleStatDesc;
	}

	public void setLifecycleStatDesc(String lifecycleStatDesc) {
		this.lifecycleStatDesc = lifecycleStatDesc;
	}

	public String getLifecycleStatType() {
		return this.lifecycleStatType;
	}

	public void setLifecycleStatType(String lifecycleStatType) {
		this.lifecycleStatType = lifecycleStatType;
	}

}