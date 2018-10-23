/**
 * 
 */
package com.yuchengtech.emp.biappframe.user.web.vo;

import java.io.Serializable;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author luoshifei  luosf@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:		 修改人：		  修改日期:		     修改内容: 
 * </pre>
 */
public class BioneUserInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userId;
	
	private String userNo;

	private String userName;
	
	private String orgNo;
	
	private String orgName;
	
	private String deptNo;
	
	private String deptName;
	
	private String email;

	private String userSts;
	
	private String mobile;

	private String address;
	
	private String birthday;

	private String sex;

	private String tel;

	private String lastPwdUpdateTime;
	
	private String lastUpdateTime;
	
	private String lastUpdateUser;
	
	private String isBuiltin;
	
	private String postcode;
	
	private String remark;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserSts() {
		return userSts;
	}

	public void setUserSts(String userSts) {
		this.userSts = userSts;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLastPwdUpdateTime() {
		return lastPwdUpdateTime;
	}

	public void setLastPwdUpdateTime(String lastPwdUpdateTime) {
		this.lastPwdUpdateTime = lastPwdUpdateTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getIsBuiltin() {
		return isBuiltin;
	}

	public void setIsBuiltin(String isBuiltin) {
		this.isBuiltin = isBuiltin;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	
}
