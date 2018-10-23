package com.yuchengtech.bcrm.sales.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
@ParentPackage("json-default")
@Action(value="/MktMyActiListAction", results={
    @Result(name="success", type="json"),
})
public class MktMyActiListAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	 	 
	public void prepare() {
		// TODO Auto-generated method stub
		
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String ActiId =request.getParameter("ActiId");
    	String exeId =request.getParameter("exeId");
    	StringBuilder sb = new StringBuilder();
    	if(ActiId == null&&exeId == null){//首页查询
    		sb.append("select o.*,a.user_name,DE.cust_type as CUST_TYP,DE.CUST_STAT,DE.linkman_name as cust_Contact_Name from OCRM_F_MK_MKT_MY_ACTI o,ADMIN_AUTH_ACCOUNT a,ACRM_F_CI_CUSTOMER DE where o.create_user=a.account_name " +
    			 		" AND DE.CUST_ID = O.CUST_ID and o.EXECUTOR_ID='"+auth.getUserId()+"' ");
    	}else if(!"".equals(ActiId)&&ActiId!=null&&!"".equals(exeId)&&exeId!=null){
    		sb.append("select o.*,a.user_name,DE.cust_type as CUST_TYP,DE.CUST_STAT,DE.linkman_name as cust_Contact_Name from OCRM_F_MK_MKT_MY_ACTI o,ADMIN_AUTH_ACCOUNT a,ACRM_F_CI_CUSTOMER DE where o.create_user=a.account_name " +
    			 		" AND DE.CUST_ID = O.CUST_ID  and o.MKT_ACTI_ID='"+ActiId+"'");
    	}else{
    		sb.append("select o.*,                              "+
    				"       AC.MKT_ACTI_NAME,                 "+
    				"       AC.MKT_ACTI_TYPE,                 "+
    				"       AC.MKT_ACTI_MODE,                 "+
    				"       AC.MKT_ACTI_STAT,                 "+
    				"       AC.MKT_ACTI_AIM,                  "+
    				"       AC.MKT_ACTI_CONT,                 "+
    				"       AC.PSTART_DATE,                   "+
    				"       AC.PEND_DATE,                     "+
    				"       AC.MKT_ACTI_COST,                 "+
    				"       AC.AEND_DATE,                     "+
    				"       AC.ACTI_REMARK,                   "+
    				"       AC.MKT_ACTI_ADDR,                 "+
    				"       AC.ASTART_DATE,                   "+
    				"       AC.ACTI_CUST_DESC,                "+
    				"       a.user_name,                      "+
    				"       DE.CUST_TYPE as CUST_CATEGORY,     "+
    				"       DE.CUST_STAT as CUST_TYP,         "+
    				"       DE.LINKMAN_NAME as cust_Contact_Name "+
    				"  from OCRM_F_MK_MKT_MY_ACTI  o,         "+
    				"       OCRM_F_MK_MKT_ACTIVITY AC,        "+
    				"       ADMIN_AUTH_ACCOUNT     a,         "+
    				"       ACRM_F_CI_CUSTOMER    DE         "+
    				" where o.create_user = a.account_name    "+
    				"   AND AC.MKT_ACTI_ID = O.MKT_ACTI_ID    "+
    				"   AND DE.CUST_ID = O.CUST_ID            ");
    		 sb.append(" and o.MKT_ACTI_ID='"+ActiId+"'");
    		 sb.append(" and o.EXECUTOR_ID='"+auth.getUserId()+"' ");
    	}
    	
		 for(String key:this.getJson().keySet()){
			     if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
			         if(key.equals("MY_ACTI_NAME"))
			             sb.append(" and o."+key+" like '%"+this.getJson().get(key)+"%'");
			         else if(key.equals("PROGRESS_STAGE"))
			             sb.append(" and o."+key+"= '"+this.getJson().get(key)+"'");
			         else if(key.equals("CREATE_DATE"))
			        	 sb.append(" and o."+key+"= to_date('" +this.getJson().get(key).toString().substring(0, 10)+"','yyyy-MM-dd')");
			         else if(key.equals("IS_CRE_CHANCE"))
			             sb.append(" and o."+key+"= '"+this.getJson().get(key)+"'");
			         else{
		                	sb.append(" and o."+key+" like '%"+this.getJson().get(key)+"%'");
		                }
			        	 
			         
			     }
			 }
			 setPrimaryKey("o.UPDATE_DATE,O.CREATE_DATE desc ");
			 addOracleLookup("PROGRESS_STAGE", "STAGE_LEAVL");
			 addOracleLookup("MKT_ACTI_TYPE", "ACTI_TYPE");
			 addOracleLookup("MKT_ACTI_STAT", "MACTI_STATUS");
			 addOracleLookup("IS_CRE_CHANCE", "IF_FLAG");
			 SQL=sb.toString();
			 datasource = ds;
	}
	 //执行营销活动
    public String activityExecute() throws SQLException
    {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String myActiId = request.getParameter("myActiId");
		String executorId = request.getParameter("executorId");
		String executorName = request.getParameter("executorName");

			Connection conn=null;
			Statement stat=null;
			ResultSet rs = null;
			 try {
				conn = ds.getConnection();
				 stat = conn.createStatement();
				 String kindSql = "UPDATE OCRM_F_MK_MKT_MY_ACTI T SET T.EXECUTOR_ID ='"+executorId+"' ,T.EXECUTOR_NAME='"+executorName+"',T.IS_ASSIGN='1' WHERE T.MY_ACTI_ID in('"+myActiId.replace(",", "','")+"') ";
				 stat.executeUpdate(kindSql);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcUtil.close(rs, stat, conn);
			}
		addActionMessage(" lookupMapping removed successfully");
		return "success";
    }
    
    //在手动生成商机后，同步的更新所选择的我的营销活动的是否生成商机属性为1：是。
    public String updateIsCreChance() throws SQLException
    {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String myMktActiId = request.getParameter("myMktActiId");

				
			Connection conn=null;
			Statement stat=null;
			 try {
				conn = ds.getConnection();
				 stat = conn.createStatement();
				 String kindSql = "UPDATE OCRM_F_MK_MKT_MY_ACTI T SET T.IS_CRE_CHANCE = '1' WHERE T.MY_ACTI_ID ='"+myMktActiId+"' ";
				 stat.executeUpdate(kindSql);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				JdbcUtil.close(null, stat, conn);
			}
		addActionMessage(" update record successfully");
		return "success";
    }
    
}
