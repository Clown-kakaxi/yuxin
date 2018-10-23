package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;

public class VistBranchr implements NodeUserListExtIF{
	
	@Override
	public List getNodeUserList(EVO vo) throws Exception {
		// TODO Auto-generated method stub
		String brancher="";
		String sql="SELECT ACC.ACCOUNT_NAME "
				+ " FROM ADMIN_AUTH_ACCOUNT_ROLE  AR INNER  JOIN ADMIN_AUTH_ACCOUNT ACC ON ACC.ID = AR.ACCOUNT_ID "
				+ " INNER  JOIN SYS_UNITS ORG ON ORG.UNITID = ACC.ORG_ID"
				+ " WHERE AR.APP_ID = 62 AND AR.ROLE_ID = 311 AND ACC.ORG_ID="+vo.getOrgid()
				+ " ORDER BY ACC.ORG_ID";
		Connection connection = vo.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List list = new ArrayList();
		if(rs.next()){
			brancher=rs.getString(1);
			list.add(brancher);
			while(rs.next()){
				brancher=rs.getString(1);
				list.add(brancher);
			}
		}else{
			String sql2="SELECT ACC.ACCOUNT_NAME "
					+ " FROM ADMIN_AUTH_ACCOUNT_ROLE  AR INNER  JOIN ADMIN_AUTH_ACCOUNT ACC ON ACC.ID = AR.ACCOUNT_ID "
					+ " INNER  JOIN SYS_UNITS ORG ON ORG.UNITID = ACC.ORG_ID"
					+ " WHERE AR.APP_ID = 62 AND AR.ROLE_ID = 202 AND ACC.ORG_ID IN (SELECT T.ORG_ID FROM ADMIN_AUTH_ORG T START WITH T.ORG_ID = (SELECT O.UP_ORG_ID FROM ADMIN_AUTH_ORG O WHERE ORG_ID ="+vo.getOrgid()+") CONNECT BY T.UP_ORG_ID = PRIOR T.ORG_ID)"
					+ " ORDER BY ACC.ORG_ID";
			ResultSet rs2 = stmt.executeQuery(sql2);
			while(rs2.next()){
				brancher=rs2.getString(1);
				list.add(brancher);
			}
		}
		
		/*String manager =  StringUtil.toString((HashMap) evo.paramMap.get("custMgrYJ"));
		System.out.println("*********:"+manager);*/
		//设置办理人列表
		return list;
	}

}
