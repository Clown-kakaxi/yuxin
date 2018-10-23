package com.yuchengtech.emp.biappframe.authres.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIONE_FUNC_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_FUNC_INFO")
public class BioneFuncInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FUNC_ID", unique=true, nullable=false, length=32)
	private String funcId;

	@Column(name="FUNC_NAME", length=100)
	private String funcName;

	@Column(name="FUNC_STS", length=1)
	private String funcSts;

	@Column(name="MODULE_ID", nullable=false, length=32)
	private String moduleId;

	@Column(name="NAV_ICON", length=20)
	private String navIcon;

	@Column(name="NAV_PATH", length=100)
	private String navPath;

	@Column(name="ORDER_NO", precision=5)
	private BigDecimal orderNo;

	@Column(length=500)
	private String remark;

	@Column(name="UP_ID", nullable=false, length=32)
	private String upId;

    public BioneFuncInfo() {
    }

	public String getFuncId() {
		return this.funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
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

}