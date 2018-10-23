package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;

public class RecevierBranchLeader implements NodeUserListExtIF {

	@Override
	public List getNodeUserList(EVO vo) throws Exception {
		String branch_leader="";//teamHeader的支行行长
		String batch_no = vo.getInstanceID().split("_")[1];//批次号
		//获得流程变量值
		String sql = " SELECT account_name FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.* " +
				" FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME, AR.ID "+
                " FROM ADMIN_AUTH_ACCOUNT_ROLE AR"+
                " INNER JOIN ADMIN_AUTH_ACCOUNT ACC"+
                " ON ACC.ID = AR.ACCOUNT_ID"+
                " INNER JOIN SYS_UNITS ORG"+
                " ON ORG.UNITID = ACC.ORG_ID"+
                " WHERE AR.APP_ID = 62"+
                " AND AR.ROLE_ID = 311"+
                " ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY"+
                " WHERE org_id=(select t.t_org_id from OCRM_F_CI_TRANS_APPLY t where t.apply_no='"+batch_no+"')";
		Connection connection = vo.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List list = new ArrayList();
		while(rs.next()){
			branch_leader= rs.getString(1);
			list.add(branch_leader);
		}
		/*String manager =  StringUtil.toString((HashMap) evo.paramMap.get("custMgrYJ"));
		System.out.println("*********:"+manager);*/
		//设置办理人列表
		return list;
	}

}
