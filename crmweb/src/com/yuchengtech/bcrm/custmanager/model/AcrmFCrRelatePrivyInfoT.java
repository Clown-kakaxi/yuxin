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
 * The persistent class for the ACRM_F_CR_RELATE_PRIVY_INFO_T database table.
 * 关联人的关联方从表(临时表)
 */
@Entity
@Table(name="ACRM_F_CR_RELATE_PRIVY_INFO_T")
public class AcrmFCrRelatePrivyInfoT implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACRM_F_CR_RELATE_PRIVY_INFO_T_RELATEID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize=1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CR_RELATE_PRIVY_INFO_T_RELATEID_GENERATOR")
	@Column(name="RELATE_ID")
	private long relateId;

	@Column(name="CANCEL_STATE")
	private String cancelState;

	@Column(name="CONTACT_ADDR")
	private String contactAddr;

	@Column(name="DECLARANT_BANK_REL")
	private String declarantBankRel;

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

	@Column(name="MAIN_ID")
	private BigDecimal mainId;

	@Column(name="PRIVY_ATTRIBUTE")
	private String privyAttribute;

	@Column(name="PRIVY_NAME")
	private String privyName;

	@Column(name="RELATE_DECLARANT_REL")
	private String relateDeclarantRel;

	@Column(name="STOCK_RATIO")
	private BigDecimal stockRatio;

	private String tel;

    public AcrmFCrRelatePrivyInfoT() {
    }

	public long getRelateId() {
		return this.relateId;
	}

	public void setRelateId(long relateId) {
		this.relateId = relateId;
	}

	public String getCancelState() {
		return this.cancelState;
	}

	public void setCancelState(String cancelState) {
		this.cancelState = cancelState;
	}

	public String getContactAddr() {
		return this.contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}

	public String getDeclarantBankRel() {
		return this.declarantBankRel;
	}

	public void setDeclarantBankRel(String declarantBankRel) {
		this.declarantBankRel = declarantBankRel;
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

	public BigDecimal getMainId() {
		return this.mainId;
	}

	public void setMainId(BigDecimal mainId) {
		this.mainId = mainId;
	}

	public String getPrivyAttribute() {
		return this.privyAttribute;
	}

	public void setPrivyAttribute(String privyAttribute) {
		this.privyAttribute = privyAttribute;
	}

	public String getPrivyName() {
		return this.privyName;
	}

	public void setPrivyName(String privyName) {
		this.privyName = privyName;
	}

	public String getRelateDeclarantRel() {
		return this.relateDeclarantRel;
	}

	public void setRelateDeclarantRel(String relateDeclarantRel) {
		this.relateDeclarantRel = relateDeclarantRel;
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