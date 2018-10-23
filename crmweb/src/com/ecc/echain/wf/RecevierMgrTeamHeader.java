package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;

public class RecevierMgrTeamHeader implements NodeUserListExtIF {

	@SuppressWarnings("rawtypes")
	@Override
	public List getNodeUserList(EVO vo) throws Exception {
		String mgr_team_head="";//接受的RM归属的teamheader如何是个金的RM去AO主管
		String batch_no = vo.getInstanceID().split("_")[1];//批次号
		//获得流程变量值
		String sql = " select a.belong_team_head from admin_auth_account a  where account_name=(select t.t_mgr_id from OCRM_F_CI_TRANS_APPLY t where t.apply_no='"+batch_no+"') and a.belong_team_head is not null ";
		Connection connection = vo.getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List<String> list = new ArrayList<String>();
		if(rs.next()){
			mgr_team_head= rs.getString(1);
			if(mgr_team_head!=null){
			 list.add(mgr_team_head);
			}
		}
		boolean flag = checkRecevierMgrName(batch_no, vo);
		if(flag){
			if(list==null || list.size()==0){
			        String sql1="SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*" +
			   		" FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME, ACC.ORG_ID, ORG.UNITNAME,AR.ID" +
			   		" FROM ADMIN_AUTH_ACCOUNT_ROLE AR" +
			   		" INNER JOIN ADMIN_AUTH_ACCOUNT ACC" +
			   		" ON ACC.ID = AR.ACCOUNT_ID" +
			   		" INNER JOIN SYS_UNITS ORG" +
			   		" ON ORG.UNITID = ACC.ORG_ID" +
			   		" WHERE AR.APP_ID = 62" +
			   		" AND AR.ROLE_ID = 310" +
			   		" ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY" +
			   		" WHERE ORG_ID=(SELECT T.T_ORG_ID FROM OCRM_F_CI_TRANS_APPLY T WHERE T.APPLY_NO='"+batch_no+"')";
			        ResultSet rs1 = stmt.executeQuery(sql1);
			    	while(rs1.next()){
						mgr_team_head= rs1.getString(1);
						if(mgr_team_head!=null){
							 list.add(mgr_team_head);
							}
					}
			   }
		}
		/*String manager =  StringUtil.toString((HashMap) evo.paramMap.get("custMgrYJ"));
		System.out.println("*********:"+manager);*/
		//设置办理人列表
		
		return list;
	}
	
	
	public  boolean checkRecevierMgrName(String batch_no,EVO vo) throws Exception{
		boolean flag = false;
		try{
			String sql ="SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*" +
					" FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME,ACC.ORG_ID, ORG.UNITNAME,AR.ID" +
					" FROM ADMIN_AUTH_ACCOUNT_ROLE AR" +
					" INNER JOIN ADMIN_AUTH_ACCOUNT ACC" +
					" ON ACC.ID = AR.ACCOUNT_ID" +
					" INNER JOIN SYS_UNITS ORG" +
					" ON ORG.UNITID = ACC.ORG_ID" +
					" WHERE AR.APP_ID = 62" +
					" AND AR.ROLE_ID = 303" +
					" ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY" +
					" WHERE ACCOUNT_NAME =(select t.t_mgr_id from OCRM_F_CI_TRANS_APPLY t where t.apply_no='"+batch_no+"')";
			Connection connection = vo.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
		   String Name = "";
		   if (result.next()){
			   Name = result.getString(1);
		   }
		   if(Name!= null && !"".equals(Name)){
			   flag = true;
		   }
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

}
