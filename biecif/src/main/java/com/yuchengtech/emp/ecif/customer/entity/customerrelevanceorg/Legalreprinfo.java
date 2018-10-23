package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the LEGALREPRINFO database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_LEGALREPRINFO")
public class Legalreprinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LEGAL_REPR_INFO_ID", unique=true, nullable=false)
	private Long legalReprInfoId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="LEGAL_REPR_CONTMETH", length=20)
	private String legalReprContmeth;

	@Column(name="LEGAL_REPR_IDENT_EXPIRED_DATE",length=20)
	private String legalReprIdentExpiredDate;

	@Column(name="LEGAL_REPR_IDENT_NO", length=40)
	private String legalReprIdentNo;

	@Column(name="LEGAL_REPR_IDENT_TYPE", length=20)
	private String legalReprIdentType;

	@Column(name="LEGAL_REPR_MOBILE", length=20)
	private String legalReprMobile;

	@Column(name="LEGAL_REPR_NAME", length=60)
	private String legalReprName;

	@Column(name="LEGAL_REPR_TEL", length=32)
	private String legalReprTel;

    public Legalreprinfo() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getLegalReprInfoId() {
		return this.legalReprInfoId;
	}

	public void setLegalReprInfoId(Long legalReprInfoId) {
		this.legalReprInfoId = legalReprInfoId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getLegalReprContmeth() {
		return this.legalReprContmeth;
	}

	public void setLegalReprContmeth(String legalReprContmeth) {
		this.legalReprContmeth = legalReprContmeth;
	}

	public String getLegalReprIdentExpiredDate() {
		return this.legalReprIdentExpiredDate;
	}

	public void setLegalReprIdentExpiredDate(String legalReprIdentExpiredDate) {
		this.legalReprIdentExpiredDate = legalReprIdentExpiredDate;
	}

	public String getLegalReprIdentNo() {
		return this.legalReprIdentNo;
	}

	public void setLegalReprIdentNo(String legalReprIdentNo) {
		this.legalReprIdentNo = legalReprIdentNo;
	}

	public String getLegalReprIdentType() {
		return this.legalReprIdentType;
	}

	public void setLegalReprIdentType(String legalReprIdentType) {
		this.legalReprIdentType = legalReprIdentType;
	}

	public String getLegalReprMobile() {
		return this.legalReprMobile;
	}

	public void setLegalReprMobile(String legalReprMobile) {
		this.legalReprMobile = legalReprMobile;
	}

	public String getLegalReprName() {
		return this.legalReprName;
	}

	public void setLegalReprName(String legalReprName) {
		this.legalReprName = legalReprName;
	}

	public String getLegalReprTel() {
		return this.legalReprTel;
	}

	public void setLegalReprTel(String legalReprTel) {
		this.legalReprTel = legalReprTel;
	}

}