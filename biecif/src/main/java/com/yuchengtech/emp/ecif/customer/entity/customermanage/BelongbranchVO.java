package com.yuchengtech.emp.ecif.customer.entity.customermanage;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;


/**
 * The persistent class for the BELONGBRANCH database table.
 * 
 */
public class BelongbranchVO {
	private Long belongBranchId;

	private String belongType;

	private String brccode1;

	private String brcname1;
	
	private String brccode2;

	private String brcname2;
	
	private String mainType;

	private Long custId;

	private String custSource;

    public BelongbranchVO() {
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

	public String getBrcname1() {
		return brcname1;
	}

	public void setBrcname1(String brcname1) {
		this.brcname1 = brcname1;
	}

	public String getBrcname2() {
		return brcname2;
	}

	public void setBrcname2(String brcname2) {
		this.brcname2 = brcname2;
	}

}