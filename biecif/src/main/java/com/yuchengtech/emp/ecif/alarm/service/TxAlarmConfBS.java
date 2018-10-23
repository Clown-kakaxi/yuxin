package com.yuchengtech.emp.ecif.alarm.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmConf;

@Service
@Transactional(readOnly = true)
public class TxAlarmConfBS extends BaseBS<TxAlarmConf> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxAlarmConf> getTxAlarmConfList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxAlarmConf from TxAlarmConf TxAlarmConf where 1=1");

		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}

		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		SearchResult<TxAlarmConf> TxAlarmConfList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxAlarmConfList;
	}
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox(String alarmObjectType) {
		StringBuffer jql = new StringBuffer("");
		
		//为了处理从前台传过来的中文值（前台传过来的val不是ID）
		try {
			alarmObjectType= java.net.URLDecoder.decode(alarmObjectType,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if(alarmObjectType.equals("0")||alarmObjectType.equals("分组")){			//分组
			jql = new StringBuffer("select groupId, groupName from TxAlarmGroup  t");
		}else if(alarmObjectType.equals("1")||alarmObjectType.equals("人员")){			//人员
			jql = new StringBuffer("select userId, userName from TxAlarmUser  t");
		}else{
			return null;
		}

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
	 * 根据报警配置ID获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBoxById(Long id) {
		
		TxAlarmConf model = this.getEntityById(id);
		return getComBoBox(model.getAlarmObjectType());
	}
		
	
		
	public String getGroupName(String groupName) {
		
		String srcSysCdStr ="";
		StringBuffer jql = new StringBuffer("select  groupId, groupName from TxAlarmConf  t where groupName ='" +groupName+"'");
 
		List<Object[]> objList = this.baseDAO.findWithNameParm(jql.toString(), null);
		List<Map<String, String>> harvComboList = Lists.newArrayList();
		for(Object[] obj: objList) {
			srcSysCdStr = obj[0] != null ? (String)obj[0] : null;
		}
		return srcSysCdStr;
	}	
	
}
