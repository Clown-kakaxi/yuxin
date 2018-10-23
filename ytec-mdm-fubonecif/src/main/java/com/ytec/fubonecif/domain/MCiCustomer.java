package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiCustomer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_CUSTOMER")
public class MCiCustomer implements java.io.Serializable {

	// Fields

	private String custId;
	private String coreNo;
	private String custType;
	private String identType;
	private String identNo;
	private String custName;
	private String postName;
	private String shortName;
	private String enName;
	private String enShortName;
	private String custStat;
	private String jobType;
	private String industType;
	private String riskNationCode;
	private String potentialFlag;
	private String ebankFlag;
	private String realFlag;
	private String inoutFlag;
	private String blankFlag;
	private String vipFlag;
	private String mergeFlag;
	private String custnmIdentModifiedFlag;
	private String linkmanName;
	private String linkmanTel;
	private Date firstLoanDate;
	private String loanCustMgr;
	private String loanMainBrId;
	private String arCustFlag;
	private String arCustType;
	private String sourceChannel;
	private String recommender;
	private String loanCustRank;
	private String loanCustStat;
	private String staffin;
	private String swift;
	private String profctr;
	private String cusBankRel;
	private String cusCorpRel;
	private String infoPer;
	private Date createDate;
	private Timestamp createTime;
	private String createBranchNo;
	private String createTellerNo;
	private Date createDateLn;
	private Timestamp createTimeLn;
	private String createBranchNoLn;
	private String createTellerNoLn;
	private String custLevel;
	private String riskLevel;
	private Date riskValidDate;
	private String creditLevel;
	private Double currentAum;
	private Double totalDebt;
	private BigDecimal faxtradeNorecNum;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;
	//add by liuming 
	private String loanCustId;

	// Constructors

	/** default constructor */
	public MCiCustomer() {
	}

	/** minimal constructor */
	public MCiCustomer(String custId, String lastUpdateSys) {
		this.custId = custId;
		this.lastUpdateSys = lastUpdateSys;
	}

	/** full constructor */
	public MCiCustomer(String custId, String coreNo, String custType,
			String identType, String identNo, String custName, String postName,
			String shortName, String enName, String enShortName,
			String custStat, String jobType, String industType,
			String riskNationCode, String potentialFlag, String ebankFlag,
			String realFlag, String inoutFlag, String blankFlag,
			String vipFlag, String mergeFlag, String custnmIdentModifiedFlag,
			String linkmanName, String linkmanTel, Date firstLoanDate,
			String loanCustMgr, String loanMainBrId, String arCustFlag,
			String arCustType, String sourceChannel, String recommender,
			String loanCustRank, String loanCustStat, String staffin,
			String swift, String profctr, String cusBankRel, String cusCorpRel,
			String infoPer, Date createDate, Timestamp createTime,
			String createBranchNo, String createTellerNo,
			Date createDateLn, Timestamp createTimeLn,
			String createBranchNoLn, String createTellerNoLn, String custLevel,
			String riskLevel, Date riskValidDate, String creditLevel,
			Double currentAum, Double totalDebt, BigDecimal faxtradeNorecNum,
			String lastUpdateSys, String lastUpdateUser, Timestamp lastUpdateTm,
			String txSeqNo
			//add by liuming 20170629
			,String loanCustId) {
		this.custId = custId;
		this.coreNo = coreNo;
		this.custType = custType;
		this.identType = identType;
		this.identNo = identNo;
		this.custName = custName;
		this.postName = postName;
		this.shortName = shortName;
		this.enName = enName;
		this.enShortName = enShortName;
		this.custStat = custStat;
		this.jobType = jobType;
		this.industType = industType;
		this.riskNationCode = riskNationCode;
		this.potentialFlag = potentialFlag;
		this.ebankFlag = ebankFlag;
		this.realFlag = realFlag;
		this.inoutFlag = inoutFlag;
		this.blankFlag = blankFlag;
		this.vipFlag = vipFlag;
		this.mergeFlag = mergeFlag;
		this.custnmIdentModifiedFlag = custnmIdentModifiedFlag;
		this.linkmanName = linkmanName;
		this.linkmanTel = linkmanTel;
		this.firstLoanDate = firstLoanDate;
		this.loanCustMgr = loanCustMgr;
		this.loanMainBrId = loanMainBrId;
		this.arCustFlag = arCustFlag;
		this.arCustType = arCustType;
		this.sourceChannel = sourceChannel;
		this.recommender = recommender;
		this.loanCustRank = loanCustRank;
		this.loanCustStat = loanCustStat;
		this.staffin = staffin;
		this.swift = swift;
		this.profctr = profctr;
		this.cusBankRel = cusBankRel;
		this.cusCorpRel = cusCorpRel;
		this.infoPer = infoPer;
		this.createDate = createDate;
		this.createTime = createTime;
		this.createBranchNo = createBranchNo;
		this.createTellerNo = createTellerNo;
		this.createDateLn = createDateLn;
		this.createTimeLn = createTimeLn;
		this.createBranchNoLn = createBranchNoLn;
		this.createTellerNoLn = createTellerNoLn;
		this.custLevel = custLevel;
		this.riskLevel = riskLevel;
		this.riskValidDate = riskValidDate;
		this.creditLevel = creditLevel;
		this.currentAum = currentAum;
		this.totalDebt = totalDebt;
		this.faxtradeNorecNum = faxtradeNorecNum;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
		//add by liuming 20170629
		this.loanCustId = loanCustId;
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

	@Column(name = "CORE_NO", length = 20)
	public String getCoreNo() {
		return this.coreNo;
	}

	public void setCoreNo(String coreNo) {
		this.coreNo = coreNo;
	}

	@Column(name = "CUST_TYPE", length = 20)
	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	@Column(name = "IDENT_TYPE", length = 20)
	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	@Column(name = "IDENT_NO", length = 40)
	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	@Column(name = "CUST_NAME", length = 80)
	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Column(name = "POST_NAME", length = 70)
	public String getPostName() {
		return this.postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	@Column(name = "SHORT_NAME", length = 80)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "EN_NAME", length = 100)
	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Column(name = "EN_SHORT_NAME", length = 80)
	public String getEnShortName() {
		return this.enShortName;
	}

	public void setEnShortName(String enShortName) {
		this.enShortName = enShortName;
	}

	@Column(name = "CUST_STAT", length = 20)
	public String getCustStat() {
		return this.custStat;
	}

	public void setCustStat(String custStat) {
		this.custStat = custStat;
	}

	@Column(name = "JOB_TYPE", length = 20)
	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	@Column(name = "INDUST_TYPE", length = 20)
	public String getIndustType() {
		return this.industType;
	}

	public void setIndustType(String industType) {
		this.industType = industType;
	}

	@Column(name = "RISK_NATION_CODE", length = 20)
	public String getRiskNationCode() {
		return this.riskNationCode;
	}

	public void setRiskNationCode(String riskNationCode) {
		this.riskNationCode = riskNationCode;
	}

	@Column(name = "POTENTIAL_FLAG", length = 1)
	public String getPotentialFlag() {
		return this.potentialFlag;
	}

	public void setPotentialFlag(String potentialFlag) {
		this.potentialFlag = potentialFlag;
	}

	@Column(name = "EBANK_FLAG", length = 1)
	public String getEbankFlag() {
		return this.ebankFlag;
	}

	public void setEbankFlag(String ebankFlag) {
		this.ebankFlag = ebankFlag;
	}

	@Column(name = "REAL_FLAG", length = 20)
	public String getRealFlag() {
		return this.realFlag;
	}

	public void setRealFlag(String realFlag) {
		this.realFlag = realFlag;
	}

	@Column(name = "INOUT_FLAG", length = 20)
	public String getInoutFlag() {
		return this.inoutFlag;
	}

	public void setInoutFlag(String inoutFlag) {
		this.inoutFlag = inoutFlag;
	}

	@Column(name = "BLANK_FLAG", length = 20)
	public String getBlankFlag() {
		return this.blankFlag;
	}

	public void setBlankFlag(String blankFlag) {
		this.blankFlag = blankFlag;
	}

	@Column(name = "VIP_FLAG", length = 1)
	public String getVipFlag() {
		return this.vipFlag;
	}

	public void setVipFlag(String vipFlag) {
		this.vipFlag = vipFlag;
	}

	@Column(name = "MERGE_FLAG", length = 20)
	public String getMergeFlag() {
		return this.mergeFlag;
	}

	public void setMergeFlag(String mergeFlag) {
		this.mergeFlag = mergeFlag;
	}

	@Column(name = "CUSTNM_IDENT_MODIFIED_FLAG", length = 1)
	public String getCustnmIdentModifiedFlag() {
		return this.custnmIdentModifiedFlag;
	}

	public void setCustnmIdentModifiedFlag(String custnmIdentModifiedFlag) {
		this.custnmIdentModifiedFlag = custnmIdentModifiedFlag;
	}

	@Column(name = "LINKMAN_NAME", length = 80)
	public String getLinkmanName() {
		return this.linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	@Column(name = "LINKMAN_TEL", length = 20)
	public String getLinkmanTel() {
		return this.linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "FIRST_LOAN_DATE", length = 7)
	public Date getFirstLoanDate() {
		return this.firstLoanDate;
	}

	public void setFirstLoanDate(Date firstLoanDate) {
		this.firstLoanDate = firstLoanDate;
	}

	@Column(name = "LOAN_CUST_MGR", length = 20)
	public String getLoanCustMgr() {
		return this.loanCustMgr;
	}

	public void setLoanCustMgr(String loanCustMgr) {
		this.loanCustMgr = loanCustMgr;
	}

	@Column(name = "LOAN_MAIN_BR_ID", length = 20)
	public String getLoanMainBrId() {
		return this.loanMainBrId;
	}

	public void setLoanMainBrId(String loanMainBrId) {
		this.loanMainBrId = loanMainBrId;
	}

	@Column(name = "AR_CUST_FLAG", length = 1)
	public String getArCustFlag() {
		return this.arCustFlag;
	}

	public void setArCustFlag(String arCustFlag) {
		this.arCustFlag = arCustFlag;
	}

	@Column(name = "AR_CUST_TYPE", length = 20)
	public String getArCustType() {
		return this.arCustType;
	}

	public void setArCustType(String arCustType) {
		this.arCustType = arCustType;
	}

	@Column(name = "SOURCE_CHANNEL", length = 20)
	public String getSourceChannel() {
		return this.sourceChannel;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	@Column(name = "RECOMMENDER", length = 20)
	public String getRecommender() {
		return this.recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}

	@Column(name = "LOAN_CUST_RANK", length = 20)
	public String getLoanCustRank() {
		return this.loanCustRank;
	}

	public void setLoanCustRank(String loanCustRank) {
		this.loanCustRank = loanCustRank;
	}

	@Column(name = "LOAN_CUST_STAT", length = 20)
	public String getLoanCustStat() {
		return this.loanCustStat;
	}

	public void setLoanCustStat(String loanCustStat) {
		this.loanCustStat = loanCustStat;
	}

	@Column(name = "STAFFIN", length = 20)
	public String getStaffin() {
		return this.staffin;
	}

	public void setStaffin(String staffin) {
		this.staffin = staffin;
	}

	@Column(name = "SWIFT", length = 20)
	public String getSwift() {
		return this.swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
	}

	@Column(name = "PROFCTR", length = 20)
	public String getProfctr() {
		return this.profctr;
	}

	public void setProfctr(String profctr) {
		this.profctr = profctr;
	}

	@Column(name = "CUS_BANK_REL", length = 20)
	public String getCusBankRel() {
		return this.cusBankRel;
	}

	public void setCusBankRel(String cusBankRel) {
		this.cusBankRel = cusBankRel;
	}

	@Column(name = "CUS_CORP_REL", length = 20)
	public String getCusCorpRel() {
		return this.cusCorpRel;
	}

	public void setCusCorpRel(String cusCorpRel) {
		this.cusCorpRel = cusCorpRel;
	}

	@Column(name = "INFO_PER", length = 20)
	public String getInfoPer() {
		return this.infoPer;
	}

	public void setInfoPer(String infoPer) {
		this.infoPer = infoPer;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATE_TIME")
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_BRANCH_NO", length = 20)
	public String getCreateBranchNo() {
		return this.createBranchNo;
	}

	public void setCreateBranchNo(String createBranchNo) {
		this.createBranchNo = createBranchNo;
	}

	@Column(name = "CREATE_TELLER_NO", length = 20)
	public String getCreateTellerNo() {
		return this.createTellerNo;
	}

	public void setCreateTellerNo(String createTellerNo) {
		this.createTellerNo = createTellerNo;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE_LN", length = 7)
	public Date getCreateDateLn() {
		return this.createDateLn;
	}

	public void setCreateDateLn(Date createDateLn) {
		this.createDateLn = createDateLn;
	}

	@Column(name = "CREATE_TIME_LN")
	public Timestamp getCreateTimeLn() {
		return this.createTimeLn;
	}

	public void setCreateTimeLn(Timestamp createTimeLn) {
		this.createTimeLn = createTimeLn;
	}

	@Column(name = "CREATE_BRANCH_NO_LN", length = 20)
	public String getCreateBranchNoLn() {
		return this.createBranchNoLn;
	}

	public void setCreateBranchNoLn(String createBranchNoLn) {
		this.createBranchNoLn = createBranchNoLn;
	}

	@Column(name = "CREATE_TELLER_NO_LN", length = 20)
	public String getCreateTellerNoLn() {
		return this.createTellerNoLn;
	}

	public void setCreateTellerNoLn(String createTellerNoLn) {
		this.createTellerNoLn = createTellerNoLn;
	}

	@Column(name = "CUST_LEVEL", length = 20)
	public String getCustLevel() {
		return this.custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	@Column(name = "RISK_LEVEL", length = 20)
	public String getRiskLevel() {
		return this.riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	@Column(name = "RISK_VALID_DATE", length = 7)
	public Date getRiskValidDate() {
		return this.riskValidDate;
	}
	@Temporal(TemporalType.DATE)
	public void setRiskValidDate(Date riskValidDate) {
		this.riskValidDate = riskValidDate;
	}

	@Column(name = "CREDIT_LEVEL", length = 20)
	public String getCreditLevel() {
		return this.creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	@Column(name = "CURRENT_AUM", precision = 17)
	public Double getCurrentAum() {
		return this.currentAum;
	}

	public void setCurrentAum(Double currentAum) {
		this.currentAum = currentAum;
	}

	@Column(name = "TOTAL_DEBT", precision = 17)
	public Double getTotalDebt() {
		return this.totalDebt;
	}

	public void setTotalDebt(Double totalDebt) {
		this.totalDebt = totalDebt;
	}

	@Column(name = "FAXTRADE_NOREC_NUM", precision = 22, scale = 0)
	public BigDecimal getFaxtradeNorecNum() {
		return this.faxtradeNorecNum;
	}

	public void setFaxtradeNorecNum(BigDecimal faxtradeNorecNum) {
		this.faxtradeNorecNum = faxtradeNorecNum;
	}

	@Column(name = "LAST_UPDATE_SYS", nullable = false, length = 20)
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

	@Column(name = "LAST_UPDATE_TM")
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
	
	//add by liuming 20170629
	@Column(name = "LOAN_CUST_ID", length = 20)
	public String getLoanCustId() {
		return loanCustId;
	}

	public void setLoanCustId(String loanCustId) {
		this.loanCustId = loanCustId;
	}

}