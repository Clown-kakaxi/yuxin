package com.yuchengtech.emp.ecif.core.entity;



import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the TX_METADATA_CHECK_RESULT database table.
 * 
 */
@Entity
@Table(name="TX_METADATA_CHECK_RESULT")
public class TxMetadataCheckResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RESULT_ID")
	private Long resultId;

	@Column(name="CHANGE_DESC")
	private String changeDesc;

	@Column(name="CHANGE_VALUE")
	private String changeValue;

	@Column(name="COL_NAME")
	private String colName;

	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="TAB_ID")
	private Long tabId;
	
	@Column(name="COL_ID")
	private Long colId;
	
	@Column(name="DATA_LEN")
	private BigDecimal dataLen;

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

	@Column(name="DATA_PREC")
	private BigDecimal dataPrec;

	@Column(name="DATA_TYPE")
	private String dataType;

	private String nulls;

	@Column(name="STATE")
	private String state;

	@Column(name="TAB_NAME")
	private String tabName;

	@Column(name="TAB_SCHEMA")
	private String tabSchema;

	@Column(name="TX_CODES")
	private String txCodes;

	@Column(name="TX_CODES_DESC")
	private String txCodesDesc;

    public TxMetadataCheckResult() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getResultId() {
		return this.resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	public String getChangeDesc() {
		return this.changeDesc;
	}

	public void setChangeDesc(String changeDesc) {
		this.changeDesc = changeDesc;
	}

	public String getChangeValue() {
		return this.changeValue;
	}

	public void setChangeValue(String changeValue) {
		this.changeValue = changeValue;
	}

	public String getColName() {
		return this.colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
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

	public BigDecimal getDataLen() {
		return this.dataLen;
	}

	public void setDataLen(BigDecimal dataLen) {
		this.dataLen = dataLen;
	}

	public BigDecimal getDataPrec() {
		return this.dataPrec;
	}

	public void setDataPrec(BigDecimal dataPrec) {
		this.dataPrec = dataPrec;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getNulls() {
		return this.nulls;
	}

	public void setNulls(String nulls) {
		this.nulls = nulls;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTabName() {
		return this.tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getTabSchema() {
		return this.tabSchema;
	}

	public void setTabSchema(String tabSchema) {
		this.tabSchema = tabSchema;
	}

	public String getTxCodes() {
		return this.txCodes;
	}

	public void setTxCodes(String txCodes) {
		this.txCodes = txCodes;
	}

	public String getTxCodesDesc() {
		return this.txCodesDesc;
	}

	public void setTxCodesDesc(String txCodesDesc) {
		this.txCodesDesc = txCodesDesc;
	}

}