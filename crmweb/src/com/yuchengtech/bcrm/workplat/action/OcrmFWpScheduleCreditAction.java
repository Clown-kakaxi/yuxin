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
import com.yuchengtech.bcrm.workplat.model.OcrmFWpScheduleCredit;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleCreditService;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 
 * 日程处理-授信工作 action
 *  * @author luyy
 * @since 2014-06-24
 */

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/ocrmFWpScheduleC", results = { @Result(name = "success", type = "json")})
public class OcrmFWpScheduleCreditAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	 @Autowired
	 private  OcrmFWpScheduleCreditService  service;
	 @Autowired
	 private  OcrmFWpScheduleService  sservice;
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	 @Autowired
		public void init(){
		  	model = new OcrmFWpScheduleCredit(); 
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
    	 StringBuilder sb = new StringBuilder(" select v.*,s.user_id as VISITOR,a.user_name as arange_name" +
    	 		" from OCRM_F_WP_SCHEDULE_CREDIT v,admin_auth_account a,OCRM_F_WP_SCHEDULE s where s.sch_id = v.sch_id and v.ARANGE_ID = a.account_name and v.SCH_ID='"+schId+"'");
    	 if(userId.equals(auth.getUserId())){//自己查看：所有任务
    		 sb.append(" and v.STAT <>'dxf'"); 
    	 }else{//看别人的：当前查看人创建的和被查看人自主创建的任务
    		 sb.append(" and (v.ARANGE_ID = '"+auth.getUserId()+"'  or v.ARANGE_ID = '"+userId+"')"); 
    	 }
     	addOracleLookup("STAT", "STAT_CRD");
		SQL=sb.toString();
		datasource = ds;
	}
	//保存
	public void save(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 String schId = request.getParameter("schIds");
    	 String userId = request.getParameter("userId");
    	 
    	 if(((OcrmFWpScheduleCredit)model).getCId() == null ){//新增的时候
    		 ((OcrmFWpScheduleCredit)model).setSchId(BigDecimal.valueOf(Long.parseLong(schId)));
        	 if(userId.equals(auth.getUserId())){//自己创建任务
        		 ((OcrmFWpScheduleCredit)model).setStat("ap");
        	 }else{//为他人创建任务
        		 ((OcrmFWpScheduleCredit)model).setStat("xf");
        	 }
        	 service.save(model);
        	 //修改日程主表的数目值
        	 OcrmFWpSchedule sch = (OcrmFWpSchedule)sservice.find(Long.parseLong(schId));
        	 sch.setCreditCount(sch.getCreditCount().add(BigDecimal.ONE));
        	 sservice.save(sch);
        	 
    	 }else{
    		 service.save(model);
    	 }
	}
	
}


