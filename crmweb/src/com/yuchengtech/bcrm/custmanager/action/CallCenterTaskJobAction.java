package com.yuchengtech.bcrm.custmanager.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bcrm.sales.message.action.MailClient;
import com.yuchengtech.crm.constance.JdbcUtil;

public class CallCenterTaskJobAction {

	private static Logger log = Logger.getLogger(CallCenterTaskJobAction.class);
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	public void remind() {
		log.info("处理任务开始");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		System.out.println("世茂-翡翠首府");
		int num12 = 0;
		int num24 = 0;
		int all = 0;
		List emails = new ArrayList();
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = "select t.email from admin_auth_account t"
					+ " left join ADMIN_AUTH_ACCOUNT_ROLE t1 on t.id=t1.account_id"
					+ " left join ADMIN_AUTH_ROLE t2 on t1.role_id=t2.id"
					+ " where t2.role_code in ('R306','R308') and t.email is not null";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				emails.add(rs.getString("email"));
			}
			sql = " select  r.num12,re2.num24 from"
					+ " (select count(t.cust_id) as num12"
					+ " from OCRM_F_CI_CALLCENTER_UPHIS t"
					+ " where t.appr_flag = '0'"
					+ " and (sysdate - to_date(t.update_date, 'yyyy-mm-dd hh24:mi:ss'))*24 > 12) r,"
					+ " (select count(t.cust_id) as num24"
					+ " from OCRM_F_CI_CALLCENTER_UPHIS t"
					+ " where t.appr_flag = '0'"
					+ " and (sysdate - to_date(t.update_date, 'yyyy-mm-dd hh24:mi:ss'))*24 > 24) re2";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				num12 = rs.getInt("num12");
				num24 = rs.getInt("num24");
			}
			all = num12 + num24;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, stmt, conn);
		}
		if (num12 > 0 || num24 > 0) {
			for (int i = 0; i < emails.size(); i++) {
				try {
					MailClient.getInstance().sendMsg(
							emails.get(i).toString(),
							"未复核案件通知",
							"未复核案件共计" + all + "件，超过12小时未复核有" + num12
									+ "件，超过24小时以上未复核有" + num24 + "件");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
