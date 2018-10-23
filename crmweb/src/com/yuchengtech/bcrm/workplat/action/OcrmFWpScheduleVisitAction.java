package com.yuchengtech.bcrm.workplat.action;


import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
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
import com.yuchengtech.bcrm.workplat.model.OcrmFWpScheduleVisit;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleService;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleVisitService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 
 * 日程处理-客户拜访 action
 *  * @author luyy
 * @since 2014-06-24
 */

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/ocrmFWpScheduleV", results = { @Result(name = "success", type = "json")})
public class OcrmFWpScheduleVisitAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	 @Autowired
	 private  OcrmFWpScheduleVisitService  service;
	 
	 @Autowired
	 private  OcrmFWpScheduleService  sservice;
	 
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	 @Autowired
		public void init(){
		  	model = new OcrmFWpScheduleVisit(); 
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
    	 String schId = request.getParameter("schId");
    	 String userId = request.getParameter("userId");
//    	 String start = request.getParameter("start");
//		 String end = request.getParameter("end");
		 String date = request.getParameter("date");
    	 StringBuilder sb = new StringBuilder(" select v.*,a.user_name as arange_name" +
    	 		" from OCRM_F_WP_SCHEDULE_VISIT v,admin_auth_account a where v.ARANGE_ID = a.account_name and SCH_ID='"+schId+"'" );
    	 if(userId.equals(auth.getUserId())){//自己查看：所有任务
    		 sb.append(" and VISIT_STAT <>'dxf'"); 
    	 }else{//看别人的：当前查看人创建的和被查看人自主创建的任务
    		 sb.append(" and (ARANGE_ID = '"+auth.getUserId()+"'  or ARANGE_ID = '"+userId+"')"); 
    	 }
    	 sb.append(" union "+
     			" (select t.CALL_ID as V_ID,0 as SCH_ID,b.CUST_TYPE,t.CUST_ID,t.CUST_NAME,t.next_visit_way as VISIT_TYPE,t.link_phone as PHONE, "+
     			" t.last_update_user as VISITOR,'' as VISIT_STAT,'' as VISIT_NOTE,t.last_update_user as ARANGE_ID,t.last_update_user as USER_ID, "+
     			" '' as USER_NAME,t.next_visit_date as SCH_START_TIME,t.next_visit_date as SCH_EDN_TIME ,'' as arange_name from  OCRM_F_SE_CALLREPORT t "+ 
     			" left join (select distinct cust_id,cust_type from ACRM_F_CI_CUSTOMER "+
                " union select cus_id as cust_id,cust_type from ACRM_F_CI_POT_CUS_COM ) b on t.cust_id = b.cust_id "+
                " where trim(next_visit_date) is not null and last_update_user = '"+auth.getUserId()+"' " +
     			" and t.next_visit_date = to_date('"+date+"','yyyy-MM-dd'))");
     	addOracleLookup("CUST_TYPE", "XD000080");
     	addOracleLookup("VISIT_TYPE", "VISIT_TYPE");
     	addOracleLookup("VISIT_STAT", "VISIT_STAT");
		SQL=sb.toString();
		datasource = ds;
	}
	
	//保存
	public void save(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 String schId = request.getParameter("schIds");
    	 String userId = request.getParameter("userId");
    	 
    	 if(((OcrmFWpScheduleVisit)model).getVId() == null ){//新增的时候
    		 ((OcrmFWpScheduleVisit)model).setSchId(BigDecimal.valueOf(Long.parseLong(schId)));
        	 if(userId.equals(auth.getUserId())){//自己创建任务
        		 ((OcrmFWpScheduleVisit)model).setVisitStat("ap");
        	 }else{//为他人创建任务
        		 ((OcrmFWpScheduleVisit)model).setVisitStat("xf");
        	 }
        	 service.save(model);
        	 //修改日程主表的数目值
        	 OcrmFWpSchedule sch = (OcrmFWpSchedule)sservice.find(Long.parseLong(schId));
        	 sch.setVisitCount(sch.getVisitCount().add(BigDecimal.ONE));
        	 sservice.save(sch);
        	 
    	 }else{
    		 service.save(model);
    	 }
    	 
	}
	
	
}


