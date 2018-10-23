package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;

public class MktTask implements NodeUserListExtIF {
	
	public List getNodeUserList(EVO vo) throws Exception {
		String mgr_team_head="";//归属的teamheader
		String userId = vo.getInstanceID().split("_")[2];//批次号
		//获得流程变量值
		String sql = " select a.belong_team_head from admin_auth_account a  where account_name='"+userId+"'";
		Connection connection = vo.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List list = new ArrayList();
		if(rs.next()){
			mgr_team_head= rs.getString(1);
			list.add(mgr_team_head);
		}
		/*String manager =  StringUtil.toString((HashMap) evo.paramMap.get("custMgrYJ"));
		System.out.println("*********:"+manager);*/
		//设置办理人列表
		
		return list;
	}

}
