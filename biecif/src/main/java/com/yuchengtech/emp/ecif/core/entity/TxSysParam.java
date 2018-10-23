package com.yuchengtech.emp.ecif.core.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TX_SYS_PARAM database table.
 * 
 */
@Entity
@Table(name="TX_SYS_PARAM")
public class TxSysParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TX_SYS_PARAM_PARAMID_GENERATOR", sequenceName="SEQ_TX_EVT_NOTICE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TX_SYS_PARAM_PARAMID_GENERATOR")
	@Column(name="PARAM_ID")
	private long paramId;

	@Column(name="CREATE_OPER")
	private String createOper;

	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="EFFECTIVE_DATE")
	private Timestamp effectiveDate;

	@Column(name="EXPIRED_DATE")
	private Timestamp expiredDate;

	@Column(name="PARAM_NAME")
	private String paramName;

	@Column(name="PARAM_STAT")
	private String paramStat;

	@Column(name="PARAM_TYPE")
	private String paramType;

	@Column(name="PARAM_VALUE")
	private String paramValue;

	@Column(name="UPDATE_OPER")
	private String updateOper;

	@Column(name="UPDATE_TIME")
	private Timestamp updateTime;

    public TxSysParam() {
    }

	public long getParamId() {
		return this.paramId;
	}

	public void setParamId(long paramId) {
		this.paramId = paramId;
	}

	public String getCreateOper() {
		return this.createOper;
	}

	public void setCreateOper(String createOper) {
		this.createOper = createOper;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Timestamp getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Timestamp expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamStat() {
		return this.paramStat;
	}

	public void setParamStat(String paramStat) {
		this.paramStat = paramStat;
	}

	public String getParamType() {
		return this.paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getUpdateOper() {
		return this.updateOper;
	}

	public void setUpdateOper(String updateOper) {
		this.updateOper = updateOper;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}