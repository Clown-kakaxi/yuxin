package com.yuchengtech.emp.biappframe.authres.web.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BioneMenuInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String menuId;

	private String funcName;

	private String funcSts;

	private String moduleId;

	private String navIcon;

	private String navPath;

	private BigDecimal orderNo;

	private String remark;

	private String upId;

	private String indexSts;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getFuncName() {
		return this.funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncSts() {
		return this.funcSts;
	}

	public void setFuncSts(String funcSts) {
		this.funcSts = funcSts;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getNavIcon() {
		return this.navIcon;
	}

	public void setNavIcon(String navIcon) {
		this.navIcon = navIcon;
	}

	public String getNavPath() {
		return this.navPath;
	}

	public void setNavPath(String navPath) {
		this.navPath = navPath;
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

	public String getUpId() {
		return this.upId;
	}

	public void setUpId(String upId) {
		this.upId = upId;
	}

	public String getIndexSts() {
		return indexSts;
	}

	public void setIndexSts(String indexSts) {
		this.indexSts = indexSts;
	}

}