package com.yuchengtech.emp.ecif.core.entity;


import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the COL_DEF database table.
 * 
 */
public class ColDefVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long tabId;
	
	private Long colId;
	
	public Long getTabId() {
		return tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	public Long getColId() {
		return colId;
	}

	public void setColId(Long colId) {
		this.colId = colId;
	}
	private String colName;

	private String colChName;	
	
	private String colDesc;	
		
	private Long colSeq;
	
	private String dataType;	
	
	private Long dataLen;	
	
	private Long dataPrec;		
	
	private String nulls;		
	
	private String dataFmt;		
	
	private String isCode;			
	
	private String cateId;		
	
	private String keyType;				
	
	private String decode;			
		
	private Timestamp createTm;

	private String createUser;

	private String state;

	private Timestamp updateTm;

	private String updateUser;
	
	private String cateName;		

    public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public ColDefVO() {
    }

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColChName() {
		return colChName;
	}

	public void setColChName(String colChName) {
		this.colChName = colChName;
	}

	public String getColDesc() {
		return colDesc;
	}

	public void setColDesc(String colDesc) {
		this.colDesc = colDesc;
	}

	public Long getColSeq() {
		return colSeq;
	}

	public void setColSeq(Long colSeq) {
		this.colSeq = colSeq;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getDataLen() {
		return dataLen;
	}

	public void setDataLen(Long dataLen) {
		this.dataLen = dataLen;
	}

	public Long getDataPrec() {
		return dataPrec;
	}

	public void setDataPrec(Long dataPrec) {
		this.dataPrec = dataPrec;
	}

	public String getNulls() {
		return nulls;
	}

	public void setNulls(String nulls) {
		this.nulls = nulls;
	}

	public String getDataFmt() {
		return dataFmt;
	}

	public void setDataFmt(String dataFmt) {
		this.dataFmt = dataFmt;
	}

	public String getIsCode() {
		return isCode;
	}

	public void setIsCode(String isCode) {
		this.isCode = isCode;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getDecode() {
		return decode;
	}

	public void setDecode(String decode) {
		this.decode = decode;
	}

	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}