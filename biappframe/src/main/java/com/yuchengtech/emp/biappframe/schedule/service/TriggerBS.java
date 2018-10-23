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
import com.yuchengtech.emp.biappframe.schedule.entity.BioneTriggerInfo;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.bione.dao.SearchResult;

/**
 * <pre>
 * Title:触发器管理类
 * Description: 
 * </pre>
 * 
 * @author huangye huangye@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service("triggerBS")
@Transactional(readOnly = true)
public class TriggerBS  extends BaseBS<BioneTriggerInfo>{

	protected static Logger log = LoggerFactory.getLogger(TriggerBS.class);
	
	@Autowired
	private TaskBS taskBS;
	
	/**
	 * 分布查询角发器信息
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<BioneTriggerInfo> getTriggerList(int firstResult,
			int pageSize, String orderBy, String orderType,Map<String,Object> conditionMap) {
		String jql = "select trigger from BioneTriggerInfo trigger WHERE trigger.logicSysNo=:logicSysNo ";

		if (!conditionMap.get("jql").equals("")) {
			jql+=" and " + conditionMap.get("jql")+" ";
		}

		if (!StringUtils.isEmpty(orderBy)) {
			jql += "order by trigger." + orderBy + " " + orderType;
		}

		Map<String, Object> values = (Map<String, Object>) conditionMap.get("params");
		values.put("logicSysNo", BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());
		
		SearchResult<BioneTriggerInfo> triggerList = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql, values);
		return triggerList;
	}
	
	public List<BioneTriggerInfo> findTriggerIdAndName() {
		StringBuffer jql = new StringBuffer("");
		jql.append("select trigger from BioneTriggerInfo trigger where trigger.logicSysNo = '" 
				+ BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo() + "'");
		return this.baseDAO.findWithNameParm(jql.toString(), null);
	}
	
	/**
	 * 判断该触发器下是否有正在运行作业
	 * @param triggerId
	 * @return
	 */
	public String checkHasRunningJobOrNot(String triggerId){
		StringBuilder returnStr = new StringBuilder("");
		if(triggerId == null || "".equals(triggerId)){
			return returnStr.toString();
		}
		String jql = "select task from BioneTaskInfo task where task.logicSysNo=?0 and task.taskSts=?1 and task.triggerId=?2";
		List<BioneTaskInfo> tasks = this.baseDAO.findWithIndexParam(jql, BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo(),
				GlobalConstants.TASK_STS_NORMAL,triggerId);
		if(tasks != null){
			for(int i = 0 ; i < tasks.size() ; i++){
				//若所关联作业中有任何作业处于运行状态，返回
				BioneTaskInfo taskTmp = tasks.get(i);
				String sts = this.taskBS.getTriggerState(taskTmp.getTaskId());
				if("running".equals(sts)){
					if(!"".equals(returnStr.toString())){
						returnStr.append(",");
					}
					returnStr.append(taskTmp.getTaskName());
				}
			}
		}
		return returnStr.toString();
	}
	
	/**
	 * 查看关联了作业的触发器信息
	 * @param ids
	 * @return
	 */
	public String checkHasJobTriggers(String ids){
		StringBuilder names = new StringBuilder("");
		if(ids != null && !"".equals(ids)){
			String[] strs = ids.split(",");
			for(int i = 0 ; i < strs.length ; i++){
				String idTmp = strs[i];
				List<BioneTaskInfo> infoTmp = this.getEntityListByProperty(BioneTaskInfo.class, "triggerId", idTmp);
				if(infoTmp != null && infoTmp.size() > 0){
					BioneTriggerInfo triggerTmp = this.getEntityById(BioneTriggerInfo.class, idTmp);
					if(!"".equals(names.toString())){
						names.append(",");
					}
					names.append(triggerTmp.getTriggerName());
				}
			}
		}
		return names.toString();
	}
	
	/**
	 * 批量删除
	 * @param ids数组
	 * @return
	 */
	@Transactional(readOnly = false)
	public void deleteBatch(String[] ids){
		if(ids != null){
			for(int i = 0 ; i < ids.length ; i++){
				if(i > 0 && i % 10 == 0){
					this.baseDAO.flush();
				}
				this.removeEntityById(ids[i]);
			}
		}
	}
	
	/**
	 * 保存触发器信息
	 * @param 触发器
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdateTrigger(BioneTriggerInfo triggerInfo){
		if(triggerInfo != null){
			this.saveOrUpdateEntity(triggerInfo);
			this.baseDAO.flush();
		}
	}
}
