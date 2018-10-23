package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiOrgRelativecorp entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG_RELATIVECORP")
public class MCiOrgRelativecorp implements java.io.Serializable {

	// Fields

	private String relativeCorpId;
	private String custId;
	private String relativeCorpName;
	private String orgCode;
	private String relationType;
	private String relationDesc;
	private Double totalAssets;
	private Double netAssets;
	private Double totalDebt;
	private Double netProfit;
	private String mainBusinessType;
	private Double mainBusinessIncome;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiOrgRelativecorp() {
	}

	/** minimal constructor */
	public MCiOrgRelativecorp(String relativeCorpId) {
		this.relativeCorpId = relativeCorpId;
	}

	/** full constructor */
	public MCiOrgRelativecorp(String relativeCorpId, String custId,
			String relativeCorpName, String orgCode, String relationType,
			String relationDesc, Double totalAssets, Double netAssets,
			Double totalDebt, Double netProfit, String mainBusinessType,
			Double mainBusinessIncome, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
		this.relativeCorpId = relativeCorpId;
		this.custId = custId;
		this.relativeCorpName = relativeCorpName;
		this.orgCode = orgCode;
		this.relationType = relationType;
		this.relationDesc = relationDesc;
		this.totalAssets = totalAssets;
		this.netAssets = netAssets;
		this.totalDebt = totalDebt;
		this.netProfit = netProfit;
		this.mainBusinessType = mainBusinessType;
		this.mainBusinessIncome = mainBusinessIncome;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "RELATIVE_CORP_ID", unique = true, nullable = false, length = 20)
	public String getRelativeCorpId() {
		return this.relativeCorpId;
	}

	public void setRelativeCorpId(String relativeCorpId) {
		this.relativeCorpId = relativeCorpId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "RELATIVE_CORP_NAME", length = 80)
	public String getRelativeCorpName() {
		return this.relativeCorpName;
	}

	public void setRelativeCorpName(String relativeCorpName) {
		this.relativeCorpName = relativeCorpName;
	}

	@Column(name = "ORG_CODE", length = 40)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "RELATION_TYPE", length = 20)
	public String getRelationType() {
		return this.relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	@Column(name = "RELATION_DESC", length = 100)
	public String getRelationDesc() {
		return this.relationDesc;
	}

	public void setRelationDesc(String relationDesc) {
		this.relationDesc = relationDesc;
	}

	@Column(name = "TOTAL_ASSETS", precision = 17)
	public Double getTotalAssets() {
		return this.totalAssets;
	}

	public void setTotalAssets(Double totalAssets) {
		this.totalAssets = totalAssets;
	}

	@Column(name = "NET_ASSETS", precision = 17)
	public Double getNetAssets() {
		return this.netAssets;
	}

	public void setNetAssets(Double netAssets) {
		this.netAssets = netAssets;
	}

	@Column(name = "TOTAL_DEBT", precision = 17)
	public Double getTotalDebt() {
		return this.totalDebt;
	}

	public void setTotalDebt(Double totalDebt) {
		this.totalDebt = totalDebt;
	}

	@Column(name = "NET_PROFIT", precision = 17)
	public Double getNetProfit() {
		return this.netProfit;
	}

	public void setNetProfit(Double netProfit) {
		this.netProfit = netProfit;
	}

	@Column(name = "MAIN_BUSINESS_TYPE", length = 20)
	public String getMainBusinessType() {
		return this.mainBusinessType;
	}

	public void setMainBusinessType(String mainBusinessType) {
		this.mainBusinessType = mainBusinessType;
	}

	@Column(name = "MAIN_BUSINESS_INCOME", precision = 17)
	public Double getMainBusinessIncome() {
		return this.mainBusinessIncome;
	}

	public void setMainBusinessIncome(Double mainBusinessIncome) {
		this.mainBusinessIncome = mainBusinessIncome;
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