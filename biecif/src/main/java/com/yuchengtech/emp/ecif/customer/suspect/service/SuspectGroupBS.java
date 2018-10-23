package com.yuchengtech.emp.ecif.customer.suspect.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.util.ConvertUtils;
import com.yuchengtech.emp.ecif.customer.entity.other.SuspectGroup;
import com.yuchengtech.emp.ecif.customer.entity.other.SuspectList;
import com.yuchengtech.emp.ecif.customer.suspect.web.vo.SuspectCustVO;
/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
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
public class SuspectGroupBS extends BaseBS<SuspectGroup> {

	private Logger log = LoggerFactory.getLogger(SuspectGroupBS.class);

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
	public SearchResult<SuspectCustVO> getSuspectGroupList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" g.SUSPECT_GROUP_DESC, ");
		sql.append(" g.SUSPECT_DATA_DATE, ");
		sql.append(" g.SUSPECT_SEARCH_DATE, ");
		sql.append(" g.ENTER_REASON, ");
	    sql.append(" c.cust_No, ");
	    sql.append(" c.cust_Type, ");
	    sql.append(" l.cust_name, ");
	    sql.append(" l.SUSPECT_GROUP_ID, ");
	    sql.append(" l.CUST_ID, ");
	    sql.append(" l.MERGE_DEAL_FLAG, ");
	    sql.append(" l.MERGE_DEAL_DATE, ");
	    sql.append(" l.CREATE_DATE, ");
	    sql.append(" c.CREATE_BRANCH_NO ");
	    sql.append(" FROM ");
	    sql.append(" APP_SUSPECT_LIST l  ");
	    sql.append(" left join APP_SUSPECT_GROUP g ");
	    sql.append(" on l.SUSPECT_GROUP_ID=g.SUSPECT_GROUP_ID ");
	    sql.append(" left join M_CI_CUSTOMER c ");
	    sql.append(" on l.CUST_ID=c.CUST_ID ");
//	    sql.append(" left join NAMETITLE n ");
//	    sql.append(" on l.CUST_ID=n.CUST_ID ");
		sql.append(" WHERE 1=1 ");
		
//		StringBuffer jql = new StringBuffer();
//		jql.append(" select new com.yuchengtech.emp.ecif.customer.suspect.web.vo.SuspectCustVO ");
//		jql.append(" (sg.suspectGroupDesc, sg.suspectDataDate, sg.suspectSearchDate, ");
//		jql.append(" sg.suspectExportFlag, sg.enterReason, ");
//		jql.append(" c.custNo, c.custType, n.name, sl) ");
//		jql.append(" from SuspectGroup sg, SuspectList sl, Customer c, NameTitle n ");
//		jql.append(" where sg.suspectGroupId=sl.suspectGroupId and sl.custId=c.custId and sl.custId=n.custId ");
		
		//{jql=c.custType =:param0 and c.custNo =:param1, 
		//params={param0=2, param1=28012603841}, 
		//fieldValues={c.custNo=28012603841, c.custType=2}}
		Map<String, ?> fieldValues = (Map<String, ?>) conditionMap.get("fieldValues");
		if(fieldValues.get("l.cust_Type") != null){
			if(fieldValues.get("l.cust_Type").toString().equals("1")){
				sql.append(" and l.cust_Type ='"+fieldValues.get("l.cust_Type")+"'" );
			}else{
				sql.append(" and (l.cust_Type ='2' or l.cust_Type ='3') " );
			}
		}
		if(fieldValues.get("c.cust_No") != null){
			Long groupId = getSuspectListByCustNo(fieldValues.get("c.cust_No").toString());
			sql.append(" and l.SUSPECT_GROUP_ID =" + groupId);
		}		
//		if (!conditionMap.get("jql").equals("")) {
//			jql.append(" and " + conditionMap.get("jql"));
//		}
		if (!StringUtils.isEmpty(orderBy)) {
			sql.append(" order by l." + orderBy + " " + orderType);
		}
		Map<String, ?> values = null;//(Map<String, ?>) conditionMap.get("params");
//		return this.baseDAO.findPageWithNameParam(firstResult, pageSize,
//		jql.toString(), values);
		List<SuspectCustVO> temps = new ArrayList<SuspectCustVO>();
		SearchResult<Object[]> temp = this.baseDAO.findPageWithNameParamByNativeSQL(
				firstResult, pageSize,
				sql.toString(), values);
		List<Object[]> objList = temp.getResult();
		for(Object[] o : objList){
			SuspectCustVO vo = new SuspectCustVO();
		    vo.setSuspectGroupDesc(o[0]!=null?o[0].toString():"");
		    vo.setSuspectDataDate(o[1]!=null?o[1].toString():"");
		    vo.setSuspectSearchDate(o[2]!=null?o[2].toString():"");
		    vo.setEnterReason(o[3]!=null?o[3].toString():"");
		    vo.setCustNo(o[4]!=null?o[4].toString():"");
		    vo.setCustType(o[5]!=null?o[5].toString():"");
		    vo.setCustName(o[6]!=null?o[6].toString():"");
		    vo.setSuspectGroupId(Long.valueOf(o[7].toString()));
		    vo.setCustId(Long.valueOf(o[8].toString()));
		    vo.setMergeDealFlag(o[9]!=null?o[9].toString():"");
		    try {
				vo.setMergeDealDate(o[10]!=null?ConvertUtils.getStrToTimestamp2(o[10].toString()):null);
			} catch (ParseException e) {
				vo.setMergeDealDate(null);
			}
		    try {
		    	vo.setCreateDate(o[11]!=null?ConvertUtils.getDateStrToData(o[11].toString()):null);
			} catch (ParseException e) {
				vo.setCreateDate(null);
			}
		    vo.setCreateBranchNo(o[12]!=null?o[12].toString():"");
			temps.add(vo);
		}		
		SearchResult<SuspectCustVO> results = new SearchResult<SuspectCustVO>();
		results.setResult(temps);
		results.setTotalCount(temp.getTotalCount());
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public Long getSuspectListByCustNo(String custNo){
		if(StringUtils.isEmpty(custNo)){
			return 0L;
		}
//		String sql = " select sl.SUSPECT_GROUP_ID from SUSPECT_LIST sl, Customer c " +
//				" where sl.cust_Id=c.cust_Id and c.cust_No=?0 ";
//		List<SuspectList> result = this.baseDAO.createNativeQueryWithIndexParam(
//				sql, custNo).getResultList();
		StringBuffer jql = new StringBuffer();
//		jql.append(" select sl.suspectGroupId from SuspectList sl, Customer c ");
//		jql.append(" where sl.custId=c.custId and c.custNo=?0 ");
		jql.append(" select s from SuspectList s");
		jql.append(" where s.custNo=?0 ");
		List<SuspectList> result = this.baseDAO.createQueryWithIndexParam(
				jql.toString(), custNo).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0).getSuspectGroupId();
		}
		return 0L;
	}
	
	/**
	 * 通过客户号和组号获得疑似客户信息
	 * 分组标识	
	 * 客户编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SuspectList getSuspectListByCustId(Long suspectGroupId, String custNo){
		if(suspectGroupId == null || suspectGroupId == 0L || StringUtils.isEmpty(custNo)){
			return null;
		}
		StringBuffer jql = new StringBuffer();
		jql.append(" select sl from SuspectList sl, Customer c ");
		jql.append(" where sl.custId=c.custId and sl.suspectGroupId=?0 and c.custNo=?1 ");
		List<SuspectList> result = this.baseDAO.createQueryWithIndexParam(
				jql.toString(), suspectGroupId, custNo).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
	/**
	 * 通过客户号获得疑似客户信息
	 * 客户编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SuspectList getSuspectListGroupByCustId(String custNo){
		if(StringUtils.isEmpty(custNo)){
			return null;
		}
		StringBuffer jql = new StringBuffer();
		jql.append(" select sl from SuspectList sl, Customer c ");
		jql.append(" where sl.custId=c.custId and c.custNo=?0 ");
		List<SuspectList> result = this.baseDAO.createQueryWithIndexParam(
				jql.toString(), custNo).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}
	
	/**
	 * 直接更新给定的疑似客户确认信息
	 * @param Long suspectGroupId
	 */
	public void updateComfirmInfo(Long suspectListId, String suspectComfirmFlag, 
			String suspectComfirmResult, String suspectComfirmOperator){
		String sql = "UPDATE APP_SUSPECT_LIST SET SUSPECT_CONFIRM_FLAG=?0, " +
				" SUSPECT_CONFIRM_RESULT=?1, SUSPECT_CONFIRM_OPERATOR=?2 " +
				" WHERE SUSPECT_LIST_ID=?3";
		this.baseDAO.createNativeQueryWithIndexParam(
				sql,
				suspectComfirmFlag,
				suspectComfirmResult,
				suspectComfirmOperator,
				suspectListId).executeUpdate();
		this.baseDAO.flush();
	}
	
	/**
	 * 直接更新给定的疑似客户合并信息
	 * 合并处理标志,合并处理日期
	 * @param Long suspectGroupId
	 */
	public void updateMergeInfo(Long suspectListId, 
			String mergeDealFlag, String mergeDealDate){
		String sql = "UPDATE APP_SUSPECT_LIST SET MERGE_DEAL_FLAG=?0, MERGE_DEAL_DATE=?1 " +
				" WHERE SUSPECT_LIST_ID=?2";
		this.baseDAO.createNativeQueryWithIndexParam(
				sql,
				mergeDealFlag,
				mergeDealDate,
				suspectListId).executeUpdate();
		this.baseDAO.flush();
	}

}
