package com.yuchengtech.emp.ecif.transaction.service;

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
import com.yuchengtech.emp.ecif.transaction.entity.TxErr;

@Service
@Transactional(readOnly = true)
public class TxErrBS extends BaseBS<TxErr> {
	@SuppressWarnings("unchecked")
	public SearchResult<TxErr> getTxErrList(int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		
		Map<String, Object> values = (Map<String, Object>) conditionMap
				.get("params");
		
		StringBuffer jql = new StringBuffer("");
		jql.append("select TxErr from TxErr TxErr where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		
		String j = (String) conditionMap.get("jql");
		StringBuffer sbf = new StringBuffer(j);
		DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.CHINA);
		int start = sbf.indexOf(">=:");
		String startWith = null;
		if (start > 0) {
			int start1 = sbf.indexOf("param", start);
			startWith = sbf.substring(start1, start1 + 6);
			
			
			try {
				Date date = DATE_FORMAT.parse(values.get(startWith).toString());
				values.put(startWith, date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String endWith = null;
		int end = sbf.indexOf("<=:");
		if (end > 0) {
			int end1 = sbf.indexOf("param", end);
			endWith = sbf.substring(end1, end1 + 6);
			Date date = null;
			
			try {
				date = DATE_FORMAT.parse(values.get(endWith).toString());
				long endLong = date.getTime();
				values.put(endWith, date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by " + orderBy + " " + orderType);
		}
		
		SearchResult<TxErr> TxLogList = this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
		return TxLogList;
	}
			
	
}
