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
 * @描述：营销管理->商机管理->商机池/我的商机->销售活动查询action（商机池和我的商机模块公用此查询）
 * @author wzy
 * @date:2013-02-22
 */
@ParentPackage("json-default")
@Action(value = "/mktBusiOpporSalesActivQueryAction", results = { @Result(name = "success", type = "json"), })
public class MktBusiOpporSalesActivQueryAction extends CommonAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource dsOracle;

	// 覆盖父类的prepare方法：构造查询列表数据的方法逻辑
	public void prepare() {
		ActionContext ctx = null;
		String oppor_id = null;// 商机ID
		StringBuilder sb = null;
		try {
			ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			oppor_id = request.getParameter("oppor_id");
			sb = new StringBuilder("");

			sb.append("select t.sales_activ_id,");
			sb.append(" t.oppor_id,");
			sb.append(" t.sales_activ_name,");
			sb.append(" t.sales_stage,");
			sb.append(" t.exec_date,");
			sb.append(" t.exec_way,");
			sb.append(" t.activ_content,");
			sb.append(" t.exec_user_id,");
			sb.append(" t.exec_user_name,");
			sb.append(" t.exec_org_id,");
			sb.append(" t.exec_org_name,");
			sb.append(" to_char(t.next_contact_time,'yyyy-mm-dd') as next_contact_time,");
			sb.append(" t.next_exec_way,");
			sb.append(" t.next_exec_content,");
			sb.append(" t.activ_memo");
			sb.append("  from ocrm_f_mm_mkt_sales_activ t");
			sb.append(" where 1 = 1");

			// 构造SQL的查询条件
			sb.append(" and t.oppor_id = '" + oppor_id + "'");

			// 数据字典设置
			addOracleLookup("SALES_STAGE", "SALES_STAGE");// 销售阶段
			addOracleLookup("EXEC_WAY", "EXEC_WAY");// 活动执行方式
			addOracleLookup("NEXT_EXEC_WAY", "EXEC_WAY");// 活动执行方式

			// 设置排序
			setPrimaryKey("t.sales_activ_id desc,t.exec_date desc,t.sales_activ_name asc");

			// 给SQL对象赋值(完整的查询SQL)
			SQL = sb.toString();

			// 数据源
			datasource = dsOracle;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}