package com.yuchengtech.bcrm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_DP_SAVE_INFO database table.
 * 
 */
@Entity
@Table(name="ACRM_F_DP_SAVE_INFO")
public class AcrmFDpSaveInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="AGREEMENT_ID")
	private long agreementId;

	@Column(name="ACCONT_TYPE")
	private String accontType;

	@Column(name="ACCT_STATUS")
	private String acctStatus;

	private BigDecimal amal;

	@Column(name="BAL_RMB")
	private BigDecimal balRmb;

	@Column(name="BAL_US")
	private BigDecimal balUs;

	private String bskd;

	@Column(name="CLOSE_DT")
	private String closeDt;

	@Column(name="CLOSE_TL")
	private String closeTl;

	@Column(name="CRM_DT")
	private String crmDt;

	@Column(name="CUST_NO")
	private String custNo;

	private String cyno;

	@Column(name="DEPOSIT_RESE_REQ")
	private String depositReseReq;

	private String detp;

	private String fsfg;

	private BigDecimal fzam;

	private BigDecimal inrt;

	@Column(name="INTE_BEAR_MODE")
	private String inteBearMode;

	@Column(name="INTE_BEAR_TERM")
	private String inteBearTerm;

	@Column(name="INTEREST_SETTLEMENT")
	private String interestSettlement;

	private String item;

	private BigDecimal lkbl;

	private String lkfg;

    @Temporal( TemporalType.DATE)
	private Date ltdt;

	@Column(name="MS_AC_BAL")
	private BigDecimal msAcBal;

	private String msfg;

	@Column(name="MVAL_RMB")
	private BigDecimal mvalRmb;

	@Column(name="ODS_ACCT_NO")
	private String odsAcctNo;

	@Column(name="OPEN_DT")
	private String openDt;

	@Column(name="OPEN_TL")
	private String openTl;

	@Column(name="ORG_NO")
	private String orgNo;

	private String perd;

	@Column(name="PERD_UNIT")
	private String perdUnit;

	@Column(name="PROD_FDKIND")
	private String prodFdkind;

	@Column(name="PROD_SDKIND")
	private String prodSdkind;

	@Column(name="PROD_TDKIND")
	private String prodTdkind;

	@Column(name="PRODUCT_ID")
	private String productId;

	@Column(name="S_CUST_NO")
	private String sCustNo;

	private String sbit;

	private String smfg;

	@Column(name="SS_AVE_DAILY")
	private BigDecimal ssAveDaily;

	private String ssit;

	private String stcd;

	@Column(name="SYS_NO")
	private String sysNo;

	@Column(name="TD_IR_TP")
	private String tdIrTp;

    @Temporal( TemporalType.DATE)
	@Column(name="TD_MU_DT")
	private Date tdMuDt;

    @Temporal( TemporalType.DATE)
	@Column(name="TD_VL_DT")
	private Date tdVlDt;

	@Column(name="UP_BAL")
	private BigDecimal upBal;

	@Column(name="UP_BAL_RMB")
	private BigDecimal upBalRmb;

    public AcrmFDpSaveInfo() {
    }

	public long getAgreementId() {
		return this.agreementId;
	}

	public void setAgreementId(long agreementId) {
		this.agreementId = agreementId;
	}

	public String getAccontType() {
		return this.accontType;
	}

	public void setAccontType(String accontType) {
		this.accontType = accontType;
	}

	public String getAcctStatus() {
		return this.acctStatus;
	}

	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}

	public BigDecimal getAmal() {
		return this.amal;
	}

	public void setAmal(BigDecimal amal) {
		this.amal = amal;
	}

	public BigDecimal getBalRmb() {
		return this.balRmb;
	}

	public void setBalRmb(BigDecimal balRmb) {
		this.balRmb = balRmb;
	}

	public BigDecimal getBalUs() {
		return this.balUs;
	}

	public void setBalUs(BigDecimal balUs) {
		this.balUs = balUs;
	}

	public String getBskd() {
		return this.bskd;
	}

	public void setBskd(String bskd) {
		this.bskd = bskd;
	}

	public String getCloseDt() {
		return this.closeDt;
	}

	public void setCloseDt(String closeDt) {
		this.closeDt = closeDt;
	}

	public String getCloseTl() {
		return this.closeTl;
	}

	public void setCloseTl(String closeTl) {
		this.closeTl = closeTl;
	}

	public String getCrmDt() {
		return this.crmDt;
	}

	public void setCrmDt(String crmDt) {
		this.crmDt = crmDt;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCyno() {
		return this.cyno;
	}

	public void setCyno(String cyno) {
		this.cyno = cyno;
	}

	public String getDepositReseReq() {
		return this.depositReseReq;
	}

	public void setDepositReseReq(String depositReseReq) {
		this.depositReseReq = depositReseReq;
	}

	public String getDetp() {
		return this.detp;
	}

	public void setDetp(String detp) {
		this.detp = detp;
	}

	public String getFsfg() {
		return this.fsfg;
	}

	public void setFsfg(String fsfg) {
		this.fsfg = fsfg;
	}

	public BigDecimal getFzam() {
		return this.fzam;
	}

	public void setFzam(BigDecimal fzam) {
		this.fzam = fzam;
	}

	public BigDecimal getInrt() {
		return this.inrt;
	}

	public void setInrt(BigDecimal inrt) {
		this.inrt = inrt;
	}

	public String getInteBearMode() {
		return this.inteBearMode;
	}

	public void setInteBearMode(String inteBearMode) {
		this.inteBearMode = inteBearMode;
	}

	public String getInteBearTerm() {
		return this.inteBearTerm;
	}

	public void setInteBearTerm(String inteBearTerm) {
		this.inteBearTerm = inteBearTerm;
	}

	public String getInterestSettlement() {
		return this.interestSettlement;
	}

	public void setInterestSettlement(String interestSettlement) {
		this.interestSettlement = interestSettlement;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public BigDecimal getLkbl() {
		return this.lkbl;
	}

	public void setLkbl(BigDecimal lkbl) {
		this.lkbl = lkbl;
	}

	public String getLkfg() {
		return this.lkfg;
	}

	public void setLkfg(String lkfg) {
		this.lkfg = lkfg;
	}

	public Date getLtdt() {
		return this.ltdt;
	}

	public void setLtdt(Date ltdt) {
		this.ltdt = ltdt;
	}

	public BigDecimal getMsAcBal() {
		return this.msAcBal;
	}

	public void setMsAcBal(BigDecimal msAcBal) {
		this.msAcBal = msAcBal;
	}

	public String getMsfg() {
		return this.msfg;
	}

	public void setMsfg(String msfg) {
		this.msfg = msfg;
	}

	public BigDecimal getMvalRmb() {
		return this.mvalRmb;
	}

	public void setMvalRmb(BigDecimal mvalRmb) {
		this.mvalRmb = mvalRmb;
	}

	public String getOdsAcctNo() {
		return this.odsAcctNo;
	}

	public void setOdsAcctNo(String odsAcctNo) {
		this.odsAcctNo = odsAcctNo;
	}

	public String getOpenDt() {
		return this.openDt;
	}

	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}

	public String getOpenTl() {
		return this.openTl;
	}

	public void setOpenTl(String openTl) {
		this.openTl = openTl;
	}

	public String getOrgNo() {
		return this.orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getPerd() {
		return this.perd;
	}

	public void setPerd(String perd) {
		this.perd = perd;
	}

	public String getPerdUnit() {
		return this.perdUnit;
	}

	public void setPerdUnit(String perdUnit) {
		this.perdUnit = perdUnit;
	}

	public String getProdFdkind() {
		return this.prodFdkind;
	}

	public void setProdFdkind(String prodFdkind) {
		this.prodFdkind = prodFdkind;
	}

	public String getProdSdkind() {
		return this.prodSdkind;
	}

	public void setProdSdkind(String prodSdkind) {
		this.prodSdkind = prodSdkind;
	}

	public String getProdTdkind() {
		return this.prodTdkind;
	}

	public void setProdTdkind(String prodTdkind) {
		this.prodTdkind = prodTdkind;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSCustNo() {
		return this.sCustNo;
	}

	public void setSCustNo(String sCustNo) {
		this.sCustNo = sCustNo;
	}

	public String getSbit() {
		return this.sbit;
	}

	public void setSbit(String sbit) {
		this.sbit = sbit;
	}

	public String getSmfg() {
		return this.smfg;
	}

	public void setSmfg(String smfg) {
		this.smfg = smfg;
	}

	public BigDecimal getSsAveDaily() {
		return this.ssAveDaily;
	}

	public void setSsAveDaily(BigDecimal ssAveDaily) {
		this.ssAveDaily = ssAveDaily;
	}

	public String getSsit() {
		return this.ssit;
	}

	public void setSsit(String ssit) {
		this.ssit = ssit;
	}

	public String getStcd() {
		return this.stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	public String getSysNo() {
		return this.sysNo;
	}

	public void setSysNo(String sysNo) {
		this.sysNo = sysNo;
	}

	public String getTdIrTp() {
		return this.tdIrTp;
	}

	public void setTdIrTp(String tdIrTp) {
		this.tdIrTp = tdIrTp;
	}

	public Date getTdMuDt() {
		return this.tdMuDt;
	}

	public void setTdMuDt(Date tdMuDt) {
		this.tdMuDt = tdMuDt;
	}

	public Date getTdVlDt() {
		return this.tdVlDt;
	}

	public void setTdVlDt(Date tdVlDt) {
		this.tdVlDt = tdVlDt;
	}

	public BigDecimal getUpBal() {
		return this.upBal;
	}

	public void setUpBal(BigDecimal upBal) {
		this.upBal = upBal;
	}

	public BigDecimal getUpBalRmb() {
		return this.upBalRmb;
	}

	public void setUpBalRmb(BigDecimal upBalRmb) {
		this.upBalRmb = upBalRmb;
	}

}