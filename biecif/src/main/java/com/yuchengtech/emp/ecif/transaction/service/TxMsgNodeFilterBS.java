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
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeFilter;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeFilterVO;

@Service
@Transactional(readOnly = false)
public class TxMsgNodeFilterBS extends BaseBS<TxMsgNodeFilter> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxMsgNodeFilter> getTxMsgNodeFilterList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap,Integer tabId) {
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxMsgNodeFilter from TxMsgNodeFilter TxMsgNodeFilter where tabId=" + tabId);
		
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by TxMsgNodeFilter." + orderBy + " " + orderType);
		}	
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxMsgNodeFilter> TxMsgNodeFilter = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxMsgNodeFilter;
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
	
	@SuppressWarnings("unchecked")
	public Map<String, Object>  getTxMsgNodeFilterVOList(String nodeId) {
		
		Map paramMap = new HashMap();
		paramMap.put("nodeId", nodeId);

		StringBuffer sql = new StringBuffer("");
		
		sql.append(" SELECT  r.FILTER_ID,r.NODE_ID,r.TAB_ID,r.COL_ID,r.REL,r.ATTR_ID,r.STATE,r.CREATE_TM,r.CREATE_USER,r.UPDATE_TM,r.UPDATE_USER,t1.TAB_DESC,t3.COL_CH_NAME,t4.ATTR_NAME,r.FILTER_CONDITIONS  ");  
		sql.append(" FROM  TX_MSG_NODE_FILTER r ");
		sql.append(" inner join TX_TAB_DEF t1 on r.TAB_ID = t1.TAB_ID");
		sql.append(" inner join TX_COL_DEF t3 on r.TAB_ID = t3.TAB_ID  AND r.COL_ID = t3.COL_ID ");
		sql.append(" left join TX_MSG_NODE_ATTR t4 on r.ATTR_ID = t4.ATTR_ID ");
		sql.append(" where r.node_id= :nodeId ");
		
		List<Object[]> list = this.baseDAO.findByNativeSQLWithNameParam(sql.toString(), paramMap);
		
		List<TxMsgNodeFilterVO> results =new ArrayList<TxMsgNodeFilterVO>();
		
		for(int i=0;i<list.size();i++){
			TxMsgNodeFilterVO bat=new TxMsgNodeFilterVO();
			
			bat.setFilterId(list.get(i)[0]!=null?new Long(list.get(i)[0].toString()):null);
			bat.setNodeId(list.get(i)[1]!=null?new Long(list.get(i)[1].toString()):null);
			bat.setTabId(list.get(i)[2]!=null?new Integer(list.get(i)[2].toString()):null);
			bat.setColId(list.get(i)[3]!=null?new Integer(list.get(i)[3].toString()):null);
			bat.setRel(list.get(i)[4]!=null?list.get(i)[4].toString():"");
			bat.setAttrId(list.get(i)[5]!=null?new Long(list.get(i)[5].toString()):null);
			bat.setState(list.get(i)[6]!=null?list.get(i)[6].toString():"");
			bat.setTabDesc(list.get(i)[11]!=null?list.get(i)[11].toString():"");
			bat.setColChName(list.get(i)[12]!=null?list.get(i)[12].toString():"");
			bat.setAttrName(list.get(i)[13]!=null?list.get(i)[13].toString():"");
			bat.setFilterConditions(list.get(i)[14]!=null?list.get(i)[14].toString():"");
			
			results.add(bat);
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("Rows", results);
		map.put("Total", results.size());		
		
		return map;
	}
	
		
}
