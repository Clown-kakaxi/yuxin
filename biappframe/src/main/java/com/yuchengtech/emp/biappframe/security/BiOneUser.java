package com.yuchengtech.emp.biappframe.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * <pre>
 * Title:平台自定义Authentication类
 * Description: 使shiro的Subject可以包含除用户名外更多的信息
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class BiOneUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5838655360268384081L;
	
	//超级管理员用户的用户名
	//private static final String BIONE_SUPER_USERNAME = GlobalConstants.SUPER_USER_NO;

	private String userId;// 用户ID
	private String loginName;//用户
	private String userName;// 用户名称
	private String orgNo;// 机构编号
	private String orgName;// 用户所属机构名称
	private String deptNo;// 部门编号
	private String deptName;// 用户所属部门名称
	private String currentLogicSysNo;//当前登录的逻辑系统标识
	private boolean isSuperUser;//是否为管理员用户，如果是管理员用户，登录所管理的逻辑系统时没有权限控制
	
	// 与用户关联的的授权对象 如机构、部门、角色、授权组等
	private Map<String, List<String>> authObjMap = new HashMap<String, List<String>>();
//	
//	public boolean isSuperUser(){
//		
//		return BIONE_SUPER_USERNAME.equals(this.loginName);
//		
//	}

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map<String, List<String>> getAuthObjMap() {
		return authObjMap;
	}

	public void setAuthObjMap(Map<String, List<String>> authObjMap) {
		this.authObjMap = authObjMap;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCurrentLogicSysNo() {
		return currentLogicSysNo;
	}

	public void setCurrentLogicSysNo(String currentLogicSysNo) {
		this.currentLogicSysNo = currentLogicSysNo;
	}

	public boolean isSuperUser() {
		return isSuperUser;
	}

	public void setSuperUser(boolean isSuperUser) {
		this.isSuperUser = isSuperUser;
	}

}
