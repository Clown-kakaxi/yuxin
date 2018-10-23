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
 * The persistent class for the SELLER database table.
 * 
 */
@Entity
@Table(name="M_CI_ORG_SELLER")
public class Seller implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SELLER_ID", unique=true, nullable=false)
	private Long sellerId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="DEALPROJNAME",length=60)
	private String dealprojname;

	@Column(name="LOCATION",length=200)
	private String location;

	@Column(name="MAINSELLERID")
	private Long mainsellerid;

	@Column(name="MAINSELLERNAME",length=60)
	private String mainsellername;

	@Column(name="NOTE",length=200)
	private String note;

	@Column(name="PAYDATE",length=200)
	private String paydate;

	@Column(name="RECEAMT",precision=17, scale=2)
	private BigDecimal receamt;

	@Column(name="TEL",length=20)
	private String tel;

    public Seller() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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

	public Long getMainsellerid() {
		return this.mainsellerid;
	}

	public void setMainsellerid(Long mainsellerid) {
		this.mainsellerid = mainsellerid;
	}

	public String getMainsellername() {
		return this.mainsellername;
	}

	public void setMainsellername(String mainsellername) {
		this.mainsellername = mainsellername;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPaydate() {
		return this.paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public BigDecimal getReceamt() {
		return this.receamt;
	}

	public void setReceamt(BigDecimal receamt) {
		this.receamt = receamt;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}