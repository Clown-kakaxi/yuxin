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
 * Title:Family的实体类
 * Description: 家庭信息
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
@Table(name="M_CI_PER_FAMILY")
public class Family implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="FAMILY_ADDR", length=200)
	private String familyAddr;
	
	@Column(name="HOME_TEL", length=20)
	private String homeTel;
	
	@Column(name="POPULATION")
	private Integer population;
	
	@Column(name="RESIDENCE_STAT", length=18)
	private String residenceStat;
	
//	@Column(name="FAMILY_ANN_INC_SCOPE")
//	private String familyAnnIncScope;
//	
//	@Column(name="FAMILY_ANNUAL_INCOME")
//	private Double familyAnnualIncome;
//	
//	@Column(name="FAMILY_ANNUAL_PAY")
//	private Double familyAnnualPay;
//	
//	@Column(name="FAMILY_MON_INC_SCOPE")
//	private Double familyMonIncScope;
//	
//	@Column(name="FAMILY_MONTH_INCOME")
//	private Double familyMonthIncome;
//	
//	@Column(name="FAMILY_MONTH_PAY")
//	private Double familyMonthPay;
//	
//	@Column(name="FAMILY_ASSETS")
//	private Double familyAssets;
//	
//	@Column(name="FAMILY_ASSETS_INFO", length=80)
//	private String familyAssetsInfo;
	
	@Column(name="IS_HOUSE_HOLDER", length=1)
	private String isHouseHolder;

	@Column(name="CHILDREN_NUM")
	private Long childrenNum;

	@Column(name="SUPPLY_POP_NUM")
	private Long supplyPopNum;
	
	@Column(name="PROVIDE_POP_NUM")
	private Long providePopNum;

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getFamilyAddr() {
		return familyAddr;
	}

	public void setFamilyAddr(String familyAddr) {
		this.familyAddr = familyAddr;
	}

	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public Integer getPopulation() {
		return population;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}

	public String getResidenceStat() {
		return residenceStat;
	}

	public void setResidenceStat(String residenceStat) {
		this.residenceStat = residenceStat;
	}

//	public Double getFamilyAnnualIncome() {
//		return familyAnnualIncome;
//	}
//
//	public void setFamilyAnnualIncome(Double familyAnnualIncome) {
//		this.familyAnnualIncome = familyAnnualIncome;
//	}
//
//	public Double getFamilyAnnualPay() {
//		return familyAnnualPay;
//	}
//
//	public void setFamilyAnnualPay(Double familyAnnualPay) {
//		this.familyAnnualPay = familyAnnualPay;
//	}
//
//	public Double getFamilyMonthIncome() {
//		return familyMonthIncome;
//	}
//
//	public void setFamilyMonthIncome(Double familyMonthIncome) {
//		this.familyMonthIncome = familyMonthIncome;
//	}
//
//	public Double getFamilyMonthPay() {
//		return familyMonthPay;
//	}
//
//	public void setFamilyMonthPay(Double familyMonthPay) {
//		this.familyMonthPay = familyMonthPay;
//	}
//
//	public Double getFamilyAssets() {
//		return familyAssets;
//	}
//
//	public void setFamilyAssets(Double familyAssets) {
//		this.familyAssets = familyAssets;
//	}
//
//	public String getFamilyAssetsInfo() {
//		return familyAssetsInfo;
//	}
//
//	public void setFamilyAssetsInfo(String familyAssetsInfo) {
//		this.familyAssetsInfo = familyAssetsInfo;
//	}

	public String getIsHouseHolder() {
		return isHouseHolder;
	}

	public void setIsHouseHolder(String isHouseHolder) {
		this.isHouseHolder = isHouseHolder;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(Long childrenNum) {
		this.childrenNum = childrenNum;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getSupplyPopNum() {
		return supplyPopNum;
	}

	public void setSupplyPopNum(Long supplyPopNum) {
		this.supplyPopNum = supplyPopNum;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getProvidePopNum() {
		return providePopNum;
	}

	public void setProvidePopNum(Long providePopNum) {
		this.providePopNum = providePopNum;
	}

//	public String getFamilyAnnIncScope() {
//		return familyAnnIncScope;
//	}
//
//	public void setFamilyAnnIncScope(String familyAnnIncScope) {
//		this.familyAnnIncScope = familyAnnIncScope;
//	}
//
//	public Double getFamilyMonIncScope() {
//		return familyMonIncScope;
//	}
//
//	public void setFamilyMonIncScope(Double familyMonIncScope) {
//		this.familyMonIncScope = familyMonIncScope;
//	}
}
