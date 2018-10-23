package com.yuchengtech.bcrm.oneKeyAccount.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_F_CI_WXTEST database table.
 * 
 */
@Entity
@Table(name="OCRM_F_CI_WXTEST")
//@NamedQuery(name="OcrmFCiWxtest.findAll", query="SELECT o FROM OcrmFCiWxtest o")
public class OcrmFCiWxtest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FILED_ID")
	private String filedId;

	@Column(name="FIELD_DESC")
	private String fieldDesc;

	@Column(name="FIELD_ISREQUIRED")
	private String fieldIsrequired;

	@Column(name="FIELD_LENGTH")
	private BigDecimal fieldLength;

	@Column(name="FIELD_TYPE")
	private String fieldType;

	@Column(name="FILED_CODE")
	private String filedCode;

	@Column(name="FILED_NAME")
	private String filedName;

	@Column(name="MSG_TYPE")
	private String msgType;

	@Column(name="SYS_TYPE")
	private String sysType;
	
	@Column(name="FIELD_XML_TAG")
	private String fieldXmlTag;
	
	@Column(name="SEQ")
	private String seq;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public OcrmFCiWxtest() {
	}

	public String getFiledId() {
		return this.filedId;
	}

	public void setFiledId(String filedId) {
		this.filedId = filedId;
	}

	public String getFieldDesc() {
		return this.fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public String getFieldIsrequired() {
		return this.fieldIsrequired;
	}

	public void setFieldIsrequired(String fieldIsrequired) {
		this.fieldIsrequired = fieldIsrequired;
	}

	public BigDecimal getFieldLength() {
		return this.fieldLength;
	}

	public void setFieldLength(BigDecimal fieldLength) {
		this.fieldLength = fieldLength;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFiledCode() {
		return this.filedCode;
	}

	public void setFiledCode(String filedCode) {
		this.filedCode = filedCode;
	}

	public String getFiledName() {
		return this.filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getSysType() {
		return this.sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getFieldXmlTag() {
		return fieldXmlTag;
	}

	public void setFieldXmlTag(String fieldXmlTag) {
		this.fieldXmlTag = fieldXmlTag;
	}

}