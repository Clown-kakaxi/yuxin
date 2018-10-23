package com.yuchengtech.emp.ecif.customer.entity.customerbaseperson;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

/**
 * 
 * <pre>
 * Title:PersonExtend的实体类
 * Description: 个人信息扩展
 * </pre>
 * 
 * @author zhengyukun zhengyk3@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Entity
@Table(name="PERSONEXTEND")
public class PersonExtend implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="BUILD_LOAN_ORIGINAL_DATE", length=10)
	private String buildLoanOriginalDate;
	
	@Column(name="OTHER_DESC", length=100)
	private String otherDesc;
	
	@Column(name="MONITOR_OBJ_TYPE", length=1)
	private String monitorObjType;
	
	@Column(name="ADMIN_VILLAGE_NO", length=20)
	private String adminVillageNo;
	
	@Column(name="ADMIN_VILLAGE_NAME", length=200)
	private String adminVillageName;
	
	@Column(name="INTRODUCTION", length=200)
	private String introduction;
	
	@Column(name="ASSETS_DEBT_DETAIL", length=200)
	private String assetsDebtDetail;
	
	@Column(name="MAIN_INCOME", length=200)
	private String mainIncome;
	
	@Column(name="SALARY_ACCOUNT", length=40)
	private String salaryAccount;
	
	@Column(name="SALARY_ACCOUNT_BANK", length=200)
	private String salaryAccountBank;
	
	@Column(name="LOAN_BODY_TYPE", length=2)
	private String loanBodyType;
	
	@Column(name="PERSON_AUTH_LEVEL", length=20)
	private String personAuthLevel;
	
	@Column(name="FINANCE_BELONG", length=18)
	private String financeBelong;
	
	@Column(name="TEMP_SAVE_FLAG", length=18)
	private String tempSaveFlag;
	

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getBuildLoanOriginalDate() {
		return buildLoanOriginalDate;
	}

	public void setBuildLoanOriginalDate(String buildLoanOriginalDate) {
		this.buildLoanOriginalDate = buildLoanOriginalDate;
	}

	public String getOtherDesc() {
		return otherDesc;
	}

	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}

	public String getMonitorObjType() {
		return monitorObjType;
	}

	public void setMonitorObjType(String monitorObjType) {
		this.monitorObjType = monitorObjType;
	}

	public String getAdminVillageNo() {
		return adminVillageNo;
	}

	public void setAdminVillageNo(String adminVillageNo) {
		this.adminVillageNo = adminVillageNo;
	}

	public String getAdminVillageName() {
		return adminVillageName;
	}

	public void setAdminVillageName(String adminVillageName) {
		this.adminVillageName = adminVillageName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getAssetsDebtDetail() {
		return assetsDebtDetail;
	}

	public void setAssetsDebtDetail(String assetsDebtDetail) {
		this.assetsDebtDetail = assetsDebtDetail;
	}

	public String getMainIncome() {
		return mainIncome;
	}

	public void setMainIncome(String mainIncome) {
		this.mainIncome = mainIncome;
	}

	public String getSalaryAccount() {
		return salaryAccount;
	}

	public void setSalaryAccount(String salaryAccount) {
		this.salaryAccount = salaryAccount;
	}

	public String getSalaryAccountBank() {
		return salaryAccountBank;
	}

	public void setSalaryAccountBank(String salaryAccountBank) {
		this.salaryAccountBank = salaryAccountBank;
	}

	public String getLoanBodyType() {
		return loanBodyType;
	}

	public void setLoanBodyType(String loanBodyType) {
		this.loanBodyType = loanBodyType;
	}

	public String getPersonAuthLevel() {
		return personAuthLevel;
	}

	public void setPersonAuthLevel(String personAuthLevel) {
		this.personAuthLevel = personAuthLevel;
	}

	public String getFinanceBelong() {
		return financeBelong;
	}

	public void setFinanceBelong(String financeBelong) {
		this.financeBelong = financeBelong;
	}

	public String getTempSaveFlag() {
		return tempSaveFlag;
	}

	public void setTempSaveFlag(String tempSaveFlag) {
		this.tempSaveFlag = tempSaveFlag;
	}
	
}
