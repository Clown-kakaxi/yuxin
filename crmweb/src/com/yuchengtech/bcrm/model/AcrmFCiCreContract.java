package com.yuchengtech.bcrm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the ACRM_F_CI_CRE_CONTRACT database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_CRE_CONTRACT")
public class AcrmFCiCreContract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String bak1;

	private String bak2;

	private String bak3;

	@Column(name="CRD_AMT")
	private BigDecimal crdAmt;

	private String currency;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="LN_CUST_ID")
	private String lnCustId;

	@Column(name="ENABLE_AMT")
	private BigDecimal enableAmt;

	private String flag;

	@Column(name="LIMIT_NO")
	private String limitNo;

	private String product;

	private String product1;
	
	private String serno;

	@Column(name="SUB_LIMIT_ID")
	private String subLimitId;

	@Column(name="USE_AMT")
	private BigDecimal useAmt;

	@Column(name="USE_RATE1")
	private BigDecimal useRate1;

	@Column(name="USE_RATE2")
	private BigDecimal useRate2;

    public AcrmFCiCreContract() {
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBak1() {
		return this.bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak2() {
		return this.bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public String getBak3() {
		return this.bak3;
	}

	public void setBak3(String bak3) {
		this.bak3 = bak3;
	}

	public BigDecimal getCrdAmt() {
		return this.crdAmt;
	}

	public void setCrdAmt(BigDecimal crdAmt) {
		this.crdAmt = crdAmt;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getLnCustId() {
		return lnCustId;
	}

	public void setLnCustId(String lnCustId) {
		this.lnCustId = lnCustId;
	}

	public BigDecimal getEnableAmt() {
		return this.enableAmt;
	}

	public void setEnableAmt(BigDecimal enableAmt) {
		this.enableAmt = enableAmt;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getLimitNo() {
		return this.limitNo;
	}

	public void setLimitNo(String limitNo) {
		this.limitNo = limitNo;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProduct1() {
		return product1;
	}

	public void setProduct1(String product1) {
		this.product1 = product1;
	}

	public String getSerno() {
		return serno;
	}

	public void setSerno(String serno) {
		this.serno = serno;
	}

	public String getSubLimitId() {
		return this.subLimitId;
	}

	public void setSubLimitId(String subLimitId) {
		this.subLimitId = subLimitId;
	}

	public BigDecimal getUseAmt() {
		return this.useAmt;
	}

	public void setUseAmt(BigDecimal useAmt) {
		this.useAmt = useAmt;
	}

	public BigDecimal getUseRate1() {
		return this.useRate1;
	}

	public void setUseRate1(BigDecimal useRate1) {
		this.useRate1 = useRate1;
	}

	public BigDecimal getUseRate2() {
		return this.useRate2;
	}

	public void setUseRate2(BigDecimal useRate2) {
		this.useRate2 = useRate2;
	}

}