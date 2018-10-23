package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiOrgHolderinfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiOrgHolderinfoId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiOrgHolderinfoId() {
	}

	/** minimal constructor */
	public HMCiOrgHolderinfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiOrgHolderinfoId(String holderId, String custId,
			String holderType, String holderName, String identType,
			String identNo, Date identExpiredDate, Date birthday, String email,
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
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "HOLDER_ID", length = 20)
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
		if (!(other instanceof HMCiOrgHolderinfoId))
			return false;
		HMCiOrgHolderinfoId castOther = (HMCiOrgHolderinfoId) other;

		return ((this.getHolderId() == castOther.getHolderId()) || (this
				.getHolderId() != null
				&& castOther.getHolderId() != null && this.getHolderId()
				.equals(castOther.getHolderId())))
				&& ((this.getCustId() == castOther.getCustId()) || (this
						.getCustId() != null
						&& castOther.getCustId() != null && this.getCustId()
						.equals(castOther.getCustId())))
				&& ((this.getHolderType() == castOther.getHolderType()) || (this
						.getHolderType() != null
						&& castOther.getHolderType() != null && this
						.getHolderType().equals(castOther.getHolderType())))
				&& ((this.getHolderName() == castOther.getHolderName()) || (this
						.getHolderName() != null
						&& castOther.getHolderName() != null && this
						.getHolderName().equals(castOther.getHolderName())))
				&& ((this.getIdentType() == castOther.getIdentType()) || (this
						.getIdentType() != null
						&& castOther.getIdentType() != null && this
						.getIdentType().equals(castOther.getIdentType())))
				&& ((this.getIdentNo() == castOther.getIdentNo()) || (this
						.getIdentNo() != null
						&& castOther.getIdentNo() != null && this.getIdentNo()
						.equals(castOther.getIdentNo())))
				&& ((this.getIdentExpiredDate() == castOther
						.getIdentExpiredDate()) || (this.getIdentExpiredDate() != null
						&& castOther.getIdentExpiredDate() != null && this
						.getIdentExpiredDate().equals(
								castOther.getIdentExpiredDate())))
				&& ((this.getBirthday() == castOther.getBirthday()) || (this
						.getBirthday() != null
						&& castOther.getBirthday() != null && this
						.getBirthday().equals(castOther.getBirthday())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null
						&& castOther.getEmail() != null && this.getEmail()
						.equals(castOther.getEmail())))
				&& ((this.getIsOffenceFlag() == castOther.getIsOffenceFlag()) || (this
						.getIsOffenceFlag() != null
						&& castOther.getIsOffenceFlag() != null && this
						.getIsOffenceFlag()
						.equals(castOther.getIsOffenceFlag())))
				&& ((this.getSponsorKind() == castOther.getSponsorKind()) || (this
						.getSponsorKind() != null
						&& castOther.getSponsorKind() != null && this
						.getSponsorKind().equals(castOther.getSponsorKind())))
				&& ((this.getSponsorAmt() == castOther.getSponsorAmt()) || (this
						.getSponsorAmt() != null
						&& castOther.getSponsorAmt() != null && this
						.getSponsorAmt().equals(castOther.getSponsorAmt())))
				&& ((this.getSponsorCurr() == castOther.getSponsorCurr()) || (this
						.getSponsorCurr() != null
						&& castOther.getSponsorCurr() != null && this
						.getSponsorCurr().equals(castOther.getSponsorCurr())))
				&& ((this.getSponsorPercent() == castOther.getSponsorPercent()) || (this
						.getSponsorPercent() != null
						&& castOther.getSponsorPercent() != null && this
						.getSponsorPercent().equals(
								castOther.getSponsorPercent())))
				&& ((this.getSponsorDate() == castOther.getSponsorDate()) || (this
						.getSponsorDate() != null
						&& castOther.getSponsorDate() != null && this
						.getSponsorDate().equals(castOther.getSponsorDate())))
				&& ((this.getIsCheckFlag() == castOther.getIsCheckFlag()) || (this
						.getIsCheckFlag() != null
						&& castOther.getIsCheckFlag() != null && this
						.getIsCheckFlag().equals(castOther.getIsCheckFlag())))
				&& ((this.getStockPercent() == castOther.getStockPercent()) || (this
						.getStockPercent() != null
						&& castOther.getStockPercent() != null && this
						.getStockPercent().equals(castOther.getStockPercent())))
				&& ((this.getCountryCode() == castOther.getCountryCode()) || (this
						.getCountryCode() != null
						&& castOther.getCountryCode() != null && this
						.getCountryCode().equals(castOther.getCountryCode())))
				&& ((this.getHolderOrgAddr() == castOther.getHolderOrgAddr()) || (this
						.getHolderOrgAddr() != null
						&& castOther.getHolderOrgAddr() != null && this
						.getHolderOrgAddr()
						.equals(castOther.getHolderOrgAddr())))
				&& ((this.getHolderOrgRegAddr() == castOther
						.getHolderOrgRegAddr()) || (this.getHolderOrgRegAddr() != null
						&& castOther.getHolderOrgRegAddr() != null && this
						.getHolderOrgRegAddr().equals(
								castOther.getHolderOrgRegAddr())))
				&& ((this.getHolderOrgTel() == castOther.getHolderOrgTel()) || (this
						.getHolderOrgTel() != null
						&& castOther.getHolderOrgTel() != null && this
						.getHolderOrgTel().equals(castOther.getHolderOrgTel())))
				&& ((this.getHolderPerGender() == castOther
						.getHolderPerGender()) || (this.getHolderPerGender() != null
						&& castOther.getHolderPerGender() != null && this
						.getHolderPerGender().equals(
								castOther.getHolderPerGender())))
				&& ((this.getHolderPerBirthLocale() == castOther
						.getHolderPerBirthLocale()) || (this
						.getHolderPerBirthLocale() != null
						&& castOther.getHolderPerBirthLocale() != null && this
						.getHolderPerBirthLocale().equals(
								castOther.getHolderPerBirthLocale())))
				&& ((this.getHolderPerCtryAddr() == castOther
						.getHolderPerCtryAddr()) || (this
						.getHolderPerCtryAddr() != null
						&& castOther.getHolderPerCtryAddr() != null && this
						.getHolderPerCtryAddr().equals(
								castOther.getHolderPerCtryAddr())))
				&& ((this.getHolderPerCtryTel() == castOther
						.getHolderPerCtryTel()) || (this.getHolderPerCtryTel() != null
						&& castOther.getHolderPerCtryTel() != null && this
						.getHolderPerCtryTel().equals(
								castOther.getHolderPerCtryTel())))
				&& ((this.getRemtRecverCtryCd() == castOther
						.getRemtRecverCtryCd()) || (this.getRemtRecverCtryCd() != null
						&& castOther.getRemtRecverCtryCd() != null && this
						.getRemtRecverCtryCd().equals(
								castOther.getRemtRecverCtryCd())))
				&& ((this.getRemtRecverCtryAddr() == castOther
						.getRemtRecverCtryAddr()) || (this
						.getRemtRecverCtryAddr() != null
						&& castOther.getRemtRecverCtryAddr() != null && this
						.getRemtRecverCtryAddr().equals(
								castOther.getRemtRecverCtryAddr())))
				&& ((this.getAuthedPerCtryCd() == castOther
						.getAuthedPerCtryCd()) || (this.getAuthedPerCtryCd() != null
						&& castOther.getAuthedPerCtryCd() != null && this
						.getAuthedPerCtryCd().equals(
								castOther.getAuthedPerCtryCd())))
				&& ((this.getAuthedPerCtryAddr() == castOther
						.getAuthedPerCtryAddr()) || (this
						.getAuthedPerCtryAddr() != null
						&& castOther.getAuthedPerCtryAddr() != null && this
						.getAuthedPerCtryAddr().equals(
								castOther.getAuthedPerCtryAddr())))
				&& ((this.getHolderPerPostAddr() == castOther
						.getHolderPerPostAddr()) || (this
						.getHolderPerPostAddr() != null
						&& castOther.getHolderPerPostAddr() != null && this
						.getHolderPerPostAddr().equals(
								castOther.getHolderPerPostAddr())))
				&& ((this.getHolderPerOffcTel() == castOther
						.getHolderPerOffcTel()) || (this.getHolderPerOffcTel() != null
						&& castOther.getHolderPerOffcTel() != null && this
						.getHolderPerOffcTel().equals(
								castOther.getHolderPerOffcTel())))
				&& ((this.getHolderPerFamlyTel() == castOther
						.getHolderPerFamlyTel()) || (this
						.getHolderPerFamlyTel() != null
						&& castOther.getHolderPerFamlyTel() != null && this
						.getHolderPerFamlyTel().equals(
								castOther.getHolderPerFamlyTel())))
				&& ((this.getHolderPerMobile() == castOther
						.getHolderPerMobile()) || (this.getHolderPerMobile() != null
						&& castOther.getHolderPerMobile() != null && this
						.getHolderPerMobile().equals(
								castOther.getHolderPerMobile())))
				&& ((this.getHolderPerIndPos() == castOther
						.getHolderPerIndPos()) || (this.getHolderPerIndPos() != null
						&& castOther.getHolderPerIndPos() != null && this
						.getHolderPerIndPos().equals(
								castOther.getHolderPerIndPos())))
				&& ((this.getLegalReprName() == castOther.getLegalReprName()) || (this
						.getLegalReprName() != null
						&& castOther.getLegalReprName() != null && this
						.getLegalReprName()
						.equals(castOther.getLegalReprName())))
				&& ((this.getNeedSponsorAmt() == castOther.getNeedSponsorAmt()) || (this
						.getNeedSponsorAmt() != null
						&& castOther.getNeedSponsorAmt() != null && this
						.getNeedSponsorAmt().equals(
								castOther.getNeedSponsorAmt())))
				&& ((this.getActualStockPercent() == castOther
						.getActualStockPercent()) || (this
						.getActualStockPercent() != null
						&& castOther.getActualStockPercent() != null && this
						.getActualStockPercent().equals(
								castOther.getActualStockPercent())))
				&& ((this.getIsRptMerge() == castOther.getIsRptMerge()) || (this
						.getIsRptMerge() != null
						&& castOther.getIsRptMerge() != null && this
						.getIsRptMerge().equals(castOther.getIsRptMerge())))
				&& ((this.getIsReported() == castOther.getIsReported()) || (this
						.getIsReported() != null
						&& castOther.getIsReported() != null && this
						.getIsReported().equals(castOther.getIsReported())))
				&& ((this.getIsRegAtUsa() == castOther.getIsRegAtUsa()) || (this
						.getIsRegAtUsa() != null
						&& castOther.getIsRegAtUsa() != null && this
						.getIsRegAtUsa().equals(castOther.getIsRegAtUsa())))
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
				+ (getHolderId() == null ? 0 : this.getHolderId().hashCode());
		result = 37 * result
				+ (getCustId() == null ? 0 : this.getCustId().hashCode());
		result = 37
				* result
				+ (getHolderType() == null ? 0 : this.getHolderType()
						.hashCode());
		result = 37
				* result
				+ (getHolderName() == null ? 0 : this.getHolderName()
						.hashCode());
		result = 37 * result
				+ (getIdentType() == null ? 0 : this.getIdentType().hashCode());
		result = 37 * result
				+ (getIdentNo() == null ? 0 : this.getIdentNo().hashCode());
		result = 37
				* result
				+ (getIdentExpiredDate() == null ? 0 : this
						.getIdentExpiredDate().hashCode());
		result = 37 * result
				+ (getBirthday() == null ? 0 : this.getBirthday().hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
		result = 37
				* result
				+ (getIsOffenceFlag() == null ? 0 : this.getIsOffenceFlag()
						.hashCode());
		result = 37
				* result
				+ (getSponsorKind() == null ? 0 : this.getSponsorKind()
						.hashCode());
		result = 37
				* result
				+ (getSponsorAmt() == null ? 0 : this.getSponsorAmt()
						.hashCode());
		result = 37
				* result
				+ (getSponsorCurr() == null ? 0 : this.getSponsorCurr()
						.hashCode());
		result = 37
				* result
				+ (getSponsorPercent() == null ? 0 : this.getSponsorPercent()
						.hashCode());
		result = 37
				* result
				+ (getSponsorDate() == null ? 0 : this.getSponsorDate()
						.hashCode());
		result = 37
				* result
				+ (getIsCheckFlag() == null ? 0 : this.getIsCheckFlag()
						.hashCode());
		result = 37
				* result
				+ (getStockPercent() == null ? 0 : this.getStockPercent()
						.hashCode());
		result = 37
				* result
				+ (getCountryCode() == null ? 0 : this.getCountryCode()
						.hashCode());
		result = 37
				* result
				+ (getHolderOrgAddr() == null ? 0 : this.getHolderOrgAddr()
						.hashCode());
		result = 37
				* result
				+ (getHolderOrgRegAddr() == null ? 0 : this
						.getHolderOrgRegAddr().hashCode());
		result = 37
				* result
				+ (getHolderOrgTel() == null ? 0 : this.getHolderOrgTel()
						.hashCode());
		result = 37
				* result
				+ (getHolderPerGender() == null ? 0 : this.getHolderPerGender()
						.hashCode());
		result = 37
				* result
				+ (getHolderPerBirthLocale() == null ? 0 : this
						.getHolderPerBirthLocale().hashCode());
		result = 37
				* result
				+ (getHolderPerCtryAddr() == null ? 0 : this
						.getHolderPerCtryAddr().hashCode());
		result = 37
				* result
				+ (getHolderPerCtryTel() == null ? 0 : this
						.getHolderPerCtryTel().hashCode());
		result = 37
				* result
				+ (getRemtRecverCtryCd() == null ? 0 : this
						.getRemtRecverCtryCd().hashCode());
		result = 37
				* result
				+ (getRemtRecverCtryAddr() == null ? 0 : this
						.getRemtRecverCtryAddr().hashCode());
		result = 37
				* result
				+ (getAuthedPerCtryCd() == null ? 0 : this.getAuthedPerCtryCd()
						.hashCode());
		result = 37
				* result
				+ (getAuthedPerCtryAddr() == null ? 0 : this
						.getAuthedPerCtryAddr().hashCode());
		result = 37
				* result
				+ (getHolderPerPostAddr() == null ? 0 : this
						.getHolderPerPostAddr().hashCode());
		result = 37
				* result
				+ (getHolderPerOffcTel() == null ? 0 : this
						.getHolderPerOffcTel().hashCode());
		result = 37
				* result
				+ (getHolderPerFamlyTel() == null ? 0 : this
						.getHolderPerFamlyTel().hashCode());
		result = 37
				* result
				+ (getHolderPerMobile() == null ? 0 : this.getHolderPerMobile()
						.hashCode());
		result = 37
				* result
				+ (getHolderPerIndPos() == null ? 0 : this.getHolderPerIndPos()
						.hashCode());
		result = 37
				* result
				+ (getLegalReprName() == null ? 0 : this.getLegalReprName()
						.hashCode());
		result = 37
				* result
				+ (getNeedSponsorAmt() == null ? 0 : this.getNeedSponsorAmt()
						.hashCode());
		result = 37
				* result
				+ (getActualStockPercent() == null ? 0 : this
						.getActualStockPercent().hashCode());
		result = 37
				* result
				+ (getIsRptMerge() == null ? 0 : this.getIsRptMerge()
						.hashCode());
		result = 37
				* result
				+ (getIsReported() == null ? 0 : this.getIsReported()
						.hashCode());
		result = 37
				* result
				+ (getIsRegAtUsa() == null ? 0 : this.getIsRegAtUsa()
						.hashCode());
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