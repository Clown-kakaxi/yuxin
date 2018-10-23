package com.yuchengtech.bcrm.custmanager.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.wf.CallCenterPerFsxFir;
import com.ecc.echain.wf.CallCenterPerSxSec;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.service.CallCenerCustInfoService;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

@SuppressWarnings("serial")
@Action("callCenter")
public class CallCenerCustInfoAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();

	@Autowired
	private CallCenerCustInfoService service;

	@Autowired
	public void init() {
		model = new AcrmFCiCustomer();
		setCommonService(service);
	}

	private static Logger log = Logger.getLogger(CallCenerCustInfoAction.class);

	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String flag = "";
		flag = request.getParameter("flag");
		String custId=request.getParameter("custId");
		String appr_flag = "";
		if ("done".equals(flag)) {
			appr_flag = "'1','2'";
		} else {
			appr_flag = "'0'";
		}
		StringBuffer sb = new StringBuffer(
				"select  t.id, t.cust_id, t.update_user, t.update_date,  t.appr_flag,  t.appr_date, t.appr_user,  t4.cust_name,  t1.user_name, t5.user_name as appr_user_name,t.update_flag,t.COMMENTCONTENT "
						+ " from OCRM_F_CI_CALLCENTER_UPHIS t"
						+ " left join admin_auth_account t1"
						+ "    on t.update_user = t1.account_name"
						+ " left join ADMIN_AUTH_ACCOUNT_ROLE t2"
						+ "    on t1.id = t2.account_id"
						+ " left join ADMIN_AUTH_ROLE t3"
						+ "   on t2.role_id = t3.id"
						+ "  left join acrm_f_ci_customer t4"
						+ "    on t4.cust_id = t.cust_id"
						+ "   left join admin_auth_account t5"
						+ "    on t.appr_user = t5.account_name"
						+ "       where t.appr_flag in ("+appr_flag+")"
						+ "         and t3.role_code in ('R306', 'R308')");
		if("opinion".equals(flag)){
			sb.append(" and t.cust_id='"+custId+"'");
		}
		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("USER_NAME"))
					sb.append(" and t1." + key + " LIKE '%"
							+ this.getJson().get(key) + "%'");
				else if (key.equals("CUST_NAME"))
					sb.append(" and t4." + key + " like '%"
							+ this.getJson().get(key) + "%'");
				else if (key.equals("UPDATE_DATE"))
					sb.append(" and substr(t."
							+ key
							+ ",0,10) = '"
							+ this.getJson().get(key).toString()
									.substring(0, 10) + "'");
			}
		}
		setPrimaryKey("T.update_date ASC");
		SQL = sb.toString();
		datasource = ds;
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void commitAll() {
		try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse) ctx
					.get(ServletActionContext.HTTP_RESPONSE);

			String custId = request.getParameter("custId");
			String custName = request.getParameter("custName");

			// 验证是否已经提交审核,验证不通过会抛出异常
			service.judgeExist("CI_" + custId);

			String fsxFirst = request.getParameter("fsxFirst");
			String fsxSec_1 = request.getParameter("fsxSec_1");
			String fsxSec_2 = request.getParameter("fsxSec_2");
			String fsxSec_3 = request.getParameter("fsxSec_3");

			JSONArray jFsxFirst = JSONArray.fromObject(fsxFirst);
			JSONArray jFsxSec_1 = JSONArray.fromObject(fsxSec_1);
			JSONArray jFsxSec_2 = JSONArray.fromObject(fsxSec_2);
			JSONArray jFsxSec_3 = JSONArray.fromObject(fsxSec_3);

			// 验证修改后，证件1号码是否已经存在，如若已存在，禁止提交
			service.identNoExist(jFsxFirst, custId);

			service.commitFsxPerAll(jFsxFirst, jFsxSec_1, jFsxSec_2, jFsxSec_3,
					custId, custName);

			response.getWriter().flush();
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "0000", "操作失败,请联系管理员");
		}
	}

	/**
	 * 判断客户记录是否被锁定
	 * 
	 * @return
	 */
	public String isLockCust() {
		try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String custId = "CI_" + request.getParameter("custId");
			StringBuffer sb = new StringBuffer(
					"SELECT DISTINCT A.USER_NAME||'['||T.AUTHOR||']' AS AUTHOR FROM WF_WORKLIST T LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR where t.WFSTATUS <> '3' and t.instanceid like '%"
							+ custId
							+ "%'    union "
							+ " select DISTINCT ac.USER_NAME || '[' || his.update_user || ']' AS AUTHOR  from OCRM_F_CI_CALLCENTER_UPHIS  his "
							+ " left join ADMIN_AUTH_ACCOUNT ac on his.update_user=ac.account_name "
							+ " where his.cust_id='"
							+ request.getParameter("custId")
							+ "' and his.appr_flag='0'");
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();

			// "客户记录被锁定，操作员"+list.get(0)

			if (this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String, Object>();
			this.json.put("json", result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1002", e.getMessage());
		}
		return "success";
	}

	public Result querySQL(StringBuffer sb) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Result result = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			log.info("执行SQL:" + sb);
			rs = stmt.executeQuery(sb.toString());
			result = ResultSupport.toResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.close(rs, stmt, null);// conn在工作流中调用并关闭,这里不做关闭处理
		}
		return result;
	}
	
	public void saveOpinion() throws SQLException{
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String id=request.getParameter("id");
		String commentContent=request.getParameter("commentContent");
		String sql = " update OCRM_F_CI_CALLCENTER_UPHIS t set t.COMMENTCONTENT='"+commentContent+"' ,t.appr_user = '"
				+ auth.getUserId()
				+ "',t.appr_date = to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') where t.id='"
				+ id + "' and t.appr_flag='0'";
		Statement stmt = null;
		Connection conn = ds.getConnection();
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.execute(sql);
			log.info("执行SQL:" + sql);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.closeStatement(stmt);// conn在工作流中调用并关闭,这里不做关闭处理
		}
		
	}

	public void endY() {
		try {
			AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			String instanceid = request.getParameter("instanceid");
			String custId = instanceid.split("_")[1];// 客户号
			String preUpdateFlag = instanceid.split("_")[2];// 修改标识前缀

			StringBuffer sb = new StringBuffer(
					"SELECT DISTINCT T.UPDATE_FLAG,SUBSTR(T.UPDATE_FLAG,15,1) PAGE_ITEM,SUBSTR(T.UPDATE_FLAG,16,1) MODIFY_FLAG,T.APPR_FLAG FROM OCRM_F_CI_CUSTINFO_UPHIS T WHERE T.CUST_ID = '"
							+ custId
							+ "' AND T.UPDATE_FLAG LIKE '"
							+ preUpdateFlag + "|%' ORDER BY T.UPDATE_FLAG ASC");
			Result result = querySQL(sb);
			for (SortedMap<?, ?> item : result.getRows()) {
				// 关联修改记录
				String updateFlag = item.get("UPDATE_FLAG") != null ? (String) item
						.get("UPDATE_FLAG") : "";
				// 0第一屏,1第二屏地址,2第二屏联系人,3第二屏联系信息,4第三屏
				int pageItem = item.get("PAGE_ITEM") != null ? Integer
						.valueOf((String) item.get("PAGE_ITEM")) : -1;
				// 1新增,0修改,仅限于pageItem为1,2,3时生效
				String modifyFlag = item.get("MODIFY_FLAG") != null ? (String) item
						.get("MODIFY_FLAG") : "0";
				// 1已经复核,2否决,是否在上一次流程提交过程中,已经复核过了,如果已复核过了,则不再调交易复核
				String apprFlag = item.get("APPR_FLAG") != null ? (String) item
						.get("APPR_FLAG") : "";
				if ("1".equals(apprFlag)) {
					log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对私非授信已复核[WARNNING]："
							+ updateFlag);
					continue;
				}

				switch (pageItem) {
				case 0:
					log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对私非授信第一屏调用开始--"
							+ updateFlag);
					new CallCenterPerFsxFir().endY(custId, updateFlag, auth,
							ds.getConnection());
					log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对私非授信第一屏调用结束--"
							+ updateFlag);
					break;
				case 1:
				case 2:
				case 3:
					log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对私非授信第二屏调用开始--"
							+ updateFlag);
					new CallCenterPerSxSec().endY(custId, updateFlag, auth,
							pageItem, modifyFlag, ds.getConnection());
					log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<对私非授信第二屏调用结束--"
							+ updateFlag);
					break;
				default:
					break;
				}
			}
		} catch (BizException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 0, "0000",
					"Warning-169：数据信息同步失败，请及时联系IT部门！");
		}
	}

	public void endN() throws SQLException {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String nodeid = request.getParameter("nodeid");
		String custId = request.getParameter("custId");
		String sql = " update OCRM_F_CI_CUSTINFO_UPHIS t set t.appr_flag='2' ,t.appr_user = '"
				+ auth.getUserId()
				+ "',t.appr_date = to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') where t.update_flag='"
				+ nodeid + "'";
		String sql2 = " update OCRM_F_CI_CALLCENTER_UPHIS t set t.appr_flag='2' ,t.appr_user = '"
				+ auth.getUserId()
				+ "',t.appr_date = to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') where t.cust_id='"
				+ custId + "' and t.appr_flag='0'";
		Statement stmt = null;
		Connection conn = ds.getConnection();
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			log.info("执行SQL:" + sql);
			stmt.execute(sql);
			stmt.execute(sql2);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.closeStatement(stmt);// conn在工作流中调用并关闭,这里不做关闭处理
		}

	}
}
