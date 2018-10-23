package com.yuchengtech.bcrm.customer.action;

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
 * 
 * 放大镜--客户群查询
 * @sujm 2013-2-26
 *
 */
@ParentPackage("json-default")
@Action(value="/querycustomerbaseinfo", results={
    @Result(name="success", type="json")
})
public class QueryCustomerBaseInfoAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	private HttpServletRequest request;
	
    @Override
    public void prepare() {
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	 	String custIds =  request.getParameter("custIds");
	 	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
        String currendOrgId = auth.getUnitId();
        List orgList  = auth.getPathOrgList();
        String allowDtGroup = request.getParameter("allowDtGroup");
        StringBuffer builder = new StringBuffer("  SELECT DISTINCT X.ID,                        "+
        		"                X.CUST_BASE_CREATE_DATE,                                       "+
        		"                X.CUST_BASE_NAME,                                              "+
        		"                X.CUST_BASE_DESC,                                              "+
        		"                X.SHARE_FLAG,                                                  "+
        		"                X.CUST_BASE_NUMBER,  	                                        "+
        		"                X.USER_NAME,		                                            "+
        		"                X.CUST_BASE_CREATE_ORG,                                        "+
        		"                X.CUST_BASE_CREATE_NAME,                                       "+
        		"                X.CUST_FROM,			                                        "+
        		"                X.GROUP_TYPE,			                                        "+
        		"                X.GROUP_MEMBER_TYPE 	                                        "+
//        		"               wmsys.wm_concat(X.CUST_ID) over(partition by X.ID) AS CUST_STR, "+
//        		"             wmsys.wm_concat(X.CUST_NAME) over(partition by X.ID) AS CUST_STR1 "+
        		"  FROM (SELECT T.ID,                                                           "+
        		"               T.CUST_BASE_NAME,                                               "+
        		"               T.CUST_BASE_CREATE_DATE,                                        "+
        		"               T.CUST_BASE_DESC,                                               "+
        		"               T.SHARE_FLAG,                                                   "+
        		"               T.CUST_BASE_NUMBER,                                             "+
        		"               T.CUST_BASE_CREATE_ORG,                                         "+
        		"               T.CUST_BASE_CREATE_NAME,                                        "+
        		"               T.CUST_FROM,			                                        "+
        		"               T.GROUP_TYPE,			                                        "+
        		"               T.GROUP_MEMBER_TYPE,	                                        "+
        		"               T3.CUST_NAME,                                   "+
        		"               T1.CUST_ID,														"+
        		" 				T2.USER_NAME                                                    "+
        		"  FROM OCRM_F_CI_BASE t left join ocrm_f_ci_relate_cust_base T1 on T.ID = T1.CUST_BASE_ID left join admin_auth_account t2 on t2.account_name = t.cust_base_create_name left join acrm_f_ci_customer t3 on t3.cust_id = t1.cust_id ");
        builder.append(") X where 1>0 ");
        if(custIds != null && ! "".equals(custIds))
        	builder.append(" and X.ID not in (select distinct a.id from OCRM_F_CI_BASE a, ocrm_f_ci_relate_cust_base b where b.cust_base_id = a.id and b.cust_id in (" + custIds +"))");
        for (String key : getJson().keySet()){
        	String value = getJson().get(key).toString();
        	if (! "".equals(value)) {
        		if("GROUP_MEMBER_TYPE".equals(key))
        			builder.append(" and X." + key + " = " + " '" + value + "'");
        		else if("GROUP_TYPE".equals(key))
        			builder.append(" and X." + key + " = " + " '" + value + "'");
        		else if("CUST_BASE_NUMBER".equals(key))
        			builder.append(" and X." + key + " = " + " '" + value + "'");
        		else if("CUST_BASE_NAME".equals(key))
        			builder.append(" and X." + key + " like " + "'%" + value +"%'");
        		else if("CUST_BASE_CREATE_DATE".equals(key))
        			builder.append(" and X." + key + " = " + "to_date('" + value + "', 'YYYY-MM-DD')");            
      	  	}
        }
        Map b = null;
        
        if("false".equals(allowDtGroup)){//不能选择自动筛选客户群
        	builder.append(" and X.CUST_FROM<>'2'");
        }
        StringBuffer c = new StringBuffer("");
        for(int a=0;a<orgList.size();a++){
        	b = (Map)orgList.get(a);     		
        	c.append((String)b.get("UNITSEQ"));	 	
        }
        builder.append(" and (X.Cust_base_create_name='" + currenUserId+ "'");
        builder.append(" or (X.share_flag = '1') " +
        				"or (X.CUST_BASE_CREATE_ORG = '"+currendOrgId+"' and X.share_flag = '2')" +
        				" or (X.cust_base_create_org in('"+c.toString()+"') and X.share_flag = '3'))");
        setPrimaryKey("X.ID");
        SQL = builder.toString();
        datasource = ds;
        addOracleLookup("GROUP_TYPE", "CUSTOMER_GROUP_TYPE");
        addOracleLookup("GROUP_MEMBER_TYPE", "GROUP_MEMEBER_TYPE");
        addOracleLookup("SHARE_FLAG", "SHARE_FLAG");
        addOracleLookup("CUST_FROM", "CUSTOMER_SOURCE_TYPE");
    }
}
