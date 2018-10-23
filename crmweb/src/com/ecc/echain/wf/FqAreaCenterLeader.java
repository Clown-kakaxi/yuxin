package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bob.vo.AuthUser;

public class FqAreaCenterLeader implements NodeUserListExtIF {

	@Override
	public List getNodeUserList(EVO vo) throws Exception {
		String area_leader="";//区域中心行长
		String batch_no = vo.getInstanceID().split("_")[1];//批次号
//		
//		String sql="SELECT account_name FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*" +
//				" FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME,AR.ID" +
//				" FROM ADMIN_AUTH_ACCOUNT_ROLE AR" +
//				" INNER JOIN (select * from admin_auth_account t where t.org_id in(" +
//				" select org_id from admin_auth_org start with org_id=(   select o.up_org_id from admin_auth_org  o   where org_id=(select T_org_id from OCRM_F_CI_TRANS_APPLY a" +
//				" where a.apply_no = '"+batch_no+"')) connect by up_org_id = prior org_id)) ACC" +
//				" ON ACC.ID = AR.ACCOUNT_ID" +
//				" INNER JOIN SYS_UNITS ORG" +
//				" ON ORG.UNITID = ACC.ORG_ID" +
//				" WHERE AR.APP_ID = 62" +
//				" AND AR.ROLE_ID = 202" +
//				" ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY";
		String sql="SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*" +
				" FROM (SELECT ACC.USER_NAME, ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME,AR.ID" +
				" FROM ADMIN_AUTH_ACCOUNT_ROLE AR" +
				" INNER JOIN (SELECT * FROM ADMIN_AUTH_ACCOUNT T WHERE T.ORG_ID IN(" +
				" SELECT ORG_ID FROM ADMIN_AUTH_ORG START WITH ORG_ID=(SELECT O.UP_ORG_ID FROM ADMIN_AUTH_ORG  O  WHERE ORG_ID=(SELECT ORG_ID FROM ADMIN_AUTH_ACCOUNT WHERE ACCOUNT_NAME =(SELECT USER_ID FROM OCRM_F_CI_TRANS_APPLY WHERE APPLY_NO = '"+batch_no+"'))) CONNECT BY UP_ORG_ID = PRIOR ORG_ID)) ACC" +
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
		/*String manager =  StringUtil.toString((HashMap) evo.paramMap.get("custMgrYJ"));
		System.out.println("*********:"+manager);*/
		//设置办理人列表
		return list;
	}

}
