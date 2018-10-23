package com.yuchengtech.bcrm.customer.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ecc.echain.wf.TaskJob;
import com.yuchengtech.bcrm.sales.message.action.MailClient;
import com.yuchengtech.crm.constance.JdbcUtil;
/**
 * 个金潜在客户主动回收
 * @author mamusa
 *2016-01-29
 */
public class LatentCusRecoveryJobAction {
	private static Logger log = Logger.getLogger(LatentCusRecoveryJobAction.class);
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	/**
	 * 自动回收条件：
	 * 1.以当前时间开始最近两个工作日内没有做callreport且未成为正式客户的 ，
	 * 2.以当前时间开始最近两个工作日内有做callreport且未成为正式客户且超过默认3个月截止回收日期的。
	 */
	public void recoveryLatentCust(){
		log.info("个金潜在客户自动回收处理任务-----开始");
		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		ResultSet rs = null;
		String sql = "";
		String cusidStr="";     //客户id
		String backstateStr=""; //分配回收状态 1.未回收 2.已分派 3.新建，0.已回收
		String moveruserStr="";   //分派人
		String mainbridStr="";  //所属机构
		String custmgr="";//所属客户经理
		String custName="";  //客户名
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmss");
		Date newdate=new Date();
		String time = sdfs.format(newdate);
		String time1=new SimpleDateFormat("yyyy-MM-dd").format(newdate);
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from ( ");
				sb.append(" select d.CUS_ID, d.CUS_NAME, d.CERT_TYPE, d.CERT_CODE, d.CUST_TYPE, d.CALL_NO, d.CUST_MGR, ac.user_name CUST_MGR_NAME, ao.org_name MAIN_BR_NAME, d.MAIN_BR_ID, d.STATE, d.CONCLUSION, d.MKT_ACTIVITIE, d.SOURCE_CHANNEL, d.REFEREES_ID,a.USER_NAME MOVER_USER_NAME,d.MOVER_USER, d.MOVER_DATE, d.BACK_STATE, d.MAIN_BR_ID TREE_STORE, d.INPUT_DATE, d.ACTUAL_RECEIVE_DATE, d.DEFAULT_RECEIVE_DATE ");
				sb.append(" from ACRM_F_CI_POT_CUS_COM d ");
				sb.append(" left join ACRM_F_CI_CUSTOMER c on d.referees_id = c.cust_id");
				sb.append(" left join ADMIN_AUTH_ACCOUNT ac on d.CUST_MGR = ac.account_name");
				sb.append(" left join ADMIN_AUTH_ACCOUNT a on d.MOVER_USER = a.account_name");
			    sb.append(" left join ADMIN_AUTH_ACCOUNT M on d.CUST_MGR = M.account_name");
			    sb.append(" left join ADMIN_AUTH_ORG ao on d.main_br_id = ao.org_id  ");
			    sb.append(" where 1 = 1  and d.cust_type = '2' and d.STATE = '0'  and d.back_state='2'   and d.formal_cust_flag='1' ");
			    sb.append(" and (select c.cust_id  from acrm_f_ci_customer c  left  join ACRM_F_CI_CONTMETH t on c.cust_id=t.cust_id   where  replace(t.contmeth_info,substr(t.contmeth_info,0,instr(t.contmeth_info,'-',1,LENGTH(REGEXP_REPLACE(REPLACE(t.contmeth_info, '-', '@'),  '[^@]+',  '')))))=replace(d.call_no,substr(d.call_no,0,instr(d.call_no,'-',1,2))) and c.cust_name=d.cus_name) is null ");
			    sb.append("   and TO_CHAR(d.mover_date,'YYYYMMDD')<(SELECT min(TRIM(TO_CHAR(PLAN_DATE, 'YYYYMMDD'))) PLAN_DATE FROM (SELECT * from ACRM_F_CI_WORK_PLAN WHERE IS_WORK = '1' AND TO_CHAR(PLAN_DATE, 'YYYYMMDD') < TO_CHAR(SYSDATE, 'YYYYMMDD') ORDER BY PLAN_DATE DESC) WHERE ROWNUM < 3) ");
			    sb.append(" and d.CUS_ID not IN (SELECT CUST_ID FROM OCRM_F_SE_CALLREPORT   ");
			    sb.append("	WHERE TO_CHAR(LAST_UPDATE_TM,'YYYYMMDD') IN    ");
			    sb.append("	(SELECT TRIM(TO_CHAR(PLAN_DATE, 'YYYYMMDD')) PLAN_DATE   ");
			    sb.append("  FROM (SELECT *  from ACRM_F_CI_WORK_PLAN   ");
			    sb.append(" WHERE IS_WORK = '1'   ");
			    sb.append("  AND TO_CHAR(PLAN_DATE, 'YYYYMMDD') <  TO_CHAR(SYSDATE, 'YYYYMMDD') ");
			    sb.append("   ORDER BY PLAN_DATE DESC) ");
			    sb.append("  WHERE ROWNUM < 3)) ");
			    sb.append(" union ");
			    sb.append(" select d.CUS_ID, d.CUS_NAME, d.CERT_TYPE, d.CERT_CODE, d.CUST_TYPE, d.CALL_NO, d.CUST_MGR, ac.user_name CUST_MGR_NAME, ao.org_name MAIN_BR_NAME, d.MAIN_BR_ID, d.STATE, d.CONCLUSION, d.MKT_ACTIVITIE, d.SOURCE_CHANNEL, d.REFEREES_ID,a.USER_NAME MOVER_USER_NAME,d.MOVER_USER,d.MOVER_DATE, d.BACK_STATE, d.MAIN_BR_ID TREE_STORE, d.INPUT_DATE, d.ACTUAL_RECEIVE_DATE, d.DEFAULT_RECEIVE_DATE ");
			    sb.append(" from ACRM_F_CI_POT_CUS_COM d ");
			    sb.append(" left join ACRM_F_CI_CUSTOMER c on d.referees_id = c.cust_id ");
			    sb.append(" left join ADMIN_AUTH_ACCOUNT ac on d.CUST_MGR = ac.account_name ");
			    sb.append(" left join ADMIN_AUTH_ACCOUNT a on d.MOVER_USER = a.account_name ");
			    sb.append(" left join ADMIN_AUTH_ACCOUNT M on d.CUST_MGR = M.account_name ");
			    sb.append(" left join ADMIN_AUTH_ORG ao on d.main_br_id = ao.org_id  ");
			    sb.append(" where 1 = 1 and d.cust_type = '2' and d.STATE = '0' and d.back_state='2'  and d.formal_cust_flag='1' ");
			    sb.append(" and (select c.cust_id  from acrm_f_ci_customer c  left  join ACRM_F_CI_CONTMETH t on c.cust_id=t.cust_id   where  replace(t.contmeth_info,substr(t.contmeth_info,0,instr(t.contmeth_info,'-',1,LENGTH(REGEXP_REPLACE(REPLACE(t.contmeth_info, '-', '@'),  '[^@]+',  '')))))=replace(d.call_no,substr(d.call_no,0,instr(d.call_no,'-',1,2))) and c.cust_name=d.cus_name) is null ");
			    sb.append(" and sysdate>=add_months(d.mover_date, 3) ");
			    sb.append("   and TO_CHAR(d.mover_date,'YYYYMMDD')<(SELECT min(TRIM(TO_CHAR(PLAN_DATE, 'YYYYMMDD'))) PLAN_DATE FROM (SELECT * from ACRM_F_CI_WORK_PLAN WHERE IS_WORK = '1' AND TO_CHAR(PLAN_DATE, 'YYYYMMDD') < TO_CHAR(SYSDATE, 'YYYYMMDD') ORDER BY PLAN_DATE DESC) WHERE ROWNUM < 3) ");
			    sb.append(" and d.CUS_ID  IN (SELECT CUST_ID FROM OCRM_F_SE_CALLREPORT   ");
			    sb.append("	WHERE TO_CHAR(LAST_UPDATE_TM,'YYYYMMDD') IN    ");
			    sb.append("	(SELECT TRIM(TO_CHAR(PLAN_DATE, 'YYYYMMDD')) PLAN_DATE   ");
			    sb.append("  FROM (SELECT *  from ACRM_F_CI_WORK_PLAN   ");
			    sb.append(" WHERE IS_WORK = '1'   ");
			    sb.append("  AND TO_CHAR(PLAN_DATE, 'YYYYMMDD') <  TO_CHAR(SYSDATE, 'YYYYMMDD') ");
			    sb.append("   ORDER BY PLAN_DATE DESC) ");
			    sb.append("  WHERE ROWNUM < 3)) ) where 1=1");
		sql=sb.toString();
		log.info("个金潜在客户自动回收处理sql"+sql);
		try {
			conn=ds.getConnection();
			stmt = conn.createStatement();
			stmt1= conn.createStatement();
			stmt2= conn.createStatement();
			stmt3= conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				cusidStr=rs.getString("CUS_ID");
				backstateStr=rs.getString("BACK_STATE");
				moveruserStr=rs.getString("MOVER_USER");
				custName=rs.getString("CUS_NAME");
				custmgr=rs.getString("CUST_MGR");
				mainbridStr=getOrgidMethods(moveruserStr);
				if("2".equals(backstateStr)){
					String updatesql=" update acrm_f_ci_pot_cus_com  set cust_mgr='"+moveruserStr+"', main_br_id='"+mainbridStr+"', back_state ='0',mover_user='"+moveruserStr+"',mover_date=null,actual_receive_date=to_date('"+time+"','yyyy-mm-dd hh24:mi:ss'),OPERATE_TIME=systimestamp where  cus_id in ('"+cusidStr+"')";
					log.info("个金潜在客户自动回收处理updatesql"+updatesql);
					stmt1.execute(updatesql);
					String  recoveryhis=" insert into ACRM_F_CI_POT_CUS_HIS(cus_id,cus_name,state,mover_user,cust_mgr_before,cust_mgr_after,mover_date) values('"+cusidStr+"','"+custName+"','2','"+moveruserStr+"','"+custmgr+"','"+moveruserStr+"','"+time+"')";
					stmt3.execute(recoveryhis);
				  String  sqlRemind="insert into OCRM_F_WP_REMIND (INFO_ID, USER_ID, MSG_CRT_DATE, REMIND_REMARK, IF_MAIL) values(SEQ_ID.NEXTVAL,'"+custmgr+"',to_date('"+time1+"','yyyy-mm-dd')+1,'个金潜在客户["+custName+"]已被主动回收！','1')";
					log.info("个金潜在客户自动回收处理sqlRemind"+sqlRemind);
				  stmt2.execute(sqlRemind);
					 //邮件提醒客户经理
			        doEmailRemind(custmgr,"个金潜在客户["+custName+"]已被主动回收，谢谢！");
				
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, null);
			JdbcUtil.close(null, stmt1, null);
			JdbcUtil.close(null, stmt3, null);
			JdbcUtil.close(null, stmt2, conn);
		}
		log.info("个金潜在客户自动回收处理任务-------结束");
			
	}
	
	   //接受人所属机构ID
	   public String getOrgidMethods(String custmgr){
		    String Orgid="";
		    Connection  connection=null;
	  		Statement stmt=null;
	  		ResultSet result=null;
	   	try{
	   		 connection = ds.getConnection();
			 stmt = connection.createStatement();
	   		 String sql="select ac.org_id from ADMIN_AUTH_ACCOUNT ac where ac.account_name='"+custmgr+"'";
	   		 result = stmt.executeQuery(sql);
	   		 while(result.next()){
	   			Orgid=result.getString("org_id");
	   		 }	
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}finally{
	   		JdbcUtil.close(result, stmt, connection);
	   	}
	   	return Orgid;
	   }
	   
	   public void doEmailRemind(String custMgr,String remindremark){
		   String email= ""; 
		   String username="";
		   List<Object> listTemp1=getResultStr("select a.EMAIL from ADMIN_AUTH_ACCOUNT a  where a.account_name='"+custMgr+"' and trim(a.EMAIL) is not null");
		   if(listTemp1!=null&&listTemp1.size()>0){
			   email=(String) listTemp1.get(0);
		   }
		   List<Object> listTemp2=getResultStr("select a.USER_NAME from ADMIN_AUTH_ACCOUNT a  where a.account_name='"+custMgr+"' and trim(a.EMAIL) is not null");
		   if(listTemp2!=null&&listTemp2.size()>0){
			   username=(String) listTemp2.get(0);
		   }
		   StringBuffer remindremarkstr=new StringBuffer("尊敬的："+username+"\r\n"+"<br/>");
		   remindremarkstr.append(remindremark);
		   try {
				MailClient.getInstance().sendMsg(email, "个金潜在客户主动回收提醒信息通知", remindremarkstr.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
	   }
	   
	   //根据条件查询
	   public List<Object> getResultStr(String sql){
	   	List<Object> List = new ArrayList<Object>();
	   	Connection  connection=null;
	   	Statement stmt=null;
	   	ResultSet result=null;
	   	try{
	  				 connection = ds.getConnection();
	  				 stmt = connection.createStatement();
	  				 result = stmt.executeQuery(sql);
	  				String ResultStr="";
	  			    while (result.next()){
	  			    	ResultStr = result.getString(1);
	  			    	List.add(ResultStr);
	  			    }
	  			 return List;
	  		}catch(Exception e){
	  			e.printStackTrace();
	  		}finally{
	  			JdbcUtil.close(result, stmt, connection);
	  		}
			return null;
	      }
	
}
