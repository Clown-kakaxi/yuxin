package com.yuchengtech.bcrm.customer.potentialSme.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFCiSmeCaEService;
import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFCiSmeIntentEService;
import com.yuchengtech.bcrm.customer.potentialSme.service.OcrmFCiSmeProspectEService;
import com.yuchengtech.bcrm.sales.message.action.MailClient;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.constance.JdbcUtil;

public class PipelineTaskJobAction extends CommonAction {
	private static Logger log = Logger.getLogger(PipelineTaskJobAction.class);
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private OcrmFCiSmeProspectEService smeProspectEService;
	@Autowired
    private OcrmFCiSmeIntentEService smeIntentEService;
	@Autowired
    private OcrmFCiSmeCaEService smeCaEService;
	
	/**
	 * pipeline各阶段的监控超时处理
	 */
	public void pipeline() {
		log.info("处理任务开始");
		//prospect信息阶段
		smeProspectE();
		//合作意向信息阶段
		smeIntentE();
		//文件收集阶段
		smeCaE();
		//文件及CA准备阶段
		smeCaCo();
		//信用审查阶段
		smeCheckE();
		//核批阶段
		smeApprovlE();
		log.info("处理任务结束");
	}
	
	/**
	 * prospect信息阶段
	 * 时间超过三个月发邮件警告
	 * 超过六个月否决案件
	 */
	public void smeProspectE(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = " select c.*, a.USER_NAME  from OCRM_F_CI_MKT_PROSPECT_C c  left join admin_auth_account a  on c.user_id = a.account_name "+
					"  where 1 = 1  and (a.belong_busi_line = '5' or a.belong_busi_line = '0')  and IF_PIPELINE = '2' " +
					"  ORDER BY c.IF_PIPELINE desc, c.RECORD_DATE asc ";
			rs = stmt.executeQuery(sql);
			int dayNum = getDayNum("2");
			while(rs.next()){
				String recordDate = rs.getString("record_date");  //记录日期
				String pipelineId = rs.getString("pipeline_id");  //pipeline_id
				String rmId = rs.getString("RM_ID");              //客户经理Id
				String custName = rs.getString("CUST_NAME");      //客户名称
				String id = rs.getString("ID");                   //ID
				if(StringUtils.isNotEmpty(recordDate)){
					if(dayNum != 0){
						boolean flag = timeOut(recordDate,dayNum);
						if(flag){
							log.info("prospect信息阶段,案件超时"+dayNum+"天，否决");
							if(StringUtils.isNotEmpty(id)){
								log.info("prospect信息阶段,"+custName+"案件超时"+dayNum+"天，否决");
								String sql1 = "  update OCRM_F_CI_MKT_PROSPECT_C c set c.if_pipeline='0' where c.id = '"+id+"' and pipeline_id ='"+pipelineId+"'";
								updateStatus(sql1);
							}
						}else{
							int dayNum2 = getDayNum("1");
							if(dayNum2 != 0){
								if(StringUtils.isNotEmpty(recordDate)){
									flag = timeOut(recordDate,dayNum2);
									if(flag){
										log.info("prospect信息阶段,案件超时"+dayNum2+"天，警告");
										if(StringUtils.isNotEmpty(rmId)){
											log.info("prospect信息阶段,"+custName+"案件超时"+dayNum2+"天，警告");
											sendEmail("prospect信息阶段",rmId,custName,"1",dayNum2);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
	}
	
	/**
	 * 合作意向信息阶段
	 * 时间超过45天退件
	 */
	public void smeIntentE(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = " select c.*  from OCRM_F_CI_MKT_INTENT_C c left join admin_auth_account a  on c.user_id = a.account_name  "+
					"   where 1 = 1 and (a.belong_busi_line = '5' or a.belong_busi_line = '0') and (c.if_second_step is null  or   c.if_second_step = '2')  " +
					"   ORDER BY c.IF_SECOND_STEP desc, c.RECORD_DATE asc ";
			rs = stmt.executeQuery(sql);
			int dayNum = getDayNum("3");
			while(rs.next()){
				String recordDate = rs.getString("RECORD_DATE");  //记录日期
				String id = rs.getString("ID");                   //ID
				String pipelineId = rs.getString("pipeline_id");  //pipeline_id
				String prospectId = rs.getString("prospect_ID");  //prospect_ID
				String custName = rs.getString("CUST_NAME");      //客户名称
				if(StringUtils.isNotEmpty(recordDate)){
					if(dayNum != 0){
						boolean flag = timeOut(recordDate,dayNum);
						if(flag){
							log.info("合作意向信息阶段,案件超时"+dayNum+"天，退件");
							//从prospect信息阶段新增过来的数据，超时退回
							if(StringUtils.isNotEmpty(prospectId)){
								log.info("合作意向信息阶段,"+custName+"案件超时"+dayNum+"天，退件");
								smeProspectEService.goBack(prospectId);
								if(StringUtils.isNotEmpty(id)){
									String sql2 = " UPDATE OCRM_F_CI_MKT_INTENT_C  SET if_second_step='99' WHERE id ='"+id+"' and pipeline_id ='"+pipelineId+"'";
									updateStatus(sql2);
								}
							}else{
								//在合作意向信息阶段新增的数据，超时否决
								log.info("合作意向信息阶段,"+custName+"案件超时"+dayNum+"天，否决");
								String sql3 = " UPDATE OCRM_F_CI_MKT_INTENT_C  SET if_second_step='0' WHERE id ='"+id+"'  and pipeline_id ='"+pipelineId+"'";
								updateStatus(sql3);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
	}
	
	/**
	 * 文件收集阶段
	 * 时间超过14天退件
	 */
	public void smeCaE(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = " select c.*  from OCRM_F_CI_MKT_CA_C c  left join admin_auth_account a  on c.user_id = a.account_name  "+
					"   where 1 = 1 and (a.belong_busi_line = '5' or a.belong_busi_line = '0')  and (c.IF_THIRD_STEP = '2' or  c.IF_THIRD_STEP is null)  " +
					" and ((c.IF_SUMBIT_CO not like '1' and c.IF_SUMBIT_CO not like '3') or  c.IF_SUMBIT_CO is null) " +
					"   ORDER BY c.IF_THIRD_STEP desc, c.RECORD_DATE asc ";
			rs = stmt.executeQuery(sql);
			int dayNum = getDayNum("4");
			while(rs.next()){
				String recordDate = rs.getString("RECORD_DATE");  //记录日期
				String id = rs.getString("ID");                   //ID
				String pipelineId = rs.getString("pipeline_id");  //pipeline_id
				String intentId = rs.getString("INTENT_ID");    //INTENT_ID
				String custName = rs.getString("CUST_NAME");      //客户名称
				if(StringUtils.isNotEmpty(recordDate)){
					if(dayNum != 0){
						boolean flag = timeOut(recordDate,dayNum);
						if(flag){
							log.info("文件收集阶段,案件超时"+dayNum+"天，退件");
							if(StringUtils.isNotEmpty(intentId)){
								log.info("文件收集阶段,"+custName+"案件超时"+dayNum+"天，退件");
								smeIntentEService.goBack(intentId);
								if(StringUtils.isNotEmpty(id)){
									String sql2 = " UPDATE OCRM_F_CI_MKT_CA_C  SET IF_THIRD_STEP='99' WHERE id ='"+id+"' and pipeline_id ='"+pipelineId+"'";
									updateStatus(sql2);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
	}
	
	/**
	 * 文件及CA准备阶段
	 * 时间超过7天警告
	 */
	public void smeCaCo(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = " select c.*  from OCRM_F_CI_MKT_CA_C c left join admin_auth_account a  on c.user_id = a.account_name  "+
					"   where 1 = 1 and (a.belong_busi_line = '5' or a.belong_busi_line = '0')  and (c.IF_THIRD_STEP ='2' or c.IF_THIRD_STEP is null)  " +
					" and c.CASE_TYPE = '16' and c.IF_SUMBIT_CO = '1' " +
					"    ORDER BY c.IF_THIRD_STEP desc, c.RECORD_DATE asc ";
			rs = stmt.executeQuery(sql);
			int dayNum = getDayNum("5");
			while(rs.next()){
				String recordDate = rs.getString("RECORD_DATE");  //记录日期
				String rmId = rs.getString("RM_ID");              //客户经理Id
				String custName = rs.getString("CUST_NAME");      //客户名称
				if(StringUtils.isNotEmpty(recordDate)){
					if(dayNum != 0){
						boolean flag = timeOut(recordDate,dayNum);
						if(flag){
							log.info("文件及CA准备阶段,案件超时"+dayNum+"天，警告");
							if(StringUtils.isNotEmpty(rmId)){
								log.info("文件及CA准备阶段,"+custName+"案件超时"+dayNum+"天，警告");
								sendEmail("文件及CA准备阶段",rmId,custName,"2",dayNum);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
	}
	
	/**
	 * 信用审查阶段
	 * 和文件及CA准备阶段时间相加
	 * (1)共超时7天警告
	 * (2)超时7天并且补件完成日期小于当前日期退件
	 * (3)超时7天并且补件完成日期为空，信审要求补件日期小于当前日期退件
	 */
	public void smeCheckE(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowDate = sdf.format(new Date());
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = " select c.*  from OCRM_F_CI_MKT_CHECK_C c  left join admin_auth_account a  on c.user_id = a.account_name  "+
					"   where 1 = 1 and (a.belong_busi_line = '5' or a.belong_busi_line = '0') and (c.IF_FOURTH_STEP in ('6') or  c.IF_FOURTH_STEP is null)  " +
					"    ORDER BY c.IF_FOURTH_STEP desc, c.RECORD_DATE asc ";
			rs = stmt.executeQuery(sql);
			int dayNum = getDayNum("6");
			while(rs.next()){
				String id = rs.getString("ID");                            //id
				String recordDate = rs.getString("RECORD_DATE");           //记录日期
				String pipelineId = rs.getString("pipeline_id");           //pipeline_id
				String caId = rs.getString("CA_ID");                       //CA_ID
				String rmId = rs.getString("RM_ID");                       //客户经理Id
				String custName = rs.getString("CUST_NAME");               //客户名称
				String addCaseDate = rs.getString("ADD_CASE_DATE");        //补件完成日期
				String requireCaseDate = rs.getString("REQUIRE_CASE_DATE");//信审要求补件日期
				String caseType = rs.getString("CASE_TYPE");               //案件类型
				if(StringUtils.isNotEmpty(recordDate)){
					String recordDateCa = getSmeCaCo(caId,pipelineId);
					int totalDays = getTotalDays(recordDate) + getTotalDays(recordDateCa);
					if(dayNum != 0){
						if(totalDays >= dayNum){
							log.info("信用审查阶段和文件及CA准备阶段,案件超时"+dayNum+"天，警告");
							if(StringUtils.isNotEmpty(rmId)){
								log.info("信用审查阶段,"+custName+"案件超时"+dayNum+"天，警告");
								sendEmail("信用审查阶段",rmId,custName,"2",dayNum);
							}
							//超时7天并且补件完成日期小于当前日期退件
							if(StringUtils.isNotEmpty(addCaseDate)){
								Date date1 = sdf.parse(addCaseDate);
								Date date2 = sdf.parse(nowDate);
								if(date1.getTime()<date2.getTime()){
									log.info("超时"+dayNum+"天并且补件完成日期小于当前日期退件");
									smeCaEService.goBack(caId,"1");
									//案件类型为抵押贷款退回文件及CA准备阶段
									if("16".equals(caseType)){
										log.info("信用审查阶段,"+custName+"案件超时"+dayNum+"天并且补件完成日期小于当前日期退件");
										smeCaEService.goBack(caId,"2");
									}
									String sql3 = " UPDATE OCRM_F_CI_MKT_CHECK_C  SET IF_FOURTH_STEP='99' WHERE id='"+id+"' and pipeline_id ='"+pipelineId+"'";
									updateStatus(sql3);
								}
							}else{//超时7天并且补件完成日期为空，信审要求补件日期小于当前日期退件
								if (StringUtils.isNotEmpty(requireCaseDate)) {
									Date date1 = sdf.parse(requireCaseDate);
									Date date2 = sdf.parse(nowDate);
									if(date1.getTime()<date2.getTime()){
										log.info("信用审查阶段,"+custName+"案件超时"+dayNum+"天并且补件完成日期为空，信审要求补件日期小于当前日期退件");
										smeCaEService.goBack(caId,"1");
										//案件类型为抵押贷款退回文件及CA准备阶段
										if("16".equals(caseType)){
											smeCaEService.goBack(caId,"2");
										}
										String sql3 = " UPDATE OCRM_F_CI_MKT_CHECK_C  SET IF_FOURTH_STEP='99' WHERE id='"+id+"' and pipeline_id ='"+pipelineId+"'";
										updateStatus(sql3);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
	}
	
	/**
	 * 核批阶段
	 * 超过22天，警告
	 */
	public void smeApprovlE(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = " select c.*  from OCRM_F_CI_MKT_APPROVL_C c left join admin_auth_account a  on c.user_id = a.account_name  "+
					"   where 1 = 1 and (a.belong_busi_line = '5' or a.belong_busi_line = '0') and (c.IF_FIFTH_STEP ='6' or (c.IF_FIFTH_STEP is null) or  (c.if_fifth_step = '1' and c.if_sure = '0')) " +
					"    ORDER BY c.IF_FIFTH_STEP desc, c.RECORD_DATE asc ";
			rs = stmt.executeQuery(sql);
			int dayNum = getDayNum("7");
			while(rs.next()){
				String recordDate = rs.getString("RECORD_DATE");  //记录日期
				String custName = rs.getString("CUST_NAME");      //客户名称
				String rmId = rs.getString("RM_ID");              //客户经理ID
				if(StringUtils.isNotEmpty(recordDate)){
					if(dayNum != 0){
						boolean flag = timeOut(recordDate,dayNum);
						if(flag){
							log.info("核批阶段,案件超时"+dayNum+"天，警告");
							if(StringUtils.isNotEmpty(rmId)){
								log.info("核批阶段,"+custName+"案件超时"+dayNum+"天，警告");
								sendEmail("核批阶段",rmId,custName,"2",dayNum);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
	}
	
	/**
	 * 获取文件及CA准备阶段的创建时间
	 * @param caId
	 * @param pipelineId
	 * @return
	 */
	public String getSmeCaCo(String caId,String pipelineId){
		String recordDate = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql = "  select c.*  from OCRM_F_CI_MKT_CA_C c    "+
					" where c.id ='"+caId+"' and c.pipeline_id='"+pipelineId+"'" +
					" and c.IF_THIRD_STEP ='1' and c.CASE_TYPE = '16' and c.IF_SUMBIT_CO = '1'  " ;
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				recordDate = rs.getString("RECORD_DATE");  //记录日期
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
		return recordDate;
	}
	
	/**
	 * 执行修改状态sql文
	 * @param sql
	 */
	public void updateStatus(String sql){
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
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
    }
	
	/**
	 * 发送邮件
	 * @param custName
	 * @param flag
	 * @param days
	 */
	public void sendEmail(String sme,String rmId,String custName,String flag,int days){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sb = "";
		if(StringUtils.isNotEmpty(flag) && days != 0){
			try {
				conn = ds.getConnection();
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				sb = " select t2.role_name,t.user_name,t.account_name,t.user_code,t.email  from admin_auth_account t  " +
						" left join ADMIN_AUTH_ACCOUNT_ROLE t1 on t.id=t1.account_id " +
		    			" left join ADMIN_AUTH_ROLE t2 on t1.role_id=t2.id where 1=1 ";
				if("1".equals(flag)){
					if(StringUtils.isNotEmpty(rmId)){
						sb += " and t.account_name = '"+rmId+"'  ";
					}
				}else if("2".equals(flag)){
					sb += " and  t2.role_code in ('R121') and t.email is not null  ";
				}
		    	rs = stmt.executeQuery(sb);
		    	while(rs.next()){
		    		String email = rs.getString("email");//邮件地址
		    		if(StringUtils.isNotEmpty(email)){
		    			String body = sme + "," + custName + "案件逾期"+days+"天未处理，请知悉。";
						MailClient.getInstance().sendMsg(email, "逾期通知", body);
					}
		    	}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				JdbcUtil.close(rs, stmt, conn);
			}
		}
    }
	
	public boolean timeOut(String creatDate,int days){
		boolean flag = false;
		if(StringUtils.isNotEmpty(creatDate)){
			int totalDays = getTotalDays(creatDate);
			if(days != 0){
				if(totalDays >= days){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	public int getDayNum(String smeFlag){
		int dayNum = 0;
		Connection conn = null; 
		Statement stmt = null;
		ResultSet rs = null;
		String sb = "";
		if(StringUtils.isNotEmpty(smeFlag)){
			try {
				conn = ds.getConnection();
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				sb = " select t.day_num from  OCRM_F_CI_MKT_TIMER_C t where t.flag='"+smeFlag+"' ";
		    	rs = stmt.executeQuery(sb);
		    	while(rs.next()){
		    		String dayNumStr = rs.getString("day_num");//不同阶段的时间限制（天数）
		    		if(StringUtils.isNotEmpty(dayNumStr)){
		    			dayNum = Integer.parseInt(dayNumStr);
					}
		    	}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				JdbcUtil.close(rs, stmt, conn);
			}
		}
		return dayNum;
	}
	
	public int getTotalDays(String creatDate){
		int totalDays = 0;
		if(StringUtils.isNotEmpty(creatDate)){
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = fmt.format(new Date());
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "";
			try {
				Date date = sdf.parse(creatDate);
				creatDate = fmt.format(date);
				conn = ds.getConnection();
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				sql = " select count(c.plan_date) as counts from ACRM_F_CI_WORK_PLAN c "+
						"  where 1 = 1 and c.plan_date >= to_date('"+creatDate+"', 'yyyy/mm/dd')  " +
						"  and  c.plan_date <= to_date('"+nowDate+"', 'yyyy/mm/dd') and c.is_work='1' ";
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					totalDays = Integer.parseInt(rs.getString("counts"));//工作日天数
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				JdbcUtil.close(rs, stmt, conn);
			}
		}
		return totalDays;
	}

}
