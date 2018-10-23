package com.yuchengtech.bcrm.customer.action;

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
 * @描述：客户管理->客户群组管理->群成员关系图->查询群组成员
 * @author wzy
 * @date:2013-04-10
 */
@ParentPackage("json-default")
@Action(value = "/queryCustGroupMemberGraphAction", results = { @Result(name = "success", type = "json"), })
public class QueryCustGroupMemberGraphAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	public void prepare() {
		ActionContext ctx = null;
		StringBuilder sb = null;
		HttpServletRequest request = null;
		String cust_base_id = null;
		ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		cust_base_id = request.getParameter("custGroupID");
		sb = new StringBuilder("");
		// 查询客户群里所有的客户
		sb.append("  select t2.cust_id,");
		sb.append("       t2.cust_zh_name,");
		sb.append("       t2.cert_type,");
		sb.append("       t2.cert_num,");
		sb.append("       t2.cust_typ,");
		sb.append("       t1.crate_date,");
		sb.append("       t3.institution_name,");
		sb.append("       t4.mgr_name");
		sb.append("  from ocrm_f_ci_relate_cust_base t1");
		sb.append("       left outer join ocrm_f_ci_cust_desc t2 on t1.cust_id = t2.cust_id");
		sb.append("       left outer join (select * from ocrm_f_ci_belong_org t where t.main_type = '1') t3 on t1.cust_id = t3.cust_id");
		sb.append("       left outer join (select * from ocrm_f_ci_belong_custmgr t where t.main_type = '1') t4 on t1.cust_id = t4.cust_id");
		sb.append("  where t1.cust_base_id = " + cust_base_id);
		// 拼接前台传入的查询条件到SQL语句
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (null != key && key.equals("CUST_ID")) {
					// 客户号
					sb.append(" and t2.cust_id = '" + this.getJson().get(key)
							+ "'");
				} else if (null != key && key.equals("CUST_ZH_NAME")) {
					// 客户名称
					sb.append(" and t2.cust_zh_name like '%"
							+ this.getJson().get(key) + "%'");
				}
			}
		}
		setPrimaryKey("t1.crate_date desc ");// 排序
		// 设置数据字典数据
		addOracleLookup("CERT_TYPE", "PAR0100006");// 证件类型
		addOracleLookup("CUST_TYP", "XD000080");// 客户类型
		SQL = sb.toString();
		datasource = ds;
	}
}