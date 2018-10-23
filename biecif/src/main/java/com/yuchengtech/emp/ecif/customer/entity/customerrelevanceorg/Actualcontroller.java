package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the ACTUALCONTROLLER database table.
 * 
 */
@Entity
@Table(name="ACTUALCONTROLLER")
public class Actualcontroller implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ACTUAL_CONTROLLER_INFO_ID", unique=true, nullable=false)
	private Long actualControllerInfoId;

	@Column(name="ACTUAL_CTRL_BIRTHDAY",length=20)
	private String actualCtrlBirthday;

	@Column(name="ACTUAL_CTRL_GENDER", length=20)
	private String actualCtrlGender;

	@Column(name="ACTUAL_CTRL_IDENT_CHECK", length=1)
	private String actualCtrlIdentCheck;

	@Column(name="ACTUAL_CTRL_IDENT_NO", length=40)
	private String actualCtrlIdentNo;

	@Column(name="ACTUAL_CTRL_IDENT_TYPE", length=20)
	private String actualCtrlIdentType;

	@Column(name="ACTUAL_CTRL_LEGAL_REPR_REL", length=60)
	private String actualCtrlLegalReprRel;

	@Column(name="ACTUAL_CTRL_MOBILE", length=20)
	private String actualCtrlMobile;

	@Column(name="ACTUAL_CTRL_NAME", length=80)
	private String actualCtrlName;

	@Column(name="ACTUAL_CTRL_ORG_TYPE", length=20)
	private String actualCtrlOrgType;

	@Column(name="ACTUAL_CTRL_TEL", length=32)
	private String actualCtrlTel;

	@Column(name="ACTUAL_CTRL_TYPE", length=20)
	private String actualCtrlType;

	@Column(name="CUST_ID")
	private Long custId;

    public Actualcontroller() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getActualControllerInfoId() {
		return this.actualControllerInfoId;
	}

	public void setActualControllerInfoId(Long actualControllerInfoId) {
		this.actualControllerInfoId = actualControllerInfoId;
	}

	public String getActualCtrlBirthday() {
		return this.actualCtrlBirthday;
	}

	public void setActualCtrlBirthday(String actualCtrlBirthday) {
		this.actualCtrlBirthday = actualCtrlBirthday;
	}

	public String getActualCtrlGender() {
		return this.actualCtrlGender;
	}

	public void setActualCtrlGender(String actualCtrlGender) {
		this.actualCtrlGender = actualCtrlGender;
	}
	
	public String getActualCtrlIdentCheck() {
		return this.actualCtrlIdentCheck;
	}

	public void setActualCtrlIdentCheck(String actualCtrlIdentCheck) {
		this.actualCtrlIdentCheck = actualCtrlIdentCheck;
	}

	public String getActualCtrlIdentNo() {
		return this.actualCtrlIdentNo;
	}

	public void setActualCtrlIdentNo(String actualCtrlIdentNo) {
		this.actualCtrlIdentNo = actualCtrlIdentNo;
	}

	public String getActualCtrlIdentType() {
		return this.actualCtrlIdentType;
	}

	public void setActualCtrlIdentType(String actualCtrlIdentType) {
		this.actualCtrlIdentType = actualCtrlIdentType;
	}

	public String getActualCtrlLegalReprRel() {
		return this.actualCtrlLegalReprRel;
	}

	public void setActualCtrlLegalReprRel(String actualCtrlLegalReprRel) {
		this.actualCtrlLegalReprRel = actualCtrlLegalReprRel;
	}

	public String getActualCtrlMobile() {
		return this.actualCtrlMobile;
	}

	public void setActualCtrlMobile(String actualCtrlMobile) {
		this.actualCtrlMobile = actualCtrlMobile;
	}

	public String getActualCtrlName() {
		return this.actualCtrlName;
	}

	public void setActualCtrlName(String actualCtrlName) {
		this.actualCtrlName = actualCtrlName;
	}

	public String getActualCtrlOrgType() {
		return this.actualCtrlOrgType;
	}

	public void setActualCtrlOrgType(String actualCtrlOrgType) {
		this.actualCtrlOrgType = actualCtrlOrgType;
	}

	public String getActualCtrlTel() {
		return this.actualCtrlTel;
	}

	public void setActualCtrlTel(String actualCtrlTel) {
		this.actualCtrlTel = actualCtrlTel;
	}

	public String getActualCtrlType() {
		return this.actualCtrlType;
	}

	public void setActualCtrlType(String actualCtrlType) {
		this.actualCtrlType = actualCtrlType;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

}