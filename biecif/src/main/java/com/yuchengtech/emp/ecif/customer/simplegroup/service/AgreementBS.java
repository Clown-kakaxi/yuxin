package com.yuchengtech.emp.ecif.customer.simplegroup.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Bdsacctinfo;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Bdshracctinfo;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Bondcontract;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Bugoldcont;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Cesubcredit;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Creditcontract;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Depositaccount;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Ebankcontract;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Ebanksignaccount;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Guarantycontract;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Loanaccount;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Loancontract;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Oweinterest;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Tbassetacc;
import com.yuchengtech.emp.ecif.customer.entity.agreement.Tbshare;

@Service
@Transactional(readOnly = true)
public class AgreementBS extends BaseBS<Object> {

	@SuppressWarnings("unchecked")
	public SearchResult<Depositaccount> getDepositaccount(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Depositaccount obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}

	@SuppressWarnings("unchecked")
	public SearchResult<Loanaccount> getLoanaccount(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Loanaccount obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<Ebanksignaccount> getEbanksignaccount(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Ebanksignaccount obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<Ebankcontract> getEbankcontract(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Ebankcontract obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<Creditcontract> getCreditcontract(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Creditcontract obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<Loancontract> getLoancontract(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Loancontract obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<Guarantycontract> getGuarantycontract(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Guarantycontract obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}

	@SuppressWarnings("unchecked")
	public SearchResult<Tbshare> getTbshare(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Tbshare obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<Cesubcredit> getCesubcredit(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Cesubcredit obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<Oweinterest> getOweinterest(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Oweinterest obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<Bdshracctinfo> getTbassetacc(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Bdshracctinfo obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<Bondcontract> getBondcontract(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Bondcontract obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<Bugoldcont> getBugoldcont(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Bugoldcont obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}
	
	@SuppressWarnings("unchecked")
	public SearchResult<Bdsacctinfo> getBdshracctinfo(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, long custId) {
		StringBuffer jql = new StringBuffer("");
		jql.append("select obj from Bdsacctinfo obj, Custcontrrel chr where chr.contrId = obj.contrId");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!"".equals(custId)) {
			jql.append(" and chr.custId = " + custId + "");
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by obj." + orderBy + " " + orderType);
		}
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
				jql.toString(), values);
	}

}