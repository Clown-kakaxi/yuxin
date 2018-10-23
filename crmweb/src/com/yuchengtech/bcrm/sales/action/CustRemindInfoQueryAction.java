package com.yuchengtech.bcrm.sales.action;

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
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 客户视图--客户提醒信息
 * @author geyu
 * 2014-7-22
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custRemindInfoQuery", results = { @Result(name = "success", type = "json")})
public class CustRemindInfoQueryAction  extends CommonAction {
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
   	    StringBuffer sb=new StringBuffer("select t.*,t1.remind_type from OCRM_F_WP_REMIND t left join OCRM_F_WP_REMIND_RULE t1 on t.rule_id=t1.rule_id "+ 
        " where t.cust_id='"+custId+"'");
   	    this.setPrimaryKey(" t.msg_crt_date  desc");
   	    SQL=sb.toString();
		datasource = ds;
		configCondition("t.RULE_CODE", "=", "RULE_CODE",DataType.String);
		configCondition("t.MSG_CRT_DATE", "=", "MSG_CRT_DATE",DataType.Date);
		configCondition("t.MSG_END_DATE", "=", "MSG_END_DATE",DataType.Date);
		configCondition("t.REMIND_REMARK", "like", "REMIND_REMARK",DataType.String);
	
		
	}

}
