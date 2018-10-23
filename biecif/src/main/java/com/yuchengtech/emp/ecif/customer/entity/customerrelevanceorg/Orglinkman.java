package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the ORGLINKMAN database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_ORGLINKMAN")
public class Orglinkman implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LINKMAN_ID", unique=true, nullable=false)
	private Long linkmanId;

	@Column(name="ADDRESS",length=255)
	private String address;
	
	@Column(name="BIRTHDAY",length=20)
	private String birthday;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="DUTY",length=20)
	private String duty;

	@Column(name="EMAIL",length=40)
	private String email;

	@Column(name="GENDER",length=20)
	private String gender;

	@Column(name="LINKMAN_IDENT_NO", length=40)
	private String linkmanIdentNo;

	@Column(name="LINKMAN_IDENT_TYPE", length=20)
	private String linkmanIdentType;

	@Column(name="LINKMAN_NAME", length=80)
	private String linkmanName;

	@Column(name="LINKMAN_TYPE", length=20)
	private String linkmanType;

	@Column(name="MOBILE",length=20)
	private String mobile;

	@Column(name="OFFICE_TEL", length=20)
	private String officeTel;

    public Orglinkman() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getLinkmanId() {
		return this.linkmanId;
	}

	public void setLinkmanId(Long linkmanId) {
		this.linkmanId = linkmanId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLinkmanIdentNo() {
		return this.linkmanIdentNo;
	}

	public void setLinkmanIdentNo(String linkmanIdentNo) {
		this.linkmanIdentNo = linkmanIdentNo;
	}

	public String getLinkmanIdentType() {
		return this.linkmanIdentType;
	}

	public void setLinkmanIdentType(String linkmanIdentType) {
		this.linkmanIdentType = linkmanIdentType;
	}

	public String getLinkmanName() {
		return this.linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getLinkmanType() {
		return this.linkmanType;
	}

	public void setLinkmanType(String linkmanType) {
		this.linkmanType = linkmanType;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficeTel() {
		return this.officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

}