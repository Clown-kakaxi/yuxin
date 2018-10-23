package com.yuchengtech.bcrm.customer.potentialSme.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.constance.JdbcUtil;

public class PipelineTaskAction extends CommonAction {

	private static Logger log = Logger.getLogger(PipelineTaskJobAction.class);

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	/**
	 * pipeline合作意向阶段的监控超时处理
	 */
	public void pipelineTask() {
		log.info("处理任务开始");
		// 合作意向信息阶段
		pipelineIntentE();
		log.info("处理任务结束");
	}

	/**
	 * 合作意向信息阶段 时间超过90天置为失效案件
	 */
	public void pipelineIntentE() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = "select * from OCRM_F_MK_PIPELINE P where 1=1 and P.NOW_PROGRESS='2' and (P.IF_NEXT = '2' or p.If_Next IS NULL) AND (P.CASE_STATE IS NULL OR P.CASE_STATE NOT IN ('0', '1'))"
					+ " order by P.NOW_PROGRESS,P.FIRST_IN_DATE ASC";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString("ID");
				String custName = rs.getString("CUST_NAME");
				String createDate = rs.getString("FIRST_IN_DATE");
				if (StringUtils.isNotEmpty(createDate)) {
					int totalDays = getTotalDays(createDate);
					if (totalDays >= 90) {
						log.info("合作意向阶段，案件超时" + totalDays + "天，置为无效！");
						log.info(id + "合作意向阶段，" + custName + "案件超时" + totalDays
								+ "天，置为无效！");
						// String updateDate = new
						// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new
						// Date());
						String sql_up = "update OCRM_F_MK_PIPELINE set CASE_STATE = 0, UPDATE_DATE = sysdate where id='"
								+ id + "'";
						updateCase(sql_up);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, stmt, conn);
		}

	}

	/**
	 * 计算案件在合作意向阶段滞留天数（工作日）
	 * 
	 * @param createDate
	 * @return
	 */
	public int getTotalDays(String createDate) {
		int totalDays = 0;
		if (StringUtils.isNotEmpty(createDate)) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = fmt.format(new Date());
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "";
			try {
				Date date = sdf.parse(createDate);
				createDate = fmt.format(date);
				conn = ds.getConnection();
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				sql = " select count(c.plan_date) as counts from ACRM_F_CI_WORK_PLAN c "
						+ "  where 1 = 1 and c.plan_date >= to_date('"
						+ createDate
						+ "', 'yyyy/mm/dd')  "
						+ "  and  c.plan_date <= to_date('"
						+ nowDate
						+ "', 'yyyy/mm/dd') and c.is_work='1' ";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					totalDays = Integer.parseInt(rs.getString("counts"));// 工作日天数
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcUtil.close(rs, stmt, conn);
			}
		}
		return totalDays;
	}

	/**
	 * 执行修改案件状态为“无效”
	 * 
	 * @param id
	 */
	public void updateCase(String sql) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.execute(sql);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, stmt, conn);
		}
	}
}
