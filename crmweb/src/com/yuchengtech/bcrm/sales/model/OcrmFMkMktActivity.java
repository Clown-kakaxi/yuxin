package com.yuchengtech.bcrm.sales.model;


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
 * The persistent class for the OCRM_F_MK_MKT_ACTIVITY database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MK_MKT_ACTIVITY")
public class OcrmFMkMktActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_MK_MKT_ACTIVITY_MKTACTIID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_MK_MKT_ACTIVITY_MKTACTIID_GENERATOR")
	@Column(name="MKT_ACTI_ID")
	private Long mktActiId;

	@Column(name="ACTI_CUST_DESC")
	private String actiCustDesc;

	@Column(name="ACTI_OPER_DESC")
	private String actiOperDesc;

	@Column(name="ACTI_PROD_DESC")
	private String actiProdDesc;

	@Column(name="ACTI_REMARK")
	private String actiRemark;

    @Temporal( TemporalType.DATE)
	@Column(name="AEND_DATE")
	private Date aendDate;

    @Temporal( TemporalType.DATE)
	@Column(name="ASTART_DATE")
	private Date astartDate;

    @Temporal( TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="MKT_ACTI_ADDR")
	private String mktActiAddr;

	@Column(name="MKT_ACTI_AIM")
	private String mktActiAim;

	@Column(name="MKT_ACTI_CONT")
	private String mktActiCont;

	@Column(name="MKT_ACTI_COST")
	private BigDecimal mktActiCost;

	@Column(name="MKT_ACTI_MODE")
	private String mktActiMode;

	@Column(name="MKT_ACTI_NAME")
	private String mktActiName;

	@Column(name="MKT_ACTI_STAT")
	private String mktActiStat;
	
	@Column(name="MKT_APP_STATE")
	private String mktAppState;

	@Column(name="MKT_ACTI_TEAM")
	private String mktActiTeam;

	@Column(name="MKT_ACTI_TYPE")
	private String mktActiType;

    @Temporal( TemporalType.DATE)
	@Column(name="PEND_DATE")
	private Date pendDate;

    @Temporal( TemporalType.DATE)
	@Column(name="PSTART_DATE")
	private Date pstartDate;

    @Temporal( TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;

	@Column(name="CREATE_ORG")
	private String createOrg;
	
	@Column(name="MKT_CHANEL")
	private String mktChanel;
	
	@Column(name="MKT_ACTI_CODE")
	private String mktActiCode;

	public Long getMktActiId() {
		return mktActiId;
	}

	public void setMktActiId(Long mktActiId) {
		this.mktActiId = mktActiId;
	}

	public String getMktChanel() {
		return mktChanel;
	}

	public void setMktChanel(String mktChanel) {
		this.mktChanel = mktChanel;
	}

	public String getActiCustDesc() {
		return actiCustDesc;
	}

	public void setActiCustDesc(String actiCustDesc) {
		this.actiCustDesc = actiCustDesc;
	}

	public String getActiOperDesc() {
		return actiOperDesc;
	}

	public void setActiOperDesc(String actiOperDesc) {
		this.actiOperDesc = actiOperDesc;
	}

	public String getActiProdDesc() {
		return actiProdDesc;
	}

	public void setActiProdDesc(String actiProdDesc) {
		this.actiProdDesc = actiProdDesc;
	}

	public String getActiRemark() {
		return actiRemark;
	}

	public void setActiRemark(String actiRemark) {
		this.actiRemark = actiRemark;
	}

	public Date getAendDate() {
		return aendDate;
	}

	public void setAendDate(Date aendDate) {
		this.aendDate = aendDate;
	}

	public Date getAstartDate() {
		return astartDate;
	}

	public void setAstartDate(Date astartDate) {
		this.astartDate = astartDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getMktActiAddr() {
		return mktActiAddr;
	}

	public void setMktActiAddr(String mktActiAddr) {
		this.mktActiAddr = mktActiAddr;
	}

	public String getMktActiAim() {
		return mktActiAim;
	}

	public void setMktActiAim(String mktActiAim) {
		this.mktActiAim = mktActiAim;
	}

	public String getMktActiCont() {
		return mktActiCont;
	}

	public void setMktActiCont(String mktActiCont) {
		this.mktActiCont = mktActiCont;
	}

	public BigDecimal getMktActiCost() {
		return mktActiCost;
	}

	public void setMktActiCost(BigDecimal mktActiCost) {
		this.mktActiCost = mktActiCost;
	}

	public String getMktActiMode() {
		return mktActiMode;
	}

	public void setMktActiMode(String mktActiMode) {
		this.mktActiMode = mktActiMode;
	}

	public String getMktActiName() {
		return mktActiName;
	}

	public void setMktActiName(String mktActiName) {
		this.mktActiName = mktActiName;
	}

	public String getMktActiStat() {
		return mktActiStat;
	}

	public void setMktActiStat(String mktActiStat) {
		this.mktActiStat = mktActiStat;
	}

	public String getMktAppState() {
		return mktAppState;
	}

	public void setMktAppState(String mktAppState) {
		this.mktAppState = mktAppState;
	}
	
	public String getMktActiTeam() {
		return mktActiTeam;
	}

	public void setMktActiTeam(String mktActiTeam) {
		this.mktActiTeam = mktActiTeam;
	}

	public String getMktActiType() {
		return mktActiType;
	}

	public void setMktActiType(String mktActiType) {
		this.mktActiType = mktActiType;
	}

	public Date getPendDate() {
		return pendDate;
	}

	public void setPendDate(Date pendDate) {
		this.pendDate = pendDate;
	}

	public Date getPstartDate() {
		return pstartDate;
	}

	public void setPstartDate(Date pstartDate) {
		this.pstartDate = pstartDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreateOrg() {
		return createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}
	
	public String getMktActiCode() {
		return this.mktActiCode;
	}

	public void setMktActiCode(String mktActiCode) {
		this.mktActiCode = mktActiCode;
	}
}