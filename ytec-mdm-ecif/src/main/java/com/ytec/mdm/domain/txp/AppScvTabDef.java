package com.ytec.mdm.domain.txp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppScvTabDef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_SCV_TAB_DEF")
public class AppScvTabDef implements java.io.Serializable {

	// Fields

	private Long tbSerialNo;
	private String tbName;
	private String tbNmAcronym;
	private String tbCnnm;
	private String tbType;
	private String isMiddleTable;
	private String middleTableNm;
	private String relationColumn;
	private String isManytoone;
	private Long tbShowSeq;
	private String tbDescribe;

	// Constructors

	/** default constructor */
	public AppScvTabDef() {
	}

	/** minimal constructor */
	public AppScvTabDef(Long tbSerialNo) {
		this.tbSerialNo = tbSerialNo;
	}

	/** full constructor */
	public AppScvTabDef(Long tbSerialNo, String tbName,
			String tbNmAcronym, String tbCnnm, String tbType,
			String isMiddleTable, String middleTableNm, String relationColumn,
			String isManytoone, Long tbShowSeq, String tbDescribe) {
		this.tbSerialNo = tbSerialNo;
		this.tbName = tbName;
		this.tbNmAcronym = tbNmAcronym;
		this.tbCnnm = tbCnnm;
		this.tbType = tbType;
		this.isMiddleTable = isMiddleTable;
		this.middleTableNm = middleTableNm;
		this.relationColumn = relationColumn;
		this.isManytoone = isManytoone;
		this.tbShowSeq = tbShowSeq;
		this.tbDescribe = tbDescribe;
	}

	// Property accessors
	@Id
	@Column(name = "TB_SERIAL_NO", unique = true, nullable = false, precision = 22)
	public Long getTbSerialNo() {
		return this.tbSerialNo;
	}

	public void setTbSerialNo(Long tbSerialNo) {
		this.tbSerialNo = tbSerialNo;
	}

	@Column(name = "TB_NAME", length = 128)
	public String getTbName() {
		return this.tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	@Column(name = "TB_NM_ACRONYM", length = 50)
	public String getTbNmAcronym() {
		return this.tbNmAcronym;
	}

	public void setTbNmAcronym(String tbNmAcronym) {
		this.tbNmAcronym = tbNmAcronym;
	}

	@Column(name = "TB_CNNM", length = 50)
	public String getTbCnnm() {
		return this.tbCnnm;
	}

	public void setTbCnnm(String tbCnnm) {
		this.tbCnnm = tbCnnm;
	}

	@Column(name = "TB_TYPE", length = 1)
	public String getTbType() {
		return this.tbType;
	}

	public void setTbType(String tbType) {
		this.tbType = tbType;
	}

	@Column(name = "IS_MIDDLE_TABLE", length = 1)
	public String getIsMiddleTable() {
		return this.isMiddleTable;
	}

	public void setIsMiddleTable(String isMiddleTable) {
		this.isMiddleTable = isMiddleTable;
	}

	@Column(name = "MIDDLE_TABLE_NM", length = 128)
	public String getMiddleTableNm() {
		return this.middleTableNm;
	}

	public void setMiddleTableNm(String middleTableNm) {
		this.middleTableNm = middleTableNm;
	}

	@Column(name = "RELATION_COLUMN", length = 128)
	public String getRelationColumn() {
		return this.relationColumn;
	}

	public void setRelationColumn(String relationColumn) {
		this.relationColumn = relationColumn;
	}

	@Column(name = "IS_MANYTOONE", length = 1)
	public String getIsManytoone() {
		return this.isManytoone;
	}

	public void setIsManytoone(String isManytoone) {
		this.isManytoone = isManytoone;
	}

	@Column(name = "TB_SHOW_SEQ", precision = 22)
	public Long getTbShowSeq() {
		return this.tbShowSeq;
	}

	public void setTbShowSeq(Long tbShowSeq) {
		this.tbShowSeq = tbShowSeq;
	}

	@Column(name = "TB_DESCRIBE")
	public String getTbDescribe() {
		return this.tbDescribe;
	}

	public void setTbDescribe(String tbDescribe) {
		this.tbDescribe = tbDescribe;
	}

}