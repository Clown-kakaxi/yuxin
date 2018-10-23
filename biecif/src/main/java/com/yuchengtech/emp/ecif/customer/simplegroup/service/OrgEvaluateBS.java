package com.yuchengtech.emp.ecif.customer.simplegroup.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.customer.entity.customerevaluate.Creditinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerevaluate.Orgauth;
import com.yuchengtech.emp.ecif.customer.entity.customerevaluate.Orggrade;
import com.yuchengtech.emp.ecif.customer.entity.customerevaluate.Orgstarlevel;
import com.yuchengtech.emp.ecif.customer.entity.customerevaluate.Scoreinfo;

@Service
@Transactional(readOnly = true)
public class OrgEvaluateBS extends BaseBS<Object> {

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
	// 展示Orgauth的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Orgauth> getOrgauthList(int firstResult, int pageSize,
			String orderBy, String orderType, Map<String, Object> conditionMap,
			long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Orgauth obj where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and obj.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}

	// 展示Orggrade的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Orggrade> getOrggradeList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Orggrade obj where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and obj.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}

	// 展示Orgstarlevel的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Orgstarlevel> getOrgstarlevelList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Orgstarlevel obj where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and obj.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}

	// 展示Scoreinfo的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Scoreinfo> getScoreinfoList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Scoreinfo obj where 1=1");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and obj.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}

	// 展示Creditinfo的页面
		@SuppressWarnings("unchecked")
		public SearchResult<Creditinfo> getCreditinfoList(int firstResult,
				int pageSize, String orderBy, String orderType,
				Map<String, Object> conditionMap, long custId) {
			StringBuffer jql = new StringBuffer("");
			jql.append("select obj from Creditinfo obj where 1=1");
			if (!conditionMap.get("jql").equals("")) {
				jql.append(" and " + conditionMap.get("jql"));
			}
			if (!"".equals(custId)) {
				jql.append(" and obj.custId = " + custId + "");
			}
			if (!StringUtils.isEmpty(orderBy)) {
				jql.append(" order by obj." + orderBy + " " + orderType);
			}
			Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
			return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
					jql.toString(), values);
		}
}
