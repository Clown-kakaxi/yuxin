package com.yuchengtech.emp.ecif.core.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.core.entity.TxColMap;

@Service
@Transactional(readOnly = true)
public class TxColMapBS extends BaseBS<TxColMap> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxColMap> getTxColMapList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxColMap from TxColMap TxColMap where 1=1");
		
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by TxColMap.id.srcSysCd ,TxColMap.id.srcTab" );
		}	
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxColMap> TxColMap = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxColMap;
	}
	

		
}
