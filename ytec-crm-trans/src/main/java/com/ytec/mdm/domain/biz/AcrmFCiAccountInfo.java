package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ACRM_F_CI_ACCOUNT_INFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ACCOUNT_INFO")
@NamedQuery(name="AcrmFCiAccountInfo.findAll", query="SELECT a FROM AcrmFCiAccountInfo a")
public class AcrmFCiAccountInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ACCOUNT_CONTENTS")
	private String accountContents;
	
	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="IS_DOMESTIC_CUST")
	private String isDomesticCust;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="STATE")
	private String state;
	
	@Column(name="SERIALNO")
	private String serialno;

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public AcrmFCiAccountInfo() {
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