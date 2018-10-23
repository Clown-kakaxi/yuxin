package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;

public class BusiLine implements NodeUserListExtIF{

	@Override
	public List getNodeUserList(EVO vo) throws Exception {
		String busi_line="";//TT/CB业务条线
		String batch_no = vo.getInstanceID().split("_")[1];//批次号
		//获得流程变量值
		String sql = "SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*" +
				" FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME,ACC.ORG_ID,ACC.BELONG_BUSI_LINE,ORG.UNITNAME,AR.ID" +
				" FROM ADMIN_AUTH_ACCOUNT_ROLE AR" +
				" INNER JOIN ADMIN_AUTH_ACCOUNT ACC" +
				" ON ACC.ID = AR.ACCOUNT_ID" +
				" INNER JOIN SYS_UNITS ORG" +
				" ON ORG.UNITID = ACC.ORG_ID" +
				" WHERE AR.APP_ID = 62" +
				" AND AR.ROLE_ID = 107" +
				" ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY" +
				" WHERE BELONG_BUSI_LINE=(" +
				" SELECT  A.BELONG_BUSI_LINE FROM ADMIN_AUTH_ACCOUNT A" +
				" LEFT JOIN OCRM_F_CI_TRANS_APPLY B" +
				" ON A.ACCOUNT_NAME=B.USER_ID " +
				" WHERE B.APPLY_NO='"+batch_no+"')";
		Connection connection = vo.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List list = new ArrayList();
		while(rs.next()){
			busi_line= rs.getString(1);
			list.add(busi_line);
		}
		//设置办理人列表
		return list;
	}

}
