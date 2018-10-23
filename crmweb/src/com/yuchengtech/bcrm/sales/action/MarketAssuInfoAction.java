package com.yuchengtech.bcrm.sales.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTask;
import com.yuchengtech.bcrm.sales.service.MarketAssuInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.JPAAnnotationMetadataUtil;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

/**
 * 营销任务管理功能模块Action
 * 
 * @author sujm
 * @since 2013-04-22
 */
@SuppressWarnings("serial")
@Action("/marketassuinfo")
public class MarketAssuInfoAction extends CommonAction {

	@Autowired
	private MarketAssuInfoService marketAssuInfoService;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	// 当前用户
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();

	@Autowired
	public void init() {
		model = new OcrmFMmTask();
		setCommonService(marketAssuInfoService);
	}

	// 营销任务的新增、修改、删除功能
	public DefaultHttpHeaders create() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		if (request.getParameter("operate").equals("add")) {
			// 新增,修改
			OcrmFMmTask ocrmFMmTask = (OcrmFMmTask) model;
			marketAssuInfoService.save(ocrmFMmTask);
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
			auth.setPid(metadataUtil.getId(model).toString());// 获取新增操作产生的最新记录的ID
		} else if (request.getParameter("operate").equals("delete")) {
			// 删除
			marketAssuInfoService.delTask(request);
		}
		return new DefaultHttpHeaders("success")
				.setLocationId(((OcrmFMmTask) model).getTaskId());
	}

	// 查询营销任务
	public void prepare() {
		StringBuilder sb = new StringBuilder(
				"select p.*, t.task_name as task_parent_name\n"
						+ "  from ocrm_f_mm_task p left join  ocrm_f_mm_task t\n"
						+ " on p.task_parent_id = t.task_id where 1=1 \n");
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("TASK_NAME")) {// 任务名称
					sb.append(" and p.task_name like '%"
							+ this.getJson().get(key) + "%'");
				} else if (key.equals("TASK_STAT")) {// 任务状态
					sb.append(" and p.task_stat = '" + this.getJson().get(key)
							+ "'");
				} else if (key.equals("DIST_USER")) {
					sb.append(" and p.dist_user = '" + this.getJson().get(key)
							+ "'");
				}
			}
		}
		SQL = sb.toString();
		setPrimaryKey("p.task_id desc");
		addOracleLookup("TASK_STAT", "MTASK_STAT");
		datasource = ds;

	}

	// 判断是否存在未关闭的子任务
	public HttpHeaders ifExitSunTask() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		// 任务编号
		String taskId = request.getParameter("taskId");
		String ifExit = "no";
		String name = "";

		String sql = " select task_name from OCRM_F_MM_TASK where TASK_PARENT_ID='"
				+ taskId + "' and TASK_STAT <>'4'";

		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				name += rs.getString("task_name") + ",";
			}
			if (name.length() != 0) {
				ifExit = "yes";
				name = name.substring(0, name.length() - 1);
			}
		} catch (SQLException e) {
			throw new BizException(1, 2, "1002", e.getMessage());
		} finally {
			JdbcUtil.close(rs, stmt, conn);
		}

		if (this.json != null)
			this.json.clear();
		else
			this.json = new HashMap<String, Object>();

		this.json.put("ifExit", ifExit);
		this.json.put("names", name);

		return new DefaultHttpHeaders("success").disableCaching();
	}
}