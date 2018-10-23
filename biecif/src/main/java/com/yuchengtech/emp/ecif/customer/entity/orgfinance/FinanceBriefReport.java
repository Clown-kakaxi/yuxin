package com.yuchengtech.emp.ecif.customer.entity.orgfinance;
import java.io.Serializable;
import javax.persistence.*;
import java.math.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
@Entity(name="com.yuchengtech.emp.ecif.customer.entity.orgfinance.FinanceBriefReport")
@Table(name="M_CI_ORG_FIN_BRIEF_REPORT")
public class FinanceBriefReport implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="FINANCE_BRIEF_REPORT_ID", unique=true, nullable=false)
	private Long financeBriefReportId;
	@Column(name="CUST_ID")
	private Long custId;
	@Column(name="REPORT_NO")
	private String reportNo;
	@Column(name="REPORT_KIND")
	private String reportKind;
	@Column(name="ACCOUNTING_RULE")
	private String accountingRule;
	@Column(name="REPORT_TYPE")
	private String reportType;
	@Column(name="REPORT_UNIT")
	private String reportUnit;
	@Column(name="REPORT_DETAIL")
	private String reportDetail;
	@Column(name="REPORT_DETAIL_AMT")
	private BigDecimal reportDetailAmt;
	@Column(name="ASSETS_CONDITION")
	private String assetsCondition;
	@Column(name="CAPITAL_CONDITION")
	private String capitalCondition;
	@Column(name="PROFIT_ABILITY")
	private String profitAbility;
	@Column(name="PROFIT_INDEX")
	private String profitIndex;
	@Column(name="LOAN_QUALITY")
	private String loanQuality;
	@Column(name="RISK_MONITOR_INDEX")
	private String riskMonitorIndex;
	@Column(name="LIQUIDITY_MONITOR_INDEX")
	private String liquidityMonitorIndex;
	@Column(name="TOTAL_ASSETS")
	private BigDecimal totalAssets;
	@Column(name="TOTAL_DEBT")
	private BigDecimal totalDebt;
	@Column(name="AUDIT_DATE")
	private String auditDate;
	@Column(name="AUDIT_OFFICE")
	private String auditOffice;
	@Column(name="AUDIT_OPINION")
	private String auditOpinion;
	@Column(name="AUDIT_FLAG")
	private String auditFlag;
	@Column(name="HAS_RESERVE_OPT")
	private String hasReserveOpt;
	@Column(name="LAST_UPDATE_SYS")
	private String lastUpdateSys;
	@Column(name="LAST_UPDATE_USER")
	private String lastUpdateUser;
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	@Column(name="TX_SEQ_NO")
	private String txSeqNo;
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getFinanceBriefReportId() {
 return this.financeBriefReportId;
 }
 public void setFinanceBriefReportId(Long financeBriefReportId) {
  this.financeBriefReportId=financeBriefReportId;
 }
 @JsonSerialize(using=BioneLongSerializer.class) 
 public Long getCustId() {
 return this.custId;
 }
 public void setCustId(Long custId) {
  this.custId=custId;
 }
 public String getReportNo() {
 return this.reportNo;
 }
 public void setReportNo(String reportNo) {
  this.reportNo=reportNo;
 }
 public String getReportKind() {
 return this.reportKind;
 }
 public void setReportKind(String reportKind) {
  this.reportKind=reportKind;
 }
 public String getAccountingRule() {
 return this.accountingRule;
 }
 public void setAccountingRule(String accountingRule) {
  this.accountingRule=accountingRule;
 }
 public String getReportType() {
 return this.reportType;
 }
 public void setReportType(String reportType) {
  this.reportType=reportType;
 }
 public String getReportUnit() {
 return this.reportUnit;
 }
 public void setReportUnit(String reportUnit) {
  this.reportUnit=reportUnit;
 }
 public String getReportDetail() {
 return this.reportDetail;
 }
 public void setReportDetail(String reportDetail) {
  this.reportDetail=reportDetail;
 }
 public BigDecimal getReportDetailAmt() {
 return this.reportDetailAmt;
 }
 public void setReportDetailAmt(BigDecimal reportDetailAmt) {
  this.reportDetailAmt=reportDetailAmt;
 }
 public String getAssetsCondition() {
 return this.assetsCondition;
 }
 public void setAssetsCondition(String assetsCondition) {
  this.assetsCondition=assetsCondition;
 }
 public String getCapitalCondition() {
 return this.capitalCondition;
 }
 public void setCapitalCondition(String capitalCondition) {
  this.capitalCondition=capitalCondition;
 }
 public String getProfitAbility() {
 return this.profitAbility;
 }
 public void setProfitAbility(String profitAbility) {
  this.profitAbility=profitAbility;
 }
 public String getProfitIndex() {
 return this.profitIndex;
 }
 public void setProfitIndex(String profitIndex) {
  this.profitIndex=profitIndex;
 }
 public String getLoanQuality() {
 return this.loanQuality;
 }
 public void setLoanQuality(String loanQuality) {
  this.loanQuality=loanQuality;
 }
 public String getRiskMonitorIndex() {
 return this.riskMonitorIndex;
 }
 public void setRiskMonitorIndex(String riskMonitorIndex) {
  this.riskMonitorIndex=riskMonitorIndex;
 }
 public String getLiquidityMonitorIndex() {
 return this.liquidityMonitorIndex;
 }
 public void setLiquidityMonitorIndex(String liquidityMonitorIndex) {
  this.liquidityMonitorIndex=liquidityMonitorIndex;
 }
 public BigDecimal getTotalAssets() {
 return this.totalAssets;
 }
 public void setTotalAssets(BigDecimal totalAssets) {
  this.totalAssets=totalAssets;
 }
 public BigDecimal getTotalDebt() {
 return this.totalDebt;
 }
 public void setTotalDebt(BigDecimal totalDebt) {
  this.totalDebt=totalDebt;
 }
 public String getAuditDate() {
 return this.auditDate;
 }
 public void setAuditDate(String auditDate) {
  this.auditDate=auditDate;
 }
 public String getAuditOffice() {
 return this.auditOffice;
 }
 public void setAuditOffice(String auditOffice) {
  this.auditOffice=auditOffice;
 }
 public String getAuditOpinion() {
 return this.auditOpinion;
 }
 public void setAuditOpinion(String auditOpinion) {
  this.auditOpinion=auditOpinion;
 }
 public String getAuditFlag() {
 return this.auditFlag;
 }
 public void setAuditFlag(String auditFlag) {
  this.auditFlag=auditFlag;
 }
 public String getHasReserveOpt() {
 return this.hasReserveOpt;
 }
 public void setHasReserveOpt(String hasReserveOpt) {
  this.hasReserveOpt=hasReserveOpt;
 }
 public String getLastUpdateSys() {
 return this.lastUpdateSys;
 }
 public void setLastUpdateSys(String lastUpdateSys) {
  this.lastUpdateSys=lastUpdateSys;
 }
 public String getLastUpdateUser() {
 return this.lastUpdateUser;
 }
 public void setLastUpdateUser(String lastUpdateUser) {
  this.lastUpdateUser=lastUpdateUser;
 }
 public String getLastUpdateTm() {
 return this.lastUpdateTm;
 }
 public void setLastUpdateTm(String lastUpdateTm) {
  this.lastUpdateTm=lastUpdateTm;
 }
 public String getTxSeqNo() {
 return this.txSeqNo;
 }
 public void setTxSeqNo(String txSeqNo) {
  this.txSeqNo=txSeqNo;
 }
 }

