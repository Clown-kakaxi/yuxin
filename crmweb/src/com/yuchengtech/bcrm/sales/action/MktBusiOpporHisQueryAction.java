package com.yuchengtech.bcrm.sales.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;

/**
 * @描述：营销管理->商机管理->商机池功能的商机历史列表查询Action
 * @author wzy
 * @date:2013-02-25
 */
@ParentPackage("json-default")
@Action(value = "/mktBusiOpporHisQueryAction", results = { @Result(name = "success", type = "json"), })
public class MktBusiOpporHisQueryAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;

	public void prepare() {
		String opporId = null;
		StringBuilder sb = new StringBuilder("");

		sb.append("select t.oppor_id,");
		sb.append("       t.opr_user_id,");
		sb.append("       t.opr_user_name,");
		sb.append("       t.opr_org_id,");
		sb.append("       t.opr_org_name,");
		sb.append("       t.opr_content,");
		sb.append("       to_char(t.opr_date_time, 'yyyy-mm-dd hh24:mi:ss') as opr_date_time");
		sb.append("  from ocrm_f_mm_mkt_busi_oppor_his_s t");
		sb.append(" where 1 = 1");

		request = this.getRequest();
		opporId = request.getParameter("oppor_id");
		sb.append(" and t.oppor_id = '" + opporId + "'");

		setPrimaryKey("t.step_id desc ");// 排序

		SQL = sb.toString();
		datasource = ds;

	}

	// 获取HttpServletRequest对象
	private HttpServletRequest getRequest() {
		ActionContext ctx = ActionContext.getContext();
		return (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	}
}