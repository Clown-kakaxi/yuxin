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
* @ClassName: PublicCustChartAction 
* @Description: 对公客户信息首页图表数据
* @author wangmk1 
* @date 2014-7-27 上午11:43:07 
*
 */
@ParentPackage("json-default")
@Action("/publicCustChart")
public class PublicCustChartAction extends CommonAction {
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
			sb.append("SELECT DEPOSIT_BALANCE,LOAN_BALANCE,LIABILITIES_OFF_TB,ETL_DATE FROM ACRM_A_CI_GATH_BUSINESS WHERE CUST_ID='"+custId+"'");
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
	public void searchDepositJournal(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT s1.CUR_AC_BL,s2.PROD_NAME FROM ACRM_F_CI_GK_SAVE s1 " +
					" left join OCRM_F_PD_PROD_INFO s2 on s1.DEPOSIT_TYPE=s2.PRODUCT_ID" +
					" WHERE s1.CUST_ID='"+custId+"'");
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
	public void searchLoanJournal(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT s1.CUR_AC_BL,s2.PROD_NAME FROM ACRM_F_CI_GK_LOAN s1 " +
					" left join OCRM_F_PD_PROD_INFO s2 on s1.LOAN_TYP=s2.PRODUCT_ID" +
					" WHERE s1.CUST_ID='"+custId+"'");
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
	public void searchDeposit(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("select d.cust_id,nvl(d.index1,0) index1,h.deposit_balance,d.etl_date-1 AS etl_date from ACRM_A_CI_BUSS_TRANS_DAY d " +
							"left join Acrm_a_Ci_Gath_Business_His h on d.cust_id=h.cust_id and add_months(d.etl_date,-12)=h.etl_date " +
								"where d.CUST_ID='" +custId+"' order by d.etl_date asc");
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
	public void searchNetProfit(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.INDEX11, 0)INDEX11,ETL_DATE-1 AS ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_MONTH t WHERE  months_between(sysdate,ETL_DATE ) between 0 and 12	and CUST_ID='" +custId+"'"+
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
	public void searchLoan(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("select d.cust_id,nvl(d.index4,0) index4,h.LIABILITIES_IN_TB,d.etl_date-1 AS etl_date from ACRM_A_CI_BUSS_TRANS_DAY d " +
							"left join Acrm_a_Ci_Gath_Business_His h on d.cust_id=h.cust_id and add_months(d.etl_date,-12)=h.etl_date " +
								"where d.CUST_ID='" +custId+"' order by d.etl_date asc");
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
	public void searchDepositTrend(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.INDEX1, 0)INDEX1,nvl(t.INDEX3, 0)INDEX3,ETL_DATE-1 AS ETL_DATE FROM ACRM_A_CI_BUSS_TRANS_MONTH t WHERE  months_between(sysdate,ETL_DATE ) between 0 and 12 and CUST_ID='" +custId+"'"+
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
	public void searchLoanTrend(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("SELECT nvl(t.INDEX4, 0)INDEX4,nvl(t.INDEX6, 0)INDEX6,ETL_DATE-1 AS ETL_DATE  FROM ACRM_A_CI_BUSS_TRANS_MONTH t WHERE  months_between(sysdate,ETL_DATE ) between 0 and 12 and CUST_ID='" +custId+"'"+
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
