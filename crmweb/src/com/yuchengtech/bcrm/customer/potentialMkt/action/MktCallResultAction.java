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
 * 拜访结果汇总信息   放大镜使用
 * 
 * @author luyy
 * @since 2014-07-27
 */

@SuppressWarnings("serial")
@Action("/mktCallResult")
public class MktCallResultAction extends CommonAction {

	

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
		String type = request.getParameter("type");
		StringBuilder sb = new StringBuilder("");
		
		if("3".equals(type)){//客户经理拜访信息  对私
			sb.append(" select CUST_NAME,VISIT_DATE,decode(CHECK_STAT,'1','未完成','已完成') as if_end from OCRM_F_CI_MKT_VISIT_P where user_id = '"+request.getParameter("USER_ID") +"'");
			sb.append(" and VISIT_DATE >= to_date('"+request.getParameter("DATE_S").toString().substring(0,10)+"','YYYY-MM-dd') ");
			sb.append(" and VISIT_DATE <= to_date('"+request.getParameter("DATE_E").toString().substring(0,10)+"','YYYY-MM-dd') " ); 
		}else if("4".equals(type)){//客户经理拜访信息  对公
			sb.append(" select CUST_NAME,VISIT_DATE,decode(CHECK_STAT,'1','未完成','已完成') as if_end from OCRM_F_CI_MKT_VISIT_C where user_id = '"+request.getParameter("USER_ID") +"'");
			sb.append(" and VISIT_DATE >= to_date('"+request.getParameter("DATE_S").toString().substring(0,10)+"','YYYY-MM-dd') ");
			sb.append(" and VISIT_DATE <= to_date('"+request.getParameter("DATE_E").toString().substring(0,10)+"','YYYY-MM-dd') " ); 
		}else{
			sb.append("select t.user_id,ac.user_name,te.team_name,ac.org_id,call_num,cold_call,visit,cold_per,new_visit,pipeline,pipe_per from "+
					"(select user_id,count(1) as call_num,sum(if_cold_call) as cold_call,sum(if_cold_call) as visit,round(sum(if_cold_call)/count(1),4)*100||'%' as cold_per, " +
					"sum(if_new) as new_visit,sum(if_pipeline) as pipeline,round(sum(if_pipeline)/count(1),4)*100||'%' as pipe_per "+
					"from CUST_CALL_RESULT_VIEW t  where cust_type='"+type+"'  ");
			
			sb.append(" and t.phone_date >= to_date('"+this.getJson().get("DATE_S").toString().substring(0,10)+"','YYYY-MM-dd')");
			sb.append(" and t.phone_date <= to_date('"+this.getJson().get("DATE_E").toString().substring(0,10)+"','YYYY-MM-dd') " +
					"group by user_id ) t left join admin_auth_account ac on t.user_id = ac.account_name  " +
					"left join OCRM_F_CM_TEAM_CUST_MANAGER te on te.user_id=t.user_id");
			for (String key : this.getJson().keySet()) {// 查询条件判断
				if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
					if (key.equals("ORG_ID")) {
						sb.append(" AND ac."+key+" = '"+this.getJson().get(key)+"'");
					}
					if (key.equals("USER_ID")) {
						sb.append(" AND t."+key+" = '"+this.getJson().get(key)+"'");
					}
				}
			}
		}

		SQL = sb.toString();
		datasource = ds;
	}
	
	
 
}
