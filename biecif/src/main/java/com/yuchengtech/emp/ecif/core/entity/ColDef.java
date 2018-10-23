package com.yuchengtech.emp.ecif.core.entity;


import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the COL_DEF database table.
 * 
 */
@Entity
@Table(name="TX_COL_DEF")
public class ColDef implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TxColDefPK id;
	
	@Column(name="COL_NAME")
	private String colName;

	@Column(name="COL_CH_NAME")
	private String colChName;	
	
	@Column(name="COL_DESC")
	private String colDesc;	
		
	@Column(name="COL_SEQ")
	private Long colSeq;
	
	@Column(name="DATA_TYPE")
	private String dataType;	
	
	@Column(name="DATA_LEN")
	private Long dataLen;	
	
	@Column(name="DATA_PREC")
	private Long dataPrec;		
	
	@Column(name="NULLS")
	private String nulls;		
	
	@Column(name="DATA_FMT")
	private String dataFmt;		
	
	@Column(name="IS_CODE")
	private String isCode;			
	
	@Column(name="CATE_ID")
	private String cateId;		
	
	@Column(name="KEY_TYPE")
	private String keyType;				
	
	@Column(name="DECODE")
	private String decode;			
	
	
	@Column(name="CREATE_TM")
	private Timestamp createTm;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="STATE")
	private String state;

	@Column(name="UPDATE_TM")
	private Timestamp updateTm;

	@Column(name="UPDATE_USER")
	private String updateUser;

    public ColDef() {
    }

	public String getColName() {
		return colName;
	}

	public TxColDefPK getId() {
		return id;
	}

	public void setId(TxColDefPK id) {
		this.id = id;
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