package com.yuchengtech.bcrm.customer.customerMktTeam.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerMktTeam.model.OcrmFCmTeamCustManager;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

/**
 * @description:客户经理团队管理成员信息保存，修改 ，删除
 * @author xiebz
 * @data 2014-07-02
 */
@Service
public class CustomerMktTeamMembersService extends CommonService {
	public CustomerMktTeamMembersService() {
		JPABaseDAO<OcrmFCmTeamCustManager, Long> baseDao = new JPABaseDAO<OcrmFCmTeamCustManager, Long>(
				OcrmFCmTeamCustManager.class);
		super.setBaseDAO(baseDao);
	}

	/**
	 * 修改，保存数据处理
	 */
	public Object save(Object obj) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		OcrmFCmTeamCustManager marketTeamManager = (OcrmFCmTeamCustManager) obj;
		OcrmFCmTeamCustManager marketTeamMge = null;
		// 新增经理团队成员
		String ids = marketTeamManager.getUserId();
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.createStatement();
			StringBuffer sb = new StringBuffer();
			String[] idStr = ids.split(",");
			int i = 0;
			for (String id : idStr) {
				i++;
				if (i == idStr.length) {
					sb.append("'").append(id).append("'");
				} else {
					sb.append("'").append(id).append("'").append(",");
				}
			}
			// 团队成员ID
			int id = 0;
			String sql1 = "select (max(id)+1) as id from OCRM_F_CM_TEAM_CUST_MANAGER r ";
			rs1 = stmt.executeQuery(sql1);
			while (rs1.next()) {
				id = rs1.getInt("ID");
			}
			// 团队成员机构
			String orgId = "";
			String sql2 = "select * from OCRM_F_CM_MKT_TEAM t where t.mkt_team_id = "
					+ marketTeamManager.getMktTeamId();
			rs2 = stmt.executeQuery(sql2);
			while (rs2.next()) {
				orgId = rs2.getString("ORG_ID");
			}
			// 取要加入团队的成员
			String sql = "select t.* from admin_auth_account t  where t.account_name in ("
					+ sb
					+ ") "
					+ " and account_name not in (select r.cust_manager_id from OCRM_F_CM_TEAM_CUST_MANAGER r "
					+ " where r.mkt_team_id = "
					+ marketTeamManager.getMktTeamId()
					+ ""
					+ " and r.cust_manager_state in ('0','1','2') )";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				marketTeamMge = new OcrmFCmTeamCustManager();
				marketTeamMge.setId(Long.valueOf(id));
				marketTeamMge.setMktTeamId(marketTeamManager.getMktTeamId());
				marketTeamMge.setJoinDate(new Date());
				marketTeamMge.setTeamType(request.getParameter("teamType"));
				marketTeamMge.setUserId(auth.getUserId());
				marketTeamMge.setBelongOrg(orgId);
				marketTeamMge.setTeamName(request.getParameter("mktTeamName"));
				marketTeamMge.setCustManagerName(rs.getString("USER_NAME"));
				marketTeamMge.setCustManagerOrg(rs.getString("ORG_ID"));
				marketTeamMge.setCustManagerId(rs.getString("ACCOUNT_NAME"));
				marketTeamMge.setCustManagerState("2");// 0.待加入 ，1 待删除 ，2 生效
				id++;
				super.save(marketTeamMge);
			}
			if(marketTeamMge==null){
				throw new BizException(1, 2, "1002", "客户经理已经存在");
			}
		} catch (Exception e) {
			throw new BizException(1, 2, "1002", "已经存在", e.getMessage());
		} finally {
			JdbcUtil.closeResultSet(rs1);
			JdbcUtil.closeResultSet(rs2);
			JdbcUtil.close(rs, stmt, conn);
		}
		return marketTeamMge;

	}

	/**
	 * 删除审批
	 */
	public void batchRemove(String ids) {
		super.batchUpdateByName(
				"delete from  OcrmFCmTeamCustManager r  where r.id IN ("
						+ ids + ")", null);
	}
}
