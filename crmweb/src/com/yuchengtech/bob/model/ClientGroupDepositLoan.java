package com.yuchengtech.bob.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the OCRM_F_CI_GROUP_DEP_LN database table.
 * 
 * FDM层(基础数据层)，归属于客户存贷款的二级主题，数据为对客户存贷款信息
 */
@Entity
//@Table(name = "OCRM_F_CI_GROUP_DEP_LN", schema = "fdm")

@Table(name = "OCRM_F_CI_GROUP_DEP_LN")
public class ClientGroupDepositLoan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1608201876455241658L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", nullable = false)
	private Long ID;

	/** 申请企业名称 */
	@Column(name = "CUST_ID", nullable = false, length = 50)
	private String custId;

	/** 存款_贡献度C */
	@Column(name = "DEP_CONTRI", precision = 24, scale = 6)
	private BigDecimal depositContribution;

	/** 存款_比上日 */
	@Column(name = "DEP_CP_LAST_DAY", precision = 24, scale = 6)
	private BigDecimal depositCompareLastDay;

	/** 存款_比上月 */
	@Column(name = "DEP_CP_LAST_MONTH", precision = 24, scale = 6)
	private BigDecimal depositCompareLastMonth;

	/** 存款_比上季 */
	@Column(name = "DEP_CP_LAST_QUAR", precision = 24, scale = 6)
	private BigDecimal depositCompareLastQuar;

	/** 存款_比年初 */
	@Column(name = "DEP_CP_YEAR_START", precision = 24, scale = 6)
	private BigDecimal depositCompareYearStart;

	/** 存款_月均余额 */
	@Column(name = "DEP_MONTH_AVG", precision = 24, scale = 6)
	private BigDecimal depositMonthAverage;

	/** 存款_季均余额 */
	@Column(name = "DEP_QUAR_AVG", precision = 24, scale = 6)
	private BigDecimal depositQuarAverage;

	/** 存款_时点余额 */
	@Column(name = "DEP_TIME_POINT", precision = 24, scale = 6)
	private BigDecimal depositTimePointAverage;

	/** 存款_年均余额 */
	@Column(name = "DEP_YEAR_AVG", precision = 24, scale = 6)
	private BigDecimal depositYearAverage;

	/** 贷款_贡献度C */
	@Column(name = "LN_CONTRI", precision = 24, scale = 6)
	private BigDecimal loanContribution;

	/** 贷款_比上日 */
	@Column(name = "LN_CP_LAST_DAY", precision = 24, scale = 6)
	private BigDecimal lnCpLastDay;

	/** 贷款_比上月 */
	@Column(name = "LN_CP_LAST_MONTH", precision = 24, scale = 6)
	private BigDecimal loanCompareLastMonth;

	/** 贷款_比上季 */
	@Column(name = "LN_CP_LAST_QUAR", precision = 24, scale = 6)
	private BigDecimal loanCompareLastQuar;

	/** 贷款_比年初 */
	@Column(name = "LN_CP_YEAR_START", precision = 24, scale = 6)
	private BigDecimal loanCompareYearStart;

	/** 贷款_月均余额 */
	@Column(name = "LN_MONTH_AVG", precision = 24, scale = 6)
	private BigDecimal loanMonthAverage;

	/** 贷款_季均余额 */
	@Column(name = "LN_QUAR_AVG", precision = 24, scale = 6)
	private BigDecimal loanQuarterAverage;

	/** 贷款_时点余额 */
	@Column(name = "LN_TIME_POINT", precision = 24, scale = 6)
	private BigDecimal loanTimePointAverage;

	/** 贷款_年均余额 */
	@Column(name = "LN_YEAR_AVG", precision = 24, scale = 6)
	private BigDecimal loanYearAverage;

	// bi-directional many-to-one association to ClientGroupMember
	// @ManyToOne
	// @JoinColumn(name = "CUST_ID", referencedColumnName = "CUST_ID", nullable
	// = false, insertable = false, updatable = false)
	// private ClientGroupMember ocrmFCiGroupMember;

	// // bi-directional many-to-one association to ClientGroupMember
	// @OneToMany(mappedBy = "ocrmFCiGroupDepLn")
	// private Set<ClientGroupMember> ocrmFCiGroupMembers;

	public BigDecimal getLnCpLastDay() {
		return this.lnCpLastDay;
	}

	public void setLnCpLastDay(BigDecimal lnCpLastDay) {
		this.lnCpLastDay = lnCpLastDay;
	}

	// public ClientGroupMember getOcrmFCiGroupMember() {
	// return this.ocrmFCiGroupMember;
	// }
	//
	// public void setOcrmFCiGroupMember(ClientGroupMember ocrmFCiGroupMember) {
	// this.ocrmFCiGroupMember = ocrmFCiGroupMember;
	// }

	// public Set<ClientGroupMember> getOcrmFCiGroupMembers() {
	// return this.ocrmFCiGroupMembers;
	// }
	//
	// public void setOcrmFCiGroupMembers(
	// Set<ClientGroupMember> ocrmFCiGroupMembers) {
	// this.ocrmFCiGroupMembers = ocrmFCiGroupMembers;
	// }

	public void setDepositContribution(BigDecimal depositContribution) {
		this.depositContribution = depositContribution;
	}

	public BigDecimal getDepositContribution() {
		return depositContribution;
	}

	public void setDepositCompareLastDay(BigDecimal depositCompareLastDay) {
		this.depositCompareLastDay = depositCompareLastDay;
	}

	public BigDecimal getDepositCompareLastDay() {
		return depositCompareLastDay;
	}

	public void setDepositCompareLastMonth(BigDecimal depositCompareLastMonth) {
		this.depositCompareLastMonth = depositCompareLastMonth;
	}

	public BigDecimal getDepositCompareLastMonth() {
		return depositCompareLastMonth;
	}

	public void setDepositCompareLastQuar(BigDecimal depositCompareLastQuar) {
		this.depositCompareLastQuar = depositCompareLastQuar;
	}

	public BigDecimal getDepositCompareLastQuar() {
		return depositCompareLastQuar;
	}

	public void setDepositCompareYearStart(BigDecimal depositCompareYearStart) {
		this.depositCompareYearStart = depositCompareYearStart;
	}

	public BigDecimal getDepositCompareYearStart() {
		return depositCompareYearStart;
	}

	public void setDepositMonthAverage(BigDecimal depositMonthAverage) {
		this.depositMonthAverage = depositMonthAverage;
	}

	public BigDecimal getDepositMonthAverage() {
		return depositMonthAverage;
	}

	public void setDepositQuarAverage(BigDecimal depositQuarAverage) {
		this.depositQuarAverage = depositQuarAverage;
	}

	public BigDecimal getDepositQuarAverage() {
		return depositQuarAverage;
	}

	public void setDepositTimePointAverage(BigDecimal depositTimePointAverage) {
		this.depositTimePointAverage = depositTimePointAverage;
	}

	public BigDecimal getDepositTimePointAverage() {
		return depositTimePointAverage;
	}

	public void setDepositYearAverage(BigDecimal depositYearAverage) {
		this.depositYearAverage = depositYearAverage;
	}

	public BigDecimal getDepositYearAverage() {
		return depositYearAverage;
	}

	public void setLoanContribution(BigDecimal loanContribution) {
		this.loanContribution = loanContribution;
	}

	public BigDecimal getLoanContribution() {
		return loanContribution;
	}

	public void setLoanCompareLastMonth(BigDecimal loanCompareLastMonth) {
		this.loanCompareLastMonth = loanCompareLastMonth;
	}

	public BigDecimal getLoanCompareLastMonth() {
		return loanCompareLastMonth;
	}

	public void setLoanCompareLastQuar(BigDecimal loanCompareLastQuar) {
		this.loanCompareLastQuar = loanCompareLastQuar;
	}

	public BigDecimal getLoanCompareLastQuar() {
		return loanCompareLastQuar;
	}

	public void setLoanCompareYearStart(BigDecimal loanCompareYearStart) {
		this.loanCompareYearStart = loanCompareYearStart;
	}

	public BigDecimal getLoanCompareYearStart() {
		return loanCompareYearStart;
	}

	public void setLoanMonthAverage(BigDecimal loanMonthAverage) {
		this.loanMonthAverage = loanMonthAverage;
	}

	public BigDecimal getLoanMonthAverage() {
		return loanMonthAverage;
	}

	public void setLoanQuarterAverage(BigDecimal loanQuarterAverage) {
		this.loanQuarterAverage = loanQuarterAverage;
	}

	public BigDecimal getLoanQuarterAverage() {
		return loanQuarterAverage;
	}

	public void setLoanTimePointAverage(BigDecimal loanTimePointAverage) {
		this.loanTimePointAverage = loanTimePointAverage;
	}

	public BigDecimal getLoanTimePointAverage() {
		return loanTimePointAverage;
	}

	public void setLoanYearAverage(BigDecimal loanYearAverage) {
		this.loanYearAverage = loanYearAverage;
	}

	public BigDecimal getLoanYearAverage() {
		return loanYearAverage;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public Long getID() {
		return ID;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustId() {
		return custId;
	}

}