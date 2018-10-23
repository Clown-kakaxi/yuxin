package com.yuchengtech.emp.biappframe.passwd.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="BIONE_PWD_HIS_INFO")
public class BionePwdHisInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PWD_HIS_ID" , unique=true, nullable=false, length=32)
	private String pwdHisId ;
	
	@Column(name="USER_ID" , length=32)
	private String userId ;
	
	@Column(name="USER_PWD" , length=100)
	private String userPwd ;
	
	@Column(name="BACKUP_DESC" , length=200)
	private String backupDesc ;
	
	@Column(name="CREATE_TIME")
	private Timestamp createTime ;
	
	@Column(name="REMARK" , length=500)
	private String remark ;

	
	public String getPwdHisId() {
		return pwdHisId;
	}

	public void setPwdHisId(String pwdHisId) {
		this.pwdHisId = pwdHisId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getBackupDesc() {
		return backupDesc;
	}

	public void setBackupDesc(String backupDesc) {
		this.backupDesc = backupDesc;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
