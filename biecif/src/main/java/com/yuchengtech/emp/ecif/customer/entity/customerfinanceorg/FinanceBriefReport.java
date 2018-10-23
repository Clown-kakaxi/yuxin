package com.yuchengtech.emp.ecif.customer.entity.customerfinanceorg;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.math.BigDecimal;


/**
 * The persistent class for the FINANCE_BRIEF_REPORT database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_FINANCE_BRIEF_REPORT")
public class FinanceBriefReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FINANCE_BRIEF_REPORT_ID", unique=true, nullable=false)
	private Long financeBriefReportId;

	@Column(name="ACCOUNTING_RULE", length=20)
	private String accountingRule;

	@Column(name="ASSETS_CONDITION", length=20)
	private String assetsCondition;

	@Column(name="AUDITDATE", length=20)
	private String auditdate;

	@Column(name="AUDITFLAG",length=1)
	private String auditflag;

	@Column(name="AUDITOFFICE",length=80)
	private String auditoffice;

	@Column(name="AUDITOINION",length=80)
	private String auditoinion;

	@Column(name="CAPITAL_CONDITION", length=20)
	private String capitalCondition;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="HAS_RESERVE_OPT", length=1)
	private String hasReserveOpt;

	@Column(name="LIQUIDITY_MONITOR_INDEX", length=20)
	private String liquidityMonitorIndex;

	@Column(name="LOAN_QUALITY", length=20)
	private String loanQuality;

	@Column(name="PROFIT_INDEX", length=20)
	private String profitIndex;

	@Column(name="PROFITABILITY",length=20)
	private String profitability;

	@Column(name="REPORT_DETAIL", length=100)
	private String reportDetail;

	@Column(name="REPORT_DETAIL_AMT", precision=17, scale=2)
	private BigDecimal reportDetailAmt;

	@Column(name="REPORT_KIND", length=20)
	private String reportKind;

	@Column(name="REPORT_NO", length=32)
	private String reportNo;

	@Column(name="REPORT_TYPE", length=20)
	private String reportType;

	@Column(name="REPORT_UNIT", length=20)
	private String reportUnit;

	@Column(name="RISK_MONITOR_INDEX", length=20)
	private String riskMonitorIndex;

	@Column(name="TOTAL_ASSETS", precision=17, scale=2)
	private BigDecimal totalAssets;

	@Column(name="TOTAL_DEBT", precision=17, scale=2)
	private BigDecimal totalDebt;

    public FinanceBriefReport() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getFinanceBriefReportId() {
		return this.financeBriefReportId;
	}

	public void setFinanceBriefReportId(Long financeBriefReportId) {
		this.financeBriefReportId = financeBriefReportId;
	}

	public String getAccountingRule() {
		return this.accountingRule;
	}

	public void setAccountingRule(String accountingRule) {
		this.accountingRule = accountingRule;
	}

	public String getAssetsCondition() {
		return this.assetsCondition;
	}

	public void setAssetsCondition(String assetsCondition) {
		this.assetsCondition = assetsCondition;
	}

	public String getAuditdate() {
		return this.auditdate;
	}

	public void setAuditdate(String auditdate) {
		this.auditdate = auditdate;
	}

	public String getAuditflag() {
		return this.auditflag;
	}

	public void setAuditflag(String auditflag) {
		this.auditflag = auditflag;
	}

	public String getAuditoffice() {
		return this.auditoffice;
	}

	public void setAuditoffice(String auditoffice) {
		this.auditoffice = auditoffice;
	}

	public String getAuditoinion() {
		return this.auditoinion;
	}

	public void setAuditoinion(String auditoinion) {
		this.auditoinion = auditoinion;
	}

	public String getCapitalCondition() {
		return this.capitalCondition;
	}

	public void setCapitalCondition(String capitalCondition) {
		this.capitalCondition = capitalCondition;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getHasReserveOpt() {
		return this.hasReserveOpt;
	}

	public void setHasReserveOpt(String hasReserveOpt) {
		this.hasReserveOpt = hasReserveOpt;
	}

	public String getLiquidityMonitorIndex() {
		return this.liquidityMonitorIndex;
	}

	public void setLiquidityMonitorIndex(String liquidityMonitorIndex) {
		this.liquidityMonitorIndex = liquidityMonitorIndex;
	}

	public String getLoanQuality() {
		return this.loanQuality;
	}

	public void setLoanQuality(String loanQuality) {
		this.loanQuality = loanQuality;
	}

	public String getProfitIndex() {
		return this.profitIndex;
	}

	public void setProfitIndex(String profitIndex) {
		this.profitIndex = profitIndex;
	}

	public String getProfitability() {
		return this.profitability;
	}

	public void setProfitability(String profitability) {
		this.profitability = profitability;
	}

	public String getReportDetail() {
		return this.reportDetail;
	}

	public void setReportDetail(String reportDetail) {
		this.reportDetail = reportDetail;
	}

	public BigDecimal getReportDetailAmt() {
		return this.reportDetailAmt;
	}

	public void setReportDetailAmt(BigDecimal reportDetailAmt) {
		this.reportDetailAmt = reportDetailAmt;
	}

	public String getReportKind() {
		return this.reportKind;
	}

	public void setReportKind(String reportKind) {
		this.reportKind = reportKind;
	}

	public String getReportNo() {
		return this.reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getReportType() {
		return this.reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportUnit() {
		return this.reportUnit;
	}

	public void setReportUnit(String reportUnit) {
		this.reportUnit = reportUnit;
	}

	public String getRiskMonitorIndex() {
		return this.riskMonitorIndex;
	}

	public void setRiskMonitorIndex(String riskMonitorIndex) {
		this.riskMonitorIndex = riskMonitorIndex;
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