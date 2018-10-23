package com.yuchengtech.emp.ecif.customer.suspect.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.customer.entity.other.CustMergeRecord;
import com.yuchengtech.emp.ecif.customer.entity.other.SuspectList;
/**
 * <pre>
 * Title:客户合并信息
 * Description: 。
 * </pre>
 * @author guanyb  guanyb@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Service
@Transactional(readOnly = true)
public class CustMergeRecordBS extends BaseBS<CustMergeRecord> {

	/**
	 * 获取列表数据, 支持查询
	 * @param firstResult 分页的开始索引
	 * @param pageSize 页面大小
	 * @param orderBy 排序字段
	 * @param orderType 排序方式
	 * @param approvalStat 审批状态判断过滤条件
	 * @param conditionMap 搜索条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<CustMergeRecord> getCustMergeRecordList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		
//		PersonIdentifier
//		Orgidentinfo
		
		StringBuffer jql = new StringBuffer();
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		jql.append(" select c ");
		jql.append(" from CustMergeRecord c where 1=1 ");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by c." + orderBy + " " + orderType);
		}
		SearchResult<CustMergeRecord> res = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql.toString(), values);
		return res;
	}
	
	/**
	 * 通过两个客户号，查询合并记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CustMergeRecord getCustMergeRecordByCustId(String cust1, String cust2){
		if(StringUtils.isEmpty(cust1) || StringUtils.isEmpty(cust2)){
			return null;
		}
		StringBuffer jql = new StringBuffer("");
		jql.append(" select sl from CustMergeRecord sl ");
		jql.append(" where sl.reserveCustNo=?0 and sl.mergedCustNo=?1 ");
		List<CustMergeRecord> result = this.baseDAO.createQueryWithIndexParam(
				jql.toString(), cust1, cust2).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}

}
