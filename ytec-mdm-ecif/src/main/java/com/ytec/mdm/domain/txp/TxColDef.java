package com.ytec.mdm.domain.txp;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TxColDef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TX_COL_DEF")
public class TxColDef implements java.io.Serializable {

	// Fields

	private TxColDefId id;
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
	private String state;
	private Timestamp createTm;
	private String createUser;
	private Timestamp updateTm;
	private String updateUser;

	// Constructors

	/** default constructor */
	public TxColDef() {
	}

	/** minimal constructor */
	public TxColDef(TxColDefId id) {
		this.id = id;
	}

	/** full constructor */
	public TxColDef(TxColDefId id, String colName, String colChName,
			String colDesc, Long colSeq, String dataType,
			Long dataLen, Long dataPrec, String nulls,
			String dataFmt, String isCode, String cateId, String keyType,
			String decode, String state, Timestamp createTm, String createUser,
			Timestamp updateTm, String updateUser) {
		this.id = id;
		this.colName = colName;
		this.colChName = colChName;
		this.colDesc = colDesc;
		this.colSeq = colSeq;
		this.dataType = dataType;
		this.dataLen = dataLen;
		this.dataPrec = dataPrec;
		this.nulls = nulls;
		this.dataFmt = dataFmt;
		this.isCode = isCode;
		this.cateId = cateId;
		this.keyType = keyType;
		this.decode = decode;
		this.state = state;
		this.createTm = createTm;
		this.createUser = createUser;
		this.updateTm = updateTm;
		this.updateUser = updateUser;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "tabId", column = @Column(name = "TAB_ID", nullable = false, precision = 22)),
			@AttributeOverride(name = "colId", column = @Column(name = "COL_ID", nullable = false, precision = 22)) })
	public TxColDefId getId() {
		return this.id;
	}

	public void setId(TxColDefId id) {
		this.id = id;
	}

	@Column(name = "COL_NAME", length = 50)
	public String getColName() {
		return this.colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	@Column(name = "COL_CH_NAME", length = 50)
	public String getColChName() {
		return this.colChName;
	}

	public void setColChName(String colChName) {
		this.colChName = colChName;
	}

	@Column(name = "COL_DESC", length = 100)
	public String getColDesc() {
		return this.colDesc;
	}

	public void setColDesc(String colDesc) {
		this.colDesc = colDesc;
	}

	@Column(name = "COL_SEQ", precision = 22)
	public Long getColSeq() {
		return this.colSeq;
	}

	public void setColSeq(Long colSeq) {
		this.colSeq = colSeq;
	}

	@Column(name = "DATA_TYPE", length = 20)
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "DATA_LEN", precision = 22)
	public Long getDataLen() {
		return this.dataLen;
	}

	public void setDataLen(Long dataLen) {
		this.dataLen = dataLen;
	}

	@Column(name = "DATA_PREC", precision = 22)
	public Long getDataPrec() {
		return this.dataPrec;
	}

	public void setDataPrec(Long dataPrec) {
		this.dataPrec = dataPrec;
	}

	@Column(name = "NULLS", length = 1)
	public String getNulls() {
		return this.nulls;
	}

	public void setNulls(String nulls) {
		this.nulls = nulls;
	}

	@Column(name = "DATA_FMT")
	public String getDataFmt() {
		return this.dataFmt;
	}

	public void setDataFmt(String dataFmt) {
		this.dataFmt = dataFmt;
	}

	@Column(name = "IS_CODE", length = 1)
	public String getIsCode() {
		return this.isCode;
	}

	public void setIsCode(String isCode) {
		this.isCode = isCode;
	}

	@Column(name = "CATE_ID", length = 10)
	public String getCateId() {
		return this.cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	@Column(name = "KEY_TYPE", length = 1)
	public String getKeyType() {
		return this.keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	@Column(name = "DECODE", length = 1)
	public String getDecode() {
		return this.decode;
	}

	public void setDecode(String decode) {
		this.decode = decode;
	}

	@Column(name = "STATE", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "CREATE_TM", length = 11)
	public Timestamp getCreateTm() {
		return this.createTm;
	}

	public void setCreateTm(Timestamp createTm) {
		this.createTm = createTm;
	}

	@Column(name = "CREATE_USER", length = 20)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "UPDATE_TM", length = 11)
	public Timestamp getUpdateTm() {
		return this.updateTm;
	}

	public void setUpdateTm(Timestamp updateTm) {
		this.updateTm = updateTm;
	}

	@Column(name = "UPDATE_USER", length = 20)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String toString(){
		return "id:" + id+ "; "+
				"colName:" + colName+ "; "+
				"colChName:" + colChName+ "; "+
				"colDesc:" + colDesc+ "; "+
				"colSeq:" + colSeq+ "; "+
				"dataType:" + dataType+ "; "+
				"dataLen:" + dataLen+ "; "+
				"dataPrec:" + dataPrec+ "; "+
				"nulls:" + nulls+ "; "+
				"dataFmt:" + dataFmt+ "; "+
				"isCode:" + isCode+ "; "+
				"cateId:" + cateId+ "; "+
				"keyType:" + keyType+ "; "+
				"decode:" + decode+ "; "+
				"state:" + state+ "; "+
				"createTm:" + createTm+ "; "+
				"createUser:" + createUser+ "; "+
				"updateTm:" + updateTm+ "; "+
				"updateUser:" + updateUser;
	}
}