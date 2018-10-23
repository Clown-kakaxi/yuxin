package com.yuchengtech.emp.biappframe.variable.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the BIONE_SYS_VAR_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_SYS_VAR_INFO")
public class BioneSysVarInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="idGenerator")
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@Column(name="VAR_ID")
	private String varId;

	@Column(name="LOGIC_SYS_NO")
	private String logicSysNo;

	private String remark;

	@Column(name="VAR_NAME")
	private String varName;

	@Column(name="VAR_NO")
	private String varNo;

	@Column(name="VAR_VALUE")
	private String varValue;
	
	@Column(name="DS_ID")
	private String dsId;
	
	@Column(name="VAR_TYPE")
	private String varType;

    public BioneSysVarInfo() {
    }

	public String getVarId() {
		return this.varId;
	}

	public void setVarId(String varId) {
		this.varId = varId;
	}

	public String getLogicSysNo() {
		return this.logicSysNo;
	}

	public void setLogicSysNo(String logicSysNo) {
		this.logicSysNo = logicSysNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVarName() {
		return this.varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getVarNo() {
		return this.varNo;
	}

	public void setVarNo(String varNo) {
		this.varNo = varNo;
	}

	public String getVarValue() {
		return this.varValue;
	}

	public void setVarValue(String varValue) {
		this.varValue = varValue;
	}

	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getVarType() {
		return varType;
	}

	public void setVarType(String varType) {
		this.varType = varType;
	}
	
}