package com.yuchengtech.emp.ecif.customer.entity.customerriskperson;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONSOCIALSECURITY database table.
 * 
 */
@Entity
@Table(name="PERSONSOCIALSECURITY")
public class Personsocialsecurity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="HAVEALLOFLAG",length=1)
	private String havealloflag;

	@Column(name="HAVECOMMFLAG",length=1)
	private String havecommflag;

	@Column(name="HAVEENDOFLAG",length=1)
	private String haveendoflag;

	@Column(name="HAVEIDLEFLAG",length=1)
	private String haveidleflag;

	@Column(name="HAVEMEDIFLAG",length=1)
	private String havemediflag;

    public Personsocialsecurity() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getHavealloflag() {
		return this.havealloflag;
	}

	public void setHavealloflag(String havealloflag) {
		this.havealloflag = havealloflag;
	}

	public String getHavecommflag() {
		return this.havecommflag;
	}

	public void setHavecommflag(String havecommflag) {
		this.havecommflag = havecommflag;
	}

	public String getHaveendoflag() {
		return this.haveendoflag;
	}

	public void setHaveendoflag(String haveendoflag) {
		this.haveendoflag = haveendoflag;
	}

	public String getHaveidleflag() {
		return this.haveidleflag;
	}

	public void setHaveidleflag(String haveidleflag) {
		this.haveidleflag = haveidleflag;
	}

	public String getHavemediflag() {
		return this.havemediflag;
	}

	public void setHavemediflag(String havemediflag) {
		this.havemediflag = havemediflag;
	}

}