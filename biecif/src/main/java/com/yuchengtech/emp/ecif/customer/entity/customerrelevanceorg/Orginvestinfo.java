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
 * The persistent class for the ORGINVESTINFO database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_INVESTINFO")
public class Orginvestinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_INVEST_INFO_ID", unique=true, nullable=false)
	private Long orgInvestInfoId;

	@Column(name="CURRSIGN",length=20)
	private String currsign;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="DUTY",length=20)
	private String duty;

	@Column(name="EFFSTATUS",length=1)
	private String effstatus;

	@Column(name="ENGAGETERM",length=1)
	private Long engageterm;

	@Column(name="FIRST_YEAR_INVEST_INCOME", precision=17, scale=2)
	private BigDecimal firstYearInvestIncome;

	@Column(name="HOLDDATE",length=10)
	private String holddate;

	@Column(name="INVEAMT",precision=17, scale=2)
	private BigDecimal inveamt;

	@Column(name="INVEDATE",length=20)
	private String invedate;

	@Column(name="INVEKIND",length=1)
	private String invekind;

	@Column(name="INVESETED_REG_CAPITAL", precision=17, scale=2)
	private BigDecimal invesetedRegCapital;

	@Column(name="INVEST_CUST_NO", length=20)
	private String investCustNo;

	@Column(name="INVESTED_CUST_LEGAL_REPR", length=80)
	private String investedCustLegalRepr;

	@Column(name="INVESTED_CUST_NAME", length=60)
	private String investedCustName;

	@Column(name="INVESTED_CUST_NO", length=20)
	private String investedCustNo;

	@Column(name="INVESTED_CUST_ORG_CODE", length=10)
	private String investedCustOrgCode;

	@Column(name="INVESTED_CUST_REG_ADDR", precision=17, scale=2)
	private BigDecimal investedCustRegAddr;

	@Column(name="INVESTED_CUST_REG_NO", length=20)
	private String investedCustRegNo;

	@Column(name="INVESTED_LOAN_CARD_NO", length=16)
	private String investedLoanCardNo;

	@Column(name="INVESTED_LOAN_CARD_STAT", length=1)
	private String investedLoanCardStat;

	@Column(name="INVESTMENTSUM",precision=17, scale=2)
	private BigDecimal investmentsum;

	@Column(name="INVESTYIELD",precision=17, scale=2)
	private BigDecimal investyield;

	@Column(name="IS_LARGEST_SH", length=1)
	private String isLargestSh;

	@Column(name="KEYNO",length=32)
	private String keyno;

	@Column(name="OUGHTSUM",precision=17, scale=2)
	private BigDecimal oughtsum;

	@Column(name="STOCK_PERC", precision=5, scale=2)
	private BigDecimal stockPerc;

	@Column(name="STOCKCERTNO",length=32)
	private String stockcertno;

    public Orginvestinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getOrgInvestInfoId() {
		return this.orgInvestInfoId;
	}

	public void setOrgInvestInfoId(Long orgInvestInfoId) {
		this.orgInvestInfoId = orgInvestInfoId;
	}

	public String getCurrsign() {
		return this.currsign;
	}

	public void setCurrsign(String currsign) {
		this.currsign = currsign;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getEffstatus() {
		return this.effstatus;
	}

	public void setEffstatus(String effstatus) {
		this.effstatus = effstatus;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getEngageterm() {
		return this.engageterm;
	}

	public void setEngageterm(Long engageterm) {
		this.engageterm = engageterm;
	}

	public BigDecimal getFirstYearInvestIncome() {
		return this.firstYearInvestIncome;
	}

	public void setFirstYearInvestIncome(BigDecimal firstYearInvestIncome) {
		this.firstYearInvestIncome = firstYearInvestIncome;
	}

	public String getHolddate() {
		return this.holddate;
	}

	public void setHolddate(String holddate) {
		this.holddate = holddate;
	}

	public BigDecimal getInveamt() {
		return this.inveamt;
	}

	public void setInveamt(BigDecimal inveamt) {
		this.inveamt = inveamt;
	}

	public String getInvedate() {
		return this.invedate;
	}

	public void setInvedate(String invedate) {
		this.invedate = invedate;
	}

	public String getInvekind() {
		return this.invekind;
	}

	public void setInvekind(String invekind) {
		this.invekind = invekind;
	}

	public BigDecimal getInvesetedRegCapital() {
		return this.invesetedRegCapital;
	}

	public void setInvesetedRegCapital(BigDecimal invesetedRegCapital) {
		this.invesetedRegCapital = invesetedRegCapital;
	}

	public String getInvestCustNo() {
		return this.investCustNo;
	}

	public void setInvestCustNo(String investCustNo) {
		this.investCustNo = investCustNo;
	}

	public String getInvestedCustLegalRepr() {
		return this.investedCustLegalRepr;
	}

	public void setInvestedCustLegalRepr(String investedCustLegalRepr) {
		this.investedCustLegalRepr = investedCustLegalRepr;
	}

	public String getInvestedCustName() {
		return this.investedCustName;
	}

	public void setInvestedCustName(String investedCustName) {
		this.investedCustName = investedCustName;
	}

	public String getInvestedCustNo() {
		return this.investedCustNo;
	}

	public void setInvestedCustNo(String investedCustNo) {
		this.investedCustNo = investedCustNo;
	}

	public String getInvestedCustOrgCode() {
		return this.investedCustOrgCode;
	}

	public void setInvestedCustOrgCode(String investedCustOrgCode) {
		this.investedCustOrgCode = investedCustOrgCode;
	}

	public BigDecimal getInvestedCustRegAddr() {
		return this.investedCustRegAddr;
	}

	public void setInvestedCustRegAddr(BigDecimal investedCustRegAddr) {
		this.investedCustRegAddr = investedCustRegAddr;
	}

	public String getInvestedCustRegNo() {
		return this.investedCustRegNo;
	}

	public void setInvestedCustRegNo(String investedCustRegNo) {
		this.investedCustRegNo = investedCustRegNo;
	}

	public String getInvestedLoanCardNo() {
		return this.investedLoanCardNo;
	}

	public void setInvestedLoanCardNo(String investedLoanCardNo) {
		this.investedLoanCardNo = investedLoanCardNo;
	}

	public String getInvestedLoanCardStat() {
		return this.investedLoanCardStat;
	}

	public void setInvestedLoanCardStat(String investedLoanCardStat) {
		this.investedLoanCardStat = investedLoanCardStat;
	}

	public BigDecimal getInvestmentsum() {
		return this.investmentsum;
	}

	public void setInvestmentsum(BigDecimal investmentsum) {
		this.investmentsum = investmentsum;
	}

	public BigDecimal getInvestyield() {
		return this.investyield;
	}

	public void setInvestyield(BigDecimal investyield) {
		this.investyield = investyield;
	}

	public String getIsLargestSh() {
		return this.isLargestSh;
	}

	public void setIsLargestSh(String isLargestSh) {
		this.isLargestSh = isLargestSh;
	}

	public String getKeyno() {
		return this.keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public BigDecimal getOughtsum() {
		return this.oughtsum;
	}

	public void setOughtsum(BigDecimal oughtsum) {
		this.oughtsum = oughtsum;
	}

	public BigDecimal getStockPerc() {
		return this.stockPerc;
	}

	public void setStockPerc(BigDecimal stockPerc) {
		this.stockPerc = stockPerc;
	}

	public String getStockcertno() {
		return this.stockcertno;
	}

	public void setStockcertno(String stockcertno) {
		this.stockcertno = stockcertno;
	}

}