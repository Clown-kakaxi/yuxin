package com.yuchengtech.bcrm.sales.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSON;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTask;
import com.yuchengtech.bcrm.sales.service.MarketTaskAssignService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @描述：营销管理->营销任务管理->营销任务下达：功能操作Action
 * @author wzy
 * @date:2013-06-20
 */
@SuppressWarnings("serial")
@Action("/marketTaskAssignAction")
public class MarketTaskAssignAction extends CommonAction {

	@Autowired
	private MarketTaskAssignService marketTaskAssignService;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	// 当前用户
	private AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();

	@Autowired
	public void init() {
		model = new OcrmFMmTask();
		setCommonService(marketTaskAssignService);
	}

	// 查询营销任务
	public void prepare() {
		this.setRequest();
		String querySign = request.getParameter("querySign");
		if ("queryHadTargetData".equals(querySign)) {
			this.queryHadTargetData();
		} else if ("queryHadTargetDataDetail".equals(querySign)) {
			this.queryHadTargetDataDetail();
		} else {
			StringBuilder sb = new StringBuilder("");
			sb.append("select p.*, t.task_name as task_parent_name\n");
			sb.append("  from ocrm_f_mm_task p \n");
			sb.append(" left outer join ocrm_f_mm_task t\n");
			sb.append(" on p.task_parent_id = t.task_id");
			sb.append(" where p.task_parent_id is null\n");
			sb.append(" and p.create_org_id in (" + this.getSonOrgIds(auth)
					+ ")");// 权限控制（查询自己所在机构及子机构创建的数据）
			for (String key : this.getJson().keySet()) {
				if (null != this.getJson().get(key)
						&& !this.getJson().get(key).equals("")) {
					if (key.equals("TASK_NAME")) {// 任务名称
						sb.append(" and p.task_name like '%"
								+ this.getJson().get(key) + "%'");
					} else if (key.equals("TASK_STAT")) {// 任务状态
						sb.append(" and p.task_stat = '"
								+ this.getJson().get(key) + "'");
					} else if (key.equals("DIST_USER")) {
						sb.append(" and p.dist_user = '"
								+ this.getJson().get(key) + "'");
					}
				}
			}
			SQL = sb.toString();
			setPrimaryKey("p.task_id desc");
			addOracleLookup("TASK_STAT", "MTASK_STAT");
			datasource = ds;
		}
	}

	// 查询当前用户所在机构及子机构（ID的集合）
	@SuppressWarnings("rawtypes")
	private String getSonOrgIds(AuthUser auth) {
		String result = "";
		Map map = null;
		if (auth != null && auth.getAuthOrgList1() != null
				&& auth.getAuthOrgList1().size() > 0) {
			for (int i = 0; i < auth.getAuthOrgList1().size(); i++) {
				map = (Map) auth.getAuthOrgList1().get(i);
				if (map != null && map.get("UNITID") != null) {
					result += ("'" + map.get("UNITID") + "'");
					if (i != auth.getAuthOrgList1().size() - 1) {
						result += ",";
					}
				}
			}
		}
		return result;
	}

	// 查询任务的指标信息（列表）
	private void queryHadTargetData() {
		String taskId = request.getParameter("taskId");
		String queryType = request.getParameter("queryType");
		StringBuilder sb = marketTaskAssignService.queryHadTargetData(taskId,
				queryType);
		setPrimaryKey("b.task_id asc");
		SQL = sb.toString();
		datasource = ds;
	}

	// 查询任务的指标信息（将指标分组后的列表）
	private void queryHadTargetDataDetail() {
		String taskId = request.getParameter("taskId");
		StringBuilder sb = marketTaskAssignService
				.queryHadTargetDataDetail(taskId);
		setPrimaryKey("b.task_id asc");
		SQL = sb.toString();
		addOracleLookup("TASK_STAT", "MTASK_STAT");
		datasource = ds;
	}

	// 保存新增的营销任务
	public void saveTask() {
		this.setRequest();
		OcrmFMmTask ocrmFMmTask = (OcrmFMmTask) model;
		String targetIds = request.getParameter("targetIds");// 指标ID集合
		String targetValueData = request.getParameter("targetValueData");// 填写的指标值
		marketTaskAssignService.saveTask(ocrmFMmTask, auth, targetValueData,
				targetIds);
	}

	// 保存修改的营销任务
	public void updateTask() {
		this.setRequest();
		OcrmFMmTask ocrmFMmTask = (OcrmFMmTask) model;
		String targetIds = request.getParameter("targetIds");// 指标ID集合
		String targetValueData = request.getParameter("targetValueData");// 填写的指标值
		marketTaskAssignService.updateTask(ocrmFMmTask, auth, targetValueData,
				targetIds);
	}

	// 删除任务
	public void deleteTask() {
		this.setRequest();
		marketTaskAssignService.deleteTask(request);
	}

	// 查询任务的指标信息列表的表头
	@SuppressWarnings({ "rawtypes", "static-access" })
	public void getTargetDataHeader() {
		this.setRequest();
		String taskId = request.getParameter("taskId");
		String queryType = request.getParameter("queryType");
		List targetList = null;
		String header = "";
		Object[] objs = null;
		HttpServletResponse response = null;
		targetList = marketTaskAssignService.queryTarget(taskId, queryType);
		if (targetList != null && targetList.size() > 0) {
			for (int i = 0; i < targetList.size(); i++) {
				objs = (Object[]) targetList.get(i);
				if (objs != null && objs.length == 2) {
					header += objs[1];
					if (i != targetList.size() - 1) {
						header += ",";
					}
				}
			}
		}
		try {
			response = (HttpServletResponse) ActionContext.getContext()
					.getContext().get(ServletActionContext.HTTP_RESPONSE);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(header);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 营销任务下达
	public void taskAssignTransmit() {
		this.setRequest();
		String cbid = request.getParameter("cbid");
		marketTaskAssignService.taskAssignTransmit(auth, cbid);
	}

	// 营销任务指标值修改（指标调整）
	public void adjustTaskTargetData() {
		this.setRequest();
		String taskId = request.getParameter("taskId");// 任务ID
		String targetDataValue = request.getParameter("targetDataValue");// 填写的指标值
		marketTaskAssignService.adjustTaskTargetData(auth, targetDataValue,
				taskId);
	}

	// 判断某个任务的子任务中是否有“未关闭”状态的，有：返回未关闭任务的名称，没有：返回空字符串
	public HttpHeaders isExistSonTask() {
		this.setRequest();
		String taskId = request.getParameter("taskId");// 任务ID
		String noComplete = marketTaskAssignService.isExistSonTask(taskId);
		if (this.json != null) {
			this.json.clear();
		} else {
			this.json = new HashMap<String, Object>();
		}
		this.json.put("noCompleteTaskNames", noComplete);
		return new DefaultHttpHeaders("success").disableCaching();
	}

	// 关闭营销任务
	public void closeTask() {
		this.setRequest();
		String taskId = request.getParameter("taskId");// 营销任务ID
		marketTaskAssignService.closeTask(taskId,auth);
	}

	// 查询某营销任务对应的指标和执行对象
	@SuppressWarnings("static-access")
	public void queryTaskOperAndTarget() {
		this.setRequest();
		String taskId = request.getParameter("taskId");
		String queryType = request.getParameter("queryType");
		HttpServletResponse response = null;
		JSON json = marketTaskAssignService.queryTaskOperAndTarget(taskId,queryType);
		try {
			response = (HttpServletResponse) ActionContext.getContext()
					.getContext().get(ServletActionContext.HTTP_RESPONSE);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 设置request对象
	private void setRequest() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
	}

}
