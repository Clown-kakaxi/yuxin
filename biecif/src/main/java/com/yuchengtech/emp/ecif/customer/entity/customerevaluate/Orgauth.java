package com.yuchengtech.emp.ecif.customer.entity.customerevaluate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;




/**
 * The persistent class for the ORGAUTH database table.
 * 
 */
@Entity
@Table(name="ORGAUTH")
public class Orgauth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_AUTH_ID", unique=true, nullable=false)
	private Long orgAuthId;

	@Column(name="AUTH_DATE",length=20)
	private String authDate;

	@Column(name="AUTH_ORG", length=80)
	private String authOrg;

	@Column(name="AUTH_RESULT", length=200)
	private String authResult;

	@Column(name="AUTH_TYPE", length=20)
	private String authType;

	@Column(name="CERT_NAME", length=80)
	private String certName;

	@Column(name="CERT_NO", length=32)
	private String certNo;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="EFFECTIVE_DATE",length=20)
	private String effectiveDate;

	@Column(name="EXPIRED_DATE",length=20)
	private String expiredDate;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM",length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="VALID_DATE",length=20)
	private String validDate;

    public Orgauth() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOrgAuthId() {
		return this.orgAuthId;
	}

	public void setOrgAuthId(Long orgAuthId) {
		this.orgAuthId = orgAuthId;
	}

	public String getAuthDate() {
		return this.authDate;
	}

	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}

	public String getAuthOrg() {
		return this.authOrg;
	}

	public void setAuthOrg(String authOrg) {
		this.authOrg = authOrg;
	}

	public String getAuthResult() {
		return this.authResult;
	}

	public void setAuthResult(String authResult) {
		this.authResult = authResult;
	}

	public String getAuthType() {
		return this.authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getCertName() {
		return this.certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getCertNo() {
		return this.certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getValidDate() {
		return this.validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

}