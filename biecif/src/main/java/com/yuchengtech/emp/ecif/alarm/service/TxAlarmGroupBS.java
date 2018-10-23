package com.yuchengtech.emp.ecif.alarm.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmGroup;

@Service
@Transactional(readOnly = true)
public class TxAlarmGroupBS extends BaseBS<TxAlarmGroup> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxAlarmGroup> getTxAlarmGroupList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxAlarmGroup from TxAlarmGroup TxAlarmGroup where 1=1");

		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxAlarmGroup> TxAlarmGroupList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxAlarmGroupList;
	}
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select groupId, groupName from TxAlarmGroup  t");

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
		
	public String getGroupName(String groupName) {
		
		String srcSysCdStr ="";
		StringBuffer jql = new StringBuffer("select  groupId, groupName from TxAlarmGroup  t where groupName ='" +groupName+"'");
 
		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), null);
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		for(Object[] obj: objList) {
			srcSysCdStr = obj[0] != null ? (String)obj[0] : null;
		}
		return srcSysCdStr;
	}	
	
}
