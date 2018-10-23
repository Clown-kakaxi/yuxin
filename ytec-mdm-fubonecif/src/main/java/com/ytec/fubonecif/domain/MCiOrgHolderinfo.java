package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MCiOrgHolderinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CI_ORG_HOLDERINFO")
public class MCiOrgHolderinfo implements java.io.Serializable {

	// Fields

	private String holderId;
	private String custId;
	private String holderType;
	private String holderName;
	private String identType;
	private String identNo;
	private Date identExpiredDate;
	private Date birthday;
	private String email;
	private String isOffenceFlag;
	private String sponsorKind;
	private Double sponsorAmt;
	private String sponsorCurr;
	private Double sponsorPercent;
	private Date sponsorDate;
	private String isCheckFlag;
	private Double stockPercent;
	private String countryCode;
	private String holderOrgAddr;
	private String holderOrgRegAddr;
	private String holderOrgTel;
	private String holderPerGender;
	private String holderPerBirthLocale;
	private String holderPerCtryAddr;
	private String holderPerCtryTel;
	private String remtRecverCtryCd;
	private String remtRecverCtryAddr;
	private String authedPerCtryCd;
	private String authedPerCtryAddr;
	private String holderPerPostAddr;
	private String holderPerOffcTel;
	private String holderPerFamlyTel;
	private String holderPerMobile;
	private String holderPerIndPos;
	private String legalReprName;
	private Double needSponsorAmt;
	private Double actualStockPercent;
	private String isRptMerge;
	private String isReported;
	private String isRegAtUsa;
	private String remark;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCiOrgHolderinfo() {
	}

	/** minimal constructor */
	public MCiOrgHolderinfo(String holderId) {
		this.holderId = holderId;
	}

	/** full constructor */
	public MCiOrgHolderinfo(String holderId, String custId, String holderType,
			String holderName, String identType, String identNo,
			Date identExpiredDate, Date birthday, String email,
			String isOffenceFlag, String sponsorKind, Double sponsorAmt,
			String sponsorCurr, Double sponsorPercent, Date sponsorDate,
			String isCheckFlag, Double stockPercent, String countryCode,
			String holderOrgAddr, String holderOrgRegAddr, String holderOrgTel,
			String holderPerGender, String holderPerBirthLocale,
			String holderPerCtryAddr, String holderPerCtryTel,
			String remtRecverCtryCd, String remtRecverCtryAddr,
			String authedPerCtryCd, String authedPerCtryAddr,
			String holderPerPostAddr, String holderPerOffcTel,
			String holderPerFamlyTel, String holderPerMobile,
			String holderPerIndPos, String legalReprName,
			Double needSponsorAmt, Double actualStockPercent,
			String isRptMerge, String isReported, String isRegAtUsa,
			String remark, String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.holderId = holderId;
		this.custId = custId;
		this.holderType = holderType;
		this.holderName = holderName;
		this.identType = identType;
		this.identNo = identNo;
		this.identExpiredDate = identExpiredDate;
		this.birthday = birthday;
		this.email = email;
		this.isOffenceFlag = isOffenceFlag;
		this.sponsorKind = sponsorKind;
		this.sponsorAmt = sponsorAmt;
		this.sponsorCurr = sponsorCurr;
		this.sponsorPercent = sponsorPercent;
		this.sponsorDate = sponsorDate;
		this.isCheckFlag = isCheckFlag;
		this.stockPercent = stockPercent;
		this.countryCode = countryCode;
		this.holderOrgAddr = holderOrgAddr;
		this.holderOrgRegAddr = holderOrgRegAddr;
		this.holderOrgTel = holderOrgTel;
		this.holderPerGender = holderPerGender;
		this.holderPerBirthLocale = holderPerBirthLocale;
		this.holderPerCtryAddr = holderPerCtryAddr;
		this.holderPerCtryTel = holderPerCtryTel;
		this.remtRecverCtryCd = remtRecverCtryCd;
		this.remtRecverCtryAddr = remtRecverCtryAddr;
		this.authedPerCtryCd = authedPerCtryCd;
		this.authedPerCtryAddr = authedPerCtryAddr;
		this.holderPerPostAddr = holderPerPostAddr;
		this.holderPerOffcTel = holderPerOffcTel;
		this.holderPerFamlyTel = holderPerFamlyTel;
		this.holderPerMobile = holderPerMobile;
		this.holderPerIndPos = holderPerIndPos;
		this.legalReprName = legalReprName;
		this.needSponsorAmt = needSponsorAmt;
		this.actualStockPercent = actualStockPercent;
		this.isRptMerge = isRptMerge;
		this.isReported = isReported;
		this.isRegAtUsa = isRegAtUsa;
		this.remark = remark;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "HOLDER_ID", unique = true, nullable = false, length = 20)
	public String getHolderId() {
		return this.holderId;
	}

	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "HOLDER_TYPE", length = 20)
	public String getHolderType() {
		return this.holderType;
	}

	public void setHolderType(String holderType) {
		this.holderType = holderType;
	}

	@Column(name = "HOLDER_NAME", length = 80)
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "IDENT_EXPIRED_DATE", length = 7)
	public Date getIdentExpiredDate() {
		return this.identExpiredDate;
	}

	public void setIdentExpiredDate(Date identExpiredDate) {
		this.identExpiredDate = identExpiredDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "IS_OFFENCE_FLAG", length = 1)
	public String getIsOffenceFlag() {
		return this.isOffenceFlag;
	}

	public void setIsOffenceFlag(String isOffenceFlag) {
		this.isOffenceFlag = isOffenceFlag;
	}

	@Column(name = "SPONSOR_KIND", length = 20)
	public String getSponsorKind() {
		return this.sponsorKind;
	}

	public void setSponsorKind(String sponsorKind) {
		this.sponsorKind = sponsorKind;
	}

	@Column(name = "SPONSOR_AMT", precision = 17)
	public Double getSponsorAmt() {
		return this.sponsorAmt;
	}

	public void setSponsorAmt(Double sponsorAmt) {
		this.sponsorAmt = sponsorAmt;
	}

	@Column(name = "SPONSOR_CURR", length = 20)
	public String getSponsorCurr() {
		return this.sponsorCurr;
	}

	public void setSponsorCurr(String sponsorCurr) {
		this.sponsorCurr = sponsorCurr;
	}

	@Column(name = "SPONSOR_PERCENT", precision = 10, scale = 4)
	public Double getSponsorPercent() {
		return this.sponsorPercent;
	}

	public void setSponsorPercent(Double sponsorPercent) {
		this.sponsorPercent = sponsorPercent;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SPONSOR_DATE", length = 7)
	public Date getSponsorDate() {
		return this.sponsorDate;
	}

	public void setSponsorDate(Date sponsorDate) {
		this.sponsorDate = sponsorDate;
	}

	@Column(name = "IS_CHECK_FLAG", length = 1)
	public String getIsCheckFlag() {
		return this.isCheckFlag;
	}

	public void setIsCheckFlag(String isCheckFlag) {
		this.isCheckFlag = isCheckFlag;
	}

	@Column(name = "STOCK_PERCENT", precision = 10, scale = 4)
	public Double getStockPercent() {
		return this.stockPercent;
	}

	public void setStockPercent(Double stockPercent) {
		this.stockPercent = stockPercent;
	}

	@Column(name = "COUNTRY_CODE", length = 20)
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Column(name = "HOLDER_ORG_ADDR", length = 200)
	public String getHolderOrgAddr() {
		return this.holderOrgAddr;
	}

	public void setHolderOrgAddr(String holderOrgAddr) {
		this.holderOrgAddr = holderOrgAddr;
	}

	@Column(name = "HOLDER_ORG_REG_ADDR", length = 200)
	public String getHolderOrgRegAddr() {
		return this.holderOrgRegAddr;
	}

	public void setHolderOrgRegAddr(String holderOrgRegAddr) {
		this.holderOrgRegAddr = holderOrgRegAddr;
	}

	@Column(name = "HOLDER_ORG_TEL", length = 20)
	public String getHolderOrgTel() {
		return this.holderOrgTel;
	}

	public void setHolderOrgTel(String holderOrgTel) {
		this.holderOrgTel = holderOrgTel;
	}

	@Column(name = "HOLDER_PER_GENDER", length = 20)
	public String getHolderPerGender() {
		return this.holderPerGender;
	}

	public void setHolderPerGender(String holderPerGender) {
		this.holderPerGender = holderPerGender;
	}

	@Column(name = "HOLDER_PER_BIRTH_LOCALE", length = 50)
	public String getHolderPerBirthLocale() {
		return this.holderPerBirthLocale;
	}

	public void setHolderPerBirthLocale(String holderPerBirthLocale) {
		this.holderPerBirthLocale = holderPerBirthLocale;
	}

	@Column(name = "HOLDER_PER_CTRY_ADDR", length = 200)
	public String getHolderPerCtryAddr() {
		return this.holderPerCtryAddr;
	}

	public void setHolderPerCtryAddr(String holderPerCtryAddr) {
		this.holderPerCtryAddr = holderPerCtryAddr;
	}

	@Column(name = "HOLDER_PER_CTRY_TEL", length = 20)
	public String getHolderPerCtryTel() {
		return this.holderPerCtryTel;
	}

	public void setHolderPerCtryTel(String holderPerCtryTel) {
		this.holderPerCtryTel = holderPerCtryTel;
	}

	@Column(name = "REMT_RECVER_CTRY_CD", length = 20)
	public String getRemtRecverCtryCd() {
		return this.remtRecverCtryCd;
	}

	public void setRemtRecverCtryCd(String remtRecverCtryCd) {
		this.remtRecverCtryCd = remtRecverCtryCd;
	}

	@Column(name = "REMT_RECVER_CTRY_ADDR", length = 200)
	public String getRemtRecverCtryAddr() {
		return this.remtRecverCtryAddr;
	}

	public void setRemtRecverCtryAddr(String remtRecverCtryAddr) {
		this.remtRecverCtryAddr = remtRecverCtryAddr;
	}

	@Column(name = "AUTHED_PER_CTRY_CD", length = 20)
	public String getAuthedPerCtryCd() {
		return this.authedPerCtryCd;
	}

	public void setAuthedPerCtryCd(String authedPerCtryCd) {
		this.authedPerCtryCd = authedPerCtryCd;
	}

	@Column(name = "AUTHED_PER_CTRY_ADDR", length = 200)
	public String getAuthedPerCtryAddr() {
		return this.authedPerCtryAddr;
	}

	public void setAuthedPerCtryAddr(String authedPerCtryAddr) {
		this.authedPerCtryAddr = authedPerCtryAddr;
	}

	@Column(name = "HOLDER_PER_POST_ADDR", length = 200)
	public String getHolderPerPostAddr() {
		return this.holderPerPostAddr;
	}

	public void setHolderPerPostAddr(String holderPerPostAddr) {
		this.holderPerPostAddr = holderPerPostAddr;
	}

	@Column(name = "HOLDER_PER_OFFC_TEL", length = 20)
	public String getHolderPerOffcTel() {
		return this.holderPerOffcTel;
	}

	public void setHolderPerOffcTel(String holderPerOffcTel) {
		this.holderPerOffcTel = holderPerOffcTel;
	}

	@Column(name = "HOLDER_PER_FAMLY_TEL", length = 20)
	public String getHolderPerFamlyTel() {
		return this.holderPerFamlyTel;
	}

	public void setHolderPerFamlyTel(String holderPerFamlyTel) {
		this.holderPerFamlyTel = holderPerFamlyTel;
	}

	@Column(name = "HOLDER_PER_MOBILE", length = 20)
	public String getHolderPerMobile() {
		return this.holderPerMobile;
	}

	public void setHolderPerMobile(String holderPerMobile) {
		this.holderPerMobile = holderPerMobile;
	}

	@Column(name = "HOLDER_PER_IND_POS", length = 20)
	public String getHolderPerIndPos() {
		return this.holderPerIndPos;
	}

	public void setHolderPerIndPos(String holderPerIndPos) {
		this.holderPerIndPos = holderPerIndPos;
	}

	@Column(name = "LEGAL_REPR_NAME", length = 80)
	public String getLegalReprName() {
		return this.legalReprName;
	}

	public void setLegalReprName(String legalReprName) {
		this.legalReprName = legalReprName;
	}

	@Column(name = "NEED_SPONSOR_AMT", precision = 17)
	public Double getNeedSponsorAmt() {
		return this.needSponsorAmt;
	}

	public void setNeedSponsorAmt(Double needSponsorAmt) {
		this.needSponsorAmt = needSponsorAmt;
	}

	@Column(name = "ACTUAL_STOCK_PERCENT", precision = 17)
	public Double getActualStockPercent() {
		return this.actualStockPercent;
	}

	public void setActualStockPercent(Double actualStockPercent) {
		this.actualStockPercent = actualStockPercent;
	}

	@Column(name = "IS_RPT_MERGE", length = 1)
	public String getIsRptMerge() {
		return this.isRptMerge;
	}

	public void setIsRptMerge(String isRptMerge) {
		this.isRptMerge = isRptMerge;
	}

	@Column(name = "IS_REPORTED", length = 1)
	public String getIsReported() {
		return this.isReported;
	}

	public void setIsReported(String isReported) {
		this.isReported = isReported;
	}

	@Column(name = "IS_REG_AT_USA", length = 1)
	public String getIsRegAtUsa() {
		return this.isRegAtUsa;
	}

	public void setIsRegAtUsa(String isRegAtUsa) {
		this.isRegAtUsa = isRegAtUsa;
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