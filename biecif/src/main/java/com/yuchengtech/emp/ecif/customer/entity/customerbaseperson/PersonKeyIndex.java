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
 * Title:PersonKeyIndex的实体类
 * Description: 个人信息
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
@Table(name="M_CI_PER_KEYINDEX")
public class PersonKeyIndex implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="ANNUAL_INCOME")
	private Double annualIncome;
	
	@Column(name="MONTH_SALARY_INCOME")
	private Double monthSalaryIncome;
	
	@Column(name="MONTH_BUSI_INCOME")
	private Double monthBusiIncome;
	
	@Column(name="MONTH_OTHER_INCOME")
	private Double monthOtherIncome;
	
	@Column(name="MONTH_AVG_INCOME")
	private Double monthAvgIncome;
	
	@Column(name="FINANCIAL_ASSETS")
	private Double financialAssets;
	
	@Column(name="DEPOSIT_BAL")
	private Double depositBal;
	
	@Column(name="NATIONAL_DEBT_BAL")
	private Double nationalDebtBal;
	
	@Column(name="FINANCIAL_PROD_BAL")
	private Double financialProdBal;
	
	@Column(name="LOAN_BAL")
	private Double loanBal;
	
	@Column(name="ASSETS")
	private Double assets;
	
	@Column(name="OTHER_BANK_ASSETS")
	private Double otherBankAssets;
	
	@Column(name="OTHER_BANK_DEBT")
	private Double otherBankdebt;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Double getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(Double annualIncome) {
		this.annualIncome = annualIncome;
	}

	public Double getMonthSalaryIncome() {
		return monthSalaryIncome;
	}

	public void setMonthSalaryIncome(Double monthSalaryIncome) {
		this.monthSalaryIncome = monthSalaryIncome;
	}

	public Double getMonthBusiIncome() {
		return monthBusiIncome;
	}

	public void setMonthBusiIncome(Double monthBusiIncome) {
		this.monthBusiIncome = monthBusiIncome;
	}

	public Double getMonthOtherIncome() {
		return monthOtherIncome;
	}

	public void setMonthOtherIncome(Double monthOtherIncome) {
		this.monthOtherIncome = monthOtherIncome;
	}

	public Double getMonthAvgIncome() {
		return monthAvgIncome;
	}

	public void setMonthAvgIncome(Double monthAvgIncome) {
		this.monthAvgIncome = monthAvgIncome;
	}

	public Double getFinancialAssets() {
		return financialAssets;
	}

	public void setFinancialAssets(Double financialAssets) {
		this.financialAssets = financialAssets;
	}

	public Double getDepositBal() {
		return depositBal;
	}

	public void setDepositBal(Double depositBal) {
		this.depositBal = depositBal;
	}

	public Double getNationalDebtBal() {
		return nationalDebtBal;
	}

	public void setNationalDebtBal(Double nationalDebtBal) {
		this.nationalDebtBal = nationalDebtBal;
	}

	public Double getFinancialProdBal() {
		return financialProdBal;
	}

	public void setFinancialProdBal(Double financialProdBal) {
		this.financialProdBal = financialProdBal;
	}

	public Double getLoanBal() {
		return loanBal;
	}

	public void setLoanBal(Double loanBal) {
		this.loanBal = loanBal;
	}

	public Double getAssets() {
		return assets;
	}

	public void setAssets(Double assets) {
		this.assets = assets;
	}

	public Double getOtherBankAssets() {
		return otherBankAssets;
	}

	public void setOtherBankAssets(Double otherBankAssets) {
		this.otherBankAssets = otherBankAssets;
	}

	public Double getOtherBankdebt() {
		return otherBankdebt;
	}

	public void setOtherBankdebt(Double otherBankdebt) {
		this.otherBankdebt = otherBankdebt;
	}
}
