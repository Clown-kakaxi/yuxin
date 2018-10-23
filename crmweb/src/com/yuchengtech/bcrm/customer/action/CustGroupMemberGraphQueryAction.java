package com.yuchengtech.bcrm.customer.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

/**
 * @描述：客户群组管理->客户群组视图->群成员关系图查询Action
 * @author wzy
 * @since 2013-04-10
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custGroupMemberGraphQueryAction", results = { @Result(name = "success", type = "json") })
public class CustGroupMemberGraphQueryAction extends CommonAction {

	// 数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	// 构造查询SQL
	public void prepare() {
		StringBuilder sb = new StringBuilder("");
		sb.append("select t.id,");
		sb.append("       t.graph_name,");
		sb.append("       t.graph_describe,");
		sb.append("       t.create_user_id,");
		sb.append("       t.create_user_name,");
		sb.append("       t.create_date,");
		sb.append("       t.create_org_id,");
		sb.append("       t.create_org_name,");
		sb.append("       t.update_user_id,");
		sb.append("       t.update_user_name,");
		sb.append("       t.update_date,");
		sb.append("       t.update_org_id,");
		sb.append("       t.update_org_name");
		sb.append("  from ocrm_f_ci_custgroup_graph t");
		sb.append("  where 1 = 1");
		// 拼接前台传入的查询条件到SQL语句
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (null != key && key.equals("GRAPH_NAME")) {
					// 关系图名称
					sb.append(" and t.graph_name like '%"
							+ this.getJson().get(key) + "%'");
				} else if (null != key && key.equals("CREATE_DATE")) {
					// 关系图创建时间
					String et = (String) this.getJson().get(key);
					sb.append(" and t.create_date = to_date('"
							+ et.substring(0, 10) + "','yyyy-MM-dd')");
				}
			}
		}
		// 设置排序字段
		setPrimaryKey("t.id asc");
		SQL = sb.toString();
		datasource = ds;
	}
}
