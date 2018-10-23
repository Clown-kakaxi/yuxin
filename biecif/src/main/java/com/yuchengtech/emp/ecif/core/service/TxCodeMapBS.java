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
import com.yuchengtech.emp.ecif.core.entity.TxCodeMap;

@Service
@Transactional(readOnly = true)
public class TxCodeMapBS extends BaseBS<TxCodeMap> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxCodeMap> getTxCodeMapList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxCodeMap from TxCodeMap TxCodeMap where 1=1");
		
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by TxCodeMap.id.srcSysCd ,TxCodeMap.id.stdCate" );
		}	
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxCodeMap> TxCodeMap = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxCodeMap;
	}
	
	/**
	 * 更新元数据列对象
	 * @param value  属性值，支持多个属性值，属性值之间用，分割
	 */
	@Transactional(readOnly = false)
	public void delTabColumns(String value) {
		if (StringUtils.isBlank(value)) {
			return;
		}
		String[] values = value.split(",");
		
		String sqldelete = ""  ;
		
		List<Object> valueList = Lists.newArrayList();
		if (values != null) {
			for (String val : values) {
								
				sqldelete = "delete from TX_col_def where TAB_ID=" + val;
				this.baseDAO.createNativeQueryWithIndexParam(sqldelete, null).executeUpdate();
				
			}
		}	
	}
		
}
