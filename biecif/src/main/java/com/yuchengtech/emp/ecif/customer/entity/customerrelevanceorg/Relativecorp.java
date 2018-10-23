package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the RELATIVECORP database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_RELATIVECORP")
public class Relativecorp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RELATIVE_CORP_INFO_ID", unique=true, nullable=false)
	private Long relativeCorpInfoId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="MAIN_BUSINESS", precision=17, scale=2)
	private BigDecimal mainBusiness;

	@Column(name="NET_ASSETS", precision=17, scale=2)
	private BigDecimal netAssets;

	@Column(name="NET_PROFIT", precision=17, scale=2)
	private BigDecimal netProfit;

	@Column(name="ORG_CODE", length=10)
	private String orgCode;

	@Column(name="RELATION_DESC", length=100)
	private String relationDesc;

	@Column(name="RELATION_TYPE", length=10)
	private String relationType;

	@Column(name="RELATIVE_CORP_ID")
	private Long relativeCorpId;

	@Column(name="RELATIVE_CORP_NAME", length=60)
	private String relativeCorpName;

	@Column(name="TOTAL_ASSETS", precision=17, scale=2)
	private BigDecimal totalAssets;

	@Column(name="TOTAL_DEBT", precision=17, scale=2)
	private BigDecimal totalDebt;

    public Relativecorp() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getRelativeCorpInfoId() {
		return this.relativeCorpInfoId;
	}

	public void setRelativeCorpInfoId(Long relativeCorpInfoId) {
		this.relativeCorpInfoId = relativeCorpInfoId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public BigDecimal getMainBusiness() {
		return this.mainBusiness;
	}

	public void setMainBusiness(BigDecimal mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public BigDecimal getNetAssets() {
		return this.netAssets;
	}

	public void setNetAssets(BigDecimal netAssets) {
		this.netAssets = netAssets;
	}

	public BigDecimal getNetProfit() {
		return this.netProfit;
	}

	public void setNetProfit(BigDecimal netProfit) {
		this.netProfit = netProfit;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRelationDesc() {
		return this.relationDesc;
	}

	public void setRelationDesc(String relationDesc) {
		this.relationDesc = relationDesc;
	}

	public String getRelationType() {
		return this.relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public Long getRelativeCorpId() {
		return this.relativeCorpId;
	}

	public void setRelativeCorpId(Long relativeCorpId) {
		this.relativeCorpId = relativeCorpId;
	}

	public String getRelativeCorpName() {
		return this.relativeCorpName;
	}

	public void setRelativeCorpName(String relativeCorpName) {
		this.relativeCorpName = relativeCorpName;
	}

	public BigDecimal getTotalAssets() {
		return this.totalAssets;
	}

	public void setTotalAssets(BigDecimal totalAssets) {
		this.totalAssets = totalAssets;
	}

	public BigDecimal getTotalDebt() {
		return this.totalDebt;
	}

	public void setTotalDebt(BigDecimal totalDebt) {
		this.totalDebt = totalDebt;
	}

}