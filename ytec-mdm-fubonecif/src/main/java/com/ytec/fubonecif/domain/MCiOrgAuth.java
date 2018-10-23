package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiOrgAuth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG_AUTH")
public class MCiOrgAuth implements java.io.Serializable {

	// Fields

	private String orgAuthId;
	private String custId;
	private String authType;
	private String authOrg;
	private String authResult;
	private String certName;
	private String certNo;
	private Date authDate;
	private Date validDate;
	private Date effectiveDate;
	private Date expiredDate;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiOrgAuth() {
	}

	/** minimal constructor */
	public MCiOrgAuth(String orgAuthId) {
		this.orgAuthId = orgAuthId;
	}

	/** full constructor */
	public MCiOrgAuth(String orgAuthId, String custId, String authType,
			String authOrg, String authResult, String certName, String certNo,
			Date authDate, Date validDate, Date effectiveDate,
			Date expiredDate, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.orgAuthId = orgAuthId;
		this.custId = custId;
		this.authType = authType;
		this.authOrg = authOrg;
		this.authResult = authResult;
		this.certName = certName;
		this.certNo = certNo;
		this.authDate = authDate;
		this.validDate = validDate;
		this.effectiveDate = effectiveDate;
		this.expiredDate = expiredDate;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "ORG_AUTH_ID", unique = true, nullable = false, length = 20)
	public String getOrgAuthId() {
		return this.orgAuthId;
	}

	public void setOrgAuthId(String orgAuthId) {
		this.orgAuthId = orgAuthId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "AUTH_TYPE", length = 20)
	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	@Column(name = "AUTH_ORG", length = 80)
	public String getAuthOrg() {
		return this.authOrg;
	}

	public void setAuthOrg(String authOrg) {
		this.authOrg = authOrg;
	}

	@Column(name = "AUTH_RESULT", length = 200)
	public String getAuthResult() {
		return this.authResult;
	}

	public void setAuthResult(String authResult) {
		this.authResult = authResult;
	}

	@Column(name = "CERT_NAME", length = 80)
	public String getCertName() {
		return this.certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	@Column(name = "CERT_NO", length = 32)
	public String getCertNo() {
		return this.certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "AUTH_DATE", length = 7)
	public Date getAuthDate() {
		return this.authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VALID_DATE", length = 7)
	public Date getValidDate() {
		return this.validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECTIVE_DATE", length = 7)
	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRED_DATE", length = 7)
	public Date getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}