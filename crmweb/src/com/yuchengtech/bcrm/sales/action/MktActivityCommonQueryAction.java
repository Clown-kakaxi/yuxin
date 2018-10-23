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
 * @描述：营销活动放大镜查询Action
 * @author wzy
 * @date:2013-03-04
 */
@ParentPackage("json-default")
@Action(value = "/mktActivityCommonQueryAction", results = { @Result(name = "success", type = "json"), })
public class MktActivityCommonQueryAction extends CommonAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource dsOracle;

	// 覆盖父类的prepare方法：构造查询列表数据的方法逻辑
	public void prepare() {
		ActionContext ctx = null;
		StringBuilder sb = null;
		try {
			ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			sb = new StringBuilder("");
			String mktAppState = request.getParameter("mktAppState");//营销活动审批状态
			sb.append("select t.mkt_acti_id,");
			sb.append("       t.MKT_ACTI_NAME,");
			sb.append("       t.MKT_ACTI_TYPE,");
			sb.append("       t.PSTART_DATE,");
			sb.append("       t.PEND_DATE,");
			sb.append("       t.ASTART_DATE,");
			sb.append("       t.AEND_DATE,");
			sb.append("       t.MKT_ACTI_STAT");
			sb.append("  from ocrm_f_mk_mkt_activity t");
			sb.append(" where 1=1 ");
         
			if(mktAppState!=null && !mktAppState.isEmpty()){
				sb.append(" and t.MKT_APP_STATE = '3'");// 营销活动审批状态“审批通过-3”mkt_app_state
			}
			// 构造SQL的查询条件(从前台页面传入的查询条件参数)
			for (String key : this.getJson().keySet()) {
				if (null != this.getJson().get(key)
						&& !this.getJson().get(key).equals("")) {
					if (key.equals("MKT_ACTI_TYPE")) {// 营销活动类型
						sb.append(" and t.mkt_acti_type ='"
								+ this.getJson().get(key) + "'");
					} else if (key.equals("MKT_ACTI_NAME")) {// 营销活动名称
						sb.append(" and t.MKT_ACTI_NAME like'%"
								+ this.getJson().get(key) + "%'");
					}
				}
			}

			// 数据字典设置
			addOracleLookup("MKT_ACTI_TYPE", "ACTI_TYPE");// 营销活动类型

			// 设置排序
			setPrimaryKey("t.MKT_ACTI_NAME asc");

			// 给SQL对象赋值(完整的查询SQL)
			SQL = sb.toString();

			// 数据源
			datasource = dsOracle;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}