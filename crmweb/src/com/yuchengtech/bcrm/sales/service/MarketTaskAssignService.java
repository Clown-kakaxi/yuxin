package com.yuchengtech.bcrm.sales.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTask;
import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTaskTarget;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.SystemConstance;

/**
 * @描述：营销管理->营销任务管理->营销任务下达：功能操作Service
 * @author wzy
 * @date:2013-06-20
 */
@Service
@Transactional(value = "postgreTransactionManager")
public class MarketTaskAssignService extends CommonService {
	public MarketTaskAssignService() {
		JPABaseDAO<OcrmFMmTask, Long> baseDAO = new JPABaseDAO<OcrmFMmTask, Long>(
				OcrmFMmTask.class);
		super.setBaseDAO(baseDAO);
	}

	// 保存新增的任务
	public void saveTask(OcrmFMmTask ocrmFMmTask, AuthUser auth,
			String targetValueData, String targetIds) {
		Date sysDate = new Date();
		OcrmFMmTask tempTask = new OcrmFMmTask();
		this.saveMainTask(ocrmFMmTask, auth, sysDate, tempTask);// 保存主任务信息
		this.saveSonTask(tempTask, auth, targetIds, sysDate, targetValueData);// 保存子任务信息
	}

	// 保存主任务信息
	private void saveMainTask(OcrmFMmTask ocrmFMmTask, AuthUser auth,
			Date sysDate, OcrmFMmTask tempTask) {
		ocrmFMmTask.setCreateDate(sysDate);
		ocrmFMmTask.setCreateOrgId(auth.getUnitId());
		ocrmFMmTask.setCreateOrgName(auth.getUnitName());
		ocrmFMmTask.setCreateUser(auth.getUserId());
		ocrmFMmTask.setCreateUserName(auth.getUsername());
		ocrmFMmTask.setRecentlyUpdateDate(sysDate);
		ocrmFMmTask.setRecentlyUpdateId(auth.getUserId());
		ocrmFMmTask.setRecentlyUpdateName(auth.getUsername());
		ocrmFMmTask.setTaskStat("1");
		BeanUtils.copyProperties(ocrmFMmTask, tempTask);
		ocrmFMmTask.setOperObjId(null);
		ocrmFMmTask.setOperObjName(null);
		super.em.persist(ocrmFMmTask);
		tempTask.setTaskId(ocrmFMmTask.getTaskId());
	}

	// 保存任务对应的子任务信息
	// 一个执行对象对应一个子任务，对应一套指标数据
	private void saveSonTask(OcrmFMmTask ocrmFMmTask, AuthUser auth,
			String targetIds, Date sysDate, String targetValueData) {
		String[] operObjIdArr = null;
		String[] operObjNameArr = null;
		OcrmFMmTask tempTask = null;
		String[] targetValueDataArr = targetValueData.split(";");
		String operObjIds = ocrmFMmTask.getOperObjId();
		if (operObjIds != null && !"".equals(operObjIds)) {
			operObjIdArr = operObjIds.split(",");
			operObjNameArr = ocrmFMmTask.getOperObjName().split(",");
			if (operObjIdArr != null && operObjIdArr.length > 0) {
				for (int i = 0; i < operObjIdArr.length; i++) {
					if (operObjIdArr[i] != null
							&& !"".equals(operObjIdArr[i].trim())) {
						tempTask = new OcrmFMmTask();
						BeanUtils.copyProperties(ocrmFMmTask, tempTask);
						tempTask.setTaskId(null);
						tempTask.setTaskName(ocrmFMmTask.getTaskName() + "-"
								+ operObjNameArr[i]);// 营销任务名称
						tempTask.setOperObjId(operObjIdArr[i]);// 执行对象ID
						tempTask.setOperObjName(operObjNameArr[i]);// 执行对象名称
						tempTask.setTaskParentId(new BigDecimal(ocrmFMmTask
								.getTaskId()));// 上级任务ID
						tempTask.setTaskStat("1");// 任务状态（暂存）
						super.em.persist(tempTask);
						this.saveSonTaskTarget(tempTask, auth, targetIds,
								targetValueDataArr[i]);
					}
				}
			}
		}
	}

	// 保存子任务对应的指标信息
	private void saveSonTaskTarget(OcrmFMmTask ocrmFMmTask, AuthUser auth,
			String targetIds, String targetValueData) {
		String[] targetIdArr = targetIds.split(",");
		String[] targetValueArr = targetValueData.split(",");
		OcrmFMmTaskTarget tempTarget = null;
		if (targetIdArr != null && targetIdArr.length > 0) {
			for (int i = 0; i < targetIdArr.length; i++) {
				tempTarget = new OcrmFMmTaskTarget();
				tempTarget.setTaskId(ocrmFMmTask.getTaskId());// 任务ID
				tempTarget.setTargetCode(targetIdArr[i]);// 指标ID
				tempTarget.setTargetValue(new BigDecimal(targetValueArr[i]));// 指标目标值
				tempTarget.setCreateUserId(auth.getUserId());
				tempTarget.setCreateUserName(auth.getUsername());
				tempTarget.setRecentlyUpdateId(auth.getUserId());
				tempTarget.setRecentlyUpdateName(auth.getUsername());
				tempTarget.setRecentlyUpdateDate(ocrmFMmTask.getCreateDate());
				super.em.persist(tempTarget);
			}
		}
	}

	// 保存修改的任务
	public void updateTask(OcrmFMmTask ocrmFMmTask, AuthUser auth,
			String targetValueData, String targetIds) {
		Date sysDate = new Date();
		// 更新主任务信息
		this.updateMainTask(ocrmFMmTask, auth, sysDate);
		// 删除主任务原有的子任务信息及子任务对应的指标信息
		this.deleteSonTask(ocrmFMmTask);
		// 保存新的子任务及对应的指标信息
		this.saveSonTask(ocrmFMmTask, auth, targetIds, sysDate, targetValueData);
	}

	// 更新主任务
	private String updateMainTask(OcrmFMmTask ocrmFMmTask, AuthUser auth,
			Date sysDate) {
		String result = null;
		OcrmFMmTask tempTask = em.find(OcrmFMmTask.class,
				ocrmFMmTask.getTaskId());
		if (tempTask != null) {
			result = tempTask.getTaskName();
			tempTask.setTaskName(ocrmFMmTask.getTaskName());
			tempTask.setTaskBeginDate(ocrmFMmTask.getTaskBeginDate());
			tempTask.setTaskEndDate(ocrmFMmTask.getTaskEndDate());
			tempTask.setDistTaskType(ocrmFMmTask.getDistTaskType());
			tempTask.setTaskType(ocrmFMmTask.getTaskType());
			tempTask.setMemo(ocrmFMmTask.getMemo());
			tempTask.setRecentlyUpdateDate(sysDate);
			tempTask.setRecentlyUpdateId(auth.getUserId());
			tempTask.setRecentlyUpdateName(auth.getUsername());
			em.merge(tempTask);
		}
		return result;
	}

	// 删除某个任务下的所有子任务（及其子任务对应的指标）
	@SuppressWarnings("rawtypes")
	private void deleteSonTask(OcrmFMmTask ocrmFMmTask) {
		List list = null;
		String jql = null;
		OcrmFMmTask tempTask = null;
		jql = "select p from OcrmFMmTask p where p.taskParentId = "
				+ ocrmFMmTask.getTaskId();
		list = super.em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				tempTask = (OcrmFMmTask) list.get(j);
				if (tempTask != null) {
					this.deleteTaskByObj(tempTask);
				}
			}
		}
	}

	// 删除营销任务(连同子任务一起删除)，支持批量删除
	// 只能删除“暂存”状态的任务，暂存状态的任务最多只有一级子任务
	@SuppressWarnings("rawtypes")
	public void deleteTask(HttpServletRequest request) {
		String s = request.getParameter("cbid");
		JSONObject jsonObject = JSONObject.fromObject(s);
		JSONArray jarray = jsonObject.getJSONArray("id");
		String jql = null;
		List list = null;
		OcrmFMmTask tempTask = null;
		for (int i = 0; i < jarray.size(); i++) {
			// 删除子任务
			jql = "select p from OcrmFMmTask p where p.taskParentId = "
					+ jarray.get(i);
			list = super.em.createQuery(jql).getResultList();
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					tempTask = (OcrmFMmTask) list.get(j);
					if (tempTask != null) {
						this.deleteTaskByObj(tempTask);
					}
				}
			}
			// 删除任务
			tempTask = super.em.find(OcrmFMmTask.class,
					Long.parseLong(jarray.get(i).toString()));
			if (tempTask != null) {
				this.deleteTaskByObj(tempTask);
			}
		}
	}

	// 根据传入的任务对象，删除对应的任务及和该任务关联的指标
	private void deleteTaskByObj(OcrmFMmTask task) {
		String jql = null;
		HashMap<String, Object> values = null;
		// 删除指标
		jql = "delete from OcrmFMmTaskTarget p where p.taskId = "
				+ task.getTaskId();
		values = new HashMap<String, Object>();
		super.batchUpdateByName(jql, values);
		// 删除营销任务
		super.remove(task.getTaskId());
	}

	// 查询某个任务下有哪些指标
	@SuppressWarnings("rawtypes")
	public List queryTarget(String taskId, String queryType) {
		List targetList = null;
		String sql = "select distinct t.target_code as index_id, t.target_name\n"
				+ "  from ocrm_f_mm_task_target p,\n"
				+ "       ocrm_sys_target t\n"
				+ " where t.target_code = p.target_code\n"
				+ "   and p.task_id = ";
		if ("ownTask".equals(queryType)) {
			// 查询任务本身的指标
			sql += taskId;
		} else {
			// 查询任务的子任务的指标
			sql += "(select t.task_id\n" + "  from ocrm_f_mm_task t\n"
					+ " where t.task_parent_id = " + taskId + "\n";
			if (SystemConstance.DB_TYPE.equalsIgnoreCase("ORACLE")) {
				sql += "   and rownum = 1)"; // for oarcle
			} else if (SystemConstance.DB_TYPE.equalsIgnoreCase("DB2")) {
				sql += "   fetch first 1 rows only)"; // for db2
			}
		}
		sql += " order by t.target_code asc";
		targetList = em.createNativeQuery(sql).getResultList();
		return targetList;
	}

	// 构造查询指标值的SQL语句
	@SuppressWarnings("rawtypes")
	public StringBuilder queryHadTargetData(String taskId, String queryType) {
		StringBuilder sb = null;
		List targetList = null;
		Object[] objs = null;
		targetList = this.queryTarget(taskId, queryType);
		if (targetList != null && targetList.size() > 0) {
			sb = new StringBuilder("select a.oper_obj_name,b.task_id,\n");
			for (int i = 0; i < targetList.size(); i++) {
				objs = (Object[]) targetList.get(i);
				if (objs != null && objs.length == 2) {
					sb.append("max(decode(c.target_code, '" + objs[0]
							+ "', b.target_value, 0)) as target_value_" + i);
					if (i != targetList.size() - 1) {
						sb.append(",\n");
					} else {
						sb.append("\n");
					}
				}
			}
			sb.append("  from ocrm_f_mm_task a,\n"
					+ "       ocrm_f_mm_task_target b,\n"
					+ "       ocrm_sys_target c\n"
					+ " where a.task_id = b.task_id\n"
					+ "   and b.target_code = c.target_code\n");
			if ("ownTask".equals(queryType)) {
				sb.append("   and b.task_id = " + taskId);
			} else {
				sb.append("   and b.task_id in (select t.task_id\n"
						+ "                       from ocrm_f_mm_task t\n"
						+ "                      where t.task_parent_id = "
						+ taskId + ")\n");
			}
			sb.append(" group by a.oper_obj_name,b.task_id");
		}
		return sb;
	}

	// 查询任务的指标信息（将指标分组后的列表）
	@SuppressWarnings("rawtypes")
	public StringBuilder queryHadTargetDataDetail(String taskId) {
		StringBuilder sb = null;
		List targetList = null;
		Object[] objs = null;
		boolean isHadSonTask = false;
		isHadSonTask = this.isHadSonTask(taskId);
		if (isHadSonTask) {
			// 查询子任务
			targetList = this.queryTarget(taskId, null);
		} else {
			// 查询任务本身
			targetList = this.queryTarget(taskId, "ownTask");
		}
		if (targetList != null && targetList.size() > 0) {
			sb = new StringBuilder(
					"select b.task_id,a.task_stat,a.oper_obj_name,\n");
			for (int i = 0; i < targetList.size(); i++) {
				objs = (Object[]) targetList.get(i);
				if (objs != null && objs.length == 2) {
					sb.append("max(decode(c.target_code, '" + objs[0]
							+ "', b.target_value, 0)) as target_value_" + i);
					sb.append(",\n max(decode(c.target_code, '" + objs[0]
							+ "', b.achieve_value, 0)) as achieve_value_" + i);
					sb.append(",\n max(decode(c.target_code, '" + objs[0]
							+ "', b.achieve_percent, 0)) as achper_value_" + i);
					if (i != targetList.size() - 1) {
						sb.append(",\n");
					} else {
						sb.append("\n");
					}
				}
			}
		} else {
			sb = new StringBuilder(
					"select a.oper_obj_name,b.task_id,a.task_stat ");
		}
		sb.append("  from ocrm_f_mm_task a,\n"
				+ "       ocrm_f_mm_task_target b,\n"
				+ "       ocrm_sys_target c\n"
				+ " where a.task_id = b.task_id\n"
				+ "   and b.target_code = c.target_code\n");
		if (isHadSonTask) {
			// 查询子任务
			sb.append("   and b.task_id in (select t.task_id\n"
					+ "                       from ocrm_f_mm_task t\n"
					+ "                      where t.task_parent_id = "
					+ taskId + ")\n");
		} else {
			// 查询任务本身
			sb.append(" and b.task_id = " + taskId);
		}
		sb.append(" group by a.oper_obj_name,b.task_id,a.task_stat");
		return sb;
	}

	// 查询某个任务是否有子任务，有：返回true；没有：返回false
	@SuppressWarnings("rawtypes")
	private boolean isHadSonTask(String taskId) {
		boolean result = false;
		List list = null;
		String jql = null;
		jql = "select t from OcrmFMmTask t where t.taskParentId = " + taskId;
		list = super.em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			result = true;
		}
		return result;
	}

	// 营销任务下达
	@SuppressWarnings("rawtypes")
	public void taskAssignTransmit(AuthUser auth, String cbid) {
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		if (cbid == null || "".equals(cbid)) {
			return;
		}
		List list = null;
		String jql = null;
		String ids = "";
		Date sysDate = new Date();
		OcrmFMmTask tempTask = null;
		jsonObject = JSONObject.fromObject(cbid);
		jsonArray = jsonObject.getJSONArray("id");
		if (jsonArray != null && jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				if (jsonArray.get(i) != null
						&& !"".equals(jsonArray.get(i).toString().trim())) {
					ids += jsonArray.get(i).toString();
				}
				if (i != jsonArray.size() - 1) {
					ids += ",";
				}
			}
		}
		// 处理选中任务的下达逻辑
		jql = "select p from OcrmFMmTask p where p.taskId in (" + ids + ")";
		list = super.em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				tempTask = (OcrmFMmTask) list.get(j);
				if (tempTask != null) {
					tempTask.setTaskStat("3");// 已下达
					tempTask.setDistUser(auth.getUserId());// 下达人ID
					tempTask.setDistUserName(auth.getUsername());// 下达人名称
					tempTask.setDistOrg(auth.getUnitId());// 下达机构ID
					tempTask.setDistOrgName(auth.getUnitName());// 下达机构名称
					tempTask.setTaskDistDate(sysDate);// 下达时间
					super.em.merge(tempTask);
				}
			}
		}
		// 处理选中任务的子任务的下达逻辑
		jql = "select p from OcrmFMmTask p where p.taskParentId in (" + ids
				+ ")";
		list = null;
		list = super.em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				tempTask = (OcrmFMmTask) list.get(j);
				if (tempTask != null) {
					tempTask.setTaskStat("2");// 执行中
					tempTask.setDistUser(auth.getUserId());// 下达人ID
					tempTask.setDistUserName(auth.getUsername());// 下达人名称
					tempTask.setDistOrg(auth.getUnitId());// 下达机构ID
					tempTask.setDistOrgName(auth.getUnitName());// 下达机构名称
					tempTask.setTaskDistDate(sysDate);// 下达时间
					super.em.merge(tempTask);
				}
			}
		}
	}

	// 营销任务指标值修改（指标调整）
	@SuppressWarnings("rawtypes")
	public void adjustTaskTargetData(AuthUser auth, String targetDataValue,
			String taskId) {
		String jql = null;
		List taskList = null;
		List targetList = null;
		Date sysDate = new Date();
		OcrmFMmTask tempTask = null;
		String[] targetValueArr = null;
		String[] targetValueDataArr = null;
		OcrmFMmTaskTarget tempTarget = null;
		if (targetDataValue == null || "".equals(targetDataValue)) {
			return;
		}
		targetValueDataArr = targetDataValue.substring(1,
				targetDataValue.length() - 1).split(";");
		if (targetValueDataArr != null && targetValueDataArr.length > 0) {
			jql = "select p from OcrmFMmTask p where p.taskParentId = "
					+ taskId + " order by p.taskId asc";
			taskList = super.em.createQuery(jql).getResultList();
			if (taskList != null && taskList.size() > 0) {
				for (int i = 0; i < taskList.size(); i++) {
					tempTask = (OcrmFMmTask) taskList.get(i);
					jql = "select t from OcrmFMmTaskTarget t where t.taskId = "
							+ tempTask.getTaskId()
							+ " order by t.targetCode asc";
					targetList = em.createQuery(jql).getResultList();
					targetValueArr = targetValueDataArr[i].split(",");
					for (int j = 0; j < targetList.size(); j++) {
						tempTarget = (OcrmFMmTaskTarget) targetList.get(j);
						if (tempTarget != null) {
							tempTarget.setTargetValue(new BigDecimal(
									targetValueArr[j]));
							tempTarget.setRecentlyUpdateId(auth.getUserId());
							tempTarget
									.setRecentlyUpdateName(auth.getUsername());
							tempTarget.setRecentlyUpdateDate(sysDate);
							em.merge(tempTarget);
						}
					}
				}
			}
		}
	}

	// 判断某个任务的子任务中是否有“未关闭”状态的，有：返回未关闭任务的名称，没有：返回空字符串
	@SuppressWarnings("rawtypes")
	public String isExistSonTask(String taskId) {
		List list = null;
		String jql = null;
		String noComplete = "";
		OcrmFMmTask tempTask = null;
		jql = "select p from OcrmFMmTask p";
		jql += " where p.taskParentId = " + taskId;
		jql += " and p.taskStat != 4";
		jql += " order by p.taskId asc";
		list = super.em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				tempTask = (OcrmFMmTask) list.get(i);
				if (tempTask != null) {
					noComplete += tempTask.getTaskName();
					if (i != list.size() - 1) {
						noComplete += "<br>";
					}
				}
			}
		}
		return noComplete;
	}

	// 关闭营销任务
	public void closeTask(String taskId, AuthUser auth) {
		OcrmFMmTask tempTask = super.em.find(OcrmFMmTask.class,
				Long.valueOf(taskId));
		tempTask.setTaskStat("4");// 将任务状态置成“已关闭(4)”
		tempTask.setRecentlyUpdateId(auth.getUserId());
		tempTask.setRecentlyUpdateName(auth.getUsername());
		tempTask.setRecentlyUpdateDate(new Date());
		super.em.merge(tempTask);
	}

	// 查询某营销任务对应的指标和执行对象
	@SuppressWarnings("rawtypes")
	public JSON queryTaskOperAndTarget(String taskId, String queryType) {
		JSON jo = null;
		List list = null;
		String jql = null;
		OcrmFMmTask tempTask = null;
		Object[] objs = null;
		String operIds = "";// 执行对象ID（用逗号分隔）
		String operNames = "";// 执行对象名称（用逗号分隔）
		String targetIds = "";// 任务指标ID（用逗号分隔）
		String targetNames = "";// 任务指标名称（用逗号分隔）
		String operObjType = "";// 子任务执行对象类型
		String resultJsonStr = null;
		jo = JSONSerializer
				.toJSON("{'operIds':'','operNames':'','targetIds':'','targetNames':'','operObjType':''}");
		// 查询执行对象（查询直接子任务）
		jql = "select p from OcrmFMmTask p";
		jql += " where p.taskParentId = " + taskId;
		jql += " order by p.taskId asc";
		list = super.em.createQuery(jql).getResultList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				tempTask = (OcrmFMmTask) list.get(i);
				if (tempTask != null) {
					if (i == 0) {
						operObjType = tempTask.getDistTaskType();
					}
					operIds += tempTask.getOperObjId();
					operNames += tempTask.getOperObjName();
					if (i != list.size() - 1) {
						operIds += ",";
						operNames += ",";
					}
				}
			}
		}
		// 查询任务指标
		list = null;
		list = this.queryTarget(taskId, queryType);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				objs = (Object[]) list.get(i);
				if (objs != null && objs.length == 2) {
					targetIds += objs[0];
					targetNames += objs[1];
					if (i != list.size() - 1) {
						targetIds += ",";
						targetNames += ",";
					}
				}
			}
		}
		resultJsonStr = "{'operIds':'" + operIds + "','operNames':'"
				+ operNames + "','targetIds':'" + targetIds
				+ "','targetNames':'" + targetNames.replaceAll("\n", "")
				+ "','operObjType':'" + operObjType + "'}";
		jo = JSONSerializer.toJSON(resultJsonStr);
		return jo;
	}
}