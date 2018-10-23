package com.yuchengtech.bcrm.customer.potentialMkt.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPotCusCom;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewMatepurchase;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewNewRecord;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewOldRecord;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewProsale;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewShareholder;
import com.yuchengtech.bcrm.customer.potentialMkt.model.OcrmFInterviewTask;
import com.yuchengtech.bcrm.workplat.model.OcrmFWpSchedule;
import com.yuchengtech.bcrm.workplat.model.OcrmFWpScheduleVisit;
import com.yuchengtech.bcrm.workplat.service.OcrmFWpScheduleService;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
public class OcrmFInterviewTaskService extends CommonService{
	 @Autowired
	 private  OcrmFWpScheduleService  service;
	 public OcrmFInterviewTaskService(){
		 JPABaseDAO<OcrmFInterviewTask,String> baseDao = new JPABaseDAO<OcrmFInterviewTask,String>(OcrmFInterviewTask.class);
	 	 super.setBaseDAO(baseDao);
	 }
	 public Object save(Object obj) {
	        ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String name = request.getParameter("intervieweeName");
	    	String post = request.getParameter("intervieweePost");
	    	String phone = request.getParameter("intervieweePhone");
	    	String joinPerson = request.getParameter("joinPerson");
	    	String joinPersonId = request.getParameter("joinPersonId");
	    	
			OcrmFInterviewTask task = (OcrmFInterviewTask)obj;
			Timestamp ts = new Timestamp(task.getVisitTime().getTime());
			task.setVisitTime(ts);
			if("1".equals(task.getTaskType())){//新户
				task.setMgrId(auth.getUserId());
				task.setMgrName(auth.getUsername());
			}
			if(task.getId() == null){//新增
				task.setReviewState("1");
				task.setCreateTime(new Date());
				super.save(task);
				task.setTaskNumber(task.getId());
				if("0".equals(task.getTaskType())){//旧户
					if("2".equals(task.getVisitType())){//新案
						task.setModelType("1");
					}else{
						task.setModelType("0");
					}
				}else{
					task.setModelType("1");
				}
				super.save(task);
				//产生日程信息
				try {
					saveVisit(task,phone);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				task.setReviewState("1");
				task.setCreateTime(new Date());
				super.save(task);
			}
			//更新拜访任务明细
			saveRecord(task,name,post,phone,joinPerson,joinPersonId);
			
			return task;
	    }
	 /**
	  * 更新拜访任务明细
	  * @param task
	  * @param name
	  * @param post
	  * @param phone
	  * @param joinPerson
	  */
	 @SuppressWarnings("unchecked")
	public void saveRecord(OcrmFInterviewTask task,String name,String post,String phone,String joinPerson){
		 if("0".equals(task.getTaskType()) && !"2".equals(task.getVisitType())){//非潜在客户
			    List<OcrmFInterviewOldRecord> list = this.findByJql("select c from OcrmFInterviewOldRecord c where c.taskNumber = '"+task.getId()+"'", null);
				if(list!=null && !list.isEmpty()){
					for(OcrmFInterviewOldRecord old:list){
						old.setIntervieweeName(name);
						old.setIntervieweePhone(phone);
						old.setIntervieweePost(post);
						old.setJoinPerson(joinPerson);
						old.setCreateTime(new Date());
						super.save(old);
					}
				}else{
					OcrmFInterviewOldRecord  old = new OcrmFInterviewOldRecord();
					old.setIntervieweeName(name);
					old.setIntervieweePhone(phone);
					old.setIntervieweePost(post);
					old.setJoinPerson(joinPerson);
					old.setTaskNumber(task.getId());
					old.setCreateTime(new Date());
					super.save(old);
				}
			}else if("1".equals(task.getTaskType()) || ("0".equals(task.getTaskType()) && "2".equals(task.getVisitType()))){//潜在客户
				 List<OcrmFInterviewNewRecord> list = this.findByJql("select c from OcrmFInterviewNewRecord c where c.taskNumber = '"+task.getId()+"'", null);
					if(list!=null && !list.isEmpty()){
						for(OcrmFInterviewNewRecord newRecord:list){
							newRecord.setIntervieweeName(name);
							newRecord.setIntervieweePhone(phone);
							newRecord.setIntervieweePost(post);
							newRecord.setJoinPerson(joinPerson);
							newRecord.setCreateTime(new Date());
							super.save(newRecord);
						}
					}else{
						OcrmFInterviewNewRecord  newRecord = new OcrmFInterviewNewRecord();
						//带出潜在客户相关信息CUS_OWNBUSI  CUS_LEGALPERSON
						List<AcrmFCiPotCusCom> coms = this.findByJql("select c from AcrmFCiPotCusCom c where c.cusId = '"+task.getCustId()+"'", null);
						if(coms!=null && !coms.isEmpty()){
							if(coms.get(0).getIndustType()!=null){
								newRecord.setCusOwnbusi(BigDecimal.valueOf(Long.valueOf(coms.get(0).getIndustType())));
							}
							newRecord.setCusLegalperson(coms.get(0).getLegalName());
							
							//保存主要股东及持股比例
							if(coms.get(0).getPartnerInfo1()!=null && !coms.get(0).getPartnerInfo1().isEmpty() ){//第一持股人
								OcrmFInterviewShareholder shareholder =  new OcrmFInterviewShareholder();
								shareholder.setTaskNumber(task.getId());
								shareholder.setMSponsor(coms.get(0).getPartnerInfo1());
								shareholder.setMRatio(BigDecimal.valueOf(Long.valueOf(coms.get(0).getPartnerRate1())));
								super.save(shareholder);
							}
							if(coms.get(0).getPartnerInfo2()!=null && !coms.get(0).getPartnerInfo2().isEmpty() ){//第二持股人
								OcrmFInterviewShareholder shareholder =  new OcrmFInterviewShareholder();
								shareholder.setTaskNumber(task.getId());
								shareholder.setMSponsor(coms.get(0).getPartnerInfo2());
								shareholder.setMRatio(BigDecimal.valueOf(Long.valueOf(coms.get(0).getPartnerRate2())));
								super.save(shareholder);
							}
							if(coms.get(0).getPartnerInfo3()!=null && !coms.get(0).getPartnerInfo3().isEmpty() ){//第三持股人
								OcrmFInterviewShareholder shareholder =  new OcrmFInterviewShareholder();
								shareholder.setTaskNumber(task.getId());
								shareholder.setMSponsor(coms.get(0).getPartnerInfo3());
								shareholder.setMRatio(BigDecimal.valueOf(Long.valueOf(coms.get(0).getPartnerRate3())));
								super.save(shareholder);
							}
							//原料采购情况
							if(coms.get(0).getSupInf() != null && !coms.get(0).getSupInf().isEmpty()){//第一供货商
								OcrmFInterviewMatepurchase p =  new OcrmFInterviewMatepurchase();
								p.setTaskNumber(task.getId());
								p.setMpSupplier(coms.get(0).getSupInf());
								super.save(p);
							}
							if(coms.get(0).getSupInfS() != null && !coms.get(0).getSupInfS().isEmpty()){//第二供货商
								OcrmFInterviewMatepurchase p =  new OcrmFInterviewMatepurchase();
								p.setTaskNumber(task.getId());
								p.setMpSupplier(coms.get(0).getSupInfS());
								super.save(p);
							}
							//产品销售状况
							 if(coms.get(0).getBuyerInf() != null && !coms.get(0).getBuyerInf().isEmpty()){//第一买售商
								 OcrmFInterviewProsale pr =  new OcrmFInterviewProsale();
								 pr.setTaskNumber(task.getId());
								 pr.setPsBuyer(coms.get(0).getBuyerInf());
								 super.save(pr);
							 } 
							 if(coms.get(0).getBuyerInfS() != null && !coms.get(0).getBuyerInfS().isEmpty()){//第二买售商
								 OcrmFInterviewProsale pr =  new OcrmFInterviewProsale();
								 pr.setTaskNumber(task.getId());
								 pr.setPsBuyer(coms.get(0).getBuyerInfS());
								 super.save(pr);
							 } 
						}
						newRecord.setIntervieweeName(name);
						newRecord.setIntervieweePhone(phone);
						newRecord.setIntervieweePost(post);
						newRecord.setJoinPerson(joinPerson);
						newRecord.setTaskNumber(task.getId());
						newRecord.setCreateTime(new Date());
						super.save(newRecord);
					}
			}
	 }
	 /**
	  * 更新拜访任务明细
	  * @param task
	  * @param name
	  * @param post
	  * @param phone
	  * @param joinPerson
	  * @param joinPersonId
	  */
	 @SuppressWarnings("unchecked")
	public void saveRecord(OcrmFInterviewTask task,String name,String post,String phone,String joinPerson,String joinPersonId){
		 if("0".equals(task.getTaskType()) && !"2".equals(task.getVisitType())){//非潜在客户
			    List<OcrmFInterviewOldRecord> list = this.findByJql("select c from OcrmFInterviewOldRecord c where c.taskNumber = '"+task.getId()+"'", null);
				if(list!=null && !list.isEmpty()){
					for(OcrmFInterviewOldRecord old:list){
						old.setIntervieweeName(name);
						old.setIntervieweePhone(phone);
						old.setIntervieweePost(post);
						old.setJoinPerson(joinPerson);
						old.setJoinPersonId(joinPersonId);
						old.setCreateTime(new Date());
						super.save(old);
					}
				}else{
					OcrmFInterviewOldRecord  old = new OcrmFInterviewOldRecord();
					old.setIntervieweeName(name);
					old.setIntervieweePhone(phone);
					old.setIntervieweePost(post);
					old.setJoinPerson(joinPerson);
					old.setJoinPersonId(joinPersonId);
					old.setTaskNumber(task.getId());
					old.setCreateTime(new Date());
					super.save(old);
				}
			}else if("1".equals(task.getTaskType()) || ("0".equals(task.getTaskType()) && "2".equals(task.getVisitType()))){//潜在客户
				 List<OcrmFInterviewNewRecord> list = this.findByJql("select c from OcrmFInterviewNewRecord c where c.taskNumber = '"+task.getId()+"'", null);
					if(list!=null && !list.isEmpty()){
						for(OcrmFInterviewNewRecord newRecord:list){
							newRecord.setIntervieweeName(name);
							newRecord.setIntervieweePhone(phone);
							newRecord.setIntervieweePost(post);
							newRecord.setJoinPerson(joinPerson);
							newRecord.setJoinPersonId(joinPersonId);
							newRecord.setCreateTime(new Date());
							super.save(newRecord);
						}
					}else{
						OcrmFInterviewNewRecord  newRecord = new OcrmFInterviewNewRecord();
						//带出潜在客户相关信息CUS_OWNBUSI  CUS_LEGALPERSON
						List<AcrmFCiPotCusCom> coms = this.findByJql("select c from AcrmFCiPotCusCom c where c.cusId = '"+task.getCustId()+"'", null);
						if(coms!=null && !coms.isEmpty()){
							if(coms.get(0).getIndustType()!=null){
								newRecord.setCusOwnbusi(BigDecimal.valueOf(Long.valueOf(coms.get(0).getIndustType())));
							}
							newRecord.setCusLegalperson(coms.get(0).getLegalName());
							
							//保存主要股东及持股比例
							if(coms.get(0).getPartnerInfo1()!=null && !coms.get(0).getPartnerInfo1().isEmpty() ){//第一持股人
								OcrmFInterviewShareholder shareholder =  new OcrmFInterviewShareholder();
								shareholder.setTaskNumber(task.getId());
								shareholder.setMSponsor(coms.get(0).getPartnerInfo1());
								shareholder.setMRatio(BigDecimal.valueOf(Long.valueOf(coms.get(0).getPartnerRate1())));
								super.save(shareholder);
							}
							if(coms.get(0).getPartnerInfo2()!=null && !coms.get(0).getPartnerInfo2().isEmpty() ){//第二持股人
								OcrmFInterviewShareholder shareholder =  new OcrmFInterviewShareholder();
								shareholder.setTaskNumber(task.getId());
								shareholder.setMSponsor(coms.get(0).getPartnerInfo2());
								shareholder.setMRatio(BigDecimal.valueOf(Long.valueOf(coms.get(0).getPartnerRate2())));
								super.save(shareholder);
							}
							if(coms.get(0).getPartnerInfo3()!=null && !coms.get(0).getPartnerInfo3().isEmpty() ){//第三持股人
								OcrmFInterviewShareholder shareholder =  new OcrmFInterviewShareholder();
								shareholder.setTaskNumber(task.getId());
								shareholder.setMSponsor(coms.get(0).getPartnerInfo3());
								shareholder.setMRatio(BigDecimal.valueOf(Long.valueOf(coms.get(0).getPartnerRate3())));
								super.save(shareholder);
							}
							//原料采购情况
							if(coms.get(0).getSupInf() != null && !coms.get(0).getSupInf().isEmpty()){//第一供货商
								OcrmFInterviewMatepurchase p =  new OcrmFInterviewMatepurchase();
								p.setTaskNumber(task.getId());
								p.setMpSupplier(coms.get(0).getSupInf());
								super.save(p);
							}
							if(coms.get(0).getSupInfS() != null && !coms.get(0).getSupInfS().isEmpty()){//第二供货商
								OcrmFInterviewMatepurchase p =  new OcrmFInterviewMatepurchase();
								p.setTaskNumber(task.getId());
								p.setMpSupplier(coms.get(0).getSupInfS());
								super.save(p);
							}
							//产品销售状况
							 if(coms.get(0).getBuyerInf() != null && !coms.get(0).getBuyerInf().isEmpty()){//第一买售商
								 OcrmFInterviewProsale pr =  new OcrmFInterviewProsale();
								 pr.setTaskNumber(task.getId());
								 pr.setPsBuyer(coms.get(0).getBuyerInf());
								 super.save(pr);
							 } 
							 if(coms.get(0).getBuyerInfS() != null && !coms.get(0).getBuyerInfS().isEmpty()){//第二买售商
								 OcrmFInterviewProsale pr =  new OcrmFInterviewProsale();
								 pr.setTaskNumber(task.getId());
								 pr.setPsBuyer(coms.get(0).getBuyerInfS());
								 super.save(pr);
							 } 
						}
						newRecord.setIntervieweeName(name);
						newRecord.setIntervieweePhone(phone);
						newRecord.setIntervieweePost(post);
						newRecord.setJoinPerson(joinPerson);
						newRecord.setJoinPersonId(joinPersonId);
						newRecord.setTaskNumber(task.getId());
						newRecord.setCreateTime(new Date());
						super.save(newRecord);
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
	  * 保存来自电访的拜访任务
	  * @param task
	  */
	 public  void saveTask(OcrmFInterviewTask task){
		task.setReviewState("1");
		task.setCreateTime(new Date());
		super.save(task);
		//保存tasknumber
		task.setTaskNumber(task.getId());
		super.save(task);
		//产生日程信息
		try {
			saveVisit(task,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 /**
	  * 删除日程
	  */
	@SuppressWarnings("unchecked")
	public void deleteSchedule(String taskId){
		 OcrmFWpScheduleVisit  visit = this.em.find(OcrmFWpScheduleVisit.class, Long.parseLong(taskId));
		 if(visit !=null){
			 String schIdStr = String.valueOf(visit.getSchId());//日程ID
			 OcrmFWpSchedule schOld = this.em.find(OcrmFWpSchedule.class, Long.parseLong(schIdStr));
			 //减少任务数
			 schOld.setVisitCount(schOld.getVisitCount().subtract(BigDecimal.ONE));
			 service.save(schOld);
		 }
 		 //删除日程细项
 		 this.batchUpdateByName("delete from OcrmFWpScheduleVisit t where t.vId = '"+visit.getVId()+"'", null);
 		 //如果删除的是callreport维护 提交时产生的拜访任务 跟新原任务的flag状态
 		 List<OcrmFInterviewTask> tasks = 
 			 this.findByJql("select t from OcrmFInterviewTask t where t.newRecordId = '"+taskId+"'", null);
 		 for(OcrmFInterviewTask task:tasks){
 			 task.setFlag("1");
 			 super.save(task);
 		 }
	 }
}
