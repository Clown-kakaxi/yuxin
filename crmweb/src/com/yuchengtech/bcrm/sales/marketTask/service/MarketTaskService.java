package com.yuchengtech.bcrm.sales.marketTask.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTask;
import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTaskTarget;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @describtion: 营销任务信息
 *
 * @author : helin
 * @date : 2014-07-03 10:19:20
 */
@Service
public class MarketTaskService extends CommonService {
    public MarketTaskService(){
        JPABaseDAO<OcrmFMmTask, Long> baseDao = new JPABaseDAO<OcrmFMmTask, Long>(OcrmFMmTask.class);
        super.setBaseDAO(baseDao);
    }
    
    /**
     * 新增或修改任务
     */
    public Object save(Object obj) {
    	ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	String targetIds = request.getParameter("targetIds");// 指标ID集合
		String targetValueData = request.getParameter("targetDataValue");// 填写的指标值
		
		String cycleIds = request.getParameter("cycleIds");// 指标ID集合
		String cycleNames = request.getParameter("cycleNames");// 填写的指标值
		
        OcrmFMmTask marketTask = (OcrmFMmTask)obj;
        if("01".equals(marketTask.getTargetType())){	//指标类型：01、周期性指标，02非周期性指标
        	String[] cycleIdArr = cycleIds.split(",");
        	String[] tempArr = cycleIdArr[0].split("#");
        	marketTask.setTaskBeginDate(DateUtils.parseDate(tempArr[1]));
        	marketTask.setTaskEndDate(DateUtils.parseDate(tempArr[2]));
        	for(int i=1;i<cycleIdArr.length;i++){
        		tempArr = cycleIdArr[i].split("#");
        		if(marketTask.getTaskBeginDate().getTime() > DateUtils.parseDate(tempArr[1]).getTime()){
        			marketTask.setTaskBeginDate(DateUtils.parseDate(tempArr[1]));
        		}
        		if(marketTask.getTaskEndDate().getTime() < DateUtils.parseDate(tempArr[2]).getTime()){
        			marketTask.setTaskEndDate(DateUtils.parseDate(tempArr[2]));
        		}
        	}
    	}
        if(marketTask.getTaskId() == null){
        	//新增营销任务
        	return this.saveMainTask(marketTask,targetIds,targetValueData,cycleIds,cycleNames,auth);
        }else{
        	//修改营销任务
        	return this.updateMainTask(marketTask,targetIds,targetValueData,cycleIds,cycleNames,auth);
        }
    }
    
    /**
     * 新增主营销任务
     * @param mainTask	主任务
     * @param targetIds 任务指标ID集合,如： A,B,C,D
     * @param targetValueData 任务指标值,如：1,2,3,4;11,22,33,44;
     * @param cycleIds 周期类型ID集合,如：2#2014-01-01#2014-01-31,2#2014-02-01#2014-01-28
     * @param cycleNames 周期类型NAME集合,如 2014年1月,2014年2月
     * @param auth
     * @return
     */
    private Object saveMainTask(OcrmFMmTask mainTask,String targetIds,String targetValueData,String cycleIds,String cycleNames,AuthUser auth){
    	Date currDate = new Date();
    	OcrmFMmTask tempMarketTask = new OcrmFMmTask();
    	mainTask.setCreateUser(auth.getUserId());
    	mainTask.setCreateUserName(auth.getUsername());
    	mainTask.setCreateOrgId(auth.getUnitId());
    	mainTask.setCreateOrgName(auth.getUnitName());
    	mainTask.setCreateDate(currDate);
    	mainTask.setRecentlyUpdateDate(currDate);
    	mainTask.setRecentlyUpdateId(auth.getUserId());
    	mainTask.setRecentlyUpdateName(auth.getUsername());
    	mainTask.setTaskStat("1");		//任务状态task_stat：1暂存，2执行中，3已下达，4已关闭
    	
    	BeanUtils.copyProperties(mainTask, tempMarketTask);
    	mainTask.setOperObjId(null);
    	mainTask.setOperObjName(null);
    	Object obj = super.save(mainTask);
    	tempMarketTask.setTaskId(mainTask.getTaskId());
    	tempMarketTask.setTaskParentId(BigDecimal.valueOf(mainTask.getTaskId()));
    	this.saveSubTask(tempMarketTask,targetIds,targetValueData,cycleIds,cycleNames);
		this.em.createNativeQuery("INSERT INTO OCRM_F_MM_TASK_TARGET(TARGET_NO,TASK_ID,TARGET_CODE,CYCLE_TYPE,CYCLE_NAME,START_DATE,END_DATE,ORIGINAL_VALUE,TARGET_VALUE,ACHIEVE_VALUE,ACHIEVE_PERCENT,CREATE_USER_ID,CREATE_USER_NAME,RECENTLY_UPDATE_ID,RECENTLY_UPDATE_NAME,RECENTLY_UPDATE_DATE) "
				+" SELECT ID_SEQUENCE.NEXTVAL,'"+mainTask.getTaskId()+"',TARGET_CODE,CYCLE_TYPE,CYCLE_NAME,START_DATE,END_DATE,0,TARGET_VALUE,0,0,'"+mainTask.getCreateUser()+"','"+mainTask.getCreateUserName()+"','"+mainTask.getRecentlyUpdateId()+"','"+mainTask.getRecentlyUpdateName()+"',to_date('"+DateUtils.formatDate(mainTask.getRecentlyUpdateDate())+"','yyyy-MM-dd')"
				+" FROM (SELECT T.TARGET_CODE,T.CYCLE_TYPE,T.CYCLE_NAME,T.START_DATE,T.END_DATE,SUM(T.TARGET_VALUE) TARGET_VALUE"
				+" FROM OCRM_F_MM_TASK_TARGET T INNER JOIN OCRM_F_MM_TASK T1 ON T1.TASK_ID = T.TASK_ID WHERE T1.TASK_PARENT_ID = '"+mainTask.getTaskId()+"' GROUP BY T.TARGET_CODE,T.CYCLE_TYPE,T.START_DATE,T.END_DATE,T.CYCLE_NAME) T2").executeUpdate();
    	return obj;
    }
    
    /**
     * 保存主任务对应的多个子任务
     * @param mainTask 主任务
     * @param targetIds	任务指标ID集合,如： A,B,C,D
     * @param targetValueData 任务指标值,如：1,2,3,4;11,22,33,44;
     * @param cycleIds 周期类型ID集合,如：2#2014-01-01#2014-01-31,2#2014-02-01#2014-01-28
     * @param cycleNames 周期类型NAME集合,如 2014年1月,2014年2月
     */
    private void saveSubTask(OcrmFMmTask mainTask,String targetIds,String targetValueData,String cycleIds,String cycleNames){
		String[] targetValueDataArr = targetValueData.split(";");
		String operObjIds = mainTask.getOperObjId();
		if (operObjIds != null && !"".equals(operObjIds)) {
			String[] operObjIdArr = operObjIds.split(",");
			String[] operObjNameArr = mainTask.getOperObjName().split(",");
			if (operObjIdArr != null && operObjIdArr.length > 0) {
				for (int i = 0; i < operObjIdArr.length; i++) {
					if (operObjIdArr[i] != null && !"".equals(operObjIdArr[i].trim())) {
						OcrmFMmTask subTask = new OcrmFMmTask();
						BeanUtils.copyProperties(mainTask, subTask);
						subTask.setTaskId(null);					//将复制过来的子任务ID重置为空
						subTask.setTaskName(mainTask.getTaskName() + "-"+ operObjNameArr[i]);	// 营销任务名称
						subTask.setOperObjId(operObjIdArr[i]);		// 执行对象ID
						subTask.setOperObjName(operObjNameArr[i]);	// 执行对象名称
						subTask.setTaskParentId(BigDecimal.valueOf((mainTask.getTaskId())));	// 上级任务ID
						subTask.setTaskStat("1");					// 任务状态（暂存）
						super.save(subTask);
						this.saveSubTaskTarget(subTask, targetIds,targetValueDataArr[i],cycleIds,cycleNames);
					}
				}
			}
		}
    }
    
    /**
     * 保存单个子任务多指标值
     * @param subTask	子任务
     * @param targetIds	任务指标ID集合,如： A,B,C,D
     * @param targetValueData	任务指标值,如：1,2,3,4
     * @param cycleIds 周期类型ID集合,如：2#2014-01-01#2014-01-31,2#2014-02-01#2014-01-28
     * @param cycleNames 周期类型NAME集合,如 2014年1月,2014年2月
     */
    private void saveSubTaskTarget(OcrmFMmTask subTask,String targetIds,String targetValueData,String cycleIds,String cycleNames){
    	String[] targetIdArr = targetIds.split(",");
		String[] targetValueArr = targetValueData.split(",");
		String[] cycleIdsArr = cycleIds.split(",");
		String[] cycleNamesArr = cycleNames.split(",");
		OcrmFMmTaskTarget subTaskTarget = null;
		if (targetIdArr != null && targetIdArr.length > 0) {
			for (int i = 0; i < targetIdArr.length; i++) {
				if("01".equals(subTask.getTargetType())){
					for(int j=0;j<cycleIdsArr.length;j++){
						String[] cycleArr = cycleIdsArr[j].split("#");
						subTaskTarget = new OcrmFMmTaskTarget();
						subTaskTarget.setTaskId(subTask.getTaskId());	// 任务ID
						subTaskTarget.setTargetCode(targetIdArr[i]);	// 指标ID
						subTaskTarget.setOriginalValue(BigDecimal.valueOf(0));	//初始值
						subTaskTarget.setTargetValue(new BigDecimal(targetValueArr[(i * cycleIdsArr.length) + j]));// 指标目标值
						subTaskTarget.setAchieveValue(BigDecimal.valueOf(0));	//完成值
						subTaskTarget.setAchievePercent(BigDecimal.valueOf(0));	//完成百分比
						
						subTaskTarget.setCycleType(cycleArr[0]);
						subTaskTarget.setStartDate(DateUtils.parseDate(cycleArr[1]));
						subTaskTarget.setEndDate(DateUtils.parseDate(cycleArr[2]));
						subTaskTarget.setCycleName(cycleNamesArr[j]);
						
						subTaskTarget.setCreateUserId(subTask.getRecentlyUpdateId());
						subTaskTarget.setCreateUserName(subTask.getRecentlyUpdateName());
						subTaskTarget.setRecentlyUpdateId(subTask.getRecentlyUpdateId());
						subTaskTarget.setRecentlyUpdateName(subTask.getRecentlyUpdateName());
						subTaskTarget.setRecentlyUpdateDate(subTask.getCreateDate());
						super.save(subTaskTarget);
					}
				}else{
					subTaskTarget = new OcrmFMmTaskTarget();
					subTaskTarget.setTaskId(subTask.getTaskId());	// 任务ID
					subTaskTarget.setTargetCode(targetIdArr[i]);	// 指标ID
					subTaskTarget.setOriginalValue(BigDecimal.valueOf(0));	//初始值
					subTaskTarget.setTargetValue(new BigDecimal(targetValueArr[i]));// 指标目标值
					subTaskTarget.setAchieveValue(BigDecimal.valueOf(0));	//完成值
					subTaskTarget.setAchievePercent(BigDecimal.valueOf(0));	//完成百分比
					
					subTaskTarget.setCreateUserId(subTask.getRecentlyUpdateId());
					subTaskTarget.setCreateUserName(subTask.getRecentlyUpdateName());
					subTaskTarget.setRecentlyUpdateId(subTask.getRecentlyUpdateId());
					subTaskTarget.setRecentlyUpdateName(subTask.getRecentlyUpdateName());
					subTaskTarget.setRecentlyUpdateDate(subTask.getCreateDate());
					super.save(subTaskTarget);
				}
			}
		}

    }
    
    /**
     * 修改主任务
     * @param mainTask 主任务
     * @param targetIds 任务指标ID集合,如： A,B,C,D
     * @param targetValueData 任务指标值,如：1,2,3,4;11,22,33,44;
     * @param cycleIds 周期类型ID集合,如：2#2014-01-01#2014-01-31,2#2014-02-01#2014-01-28
     * @param cycleNames 周期类型NAME集合,如 2014年1月,2014年2月
     * @param auth
     * @return
     */
    private Object updateMainTask(OcrmFMmTask mainTask,String targetIds,String targetValueData,String cycleIds,String cycleNames,AuthUser auth){
    	//根据任务ID,查找数据库任务ID
    	OcrmFMmTask tempTask = em.find(OcrmFMmTask.class,mainTask.getTaskId());
    	Object obj = null;
    	if (tempTask != null) {
			tempTask.setTaskName(mainTask.getTaskName());
			tempTask.setTaskType(mainTask.getTaskType());
			tempTask.setDistTaskType(mainTask.getDistTaskType());
			tempTask.setTargetType(mainTask.getTargetType());
			tempTask.setMemo(mainTask.getMemo());
			
			tempTask.setTaskBeginDate(mainTask.getTaskBeginDate());
			tempTask.setTaskEndDate(mainTask.getTaskEndDate());
			
			tempTask.setRecentlyUpdateDate(new Date());
			tempTask.setRecentlyUpdateId(auth.getUserId());
			tempTask.setRecentlyUpdateName(auth.getUsername());
			obj = super.save(tempTask);	//保存主任务信息
			
			mainTask.setCreateDate(tempTask.getCreateDate());
			mainTask.setCreateOrgId(tempTask.getCreateOrgId());
			mainTask.setCreateOrgName(tempTask.getCreateOrgName());
			mainTask.setCreateUser(tempTask.getCreateUser());
			mainTask.setCreateUserName(tempTask.getCreateUserName());
			mainTask.setRecentlyUpdateDate(new Date());
			mainTask.setRecentlyUpdateId(auth.getUserId());
			mainTask.setRecentlyUpdateName(auth.getUsername());
			this.deleteSubTask(mainTask);
			//临时性的把执行对象更新为前台传过来的值,便于创建子任务,但不作保存
			this.saveSubTask(mainTask,targetIds,targetValueData,cycleIds,cycleNames);
		}
    	//删除顶级任务指标值
    	this.em.createNativeQuery("delete from OCRM_F_MM_TASK_TARGET t where t.task_id = '"+mainTask.getTaskId()+"'").executeUpdate();
    	//重新计算顶级任务指标值
    	this.em.createNativeQuery("INSERT INTO OCRM_F_MM_TASK_TARGET(TARGET_NO,TASK_ID,TARGET_CODE,CYCLE_TYPE,CYCLE_NAME,START_DATE,END_DATE,ORIGINAL_VALUE,TARGET_VALUE,ACHIEVE_VALUE,ACHIEVE_PERCENT,CREATE_USER_ID,CREATE_USER_NAME,RECENTLY_UPDATE_ID,RECENTLY_UPDATE_NAME,RECENTLY_UPDATE_DATE) "
				+" SELECT ID_SEQUENCE.NEXTVAL,'"+mainTask.getTaskId()+"',TARGET_CODE,CYCLE_TYPE,CYCLE_NAME,START_DATE,END_DATE,0,TARGET_VALUE,0,0,'"+mainTask.getCreateUser()+"','"+mainTask.getCreateUserName()+"','"+mainTask.getRecentlyUpdateId()+"','"+mainTask.getRecentlyUpdateName()+"',to_date('"+DateUtils.formatDate(mainTask.getRecentlyUpdateDate())+"','yyyy-MM-dd')"
				+" FROM (SELECT T.TARGET_CODE,T.CYCLE_TYPE,T.CYCLE_NAME,T.START_DATE,T.END_DATE,SUM(T.TARGET_VALUE) TARGET_VALUE"
				+" FROM OCRM_F_MM_TASK_TARGET T INNER JOIN OCRM_F_MM_TASK T1 ON T1.TASK_ID = T.TASK_ID WHERE T1.TASK_PARENT_ID = '"+mainTask.getTaskId()+"' GROUP BY T.TARGET_CODE,T.CYCLE_TYPE,T.START_DATE,T.END_DATE,T.CYCLE_NAME) T2").executeUpdate();
    	
    	return obj;
    }
    
    /**
     * 删除主任务对应的子任务及其子任务指标值
     * @param taskId 主任务id
     */
    private void deleteSubTask(OcrmFMmTask mainTask){
    	//删除子任务对应的指标
    	this.em.createNativeQuery("DELETE FROM OCRM_F_MM_TASK_TARGET T1 WHERE T1.TASK_ID IN (SELECT T.TASK_ID FROM OCRM_F_MM_TASK T WHERE T.TASK_PARENT_ID = '"+mainTask.getTaskId()+"')").executeUpdate();
    	//删除子任务
    	this.em.createNativeQuery("DELETE FROM OCRM_F_MM_TASK T where t.TASK_PARENT_ID = '"+mainTask.getTaskId()+"'").executeUpdate();
    }
    
    /**
     * 营销任务删除，暂且支持单条删除,且只能删除暂存状态的任务
     */
    public void batchRemove(String ids) {
        Map<String, Object> values = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM OcrmFMmTask t WHERE t.taskId IN("+ ids +")", values);
        OcrmFMmTask mainTask = new OcrmFMmTask();
        mainTask.setTaskId(Long.valueOf(ids));
        this.deleteSubTask(mainTask);
    }
    
    /**
     * 查询任务对象及其指标
     * @param taskId 任务id
     * @return
     */
    public Map<String, String> queryTaskTarget(String taskId){
    	Map<String, String> map = new HashMap<String, String>();
    	
    	String oper_obj_id = "";		//执行对象id
    	String oper_obj_name = "";		//执行对象名称
    	
    	String target_id = "";			//指标id
    	String target_name = "";		//指标名称
    	String achieve_value_state=""; //完成值审批状态
    	String achieve_remark="";
    	
    	String cycle_id = "";			//周期id
    	String cycle_name = "";			//周期名称
    	
    	String target_value = "";			//子任务指标值
    	String temp_target_value = "";		//临时子任务指标值
    	String achieve_values = "";			//子任务指标完成值
    	String temp_achieve_values = "";	//临时子任务指标完成值
    	String achieve_percents = "";		//子任务指标完成百分比
    	String temp_achieve_percents = "";	//临时子任务指标完成百分比
    	
    	String curr_target_value = ""; //当前任务指标
    	String curr_achieve_values = "";//本任务完成指标值
    	String curr_achieve_percents = "";//本任务完成指标百分比
    	
    	String subTaskId = null;
    	
    	//获取执行对象,右无子任务，则执行对象取自身
    	List<?> taskList = this.em.createNativeQuery("SELECT TASK_ID,OPER_OBJ_ID,OPER_OBJ_NAME FROM OCRM_F_MM_TASK T WHERE T.TASK_PARENT_ID = '"+taskId+"'").getResultList();
    	if(taskList != null && taskList.size() > 0){
    		for(int i=0;i<taskList.size();i++){
    			Object[] taskObj = (Object[]) taskList.get(i);
    			subTaskId = String.valueOf(taskObj[0]);
    			if(i == 0){
	        		oper_obj_id = String.valueOf(taskObj[1]);
	        		oper_obj_name = String.valueOf(taskObj[2]);
    			}else{
    				oper_obj_id += ","+String.valueOf(taskObj[1]);
	        		oper_obj_name += ","+String.valueOf(taskObj[2]);
    			}
    			
    			//每个执行对象对应的指标值,此处的order by 不能随便更改,否则会影响调整时，对象未对应上
    			List<?> taskTargetList = this.em.createNativeQuery("SELECT T.TARGET_VALUE,T.ACHIEVE_VALUE,T.ACHIEVE_PERCENT,T.TASK_ID,T.TARGET_CODE,T.END_DATE FROM OCRM_F_MM_TASK_TARGET T WHERE T.TASK_ID = '"+subTaskId+"' ORDER BY T.TARGET_CODE,T.CYCLE_TYPE,T.END_DATE").getResultList();
    	    	if(taskTargetList != null && taskTargetList.size()>0){
    	    		for(int j=0;j<taskTargetList.size();j++){
    	    			Object[] taskTargetObj = (Object[]) taskTargetList.get(j);
    	    			if(j == 0){
    	    				temp_target_value = String.valueOf(taskTargetObj[0]);
    	    				temp_achieve_values = String.valueOf(taskTargetObj[1]);
    	    				temp_achieve_percents = String.valueOf(taskTargetObj[2]);
    	    			}else{
    	    				temp_target_value += ","+String.valueOf(taskTargetObj[0]);
    	    				temp_achieve_values += ","+String.valueOf(taskTargetObj[1]);
    	    				temp_achieve_percents += ","+String.valueOf(taskTargetObj[2]);
    	    			}
    	    		}
    	    	}
    			target_value += temp_target_value + ";";
    			achieve_values += temp_achieve_values + ";";
    			achieve_percents += temp_achieve_percents + ";";
    		}
    		target_value = target_value.substring(0,target_value.length() -1);
    		achieve_values = achieve_values.substring(0,achieve_values.length() -1);
    		achieve_percents = achieve_percents.substring(0,achieve_percents.length() -1);
    	}else{
    		//查询当前任务指标，无子任务时进行此
    		taskList = this.em.createNativeQuery("SELECT TASK_ID,OPER_OBJ_ID,OPER_OBJ_NAME FROM OCRM_F_MM_TASK T WHERE T.TASK_ID = '"+taskId+"'").getResultList();
    		Object[] taskObj = (Object[]) taskList.get(0);
    		oper_obj_id = String.valueOf(taskObj[1]);
    		oper_obj_name = String.valueOf(taskObj[2]);
    	}
    	
    	//获取指标值id
    	List<?> targetList = this.em.createNativeQuery("SELECT T.TARGET_CODE,T1.TARGET_NAME,  ITEM.F_VALUE  AS ACHIEVE_VALUE_STATE,T.ACHIEVE_REMARK FROM OCRM_F_MM_TASK_TARGET T LEFT JOIN OCRM_F_MM_TARGET T1 ON T1.TARGET_CODE = T.TARGET_CODE "
    			+ " LEFT JOIN OCRM_SYS_LOOKUP_ITEM ITEM ON ITEM.F_CODE=T.ACHIEVE_VALUE_STATE and ITEM.F_LOOKUP_ID='APPROVEL_STATUS'  "
    			+ " WHERE T.TASK_ID = '"+taskId+"' GROUP BY T.TARGET_CODE,T1.TARGET_NAME,ITEM.F_VALUE,T.ACHIEVE_REMARK ORDER BY T.TARGET_CODE").getResultList();
    	if(targetList != null && targetList.size()>0){
    		for(int i=0;i<targetList.size();i++){
    			Object[] taskTargetObj = (Object[]) targetList.get(i);
    			if(i == 0){
    				target_id = String.valueOf(taskTargetObj[0]);
    				target_name = String.valueOf(taskTargetObj[1]);
    				achieve_value_state=String.valueOf(taskTargetObj[2]);
    				achieve_remark=String.valueOf(taskTargetObj[3]);
    			}else{
    				target_id += ","+String.valueOf(taskTargetObj[0]);
    				target_name += ","+String.valueOf(taskTargetObj[1]);
    				achieve_value_state=String.valueOf(taskTargetObj[2]);
    				achieve_remark=String.valueOf(taskTargetObj[3]);
    			}
        	}
    	}
    	//获取周期值
    	List<?> cycleList = this.em.createNativeQuery("SELECT T.CYCLE_TYPE || '#' || TO_CHAR(T.START_DATE, 'YYYY-MM-DD') || '#' || TO_CHAR(T.END_DATE, 'YYYY-MM-DD') AS CYCLE_ID, T.CYCLE_NAME  FROM OCRM_F_MM_TASK_TARGET T WHERE T.TASK_ID = '"+taskId+"' GROUP BY T.CYCLE_TYPE,T.START_DATE,T.END_DATE,T.CYCLE_NAME ORDER BY T.END_DATE").getResultList();
    	if(cycleList != null && cycleList.size()>0){
    		for(int i=0;i<cycleList.size();i++){
    			Object[] taskCycleObj = (Object[]) cycleList.get(i);
    			if(i == 0){
    				cycle_id = String.valueOf(taskCycleObj[0]);
    				cycle_name = String.valueOf(taskCycleObj[1]);
    				if("##".equals(cycle_id)){//判断是否是非周期性指标
    					cycle_id = "";
    					cycle_name = "";
    				}
    			}else{
    				cycle_id += ","+String.valueOf(taskCycleObj[0]);
    				cycle_name += ","+String.valueOf(taskCycleObj[1]);
    			}
        	}
    	}
    	
    	//获取本任务指标值, 排序不能随便变更，变更后前台显示会对应不上
    	List<?> currTaskList = this.em.createNativeQuery("SELECT T.TARGET_VALUE,T.ACHIEVE_VALUE,T.ACHIEVE_PERCENT,T.TASK_ID,T.TARGET_CODE,T.END_DATE FROM OCRM_F_MM_TASK_TARGET T WHERE T.TASK_ID = '"+taskId+"' ORDER BY T.TARGET_CODE,T.CYCLE_TYPE,T.END_DATE").getResultList();
    	if(currTaskList != null && currTaskList.size()>0){
    		for(int i=0;i<currTaskList.size();i++){
    			Object[] taskObj = (Object[]) currTaskList.get(i);
    			if(i == 0){
    				curr_target_value = String.valueOf(taskObj[0]);
    				curr_achieve_values = String.valueOf(taskObj[1]);
    				curr_achieve_percents = String.valueOf(taskObj[2]);
    			}else{
    				curr_target_value += ","+String.valueOf(taskObj[0]);
    				curr_achieve_values += ","+ String.valueOf(taskObj[1]);
    				curr_achieve_percents += "," + String.valueOf(taskObj[2]);
    			}
    		}
    	}
    	
    	map.put("oper_obj_ids", oper_obj_id);					//执行对象ID集合
    	map.put("oper_obj_names", oper_obj_name);				//执行对象名称集合
    	
    	map.put("target_ids", target_id);						//指标ID集合
    	map.put("target_names", target_name);					//指标名称集合
    	map.put("achieve_value_states", achieve_value_state);   //完成值审批状态
    	map.put("achieve_remarks", achieve_remark); 
    	
    	map.put("cycle_ids", cycle_id);							//指标周期类型集合
    	map.put("cycle_names", cycle_name);						//指标周期名称集合
    	
    	map.put("target_values", target_value);					//子任务指标值集合
    	map.put("achieve_values", achieve_values);	//子任务指标完成值集合
    	map.put("achieve_percents", achieve_percents);//子任务指标完成百分比集合
    	
    	map.put("curr_target_values", curr_target_value);		//当前任务指标值集合
    	map.put("curr_achieve_values", curr_achieve_values);	//本任务指标完成值集合
    	map.put("curr_achieve_percents", curr_achieve_percents);//本任务指标完成百分比
    	return map;
    }
    
    /**
     * 下达营销任务
     * @param taskId
     */
    public void transTask(String taskId){
    	AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	List<?> l = em.createNativeQuery("select * from OCRM_F_MM_TASK t where t.task_parent_id = '"+taskId+"'").getResultList();
    	if(!(l != null && l.size() >0)){
    		throw new BizException(1,0,"","任务未分解，不能下达！");
    	}
    	//任务状态task_stat：1暂存，2执行中，3已下达，4已关闭
    	//更新主营销任务
    	this.em.createNativeQuery("update OCRM_F_MM_TASK t set t.task_stat = '3',t.dist_user='"+auth.getUserId()+"',t.dist_user_name='"+auth.getUsername()+"',t.dist_org='"+auth.getUnitId()+"',t.dist_org_name='"+auth.getUnitName()+"',t.task_dist_date = sysdate where t.task_id in ("+taskId+")").executeUpdate();
    	//更新子营销任务
    	this.em.createNativeQuery("update OCRM_F_MM_TASK t set t.task_stat = '2',t.dist_user='"+auth.getUserId()+"',t.dist_user_name='"+auth.getUsername()+"',t.dist_org='"+auth.getUnitId()+"',t.dist_org_name='"+auth.getUnitName()+"',t.task_dist_date = sysdate where t.task_parent_id in ("+taskId+")").executeUpdate();
    }
    
    /**
     * 关闭营销任务
     * @param taskId
     */
    public void closeTask(String taskId){
    	AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	//先判断是否存在子任务没有关闭,判断任务存在“执行中“,“已下达”的子任务
    	List<?> list = this.em.createNativeQuery("select task_id from OCRM_F_MM_TASK where (TASK_STAT = '2' OR TASK_STAT = '3') and task_parent_id = '"+taskId+"'").getResultList();
    	if(list != null && list.size() > 0){
    		throw new BizException(1,0,"","子任务未关闭，不能关闭主任务！");
    	}
    	this.em.createNativeQuery("update OCRM_F_MM_TASK t set t.task_stat = '4',t.recently_update_id='"+auth.getUserId()+"',t.recently_update_name='"+auth.getUsername()+"',t.recently_update_date = sysdate where t.task_id = '"+taskId+"'").executeUpdate();
    }
    
    /**
     * 营销任务调整
     * @param marketTask
     */
    public void adjustTask(OcrmFMmTask marketTask){
    	ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	String operObjIds = marketTask.getOperObjId();	//执行对象ID集合
    	String targetIds = request.getParameter("targetIds");// 指标ID集合
    	String cycleIds = request.getParameter("cycleIds");// 指标ID集合
		String targetValueData = request.getParameter("targetDataValue");// 填写的指标值
		String[] targetValueArr = null;
		String[] targetValueDataArr = targetValueData.split(";"); //即使最后多一组空,也可不用处理,因为指标只有那么多组
		//根据任务ID,查找数据库任务ID
    	OcrmFMmTask tempTask = em.find(OcrmFMmTask.class,marketTask.getTaskId());
//    	if("2".equals(tempTask.getDistTaskType())){
//    		throw new BizException(1, 0, "", "执行对象类型已经是客户经理，不允许调整！");
//    	}
    	//执行对象是机构,但是当前登录人所属机构不是执行对象机构，不不允许分解
    	if("1".equals(tempTask.getDistTaskType()) && !auth.getUnitId().equals(tempTask.getOperObjId()) && tempTask.getOperObjId() != null){
    		throw new BizException(1, 0, "", "执行对象机构不是当前所属机构，不允许调整！");
    	}
    	if("3".equals(tempTask.getDistTaskType())){
    		List<?> list = em.createNativeQuery("select * from OCRM_F_CM_MKT_TEAM where MKT_TEAM_ID = '"+tempTask.getOperObjId()+"' and TEAM_LEADER_ID = '"+auth.getUserId()+"'").getResultList();
    		if(list == null || list.size() <=0){
    			throw new BizException(1, 0, "", "您不是执行对象团队的负责人，不允许调整！");
    		}
    	}
    	tempTask.setRecentlyUpdateDate(new Date());
		tempTask.setRecentlyUpdateId(auth.getUserId());
		tempTask.setRecentlyUpdateName(auth.getUsername());
		super.save(tempTask);	//更新主任务信息
		//sql查询的顺序一定要注意,不然会影响调整的对象指标对不上
		List<OcrmFMmTask> subTaskList = this.em.createQuery("select t from OcrmFMmTask t where t.taskParentId = '"+tempTask.getTaskId()+"' order by t.taskId desc").getResultList();
		for(int i=0;i<subTaskList.size();i++){
			OcrmFMmTask subTask = subTaskList.get(i);
			subTask.setRecentlyUpdateDate(tempTask.getRecentlyUpdateDate());
			subTask.setRecentlyUpdateId(auth.getUserId());
			subTask.setRecentlyUpdateName(auth.getUsername());
			super.save(subTask);	//更新子任务
			
			targetValueArr = targetValueDataArr[i].split(",");
			//更新子任务指标
			List<OcrmFMmTaskTarget> subTargetList = this.em.createQuery("select t from OcrmFMmTaskTarget t where t.taskId = '"+subTask.getTaskId()+"' order by t.targetCode,t.cycleType,t.endDate asc").getResultList();
			for(int j=0;j<subTargetList.size();j++){
				OcrmFMmTaskTarget subTarget = subTargetList.get(j);
				subTarget.setRecentlyUpdateDate(tempTask.getRecentlyUpdateDate());
				subTarget.setRecentlyUpdateId(auth.getUserId());
				subTarget.setRecentlyUpdateName(auth.getUsername());
				subTarget.setTargetValue(new BigDecimal(targetValueArr[j]));//调整目标值
				super.save(subTarget);
			}
		}
		//如果调整的是最顶层任务的子任务,则相应的要调整顶层任务的指标值
		if(tempTask.getTaskParentId() == null){
			//删除顶级任务指标值
	    	this.em.createNativeQuery("delete from OCRM_F_MM_TASK_TARGET t where t.task_id = '"+tempTask.getTaskId()+"'").executeUpdate();
			//重新计算顶级任务指标值
	    	this.em.createNativeQuery("INSERT INTO OCRM_F_MM_TASK_TARGET(TARGET_NO,TASK_ID,TARGET_CODE,CYCLE_TYPE,CYCLE_NAME,START_DATE,END_DATE,ORIGINAL_VALUE,TARGET_VALUE,ACHIEVE_VALUE,ACHIEVE_PERCENT,CREATE_USER_ID,CREATE_USER_NAME,RECENTLY_UPDATE_ID,RECENTLY_UPDATE_NAME,RECENTLY_UPDATE_DATE) "
					+" SELECT ID_SEQUENCE.NEXTVAL,'"+tempTask.getTaskId()+"',TARGET_CODE,CYCLE_TYPE,CYCLE_NAME,START_DATE,END_DATE,0,TARGET_VALUE,0,0,'"+tempTask.getCreateUser()+"','"+tempTask.getCreateUserName()+"','"+tempTask.getRecentlyUpdateId()+"','"+tempTask.getRecentlyUpdateName()+"',to_date('"+DateUtils.formatDate(tempTask.getRecentlyUpdateDate())+"','yyyy-MM-dd')"
					+" FROM (SELECT T.TARGET_CODE,T.CYCLE_TYPE,T.CYCLE_NAME,T.START_DATE,T.END_DATE,SUM(T.TARGET_VALUE) TARGET_VALUE"
					+" FROM OCRM_F_MM_TASK_TARGET T INNER JOIN OCRM_F_MM_TASK T1 ON T1.TASK_ID = T.TASK_ID WHERE T1.TASK_PARENT_ID = '"+tempTask.getTaskId()+"' GROUP BY T.TARGET_CODE,T.CYCLE_TYPE,T.START_DATE,T.END_DATE,T.CYCLE_NAME) T2").executeUpdate();
	    }
    }
    
    /**
     * 营销任务分解,任务分解后,需要下达
     * @param marketTask
     */
    public void resolveTask(OcrmFMmTask marketTask){
    	ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	String targetIds = request.getParameter("targetIds");// 指标ID集合
		String targetValueData = request.getParameter("targetDataValue");// 填写的指标值
		String cycleIds = request.getParameter("cycleIds");// 指标ID集合
		String cycleNames = request.getParameter("cycleNames");// 填写的指标值
		
		//根据任务ID,查找数据库任务ID
    	OcrmFMmTask tempTask = em.find(OcrmFMmTask.class,marketTask.getTaskId());
//    	if("2".equals(tempTask.getDistTaskType())){
//    		throw new BizException(1, 0, "", "执行对象类型已经是客户经理，不允许分解！");
//    	}
    	//执行对象是机构,但是当前登录人所属机构不是执行对象机构，不不允许分解
    	if("1".equals(tempTask.getDistTaskType()) && !auth.getUnitId().equals(tempTask.getOperObjId())){
    		throw new BizException(1, 0, "", "执行对象机构不是当前所属机构，不允许分解！");
    	}
//    	if("3".equals(tempTask.getDistTaskType())){
//    		List<?> list = em.createNativeQuery("select * from OCRM_F_CM_MKT_TEAM where MKT_TEAM_ID = '"+tempTask.getOperObjId()+"' and TEAM_LEADER_ID = '"+auth.getUserId()+"'").getResultList();
//    		if(list == null || list.size() <=0){
//    			throw new BizException(1, 0, "", "您不是执行对象团队的负责人，不允许分解！");
//    		}
//    	}
    	tempTask.setRecentlyUpdateDate(new Date());
		tempTask.setRecentlyUpdateId(auth.getUserId());
		tempTask.setRecentlyUpdateName(auth.getUsername());
		tempTask.setDistOrg(null);
		tempTask.setDistOrgName(null);
		tempTask.setDistUser(null);
		tempTask.setDistUserName(null);
		tempTask.setTaskDistDate(null);
		super.save(tempTask);	//更新主任务信息
		
		marketTask.setTargetType(tempTask.getTargetType());
		marketTask.setTaskType(tempTask.getTaskType());
		marketTask.setTaskName(tempTask.getTaskName());
		marketTask.setTaskBeginDate(tempTask.getTaskBeginDate());
		marketTask.setTaskEndDate(tempTask.getTaskEndDate());
		marketTask.setMemo(tempTask.getMemo());
		
		//分解的子任务，创建人即是分解人
		marketTask.setCreateDate(new Date());
		marketTask.setCreateOrgId(auth.getUnitId());
		marketTask.setCreateOrgName(auth.getUnitName());
		marketTask.setCreateUser(auth.getUserId());
		marketTask.setCreateUserName(auth.getUsername());
		marketTask.setRecentlyUpdateDate(new Date());
		marketTask.setRecentlyUpdateId(auth.getUserId());
		marketTask.setRecentlyUpdateName(auth.getUsername());
		//删除上次分解时的值
		this.deleteSubTask(marketTask);
		//临时性的把执行对象更新为前台传过来的值,便于创建子任务,但不作保存
		//如果主任务第一次是营销团队，则分解后的任务默认为客户经理,否则是界面所选择的类型
		if("3".equals(tempTask.getDistTaskType())){
			marketTask.setDistTaskType("2");//1 机构，2客户经理，3营销团队
		}
		this.saveSubTask(marketTask,targetIds,targetValueData,cycleIds,cycleNames);
		
    }
}
