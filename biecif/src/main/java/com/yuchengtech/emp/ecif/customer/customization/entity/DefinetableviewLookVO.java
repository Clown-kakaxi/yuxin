package com.yuchengtech.emp.ecif.customer.customization.entity;

import java.io.Serializable;


/**
 * The persistent class for the DEFINETABLEVIEW database table.
 * 
 */
public class DefinetableviewLookVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int tbSerialNo;

	private String tbCnnm;
	
	private String tbNmAcronym;

	private String tbDescribe;

	private String tbName;

	private String tbType;
	
	private int tbShowSeq;

	private int cumSerialNo;

	private String cumCnnm;

	private String cumDescribe;

	private String cumName;

	//private String cumType;
	
	private String isManytoone;
	
	private String isMiddleTable;

	private String middleTableNm;

	private String relationColumn;
	
	private int cumShowSeq;

	private String isCondition;

	private String isMust;

	private String isResult;
	
	private String isCode;
	
	private String cateId;
	
	private String dataType;
	
	private String dataLen;

    public DefinetableviewLookVO() {
    }

	public int getTbSerialNo() {
		return tbSerialNo;
	}

	public void setTbSerialNo(int tbSerialNo) {
		this.tbSerialNo = tbSerialNo;
	}

	public String getTbCnnm() {
		return tbCnnm;
	}

	public void setTbCnnm(String tbCnnm) {
		this.tbCnnm = tbCnnm;
	}

	public String getTbNmAcronym() {
		return tbNmAcronym;
	}

	public void setTbNmAcronym(String tbNmAcronym) {
		this.tbNmAcronym = tbNmAcronym;
	}
	
	public String getTbDescribe() {
		return tbDescribe;
	}

	public void setTbDescribe(String tbDescribe) {
		this.tbDescribe = tbDescribe;
	}

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

	public String getTbType() {
		return tbType;
	}

	public void setTbType(String tbType) {
		this.tbType = tbType;
	}

	public int getTbShowSeq() {
		return tbShowSeq;
	}

	public void setTbShowSeq(int tbShowSeq) {
		this.tbShowSeq = tbShowSeq;
	}

	public int getCumSerialNo() {
		return cumSerialNo;
	}

	public void setCumSerialNo(int cumSerialNo) {
		this.cumSerialNo = cumSerialNo;
	}

	public String getCumCnnm() {
		return cumCnnm;
	}

	public void setCumCnnm(String cumCnnm) {
		this.cumCnnm = cumCnnm;
	}

	public String getCumDescribe() {
		return cumDescribe;
	}

	public void setCumDescribe(String cumDescribe) {
		this.cumDescribe = cumDescribe;
	}

	public String getCumName() {
		return cumName;
	}

	public void setCumName(String cumName) {
		this.cumName = cumName;
	}

//	public String getCumType() {
//		return cumType;
//	}
//
//	public void setCumType(String cumType) {
//		this.cumType = cumType;
//	}

	public String getIsManytoone() {
		return isManytoone;
	}

	public void setIsManytoone(String isManytoone) {
		this.isManytoone = isManytoone;
	}

	public String getIsMiddleTable() {
		return isMiddleTable;
	}

	public void setIsMiddleTable(String isMiddleTable) {
		this.isMiddleTable = isMiddleTable;
	}

	public String getMiddleTableNm() {
		return middleTableNm;
	}

	public void setMiddleTableNm(String middleTableNm) {
		this.middleTableNm = middleTableNm;
	}

	public String getRelationColumn() {
		return relationColumn;
	}

	public void setRelationColumn(String relationColumn) {
		this.relationColumn = relationColumn;
	}

	public int getCumShowSeq() {
		return cumShowSeq;
	}

	public void setCumShowSeq(int cumShowSeq) {
		this.cumShowSeq = cumShowSeq;
	}

	public String getIsCondition() {
		return isCondition;
	}

	public void setIsCondition(String isCondition) {
		this.isCondition = isCondition;
	}

	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}

	public String getIsResult() {
		return isResult;
	}

	public void setIsResult(String isResult) {
		this.isResult = isResult;
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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataLen() {
		return dataLen;
	}

	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}
}