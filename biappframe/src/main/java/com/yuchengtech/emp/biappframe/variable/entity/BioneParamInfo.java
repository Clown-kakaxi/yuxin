package com.yuchengtech.emp.biappframe.variable.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;


/**
 * The persistent class for the BIONE_PARAM_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_PARAM_INFO")
public class BioneParamInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(name="PARAM_ID", unique=true, nullable=false, length=32)
	private String paramId;

	@Column(name="LOGIC_SYS_NO", nullable=false, length=32)
	private String logicSysNo;

	@Column(name="ORDER_NO", precision=5)
	private BigDecimal orderNo;

	@Column(name="PARAM_NAME", length=100)
	private String paramName;

	@Column(name="PARAM_TYPE_NO", nullable=false, length=32)
	private String paramTypeNo;

	@Column(name="PARAM_VALUE", length=500)
	private String paramValue;
	
	@Column(name="UP_NO",length=32)
	private String upNo;
	
	@Column(length=500)
	private String remark;

    public BioneParamInfo() {
    }

	public String getParamId() {
		return this.paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getLogicSysNo() {
		return this.logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public BigDecimal getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamTypeNo() {
		return this.paramTypeNo;
	}

	public void setParamTypeNo(String paramTypeNo) {
		this.paramTypeNo = paramTypeNo;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpNo() {
		return upNo;
	}

	public void setUpNo(String upNo) {
		this.upNo = upNo;
	}

}