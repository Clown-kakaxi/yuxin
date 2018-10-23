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
public class OcrmFInterviewNewRecordService extends CommonService{
	 @Autowired
	 private  OcrmFWpScheduleService  service;
	 public OcrmFInterviewNewRecordService(){
		 JPABaseDAO<OcrmFInterviewNewRecord,String> baseDao = new JPABaseDAO<OcrmFInterviewNewRecord,String>(OcrmFInterviewNewRecord.class);
	 	 super.setBaseDAO(baseDao);
	 }
	 @SuppressWarnings("unchecked")
	public Object save(Object obj,boolean flag) {
		 	ActionContext ctx = ActionContext.getContext();
			OcrmFInterviewNewRecord task = (OcrmFInterviewNewRecord)obj;
			List<OcrmFInterviewNewRecord> list = this.findByJql("select c from OcrmFInterviewNewRecord c where c.taskNumber = '"+task.getTaskNumber().split(",")[0]+"'", null);
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			String visitType = request.getParameter("visitType");
			String visitTime = request.getParameter("visitTime");//预约拜访时间
			String visitStartTime = request.getParameter("visitStartTime");//预约拜访开始时间
			String visitEndTime = request.getParameter("visitEndTime");//预约拜访结束时间
			if(list!=null && !list.isEmpty()){
				for(OcrmFInterviewNewRecord r:list){
					task.setId(r.getId());
					task.setTaskNumber(r.getTaskNumber());
				}
			}else{
				task.setTaskNumber(task.getTaskNumber().split(",")[0]);
				task.setId(null);
			}
			/**
			 * 提交按钮 新户反馈时设置任务状态,
			 */
			List<OcrmFInterviewTask> ts = 
				this.findByJql("select c from OcrmFInterviewTask c where c.taskNumber = '"+task.getTaskNumber().split(",")[0]+"'", null);
			for(OcrmFInterviewTask t:ts){
//				t.setReviewState("2");//审批中
				this.batchUpdateByName("update OcrmFInterviewTask t set t.reviewState = '2' where (t.id = '"+task.getTaskNumber().split(",")[0]+"' or t.taskNumber = '"+task.getTaskNumber().split(",")[0]+"')", null);
				t.setVisitType(visitType);
				try {
					t.setVisitTime(new SimpleDateFormat("yyyy-MM-dd").parse(visitTime));
					t.setVisitStartTime(visitStartTime);
					t.setVisitEndTime(visitEndTime);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				if("2".equals(visitType)){//旧户新案
					t.setModelType("1");
				}
				super.save(t);
				
				/***
				 * 产生日程（修改预约拜访日期和实际拜访日期）
				 */
				try {
					saveScheduleVisit(t,task);//提交
				} catch (ParseException e) {
					e.printStackTrace();
				}
				/**
				 * 产生新的拜访任务
				 */
				if(task.getCallNexttime() != null){
					saveNewRecord(t,task);
				}
			}
			return super.save(task);
	    }
	 @SuppressWarnings("unchecked")
	 public Object save(Object obj) {
		    ActionContext ctx = ActionContext.getContext();
		    HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		    String visitType = request.getParameter("visitType");
		    String visitTime = request.getParameter("visitTime");
		    String visitStartTime = request.getParameter("visitStartTime");//预约拜访开始时间
			String visitEndTime = request.getParameter("visitEndTime");//预约拜访结束时间
			OcrmFInterviewNewRecord task = (OcrmFInterviewNewRecord)obj;
			List<OcrmFInterviewNewRecord> list = this.findByJql("select c from OcrmFInterviewNewRecord c where c.taskNumber = '"+task.getTaskNumber().split(",")[0]+"'", null);
			if(list!=null && !list.isEmpty()){
				for(OcrmFInterviewNewRecord r:list){
					task.setId(r.getId());
					task.setTaskNumber(r.getTaskNumber());
				}
			}else{
				task.setTaskNumber(task.getTaskNumber().split(",")[0]);
				task.setId(null);
			}
			/**
			 * 保存按钮设置任务状态为反馈暂存,
			 */
			List<OcrmFInterviewTask> ts = 
				this.findByJql("select c from OcrmFInterviewTask c where c.taskNumber = '"+task.getTaskNumber().split(",")[0]+"'", null);
//			OcrmFInterviewTask t = this.em.
//				this.em.find(OcrmFInterviewTask.class, task.getTaskNumber().split(",")[0]);
			for(OcrmFInterviewTask t:ts){
				t.setReviewState("1");//未完成
				t.setVisitType(visitType);
				try {
					t.setVisitTime(new SimpleDateFormat("yyyy-MM-dd").parse(visitTime));
					t.setVisitStartTime(visitStartTime);
					t.setVisitEndTime(visitEndTime);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				if("2".equals(visitType)){//旧户新案
					t.setModelType("1");
				}
				super.save(t);
				
				/**
				 * 修改实际拜访日期和预约拜访日期时 修改日程
				 */
				try {
					saveScheduleVisit(t, task);//保存
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			return super.save(task);
	    }
	 /**
	  * 修改日程
	  * @param t
	  * @param newTask
	 * @throws ParseException 
	  */
	 @SuppressWarnings("unchecked")
	public  void saveScheduleVisit(OcrmFInterviewTask t,OcrmFInterviewNewRecord newTask) throws ParseException{
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
	   		   if(newTask.getCallTime()!= null){
	   			   date = sdf.format(newTask.getCallTime()).toString();
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
	   		   visit.setPhone(newTask.getIntervieweePhone());
	   		   visit.setVisitor(t.getMgrId());
	   		   visit.setVisitStat("ap");
	   		   visit.setArangeId(t.getMgrId());
	   		   visit.setUserId(t.getMgrId());
	   		   visit.setUserName(t.getMgrName());
	   		   if(newTask.getCallTime()!= null){
	   			   visit.setSchStartTime(newTask.getCallTime());
		   		   visit.setSchEdnTime(newTask.getCallTime());
	   		   }else{
	   			   visit.setSchStartTime(t.getVisitTime());
		   		   visit.setSchEdnTime(t.getVisitTime());
	   		   }
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
	  * @param newRecord
	  */
	 @SuppressWarnings("unchecked")
	public void saveNewRecord(OcrmFInterviewTask task,OcrmFInterviewNewRecord record){
		 //新的拜访任务
		 if(task.getNewRecordId()!=null && "0".equals(task.getFlag())){
			 OcrmFInterviewTask newTask = this.em.find(OcrmFInterviewTask.class, task.getNewRecordId());
			 if(newTask != null){//可能任务已经被删除 所有有此判断
				 newTask.setVisitTime(record.getCallNexttime());
				 super.save(newTask);
				 List<OcrmFInterviewNewRecord> newRecord = 
					 this.findByJql("select t from OcrmFInterviewNewRecord t where t.taskNumber = '"+newTask.getId()+"'", null);
				 //修改日程
				 try {
					 saveScheduleVisit(newTask,newRecord.get(0));
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
			 OcrmFInterviewNewRecord newRecord =  new OcrmFInterviewNewRecord();
			 newRecord.setTaskNumber(t.getId());
			 newRecord.setIntervieweeName(record.getIntervieweeName());
			 newRecord.setIntervieweePhone(record.getIntervieweePhone());
			 newRecord.setIntervieweePost(record.getIntervieweePost());
			 newRecord.setJoinPerson(record.getJoinPerson());
			 newRecord.setJoinPersonId(record.getJoinPersonId());
//			 newRecord.setCallTime(record.getCallTime());
			 newRecord.setCusDomicile(record.getCusDomicile());
			 newRecord.setCusNature(record.getCusNature());
			 newRecord.setCusLegalperson(record.getCusLegalperson());
			 newRecord.setCusRegtime(record.getCusRegtime());
			 newRecord.setCusCntpeople(record.getCusCntpeople());
			 newRecord.setCusOnmark(record.getCusOnmark());
			 newRecord.setCusOnmarkplace(record.getCusOnmarkplace());
			 newRecord.setCusOwnbusi(record.getCusOwnbusi());
			 newRecord.setCusBusistatus(record.getCusBusistatus());
			 newRecord.setCusOperateperson(record.getCusOperateperson());
			 newRecord.setCusAccountperson(record.getCusAccountperson());
			 newRecord.setCusMajorproduct(record.getCusMajorproduct());
			 newRecord.setCusMajorrival(record.getCusMajorrival());
			 newRecord.setDcrbMajorsholder(record.getDcrbMajorsholder());
			 newRecord.setDcrbFlow(record.getDcrbFlow());
			 newRecord.setDcrbFixedassets(record.getDcrbFixedassets());
			 newRecord.setDcrbProfit(record.getDcrbProfit());
			 newRecord.setDcrbSymbiosis(record.getDcrbSymbiosis());
			 newRecord.setDcrbOthertrade(record.getDcrbOthertrade());
			 newRecord.setDcrbMyselftrade(record.getDcrbMyselftrade());
			 newRecord.setResCustsource(record.getResCustsource());
			 newRecord.setResCasebyperson(record.getResCasebyperson());
			 newRecord.setResCasebyptel(record.getResCasebyptel());
			 newRecord.setResFollowup(record.getResFollowup());
			 newRecord.setResOtherinfo(record.getResOtherinfo());
//			 newRecord.setMarkResult(record.getMarkResult());
//			 newRecord.setMarkRefusereason(record.getMarkRefusereason());
			 newRecord.setCallSpendtime(record.getCallSpendtime());
//			 newRecord.setCallNexttime(record.getCallNexttime());
			 newRecord.setCreateTime(new Date());
			 newRecord.setRemark(record.getRemark());
			 super.save(newRecord);
			 //股东及持股比例
			 List<OcrmFInterviewShareholder> shareholderOlds  = 
				 this.findByJql("select t from OcrmFInterviewShareholder t  where t.taskNumber = '"+task.getId()+"'", null);
			 for(OcrmFInterviewShareholder sh : shareholderOlds){
				 OcrmFInterviewShareholder shareholder =  new OcrmFInterviewShareholder();
				 shareholder.setTaskNumber(t.getId());
				 shareholder.setMSponsor(sh.getMSponsor());
				 shareholder.setMMoney(sh.getMMoney());
				 shareholder.setMRatio(sh.getMRatio());
				 super.save(shareholder);
			 }
			 //主要固定资产
			 List<OcrmFInterviewFixedasset> fixedassets  = 
				 this.findByJql("select t from OcrmFInterviewFixedasset t  where t.taskNumber = '"+task.getId()+"'", null); 
			 for(OcrmFInterviewFixedasset fd : fixedassets){
				 OcrmFInterviewFixedasset asset =  new OcrmFInterviewFixedasset();
				 asset.setTaskNumber(t.getId());
				 asset.setFHtype(fd.getFHtype());
				 asset.setFOtype(fd.getFOtype());
				 asset.setFArea(fd.getFArea());
				 asset.setFUtype(fd.getFUtype());
				 asset.setFAssess(fd.getFAssess());
				 asset.setFMemo(fd.getFMemo());
				 super.save(asset);
			 }
			//营收、获利情况
			 List<OcrmFInterviewProfit> pfs  = 
				 this.findByJql("select t from OcrmFInterviewProfit t  where t.taskNumber = '"+task.getId()+"'", null);
			 for(OcrmFInterviewProfit pf : pfs){
				 OcrmFInterviewProfit fit =  new OcrmFInterviewProfit();
				 fit.setTaskNumber(t.getId());
				 fit.setPYears(pf.getPYears());
				 fit.setPYearsEnd(pf.getPYearsEnd());
				 fit.setPRevenue(pf.getPRevenue());
				 fit.setPGross(pf.getPGross());
				 fit.setPPnet(pf.getPPnet());
				 fit.setPMemo(pf.getPMemo());
				 super.save(fit);
			 }
			//原料采购情况
			 List<OcrmFInterviewMatepurchase> purs  = 
				 this.findByJql("select t from OcrmFInterviewMatepurchase t  where t.taskNumber = '"+task.getId()+"'", null);
			 for(OcrmFInterviewMatepurchase pur : purs){
				 OcrmFInterviewMatepurchase p =  new OcrmFInterviewMatepurchase();
				 p.setTaskNumber(t.getId());
				 p.setMpGoods(pur.getMpGoods());
				 p.setMpSupplier(pur.getMpSupplier());
				 p.setMpIsrelate(pur.getMpIsrelate());
				 p.setMpMonth2money(pur.getMpMonth2money());
				 p.setMpBalancedays(pur.getMpBalancedays());
				 p.setMpTradeyears(pur.getMpTradeyears());
				 p.setMpPayway(pur.getMpPayway());
				 p.setMpMemo(pur.getMpMemo());
				 super.save(p);
			 }     
			//产品销售状况
			 List<OcrmFInterviewProsale> pros  = 
				 this.findByJql("select t from OcrmFInterviewProsale t  where t.taskNumber = '"+task.getId()+"'", null);
			 for(OcrmFInterviewProsale pro : pros){
				 OcrmFInterviewProsale pr =  new OcrmFInterviewProsale();
				 pr.setTaskNumber(t.getId());
				 pr.setPsGoods(pro.getPsGoods());
				 pr.setPsBalancedays(pro.getPsBalancedays());
				 pr.setPsBuyer(pro.getPsBuyer());
				 pr.setPsIsrelate(pro.getPsIsrelate());
				 pr.setPsMonth2money(pro.getPsMonth2money());
				 pr.setPsTradeyears(pro.getPsTradeyears());
				 pr.setPsPayway(pro.getPsPayway());
				 pr.setPsMemo(pro.getPsMemo());
				 super.save(pr);
			 } 
			//存款往来银行
			 List<OcrmFInterviewDepositbank> banks  = 
				 this.findByJql("select t from OcrmFInterviewDepositbank t  where t.taskNumber = '"+task.getId()+"'", null);
			 for(OcrmFInterviewDepositbank bank : banks){
				 OcrmFInterviewDepositbank bk =  new OcrmFInterviewDepositbank();
				 bk.setTaskNumber(t.getId());
				 bk.setDBankname(bank.getDBankname());
				 bk.setDAvgdeposit(bank.getDAvgdeposit());
				 super.save(bk);
			 } 
			//贷款往来银行
			 List<OcrmFInterviewLoanbank> loanBanks  = 
				 this.findByJql("select t from OcrmFInterviewLoanbank t  where t.taskNumber = '"+task.getId()+"'", null);
			 for(OcrmFInterviewLoanbank loanBank : loanBanks){
				 OcrmFInterviewLoanbank lbk =  new OcrmFInterviewLoanbank();
				 lbk.setTaskNumber(t.getId());
				 lbk.setLBankname(loanBank.getLBankname());
				 lbk.setLLimittype(loanBank.getLLimittype());
				 lbk.setLLimitmoney(loanBank.getLLimitmoney());
				 lbk.setLBalance(loanBank.getLBalance());
				 lbk.setLRate(loanBank.getLRate());
				 lbk.setLCollateral(loanBank.getLCollateral());
				 lbk.setLMemo(loanBank.getLMemo());
				 lbk.setLDbrate(loanBank.getLDbrate());
				 super.save(lbk);
			 } 
			//拟承做存款产品
			 List<OcrmFInterviewDepositpro> dps  = 
				 this.findByJql("select t from OcrmFInterviewDepositpro t  where t.taskNumber = '"+task.getId()+"'", null);
			 for(OcrmFInterviewDepositpro dp : dps){
				 OcrmFInterviewDepositpro dpo =  new OcrmFInterviewDepositpro();
				 dpo.setTaskNumber(t.getId());
				 dpo.setDpName(dp.getDpName());
				 dpo.setDpAvgdeposit(dp.getDpAvgdeposit());
				 super.save(dpo);
			 } 
			//拟申请外汇产品额度
			 List<OcrmFInterviewForexlimit> mits  = 
				 this.findByJql("select t from OcrmFInterviewForexlimit t  where t.taskNumber = '"+task.getId()+"'", null);
			 for(OcrmFInterviewForexlimit mit : mits){
				 OcrmFInterviewForexlimit m =  new OcrmFInterviewForexlimit();
				 m.setTaskNumber(t.getId());
				 m.setFlName(mit.getFlName());
				 m.setFlLimitmoney(mit.getFlLimitmoney());
				 m.setFlDeal2month(mit.getFlDeal2month());
				 super.save(m);
			 }  
			//拟申请授信产品
			 List<OcrmFInterviewCreditpro> cs  = 
				 this.findByJql("select t from OcrmFInterviewCreditpro t  where t.taskNumber = '"+task.getId()+"'", null);
			 for(OcrmFInterviewCreditpro c : cs){
				 OcrmFInterviewCreditpro cp =  new OcrmFInterviewCreditpro();
				 cp.setTaskNumber(t.getId());
				 cp.setCpUse(c.getCpUse());
				 cp.setCpProduct(c.getCpProduct());
				 cp.setCpProductP(c.getCpProductP());
				 cp.setCpCurrency(c.getCpCurrency());
				 cp.setCpLimitmoney(c.getCpLimitmoney());
				 cp.setCpCollateral(c.getCpCollateral());
				 cp.setCpDbrate(c.getCpDbrate());
				 cp.setCpMemo(c.getCpMemo());
				 super.save(cp);
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
