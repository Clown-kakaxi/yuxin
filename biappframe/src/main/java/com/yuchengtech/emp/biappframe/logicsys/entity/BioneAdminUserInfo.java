package com.yuchengtech.emp.biappframe.logicsys.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the BIONE_ADMIN_USER_INFO database table.
 * 
 */
@Entity
@Table(name="BIONE_ADMIN_USER_INFO")
public class BioneAdminUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BioneAdminUserInfoPK id;

	@Column(name="LAST_UPDATE_TIME")
	private Timestamp lastUpdateTime;

	@Column(name="LAST_UPDATE_USER", length=100)
	private String lastUpdateUser;

	@Column(length=500)
	private String remark;

	@Column(name="USER_STS", length=1)
	private String userSts;

    public BioneAdminUserInfo() {
    }

	public BioneAdminUserInfoPK getId() {
		return this.id;
	}

	public void setId(BioneAdminUserInfoPK id) {
		this.id = id;
	}
	
	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return this.lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserSts() {
		return this.userSts;
	}

	public void setUserSts(String userSts) {
		this.userSts = userSts;
	}

}