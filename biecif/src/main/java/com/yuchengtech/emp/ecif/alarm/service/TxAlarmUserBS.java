package com.yuchengtech.emp.ecif.alarm.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmUser;

@Service
@Transactional(readOnly = true)
public class TxAlarmUserBS extends BaseBS<TxAlarmUser> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxAlarmUser> getTxAlarmUserList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxAlarmUser from TxAlarmUser TxAlarmUser where 1=1");

		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxAlarmUser> TxAlarmUserList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxAlarmUserList;
	}
	
		
	public String getGroupName(String userName) {
		
		String srcSysCdStr ="";
		StringBuffer jql = new StringBuffer("select  userId, userName from TxAlarmUser  t where userName ='" +userName+"'");
 
		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), null);
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		for(Object[] obj: objList) {
			srcSysCdStr = obj[0] != null ? (String)obj[0] : null;
		}
		return srcSysCdStr;
	}	

	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select userNo, userName from BioneUserInfo  t");

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
