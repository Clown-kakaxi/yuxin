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
import com.yuchengtech.emp.ecif.core.entity.ColDef;

@Service
@Transactional(readOnly = true)
public class ColDefBS extends BaseBS<ColDef> {
	@SuppressWarnings("unchecked")
	public SearchResult<ColDef> getColDefList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap,BigInteger tabId) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select colDef from ColDef colDef where id.tabId=" + tabId);
		
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by colDef.id.colId");
		}	
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<ColDef> ColDef = this.baseDAO.findPageWithNameParam(firstResult, 2000,
				jql.toString(), values);
		return ColDef;
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<ColDef> getColDefListByTab(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String  tabId) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append(" select ColDef from ColDef ColDef where id.tabId="+tabId);
		jql.append(" order by ColDef.tabId asc" );
		
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<ColDef> ColDef = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return ColDef;
	}	
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select stdCate, stdCateDesc from TxStdCate  t order by t.stdCateDesc");

		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), null);
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		Map<String, String> harvMap;
		for(Object[] obj: objList) {
			harvMap = Maps.newHashMap();
			harvMap.put("id", obj[0] != null ? obj[0].toString() : "");
			harvMap.put("text", obj[1] != null ? obj[1].toString() : "");
			harvComboList.add(harvMap);
		}
		return harvComboList;
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
