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
 * @描述：营销管理->商机管理->商机漏斗：查询结果超链接查询商机列表Action
 * @author wzy
 * @date:2013-03-27
 */
@ParentPackage("json-default")
@Action(value = "/mktBusiOpporListSuperLinkQueryAction", results = { @Result(name = "success", type = "json"), })
public class MktBusiOpporListSuperLinkQueryAction extends CommonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String opporStage = request.getParameter("opporStage");
		StringBuilder sb = new StringBuilder("");
		sb.append("select t.oppor_id,");
		sb.append(" t.oppor_name,");
		sb.append(" t.oppor_stat,");
		sb.append(" t.oppor_stage,");
		sb.append(" t.oppor_source,");
		sb.append(" t.oppor_type,");
		sb.append(" t.oppor_start_date,");
		sb.append(" t.oppor_end_date,");
		sb.append(" t.oppor_due_date,");
		sb.append(" t.mkt_target_id,");
		sb.append(" t.mkt_target_name,");
		sb.append(" t.mkt_activ_id,");
		sb.append(" t.mkt_activ_name,");
		sb.append(" t.oppor_content,");
		sb.append(" t.cust_id,");
		sb.append(" t.cust_name,");
		sb.append(" t.cust_contact_name,");
		sb.append(" t.cust_type,");
		sb.append(" t.cust_category,");
		sb.append(" t.plan_amount,");
		sb.append(" t.reach_prob,");
		sb.append(" t.plan_cost,");
		sb.append(" t.prod_id,");
		sb.append(" t.prod_name,");
		sb.append(" t.creater_id,");
		sb.append(" t.creater_name,");
		sb.append(" t.create_org_id,");
		sb.append(" t.create_org_name,");
		sb.append(" to_char(t.create_date_time, 'yyyy-mm-dd hh24:mi:ss') as create_date_time,");
		sb.append(" t.update_user_id,");
		sb.append(" t.update_user_name,");
		sb.append(" t.update_org_id,");
		sb.append(" t.update_org_name,");
		sb.append(" to_char(t.update_date_time, 'yyyy-mm-dd hh24:mi:ss') as update_date_time,");
		sb.append(" t.execute_user_id,");
		sb.append(" t.execute_user_name,");
		sb.append(" t.execute_org_id,");
		sb.append(" t.execute_org_name,");
		sb.append(" t.assign_ogr_id,");
		sb.append(" t.assign_org_name,");
		sb.append(" t.claim_user_id,");
		sb.append(" t.claim_user_name,");
		sb.append(" t.claim_org_id,");
		sb.append(" t.reach_amount,");
		sb.append(" t.memo,");
		sb.append(" t.claim_org_name,");
		sb.append(" a.mgr_name,");
		sb.append(" b.institution_name");
		sb.append("  from ocrm_f_mm_mkt_busi_oppor t");
		sb.append("  left outer join (select s1.cust_id, s1.mgr_name");
		sb.append("    from ocrm_f_ci_belong_custmgr s1");
		sb.append("   where s1.main_type = '1') a on t.cust_id = a.cust_id");
		sb.append("  left outer join (select s2.cust_id, s2.institution_name");
		sb.append("    from ocrm_f_ci_belong_org s2");
		sb.append("   where s2.main_type = '1') b on t.cust_id = b.cust_id");
		sb.append(" where ");
		// 不能查询“7-失败关闭”状态的数据
		sb.append(" t.oppor_stat != '7'");
		sb.append(" and t.oppor_stage = '" + opporStage + "'");
		// 前台查询条件
		// 构造SQL的查询条件(从前台页面传入的查询条件参数)
		for (String key : json.keySet()) {
			if (null != json.get(key) && !json.get(key).equals("")) {
				if (key.equals("OPPOR_DUE_DATE") && json.get(key) != null
						&& json.get(key).toString().length() >= 10) {// 商机有效期(到期日期)
					sb.append(" and t.oppor_due_date = to_date('"
							+ json.get(key).toString().substring(0, 10)
							+ "','yyyy-mm-dd')");
				} else if (key.equals("OPPOR_START_DATE")
						&& json.get(key) != null
						&& json.get(key).toString().length() >= 10) {// 商机开始日期
					sb.append(" and t.oppor_start_date = to_date('"
							+ json.get(key).toString().substring(0, 10)
							+ "','yyyy-mm-dd')");
				} else if (key.equals("OPPOR_END_DATE")
						&& json.get(key) != null
						&& json.get(key).toString().length() >= 10) {// 商机完成日期
					sb.append(" and t.oppor_end_date = to_date('"
							+ json.get(key).toString().substring(0, 10)
							+ "','yyyy-mm-dd')");
				} else if (key.equals("CREATE_DATE_TIME")
						&& json.get(key) != null
						&& json.get(key).toString().length() >= 10) {// 创建日期
					sb.append(" and t.create_date_time = to_date('"
							+ json.get(key).toString().substring(0, 10)
							+ "','yyyy-mm-dd')");
				} else if (key.equals("EXEC_ORG_ID") && json.get(key) != null) {// 执行机构
					sb.append(" and t.execute_org_id in ("
							+ this.addSingleQuote((String) json.get(key)) + ")");
				} else if (key.equals("EXEC_USER_ID") && json.get(key) != null) {// 执行人
					sb.append(" and t.execute_user_id in ("
							+ this.addSingleQuote((String) json.get(key)) + ")");
				} else if (key.equals("CUST_ID") && json.get(key) != null) {// 客户
					sb.append(" and t.cust_id in ("
							+ this.addSingleQuote((String) json.get(key)) + ")");
				}
			}
		}

		// for (String key : this.getJson().keySet()) {
		// if (null != this.getJson().get(key)
		// && !this.getJson().get(key).equals("")) {
		// if (key.equals("OPPOR_TYPE")) {// 商机类型
		// sb.append(" and t." + key + "= '" + this.getJson().get(key)
		// + "'");
		// } else if (key.equals("OPPOR_SOURCE")) {// 商机来源
		// sb.append(" and t." + key + "= '" + this.getJson().get(key)
		// + "'");
		// } else if (key.equals("OPPOR_END_DATE")) {// 商机结束日期
		// sb.append(" and t." + key + "= to_date('"
		// + this.getJson().get(key) + "','yyyy-MM-dd')");
		// } else if (key.equals("OPPOR_NAME")) {// 商机名称
		// sb.append(" and t." + key + " like '%"
		// + this.getJson().get(key) + "%'");
		// } else if (key.equals("PROD_NAME")) {// 商机产品名称
		// sb.append(" and t." + key + "= '" + this.getJson().get(key)
		// + "'");
		// } else if (key.equals("OPPOR_DUE_DATE")) {// 商机有效期
		// sb.append(" and t." + key + "= to_date('"
		// + this.getJson().get(key) + "','yyyy-MM-dd')");
		// } else if (key.equals("OPPOR_STAT")) {// 商机状态
		// sb.append(" and t." + key + "= '" + this.getJson().get(key)
		// + "'");
		// } else if (key.equals("REACH_PROB")) {// 达成概率
		// sb.append(" and t." + key + "= '" + this.getJson().get(key)
		// + "'");
		// } else if (key.equals("OPPOR_STAGE")) {// 商机阶段
		// sb.append(" and t." + key + "= '" + this.getJson().get(key)
		// + "'");
		// } else if (key.equals("OPPOR_START_DATE")) {// 商机开始日期
		// sb.append(" and t." + key + "= to_date('"
		// + this.getJson().get(key) + "','yyyy-MM-dd')");
		// }
		// }
		// }
		setPrimaryKey("t.create_date_time desc,t.oppor_name asc ");// 排序
		// 设置数据字典数据
		addOracleLookup("OPPOR_STAGE", "BUSI_CHANCE_STAGE");// 商机阶段
		addOracleLookup("REACH_PROB", "BUSI_CHANCE_PROB");// 达成概率
		addOracleLookup("OPPOR_STAT", "BUSI_CHANCE_STATUS");// 商机状态
		addOracleLookup("OPPOR_SOURCE", "BUSI_CHANCE_SOURCE");// 商机来源
		addOracleLookup("OPPOR_TYPE", "BUSI_CHANCE_TYPE");// 商机类型
		addOracleLookup("CUST_TYPE", "CUSTOMER_STATUS");// 客户类型：0-潜在客户，1-正式客户
		addOracleLookup("CUST_CATEGORY", "XD000080");// 客户类别：2-对私客户，1-对公客户
		SQL = sb.toString();
		datasource = ds;
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