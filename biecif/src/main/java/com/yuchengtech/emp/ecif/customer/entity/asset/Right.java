package com.yuchengtech.emp.ecif.customer.entity.asset;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the "RIGHT" database table.
 * 
 */
@Entity
@Table(name="M_HL_RIGHT")
public class Right implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="ADMITTANCE_STD", length=200)
	private String admittanceStd;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="RIGHT_APPRO_NO", length=40)
	private String rightApproNo;

	@Column(name="RIGHT_DESC", length=255)
	private String rightDesc;

	@Column(name="RIGHT_YEAR_LIMIT", precision=5, scale=2)
	private BigDecimal rightYearLimit;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="USED_YEARS", precision=5, scale=2)
	private BigDecimal usedYears;

    public Right() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public String getAdmittanceStd() {
		return this.admittanceStd;
	}

	public void setAdmittanceStd(String admittanceStd) {
		this.admittanceStd = admittanceStd;
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

	public String getRightApproNo() {
		return this.rightApproNo;
	}

	public void setRightApproNo(String rightApproNo) {
		this.rightApproNo = rightApproNo;
	}

	public String getRightDesc() {
		return this.rightDesc;
	}

	public void setRightDesc(String rightDesc) {
		this.rightDesc = rightDesc;
	}

	public BigDecimal getRightYearLimit() {
		return this.rightYearLimit;
	}

	public void setRightYearLimit(BigDecimal rightYearLimit) {
		this.rightYearLimit = rightYearLimit;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public BigDecimal getUsedYears() {
		return this.usedYears;
	}

	public void setUsedYears(BigDecimal usedYears) {
		this.usedYears = usedYears;
	}

}