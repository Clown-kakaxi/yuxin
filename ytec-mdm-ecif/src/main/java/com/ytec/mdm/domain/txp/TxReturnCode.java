package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TxReturnCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_RETURN_CODE")
public class TxReturnCode implements java.io.Serializable {

	// Fields

	private String rtnCode;
	private String class1;
	private String class2;
	private String class3;
	private String class4;
	private String rtnCodeType;
	private String rtnCodeName;
	private String chRtnMsg;
	private String enRtnMsg;
	private String otherRtnMsg;
	private String outerRtnCode;
	private String chOuterRtnMsg;
	private String enOuterRtnMsg;
	private String otherOuterRtnMsg;

	// Constructors

	/** default constructor */
	public TxReturnCode() {
	}

	/** full constructor */
	public TxReturnCode(String class1, String class2, String class3,
			String class4, String rtnCodeType, String rtnCodeName,
			String chRtnMsg, String enRtnMsg, String otherRtnMsg,
			String outerRtnCode, String chOuterRtnMsg, String enOuterRtnMsg,
			String otherOuterRtnMsg) {
		this.class1 = class1;
		this.class2 = class2;
		this.class3 = class3;
		this.class4 = class4;
		this.rtnCodeType = rtnCodeType;
		this.rtnCodeName = rtnCodeName;
		this.chRtnMsg = chRtnMsg;
		this.enRtnMsg = enRtnMsg;
		this.otherRtnMsg = otherRtnMsg;
		this.outerRtnCode = outerRtnCode;
		this.chOuterRtnMsg = chOuterRtnMsg;
		this.enOuterRtnMsg = enOuterRtnMsg;
		this.otherOuterRtnMsg = otherOuterRtnMsg;
	}

	// Property accessors
	@Id
		@Column(name = "RTN_CODE", unique = true, nullable = false, length = 10)
	public String getRtnCode() {
		return this.rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	@Column(name = "CLASS_1", length = 20)
	public String getClass1() {
		return this.class1;
	}

	public void setClass1(String class1) {
		this.class1 = class1;
	}

	@Column(name = "CLASS_2", length = 20)
	public String getClass2() {
		return this.class2;
	}

	public void setClass2(String class2) {
		this.class2 = class2;
	}

	@Column(name = "CLASS_3", length = 20)
	public String getClass3() {
		return this.class3;
	}

	public void setClass3(String class3) {
		this.class3 = class3;
	}

	@Column(name = "CLASS_4", length = 20)
	public String getClass4() {
		return this.class4;
	}

	public void setClass4(String class4) {
		this.class4 = class4;
	}

	@Column(name = "RTN_CODE_TYPE", length = 20)
	public String getRtnCodeType() {
		return this.rtnCodeType;
	}

	public void setRtnCodeType(String rtnCodeType) {
		this.rtnCodeType = rtnCodeType;
	}

	@Column(name = "RTN_CODE_NAME", length = 128)
	public String getRtnCodeName() {
		return this.rtnCodeName;
	}

	public void setRtnCodeName(String rtnCodeName) {
		this.rtnCodeName = rtnCodeName;
	}

	@Column(name = "CH_RTN_MSG")
	public String getChRtnMsg() {
		return this.chRtnMsg;
	}

	public void setChRtnMsg(String chRtnMsg) {
		this.chRtnMsg = chRtnMsg;
	}

	@Column(name = "EN_RTN_MSG")
	public String getEnRtnMsg() {
		return this.enRtnMsg;
	}

	public void setEnRtnMsg(String enRtnMsg) {
		this.enRtnMsg = enRtnMsg;
	}

	@Column(name = "OTHER_RTN_MSG")
	public String getOtherRtnMsg() {
		return this.otherRtnMsg;
	}

	public void setOtherRtnMsg(String otherRtnMsg) {
		this.otherRtnMsg = otherRtnMsg;
	}

	@Column(name = "OUTER_RTN_CODE", length = 20)
	public String getOuterRtnCode() {
		return this.outerRtnCode;
	}

	public void setOuterRtnCode(String outerRtnCode) {
		this.outerRtnCode = outerRtnCode;
	}

	@Column(name = "CH_OUTER_RTN_MSG")
	public String getChOuterRtnMsg() {
		return this.chOuterRtnMsg;
	}

	public void setChOuterRtnMsg(String chOuterRtnMsg) {
		this.chOuterRtnMsg = chOuterRtnMsg;
	}

	@Column(name = "EN_OUTER_RTN_MSG")
	public String getEnOuterRtnMsg() {
		return this.enOuterRtnMsg;
	}

	public void setEnOuterRtnMsg(String enOuterRtnMsg) {
		this.enOuterRtnMsg = enOuterRtnMsg;
	}

	@Column(name = "OTHER_OUTER_RTN_MSG")
	public String getOtherOuterRtnMsg() {
		return this.otherOuterRtnMsg;
	}

	public void setOtherOuterRtnMsg(String otherOuterRtnMsg) {
		this.otherOuterRtnMsg = otherOuterRtnMsg;
	}

}