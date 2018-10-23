package com.ytec.fubonecif.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HMCiGroupInfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMCiGroupInfoId implements java.io.Serializable {

	// Fields

	private BigDecimal groupId;
	private String groupNo;
	private String groupType;
	private String groupName;
	private String groupStatus;
	private String groupRootCustId;
	private String grpFinanceType;
	private String groupMemo;
	private String groupHostOrgNo;
	private String groupRootAddress;
	private Date creataDate;
	private String createUserId;
	private String createUserName;
	private String createUserOrgId;
	private String groupNameMain;
	private String gao;
	private String gaoOrg;
	private Double creditAmt;
	private String creditCur;
	private Date dueDate;
	private String corpNameSub;
	private String useSituation;
	private String addrRegist;
	private BigDecimal employeeNum;
	private BigDecimal gurantCusNum;
	private BigDecimal employeeNumFormal;
	private BigDecimal pendingMemberNum;
	private BigDecimal externalSecurityNum;
	private String groupForm;
	private Double usedAmt;
	private Double loanBalance;
	private Double badLoanBalance;
	private String guaranteeType;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;
	private String hisOperSys;
	private String hisOperType;
	private Timestamp hisOperTime;
	private String hisDataDate;

	// Constructors

	/** default constructor */
	public HMCiGroupInfoId() {
	}

	/** minimal constructor */
	public HMCiGroupInfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMCiGroupInfoId(BigDecimal groupId, String groupNo,
			String groupType, String groupName, String groupStatus,
			String groupRootCustId, String grpFinanceType, String groupMemo,
			String groupHostOrgNo, String groupRootAddress, Date creataDate,
			String createUserId, String createUserName, String createUserOrgId,
			String groupNameMain, String gao, String gaoOrg, Double creditAmt,
			String creditCur, Date dueDate, String corpNameSub,
			String useSituation, String addrRegist, BigDecimal employeeNum,
			BigDecimal gurantCusNum, BigDecimal employeeNumFormal,
			BigDecimal pendingMemberNum, BigDecimal externalSecurityNum,
			String groupForm, Double usedAmt, Double loanBalance,
			Double badLoanBalance, String guaranteeType, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.groupId = groupId;
		this.groupNo = groupNo;
		this.groupType = groupType;
		this.groupName = groupName;
		this.groupStatus = groupStatus;
		this.groupRootCustId = groupRootCustId;
		this.grpFinanceType = grpFinanceType;
		this.groupMemo = groupMemo;
		this.groupHostOrgNo = groupHostOrgNo;
		this.groupRootAddress = groupRootAddress;
		this.creataDate = creataDate;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createUserOrgId = createUserOrgId;
		this.groupNameMain = groupNameMain;
		this.gao = gao;
		this.gaoOrg = gaoOrg;
		this.creditAmt = creditAmt;
		this.creditCur = creditCur;
		this.dueDate = dueDate;
		this.corpNameSub = corpNameSub;
		this.useSituation = useSituation;
		this.addrRegist = addrRegist;
		this.employeeNum = employeeNum;
		this.gurantCusNum = gurantCusNum;
		this.employeeNumFormal = employeeNumFormal;
		this.pendingMemberNum = pendingMemberNum;
		this.externalSecurityNum = externalSecurityNum;
		this.groupForm = groupForm;
		this.usedAmt = usedAmt;
		this.loanBalance = loanBalance;
		this.badLoanBalance = badLoanBalance;
		this.guaranteeType = guaranteeType;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
		this.hisOperSys = hisOperSys;
		this.hisOperType = hisOperType;
		this.hisOperTime = hisOperTime;
		this.hisDataDate = hisDataDate;
	}

	// Property accessors

	@Column(name = "GROUP_ID", precision = 22, scale = 0)
	public BigDecimal getGroupId() {
		return this.groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	@Column(name = "GROUP_NO", length = 20)
	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	@Column(name = "GROUP_TYPE", length = 20)
	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	@Column(name = "GROUP_NAME", length = 80)
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "GROUP_STATUS", length = 20)
	public String getGroupStatus() {
		return this.groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	@Column(name = "GROUP_ROOT_CUST_ID", length = 20)
	public String getGroupRootCustId() {
		return this.groupRootCustId;
	}

	public void setGroupRootCustId(String groupRootCustId) {
		this.groupRootCustId = groupRootCustId;
	}

	@Column(name = "GRP_FINANCE_TYPE", length = 20)
	public String getGrpFinanceType() {
		return this.grpFinanceType;
	}

	public void setGrpFinanceType(String grpFinanceType) {
		this.grpFinanceType = grpFinanceType;
	}

	@Column(name = "GROUP_MEMO", length = 200)
	public String getGroupMemo() {
		return this.groupMemo;
	}

	public void setGroupMemo(String groupMemo) {
		this.groupMemo = groupMemo;
	}

	@Column(name = "GROUP_HOST_ORG_NO", length = 20)
	public String getGroupHostOrgNo() {
		return this.groupHostOrgNo;
	}

	public void setGroupHostOrgNo(String groupHostOrgNo) {
		this.groupHostOrgNo = groupHostOrgNo;
	}

	@Column(name = "GROUP_ROOT_ADDRESS", length = 200)
	public String getGroupRootAddress() {
		return this.groupRootAddress;
	}

	public void setGroupRootAddress(String groupRootAddress) {
		this.groupRootAddress = groupRootAddress;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATA_DATE", length = 7)
	public Date getCreataDate() {
		return this.creataDate;
	}

	public void setCreataDate(Date creataDate) {
		this.creataDate = creataDate;
	}

	@Column(name = "CREATE_USER_ID", length = 20)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "CREATE_USER_NAME", length = 80)
	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	@Column(name = "CREATE_USER_ORG_ID", length = 20)
	public String getCreateUserOrgId() {
		return this.createUserOrgId;
	}

	public void setCreateUserOrgId(String createUserOrgId) {
		this.createUserOrgId = createUserOrgId;
	}

	@Column(name = "GROUP_NAME_MAIN", length = 80)
	public String getGroupNameMain() {
		return this.groupNameMain;
	}

	public void setGroupNameMain(String groupNameMain) {
		this.groupNameMain = groupNameMain;
	}

	@Column(name = "GAO", length = 20)
	public String getGao() {
		return this.gao;
	}

	public void setGao(String gao) {
		this.gao = gao;
	}

	@Column(name = "GAO_ORG", length = 20)
	public String getGaoOrg() {
		return this.gaoOrg;
	}

	public void setGaoOrg(String gaoOrg) {
		this.gaoOrg = gaoOrg;
	}

	@Column(name = "CREDIT_AMT", precision = 20)
	public Double getCreditAmt() {
		return this.creditAmt;
	}

	public void setCreditAmt(Double creditAmt) {
		this.creditAmt = creditAmt;
	}

	@Column(name = "CREDIT_CUR", length = 20)
	public String getCreditCur() {
		return this.creditCur;
	}

	public void setCreditCur(String creditCur) {
		this.creditCur = creditCur;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DUE_DATE", length = 7)
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Column(name = "CORP_NAME_SUB", length = 200)
	public String getCorpNameSub() {
		return this.corpNameSub;
	}

	public void setCorpNameSub(String corpNameSub) {
		this.corpNameSub = corpNameSub;
	}

	@Column(name = "USE_SITUATION", length = 200)
	public String getUseSituation() {
		return this.useSituation;
	}

	public void setUseSituation(String useSituation) {
		this.useSituation = useSituation;
	}

	@Column(name = "ADDR_REGIST", length = 200)
	public String getAddrRegist() {
		return this.addrRegist;
	}

	public void setAddrRegist(String addrRegist) {
		this.addrRegist = addrRegist;
	}

	@Column(name = "EMPLOYEE_NUM", precision = 22, scale = 0)
	public BigDecimal getEmployeeNum() {
		return this.employeeNum;
	}

	public void setEmployeeNum(BigDecimal employeeNum) {
		this.employeeNum = employeeNum;
	}

	@Column(name = "GURANT_CUS_NUM", precision = 22, scale = 0)
	public BigDecimal getGurantCusNum() {
		return this.gurantCusNum;
	}

	public void setGurantCusNum(BigDecimal gurantCusNum) {
		this.gurantCusNum = gurantCusNum;
	}

	@Column(name = "EMPLOYEE_NUM_FORMAL", precision = 22, scale = 0)
	public BigDecimal getEmployeeNumFormal() {
		return this.employeeNumFormal;
	}

	public void setEmployeeNumFormal(BigDecimal employeeNumFormal) {
		this.employeeNumFormal = employeeNumFormal;
	}

	@Column(name = "PENDING_MEMBER_NUM", precision = 22, scale = 0)
	public BigDecimal getPendingMemberNum() {
		return this.pendingMemberNum;
	}

	public void setPendingMemberNum(BigDecimal pendingMemberNum) {
		this.pendingMemberNum = pendingMemberNum;
	}

	@Column(name = "EXTERNAL_SECURITY_NUM", precision = 22, scale = 0)
	public BigDecimal getExternalSecurityNum() {
		return this.externalSecurityNum;
	}

	public void setExternalSecurityNum(BigDecimal externalSecurityNum) {
		this.externalSecurityNum = externalSecurityNum;
	}

	@Column(name = "GROUP_FORM", length = 20)
	public String getGroupForm() {
		return this.groupForm;
	}

	public void setGroupForm(String groupForm) {
		this.groupForm = groupForm;
	}

	@Column(name = "USED_AMT", precision = 20)
	public Double getUsedAmt() {
		return this.usedAmt;
	}

	public void setUsedAmt(Double usedAmt) {
		this.usedAmt = usedAmt;
	}

	@Column(name = "LOAN_BALANCE", precision = 20)
	public Double getLoanBalance() {
		return this.loanBalance;
	}

	public void setLoanBalance(Double loanBalance) {
		this.loanBalance = loanBalance;
	}

	@Column(name = "BAD_LOAN_BALANCE", precision = 20)
	public Double getBadLoanBalance() {
		return this.badLoanBalance;
	}

	public void setBadLoanBalance(Double badLoanBalance) {
		this.badLoanBalance = badLoanBalance;
	}

	@Column(name = "GUARANTEE_TYPE", length = 20)
	public String getGuaranteeType() {
		return this.guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	@Column(name = "LAST_UPDATE_SYS", length = 20)
	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	@Column(name = "LAST_UPDATE_USER", length = 20)
	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_TM", length = 11)
	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	@Column(name = "TX_SEQ_NO", length = 32)
	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	@Column(name = "HIS_OPER_SYS", length = 20)
	public String getHisOperSys() {
		return this.hisOperSys;
	}

	public void setHisOperSys(String hisOperSys) {
		this.hisOperSys = hisOperSys;
	}

	@Column(name = "HIS_OPER_TYPE", length = 2)
	public String getHisOperType() {
		return this.hisOperType;
	}

	public void setHisOperType(String hisOperType) {
		this.hisOperType = hisOperType;
	}

	@Column(name = "HIS_OPER_TIME", nullable = false, length = 11)
	public Timestamp getHisOperTime() {
		return this.hisOperTime;
	}

	public void setHisOperTime(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	@Column(name = "HIS_DATA_DATE", length = 10)
	public String getHisDataDate() {
		return this.hisDataDate;
	}

	public void setHisDataDate(String hisDataDate) {
		this.hisDataDate = hisDataDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HMCiGroupInfoId))
			return false;
		HMCiGroupInfoId castOther = (HMCiGroupInfoId) other;

		return ((this.getGroupId() == castOther.getGroupId()) || (this
				.getGroupId() != null
				&& castOther.getGroupId() != null && this.getGroupId().equals(
				castOther.getGroupId())))
				&& ((this.getGroupNo() == castOther.getGroupNo()) || (this
						.getGroupNo() != null
						&& castOther.getGroupNo() != null && this.getGroupNo()
						.equals(castOther.getGroupNo())))
				&& ((this.getGroupType() == castOther.getGroupType()) || (this
						.getGroupType() != null
						&& castOther.getGroupType() != null && this
						.getGroupType().equals(castOther.getGroupType())))
				&& ((this.getGroupName() == castOther.getGroupName()) || (this
						.getGroupName() != null
						&& castOther.getGroupName() != null && this
						.getGroupName().equals(castOther.getGroupName())))
				&& ((this.getGroupStatus() == castOther.getGroupStatus()) || (this
						.getGroupStatus() != null
						&& castOther.getGroupStatus() != null && this
						.getGroupStatus().equals(castOther.getGroupStatus())))
				&& ((this.getGroupRootCustId() == castOther
						.getGroupRootCustId()) || (this.getGroupRootCustId() != null
						&& castOther.getGroupRootCustId() != null && this
						.getGroupRootCustId().equals(
								castOther.getGroupRootCustId())))
				&& ((this.getGrpFinanceType() == castOther.getGrpFinanceType()) || (this
						.getGrpFinanceType() != null
						&& castOther.getGrpFinanceType() != null && this
						.getGrpFinanceType().equals(
								castOther.getGrpFinanceType())))
				&& ((this.getGroupMemo() == castOther.getGroupMemo()) || (this
						.getGroupMemo() != null
						&& castOther.getGroupMemo() != null && this
						.getGroupMemo().equals(castOther.getGroupMemo())))
				&& ((this.getGroupHostOrgNo() == castOther.getGroupHostOrgNo()) || (this
						.getGroupHostOrgNo() != null
						&& castOther.getGroupHostOrgNo() != null && this
						.getGroupHostOrgNo().equals(
								castOther.getGroupHostOrgNo())))
				&& ((this.getGroupRootAddress() == castOther
						.getGroupRootAddress()) || (this.getGroupRootAddress() != null
						&& castOther.getGroupRootAddress() != null && this
						.getGroupRootAddress().equals(
								castOther.getGroupRootAddress())))
				&& ((this.getCreataDate() == castOther.getCreataDate()) || (this
						.getCreataDate() != null
						&& castOther.getCreataDate() != null && this
						.getCreataDate().equals(castOther.getCreataDate())))
				&& ((this.getCreateUserId() == castOther.getCreateUserId()) || (this
						.getCreateUserId() != null
						&& castOther.getCreateUserId() != null && this
						.getCreateUserId().equals(castOther.getCreateUserId())))
				&& ((this.getCreateUserName() == castOther.getCreateUserName()) || (this
						.getCreateUserName() != null
						&& castOther.getCreateUserName() != null && this
						.getCreateUserName().equals(
								castOther.getCreateUserName())))
				&& ((this.getCreateUserOrgId() == castOther
						.getCreateUserOrgId()) || (this.getCreateUserOrgId() != null
						&& castOther.getCreateUserOrgId() != null && this
						.getCreateUserOrgId().equals(
								castOther.getCreateUserOrgId())))
				&& ((this.getGroupNameMain() == castOther.getGroupNameMain()) || (this
						.getGroupNameMain() != null
						&& castOther.getGroupNameMain() != null && this
						.getGroupNameMain()
						.equals(castOther.getGroupNameMain())))
				&& ((this.getGao() == castOther.getGao()) || (this.getGao() != null
						&& castOther.getGao() != null && this.getGao().equals(
						castOther.getGao())))
				&& ((this.getGaoOrg() == castOther.getGaoOrg()) || (this
						.getGaoOrg() != null
						&& castOther.getGaoOrg() != null && this.getGaoOrg()
						.equals(castOther.getGaoOrg())))
				&& ((this.getCreditAmt() == castOther.getCreditAmt()) || (this
						.getCreditAmt() != null
						&& castOther.getCreditAmt() != null && this
						.getCreditAmt().equals(castOther.getCreditAmt())))
				&& ((this.getCreditCur() == castOther.getCreditCur()) || (this
						.getCreditCur() != null
						&& castOther.getCreditCur() != null && this
						.getCreditCur().equals(castOther.getCreditCur())))
				&& ((this.getDueDate() == castOther.getDueDate()) || (this
						.getDueDate() != null
						&& castOther.getDueDate() != null && this.getDueDate()
						.equals(castOther.getDueDate())))
				&& ((this.getCorpNameSub() == castOther.getCorpNameSub()) || (this
						.getCorpNameSub() != null
						&& castOther.getCorpNameSub() != null && this
						.getCorpNameSub().equals(castOther.getCorpNameSub())))
				&& ((this.getUseSituation() == castOther.getUseSituation()) || (this
						.getUseSituation() != null
						&& castOther.getUseSituation() != null && this
						.getUseSituation().equals(castOther.getUseSituation())))
				&& ((this.getAddrRegist() == castOther.getAddrRegist()) || (this
						.getAddrRegist() != null
						&& castOther.getAddrRegist() != null && this
						.getAddrRegist().equals(castOther.getAddrRegist())))
				&& ((this.getEmployeeNum() == castOther.getEmployeeNum()) || (this
						.getEmployeeNum() != null
						&& castOther.getEmployeeNum() != null && this
						.getEmployeeNum().equals(castOther.getEmployeeNum())))
				&& ((this.getGurantCusNum() == castOther.getGurantCusNum()) || (this
						.getGurantCusNum() != null
						&& castOther.getGurantCusNum() != null && this
						.getGurantCusNum().equals(castOther.getGurantCusNum())))
				&& ((this.getEmployeeNumFormal() == castOther
						.getEmployeeNumFormal()) || (this
						.getEmployeeNumFormal() != null
						&& castOther.getEmployeeNumFormal() != null && this
						.getEmployeeNumFormal().equals(
								castOther.getEmployeeNumFormal())))
				&& ((this.getPendingMemberNum() == castOther
						.getPendingMemberNum()) || (this.getPendingMemberNum() != null
						&& castOther.getPendingMemberNum() != null && this
						.getPendingMemberNum().equals(
								castOther.getPendingMemberNum())))
				&& ((this.getExternalSecurityNum() == castOther
						.getExternalSecurityNum()) || (this
						.getExternalSecurityNum() != null
						&& castOther.getExternalSecurityNum() != null && this
						.getExternalSecurityNum().equals(
								castOther.getExternalSecurityNum())))
				&& ((this.getGroupForm() == castOther.getGroupForm()) || (this
						.getGroupForm() != null
						&& castOther.getGroupForm() != null && this
						.getGroupForm().equals(castOther.getGroupForm())))
				&& ((this.getUsedAmt() == castOther.getUsedAmt()) || (this
						.getUsedAmt() != null
						&& castOther.getUsedAmt() != null && this.getUsedAmt()
						.equals(castOther.getUsedAmt())))
				&& ((this.getLoanBalance() == castOther.getLoanBalance()) || (this
						.getLoanBalance() != null
						&& castOther.getLoanBalance() != null && this
						.getLoanBalance().equals(castOther.getLoanBalance())))
				&& ((this.getBadLoanBalance() == castOther.getBadLoanBalance()) || (this
						.getBadLoanBalance() != null
						&& castOther.getBadLoanBalance() != null && this
						.getBadLoanBalance().equals(
								castOther.getBadLoanBalance())))
				&& ((this.getGuaranteeType() == castOther.getGuaranteeType()) || (this
						.getGuaranteeType() != null
						&& castOther.getGuaranteeType() != null && this
						.getGuaranteeType()
						.equals(castOther.getGuaranteeType())))
				&& ((this.getLastUpdateSys() == castOther.getLastUpdateSys()) || (this
						.getLastUpdateSys() != null
						&& castOther.getLastUpdateSys() != null && this
						.getLastUpdateSys()
						.equals(castOther.getLastUpdateSys())))
				&& ((this.getLastUpdateUser() == castOther.getLastUpdateUser()) || (this
						.getLastUpdateUser() != null
						&& castOther.getLastUpdateUser() != null && this
						.getLastUpdateUser().equals(
								castOther.getLastUpdateUser())))
				&& ((this.getLastUpdateTm() == castOther.getLastUpdateTm()) || (this
						.getLastUpdateTm() != null
						&& castOther.getLastUpdateTm() != null && this
						.getLastUpdateTm().equals(castOther.getLastUpdateTm())))
				&& ((this.getTxSeqNo() == castOther.getTxSeqNo()) || (this
						.getTxSeqNo() != null
						&& castOther.getTxSeqNo() != null && this.getTxSeqNo()
						.equals(castOther.getTxSeqNo())))
				&& ((this.getHisOperSys() == castOther.getHisOperSys()) || (this
						.getHisOperSys() != null
						&& castOther.getHisOperSys() != null && this
						.getHisOperSys().equals(castOther.getHisOperSys())))
				&& ((this.getHisOperType() == castOther.getHisOperType()) || (this
						.getHisOperType() != null
						&& castOther.getHisOperType() != null && this
						.getHisOperType().equals(castOther.getHisOperType())))
				&& ((this.getHisOperTime() == castOther.getHisOperTime()) || (this
						.getHisOperTime() != null
						&& castOther.getHisOperTime() != null && this
						.getHisOperTime().equals(castOther.getHisOperTime())))
				&& ((this.getHisDataDate() == castOther.getHisDataDate()) || (this
						.getHisDataDate() != null
						&& castOther.getHisDataDate() != null && this
						.getHisDataDate().equals(castOther.getHisDataDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getGroupId() == null ? 0 : this.getGroupId().hashCode());
		result = 37 * result
				+ (getGroupNo() == null ? 0 : this.getGroupNo().hashCode());
		result = 37 * result
				+ (getGroupType() == null ? 0 : this.getGroupType().hashCode());
		result = 37 * result
				+ (getGroupName() == null ? 0 : this.getGroupName().hashCode());
		result = 37
				* result
				+ (getGroupStatus() == null ? 0 : this.getGroupStatus()
						.hashCode());
		result = 37
				* result
				+ (getGroupRootCustId() == null ? 0 : this.getGroupRootCustId()
						.hashCode());
		result = 37
				* result
				+ (getGrpFinanceType() == null ? 0 : this.getGrpFinanceType()
						.hashCode());
		result = 37 * result
				+ (getGroupMemo() == null ? 0 : this.getGroupMemo().hashCode());
		result = 37
				* result
				+ (getGroupHostOrgNo() == null ? 0 : this.getGroupHostOrgNo()
						.hashCode());
		result = 37
				* result
				+ (getGroupRootAddress() == null ? 0 : this
						.getGroupRootAddress().hashCode());
		result = 37
				* result
				+ (getCreataDate() == null ? 0 : this.getCreataDate()
						.hashCode());
		result = 37
				* result
				+ (getCreateUserId() == null ? 0 : this.getCreateUserId()
						.hashCode());
		result = 37
				* result
				+ (getCreateUserName() == null ? 0 : this.getCreateUserName()
						.hashCode());
		result = 37
				* result
				+ (getCreateUserOrgId() == null ? 0 : this.getCreateUserOrgId()
						.hashCode());
		result = 37
				* result
				+ (getGroupNameMain() == null ? 0 : this.getGroupNameMain()
						.hashCode());
		result = 37 * result
				+ (getGao() == null ? 0 : this.getGao().hashCode());
		result = 37 * result
				+ (getGaoOrg() == null ? 0 : this.getGaoOrg().hashCode());
		result = 37 * result
				+ (getCreditAmt() == null ? 0 : this.getCreditAmt().hashCode());
		result = 37 * result
				+ (getCreditCur() == null ? 0 : this.getCreditCur().hashCode());
		result = 37 * result
				+ (getDueDate() == null ? 0 : this.getDueDate().hashCode());
		result = 37
				* result
				+ (getCorpNameSub() == null ? 0 : this.getCorpNameSub()
						.hashCode());
		result = 37
				* result
				+ (getUseSituation() == null ? 0 : this.getUseSituation()
						.hashCode());
		result = 37
				* result
				+ (getAddrRegist() == null ? 0 : this.getAddrRegist()
						.hashCode());
		result = 37
				* result
				+ (getEmployeeNum() == null ? 0 : this.getEmployeeNum()
						.hashCode());
		result = 37
				* result
				+ (getGurantCusNum() == null ? 0 : this.getGurantCusNum()
						.hashCode());
		result = 37
				* result
				+ (getEmployeeNumFormal() == null ? 0 : this
						.getEmployeeNumFormal().hashCode());
		result = 37
				* result
				+ (getPendingMemberNum() == null ? 0 : this
						.getPendingMemberNum().hashCode());
		result = 37
				* result
				+ (getExternalSecurityNum() == null ? 0 : this
						.getExternalSecurityNum().hashCode());
		result = 37 * result
				+ (getGroupForm() == null ? 0 : this.getGroupForm().hashCode());
		result = 37 * result
				+ (getUsedAmt() == null ? 0 : this.getUsedAmt().hashCode());
		result = 37
				* result
				+ (getLoanBalance() == null ? 0 : this.getLoanBalance()
						.hashCode());
		result = 37
				* result
				+ (getBadLoanBalance() == null ? 0 : this.getBadLoanBalance()
						.hashCode());
		result = 37
				* result
				+ (getGuaranteeType() == null ? 0 : this.getGuaranteeType()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateSys() == null ? 0 : this.getLastUpdateSys()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateUser() == null ? 0 : this.getLastUpdateUser()
						.hashCode());
		result = 37
				* result
				+ (getLastUpdateTm() == null ? 0 : this.getLastUpdateTm()
						.hashCode());
		result = 37 * result
				+ (getTxSeqNo() == null ? 0 : this.getTxSeqNo().hashCode());
		result = 37
				* result
				+ (getHisOperSys() == null ? 0 : this.getHisOperSys()
						.hashCode());
		result = 37
				* result
				+ (getHisOperType() == null ? 0 : this.getHisOperType()
						.hashCode());
		result = 37
				* result
				+ (getHisOperTime() == null ? 0 : this.getHisOperTime()
						.hashCode());
		result = 37
				* result
				+ (getHisDataDate() == null ? 0 : this.getHisDataDate()
						.hashCode());
		return result;
	}

}