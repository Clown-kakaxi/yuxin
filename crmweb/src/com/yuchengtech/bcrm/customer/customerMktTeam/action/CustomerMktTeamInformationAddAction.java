package com.yuchengtech.bcrm.customer.customerMktTeam.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.service.OrgSearchService;
import com.yuchengtech.bcrm.customer.customerMktTeam.model.OcrmFCmMktTeam;
import com.yuchengtech.bcrm.customer.customerMktTeam.service.CustomerMktTeamInformationAddService;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFCiMktApprovedC;
import com.yuchengtech.bcrm.system.model.AdminAuthAccount;
import com.yuchengtech.bcrm.system.model.AdminAuthOrg;
import com.yuchengtech.bcrm.workplat.service.NoticeService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.model.WorkingplatformNotice;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.bob.vo.WorkingplatformNoticeVo;

/**
 * @description 客户经理团队管理查询界面数据来源
 * @author xiebz
 * @date 2014-07-02
 */
@Action("/customerMktTeamInformationAdd")
public class CustomerMktTeamInformationAddAction extends CommonAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Autowired
	private CustomerMktTeamInformationAddService service;

	@Autowired
	OrgSearchService oss;

	@Autowired
	NoticeService noticeService;

	@Autowired
	public void init() {
		model = new OcrmFCmMktTeam();
		setCommonService(service);
	}

	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String userId = auth.getUserId();
		String orgId = auth.getUnitId();
		List orgsPath = new ArrayList();

		try {
			orgsPath = (List) oss.searchPathInOrgTree(orgId).get("data");
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer c = new StringBuffer("");
		for (Object oId : orgsPath) {
			Map uniMap = (Map) oId;
			String[] paths = ((String) uniMap.get("UNITSEQ")).split(",");
			for (String o : paths)
				c.append(",'" + o + "'");
		}
		StringBuilder sb = new StringBuilder(
				"SELECT '' as CUST_MANAGER_NAME, O.ORG_NAME, T.MKT_TEAM_ID,T.CREATE_USER,T.MKT_TEAM_NAME,T.TEAM_NO,T.ORG_ID,T.TEAM_LEADER,T.CREATE_DATE,"+
				" T.TEAM_CUS_NO,T.LEAD_TELEPHONE,T.TEAM_STATUS,T.CREATE_USER_ID,T.CREATE_USER_NAME,T.CREATE_USER_ORG_ID,T.TEAM_LEADER_ID,T.TEAM_TYPE,"+
				" T.LAST_MAINTAIN_TIME,R.TEAM_SCALE"
						+ " FROM OCRM_F_CM_MKT_TEAM T "
						+ " LEFT JOIN ADMIN_AUTH_ORG O ON T.ORG_ID = O.ORG_ID"
						+ " LEFT JOIN (SELECT  T2.MKT_TEAM_ID, COUNT(1) AS TEAM_SCALE FROM OCRM_F_CM_TEAM_CUST_MANAGER T2 GROUP BY T2.MKT_TEAM_ID) R ON R.MKT_TEAM_ID=T.MKT_TEAM_ID "
						+ " WHERE 1=1 " + " and ( t.create_user_id = '"
						+ userId + "'  OR T.TEAM_LEADER_ID = '" + userId + "'"
						+ " or ( t.team_status = '2' and t.org_id in ("
						+ c.toString().substring(1) + ")");
		sb.append(")").append(")");
		String operate = request.getParameter("operate");
		String id = request.getParameter("id");
		if ("temp".equals(operate)) {
			sb.setLength(0);
			boolean flag = checkTeamModify(id);
			// if(flag){//新增审批数据源
			sb.append(" select a.user_name as TEAM_LEADER_name,g.org_name,t.* from  OCRM_F_CM_MKT_TEAM t ");
			// }else{
			// sb.append(" select a.user_name as TEAM_LEADER_name,g.org_name,t.* from  OCRM_F_CM_MKT_TEAM_temp t "
			// );
			// }
			sb.append(" left  join ADMIN_AUTH_ORG g on t.org_id = g.org_id "
					+ " left  join ADMIN_AUTH_ACCOUNT a on a.account_name = t.team_leader_id "
					+ " where t.mkt_team_id = '" + id + "' ");
		}
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				// 按输入团队名称
				if ("MKT_TEAM_NAME".equals(key)) {
					sb.append(" AND T.MKT_TEAM_NAME like '%"
							+ this.getJson().get(key) + "%'");
				} else if ("CUST_MANAGER_NAME".equals(key)) {// 模糊查询 客户经理名称查询
					sb.append(" AND exists (select 1 from OCRM_F_CM_TEAM_CUST_MANAGER m where m.mkt_team_id = t.mkt_team_id and m.CUST_MANAGER_NAME LIKE "
							+ " '%" + this.getJson().get(key) + "%')");
				} else if ("TEAM_TYPE".equals(key)) {// 团队类型
					sb.append(" AND T.TEAM_TYPE = '" + this.getJson().get(key)
							+ "'");
				} else if ("ORG_NAME".equals(key)) {
					sb.append(" AND (T.ORG_ID = '"
							+ this.getJson().get(key)
							+ "' OR T.ORG_ID  IN (SELECT UNITID FROM SYS_UNITS S WHERE UNITNAME LIKE '%"
							+ this.getJson().get(key) + "%') )");
				} else if ("CREATE_DATE".equals(key)) {
					sb.append(" AND T.CREATE_DATE = to_date('"
							+ this.getJson().get(key) + "','yyyy-mm-dd')");
				} else if ("LAST_MAINTAIN_TIME".equals(key)) {
					sb.append(" AND T.LAST_MAINTAIN_TIME = to_date('"
							+ this.getJson().get(key) + "','yyyy-mm-dd')");
				} else if ("TEAM_SCALE".equals(key)) {
					sb.append(" AND T.TEAM_SCALE = '" + this.getJson().get(key)
							+ "'");
				} else if ("TEAM_STATUS".equals(key)) {
					sb.append(" AND T.TEAM_STATUS = '"
							+ this.getJson().get(key) + "'");
				}
			}
		}
		SQL = sb.toString();
		datasource = ds;
		setPrimaryKey("t.CREATE_DATE desc,t.mkt_team_id desc");
		configCondition("t.CREATE_USER_NAME", "like", "CREATE_USER_NAME",
				DataType.String);
		configCondition("t.TEAM_LEADER", "like", "TEAM_LEADER", DataType.String);
		addOracleLookup("TEAM_STATUS", "CUSTMANAGE_TEAM_STATUS");
		addOracleLookup("TEAM_TYPE", "CUSTMANAGER_TEAM_TYPE");
	}

	public void serch() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String mktTeamId = request.getParameter("mktTeamId");
		System.out.println("mktTeamId: " + mktTeamId);
		StringBuilder sb = new StringBuilder(
				"SELECT T.* FROM OCRM_F_CM_TEAM_CUST_MANAGER T where T.MKT_TEAM_ID = ")
				.append(mktTeamId);
		setPrimaryKey("t.ID desc");
		SQL = sb.toString();
		datasource = ds;
	}

	/**
	 * 删除 客户经理 业绩查询 -批量删除
	 */
	public String batchDestroy() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("idStr");
		service.batchRemove(idStr);
		return "success";
	}

	public boolean checkTeamModify(String id) {
		OcrmFCmMktTeam team = (OcrmFCmMktTeam) service.find(Long.valueOf(id));
		if ("1".equals(team.getTeamStatus())) {// 新增审批
			return true;
		}
		return false;
	}

	/***
	 * 提交审批 保存按钮上的 提交审批
	 * 
	 * @throws Exception
	 */
	public void saveTeam() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		OcrmFCmMktTeam marketTeam = (OcrmFCmMktTeam) model;
		String mobilephone = "";
		List<AdminAuthAccount> userAccounts = service.findByJql(
				"select t from AdminAuthAccount t where t.accountName ='"
						+ request.getParameter("teamLeaderId") + "'", null);
		OcrmFCmMktTeam team = (OcrmFCmMktTeam) service.save(marketTeam);
		// service.batchUpdateByName("update OcrmFCmMktTeam t set t.teamStatus = '1' where t.mktTeamId ='"+team.getMktTeamId()+"'  ",
		// null);
		String name = request.getParameter("mktTeamName");
		String instanceid = "CMT_" + auth.getUnitId() + "_"
				+ team.getMktTeamId() + "_"
				+ new SimpleDateFormat("HHmmss").format(new Date());// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "客户经理团队创建_" + name;// 自定义流程名称
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String nextNode = "122_a4";
		List<?> list = auth.getRolesInfo();
		for (Object m : list) {
			Map<?, ?> map = (Map<?, ?>) m;// map自m引自list，ROLE_CODE为键, R000为值
			paramMap.put("role", map.get("ROLE_CODE"));
			if ("R311".equals(map.get("ROLE_CODE"))) {
				nextNode = "122_a4";
				continue;
			} else if ("R202".equals(map.get("ROLE_CODE"))) {
				nextNode = "122_a5";
				continue;
			}
		}
		service.initWorkflowByWfidAndInstanceid("122", jobName, paramMap,
				instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instanceid", instanceid);
		map.put("currNode", "122_a3");
		map.put("nextNode", nextNode);
		this.setJson(map);

	}

	public void deleteTeam() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		OcrmFCmMktTeam marketTeam = (OcrmFCmMktTeam) model;
		marketTeam.setTeamStatus("1");
		service.deleteTeam(marketTeam);
		String name = request.getParameter("mktTeamName");
		String instanceid = "CMT_" + auth.getUnitId() + "_"
				+ marketTeam.getMktTeamId() + "_"
				+ new SimpleDateFormat("HHmmss").format(new Date()) + "_delete";// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		String jobName = "客户经理团队删除_" + name;// 自定义流程名称
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String nextNode = "122_a4";
		List<?> list = auth.getRolesInfo();
		for (Object m : list) {
			Map<?, ?> map = (Map<?, ?>) m;// map自m引自list，ROLE_CODE为键, R000为值
			paramMap.put("role", map.get("ROLE_CODE"));
			if ("R311".equals(map.get("ROLE_CODE"))) {
				nextNode = "122_a4";
				continue;
			} else if ("R202".equals(map.get("ROLE_CODE"))) {
				nextNode = "122_a5";
				continue;
			}
		}
		service.initWorkflowByWfidAndInstanceid("122", jobName, paramMap,
				instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instanceid", instanceid);
		map.put("currNode", "122_a3");
		map.put("nextNode", nextNode);
		this.setJson(map);

	}

	/**
	 * 客户经理团队创建审批流程 tbar 和 修改时的提交审批函数 发起工作流
	 * */
	public void initFlow() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String requestId = request.getParameter("instanceid");
		String flag = request.getParameter("flag");// 新增还是修改，新增为1，修改为0
		String name = request.getParameter("name");
		String instanceid = "";
		String jobName = "";
		if ("0".equals(flag)) {
			service.batchUpdateByName(
					"update OcrmFCmMktTeam set teamStatus = '4'  where mktTeamId = '"
							+ requestId + "'", null);
			instanceid = "CMT_" + requestId + "_"
					+ new SimpleDateFormat("MMddHHmm").format(new Date());
			jobName = "客户经理团队修改_" + name;// 自定义流程名称
		} else {
			service.batchUpdateByName(
					"UPDATE  OcrmFCmMktTeam t SET T.teamStatus = '1'  where t.mktTeamId ='"
							+ requestId + "'", null);
			instanceid = "CMT_" + requestId;// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			jobName = "客户经理团队创建_" + name;// 自定义流程名称
		}
		service.initWorkflowByWfidAndInstanceid("33", jobName, null, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("instanceid", instanceid);
		map.put("currNode", "33_a3");
		map.put("nextNode", "33_a4");
		this.setJson(map);
	}

	// ======================================公告审批流程================================================================
	/**
	 * 公告发起工作流(页面按钮发布流程)
	 * */
	public void initFlowNotice() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String requestId = request.getParameter("instanceid");
		String name = request.getParameter("name");
		String published = request.getParameter("published");
		String instanceid = "NOTICE_" + requestId;// 此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
		List<WorkingplatformNotice> notices = service.findByJql(
				"select n from WorkingplatformNotice n where n.noticeId = '"
						+ requestId + "' ", null);
		if ("pub002".equals(published)) {// 未发布状态 发布 用来 区分在审核页面取值时的数据来源
			instanceid = "NOTICE_" + requestId + "_1" + "_"
					+ new SimpleDateFormat("ddHHmmss").format(new Date());
		} else {// 发布
			instanceid = "NOTICE_" + requestId + "_2" + "_"
					+ new SimpleDateFormat("ddHHmmss").format(new Date());
		}
		String jobName = "公告发布_" + name;// 自定义流程名称
		service.initWorkflowByWfidAndInstanceid("46", jobName, null, instanceid);// 调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等

		String nextNode = "46_a3";

		List list = auth.getRolesInfo();
		for (Object m : list) {
			Map map = (Map) m;// map自m引自list，ROLE_CODE为键, R000为值
			// 个金AO主管，法金TH，支行中的法金业务条线主管-----支行行长
			if ("R309".equals(map.get("ROLE_CODE"))
					|| "R310".equals(map.get("ROLE_CODE"))
					|| "R312".equals(map.get("ROLE_CODE"))) {
				nextNode = "46_a9";
				continue;
			} else if ("R200".equals(map.get("ROLE_CODE"))) {// 分行/区域中心中的法金业务条线主管---区域中心行长
				nextNode = "46_a3";
				continue;
			} else if ("R102".equals(map.get("ROLE_CODE"))) {// 总行贷款作业部经办
																// ---总行贷款作业部复核
				nextNode = "46_a10";
				continue;
			} else if ("R106".equals(map.get("ROLE_CODE"))
					|| "R110".equals(map.get("ROLE_CODE"))) {// 总行法金Team
																// Head,总行法金业务条线管理岗专员---总行法金业务条线主管
				nextNode = "46_a11";
				continue;
			} else if ("R111".equals(map.get("ROLE_CODE"))) {// 法金产品研发部管理专员----法金产品研发部主管
				nextNode = "46_a12";
				continue;
			} else if ("R112".equals(map.get("ROLE_CODE"))) {// 法金营销管理部管理专员----法金营销管理部主管
				nextNode = "46_a13";
				continue;
			} else if ("R120".equals(map.get("ROLE_CODE"))) {// 总行个金产品专员----个金产品主管
				nextNode = "46_a14";
				continue;
			} else if ("R125".equals(map.get("ROLE_CODE"))) {// 总行个金产品专员----个金部门主管
				nextNode = "46_a15";
				continue;
			}
		}

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("instanceid", instanceid);
		map1.put("currNode", "46_a8");
		map1.put("nextNode", nextNode);
		this.setJson(map1);

		Long idLong = new Long(Integer.parseInt(requestId));
		service.batchUpdateByName(
				"update WorkingplatformNotice n set n.status = '2' where n.noticeId = "
						+ idLong + " ", null);
	}

	/***
	 * 提交审批 新增 修改 面板 提交审批
	 */
	public void saveNotice() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String saveTo = request.getParameter("saveTo");// 标识 新增 修改 的提交审批流程
		WorkingplatformNoticeVo wn = new WorkingplatformNoticeVo();
		String name = request.getParameter("name");
		String level = request.getParameter("level");
		String isTop = request.getParameter("isTop");
		String topDate = request.getParameter("topDate");
		String date = request.getParameter("date");
		String receiveOrg = request.getParameter("receiveOrg");
		String receiveOrgName = request.getParameter("receiveOrgName");
		if ((receiveOrg == null || receiveOrg.trim() == "")
				&& (receiveOrgName != null && receiveOrgName.trim() != "")) {
			List<AdminAuthOrg> orgs = service.findByJql(
					"SELECT a FROM AdminAuthOrg a where a.orgName = '"
							+ receiveOrgName + "'", null);
			for (AdminAuthOrg org : orgs) {
				receiveOrg = org.getOrgId();
			}
		}

		try {
			String instanceid = "";
			if ("create".equals(saveTo)) {
				wn.setNoticeLevel(level);
				wn.setIsTop(isTop);
				if ("1".equals(isTop)) {
					wn.setTopActiveDate(new SimpleDateFormat("yyyy-MM-dd")
							.parse(topDate));
				}
				wn.setNoticeTitle(name);
				wn.setActiveDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
				wn.setReceiveOrg(receiveOrg);
				wn.setCreator(auth.getUserId());
				wn.setPublisher(auth.getUserId());
				wn.setPublishOrg(auth.getUnitId());
				WorkingplatformNotice notice = (WorkingplatformNotice) noticeService
						.save(wn);

				instanceid = "NOTICE_" + notice.getNoticeId() + "_1";// 新增
			} else {
				String published = request.getParameter("published");
				String noticeId = request.getParameter("noticeId");
				List<WorkingplatformNotice> ns = service.findByJql(
						"select n from WorkingplatformNotice n where n.noticeId = '"
								+ noticeId + "' ", null);
				for (WorkingplatformNotice n : ns) {
					wn.setNoticeLevel(level);
					wn.setIsTop(isTop);
					if ("1".equals(isTop)) {
						wn.setTopActiveDate(new SimpleDateFormat("yyyy-MM-dd")
								.parse(topDate));
					}
					wn.setNoticeTitle(name);
					wn.setActiveDate(new SimpleDateFormat("yyyy-MM-dd")
							.parse(date));
					wn.setReceiveOrg(receiveOrg);
					wn.setCreator(n.getCreator());
					wn.setNoticeId(Long.valueOf(noticeId));
				}
				noticeService.save(wn);
				instanceid = "NOTICE_" + noticeId + "_2" + "_"
						+ new SimpleDateFormat("ddHHmm").format(new Date());// 修改
			}

			String jobName = "公告发布_" + name;// 自定义流程名称
			service.initWorkflowByWfidAndInstanceid("46", jobName, null,
					instanceid);// 发起工作流

			String nextNode = "46_a3";
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("instanceid", instanceid);
			map1.put("currNode", "46_s1");
			map1.put("nextNode", nextNode);
			map1.put("nodeId", instanceid.split("_")[1]);
			this.setJson(map1);

			service.batchUpdateByName(
					"update WorkingplatformNotice n set n.status = '2',n.published='pub002' where n.noticeId = '"
							+ instanceid.split("_")[1] + "' ", null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
