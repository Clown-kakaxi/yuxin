package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCrDeclarantInfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCrDeclarantInfoId implements java.io.Serializable {

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
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCrDeclarantInfoId() {
	}

	/** minimal constructor */
	public HMCrDeclarantInfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCrDeclarantInfoId(BigDecimal mainId, String declarantName,
			String declarantAttr, String identType, String identNo, String tel,
			String email, String contactAddr, String declarantBankRel,
			Date startDate, Double stockRatio, String isCommecialBank,
			String cancelState, Date effectDate, Date cancelDate,
			String changeType, String declareStatus, Date declareDate,
			String cancleCause, String remark, Date createDate, String creator,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo, String hisOperSys,
			String hisOperType, Timestamp hisOperTime, String hisDataDate) {
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
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "MAIN_ID", precision = 22, scale = 0)
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
		if (!(other instanceof HMCrDeclarantInfoId))
			return false;
		HMCrDeclarantInfoId castOther = (HMCrDeclarantInfoId) other;

		return ((this.getMainId() == castOther.getMainId()) || (this
				.getMainId() != null
				&& castOther.getMainId() != null && this.getMainId().equals(
				castOther.getMainId())))
				&& ((this.getDeclarantName() == castOther.getDeclarantName()) || (this
						.getDeclarantName() != null
						&& castOther.getDeclarantName() != null && this
						.getDeclarantName()
						.equals(castOther.getDeclarantName())))
				&& ((this.getDeclarantAttr() == castOther.getDeclarantAttr()) || (this
						.getDeclarantAttr() != null
						&& castOther.getDeclarantAttr() != null && this
						.getDeclarantAttr()
						.equals(castOther.getDeclarantAttr())))
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
				&& ((this.getStartDate() == castOther.getStartDate()) || (this
						.getStartDate() != null
						&& castOther.getStartDate() != null && this
						.getStartDate().equals(castOther.getStartDate())))
				&& ((this.getStockRatio() == castOther.getStockRatio()) || (this
						.getStockRatio() != null
						&& castOther.getStockRatio() != null && this
						.getStockRatio().equals(castOther.getStockRatio())))
				&& ((this.getIsCommecialBank() == castOther
						.getIsCommecialBank()) || (this.getIsCommecialBank() != null
						&& castOther.getIsCommecialBank() != null && this
						.getIsCommecialBank().equals(
								castOther.getIsCommecialBank())))
				&& ((this.getCancelState() == castOther.getCancelState()) || (this
						.getCancelState() != null
						&& castOther.getCancelState() != null && this
						.getCancelState().equals(castOther.getCancelState())))
				&& ((this.getEffectDate() == castOther.getEffectDate()) || (this
						.getEffectDate() != null
						&& castOther.getEffectDate() != null && this
						.getEffectDate().equals(castOther.getEffectDate())))
				&& ((this.getCancelDate() == castOther.getCancelDate()) || (this
						.getCancelDate() != null
						&& castOther.getCancelDate() != null && this
						.getCancelDate().equals(castOther.getCancelDate())))
				&& ((this.getChangeType() == castOther.getChangeType()) || (this
						.getChangeType() != null
						&& castOther.getChangeType() != null && this
						.getChangeType().equals(castOther.getChangeType())))
				&& ((this.getDeclareStatus() == castOther.getDeclareStatus()) || (this
						.getDeclareStatus() != null
						&& castOther.getDeclareStatus() != null && this
						.getDeclareStatus()
						.equals(castOther.getDeclareStatus())))
				&& ((this.getDeclareDate() == castOther.getDeclareDate()) || (this
						.getDeclareDate() != null
						&& castOther.getDeclareDate() != null && this
						.getDeclareDate().equals(castOther.getDeclareDate())))
				&& ((this.getCancleCause() == castOther.getCancleCause()) || (this
						.getCancleCause() != null
						&& castOther.getCancleCause() != null && this
						.getCancleCause().equals(castOther.getCancleCause())))
				&& ((this.getRemark() == castOther.getRemark()) || (this
						.getRemark() != null
						&& castOther.getRemark() != null && this.getRemark()
						.equals(castOther.getRemark())))
				&& ((this.getCreateDate() == castOther.getCreateDate()) || (this
						.getCreateDate() != null
						&& castOther.getCreateDate() != null && this
						.getCreateDate().equals(castOther.getCreateDate())))
				&& ((this.getCreator() == castOther.getCreator()) || (this
						.getCreator() != null
						&& castOther.getCreator() != null && this.getCreator()
						.equals(castOther.getCreator())))
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
				+ (getMainId() == null ? 0 : this.getMainId().hashCode());
		result = 37
				* result
				+ (getDeclarantName() == null ? 0 : this.getDeclarantName()
						.hashCode());
		result = 37
				* result
				+ (getDeclarantAttr() == null ? 0 : this.getDeclarantAttr()
						.hashCode());
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
		result = 37 * result
				+ (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		result = 37
				* result
				+ (getStockRatio() == null ? 0 : this.getStockRatio()
						.hashCode());
		result = 37
				* result
				+ (getIsCommecialBank() == null ? 0 : this.getIsCommecialBank()
						.hashCode());
		result = 37
				* result
				+ (getCancelState() == null ? 0 : this.getCancelState()
						.hashCode());
		result = 37
				* result
				+ (getEffectDate() == null ? 0 : this.getEffectDate()
						.hashCode());
		result = 37
				* result
				+ (getCancelDate() == null ? 0 : this.getCancelDate()
						.hashCode());
		result = 37
				* result
				+ (getChangeType() == null ? 0 : this.getChangeType()
						.hashCode());
		result = 37
				* result
				+ (getDeclareStatus() == null ? 0 : this.getDeclareStatus()
						.hashCode());
		result = 37
				* result
				+ (getDeclareDate() == null ? 0 : this.getDeclareDate()
						.hashCode());
		result = 37
				* result
				+ (getCancleCause() == null ? 0 : this.getCancleCause()
						.hashCode());
		result = 37 * result
				+ (getRemark() == null ? 0 : this.getRemark().hashCode());
		result = 37
				* result
				+ (getCreateDate() == null ? 0 : this.getCreateDate()
						.hashCode());
		result = 37 * result
				+ (getCreator() == null ? 0 : this.getCreator().hashCode());
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