package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;

public class tmuOther1 implements NodeUserListExtIF {
	@Override
	public List getNodeUserList(EVO vo) throws Exception {
		String branch_leader="";//归属的teamheader
		String userId = vo.getInstanceID().split("_")[4];//批次号
		//获得流程变量值
		String sql = " SELECT B.ACCOUNT_NAME AS BRANCH_LEADER FROM ADMIN_AUTH_ACCOUNT_ROLE A "+
				" LEFT JOIN ADMIN_AUTH_ACCOUNT B ON A.ACCOUNT_ID=B.ID "+
				" WHERE  A.ROLE_ID IN('311','126') AND B.ORG_ID IN(   "+
				" SELECT B.ORG_ID FROM ADMIN_AUTH_ACCOUNT B WHERE B.ACCOUNT_NAME='"+userId+"' )";
		Connection connection = vo.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List list = new ArrayList();
		if(rs.next()){
			branch_leader= rs.getString(1);
			list.add(branch_leader);
		}
		/*String manager =  StringUtil.toString((HashMap) evo.paramMap.get("custMgrYJ"));
		System.out.println("*********:"+manager);*/
		//设置办理人列表		
		return list;
	}
}