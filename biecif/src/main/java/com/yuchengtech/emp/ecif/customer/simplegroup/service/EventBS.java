package com.yuchengtech.emp.ecif.customer.simplegroup.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.customer.entity.event.AttentEvent;
import com.yuchengtech.emp.ecif.customer.entity.event.EventInfo;
import com.yuchengtech.emp.ecif.customer.entity.event.LargeEvent;
import com.yuchengtech.emp.ecif.customer.entity.event.TxEvent;


@Service
@Transactional(readOnly = true)
public class EventBS extends BaseBS<Object> {
	
	/**
	 * 获取列表数据, 支持查询
	 * 
	 * @param firstResult
	 *            分页的开始索引
	 * @param pageSize
	 *            页面大小
	 * @param orderBy
	 *            排序字段
	 * @param orderType
	 *            排序方式
	 * @param conditionMap
	 *            搜索条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<EventInfo> getAttentEventList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select attentEvent from EventInfo attentEvent,Customer customer where 1=1 and attentEvent.eventType='20' and customer.custId = attentEvent.custId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(String.valueOf(custId))) {
			jql.append(" and customer.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by attentEvent." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<EventInfo> getLargeEventList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select largeEvent from EventInfo largeEvent,Customer customer where 1=1 and largeEvent.eventType='10' and customer.custId = largeEvent.custId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(String.valueOf(custId))) {
			jql.append(" and customer.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by largeEvent." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<EventInfo> getTxEventList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select txEvent from EventInfo txEvent,Customer customer where 1=1 and txEvent.eventType='30' and customer.custId = txEvent.custId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(String.valueOf(custId))) {
			jql.append(" and customer.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by txEvent." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
}
