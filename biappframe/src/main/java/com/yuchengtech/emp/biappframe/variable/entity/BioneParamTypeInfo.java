package com.yuchengtech.emp.biappframe.variable.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the BIONE_PARAM_TYPE_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_PARAM_TYPE_INFO")
public class BioneParamTypeInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(name="PARAM_TYPE_ID", unique=true, nullable=false, length=32)
	private String paramTypeId;

	@Column(name="LOGIC_SYS_NO", nullable=false, length=32)
	private String logicSysNo;

	@Column(name="PARAM_TYPE_NAME", length=100)
	private String paramTypeName;

	@Column(name="PARAM_TYPE_NO", nullable=false, length=32)
	private String paramTypeNo;

	@Column(length=500)
	private String remark;

	@Column(name="UP_NO", nullable=false, length=32)
	private String upNo;

    public BioneParamTypeInfo() {
    }

	public String getParamTypeId() {
		return this.paramTypeId;
	}

	public void setParamTypeId(String paramTypeId) {
		this.paramTypeId = paramTypeId;
	}

	public String getLogicSysNo() {
		return this.logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getParamTypeName() {
		return this.paramTypeName;
	}

	public void setParamTypeName(String paramTypeName) {
		this.paramTypeName = paramTypeName;
	}

	public String getParamTypeNo() {
		return this.paramTypeNo;
	}

	public void setParamTypeNo(String paramTypeNo) {
		this.paramTypeNo = paramTypeNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpNo() {
		return this.upNo;
	}

	public void setUpNo(String upNo) {
		this.upNo = upNo;
	}

}