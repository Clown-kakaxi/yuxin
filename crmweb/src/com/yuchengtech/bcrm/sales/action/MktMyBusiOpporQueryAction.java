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
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @描述：我的商机管理查询Action
 * @author wzy
 * @date:2013-02-18
 */
@ParentPackage("json-default")
@Action(value = "/mktMyBusiOpporQueryAction", results = { @Result(name = "success", type = "json"), })
public class MktMyBusiOpporQueryAction extends CommonAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource dsOracle;

	// 覆盖父类的prepare方法：构造查询列表数据的方法逻辑
	public void prepare() {
		ActionContext ctx = null;
		AuthUser auth = null;
		StringBuilder sb = null;
		try {
			ctx = ActionContext.getContext();
			auth = (AuthUser) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			sb = new StringBuilder("");

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

			// 权限判断：查询自己的商机
			// 执行人是自己
			sb.append(" (t.execute_user_id = '" + auth.getUserId() + "'");
			sb.append(" or ");
			// 创建人是自己
			sb.append(" t.creater_id = '" + auth.getUserId() + "')");

			// 构造SQL的查询条件(从前台页面传入的查询条件参数)
			for (String key : this.getJson().keySet()) {
				if (null != this.getJson().get(key)
						&& !this.getJson().get(key).equals("")) {
					if (key.equals("OPPOR_TYPE")) {// 商机类型
						sb.append(" and t.oppor_type ='"
								+ this.getJson().get(key) + "'");
					} else if (key.equals("OPPOR_NAME")) {// 商机名称
						sb.append(" and t.oppor_name like'%"
								+ this.getJson().get(key) + "%'");
					} else if (key.equals("OPPOR_STAT")) {// 商机状态
						sb.append(" and t.oppor_stat ='"
								+ this.getJson().get(key) + "'");
					} else if (key.equals("OPPOR_STAGE")) {// 商机阶段
						sb.append(" and t.oppor_stage ='"
								+ this.getJson().get(key) + "'");
					} else if (key.equals("OPPOR_SOURCE")) {// 商机来源
						sb.append(" and t.oppor_source ='"
								+ this.getJson().get(key) + "'");
					} else if (key.equals("PROD_ID")) {// 产品
						sb.append(" and t.prod_id ='" + this.getJson().get(key)
								+ "'");
					} else if (key.equals("REACH_PROB")) {// 达成概率
						sb.append(" and t.reach_prob = '"
								+ this.getJson().get(key) + "'");
					} else if (key.equals("OPPOR_DUE_DATE")
							&& this.getJson().get(key) != null) {// 商机有效期
						sb.append(" and t.oppor_due_date = to_date('"
								+ this.getJson().get(key).toString()
										.substring(0, 10) + "','yyyy-mm-dd')");
					} else if (key.equals("OPPOR_START_DATE")
							&& this.getJson().get(key) != null) {// 商机开始日期
						sb.append(" and t.oppor_start_date = to_date('"
								+ this.getJson().get(key).toString()
										.substring(0, 10) + "','yyyy-mm-dd')");
					} else if (key.equals("OPPOR_END_DATE")
							&& this.getJson().get(key) != null) {// 商机完成日期
						sb.append(" and t.oppor_end_date = to_date('"
								+ this.getJson().get(key).toString()
										.substring(0, 10) + "','yyyy-mm-dd')");
					}
				}
			}

			// 数据字典设置
			addOracleLookup("OPPOR_STAT", "BUSI_CHANCE_STATUS");// 商机状态
			addOracleLookup("OPPOR_STAGE", "BUSI_CHANCE_STAGE");// 商机阶段
			addOracleLookup("OPPOR_SOURCE", "BUSI_CHANCE_SOURCE");// 商机来源
			addOracleLookup("OPPOR_TYPE", "BUSI_CHANCE_TYPE");// 商机类型
			addOracleLookup("CUST_TYPE", "CUSTOMER_STATUS");// 客户类型：0-潜在客户，1-正式客户
			addOracleLookup("CUST_CATEGORY", "XD000080");// 客户类别：2-对私客户，1-对公客户

			// 设置排序
			setPrimaryKey("t.create_date_time desc,t.oppor_name asc ");// 排序

			// 给SQL对象赋值(完整的查询SQL)
			SQL = sb.toString();

			// 数据源
			datasource = dsOracle;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
