package com.yuchengtech.bcrm.customer.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @描述：客户管理->客户群组管理->批量生成商机->查询群组成员
 * @author wzy
 * @date:2013-04-07
 */
@ParentPackage("json-default")
@Action(value = "/queryCustGroupMemberAction", results = { @Result(name = "success", type = "json"), })
public class QueryCustGroupMemberAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	// 构造查询SQL
	public void prepare() {
		ActionContext ctx = null;
		StringBuilder sb = null;
		HttpServletRequest request = null;
		String cust_base_id = null;// 客户群组ID
		AuthUser auth = null;
		String hadSelected = null;// 已选择客户成员ID集合

		ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		cust_base_id = request.getParameter("custGroupID");
		hadSelected = request.getParameter("hadSelected");
		if (hadSelected != null && !"".equals(hadSelected)
				&& hadSelected.indexOf("[") == 0
				&& hadSelected.lastIndexOf("]") == hadSelected.length() - 1
				&& hadSelected.length() > 2) {
			hadSelected = hadSelected.replaceAll("\\[", "\\(");
			hadSelected = hadSelected.replaceAll("\\]", "\\)");
			hadSelected = hadSelected.replaceAll("\"", "'");
		} else {
			hadSelected = null;
		}
		auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		sb = new StringBuilder("");
		if (this.isCurrUserCreate(cust_base_id, auth)) {
			// 当前用户是客户群的创建人
			// 查询客户群里所有的客户
			sb.append("  select t2.cust_id,");
			sb.append("       t2.cust_zh_name,");
			sb.append("       t2.cert_type,");
			sb.append("       t2.cert_num,");
			sb.append("       t1.crate_date");
			sb.append("  from ocrm_f_ci_relate_cust_base t1");
			sb.append("       left outer join ocrm_f_ci_cust_desc t2 on t1.cust_id = t2.cust_id");
			sb.append("  where t1.cust_base_id = " + cust_base_id);
			// 拼接前台传入的查询条件到SQL语句
			for (String key : this.getJson().keySet()) {
				if (null != this.getJson().get(key)
						&& !this.getJson().get(key).equals("")) {
					if (null != key && key.equals("CUST_ID")) {
						// 客户号
						sb.append(" and t2.cust_id = '"
								+ this.getJson().get(key) + "'");
					} else if (null != key && key.equals("CUST_ZH_NAME")) {
						// 客户名称
						sb.append(" and t2.cust_zh_name like '%"
								+ this.getJson().get(key) + "%'");
					} else if (null != key && key.equals("CUST_TYP")) {
						// 客户类型
						sb.append(" and t2.cust_typ = '"
								+ this.getJson().get(key) + "'");
					}
				}
			}
			// 排除已经选择的成员客户
			if (hadSelected != null && !"".equals(hadSelected)) {
				sb.append(" and t2.cust_id not in " + hadSelected);
			}
			setPrimaryKey("t1.crate_date desc ");// 排序
		} else {
			// 当前用户不是客户群的创建人
			// 查询客户群中归属自己的客户或者所辖的客户
			sb.append("select son.cust_id,");
			sb.append("        son.cust_zh_name,");
			sb.append("        son.cert_type,");
			sb.append("        son.cert_num,");
			sb.append("        son.crate_date");
			sb.append(" from ");
			sb.append(" (select distinct t2.cust_id,");
			sb.append("                t2.cust_zh_name,");
			sb.append("                t2.cert_type,");
			sb.append("                t2.cert_num,");
			sb.append("                t2.cust_typ,");
			sb.append("                t1.crate_date");
			sb.append("  from ocrm_f_ci_relate_cust_base t1,");
			sb.append("       ocrm_f_ci_cust_desc        t2");
			sb.append("       left outer join ocrm_f_ci_belong_custmgr t3 on t2.cust_id = t3.cust_id,");
			sb.append("       ocrm_f_ci_belong_org       t4");
			sb.append(" where t1.cust_id = t2.cust_id");
			sb.append("   and t3.mgr_id = '" + auth.getUserId() + "'");
			sb.append("   and t1.cust_base_id = " + cust_base_id);
			sb.append(" union ");
			sb.append("select distinct t5.cust_id,");
			sb.append("                t5.cust_zh_name,");
			sb.append("                t5.cert_type,");
			sb.append("                t5.cert_num,");
			sb.append("                t5.cust_typ,");
			sb.append("                t4.crate_date");
			sb.append("  from ocrm_f_ci_relate_cust_base t4");
			sb.append("       left outer join ocrm_f_ci_belong_org t6 on t4.cust_id = t6.cust_id,");
			sb.append("       ocrm_f_ci_cust_desc        t5");
			sb.append(" where t4.cust_id = t5.cust_id");
			sb.append("   and t6.institution_code in");
			sb.append("       (select sysunit.unitid");
			sb.append("          from sys_units sysunit");
			sb.append("         where sysunit.unitseq like '"
					+ auth.getUnitInfo().get("UNITSEQ") + "%')");
			sb.append("   and t4.cust_base_id = " + cust_base_id);
			sb.append(") son");
			sb.append(" where 1 = 1");
			// 拼接前台传入的查询条件到SQL语句
			for (String key : this.getJson().keySet()) {
				if (null != this.getJson().get(key)
						&& !this.getJson().get(key).equals("")) {
					if (null != key && key.equals("CUST_ID")) {
						// 客户号
						sb.append(" and son.cust_id = '"
								+ this.getJson().get(key) + "'");
					} else if (null != key && key.equals("CUST_ZH_NAME")) {
						// 客户名称
						sb.append(" and son.cust_zh_name like '%"
								+ this.getJson().get(key) + "%'");
					} else if (null != key && key.equals("CUST_TYP")) {
						// 客户类型
						sb.append(" and son.cust_typ = '"
								+ this.getJson().get(key) + "'");
					}
				}
			}
			// 排除已经选择的成员客户
			if (hadSelected != null && !"".equals(hadSelected)) {
				sb.append(" and son.cust_id not in " + hadSelected);
			}
			setPrimaryKey("son.crate_date desc ");// 排序
		}
		// 设置数据字典数据
		addOracleLookup("CERT_TYPE", "PAR0100006");// 证件类型
		SQL = sb.toString();
		datasource = ds;

	}

	// 判断当前用户是否是某个客户群的创建人；是：返回true；否：返回false
	@SuppressWarnings("unchecked")
	private boolean isCurrUserCreate(String custGroupID, AuthUser auth) {
		boolean result = true;
		String sql = null;
		Map<String, Object> hm = null;
		Map<String, Object> map = null;
		sql = "select t.cust_base_create_name";
		sql += " from ocrm_f_ci_base t ";
		sql += " where t.id = " + custGroupID;
		try {
			map = new QueryHelper(sql, ds.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (map != null && map.get("data") != null) {
				hm = (HashMap<String, Object>) ((List<HashMap<String, Object>>) map
						.get("data")).get(0);
				if (!auth.getUserId().equals(hm.get("CUST_BASE_CREATE_NAME"))) {
					result = false;
				}
			}
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
}