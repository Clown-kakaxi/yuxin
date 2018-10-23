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
import com.yuchengtech.bob.service.CommonQueryService;
/***
 * 2017-06-05  修改客户查询方法 (客户选择放大镜使用)
 * Pipeline营销概览
 */
@ParentPackage("json-default")
@Action(value="/customerBaseInformationYYAction", results={
    @Result(name="success", type="json"),
})
public class CustomerBaseInformationYYAction extends CommonAction{
	private static final long serialVersionUID = 2456678963456642385L;

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
        sb.append("SELECT * from(")
        .append("SELECT C.POTENTIAL_FLAG, ")
        .append("C.CUST_ID, ")
        .append("C.CUST_NAME, ")
        .append("C.IDENT_TYPE, ")
        .append("C.IDENT_NO, ")
        .append("C.CUST_TYPE, ")
        .append("C.CUST_LEVEL, ")
        .append("C.INDUST_TYPE, ")
        .append("C.CUST_STAT, ")
        .append("M.INSTITUTION_NAME AS ORG_NAME, ")
        .append("M.MGR_NAME, ")
        .append("M.MGR_ID, ")
        .append("M.INSTITUTION AS ORG_ID, ")
        .append("CASE WHEN C.LOAN_CUST_STAT IN ('20', '21') THEN 1 ELSE 0 END IS_SX_CUST, ")//---待确定规则 
        .append("O1.UP_ORG_ID, ")
        .append("O2.ORG_NAME UP_ORG_NAME, ")
        .append("I.F_VALUE, ")
        .append("K.IS_TAIWAN_CORP ")
        .append("FROM ACRM_F_CI_CUSTOMER C ")
        .append("INNER JOIN OCRM_F_CI_BELONG_CUSTMGR M ON C.CUST_ID = M.CUST_ID ")
        .append("LEFT JOIN ACRM_F_CI_ORG O ON C.CUST_ID = O.CUST_ID ")
        .append("LEFT JOIN ADMIN_AUTH_ORG O1 ON M.INSTITUTION = O1.ORG_ID ")
        .append("LEFT JOIN ADMIN_AUTH_ORG O2 ON O1.UP_ORG_ID = O2.ORG_ID ")
        .append("LEFT JOIN OCRM_SYS_LOOKUP_ITEM I ON I.F_CODE = O.ORG_BIZ_CUST_TYPE AND I.F_LOOKUP_ID = 'XD000052' ")
        .append("LEFT JOIN ACRM_F_CI_ORG_KEYFLAG K ON C.CUST_ID = K.CUST_ID ")
        .append(") C ")
        .append("WHERE 1 = 1 ")
        .append("AND NOT EXISTS (SELECT 1 FROM OCRM_F_MK_PIPELINE T WHERE CASE_STATE IN ('0', '2') AND T.CUST_ID = C.CUST_ID) ")
        .append(" ");
        for(String key:this.getJson().keySet()){
   	     if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
   	         if(key.equals("CUST_NAME")||key.equals("IDENT_NO"))
   	             sb.append(" and c."+key+" like '%"+this.getJson().get(key)+"%'");
   	         else if(key.equals("CUST_ID")||key.equals("CUST_STAT")||"CUST_LEVEL".equals(key)||"CUST_TYPE".equals(key))
   	             //sb.append(" and  c."+key+ " = '"+this.getJson().get(key)+"'");
   	             /**
   	              * 暂时添加  企商金 合作意向信息 查询对公客户，以及客户类型为空的客户（移动信贷过来时没赋值 后期该号会去掉该条件）
   	              */
   	             if("true".equals(isNew) && key.equals("CUST_TYPE")){
   	            	 sb.append(" and  c.CUST_TYPE <> '2' ");
   	             }else{
   	            	 sb.append(" and  c."+key+ " = '"+this.getJson().get(key)+"'");
   	             }
   	         else if(key.equals("ORG_ID"))
   	        	 sb.append(" and C.ORG_ID in ('"+this.json.get(key).toString().replaceAll(",", "','")+"')");
   	      else if(key.equals("MGR_ID"))
	        	 sb.append(" and c.mgr_id in ('"+this.json.get(key).toString().replaceAll(",", "','")+"')");
   	     }
       }
        if("true".equals(isNew)){
        	sb.append(" and c.POTENTIAL_FLAG = '0' ");//企商金 合作意向信息 只显示旧客户
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
