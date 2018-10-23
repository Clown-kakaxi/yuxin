package com.ytec.fubonecif.domain;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MPubFarenInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "M_PUB_FAREN_INFO")
public class MPubFarenInfo implements java.io.Serializable {

	// Fields

	private String farenNo;
	private String farenName;
	private String nameAbbr;
	private String pinyinName;
	private String pinyinAbbr;
	private String farenDesc;
	private String validFlag;
	private Date startDate;
	private Date endDate;
	private String lastUpdateSys;
	private String lastUpdateUser;
	private Timestamp lastUpdateTm;
	private String txSeqNo;

	// Constructors

	/** default constructor */
	public MPubFarenInfo() {
	}

	/** minimal constructor */
	public MPubFarenInfo(String farenNo) {
		this.farenNo = farenNo;
	}

	/** full constructor */
	public MPubFarenInfo(String farenNo, String farenName, String nameAbbr,
			String pinyinName, String pinyinAbbr, String farenDesc,
			String validFlag, Date startDate, Date endDate,
			String lastUpdateSys, String lastUpdateUser,
			Timestamp lastUpdateTm, String txSeqNo) {
		this.farenNo = farenNo;
		this.farenName = farenName;
		this.nameAbbr = nameAbbr;
		this.pinyinName = pinyinName;
		this.pinyinAbbr = pinyinAbbr;
		this.farenDesc = farenDesc;
		this.validFlag = validFlag;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lastUpdateSys = lastUpdateSys;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdateTm = lastUpdateTm;
		this.txSeqNo = txSeqNo;
	}

	// Property accessors
	@Id
	@Column(name = "FAREN_NO", unique = true, nullable = false, length = 20)
	public String getFarenNo() {
		return this.farenNo;
	}

	public void setFarenNo(String farenNo) {
		this.farenNo = farenNo;
	}

	@Column(name = "FAREN_NAME", length = 80)
	public String getFarenName() {
		return this.farenName;
	}

	public void setFarenName(String farenName) {
		this.farenName = farenName;
	}

	@Column(name = "NAME_ABBR", length = 20)
	public String getNameAbbr() {
		return this.nameAbbr;
	}

	public void setNameAbbr(String nameAbbr) {
		this.nameAbbr = nameAbbr;
	}

	@Column(name = "PINYIN_NAME", length = 80)
	public String getPinyinName() {
		return this.pinyinName;
	}

	public void setPinyinName(String pinyinName) {
		this.pinyinName = pinyinName;
	}

	@Column(name = "PINYIN_ABBR", length = 20)
	public String getPinyinAbbr() {
		return this.pinyinAbbr;
	}

	public void setPinyinAbbr(String pinyinAbbr) {
		this.pinyinAbbr = pinyinAbbr;
	}

	@Column(name = "FAREN_DESC")
	public String getFarenDesc() {
		return this.farenDesc;
	}

	public void setFarenDesc(String farenDesc) {
		this.farenDesc = farenDesc;
	}

	@Column(name = "VALID_FLAG", length = 1)
	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

}