package com.yuchengtech.bcrm.custview.model;

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
 * The persistent class for the ACRM_F_CI_EVENT database table.
 * 
 */
@Entity
@Table(name="ACRM_F_CI_EVENT")
public class AcrmFCiEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
    @SequenceGenerator(name="ACRM_F_CI_EVENT_EVENT_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACRM_F_CI_EVENT_EVENT_ID_GENERATOR")
	@Column(name ="EVENT_ID",unique=true, nullable=false)
	private BigDecimal eventId;
    
    @Temporal( TemporalType.DATE)
	@Column(name="CRM_DT")
	private Date crmDt;

    @Temporal( TemporalType.DATE)
    @Column(name="REMIND_TIME")
    private Date remindTime;
    
    @Column(name="REMIND_PPL")
    private String remindPpl;
    
	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="EVENT_DESC")
	private String eventDesc;

	@Column(name="EVENT_NAME")
	private String eventName;

	@Column(name="EVENT_TYP")
	private String eventTyp;

    @Temporal( TemporalType.DATE)
	@Column(name="FS_DT")
	private Date fsDt;

	@Column(name="WARN_FLG")
	private String warnFlg;

    @Temporal( TemporalType.DATE)
	private Date whdt;

	private String whry;
	
	@Column(name="CUST_SOURCE")
	private String custSource;
	
	/**
	 * 提醒对象 --- 客户经理本人
	 */
	@Column(name="REMIND_PPL_CM")
	private String remindPplCm;
	
	/**
	 * 提醒对象 --- 本机构主管
	 */
	@Column(name="REMIND_PPL_DRC")
	private String remindPplDrc;
	
    public String getRemindPplCm() {
		return remindPplCm;
	}

	public void setRemindPplCm(String remindPplCm) {
		this.remindPplCm = remindPplCm;
	}

	public String getRemindPplDrc() {
		return remindPplDrc;
	}

	public void setRemindPplDrc(String remindPplDrc) {
		this.remindPplDrc = remindPplDrc;
	}

	public Date getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}

	public String getRemindPpl() {
		return remindPpl;
	}

	public void setRemindPpl(String remindPpl) {
		this.remindPpl = remindPpl;
	}

	public String getCustSource() {
		return custSource;
	}

	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}

	public AcrmFCiEvent() {
    }

	public Date getCrmDt() {
		return this.crmDt;
	}

	public void setCrmDt(Date crmDt) {
		this.crmDt = crmDt;
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

	public String getEventDesc() {
		return this.eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public BigDecimal getEventId() {
		return this.eventId;
	}

	public void setEventId(BigDecimal eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventTyp() {
		return this.eventTyp;
	}

	public void setEventTyp(String eventTyp) {
		this.eventTyp = eventTyp;
	}

	public Date getFsDt() {
		return this.fsDt;
	}

	public void setFsDt(Date fsDt) {
		this.fsDt = fsDt;
	}

	public String getWarnFlg() {
		return this.warnFlg;
	}

	public void setWarnFlg(String warnFlg) {
		this.warnFlg = warnFlg;
	}

	public Date getWhdt() {
		return this.whdt;
	}

	public void setWhdt(Date whdt) {
		this.whdt = whdt;
	}

	public String getWhry() {
		return this.whry;
	}

	public void setWhry(String whry) {
		this.whry = whry;
	}

}