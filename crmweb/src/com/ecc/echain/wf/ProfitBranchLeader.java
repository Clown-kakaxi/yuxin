package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecc.echain.ext.NodeUserListExtIF;
import com.ecc.echain.workflow.engine.EVO;

public class ProfitBranchLeader implements NodeUserListExtIF {

	@Override
	public List getNodeUserList(EVO vo) throws Exception {
		String branch_leader="";//teamHeader的支行行长
		String id = vo.getInstanceID().split("_")[1];//批次号
		String sql="SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*" +
				" FROM (SELECT ACC.USER_NAME, ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME,AR.ID" +
				" FROM ADMIN_AUTH_ACCOUNT_ROLE AR" +
				" INNER JOIN (SELECT * FROM ADMIN_AUTH_ACCOUNT T WHERE T.ORG_ID IN(" +
				" SELECT ORG_ID FROM ADMIN_AUTH_ORG START WITH ORG_ID=(SELECT O.UP_ORG_ID FROM ADMIN_AUTH_ORG  O  WHERE ORG_ID=(SELECT ORG_ID FROM ADMIN_AUTH_ACCOUNT WHERE ACCOUNT_NAME in (select create_user_id from ACRM_A_CI_PROF_RELATION where CREATE_TIMES='"+id+"'))) CONNECT BY UP_ORG_ID = PRIOR ORG_ID)) ACC" +
				" ON ACC.ID = AR.ACCOUNT_ID" +
				" INNER JOIN SYS_UNITS ORG" +
				" ON ORG.UNITID = ACC.ORG_ID" +
				" WHERE AR.APP_ID = 62" +
				" AND AR.ROLE_ID = 202" +
				" ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY";
		Connection connection = vo.getConnection();
		Statement stmt = connection.createStatement();
		boolean flag = false;
		ResultSet rs = stmt.executeQuery(sql);
		List list = new ArrayList();
		List<String> branchlist =new ArrayList<String>();
		while(rs.next()){
			branch_leader= rs.getString(1);
			list.add(branch_leader);
		}
		 if(list!=null){
     		  if(list.size()==1){//如果区域中心主管/分行行长只有一个人  
     			 String sql1 = " SELECT account_name FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.* " +
     					" FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME, AR.ID "+
     	                " FROM ADMIN_AUTH_ACCOUNT_ROLE AR"+
     	                " INNER JOIN ADMIN_AUTH_ACCOUNT ACC"+
     	                " ON ACC.ID = AR.ACCOUNT_ID"+
     	                " INNER JOIN SYS_UNITS ORG"+
     	                " ON ORG.UNITID = ACC.ORG_ID"+
     	                " WHERE AR.APP_ID = 62"+
     	                " AND AR.ROLE_ID = 311"+
     	                " ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY"+
     	                " WHERE org_id=(select create_user_id from ACRM_A_CI_PROF_RELATION where id='"+id+"')";
     			 rs= stmt.executeQuery(sql1);
     			 while(rs.next()){
    				String area_leader= rs.getString(1);
    				branchlist.add(area_leader);
    			}
     			 if(branchlist!=null){
     				 if(branchlist.size()==1){
     					 String areaLeader = (String) list.get(0);
     					 String branchLeader = (String) branchlist.get(0);
     					 if(areaLeader.equals(branchLeader)){
     						 flag = true;//区域中心主管和分支行行长是同一个人
     					 }
     				 }else if(branchlist.size()==0){//不存在分/支行行长
     					 flag = true;
     				 }
     			 }
     		  }
     	  }
		 if(flag){
			 return list;
		 }else{
			 return branchlist;
		 }
		/*String manager =  StringUtil.toString((HashMap) evo.paramMap.get("custMgrYJ"));
		System.out.println("*********:"+manager);*/
		//设置办理人列表
		
	}

}
