package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiPerAssetDebt entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_ASSET_DEBT")
public class MCiPerAssetDebt implements java.io.Serializable {

	// Fields

	private String assetDebtId;
	private String custId;
	private String assetDebtType;
	private String itemType;
	private String curr;
	private Double amt;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiPerAssetDebt() {
	}

	/** minimal constructor */
	public MCiPerAssetDebt(String assetDebtId) {
		this.assetDebtId = assetDebtId;
	}

	/** full constructor */
	public MCiPerAssetDebt(String assetDebtId, String custId,
			String assetDebtType, String itemType, String curr, Double amt,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.assetDebtId = assetDebtId;
		this.custId = custId;
		this.assetDebtType = assetDebtType;
		this.itemType = itemType;
		this.curr = curr;
		this.amt = amt;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "ASSET_DEBT_ID", unique = true, nullable = false, length = 20)
	public String getAssetDebtId() {
		return this.assetDebtId;
	}

	public void setAssetDebtId(String assetDebtId) {
		this.assetDebtId = assetDebtId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "ASSET_DEBT_TYPE", length = 20)
	public String getAssetDebtType() {
		return this.assetDebtType;
	}

	public void setAssetDebtType(String assetDebtType) {
		this.assetDebtType = assetDebtType;
	}

	@Column(name = "ITEM_TYPE", length = 20)
	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Column(name = "CURR", length = 20)
	public String getCurr() {
		return this.curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	@Column(name = "AMT", precision = 17)
	public Double getAmt() {
		return this.amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
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