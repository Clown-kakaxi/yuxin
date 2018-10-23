package com.yuchengtech.bcrm.notice.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the OCRM_SYS_MARQUEE_NOTICE database table.
 * 
 */
@Entity
@Table(name="OCRM_SYS_MARQUEE_NOTICE")
public class OcrmSysMarqueeNotice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_SYS_MARQUEE_NOTICE_ID_GENERATOR", sequenceName="ID_OCRM_SYS_MARQUEE_NOTICE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_SYS_MARQUEE_NOTICE_ID_GENERATOR")
	private Long id;

	@Column(name="CREATE_DT")
	private Timestamp createDt;

	@Column(name="CREATE_USER")
	private String createUser;

	private String text;

	@Column(name="VALID_DT")
	private Timestamp validDt;
	
	@Transient
	private Date validDtDate;
	@Transient
	private String validDtTime;
	
	public OcrmSysMarqueeNotice() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getValidDt() {
		return this.validDt;
	}

	public void setValidDt(Timestamp validDt) {
		this.validDt = validDt;
	}

	public Date getValidDtDate() {
		return validDtDate;
	}

	public void setValidDtDate(Date validDtDate) {
		this.validDtDate = validDtDate;
	}

	public String getValidDtTime() {
		return validDtTime;
	}

	public void setValidDtTime(String validDtTime) {
		this.validDtTime = validDtTime;
	}
	

}