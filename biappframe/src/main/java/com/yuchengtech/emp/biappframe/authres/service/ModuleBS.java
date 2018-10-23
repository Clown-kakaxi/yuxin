package com.yuchengtech.emp.biappframe.authres.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.authres.entity.BioneModuleInfo;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;


@Service
@Transactional(readOnly = true)
public class ModuleBS extends BaseBS<BioneModuleInfo> {
	
	/**
	 * 分页显示所有模块信息
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @return
	 */
	public SearchResult<BioneModuleInfo> getModuleList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select module from BioneModuleInfo module where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by module." + orderBy + " " + orderType);
		}
		@SuppressWarnings("unchecked")
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<BioneModuleInfo> moduleList = this.baseDAO.findPageWithNameParam(firstResult, pageSize, jql.toString(), values);
		return moduleList;
	}
	
	/**
	 * 查找当前模块下的功能是否正在使用
	 * @param moduleId
	 * 			模块ID
	 * @return
	 */
	public String findUsedModuleName(String moduleId) {
		String jql = new String("select module.moduleName from BioneModuleInfo module where module.moduleId in ( select func.moduleId from BioneFuncInfo func"
				+ " where func.moduleId = :moduleId and func.funcId in ( select menu.funcId from BioneMenuInfo menu ) )");
		Map<String, String> values = Maps.newHashMap();
		values.put("moduleId", moduleId);
		List<Object> objList = this.baseDAO.findWithNameParm(jql, values);
		if(objList != null && objList.size() != 0) {
			return objList.get(0).toString();
		}
		return null;
	}
	
	@Transactional(readOnly = false)	
	public void removeModuleById(String moduleId) {
		String jql = new String("delete from BioneFuncInfo func where func.moduleId = :moduleId");
		Map<String, String> values = Maps.newHashMap();
		values.put("moduleId", moduleId);
		this.baseDAO.batchExecuteWithNameParam(jql, values);
//		this.baseDAO.removeById(moduleId);
		this.removeEntityById(moduleId);
	}
}
