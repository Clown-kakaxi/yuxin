package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HMPubEmployeeInfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class HMPubEmployeeInfoId implements java.io.Serializable {

	// Fields

	private String empNo;
	private String branchNo;
	private String identType;
	private String identNo;
	private String empName;
	private String pinyinName;
	private String enName;
	private String mobilePhone;
	private String tel;
	private String email;
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
	public HMPubEmployeeInfoId() {
	}

	/** minimal constructor */
	public HMPubEmployeeInfoId(Timestamp hisOperTime) {
		this.hisOperTime = hisOperTime;
	}

	/** full constructor */
	public HMPubEmployeeInfoId(String empNo, String branchNo, String identType,
			String identNo, String empName, String pinyinName, String enName,
			String mobilePhone, String tel, String email, String lastUpdateSys,
			String lastUpdateUser, Timestamp lastUpdateTm, String txSeqNo,
			String hisOperSys, String hisOperType, Timestamp hisOperTime,
			String hisDataDate) {
		this.empNo = empNo;
		this.branchNo = branchNo;
		this.identType = identType;
		this.identNo = identNo;
		this.empName = empName;
		this.pinyinName = pinyinName;
		this.enName = enName;
		this.mobilePhone = mobilePhone;
		this.tel = tel;
		this.email = email;
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

	@Column(name = "EMP_NO", length = 20)
	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	@Column(name = "BRANCH_NO", length = 20)
	public String getBranchNo() {
		return this.branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	@Column(name = "IDENT_TYPE", length = 20)
	public String getIdentType() {
		return this.identType;
	}

	public void setIdentType(String identType) {
		this.identType = identType;
	}

	@Column(name = "IDENT_NO", length = 40)
	public String getIdentNo() {
		return this.identNo;
	}

	public void setIdentNo(String identNo) {
		this.identNo = identNo;
	}

	@Column(name = "EMP_NAME", length = 80)
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "PINYIN_NAME", length = 40)
	public String getPinyinName() {
		return this.pinyinName;
	}

	public void setPinyinName(String pinyinName) {
		this.pinyinName = pinyinName;
	}

	@Column(name = "EN_NAME", length = 40)
	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Column(name = "MOBILE_PHONE", length = 20)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "TEL", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		if (!(other instanceof HMPubEmployeeInfoId))
			return false;
		HMPubEmployeeInfoId castOther = (HMPubEmployeeInfoId) other;

		return ((this.getEmpNo() == castOther.getEmpNo()) || (this.getEmpNo() != null
				&& castOther.getEmpNo() != null && this.getEmpNo().equals(
				castOther.getEmpNo())))
				&& ((this.getBranchNo() == castOther.getBranchNo()) || (this
						.getBranchNo() != null
						&& castOther.getBranchNo() != null && this
						.getBranchNo().equals(castOther.getBranchNo())))
				&& ((this.getIdentType() == castOther.getIdentType()) || (this
						.getIdentType() != null
						&& castOther.getIdentType() != null && this
						.getIdentType().equals(castOther.getIdentType())))
				&& ((this.getIdentNo() == castOther.getIdentNo()) || (this
						.getIdentNo() != null
						&& castOther.getIdentNo() != null && this.getIdentNo()
						.equals(castOther.getIdentNo())))
				&& ((this.getEmpName() == castOther.getEmpName()) || (this
						.getEmpName() != null
						&& castOther.getEmpName() != null && this.getEmpName()
						.equals(castOther.getEmpName())))
				&& ((this.getPinyinName() == castOther.getPinyinName()) || (this
						.getPinyinName() != null
						&& castOther.getPinyinName() != null && this
						.getPinyinName().equals(castOther.getPinyinName())))
				&& ((this.getEnName() == castOther.getEnName()) || (this
						.getEnName() != null
						&& castOther.getEnName() != null && this.getEnName()
						.equals(castOther.getEnName())))
				&& ((this.getMobilePhone() == castOther.getMobilePhone()) || (this
						.getMobilePhone() != null
						&& castOther.getMobilePhone() != null && this
						.getMobilePhone().equals(castOther.getMobilePhone())))
				&& ((this.getTel() == castOther.getTel()) || (this.getTel() != null
						&& castOther.getTel() != null && this.getTel().equals(
						castOther.getTel())))
				&& ((this.getEmail() == castOther.getEmail()) || (this
						.getEmail() != null
						&& castOther.getEmail() != null && this.getEmail()
						.equals(castOther.getEmail())))
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
				+ (getEmpNo() == null ? 0 : this.getEmpNo().hashCode());
		result = 37 * result
				+ (getBranchNo() == null ? 0 : this.getBranchNo().hashCode());
		result = 37 * result
				+ (getIdentType() == null ? 0 : this.getIdentType().hashCode());
		result = 37 * result
				+ (getIdentNo() == null ? 0 : this.getIdentNo().hashCode());
		result = 37 * result
				+ (getEmpName() == null ? 0 : this.getEmpName().hashCode());
		result = 37
				* result
				+ (getPinyinName() == null ? 0 : this.getPinyinName()
						.hashCode());
		result = 37 * result
				+ (getEnName() == null ? 0 : this.getEnName().hashCode());
		result = 37
				* result
				+ (getMobilePhone() == null ? 0 : this.getMobilePhone()
						.hashCode());
		result = 37 * result
				+ (getTel() == null ? 0 : this.getTel().hashCode());
		result = 37 * result
				+ (getEmail() == null ? 0 : this.getEmail().hashCode());
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