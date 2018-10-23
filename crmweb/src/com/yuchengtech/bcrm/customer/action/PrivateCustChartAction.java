package com.yuchengtech.bcrm.customer.action;


import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
/**
 * 
* @ClassName: CustAssetsChartAction 
* @Description:个人/零售客户首页图表
* @author wangmk1 
* @date 2014-7-24 上午11:40:16 
*
 */
@ParentPackage("json-default")
@Action("/privateCustChart")
public class PrivateCustChartAction extends CommonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	public void searchAssetsInfo()  {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT T.CUST_ID,T.ETL_DATE,T.DEPOSIT_BALANCE-T.FINANPRD_BALANCE-NVL(T.ASSURANCE_BALANCE,0) AS DEPOSIT_BALANCE,T.FINANPRD_BALANCE,NVL(T.ASSURANCE_BALANCE,0)   FROM ACRM_A_CI_ASSET_AUM T WHERE CUST_ID='"+custId+"'");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
//			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
			//对应的root为json.data
//			this.json.put("json", new QueryHelper(sb.toString(), ds.getConnection()).getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void searchMonthAum(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.INDEX7, 0) INDEX7,ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_DAY t WHERE   CUST_ID='" +custId+"'"+
					" order by ETL_DATE asc");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
//			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void searchYearAum(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.INDEX7, 0) INDEX7,ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_MONTH t WHERE  months_between(sysdate , ETL_DATE) between 0 and 12  and CUST_ID='" +custId+"'"+
					" order by ETL_DATE asc");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
//			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void searchMonthLoan(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.INDEX4, 0) INDEX4,ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_DAY t WHERE   CUST_ID='" +custId+"'"+
					" order by ETL_DATE asc");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
//			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void searchYearLoan(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.INDEX4, 0) INDEX4,ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_MONTH t WHERE  months_between(sysdate , ETL_DATE) between 0 and 12  and CUST_ID='" +custId+"'"+
					" order by ETL_DATE asc");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
//			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void searchMonthContr(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.INDEX8, 0) INDEX8,ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_DAY t WHERE  CUST_ID='" +custId+"'"+
					" order by ETL_DATE asc");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
//			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void searchYearContr(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.INDEX8, 0) INDEX8,ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_MONTH t WHERE  months_between(sysdate , ETL_DATE) between 0 and 12  and CUST_ID='" +custId+"'"+
					" order by ETL_DATE asc");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
//			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void searchMonthFina(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.INDEX9, 0) INDEX9,ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_DAY t WHERE   CUST_ID='" +custId+"'"+
					" order by ETL_DATE asc");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
//			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void searchYearFina(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.INDEX9, 0) INDEX9,ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_MONTH t WHERE  months_between(sysdate , ETL_DATE) between 0 and 12  and CUST_ID='" +custId+"'"+
					" order by ETL_DATE asc");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
//			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void searchMonthPoints(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.DAYPOINTS, 0) DAYPOINTS,ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_DAY t WHERE  floor(sysdate - ETL_DATE) between 0 and 29  and CUST_ID='" +custId+"'"+
					" order by ETL_DATE asc");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
//			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void searchYearPoints(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.MONTHPOINTS, 0) MONTHPOINTS,ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_MONTH t WHERE  months_between(sysdate , ETL_DATE) between 0 and 12  and CUST_ID='" +custId+"'"+
					" order by ETL_DATE asc");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
//			//对应的root为data
			this.json=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
