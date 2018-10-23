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
 * MCrRelatePrivyInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CR_RELATE_PRIVY_INFO")
public class MCrRelatePrivyInfo implements java.io.Serializable {

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

	// Constructors

	/** default constructor */
	public MCrRelatePrivyInfo() {
	}

	/** minimal constructor */
	public MCrRelatePrivyInfo(BigDecimal relateId) {
		this.relateId = relateId;
	}

	/** full constructor */
	public MCrRelatePrivyInfo(BigDecimal relateId, String privyName,
			String privyAttribute, BigDecimal mainId,
			String relateDeclarantRel, String identType, String identNo,
			String tel, String email, String contactAddr,
			String declarantBankRel, String isCommecialBank, Double stockRatio,
			Date effectDate, String cancelState, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo) {
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
	}

	// Property accessors
	@Id
	@Column(name = "RELATE_ID", unique = true, nullable = false, precision = 22, scale = 0)
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

}