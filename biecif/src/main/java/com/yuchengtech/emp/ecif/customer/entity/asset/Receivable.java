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
 * The persistent class for the RECEIVABLE database table.
 * 
 */
@Entity
@Table(name="M_HL_RECEIVABLE")
public class Receivable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="PRODUCT_SERVICE_NAME", length=80)
	private String productServiceName;

	@Column(name="RECEIVABLE_AGING", precision=5, scale=2)
	private BigDecimal receivableAging;

	@Column(name="RECEIVABLE_AMT", precision=17, scale=2)
	private BigDecimal receivableAmt;

	@Column(name="RECEIVABLE_DEBTOR_NAME", length=80)
	private String receivableDebtorName;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Receivable() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
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

	public String getProductServiceName() {
		return this.productServiceName;
	}

	public void setProductServiceName(String productServiceName) {
		this.productServiceName = productServiceName;
	}

	public BigDecimal getReceivableAging() {
		return this.receivableAging;
	}

	public void setReceivableAging(BigDecimal receivableAging) {
		this.receivableAging = receivableAging;
	}

	public BigDecimal getReceivableAmt() {
		return this.receivableAmt;
	}

	public void setReceivableAmt(BigDecimal receivableAmt) {
		this.receivableAmt = receivableAmt;
	}

	public String getReceivableDebtorName() {
		return this.receivableDebtorName;
	}

	public void setReceivableDebtorName(String receivableDebtorName) {
		this.receivableDebtorName = receivableDebtorName;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}