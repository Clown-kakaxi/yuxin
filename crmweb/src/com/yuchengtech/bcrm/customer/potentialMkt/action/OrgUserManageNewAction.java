package com.yuchengtech.bcrm.customer.potentialMkt.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.constance.SystemConstance;

/**
 * @describtion: 用户放大镜查询类
 *
 * @date : 2016/1/4
 */
@Action("/orgusermanageNew")
public class OrgUserManageNewAction extends CommonAction {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
    public void prepare () {
    		
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);

		String role_id = request.getParameter("role_id");
		String org_id = request.getParameter("org_id");
		String searchType = request.getParameter("searchType");
		
		StringBuffer sb = new StringBuffer("SELECT DISTINCT t3.ID,t2.ORG_ID,t3.USER_NAME,t3.USER_CODE,t2.ORG_NAME,t3.ACCOUNT_NAME, t1.role_name ");
		sb.append(" FROM ADMIN_AUTH_ACCOUNT t3 ");
		sb.append(" left join ADMIN_AUTH_ACCOUNT_ROLE t4 on t3.ID = t4.ACCOUNT_ID ");
		sb.append(" left join ADMIN_AUTH_ORG t2 on t3.ORG_ID = t2.ORG_ID ");
		sb.append(" left join ADMIN_AUTH_ROLE t1 on t4.ROLE_ID = t1.ID WHERE 1=1 ");
    	//当左边机构树查询范围是全部机构时不作辖区内判断
    	if(!"ALLORG".equals(searchType)){
			if("DB2".equals(SystemConstance.DB_TYPE)){
				sb.append(" AND (t2.ORG_ID IN (SELECT a.UNITID FROM SYS_UNITS a,SYS_UNITS b WHERE  b.UNITID='"+org_id+"' and locate(b.UNITSEQ,a.UNITSEQ)>0))");
			}else{
				sb.append(" and (t2.ORG_ID IN (SELECT UNITID FROM SYS_UNITS WHERE UNITSEQ LIKE (SELECT UNITSEQ FROM SYS_UNITS WHERE UNITID='"+org_id+"')||'%'))");
			}
    	}
    	if(!"".equals(role_id)){
    		sb.append("and t4.ROLE_ID in ("+ role_id+")");
    	}
    	
    	for(String key : this.getJson().keySet()){
    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
				if(null!=key&&key.equals("USER_NAME")){
					sb.append("  AND (t3.USER_NAME like '%"+this.getJson().get(key)+"%' OR t3.ACCOUNT_NAME like '%"+this.getJson().get(key)+"%')");
				}else if (key.equals("ROLE_ID")){
					sb.append("  AND (t4.ROLE_ID in ( "+this.getJson().get(key)+"))");
				}else if (key.equals("TREE_STORE")){
					if("DB2".equals(SystemConstance.DB_TYPE)){
						sb.append(" AND   (t2.ORG_ID IN  (   SELECT a.UNITID FROM SYS_UNITS a,SYS_UNITS b WHERE  b.UNITID='"+(String)this.getJson().get(key)+"' and locate(b.UNITSEQ,a.UNITSEQ)>0))");
					}else{
						sb.append(" and (t2.ORG_ID IN (SELECT UNITID FROM SYS_UNITS WHERE UNITSEQ LIKE (SELECT UNITSEQ FROM SYS_UNITS WHERE UNITID='"+
							(String)this.getJson().get(key)+"')||'%'))");
					}
				}else if (key.equals("ROLE_ID2")){
					sb.append("  AND (t4.ROLE_ID in ( "+this.getJson().get(key)+"))");
				}else if (key.equals("searchForRoleType")){
					sb.append("  AND (t4.ROLE_ID in ( "+this.getJson().get(key)+"))");
				}else if (key.equals("ORG_ID")){
					sb.append("  AND (t2.ORG_ID in ( '"+this.getJson().get(key)+"'))");
				}
    		}
		}
    	
    	SQL=sb.toString();
		datasource = ds;
    }
    
}
