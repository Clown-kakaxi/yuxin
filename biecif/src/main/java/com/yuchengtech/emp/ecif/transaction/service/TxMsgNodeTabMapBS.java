package com.yuchengtech.emp.ecif.transaction.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabMap;

@Service
@Transactional(readOnly = false)
public class TxMsgNodeTabMapBS extends BaseBS<TxMsgNodeTabMap> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxMsgNodeTabMap> getTxMsgNodeTabMapList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap,Long nodeId) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxMsgNodeTabMap from TxMsgNodeTabMap TxMsgNodeTabMap where nodeId=" + nodeId);
		
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by TxMsgNodeTabMap." + orderBy + " " + orderType);
		}	
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxMsgNodeTabMap> TxMsgNodeTabMap = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxMsgNodeTabMap;
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<TxMsgNodeTabMap> getTxMsgNodeTabMapListByTab(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String  nodeId) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append(" select TxMsgNodeTabMap from TxMsgNodeTabMap TxMsgNodeTabMap where nodeId="+nodeId);
		jql.append(" order by TxMsgNodeTabMap.nodeId asc" );
		
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxMsgNodeTabMap> TxMsgNodeTabMap = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxMsgNodeTabMap;
	}	
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select txCode, txName from TxDef  t");

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
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getTabDefComBoBox() {
		StringBuffer jql = new StringBuffer("select tabId,tabDesc from TabDef  t");

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
	 * 获取信息
	 * @return
	 */
	public List<Object[]> getVOList(Long nodeId) {
		
		StringBuffer sql = new StringBuffer(" select  NODE_TAB_MAP_ID,NODE_ID,t1.TAB_ID,TAB_ROLE_TP,t1.STATE,TAB_NAME,TAB_DESC from TX_MSG_NODE_TAB_MAP t1, TX_Tab_Def  t2");
								sql.append("  where t1.TAB_ID = t2.TAB_ID ");
								sql.append("  and t1.NODE_ID=" + nodeId);
		
		List<Object[]> objList = this.baseDAO.findByNativeSQLWithNameParam(sql.toString(), null);
		
		return objList;
	}
		
	
}
