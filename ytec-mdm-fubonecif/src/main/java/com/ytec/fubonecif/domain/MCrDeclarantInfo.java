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
 * MCrDeclarantInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_CR_DECLARANT_INFO")
public class MCrDeclarantInfo implements java.io.Serializable {

	// Fields

	private BigDecimal mainId;
	private String declarantName;
	private String declarantAttr;
	private String identType;
	private String identNo;
	private String tel;
	private String email;
	private String contactAddr;
	private String declarantBankRel;
	private Date startDate;
	private Double stockRatio;
	private String isCommecialBank;
	private String cancelState;
	private Date effectDate;
	private Date cancelDate;
	private String changeType;
	private String declareStatus;
	private Date declareDate;
	private String cancleCause;
	private String remark;
	private Date createDate;
	private String creator;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MCrDeclarantInfo() {
	}

	/** minimal constructor */
	public MCrDeclarantInfo(BigDecimal mainId) {
		this.mainId = mainId;
	}

	/** full constructor */
	public MCrDeclarantInfo(BigDecimal mainId, String declarantName,
			String declarantAttr, String identType, String identNo, String tel,
			String email, String contactAddr, String declarantBankRel,
			Date startDate, Double stockRatio, String isCommecialBank,
			String cancelState, Date effectDate, Date cancelDate,
			String changeType, String declareStatus, Date declareDate,
			String cancleCause, String remark, Date createDate, String creator,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.mainId = mainId;
		this.declarantName = declarantName;
		this.declarantAttr = declarantAttr;
		this.identType = identType;
		this.identNo = identNo;
		this.tel = tel;
		this.email = email;
		this.contactAddr = contactAddr;
		this.declarantBankRel = declarantBankRel;
		this.startDate = startDate;
		this.stockRatio = stockRatio;
		this.isCommecialBank = isCommecialBank;
		this.cancelState = cancelState;
		this.effectDate = effectDate;
		this.cancelDate = cancelDate;
		this.changeType = changeType;
		this.declareStatus = declareStatus;
		this.declareDate = declareDate;
		this.cancleCause = cancleCause;
		this.remark = remark;
		this.createDate = createDate;
		this.creator = creator;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "MAIN_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getMainId() {
		return this.mainId;
	}

	public void setMainId(BigDecimal mainId) {
		this.mainId = mainId;
	}

	@Column(name = "DECLARANT_NAME", length = 80)
	public String getDeclarantName() {
		return this.declarantName;
	}

	public void setDeclarantName(String declarantName) {
		this.declarantName = declarantName;
	}

	@Column(name = "DECLARANT_ATTR", length = 20)
	public String getDeclarantAttr() {
		return this.declarantAttr;
	}

	public void setDeclarantAttr(String declarantAttr) {
		this.declarantAttr = declarantAttr;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "STOCK_RATIO", precision = 10, scale = 4)
	public Double getStockRatio() {
		return this.stockRatio;
	}

	public void setStockRatio(Double stockRatio) {
		this.stockRatio = stockRatio;
	}

	@Column(name = "IS_COMMECIAL_BANK", length = 1)
	public String getIsCommecialBank() {
		return this.isCommecialBank;
	}

	public void setIsCommecialBank(String isCommecialBank) {
		this.isCommecialBank = isCommecialBank;
	}

	@Column(name = "CANCEL_STATE", length = 20)
	public String getCancelState() {
		return this.cancelState;
	}

	public void setCancelState(String cancelState) {
		this.cancelState = cancelState;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EFFECT_DATE", length = 7)
	public Date getEffectDate() {
		return this.effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CANCEL_DATE", length = 7)
	public Date getCancelDate() {
		return this.cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	@Column(name = "CHANGE_TYPE", length = 20)
	public String getChangeType() {
		return this.changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	@Column(name = "DECLARE_STATUS", length = 1)
	public String getDeclareStatus() {
		return this.declareStatus;
	}

	public void setDeclareStatus(String declareStatus) {
		this.declareStatus = declareStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DECLARE_DATE", length = 7)
	public Date getDeclareDate() {
		return this.declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	@Column(name = "CANCLE_CAUSE", length = 200)
	public String getCancleCause() {
		return this.cancleCause;
	}

	public void setCancleCause(String cancleCause) {
		this.cancleCause = cancleCause;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATOR", length = 20)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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