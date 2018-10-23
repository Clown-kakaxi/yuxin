package com.yuchengtech.emp.biappframe.authobj.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.authobj.entity.BioneAuthObjgrpInfo;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;

@Service("authObjgrpBS")
@Transactional(readOnly = true)
public class AuthObjgrpBS extends BaseBS<BioneAuthObjgrpInfo> {

	/**
	 * 分页查询对象组信息列表
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<BioneAuthObjgrpInfo> getObjgrpInfoList(int firstResult, int pageSize, String orderBy,
			String orderType, Map<String, Object> conditionMap) {
		String jql = "SELECT objgrp FROM BioneAuthObjgrpInfo objgrp WHERE objgrp.logicSysNo=:logicSysNo ";

		if (!conditionMap.get("jql").equals("")) {
			jql += "AND " + conditionMap.get("jql") + " ";
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql += "ORDER BY objgrp." + orderBy + " " + orderType;
		}

		Map<String, Object> values = (Map<String, Object>) conditionMap.get("params");
		values.put("logicSysNo", BiOneSecurityUtils.getCurrentUserInfo().getCurrentLogicSysNo());

		SearchResult<BioneAuthObjgrpInfo> objgrpInfoList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql, values);
		return objgrpInfoList;
	}

	public boolean checkIsObjgrpNoExist(String objgrpNo) {
		boolean flag = true;
		String jql = "select role FROM BioneAuthObjgrpInfo role where objgrpNo=?0 AND logicSysNo=?1";
		List<BioneAuthObjgrpInfo> list = this.baseDAO.findWithIndexParam(jql, objgrpNo, BiOneSecurityUtils
				.getCurrentUserInfo().getCurrentLogicSysNo());
		if (list != null && list.size() > 0) {
			flag = false;
		}
		return flag;
	}

	@Transactional(readOnly = false)
	public void removeEntityBatch(String id) {
		if (id.endsWith(",")) {
			id = id.substring(0, id.length() - 1);
		}
		String[] ids = id.split(",");
		for (String idd : ids) {
			removeEntityById(idd);
		}
	}
}
