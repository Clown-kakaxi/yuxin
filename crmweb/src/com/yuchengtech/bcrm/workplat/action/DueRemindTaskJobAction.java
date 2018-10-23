package com.yuchengtech.bcrm.workplat.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bcrm.sales.message.action.MailClient;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;


public class DueRemindTaskJobAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	/**
	 * 
	 * 各种到期提醒（理财到期提醒，生日到期提醒等等）
	 */
	public void dueRemind(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			StringBuffer sb = new StringBuffer( " select distinct a.ACCOUNT_NAME,a.USER_NAME,a.EMAIL "+
												" from ADMIN_AUTH_ACCOUNT a "+
											    " left join OCRM_F_WP_REMIND b on a.ACCOUNT_NAME = b.user_id "+
											    " where b.MSG_CRT_DATE = to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') "+
											    " and trim(a.EMAIL) is not null "+
											    " and trim(b.REMIND_REMARK) is not null "+
											    " and b.IF_MAIL <> '1' " );
			rs = stmt.executeQuery(sb.toString());
			
			while(rs.next()){
				String accountname = rs.getString("ACCOUNT_NAME");
				String username = rs.getString("USER_NAME");
			    String email = rs.getString("EMAIL");
			    
			    String dueremind = sourchRemind(accountname,username); //根据单个用户查询并美化提醒内容
			    sendEmail(email,dueremind);     //调用发送邮件方法
			    changeStatus(accountname);      //修改数据库状态，置邮件状态为已发送
			}
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
				MailClient.getInstance().sendMsg(email, "客户提醒信息通知", remindremark);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
	/**
	 * 修改数据库状态，置邮件状态为已发送
	 */
	public void changeStatus(String accountname){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			StringBuffer sb = new StringBuffer(" update OCRM_F_WP_REMIND set IF_MAIL = '1' "+
	    		                               " where msg_crt_date = to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') "+
	    		                               " and user_id = '"+accountname+"' ");
			stmt.executeUpdate(sb.toString());
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
	}
    /****
     * 根据用户来查询用户对应提醒事件,并对提醒内容进行美化处理
     */
	public String sourchRemind(String accountname,String username){
		List<HashMap<String, Object>> remind = null;
		QueryHelper query = null;
		StringBuffer sb = new StringBuffer(" select trim(REMIND_REMARK) as REMIND_REMARK from OCRM_F_WP_REMIND " +
				                           " where MSG_CRT_DATE = to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') " +
				                           " and user_id = '"+accountname+"'");
		String dueremind = "尊敬的 "+username+" 您好！以下是您的今日客户信息提醒内容：";
		try {
			query = new QueryHelper(sb.toString(), ds.getConnection());
			remind = (List<HashMap<String, Object>>) query.getJSON().get("data");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i=0;i<remind.size();i++){
			dueremind = dueremind +"\r\n"+"<br/>"+"   "+(i+1)+"、"+(String)remind.get(i).get("REMIND_REMARK");
		}
		return dueremind;
	}
}
