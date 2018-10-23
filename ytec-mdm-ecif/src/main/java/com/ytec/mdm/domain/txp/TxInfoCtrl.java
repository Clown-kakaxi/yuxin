package com.ytec.mdm.domain.txp;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TxInfoCtrl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_INFO_CTRL")
public class TxInfoCtrl implements java.io.Serializable {

	// Fields

	private Long infoCtrlId;
	private Long infoItemId;
	private Long grantObjectId;
	private String ctrlType;
	private String ctrlDesc;
	private String ctrlStat;
	private Date startDate;
	private Date endDate;

	// Constructors

	/** default constructor */
	public TxInfoCtrl() {
	}

	/** full constructor */
	public TxInfoCtrl(Long infoItemId, Long grantObjectId,
			String ctrlType, String ctrlDesc, String ctrlStat, Date startDate,
			Date endDate) {
		this.infoItemId = infoItemId;
		this.grantObjectId = grantObjectId;
		this.ctrlType = ctrlType;
		this.ctrlDesc = ctrlDesc;
		this.ctrlStat = ctrlStat;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	// Property accessors
	@Id
		@Column(name = "INFO_CTRL_ID", unique = true, nullable = false)
	public Long getInfoCtrlId() {
		return this.infoCtrlId;
	}

	public void setInfoCtrlId(Long infoCtrlId) {
		this.infoCtrlId = infoCtrlId;
	}

	@Column(name = "INFO_ITEM_ID")
	public Long getInfoItemId() {
		return this.infoItemId;
	}

	public void setInfoItemId(Long infoItemId) {
		this.infoItemId = infoItemId;
	}

	@Column(name = "GRANT_OBJECT_ID")
	public Long getGrantObjectId() {
		return this.grantObjectId;
	}

	public void setGrantObjectId(Long grantObjectId) {
		this.grantObjectId = grantObjectId;
	}

	@Column(name = "CTRL_TYPE", length = 20)
	public String getCtrlType() {
		return this.ctrlType;
	}

	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}

	@Column(name = "CTRL_DESC")
	public String getCtrlDesc() {
		return this.ctrlDesc;
	}

	public void setCtrlDesc(String ctrlDesc) {
		this.ctrlDesc = ctrlDesc;
	}

	@Column(name = "CTRL_STAT", length = 1)
	public String getCtrlStat() {
		return this.ctrlStat;
	}

	public void setCtrlStat(String ctrlStat) {
		this.ctrlStat = ctrlStat;
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

}