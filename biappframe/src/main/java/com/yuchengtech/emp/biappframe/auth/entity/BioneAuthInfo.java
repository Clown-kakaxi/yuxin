package com.yuchengtech.emp.biappframe.auth.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the BIONE_AUTH_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_AUTH_INFO")
public class BioneAuthInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="AUTH_TYPE_ID", unique=true, nullable=false, length=32)
	private String authTypeId;

	@Column(name="AUTH_TYPE_NAME", length=100)
	private String authTypeName;

	@Column(name="AUTH_TYPE_NO", nullable=false, length=32)
	private String authTypeNo;

    public BioneAuthInfo() {
    }

	public String getAuthTypeId() {
		return this.authTypeId;
	}

	public void setAuthTypeId(String authTypeId) {
		this.authTypeId = authTypeId;
	}

	public String getAuthTypeName() {
		return this.authTypeName;
	}

	public void setAuthTypeName(String authTypeName) {
		this.authTypeName = authTypeName;
	}

	public String getAuthTypeNo() {
		return this.authTypeNo;
	}

	public void setAuthTypeNo(String authTypeNo) {
		this.authTypeNo = authTypeNo;
	}

}