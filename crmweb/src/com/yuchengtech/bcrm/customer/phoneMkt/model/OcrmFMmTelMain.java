package com.yuchengtech.bcrm.customer.phoneMkt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the OCRM_F_MM_TEL_MAIN database table.
 * 
 */
@Entity
@Table(name="OCRM_F_MM_TEL_MAIN")
public class OcrmFMmTelMain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_F_MM_TEL_MAIN_ID_GENERATOR", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_F_MM_TEL_MAIN_ID_GENERATOR")
	@Column(unique=true, nullable=false, precision=19)
	private Long id;

	@Column(name="BIS_TYPE", length=10)
	private String bisType;

	@Column(length=500)
	private String content;

	@Column(name="CUST_ID", length=21)
	private String custId;

	@Column(name="END_TIME")
	private Timestamp endTime;

	@Column(name="FOLLOW_DATE", length=21)
	private String followDate;

	@Column(name="FOLLOW_DO", length=10)
	private String followDo;

	@Column(name="RECORD_ID", precision=19)
	private BigDecimal recordId;

	@Column(name="STAET_TIME")
	private Timestamp staetTime;

	@Column(name="TEL_TYPE", length=10)
	private String telType;

	@Column(name="TIME_LONG", length=20)
	private String timeLong;

	@Column(name="USER_ID", length=21)
	private String userId;
	
//	@Column(name="FR_ID", length=21)
//	private String frId;

    public OcrmFMmTelMain() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
//	public String getFrId() {
//		return frId;
//	}
//
//	public void setFrId(String frId) {
//		this.frId = frId;
//	}

	public String getBisType() {
		return this.bisType;
	}

	public void setBisType(String bisType) {
		this.bisType = bisType;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getFollowDate() {
		return this.followDate;
	}

	public void setFollowDate(String followDate) {
		this.followDate = followDate;
	}

	public String getFollowDo() {
		return this.followDo;
	}

	public void setFollowDo(String followDo) {
		this.followDo = followDo;
	}

	public BigDecimal getRecordId() {
		return this.recordId;
	}

	public void setRecordId(BigDecimal recordId) {
		this.recordId = recordId;
	}

	public Timestamp getStaetTime() {
		return this.staetTime;
	}

	public void setStaetTime(Timestamp staetTime) {
		this.staetTime = staetTime;
	}

	public String getTelType() {
		return this.telType;
	}

	public void setTelType(String telType) {
		this.telType = telType;
	}

	public String getTimeLong() {
		return this.timeLong;
	}

	public void setTimeLong(String timeLong) {
		this.timeLong = timeLong;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}