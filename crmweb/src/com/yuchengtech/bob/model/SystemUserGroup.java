package com.yuchengtech.bob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 用户组定义表
 * @author Administrator
 *
 */
@Entity
@Table(name = "OCRM_F_SM_SYS_USERGROUP")
public class SystemUserGroup {

	
	//序列ID
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	//用户组ID
	@Column(name = "SYSTEM_GROUPID",length=100)
	private String groupid;
	
	//用户组名
	@Column(name = "SYSTEM_GROUPNAME",length=100)
	private String groupname;
	
	//是否启用
	@Column(name = "IS_ENABLED")
	private Boolean isEnabled;
	
	//用户组备注
	@Column(name = "SYSTEM_GROUPMEMO", length = 1000)
	private String groupmemo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

    public void setGroupmemo(String groupmemo) {
        this.groupmemo = groupmemo;
    }

    public String getGroupmemo() {
        return groupmemo;
    }
	
	//启用时间
//	private Date startDate;
//	
//	//结束时间
//	private Date endDate;
//	
//	//创建人ID
//	private String createid;
//	
	
	
	
}
