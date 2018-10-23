package com.ecc.echain.wf;

import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;

/***
 * 客户经理团队创建复核
 * 
 * @author agile
 */
public class CustMgrTeam extends EChainCallbackCommon {
	// 通过处理
	public void endY(EVO vo) {
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			if (instanceids.length == 5) {
				SQL = "DELETE FROM OCRM_F_CM_MKT_TEAM T WHERE T.MKT_TEAM_ID='"
						+ instanceids[2] + "'";
				execteSQL(vo);// 审批通过，修改客户经理信息表
			} else {
				SQL = "SELECT * FROM OCRM_F_CM_MKT_TEAM T WHERE T.MKT_TEAM_ID = '"
						+ instanceids[2] + "'";
				Result result = querySQL(vo);
				String teamStatus = "";
				for (SortedMap item : result.getRows()) {
					teamStatus = item.get("TEAM_STATUS").toString();
				}
				// 0暂存，1 新增审批中 ，2 生效(审批通过)，3 失效（审批拒绝）4 修改审批中

				SQL = "SELECT t.MKT_TEAM_NAME,t.ORG_ID,t.TEAM_LEADER_ID,t.TEAM_TYPE,a.user_name as TEAM_LEADER,a.mobilephone as LEADER_TELEPHONE FROM OCRM_F_CM_MKT_TEAM_temp T"
						+ " left join  Admin_Auth_Account  a  on t.team_leader_id = a.account_name "
						+ " WHERE T.MKT_TEAM_ID = '" + instanceids[2] + "'";
				result = querySQL(vo);

				for (SortedMap item : result.getRows()) {
					SQL = "update OCRM_F_CM_MKT_TEAM t set  "
							+ " t.mkt_team_name = '"
							+ item.get("MKT_TEAM_NAME").toString() + "', "
							+ " t.org_id = '" + item.get("ORG_ID").toString()
							+ "', " + " t.team_leader_id = '"
							+ item.get("TEAM_LEADER_ID") + "', "
							+ " t.team_leader = '" + item.get("TEAM_LEADER")
							+ "', " + " t.lead_telephone = '"
							+ item.get("LEADER_TELEPHONE") + "', "
							+ " t.team_type = '" + item.get("TEAM_TYPE") + "'"
							+ " where T.MKT_TEAM_ID='" + instanceids[1] + "'";
					execteSQL(vo);
				}

				// 0暂存，1 新增审批中 ，2 生效(审批通过)，3 失效（审批拒绝）4 修改审批中
				SQL = "update OCRM_F_CM_MKT_TEAM t set  t.TEAM_STATUS = '2'  where T.MKT_TEAM_ID='"
						+ instanceids[2] + "'";
				execteSQL(vo);// 审批通过，修改客户经理信息表
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}

	public void endN(EVO vo) {
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = "update OCRM_F_CM_MKT_TEAM t set  t.TEAM_STATUS = '3'  where T.MKT_TEAM_ID='"
					+ instanceids[2] + "'";
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}

	/**
	 * 撤销办理
	 * 
	 * @param vo
	 */
	public void endCB(EVO vo) {
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			SQL = "SELECT * FROM OCRM_F_CM_MKT_TEAM T WHERE T.MKT_TEAM_ID = '"
					+ instanceids[1] + "'";
			Result result = querySQL(vo);
			String teamStatus = "";
			for (SortedMap item : result.getRows()) {
				teamStatus = item.get("TEAM_STATUS").toString();
			}
			// 0暂存，1 新增审批中 ，2 生效(审批通过)，3 失效（审批拒绝）4 修改审批中
			if ("1".equals(teamStatus)) {
				SQL = "update OCRM_F_CM_MKT_TEAM t set  t.TEAM_STATUS = '0'  where T.MKT_TEAM_ID='"
						+ instanceids[1] + "'";
				execteSQL(vo);
			}
			if ("4".equals(teamStatus)) {
				SQL = "update OCRM_F_CM_MKT_TEAM t set  t.TEAM_STATUS = '2'  where T.MKT_TEAM_ID='"
						+ instanceids[1] + "'";
				execteSQL(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}

}
