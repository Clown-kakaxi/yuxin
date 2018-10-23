package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiCustomerId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiCustomerId implements java.io.Serializable {

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
	private String cusBankRel;
	private String cusCorpRel;
	private String infoPer;
	private Date createDate;
	private String createTime;
	private String createBranchNo;
	private String createTellerNo;
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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiCustomerId() {
	}

	/** minimal constructor */
	public HMCiCustomerId(String custId, Timestamp hisOperTime) {
		this.custId = custId;
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiCustomerId(String custId, String coreNo, String custType,
			String identType, String identNo, String custName, String postName,
			String shortName, String enName, String enShortName,
			String custStat, String jobType, String industType,
			String riskNationCode, String potentialFlag, String ebankFlag,
			String realFlag, String inoutFlag, String blankFlag,
			String vipFlag, String mergeFlag, String linkmanName,
			String linkmanTel, Date firstLoanDate, String loanCustMgr,
			String loanMainBrId, String arCustFlag, String arCustType,
			String sourceChannel, String recommender, String loanCustRank,
			String loanCustStat, String cusBankRel, String cusCorpRel,
			String infoPer, Date createDate, String createTime,
			String createBranchNo, String createTellerNo, String custLevel,
			String riskLevel, Date riskValidDate, String creditLevel,
			Double currentAum, Double totalDebt, BigDecimal faxtradeNorecNum,
			String lastUpdateSys, String lastUpdateUser, Timestamp lastUpdateTm,
			String txSeqNo, String hisOperSys, String hisOperType,
			Timestamp hisOperTime, String hisDataDate) {
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
		this.cusBankRel = cusBankRel;
		this.cusCorpRel = cusCorpRel;
		this.infoPer = infoPer;
		this.createDate = createDate;
		this.createTime = createTime;
		this.createBranchNo = createBranchNo;
		this.createTellerNo = createTellerNo;
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "CUST_ID", nullable = false, length = 20)
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
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
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

	@Temporal(TemporalType.DATE)
	@Column(name = "RISK_VALID_DATE", length = 7)
	public Date getRiskValidDate() {
		return this.riskValidDate;
	}

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

	@Column(name = "HIS_OPER_TIME", nullable = false)
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
		if (!(other instanceof HMCiCustomerId))
			return false;
		HMCiCustomerId castOther = (HMCiCustomerId) other;

		return ((this.getCustId() == castOther.getCustId()) || (this
				.getCustId() != null
				&& castOther.getCustId() != null && this.getCustId().equals(
				castOther.getCustId())))
				&& ((this.getCoreNo() == castOther.getCoreNo()) || (this
						.getCoreNo() != null
						&& castOther.getCoreNo() != null && this.getCoreNo()
						.equals(castOther.getCoreNo())))
				&& ((this.getCustType() == castOther.getCustType()) || (this
						.getCustType() != null
						&& castOther.getCustType() != null && this
						.getCustType().equals(castOther.getCustType())))
				&& ((this.getIdentType() == castOther.getIdentType()) || (this
						.getIdentType() != null
						&& castOther.getIdentType() != null && this
						.getIdentType().equals(castOther.getIdentType())))
				&& ((this.getIdentNo() == castOther.getIdentNo()) || (this
						.getIdentNo() != null
						&& castOther.getIdentNo() != null && this.getIdentNo()
						.equals(castOther.getIdentNo())))
				&& ((this.getCustName() == castOther.getCustName()) || (this
						.getCustName() != null
						&& castOther.getCustName() != null && this
						.getCustName().equals(castOther.getCustName())))
				&& ((this.getPostName() == castOther.getPostName()) || (this
						.getPostName() != null
						&& castOther.getPostName() != null && this
						.getPostName().equals(castOther.getPostName())))
				&& ((this.getShortName() == castOther.getShortName()) || (this
						.getShortName() != null
						&& castOther.getShortName() != null && this
						.getShortName().equals(castOther.getShortName())))
				&& ((this.getEnName() == castOther.getEnName()) || (this
						.getEnName() != null
						&& castOther.getEnName() != null && this.getEnName()
						.equals(castOther.getEnName())))
				&& ((this.getEnShortName() == castOther.getEnShortName()) || (this
						.getEnShortName() != null
						&& castOther.getEnShortName() != null && this
						.getEnShortName().equals(castOther.getEnShortName())))
				&& ((this.getCustStat() == castOther.getCustStat()) || (this
						.getCustStat() != null
						&& castOther.getCustStat() != null && this
						.getCustStat().equals(castOther.getCustStat())))
				&& ((this.getJobType() == castOther.getJobType()) || (this
						.getJobType() != null
						&& castOther.getJobType() != null && this.getJobType()
						.equals(castOther.getJobType())))
				&& ((this.getIndustType() == castOther.getIndustType()) || (this
						.getIndustType() != null
						&& castOther.getIndustType() != null && this
						.getIndustType().equals(castOther.getIndustType())))
				&& ((this.getRiskNationCode() == castOther.getRiskNationCode()) || (this
						.getRiskNationCode() != null
						&& castOther.getRiskNationCode() != null && this
						.getRiskNationCode().equals(
								castOther.getRiskNationCode())))
				&& ((this.getPotentialFlag() == castOther.getPotentialFlag()) || (this
						.getPotentialFlag() != null
						&& castOther.getPotentialFlag() != null && this
						.getPotentialFlag()
						.equals(castOther.getPotentialFlag())))
				&& ((this.getEbankFlag() == castOther.getEbankFlag()) || (this
						.getEbankFlag() != null
						&& castOther.getEbankFlag() != null && this
						.getEbankFlag().equals(castOther.getEbankFlag())))
				&& ((this.getRealFlag() == castOther.getRealFlag()) || (this
						.getRealFlag() != null
						&& castOther.getRealFlag() != null && this
						.getRealFlag().equals(castOther.getRealFlag())))
				&& ((this.getInoutFlag() == castOther.getInoutFlag()) || (this
						.getInoutFlag() != null
						&& castOther.getInoutFlag() != null && this
						.getInoutFlag().equals(castOther.getInoutFlag())))
				&& ((this.getBlankFlag() == castOther.getBlankFlag()) || (this
						.getBlankFlag() != null
						&& castOther.getBlankFlag() != null && this
						.getBlankFlag().equals(castOther.getBlankFlag())))
				&& ((this.getVipFlag() == castOther.getVipFlag()) || (this
						.getVipFlag() != null
						&& castOther.getVipFlag() != null && this.getVipFlag()
						.equals(castOther.getVipFlag())))
				&& ((this.getMergeFlag() == castOther.getMergeFlag()) || (this
						.getMergeFlag() != null
						&& castOther.getMergeFlag() != null && this
						.getMergeFlag().equals(castOther.getMergeFlag())))
				&& ((this.getLinkmanName() == castOther.getLinkmanName()) || (this
						.getLinkmanName() != null
						&& castOther.getLinkmanName() != null && this
						.getLinkmanName().equals(castOther.getLinkmanName())))
				&& ((this.getLinkmanTel() == castOther.getLinkmanTel()) || (this
						.getLinkmanTel() != null
						&& castOther.getLinkmanTel() != null && this
						.getLinkmanTel().equals(castOther.getLinkmanTel())))
				&& ((this.getFirstLoanDate() == castOther.getFirstLoanDate()) || (this
						.getFirstLoanDate() != null
						&& castOther.getFirstLoanDate() != null && this
						.getFirstLoanDate()
						.equals(castOther.getFirstLoanDate())))
				&& ((this.getLoanCustMgr() == castOther.getLoanCustMgr()) || (this
						.getLoanCustMgr() != null
						&& castOther.getLoanCustMgr() != null && this
						.getLoanCustMgr().equals(castOther.getLoanCustMgr())))
				&& ((this.getLoanMainBrId() == castOther.getLoanMainBrId()) || (this
						.getLoanMainBrId() != null
						&& castOther.getLoanMainBrId() != null && this
						.getLoanMainBrId().equals(castOther.getLoanMainBrId())))
				&& ((this.getArCustFlag() == castOther.getArCustFlag()) || (this
						.getArCustFlag() != null
						&& castOther.getArCustFlag() != null && this
						.getArCustFlag().equals(castOther.getArCustFlag())))
				&& ((this.getArCustType() == castOther.getArCustType()) || (this
						.getArCustType() != null
						&& castOther.getArCustType() != null && this
						.getArCustType().equals(castOther.getArCustType())))
				&& ((this.getSourceChannel() == castOther.getSourceChannel()) || (this
						.getSourceChannel() != null
						&& castOther.getSourceChannel() != null && this
						.getSourceChannel()
						.equals(castOther.getSourceChannel())))
				&& ((this.getRecommender() == castOther.getRecommender()) || (this
						.getRecommender() != null
						&& castOther.getRecommender() != null && this
						.getRecommender().equals(castOther.getRecommender())))
				&& ((this.getLoanCustRank() == castOther.getLoanCustRank()) || (this
						.getLoanCustRank() != null
						&& castOther.getLoanCustRank() != null && this
						.getLoanCustRank().equals(castOther.getLoanCustRank())))
				&& ((this.getLoanCustStat() == castOther.getLoanCustStat()) || (this
						.getLoanCustStat() != null
						&& castOther.getLoanCustStat() != null && this
						.getLoanCustStat().equals(castOther.getLoanCustStat())))
				&& ((this.getCusBankRel() == castOther.getCusBankRel()) || (this
						.getCusBankRel() != null
						&& castOther.getCusBankRel() != null && this
						.getCusBankRel().equals(castOther.getCusBankRel())))
				&& ((this.getCusCorpRel() == castOther.getCusCorpRel()) || (this
						.getCusCorpRel() != null
						&& castOther.getCusCorpRel() != null && this
						.getCusCorpRel().equals(castOther.getCusCorpRel())))
				&& ((this.getInfoPer() == castOther.getInfoPer()) || (this
						.getInfoPer() != null
						&& castOther.getInfoPer() != null && this.getInfoPer()
						.equals(castOther.getInfoPer())))
				&& ((this.getCreateDate() == castOther.getCreateDate()) || (this
						.getCreateDate() != null
						&& castOther.getCreateDate() != null && this
						.getCreateDate().equals(castOther.getCreateDate())))
				&& ((this.getCreateTime() == castOther.getCreateTime()) || (this
						.getCreateTime() != null
						&& castOther.getCreateTime() != null && this
						.getCreateTime().equals(castOther.getCreateTime())))
				&& ((this.getCreateBranchNo() == castOther.getCreateBranchNo()) || (this
						.getCreateBranchNo() != null
						&& castOther.getCreateBranchNo() != null && this
						.getCreateBranchNo().equals(
								castOther.getCreateBranchNo())))
				&& ((this.getCreateTellerNo() == castOther.getCreateTellerNo()) || (this
						.getCreateTellerNo() != null
						&& castOther.getCreateTellerNo() != null && this
						.getCreateTellerNo().equals(
								castOther.getCreateTellerNo())))
				&& ((this.getCustLevel() == castOther.getCustLevel()) || (this
						.getCustLevel() != null
						&& castOther.getCustLevel() != null && this
						.getCustLevel().equals(castOther.getCustLevel())))
				&& ((this.getRiskLevel() == castOther.getRiskLevel()) || (this
						.getRiskLevel() != null
						&& castOther.getRiskLevel() != null && this
						.getRiskLevel().equals(castOther.getRiskLevel())))
				&& ((this.getRiskValidDate() == castOther.getRiskValidDate()) || (this
						.getRiskValidDate() != null
						&& castOther.getRiskValidDate() != null && this
						.getRiskValidDate()
						.equals(castOther.getRiskValidDate())))
				&& ((this.getCreditLevel() == castOther.getCreditLevel()) || (this
						.getCreditLevel() != null
						&& castOther.getCreditLevel() != null && this
						.getCreditLevel().equals(castOther.getCreditLevel())))
				&& ((this.getCurrentAum() == castOther.getCurrentAum()) || (this
						.getCurrentAum() != null
						&& castOther.getCurrentAum() != null && this
						.getCurrentAum().equals(castOther.getCurrentAum())))
				&& ((this.getTotalDebt() == castOther.getTotalDebt()) || (this
						.getTotalDebt() != null
						&& castOther.getTotalDebt() != null && this
						.getTotalDebt().equals(castOther.getTotalDebt())))
				&& ((this.getFaxtradeNorecNum() == castOther
						.getFaxtradeNorecNum()) || (this.getFaxtradeNorecNum() != null
						&& castOther.getFaxtradeNorecNum() != null && this
						.getFaxtradeNorecNum().equals(
								castOther.getFaxtradeNorecNum())))
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
		result = 37 * result
				+ (getCoreNo() == null ? 0 : this.getCoreNo().hashCode());
		result = 37 * result
				+ (getCustType() == null ? 0 : this.getCustType().hashCode());
		result = 37 * result
				+ (getIdentType() == null ? 0 : this.getIdentType().hashCode());
		result = 37 * result
				+ (getIdentNo() == null ? 0 : this.getIdentNo().hashCode());
		result = 37 * result
				+ (getCustName() == null ? 0 : this.getCustName().hashCode());
		result = 37 * result
				+ (getPostName() == null ? 0 : this.getPostName().hashCode());
		result = 37 * result
				+ (getShortName() == null ? 0 : this.getShortName().hashCode());
		result = 37 * result
				+ (getEnName() == null ? 0 : this.getEnName().hashCode());
		result = 37
				* result
				+ (getEnShortName() == null ? 0 : this.getEnShortName()
						.hashCode());
		result = 37 * result
				+ (getCustStat() == null ? 0 : this.getCustStat().hashCode());
		result = 37 * result
				+ (getJobType() == null ? 0 : this.getJobType().hashCode());
		result = 37
				* result
				+ (getIndustType() == null ? 0 : this.getIndustType()
						.hashCode());
		result = 37
				* result
				+ (getRiskNationCode() == null ? 0 : this.getRiskNationCode()
						.hashCode());
		result = 37
				* result
				+ (getPotentialFlag() == null ? 0 : this.getPotentialFlag()
						.hashCode());
		result = 37 * result
				+ (getEbankFlag() == null ? 0 : this.getEbankFlag().hashCode());
		result = 37 * result
				+ (getRealFlag() == null ? 0 : this.getRealFlag().hashCode());
		result = 37 * result
				+ (getInoutFlag() == null ? 0 : this.getInoutFlag().hashCode());
		result = 37 * result
				+ (getBlankFlag() == null ? 0 : this.getBlankFlag().hashCode());
		result = 37 * result
				+ (getVipFlag() == null ? 0 : this.getVipFlag().hashCode());
		result = 37 * result
				+ (getMergeFlag() == null ? 0 : this.getMergeFlag().hashCode());
		result = 37
				* result
				+ (getLinkmanName() == null ? 0 : this.getLinkmanName()
						.hashCode());
		result = 37
				* result
				+ (getLinkmanTel() == null ? 0 : this.getLinkmanTel()
						.hashCode());
		result = 37
				* result
				+ (getFirstLoanDate() == null ? 0 : this.getFirstLoanDate()
						.hashCode());
		result = 37
				* result
				+ (getLoanCustMgr() == null ? 0 : this.getLoanCustMgr()
						.hashCode());
		result = 37
				* result
				+ (getLoanMainBrId() == null ? 0 : this.getLoanMainBrId()
						.hashCode());
		result = 37
				* result
				+ (getArCustFlag() == null ? 0 : this.getArCustFlag()
						.hashCode());
		result = 37
				* result
				+ (getArCustType() == null ? 0 : this.getArCustType()
						.hashCode());
		result = 37
				* result
				+ (getSourceChannel() == null ? 0 : this.getSourceChannel()
						.hashCode());
		result = 37
				* result
				+ (getRecommender() == null ? 0 : this.getRecommender()
						.hashCode());
		result = 37
				* result
				+ (getLoanCustRank() == null ? 0 : this.getLoanCustRank()
						.hashCode());
		result = 37
				* result
				+ (getLoanCustStat() == null ? 0 : this.getLoanCustStat()
						.hashCode());
		result = 37
				* result
				+ (getCusBankRel() == null ? 0 : this.getCusBankRel()
						.hashCode());
		result = 37
				* result
				+ (getCusCorpRel() == null ? 0 : this.getCusCorpRel()
						.hashCode());
		result = 37 * result
				+ (getInfoPer() == null ? 0 : this.getInfoPer().hashCode());
		result = 37
				* result
				+ (getCreateDate() == null ? 0 : this.getCreateDate()
						.hashCode());
		result = 37
				* result
				+ (getCreateTime() == null ? 0 : this.getCreateTime()
						.hashCode());
		result = 37
				* result
				+ (getCreateBranchNo() == null ? 0 : this.getCreateBranchNo()
						.hashCode());
		result = 37
				* result
				+ (getCreateTellerNo() == null ? 0 : this.getCreateTellerNo()
						.hashCode());
		result = 37 * result
				+ (getCustLevel() == null ? 0 : this.getCustLevel().hashCode());
		result = 37 * result
				+ (getRiskLevel() == null ? 0 : this.getRiskLevel().hashCode());
		result = 37
				* result
				+ (getRiskValidDate() == null ? 0 : this.getRiskValidDate()
						.hashCode());
		result = 37
				* result
				+ (getCreditLevel() == null ? 0 : this.getCreditLevel()
						.hashCode());
		result = 37
				* result
				+ (getCurrentAum() == null ? 0 : this.getCurrentAum()
						.hashCode());
		result = 37 * result
				+ (getTotalDebt() == null ? 0 : this.getTotalDebt().hashCode());
		result = 37
				* result
				+ (getFaxtradeNorecNum() == null ? 0 : this
						.getFaxtradeNorecNum().hashCode());
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