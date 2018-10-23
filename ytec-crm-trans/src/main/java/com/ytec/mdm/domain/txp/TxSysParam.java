package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxSysParam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_SYS_PARAM")
public class TxSysParam implements java.io.Serializable {

	// Fields

	private Long paramId;
	private String paramName;
	private String paramType;
	private String paramStat;
	private String paramValue;
	private String createOper;
	private Timestamp createTime;
	private String updateOper;
	private Timestamp updateTime;
	private Timestamp effectiveDate;
	private Timestamp expiredDate;

	// Constructors

	/** default constructor */
	public TxSysParam() {
	}

	/** full constructor */
	public TxSysParam(String paramName, String paramType, String paramStat,
			String paramValue, String createOper, Timestamp createTime,
			String updateOper, Timestamp updateTime, Timestamp effectiveDate,
			Timestamp expiredDate) {
		this.paramName = paramName;
		this.paramType = paramType;
		this.paramStat = paramStat;
		this.paramValue = paramValue;
		this.createOper = createOper;
		this.createTime = createTime;
		this.updateOper = updateOper;
		this.updateTime = updateTime;
		this.effectiveDate = effectiveDate;
		this.expiredDate = expiredDate;
	}

	// Property accessors
	@Id
		@Column(name = "PARAM_ID", unique = true, nullable = false)
	public Long getParamId() {
		return this.paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	@Column(name = "PARAM_NAME", length = 40)
	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Column(name = "PARAM_TYPE", length = 20)
	public String getParamType() {
		return this.paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	@Column(name = "PARAM_STAT", length = 20)
	public String getParamStat() {
		return this.paramStat;
	}

	public void setParamStat(String paramStat) {
		this.paramStat = paramStat;
	}

	@Column(name = "PARAM_VALUE")
	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Column(name = "CREATE_OPER", length = 20)
	public String getCreateOper() {
		return this.createOper;
	}

	public void setCreateOper(String createOper) {
		this.createOper = createOper;
	}

	@Column(name = "CREATE_TIME", length = 11)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_OPER", length = 20)
	public String getUpdateOper() {
		return this.updateOper;
	}

	public void setUpdateOper(String updateOper) {
		this.updateOper = updateOper;
	}

	@Column(name = "UPDATE_TIME", length = 11)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "EFFECTIVE_DATE", length = 11)
	public Timestamp getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Column(name = "EXPIRED_DATE", length = 11)
	public Timestamp getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}

}