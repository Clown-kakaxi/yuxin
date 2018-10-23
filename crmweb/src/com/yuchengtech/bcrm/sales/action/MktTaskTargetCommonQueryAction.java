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
 * @描述：营销任务指标放大镜查询Action
 * @author wzy
 * @date:2013-03-04
 */
@ParentPackage("json-default")
@Action(value = "/mktTaskTargetCommonQueryAction", results = { @Result(name = "success", type = "json"), })
public class MktTaskTargetCommonQueryAction extends CommonAction {

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

			sb.append("select t.target_code,");
			sb.append("t.target_name,");
			sb.append("t.target_type,");
			sb.append("t.target_cycle");
			sb.append("  from ocrm_f_mm_target t");

			sb.append(" where 1 = 1");

			// 构造SQL的查询条件(从前台页面传入的查询条件参数)
			for (String key : this.getJson().keySet()) {
				if (null != this.getJson().get(key)
						&& !this.getJson().get(key).equals("")) {
					if (key.equals("TARGET_TYPE")) {// 营销任务指标类型
						sb.append(" and t.target_type ='"
								+ this.getJson().get(key) + "'");
					} else if (key.equals("TARGET_NAME")) {// 营销任务指标名称
						sb.append(" and t.target_name like'%"
								+ this.getJson().get(key) + "%'");
					}
				}
			}

			// 数据字典设置
			addOracleLookup("TARGET_TYPE", "INDEX_TYP");// 营销任务指标类型

			// 设置排序
			setPrimaryKey("t.target_name asc");

			// 给SQL对象赋值(完整的查询SQL)
			SQL = sb.toString();

			// 数据源
			datasource = dsOracle;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}