package com.yuchengtech.emp.biappframe.authres.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
/**
 * The persistent class for the BIONE_MENU_INFO database table.
 * 
 */
@Entity
@Table(name = "BIONE_MENU_INFO")
public class BioneMenuInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MENU_ID", unique = true, nullable = false, length = 32)
	private String menuId;

	@Column(name = "LOGIC_SYS_NO", nullable = false, length = 32)
	private String logicSysNo;

	@Column(name = "FUNC_ID", nullable = false, length = 32)
	private String funcId;
	
	@Column(name = "UP_ID", nullable = false, length = 32)
	private String upId;
	
	@Column(name = "INDEX_STS", nullable = true, length = 1)
	private String indexSts;

	@Column(name="ORDER_NO", precision=5)
	private BigDecimal orderNo;
	
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getLogicSysNo() {
		return logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getIndexSts() {
		return indexSts;
	}

	public void setIndexSts(String indexSts) {
		this.indexSts = indexSts;
	}

	public String getUpId() {
		return upId;
	}

	public void setUpId(String upId) {
		this.upId = upId;
	}

	public BigDecimal getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}
	
}