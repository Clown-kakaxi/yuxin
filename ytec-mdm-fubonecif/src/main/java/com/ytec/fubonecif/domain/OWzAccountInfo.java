package com.ytec.fubonecif.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the O_WZ_ACCOUNT_INFO database table.
 * 
 */
@Entity
@Table(name="O_WZ_ACCOUNT_INFO")
public class OWzAccountInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="O_WZ_ACCOUNT_INFO_SERIALNO_GENERATOR" )
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="O_WZ_ACCOUNT_INFO_SERIALNO_GENERATOR")
	@Column(unique=true, nullable=false, length=32)
	private String serialno;

	@Column(nullable=false, length=32)
	private String accountno;

	@Column(nullable=false, length=32)
	private String customerid;

    public OWzAccountInfo() {
    }

	public String getSerialno() {
		return this.serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getAccountno() {
		return this.accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

}