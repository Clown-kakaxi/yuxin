//package com.yuchengtech.bcrm.customer.customerView.model;
//
//import java.io.Serializable;
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.util.Date;
//
//
///**
// * The persistent class for the ACRM_F_CI_DEPOSIT_ACT database table.
// * 存款账号列表信息
// */
//@Entity
//@Table(name="ACRM_F_CI_DEPOSIT_ACT")
//public class AcrmFCiDepositAct implements Serializable {
//	private static final long serialVersionUID = 553682347327268480L;
//
//	/**账号**/
//	@Id
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CommonSequnce")
//	@Column(name="ACCT_NO")
//	private String acctNo;
//
//	/**账户名称**/
//	@Column(name="ACCOUNT_NAME")
//	private String accountName;
//
//	/**账户状态**/
//	@Column(name="ACCOUNT_STAT")
//	private String accountStat;
//
//	/**账户名称**/
//	@Column(name="ACCT_NAME")
//	private String acctName;
//
//	/**余额（折人民币）**/
//	@Column(name="AMOUNT")
//	private BigDecimal amount;
//
//	/**余额（原币种）**/
//	@Column(name="AMOUNT_ORG_MONEY")
//	private BigDecimal amountOrgMoney;
//
//	/**客户经理编号**/
//	@Column(name="AO_NO")
//	private String aoNo;
//
//	/**贡献度_模拟利润**/
//	@Column(name="BEF_DEGREE_CONTRI")
//	private BigDecimal befDegreeContri;
//
//	/**币种**/
//	@Column(name="CUR_TYPE")
//	private String curType;
//
//	/**客户编号**/
//	@Column(name="CUST_ID")
//	private String custId;
//
//	/**月日均**/
//	@Column(name="DEPOSITE_AVG_M")
//	private BigDecimal depositeAvgM;
//
//	/**季日均**/
//	@Column(name="DEPOSITE_AVG_Q")
//	private BigDecimal depositeAvgQ;
//
//	/**年日均**/
//	@Column(name="DEPOSITE_AVG_Y")
//	private BigDecimal depositeAvgY;
//
//	/**ETL日期**/
//    @Temporal( TemporalType.DATE)
//	@Column(name="ETL_DATE")
//	private Date etlDate;
//
//    /**FTP（内转价格）**/
//    @Column(name="FTP")
//	private BigDecimal ftp;
//
//	/**跨境账户币种**/
//    @Column(name="KJZHBZ")
//	private String kjzhbz;
//
//	/**跨境账户余额**/
//    @Column(name="KJZHYE")
//	private BigDecimal kjzhye;
//
//	/**销户日期**/
//    @Temporal( TemporalType.DATE)
//	@Column(name="LOGOUT_ACCOUNT_DATE")
//	private Date logoutAccountDate;
//
//    /**到期日**/
//    @Temporal( TemporalType.DATE)
//	@Column(name="MATURE_DATE")
//	private Date matureDate;
//
//    /**开户日期**/
//    @Temporal( TemporalType.DATE)
//	@Column(name="OPEN_ACCOUNT_DATE")
//	private Date openAccountDate;
//
//    /**开户网点名称**/
//	@Column(name="ORG_NAME")
//	private String orgName;
//
//	/**开户机构**/
//	@Column(name="ORG_NO")
//	private String orgNo;
//
//	/**利率**/
//	@Column(name="RATE")
//	private BigDecimal rate;
//
//	/**资产收益率**/
//	@Column(name="ROA")
//	private BigDecimal roa;
//
//	/**序号**/
//	@Column(name="SEQUENCE")
//	private String sequence;
//
//	/**起息日**/
//    @Temporal( TemporalType.DATE)
//	@Column(name="START_INTER_DATE")
//	private Date startInterDate;
//
//    /**科目**/
//    @Column(name="SUBJECTS")
//	private String subjects;
//
//	/**转存次数**/
//	@Column(name="TRANS_TIMES")
//	private BigDecimal transTimes;
//
//    public AcrmFCiDepositAct() {
//    }
//
//	public String getAcctNo() {
//		return this.acctNo;
//	}
//
//	public void setAcctNo(String acctNo) {
//		this.acctNo = acctNo;
//	}
//
//	public String getAccountName() {
//		return this.accountName;
//	}
//
//	public void setAccountName(String accountName) {
//		this.accountName = accountName;
//	}
//
//	public String getAccountStat() {
//		return this.accountStat;
//	}
//
//	public void setAccountStat(String accountStat) {
//		this.accountStat = accountStat;
//	}
//
//	public String getAcctName() {
//		return this.acctName;
//	}
//
//	public void setAcctName(String acctName) {
//		this.acctName = acctName;
//	}
//
//	public BigDecimal getAmount() {
//		return this.amount;
//	}
//
//	public void setAmount(BigDecimal amount) {
//		this.amount = amount;
//	}
//
//	public BigDecimal getAmountOrgMoney() {
//		return this.amountOrgMoney;
//	}
//
//	public void setAmountOrgMoney(BigDecimal amountOrgMoney) {
//		this.amountOrgMoney = amountOrgMoney;
//	}
//
//	public String getAoNo() {
//		return this.aoNo;
//	}
//
//	public void setAoNo(String aoNo) {
//		this.aoNo = aoNo;
//	}
//
//	public BigDecimal getBefDegreeContri() {
//		return this.befDegreeContri;
//	}
//
//	public void setBefDegreeContri(BigDecimal befDegreeContri) {
//		this.befDegreeContri = befDegreeContri;
//	}
//
//	public String getCurType() {
//		return this.curType;
//	}
//
//	public void setCurType(String curType) {
//		this.curType = curType;
//	}
//
//	public String getCustId() {
//		return this.custId;
//	}
//
//	public void setCustId(String custId) {
//		this.custId = custId;
//	}
//
//	public BigDecimal getDepositeAvgM() {
//		return this.depositeAvgM;
//	}
//
//	public void setDepositeAvgM(BigDecimal depositeAvgM) {
//		this.depositeAvgM = depositeAvgM;
//	}
//
//	public BigDecimal getDepositeAvgQ() {
//		return this.depositeAvgQ;
//	}
//
//	public void setDepositeAvgQ(BigDecimal depositeAvgQ) {
//		this.depositeAvgQ = depositeAvgQ;
//	}
//
//	public BigDecimal getDepositeAvgY() {
//		return this.depositeAvgY;
//	}
//
//	public void setDepositeAvgY(BigDecimal depositeAvgY) {
//		this.depositeAvgY = depositeAvgY;
//	}
//
//	public Date getEtlDate() {
//		return this.etlDate;
//	}
//
//	public void setEtlDate(Date etlDate) {
//		this.etlDate = etlDate;
//	}
//
//	public BigDecimal getFtp() {
//		return this.ftp;
//	}
//
//	public void setFtp(BigDecimal ftp) {
//		this.ftp = ftp;
//	}
//
//	public String getKjzhbz() {
//		return this.kjzhbz;
//	}
//
//	public void setKjzhbz(String kjzhbz) {
//		this.kjzhbz = kjzhbz;
//	}
//
//	public BigDecimal getKjzhye() {
//		return this.kjzhye;
//	}
//
//	public void setKjzhye(BigDecimal kjzhye) {
//		this.kjzhye = kjzhye;
//	}
//
//	public Date getLogoutAccountDate() {
//		return this.logoutAccountDate;
//	}
//
//	public void setLogoutAccountDate(Date logoutAccountDate) {
//		this.logoutAccountDate = logoutAccountDate;
//	}
//
//	public Date getMatureDate() {
//		return this.matureDate;
//	}
//
//	public void setMatureDate(Date matureDate) {
//		this.matureDate = matureDate;
//	}
//
//	public Date getOpenAccountDate() {
//		return this.openAccountDate;
//	}
//
//	public void setOpenAccountDate(Date openAccountDate) {
//		this.openAccountDate = openAccountDate;
//	}
//
//	public String getOrgName() {
//		return this.orgName;
//	}
//
//	public void setOrgName(String orgName) {
//		this.orgName = orgName;
//	}
//
//	public String getOrgNo() {
//		return this.orgNo;
//	}
//
//	public void setOrgNo(String orgNo) {
//		this.orgNo = orgNo;
//	}
//
//	public BigDecimal getRate() {
//		return this.rate;
//	}
//
//	public void setRate(BigDecimal rate) {
//		this.rate = rate;
//	}
//
//	public BigDecimal getRoa() {
//		return this.roa;
//	}
//
//	public void setRoa(BigDecimal roa) {
//		this.roa = roa;
//	}
//
//	public String getSequence() {
//		return this.sequence;
//	}
//
//	public void setSequence(String sequence) {
//		this.sequence = sequence;
//	}
//
//	public Date getStartInterDate() {
//		return this.startInterDate;
//	}
//
//	public void setStartInterDate(Date startInterDate) {
//		this.startInterDate = startInterDate;
//	}
//
//	public String getSubjects() {
//		return this.subjects;
//	}
//
//	public void setSubjects(String subjects) {
//		this.subjects = subjects;
//	}
//
//	public BigDecimal getTransTimes() {
//		return this.transTimes;
//	}
//
//	public void setTransTimes(BigDecimal transTimes) {
//		this.transTimes = transTimes;
//	}
//
//}