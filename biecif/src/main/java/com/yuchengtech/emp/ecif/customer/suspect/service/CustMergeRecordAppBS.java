package com.yuchengtech.emp.ecif.customer.suspect.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.customer.entity.other.CustMergeRecord;
import com.yuchengtech.emp.ecif.customer.entity.other.CustMergeRecordApproval;
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
public class CustMergeRecordAppBS extends BaseBS<CustMergeRecordApproval> {

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
	public SearchResult<CustMergeRecordApproval> getCustMergeRecordList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String approvalStat) {
		
//		PersonIdentifier
//		Orgidentinfo
		
		StringBuffer jql = new StringBuffer();
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");		
		
		jql.append(" select c ");
		jql.append(" from CustMergeRecordApproval c where 1=1 ");
		if (!conditionMap.get("jql").equals("")) {
			jql.append(" and " + conditionMap.get("jql"));
		}
		//审批状态判断过滤条件
		if(approvalStat.equals(GlobalConstants.APPROVAL_STAT_1)){
			jql.append(" and c.approvalStat = '");
			jql.append(GlobalConstants.APPROVAL_STAT_1);//待审批
			jql.append("' ");
		}else{
			jql.append(" and c.approvalStat <> '");
			jql.append(GlobalConstants.APPROVAL_STAT_1);//审批通过或审批不通过
			jql.append("' ");
		}
		
		if (!StringUtils.isEmpty(orderBy)) {
			jql.append(" order by c." + orderBy + " " + orderType);
		}
		SearchResult<CustMergeRecordApproval> res = this.baseDAO
				.findPageWithNameParam(firstResult, pageSize, jql.toString(), values);
		return res;
	}
	
	/**
	 * 通过两个客户号，查询合并记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CustMergeRecordApproval getCustMergeRecordAppByCustId(String cust1, String cust2){
		if(StringUtils.isEmpty(cust1) || StringUtils.isEmpty(cust2)){
			return null;
		}
		StringBuffer jql = new StringBuffer("");
		jql.append(" select sl from CustMergeRecordApproval sl ");
		jql.append(" where sl.reserveCustNo=?0 and sl.mergedCustNo=?1 and sl.approvalStat=?2 ");
		List<CustMergeRecordApproval> result = this.baseDAO.createQueryWithIndexParam(
				jql.toString(), cust1, cust2, GlobalConstants.APPROVAL_STAT_1).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}

	/**
	 * 通过两个客户号，查询合并记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CustMergeRecordApproval getCustMergeRecordAppById(String cust1, String cust2, String id){
		if(StringUtils.isEmpty(cust1) || StringUtils.isEmpty(cust2) || StringUtils.isEmpty(id)){
			return null;
		}
		StringBuffer jql = new StringBuffer("");
		jql.append(" select sl from CustMergeRecordApproval sl ");
		jql.append(" where sl.reserveCustNo=?0 and sl.mergedCustNo=?1 and sl.approvalStat=?2 ");
		jql.append(" and sl.mergeRecordApprovalId=?3 ");
		List<CustMergeRecordApproval> result = this.baseDAO.createQueryWithIndexParam(
				jql.toString(), cust1, cust2, GlobalConstants.APPROVAL_STAT_1, Long.valueOf(id)).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
}
