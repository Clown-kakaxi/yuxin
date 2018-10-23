package com.yuchengtech.bcrm.serviceManage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the OCRM_F_MKT_TRACK_RECORD database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MKT_TRACK_RECORD")
public class MarketTrackRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TRACKRECORD_GENERATOR", sequenceName="TRACKRECORD_SEQUENCE",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRACKRECORD_GENERATOR")
	@Column(name="RECORD_ID")
	private Long recordId;

	@Column(name="CANTACT_CHANNEL")
	private String cantactChannel;

	@Temporal(TemporalType.DATE)
	@Column(name="CANTACT_DATE")
	private Date cantactDate;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	private String executor;

	@Column(name="MARKET_RESULT")
	private String marketResult;

	@Column(name="MKT_ID")
	private BigDecimal mktId;

	@Column(name="NEED_EVENT")
	private String needEvent;

	@Column(name="SERVICE_KIND")
	private String serviceKind;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;

	public MarketTrackRecord() {
	}

	public Long getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public String getCantactChannel() {
		return this.cantactChannel;
	}

	public void setCantactChannel(String cantactChannel) {
		this.cantactChannel = cantactChannel;
	}

	public Date getCantactDate() {
		return this.cantactDate;
	}

	public void setCantactDate(Date cantactDate) {
		this.cantactDate = cantactDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getExecutor() {
		return this.executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getMarketResult() {
		return this.marketResult;
	}

	public void setMarketResult(String marketResult) {
		this.marketResult = marketResult;
	}

	public BigDecimal getMktId() {
		return this.mktId;
	}

	public void setMktId(BigDecimal mktId) {
		this.mktId = mktId;
	}

	public String getNeedEvent() {
		return this.needEvent;
	}

	public void setNeedEvent(String needEvent) {
		this.needEvent = needEvent;
	}

	public String getServiceKind() {
		return this.serviceKind;
	}

	public void setServiceKind(String serviceKind) {
		this.serviceKind = serviceKind;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}