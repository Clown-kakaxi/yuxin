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
 * The persistent class for the OCRM_A_CI_CUSTLOSS_RULE database table.
 * 
 */
@Entity
@Table(name="OCRM_A_CI_CUSTLOSS_RULE")
public class OcrmACiCustlossRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_A_CI_CUSTLOSS_RULE_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_A_CI_CUSTLOSS_RULE_ID_GENERATOR")
	private Long id;

	private BigDecimal amount;

	@Column(name="CREATE_ID")
	private String createId;

	@Column(name="CREATE_NAME")
	private String createName;

	@Column(name="REMIND_ROLES")
	private String remindRoles;

    public OcrmACiCustlossRule() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getRemindRoles() {
		return this.remindRoles;
	}

	public void setRemindRoles(String remindRoles) {
		this.remindRoles = remindRoles;
	}

}