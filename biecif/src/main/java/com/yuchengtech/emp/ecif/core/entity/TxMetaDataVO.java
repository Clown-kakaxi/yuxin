package com.yuchengtech.emp.ecif.core.entity;


import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the COL_DEF database table.
 * 
 */
public class TxMetaDataVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tabSchema;
	
	private String tabName;
	
	private String colName;

	private String dataType;	
	
	private Long tabId;	
	
	private Long colId;	

	private Long dataLen;	
	
	private Long dataPrec;		
	
	private String nulls;		

	private String txIds;	
	
	private String txCodes;
	

	private String txCodesDesc;
	
	private String changeValue;	
	
	private String changeDesc;			

	private String state;			

	public String getTxIds() {
		return txIds;
	}

	public void setTxIds(String txIds) {
		this.txIds = txIds;
	}

	public TxMetaDataVO() {
    }

	public String getTabSchema() {
		return tabSchema;
	}

	public void setTabSchema(String tabSchema) {
		this.tabSchema = tabSchema;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getTxCodes() {
		return txCodes;
	}

	public void setTxCodes(String txCodes) {
		this.txCodes = txCodes;
	}

	public String getChangeDesc() {
		return changeDesc;
	}

	public void setChangeDesc(String changeDesc) {
		this.changeDesc = changeDesc;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public void setNulls(String nulls) {
		this.nulls = nulls;
	}

	public String getTxCodesDesc() {
		return txCodesDesc;
	}

	public void setTxCodesDesc(String txCodesDesc) {
		this.txCodesDesc = txCodesDesc;
	}

	public String getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(String changeValue) {
		this.changeValue = changeValue;
	}
	
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
	
}