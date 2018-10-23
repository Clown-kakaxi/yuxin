package com.yuchengtech.bcrm.customer.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 个金潜在客户修改历史查询
 * @author mamusa
 * @date2016-01-08
 *
 */
@Action("/latentDispatchHisQueryAction")
public class LatentDispatchHisQueryAction extends CommonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String cusId = request.getParameter("cusId");
    	String stateval = request.getParameter("stateval");
    	StringBuffer sb=new StringBuffer("select ph.cus_id,ph.cus_name,ph.state,ac1.user_name mover_user,to_char(to_date(ph.mover_date,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') mover_date,ac2.user_name cust_mgr_before,ac3.user_name cust_mgr_after ,REGEXP_REPLACE( d.call_no, '#-', '') call_no  from ACRM_F_CI_POT_CUS_HIS ph ");
    	sb.append(" left join ADMIN_AUTH_ACCOUNT ac1 on ph.mover_user=ac1.account_name");
    	sb.append(" left join ADMIN_AUTH_ACCOUNT ac2 on ph.cust_mgr_before=ac2.account_name");
    	sb.append(" left join ADMIN_AUTH_ACCOUNT ac3 on ph.cust_mgr_after=ac3.account_name");
    	sb.append(" left join ACRM_F_CI_POT_CUS_COM d on d.cus_id=ph.cus_id ");
    	if(stateval!=null&&"2".equals(stateval)){
    		sb.append("  WHERE  ph.cus_id in (select cus_id from ACRM_F_CI_POT_CUS_COM where cust_type='2') and  pH.State='2'");
    	}else{
    	sb.append(" WHERE 1=1 and pH.CUS_ID='"+cusId+"'");
    	}
    	setPrimaryKey("ph.mover_date desc");
    	
    	
    	SQL=sb.toString();
		datasource=ds;
	}
}
