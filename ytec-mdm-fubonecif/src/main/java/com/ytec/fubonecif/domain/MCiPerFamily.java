package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MCiPerFamily entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_PER_FAMILY")
public class MCiPerFamily implements java.io.Serializable {

	// Fields

	private String custId;
	private String familyAddr;
	private String homeTel;
	private BigDecimal population;
	private BigDecimal childrenNum;
	private BigDecimal providePopNum;
	private BigDecimal supplyPopNum;
	private BigDecimal laborPopNum;
	private String isHouseHolder;
	private String houseHolderName;
	private String residenceStat;
	private String houseStat;
	private String hasHomeCar;
	private String isCreditFamily;
	private String isHarmony;
	private Double creditAmount;
	private String creditInfo;
	private String busiAndScale;
	private String mainIncomeSource;
	private String familyAnnIncScope;
	private String familyAnnualPayScope;
	private String familyAssetsInfo;
	private String familyDebtScope;
	private String familyAdverseRecords;
	private String remark;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiPerFamily() {
	}

	/** minimal constructor */
	public MCiPerFamily(String custId) {
		this.custId = custId;
	}

	/** full constructor */
	public MCiPerFamily(String custId, String familyAddr, String homeTel,
			BigDecimal population, BigDecimal childrenNum,
			BigDecimal providePopNum, BigDecimal supplyPopNum,
			BigDecimal laborPopNum, String isHouseHolder,
			String houseHolderName, String residenceStat, String houseStat,
			String hasHomeCar, String isCreditFamily, String isHarmony,
			Double creditAmount, String creditInfo, String busiAndScale,
			String mainIncomeSource, String familyAnnIncScope,
			String familyAnnualPayScope, String familyAssetsInfo,
			String familyDebtScope, String familyAdverseRecords, String remark,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.custId = custId;
		this.familyAddr = familyAddr;
		this.homeTel = homeTel;
		this.population = population;
		this.childrenNum = childrenNum;
		this.providePopNum = providePopNum;
		this.supplyPopNum = supplyPopNum;
		this.laborPopNum = laborPopNum;
		this.isHouseHolder = isHouseHolder;
		this.houseHolderName = houseHolderName;
		this.residenceStat = residenceStat;
		this.houseStat = houseStat;
		this.hasHomeCar = hasHomeCar;
		this.isCreditFamily = isCreditFamily;
		this.isHarmony = isHarmony;
		this.creditAmount = creditAmount;
		this.creditInfo = creditInfo;
		this.busiAndScale = busiAndScale;
		this.mainIncomeSource = mainIncomeSource;
		this.familyAnnIncScope = familyAnnIncScope;
		this.familyAnnualPayScope = familyAnnualPayScope;
		this.familyAssetsInfo = familyAssetsInfo;
		this.familyDebtScope = familyDebtScope;
		this.familyAdverseRecords = familyAdverseRecords;
		this.remark = remark;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "CUST_ID", unique = true, nullable = false, length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "FAMILY_ADDR", length = 200)
	public String getFamilyAddr() {
		return this.familyAddr;
	}

	public void setFamilyAddr(String familyAddr) {
		this.familyAddr = familyAddr;
	}

	@Column(name = "HOME_TEL", length = 20)
	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	@Column(name = "POPULATION", precision = 22, scale = 0)
	public BigDecimal getPopulation() {
		return this.population;
	}

	public void setPopulation(BigDecimal population) {
		this.population = population;
	}

	@Column(name = "CHILDREN_NUM", precision = 22, scale = 0)
	public BigDecimal getChildrenNum() {
		return this.childrenNum;
	}

	public void setChildrenNum(BigDecimal childrenNum) {
		this.childrenNum = childrenNum;
	}

	@Column(name = "PROVIDE_POP_NUM", precision = 22, scale = 0)
	public BigDecimal getProvidePopNum() {
		return this.providePopNum;
	}

	public void setProvidePopNum(BigDecimal providePopNum) {
		this.providePopNum = providePopNum;
	}

	@Column(name = "SUPPLY_POP_NUM", precision = 22, scale = 0)
	public BigDecimal getSupplyPopNum() {
		return this.supplyPopNum;
	}

	public void setSupplyPopNum(BigDecimal supplyPopNum) {
		this.supplyPopNum = supplyPopNum;
	}

	@Column(name = "LABOR_POP_NUM", precision = 22, scale = 0)
	public BigDecimal getLaborPopNum() {
		return this.laborPopNum;
	}

	public void setLaborPopNum(BigDecimal laborPopNum) {
		this.laborPopNum = laborPopNum;
	}

	@Column(name = "IS_HOUSE_HOLDER", length = 1)
	public String getIsHouseHolder() {
		return this.isHouseHolder;
	}

	public void setIsHouseHolder(String isHouseHolder) {
		this.isHouseHolder = isHouseHolder;
	}

	@Column(name = "HOUSE_HOLDER_NAME", length = 80)
	public String getHouseHolderName() {
		return this.houseHolderName;
	}

	public void setHouseHolderName(String houseHolderName) {
		this.houseHolderName = houseHolderName;
	}

	@Column(name = "RESIDENCE_STAT", length = 20)
	public String getResidenceStat() {
		return this.residenceStat;
	}

	public void setResidenceStat(String residenceStat) {
		this.residenceStat = residenceStat;
	}

	@Column(name = "HOUSE_STAT", length = 20)
	public String getHouseStat() {
		return this.houseStat;
	}

	public void setHouseStat(String houseStat) {
		this.houseStat = houseStat;
	}

	@Column(name = "HAS_HOME_CAR", length = 1)
	public String getHasHomeCar() {
		return this.hasHomeCar;
	}

	public void setHasHomeCar(String hasHomeCar) {
		this.hasHomeCar = hasHomeCar;
	}

	@Column(name = "IS_CREDIT_FAMILY", length = 1)
	public String getIsCreditFamily() {
		return this.isCreditFamily;
	}

	public void setIsCreditFamily(String isCreditFamily) {
		this.isCreditFamily = isCreditFamily;
	}

	@Column(name = "IS_HARMONY", length = 1)
	public String getIsHarmony() {
		return this.isHarmony;
	}

	public void setIsHarmony(String isHarmony) {
		this.isHarmony = isHarmony;
	}

	@Column(name = "CREDIT_AMOUNT", precision = 17)
	public Double getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	@Column(name = "CREDIT_INFO", length = 20)
	public String getCreditInfo() {
		return this.creditInfo;
	}

	public void setCreditInfo(String creditInfo) {
		this.creditInfo = creditInfo;
	}

	@Column(name = "BUSI_AND_SCALE", length = 200)
	public String getBusiAndScale() {
		return this.busiAndScale;
	}

	public void setBusiAndScale(String busiAndScale) {
		this.busiAndScale = busiAndScale;
	}

	@Column(name = "MAIN_INCOME_SOURCE", length = 20)
	public String getMainIncomeSource() {
		return this.mainIncomeSource;
	}

	public void setMainIncomeSource(String mainIncomeSource) {
		this.mainIncomeSource = mainIncomeSource;
	}

	@Column(name = "FAMILY_ANN_INC_SCOPE", length = 20)
	public String getFamilyAnnIncScope() {
		return this.familyAnnIncScope;
	}

	public void setFamilyAnnIncScope(String familyAnnIncScope) {
		this.familyAnnIncScope = familyAnnIncScope;
	}

	@Column(name = "FAMILY_ANNUAL_PAY_SCOPE", length = 20)
	public String getFamilyAnnualPayScope() {
		return this.familyAnnualPayScope;
	}

	public void setFamilyAnnualPayScope(String familyAnnualPayScope) {
		this.familyAnnualPayScope = familyAnnualPayScope;
	}

	@Column(name = "FAMILY_ASSETS_INFO", length = 80)
	public String getFamilyAssetsInfo() {
		return this.familyAssetsInfo;
	}

	public void setFamilyAssetsInfo(String familyAssetsInfo) {
		this.familyAssetsInfo = familyAssetsInfo;
	}

	@Column(name = "FAMILY_DEBT_SCOPE", length = 20)
	public String getFamilyDebtScope() {
		return this.familyDebtScope;
	}

	public void setFamilyDebtScope(String familyDebtScope) {
		this.familyDebtScope = familyDebtScope;
	}

	@Column(name = "FAMILY_ADVERSE_RECORDS", length = 200)
	public String getFamilyAdverseRecords() {
		return this.familyAdverseRecords;
	}

	public void setFamilyAdverseRecords(String familyAdverseRecords) {
		this.familyAdverseRecords = familyAdverseRecords;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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