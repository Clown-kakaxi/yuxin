package com.ecc.echain.wf;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/***
 *客户经理团队成员复核
 * @author agile
 */
public class CustMktPer extends EChainCallbackCommon{
	//通过处理
	public void endY(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			
			AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
			SQL = "SELECT * FROM OCRM_F_CM_TEAM_CUST_MANAGER T WHERE T.MKT_TEAM_ID = '"+instanceids[1]+"'";//团队ID
			Result result = querySQL(vo);
			String teamStatus = "";
			String mktTeamId = "";
			for (SortedMap item : result.getRows()){
				teamStatus = item.get("CUST_MANAGER_STATE").toString();
				mktTeamId = item.get("MKT_TEAM_ID").toString();
				
				//0.待加入 ，1 待删除  ，2  生效 3,失效
				if("0".equals(teamStatus)){
					SQL =  "update OCRM_F_CM_TEAM_CUST_MANAGER t set  T.CUST_MANAGER_STATE = '2',t.approver = '"+auth.getUserId()+"'  where T.ID='"+item.get("ID").toString()+"'";
					execteSQL(vo);//审批通过
					
					SQL =  " update OCRM_F_CM_MKT_TEAM r  set  " +
						   " r.team_scale = (select count(mr.id) from OCRM_F_CM_TEAM_CUST_MANAGER mr where mr.mkt_team_id = r.mkt_team_id and mr.cust_manager_state = '2')" +
						   " where r.MKT_TEAM_ID = '"+mktTeamId+"'";
					execteSQL(vo);//增加团队人数
					
					sendRemind(item.get("ID").toString(),vo,true);
				}
				if("1".equals(teamStatus)){
					SQL =  "update OCRM_F_CM_TEAM_CUST_MANAGER t set t.CUST_MANAGER_STATE = '3'  where T.ID='"+item.get("ID").toString()+"'";
					execteSQL(vo);//审批通过
					
					SQL =  " update OCRM_F_CM_MKT_TEAM r  set  " +
					   " r.team_scale = (select count(mr.id) from OCRM_F_CM_TEAM_CUST_MANAGER mr where mr.mkt_team_id = r.mkt_team_id and mr.cust_manager_state = '2')" +
					   " where r.MKT_TEAM_ID = '"+mktTeamId+"'";
					execteSQL(vo);//减少团队人数
					
					sendRemind(item.get("ID").toString(),vo,false);
				}
			}
		} catch (SQLException e) {
			System.out.println("执行SQL出错");
			throw new BizException(0, 1, "1002", e.getMessage());
		}
	}
	
	public void endN(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String instanceids[] = instanceid.split("_");
			
			SQL = "SELECT * FROM OCRM_F_CM_TEAM_CUST_MANAGER T WHERE T.mkt_team_ID = '"+instanceids[1]+"'";
			Result result = querySQL(vo);
			String teamStatus="";
			for (SortedMap item : result.getRows()){
				teamStatus = item.get("CUST_MANAGER_STATE").toString();
				
				//0.待加入 ，1 待删除  ，2  生效 3,失效
				if("0".equals(teamStatus)){
					SQL =  "delete from  OCRM_F_CM_TEAM_CUST_MANAGER t  where T.ID='"+item.get("ID").toString()+"'";
					execteSQL(vo);
				}
				if("1".equals(teamStatus)){
					SQL =  "update OCRM_F_CM_TEAM_CUST_MANAGER t set t.CUST_MANAGER_STATE = '2'  where T.ID='"+item.get("ID").toString()+"'";
					execteSQL(vo);
				}
			}
		} catch (SQLException e) {
			System.out.println("执行SQL出错");
			throw new BizException(0, 1, "1002", e.getMessage());
	   }
    }
    /**
     *审批通过写入信息提醒表 
     */
	public void sendRemind(String id,EVO vo,boolean flg){
		try {
			
			SQL = "select * from  OCRM_F_WP_REMIND_RULE  r where r.RULE_CODE = '901' ";//901 字典表对应团队信息提醒类型
			Result result = querySQL(vo);
			String ruleId="";
			String ruleCode="";
			for (SortedMap item : result.getRows()){
				ruleId = item.get("RULE_ID").toString();
				ruleCode = item.get("RULE_CODE").toString();
			}
			
			SQL = "SELECT u1.userName as APPROVER_NAME,u.org_name,m.* FROM OCRM_F_CM_TEAM_CUST_MANAGER m" +
					" left join OCRM_F_CM_MKT_TEAM t on m.mkt_team_id = t.mkt_team_id " +
					" left join sys_units u on u.id = m.belong_org " +
					" left join sys_users u1 on u1.userId=t.team_leader_id " +
					" WHERE m.ID = '"+id+"'";
			result = querySQL(vo);
			String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			for (SortedMap item : result.getRows()){
				if(flg){
					SQL="INSERT INTO OCRM_F_WP_REMIND(INFO_ID,RULE_ID,RULE_CODE,MSG_CRT_DATE,REMIND_REMARK)" +
					" VALUES((SELECT max(INFO_ID)+1 FROM OCRM_F_WP_REMIND)," +
					" '"+ruleId+"','"+ruleCode+"',to_date('"+dateString+"','yyyy-mm-dd')," +
					" '"+dateString+"，"+item.get("ORG_NAME").toString()+"机构，"+item.get("TEAM_NAME").toString()+"团队，" +
					"维护团队新增成员"+item.get("CUST_MANAGER_NAME")+"，团队负责人"+item.get("APPROVER_NAME")+"。')";
				}else{
					SQL="INSERT INTO OCRM_F_WP_REMIND(INFO_ID,RULE_ID,RULE_CODE,MSG_CRT_DATE,REMIND_REMARK)" +
					" VALUES((SELECT max(INFO_ID)+1 FROM OCRM_F_WP_REMIND)," +
					" '"+ruleId+"','"+ruleCode+"',to_date('"+dateString+"','yyyy-mm-dd')," +
					" '"+dateString+"，"+item.get("ORG_NAME").toString()+"机构，"+item.get("TEAM_NAME").toString()+"团队，" +
					"维护团队删除成员"+item.get("CUST_MANAGER_NAME")+"，团队负责人"+item.get("APPROVER_NAME")+"。')";
				}
				execteSQL(vo);
			}
			
			
		} catch (Exception e) {
			throw new BizException(0, 1, "1002", e.getMessage());
		}
	}
	
}
