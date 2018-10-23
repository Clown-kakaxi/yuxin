package com.yuchengtech.bob.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OCRM_F_SM_SYS_USERS")
public class SystemUsers implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	//用户ID
	@Column(name = "SYSTEM_USERID",length=100)
	private String userid;

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


	//用户名称
	@Column(name = "SYSTEM_USERNAME",length=100)
	private String username;
	
	//用户密码
	@Column(name = "SYSTEM_PASSWORD",length=100)
	private String password;
	
	//用户类型
	@Column(name = "SYSTEM_USERTYPE",length=20)
	private String usertype;
	
	//用户性别
	@Column(name = "SYSTEM_SEX",length=2)
	private String sex;
	
	//邮件
	@Column(name = "SYSTEM_EMAIL",length=100)
	private String email;
	
	//电话
	@Column(name = "SYSTEM_TELPHONE",length=20)
	private String telphone;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
//	//创建时间
//	@Temporal(TemporalType.DATE)
//	@Column(name = "SYSTEM_CREATEDATE")
//	private Date createDate;
//	
//	//修改人ID
//	@Column(name = "SYSTEM_CHANGEUSERID",length=100)
//	private String changeuserid;
//	
//	//修改时间
//	@Temporal(TemporalType.DATE)
//	@Column(name = "SYSTEM_LASTCHANGEDATE")
//	private Date lastchangeDate;
//	
//	//是否启用标志
//	@Column(name = "SYSTEM_ISLOCKED")
//	private boolean isLocked;


	
	
	
	

}
