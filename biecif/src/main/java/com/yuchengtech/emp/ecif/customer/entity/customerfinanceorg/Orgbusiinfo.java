package com.yuchengtech.emp.ecif.customer.entity.customerfinanceorg;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the ORGBUSIINFO database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_BUSIINFO")
public class Orgbusiinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_BUSI_INFO_ID", unique=true, nullable=false)
	private Long orgBusiInfoId;

	@Column(name="BUDGET_ADMIN_TYPE", length=18)
	private String budgetAdminType;

	@Column(name="BUSINESS_PLAN", length=1000)
	private String businessPlan;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="LASTINCO",precision=17, scale=2)
	private BigDecimal lastinco;

	@Column(name="MAINMARKET",length=800)
	private String mainmarket;

	@Column(name="MATERIAL",length=500)
	private String material;

	@Column(name="OCCUINDUSTRY",length=20)
	private String occuindustry;

	@Column(name="OTHER_DESC", length=1000)
	private String otherDesc;

	@Column(name="SALES_INCOME", precision=17, scale=2)
	private BigDecimal salesIncome;

	@Column(name="SELLAMT",length=40)
	private String sellamt;

	@Column(name="SUPPLIER",length=1000)
	private String supplier;

	@Column(name="VENDORS",length=1000)
	private String vendors;

	@Column(name="YEARINCO",precision=17, scale=2)
	private BigDecimal yearinco;

    public Orgbusiinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOrgBusiInfoId() {
		return this.orgBusiInfoId;
	}

	public void setOrgBusiInfoId(Long orgBusiInfoId) {
		this.orgBusiInfoId = orgBusiInfoId;
	}

	public String getBudgetAdminType() {
		return this.budgetAdminType;
	}

	public void setBudgetAdminType(String budgetAdminType) {
		this.budgetAdminType = budgetAdminType;
	}

	public String getBusinessPlan() {
		return this.businessPlan;
	}

	public void setBusinessPlan(String businessPlan) {
		this.businessPlan = businessPlan;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public BigDecimal getLastinco() {
		return this.lastinco;
	}

	public void setLastinco(BigDecimal lastinco) {
		this.lastinco = lastinco;
	}

	public String getMainmarket() {
		return this.mainmarket;
	}

	public void setMainmarket(String mainmarket) {
		this.mainmarket = mainmarket;
	}

	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getOccuindustry() {
		return this.occuindustry;
	}

	public void setOccuindustry(String occuindustry) {
		this.occuindustry = occuindustry;
	}

	public String getOtherDesc() {
		return this.otherDesc;
	}

	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}

	public BigDecimal getSalesIncome() {
		return this.salesIncome;
	}

	public void setSalesIncome(BigDecimal salesIncome) {
		this.salesIncome = salesIncome;
	}

	public String getSellamt() {
		return this.sellamt;
	}

	public void setSellamt(String sellamt) {
		this.sellamt = sellamt;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getVendors() {
		return this.vendors;
	}

	public void setVendors(String vendors) {
		this.vendors = vendors;
	}

	public BigDecimal getYearinco() {
		return this.yearinco;
	}

	public void setYearinco(BigDecimal yearinco) {
		this.yearinco = yearinco;
	}

}