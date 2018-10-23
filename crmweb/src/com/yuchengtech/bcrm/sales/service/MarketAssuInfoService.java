package com.yuchengtech.bcrm.sales.service;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTask;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @描述：营销任务管理功能模块Service
 * @author wzy
 * @date:2013-06-11
 */
@Service
@Transactional(value = "postgreTransactionManager")
public class MarketAssuInfoService extends CommonService {

	public MarketAssuInfoService() {
		JPABaseDAO<OcrmFMmTask, Long> baseDAO = new JPABaseDAO<OcrmFMmTask, Long>(
				OcrmFMmTask.class);
		super.setBaseDAO(baseDAO);
	}

	// 根据ID是否为空进行新增或者修改并更新最近更新人，最近更新日期和最近更新机构等信息项
	@SuppressWarnings("unchecked")
	public Object save(Object obj) {
		OcrmFMmTask ocrmFMmTask = (OcrmFMmTask) obj;
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (ocrmFMmTask.getTaskId() == null) {
			// 新增操作
			ocrmFMmTask.setCreateDate(new Date());
			ocrmFMmTask.setCreateOrgId(auth.getUnitId());
			ocrmFMmTask.setCreateOrgName(auth.getUnitName());
			ocrmFMmTask.setCreateUser(auth.getUserId());
			ocrmFMmTask.setCreateUserName(auth.getUsername());
			ocrmFMmTask.setRecentlyUpdateDate(new Date());
			ocrmFMmTask.setRecentlyUpdateId(auth.getUserId());
			ocrmFMmTask.setRecentlyUpdateName(auth.getUsername());
			ocrmFMmTask.setTaskStat("1");
			return baseDAO.save(ocrmFMmTask);
		} else {
			// 更新操作
			OcrmFMmTask oft = (OcrmFMmTask) baseDAO
					.get(ocrmFMmTask.getTaskId());
			oft.setTaskName(ocrmFMmTask.getTaskName());
			oft.setTaskType(ocrmFMmTask.getTaskType());
			oft.setTaskBeginDate(ocrmFMmTask.getTaskBeginDate());
			oft.setTaskEndDate(ocrmFMmTask.getTaskEndDate());
			oft.setDistTaskType(ocrmFMmTask.getDistTaskType());
			oft.setMemo(ocrmFMmTask.getMemo());
			oft.setRecentlyUpdateDate(new Date());
			oft.setRecentlyUpdateId(auth.getUserId());
			oft.setRecentlyUpdateName(auth.getUsername());
			return baseDAO.merge(oft);
		}
	}

	// 删除营销任务
	public void delTask(HttpServletRequest request) {
		String s = request.getParameter("cbid");
		JSONObject jsonObject = JSONObject.fromObject(s);
		JSONArray jarray = jsonObject.getJSONArray("id");
		String jql = null;
		HashMap<String, Object> values = null;
		for (int i = 0; i < jarray.size(); i++) {
			// 删除指标
			jql = "delete from OcrmFMmTaskTarget p where p.operrecid in "
					+ " (select t.id from OcrmFMmTaskOperator t where t.taskId = "
					+ jarray.get(i) + ")";
			values = new HashMap<String, Object>();
			super.batchUpdateByName(jql, values);
			// 删除执行对象
			jql = "delete from OcrmFMmTaskOperator t where t.taskId = "
					+ jarray.get(i);
			values = new HashMap<String, Object>();
			super.batchUpdateByName(jql, values);
			// 删除营销任务
			super.remove(Long.parseLong(jarray.get(i).toString()));
		}
	}
}
