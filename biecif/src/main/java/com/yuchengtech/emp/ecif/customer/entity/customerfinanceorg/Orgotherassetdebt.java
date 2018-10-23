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
 * The persistent class for the ORGOTHERASSETDEBT database table.
 * 
 */
@Entity
@Table(name="ORGOTHERASSETDEBT")
public class Orgotherassetdebt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_ASSET_DEBT_ID", unique=true, nullable=false)
	private Long orgAssetDebtId;

	@Column(name="ADAGE",length=32)
	private String adage;

	@Column(name="ADCONTENT",length=80)
	private String adcontent;

	@Column(name="ADCURRENCY",length=20)
	private String adcurrency;

	@Column(name="ADDESCRIBE",length=200)
	private String addescribe;

	@Column(name="ADSUM",precision=17, scale=2)
	private BigDecimal adsum;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="FINANCEITEM",length=18)
	private String financeitem;

	@Column(name="UPDATEDATE",length=20)
	private String updatedate;

	@Column(name="UPTODATE",length=20)
	private String uptodate;

    public Orgotherassetdebt() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOrgAssetDebtId() {
		return this.orgAssetDebtId;
	}

	public void setOrgAssetDebtId(Long orgAssetDebtId) {
		this.orgAssetDebtId = orgAssetDebtId;
	}

	public String getAdage() {
		return this.adage;
	}

	public void setAdage(String adage) {
		this.adage = adage;
	}

	public String getAdcontent() {
		return this.adcontent;
	}

	public void setAdcontent(String adcontent) {
		this.adcontent = adcontent;
	}

	public String getAdcurrency() {
		return this.adcurrency;
	}

	public void setAdcurrency(String adcurrency) {
		this.adcurrency = adcurrency;
	}

	public String getAddescribe() {
		return this.addescribe;
	}

	public void setAddescribe(String addescribe) {
		this.addescribe = addescribe;
	}

	public BigDecimal getAdsum() {
		return this.adsum;
	}

	public void setAdsum(BigDecimal adsum) {
		this.adsum = adsum;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getFinanceitem() {
		return this.financeitem;
	}

	public void setFinanceitem(String financeitem) {
		this.financeitem = financeitem;
	}

	public String getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}

	public String getUptodate() {
		return this.uptodate;
	}

	public void setUptodate(String uptodate) {
		this.uptodate = uptodate;
	}

}