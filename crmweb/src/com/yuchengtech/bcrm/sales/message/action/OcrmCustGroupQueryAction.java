package com.yuchengtech.bcrm.sales.message.action;

import java.util.List;
import java.util.Map;

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

/**
 * 渠道自动营销客户群查询
 * @author geyu
 * 2014-7-19
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/ocrmCustGroupQuery", results = { @Result(name = "success", type = "json")})
public class OcrmCustGroupQueryAction extends CommonAction  {
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource dsOracle;  
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	private HttpServletRequest request;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String custBaseNumber = request.getParameter("custBaseNumber");//获取到客户群编号，查询单个客户群信息---客户视图基本信息使用
        StringBuffer builder = new StringBuffer("select t1.*," +
        		"CASE t1.SHARE_FLAG" +
        		" WHEN '0' THEN '私有'" +
        		" WHEN '1' THEN '全行共享'" +
        		" WHEN '2' THEN '本机构共享'" +
        		" WHEN '3' THEN '辖内机构共享' END 	SHARE_FLAG_NAME," +
        		" case when t1.CUST_FROM ='2' then '动态' else (SELECT count(1)  from ocrm_f_ci_relate_cust_base where cust_base_id=t1.id)||'人' end AS MEMBERSNUM ," +
        		" t2.ORG_NAME as cust_base_create_org_name, t3.USER_NAME as createName  " +
        		" from OCRM_F_CI_BASE t1  " +
        		" LEFT JOIN ADMIN_AUTH_ORG t2 on t2.ORG_ID = t1.CUST_BASE_CREATE_ORG " +
        		" LEFT JOIN ADMIN_AUTH_ACCOUNT t3 on t1.CUST_BASE_CREATE_NAME = t3.ACCOUNT_NAME " +
        		"  where 1>0 and t1.group_member_type='2'");
        
        if(null!=custBaseNumber&&(!"".equals(custBaseNumber))){
        	builder.append(" and t1.id='"+custBaseNumber+"' ");//拼接条件，查询单个客户群信息---客户视图基本信息使用
        }
        
        AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
        String currendOrgId = auth.getUnitId();
        List orgList  = auth.getPathOrgList();
        Map b = null;
     
        StringBuffer c = new StringBuffer("");
        for(int a=0;a<orgList.size();a++){
        	b = (Map)orgList.get(a);     		
        	c.append((String)b.get("UNITSEQ"));	 	
        }
        builder.append(" and (t1.Cust_base_create_name='" + currenUserId+ "'");
        builder.append(" or (t1.share_flag = '1') " +
        				"or (t1.cust_base_create_org  = '"+currendOrgId+"' and t1.share_flag = '2')" +
        				" or (t1.cust_base_create_org in('"+c.toString()+"') and t1.share_flag = '3'))");
        
        for (String key : getJson().keySet()){
        	String value = getJson().get(key).toString();
        	if (! "".equals(value)) {
        		if("GROUP_TYPE".equals(key)||"CUST_FROM".equals(key)||"GROUP_MEMBER_TYPE".equals(key)||"SHARE_FLAG".equals(key)){
        			builder.append(" and t1." + key + " = " + " '" + value + "'");
        		}else if("CUST_BASE_NAME".equals(key)||"CUST_BASE_NUMBER".equals(key)){
        			builder.append(" and t1." + key + " like " + "'%" + value +"%'");
        		}else if("custMgrId".equals(key)){
        			builder.append(" and t1.CUST_BASE_CREATE_NAME in('"+value.replace(",", "','")+"')");
        		}else if("CUST_ORG_ID".equals(key)){
        			builder.append(" and t1.CUST_BASE_CREATE_ORG in('"+value.replace(",", "','")+"')");
        		}else if("CUST_BASE_CREATE_DATE_S".equals(key)){
        			builder.append(" and t1.CUST_BASE_CREATE_DATE >= " + "to_date('" + value.substring(0,10) + "', 'YYYY-MM-DD')");            
        		}else if("CUST_BASE_CREATE_DATE_E".equals(key)){
        			builder.append(" and t1.CUST_BASE_CREATE_DATE <= " + "to_date('" + value.substring(0,10) + "', 'YYYY-MM-DD')");  
        		}else if("CUST_ID".equals(key)){
        			builder.append(" and t1.id in(select base.cust_base_id from ocrm_f_ci_relate_cust_base base where base.cust_id = '"+value+"' ) ");
        		}else if("CUST_NAME".equals(key)){
        			builder.append(" and t1.id in(select base.cust_base_id from ocrm_f_ci_relate_cust_base base where base.cust_zh_name like '%"+value+"%' ) ");
        		}else if("CERT_TYPE".equals(key)){
        			builder.append(" and t1.id in(select base.cust_base_id from ocrm_f_ci_relate_cust_base base where base.cust_id in(select cust_id from ACRM_F_CI_CUSTOMER de where de.IDENT_TYPE='"+value+"')  ) ");
        		}else if("CERT_NUM".equals(key)){
        			builder.append(" and t1.id in(select base.cust_base_id from ocrm_f_ci_relate_cust_base base where base.cust_id in(select cust_id from ACRM_F_CI_CUSTOMER de where de.IDENT_NO='"+value+"')  ) ");
        		}
        	}
        }
        setPrimaryKey("t1.CUST_BASE_CREATE_DATE desc ");
        SQL = builder.toString();
        
        datasource = dsOracle;
		
	}

}
