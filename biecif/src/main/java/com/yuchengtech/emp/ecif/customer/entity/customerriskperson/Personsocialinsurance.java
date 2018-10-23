package com.yuchengtech.emp.ecif.customer.entity.customerriskperson;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the PERSONSOCIALINSURANCE database table.
 * 
 */
@Entity
@Table(name="PERSONSOCIALINSURANCE")
public class Personsocialinsurance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PERSON_SI_ID", unique=true, nullable=false)
	private Long personSiId;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="SIACCOUNT",length=40)
	private String siaccount;

	@Column(name="SIBALANCE",precision=17, scale=2)
	private BigDecimal sibalance;

	@Column(name="SITYPE",length=20)
	private String sitype;

	@Column(name="UPTODATE",length=20)
	private String uptodate;

	@Column(name="SERIAL_NO",length=40)
	private String serialNo;

    public Personsocialinsurance() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getPersonSiId() {
		return this.personSiId;
	}

	public void setPersonSiId(Long personSiId) {
		this.personSiId = personSiId;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getSiaccount() {
		return this.siaccount;
	}

	public void setSiaccount(String siaccount) {
		this.siaccount = siaccount;
	}

	public BigDecimal getSibalance() {
		return this.sibalance;
	}

	public void setSibalance(BigDecimal sibalance) {
		this.sibalance = sibalance;
	}

	public String getSitype() {
		return this.sitype;
	}

	public void setSitype(String sitype) {
		this.sitype = sitype;
	}

	public String getUptodate() {
		return this.uptodate;
	}

	public void setUptodate(String uptodate) {
		this.uptodate = uptodate;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

}