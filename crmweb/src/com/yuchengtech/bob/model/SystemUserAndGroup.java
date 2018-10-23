package com.yuchengtech.bob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户与用户组关联关系表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "OCRM_F_SM_SYS_USERANDGROUP")
public class SystemUserAndGroup {

	//序列ID
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	//用户ID
	@Column(name = "SYSTEM_USERID",length=100)
	private String userid;

	//组ID
	@Column(name = "SYSTEM_GROUPID",length=100)
	private String groupid;
	
//	//开始时间
//	private Date startDate;
//	
//	//结束时间
//	private Date endDate;
	
	//创建人
	@Column(name = "SYSTEM_CREATEID",length=100)
	private String createid;

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

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getCreateid() {
		return createid;
	}

	public void setCreateid(String createid) {
		this.createid = createid;
	}
	
	

}
