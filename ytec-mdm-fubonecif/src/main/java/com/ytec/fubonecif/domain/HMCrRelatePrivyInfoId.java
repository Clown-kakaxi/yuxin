package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCrRelatePrivyInfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCrRelatePrivyInfoId implements java.io.Serializable {

	// Fields

	private BigDecimal relateId;
	private String privyName;
	private String privyAttribute;
	private BigDecimal mainId;
	private String relateDeclarantRel;
	private String identType;
	private String identNo;
	private String tel;
	private String email;
	private String contactAddr;
	private String declarantBankRel;
	private String isCommecialBank;
	private Double stockRatio;
	private Date effectDate;
	private String cancelState;
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
	public HMCrRelatePrivyInfoId() {
	}

	/** minimal constructor */
	public HMCrRelatePrivyInfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCrRelatePrivyInfoId(BigDecimal relateId, String privyName,
			String privyAttribute, BigDecimal mainId,
			String relateDeclarantRel, String identType, String identNo,
			String tel, String email, String contactAddr,
			String declarantBankRel, String isCommecialBank, Double stockRatio,
			Date effectDate, String cancelState, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.relateId = relateId;
		this.privyName = privyName;
		this.privyAttribute = privyAttribute;
		this.mainId = mainId;
		this.relateDeclarantRel = relateDeclarantRel;
		this.identType = identType;
		this.identNo = identNo;
		this.tel = tel;
		this.email = email;
		this.contactAddr = contactAddr;
		this.declarantBankRel = declarantBankRel;
		this.isCommecialBank = isCommecialBank;
		this.stockRatio = stockRatio;
		this.effectDate = effectDate;
		this.cancelState = cancelState;
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

	@Column(name = "RELATE_ID", precision = 22, scale = 0)
	public BigDecimal getRelateId() {
		return this.relateId;
	}

	public void setRelateId(BigDecimal relateId) {
		this.relateId = relateId;
	}

	@Column(name = "PRIVY_NAME", length = 80)
	public String getPrivyName() {
		return this.privyName;
	}

	public void setPrivyName(String privyName) {
		this.privyName = privyName;
	}

	@Column(name = "PRIVY_ATTRIBUTE", length = 20)
	public String getPrivyAttribute() {
		return this.privyAttribute;
	}

	public void setPrivyAttribute(String privyAttribute) {
		this.privyAttribute = privyAttribute;
	}

	@Column(name = "MAIN_ID", precision = 22, scale = 0)
	public BigDecimal getMainId() {
		return this.mainId;
	}

	public void setMainId(BigDecimal mainId) {
		this.mainId = mainId;
	}

	@Column(name = "RELATE_DECLARANT_REL", length = 20)
	public String getRelateDeclarantRel() {
		return this.relateDeclarantRel;
	}

	public void setRelateDeclarantRel(String relateDeclarantRel) {
		this.relateDeclarantRel = relateDeclarantRel;
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

	@Column(name = "TEL", length = 32)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CONTACT_ADDR", length = 200)
	public String getContactAddr() {
		return this.contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}

	@Column(name = "DECLARANT_BANK_REL", length = 20)
	public String getDeclarantBankRel() {
		return this.declarantBankRel;
	}

	public void setDeclarantBankRel(String declarantBankRel) {
		this.declarantBankRel = declarantBankRel;
	}

	@Column(name = "IS_COMMECIAL_BANK", length = 1)
	public String getIsCommecialBank() {
		return this.isCommecialBank;
	}

	public void setIsCommecialBank(String isCommecialBank) {
		this.isCommecialBank = isCommecialBank;
	}

	@Column(name = "STOCK_RATIO", precision = 10, scale = 4)
	public Double getStockRatio() {
		return this.stockRatio;
	}

	public void setStockRatio(Double stockRatio) {
		this.stockRatio = stockRatio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECT_DATE", length = 7)
	public Date getEffectDate() {
		return this.effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	@Column(name = "CANCEL_STATE", length = 20)
	public String getCancelState() {
		return this.cancelState;
	}

	public void setCancelState(String cancelState) {
		this.cancelState = cancelState;
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
		if (!(other instanceof HMCrRelatePrivyInfoId))
			return false;
		HMCrRelatePrivyInfoId castOther = (HMCrRelatePrivyInfoId) other;

		return ((this.getRelateId() == castOther.getRelateId()) || (this
				.getRelateId() != null
				&& castOther.getRelateId() != null && this.getRelateId()
				.equals(castOther.getRelateId())))
				&& ((this.getPrivyName() == castOther.getPrivyName()) || (this
						.getPrivyName() != null
						&& castOther.getPrivyName() != null && this
						.getPrivyName().equals(castOther.getPrivyName())))
				&& ((this.getPrivyAttribute() == castOther.getPrivyAttribute()) || (this
						.getPrivyAttribute() != null
						&& castOther.getPrivyAttribute() != null && this
						.getPrivyAttribute().equals(
								castOther.getPrivyAttribute())))
				&& ((this.getMainId() == castOther.getMainId()) || (this
						.getMainId() != null
						&& castOther.getMainId() != null && this.getMainId()
						.equals(castOther.getMainId())))
				&& ((this.getRelateDeclarantRel() == castOther
						.getRelateDeclarantRel()) || (this
						.getRelateDeclarantRel() != null
						&& castOther.getRelateDeclarantRel() != null && this
						.getRelateDeclarantRel().equals(
								castOther.getRelateDeclarantRel())))
				&& ((this.getIdentType() == castOther.getIdentType()) || (this
						.getIdentType() != null
						&& castOther.getIdentType() != null && this
						.getIdentType().equals(castOther.getIdentType())))
				&& ((this.getIdentNo() == castOther.getIdentNo()) || (this
						.getIdentNo() != null
						&& castOther.getIdentNo() != null && this.getIdentNo()
						.equals(castOther.getIdentNo())))
				&& ((this.getTel() == castOther.getTel()) || (this.getTel() != null
						&& castOther.getTel() != null && this.getTel().equals(
						castOther.getTel())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null
						&& castOther.getEmail() != null && this.getEmail()
						.equals(castOther.getEmail())))
				&& ((this.getContactAddr() == castOther.getContactAddr()) || (this
						.getContactAddr() != null
						&& castOther.getContactAddr() != null && this
						.getContactAddr().equals(castOther.getContactAddr())))
				&& ((this.getDeclarantBankRel() == castOther
						.getDeclarantBankRel()) || (this.getDeclarantBankRel() != null
						&& castOther.getDeclarantBankRel() != null && this
						.getDeclarantBankRel().equals(
								castOther.getDeclarantBankRel())))
				&& ((this.getIsCommecialBank() == castOther
						.getIsCommecialBank()) || (this.getIsCommecialBank() != null
						&& castOther.getIsCommecialBank() != null && this
						.getIsCommecialBank().equals(
								castOther.getIsCommecialBank())))
				&& ((this.getStockRatio() == castOther.getStockRatio()) || (this
						.getStockRatio() != null
						&& castOther.getStockRatio() != null && this
						.getStockRatio().equals(castOther.getStockRatio())))
				&& ((this.getEffectDate() == castOther.getEffectDate()) || (this
						.getEffectDate() != null
						&& castOther.getEffectDate() != null && this
						.getEffectDate().equals(castOther.getEffectDate())))
				&& ((this.getCancelState() == castOther.getCancelState()) || (this
						.getCancelState() != null
						&& castOther.getCancelState() != null && this
						.getCancelState().equals(castOther.getCancelState())))
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
				+ (getRelateId() == null ? 0 : this.getRelateId().hashCode());
		result = 37 * result
				+ (getPrivyName() == null ? 0 : this.getPrivyName().hashCode());
		result = 37
				* result
				+ (getPrivyAttribute() == null ? 0 : this.getPrivyAttribute()
						.hashCode());
		result = 37 * result
				+ (getMainId() == null ? 0 : this.getMainId().hashCode());
		result = 37
				* result
				+ (getRelateDeclarantRel() == null ? 0 : this
						.getRelateDeclarantRel().hashCode());
		result = 37 * result
				+ (getIdentType() == null ? 0 : this.getIdentType().hashCode());
		result = 37 * result
				+ (getIdentNo() == null ? 0 : this.getIdentNo().hashCode());
		result = 37 * result
				+ (getTel() == null ? 0 : this.getTel().hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
		result = 37
				* result
				+ (getContactAddr() == null ? 0 : this.getContactAddr()
						.hashCode());
		result = 37
				* result
				+ (getDeclarantBankRel() == null ? 0 : this
						.getDeclarantBankRel().hashCode());
		result = 37
				* result
				+ (getIsCommecialBank() == null ? 0 : this.getIsCommecialBank()
						.hashCode());
		result = 37
				* result
				+ (getStockRatio() == null ? 0 : this.getStockRatio()
						.hashCode());
		result = 37
				* result
				+ (getEffectDate() == null ? 0 : this.getEffectDate()
						.hashCode());
		result = 37
				* result
				+ (getCancelState() == null ? 0 : this.getCancelState()
						.hashCode());
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