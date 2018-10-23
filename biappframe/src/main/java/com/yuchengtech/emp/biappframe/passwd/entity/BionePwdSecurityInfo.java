package com.yuchengtech.emp.biappframe.passwd.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BIONE_PWD_SECURITY_INFO")
public class BionePwdSecurityInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="PWD_SECURITY_ID", nullable=false, unique=true, length=32)
	private String pwdSecurityId  ;
	
	
	@Column(name="USE_PWD_SECURITY", length=1)
	private String usePwdSecurity ;
	
	@Column(name="IS_SAVE_PWD_HIS", length=1)
	private String isSavePwdHis ;
	
	@Column(name="PWD_USE_TIME", precision=12)
	private BigDecimal pwdUseTime ;
	
	@Column(name="PWD_MIX_LENGTH", precision=12)
	private BigDecimal pwdMixLength ;
	
	@Column(name="PWD_MAX_LENGTH", precision=12)
	private BigDecimal pwdMaxLength ;
	
	@Column(name="PWD_COMPLEX", length=200)
	private String pwdComplex ;
	
	@Column(name="ALLOW_ERROR_TIMES", precision=12)
	private BigDecimal allowErrorTimes ;
	
	@Column(name="LOCK_TYPE", length=10)
	private String lockType ;
	
	@Column(name="LOCK_TIME", precision=12)
	private BigDecimal lockTime ;
	
	@Column(name="CREATE_TIME")
	private Timestamp createTime ;
	
	@Column(name="LAST_UPDATE_TIME")
	private Timestamp lastUpdateTime ;

	@Column(name="REMARK", length=500)
	private String remark ;
	
	public String getUsePwdSecurity() {
		return usePwdSecurity;
	}

	public void setUsePwdSecurity(String usePwdSecurity) {
		this.usePwdSecurity = usePwdSecurity;
	}

	public String getPwdSecurityId() {
		return pwdSecurityId;
	}

	public void setPwdSecurityId(String pwdSecurityId) {
		this.pwdSecurityId = pwdSecurityId;
	}

	public String getIsSavePwdHis() {
		return isSavePwdHis;
	}

	public void setIsSavePwdHis(String isSavePwdHis) {
		this.isSavePwdHis = isSavePwdHis;
	}

	public BigDecimal getPwdUseTime() {
		return pwdUseTime;
	}

	public void setPwdUseTime(BigDecimal pwdUseTime) {
		this.pwdUseTime = pwdUseTime;
	}

	public BigDecimal getPwdMixLength() {
		return pwdMixLength;
	}

	public void setPwdMixLength(BigDecimal pwdMixLength) {
		this.pwdMixLength = pwdMixLength;
	}

	public BigDecimal getPwdMaxLength() {
		return pwdMaxLength;
	}

	public void setPwdMaxLength(BigDecimal pwdMaxLength) {
		this.pwdMaxLength = pwdMaxLength;
	}

	public String getPwdComplex() {
		return pwdComplex;
	}

	public void setPwdComplex(String pwdComplex) {
		this.pwdComplex = pwdComplex;
	}

	public BigDecimal getAllowErrorTimes() {
		return allowErrorTimes;
	}

	public void setAllowErrorTimes(BigDecimal allowErrorTimes) {
		this.allowErrorTimes = allowErrorTimes;
	}

	public String getLockType() {
		return lockType;
	}

	public void setLockType(String lockType) {
		this.lockType = lockType;
	}

	public BigDecimal getLockTime() {
		return lockTime;
	}

	public void setLockTime(BigDecimal lockTime) {
		this.lockTime = lockTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
