package com.yuchengtech.emp.ecif.transaction.service;

import java.util.ArrayList;
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
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNode;

@Service
@Transactional(readOnly = false)
public class TxMsgNodeBS extends BaseBS<TxMsgNode> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxMsgNode> getTxMsgNodeList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap,Integer tabId) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxMsgNode from TxMsgNode TxMsgNode where tabId=" + tabId);
		
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by TxMsgNode." + orderBy + " " + orderType);
		}	
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxMsgNode> TxMsgNode = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxMsgNode;
	}
	
	@SuppressWarnings("unchecked")
	public List<TxMsgNode> getTxMsgNodeList(Long msgId) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxMsgNode from TxMsgNode TxMsgNode where msgId=" + msgId);
		
		List<TxMsgNode> list = this.baseDAO.findWithNameParm(jql.toString(), null);
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TxMsgNode> getTxMsgNodeChildList(Long msgId,Long upNodeId ) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxMsgNode from TxMsgNode TxMsgNode where msgId=" + msgId +" and upNodeId="+ upNodeId);
		
		List<TxMsgNode> list = this.baseDAO.findWithNameParm(jql.toString(), null);
		return list;
	}
		
	@SuppressWarnings("unchecked")
	public void deleteBatch(Long nodeId) {

		//删除节点
		String jql1 = "delete from TxMsgNode  		where nodeId = ?0";
		String jql2 = "delete from TxMsgNodeAttr  	where nodeId = ?0";
		String jql3 = "delete from TxMsgNodeFilter  where nodeId = ?0";
		String jql4 = "delete from TxMsgNodeTabMap  where nodeId = ?0";
		String jql5 = "delete from TxMsgNodeTabsRel where nodeId = ?0";		

		this.baseDAO.batchExecuteWithIndexParam(jql1, nodeId);
		this.baseDAO.batchExecuteWithIndexParam(jql2, nodeId);
		this.baseDAO.batchExecuteWithIndexParam(jql3, nodeId);
		this.baseDAO.batchExecuteWithIndexParam(jql4, nodeId);
		this.baseDAO.batchExecuteWithIndexParam(jql5, nodeId);

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
		
}
