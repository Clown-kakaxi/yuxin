package com.yuchengtech.emp.ecif.customer.entity.customerfinanceperson;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the FARMERFS database table.
 * 
 */
@Entity
@Table(name="FARMERFS")
public class Farmerf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FarmerfPK id;

	@Column(length=40)
	private String attribute1;

	@Column(length=40)
	private String customerid;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM")
	private Timestamp lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(precision=24, scale=2)
	private BigDecimal num1;

	@Column(precision=24, scale=2)
	private BigDecimal num2;

	@Column(precision=24, scale=2)
	private BigDecimal num3;

	@Column(precision=24, scale=2)
	private BigDecimal num4;

	@Column(precision=24, scale=2)
	private BigDecimal num5;

	@Column(precision=24, scale=2)
	private BigDecimal num6;

	@Column(length=10)
	private String recordmonth;

	@Column(name="SUM", precision=24, scale=6)
	private BigDecimal sum;

	@Column(precision=24, scale=6)
	private BigDecimal sum1;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

	@Column(length=40)
	private String type1;

	@Column(length=40)
	private String type2;

	@Column(length=40)
	private String type3;

	@Column(length=40)
	private String type4;

	@Column(length=40)
	private String type5;

	@Column(length=40)
	private String type6;

	@Column(length=40)
	private String type7;

    public Farmerf() {
    }

	public FarmerfPK getId() {
		return this.id;
	}

	public void setId(FarmerfPK id) {
		this.id = id;
	}
	
	public String getAttribute1() {
		return this.attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public Timestamp getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(Timestamp lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public BigDecimal getNum1() {
		return this.num1;
	}

	public void setNum1(BigDecimal num1) {
		this.num1 = num1;
	}

	public BigDecimal getNum2() {
		return this.num2;
	}

	public void setNum2(BigDecimal num2) {
		this.num2 = num2;
	}

	public BigDecimal getNum3() {
		return this.num3;
	}

	public void setNum3(BigDecimal num3) {
		this.num3 = num3;
	}

	public BigDecimal getNum4() {
		return this.num4;
	}

	public void setNum4(BigDecimal num4) {
		this.num4 = num4;
	}

	public BigDecimal getNum5() {
		return this.num5;
	}

	public void setNum5(BigDecimal num5) {
		this.num5 = num5;
	}

	public BigDecimal getNum6() {
		return this.num6;
	}

	public void setNum6(BigDecimal num6) {
		this.num6 = num6;
	}

	public String getRecordmonth() {
		return this.recordmonth;
	}

	public void setRecordmonth(String recordmonth) {
		this.recordmonth = recordmonth;
	}

	public BigDecimal getSum() {
		return this.sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public BigDecimal getSum1() {
		return this.sum1;
	}

	public void setSum1(BigDecimal sum1) {
		this.sum1 = sum1;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

	public String getType1() {
		return this.type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return this.type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public String getType3() {
		return this.type3;
	}

	public void setType3(String type3) {
		this.type3 = type3;
	}

	public String getType4() {
		return this.type4;
	}

	public void setType4(String type4) {
		this.type4 = type4;
	}

	public String getType5() {
		return this.type5;
	}

	public void setType5(String type5) {
		this.type5 = type5;
	}

	public String getType6() {
		return this.type6;
	}

	public void setType6(String type6) {
		this.type6 = type6;
	}

	public String getType7() {
		return this.type7;
	}

	public void setType7(String type7) {
		this.type7 = type7;
	}

}