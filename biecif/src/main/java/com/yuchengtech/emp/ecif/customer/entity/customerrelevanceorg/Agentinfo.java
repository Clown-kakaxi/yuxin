package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the AGENTINFO database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_AGENTINFO")
public class Agentinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="AGENT_INFO_ID", unique=true, nullable=false)
	private Long agentInfoId;

	@Column(name="AGENT_ID")
	private Long agentId;

	@Column(name="AGENT_IDENT_EXPIRED_DATE",length=20)
	private String agentIdentExpiredDate;

	@Column(name="AGENT_IDENT_NO", length=40)
	private String agentIdentNo;

	@Column(name="AGENT_IDENT_TYPE", length=20)
	private String agentIdentType;

	@Column(name="AGENT_NAME", length=20)
	private String agentName;

	@Column(name="CUST_ID")
	private Long custId;

    public Agentinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getAgentInfoId() {
		return this.agentInfoId;
	}

	public void setAgentInfoId(Long agentInfoId) {
		this.agentInfoId = agentInfoId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getAgentId() {
		return this.agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getAgentIdentExpiredDate() {
		return this.agentIdentExpiredDate;
	}

	public void setAgentIdentExpiredDate(String agentIdentExpiredDate) {
		this.agentIdentExpiredDate = agentIdentExpiredDate;
	}

	public String getAgentIdentNo() {
		return this.agentIdentNo;
	}

	public void setAgentIdentNo(String agentIdentNo) {
		this.agentIdentNo = agentIdentNo;
	}

	public String getAgentIdentType() {
		return this.agentIdentType;
	}

	public void setAgentIdentType(String agentIdentType) {
		this.agentIdentType = agentIdentType;
	}

	public String getAgentName() {
		return this.agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

}