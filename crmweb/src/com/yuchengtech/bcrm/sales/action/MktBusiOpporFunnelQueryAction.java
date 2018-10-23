package com.yuchengtech.bcrm.sales.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.service.MktBusiOpporFunnelQueryService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * @描述：销售漏斗查询Action
 * @author wzy
 * @date:2013-03-25
 */
@ParentPackage("json-default")
@Action(value = "/mktBusiOpporFunnelQueryAction", results = { @Result(name = "success", type = "json"), })
public class MktBusiOpporFunnelQueryAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private MktBusiOpporFunnelQueryService mktBusiOpporFunnelQueryService;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource dsOracle;

	// 覆盖父类的prepare方法：构造查询列表数据的方法逻辑
	public void prepare() {
		try {
			SQL = this.makeQuerySql();
			datasource = dsOracle;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询销售漏斗图形化展示的数据
	public void getQueryResultJsonData() {
		String sql = null;
		String result = null;
		ActionContext ctx = null;
		HttpServletResponse response = null;
		sql = this.makeQuerySql();
		result = mktBusiOpporFunnelQueryService.getQueryResultJsonData(sql);
		result = (result == null ? "" : result);
		ctx = ActionContext.getContext();
		response = (HttpServletResponse) ctx
				.get(ServletActionContext.HTTP_RESPONSE);
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(result);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 构造查询SQL
	@SuppressWarnings("unchecked")
	private String makeQuerySql() {
		StringBuilder sb = null;
		ActionContext ctx = null;
		String condition = null;
		Map<String, Object> json = null;
		try {
			ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			condition = request.getParameter("condition");
			json = (Map<String, Object>) JSONUtil.deserialize(condition);
			sb = new StringBuilder("");
			sb.append("select a.f_code,");
			sb.append("       a.f_value,");
			sb.append("       (a.f_code || '-' || a.f_value) as f_c_v,");
			sb.append("       (decode(a.f_code,");
			sb.append("               '1',");
			sb.append("               '了解客户需求',");
			sb.append("               '2',");
			sb.append("               '确认客户需求、确认产品/服务',");
			sb.append("               '3',");
			sb.append("               '制定服务方案、产品介绍',");
			sb.append("               '4',");
			sb.append("               '销售洽谈',");
			sb.append("               '5',");
			sb.append("               '签约/购买')) as count_stage,");
			sb.append("       (decode(a.f_code,");
			sb.append("               '1',");
			sb.append("               '10%',");
			sb.append("               '2',");
			sb.append("               '25%',");
			sb.append("               '3',");
			sb.append("               '50%',");
			sb.append("               '4',");
			sb.append("               '75%',");
			sb.append("               '5',");
			sb.append("               '100%')) as count_percent,");
			sb.append("       (decode(a.f_code, '1', 0.1, '2', 0.25, '3', 0.5, '4', 0.75, '5', 1)) as count_percent_num,");
			sb.append("       count(b.oppor_id) as count_number,");
			sb.append("       decode(sum(b.plan_amount), null, 0, sum(b.plan_amount)) as count_amount,");
			sb.append("       ((decode(a.f_code, '1', 0.1, '2', 0.25, '3', 0.5, '4', 0.75, '5', 1)) *");
			sb.append("       decode(sum(b.plan_amount), null, 0, sum(b.plan_amount))) as count_weight");
			sb.append("  from ocrm_sys_lookup_item a");
			sb.append("  left outer join (select c.oppor_id, c.plan_amount, c.oppor_stage,c.CREATER_ID,c.EXECUTE_USER_ID,c.CREATE_ORG_ID,c.EXECUTE_ORG_ID");
			sb.append("   from ocrm_f_mm_mkt_busi_oppor c");
			sb.append("   where c.oppor_stat != '7'");
			// 构造SQL的查询条件(从前台页面传入的查询条件参数)
			for (String key : json.keySet()) {
				if (null != json.get(key) && !json.get(key).equals("")) {
					if (key.equals("OPPOR_DUE_DATE") && json.get(key) != null
							&& json.get(key).toString().length() >= 10) {// 商机有效期(到期日期)
						sb.append(" and c.oppor_due_date = to_date('"
								+ json.get(key).toString().substring(0, 10)
								+ "','yyyy-mm-dd')");
					} else if (key.equals("OPPOR_START_DATE")
							&& json.get(key) != null
							&& json.get(key).toString().length() >= 10) {// 商机开始日期
						sb.append(" and c.oppor_start_date = to_date('"
								+ json.get(key).toString().substring(0, 10)
								+ "','yyyy-mm-dd')");
					} else if (key.equals("OPPOR_END_DATE")
							&& json.get(key) != null
							&& json.get(key).toString().length() >= 10) {// 商机完成日期
						sb.append(" and c.oppor_end_date = to_date('"
								+ json.get(key).toString().substring(0, 10)
								+ "','yyyy-mm-dd')");
					} else if (key.equals("CREATE_DATE_TIME")
							&& json.get(key) != null
							&& json.get(key).toString().length() >= 10) {// 创建日期
						sb.append(" and c.create_date_time = to_date('"
								+ json.get(key).toString().substring(0, 10)
								+ "','yyyy-mm-dd')");
					} else if (key.equals("EXECUTE_ORG_ID")
							&& json.get(key) != null) {// 执行机构
						sb.append(" and c.execute_org_id in ("
								+ this.addSingleQuote((String) json.get(key))
								+ ")");
					} else if (key.equals("EXECUTE_USER_ID")
							&& json.get(key) != null) {// 执行人
						sb.append(" and c.execute_user_id in ("
								+ this.addSingleQuote((String) json.get(key))
								+ ")");
					} else if (key.equals("CUST_ID") && json.get(key) != null) {// 客户
						sb.append(" and c.cust_id in ("
								+ this.addSingleQuote((String) json.get(key))
								+ ")");
					}
				}
			}
			sb.append("  ) b on a.f_code = b.oppor_stage");
			sb.append(" where a.f_lookup_id = 'BUSI_CHANCE_STAGE'");
			// 设置分组
			sb.append(" group by a.f_code, a.f_value");
			// 设置排序
			sb.append(" order by a.f_code asc");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	// 给传入的字符串按规则增加单引号
	// 规则：将"a,b,c"转换成"'a','b','c'"
	private String addSingleQuote(String str) {
		String rs = "";
		String[] args = null;
		if (str != null && !"".equals(str)) {
			args = str.split(",");
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					rs += ("'" + args[i] + "'");
					if (i < args.length - 1) {
						rs += ",";
					}
				}
			}
		}
		return rs;
	}

}