package com.yuchengtech.emp.ecif.customer.simplegroup.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Actualcontroller;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Agentinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Executiveinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Holderinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Legalreprinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Operatorinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Orgassuinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Orginvestinfo;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Orgkeylinkman;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Orglinkman;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Relativecorp;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Seller;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Supplier;
import com.yuchengtech.emp.ecif.customer.entity.customerrelevanceorg.Updownstreaminfo;

@Service
@Transactional(readOnly = true)
public class OrgRelativeBS extends BaseBS<Object> {

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
	// 展示Actualcontroller的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Actualcontroller> getActualcontrollerList(
			int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Actualcontroller obj where 1=1");
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

	// 展示Agentinfo的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Agentinfo> getAgentinfoList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Agentinfo obj where 1=1");
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

	// 展示Executiveinfo的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Executiveinfo> getExecutiveinfoList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Executiveinfo obj where 1=1");
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

	// 展示Holderinfo的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Holderinfo> getHolderinfoList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Holderinfo obj where 1=1");
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

	// 展示Legalreprinfo的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Legalreprinfo> getLegalreprinfoList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Legalreprinfo obj where 1=1");
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

	// 展示Operatorinfo的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Operatorinfo> getOperatorinfoList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Operatorinfo obj where 1=1");
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

	// 展示Orgassuinfo的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Orgassuinfo> getOrgassuinfoList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Orgassuinfo obj where 1=1");
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

	// 展示Orginvestinfo的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Orginvestinfo> getOrginvestinfoList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Orginvestinfo obj where 1=1");
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

	// 展示Orgkeylinkman的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Orgkeylinkman> getOrgkeylinkmanList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Orgkeylinkman obj where 1=1");
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

	// 展示Orglinkman的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Orglinkman> getOrglinkmanList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Orglinkman obj where 1=1");
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

	// 展示Relativecorp的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Relativecorp> getRelativecorpList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Relativecorp obj where 1=1");
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

	// 展示Seller的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Seller> getSellerList(int firstResult, int pageSize,
			String orderBy, String orderType, Map<String, Object> conditionMap,
			long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Seller obj where 1=1");
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

	// 展示Supplier的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Supplier> getSupplierList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Supplier obj where 1=1");
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

	// 展示Updownstreaminfo的页面
	@SuppressWarnings("unchecked")
	public SearchResult<Updownstreaminfo> getUpdownstreaminfoList(
			int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Updownstreaminfo obj where 1=1");
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
