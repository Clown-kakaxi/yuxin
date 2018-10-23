package com.yuchengtech.bcrm.customer.action;

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
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.service.CommonQueryService;
/***
 * 2014-07-17  修改客户查询方法 (客户选择放大镜使用)
 * @author luyueyue
 *
 */
@ParentPackage("json-default")
@Action(value="/customerBaseInformationlf", results={
    @Result(name="success", type="json"),
})
public class CustomerBaseInformationLfAction extends CommonAction{
	private static final long serialVersionUID = -1000779866159801187L;

	@Autowired
	private CommonQueryService cqs;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;  
	private HttpServletRequest request;
	
	private Map<String, Object> map = new HashMap<String, Object>();
 	public void prepare() {	
 		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String isNew = request.getParameter("isNew");
        StringBuilder sb = new StringBuilder();
        if("true".equals(isNew)){
        	 sb.append("select  * from (select c.POTENTIAL_FLAG,c.source_channel,c.cust_id,c.cust_name,c.ident_type,c.ident_no,c.cust_type,c.cust_level,c.job_type,c.indust_type," +
             		"c.linkman_name,c.linkman_tel,'' as if_targetbusi,m.INSTITUTION,c.cust_stat,m.institution_name as org_name,m.mgr_name,m.mgr_id,m.INSTITUTION as org_id,d.addr," +
             		" CASE WHEN CR.CUST_ID IS NULL THEN '0' ELSE '1' END AS IS_SX_CUST " +
             		" from ACRM_F_CI_CUSTOMER c " +
             		" LEFT JOIN (SELECT CUST_ID FROM ACRM_F_CI_CROSSINDEX WHERE SRC_SYS_NO = 'LN') CR ON CR.CUST_ID = c.CUST_ID " +
             		" left join OCRM_F_CI_BELONG_CUSTMGR m on c.cust_id = m.cust_id  "+
             		" left join ACRM_F_CI_ADDRESS d on d.cust_id=c.cust_id and d.ADDR_TYPE='02' where 1 = 1 ");
             sb.append(" union ");
             sb.append(" select '1' as POTENTIAL_FLAG,'' as source_channel,p.cus_id as cust_id,p.cus_name as cust_name,'' as ident_type,'' as ident_no,p.cust_type," +
             		  " '' as cust_level,'' as job_type,p.indust_type as indust_type,p.atten_name as linkman_name,p.call_no as linkman_tel ,p.if_targetbusi as if_targetbusi,p.MAIN_BR_ID as INSTITUTION," +
             		  " '' as cust_stat, ao.org_name as org_name,ac.user_name as mgr_name,p.CUST_MGR as mgr_id,p.MAIN_BR_ID as org_id,'' as addr,'0' as IS_SX_CUST from ACRM_F_CI_POT_CUS_COM p " +
             		  " left join admin_auth_account ac on p.CUST_MGR=ac.account_name "+
             		  " left join admin_auth_org ao on ao.org_id=ac.org_id) m where 1=1 ");
             
             for(String key:this.getJson().keySet()){
        		 if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
        			 if(key.equals("CUST_NAME")||key.equals("IDENT_NO"))
        				 sb.append(" and m."+key+" like '%"+this.getJson().get(key)+"%'");
        			 else if(key.equals("CUST_ID")||key.equals("CUST_STAT")||"CUST_LEVEL".equals(key)||"CUST_TYPE".equals(key))
        				 sb.append(" and  m."+key+ " = '"+this.getJson().get(key)+"'");
        			 else if(key.equals("ORG_ID"))
        				 sb.append(" and m.INSTITUTION in ('"+this.json.get(key).toString().replaceAll(",", "','")+"')");
        			 else if(key.equals("MGR_ID"))
        				 sb.append(" and m.mgr_id in ('"+this.json.get(key).toString().replaceAll(",", "','")+"')");
        		 }
        	 }
             
        }else{
        	 sb.append("select c.POTENTIAL_FLAG,c.source_channel,c.cust_id,c.cust_name,c.ident_type,c.ident_no,c.cust_type,c.cust_level,c.job_type,c.indust_type," +
             		"c.linkman_name,c.linkman_tel,c.cust_stat,m.institution_name as org_name,m.mgr_name,m.mgr_id,m.INSTITUTION as org_id,d.addr " +
             		"from ACRM_F_CI_CUSTOMER c left join OCRM_F_CI_BELONG_CUSTMGR m on c.cust_id = m.cust_id  "+
             		"left join ACRM_F_CI_ADDRESS d on d.cust_id=c.cust_id and d.ADDR_TYPE='02' where 1 = 1 ");
        	 
        	 for(String key:this.getJson().keySet()){
        		 if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
        			 if(key.equals("CUST_NAME")||key.equals("IDENT_NO"))
        				 sb.append(" and c."+key+" like '%"+this.getJson().get(key)+"%'");
        			 else if(key.equals("CUST_ID")||key.equals("CUST_STAT")||"CUST_LEVEL".equals(key)||"CUST_TYPE".equals(key))
        				 sb.append(" and  c."+key+ " = '"+this.getJson().get(key)+"'");
        			 else if(key.equals("ORG_ID"))
        				 sb.append(" and m.INSTITUTION in ('"+this.json.get(key).toString().replaceAll(",", "','")+"')");
        			 else if(key.equals("MGR_ID"))
        				 sb.append(" and m.mgr_id in ('"+this.json.get(key).toString().replaceAll(",", "','")+"')");
        		 }
        	 }
        }
        
        addOracleLookup("IDENT_TYPE", "XD000040");
        addOracleLookup("CUST_TYPE", "XD000080");
        addOracleLookup("POTENTIAL_FLAG", "XD000084");
        addOracleLookup("CUST_LEVEL", "PRE_CUST_LEVEL");
        addOracleLookup("CUST_STAT", "XD000081");
        addOracleLookup("JOB_TYPE", "PAR0400044");
        addOracleLookup("INDUST_TYPE", "XD000055");  //行业分类
		SQL = sb.toString();
		datasource = dsOracle;

		configCondition("POTENTIAL_FLAG","=","POTENTIAL_FLAG",DataType.String);
	}
 	/*
 	 * @describe 客户名称自动搜索功能后台代码
 	 * @author xinzq 
 	 */
	public String NameFind() {
		
		ActionContext ctx = ActionContext.getContext();
	      request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	      String nameFind = request.getParameter("custName");
	      StringBuilder tempSql = new StringBuilder("select T.CUST_ZH_NAME from OCRM_F_CI_CUST_DESC t where t.CUST_ZH_NAME like '%"+nameFind+"%'");
					map = cqs.excuteQuery(tempSql.toString(), 0, 10);
					this.json = map;
	      return "success";
				}
}
