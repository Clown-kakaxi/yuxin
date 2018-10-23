package com.yuchengtech.bcrm.sales.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@Action("/marketAssuQuery")
@Results({@Result(name = "success", type = "redirectAction", params = {
		"actionName", "marketAssuQuery" }) })
public class MarketAssuQueryAction extends CommonAction {

	 @Autowired
     @Qualifier("dsOracle")
     private DataSource ds;
	 private String strb;
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		

		//StringBuilder sb = new StringBuilder("select su.id ,su.user_id,su.manager_type,su.manager_level,su.crm_mail_position ,su.levl,su.telephone,su.is_having_card,su.user_name,su.jobfamilyname,su.educationlevel,su.attendondate,su.economicyear,su.gender,su.graduateschool,su.political,su.defaultproperty,su.gwnames,su.accessionstate,su.age,su.unitname,su.unitid from(select o.id as id,o.user_id as user_id,o.manager_type as manager_type,o.manager_level as manager_level,o.crm_mail_position as crm_mail_position,o.levl as levl,o.telephone as telephone,o.is_having_card as is_having_card,v.user_name as user_name,v.jobfamilyname as jobfamilyname,v.educationlevel as educationlevel,v.attendondate as attendondate,v.economicyear as economicyear,v.gender as gender,v.graduateschool as graduateschool,v.political as political,v.defaultproperty as defaultproperty,v.gwnames as gwnames,v.accessionstate as accessionstate,v.age as age,(select s1.unitname from sys_units s1 left join sys_users s2 on s1.id = s2.unitid where s2.userid = o.user_id) as unitname, (select s1.unitid from sys_units s1 left join sys_users s2 on s1.id =s2.unitid where s2.userid =  o.user_id) as unitid  from ocrm_f_cm_cust_manager_info o inner join v_acrm_userinfo v on o.user_id = v.user_id inner join sys_users s on s.userid = o.user_id)su where 1>0 ");
		 StringBuilder sb = new StringBuilder("select p.* from (select p.*,s.USERNAME as create_name,s1.USERNAME as disk_name,s2.USERNAME as oper_name from OCRM_F_MM_TASK p left join sys_users s on p.CREATE_USER = s.USERID left join sys_users s1 on s1.USERID = p.DIST_USER left join sys_users s2 on s2.USERID = p.OPER_USER where 1>0) p where 1>0");
	        for(String key:this.getJson().keySet()){
	            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
	            	
	                if(key.equals("TASK_NAME"))
	                	  sb.append(" and "+key+" like '%"+this.getJson().get(key)+"%'");
	                else if(key.equals("TASK_STAT"))
	                	 sb.append(" and "+key+" = '"+this.getJson().get(key)+"'");
	                else if(key.equals("OPER_USER")){
	                	 strb =  (String) this.getJson().get(key);
	                	 String tempStr = strb.replace(",", "','");
	                	 sb.append(" and p.OPER_USER in('"+tempStr+"')");
	                }
	                else if(key.equals("DIST_USER")){
	                	 strb =  (String) this.getJson().get(key);
	                	 String tempStr = strb.replace(",", "','");
	                	 sb.append(" and p.DIST_USER in('"+tempStr+"')");
	                }
	                else if(key.equals("OPER_ORG")){
	                	 strb =  (String) this.getJson().get(key);
	                	 String tempStr = strb.replace(",", "','");
	                	 sb.append(" and p.OPER_NAME in('"+tempStr+"')");
	                }
	                else if(key.equals("createDate"))
	                	  sb.append(" and "+key+" = "+this.getJson().get(key));
//	                else{
//	                	sb.append(" and "+key+" = "+this.getJson().get(key));
//	                }
	            }
	        }
		SQL = sb.toString();
		setPrimaryKey("p.TASK_ID desc");
		addOracleLookup("TASK_STAT", "MTASK_STAT");
		datasource = ds;
		
		
	}
}
