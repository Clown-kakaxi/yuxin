package com.yuchengtech.bcrm.customer.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the OCRM_F_CI_GROUP_INFO database table.
 * 
 */
@Entity
@Table(name = "OCRM_F_CI_GROUP_INFO")
public class OcrmFCiGroupInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	private Long id;

	@Column(name = "ADDR_REGIST")
	private String addrRegist;

	@Column(name = "BAD_LOAN_BALANCE")
	private BigDecimal badLoanBalance;

	@Column(name = "CORP_NAME_SUB")
	private String corpNameSub;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATA_DATE")
	private Date creataDate;

	@Column(name = "CREATE_USER_ID")
	private String createUserId;

	@Column(name = "CREATE_USER_NAME")
	private String createUserName;

	@Column(name = "CREATE_USER_ORG_ID")
	private String createUserOrgId;

	@Column(name = "CREDIT_AMT")
	private BigDecimal creditAmt;

	@Column(name = "CREDIT_CUR")
	private String creditCur;

	@Temporal(TemporalType.DATE)
	@Column(name = "DUE_DATE")
	private Date dueDate;

	@Column(name = "EMPLOYEE_NUM")
	private BigDecimal employeeNum;

	@Column(name = "EMPLOYEE_NUM_FORMAL")
	private BigDecimal employeeNumFormal;

	@Column(name = "EXTERNAL_SECURITY_NUM")
	private BigDecimal externalSecurityNum;

	private String gao;

	@Column(name = "GAO_ORG")
	private String gaoOrg;

	@Column(name = "GROUP_FORM")
	private String groupForm;

	@Column(name = "GROUP_HOST_ORG_NO")
	private String groupHostOrgNo;

	@Column(name = "GROUP_MEMO")
	private String groupMemo;

	@Column(name = "GROUP_NAME")
	private String groupName;

	@Column(name = "GROUP_NAME_MAIN")
	private String groupNameMain;

	@Column(name = "GROUP_NO")
	private String groupNo;

	@Column(name = "GROUP_ROOT_ADDRESS")
	private String groupRootAddress;

	@Column(name = "GROUP_ROOT_CUST_ID")
	private String groupRootCustId;

	@Column(name = "GROUP_STATUS")
	private String groupStatus;

	@Column(name = "GROUP_TYPE")
	private String groupType;

	@Column(name = "GRP_FINANCE_TYPE")
	private String grpFinanceType;

	@Column(name = "GUARANTEE_TYPE")
	private String guaranteeType;

	@Column(name = "GURANT_CUS_NUM")
	private BigDecimal gurantCusNum;

	@Column(name = "LOAN_BALANCE")
	private BigDecimal loanBalance;

	@Column(name = "PENDING_MEMBER_NUM")
	private BigDecimal pendingMemberNum;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	@Column(name = "UPDATE_USER_ID")
	private String updateUserId;

	@Column(name = "USE_SITUATION")
	private String useSituation;

	@Column(name = "USED_AMT")
	private BigDecimal usedAmt;

	public OcrmFCiGroupInfo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddrRegist() {
		return this.addrRegist;
	}

	public void setAddrRegist(String addrRegist) {
		this.addrRegist = addrRegist;
	}

	public BigDecimal getBadLoanBalance() {
		return this.badLoanBalance;
	}

	public void setBadLoanBalance(BigDecimal badLoanBalance) {
		this.badLoanBalance = badLoanBalance;
	}

	public String getCorpNameSub() {
		return this.corpNameSub;
	}

	public void setCorpNameSub(String corpNameSub) {
		this.corpNameSub = corpNameSub;
	}

	public Date getCreataDate() {
		return this.creataDate;
	}

	public void setCreataDate(Date creataDate) {
		this.creataDate = creataDate;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserOrgId() {
		return this.createUserOrgId;
	}

	public void setCreateUserOrgId(String createUserOrgId) {
		this.createUserOrgId = createUserOrgId;
	}

	public BigDecimal getCreditAmt() {
		return this.creditAmt;
	}

	public void setCreditAmt(BigDecimal creditAmt) {
		this.creditAmt = creditAmt;
	}

	public String getCreditCur() {
		return this.creditCur;
	}

	public void setCreditCur(String creditCur) {
		this.creditCur = creditCur;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getEmployeeNum() {
		return this.employeeNum;
	}

	public void setEmployeeNum(BigDecimal employeeNum) {
		this.employeeNum = employeeNum;
	}

	public BigDecimal getEmployeeNumFormal() {
		return this.employeeNumFormal;
	}

	public void setEmployeeNumFormal(BigDecimal employeeNumFormal) {
		this.employeeNumFormal = employeeNumFormal;
	}

	public BigDecimal getExternalSecurityNum() {
		return this.externalSecurityNum;
	}

	public void setExternalSecurityNum(BigDecimal externalSecurityNum) {
		this.externalSecurityNum = externalSecurityNum;
	}

	public String getGao() {
		return this.gao;
	}

	public void setGao(String gao) {
		this.gao = gao;
	}

	public String getGaoOrg() {
		return this.gaoOrg;
	}

	public void setGaoOrg(String gaoOrg) {
		this.gaoOrg = gaoOrg;
	}

	public String getGroupForm() {
		return this.groupForm;
	}

	public void setGroupForm(String groupForm) {
		this.groupForm = groupForm;
	}

	public String getGroupHostOrgNo() {
		return this.groupHostOrgNo;
	}

	public void setGroupHostOrgNo(String groupHostOrgNo) {
		this.groupHostOrgNo = groupHostOrgNo;
	}

	public String getGroupMemo() {
		return this.groupMemo;
	}

	public void setGroupMemo(String groupMemo) {
		this.groupMemo = groupMemo;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupNameMain() {
		return this.groupNameMain;
	}

	public void setGroupNameMain(String groupNameMain) {
		this.groupNameMain = groupNameMain;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupRootAddress() {
		return this.groupRootAddress;
	}

	public void setGroupRootAddress(String groupRootAddress) {
		this.groupRootAddress = groupRootAddress;
	}

	public String getGroupRootCustId() {
		return this.groupRootCustId;
	}

	public void setGroupRootCustId(String groupRootCustId) {
		this.groupRootCustId = groupRootCustId;
	}

	public String getGroupStatus() {
		return this.groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getGrpFinanceType() {
		return this.grpFinanceType;
	}

	public void setGrpFinanceType(String grpFinanceType) {
		this.grpFinanceType = grpFinanceType;
	}

	public String getGuaranteeType() {
		return this.guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public BigDecimal getGurantCusNum() {
		return this.gurantCusNum;
	}

	public void setGurantCusNum(BigDecimal gurantCusNum) {
		this.gurantCusNum = gurantCusNum;
	}

	public BigDecimal getLoanBalance() {
		return this.loanBalance;
	}

	public void setLoanBalance(BigDecimal loanBalance) {
		this.loanBalance = loanBalance;
	}

	public BigDecimal getPendingMemberNum() {
		return this.pendingMemberNum;
	}

	public void setPendingMemberNum(BigDecimal pendingMemberNum) {
		this.pendingMemberNum = pendingMemberNum;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUseSituation() {
		return this.useSituation;
	}

	public void setUseSituation(String useSituation) {
		this.useSituation = useSituation;
	}

	public BigDecimal getUsedAmt() {
		return this.usedAmt;
	}

	public void setUsedAmt(BigDecimal usedAmt) {
		this.usedAmt = usedAmt;
	}

}