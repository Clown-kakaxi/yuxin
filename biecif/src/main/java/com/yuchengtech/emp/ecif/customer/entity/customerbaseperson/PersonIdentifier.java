package com.yuchengtech.emp.ecif.customer.entity.customerbaseperson;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;
//custId不是主键，不知道怎么映射
/**
 * 
 * <pre>
 * Title:PersonIdentifier的实体类
 * Description: 个人信息
 * </pre>
 * 
 * @author zhengyukun zhengyk3@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Entity
@Table(name="M_CI_PER_IDENTIFIER")
public class PersonIdentifier implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(generator = "PERIDENT_SEQ")
	@GenericGenerator(name = "PERIDENT_SEQ", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_PER_IDENT_ID") })
	@Column(name = "IDENT_ID", unique = true, nullable = false)
	private Long identId;

	@Column(name="CUST_ID")
	private Long custId;
	
	@Column(name="IDENT_TYPE", length=20)
	private String identType;
	
	@Column(name="IDENT_NO", length=40)
	private String identNo;
	
	@Column(name="IDENT_CUST_NAME", length=50)
	private String identCustName;
	
	@Column(name="IDENT_DESC", length=80)
	private String identDesc;
	
	@Column(name="COUNTRY_OR_REGION", length=20)
	private String countryOrRegion;
	
	@Column(name="IDENT_ORG", length=40)
	private String identOrg;
	
	@Column(name="IDENT_APPROVE_UNIT", length=40)
	private String identApproveUnit;
	
	@Column(name="IDENT_CHECK_FLAG", length=1)
	private String identCheckFlag;
	
	@Column(name="IDENT_CHECKING_DATE", length=10)
	private String identCheckingDate;
	
	@Column(name="IDENT_CHECKED_DATE", length=10)
	private String identCheckedDate;
	
	@Column(name="IDENT_VALID_PERIOD", length=20)
	private String identValidFeriod;
	
	@Column(name="IDENT_EFFECTIVE_DATE", length=10)
	private String identEffectiveDate;
	
	@Column(name="IDENT_EXPIRED_DATE", length=10)
	private String identExpiredDate;
	
	@Column(name="IDENT_VALID_FLAG", length=1)
	private String identValidFlag;
	
	@Column(name="IDENT_PERIOD", length=20)
	private String identPeriod;
	
	@Column(name="VERIFY_DATE", length=10)
	private String verifyDate;
	
	@Column(name="VERIFY_EMPLOYEE", length=20)
	private String verifyEmployee;
	
	@Column(name="VERIFY_RESULT", length=20)
	private String verifyResult;
	
	@Column(name="MAIN_IDENT_FLAG", length=1)
	private String mainIdentFlag;
	
	@Column(name="OPEN_IDENT_FLAG", length=1)
	private String openIdentFlag;

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
	
    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getIdentId() {
		return identId;
	}

	public void setIdentId(Long identId) {
		this.identId = identId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getIdentType() {
		return identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	public String getIdentNo() {
		return identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	public String getIdentCustName() {
		return identCustName;
	}

	public void setIdentCustName(String identCustName) {
		this.identCustName = identCustName;
	}

	public String getIdentDesc() {
		return identDesc;
	}

	public void setIdentDesc(String identDesc) {
		this.identDesc = identDesc;
	}

	public String getCountryOrRegion() {
		return countryOrRegion;
	}

	public void setCountryOrRegion(String countryOrRegion) {
		this.countryOrRegion = countryOrRegion;
	}

	public String getIdentOrg() {
		return identOrg;
	}

	public void setIdentOrg(String identOrg) {
		this.identOrg = identOrg;
	}

	public String getIdentApproveUnit() {
		return identApproveUnit;
	}

	public void setIdentApproveUnit(String identApproveUnit) {
		this.identApproveUnit = identApproveUnit;
	}

	public String getIdentCheckFlag() {
		return identCheckFlag;
	}

	public void setIdentCheckFlag(String identCheckFlag) {
		this.identCheckFlag = identCheckFlag;
	}

	public String getIdentCheckingDate() {
		return identCheckingDate;
	}

	public void setIdentCheckingDate(String identCheckingDate) {
		this.identCheckingDate = identCheckingDate;
	}

	public String getIdentCheckedDate() {
		return identCheckedDate;
	}

	public void setIdentCheckedDate(String identCheckedDate) {
		this.identCheckedDate = identCheckedDate;
	}

	public String getIdentValidFeriod() {
		return identValidFeriod;
	}

	public void setIdentValidFeriod(String identValidFeriod) {
		this.identValidFeriod = identValidFeriod;
	}

	public String getIdentEffectiveDate() {
		return identEffectiveDate;
	}

	public void setIdentEffectiveDate(String identEffectiveDate) {
		this.identEffectiveDate = identEffectiveDate;
	}

	public String getIdentExpiredDate() {
		return identExpiredDate;
	}

	public void setIdentExpiredDate(String identExpiredDate) {
		this.identExpiredDate = identExpiredDate;
	}

	public String getIdentValidFlag() {
		return identValidFlag;
	}

	public void setIdentValidFlag(String identValidFlag) {
		this.identValidFlag = identValidFlag;
	}

	public String getIdentPeriod() {
		return identPeriod;
	}

	public void setIdentPeriod(String identPeriod) {
		this.identPeriod = identPeriod;
	}

	public String getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(String verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getVerifyEmployee() {
		return verifyEmployee;
	}

	public void setVerifyEmployee(String verifyEmployee) {
		this.verifyEmployee = verifyEmployee;
	}

	public String getVerifyResult() {
		return verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	public String getMainIdentFlag() {
		return mainIdentFlag;
	}

	public void setMainIdentFlag(String mainIdentFlag) {
		this.mainIdentFlag = mainIdentFlag;
	}

	public String getOpenIdentFlag() {
		return openIdentFlag;
	}

	public void setOpenIdentFlag(String openIdentFlag) {
		this.openIdentFlag = openIdentFlag;
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
