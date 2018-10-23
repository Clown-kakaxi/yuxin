package com.yuchengtech.emp.ecif.customer.special.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.ConvertUtils;
import com.yuchengtech.emp.ecif.customer.entity.customerrisk.SpecialList;
import com.yuchengtech.emp.ecif.customer.special.web.vo.SpecialListVO;
/**
 * <pre>
 * Title:黑名单信息
 * Description: 业务数据展示，对于ecif系统的数据进行修改、删除、新增黑名单，操作的信息状态为待审批。
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
public class SpecialListAABS extends BaseBS<SpecialList> {

	/**
	 * 获取列表数据, 支持查询
	 * @param firstResult 分页的开始索引
	 * @param pageSize 页面大小
	 * @param orderBy 排序字段
	 * @param orderType 排序方式
	 * @param conditionMap 搜索条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<SpecialListVO> getSpecialListList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap) {
		StringBuffer sql = new StringBuffer();		
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
		    sql.append(" s.START_DATE, ");
		    sql.append(" s.END_DATE, ");
		    sql.append(" s.LAST_UPDATE_SYS, ");
		    sql.append(" s.LAST_UPDATE_USER, ");
		    sql.append(" s.LAST_UPDATE_TM, ");
		    sql.append(" s.TX_SEQ_NO, ");
		    sql.append(" s.APPROVAL_FLAG, ");
		    sql.append(" c.CUST_NO, ");
		    sql.append(" s.SPECIAL_LIST_KIND, ");
		    sql.append(" c.CUST_TYPE ");
		    sql.append(" FROM ");
		    sql.append(" M_CI_SPECIALLIST s  ");
		    sql.append(" left join M_CI_CUSTOMER c ");
		    sql.append(" on s.CUST_ID=c.CUST_ID ");
			sql.append(" WHERE ");
			sql.append(" s.SPECIAL_LIST_TYPE='"+GlobalConstants.SPECIALLIST_TYPE+"' ");		    
	    
		if (!conditionMap.get("jql").equals("")) {
			sql.append(" and " + conditionMap.get("jql"));
		}
//		sql.append(" and (s.APPROVAL_FLAG <>'"+GlobalConstants.APPROVAL_FLAG_1+"' ");
//	    sql.append(" or s.APPROVAL_FLAG is null) ");
		sql.append(" order by s.CUST_ID desc ");
		
		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");
		
		Map<String, String> codeMapSpecialListKind = Maps.newHashMap();//黑名单类型
		codeMapSpecialListKind = getDescCodeMapSpecialListKind(GlobalConstants.CODE_STR_SPECIALlIST_TYPE);
		
		List<SpecialListVO> temps = new ArrayList<SpecialListVO>();
		SearchResult<Object[]> temp = this.baseDAO.findPageWithNameParamByNativeSQL(firstResult, pageSize,
				sql.toString(), values);
		List<Object[]> objList = temp.getResult();
		Date tempDate = null;
		for(Object[] o : objList){
			SpecialListVO vo = new SpecialListVO();
			vo.setSpecialListId(Long.valueOf(o[0].toString()));
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
			tempDate = null;
			vo.setLastUpdateSys(o[11]!=null?o[11].toString():"");
			vo.setLastUpdateUser(o[12]!=null?o[12].toString():"");
			try {
				vo.setLastUpdateTm(o[13]!=null?ConvertUtils.getStrToTimestamp2(o[13].toString()):null);
			} catch (ParseException e) {
				vo.setLastUpdateTm(null);
			}			
			vo.setTxSeqNo(o[14]!=null?o[14].toString():"");
			vo.setApprovalFlag(o[15]!=null?o[15].toString():"");
			vo.setCustNo(o[16]!=null?o[16].toString():"");
			vo.setSpecialListKind(o[17]!=null?o[17].toString():"");
			//可控制黑名单类型与当前类型比较
			if(getOtherKind(codeMapSpecialListKind).equals(o[17]!=null?o[17].toString():"")){
				vo.setOtherKind("1");//受控
			}else{
				vo.setOtherKind("0");//不受控
			}
			vo.setCustType(o[18]!=null?o[18].toString():"");
			temps.add(vo);
		}		
		SearchResult<SpecialListVO> results = new SearchResult<SpecialListVO>();
		results.setResult(temps);
		results.setTotalCount(temp.getTotalCount());
		
		return results;
	}
	
	/**
	 * 筛选授权的黑名单类别
	 * @param codeMapSpecialListKind
	 * @return
	 */
	public String getOtherKind(Map<String, String> codeMapSpecialListKind){
		if(GlobalConstants.OTHER_SPECIALLIST.length > 0){
			for(String str : GlobalConstants.OTHER_SPECIALLIST){
				if(codeMapSpecialListKind.get(str)!=null){
					return codeMapSpecialListKind.get(str);
				}
			}
		}
		return "-1";
	}
	
	/**
	 * 直接更新给定的黑名单审批标识
	 * @param specialListId
	 */
	public void updateApprovalFlag(Long specialListId, String approvalFlag){
		String sql = "UPDATE M_CI_SPECIALLIST SET APPROVAL_FLAG=?0 WHERE SPECIAL_LIST_ID=?1";
		this.baseDAO.createNativeQueryWithIndexParam(
				sql,
				approvalFlag,
				specialListId).executeUpdate();
		this.baseDAO.flush();
	}
	
	/**
	 * 通过客户标识获得黑名单信息
	 * @param custId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SpecialList getSpecialListByInfo(
			String identType, String identNo, 
			String identCustName, String specialListKind){
		if(StringUtils.isEmpty(identType) || StringUtils.isEmpty(identNo) || 
				StringUtils.isEmpty(identCustName) || StringUtils.isEmpty(specialListKind)){
			return null;
		}
		String jql = "select c from SpecialList c where c.identType=?0 and c.identNo=?1 " +
				" and c.identCustName=?2 and c.specialListKind=?3 ";
		List<SpecialList> result = this.baseDAO.createQueryWithIndexParam(
				jql, identType, identNo, identCustName, specialListKind).getResultList();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}

	/**
	 * 获取信息, 用于生成下拉框，黑名单类别
	 * @return
	 */
	public List<Map<String, String>> getComBoBoxSpecialListKind(String codeType) {
		//String sql = "select CODE_ID, CODE_DESC from STD_CODE where CATE_ID=?0";
		codeType = codeType.replaceAll(",", "','");		
		String sql = "select STD_CODE, STD_CODE_DESC " +
				" from TX_STD_CODE where STATE = '0' AND STD_CATE in ('"+codeType+"')";
		//
		List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(sql);
		List<Map<String, String>> comboList = Lists.newArrayList();
		Map<String, String> map;
		for(Object[] obj: objList) {
			long temp = Long.valueOf(obj[0].toString());//STD_CODE
			if(temp > 1000000000L && temp < 2000000000L){
				map = Maps.newHashMap();
				map.put("id", obj[0] != null ? obj[0].toString() : "");
				map.put("text", obj[1] != null ? obj[1].toString() : "");
				comboList.add(map);
			}			
		}
		return comboList;
	}
	
	/**
	 * 通过传入的域，得到需要的码值，黑名单类别
	 * @param codeType 可以包括多个域，用','分割
	 * @return
	 */
	public Map<String, String> getCodeMapSpecialListKind(String codeType){
		codeType = codeType.replaceAll(",", "','");
		Map<String, String> map = null;
		if(!StringUtils.isEmpty(codeType)){//if(codeType != null && !"".equals(codeType)){
			map = Maps.newHashMap();
			StringBuffer jql = new StringBuffer();
			//jql.append("select CODE_ID||CATE_ID code,CODE_DESC val from STD_CODE where CATE_ID in (");
			jql.append("select STD_CODE code,STD_CODE_DESC val from TX_STD_CODE where STATE = '0' AND STD_CATE in ('");
			jql.append(codeType);
			jql.append("')");
			
			List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql.toString());
			for(Object[] obj: objList) {
				long temp = Long.valueOf(obj[0].toString());//STD_CODE
				if(temp > 1000000000 && temp < 2000000000){
					map.put(obj[0].toString(), obj[1].toString());
				}
			}
		}
		return map;
	}
	
	/**
	 * 通过传入的域，得到需要的码值，黑名单类别
	 * @param codeType 可以包括多个域，用','分割
	 * @return
	 */
	public Map<String, String> getDescCodeMapSpecialListKind(String codeType){
		codeType = codeType.replaceAll(",", "','");
		Map<String, String> map = null;
		if(!StringUtils.isEmpty(codeType)){//if(codeType != null && !"".equals(codeType)){
			map = Maps.newHashMap();
			StringBuffer jql = new StringBuffer();
			//jql.append("select CODE_ID||CATE_ID code,CODE_DESC val from STD_CODE where CATE_ID in (");
			jql.append("select STD_CODE_DESC val,STD_CODE code from TX_STD_CODE where STATE = '0' AND STD_CATE in ('");
			jql.append(codeType);
			jql.append("')");
			
			List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql.toString());
			for(Object[] obj: objList) {
				long temp = Long.valueOf(obj[1].toString());//STD_CODE
				if(temp > 1000000000L && temp < 2000000000L){
					map.put(obj[0].toString(), obj[1].toString());
				}
			}
		}
		return map;
	}
	
}
