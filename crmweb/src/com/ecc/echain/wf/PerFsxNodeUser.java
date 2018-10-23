package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jfree.util.Log;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bob.vo.AuthUser;

public class PerFsxNodeUser implements NodeUserListExtIF {
	@Override
	public List getNodeUserList(EVO vo) throws Exception {
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String mgr_team_head="";//归属的teamheader
		String batch_no = vo.getInstanceID().split("_")[1];//批次号
		//获得流程变量值
		String sql = "SELECT  ACC.ACCOUNT_NAME FROM ADMIN_AUTH_ACCOUNT_ROLE AR INNER JOIN ADMIN_AUTH_ACCOUNT ACC ON ACC.ID = AR.ACCOUNT_ID "
				+ " INNER JOIN SYS_UNITS ORG ON ORG.UNITID = ACC.ORG_ID "
				+ " WHERE AR.APP_ID = 62 AND AR.ROLE_ID = 301 AND ACC.ACCOUNT_NAME NOT IN ('"+auth.getUserId()+"') and ACC.ORG_ID='"+auth.getUnitId()+"'  ORDER BY ACC.ORG_ID ";
		Log.info(sql);
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
