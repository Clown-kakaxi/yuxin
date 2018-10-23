package com.yuchengtech.emp.ecif.syncmanage.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.syncmanage.entity.TxSyncLog;

@Service
@Transactional(readOnly = true)
public class TxSyncLogBS extends BaseBS<TxSyncLog> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxSyncLog> getTxSyncLogList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		
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
			if(params[0].trim().equals("syncDealTime")){
				paramname = params[1].trim();
			}
		}
		
		DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.CHINA);
		if (fieldValues.get("syncDealTime") !=null&&!fieldValues.get("syncDealTime").equals("")) {
			try {
				Date date = DATE_FORMAT.parse(fieldValues.get("syncDealTime").toString());
				
				//将values里面的值赋值为date类型
				values.put(paramname, fieldValues.get("syncDealTime").toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		StringBuffer jql = new StringBuffer("");
		jql.append("select TxSyncLog from TxSyncLog TxSyncLog where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}

		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by " + orderBy + " " + orderType);
		}
		
		//替换日期为日期函数
		String newjql = jql.toString().replaceAll("syncDealTime", "to_char(syncDealTime,'yyyy-mm-dd')");
		
		SearchResult<TxSyncLog> TxSyncLogList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				newjql, values);
		
		return TxSyncLogList;
	}
			
	
}
