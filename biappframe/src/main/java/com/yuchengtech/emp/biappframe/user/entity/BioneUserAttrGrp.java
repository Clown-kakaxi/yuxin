package com.yuchengtech.emp.biappframe.user.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the BIONE_USER_ATTR_GRP database table.
 * 
 */
@Entity
@Table(name="BIONE_USER_ATTR_GRP")
public class BioneUserAttrGrp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GRP_ID", unique=true, nullable=false, length=32)
	private String grpId;

	@Column(name="GRP_ICON", length=100)
	private String grpIcon;

	@Column(name="GRP_NAME", length=100)
	private String grpName;
	
	@Column(name="ORDER_NO" , precision=5)
	private BigDecimal orderNo;

    public BioneUserAttrGrp() {
    }

	public String getGrpId() {
		return this.grpId;
	}

	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	public String getGrpIcon() {
		return this.grpIcon;
	}

	public void setGrpIcon(String grpIcon) {
		this.grpIcon = grpIcon;
	}

	public String getGrpName() {
		return this.grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public BigDecimal getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
	}

}