package com.yuchengtech.emp.biappframe.auth.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjDef;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;

@Service
public class AuthObjDefBS extends BaseBS<BioneAuthObjDef> {
	@SuppressWarnings("unchecked")
	public SearchResult<BioneAuthObjDef> getAuthObjDefList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select authObjDef from BioneAuthObjDef authObjDef where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by authObjDef." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<BioneAuthObjDef> authObjDefList = this.baseDAO.findPageWithNameParam(firstResult, pageSize, jql.toString(), values);
		return authObjDefList;
	}
}
