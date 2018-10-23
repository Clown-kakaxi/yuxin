package com.yuchengtech.bcrm.customer.belong.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 客户被移转的纪录
 * 移交申请信息查询  流程页面使用
 * 个金
 * @author lianghe
 *
 */
@SuppressWarnings("serial")
@Action("/transApplyHis")
public class OcrmFCiTransApplyHisAction extends CommonAction {
	private HttpServletRequest request;

	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
	}
	
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String apply = request.getParameter("applyNo");
    	StringBuffer sb = new StringBuffer("");
    	sb.append(" select c.id,a.apply_no,c.cust_id,c.cust_name,c.mgr_id,c.mgr_name,a.t_mgr_id,a.t_mgr_name,a.apply_date,a.work_interfix_dt,a.approve_stat,m.counts ");
    	sb.append("   from ocrm_f_ci_trans_apply a  ");
    	sb.append("   left join ocrm_f_ci_trans_cust c on a.apply_no = c.apply_no ");
    	sb.append("   left join (select count(c.id) as counts, c.cust_id  from ocrm_f_ci_trans_apply a  left join ocrm_f_ci_trans_cust c on a.apply_no = c.apply_no where a.approve_stat <> '3' group by c.cust_id) m ");
    	sb.append("    on c.cust_id = m.cust_id ");
    	sb.append("  where c.cust_id in (select c.cust_id from ocrm_f_ci_trans_apply a ");
    	sb.append("                        left join ocrm_f_ci_trans_cust c on a.apply_no = c.apply_no");
    	sb.append("                       where a.apply_no = '"+apply+"') ");
    	sb.append("  order by c.cust_id,a.apply_no desc ");
    	
    	SQL = sb.toString();
    	datasource = ds;
    }

}
