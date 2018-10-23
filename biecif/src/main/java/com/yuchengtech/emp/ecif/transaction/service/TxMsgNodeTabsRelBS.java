package com.yuchengtech.emp.ecif.transaction.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabsRel;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabsRelVO;

@Service
@Transactional(readOnly = false)
public class TxMsgNodeTabsRelBS extends BaseBS<TxMsgNodeTabsRel> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxMsgNodeTabsRel> getTxMsgNodeTabsRelList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap,String nodeId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxMsgNodeTabsRel from TxMsgNodeTabsRel TxMsgNodeTabsRel where 1=1");
		if (nodeId != null||"".equals(nodeId)){
			jql.append(" and " + "TxMsgNodeTabsRel.nodeId='" + nodeId + "'");
		}
//		if (!StringUtils.isEmpty(orderBy)) {
//			jql.append(" order by TxClientAuth." + orderBy + " " + orderType);
//		}	
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxMsgNodeTabsRel> TxMsgNodeTabsRel = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxMsgNodeTabsRel;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object>  getTxMsgNodeTabsRelVOList(String nodeId) {
		
		Map paramMap = new HashMap();
		paramMap.put("nodeId", nodeId);

		StringBuffer sql = new StringBuffer("");
		
		sql.append(" SELECT  NODE_TABS_REL_ID,NODE_ID,TAB_ID_1,t1.TAB_DESC as TAB_DESC_1 ,COL_ID_1,t3.COL_CH_NAME as col_Name_1,TAB_ID_2,t2.TAB_DESC as TAB_DESC_2 ,COL_ID_2,t4.COL_CH_NAME as col_Name_2,t1.STATE ");  
		sql.append(" FROM  TX_MSG_NODE_TABS_REL r ");
		sql.append(" inner join TX_TAB_DEF t1 on r.TAB_ID_1 = t1. TAB_ID");
		sql.append(" inner join TX_TAB_DEF t2 on r.TAB_ID_2 = t2. TAB_ID ");
		sql.append(" inner join TX_COL_DEF t3 on r.TAB_ID_1 = t3. TAB_ID  AND r.COL_ID_1 = t3.COL_ID ");
		sql.append(" inner join TX_COL_DEF t4 on r.TAB_ID_1 = t4. TAB_ID  AND r.COL_ID_2 = t4.COL_ID ");
		sql.append(" where node_id= :nodeId ");
		
		List<Object[]> list = this.baseDAO.findByNativeSQLWithNameParam(sql.toString(), paramMap);
		
		List<TxMsgNodeTabsRelVO> results =new ArrayList<TxMsgNodeTabsRelVO>();
		
		for(int i=0;i<list.size();i++){
			TxMsgNodeTabsRelVO bat=new TxMsgNodeTabsRelVO();
			bat.setNodeTabsRelId(list.get(i)[0]!=null?new Long(list.get(i)[0].toString()):null);
			bat.setNodeId(list.get(i)[1]!=null?new Long(list.get(i)[1].toString()):null);
			bat.setTabId1(list.get(i)[2]!=null?new Integer(list.get(i)[2].toString()):null);
			bat.setTabDesc1(list.get(i)[3]!=null?list.get(i)[3].toString():"0");
			bat.setColId1(list.get(i)[4]!=null?new Integer(list.get(i)[4].toString()):null);
			bat.setColName1(list.get(i)[5]!=null?list.get(i)[5].toString():"0");
			bat.setTabId2(list.get(i)[6]!=null?new Integer(list.get(i)[6].toString()):null);
			bat.setTabDesc2(list.get(i)[7]!=null?list.get(i)[7].toString():"0");
			bat.setColId2(list.get(i)[8]!=null?new Integer(list.get(i)[8].toString()):null);
			bat.setColName2(list.get(i)[9]!=null?list.get(i)[9].toString():"0");
			bat.setState(list.get(i)[10]!=null?list.get(i)[10].toString():"0");
			
			results.add(bat);
			
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("Rows", results);
		map.put("Total", results.size());		
		
		return map;
	}
		
}
