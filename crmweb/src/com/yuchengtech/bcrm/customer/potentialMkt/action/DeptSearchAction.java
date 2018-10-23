package com.yuchengtech.bcrm.customer.potentialMkt.action;



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

/**
 * 部门查询Action  放大镜使用
 * 
 * @author luyy
 * @since 2014-07-23
 */

@SuppressWarnings("serial")
@Action("/deptSearch")
public class DeptSearchAction extends CommonAction {

	

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性

	@Autowired
	public void init() {
	}
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		StringBuilder sb = new StringBuilder("");
			sb.append("select g.*,o.org_name as belong_org_name from ADMIN_AUTH_DPT g left join admin_auth_org o on g.belong_org_id = o.org_id where 1=1 ");
			
			for (String key : this.getJson().keySet()) {// 查询条件判断
				if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
					if (key.equals("DPT_NAME")) {
						sb.append(" AND g."+key+" like '%"+this.getJson().get(key)+"%'");
					} 
				}
			}

		SQL = sb.toString();
		datasource = ds;
	}
	
	
 
}
