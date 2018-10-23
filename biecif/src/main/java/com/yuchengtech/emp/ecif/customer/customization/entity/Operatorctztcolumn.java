package com.yuchengtech.emp.ecif.customer.customization.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the OPERATORCTZTCOLUMN database table.
 * 
 */
@Entity
@Table(name="APP_SCV_OPER_COL_DEF")
public class Operatorctztcolumn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "OPER_SERIAL_NO_GENERATOR")
	@GenericGenerator(name = "OPER_SERIAL_NO_GENERATOR", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_OCC_SERIAL_NO") })
	@Column(name="OPER_SERIAL_NO", unique=true, nullable=false)
	private Long operSerialNo;

	@Column(name="CST_TYPE", nullable=false, length=1)
	private String cstType;

	@Column(name="CUM_SERIAL_NO", nullable=false)
	private int cumSerialNo;

	@Column(name="OPR_NO", nullable=false, length=32)
	private String oprNo;

    public Operatorctztcolumn() {
    }

	public Long getOperSerialNo() {
		return this.operSerialNo;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public void setOperSerialNo(Long operSerialNo) {
		this.operSerialNo = operSerialNo;
	}

	public String getCstType() {
		return this.cstType;
	}

	public void setCstType(String cstType) {
		this.cstType = cstType;
	}

	public int getCumSerialNo() {
		return this.cumSerialNo;
	}

	public void setCumSerialNo(int cumSerialNo) {
		this.cumSerialNo = cumSerialNo;
	}

	public String getOprNo() {
		return this.oprNo;
	}

	public void setOprNo(String oprNo) {
		this.oprNo = oprNo;
	}

}