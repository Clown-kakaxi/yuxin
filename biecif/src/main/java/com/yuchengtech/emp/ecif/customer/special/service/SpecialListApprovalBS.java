package com.yuchengtech.emp.ecif.customer.special.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.ConvertUtils;
import com.yuchengtech.emp.ecif.customer.entity.other.SpecialListApproval;
import com.yuchengtech.emp.ecif.customer.special.web.vo.SpecialListApprovalVO;
/**
 * <pre>
 * Title:黑名单审批信息
 * Description: 对待审批信息进行审批，并根据审批信息的操作类型对黑名单信息进行相应操作。
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
public class SpecialListApprovalBS extends BaseBS<SpecialListApproval> {

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
	public SearchResult<SpecialListApprovalVO> getSpecialListApprovalList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String approvalStat) {
		StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT ");
			sql.append(" s.SPECIAL_LIST_ID, ");
			sql.append(" s.CUST_ID, ");
			sql.append(" s.SPECIAL_LIST_TYPE, ");
			sql.append(" s.SPECIAL_LIST_FLAG, ");
		    sql.append(" s.IDENT_TYPE, ");
		    sql.append(" s.IDENT_NO, ");
		    sql.append(" s.IDENT_CUST_NAME, ");
		    sql.append(" s.ENTER_REASON, ");
		    sql.append(" s.STAT_FLAG, ");
		    sql.append(" s.START_DATE, ");//10
		    sql.append(" s.END_DATE, ");//11
		    
		    sql.append(" s.OPERATOR, ");
		    sql.append(" s.OPER_TIME, ");
		    sql.append(" s.OPER_STAT, ");
		    sql.append(" s.APPROVAL_OPERATOR, ");
		    sql.append(" s.APPROVAL_TIME, ");
		    sql.append(" s.APPROVAL_STAT, ");
		    sql.append(" s.APPROVAL_NOTE, ");
		    sql.append(" s.SPECIALLIST_APPROVAL_ID, ");
		    sql.append(" c.CUST_NO, ");
		    sql.append(" s.SPECIAL_LIST_KIND ");
		    
		    sql.append(" FROM ");
		    sql.append(" APP_SPECIALLIST_APPROVAL s  ");
		    sql.append(" left join M_CI_CUSTOMER c ");
		    sql.append(" on s.CUST_ID=c.CUST_ID ");
			sql.append(" WHERE 1=1 ");
			//审批状态判断过滤条件
			if(approvalStat.equals(GlobalConstants.APPROVAL_STAT_1)){
				sql.append(" and s.APPROVAL_STAT = '");
				sql.append(GlobalConstants.APPROVAL_STAT_1);//待审批
				sql.append("' ");
			}else{
				sql.append(" and s.APPROVAL_STAT <> '");
				sql.append(GlobalConstants.APPROVAL_STAT_1);//审批通过或审批不通过
				sql.append("' ");
			}
	    
//		if (!conditionMap.get("jql").equals("")) {
//			sql.append(" and " + conditionMap.get("jql"));
//		}
		Map<String, ?> fieldValues = (Map<String, ?>) conditionMap.get("fieldValues");
		if(approvalStat.equals(GlobalConstants.APPROVAL_STAT_1)){
			if(fieldValues.get("c.cust_No") != null){
				sql.append(" and c.cust_No ='"+fieldValues.get("c.cust_No")+"'" );
			}
			if(fieldValues.get("s.ident_No") != null){
				sql.append(" and s.ident_No ='"+fieldValues.get("s.ident_No")+"'" );
			}
			if(fieldValues.get("s.SPECIAL_LIST_KIND") != null){
				sql.append(" and s.SPECIAL_LIST_KIND ='"+fieldValues.get("s.SPECIAL_LIST_KIND")+"'" );
			}
			if(fieldValues.get("srptMonth") != null){
				sql.append(" and s.OPER_TIME >='" +fieldValues.get("srptMonth")+" 00:00:00'" );
			}
			if(fieldValues.get("erptMonth") != null){
				sql.append(" and s.OPER_TIME <='" +fieldValues.get("erptMonth")+" 23:59:59'" );
			}
		}else{
			if(fieldValues.get("c.cust_No") != null){
				sql.append(" and c.cust_No ='"+fieldValues.get("c.cust_No")+"'" );
			}
			if(fieldValues.get("s.ident_No") != null){
				sql.append(" and s.ident_No ='"+fieldValues.get("s.ident_No")+"'" );
			}
			if(fieldValues.get("s.SPECIAL_LIST_KIND") != null){
				sql.append(" and s.SPECIAL_LIST_KIND ='"+fieldValues.get("s.SPECIAL_LIST_KIND")+"'" );
			}
			if(fieldValues.get("s.APPROVAL_STAT") != null){
				sql.append(" and s.APPROVAL_STAT ='"+fieldValues.get("s.APPROVAL_STAT")+"'" );
			}
			if(fieldValues.get("srptMonth") != null){
				sql.append(" and s.APPROVAL_TIME >='" +fieldValues.get("srptMonth")+" 00:00:00'" );
			}
			if(fieldValues.get("erptMonth") != null){
				sql.append(" and s.APPROVAL_TIME <='" +fieldValues.get("erptMonth")+" 23:59:59'" );
			}
		}
		
		//审批状态判断排序条件
		if(approvalStat.equals(GlobalConstants.APPROVAL_STAT_1)){
			sql.append(" order by s.OPER_TIME desc ");
		}else{
			sql.append(" order by s.APPROVAL_TIME desc ");
		}
		
		Map<String, ?> values = null;//(Map<String, ?>) conditionMap.get("params");
		
		List<SpecialListApprovalVO> temps = new ArrayList<SpecialListApprovalVO>();
		SearchResult<Object[]> temp = this.baseDAO.findPageWithNameParamByNativeSQL(firstResult, pageSize,
				sql.toString(), values);
		List<Object[]> objList = temp.getResult();
		Date tempDate = null;
		Timestamp tempDate1 = null;
		for(Object[] o : objList){
			SpecialListApprovalVO vo = new SpecialListApprovalVO();
			vo.setSpecialListId(o[0]!=null?Long.valueOf(o[0].toString()):null);
			vo.setCustId(o[1]!=null?Long.valueOf(o[1].toString()):null);
			vo.setSpecialListType(o[2]!=null?o[2].toString():GlobalConstants.SPECIALLIST_TYPE);
			vo.setSpecialListFlag(o[3]!=null?o[3].toString():"");
			vo.setIdentType(o[4]!=null?o[4].toString():"");
			vo.setIdentNo(o[5]!=null?o[5].toString():"");
			vo.setIdentCustName(o[6]!=null?o[6].toString():"");
			vo.setEnterReason(o[7]!=null?o[7].toString():"");
			vo.setStatFlag(o[8]!=null?o[8].toString():"");
			try {
				tempDate = o[9]!=null?ConvertUtils.getDateStrToData(o[9].toString()):null;
			} catch (ParseException e) {
				tempDate = null;
			}finally{
				vo.setStartDate(tempDate);
			}
			tempDate = null;
			try {
				tempDate = o[10]!=null?ConvertUtils.getDateStrToData(o[10].toString()):null;
			} catch (ParseException e) {
				tempDate = null;
			}finally{
				vo.setEndDate(tempDate);
			}
			
		    vo.setOperator(o[11]!=null?o[11].toString():"");
		    try {
		    	tempDate1 = o[12]!=null?ConvertUtils.getStrToTimestamp2(o[12].toString()):null;
			} catch (ParseException e) {
				tempDate1 = null;
			}finally{
				vo.setOperTime(tempDate1);
			}
		    tempDate1 = null;
		    vo.setOperStat(o[13]!=null?o[13].toString():"");
		    vo.setApprovalOperator(o[14]!=null?o[14].toString():"");
		    try {
		    	tempDate1 = o[15]!=null?ConvertUtils.getStrToTimestamp2(o[15].toString()):null;
			} catch (ParseException e) {
				tempDate1 = null;
			}finally{
				vo.setApprovalTime(tempDate1);
			}
		    tempDate1 = null;
		    vo.setApprovalStat(o[16]!=null?o[16].toString():"");
		    vo.setApprovalNote(o[17]!=null?o[17].toString():"");
		    vo.setSpecialListApprovalId(o[18]!=null?Long.valueOf(o[18].toString()):null);
		    
		    vo.setCustNo(o[19]!=null?o[19].toString():"");
		    vo.setSpecialListKind(o[20]!=null?o[20].toString():"");
			temps.add(vo);
		}
		
		SearchResult<SpecialListApprovalVO> results = new SearchResult<SpecialListApprovalVO>();
		results.setResult(temps);
		results.setTotalCount(temp.getTotalCount());
		
		return results;
	}
	
	/**
	 * 直接更新给定的黑名单审批信息
	 * @param specialListId
	 */
	public void updateApprovalInfo(Long specialListApprovalId, 
			String approvalOperator, Date approvalTime, String approvalStat, String approvalNote){
		String sql = "UPDATE APP_SPECIALLIST_APPROVAL SET " +
				" APPROVAL_OPERATOR=?0, APPROVAL_TIME=?1, APPROVAL_STAT=?2, APPROVAL_NOTE=?3 " +
				" WHERE SPECIALLIST_APPROVAL_ID=?4";
		this.baseDAO.createNativeQueryWithIndexParam(
				sql,
				approvalOperator,
				approvalTime,
				approvalStat,
				approvalNote,
				specialListApprovalId).executeUpdate();
		this.baseDAO.flush();
	}

	/**
	 * 通过客户标识获得黑名单审批信息
	 * @param custId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SpecialListApproval getSpecialListApprovalByCustId(Long custId){
		if(custId == null || custId == 0L){
			return null;
		}
		String sql = "select c from SpecialListApproval c where c.custId=?0 ";
		List<SpecialListApproval> result = this.baseDAO.createQueryWithIndexParam(
				sql, custId).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
	/**
	 * 通过客户标识获得黑名单信息
	 * @param custId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SpecialListApproval getSpecialListApprovalByInfo(
			String identType, String identNo, 
			String identCustName, String specialListKind){
		if(StringUtils.isEmpty(identType) || StringUtils.isEmpty(identNo) || 
				StringUtils.isEmpty(identCustName) || StringUtils.isEmpty(specialListKind)){
			return null;
		}
		String sql = "select c from SpecialListApproval c where c.identType=?0 and c.identNo=?1 " +
				" and c.identCustName=?2 and c.specialListKind=?3 and c.approvalStat =?4";
		List<SpecialListApproval> result = this.baseDAO.createQueryWithIndexParam(
				sql, identType, identNo, identCustName, specialListKind, GlobalConstants.APPROVAL_STAT_1).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
}
