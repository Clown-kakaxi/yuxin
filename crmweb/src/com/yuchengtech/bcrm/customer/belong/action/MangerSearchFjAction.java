
package com.yuchengtech.bcrm.customer.belong.action;


import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.SystemConstance;

/**
 * 客户经理查询   给新做的客户经理放大镜使用
 * @author luyy
 *@since 2014-07-08
 */

@SuppressWarnings("serial")
@Action("/managerSearchfj")
public class MangerSearchFjAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
	}
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	/**
	 * 关联表查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String type = request.getParameter("type");//1 支行内客户经理 2 区域内非本支行的客户经理   3区域内客户经理   4 跨区域客户经理   5非本支行客户经理
    	StringBuffer sb = new StringBuffer(" select a.account_name,a.user_name,a.org_id,o.org_name from admin_auth_account a,admin_auth_org o where a.org_id = o.org_id ");
//    	StringBuffer sb = new StringBuffer();
//    	sb.append(" select a.account_name,a.user_name,a.org_id,o.org_name from admin_auth_account a, admin_auth_org o ");
//        sb.append(" where a.org_id = o.org_id and a.id in ( select ar.account_id from ADMIN_AUTH_ACCOUNT_ROLE ar ");
//        sb.append(" left join ADMIN_AUTH_ROLE r on ar.role_id=r.id where r.role_code in ('R303','R305'))");
       	if("1".equals(type)){
    		sb.append(" and a.org_id = '"+auth.getUnitId()+"'");
    	}else if("2".equals(type)){
    		String upOrg = auth.getUnitInfo().get("SUPERUNITID")+"";
    	    sb.append(" and a.org_id in (select org_id from admin_auth_org where org_id<>'"+auth.getUnitId()+"' start with org_id='"+upOrg+"' connect by up_org_id = prior org_id)");
    	}else if("3".equals(type)){
    		String upOrg = auth.getUnitInfo().get("SUPERUNITID")+"";
    		sb.append(" and a.org_id in (select org_id from admin_auth_org start with org_id='"+upOrg+"' connect by up_org_id = prior org_id)");
    	}else if("4".equals(type)){
    		String upOrg = auth.getUnitInfo().get("SUPERUNITID")+"";
    		sb.append(" and a.org_id not in (select org_id from admin_auth_org start with org_id='"+upOrg+"' connect by up_org_id = prior org_id)");
    	}else if("5".equals(type)){
    		String upOrg = auth.getUnitInfo().get("SUPERUNITID")+"";
    		sb.append(" and ((a.org_id in (select org_id from admin_auth_org where org_id<>'"+auth.getUnitId()+"' start with org_id='"+upOrg+"' connect by up_org_id = prior org_id))" +
    				" or (a.org_id not in (select org_id from admin_auth_org start with org_id='"+upOrg+"' connect by up_org_id = prior org_id)))");
    	}
    	//处理页面查询条件
    	 for(String key : this.getJson().keySet()){
     		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
 				if("USER_NAME".equals(key)){
 					sb.append("  and a."+key+" like '%"+this.getJson().get(key)+"%'  ");
 				}}
 		}
    	
    	 //角色限制   支行客户经理
//    	 sb.append(" and a.id in (select account_id from ADMIN_AUTH_ACCOUNT_ROLE where role_id in (select id from admin_auth_role where role_code in ('R304')))");
//    	 sb.append(" and a.account_name<>'"+auth.getUserId()+"'");//排除自己
    	 SQL = sb.toString();
    	datasource = ds;
    }
	  
}
