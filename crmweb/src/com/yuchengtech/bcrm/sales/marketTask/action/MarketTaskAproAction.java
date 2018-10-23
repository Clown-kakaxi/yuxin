package com.yuchengtech.bcrm.sales.marketTask.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/mktTaskApro")
public class MarketTaskAproAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	public void prepare() {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String id=request.getParameter("id");
		StringBuffer sb = new StringBuffer(
				"select t.oper_obj_name,t2.target_name,t1.target_value,t1.achieve_value,t1.ACHIEVE_REMARK  from OCRM_F_MM_TASK t"+
				"	left join OCRM_F_MM_TASK_TARGET t1 on t.task_id=t1.task_id "
				+ "	left join OCRM_F_MM_TARGET t2 on t1.target_code=t2.target_code " );
		sb.append(" where 1=1 and t.task_id='"+id+"'");
		// 暂存状态的子任务不允许查询出来
		SQL = sb.toString();
		datasource = ds;
	}

}
