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
import com.yuchengtech.emp.ecif.customer.entity.other.CustSplitRecord;
/**
 * <pre>
 * Title:客户拆分信息
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
public class CustSplitRecordBS extends BaseBS<CustSplitRecord> {

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
	public SearchResult<CustSplitRecord> getCustSplitRecordList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		
//		PersonIdentifier
//		Orgidentinfo
		
		StringBuffer jql = new StringBuffer();
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		jql.append(" select c ");
		jql.append(" from CustSplitRecord c where 1=1 ");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by c." + orderBy + " " + orderType);
		}
		SearchResult<CustSplitRecord> res = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql.toString(), values);
		return res;
	}
	
	/**
	 * 通过拆分客户号，查询拆分记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CustSplitRecord getCustMergeRecordByCustId(String cust1){
		if(StringUtils.isEmpty(cust1)){
			return null;
		}
		StringBuffer jql = new StringBuffer("");
		jql.append(" select sl from CustSplitRecord sl ");
		jql.append(" where sl.mergedCustNo=?0 order by sl.splitRecordId desc ");
		List<CustSplitRecord> result = this.baseDAO.createQueryWithIndexParam(
				jql.toString(), cust1).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}

}
