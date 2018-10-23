/**
 * 
 */
package com.yuchengtech.emp.biappframe.user.web.vo;

import java.math.BigDecimal;

/**
 * <pre>
 * Title:授权对象属性VO
 * Description: 程序功能的描述 
 * </pre>
 * @author caiqy  caiqy@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class BioneUserAttrVO {
	private String attrId;

	private String objDefNo;

	private String attrSts;

	private String checkRule;

	private String displayName;

	private String elementAlign;

	private String elementType;

	private BigDecimal elementWidth;

	private String fieldLength;

	private String fieldName;

	private String ifAllowNull;

	private String ifExt;

	private String ifNewline;

	private String labelAlign;

	private String labelName;

	private BigDecimal labelWidth;

	private String queryScript;

	private String remark;

	private BigDecimal spaceWidth;

    public BioneUserAttrVO() {
    }

	//set get
    
	/**
	 * @return 返回 attrId。
	 */
	public String getAttrId() {
		return attrId;
	}

	/**
	 * @param attrId 设置 attrId。
	 */
	public void setAttrId(String attrId) {
		if(attrId != null && !"".equals(attrId)){
			try{
				this.attrId = attrId;
			}catch(Exception e){
				this.attrId = "0";
			}
		}else{			
			this.attrId = "0";
		}
	}

	/**
	 * @return 返回 objDefNo。
	 */
	public String getObjDefNo() {
		return objDefNo;
	}

	/**
	 * @param objDefNo 设置 objDefNo。
	 */
	public void setObjDefNo(String objDefNo) {
		this.objDefNo = objDefNo;
	}

	/**
	 * @return 返回 attrSts。
	 */
	public String getAttrSts() {
		return attrSts;
	}

	/**
	 * @param attrSts 设置 attrSts。
	 */
	public void setAttrSts(String attrSts) {
		this.attrSts = attrSts;
	}

	/**
	 * @return 返回 checkRule。
	 */
	public String getCheckRule() {
		return checkRule;
	}

	/**
	 * @param checkRule 设置 checkRule。
	 */
	public void setCheckRule(String checkRule) {
		this.checkRule = checkRule;
	}

	/**
	 * @return 返回 displayName。
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName 设置 displayName。
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return 返回 elementAlign。
	 */
	public String getElementAlign() {
		return elementAlign;
	}

	/**
	 * @param elementAlign 设置 elementAlign。
	 */
	public void setElementAlign(String elementAlign) {
		this.elementAlign = elementAlign;
	}

	/**
	 * @return 返回 elementType。
	 */
	public String getElementType() {
		return elementType;
	}

	/**
	 * @param elementType 设置 elementType。
	 */
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	/**
	 * @return 返回 elementWidth。
	 */
	public BigDecimal getElementWidth() {
		return elementWidth;
	}

	/**
	 * @param elementWidth 设置 elementWidth。
	 */
	public void setElementWidth(BigDecimal elementWidth) {
		this.elementWidth = elementWidth;
	}

	/**
	 * @return 返回 fieldLength。
	 */
	public String getFieldLength() {
		return fieldLength;
	}

	/**
	 * @param fieldLength 设置 fieldLength。
	 */
	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}

	/**
	 * @return 返回 fieldName。
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName 设置 fieldName。
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return 返回 ifAllowNull。
	 */
	public String getIfAllowNull() {
		return ifAllowNull;
	}

	/**
	 * @param ifAllowNull 设置 ifAllowNull。
	 */
	public void setIfAllowNull(String ifAllowNull) {
		this.ifAllowNull = ifAllowNull;
	}

	/**
	 * @return 返回 ifExt。
	 */
	public String getIfExt() {
		return ifExt;
	}

	/**
	 * @param ifExt 设置 ifExt。
	 */
	public void setIfExt(String ifExt) {
		this.ifExt = ifExt;
	}

	/**
	 * @return 返回 ifNewline。
	 */
	public String getIfNewline() {
		return ifNewline;
	}

	/**
	 * @param ifNewline 设置 ifNewline。
	 */
	public void setIfNewline(String ifNewline) {
		this.ifNewline = ifNewline;
	}

	/**
	 * @return 返回 labelAlign。
	 */
	public String getLabelAlign() {
		return labelAlign;
	}

	/**
	 * @param labelAlign 设置 labelAlign。
	 */
	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}

	/**
	 * @return 返回 labelName。
	 */
	public String getLabelName() {
		return labelName;
	}

	/**
	 * @param labelName 设置 labelName。
	 */
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	/**
	 * @return 返回 labelWidth。
	 */
	public BigDecimal getLabelWidth() {
		return labelWidth;
	}

	/**
	 * @param labelWidth 设置 labelWidth。
	 */
	public void setLabelWidth(BigDecimal labelWidth) {
		this.labelWidth = labelWidth;
	}

	/**
	 * @return 返回 queryScript。
	 */
	public String getQueryScript() {
		return queryScript;
	}

	/**
	 * @param queryScript 设置 queryScript。
	 */
	public void setQueryScript(String queryScript) {
		this.queryScript = queryScript;
	}

	/**
	 * @return 返回 remark。
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark 设置 remark。
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return 返回 spaceWidth。
	 */
	public BigDecimal getSpaceWidth() {
		return spaceWidth;
	}

	/**
	 * @param spaceWidth 设置 spaceWidth。
	 */
	public void setSpaceWidth(BigDecimal spaceWidth) {
		this.spaceWidth = spaceWidth;
	}
    
}
