package com.yuchengtech.emp.ecif.alarm.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.alarm.entity.TxAlarmSms;

@Service
@Transactional(readOnly = true)
public class TxAlarmSmsBS extends BaseBS<TxAlarmSms> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxAlarmSms> getTxAlarmSmsList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap,String loginName) {
		
		Map<String, Object> values = (Map<String, Object>) conditionMap
				.get("params");
		
		Map<String, Object> fieldValues= (Map<String, Object>) conditionMap
				.get("fieldValues");
		
		String j = (String) conditionMap.get("jql");
		StringBuffer sbf = new StringBuffer(j);
		String[] condtions = j.split("and");
		String paramname = "";

		for(int i=0;i<condtions.length;i++){
			String[] params = condtions[i].split("=:");
			if(params[0].trim().equals("occurDate")){
				paramname = params[1].trim();
			}
		}
		
		DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.CHINA);
		if (fieldValues.get("occurDate") !=null&&!fieldValues.get("occurDate").equals("")) {
			try {
				Date date = DATE_FORMAT.parse(fieldValues.get("occurDate").toString());
				
				//将values里面的值赋值为date类型
				values.put(paramname, date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxAlarmSms from TxAlarmSms TxAlarmSms where 1=1 ");

		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		
		SearchResult<TxAlarmSms> TxAlarmSmsList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxAlarmSmsList;
	}
	
	/**
	 * 获取信息, 用于生成下拉框
	 * @return
	 */
	public List<Map<String, String>> getComBoBox() {
		StringBuffer jql = new StringBuffer("select groupId, groupName from TxAlarmSms  t");

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
