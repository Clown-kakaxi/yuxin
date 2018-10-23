package com.yuchengtech.bcrm.workplat.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.workplat.model.OcrmFHoliday;
import com.yuchengtech.bcrm.workplat.service.OcrmFHolidayService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;


@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/ocrmFWpScheduleH", results = { @Result(name = "success", type = "json")})
public class OcrmFHolidayAction extends CommonAction {

	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	 @Autowired
	 private  OcrmFHolidayService  service;
	 
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	 @Autowired
		public void init(){
		  	model = new OcrmFHoliday(); 
			setCommonService(service);
			needLog=true;
		}
	 
	 /**
	 *信息查询SQL
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	StringBuilder sb = new StringBuilder(" select v.* from OCRM_F_HOLIDAY v where v.ACCOUNT_NAME = '"+auth.getUserId()+"'");
    	addOracleLookup("BEGIN_TIME", "TIME_TYPE");
     	addOracleLookup("END_TIME", "TIME_TYPE");
     	addOracleLookup("HOLIDAY_TYPE", "HOLIDAY_TYPE");
		SQL=sb.toString();
		datasource = ds;
		
	}
	
	/**
	 * 数据保存
	 */
	public DefaultHttpHeaders saveData(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 if(((OcrmFHoliday)model).getId() == null){
 			((OcrmFHoliday)model).setAccountName(auth.getUserId());
 			((OcrmFHoliday)model).setRecordDate(new Date());
 			service.save(model);
    	 } else if(((OcrmFHoliday)model).getId()!=null){
    		String id = ((OcrmFHoliday)model).getId().toString();
 			String jql = "update  OcrmFHoliday s set s.beginDate=:beginDate,s.beginTime=:beginTime,s.endDate=:endDate," +
 					"s.endTime=:endTime,s.holidayType=:holidayType,s.accountName=:accountName,s.recordDate=:recordDate where s.id='"+id+"'";
 	        Map<String,Object> values = new HashMap<String,Object>();
 	        values.put("beginDate",((OcrmFHoliday)model).getBeginDate());
 	        values.put("beginTime",((OcrmFHoliday)model).getBeginTime());
 	        values.put("endDate",((OcrmFHoliday)model).getEndDate());
 	        values.put("endTime",((OcrmFHoliday)model).getEndTime());
 	        values.put("holidayType",((OcrmFHoliday)model).getHolidayType());
 	        values.put("accountName",auth.getUserId());
 	        values.put("recordDate", new Date());
 	        service.batchUpdateByName(jql, values);
    	 }
		return new DefaultHttpHeaders("success");
	}
	
	//删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	service.batchDel(request);
		return new DefaultHttpHeaders("success");
    }
}
