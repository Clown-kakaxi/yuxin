package com.yuchengtech.bcrm.workplat.model;

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
 * The persistent class for the OCRM_F_WP_SCHEDULE_VISIT database table.
 * 
 */
@Entity
@Table(name="OCRM_F_WP_SCHEDULE_VISIT")
public class OcrmFWpScheduleVisit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_WP_SCHEDULE_VISIT_VID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_WP_SCHEDULE_VISIT_VID_GENERATOR")
	@Column(name="V_ID")
	private Long vId;

	@Column(name="ARANGE_ID")
	private String arangeId;

	@Column(name="CUST_ID")
	private String custId;

	@Column(name="CUST_NAME")
	private String custName;

	@Column(name="CUST_TYPE")
	private String custType;

	private String phone;

	@Column(name="SCH_ID")
	private BigDecimal schId;

	@Column(name="VISIT_NOTE")
	private String visitNote;

	@Column(name="VISIT_STAT")
	private String visitStat;

	@Column(name="VISIT_TYPE")
	private String visitType;

	private String visitor;
	
	 @Temporal( TemporalType.DATE)
		@Column(name="SCH_EDN_TIME")
		private Date schEdnTime;
	 
	 @Temporal( TemporalType.DATE)
		@Column(name="SCH_START_TIME")
		private Date schStartTime;
	 
	 @Column(name="USER_ID")
		private String userId;

		@Column(name="USER_NAME")
		private String userName;
		
		

    public Date getSchEdnTime() {
			return schEdnTime;
		}

		public void setSchEdnTime(Date schEdnTime) {
			this.schEdnTime = schEdnTime;
		}

		public Date getSchStartTime() {
			return schStartTime;
		}

		public void setSchStartTime(Date schStartTime) {
			this.schStartTime = schStartTime;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

	public OcrmFWpScheduleVisit() {
    }

	public Long getVId() {
		return this.vId;
	}

	public void setVId(Long vId) {
		this.vId = vId;
	}

	public String getArangeId() {
		return this.arangeId;
	}

	public void setArangeId(String arangeId) {
		this.arangeId = arangeId;
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

	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getSchId() {
		return this.schId;
	}

	public void setSchId(BigDecimal schId) {
		this.schId = schId;
	}

	public String getVisitNote() {
		return this.visitNote;
	}

	public void setVisitNote(String visitNote) {
		this.visitNote = visitNote;
	}

	public String getVisitStat() {
		return this.visitStat;
	}

	public void setVisitStat(String visitStat) {
		this.visitStat = visitStat;
	}

	public String getVisitType() {
		return this.visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getVisitor() {
		return this.visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

}