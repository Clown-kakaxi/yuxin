package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;

public class CustRecevierMgr implements NodeUserListExtIF  {

	@Override
	public List getNodeUserList(EVO vo) throws Exception {
		/*EVO vo = WorkFlowClient.getInstance().getInstanceInfo(evo);
		Object o = vo.paramMap.get("getNodeFormData");*/
		
		String t_mgr_id="";//接收客户经理ID
		String batch_no = vo.getInstanceID().split("_")[1];//批次号
		//获得流程变量值
		String sql = "select t.t_mgr_id from OCRM_F_CI_TRANS_APPLY t where t.apply_no='"+batch_no+"'";
		Connection connection = vo.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next()){
			t_mgr_id = rs.getString(1);
		}
		/*String manager =  StringUtil.toString((HashMap) evo.paramMap.get("custMgrYJ"));
		System.out.println("*********:"+manager);*/
		//设置办理人列表
		List list = new ArrayList();
		list.add(t_mgr_id);
		return list;
	}

}
