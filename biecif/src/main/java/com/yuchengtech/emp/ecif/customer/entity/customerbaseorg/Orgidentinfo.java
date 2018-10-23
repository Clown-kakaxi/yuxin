package com.yuchengtech.emp.ecif.customer.entity.customerbaseorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the ORGIDENTINFO database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_IDENTIFIER")
public class Orgidentinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORGIDENT_SEQ")
	@GenericGenerator(name = "ORGIDENT_SEQ", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_ORG_IDENT_ID") })
	@Column(name = "IDENT_ID", unique = true, nullable = false)
	private Long identId;

	@Column(name="COUNTRY_OR_REGION", length=20)
	private String countryOrRegion;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="IDENT_APPROVE_UNIT", length=40)
	private String identApproveUnit;

	@Column(name="IDENT_CHECK_FLAG", length=1)
	private String identCheckFlag;

	@Column(name="IDENT_CHECKED_DATE", length=20)
	private String identCheckedDate;

	@Column(name="IDENT_CHECKING_DATE", length=20)
	private String identCheckingDate;

	@Column(name="IDENT_CUST_NAME", length=200)
	private String identCustName;

	@Column(name="IDENT_DESC", length=60)
	private String identDesc;

	@Column(name="IDENT_EFFECTIVE_DATE", length=20)
	private String identEffectiveDate;

	@Column(name="IDENT_EXPRIED_DATE", length=20)
	private String identExpriedDate;

	@Column(name="IDENT_NO", length=40)
	private String identNo;

	@Column(name="IDENT_ORG", length=60)
	private String identOrg;

	@Column(name="IDENT_PERIOD")
	private Long identPeriod;

	@Column(name="IDENT_TYPE", length=20)
	private String identType;

	@Column(name="IDENT_VALID_FLAG", length=1)
	private String identValidFlag;

	@Column(name="IDENT_VALID_PERIOD", length=10)
	private String identValidPeriod;

	@Column(name="MAIN_IDENT_FLAG", length=1)
	private String mainIdentFlag;

	@Column(name="OPEN_IDENT_FLAG", length=1)
	private String openIdentFlag;

	@Column(name="VERIFY_DATE",length=20)
	private String verifyDate;

	@Column(name="VERIFY_EMPLOYEE", length=20)
	private String verifyEmployee;

	@Column(name="VERIFY_RESULT", length=20)
	private String verifyResult;
	
//	LAST_UPDATE_SYS	最后更新系统
//	LAST_UPDATE_USER	最后更新人
//	LAST_UPDATE_TM	最后更新时间
//	TX_SEQ_NO	交易流水号
	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;
	
	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;
	
	@Column(name="LAST_UPDATE_TM")
	private String lastUpdateTm;
	
	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Orgidentinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getIdentId() {
		return this.identId;
	}

	public void setIdentId(Long identId) {
		this.identId = identId;
	}

	public String getCountryOrRegion() {
		return this.countryOrRegion;
	}

	public void setCountryOrRegion(String countryOrRegion) {
		this.countryOrRegion = countryOrRegion;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getIdentApproveUnit() {
		return this.identApproveUnit;
	}

	public void setIdentApproveUnit(String identApproveUnit) {
		this.identApproveUnit = identApproveUnit;
	}

	public String getIdentCheckFlag() {
		return this.identCheckFlag;
	}

	public void setIdentCheckFlag(String identCheckFlag) {
		this.identCheckFlag = identCheckFlag;
	}

	public String getIdentCheckedDate() {
		return this.identCheckedDate;
	}

	public void setIdentCheckedDate(String identCheckedDate) {
		this.identCheckedDate = identCheckedDate;
	}

	public String getIdentCheckingDate() {
		return this.identCheckingDate;
	}

	public void setIdentCheckingDate(String identCheckingDate) {
		this.identCheckingDate = identCheckingDate;
	}

	public String getIdentCustName() {
		return this.identCustName;
	}

	public void setIdentCustName(String identCustName) {
		this.identCustName = identCustName;
	}

	public String getIdentDesc() {
		return this.identDesc;
	}

	public void setIdentDesc(String identDesc) {
		this.identDesc = identDesc;
	}

	public String getIdentEffectiveDate() {
		return this.identEffectiveDate;
	}

	public void setIdentEffectiveDate(String identEffectiveDate) {
		this.identEffectiveDate = identEffectiveDate;
	}

	public String getIdentExpriedDate() {
		return this.identExpriedDate;
	}

	public void setIdentExpriedDate(String identExpriedDate) {
		this.identExpriedDate = identExpriedDate;
	}

	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentOrg() {
		return this.identOrg;
	}

	public void setIdentOrg(String identOrg) {
		this.identOrg = identOrg;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getIdentPeriod() {
		return this.identPeriod;
	}

	public void setIdentPeriod(Long identPeriod) {
		this.identPeriod = identPeriod;
	}

	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIdentValidFlag() {
		return this.identValidFlag;
	}

	public void setIdentValidFlag(String identValidFlag) {
		this.identValidFlag = identValidFlag;
	}

	public String getIdentValidPeriod() {
		return this.identValidPeriod;
	}

	public void setIdentValidPeriod(String identValidPeriod) {
		this.identValidPeriod = identValidPeriod;
	}

	public String getMainIdentFlag() {
		return this.mainIdentFlag;
	}

	public void setMainIdentFlag(String mainIdentFlag) {
		this.mainIdentFlag = mainIdentFlag;
	}

	public String getOpenIdentFlag() {
		return this.openIdentFlag;
	}

	public void setOpenIdentFlag(String openIdentFlag) {
		this.openIdentFlag = openIdentFlag;
	}

	public String getVerifyDate() {
		return this.verifyDate;
	}

	public void setVerifyDate(String verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getVerifyEmployee() {
		return this.verifyEmployee;
	}

	public void setVerifyEmployee(String verifyEmployee) {
		this.verifyEmployee = verifyEmployee;
	}

	public String getVerifyResult() {
		return this.verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	public String getLastUpdateSys() {
		return lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getTxSeqNo() {
		return txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getLastUpdateTm() {
		return lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}
}