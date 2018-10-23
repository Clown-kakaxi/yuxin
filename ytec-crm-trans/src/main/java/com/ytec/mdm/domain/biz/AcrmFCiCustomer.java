package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the M_CI_CUSTOMER database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CUSTOMER")
public class AcrmFCiCustomer implements Serializable {
	@Id
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="AR_CUST_FLAG")
	private String arCustFlag;

	@Column(name="AR_CUST_TYPE")
	private String arCustType;

	@Column(name="BLANK_FLAG")
	private String blankFlag;

	@Column(name="CORE_NO")
	private String coreNo;

	@Column(name="CREATE_BRANCH_NO")
	private String createBranchNo;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_TELLER_NO")
	private String createTellerNo;

	@Column(name="CREATE_TIME")
	private Timestamp createTime;

	@Column(name="CREDIT_LEVEL")
	private String creditLevel;

	@Column(name="CURRENT_AUM")
	private BigDecimal currentAum;

	@Column(name="CUS_BANK_REL")
	private String cusBankRel;

	@Column(name="CUS_CORP_REL")
	private String cusCorpRel;

	@Column(name="CUST_LEVEL")
	private String custLevel;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_STAT")
	private String custStat;

	@Column(name="CUST_TYPE")
	private String custType;

	@Column(name="EBANK_FLAG")
	private String ebankFlag;

	@Column(name="EN_NAME")
	private String enName;

	@Column(name="EN_SHORT_NAME")
	private String enShortName;

	@Column(name="FAXTRADE_NOREC_NUM")
	private BigDecimal faxtradeNorecNum;

    @Temporal( TemporalType.DATE)
	@Column(name="FIRST_LOAN_DATE")
	private Date firstLoanDate;

	@Column(name="IDENT_NO")
	private String identNo;

	@Column(name="IDENT_TYPE")
	private String identType;

	@Column(name="INDUST_TYPE")
	private String industType;

	@Column(name="INFO_PER")
	private String infoPer;

	@Column(name="INOUT_FLAG")
	private String inoutFlag;

	@Column(name="JOB_TYPE")
	private String jobType;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="LINKMAN_NAME")
	private String linkmanName;

	@Column(name="LINKMAN_TEL")
	private String linkmanTel;

	@Column(name="LOAN_CUST_MGR")
	private String loanCustMgr;

	@Column(name="LOAN_CUST_RANK")
	private String loanCustRank;

	@Column(name="LOAN_CUST_STAT")
	private String loanCustStat;

	@Column(name="LOAN_MAIN_BR_ID")
	private String loanMainBrId;

	@Column(name="MERGE_FLAG")
	private String mergeFlag;

	@Column(name="POST_NAME")
	private String postName;

	@Column(name="POTENTIAL_FLAG")
	private String potentialFlag;

	@Column(name="REAL_FLAG")
	private String realFlag;

	private String recommender;

	@Column(name="RISK_LEVEL")
	private String riskLevel;

	@Column(name="RISK_NATION_CODE")
	private String riskNationCode;

//    @Temporal( TemporalType.DATE)
//	@Column(name="RISK_VALID_DATE")
//	private Date riskValidDate;

	@Column(name="SHORT_NAME")
	private String shortName;

	@Column(name="SOURCE_CHANNEL")
	private String sourceChannel;

	@Column(name="TOTAL_DEBT")
	private BigDecimal totalDebt;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

	@Column(name="VIP_FLAG")
	private String vipFlag;
	
	//add by liuming 
	@Column(name="loan_cust_id")
	private String loanCustId;

    public AcrmFCiCustomer() {
    }

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getArCustFlag() {
		return this.arCustFlag;
	}

	public void setArCustFlag(String arCustFlag) {
		this.arCustFlag = arCustFlag;
	}

	public String getArCustType() {
		return this.arCustType;
	}

	public void setArCustType(String arCustType) {
		this.arCustType = arCustType;
	}

	public String getBlankFlag() {
		return this.blankFlag;
	}

	public void setBlankFlag(String blankFlag) {
		this.blankFlag = blankFlag;
	}

	public String getCoreNo() {
		return this.coreNo;
	}

	public void setCoreNo(String coreNo) {
		this.coreNo = coreNo;
	}

	public String getCreateBranchNo() {
		return this.createBranchNo;
	}

	public void setCreateBranchNo(String createBranchNo) {
		this.createBranchNo = createBranchNo;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateTellerNo() {
		return this.createTellerNo;
	}

	public void setCreateTellerNo(String createTellerNo) {
		this.createTellerNo = createTellerNo;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreditLevel() {
		return this.creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	public BigDecimal getCurrentAum() {
		return this.currentAum;
	}

	public void setCurrentAum(BigDecimal currentAum) {
		this.currentAum = currentAum;
	}

	public String getCusBankRel() {
		return this.cusBankRel;
	}

	public void setCusBankRel(String cusBankRel) {
		this.cusBankRel = cusBankRel;
	}

	public String getCusCorpRel() {
		return this.cusCorpRel;
	}

	public void setCusCorpRel(String cusCorpRel) {
		this.cusCorpRel = cusCorpRel;
	}

	public String getCustLevel() {
		return this.custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustStat() {
		return this.custStat;
	}

	public void setCustStat(String custStat) {
		this.custStat = custStat;
	}

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getEbankFlag() {
		return this.ebankFlag;
	}

	public void setEbankFlag(String ebankFlag) {
		this.ebankFlag = ebankFlag;
	}

	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getEnShortName() {
		return this.enShortName;
	}

	public void setEnShortName(String enShortName) {
		this.enShortName = enShortName;
	}

	public BigDecimal getFaxtradeNorecNum() {
		return this.faxtradeNorecNum;
	}

	public void setFaxtradeNorecNum(BigDecimal faxtradeNorecNum) {
		this.faxtradeNorecNum = faxtradeNorecNum;
	}

	public Date getFirstLoanDate() {
		return this.firstLoanDate;
	}

	public void setFirstLoanDate(Date firstLoanDate) {
		this.firstLoanDate = firstLoanDate;
	}

	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIndustType() {
		return this.industType;
	}

	public void setIndustType(String industType) {
		this.industType = industType;
	}

	public String getInfoPer() {
		return this.infoPer;
	}

	public void setInfoPer(String infoPer) {
		this.infoPer = infoPer;
	}

	public String getInoutFlag() {
		return this.inoutFlag;
	}

	public void setInoutFlag(String inoutFlag) {
		this.inoutFlag = inoutFlag;
	}

	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
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

	public String getLinkmanName() {
		return this.linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getLinkmanTel() {
		return this.linkmanTel;
	}

	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}

	public String getLoanCustMgr() {
		return this.loanCustMgr;
	}

	public void setLoanCustMgr(String loanCustMgr) {
		this.loanCustMgr = loanCustMgr;
	}

	public String getLoanCustRank() {
		return this.loanCustRank;
	}

	public void setLoanCustRank(String loanCustRank) {
		this.loanCustRank = loanCustRank;
	}

	public String getLoanCustStat() {
		return this.loanCustStat;
	}

	public void setLoanCustStat(String loanCustStat) {
		this.loanCustStat = loanCustStat;
	}

	public String getLoanMainBrId() {
		return this.loanMainBrId;
	}

	public void setLoanMainBrId(String loanMainBrId) {
		this.loanMainBrId = loanMainBrId;
	}

	public String getMergeFlag() {
		return this.mergeFlag;
	}

	public void setMergeFlag(String mergeFlag) {
		this.mergeFlag = mergeFlag;
	}

	public String getPostName() {
		return this.postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPotentialFlag() {
		return this.potentialFlag;
	}

	public void setPotentialFlag(String potentialFlag) {
		this.potentialFlag = potentialFlag;
	}

	public String getRealFlag() {
		return this.realFlag;
	}

	public void setRealFlag(String realFlag) {
		this.realFlag = realFlag;
	}

	public String getRecommender() {
		return this.recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}

	public String getRiskLevel() {
		return this.riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getRiskNationCode() {
		return this.riskNationCode;
	}

	public void setRiskNationCode(String riskNationCode) {
		this.riskNationCode = riskNationCode;
	}

//	public Date getRiskValidDate() {
//		return this.riskValidDate;
//	}
//
//	public void setRiskValidDate(Date riskValidDate) {
//		this.riskValidDate = riskValidDate;
//	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSourceChannel() {
		return this.sourceChannel;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public BigDecimal getTotalDebt() {
		return this.totalDebt;
	}

	public void setTotalDebt(BigDecimal totalDebt) {
		this.totalDebt = totalDebt;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getVipFlag() {
		return this.vipFlag;
	}

	public void setVipFlag(String vipFlag) {
		this.vipFlag = vipFlag;
	}

	public String getLoanCustId() {
		return loanCustId;
	}

	public void setLoanCustId(String loanCustId) {
		this.loanCustId = loanCustId;
	}
	

}