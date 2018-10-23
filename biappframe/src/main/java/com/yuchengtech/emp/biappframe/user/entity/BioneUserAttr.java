package com.yuchengtech.emp.biappframe.user.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIONE_USER_ATTR database table.
 * 
 */
@Entity
@Table(name="BIONE_USER_ATTR")
public class BioneUserAttr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ATTR_ID", unique=true, nullable=false, length=32)
	private String attrId;

	@Column(name="ATTR_STS", length=1)
	private String attrSts;

	@Column(name="CHECK_RULE_TYPE", length=10)
	private String checkRuleType;

	@Column(name="ELEMENT_ALIGN", length=10)
	private String elementAlign;

	@Column(name="ELEMENT_TYPE", length=10)
	private String elementType;

	@Column(name="ELEMENT_WIDTH", precision=12)
	private BigDecimal elementWidth;

	@Column(name="FIELD_LENGTH", length=10)
	private String fieldLength;

	@Column(name="FIELD_NAME", length=100)
	private String fieldName;

	@Column(name="GRP_ID", length=32)
	private String grpId;

	@Column(name="IS_ALLOW_NULL", length=1)
	private String isAllowNull;

	@Column(name="IS_ALLOW_UPDATE", length=1)
	private String isAllowUpdate;

	@Column(name="IS_EXT", length=1)
	private String isExt;

	@Column(name="IS_NEWLINE", length=1)
	private String isNewline;

	@Column(name="LABEL_ALIGN", length=10)
	private String labelAlign;

	@Column(name="LABEL_NAME", length=100)
	private String labelName;

	@Column(name="LABEL_WIDTH", precision=12)
	private BigDecimal labelWidth;

	@Column(name="ORDER_NO", precision=5)
	private BigDecimal orderNo;

	@Column(length=500)
	private String remark;

	@Column(name="COMB_DS" , length=500)
	private String combDs;
	
	@Column(name="IS_READONLY" , length=1)
	private String isReadonly;
	
	@Column(name="INIT_VALUE" , length=500)
	private String initValue;

    public BioneUserAttr() {
    }

	public String getAttrId() {
		return this.attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getAttrSts() {
		return this.attrSts;
	}

	public void setAttrSts(String attrSts) {
		this.attrSts = attrSts;
	}

	public String getCheckRuleType() {
		return this.checkRuleType;
	}

	public void setCheckRuleType(String checkRuleType) {
		this.checkRuleType = checkRuleType;
	}

	public String getElementAlign() {
		return this.elementAlign;
	}

	public void setElementAlign(String elementAlign) {
		this.elementAlign = elementAlign;
	}

	public String getElementType() {
		return this.elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public BigDecimal getElementWidth() {
		return this.elementWidth;
	}

	public void setElementWidth(BigDecimal elementWidth) {
		this.elementWidth = elementWidth;
	}

	public String getFieldLength() {
		return this.fieldLength;
	}

	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getGrpId() {
		return this.grpId;
	}

	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	public String getLabelAlign() {
		return this.labelAlign;
	}

	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}

	public String getLabelName() {
		return this.labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public BigDecimal getLabelWidth() {
		return this.labelWidth;
	}

	public void setLabelWidth(BigDecimal labelWidth) {
		this.labelWidth = labelWidth;
	}

	public BigDecimal getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getIsAllowNull() {
		return isAllowNull;
	}
	
	public String getCombDs() {
		return combDs;
	}

	public void setCombDs(String combDs) {
		this.combDs = combDs;
	}

	public void setIsAllowNull(String isAllowNull) {
		this.isAllowNull = isAllowNull;
	}

	public String getIsAllowUpdate() {
		return isAllowUpdate;
	}

	public void setIsAllowUpdate(String isAllowUpdate) {
		this.isAllowUpdate = isAllowUpdate;
	}

	public String getIsExt() {
		return isExt;
	}

	public void setIsExt(String isExt) {
		this.isExt = isExt;
	}

	public String getIsNewline() {
		return isNewline;
	}

	public void setIsNewline(String isNewline) {
		this.isNewline = isNewline;
	}

	public String getIsReadonly() {
		return isReadonly;
	}

	public void setIsReadonly(String isReadonly) {
		this.isReadonly = isReadonly;
	}

	public String getInitValue() {
		return initValue;
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}

}