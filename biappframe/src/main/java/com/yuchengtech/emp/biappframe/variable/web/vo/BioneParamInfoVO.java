package com.yuchengtech.emp.biappframe.variable.web.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 * Title: 系统参数VO
 * Description:
 * </pre>
 * 
 * @author yangyuhui yangyh4@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class BioneParamInfoVO {
	
	private String paramId;
	
	private String logicSysNo;
	
	private BigDecimal orderNo;
	
	private String paramName;
	
	private String paramTypeName;
	
	private String paramTypeNo;
	
	private String paramValue;
	
	private String remark;
	
	private String upNo;
	
	private String upParamName;
	
	private List<BioneParamInfoVO> children = new ArrayList<BioneParamInfoVO>();

	
	public String getUpParamName() {
		return upParamName;
	}

	public void setUpParamName(String upParamName) {
		this.upParamName = upParamName;
	}

	public List<BioneParamInfoVO> getChildren() {
		return children;
	}

	public void setChildren(List<BioneParamInfoVO> children) {
		this.children = children;
	}

	public String getUpNo() {
		return upNo;
	}

	public void setUpNo(String upNo) {
		this.upNo = upNo;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public BigDecimal getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamTypeName() {
		return paramTypeName;
	}

	public void setParamTypeName(String paramTypeName) {
		this.paramTypeName = paramTypeName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getParamTypeNo() {
		return paramTypeNo;
	}

	public void setParamTypeNo(String paramTypeNo) {
		this.paramTypeNo = paramTypeNo;
	}
}
