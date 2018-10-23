package com.yuchengtech.emp.ecif.customer.entity.customermanage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the BELONGBRANCH database table.
 * 
 */
@Entity
@Table(name="BELONGBRANCH")
public class Belongbranch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BELONG_BRANCH_ID", unique=true, nullable=false)
	private Long belongBranchId;

	@Column(name="BELONG_TYPE", length=20)
	private String belongType;

	@Column(name="BRCCODE1",length=9)
	private String brccode1;

	@Column(name="BRCCODE2",length=9)
	private String brccode2;
	
	@Column(name="MAIN_TYPE",length=20)
	private String mainType;

	@Column(name="CUST_ID")
	private Long custId;

	@Column(name="CUST_SOURCE", length=30)
	private String custSource;

    public Belongbranch() {
    }

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getBelongBranchId() {
		return this.belongBranchId;
	}

	public void setBelongBranchId(Long belongBranchId) {
		this.belongBranchId = belongBranchId;
	}

	public String getBelongType() {
		return this.belongType;
	}

	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}

	public String getBrccode1() {
		return this.brccode1;
	}

	public void setBrccode1(String brccode1) {
		this.brccode1 = brccode1;
	}

	public String getBrccode2() {
		return this.brccode2;
	}

	public void setBrccode2(String brccode2) {
		this.brccode2 = brccode2;
	}
	
	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

    @JsonSerialize(using=BioneLongSerializer.class)
	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustSource() {
		return this.custSource;
	}

	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}

}