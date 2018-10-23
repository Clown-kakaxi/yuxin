package com.yuchengtech.bcrm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CI_ASSET_BUSI_PROTO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_ASSET_BUSI_PROTO")
public class AcrmFCiAssetBusiProto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_ASSET_BUSI_PROTO_AGREEMENTID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_ASSET_BUSI_PROTO_AGREEMENTID_GENERATOR")
	@Column(name="AGREEMENT_ID",unique=true, nullable=false)
	private Long agreementId;

	@Column(name="ACCEPTED_A_NUMBER")
	private String acceptedANumber;

	@Column(name="ACCT_NO")
	private String acctNo;

    @Temporal( TemporalType.DATE)
	@Column(name="ACTU_WITHDR")
	private Date actuWithdr;

    @Temporal( TemporalType.DATE)
	@Column(name="AGA_PRIC")
	private Date agaPric;

	@Column(name="AGA_PRIC_RAT")
	private BigDecimal agaPricRat;

	@Column(name="AGENCY_BRAN")
	private String agencyBran;

	@Column(name="ASSET_SYS")
	private String assetSys;

    @Temporal( TemporalType.DATE)
	@Column(name="AUTH_DT")
	private Date authDt;

	private BigDecimal bal;

	@Column(name="BANNOTE_FACE_AMT")
	private BigDecimal bannoteFaceAmt;

	@Column(name="BANNOTE_GRNT_AMT")
	private BigDecimal bannoteGrntAmt;

	@Column(name="BANNOTE_GRNT_BAL")
	private BigDecimal bannoteGrntBal;

	@Column(name="BANNOTE_GRNT_PATIO")
	private BigDecimal bannoteGrntPatio;

    @Temporal( TemporalType.DATE)
	@Column(name="BBK_DT")
	private Date bbkDt;

	@Column(name="BEFO_REPORT_FLG")
	private String befoReportFlg;

    @Temporal( TemporalType.DATE)
	@Column(name="BEGIN_DATE")
	private Date beginDate;

	@Column(name="BILL_QTY")
	private Integer billQty;

	@Column(name="BILL_TYP")
	private String billTyp;

	@Column(name="BM_MOT")
	private BigDecimal bmMot;

	@Column(name="BUSI_NO")
	private String busiNo;

	@Column(name="CGT_LINE_FLG")
	private String cgtLineFlg;

	@Column(name="CN_CONT_NO")
	private String cnContNo;

	@Column(name="CONT_AMT")
	private BigDecimal contAmt;

	@Column(name="CONT_NO")
	private String contNo;

    @Temporal( TemporalType.DATE)
	@Column(name="CONT_SIGN_DT")
	private Date contSignDt;

	@Column(name="CONT_STS")
	private String contSts;

	@Column(name="CORE_CONT_NO")
	private String coreContNo;

	@Column(name="CORE_CUST_NAME")
	private String coreCustName;

	@Column(name="CORE_CUST_NO")
	private String coreCustNo;

	private String curr;

	@Column(name="CUST_NO")
	private String custNo;

	@Column(name="DEFUT_MOT")
	private BigDecimal defutMot;

	@Column(name="DPTRPT_FACE_AMT")
	private BigDecimal dptrptFaceAmt;

	@Column(name="DPTRPT_GRNT_AMT")
	private BigDecimal dptrptGrntAmt;

	@Column(name="DPTRPT_GRNT_BAL")
	private BigDecimal dptrptGrntBal;

	@Column(name="DPTRPT_GRNT_PATIO")
	private BigDecimal dptrptGrntPatio;

    @Temporal( TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="ENT_LOAN_TYP")
	private String entLoanTyp;

	@Column(name="FACTOR_TYP")
	private String factorTyp;

	@Column(name="FEE_RATIO")
	private BigDecimal feeRatio;

	@Column(name="FINAL_EAA_AUTHOR")
	private String finalEaaAuthor;

	@Column(name="FINAL_EAA_BRAN")
	private String finalEaaBran;

    @Temporal( TemporalType.DATE)
	@Column(name="FINAL_UPDATE_DATE")
	private Date finalUpdateDate;

	@Column(name="FIRST_DUTY_OFFICER")
	private String firstDutyOfficer;

	@Column(name="FIX_FLOAT_POINTS")
	private BigDecimal fixFloatPoints;

	@Column(name="FLOAT_THAN")
	private BigDecimal floatThan;

	@Column(name="FLT_CYCL")
	private String fltCycl;

	@Column(name="FOREXCH_RATE")
	private BigDecimal forexchRate;

	@Column(name="GIVE_OUT_AMT")
	private BigDecimal giveOutAmt;

	@Column(name="GRNT_TYP")
	private String grntTyp;

	@Column(name="GRNT_TYP2")
	private String grntTyp2;

	@Column(name="GRNT_TYP3")
	private String grntTyp3;

	@Column(name="INTC_CYCL")
	private String intcCycl;

	@Column(name="INTR_CORR_MODE")
	private String intrCorrMode;

	@Column(name="LATE_MOT")
	private BigDecimal lateMot;

	private String lender;

	@Column(name="LOAN_INVEST")
	private String loanInvest;

	@Column(name="LOAN_POL_PRO_CLASS")
	private String loanPolProClass;

	@Column(name="LOAN_QUAL")
	private String loanQual;

	@Column(name="LOAN_TYP")
	private String loanTyp;

	@Column(name="LOAN_USE")
	private String loanUse;

	@Column(name="LOGRT_KIND")
	private String logrtKind;

	@Column(name="LOGRT_TYP")
	private String logrtTyp;

	@Column(name="MANAGE_BRAN")
	private String manageBran;

	@Column(name="MAX_GRNT_FLG")
	private String maxGrntFlg;

	@Column(name="MODE_PAYMENT")
	private String modePayment;

	@Column(name="MON_INTE_RATE")
	private BigDecimal monInteRate;

	@Column(name="MORT_PRO_NUM")
	private String mortProNum;

	@Column(name="PRODUCT_ID")
	private String productId;

    @Temporal( TemporalType.DATE)
	@Column(name="RECORD_DATE")
	private Date recordDate;

	@Column(name="RECOVER_AMT")
	private BigDecimal recoverAmt;

	private String registrant;

	@Column(name="REPAY_CYCL")
	private String repayCycl;

	@Column(name="REPAY_MODE")
	private String repayMode;

	@Column(name="REPAY_ORG")
	private String repayOrg;

	@Column(name="RSK_EXPO_RATIO")
	private BigDecimal rskExpoRatio;

	@Column(name="SDPT_ACCT_NO")
	private String sdptAcctNo;

	@Column(name="SDPT_AMT")
	private BigDecimal sdptAmt;

	@Column(name="SDPT_BAL")
	private BigDecimal sdptBal;

	@Column(name="SDPT_PATIO")
	private BigDecimal sdptPatio;

	@Column(name="SEC_BRAN_EAA_AUTHOR")
	private String secBranEaaAuthor;

	@Column(name="SEC_DUTY_OFFICER")
	private String secDutyOfficer;

	@Column(name="SEC_EAA_BRAN")
	private String secEaaBran;

	@Column(name="SIGN_BANK")
	private String signBank;

	@Column(name="SIGN_FLG")
	private String signFlg;

	@Column(name="SPEC_LOAN_TYE")
	private String specLoanTye;

	@Column(name="STA_BRAN_AUTHOR")
	private String staBranAuthor;

	@Column(name="STA_EAA_BRAN")
	private String staEaaBran;

	@Column(name="STATI_BUSIN_VAR")
	private String statiBusinVar;

	@Column(name="SUPP_LOAN_TYP")
	private String suppLoanTyp;

	@Column(name="SYN_LOAN_SIGN")
	private String synLoanSign;

	@Column(name="SYN_PRO_NUM")
	private String synProNum;

	@Column(name="TAKE_CGT_BAL")
	private BigDecimal takeCgtBal;

	@Column(name="TAKE_CGT_LINE")
	private BigDecimal takeCgtLine;

	@Column(name="TAKE_MAX_GRNT_AMT")
	private BigDecimal takeMaxGrntAmt;

	@Column(name="TAKE_MAX_GRNT_BAL")
	private BigDecimal takeMaxGrntBal;

	@Column(name="THR_BRAN_AUTHOR")
	private String thrBranAuthor;

	@Column(name="THR_EAA_BRAN")
	private String thrEaaBran;

	@Column(name="WEI_AVER_DAY")
	private Integer weiAverDay;

	@Column(name="WEI_AVER_INTR")
	private BigDecimal weiAverIntr;

	@Column(name="WO_AMT")
	private BigDecimal woAmt;

	@Column(name="YN_DISC")
	private String ynDisc;

	@Column(name="YN_FIX_INTR")
	private String ynFixIntr;

	public Long getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(Long agreementId) {
		this.agreementId = agreementId;
	}

	public String getAcceptedANumber() {
		return acceptedANumber;
	}

	public void setAcceptedANumber(String acceptedANumber) {
		this.acceptedANumber = acceptedANumber;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public Date getActuWithdr() {
		return actuWithdr;
	}

	public void setActuWithdr(Date actuWithdr) {
		this.actuWithdr = actuWithdr;
	}

	public Date getAgaPric() {
		return agaPric;
	}

	public void setAgaPric(Date agaPric) {
		this.agaPric = agaPric;
	}

	public BigDecimal getAgaPricRat() {
		return agaPricRat;
	}

	public void setAgaPricRat(BigDecimal agaPricRat) {
		this.agaPricRat = agaPricRat;
	}

	public String getAgencyBran() {
		return agencyBran;
	}

	public void setAgencyBran(String agencyBran) {
		this.agencyBran = agencyBran;
	}

	public String getAssetSys() {
		return assetSys;
	}

	public void setAssetSys(String assetSys) {
		this.assetSys = assetSys;
	}

	public Date getAuthDt() {
		return authDt;
	}

	public void setAuthDt(Date authDt) {
		this.authDt = authDt;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}

	public BigDecimal getBannoteFaceAmt() {
		return bannoteFaceAmt;
	}

	public void setBannoteFaceAmt(BigDecimal bannoteFaceAmt) {
		this.bannoteFaceAmt = bannoteFaceAmt;
	}

	public BigDecimal getBannoteGrntAmt() {
		return bannoteGrntAmt;
	}

	public void setBannoteGrntAmt(BigDecimal bannoteGrntAmt) {
		this.bannoteGrntAmt = bannoteGrntAmt;
	}

	public BigDecimal getBannoteGrntBal() {
		return bannoteGrntBal;
	}

	public void setBannoteGrntBal(BigDecimal bannoteGrntBal) {
		this.bannoteGrntBal = bannoteGrntBal;
	}

	public BigDecimal getBannoteGrntPatio() {
		return bannoteGrntPatio;
	}

	public void setBannoteGrntPatio(BigDecimal bannoteGrntPatio) {
		this.bannoteGrntPatio = bannoteGrntPatio;
	}

	public Date getBbkDt() {
		return bbkDt;
	}

	public void setBbkDt(Date bbkDt) {
		this.bbkDt = bbkDt;
	}

	public String getBefoReportFlg() {
		return befoReportFlg;
	}

	public void setBefoReportFlg(String befoReportFlg) {
		this.befoReportFlg = befoReportFlg;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Integer getBillQty() {
		return billQty;
	}

	public void setBillQty(Integer billQty) {
		this.billQty = billQty;
	}

	public String getBillTyp() {
		return billTyp;
	}

	public void setBillTyp(String billTyp) {
		this.billTyp = billTyp;
	}

	public BigDecimal getBmMot() {
		return bmMot;
	}

	public void setBmMot(BigDecimal bmMot) {
		this.bmMot = bmMot;
	}

	public String getBusiNo() {
		return busiNo;
	}

	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}

	public String getCgtLineFlg() {
		return cgtLineFlg;
	}

	public void setCgtLineFlg(String cgtLineFlg) {
		this.cgtLineFlg = cgtLineFlg;
	}

	public String getCnContNo() {
		return cnContNo;
	}

	public void setCnContNo(String cnContNo) {
		this.cnContNo = cnContNo;
	}

	public BigDecimal getContAmt() {
		return contAmt;
	}

	public void setContAmt(BigDecimal contAmt) {
		this.contAmt = contAmt;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public Date getContSignDt() {
		return contSignDt;
	}

	public void setContSignDt(Date contSignDt) {
		this.contSignDt = contSignDt;
	}

	public String getContSts() {
		return contSts;
	}

	public void setContSts(String contSts) {
		this.contSts = contSts;
	}

	public String getCoreContNo() {
		return coreContNo;
	}

	public void setCoreContNo(String coreContNo) {
		this.coreContNo = coreContNo;
	}

	public String getCoreCustName() {
		return coreCustName;
	}

	public void setCoreCustName(String coreCustName) {
		this.coreCustName = coreCustName;
	}

	public String getCoreCustNo() {
		return coreCustNo;
	}

	public void setCoreCustNo(String coreCustNo) {
		this.coreCustNo = coreCustNo;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public BigDecimal getDefutMot() {
		return defutMot;
	}

	public void setDefutMot(BigDecimal defutMot) {
		this.defutMot = defutMot;
	}

	public BigDecimal getDptrptFaceAmt() {
		return dptrptFaceAmt;
	}

	public void setDptrptFaceAmt(BigDecimal dptrptFaceAmt) {
		this.dptrptFaceAmt = dptrptFaceAmt;
	}

	public BigDecimal getDptrptGrntAmt() {
		return dptrptGrntAmt;
	}

	public void setDptrptGrntAmt(BigDecimal dptrptGrntAmt) {
		this.dptrptGrntAmt = dptrptGrntAmt;
	}

	public BigDecimal getDptrptGrntBal() {
		return dptrptGrntBal;
	}

	public void setDptrptGrntBal(BigDecimal dptrptGrntBal) {
		this.dptrptGrntBal = dptrptGrntBal;
	}

	public BigDecimal getDptrptGrntPatio() {
		return dptrptGrntPatio;
	}

	public void setDptrptGrntPatio(BigDecimal dptrptGrntPatio) {
		this.dptrptGrntPatio = dptrptGrntPatio;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEntLoanTyp() {
		return entLoanTyp;
	}

	public void setEntLoanTyp(String entLoanTyp) {
		this.entLoanTyp = entLoanTyp;
	}

	public String getFactorTyp() {
		return factorTyp;
	}

	public void setFactorTyp(String factorTyp) {
		this.factorTyp = factorTyp;
	}

	public BigDecimal getFeeRatio() {
		return feeRatio;
	}

	public void setFeeRatio(BigDecimal feeRatio) {
		this.feeRatio = feeRatio;
	}

	public String getFinalEaaAuthor() {
		return finalEaaAuthor;
	}

	public void setFinalEaaAuthor(String finalEaaAuthor) {
		this.finalEaaAuthor = finalEaaAuthor;
	}

	public String getFinalEaaBran() {
		return finalEaaBran;
	}

	public void setFinalEaaBran(String finalEaaBran) {
		this.finalEaaBran = finalEaaBran;
	}

	public Date getFinalUpdateDate() {
		return finalUpdateDate;
	}

	public void setFinalUpdateDate(Date finalUpdateDate) {
		this.finalUpdateDate = finalUpdateDate;
	}

	public String getFirstDutyOfficer() {
		return firstDutyOfficer;
	}

	public void setFirstDutyOfficer(String firstDutyOfficer) {
		this.firstDutyOfficer = firstDutyOfficer;
	}

	public BigDecimal getFixFloatPoints() {
		return fixFloatPoints;
	}

	public void setFixFloatPoints(BigDecimal fixFloatPoints) {
		this.fixFloatPoints = fixFloatPoints;
	}

	public BigDecimal getFloatThan() {
		return floatThan;
	}

	public void setFloatThan(BigDecimal floatThan) {
		this.floatThan = floatThan;
	}

	public String getFltCycl() {
		return fltCycl;
	}

	public void setFltCycl(String fltCycl) {
		this.fltCycl = fltCycl;
	}

	public BigDecimal getForexchRate() {
		return forexchRate;
	}

	public void setForexchRate(BigDecimal forexchRate) {
		this.forexchRate = forexchRate;
	}

	public BigDecimal getGiveOutAmt() {
		return giveOutAmt;
	}

	public void setGiveOutAmt(BigDecimal giveOutAmt) {
		this.giveOutAmt = giveOutAmt;
	}

	public String getGrntTyp() {
		return grntTyp;
	}

	public void setGrntTyp(String grntTyp) {
		this.grntTyp = grntTyp;
	}

	public String getGrntTyp2() {
		return grntTyp2;
	}

	public void setGrntTyp2(String grntTyp2) {
		this.grntTyp2 = grntTyp2;
	}

	public String getGrntTyp3() {
		return grntTyp3;
	}

	public void setGrntTyp3(String grntTyp3) {
		this.grntTyp3 = grntTyp3;
	}

	public String getIntcCycl() {
		return intcCycl;
	}

	public void setIntcCycl(String intcCycl) {
		this.intcCycl = intcCycl;
	}

	public String getIntrCorrMode() {
		return intrCorrMode;
	}

	public void setIntrCorrMode(String intrCorrMode) {
		this.intrCorrMode = intrCorrMode;
	}

	public BigDecimal getLateMot() {
		return lateMot;
	}

	public void setLateMot(BigDecimal lateMot) {
		this.lateMot = lateMot;
	}

	public String getLender() {
		return lender;
	}

	public void setLender(String lender) {
		this.lender = lender;
	}

	public String getLoanInvest() {
		return loanInvest;
	}

	public void setLoanInvest(String loanInvest) {
		this.loanInvest = loanInvest;
	}

	public String getLoanPolProClass() {
		return loanPolProClass;
	}

	public void setLoanPolProClass(String loanPolProClass) {
		this.loanPolProClass = loanPolProClass;
	}

	public String getLoanQual() {
		return loanQual;
	}

	public void setLoanQual(String loanQual) {
		this.loanQual = loanQual;
	}

	public String getLoanTyp() {
		return loanTyp;
	}

	public void setLoanTyp(String loanTyp) {
		this.loanTyp = loanTyp;
	}

	public String getLoanUse() {
		return loanUse;
	}

	public void setLoanUse(String loanUse) {
		this.loanUse = loanUse;
	}

	public String getLogrtKind() {
		return logrtKind;
	}

	public void setLogrtKind(String logrtKind) {
		this.logrtKind = logrtKind;
	}

	public String getLogrtTyp() {
		return logrtTyp;
	}

	public void setLogrtTyp(String logrtTyp) {
		this.logrtTyp = logrtTyp;
	}

	public String getManageBran() {
		return manageBran;
	}

	public void setManageBran(String manageBran) {
		this.manageBran = manageBran;
	}

	public String getMaxGrntFlg() {
		return maxGrntFlg;
	}

	public void setMaxGrntFlg(String maxGrntFlg) {
		this.maxGrntFlg = maxGrntFlg;
	}

	public String getModePayment() {
		return modePayment;
	}

	public void setModePayment(String modePayment) {
		this.modePayment = modePayment;
	}

	public BigDecimal getMonInteRate() {
		return monInteRate;
	}

	public void setMonInteRate(BigDecimal monInteRate) {
		this.monInteRate = monInteRate;
	}

	public String getMortProNum() {
		return mortProNum;
	}

	public void setMortProNum(String mortProNum) {
		this.mortProNum = mortProNum;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public BigDecimal getRecoverAmt() {
		return recoverAmt;
	}

	public void setRecoverAmt(BigDecimal recoverAmt) {
		this.recoverAmt = recoverAmt;
	}

	public String getRegistrant() {
		return registrant;
	}

	public void setRegistrant(String registrant) {
		this.registrant = registrant;
	}

	public String getRepayCycl() {
		return repayCycl;
	}

	public void setRepayCycl(String repayCycl) {
		this.repayCycl = repayCycl;
	}

	public String getRepayMode() {
		return repayMode;
	}

	public void setRepayMode(String repayMode) {
		this.repayMode = repayMode;
	}

	public String getRepayOrg() {
		return repayOrg;
	}

	public void setRepayOrg(String repayOrg) {
		this.repayOrg = repayOrg;
	}

	public BigDecimal getRskExpoRatio() {
		return rskExpoRatio;
	}

	public void setRskExpoRatio(BigDecimal rskExpoRatio) {
		this.rskExpoRatio = rskExpoRatio;
	}

	public String getSdptAcctNo() {
		return sdptAcctNo;
	}

	public void setSdptAcctNo(String sdptAcctNo) {
		this.sdptAcctNo = sdptAcctNo;
	}

	public BigDecimal getSdptAmt() {
		return sdptAmt;
	}

	public void setSdptAmt(BigDecimal sdptAmt) {
		this.sdptAmt = sdptAmt;
	}

	public BigDecimal getSdptBal() {
		return sdptBal;
	}

	public void setSdptBal(BigDecimal sdptBal) {
		this.sdptBal = sdptBal;
	}

	public BigDecimal getSdptPatio() {
		return sdptPatio;
	}

	public void setSdptPatio(BigDecimal sdptPatio) {
		this.sdptPatio = sdptPatio;
	}

	public String getSecBranEaaAuthor() {
		return secBranEaaAuthor;
	}

	public void setSecBranEaaAuthor(String secBranEaaAuthor) {
		this.secBranEaaAuthor = secBranEaaAuthor;
	}

	public String getSecDutyOfficer() {
		return secDutyOfficer;
	}

	public void setSecDutyOfficer(String secDutyOfficer) {
		this.secDutyOfficer = secDutyOfficer;
	}

	public String getSecEaaBran() {
		return secEaaBran;
	}

	public void setSecEaaBran(String secEaaBran) {
		this.secEaaBran = secEaaBran;
	}

	public String getSignBank() {
		return signBank;
	}

	public void setSignBank(String signBank) {
		this.signBank = signBank;
	}

	public String getSignFlg() {
		return signFlg;
	}

	public void setSignFlg(String signFlg) {
		this.signFlg = signFlg;
	}

	public String getSpecLoanTye() {
		return specLoanTye;
	}

	public void setSpecLoanTye(String specLoanTye) {
		this.specLoanTye = specLoanTye;
	}

	public String getStaBranAuthor() {
		return staBranAuthor;
	}

	public void setStaBranAuthor(String staBranAuthor) {
		this.staBranAuthor = staBranAuthor;
	}

	public String getStaEaaBran() {
		return staEaaBran;
	}

	public void setStaEaaBran(String staEaaBran) {
		this.staEaaBran = staEaaBran;
	}

	public String getStatiBusinVar() {
		return statiBusinVar;
	}

	public void setStatiBusinVar(String statiBusinVar) {
		this.statiBusinVar = statiBusinVar;
	}

	public String getSuppLoanTyp() {
		return suppLoanTyp;
	}

	public void setSuppLoanTyp(String suppLoanTyp) {
		this.suppLoanTyp = suppLoanTyp;
	}

	public String getSynLoanSign() {
		return synLoanSign;
	}

	public void setSynLoanSign(String synLoanSign) {
		this.synLoanSign = synLoanSign;
	}

	public String getSynProNum() {
		return synProNum;
	}

	public void setSynProNum(String synProNum) {
		this.synProNum = synProNum;
	}

	public BigDecimal getTakeCgtBal() {
		return takeCgtBal;
	}

	public void setTakeCgtBal(BigDecimal takeCgtBal) {
		this.takeCgtBal = takeCgtBal;
	}

	public BigDecimal getTakeCgtLine() {
		return takeCgtLine;
	}

	public void setTakeCgtLine(BigDecimal takeCgtLine) {
		this.takeCgtLine = takeCgtLine;
	}

	public BigDecimal getTakeMaxGrntAmt() {
		return takeMaxGrntAmt;
	}

	public void setTakeMaxGrntAmt(BigDecimal takeMaxGrntAmt) {
		this.takeMaxGrntAmt = takeMaxGrntAmt;
	}

	public BigDecimal getTakeMaxGrntBal() {
		return takeMaxGrntBal;
	}

	public void setTakeMaxGrntBal(BigDecimal takeMaxGrntBal) {
		this.takeMaxGrntBal = takeMaxGrntBal;
	}

	public String getThrBranAuthor() {
		return thrBranAuthor;
	}

	public void setThrBranAuthor(String thrBranAuthor) {
		this.thrBranAuthor = thrBranAuthor;
	}

	public String getThrEaaBran() {
		return thrEaaBran;
	}

	public void setThrEaaBran(String thrEaaBran) {
		this.thrEaaBran = thrEaaBran;
	}

	public Integer getWeiAverDay() {
		return weiAverDay;
	}

	public void setWeiAverDay(Integer weiAverDay) {
		this.weiAverDay = weiAverDay;
	}

	public BigDecimal getWeiAverIntr() {
		return weiAverIntr;
	}

	public void setWeiAverIntr(BigDecimal weiAverIntr) {
		this.weiAverIntr = weiAverIntr;
	}

	public BigDecimal getWoAmt() {
		return woAmt;
	}

	public void setWoAmt(BigDecimal woAmt) {
		this.woAmt = woAmt;
	}

	public String getYnDisc() {
		return ynDisc;
	}

	public void setYnDisc(String ynDisc) {
		this.ynDisc = ynDisc;
	}

	public String getYnFixIntr() {
		return ynFixIntr;
	}

	public void setYnFixIntr(String ynFixIntr) {
		this.ynFixIntr = ynFixIntr;
	}
}