package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the GUARANTYCONTRACT database table.
 * 
 */
@Entity
@Table(name="GUARANTYCONTRACT")
public class Guarantycontract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="ASSURE_FORM", length=20)
	private String assureForm;

	@Column(name="ASSURE_TYPE", length=20)
	private String assureType;

	@Column(name="BRANCH_NO", length=18)
	private String branchNo;

	@Column(name="CONTR_NO", length=18)
	private String contrNo;

	@Column(name="CONTR_STAT", length=20)
	private String contrStat;

	@Column(name="CONTR_TYPE", length=20)
	private String contrType;

	@Column(name="EFFECTIVE_DATE", length=20)
	private String effectiveDate;

	@Column(name="END_DATE", length=20)
	private String endDate;

	@Column(name="GUARANTEE_AMT", precision=17, scale=2)
	private BigDecimal guaranteeAmt;

	@Column(name="GUARANTOR_IDENT_NO", length=40)
	private String guarantorIdentNo;

	@Column(name="GUARANTOR_IDENT_TYPE", length=20)
	private String guarantorIdentType;

	@Column(name="GUARANTOR_NAME", length=18)
	private String guarantorName;

	@Column(name="GUARANTOR_NO", length=18)
	private String guarantorNo;

	@Column(name="GUARANTY_CURR", length=20)
	private String guarantyCurr;

	@Column(name="GUARANTY_TYPE", length=20)
	private String guarantyType;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="PROD_CODE", length=18)
	private String prodCode;

	@Column(name="SIGN_DATE", length=20)
	private String signDate;

	@Column(name="TELLER_NO", length=18)
	private String tellerNo;

	@Column(name="TOTAL_GUARANTEE_AMT", precision=17, scale=2)
	private BigDecimal totalGuaranteeAmt;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(name="USED_GUARANTEE_AMT", precision=17, scale=2)
	private BigDecimal usedGuaranteeAmt;

	@Column(name="WARRANTEE_IDENT_NO", length=40)
	private String warranteeIdentNo;

	@Column(name="WARRANTEE_IDENT_TYPE", length=20)
	private String warranteeIdentType;

	@Column(name="WARRANTEE_NAME", length=18)
	private String warranteeName;

	@Column(name="WARRANTEE_NO", length=18)
	private String warranteeNo;

    public Guarantycontract() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getAssureForm() {
		return this.assureForm;
	}

	public void setAssureForm(String assureForm) {
		this.assureForm = assureForm;
	}

	public String getAssureType() {
		return this.assureType;
	}

	public void setAssureType(String assureType) {
		this.assureType = assureType;
	}

	public String getBranchNo() {
		return this.branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getContrNo() {
		return this.contrNo;
	}

	public void setContrNo(String contrNo) {
		this.contrNo = contrNo;
	}

	public String getContrStat() {
		return this.contrStat;
	}

	public void setContrStat(String contrStat) {
		this.contrStat = contrStat;
	}

	public String getContrType() {
		return this.contrType;
	}

	public void setContrType(String contrType) {
		this.contrType = contrType;
	}

	public String getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getGuaranteeAmt() {
		return this.guaranteeAmt;
	}

	public void setGuaranteeAmt(BigDecimal guaranteeAmt) {
		this.guaranteeAmt = guaranteeAmt;
	}

	public String getGuarantorIdentNo() {
		return this.guarantorIdentNo;
	}

	public void setGuarantorIdentNo(String guarantorIdentNo) {
		this.guarantorIdentNo = guarantorIdentNo;
	}

	public String getGuarantorIdentType() {
		return this.guarantorIdentType;
	}

	public void setGuarantorIdentType(String guarantorIdentType) {
		this.guarantorIdentType = guarantorIdentType;
	}

	public String getGuarantorName() {
		return this.guarantorName;
	}

	public void setGuarantorName(String guarantorName) {
		this.guarantorName = guarantorName;
	}

	public String getGuarantorNo() {
		return this.guarantorNo;
	}

	public void setGuarantorNo(String guarantorNo) {
		this.guarantorNo = guarantorNo;
	}

	public String getGuarantyCurr() {
		return this.guarantyCurr;
	}

	public void setGuarantyCurr(String guarantyCurr) {
		this.guarantyCurr = guarantyCurr;
	}

	public String getGuarantyType() {
		return this.guarantyType;
	}

	public void setGuarantyType(String guarantyType) {
		this.guarantyType = guarantyType;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getProdCode() {
		return this.prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getSignDate() {
		return this.signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getTellerNo() {
		return this.tellerNo;
	}

	public void setTellerNo(String tellerNo) {
		this.tellerNo = tellerNo;
	}

	public BigDecimal getTotalGuaranteeAmt() {
		return this.totalGuaranteeAmt;
	}

	public void setTotalGuaranteeAmt(BigDecimal totalGuaranteeAmt) {
		this.totalGuaranteeAmt = totalGuaranteeAmt;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public BigDecimal getUsedGuaranteeAmt() {
		return this.usedGuaranteeAmt;
	}

	public void setUsedGuaranteeAmt(BigDecimal usedGuaranteeAmt) {
		this.usedGuaranteeAmt = usedGuaranteeAmt;
	}

	public String getWarranteeIdentNo() {
		return this.warranteeIdentNo;
	}

	public void setWarranteeIdentNo(String warranteeIdentNo) {
		this.warranteeIdentNo = warranteeIdentNo;
	}

	public String getWarranteeIdentType() {
		return this.warranteeIdentType;
	}

	public void setWarranteeIdentType(String warranteeIdentType) {
		this.warranteeIdentType = warranteeIdentType;
	}

	public String getWarranteeName() {
		return this.warranteeName;
	}

	public void setWarranteeName(String warranteeName) {
		this.warranteeName = warranteeName;
	}

	public String getWarranteeNo() {
		return this.warranteeNo;
	}

	public void setWarranteeNo(String warranteeNo) {
		this.warranteeNo = warranteeNo;
	}

}