package com.yuchengtech.emp.ecif.customer.entity.customerfinanceperson;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;

import java.math.BigDecimal;
/**
 * The persistent class for the PERSONDEBT database table.
 * 
 */
@Entity
@Table(name="DEBT")
public class PersondebtAA implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="DEBT_BAL",precision=17, scale=2)
	private BigDecimal bal;
	
	@Column(name="DEBT_TYPE",length=80)
	private String debtType;

	@Column(name="DEBT_NAME",length=80)
	private String debtName;
	
	@Column(name="DEBT_DESC",length=200)
	private String debtDesc;
	
	@Column(name="SRC_SERIAL_NO",length=32)
	private String srcSerialNo;
	
    public String getDebtType() {
		return debtType;
	}

	public void setDebtType(String debtType) {
		this.debtType = debtType;
	}

	public String getDebtName() {
		return debtName;
	}

	public void setDebtName(String debtName) {
		this.debtName = debtName;
	}

	public String getDebtDesc() {
		return debtDesc;
	}

	public void setDebtDesc(String debtDesc) {
		this.debtDesc = debtDesc;
	}

	public String getSrcSerialNo() {
		return srcSerialNo;
	}

	public void setSrcSerialNo(String srcSerialNo) {
		this.srcSerialNo = srcSerialNo;
	}

	public PersondebtAA() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}


}