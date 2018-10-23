package com.yuchengtech.bcrm.customer.potentialMkt.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewCreditpro;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewDepositbank;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewDepositpro;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewFixedasset;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewForexlimit;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewLoanbank;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewMatepurchase;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewNewRecord;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewOldPurpose;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewOldRecord;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewProfit;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewProsale;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewShareholder;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewTask;
import com.yuchengtech.bcrm.system.model.AdminAuthAccount;
import com.yuchengtech.bcrm.workplat.model.OcrmFWpSchedule;
import com.yuchengtech.bcrm.workplat.model.OcrmFWpScheduleVisit;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleService;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

@Service
public class OcrmFInterviewOldRecordService extends CommonService{
	 private HttpServletRequest request;
	 @Autowired
	 private  OcrmFWpScheduleService  service;
	 
	 public OcrmFInterviewOldRecordService(){
		 JPABaseDAO<OcrmFInterviewOldRecord,String> baseDao = new JPABaseDAO<OcrmFInterviewOldRecord,String>(OcrmFInterviewOldRecord.class);
	 	 super.setBaseDAO(baseDao);
	 }
	@SuppressWarnings("unchecked")
	public Object save(Object obj) {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    String purCust2call = null;
	    String purDefend2call = null;
	    String purMark2pro  = null;
	    String purRisk2call = null;
	    String purSeek2coll = null;
	    String purWarn2call = null;
	    if(request.getParameter("purCust2call")!=null){
	    	purCust2call = "true".equals(request.getParameter("purCust2call").toLowerCase())?"1":"0";
	    }
	    if(request.getParameter("purDefend2call")!=null){
	    	purDefend2call = "true".equals(request.getParameter("purDefend2call").toLowerCase())?"1":"0";
	    }
	    if(request.getParameter("purMark2pro")!=null){
	    	purMark2pro = "true".equals(request.getParameter("purMark2pro").toLowerCase())?"1":"0";
	    }
	    if(request.getParameter("purRisk2call")!=null){
	    	purRisk2call = "true".equals(request.getParameter("purRisk2call").toLowerCase())?"1":"0";
	    }
	    if(request.getParameter("purSeek2coll")!=null){
	    	purSeek2coll = "true".equals(request.getParameter("purSeek2coll").toLowerCase())?"1":"0";
	    }
	    if(request.getParameter("purWarn2call")!=null){
	    	purWarn2call = "true".equals(request.getParameter("purWarn2call").toLowerCase())?"1":"0";
	    }
	    
	    String markResultOld = request.getParameter("markResultOld");
	    String markRefusereasonOld = request.getParameter("markRefusereasonOld");
	    
	    String visitType = request.getParameter("visitType");
	    String visitTime = request.getParameter("visitTime");
	    String visitStartTime = request.getParameter("visitStartTime");//预约拜访开始时间
		String visitEndTime = request.getParameter("visitEndTime");//预约拜访结束时间
	    OcrmFInterviewOldPurpose purpose = new OcrmFInterviewOldPurpose();
	    purpose.setPurCust2call(purCust2call);
	    purpose.setPurDefend2call(purDefend2call);
	    purpose.setPurMark2pro(purMark2pro);
	    purpose.setPurRisk2call(purRisk2call);
	    purpose.setPurSeek2coll(purSeek2coll);
	    purpose.setPurWarn2call(purWarn2call);
	    
	    OcrmFInterviewOldRecord oldRecord = (OcrmFInterviewOldRecord)obj;
		List<OcrmFInterviewOldRecord> list = this.findByJql("select c from OcrmFInterviewOldRecord c where c.taskNumber = '"+oldRecord.getTaskNumber().split(",")[0]+"'", null);
		if(list!=null && !list.isEmpty()){
			for(OcrmFInterviewOldRecord r:list){
				oldRecord.setId(r.getId());
				oldRecord.setTaskNumber(r.getTaskNumber());
				if(markResultOld != null && !markResultOld.isEmpty()){
					oldRecord.setMarkResult(BigDecimal.valueOf(Double.parseDouble(markResultOld)));
				}
				if(markRefusereasonOld != null && !markRefusereasonOld.isEmpty()){
					oldRecord.setMarkRefusereason(BigDecimal.valueOf(Double.parseDouble(markRefusereasonOld)));
				}
			}
		}else{
			if(markResultOld != null && !markResultOld.isEmpty()){
				oldRecord.setMarkResult(BigDecimal.valueOf(Double.parseDouble(markResultOld)));
			}
			if(markRefusereasonOld != null && !markRefusereasonOld.isEmpty()){
				oldRecord.setMarkRefusereason(BigDecimal.valueOf(Double.parseDouble(markRefusereasonOld)));
			}
			oldRecord.setTaskNumber(oldRecord.getTaskNumber().split(",")[0]);
			oldRecord.setId(null);
		}
		//保存旧户拜访目标表数据
		saveOldPurpose(oldRecord,purpose);
		/**
		 * 保存按钮设置任务状态为反馈暂存,
		 */
		List<OcrmFInterviewTask> ts = 
			this.findByJql("select c from OcrmFInterviewTask c where c.taskNumber = '"+oldRecord.getTaskNumber().split(",")[0]+"'", null);
		for(OcrmFInterviewTask t:ts){
//			OcrmFInterviewTask t = this.em.find(OcrmFInterviewTask.class, oldRecord.getTaskNumber().split(",")[0]);
			t.setReviewState("1");//未完成
			t.setVisitType(visitType);
			try {
				t.setVisitTime(new SimpleDateFormat("yyyy-MM-dd").parse(visitTime));
				t.setVisitStartTime(visitStartTime);
				t.setVisitEndTime(visitEndTime);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			t.setModelType("0");
			super.save(t);
			/***
			 * 产生日程（修改预约拜访日期和实际拜访日期）
			 */
			try {
				saveScheduleVisit(t,oldRecord);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return super.save(oldRecord);
	}
	
	/**
	 * 提交保存
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public Object save(Object obj,boolean flag) {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		 String purCust2call = null;
		    String purDefend2call = null;
		    String purMark2pro  = null;
		    String purRisk2call = null;
		    String purSeek2coll = null;
		    String purWarn2call = null;
		    if(request.getParameter("purCust2call")!=null){
		    	purCust2call = "true".equals(request.getParameter("purCust2call").toLowerCase())?"1":"0";
		    }
		    if(request.getParameter("purDefend2call")!=null){
		    	purDefend2call = "true".equals(request.getParameter("purDefend2call").toLowerCase())?"1":"0";
		    }
		    if(request.getParameter("purMark2pro")!=null){
		    	purMark2pro = "true".equals(request.getParameter("purMark2pro").toLowerCase())?"1":"0";
		    }
		    if(request.getParameter("purRisk2call")!=null){
		    	purRisk2call = "true".equals(request.getParameter("purRisk2call").toLowerCase())?"1":"0";
		    }
		    if(request.getParameter("purSeek2coll")!=null){
		    	purSeek2coll = "true".equals(request.getParameter("purSeek2coll").toLowerCase())?"1":"0";
		    }
		    if(request.getParameter("purWarn2call")!=null){
		    	purWarn2call = "true".equals(request.getParameter("purWarn2call").toLowerCase())?"1":"0";
		    }
		
		String markResultOld = request.getParameter("markResultOld");
		String markRefusereasonOld = request.getParameter("markRefusereasonOld");
		
		String visitType = request.getParameter("visitType");
		String visitTime = request.getParameter("visitTime");
		String visitStartTime = request.getParameter("visitStartTime");//预约拜访开始时间
		String visitEndTime = request.getParameter("visitEndTime");//预约拜访结束时间
		OcrmFInterviewOldPurpose purpose = new OcrmFInterviewOldPurpose();
		purpose.setPurCust2call(purCust2call);
		purpose.setPurDefend2call(purDefend2call);
		purpose.setPurMark2pro(purMark2pro);
		purpose.setPurRisk2call(purRisk2call);
		purpose.setPurSeek2coll(purSeek2coll);
		purpose.setPurWarn2call(purWarn2call);
		
		OcrmFInterviewOldRecord oldRecord = (OcrmFInterviewOldRecord)obj;
		if(oldRecord.getCreateTime() ==null){
			oldRecord.setCreateTime(new Date());
		}
		List<OcrmFInterviewOldRecord> list = this.findByJql("select c from OcrmFInterviewOldRecord c where c.taskNumber = '"+oldRecord.getTaskNumber().split(",")[0]+"'", null);
		if(list!=null && !list.isEmpty()){
			for(OcrmFInterviewOldRecord r:list){
				oldRecord.setId(r.getId());
				oldRecord.setTaskNumber(r.getTaskNumber());
				if(markResultOld != null && !markResultOld.isEmpty()){
					oldRecord.setMarkResult(BigDecimal.valueOf(Double.parseDouble(markResultOld)));
				}
				if(markRefusereasonOld != null && !markRefusereasonOld.isEmpty()){
					oldRecord.setMarkRefusereason(BigDecimal.valueOf(Double.parseDouble(markRefusereasonOld)));
				}
			}
		}else{
			if(markResultOld != null && !markResultOld.isEmpty()){
				oldRecord.setMarkResult(BigDecimal.valueOf(Double.parseDouble(markResultOld)));
			}
			if(markRefusereasonOld != null && !markRefusereasonOld.isEmpty()){
				oldRecord.setMarkRefusereason(BigDecimal.valueOf(Double.parseDouble(markRefusereasonOld)));
			}
			oldRecord.setTaskNumber(oldRecord.getTaskNumber().split(",")[0]);
			oldRecord.setId(null);
		}
		//保存旧户拜访目标表数据
		saveOldPurpose(oldRecord,purpose);
		/**
		 * 保存按钮设置任务状态为反馈暂存,
		 */
		List<OcrmFInterviewTask> ts = 
			this.findByJql("select c from OcrmFInterviewTask c where c.taskNumber = '"+oldRecord.getTaskNumber().split(",")[0]+"'", null);
		for(OcrmFInterviewTask t:ts){
//			OcrmFInterviewTask t = this.em.find(OcrmFInterviewTask.class, oldRecord.getTaskNumber().split(",")[0]);
//			t.setReviewState("2");//审核中
			this.batchUpdateByName("update OcrmFInterviewTask t set t.reviewState = '2' where (t.id = '"+oldRecord.getTaskNumber().split(",")[0]+"' or t.taskNumber = '"+oldRecord.getTaskNumber().split(",")[0]+"')", null);
			t.setVisitType(visitType);
			try {
				t.setVisitTime(new SimpleDateFormat("yyyy-MM-dd").parse(visitTime));
				t.setVisitStartTime(visitStartTime);
				t.setVisitEndTime(visitEndTime);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			t.setModelType("0");
			super.save(t);
			/***
			 * 产生日程（修改预约拜访日期和实际拜访日期）
			 */
			try {
				saveScheduleVisit(t,oldRecord);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			/**
			 * 产生新的拜访任务
			 */
			if(oldRecord.getCallNexttime() != null){
				saveOldRecord(t,oldRecord);
			}
		}
		return super.save(oldRecord);
	}
	/**
	 * 保存旧户拜访目标表数据
	 */
	@SuppressWarnings("unchecked")
	public void saveOldPurpose(OcrmFInterviewOldRecord task,OcrmFInterviewOldPurpose purpose) {
		List<OcrmFInterviewOldPurpose> list = this.findByJql("select c from OcrmFInterviewOldPurpose c where c.taskNumber = '"+task.getTaskNumber()+"'", null);
		if(list!=null && !list.isEmpty()){//修改
			for(OcrmFInterviewOldPurpose r:list){
				purpose.setId(r.getId());
				purpose.setTaskNumber(r.getTaskNumber());
			}
		}else{
			purpose.setTaskNumber(task.getTaskNumber());
			purpose.setId(null);
		}
		super.save(purpose);
	}
	
	/**
	  * 修改日程
	  * @param t
	  * @param oldTask
	 * @throws ParseException 
	  */
	 @SuppressWarnings({ "unchecked"})
	 public void saveScheduleVisit(OcrmFInterviewTask t,OcrmFInterviewOldRecord oldTask) throws ParseException{
	   	   OcrmFWpScheduleVisit  visit = this.em.find(OcrmFWpScheduleVisit.class, Long.parseLong(t.getId()));
	   	   if(visit != null){
	   		   String schIdStr = String.valueOf(visit.getSchId());//日程ID
	   		   OcrmFWpSchedule schOld = this.em.find(OcrmFWpSchedule.class, Long.parseLong(schIdStr));
	   		   //减少任务数
	   		   schOld.setVisitCount(schOld.getVisitCount().subtract(BigDecimal.ONE));
	   		   service.save(schOld);
	   		   /**
	   		    * 增加日程
	   		    */
	   		   String schId = "";
	   		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	   	       String date = "";
	   		   if(oldTask.getCallTime()!= null){
	   			   date = sdf.format(oldTask.getCallTime()).toString();
	   		   }else{
	   			   date = sdf.format(t.getVisitTime()).toString();
	   		   } 
	   		   String userId = t.getMgrId();
	   		   
	   		   List<Object[]> list =  super.getBaseDAO().findByNativeSQLWithIndexParam(" select SCH_ID,USER_ID from OCRM_F_WP_SCHEDULE where USER_ID='"+userId+"' " +
	   				   " and SCH_DATE = to_date('"+date+"','YYYY-MM-dd')");
	   		   
	   		   if(list != null && list.size()>0){
	   			   schId = list.get(0)[0].toString();
	   		   }else{
	   			   OcrmFWpSchedule sch = new OcrmFWpSchedule();
	   			   sch.setUserId(userId);
	   			   sch.setSchDate(sdf.parse(date));
	   			   sch.setCreditCount(BigDecimal.ZERO);
	   			   sch.setLoanCheckCount(BigDecimal.ZERO);
	   			   sch.setMonthCount(BigDecimal.ZERO);
	   			   sch.setOtherCount(BigDecimal.ZERO);
	   			   sch.setVisitCount(BigDecimal.ZERO);
	   			   sch.setWeekCount(BigDecimal.ZERO);
	   			   
	   			   sch = (OcrmFWpSchedule)super.save(sch);
	   			   schId = sch.getSchId().toString();
	   		   }
	   		   
	   		   //新增日程
//	   		   OcrmFWpScheduleVisit visit  = new OcrmFWpScheduleVisit();
	   		   visit.setSchId(BigDecimal.valueOf(Long.parseLong(schId)));
	   		   visit.setCustType("1");
	   		   visit.setCustId(t.getCustId());
	   		   visit.setCustName(t.getCustName());
	   		   visit.setPhone(oldTask.getIntervieweePhone());
	   		   visit.setVisitor(t.getMgrId());
	   		   visit.setVisitStat("ap");
	   		   visit.setArangeId(t.getMgrId());
	   		   visit.setUserId(t.getMgrId());
	   		   visit.setUserName(t.getMgrName());
	   		   
	   		  if(oldTask.getCallTime()!= null){
	   			   visit.setSchStartTime(oldTask.getCallTime());
		   		   visit.setSchEdnTime(oldTask.getCallTime());
	   		   }else{
	   			   visit.setSchStartTime(t.getVisitTime());
		   		   visit.setSchEdnTime(t.getVisitTime());
	   		   }
//	   		   visit.setSchStartTime(oldTask.getCallTime());
//	   		   visit.setSchEdnTime(oldTask.getCallTime());
	   		   visit.setVId(Long.parseLong(t.getId()));//拜访任务表ID作为日程表的vid
	   		   super.save(visit);
	   		   
	   		   //修改日程主表的数目值
	   		   OcrmFWpSchedule sch = (OcrmFWpSchedule)this.em.find(OcrmFWpSchedule.class,Long.parseLong(schId));
	   		   sch.setVisitCount(sch.getVisitCount().add(BigDecimal.ONE));
	   		   super.save(sch);
	   	   }
	 }
	 
	 /**
	  * 预约下次拜访时间 在提交时 产生新的拜访任务和日程
	  * @param task
	  * @param oldRecord
	  */
	 @SuppressWarnings("unchecked")
	public void saveOldRecord(OcrmFInterviewTask task,OcrmFInterviewOldRecord record){
		 //新的拜访任务
		 if(task.getNewRecordId()!=null && "0".equals(task.getFlag())){
			 OcrmFInterviewTask newTask = this.em.find(OcrmFInterviewTask.class, task.getNewRecordId());
			 if(newTask != null){//可能任务已经被删除 所有有此判断
				 newTask.setVisitTime(record.getCallNexttime());
				 super.save(newTask);
				 List<OcrmFInterviewOldRecord> oldRecord = 
					 this.findByJql("select t from OcrmFInterviewOldRecord t where t.taskNumber = '"+newTask.getId()+"'", null);
				 //修改日程
				 try {
					 saveScheduleVisit(newTask,oldRecord.get(0));
				 } catch (ParseException e) {
					 e.printStackTrace();
				 }
			 }
		 }else{
			 OcrmFInterviewTask t =  new OcrmFInterviewTask();
			 t.setCustId(task.getCustId());
			 t.setCustName(task.getCustName());
			 t.setMgrId(task.getMgrId());
			 t.setMgrName(task.getMgrName());
			 t.setTaskType(task.getTaskType());
			 t.setVisitTime(record.getCallNexttime());
			 t.setCreateTime(new Date());
			 t.setRemark(task.getRemark());
			 t.setReviewState("1");
			 t.setVisitType("");
			 t.setModelType(task.getModelType());
			 t.setFlag("0");
			 super.save(t);
			 t.setTaskNumber(t.getId());
			 super.save(t);
			 //记录原任务ID
			 task.setFlag("0");
			 task.setNewRecordId(t.getId());
			 super.save(task);
			 //新的任务明细
			
			 OcrmFInterviewOldRecord oldRecord =  new OcrmFInterviewOldRecord();
			 oldRecord.setTaskNumber(t.getId());
			 oldRecord.setIntervieweeName(record.getIntervieweeName());
			 oldRecord.setIntervieweePhone(record.getIntervieweePhone());
			 oldRecord.setIntervieweePost(record.getIntervieweePost());
			 oldRecord.setJoinPerson(record.getJoinPerson());
			 oldRecord.setJoinPersonId(record.getJoinPersonId());
//			 newRecord.setCallTime(record.getCallTime());
			 oldRecord.setCusStatus(record.getCusStatus());
			 oldRecord.setIsbuschange(record.getIsbuschange());
			 oldRecord.setBusExplain(record.getBusExplain());
			 oldRecord.setIsrevchange(record.getIsrevchange());
			 oldRecord.setRevExplain(record.getRevExplain());
			 oldRecord.setIsprochange(record.getIsprochange());
			 oldRecord.setProExplain(record.getProExplain());
			 oldRecord.setIssupchange(record.getIssupchange());
			 oldRecord.setSupExplain(record.getSupExplain());
			 oldRecord.setIspurchange(record.getIspurchange());
			 oldRecord.setPurExplain(record.getPurExplain());
			 oldRecord.setIsequchange(record.getIsequchange());
			 oldRecord.setEquExplain(record.getEquExplain());
			 oldRecord.setIsopcchange(record.getIsopcchange());
			 oldRecord.setOpcExplain(record.getOpcExplain());
			 oldRecord.setIscolchange(record.getIscolchange());
			 oldRecord.setColExplain(record.getColExplain());
			 oldRecord.setIssymchange(record.getIssymchange());
			 oldRecord.setSymExplain(record.getSymExplain());
			 oldRecord.setCreateTime(new Date());
			 super.save(oldRecord);
			 //旧户拜访目的明细
			 List<OcrmFInterviewOldPurpose> oldPurposes  = 
				 this.findByJql("select t from OcrmFInterviewOldPurpose t  where t.taskNumber = '"+task.getId()+"'", null);
			 for(OcrmFInterviewOldPurpose od : oldPurposes){
				 OcrmFInterviewOldPurpose oldPurpose =  new OcrmFInterviewOldPurpose();
				 oldPurpose.setTaskNumber(t.getId());
				 oldPurpose.setPurCust2call(od.getPurCust2call());
				 oldPurpose.setPurDefend2call(od.getPurDefend2call());
				 oldPurpose.setPurMark2pro(od.getPurMark2pro());
				 oldPurpose.setPurRisk2call(od.getPurRisk2call());
				 oldPurpose.setPurSeek2coll(od.getPurSeek2coll());
				 oldPurpose.setPurWarn2call(od.getPurWarn2call());
				 super.save(oldPurpose);
			 }
			 //写日程
			 try {
				saveVisit(t,record.getIntervieweePhone());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 }
	 }
		 /**
		  * 生成日程信息
		  * 得到SchId，根据userId和data查询，没有则新增
		  * @param task
		 * @throws ParseException 
		  */
		 @SuppressWarnings("unchecked")
		 public void saveVisit(OcrmFInterviewTask task,String phone) throws ParseException{
		   	    String schId = "";
		   	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		   	    String date = sdf.format(task.getVisitTime()).toString();
		   	    String userId = task.getMgrId();
		   	    
		   	    List<Object[]> list =  super.getBaseDAO().findByNativeSQLWithIndexParam(" select SCH_ID,USER_ID from OCRM_F_WP_SCHEDULE where USER_ID='"+userId+"' " +
		   	    		"and SCH_DATE = to_date('"+date+"','YYYY-MM-dd')");
		   	    
		   	    if(list != null && list.size()>0){
		   	    	schId = list.get(0)[0].toString();
		   	    }else{
		   	    	OcrmFWpSchedule sch = new OcrmFWpSchedule();
		   	    	sch.setUserId(userId);
		   	    	sch.setSchDate(sdf.parse(date));
		   	    	sch.setCreditCount(BigDecimal.ZERO);
		   	    	sch.setLoanCheckCount(BigDecimal.ZERO);
		   	    	sch.setMonthCount(BigDecimal.ZERO);
		   	    	sch.setOtherCount(BigDecimal.ZERO);
		   	    	sch.setVisitCount(BigDecimal.ZERO);
		   	    	sch.setWeekCount(BigDecimal.ZERO);
		   	    	
		   	    	sch = (OcrmFWpSchedule)super.save(sch);
		   	    	schId = sch.getSchId().toString();
		   	    }
		   	   //新增日程
		   	   OcrmFWpScheduleVisit visit  = new OcrmFWpScheduleVisit();
		   	   visit.setSchId(BigDecimal.valueOf(Long.parseLong(schId)));
		   	   visit.setCustType("1");
		   	   visit.setCustId(task.getCustId());
		   	   visit.setCustName(task.getCustName());
		   	   visit.setPhone(phone);
		   	   visit.setVisitor(task.getMgrId());
		   	   visit.setVisitStat("ap");
		   	   visit.setArangeId(task.getMgrId());
		   	   visit.setUserId(task.getMgrId());
		   	   visit.setUserName(task.getMgrName());
		   	   visit.setSchStartTime(task.getVisitTime());
		   	   visit.setSchEdnTime(task.getVisitTime());
		   	   visit.setVId(Long.parseLong(task.getId()));//拜访任务表ID作为日程表的vid
		   	   super.save(visit);
		   	 
		       //修改日程主表的数目值
	    	   OcrmFWpSchedule sch = (OcrmFWpSchedule)this.em.find(OcrmFWpSchedule.class,Long.parseLong(schId));
	    	   sch.setVisitCount(sch.getVisitCount().add(BigDecimal.ONE));
	    	   super.save(sch);
		 }
		 
	 /**
	  * 获取teamhead
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	 public String getTeamhead(String mgrId){
		String teamHead = "";
   	    List<AdminAuthAccount> ts = this.findByJql("select c from AdminAuthAccount c where c.accountName = '"+mgrId+"'", null);
   	    for(AdminAuthAccount t:ts){
   	    	teamHead = t.getBelongTeamHead();
   	    }
		 return teamHead;
	 }
}
