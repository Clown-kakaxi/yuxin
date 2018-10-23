package com.yuchengtech.bcrm.callReport.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.callReport.service.CallReportFunnelQueryService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @描述：销售漏斗查询Action
 * @author wzy
 * @date:2013-03-25
 */
@ParentPackage("json-default")
@Action(value = "/callReportFunnelQueryAction", results = { @Result(name = "success", type = "json"), })
public class CallReportFunnelQueryAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CallReportFunnelQueryService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource dsOracle;
	// 覆盖父类的prepare方法：构造查询列表数据的方法逻辑
	public void prepare() {
		try {
			//==============================begin ======================================
			String beginString = "";
			String endString = "";
			for (String key : json.keySet()) {
				if (null != json.get(key) && !json.get(key).equals("")) {
					if (key.equals("OPPOR_START_DATE")
							&& json.get(key) != null
							&& json.get(key).toString().length() >= 10) {// 商机开始日期
						beginString = json.get(key).toString().substring(0, 10);
					} else if (key.equals("OPPOR_END_DATE")
							&& json.get(key) != null
							&& json.get(key).toString().length() >= 10) {// 商机完成日期
						endString = json.get(key).toString().substring(0, 10);
					}
				}
			}
			Map map = service.getDate(beginString,endString);
			//==========================end ============================================
			SQL = this.makeQuerySql(map);
			datasource = dsOracle;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询销售漏斗图形化展示的数据
	public void getQueryResultJsonData() throws Exception {
		String sql = null;
		String result = null;
		ActionContext ctx = null;
		HttpServletResponse response = null;
		sql = this.makeQuerySql(null);
		result = service.getQueryResultJsonData(sql);
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
	private String makeQuerySql(Map map) throws Exception {
		Map m1 = new HashMap();
		Map m2 = new HashMap();
		Map m3 = new HashMap();
		Map m4 = new HashMap();
		Map m5 = new HashMap();
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
			
			if(map!=null){
				m1 = (Map)map.get("sb1");
				m2 = (Map)map.get("sb2");
				m3 = (Map)map.get("sb3");
				m4 = (Map)map.get("sb4");
				m5 = (Map)map.get("sb5");
				
			}
			sb.append("       round((decode(a.f_code, '1', ").append(m1.get("ct1")).append(",");
			sb.append("       '2', ").append(m2.get("ct2")).append(",");
			sb.append("       '3', ").append(m3.get("ct3")).append(",");
			sb.append("       '4', ").append(m4.get("ct4")).append(",");
			sb.append("       '5', ").append(m5.get("ct5")).append(")/ ( decode(count(b.oppor_id),0,1,count(b.oppor_id))) )*100,2) as zhl,");
			
			sb.append("       round((decode(a.f_code, '1', ").append(m1.get("date1")).append(",");
			sb.append("       '2', ").append(m2.get("date2")).append(",");
			sb.append("       '3', ").append(m3.get("date3")).append(",");
			sb.append("       '4', ").append(m4.get("date4")).append(",");
			sb.append("       '5', ").append(m5.get("date5")).append(")/ ( decode(count(b.oppor_id),0,1,count(b.oppor_id))) ),2) as sj,");
			
			
			sb.append("       count(b.oppor_id) as count_number,");
			sb.append("       decode(sum(b.plan_amount), null, 0, sum(b.plan_amount)) as count_amount,");
			sb.append("       ((decode(a.f_code, '1', 0.1, '2', 0.25, '3', 0.5, '4', 0.75, '5', 1)) *");
			sb.append("       decode(sum(b.plan_amount), null, 0, sum(b.plan_amount))) as count_weight ");
			sb.append("  from ocrm_sys_lookup_item a");
			sb.append("  left outer join (select c.id as oppor_id, c.amount as plan_amount , c.sales_stage as oppor_stage ");
			sb.append("   from ocrm_f_se_callreport_busi c   left join OCRM_F_SE_CALLREPORT d on c.call_id = d.call_id left join OCRM_F_CI_BELONG_CUSTMGR e on d.cust_id=e.cust_id");
			sb.append("   where c.sales_stage != '6'");
			// 构造SQL的查询条件(从前台页面传入的查询条件参数)
			for (String key : json.keySet()) {
				if (null != json.get(key) && !json.get(key).equals("")) {
					if(key.equals("ORG_NAME")&& json.get(key) != null){
						sb.append(" and e.institution='"+json.get(key).toString()+"' ");
					
					}else if(key.equals("MGR_ID")&& json.get(key) != null){
						sb.append("and  e.mgr_id='"+json.get(key).toString()+"' ");
						
					}/*else if (key.equals("PRODUCT_ID")&& json.get(key) != null){
						sb.append(" and c.PRODUCT_ID= '"+json.get(key).toString()+"' ");
						
					}*/
					else if (key.equals("OPPOR_START_DATE")
							&& json.get(key) != null
							&& json.get(key).toString().length() >= 10) {// 商机开始日期
						sb.append(" and c.last_update_tm >= to_date('"
								+ json.get(key).toString().substring(0, 10)
								+ "','yyyy-mm-dd')");
					} else if (key.equals("OPPOR_END_DATE")
							&& json.get(key) != null
							&& json.get(key).toString().length() >= 10) {// 商机完成日期
					sb.append(" and c.last_update_tm <= to_date('"
								+ json.get(key).toString().substring(0, 10)
							+ "','yyyy-mm-dd')");
					} else if(key.equals("PROD_NAME") && json.get(key) != null){
						sb.append(" and c.PRODUCT_ID = '"+json.get(key).toString()+"' ");
					}
				}
				
			}
			sb.append("  ) b on a.f_code = b.oppor_stage");
			sb.append(" where a.f_lookup_id = 'CALLREPORT_SAVES_STAGE'  and a.f_code != '6' ");
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
	public void assetXml() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		AuthUser authUser = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		try {
			Map map3 = new HashMap();
			StringBuffer sb = new StringBuffer();
			sb.append(" select o1.F_VALUE, o2.AMOUNT_VALUE");
			sb.append(" from OCRM_SYS_LOOKUP_ITEM o1");
			sb.append(" left outer join (select a.fail_reason,count(a.id) as AMOUNT_VALUE");
			sb.append(" from ocrm_f_se_callreport_busi a");
			sb.append(" group by a.fail_reason) o2 on o1.F_CODE = o2.fail_reason");
			sb.append(" where o1.F_LOOKUP_ID = 'CALLREPORT_FAIL_REASON'");
			sb.append(" order by o1.f_code");
		
			this.json=new QueryHelper(sb.toString(), dsOracle.getConnection()).getJSON();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}