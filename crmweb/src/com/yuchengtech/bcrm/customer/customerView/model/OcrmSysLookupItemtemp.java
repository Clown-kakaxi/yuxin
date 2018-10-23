package com.yuchengtech.bcrm.customer.customerView.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the OCRM_SYS_LOOKUP_ITEMTEMP database table.
 * ��ʱ��
 */
@Entity
@Table(name="OCRM_SYS_LOOKUP_ITEMTEMP")
public class OcrmSysLookupItemtemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OCRM_SYS_LOOKUP_ITEMTEMP", sequenceName="ID_SEQUENCE" ,allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OCRM_SYS_LOOKUP_ITEMTEMP")
	@Column(name = "F_ID")
	private Long id;
	
	
	@Column(name="F_CODE")
	private String code;

	@Column(name="F_COMMENT")
	private String comment;

	

	@Column(name="F_LOOKUP_ID")
	private String lookup;

	@Column(name="F_VALUE")
	private String value;

	public OcrmSysLookupItemtemp() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLookup() {
		return this.lookup;
	}

	public void setLookup(String lookup) {
		this.lookup = lookup;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}