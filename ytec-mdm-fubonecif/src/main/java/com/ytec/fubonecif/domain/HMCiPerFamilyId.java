package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMCiPerFamilyId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiPerFamilyId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiPerFamilyId() {
	}

	/** minimal constructor */
	public HMCiPerFamilyId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiPerFamilyId(String custId, String familyAddr, String homeTel,
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
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "CUST_ID", length = 20)
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

	@Column(name = "HIS_OPER_SYS", length = 20)
	public String getHisOperSys() {
		return this.hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	@Column(name = "HIS_OPER_TYPE", length = 2)
	public String getHisOperType() {
		return this.hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	@Column(name = "HIS_DATA_DATE", length = 10)
	public String getHisDataDate() {
		return this.hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HMCiPerFamilyId))
			return false;
		HMCiPerFamilyId castOther = (HMCiPerFamilyId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getFamilyAddr() == castOther.getFamilyAddr()) || (this
						.getFamilyAddr() != null
						&& castOther.getFamilyAddr() != null && this
						.getFamilyAddr().equals(castOther.getFamilyAddr())))
				&& ((this.getHomeTel() == castOther.getHomeTel()) || (this
						.getHomeTel() != null
						&& castOther.getHomeTel() != null && this.getHomeTel()
						.equals(castOther.getHomeTel())))
				&& ((this.getPopulation() == castOther.getPopulation()) || (this
						.getPopulation() != null
						&& castOther.getPopulation() != null && this
						.getPopulation().equals(castOther.getPopulation())))
				&& ((this.getChildrenNum() == castOther.getChildrenNum()) || (this
						.getChildrenNum() != null
						&& castOther.getChildrenNum() != null && this
						.getChildrenNum().equals(castOther.getChildrenNum())))
				&& ((this.getProvidePopNum() == castOther.getProvidePopNum()) || (this
						.getProvidePopNum() != null
						&& castOther.getProvidePopNum() != null && this
						.getProvidePopNum()
						.equals(castOther.getProvidePopNum())))
				&& ((this.getSupplyPopNum() == castOther.getSupplyPopNum()) || (this
						.getSupplyPopNum() != null
						&& castOther.getSupplyPopNum() != null && this
						.getSupplyPopNum().equals(castOther.getSupplyPopNum())))
				&& ((this.getLaborPopNum() == castOther.getLaborPopNum()) || (this
						.getLaborPopNum() != null
						&& castOther.getLaborPopNum() != null && this
						.getLaborPopNum().equals(castOther.getLaborPopNum())))
				&& ((this.getIsHouseHolder() == castOther.getIsHouseHolder()) || (this
						.getIsHouseHolder() != null
						&& castOther.getIsHouseHolder() != null && this
						.getIsHouseHolder()
						.equals(castOther.getIsHouseHolder())))
				&& ((this.getHouseHolderName() == castOther
						.getHouseHolderName()) || (this.getHouseHolderName() != null
						&& castOther.getHouseHolderName() != null && this
						.getHouseHolderName().equals(
								castOther.getHouseHolderName())))
				&& ((this.getResidenceStat() == castOther.getResidenceStat()) || (this
						.getResidenceStat() != null
						&& castOther.getResidenceStat() != null && this
						.getResidenceStat()
						.equals(castOther.getResidenceStat())))
				&& ((this.getHouseStat() == castOther.getHouseStat()) || (this
						.getHouseStat() != null
						&& castOther.getHouseStat() != null && this
						.getHouseStat().equals(castOther.getHouseStat())))
				&& ((this.getHasHomeCar() == castOther.getHasHomeCar()) || (this
						.getHasHomeCar() != null
						&& castOther.getHasHomeCar() != null && this
						.getHasHomeCar().equals(castOther.getHasHomeCar())))
				&& ((this.getIsCreditFamily() == castOther.getIsCreditFamily()) || (this
						.getIsCreditFamily() != null
						&& castOther.getIsCreditFamily() != null && this
						.getIsCreditFamily().equals(
								castOther.getIsCreditFamily())))
				&& ((this.getIsHarmony() == castOther.getIsHarmony()) || (this
						.getIsHarmony() != null
						&& castOther.getIsHarmony() != null && this
						.getIsHarmony().equals(castOther.getIsHarmony())))
				&& ((this.getCreditAmount() == castOther.getCreditAmount()) || (this
						.getCreditAmount() != null
						&& castOther.getCreditAmount() != null && this
						.getCreditAmount().equals(castOther.getCreditAmount())))
				&& ((this.getCreditInfo() == castOther.getCreditInfo()) || (this
						.getCreditInfo() != null
						&& castOther.getCreditInfo() != null && this
						.getCreditInfo().equals(castOther.getCreditInfo())))
				&& ((this.getBusiAndScale() == castOther.getBusiAndScale()) || (this
						.getBusiAndScale() != null
						&& castOther.getBusiAndScale() != null && this
						.getBusiAndScale().equals(castOther.getBusiAndScale())))
				&& ((this.getMainIncomeSource() == castOther
						.getMainIncomeSource()) || (this.getMainIncomeSource() != null
						&& castOther.getMainIncomeSource() != null && this
						.getMainIncomeSource().equals(
								castOther.getMainIncomeSource())))
				&& ((this.getFamilyAnnIncScope() == castOther
						.getFamilyAnnIncScope()) || (this
						.getFamilyAnnIncScope() != null
						&& castOther.getFamilyAnnIncScope() != null && this
						.getFamilyAnnIncScope().equals(
								castOther.getFamilyAnnIncScope())))
				&& ((this.getFamilyAnnualPayScope() == castOther
						.getFamilyAnnualPayScope()) || (this
						.getFamilyAnnualPayScope() != null
						&& castOther.getFamilyAnnualPayScope() != null && this
						.getFamilyAnnualPayScope().equals(
								castOther.getFamilyAnnualPayScope())))
				&& ((this.getFamilyAssetsInfo() == castOther
						.getFamilyAssetsInfo()) || (this.getFamilyAssetsInfo() != null
						&& castOther.getFamilyAssetsInfo() != null && this
						.getFamilyAssetsInfo().equals(
								castOther.getFamilyAssetsInfo())))
				&& ((this.getFamilyDebtScope() == castOther
						.getFamilyDebtScope()) || (this.getFamilyDebtScope() != null
						&& castOther.getFamilyDebtScope() != null && this
						.getFamilyDebtScope().equals(
								castOther.getFamilyDebtScope())))
				&& ((this.getFamilyAdverseRecords() == castOther
						.getFamilyAdverseRecords()) || (this
						.getFamilyAdverseRecords() != null
						&& castOther.getFamilyAdverseRecords() != null && this
						.getFamilyAdverseRecords().equals(
								castOther.getFamilyAdverseRecords())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())))
				&& ((this.getLastUpdateSys() == castOther.getLastUpdateSys()) || (this
						.getLastUpdateSys() != null
						&& castOther.getLastUpdateSys() != null && this
						.getLastUpdateSys()
						.equals(castOther.getLastUpdateSys())))
				&& ((this.getLastUpdateUser() == castOther.getLastUpdateUser()) || (this
						.getLastUpdateUser() != null
						&& castOther.getLastUpdateUser() != null && this
						.getLastUpdateUser().equals(
								castOther.getLastUpdateUser())))
				&& ((this.getLastUpdateTm() == castOther.getLastUpdateTm()) || (this
						.getLastUpdateTm() != null
						&& castOther.getLastUpdateTm() != null && this
						.getLastUpdateTm().equals(castOther.getLastUpdateTm())))
				&& ((this.getTxSeqNo() == castOther.getTxSeqNo()) || (this
						.getTxSeqNo() != null
						&& castOther.getTxSeqNo() != null && this.getTxSeqNo()
						.equals(castOther.getTxSeqNo())))
				&& ((this.getHisOperSys() == castOther.getHisOperSys()) || (this
						.getHisOperSys() != null
						&& castOther.getHisOperSys() != null && this
						.getHisOperSys().equals(castOther.getHisOperSys())))
				&& ((this.getHisOperType() == castOther.getHisOperType()) || (this
						.getHisOperType() != null
						&& castOther.getHisOperType() != null && this
						.getHisOperType().equals(castOther.getHisOperType())))
				&& ((this.getHisOperTime() == castOther.getHisOperTime()) || (this
						.getHisOperTime() != null
						&& castOther.getHisOperTime() != null && this
						.getHisOperTime().equals(castOther.getHisOperTime())))
				&& ((this.getHisDataDate() == castOther.getHisDataDate()) || (this
						.getHisDataDate() != null
						&& castOther.getHisDataDate() != null && this
						.getHisDataDate().equals(castOther.getHisDataDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getFamilyAddr() == null ? 0 : this.getFamilyAddr()
						.hashCode());
		result = 37 * result
				+ (getHomeTel() == null ? 0 : this.getHomeTel().hashCode());
		result = 37
				* result
				+ (getPopulation() == null ? 0 : this.getPopulation()
						.hashCode());
		result = 37
				* result
				+ (getChildrenNum() == null ? 0 : this.getChildrenNum()
						.hashCode());
		result = 37
				* result
				+ (getProvidePopNum() == null ? 0 : this.getProvidePopNum()
						.hashCode());
		result = 37
				* result
				+ (getSupplyPopNum() == null ? 0 : this.getSupplyPopNum()
						.hashCode());
		result = 37
				* result
				+ (getLaborPopNum() == null ? 0 : this.getLaborPopNum()
						.hashCode());
		result = 37
				* result
				+ (getIsHouseHolder() == null ? 0 : this.getIsHouseHolder()
						.hashCode());
		result = 37
				* result
				+ (getHouseHolderName() == null ? 0 : this.getHouseHolderName()
						.hashCode());
		result = 37
				* result
				+ (getResidenceStat() == null ? 0 : this.getResidenceStat()
						.hashCode());
		result = 37 * result
				+ (getHouseStat() == null ? 0 : this.getHouseStat().hashCode());
		result = 37
				* result
				+ (getHasHomeCar() == null ? 0 : this.getHasHomeCar()
						.hashCode());
		result = 37
				* result
				+ (getIsCreditFamily() == null ? 0 : this.getIsCreditFamily()
						.hashCode());
		result = 37 * result
				+ (getIsHarmony() == null ? 0 : this.getIsHarmony().hashCode());
		result = 37
				* result
				+ (getCreditAmount() == null ? 0 : this.getCreditAmount()
						.hashCode());
		result = 37
				* result
				+ (getCreditInfo() == null ? 0 : this.getCreditInfo()
						.hashCode());
		result = 37
				* result
				+ (getBusiAndScale() == null ? 0 : this.getBusiAndScale()
						.hashCode());
		result = 37
				* result
				+ (getMainIncomeSource() == null ? 0 : this
						.getMainIncomeSource().hashCode());
		result = 37
				* result
				+ (getFamilyAnnIncScope() == null ? 0 : this
						.getFamilyAnnIncScope().hashCode());
		result = 37
				* result
				+ (getFamilyAnnualPayScope() == null ? 0 : this
						.getFamilyAnnualPayScope().hashCode());
		result = 37
				* result
				+ (getFamilyAssetsInfo() == null ? 0 : this
						.getFamilyAssetsInfo().hashCode());
		result = 37
				* result
				+ (getFamilyDebtScope() == null ? 0 : this.getFamilyDebtScope()
						.hashCode());
		result = 37
				* result
				+ (getFamilyAdverseRecords() == null ? 0 : this
						.getFamilyAdverseRecords().hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
		result = 37
				* result
				+ (getLastUpdateSys() == null ? 0 : this.getLastUpdateSys()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateUser() == null ? 0 : this.getLastUpdateUser()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateTm() == null ? 0 : this.getLastUpdateTm()
						.hashCode());
		result = 37 * result
				+ (getTxSeqNo() == null ? 0 : this.getTxSeqNo().hashCode());
		result = 37
				* result
				+ (getHisOperSys() == null ? 0 : this.getHisOperSys()
						.hashCode());
		result = 37
				* result
				+ (getHisOperType() == null ? 0 : this.getHisOperType()
						.hashCode());
		result = 37
				* result
				+ (getHisOperTime() == null ? 0 : this.getHisOperTime()
						.hashCode());
		result = 37
				* result
				+ (getHisDataDate() == null ? 0 : this.getHisDataDate()
						.hashCode());
		return result;
	}

}