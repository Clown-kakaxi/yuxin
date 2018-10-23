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
 * 客户视图-客户拜访信息
 * @author geyu
 * 2014-7-21
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/ocrmCustViewQuery", results = { @Result(name = "success", type = "json")})
public class CustViewInfoQueryAction extends CommonAction {
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 


	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String custId=request.getParameter("custId");
   	    String flag=request.getParameter("flag");
   	    StringBuffer sb=new StringBuffer("");
   	    if("1".equals(flag)){
   	    	sb.setLength(0);
   	   	    sb.append("SELECT T.* FROM OCRM_F_WP_SCHEDULE_VISIT T WHERE T.CUST_ID='"+custId+"'");
			for (String key : this.getJson().keySet()) {
				if (null != this.getJson().get(key)
						&& !this.getJson().get(key).equals("")) {
					if (key.equals("VISIT_TYPE") || key.equals("VISIT_STAT")) {
						sb.append("AND T." + key + " = '"
								+ this.getJson().get(key) + "'");
					}
				}
			}
   	    }else{
   	    	sb.setLength(0);
   	    	sb.append("select * from OCRM_F_MKT_TRACK_RECORD t where t.service_kind='03' and t.cust_id='"+custId+"'");
   	    	for (String key : this.getJson().keySet()) {
				if (null != this.getJson().get(key)
						&& !this.getJson().get(key).equals("")) {
					if (key.equals("CANTACT_DATE") || key.equals("CANTACT_DATE")) {
						 sb.append(" and t."+key+"= to_date('" +this.getJson().get(key).toString().substring(0, 10)+"','yyyy-MM-dd')");
					}
				}
			}
   	    }
   	    SQL=sb.toString();
		datasource = ds;
		
	}
}
