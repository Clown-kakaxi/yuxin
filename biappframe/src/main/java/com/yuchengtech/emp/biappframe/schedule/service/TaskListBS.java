package com.yuchengtech.emp.biappframe.schedule.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.schedule.entity.BioneTaskInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.bione.dao.SearchResult;

/**
 * <pre>
 * Title: 系统任务
 * Description:
 * </pre>
 * 
 * @author yangyuhui yangyh4@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service("taskListBS")
@Transactional(readOnly = true)
public class TaskListBS extends BaseBS<BioneTaskInfo> {
	protected static Logger log = LoggerFactory.getLogger(TaskListBS.class);
	
	@Autowired
	private TaskBS taskBS;
	/**
	 * 查询任务信息
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @return
	 */
	public SearchResult<BioneTaskInfo> getTaskList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select task from BioneTaskInfo task where 1 = 1 ");
		jql.append(" and task.taskType = :taskType ");
		jql.append(" and task.logicSysNo=:logicSysNo ");
		
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by task." + orderBy + " " + orderType);
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> values = (Map<String, Object>) conditionMap.get("params");
		values.put("logicSysNo", BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		//过滤出系统任务
		values.put("taskType", GlobalConstants.TASK_TYPE_SYSTASK);
		SearchResult<BioneTaskInfo> taskList = this.baseDAO.findPageWithNameParam(firstResult, pageSize, jql.toString(), values);
		return taskList;
	}
	
	public boolean checkIdIsExist(String taskId){
		boolean flag = false;
		String jql = "select task from BioneTaskInfo task where task.taskId = ?0 and task.logicSysNo = ?1";
		List<BioneTaskInfo> taskList = this.baseDAO.findWithIndexParam(jql, taskId, BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		if(taskList != null && taskList.size() > 0){
			flag = true;
		}
		return flag;
	}
	
	public String findTriggerNameById(String triggerId){
		String jql = "select trigger.triggerName from BioneTriggerInfo trigger where trigger.triggerId = ?0 and trigger.logicSysNo = ?1";
		String triggerName = this.baseDAO.findUniqueWithIndexParam(jql, triggerId, BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		return triggerName;
	}
	
	/**
	 * 保存或修改任务
	 * @param task
	 * @return
	 */
	@Transactional(readOnly = false)
	public BioneTaskInfo mergeTask(BioneTaskInfo task){
		BioneTaskInfo returnTask = null;
		if(task != null){
			returnTask = this.baseDAO.merge(task);
			this.baseDAO.flush();
		}
		return returnTask;
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@Transactional(readOnly = false)
	public void batchDelete(String ids){
		if(ids != null && !"".equals(ids)){
			String[] idArray = ids.split(",");
			for(int i = 0 ; i < idArray.length ; i++){
				this.taskBS.delJob(idArray[i]);
				if(i >= 20 && i%20 == 0){
					this.baseDAO.flush();
				}
			}
		}
	}
	
	/**
	 * 任务维护功能
	 * @param task
	 * @return
	 */
	@Transactional(readOnly = false)
	public BioneTaskInfo updateTaskInfo(BioneTaskInfo task){
		return this.baseDAO.merge(task);
	}
}
