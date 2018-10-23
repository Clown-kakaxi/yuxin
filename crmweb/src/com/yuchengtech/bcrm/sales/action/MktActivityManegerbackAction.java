package com.yuchengtech.bcrm.sales.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
@ParentPackage("json-default")
@Action(value="/mktActivityManegerback", results={
    @Result(name="success", type="json"),
})
public class MktActivityManegerbackAction extends CommonAction {

	 @Autowired
		@Qualifier("dsOracle")
		private DataSource ds;
	 
	 
	 public void prepare() {
		 AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//	    	String sqlapp = "  select t.EXECUTOR_NAME,t.EXECUTOR_ID,a.MKT_ACTI_ID, a.MKT_ACTI_NAME,decode(t.deliver, null, 0, t.deliver) as deliver," +
//	    			"decode(t.sucess, null, 0, t.sucess) as sucess,Round(decode(t.sucess, null, 0, t.sucess) / t.deliver * 100, 4) as per," +
//	    			"a.ASTART_DATE,a1.user_name,a.AEND_DATE,a.CREATE_USER,a.CREATE_DATE  from OCRM_F_MK_MKT_ACTIVITY a," +
//	    			"(select sum(case PROGRESS_STAGE  when '2' then  '1' else '0' end) as sucess, count(1) as deliver, mkt_acti_id,EXECUTOR_NAME" +
//	    			",EXECUTOR_ID from OCRM_F_MK_MKT_MY_ACTI  group by mkt_acti_id ,EXECUTOR_NAME,EXECUTOR_ID) t, ADMIN_AUTH_ACCOUNT a1 " +
//	    			"where a.mkt_acti_id = t.mkt_acti_id and a.create_user=a1.account_name ";
		 String sqlapp =" select s.org_name,t.EXECUTOR_NAME,t.cust_name,a.MKT_ACTI_NAME,a.MKT_ACTI_STAT,t.PROGRESS_STAGE,t.IS_CRE_CHANCE" +
		 		",(select sysdate from dual) as NOW_DATE FROM OCRM_F_MK_MKT_MY_ACTI T LEFT JOIN OCRM_F_MK_MKT_ACTIVITY a on a.MKT_ACTI_ID=t.MKT_ACTI_ID  left join sys_units s " +
		 		" on s.unitid=a.create_org where 1>0 ";
	    	StringBuilder sb  = new StringBuilder(sqlapp);
	    	
	    	for(String key:this.getJson().keySet()){
	    	     if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
	    	         if(key.equals("EXECUTOR_NAME")){
	    	        	 String mgr=this.getJson().get(key).toString();
		                 	String mgrName []=mgr.split(",");
		                 	StringBuilder mgrsb = new StringBuilder();
		 	                for(int i=0;i<mgrName.length;i++){
		 	                	if(i==0)
		 	                		mgrsb.append("'"+mgrName[i]+"'");
		 	                	else
		 	                		mgrsb.append(",'"+mgrName[i]+"'");
		 	                	
		 	                }
		    	             sb.append(" and t.EXECUTOR_NAME in ("+mgrsb.toString()+")");
	    	         }
	    	         if(key.equals("MKT_ACTI_NAME"))
	    	             sb.append(" and a.MKT_ACTI_NAME like  '%"+this.getJson().get(key)+"%'");
	    	         if(key.equals("PROGRESS_STAGE"))
	    	             sb.append(" and t.PROGRESS_STAGE ='" +this.getJson().get(key)+"'");
	    	         if(key.equals("IS_CRE_CHANCE"))
	    	             sb.append(" and t.IS_CRE_CHANCE ='" +this.getJson().get(key)+"'");
	    	         if(key.equals("ORG_ID"))
	    	        	 sb.append(" and a.create_org ='" +this.getJson().get(key)+"'");
	    	     }
	        }
	    	
	    	 sb.append(" and t.EXECUTOR_ID in (select aa.account_name from admin_auth_account aa where aa.org_id" +
	    	 		" in (select u.UNITID from sys_units u where u.UNITSEQ like '%"+auth.getUnitId()+"%')) ");
	    	
	    	setPrimaryKey("t.EXECUTOR_ID asc ");

	        	SQL=sb.toString();
	        	datasource = ds;
		}
}
