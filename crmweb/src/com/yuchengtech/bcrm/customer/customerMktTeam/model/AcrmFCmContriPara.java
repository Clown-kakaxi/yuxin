package com.yuchengtech.bcrm.customer.customerMktTeam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ACRM_F_CM_CONTRI_PARA database table.
 * 客户经理贡献度总量目标参数表
 */
@Entity
@Table(name="ACRM_F_CM_CONTRI_PARA")
public class AcrmFCmContriPara implements Serializable {
	private static final long serialVersionUID = 5112428719618384947L;

	/**主键 */
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
//	@Column(name = "ID", nullable=false)
//	private String id;

	/**客户经理编号*/
    @Column(name="USER_ID")
	private String userId;
    
	/**机构号*/
    @Column(name="ORG_ID")
	private String orgId;

	/**机构名称*/
	@Column(name="ORG_NAME")
	private String orgName;
	
	/**客户经理名称*/
	@Column(name="USER_NAME")
	private String userName;
	
	/**时间*/
    @Temporal( TemporalType.DATE)
	@Column(name="TIME")
	private Date time;
	
	/**总贡献度本月目标总量*/
	@Column(name="TOTAL_CONTRI_MONTH")
	private String totalContriMonth;
	
	/**总贡献度本季目标总量*/
	@Column(name="TOTAL_CONTRI_SEASON")
	private String totalContriSeason;
	
	/**总贡献度本年目标总量*/
	@Column(name="TOTAL_CONTRI_YEAR")
	private String totalContriYear;
	
	/**存款时点增量本月目标总量*/
	@Column(name="SAVE_INCRE_MONTH")
	private String saveIncreMonth;
	
	/**存款时点增量本季目标总量*/
	@Column(name="SAVE_INCRE_SEASON")
	private String saveIncreSeason;
	
	/**存款时点增量本年目标总量*/
	@Column(name="SAVE_INCRE_YEAR")
	private String saveIncreYear;
	
	/**贷款动拨金额本月目标总量*/
	@Column(name="LOAN_MONTH")
	private String loanMonth;
	
	/**贷款动拨金额本季目标总量人*/
	@Column(name="LAON_SEASON")
	private String laonSeason;
	
	/**贷款动拨金额本年目标总量*/
	@Column(name="LOAN_YEAR")
	private String loanYear;
	
	/**保险销售额本月目标总量*/
	@Column(name="INSURANCE_SALE_MONTH")
	private String insuranceSaleMonth;
	
	/**保险销售额本季目标总量*/
	@Column(name="INSURANCE_SALE_SEASON")
	private String insuranceSaleSeason;
	
	/**保险销售额本年目标总量*/
	@Column(name="INSURANCE_SALE_YEAR")
	private String insuranceSaleYear;

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getTotalContriMonth() {
		return totalContriMonth;
	}

	public void setTotalContriMonth(String totalContriMonth) {
		this.totalContriMonth = totalContriMonth;
	}

	public String getTotalContriSeason() {
		return totalContriSeason;
	}

	public void setTotalContriSeason(String totalContriSeason) {
		this.totalContriSeason = totalContriSeason;
	}

	public String getTotalContriYear() {
		return totalContriYear;
	}

	public void setTotalContriYear(String totalContriYear) {
		this.totalContriYear = totalContriYear;
	}

	public String getSaveIncreMonth() {
		return saveIncreMonth;
	}

	public void setSaveIncreMonth(String saveIncreMonth) {
		this.saveIncreMonth = saveIncreMonth;
	}

	public String getSaveIncreSeason() {
		return saveIncreSeason;
	}

	public void setSaveIncreSeason(String saveIncreSeason) {
		this.saveIncreSeason = saveIncreSeason;
	}

	public String getSaveIncreYear() {
		return saveIncreYear;
	}

	public void setSaveIncreYear(String saveIncreYear) {
		this.saveIncreYear = saveIncreYear;
	}

	public String getLoanMonth() {
		return loanMonth;
	}

	public void setLoanMonth(String loanMonth) {
		this.loanMonth = loanMonth;
	}

	public String getLaonSeason() {
		return laonSeason;
	}

	public void setLaonSeason(String laonSeason) {
		this.laonSeason = laonSeason;
	}

	public String getLoanYear() {
		return loanYear;
	}

	public void setLoanYear(String loanYear) {
		this.loanYear = loanYear;
	}

	public String getInsuranceSaleMonth() {
		return insuranceSaleMonth;
	}

	public void setInsuranceSaleMonth(String insuranceSaleMonth) {
		this.insuranceSaleMonth = insuranceSaleMonth;
	}

	public String getInsuranceSaleSeason() {
		return insuranceSaleSeason;
	}

	public void setInsuranceSaleSeason(String insuranceSaleSeason) {
		this.insuranceSaleSeason = insuranceSaleSeason;
	}

	public String getInsuranceSaleYear() {
		return insuranceSaleYear;
	}

	public void setInsuranceSaleYear(String insuranceSaleYear) {
		this.insuranceSaleYear = insuranceSaleYear;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}