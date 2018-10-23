package com.yuchengtech.bcrm.customer.action;


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
 * 客户分配-查看分配历史
 * @author sxs
 * @since 2013-1-16
 * 
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custAssignHistQuery-action", results = { @Result(name = "success", type = "json")})
public class CustAssignHistQueryAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	/**
	 *模块功能查询
	 */
	public void prepare() {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		StringBuilder sb = new StringBuilder(
					"select distinct c.*,t.CUST_ZH_NAME from OCRM_F_CI_CUST_BELONG_HIST	c left join OCRM_F_CI_CUST_VIEW t on t.cust_id = c.cust_id" +
					" where 1=1 and  c.CURRENT_INSTITUTION = '"+auth.getUnitId()+"' ");
        for (String key : getJson().keySet()){
            String value = getJson().get(key).toString();
            if (! "".equals(value)) {
                if("CUST_ID".equals(key))
                    sb.append(" and c." + key + " like " + "'%" + value + "%'");
                else if("CUST_ASSIGN_TYPE".equals(key))
                    sb.append(" and c." + key + " like " + "'%" + value +"%'");
                else if("BEGIN_TIME".equals(key))
                    sb.append(" and c.ASSIGN_TIME>= to_date('" + value + "', 'YYYY-MM-DD') ");  
                else if("END_TIME".equals(key))
                    sb.append(" and c.ASSIGN_TIME <= to_date('" + value + "', 'YYYY-MM-DD') ");    
            }
        }
		
		
		
		
		
		
		
		setPrimaryKey("c.ID");
        addOracleLookup("CUST_ASSIGN_TYPE","CUST_ASSIGN_TYPE");//操作类型
        addOracleLookup("MAIN_TYPE","MAINTAIN_TYPE");//主协办类型

		SQL=sb.toString();
		datasource = ds;
	}
}
