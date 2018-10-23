package com.ytec.mdm.domain.biz;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;

/**
 * The persistent class for the ACRM_F_CI_POT_CUS_COM database table.
 */
@Entity
@Table(name = "ACRM_F_CI_POT_CUS_COM")
public class AcrmFCiPotCusCom implements Serializable {
	/**
	 *
	 */
	@Id
	// @SequenceGenerator(name = "ACRM_F_CI_POT_CUS_COM_CUSID_GENERATOR")
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACRM_F_CI_POT_CUS_COM_CUSID_GENERATOR")
	@Column(name = "CUS_ID")
	private String cusId;

	@Column(name = "A_FILETYPE")
	private String aFiletype;

	@Column(name = "ACT_CTL_NAME")
	private String actCtlName;

	@Column(name = "ACT_CTL_PHONE")
	private String actCtlPhone;

	@Column(name = "ACT_CTL_WIFE")
	private String actCtlWife;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "AMOUNT1")
	private BigDecimal amount1;

	@Column(name = "AMOUNT2")
	private BigDecimal amount2;

	@Column(name = "ATTEN_BUSI")
	private String attenBusi;

	@Column(name = "ATTEN_NAME")
	private String attenName;

	@Column(name = "ATTEN_PHONE")
	private String attenPhone;

	@Column(name = "AVE_BALANCE")
	private BigDecimal aveBalance;

	@Column(name = "B_FILETYPE")
	private String bFiletype;

	@Column(name = "BAD_CREDIT_FLAG")
	private String badCreditFlag;

	@Column(name = "BUYER_INF")
	private String buyerInf;

	@Column(name = "BUYER_INF_RATE")
	private BigDecimal buyerInfRate;

	@Column(name = "BUYER_INF_S")
	private String buyerInfS;

	@Column(name = "BUYER_INF_S_RATE")
	private BigDecimal buyerInfSRate;

	@Column(name = "C_FILETYPE")
	private String cFiletype;

	@Column(name = "CALL_NO")
	private String callNo;

	@Column(name = "CAP_AMOUNT")
	private BigDecimal capAmount;

	@Column(name = "CERT_CODE")
	private String certCode;

	@Column(name = "CERT_TYPE")
	private String certType;

//	 @Column(name = "CONCLUSIOIN")
//	 private BigDecimal conclusion;

	@Column(name = "CONTMETH_INFO")
	private String contmethInfo;

	@Column(name = "CREDIT_CARD_BANK")
	private String creditCardBank;

	@Column(name = "CREDIT_CARD_FLAG")
	private String creditCardFlag;

	@Column(name = "CREDIT_USE")
	private String creditUse;

	@Column(name = "CUS_ADDR")
	private String cusAddr;

	@Column(name = "CUS_NAME")
	private String cusName;

	@Column(name = "CUS_PHONE")
	private String cusPhone;

	@Column(name = "CUS_RESOURCE")
	private String cusResource;

	@Column(name = "CUS_STATUS")
	private String cusStatus;

	@Column(name = "CUST_MGR")
	private String custMgr;

	@Column(name = "CUST_STAT")
	private String custStat;

	@Column(name = "CUST_TYPE")
	private String custType;

	@Column(name = "DEBIT_FLAG")
	private String debitFlag;

	@Column(name = "DEBT_AMOUNT")
	private BigDecimal debtAmount;

	@Column(name = "DEL")
	private String del;

	@Column(name = "EN_NAME")
	private String enName;

	@Column(name = "FINA_AMOUNT")
	private BigDecimal finaAmount;

	@Column(name = "G_DEPOSIT")
	private BigDecimal gDeposit;

	@Column(name = "G_DEPOSITPLEDGE")
	private BigDecimal gDepositpledge;

	@Column(name = "G_EQUIPMENT")
	private BigDecimal gEquipment;

	@Column(name = "G_EQUIPMENTPLEDGE")
	private BigDecimal gEquipmentpledge;

	@Column(name = "G_FLOATING")
	private BigDecimal gFloating;

	@Column(name = "G_FLOATPLEDGE")
	private BigDecimal gFloatpledge;

	@Column(name = "G_FOREST")
	private BigDecimal gForest;

	@Column(name = "G_FORESTPLEDGE")
	private BigDecimal gForestpledge;

	@Column(name = "G_HOUSE")
	private BigDecimal gHouse;

	@Column(name = "G_HOUSEPLEDGE")
	private BigDecimal gHousepledge;

	@Column(name = "G_LAND")
	private BigDecimal gLand;

	@Column(name = "G_LANDPLEDGE")
	private BigDecimal gLandpledge;

	@Column(name = "G_MINING")
	private BigDecimal gMining;

	@Column(name = "G_MININGPLEDGE")
	private BigDecimal gMiningpledge;

	@Column(name = "G_RECEIVABLEMONEY")
	private BigDecimal gReceivablemoney;

	@Column(name = "G_RECEIVABLEMPLEDGE")
	private BigDecimal gReceivablempledge;

	@Column(name = "G_STOCK")
	private BigDecimal gStock;

	@Column(name = "G_STOCKPLEDGE")
	private BigDecimal gStockpledge;

	@Column(name = "G_VEHICLE")
	private BigDecimal gVehicle;

	@Column(name = "G_VEHICLEPLEDGE")
	private BigDecimal gVehiclepledge;

	@Column(name = "GUA_MOR_FLAG")
	private String guaMorFlag;

	@Column(name = "INDUST_TYPE")
	private String industType;

	@Column(name = "INPUT_BR_ID")
	private String inputBrId;

	@Column(name = "INPUT_DATE")
	private String inputDate;

	@Column(name = "INPUT_ID")
	private String inputId;

	@Column(name = "ISNEW")
	private String isnew;

	@Column(name = "JOB_TYPE")
	private String jobType;

	@Column(name = "LEGAL_NAME")
	private String legalName;

	@Column(name = "LICENSE_FLAG")
	private String licenseFlag;

	@Column(name = "LOAN_AMOUNT")
	private BigDecimal loanAmount;

	@Column(name = "MAIN_BR_ID")
	private String mainBrId;

	@Column(name = "PARTNER_INF1")
	private String partnerInf1;

	@Column(name = "PARTNER_INF2")
	private String partnerInf2;

	@Column(name = "PARTNER_INF3")
	private String partnerInf3;

	@Column(name = "PER_CARD_FLAG")
	private String perCardFlag;

	@Column(name = "PRE_AMOUNT")
	private BigDecimal preAmount;

	@Column(name = "PRE_CREDIT_AMOUNT")
	private BigDecimal preCreditAmount;

	@Column(name = "Q_ADDRYEARS")
	private BigDecimal qAddryears;

	@Column(name = "Q_ASSTOTAL")
	private BigDecimal qAsstotal;

	@Column(name = "Q_BUSINESS")
	private String qBusiness;

	@Column(name = "Q_CREDITLIMIT")
	private BigDecimal qCreditlimit;

	@Column(name = "Q_CUSTOMERTYPE")
	private String qCustomertype;

	@Column(name = "Q_FOUNDEDYEARS")
	private String qFoundedyears;

	@Column(name = "Q_INTERVIEWEENAME")
	private String qIntervieweename;

	@Column(name = "Q_INTERVIEWEEPOST")
	private String qIntervieweepost;

	@Column(name = "Q_LYEARINCOME")
	private BigDecimal qLyearincome;

	@Column(name = "Q_MAGYEARS")
	private String qMagyears;

	@Column(name = "Q_MARKETIN")
	private BigDecimal qMarketin;

	@Column(name = "Q_OPERATEYEARS")
	private String qOperateyears;

	@Column(name = "Q_PLANINCOME")
	private BigDecimal qPlanincome;

	@Column(name = "Q_PYEARINCOME")
	private BigDecimal qPyearincome;

	@Column(name = "Q_TOTALINCOME")
	private BigDecimal qTotalincome;

	@Column(name = "Q_WORKYEARS")
	private String qWorkyears;

	@Column(name = "QUE_INFO")
	private String queInfo;

	@Column(name = "RATE1")
	private BigDecimal rate1;

	@Column(name = "RATE2")
	private BigDecimal rate2;

	@Column(name = "RATE3")
	private BigDecimal rate3;

	@Column(name = "REG_CAP_AMT")
	private BigDecimal regCapAmt;

	@Column(name = "RELATION_COM")
	private String relationCom;

	@Column(name = "RELATION_COM_S")
	private String relationComS;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "REPAY_SOURCE")
	private String repaySource;

	@Column(name = "SHORT_NAME")
	private String shortName;

	@Column(name = "SUP_INF")
	private String supInf;

	@Column(name = "SUP_INF_RATE")
	private BigDecimal supInfRate;

	@Column(name = "SUP_INF_S")
	private String supInfS;

	@Column(name = "SUP_INF_S_RATE")
	private BigDecimal supInfSRate;

	@Column(name = "TAX_REC_FLAG")
	private String taxRecFlag;

	@Column(name = "TERM")
	private String term;

	@Column(name = "TOTAL_ASS")
	private BigDecimal totalAss;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "partner_info1")
	private String partnerInfo1;

	@Column(name = "partner_rate1")
	private String partnerRate1;

	@Column(name = "partner_info2")
	private String partnerInfo2;

	@Column(name = "partner_rate2")
	private String partnerRate2;

	@Column(name = "partner_info3")
	private String partnerInfo3;

	@Column(name = "partner_rate3")
	private String partnerRate3;

	@Column(name = "conclusion")
	private BigDecimal conclusion;
	
	@Column(name ="REQ_CURRENCY")
	private String reqCurrency;
	
	@Column(name="STATE")
	private String state;


	public AcrmFCiPotCusCom() {
	}

	public String getCusId() {
		return this.cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}

	public String getAFiletype() {
		return this.aFiletype;
	}

	public void setAFiletype(String aFiletype) {
		this.aFiletype = aFiletype;
	}

	public String getActCtlName() {
		return this.actCtlName;
	}

	public void setActCtlName(String actCtlName) {
		this.actCtlName = actCtlName;
	}

	public String getActCtlPhone() {
		return this.actCtlPhone;
	}

	public void setActCtlPhone(String actCtlPhone) {
		this.actCtlPhone = actCtlPhone;
	}

	public String getActCtlWife() {
		return this.actCtlWife;
	}

	public void setActCtlWife(String actCtlWife) {
		this.actCtlWife = actCtlWife;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount1() {
		return this.amount1;
	}

	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}

	public BigDecimal getAmount2() {
		return this.amount2;
	}

	public void setAmount2(BigDecimal amount2) {
		this.amount2 = amount2;
	}

	public String getAttenBusi() {
		return this.attenBusi;
	}

	public void setAttenBusi(String attenBusi) {
		this.attenBusi = attenBusi;
	}

	public String getAttenName() {
		return this.attenName;
	}

	public void setAttenName(String attenName) {
		this.attenName = attenName;
	}

	public String getAttenPhone() {
		return this.attenPhone;
	}

	public void setAttenPhone(String attenPhone) {
		this.attenPhone = attenPhone;
	}

	public BigDecimal getAveBalance() {
		return this.aveBalance;
	}

	public void setAveBalance(BigDecimal aveBalance) {
		this.aveBalance = aveBalance;
	}

	public String getBFiletype() {
		return this.bFiletype;
	}

	public void setBFiletype(String bFiletype) {
		this.bFiletype = bFiletype;
	}

	public String getBadCreditFlag() {
		return this.badCreditFlag;
	}

	public void setBadCreditFlag(String badCreditFlag) {
		this.badCreditFlag = badCreditFlag;
	}

	public String getBuyerInf() {
		return this.buyerInf;
	}

	public void setBuyerInf(String buyerInf) {
		this.buyerInf = buyerInf;
	}

	public BigDecimal getBuyerInfRate() {
		return this.buyerInfRate;
	}

	public void setBuyerInfRate(BigDecimal buyerInfRate) {
		this.buyerInfRate = buyerInfRate;
	}

	public String getBuyerInfS() {
		return this.buyerInfS;
	}

	public void setBuyerInfS(String buyerInfS) {
		this.buyerInfS = buyerInfS;
	}

	public BigDecimal getBuyerInfSRate() {
		return this.buyerInfSRate;
	}

	public void setBuyerInfSRate(BigDecimal buyerInfSRate) {
		this.buyerInfSRate = buyerInfSRate;
	}

	public String getCFiletype() {
		return this.cFiletype;
	}

	public void setCFiletype(String cFiletype) {
		this.cFiletype = cFiletype;
	}

	public String getCallNo() {
		return this.callNo;
	}

	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}

	public BigDecimal getCapAmount() {
		return this.capAmount;
	}

	public void setCapAmount(BigDecimal capAmount) {
		this.capAmount = capAmount;
	}

	public String getCertCode() {
		return this.certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public String getCertType() {
		return this.certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	// public BigDecimal getConclusion() {
	// return this.conclusion;
	// }
	//
	// public void setConclusion(BigDecimal conclusion) {
	// this.conclusion = conclusion;
	// }

	public String getContmethInfo() {
		return this.contmethInfo;
	}

	public void setContmethInfo(String contmethInfo) {
		this.contmethInfo = contmethInfo;
	}

	public String getCreditCardBank() {
		return this.creditCardBank;
	}

	public void setCreditCardBank(String creditCardBank) {
		this.creditCardBank = creditCardBank;
	}

	public String getCreditCardFlag() {
		return this.creditCardFlag;
	}

	public void setCreditCardFlag(String creditCardFlag) {
		this.creditCardFlag = creditCardFlag;
	}

	public String getCreditUse() {
		return this.creditUse;
	}

	public void setCreditUse(String creditUse) {
		this.creditUse = creditUse;
	}

	public String getCusAddr() {
		return this.cusAddr;
	}

	public void setCusAddr(String cusAddr) {
		this.cusAddr = cusAddr;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusPhone() {
		return this.cusPhone;
	}

	public void setCusPhone(String cusPhone) {
		this.cusPhone = cusPhone;
	}

	public String getCusResource() {
		return this.cusResource;
	}

	public void setCusResource(String cusResource) {
		this.cusResource = cusResource;
	}

	public String getCusStatus() {
		return this.cusStatus;
	}

	public void setCusStatus(String cusStatus) {
		this.cusStatus = cusStatus;
	}

	public String getCustMgr() {
		return this.custMgr;
	}

	public void setCustMgr(String custMgr) {
		this.custMgr = custMgr;
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

	public String getDebitFlag() {
		return this.debitFlag;
	}

	public void setDebitFlag(String debitFlag) {
		this.debitFlag = debitFlag;
	}

	public BigDecimal getDebtAmount() {
		return this.debtAmount;
	}

	public void setDebtAmount(BigDecimal debtAmount) {
		this.debtAmount = debtAmount;
	}

	public String getDel() {
		return this.del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public BigDecimal getFinaAmount() {
		return this.finaAmount;
	}

	public void setFinaAmount(BigDecimal finaAmount) {
		this.finaAmount = finaAmount;
	}

	public BigDecimal getGDeposit() {
		return this.gDeposit;
	}

	public void setGDeposit(BigDecimal gDeposit) {
		this.gDeposit = gDeposit;
	}

	public BigDecimal getGDepositpledge() {
		return this.gDepositpledge;
	}

	public void setGDepositpledge(BigDecimal gDepositpledge) {
		this.gDepositpledge = gDepositpledge;
	}

	public BigDecimal getGEquipment() {
		return this.gEquipment;
	}

	public void setGEquipment(BigDecimal gEquipment) {
		this.gEquipment = gEquipment;
	}

	public BigDecimal getGEquipmentpledge() {
		return this.gEquipmentpledge;
	}

	public void setGEquipmentpledge(BigDecimal gEquipmentpledge) {
		this.gEquipmentpledge = gEquipmentpledge;
	}

	public BigDecimal getGFloating() {
		return this.gFloating;
	}

	public void setGFloating(BigDecimal gFloating) {
		this.gFloating = gFloating;
	}

	public BigDecimal getGFloatpledge() {
		return this.gFloatpledge;
	}

	public void setGFloatpledge(BigDecimal gFloatpledge) {
		this.gFloatpledge = gFloatpledge;
	}

	public BigDecimal getGForest() {
		return this.gForest;
	}

	public void setGForest(BigDecimal gForest) {
		this.gForest = gForest;
	}

	public BigDecimal getGForestpledge() {
		return this.gForestpledge;
	}

	public void setGForestpledge(BigDecimal gForestpledge) {
		this.gForestpledge = gForestpledge;
	}

	public BigDecimal getGHouse() {
		return this.gHouse;
	}

	public void setGHouse(BigDecimal gHouse) {
		this.gHouse = gHouse;
	}

	public BigDecimal getGHousepledge() {
		return this.gHousepledge;
	}

	public void setGHousepledge(BigDecimal gHousepledge) {
		this.gHousepledge = gHousepledge;
	}

	public BigDecimal getGLand() {
		return this.gLand;
	}

	public void setGLand(BigDecimal gLand) {
		this.gLand = gLand;
	}

	public BigDecimal getGLandpledge() {
		return this.gLandpledge;
	}

	public void setGLandpledge(BigDecimal gLandpledge) {
		this.gLandpledge = gLandpledge;
	}

	public BigDecimal getGMining() {
		return this.gMining;
	}

	public void setGMining(BigDecimal gMining) {
		this.gMining = gMining;
	}

	public BigDecimal getGMiningpledge() {
		return this.gMiningpledge;
	}

	public void setGMiningpledge(BigDecimal gMiningpledge) {
		this.gMiningpledge = gMiningpledge;
	}

	public BigDecimal getGReceivablemoney() {
		return this.gReceivablemoney;
	}

	public void setGReceivablemoney(BigDecimal gReceivablemoney) {
		this.gReceivablemoney = gReceivablemoney;
	}

	public BigDecimal getGReceivablempledge() {
		return this.gReceivablempledge;
	}

	public void setGReceivablempledge(BigDecimal gReceivablempledge) {
		this.gReceivablempledge = gReceivablempledge;
	}

	public BigDecimal getGStock() {
		return this.gStock;
	}

	public void setGStock(BigDecimal gStock) {
		this.gStock = gStock;
	}

	public BigDecimal getGStockpledge() {
		return this.gStockpledge;
	}

	public void setGStockpledge(BigDecimal gStockpledge) {
		this.gStockpledge = gStockpledge;
	}

	public BigDecimal getGVehicle() {
		return this.gVehicle;
	}

	public void setGVehicle(BigDecimal gVehicle) {
		this.gVehicle = gVehicle;
	}

	public BigDecimal getGVehiclepledge() {
		return this.gVehiclepledge;
	}

	public void setGVehiclepledge(BigDecimal gVehiclepledge) {
		this.gVehiclepledge = gVehiclepledge;
	}

	public String getGuaMorFlag() {
		return this.guaMorFlag;
	}

	public void setGuaMorFlag(String guaMorFlag) {
		this.guaMorFlag = guaMorFlag;
	}

	public String getIndustType() {
		return this.industType;
	}

	public void setIndustType(String industType) {
		this.industType = industType;
	}

	public String getInputBrId() {
		return this.inputBrId;
	}

	public void setInputBrId(String inputBrId) {
		this.inputBrId = inputBrId;
	}

	public String getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputId() {
		return this.inputId;
	}

	public void setInputId(String inputId) {
		this.inputId = inputId;
	}

	public String getIsnew() {
		return this.isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getLegalName() {
		return this.legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLicenseFlag() {
		return this.licenseFlag;
	}

	public void setLicenseFlag(String licenseFlag) {
		this.licenseFlag = licenseFlag;
	}

	public BigDecimal getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getMainBrId() {
		return this.mainBrId;
	}

	public void setMainBrId(String mainBrId) {
		this.mainBrId = mainBrId;
	}

	public String getPartnerInf1() {
		return this.partnerInf1;
	}

	public void setPartnerInf1(String partnerInf1) {
		this.partnerInf1 = partnerInf1;
	}

	public String getPartnerInf2() {
		return this.partnerInf2;
	}

	public void setPartnerInf2(String partnerInf2) {
		this.partnerInf2 = partnerInf2;
	}

	public String getPartnerInf3() {
		return this.partnerInf3;
	}

	public void setPartnerInf3(String partnerInf3) {
		this.partnerInf3 = partnerInf3;
	}

	public String getPerCardFlag() {
		return this.perCardFlag;
	}

	public void setPerCardFlag(String perCardFlag) {
		this.perCardFlag = perCardFlag;
	}

	public BigDecimal getPreAmount() {
		return this.preAmount;
	}

	public void setPreAmount(BigDecimal preAmount) {
		this.preAmount = preAmount;
	}

	public BigDecimal getPreCreditAmount() {
		return this.preCreditAmount;
	}

	public void setPreCreditAmount(BigDecimal preCreditAmount) {
		this.preCreditAmount = preCreditAmount;
	}

	public BigDecimal getQAddryears() {
		return this.qAddryears;
	}

	public void setQAddryears(BigDecimal qAddryears) {
		this.qAddryears = qAddryears;
	}

	public BigDecimal getQAsstotal() {
		return this.qAsstotal;
	}

	public void setQAsstotal(BigDecimal qAsstotal) {
		this.qAsstotal = qAsstotal;
	}

	public String getQBusiness() {
		return this.qBusiness;
	}

	public void setQBusiness(String qBusiness) {
		this.qBusiness = qBusiness;
	}

	public BigDecimal getQCreditlimit() {
		return this.qCreditlimit;
	}

	public void setQCreditlimit(BigDecimal qCreditlimit) {
		this.qCreditlimit = qCreditlimit;
	}

	public String getQCustomertype() {
		return this.qCustomertype;
	}

	public void setQCustomertype(String qCustomertype) {
		this.qCustomertype = qCustomertype;
	}

	public String getQFoundedyears() {
		return this.qFoundedyears;
	}

	public void setQFoundedyears(String qFoundedyears) {
		this.qFoundedyears = qFoundedyears;
	}

	public String getQIntervieweename() {
		return this.qIntervieweename;
	}

	public void setQIntervieweename(String qIntervieweename) {
		this.qIntervieweename = qIntervieweename;
	}

	public String getQIntervieweepost() {
		return this.qIntervieweepost;
	}

	public void setQIntervieweepost(String qIntervieweepost) {
		this.qIntervieweepost = qIntervieweepost;
	}

	public BigDecimal getQLyearincome() {
		return this.qLyearincome;
	}

	public void setQLyearincome(BigDecimal qLyearincome) {
		this.qLyearincome = qLyearincome;
	}

	public String getQMagyears() {
		return this.qMagyears;
	}

	public void setQMagyears(String qMagyears) {
		this.qMagyears = qMagyears;
	}

	public BigDecimal getQMarketin() {
		return this.qMarketin;
	}

	public void setQMarketin(BigDecimal qMarketin) {
		this.qMarketin = qMarketin;
	}

	public String getQOperateyears() {
		return this.qOperateyears;
	}

	public void setQOperateyears(String qOperateyears) {
		this.qOperateyears = qOperateyears;
	}

	public BigDecimal getQPlanincome() {
		return this.qPlanincome;
	}

	public void setQPlanincome(BigDecimal qPlanincome) {
		this.qPlanincome = qPlanincome;
	}

	public BigDecimal getQPyearincome() {
		return this.qPyearincome;
	}

	public void setQPyearincome(BigDecimal qPyearincome) {
		this.qPyearincome = qPyearincome;
	}

	public BigDecimal getQTotalincome() {
		return this.qTotalincome;
	}

	public void setQTotalincome(BigDecimal qTotalincome) {
		this.qTotalincome = qTotalincome;
	}

	public String getQWorkyears() {
		return this.qWorkyears;
	}

	public void setQWorkyears(String qWorkyears) {
		this.qWorkyears = qWorkyears;
	}

	public String getQueInfo() {
		return this.queInfo;
	}

	public void setQueInfo(String queInfo) {
		this.queInfo = queInfo;
	}

	public BigDecimal getRate1() {
		return this.rate1;
	}

	public void setRate1(BigDecimal rate1) {
		this.rate1 = rate1;
	}

	public BigDecimal getRate2() {
		return this.rate2;
	}

	public void setRate2(BigDecimal rate2) {
		this.rate2 = rate2;
	}

	public BigDecimal getRate3() {
		return this.rate3;
	}

	public void setRate3(BigDecimal rate3) {
		this.rate3 = rate3;
	}

	public BigDecimal getRegCapAmt() {
		return this.regCapAmt;
	}

	public void setRegCapAmt(BigDecimal regCapAmt) {
		this.regCapAmt = regCapAmt;
	}

	public String getRelationCom() {
		return this.relationCom;
	}

	public void setRelationCom(String relationCom) {
		this.relationCom = relationCom;
	}

	public String getRelationComS() {
		return this.relationComS;
	}

	public void setRelationComS(String relationComS) {
		this.relationComS = relationComS;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRepaySource() {
		return this.repaySource;
	}

	public void setRepaySource(String repaySource) {
		this.repaySource = repaySource;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSupInf() {
		return this.supInf;
	}

	public void setSupInf(String supInf) {
		this.supInf = supInf;
	}

	public BigDecimal getSupInfRate() {
		return this.supInfRate;
	}

	public void setSupInfRate(BigDecimal supInfRate) {
		this.supInfRate = supInfRate;
	}

	public String getSupInfS() {
		return this.supInfS;
	}

	public void setSupInfS(String supInfS) {
		this.supInfS = supInfS;
	}

	public BigDecimal getSupInfSRate() {
		return this.supInfSRate;
	}

	public void setSupInfSRate(BigDecimal supInfSRate) {
		this.supInfSRate = supInfSRate;
	}

	public String getTaxRecFlag() {
		return this.taxRecFlag;
	}

	public void setTaxRecFlag(String taxRecFlag) {
		this.taxRecFlag = taxRecFlag;
	}

	public String getTerm() {
		return this.term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public BigDecimal getTotalAss() {
		return this.totalAss;
	}

	public void setTotalAss(BigDecimal totalAss) {
		this.totalAss = totalAss;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getaFiletype() {
		return aFiletype;
	}

	public void setaFiletype(String aFiletype) {
		this.aFiletype = aFiletype;
	}

	public String getbFiletype() {
		return bFiletype;
	}

	public void setbFiletype(String bFiletype) {
		this.bFiletype = bFiletype;
	}

	public String getcFiletype() {
		return cFiletype;
	}

	public void setcFiletype(String cFiletype) {
		this.cFiletype = cFiletype;
	}

	public BigDecimal getgDeposit() {
		return gDeposit;
	}

	public void setgDeposit(BigDecimal gDeposit) {
		this.gDeposit = gDeposit;
	}

	public BigDecimal getgDepositpledge() {
		return gDepositpledge;
	}

	public void setgDepositpledge(BigDecimal gDepositpledge) {
		this.gDepositpledge = gDepositpledge;
	}

	public BigDecimal getgEquipment() {
		return gEquipment;
	}

	public void setgEquipment(BigDecimal gEquipment) {
		this.gEquipment = gEquipment;
	}

	public BigDecimal getgEquipmentpledge() {
		return gEquipmentpledge;
	}

	public void setgEquipmentpledge(BigDecimal gEquipmentpledge) {
		this.gEquipmentpledge = gEquipmentpledge;
	}

	public BigDecimal getgFloating() {
		return gFloating;
	}

	public void setgFloating(BigDecimal gFloating) {
		this.gFloating = gFloating;
	}

	public BigDecimal getgFloatpledge() {
		return gFloatpledge;
	}

	public void setgFloatpledge(BigDecimal gFloatpledge) {
		this.gFloatpledge = gFloatpledge;
	}

	public BigDecimal getgForest() {
		return gForest;
	}

	public void setgForest(BigDecimal gForest) {
		this.gForest = gForest;
	}

	public BigDecimal getgForestpledge() {
		return gForestpledge;
	}

	public void setgForestpledge(BigDecimal gForestpledge) {
		this.gForestpledge = gForestpledge;
	}

	public BigDecimal getgHouse() {
		return gHouse;
	}

	public void setgHouse(BigDecimal gHouse) {
		this.gHouse = gHouse;
	}

	public BigDecimal getgHousepledge() {
		return gHousepledge;
	}

	public void setgHousepledge(BigDecimal gHousepledge) {
		this.gHousepledge = gHousepledge;
	}

	public BigDecimal getgLand() {
		return gLand;
	}

	public void setgLand(BigDecimal gLand) {
		this.gLand = gLand;
	}

	public BigDecimal getgLandpledge() {
		return gLandpledge;
	}

	public void setgLandpledge(BigDecimal gLandpledge) {
		this.gLandpledge = gLandpledge;
	}

	public BigDecimal getgMining() {
		return gMining;
	}

	public void setgMining(BigDecimal gMining) {
		this.gMining = gMining;
	}

	public BigDecimal getgMiningpledge() {
		return gMiningpledge;
	}

	public void setgMiningpledge(BigDecimal gMiningpledge) {
		this.gMiningpledge = gMiningpledge;
	}

	public BigDecimal getgReceivablemoney() {
		return gReceivablemoney;
	}

	public void setgReceivablemoney(BigDecimal gReceivablemoney) {
		this.gReceivablemoney = gReceivablemoney;
	}

	public BigDecimal getgReceivablempledge() {
		return gReceivablempledge;
	}

	public void setgReceivablempledge(BigDecimal gReceivablempledge) {
		this.gReceivablempledge = gReceivablempledge;
	}

	public BigDecimal getgStock() {
		return gStock;
	}

	public void setgStock(BigDecimal gStock) {
		this.gStock = gStock;
	}

	public BigDecimal getgStockpledge() {
		return gStockpledge;
	}

	public void setgStockpledge(BigDecimal gStockpledge) {
		this.gStockpledge = gStockpledge;
	}

	public BigDecimal getgVehicle() {
		return gVehicle;
	}

	public void setgVehicle(BigDecimal gVehicle) {
		this.gVehicle = gVehicle;
	}

	public BigDecimal getgVehiclepledge() {
		return gVehiclepledge;
	}

	public void setgVehiclepledge(BigDecimal gVehiclepledge) {
		this.gVehiclepledge = gVehiclepledge;
	}

	public BigDecimal getqAddryears() {
		return qAddryears;
	}

	public void setqAddryears(BigDecimal qAddryears) {
		this.qAddryears = qAddryears;
	}

	public BigDecimal getqAsstotal() {
		return qAsstotal;
	}

	public void setqAsstotal(BigDecimal qAsstotal) {
		this.qAsstotal = qAsstotal;
	}

	public String getqBusiness() {
		return qBusiness;
	}

	public void setqBusiness(String qBusiness) {
		this.qBusiness = qBusiness;
	}

	public BigDecimal getqCreditlimit() {
		return qCreditlimit;
	}

	public void setqCreditlimit(BigDecimal qCreditlimit) {
		this.qCreditlimit = qCreditlimit;
	}

	public String getqCustomertype() {
		return qCustomertype;
	}

	public void setqCustomertype(String qCustomertype) {
		this.qCustomertype = qCustomertype;
	}

	public String getqFoundedyears() {
		return qFoundedyears;
	}

	public void setqFoundedyears(String qFoundedyears) {
		this.qFoundedyears = qFoundedyears;
	}

	public String getqIntervieweename() {
		return qIntervieweename;
	}

	public void setqIntervieweename(String qIntervieweename) {
		this.qIntervieweename = qIntervieweename;
	}

	public String getqIntervieweepost() {
		return qIntervieweepost;
	}

	public void setqIntervieweepost(String qIntervieweepost) {
		this.qIntervieweepost = qIntervieweepost;
	}

	public BigDecimal getqLyearincome() {
		return qLyearincome;
	}

	public void setqLyearincome(BigDecimal qLyearincome) {
		this.qLyearincome = qLyearincome;
	}

	public String getqMagyears() {
		return qMagyears;
	}

	public void setqMagyears(String qMagyears) {
		this.qMagyears = qMagyears;
	}

	public BigDecimal getqMarketin() {
		return qMarketin;
	}

	public void setqMarketin(BigDecimal qMarketin) {
		this.qMarketin = qMarketin;
	}

	public String getqOperateyears() {
		return qOperateyears;
	}

	public void setqOperateyears(String qOperateyears) {
		this.qOperateyears = qOperateyears;
	}

	public BigDecimal getqPlanincome() {
		return qPlanincome;
	}

	public void setqPlanincome(BigDecimal qPlanincome) {
		this.qPlanincome = qPlanincome;
	}

	public BigDecimal getqPyearincome() {
		return qPyearincome;
	}

	public void setqPyearincome(BigDecimal qPyearincome) {
		this.qPyearincome = qPyearincome;
	}

	public BigDecimal getqTotalincome() {
		return qTotalincome;
	}

	public void setqTotalincome(BigDecimal qTotalincome) {
		this.qTotalincome = qTotalincome;
	}

	public String getqWorkyears() {
		return qWorkyears;
	}

	public void setqWorkyears(String qWorkyears) {
		this.qWorkyears = qWorkyears;
	}

	public String getPartnerInfo1() {
		return partnerInfo1;
	}

	public void setPartnerInfo1(String partnerInfo1) {
		this.partnerInfo1 = partnerInfo1;
	}

	public String getPartnerRate1() {
		return partnerRate1;
	}

	public void setPartnerRate1(String partnerRate1) {
		this.partnerRate1 = partnerRate1;
	}

	public String getPartnerInfo2() {
		return partnerInfo2;
	}

	public void setPartnerInfo2(String partnerInfo2) {
		this.partnerInfo2 = partnerInfo2;
	}

	public String getPartnerRate2() {
		return partnerRate2;
	}

	public void setPartnerRate2(String partnerRate2) {
		this.partnerRate2 = partnerRate2;
	}

	public String getPartnerInfo3() {
		return partnerInfo3;
	}

	public void setPartnerInfo3(String partnerInfo3) {
		this.partnerInfo3 = partnerInfo3;
	}

	public String getPartnerRate3() {
		return partnerRate3;
	}

	public void setPartnerRate3(String partnerRate3) {
		this.partnerRate3 = partnerRate3;
	}

	public BigDecimal getConclusion() {
		return conclusion;
	}

	public void setConclusion(BigDecimal conclusion) {
		this.conclusion = conclusion;
	}

	public String getReqCurrency(){
		return reqCurrency;
	}
	
	public void setReqCurrency(String reqCurrency){
		this.reqCurrency = reqCurrency;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}