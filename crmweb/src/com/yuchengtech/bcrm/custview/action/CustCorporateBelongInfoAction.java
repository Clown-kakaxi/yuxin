package com.yuchengtech.bcrm.custview.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
/**
 * 对公归属信息Action
 * @author YOYOGI
 * 2014-7-24
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custCorporateBelongInfo", results = { @Result(name = "success", type = "json")})
public class CustCorporateBelongInfoAction extends CommonAction {

	//数据源
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;
	
	/**
	 * 查找推荐人Action
	 * @return
	 * @throws SQLException
	 */
	public String findREC() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		String sql = "SELECT RECOMMENDER FROM ACRM_F_CI_CUSTOMER WHERE CUST_ID='"+ custId +"'";
		QueryHelper queryHelper = new QueryHelper(sql,ds.getConnection());
		if(this.json!=null)
    		this.json.clear();
    	else 
    		this.json = new HashMap<String,Object>(); 
    	this.json.put("json",queryHelper.getJSON());
        return "success";
	}
	
	/**
	 * 查找归属机构
	 * @return
	 * @throws SQLException
	 */
	public String findOM() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		String sql = "SELECT DISTINCT CUST_ID,INSTITUTION_NAME " +
				"FROM OCRM_F_CI_BELONG_ORG " +
				"WHERE CUST_ID = '"+ custId +"'";
		QueryHelper queryHelper = new QueryHelper(sql,ds.getConnection());
		if(this.json!=null)
    		this.json.clear();
    	else 
    		this.json = new HashMap<String,Object>(); 
    	this.json.put("json",queryHelper.getJSON());
        return "success";
	}

	/**
	 * 查找归属客户经理
	 * @return
	 * @throws SQLException
	 */
	public String findMRG() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		String sql = "SELECT DISTINCT CUST_ID, MGR_NAME,CASE WHEN MAIN_TYPE ='1' then '主办'  WHEN MAIN_TYPE ='2' THEN '协办' " +
				"END AS MAIN_TYPE  FROM OCRM_F_CI_BELONG_CUSTMGR " +
				"WHERE CUST_ID = '"+ custId +"'";
		QueryHelper queryHelper = new QueryHelper(sql,ds.getConnection());
		if(this.json!=null)
    		this.json.clear();
    	else 
    		this.json = new HashMap<String,Object>(); 
    	this.json.put("json",queryHelper.getJSON());
        return "success";
	}
	
	
	/**
	 * 查找归属客户经理团队
	 * @return
	 * @throws SQLException
	 */
	public String findMTeam() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		String sql = "SELECT DISTINCT TCM.TEAM_NAME, ORG.ORG_NAME, SU.USERNAME, TCM.JOIN_DATE " +
				"FROM OCRM_F_CI_BELONG_CUSTMGR BC " +
				"LEFT OUTER JOIN OCRM_F_CM_TEAM_CUST_MANAGER TCM " +
				"ON BC.MGR_ID = TCM.CUST_MANAGER_ID " +
				"LEFT OUTER JOIN ADMIN_AUTH_ORG ORG " +
				"ON TCM.BELONG_ORG = ORG.ORG_ID " +
				"LEFT OUTER JOIN SYS_USERS SU " +
				"ON SU.userid =  TCM.APPROVER " +
				" WHERE BC.CUST_ID = '"+ custId +"'  ORDER BY TCM.JOIN_DATE DESC";
		QueryHelper queryHelper = new QueryHelper(sql, ds.getConnection());
		if(this.json!=null){
			this.json.clear();
		}else{
			this.json = new HashMap<String, Object>();
		}
		this.json.put("json",queryHelper.getJSON());
		return "success";
	}
	
	/**
	 * 查找归属客户群
	 * @return
	 * @throws SQLException
	 */
	public String findCBase() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		String sql = "SELECT DISTINCT BB.CUST_BASE_NAME, " +
				"BB.CUST_BASE_CREATE_NAME, " +
				"ORG.ORG_NAME  , BB.CUST_BASE_CREATE_DATE " +
				"FROM  OCRM_F_CI_RELATE_CUST_BASE RCB " +
				"LEFT JOIN OCRM_F_CI_BASE BB " +
				"ON RCB.CUST_BASE_ID = BB.ID " +
				"LEFT  JOIN ADMIN_AUTH_ORG ORG " +
				"ON BB.CUST_BASE_CREATE_ORG = ORG.ORG_ID " +
				"WHERE RCB.CUST_ID = '"+ custId +"'   ORDER BY BB.CUST_BASE_CREATE_DATE DESC";
		QueryHelper queryHelper = new QueryHelper(sql, ds.getConnection());
		if(this.json!=null){
			this.json.clear();
		}else{
			this.json = new HashMap<String, Object>();
		}
		this.json.put("json", queryHelper.getJSON());
		return "success";
	}
	
	/**
	 * 查找客户其他归属信息
	 * @return
	 * @throws SQLException
	 */
	public String findBelong() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		String sql = "select AC.USER_NAME AS CUST_MANAGER_NAME ,AC2.USER_NAME AS BELONG_TEAM_HEAD_NAME ,BO.INSTITUTION_NAME AS ORG_NAME ,BL.BL_NO AS ORG_BIZ_CUST_TYPE, BL.BL_NAME AS BL_NAME,GI.GROUP_NAME AS GROUP_NAME  " +
				"from  ACRM_F_CI_ORG  CU "+  
			"LEFT JOIN OCRM_F_CI_BELONG_CUSTMGR BC ON BC.CUST_ID=CU.CUST_ID "+
			"LEFT JOIN ADMIN_AUTH_ACCOUNT AC ON AC.ACCOUNT_NAME=BC.MGR_ID "+
			"LEFT JOIN ADMIN_AUTH_ACCOUNT AC2 ON AC2.ACCOUNT_NAME=AC.BELONG_TEAM_HEAD "+
			"LEFT JOIN OCRM_F_CI_BELONG_ORG BO ON BO.CUST_ID=CU.CUST_ID "+
			"LEFT JOIN ACRM_F_CI_BUSI_LINE BL ON BL.BL_NO=CU.ORG_BIZ_CUST_TYPE "+
			"LEFT JOIN OCRM_F_CI_GROUP_MEMBER GM ON GM.CUST_ID=BC.CUST_ID "+
			"LEFT JOIN OCRM_F_CI_GROUP_INFO GI ON GI.GROUP_NO=GM.GROUP_NO "+
			"WHERE CU.CUST_ID='"+custId+"'";
		QueryHelper queryHelper = new QueryHelper(sql, ds.getConnection());
		if(this.json!=null){
			this.json.clear();
		}else{
			this.json = new HashMap<String, Object>();
		}
		this.json.put("json",queryHelper.getJSON());
		return "success";
	}
	

	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public Map<String, Object> getJson() {
		return json;
	}

	public void setJson(Map<String, Object> json) {
		this.json = json;
	}

}