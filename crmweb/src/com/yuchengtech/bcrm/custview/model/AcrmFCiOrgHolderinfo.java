package com.yuchengtech.bcrm.custview.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
 * The persistent class for the ACRM_F_CI_ORG_HOLDERINFO database table.
 * 对公客户视图 ===股东信息
 */
@Entity
@Table(name="ACRM_F_CI_ORG_HOLDERINFO")
public class AcrmFCiOrgHolderinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CI_ORG_HOLDERINFO_HOLDER_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_ORG_HOLDERINFO_HOLDER_ID_GENERATOR")
	@Column(name="HOLDER_ID")
	private String holderId;

	@Column(name="ACTUAL_STOCK_PERCENT")
	private BigDecimal actualStockPercent;

	@Column(name="AUTHED_PER_CTRY_ADDR")
	private String authedPerCtryAddr;

	@Column(name="AUTHED_PER_CTRY_CD")
	private String authedPerCtryCd;

    @Temporal( TemporalType.DATE)
	private Date birthday;

	@Column(name="COUNTRY_CODE")
	private String countryCode;

	@Column(name="CUST_ID")
	private String custId;

	private String email;

	@Column(name="HOLDER_NAME")
	private String holderName;

	@Column(name="HOLDER_ORG_ADDR")
	private String holderOrgAddr;

	@Column(name="HOLDER_ORG_REG_ADDR")
	private String holderOrgRegAddr;

	@Column(name="HOLDER_ORG_TEL")
	private String holderOrgTel;

	@Column(name="HOLDER_PER_BIRTH_LOCALE")
	private String holderPerBirthLocale;

	@Column(name="HOLDER_PER_CTRY_ADDR")
	private String holderPerCtryAddr;

	@Column(name="HOLDER_PER_CTRY_TEL")
	private String holderPerCtryTel;

	@Column(name="HOLDER_PER_FAMLY_TEL")
	private String holderPerFamlyTel;

	@Column(name="HOLDER_PER_GENDER")
	private String holderPerGender;

	@Column(name="HOLDER_PER_IND_POS")
	private String holderPerIndPos;

	@Column(name="HOLDER_PER_MOBILE")
	private String holderPerMobile;

	@Column(name="HOLDER_PER_OFFC_TEL")
	private String holderPerOffcTel;

	@Column(name="HOLDER_PER_POST_ADDR")
	private String holderPerPostAddr;

	@Column(name="HOLDER_TYPE")
	private String holderType;

    @Temporal( TemporalType.DATE)
	@Column(name="IDENT_EXPIRED_DATE")
	private Date identExpiredDate;

	@Column(name="IDENT_NO")
	private String identNo;

	@Column(name="IDENT_TYPE")
	private String identType;

	@Column(name="IS_CHECK_FLAG")
	private String isCheckFlag;

	@Column(name="IS_OFFENCE_FLAG")
	private String isOffenceFlag;

	@Column(name="IS_REG_AT_USA")
	private String isRegAtUsa;

	@Column(name="IS_REPORTED")
	private String isReported;

	@Column(name="IS_RPT_MERGE")
	private String isRptMerge;

	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name="LEGAL_REPR_NAME")
	private String legalReprName;

	@Column(name="NEED_SPONSOR_AMT")
	private BigDecimal needSponsorAmt;

	private String remark;

	@Column(name="REMT_RECVER_CTRY_ADDR")
	private String remtRecverCtryAddr;

	@Column(name="REMT_RECVER_CTRY_CD")
	private String remtRecverCtryCd;

	@Column(name="SPONSOR_AMT")
	private BigDecimal sponsorAmt;

	@Column(name="SPONSOR_CURR")
	private String sponsorCurr;

    @Temporal( TemporalType.DATE)
	@Column(name="SPONSOR_DATE")
	private Date sponsorDate;

	@Column(name="SPONSOR_KIND")
	private String sponsorKind;

	@Column(name="SPONSOR_PERCENT")
	private BigDecimal sponsorPercent;

	@Column(name="STOCK_PERCENT")
	private BigDecimal stockPercent;

	@Column(name="TX_SEQ_NO")
	private String txSeqNo;

    public AcrmFCiOrgHolderinfo() {
    }

	public String getHolderId() {
		return this.holderId;
	}

	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}

	public BigDecimal getActualStockPercent() {
		return this.actualStockPercent;
	}

	public void setActualStockPercent(BigDecimal actualStockPercent) {
		this.actualStockPercent = actualStockPercent;
	}

	public String getAuthedPerCtryAddr() {
		return this.authedPerCtryAddr;
	}

	public void setAuthedPerCtryAddr(String authedPerCtryAddr) {
		this.authedPerCtryAddr = authedPerCtryAddr;
	}

	public String getAuthedPerCtryCd() {
		return this.authedPerCtryCd;
	}

	public void setAuthedPerCtryCd(String authedPerCtryCd) {
		this.authedPerCtryCd = authedPerCtryCd;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getHolderOrgAddr() {
		return this.holderOrgAddr;
	}

	public void setHolderOrgAddr(String holderOrgAddr) {
		this.holderOrgAddr = holderOrgAddr;
	}

	public String getHolderOrgRegAddr() {
		return this.holderOrgRegAddr;
	}

	public void setHolderOrgRegAddr(String holderOrgRegAddr) {
		this.holderOrgRegAddr = holderOrgRegAddr;
	}

	public String getHolderOrgTel() {
		return this.holderOrgTel;
	}

	public void setHolderOrgTel(String holderOrgTel) {
		this.holderOrgTel = holderOrgTel;
	}

	public String getHolderPerBirthLocale() {
		return this.holderPerBirthLocale;
	}

	public void setHolderPerBirthLocale(String holderPerBirthLocale) {
		this.holderPerBirthLocale = holderPerBirthLocale;
	}

	public String getHolderPerCtryAddr() {
		return this.holderPerCtryAddr;
	}

	public void setHolderPerCtryAddr(String holderPerCtryAddr) {
		this.holderPerCtryAddr = holderPerCtryAddr;
	}

	public String getHolderPerCtryTel() {
		return this.holderPerCtryTel;
	}

	public void setHolderPerCtryTel(String holderPerCtryTel) {
		this.holderPerCtryTel = holderPerCtryTel;
	}

	public String getHolderPerFamlyTel() {
		return this.holderPerFamlyTel;
	}

	public void setHolderPerFamlyTel(String holderPerFamlyTel) {
		this.holderPerFamlyTel = holderPerFamlyTel;
	}

	public String getHolderPerGender() {
		return this.holderPerGender;
	}

	public void setHolderPerGender(String holderPerGender) {
		this.holderPerGender = holderPerGender;
	}

	public String getHolderPerIndPos() {
		return this.holderPerIndPos;
	}

	public void setHolderPerIndPos(String holderPerIndPos) {
		this.holderPerIndPos = holderPerIndPos;
	}

	public String getHolderPerMobile() {
		return this.holderPerMobile;
	}

	public void setHolderPerMobile(String holderPerMobile) {
		this.holderPerMobile = holderPerMobile;
	}

	public String getHolderPerOffcTel() {
		return this.holderPerOffcTel;
	}

	public void setHolderPerOffcTel(String holderPerOffcTel) {
		this.holderPerOffcTel = holderPerOffcTel;
	}

	public String getHolderPerPostAddr() {
		return this.holderPerPostAddr;
	}

	public void setHolderPerPostAddr(String holderPerPostAddr) {
		this.holderPerPostAddr = holderPerPostAddr;
	}

	public String getHolderType() {
		return this.holderType;
	}

	public void setHolderType(String holderType) {
		this.holderType = holderType;
	}

	public Date getIdentExpiredDate() {
		return this.identExpiredDate;
	}

	public void setIdentExpiredDate(Date identExpiredDate) {
		this.identExpiredDate = identExpiredDate;
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

	public String getIsCheckFlag() {
		return this.isCheckFlag;
	}

	public void setIsCheckFlag(String isCheckFlag) {
		this.isCheckFlag = isCheckFlag;
	}

	public String getIsOffenceFlag() {
		return this.isOffenceFlag;
	}

	public void setIsOffenceFlag(String isOffenceFlag) {
		this.isOffenceFlag = isOffenceFlag;
	}

	public String getIsRegAtUsa() {
		return this.isRegAtUsa;
	}

	public void setIsRegAtUsa(String isRegAtUsa) {
		this.isRegAtUsa = isRegAtUsa;
	}

	public String getIsReported() {
		return this.isReported;
	}

	public void setIsReported(String isReported) {
		this.isReported = isReported;
	}

	public String getIsRptMerge() {
		return this.isRptMerge;
	}

	public void setIsRptMerge(String isRptMerge) {
		this.isRptMerge = isRptMerge;
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

	public String getLegalReprName() {
		return this.legalReprName;
	}

	public void setLegalReprName(String legalReprName) {
		this.legalReprName = legalReprName;
	}

	public BigDecimal getNeedSponsorAmt() {
		return this.needSponsorAmt;
	}

	public void setNeedSponsorAmt(BigDecimal needSponsorAmt) {
		this.needSponsorAmt = needSponsorAmt;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemtRecverCtryAddr() {
		return this.remtRecverCtryAddr;
	}

	public void setRemtRecverCtryAddr(String remtRecverCtryAddr) {
		this.remtRecverCtryAddr = remtRecverCtryAddr;
	}

	public String getRemtRecverCtryCd() {
		return this.remtRecverCtryCd;
	}

	public void setRemtRecverCtryCd(String remtRecverCtryCd) {
		this.remtRecverCtryCd = remtRecverCtryCd;
	}

	public BigDecimal getSponsorAmt() {
		return this.sponsorAmt;
	}

	public void setSponsorAmt(BigDecimal sponsorAmt) {
		this.sponsorAmt = sponsorAmt;
	}

	public String getSponsorCurr() {
		return this.sponsorCurr;
	}

	public void setSponsorCurr(String sponsorCurr) {
		this.sponsorCurr = sponsorCurr;
	}

	public Date getSponsorDate() {
		return this.sponsorDate;
	}

	public void setSponsorDate(Date sponsorDate) {
		this.sponsorDate = sponsorDate;
	}

	public String getSponsorKind() {
		return this.sponsorKind;
	}

	public void setSponsorKind(String sponsorKind) {
		this.sponsorKind = sponsorKind;
	}

	public BigDecimal getSponsorPercent() {
		return this.sponsorPercent;
	}

	public void setSponsorPercent(BigDecimal sponsorPercent) {
		this.sponsorPercent = sponsorPercent;
	}

	public BigDecimal getStockPercent() {
		return this.stockPercent;
	}

	public void setStockPercent(BigDecimal stockPercent) {
		this.stockPercent = stockPercent;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}