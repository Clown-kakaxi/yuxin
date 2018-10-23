package com.ytec.fubonecif.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the M_CI_ACCOUNT_INFO database table.
 * 
 */
@Entity
@Table(name="M_CI_ACCOUNT_INFO")
public class MCiAccountInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="M_CI_ACCOUNT_INFO_SERIALNO_GENERATOR" )
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="M_CI_ACCOUNT_INFO_SERIALNO_GENERATOR")
	@Column(unique=true, nullable=false, length=32)
	private String serialno;

	@Column(name="ACCOUNT_CONTENTS", length=20)
	private String accountContents;

	@Column(name="CUST_ID", length=20)
	private String custId;

	@Column(name="IS_DOMESTIC_CUST", length=20)
	private String isDomesticCust;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="\"STATE\"", length=20)
	private String state;

    public MCiAccountInfo() {
    }

	public String getSerialno() {
		return this.serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getAccountContents() {
		return this.accountContents;
	}

	public void setAccountContents(String accountContents) {
		this.accountContents = accountContents;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getIsDomesticCust() {
		return this.isDomesticCust;
	}

	public void setIsDomesticCust(String isDomesticCust) {
		this.isDomesticCust = isDomesticCust;
	}

	public Date getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Date lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}