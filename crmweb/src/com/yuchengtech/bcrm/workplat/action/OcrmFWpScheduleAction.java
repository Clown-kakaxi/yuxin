package com.yuchengtech.bcrm.workplat.action;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.workplat.model.OcrmFWpSchedule;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleCreditService;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleLoanCheckService;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleMonthService;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleOtherService;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleService;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleVisitService;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleWeekService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.SystemConstance;
import com.yuchengtech.crm.exception.BizException;

/**
 * 
 * 日程处理的action
 *  * @author luyy
 * @since 2014-06-23
 */

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/ocrmFWpSchedule", results = { @Result(name = "success", type = "json")})
public class OcrmFWpScheduleAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	 @Autowired
	 private  OcrmFWpScheduleService  service;
	 @Autowired
	 private  OcrmFWpScheduleVisitService  service1;
	 @Autowired
	 private  OcrmFWpScheduleWeekService  service2;
	 @Autowired
	 private  OcrmFWpScheduleMonthService  service3;
	 @Autowired
	 private  OcrmFWpScheduleLoanCheckService  service4;
	 @Autowired
	 private  OcrmFWpScheduleCreditService  service5;
	 @Autowired
	 private  OcrmFWpScheduleOtherService  service6;
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	 @Autowired
		public void init(){
		  	model = new OcrmFWpSchedule(); 
			setCommonService(service);
			//新增修改删除记录是否记录日志,默认为false，不记录日志
			needLog=true;
		}
	/**
	 *信息查询SQL
	 */
	public void prepare() {
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 String tableName = "dual";
    	 if("DB2".equals(SystemConstance.DB_TYPE)){
    		 tableName = "SYSIBM.SYSDUMMY1";
    	 }
    	 StringBuilder sb = new StringBuilder("select 1 from "+tableName);
    	 SQL=sb.toString();
    	 datasource = ds;
	}
	
	public String querySchedule(){
		try{
			ActionContext ctx = ActionContext.getContext();
		   	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		   	String start = request.getParameter("start");
		   	String end = request.getParameter("end");
		   	
		   	//SCH_ID 必须不同，否则日程列表上不能正常显示多条
		   	StringBuilder sb = new StringBuilder("SELECT * FROM (");
		   	sb.append(" select '1' as ALL_DAY, v.v_id as SCH_ID,T.USER_ID,t.sch_date,'客户拜访(' || v.cust_name || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE t left join OCRM_F_WP_SCHEDULE_VISIT v on v.sch_id = t.sch_id where T.VISIT_COUNT > 0");
		   	sb.append(" union select '1' as ALL_DAY,T.SCH_ID + 2 as SCH_ID,T.USER_ID,t.sch_date,'周工作计划(' || T.WEEK_COUNT || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE T where T.WEEK_COUNT > 0");
	    	sb.append(" union select '1' as ALL_DAY,T.SCH_ID + 3 as SCH_ID,T.USER_ID,t.sch_date,'月工作计划(' || T.MONTH_COUNT || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE T where T.MONTH_COUNT > 0");
	    	sb.append(" union select '1' as ALL_DAY,T.SCH_ID + 4 as SCH_ID,T.USER_ID,t.sch_date,'贷后检查任务(' || T.LOAN_CHECK_COUNT || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE T where T.LOAN_CHECK_COUNT > 0");
	    	sb.append(" union select '1' as ALL_DAY,T.SCH_ID + 5 as SCH_ID,T.USER_ID,t.sch_date,'授信任务(' || T.CREDIT_COUNT || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE T where T.CREDIT_COUNT > 0");
	    	sb.append(" union select '1' as ALL_DAY,T.SCH_ID + 6 as SCH_ID,T.USER_ID,t.sch_date,'其他任务(' || T.OTHER_COUNT || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE T where T.OTHER_COUNT > 0");
	    	sb.append(" union select '1' as ALL_DAY,t.call_id as SCH_ID,t.last_update_user as USER_ID ,t.next_visit_date as sch_date ,'客户拜访(' || t.cust_name || ')' AS SCHEDULE_COUNT from OCRM_F_SE_CALLREPORT t where trim(next_visit_date) is not null");
	    	sb.append(") where USER_ID = '"+auth.getUserId()+"' and sch_date >= to_date('"+start+"', 'yyyyMMdd') and sch_date <= to_date('"+end+"', 'yyyyMMdd')");
	        
	    	QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
	        if(this.json!=null)
        		this.json.clear();
        	else 
        		this.json = new HashMap<String,Object>(); 
        	this.json.put("json",query.getJSON());
		}catch(Exception e){
	    	e.printStackTrace();
	    	throw new BizException(1,2,"1002",e.getMessage());
	    }
	    return "success";
	}
	
	/**
	 * 首页磁贴工作日程查询
	 */
	public String queryScheduleTwo(){
		try{
			ActionContext ctx = ActionContext.getContext();
		   	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		   	
		   	//SCH_ID 必须不同，否则日程列表上不能正常显示多条
		   	StringBuilder sb = new StringBuilder("SELECT ALL_DAY,SCH_ID,USER_ID,to_char(sch_date,'yyyy-MM-dd') as sch_date,SCHEDULE_COUNT FROM (");
		   	sb.append(" select '1' as ALL_DAY, v.v_id as SCH_ID,T.USER_ID,t.sch_date,'客户拜访(' || v.cust_name || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE t left join OCRM_F_WP_SCHEDULE_VISIT v on v.sch_id = t.sch_id where T.VISIT_COUNT > 0");
		   	sb.append(" union select '1' as ALL_DAY,T.SCH_ID + 2 as SCH_ID,T.USER_ID,t.sch_date,'周工作计划(' || T.WEEK_COUNT || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE T where T.WEEK_COUNT > 0");
	    	sb.append(" union select '1' as ALL_DAY,T.SCH_ID + 3 as SCH_ID,T.USER_ID,t.sch_date,'月工作计划(' || T.MONTH_COUNT || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE T where T.MONTH_COUNT > 0");
	    	sb.append(" union select '1' as ALL_DAY,T.SCH_ID + 4 as SCH_ID,T.USER_ID,t.sch_date,'贷后检查任务(' || T.LOAN_CHECK_COUNT || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE T where T.LOAN_CHECK_COUNT > 0");
	    	sb.append(" union select '1' as ALL_DAY,T.SCH_ID + 5 as SCH_ID,T.USER_ID,t.sch_date,'授信任务(' || T.CREDIT_COUNT || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE T where T.CREDIT_COUNT > 0");
	    	sb.append(" union select '1' as ALL_DAY,T.SCH_ID + 6 as SCH_ID,T.USER_ID,t.sch_date,'其他任务(' || T.OTHER_COUNT || ')' AS SCHEDULE_COUNT from OCRM_F_WP_SCHEDULE T where T.OTHER_COUNT > 0");
	    	sb.append(" union select '1' as ALL_DAY,t.call_id as SCH_ID,t.last_update_user as USER_ID ,t.next_visit_date as sch_date ,'客户拜访(' || t.cust_name || ')' AS SCHEDULE_COUNT from OCRM_F_SE_CALLREPORT t where trim(next_visit_date) is not null");
	    	sb.append(") where USER_ID = '"+auth.getUserId()+"'  and  to_char(sch_date,'yyyy/mm/dd')>=to_char(sysdate,'yyyy/mm/dd') order by sch_date ");
	        
	    	QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
	        if(this.json!=null)
        		this.json.clear();
        	else 
        		this.json = new HashMap<String,Object>(); 
        	this.json.put("json",query.getJSON());
		}catch(Exception e){
	    	e.printStackTrace();
	    	throw new BizException(1,2,"1002",e.getMessage());
	    }
	    return "success";
	}
	/**
	 * 得到SchId，根据userId和data查询，没有则新增
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public void getSchId() throws ParseException, IOException{
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String schId = "";
   	    String date = request.getParameter("date");
   	    String userId = request.getParameter("userId");
   	    
   	    List<Object[]> list =  service.getBaseDAO().findByNativeSQLWithIndexParam(" select SCH_ID,USER_ID from OCRM_F_WP_SCHEDULE where USER_ID='"+userId+"' " +
   	    		"and SCH_DATE = to_date('"+date+"','YYYY-MM-dd')");
   	    
   	    if(list != null && list.size()>0){
   	    	schId = list.get(0)[0].toString();
   	    }else{
   	    	OcrmFWpSchedule sch = new OcrmFWpSchedule();
   	    	sch.setUserId(userId);
   	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   	    	sch.setSchDate(sdf.parse(date));
   	    	sch.setCreditCount(BigDecimal.ZERO);
   	    	sch.setLoanCheckCount(BigDecimal.ZERO);
   	    	sch.setMonthCount(BigDecimal.ZERO);
   	    	sch.setOtherCount(BigDecimal.ZERO);
   	    	sch.setVisitCount(BigDecimal.ZERO);
   	    	sch.setWeekCount(BigDecimal.ZERO);
   	    	
   	    	sch = (OcrmFWpSchedule)service.save(sch);
   	    	schId = sch.getSchId().toString();
   	    }
	   	 HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
		 	response.setCharacterEncoding("UTF-8");
			response.getWriter().write(schId);
			response.getWriter().flush();
		}
	//任务下发
	public void jobXD() throws IOException{
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String num = "";
   	    String schId = request.getParameter("schId");
   	    BigDecimal id = BigDecimal.valueOf(Long.parseLong(schId));
   	    Map<String, Object> values = new HashMap<String, Object> ();
   	    int num1 = service.batchUpdateByName(" update OcrmFWpScheduleVisit p set p.visitStat='ap' where  p.visitStat='xf' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    int num2 = service.batchUpdateByName(" update OcrmFWpScheduleWeek p set p.stat='ap' where  p.stat='xf' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    int num3 = service.batchUpdateByName(" update OcrmFWpScheduleMonth p set p.stat='ap' where  p.stat='xf' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    int num4 = service.batchUpdateByName(" update OcrmFWpScheduleLoanCheck p set p.stat='ap' where  p.stat='xf' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    int num5 = service.batchUpdateByName(" update OcrmFWpScheduleCredit p set p.stat='ap' where  p.stat='xf' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    int num6 = service.batchUpdateByName(" update OcrmFWpScheduleOther p set p.stat='ap' where  p.stat='xf' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    
   	    num = num1+"#"+num2+"#"+num3+"#"+num4+"#"+num5+"#"+num6;
   	    HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
	 	response.setCharacterEncoding("UTF-8");
		response.getWriter().write(num);
		response.getWriter().flush();
   	    
	}
	
	//任务确认
	public void jobQR() throws IOException{
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String num = "";
   	    String schId = request.getParameter("schId");
   	    BigDecimal id = BigDecimal.valueOf(Long.parseLong(schId));
   	    Map<String, Object> values = new HashMap<String, Object> ();
   	    int num1 = service.batchUpdateByName(" update OcrmFWpScheduleVisit p set p.visitStat='qr' where  p.visitStat='wc' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    int num2 = service.batchUpdateByName(" update OcrmFWpScheduleWeek p set p.stat='qr' where  p.stat='wc' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    int num3 = service.batchUpdateByName(" update OcrmFWpScheduleMonth p set p.stat='qr' where  p.stat='wc' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    int num4 = service.batchUpdateByName(" update OcrmFWpScheduleLoanCheck p set p.stat='qr' where  p.stat='wc' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    int num5 = service.batchUpdateByName(" update OcrmFWpScheduleCredit p set p.stat='qr' where  p.stat='wc' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    int num6 = service.batchUpdateByName(" update OcrmFWpScheduleOther p set p.stat='qr' where  p.stat='wc' and p.arangeId='"+auth.getUserId()+"' and p.schId="+id, values);
   	    
   	    num = num1+"#"+num2+"#"+num3+"#"+num4+"#"+num5+"#"+num6;
   	    HttpServletResponse response =(HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
	 	response.setCharacterEncoding("UTF-8");
		response.getWriter().write(num);
		response.getWriter().flush();
   	    
	}
	public void batchDel(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   	 String schId = request.getParameter("schId");
	   	 String type = request.getParameter("type");
	   	 String ids = request.getParameter("ids");
	   	 
	   	OcrmFWpSchedule sch = (OcrmFWpSchedule)service.find(Long.parseLong(schId));
	   	 String id[] = ids.split(",");
	   	 for(String temp:id){
	   		 if("1".equals(type)){
	   			service1.remove(Long.parseLong(temp));
	   			//减少任务数
	   			sch.setVisitCount(sch.getVisitCount().subtract(BigDecimal.ONE));
	   			service.save(sch);
	   		 }
	   		if("2".equals(type)){
	   			service2.remove(Long.parseLong(temp));
	   			//减少任务数
	   			sch.setWeekCount(sch.getWeekCount().subtract(BigDecimal.ONE));
	   			service.save(sch);
	   		 }
	   		if("3".equals(type)){
	   			service3.remove(Long.parseLong(temp));
	   			//减少任务数
	   			sch.setMonthCount(sch.getMonthCount().subtract(BigDecimal.ONE));
	   			service.save(sch);
	   		 }
	   		if("4".equals(type)){
	   			service4.remove(Long.parseLong(temp));
	   			//减少任务数
	   			sch.setLoanCheckCount(sch.getLoanCheckCount().subtract(BigDecimal.ONE));
	   			service.save(sch);
	   		 }
	   		if("5".equals(type)){
	   			service5.remove(Long.parseLong(temp));
	   			//减少任务数
	   			sch.setCreditCount(sch.getCreditCount().subtract(BigDecimal.ONE));
	   			service.save(sch);
	   		 }
	   		if("6".equals(type)){
	   			service6.remove(Long.parseLong(temp));
	   			//减少任务数
	   			sch.setOtherCount(sch.getOtherCount().subtract(BigDecimal.ONE));
	   			service.save(sch);
	   		 }
	   	 }
		
	}
}


