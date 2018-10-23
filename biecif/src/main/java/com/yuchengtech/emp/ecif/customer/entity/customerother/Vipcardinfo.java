package com.yuchengtech.emp.ecif.customer.entity.customerother;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the VIPCARDINFO database table.
 * 
 */
@Entity
@Table(name="VIPCARDINFO")
public class Vipcardinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="VIP_CARD_ID", unique=true, nullable=false)
	private Long vipCardId;

	@Column(name="CUST_ID")
	private Long custId;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

    @Temporal( TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="VIP_CARD_BRCCODE", length=9)
	private String vipCardBrccode;

	@Column(name="VIP_CARD_LEVEL", length=20)
	private String vipCardLevel;

	@Column(name="VIP_CARD_NO", length=20)
	private String vipCardNo;

	@Column(name="VIP_CARD_TYPE", length=20)
	private String vipCardType;

    public Vipcardinfo() {
    }

	public Long getVipCardId() {
		return this.vipCardId;
	}

	public void setVipCardId(Long vipCardId) {
		this.vipCardId = vipCardId;
	}

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getVipCardBrccode() {
		return this.vipCardBrccode;
	}

	public void setVipCardBrccode(String vipCardBrccode) {
		this.vipCardBrccode = vipCardBrccode;
	}

	public String getVipCardLevel() {
		return this.vipCardLevel;
	}

	public void setVipCardLevel(String vipCardLevel) {
		this.vipCardLevel = vipCardLevel;
	}

	public String getVipCardNo() {
		return this.vipCardNo;
	}

	public void setVipCardNo(String vipCardNo) {
		this.vipCardNo = vipCardNo;
	}

	public String getVipCardType() {
		return this.vipCardType;
	}

	public void setVipCardType(String vipCardType) {
		this.vipCardType = vipCardType;
	}

}