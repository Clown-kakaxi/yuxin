package com.yuchengtech.emp.ecif.customer.entity.customerfinanceperson;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FARMERFS database table.
 * 
 */
@Embeddable
public class FarmerfPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CUST_ID", unique=true, nullable=false)
	private Long custId;

	@Column(name="SERIAL_NO", unique=true, nullable=false, length=40)
	private String serialNo;

    public FarmerfPK() {
    }
	public Long getCustId() {
		return this.custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getSerialNo() {
		return this.serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FarmerfPK)) {
			return false;
		}
		FarmerfPK castOther = (FarmerfPK)other;
		return 
			(this.custId == castOther.custId)
			&& this.serialNo.equals(castOther.serialNo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.custId ^ (this.custId >>> 32)));
		hash = hash * prime + this.serialNo.hashCode();
		
		return hash;
    }
}