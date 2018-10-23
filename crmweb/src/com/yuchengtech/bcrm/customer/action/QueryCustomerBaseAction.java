package com.yuchengtech.bcrm.customer.action;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 
 * 客户群查询
 * @since 2013-1-16
 *
 */
@ParentPackage("json-default")
@Action(value="/querycustomerbase", results={
    @Result(name="success", type="json")
})
public class QueryCustomerBaseAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

    @Override
    public void prepare() {
        StringBuffer builder = new StringBuffer("select t1.*," +
        		"CASE t1.SHARE_FLAG" +
        		" WHEN '0' THEN '私有'" +
        		" WHEN '1' THEN '全行共享'" +
        		" WHEN '2' THEN '本机构共享'" +
        		" WHEN '3' THEN '辖内机构共享' END 	SHARE_FLAG_NAME," +
        		" (SELECT count(1)  from ocrm_f_ci_relate_cust_base where cust_base_id=t1.id) AS MEMBERSNUM ," +
        		" t2.ORG_NAME as cust_base_create_org_name, t3.USER_NAME as createName" +
        		" from OCRM_F_CI_BASE t1  " +
        		" LEFT JOIN ADMIN_AUTH_ORG t2 on t2.ORG_ID = t1.CUST_BASE_CREATE_ORG " +
        		" LEFT JOIN ADMIN_AUTH_ACCOUNT t3 on t1.CUST_BASE_CREATE_NAME = t3.ACCOUNT_NAME " +
        		" where 1>0 ");
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
        		if("CUST_BASE_NUMBER".equals(key))
        			builder.append(" and t1." + key + " = " + " '" + value + "'");
        		else if("CUST_BASE_NAME".equals(key))
        			builder.append(" and t1." + key + " like " + "'%" + value +"%'");
        		else if("CUST_BASE_CREATE_DATE".equals(key))
        			builder.append(" and t1." + key + " = " + "to_date('" + value + "', 'YYYY-MM-DD')");            
      	  	}
        }
        setPrimaryKey("t1.id");
        SQL = builder.toString();
        datasource = ds;
    }
}
