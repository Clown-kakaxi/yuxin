package com.yuchengtech.bob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户与机构关联关系表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "OCRM_F_SM_SYS_UnitAndUsers")
public class SystemUnitAndUsers {

	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	
	//用户id
	@Column(name = "SYSTEM_USERID",length=100)
	private String userid;
	
	//机构ID
	@Column(name = "SYSTEM_UNITID",length=100)
	private String unitid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	
	
	
}
