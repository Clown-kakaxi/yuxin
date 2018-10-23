package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;

public class MktTeamAreaCenterLeader implements NodeUserListExtIF{
	@Override
	public List getNodeUserList(EVO vo) throws Exception {
		String area_leader="";//区域中心行长
		String orgId = vo.getInstanceID().split("_")[1];//机构号
		String sql="SELECT account_name FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*" +
				" FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME,AR.ID" +
				" FROM ADMIN_AUTH_ACCOUNT_ROLE AR" +
				" INNER JOIN (select * from admin_auth_account t where t.org_id in(" +
				" select org_id from admin_auth_org start with org_id=(   select o.up_org_id from admin_auth_org  o   where org_id= '"+orgId+"') connect by up_org_id = prior org_id)) ACC" +
				" ON ACC.ID = AR.ACCOUNT_ID" +
				" INNER JOIN SYS_UNITS ORG" +
				" ON ORG.UNITID = ACC.ORG_ID" +
				" WHERE AR.APP_ID = 62" +
				" AND AR.ROLE_ID = 202" +
				" ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY";
		Connection connection = vo.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List list = new ArrayList();
		while(rs.next()){
			area_leader= rs.getString(1);
			list.add(area_leader);
		}
		return list;
	}

}
