package com.yuchengtech.bcrm.custmanager.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the OCRM_F_CI_CALLCENTER_UPHIS database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_CALLCENTER_UPHIS")
public class OcrmFCiCallcenterUphi implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private BigDecimal id;

    @Temporal( TemporalType.DATE)
	@Column(name="APPR_DATE")
	private Date apprDate;

	@Column(name="APPR_FLAG")
	private String apprFlag;

	@Column(name="APPR_USER")
	private String apprUser;

	@Column(name="CUST_ID")
	private String custId;
	

	@Column(name="UPDATE_DATE")
	private String updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;
	
	@Column(name="UPDATE_FLAG")
	private String updateFlag ;
	
	@Column(name="COMMENTCONTENT")
	private String commentcontent ;

    public OcrmFCiCallcenterUphi() {
    }

	public Date getApprDate() {
		return this.apprDate;
	}

	public void setApprDate(Date apprDate) {
		this.apprDate = apprDate;
	}

	public String getApprFlag() {
		return this.apprFlag;
	}

	public void setApprFlag(String apprFlag) {
		this.apprFlag = apprFlag;
	}

	public String getApprUser() {
		return this.apprUser;
	}

	public void setApprUser(String apprUser) {
		this.apprUser = apprUser;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getCommentcontent() {
		return commentcontent;
	}

	public void setCommentcontent(String commentcontent) {
		this.commentcontent = commentcontent;
	}
	

}