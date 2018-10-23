package com.yuchengtech.bcrm.callReport.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.callReport.model.OcrmFSeCallreport;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.common.LogService;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
/**
 *call report
 * @author agile
 *
 */
@Service
@SuppressWarnings("serial")
public class OcrmFSeCallreportService extends CommonService {
	public OcrmFSeCallreportService(){
		JPABaseDAO<OcrmFSeCallreport,Long> baseDao = new JPABaseDAO<OcrmFSeCallreport,Long>(OcrmFSeCallreport.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		OcrmFSeCallreport report = (OcrmFSeCallreport)obj;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String phone = request.getParameter("linkPhone");
		String custId = request.getParameter("custId");
		String identType = request.getParameter("identType");
		String identNo = request.getParameter("identNo");
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		

		//根据Id是否为空判断是新增还是 更改
		if(report.getCallId() == null){
			if(phone != null  && !phone.isEmpty()){
				this.batchUpdateByName("update AcrmFCiCustomer t set t.linkmanTel = '"+phone.replace("-","/")+"' where t.custId = '"+report.getCustId()+"'", null);
			}
			//修改潜在客户证件信息
			if(identType != null || identNo != null){
				this.batchUpdateByName("update AcrmFCiPotCusCom t set t.certType = '"+identType+"',certCode = '"+identNo+"' where t.cusId = '"+custId+"'", null);
			}
			report.setLastUpdateUser(auth.getUserId());
			report.setLastUpdateTm(new Date());
			report.setCreateUser(auth.getUserId());
			OcrmFSeCallreport callrpt=(OcrmFSeCallreport) super.save(report);
			//把拜访日程插入提醒表
			if(report.getNextVisitDate() != null && callrpt.getNextVisitDate().after(new Date())){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				String dt = df.format(callrpt.getNextVisitDate());
				callReportRemind(callrpt.getCallId(),auth.getUserId(),dt,callrpt.getCustName(),0);
			}
			return callrpt;
		}else{
			if(phone != null  && !phone.isEmpty()){
				this.batchUpdateByName("update AcrmFCiCustomer t set t.linkmanTel = '"+phone.replace("-","/")+"' where t.custId = '"+report.getCustId()+"'", null);
			}
			//修改个金潜在客户证件信息
			if(identType != null || identNo != null){
				this.batchUpdateByName("update AcrmFCiPotCusCom t set t.certType = '"+identType+"',certCode = '"+identNo+"' where t.cusId = '"+custId+"'", null);
			}
			//把拜访日程插入提醒表
			if(report.getNextVisitDate() != null && report.getNextVisitDate().after(new Date()) && !checkCallReportRemind(report.getNextVisitDate(),report.getCustName())){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				String dt = df.format(report.getNextVisitDate());
				callReportRemind(report.getCallId(),auth.getUserId(),dt,"",1);
			}
			report.setLastUpdateUser(auth.getUserId());
			report.setCreateUser(auth.getUserId());
//			report.setLastUpdateTm(new Date());
			return super.save(report);
		}
	}
	/**
	 * 把拜访日程插入提醒表
	 */
	public void callReportRemind(Long callId,String userId,String nextDt,String custName, int i){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DataSource ds=LogService.dsOracle ;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if(i==0){
				StringBuffer sb = new StringBuffer(" insert into OCRM_F_WP_REMIND "+
						   " (INFO_ID, USER_ID, MSG_CRT_DATE, REMIND_REMARK, IF_MAIL) " +
						   " select SEQ_ID.NEXTVAL as INFO_ID, "+
						   "        '"+userId+"' as USER_ID, "+
						   "        to_date('"+nextDt+"','yyyy-mm-dd') as MSG_CRT_DATE, "+
						   "        '客户拜访(' ||'"+custName+"'|| ')' as REMIND_REMARK, "+
						   "        '0' as IF_MAIL " +
						   " from dual ");
                stmt.executeUpdate(sb.toString());
			}else{
				StringBuffer sb = new StringBuffer(" insert into OCRM_F_WP_REMIND "+
						   " (INFO_ID, USER_ID, MSG_CRT_DATE, REMIND_REMARK, IF_MAIL) " +
						   " select SEQ_ID.NEXTVAL as INFO_ID, "+
						   "        '"+userId+"' as USER_ID, "+
						   "        to_date('"+nextDt+"','yyyy-mm-dd') as MSG_CRT_DATE, "+
						   "        '客户拜访(' || t.CUST_NAME || ')' as REMIND_REMARK, "+
						   "        '0' as IF_MAIL " +
						   " from OCRM_F_SE_CALLREPORT t "+
						   " where t.CALL_ID = "+callId+" ");
                stmt.executeUpdate(sb.toString());
			}

			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
	}
	/***
	 * 修改时检查callReport提醒时间，如果下次拜访时间没有发生变化时，保存就不执行事件插入邮件发送提醒表
	 * @throws SQLException 
	 */
	public Boolean checkCallReportRemind(Date nextDt,String custName){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Boolean b = null;
		DataSource ds=LogService.dsOracle ;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
				String dt = df.format(nextDt);
				StringBuffer sb = new StringBuffer(" select * from OCRM_F_WP_REMIND " +
												   " where MSG_CRT_DATE = to_date('"+dt+"','yyyy-mm-dd')" +
												   " and USER_ID = '"+auth.getUserId()+"' " +
												   " and trim(REMIND_REMARK) = '客户拜访("+custName+")'");
				rs = stmt.executeQuery(sb.toString());
				b = rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
		}
		
		return b;
	}
}
