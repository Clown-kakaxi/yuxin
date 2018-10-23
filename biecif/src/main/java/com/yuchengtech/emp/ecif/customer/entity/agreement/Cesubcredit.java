package com.yuchengtech.emp.ecif.customer.entity.agreement;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the CESUBCREDIT database table.
 * 
 */
@Entity
@Table(name="CESUBCREDIT")
public class Cesubcredit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONTR_ID", unique=true, nullable=false)
	private String contrId;

	@Column(name="APPSTATE",length=1)
	private String appstate;

	@Column(name="CREDITID",length=20)
	private String creditid;

	@Column(name="CURRENCY",length=20)
	private String currency;

	@Column(name="CUSTID",length=20)
	private String custid;

	@Column(name="CUSTNAME",length=100)
	private String custname;

	@Column(name="FINALSUBCREDITLIMIT",precision=17, scale=2)
	private BigDecimal finalsubcreditlimit;

	@Column(name="ID",length=20)
	private String id;

	@Column(name="LAST_UPDATE_SYS", length=20)
	private String lastUpdateSys;

	@Column(name="LAST_UPDATE_TM", length=20)
	private String lastUpdateTm;

	@Column(name="LAST_UPDATE_USER", length=20)
	private String lastUpdateUser;

	@Column(name="PRODUCTCREDITTYPE",length=20)
	private String productcredittype;

	@Column(name="SUBCREDITLIMIT",precision=17, scale=2)
	private BigDecimal subcreditlimit;

	@Column(name="TX_SEQ_NO", length=32)
	private String txSeqNo;

    public Cesubcredit() {
    }

	public String getContrId() {
		return this.contrId;
	}

	public void setContrId(String contrId) {
		this.contrId = contrId;
	}

	public String getAppstate() {
		return this.appstate;
	}

	public void setAppstate(String appstate) {
		this.appstate = appstate;
	}

	public String getCreditid() {
		return this.creditid;
	}

	public void setCreditid(String creditid) {
		this.creditid = creditid;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustid() {
		return this.custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return this.custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public BigDecimal getFinalsubcreditlimit() {
		return this.finalsubcreditlimit;
	}

	public void setFinalsubcreditlimit(BigDecimal finalsubcreditlimit) {
		this.finalsubcreditlimit = finalsubcreditlimit;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastUpdateSys() {
		return this.lastUpdateSys;
	}

	public void setLastUpdateSys(String lastUpdateSys) {
		this.lastUpdateSys = lastUpdateSys;
	}

	public String getLastUpdateTm() {
		return this.lastUpdateTm;
	}

	public void setLastUpdateTm(String lastUpdateTm) {
		this.lastUpdateTm = lastUpdateTm;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getProductcredittype() {
		return this.productcredittype;
	}

	public void setProductcredittype(String productcredittype) {
		this.productcredittype = productcredittype;
	}

	public BigDecimal getSubcreditlimit() {
		return this.subcreditlimit;
	}

	public void setSubcreditlimit(BigDecimal subcreditlimit) {
		this.subcreditlimit = subcreditlimit;
	}

	public String getTxSeqNo() {
		return this.txSeqNo;
	}

	public void setTxSeqNo(String txSeqNo) {
		this.txSeqNo = txSeqNo;
	}

}