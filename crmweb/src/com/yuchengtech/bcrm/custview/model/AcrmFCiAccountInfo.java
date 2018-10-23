package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_ACCOUNT_INFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ACCOUNT_INFO")
public class AcrmFCiAccountInfo implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	@SequenceGenerator(name="ACRM_F_CI_ACCOUNT_INFO_ID_GENERATOR", sequenceName="SEQUENCE_ACRMFCIACCOUNTINFO" ,allocationSize = 1)
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_ACCOUNT_INFO_ID_GENERATOR")
//	private Long id;

	@Id
	@Column(name="CUST_ID")
	private String custId;
	
	@Column(name="ACCOUNT_CONTENTS")
	private String accountContents;

	@Column(name="IS_DOMESTIC_CUST")
	private String isDomesticCust;


    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	
	@Column(name="SERIALNO")
	private String serialno;

	private String state;
	
	@Column(name="ACCOUNT_NUMBERS")
	private String accountNumbers;

    public String getAccountContents() {
		return accountContents;
	}

	public void setAccountContents(String accountContents) {
		this.accountContents = accountContents;
	}

	public String getIsDomesticCust() {
		return isDomesticCust;
	}

	public void setIsDomesticCust(String isDomesticCust) {
		this.isDomesticCust = isDomesticCust;
	}

	public AcrmFCiAccountInfo() {
    }

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	public String getAccountNumbers() {
		return accountNumbers;
	}

	public void setAccountNumbers(String accountNumbers) {
		this.accountNumbers = accountNumbers;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

}