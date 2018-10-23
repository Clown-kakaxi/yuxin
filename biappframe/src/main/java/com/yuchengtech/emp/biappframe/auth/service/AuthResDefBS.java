package com.yuchengtech.emp.biappframe.auth.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthResDef;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;

@Service
@Transactional(readOnly = true)
public class AuthResDefBS extends BaseBS<BioneAuthResDef> {
	@SuppressWarnings("unchecked")
	public SearchResult<BioneAuthResDef> getAuthResDefList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select authResDef from BioneAuthResDef authResDef where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by authResDef." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<BioneAuthResDef> authResDefList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return authResDefList;
	}
	
}
