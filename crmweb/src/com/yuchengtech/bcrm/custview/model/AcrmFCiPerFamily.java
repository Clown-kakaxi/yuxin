package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the ACRM_F_CI_PER_FAMILY database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_PER_FAMILY")
public class AcrmFCiPerFamily implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_PER_FAMILY_ID_GENERATOR", sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_PER_FAMILY_ID_GENERATOR")
	private Long id;

	@Column(name="BUSI_AND_SCALE")
	private String busiAndScale;

	@Column(name="CHILDREN_NUM")
	private BigDecimal childrenNum;

	@Column(name="CREDIT_AMOUNT")
	private BigDecimal creditAmount;

	@Column(name="CREDIT_INFO")
	private String creditInfo;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="DEBT_STATE")
	private String debtState;

	@Column(name="FAMILY_ADDR")
	private String familyAddr;

	@Column(name="FAMILY_ADVERSE_RECORDS")
	private String familyAdverseRecords;

	@Column(name="FAMILY_ANN_INC_SCOPE")
	private String familyAnnIncScope;

	@Column(name="FAMILY_ANNUAL_PAY_SCOPE")
	private String familyAnnualPayScope;

	@Column(name="FAMILY_ASSETS_INFO")
	private String familyAssetsInfo;

	@Column(name="FAMILY_DEBT_SCOPE")
	private String familyDebtScope;

	@Column(name="FMY_JITSURYOKU")
	private String fmyJitsuryoku;

	@Column(name="HAS_HOME_CAR")
	private String hasHomeCar;

	@Column(name="HOME_TEL")
	private String homeTel;

	@Column(name="HOUSE_HOLDER_NAME")
	private String houseHolderName;

	@Column(name="HOUSE_STAT")
	private String houseStat;

	@Column(name="IS_CREDIT_FAMILY")
	private String isCreditFamily;

	@Column(name="IS_HARMONY")
	private String isHarmony;

	@Column(name="IS_HOUSE_HOLDER")
	private String isHouseHolder;

	@Column(name="LABOR_POP_NUM")
	private BigDecimal laborPopNum;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="MAIN_INCOME_SOURCE")
	private String mainIncomeSource;

	private BigDecimal population;

	@Column(name="PROVIDE_POP_NUM")
	private BigDecimal providePopNum;

	private String remark;

	@Column(name="RESIDENCE_STAT")
	private String residenceStat;

	@Column(name="SUPPLY_POP_NUM")
	private BigDecimal supplyPopNum;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

    public AcrmFCiPerFamily() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusiAndScale() {
		return this.busiAndScale;
	}

	public void setBusiAndScale(String busiAndScale) {
		this.busiAndScale = busiAndScale;
	}

	public BigDecimal getChildrenNum() {
		return this.childrenNum;
	}

	public void setChildrenNum(BigDecimal childrenNum) {
		this.childrenNum = childrenNum;
	}

	public BigDecimal getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getCreditInfo() {
		return this.creditInfo;
	}

	public void setCreditInfo(String creditInfo) {
		this.creditInfo = creditInfo;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getDebtState() {
		return this.debtState;
	}

	public void setDebtState(String debtState) {
		this.debtState = debtState;
	}

	public String getFamilyAddr() {
		return this.familyAddr;
	}

	public void setFamilyAddr(String familyAddr) {
		this.familyAddr = familyAddr;
	}

	public String getFamilyAdverseRecords() {
		return this.familyAdverseRecords;
	}

	public void setFamilyAdverseRecords(String familyAdverseRecords) {
		this.familyAdverseRecords = familyAdverseRecords;
	}

	public String getFamilyAnnIncScope() {
		return this.familyAnnIncScope;
	}

	public void setFamilyAnnIncScope(String familyAnnIncScope) {
		this.familyAnnIncScope = familyAnnIncScope;
	}

	public String getFamilyAnnualPayScope() {
		return this.familyAnnualPayScope;
	}

	public void setFamilyAnnualPayScope(String familyAnnualPayScope) {
		this.familyAnnualPayScope = familyAnnualPayScope;
	}

	public String getFamilyAssetsInfo() {
		return this.familyAssetsInfo;
	}

	public void setFamilyAssetsInfo(String familyAssetsInfo) {
		this.familyAssetsInfo = familyAssetsInfo;
	}

	public String getFamilyDebtScope() {
		return this.familyDebtScope;
	}

	public void setFamilyDebtScope(String familyDebtScope) {
		this.familyDebtScope = familyDebtScope;
	}

	public String getFmyJitsuryoku() {
		return this.fmyJitsuryoku;
	}

	public void setFmyJitsuryoku(String fmyJitsuryoku) {
		this.fmyJitsuryoku = fmyJitsuryoku;
	}

	public String getHasHomeCar() {
		return this.hasHomeCar;
	}

	public void setHasHomeCar(String hasHomeCar) {
		this.hasHomeCar = hasHomeCar;
	}

	public String getHomeTel() {
		return this.homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getHouseHolderName() {
		return this.houseHolderName;
	}

	public void setHouseHolderName(String houseHolderName) {
		this.houseHolderName = houseHolderName;
	}

	public String getHouseStat() {
		return this.houseStat;
	}

	public void setHouseStat(String houseStat) {
		this.houseStat = houseStat;
	}

	public String getIsCreditFamily() {
		return this.isCreditFamily;
	}

	public void setIsCreditFamily(String isCreditFamily) {
		this.isCreditFamily = isCreditFamily;
	}

	public String getIsHarmony() {
		return this.isHarmony;
	}

	public void setIsHarmony(String isHarmony) {
		this.isHarmony = isHarmony;
	}

	public String getIsHouseHolder() {
		return this.isHouseHolder;
	}

	public void setIsHouseHolder(String isHouseHolder) {
		this.isHouseHolder = isHouseHolder;
	}

	public BigDecimal getLaborPopNum() {
		return this.laborPopNum;
	}

	public void setLaborPopNum(BigDecimal laborPopNum) {
		this.laborPopNum = laborPopNum;
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

	public String getMainIncomeSource() {
		return this.mainIncomeSource;
	}

	public void setMainIncomeSource(String mainIncomeSource) {
		this.mainIncomeSource = mainIncomeSource;
	}

	public BigDecimal getPopulation() {
		return this.population;
	}

	public void setPopulation(BigDecimal population) {
		this.population = population;
	}

	public BigDecimal getProvidePopNum() {
		return this.providePopNum;
	}

	public void setProvidePopNum(BigDecimal providePopNum) {
		this.providePopNum = providePopNum;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResidenceStat() {
		return this.residenceStat;
	}

	public void setResidenceStat(String residenceStat) {
		this.residenceStat = residenceStat;
	}

	public BigDecimal getSupplyPopNum() {
		return this.supplyPopNum;
	}

	public void setSupplyPopNum(BigDecimal supplyPopNum) {
		this.supplyPopNum = supplyPopNum;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}