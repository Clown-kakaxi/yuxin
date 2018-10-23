package com.yuchengtech.emp.ecif.customer.entity.customerother;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the PERSONOTHERINFO database table.
 * 
 */
@Entity
@Table(name="PERSONOTHERINFO")
public class PersonOtherInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PER_OTHER_INFO_ID", unique=true, nullable=false)
	private Long perOtherInfoId;

	@Column(name="BUS_CARD_NO", length=20)
	private String busCardNo;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="EBANK_REG_NO", length=20)
	private String ebankRegNo;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(length=20)
	private String liquidassets;

	@Column(name="PUBLIC_UTILITY_PAY", length=80)
	private String publicUtilityPay;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="USED_BANK_NAME", length=60)
	private String usedBankName;

	@Column(name="USED_BANK_NUM")
	private Long usedBankNum;

    public PersonOtherInfo() {
    }

	public Long getPerOtherInfoId() {
		return this.perOtherInfoId;
	}

	public void setPerOtherInfoId(Long perOtherInfoId) {
		this.perOtherInfoId = perOtherInfoId;
	}

	public String getBusCardNo() {
		return this.busCardNo;
	}

	public void setBusCardNo(String busCardNo) {
		this.busCardNo = busCardNo;
	}

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getEbankRegNo() {
		return this.ebankRegNo;
	}

	public void setEbankRegNo(String ebankRegNo) {
		this.ebankRegNo = ebankRegNo;
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

	public String getLiquidassets() {
		return this.liquidassets;
	}

	public void setLiquidassets(String liquidassets) {
		this.liquidassets = liquidassets;
	}

	public String getPublicUtilityPay() {
		return this.publicUtilityPay;
	}

	public void setPublicUtilityPay(String publicUtilityPay) {
		this.publicUtilityPay = publicUtilityPay;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getUsedBankName() {
		return this.usedBankName;
	}

	public void setUsedBankName(String usedBankName) {
		this.usedBankName = usedBankName;
	}

	public Long getUsedBankNum() {
		return this.usedBankNum;
	}

	public void setUsedBankNum(Long usedBankNum) {
		this.usedBankNum = usedBankNum;
	}

}