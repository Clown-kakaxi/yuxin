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

import com.yuchengtech.bcrm.sales.message.action.MailClient;
import com.yuchengtech.crm.constance.JdbcUtil;
/**
 * 个金潜在客户转正式客户 查询定时任务
 * 2016-03-02
 * 
 * @author mams
 *
 */
public class LatentCusFormalQueryJobAction {
	private static Logger log = Logger.getLogger(LatentCusFormalQueryJobAction.class);
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	/**
	 * 个金潜在客户转正式客户 查询定时任务
	 * 先用 证件号+证件类型匹配 （不为空）
	 * 若 证件号+证件类型为空 则用手机号码匹配 匹配联系表 联系类型为102或209的手机号
	 */
	public void turnFormalCusNotice(){
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMddHHmmss");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String cusId=null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			log.info("个金潜在客户查询已转为正式客户提醒定时任务--开始");
			StringBuffer sb = new StringBuffer( " SELECT D.CUS_ID,D.CUS_NAME,D.CUST_MGR,D.CERT_TYPE,D.CERT_CODE,D.CALL_NO,AC.USER_NAME,AC.EMAIL,D.FORMAL_CUST_FLAG  FROM  ACRM_F_CI_POT_CUS_COM D LEFT JOIN  ADMIN_AUTH_ACCOUNT AC ON  D.CUST_MGR=AC.ACCOUNT_NAME WHERE D.CUST_TYPE='2' " );
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				String customerId=null;
				 cusId = rs.getString("CUS_ID");
				String cusName = rs.getString("CUS_NAME");
				String certType = rs.getString("CERT_TYPE");
				String certCode = rs.getString("CERT_CODE");
				String callNo = rs.getString("CALL_NO");
				String custMgr = rs.getString("CUST_MGR");
				String userName = rs.getString("USER_NAME");
				String email = rs.getString("EMAIL");
				String formalCustFlag = rs.getString("FORMAL_CUST_FLAG");
				if(certType!=null&&!"".equals(certType)&&certCode!=null&&!"".equals(certCode)){  
					String sql="select cust_id from acrm_f_ci_customer c where  c.ident_type='"+certType+"' and c.ident_no='"+certCode+"' and c.cust_type='2' ";
					List<Object> list=getResultStr(sql);
					if(list!=null && list.size()==1){
						customerId=(String) list.get(0);
					}
					
				}else if(callNo!=null&&!"".equals(callNo)){ 
					String sql=" select c.cust_id  from acrm_f_ci_customer c  left  join ACRM_F_CI_CONTMETH t on c.cust_id=t.cust_id  where  replace(t.contmeth_info,substr(t.contmeth_info,0,instr(t.contmeth_info,'-',1,LENGTH(REGEXP_REPLACE(REPLACE(t.contmeth_info, '-', '@'),  '[^@]+',  '')))))=replace('"+callNo+"',substr('"+callNo+"',0,instr('"+callNo+"','-',1,2))) ";
					List<Object> list=getResultStr(sql);
					if(list!=null && list.size()==1){
						customerId=(String) list.get(0);
					}
				}
				
				if(customerId!=null&&!"".equals(customerId)){
					String sql="update ACRM_F_CI_POT_CUS_COM pc set pc.formal_cust_flag='2', pc.cus_formalid='"+customerId+"' where pc.cus_id='"+cusId+"'";
					String sqlRemind="";
					if(email!=null&&!"".equals(email)&&"1".equals(formalCustFlag)){
						String remindremark="尊敬的："+userName+"\r\n"+"<br/>"+"个金潜在客户："+cusName+"已经转为正式客户！";
						sendEmail(email,remindremark);
						SimpleDateFormat sdfs1 = new SimpleDateFormat("yyyy-MM-dd");
						 sqlRemind="insert into OCRM_F_WP_REMIND (INFO_ID, USER_ID, MSG_CRT_DATE, REMIND_REMARK, IF_MAIL) values(SEQ_ID.NEXTVAL,'"+custMgr+"',to_date('"+sdfs1.format(new Date())+"','yyyy-mm-dd')+1,'个金潜在客户"+cusName+"已经转为正式客户！','1')";
						 updateExecSql(sql);
						 updateExecSql(sqlRemind);
						 log.info("个金潜在客户查询已转为正式客户提醒定时任务--添加提醒记录"+sqlRemind);
					}
				}
			    
			}
			log.info("个金潜在客户查询已转为正式客户提醒定时任务--结束");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
	}
	
	
	/**
	 * 发送邮件
	 * @param email
	 * @param remindremark
	 */
	public void sendEmail(String email,String remindremark){
			try {
				MailClient.getInstance().sendMsg(email, "个金潜在客户转正提醒通知", remindremark);
			} catch (Exception e) {
				e.printStackTrace();
			}
    }
	
	/**
	 * 查询
	 * @param sql
	 * @return
	 */
	public List<Object> getResultStr(String sql){
	   	List<Object> List = new ArrayList<Object>();
	   	Connection  connection=null;
	   	Statement stmts=null;
	   	ResultSet result=null;
	   	try{
	  				 connection = ds.getConnection();
	  				 stmts = connection.createStatement();
	  				 result = stmts.executeQuery(sql);
	  				String ResultStr="";
	  			    while (result.next()){
	  			    	ResultStr = result.getString(1);
	  			    	List.add(ResultStr);
	  			    }
	  			 return List;
	  		}catch(Exception e){
	  			e.printStackTrace();
	  		}finally{
	  			JdbcUtil.close(result, stmts, connection);
	  		}
			return null;
	      }
	
	   /**
	    * 执行sql
	    * @param sql
	    */
	   public void updateExecSql(String sql){
		   	Connection  conne=null;
		   	Statement stm=null;
		   	ResultSet results=null;
		   	try{
		  				 conne = ds.getConnection();
		  				 conne.setAutoCommit(false);
		  				 stm = conne.createStatement();
		  				 stm.executeUpdate(sql);
		  				 conne.commit();
		  		}catch(Exception e){
		  			try {
						conne.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		  			e.printStackTrace();
		  		}finally{
		  			JdbcUtil.close(results, stm, conne);
		  		}
		      }
}
