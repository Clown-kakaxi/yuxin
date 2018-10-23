package com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the SUPPLIER database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_SUPPLIER")
public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SUPPLIER_ID", unique=true, nullable=false)
	private Long supplierId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="DEALPROJNAME",length=60)
	private String dealprojname;

	@Column(name="LOCATION",length=200)
	private String location;
	
	@Column(name="MAINSUPPID")
	private Long mainsuppid;

	@Column(name="MAINSUPPNAME",length=60)
	private String mainsuppname;

	@Column(name="NOTE",length=200)
	private String note;

	@Column(name="PAYAMT",precision=17, scale=2)
	private BigDecimal payamt;

	@Column(name="PAYDATE",length=20)
	private String paydate;

	@Column(name="TEL",length=20)
	private String tel;

    public Supplier() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getDealprojname() {
		return this.dealprojname;
	}

	public void setDealprojname(String dealprojname) {
		this.dealprojname = dealprojname;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getMainsuppid() {
		return this.mainsuppid;
	}

	public void setMainsuppid(Long mainsuppid) {
		this.mainsuppid = mainsuppid;
	}

	public String getMainsuppname() {
		return this.mainsuppname;
	}

	public void setMainsuppname(String mainsuppname) {
		this.mainsuppname = mainsuppname;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getPayamt() {
		return this.payamt;
	}

	public void setPayamt(BigDecimal payamt) {
		this.payamt = payamt;
	}

	public String getPaydate() {
		return this.paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}