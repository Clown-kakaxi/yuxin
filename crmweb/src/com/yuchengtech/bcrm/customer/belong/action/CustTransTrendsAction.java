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

/**
 * 个金客户移交
 * 被移转客户的接收方客户经理查询移转的情况动态
 * @author lianghe
 *
 */
@SuppressWarnings("serial")
@Action("/custTransTrends")
public class CustTransTrendsAction extends CommonAction {
    
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
    	StringBuffer sb = new StringBuffer("");
    	sb.append("  select t.cust_id,t.cust_name,t.mgr_id,t.mgr_name,t.state,t.t_mgr_id,t.t_mgr_name,t.apply_date,t.work_interfix_dt,t.approve_stat,t.apply_no,l.NODENAME,l.CURRENTNODEUSERS as user_id,ac.user_name  ");
    	sb.append("    from (select m.*,row_number() over(partition by m.cust_id order by m.apply_no desc) cn  ");
    	sb.append("            from ( select c.cust_id,c.cust_name,c.mgr_id,c.mgr_name,c.state,a.t_mgr_id,a.t_mgr_name,a.apply_date,a.work_interfix_dt,a.approve_stat,a.apply_no  ");
    	sb.append("                     from ocrm_f_ci_trans_apply a left join ocrm_f_ci_trans_cust c on a.apply_no = c.apply_no   ) m)t ");
    	sb.append("  left join WF_WORKLIST l  on ('YJ_'||t.apply_no) = l.INSTANCEID  ");
    	sb.append("   left join ADMIN_AUTH_ACCOUNT ac  on substr(l.CURRENTNODEUSERS,0,INSTR(l.CURRENTNODEUSERS,';')-1) = ac.account_name ");
    	sb.append("   where t.cn =1 and t.t_mgr_id ='"+auth.getUserId()+"'  ");
    	sb.append("   order by t.approve_stat  ");
    	SQL = sb.toString();
    	datasource = ds;
    }
}
