/**
*@description 主申报人（关联人）主表（临时表）
*@author:dongyi
*@since:2014-08-16
*@checkedby:
*/
package com.yuchengtech.bcrm.custmanager.model;

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
 * The persistent class for the ACRM_F_CR_DECLARANT_INFO_TEMP database table.
 *  主申报人（关联人）主表（临时表）
 */
@Entity
@Table(name="ACRM_F_CR_DECLARANT_INFO_TEMP")
public class AcrmFCrDeclarantInfoTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CR_DECLARANT_INFO_TEMP_MAINID_GENERATOR",sequenceName="ID_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CR_DECLARANT_INFO_TEMP_MAINID_GENERATOR")
	@Column(name="MAIN_ID")
	private long mainId;

    @Temporal( TemporalType.DATE)
	@Column(name="CANCEL_DATE")
	private Date cancelDate;

	@Column(name="CANCEL_STATE")
	private String cancelState;

	@Column(name="CANCLE_CAUSE")
	private String cancleCause;

	@Column(name="CHANGE_TYPE")
	private String changeType;

	@Column(name="CONTACT_ADDR")
	private String contactAddr;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	private String creator;

	@Column(name="DECLARANT_ATTR")
	private String declarantAttr;

	@Column(name="DECLARANT_BANK_REL")
	private String declarantBankRel;

	@Column(name="DECLARANT_NAME")
	private String declarantName;

    @Temporal( TemporalType.DATE)
	@Column(name="DECLARE_DATE")
	private Date declareDate;

	@Column(name="DECLARE_STATUS")
	private String declareStatus;

    @Temporal( TemporalType.DATE)
	@Column(name="EFFECT_DATE")
	private Date effectDate;

	private String email;

	@Column(name="IDENT_NO")
	private String identNo;

	@Column(name="IDENT_TYPE")
	private String identType;

	@Column(name="IS_COMMECIAL_BANK")
	private String isCommecialBank;

    @Temporal( TemporalType.DATE)
	@Column(name="LAST_UPDATE_TM")
	private Date lastUpdateTm;

	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;

	private String remark;

    @Temporal( TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="STOCK_RATIO")
	private BigDecimal stockRatio;

	private String tel;

    public AcrmFCrDeclarantInfoTemp() {
    }

	public long getMainId() {
		return this.mainId;
	}

	public void setMainId(long mainId) {
		this.mainId = mainId;
	}

	public Date getCancelDate() {
		return this.cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelState() {
		return this.cancelState;
	}

	public void setCancelState(String cancelState) {
		this.cancelState = cancelState;
	}

	public String getCancleCause() {
		return this.cancleCause;
	}

	public void setCancleCause(String cancleCause) {
		this.cancleCause = cancleCause;
	}

	public String getChangeType() {
		return this.changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getContactAddr() {
		return this.contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getDeclarantAttr() {
		return this.declarantAttr;
	}

	public void setDeclarantAttr(String declarantAttr) {
		this.declarantAttr = declarantAttr;
	}

	public String getDeclarantBankRel() {
		return this.declarantBankRel;
	}

	public void setDeclarantBankRel(String declarantBankRel) {
		this.declarantBankRel = declarantBankRel;
	}

	public String getDeclarantName() {
		return this.declarantName;
	}

	public void setDeclarantName(String declarantName) {
		this.declarantName = declarantName;
	}

	public Date getDeclareDate() {
		return this.declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public String getDeclareStatus() {
		return this.declareStatus;
	}

	public void setDeclareStatus(String declareStatus) {
		this.declareStatus = declareStatus;
	}

	public Date getEffectDate() {
		return this.effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getIsCommecialBank() {
		return this.isCommecialBank;
	}

	public void setIsCommecialBank(String isCommecialBank) {
		this.isCommecialBank = isCommecialBank;
	}

	public Date getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Date lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getStockRatio() {
		return this.stockRatio;
	}

	public void setStockRatio(BigDecimal stockRatio) {
		this.stockRatio = stockRatio;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}