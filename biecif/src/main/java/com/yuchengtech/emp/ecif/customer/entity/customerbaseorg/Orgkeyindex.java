package com.yuchengtech.emp.ecif.customer.entity.customerbaseorg;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the ORGKEYINDEX database table.
 * 
 */
@Entity
@Table(name="ORGKEYINDEX")
public class Orgkeyindex implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="ANNUAL_INCOME", precision=17, scale=2)
	private BigDecimal annualIncome;

	@Column(name="BUSI_INCOME", precision=17, scale=2)
	private BigDecimal busiIncome;

	@Column(name="EMPLOYEE_NUM")
	private Long employeeNum;

	@Column(name="NET_ASSETS", precision=17, scale=2)
	private BigDecimal netAssets;

	@Column(name="TOTAL_ASSETS", precision=17, scale=2)
	private BigDecimal totalAssets;

    public Orgkeyindex() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public BigDecimal getAnnualIncome() {
		return this.annualIncome;
	}

	public void setAnnualIncome(BigDecimal annualIncome) {
		this.annualIncome = annualIncome;
	}

	public BigDecimal getBusiIncome() {
		return this.busiIncome;
	}

	public void setBusiIncome(BigDecimal busiIncome) {
		this.busiIncome = busiIncome;
	}

	public Long getEmployeeNum() {
		return this.employeeNum;
	}

	public void setEmployeeNum(Long employeeNum) {
		this.employeeNum = employeeNum;
	}

	public BigDecimal getNetAssets() {
		return this.netAssets;
	}

	public void setNetAssets(BigDecimal netAssets) {
		this.netAssets = netAssets;
	}

	public BigDecimal getTotalAssets() {
		return this.totalAssets;
	}

	public void setTotalAssets(BigDecimal totalAssets) {
		this.totalAssets = totalAssets;
	}

}