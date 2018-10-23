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
 * The persistent class for the BILLINFO database table.
 * 
 */
@Entity
@Table(name="M_HL_BILLINFO")
public class Billinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HOLDING_ID", unique=true, nullable=false)
	private Long holdingId;

	@Column(name="ACCEPT_TYPE", length=20)
	private String acceptType;

	@Column(name="ACCEPTOR",length=40)
	private String acceptor;

	@Column(name="BILL_CURRENCY", length=20)
	private String billCurrency;

	@Column(name="BILL_FACE_VALUE", precision=17, scale=2)
	private BigDecimal billFaceValue;

	@Column(name="BILL_NO", length=40)
	private String billNo;

	@Column(name="DRAWER",length=40)
	private String drawer;

	@Column(name="ENDORSEE",length=40)
	private String endorsee;

	@Column(name="GUARANTOR",length=40)
	private String guarantor;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM",length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="PAYEE",length=40)
	private String payee;

	@Column(name="PAYER",length=40)
	private String payer;

	@Column(name="RECOURSEFLAG",length=1)
	private String recourseflag;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Billinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getHoldingId() {
		return this.holdingId;
	}

	public void setHoldingId(Long holdingId) {
		this.holdingId = holdingId;
	}

	public String getAcceptType() {
		return this.acceptType;
	}

	public void setAcceptType(String acceptType) {
		this.acceptType = acceptType;
	}

	public String getAcceptor() {
		return this.acceptor;
	}

	public void setAcceptor(String acceptor) {
		this.acceptor = acceptor;
	}

	public String getBillCurrency() {
		return this.billCurrency;
	}

	public void setBillCurrency(String billCurrency) {
		this.billCurrency = billCurrency;
	}

	public BigDecimal getBillFaceValue() {
		return this.billFaceValue;
	}

	public void setBillFaceValue(BigDecimal billFaceValue) {
		this.billFaceValue = billFaceValue;
	}

	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getDrawer() {
		return this.drawer;
	}

	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}

	public String getEndorsee() {
		return this.endorsee;
	}

	public void setEndorsee(String endorsee) {
		this.endorsee = endorsee;
	}

	public String getGuarantor() {
		return this.guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
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

	public String getPayee() {
		return this.payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayer() {
		return this.payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getRecourseflag() {
		return this.recourseflag;
	}

	public void setRecourseflag(String recourseflag) {
		this.recourseflag = recourseflag;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}