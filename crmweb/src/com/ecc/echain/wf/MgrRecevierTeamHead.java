package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;

public class MgrRecevierTeamHead implements NodeUserListExtIF{

	@Override
	public List getNodeUserList(EVO vo) throws Exception {
		String mgr_team_head="";//归属的teamheader
		String batch_no = vo.getInstanceID().split("_")[1];//批次号
		//获得流程变量值
		String sql = " select a.belong_team_head from admin_auth_account a  where account_name=(select t.user_id from OCRM_F_CI_TRANS_APPLY t where t.apply_no='"+batch_no+"')";
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
