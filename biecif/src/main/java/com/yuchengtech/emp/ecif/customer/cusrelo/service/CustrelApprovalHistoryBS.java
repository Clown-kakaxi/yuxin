package com.yuchengtech.emp.ecif.customer.cusrelo.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
import com.yuchengtech.emp.ecif.customer.cusrelo.web.vo.CusRelationLookVO;
import com.yuchengtech.emp.ecif.customer.entity.customer.CustrelApproval;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述
 * </pre>
 * 
 * @author zhaotc zhaotc@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service("custrelApprovalHistoryBs")
@Transactional(readOnly = true)
public class CustrelApprovalHistoryBS extends BaseBS<CustrelApproval> {

	protected static Logger log = LoggerFactory
			.getLogger(CustrelApprovalHistoryBS.class);
	
	/**
	 * @param firstResult
	 *            分页的开始游标
	 * @param pageSize
	 *            游标大小
	 * @param orderBy
	 *            排序字段
	 * @param orderType
	 *            排序方式（升序/降序）
	 * @param conditionMap
	 *            参数列表（其他的参数，键值对形式）
	 * @return 任务实例集合
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<CusRelationLookVO> getCustrelApproval(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String approvalStat) {

		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" custrelApproval.CUSTREL_APPROVAL_ID,");
		sql.append(" custrelApproval.SRC_CUST_ID,");
		sql.append(" custrelApproval.DEST_CUST_ID,");
		sql.append(" custrelApproval.CUST_REL_TYPE ,");
		sql.append(" custrelApproval.CUST_REL_DESC,");
		sql.append(" custrelApproval.CUST_REL_STAT,");
		sql.append(" custrelApproval.REL_START_DATE,");
		sql.append(" custrelApproval.REL_END_DATE,");
		sql.append(" custrelApproval.OPERATOR,");
		sql.append(" custrelApproval.OPER_TIME, ");
		sql.append(" custrelApproval.OPER_STAT, ");
		sql.append(" custrelApproval.APPROVAL_OPERATOR, ");
		sql.append(" custrelApproval.APPROVAL_TIME, ");
		sql.append(" custrelApproval.APPROVAL_STAT, ");	
		sql.append(" custrelApproval.APPROVAL_NOTE, ");
		sql.append(" n1.CUST_NAME srcName, ");
		sql.append(" n2.CUST_NAME destName, ");
		sql.append(" customer1.CUST_NO srcCustNo,");
		sql.append(" customer2.CUST_NO destCustNo ");
		sql.append(" from  APP_CUSTREL_APPROVAL custrelApproval ");
		sql.append(" left join M_CI_NAMETITLE n1 on n1.CUST_ID = custrelApproval.SRC_CUST_ID ");
		sql.append(" left join M_CI_NAMETITLE n2 on n2.CUST_ID = custrelApproval.DEST_CUST_ID ");
		sql.append(" left join M_CI_CUSTOMER customer1 on customer1.CUST_ID = custrelApproval.SRC_CUST_ID");
		sql.append(" left join M_CI_CUSTOMER customer2 on customer2.CUST_ID = custrelApproval.DEST_CUST_ID");
		// 没有审批状态，审批状态不为待审批
		sql.append(" where 1=1 and ( custrelApproval.APPROVAL_STAT <> '");
		sql.append(approvalStat);
		sql.append("')");

		if (!conditionMap.get("jql").equals("")) {
			sql.append(" and " + conditionMap.get("jql"));
		}
		if (!StringUtils.isEmpty(orderBy)) {
			sql.append(" order by custrelApproval." + orderBy + " " + orderType);
		}
		Map<String, Object> values = (Map<String, Object>) conditionMap.get("params");
		String queryJql = (String) conditionMap.get("jql");
		String approvalTimeBegin = null;
		String approvalTimeEnd = null;
		if(queryJql.indexOf("custrelApproval.APPROVAL_TIME >=:") != -1){
			int approvalTimeBeginIndex = queryJql.indexOf("custrelApproval.APPROVAL_TIME >=:") + "custrelApproval.APPROVAL_TIME >=:".length();
			approvalTimeBegin = queryJql.substring(approvalTimeBeginIndex, approvalTimeBeginIndex + 6);
		}
		if(queryJql.indexOf("custrelApproval.APPROVAL_TIME <=:") != -1){
			int approvalTimeEndIndex = queryJql.indexOf("custrelApproval.APPROVAL_TIME <=:") + "custrelApproval.APPROVAL_TIME >=:".length();
			approvalTimeEnd = queryJql.substring(approvalTimeEndIndex, approvalTimeEndIndex + 6);
		}
		if(approvalTimeBegin != null && approvalTimeBegin.length() > 0){
			values.put(approvalTimeBegin, values.get(approvalTimeBegin)+" 00:00:00");
		}
		if(approvalTimeEnd != null && approvalTimeEnd.length() > 0){
			values.put(approvalTimeEnd, values.get(approvalTimeEnd)+" 23:59:59");
		}
		List<CusRelationLookVO> temps = new ArrayList<CusRelationLookVO>();
		SearchResult<Object[]> temp = this.baseDAO
				.findPageWithNameParamByNativeSQL(firstResult, pageSize,
						sql.toString(), values);

		List<Object[]> objList = temp.getResult();
		Date tempDate = null;
		Timestamp tempDate1 = null;
		for (Object[] obj : objList) {
			CusRelationLookVO custrelInstance = new CusRelationLookVO();
			//客户间关系审批id
			custrelInstance.setCustrelApprovalId((obj[0] != null ? obj[0].toString(): ""));
			//源码客户号
//			custrelInstance.setSrcCustNo(obj[2] != null ? obj[2].toString(): "");
			//源客户标识
			custrelInstance.setSrcId(obj[1] != null ? obj[1].toString() : "");
			//源客户名
//			custrelInstance.setSrcName(obj[4] != null ? obj[4].toString() : "");
			//目标客户号
//			custrelInstance.setDestCustNo(obj[4] != null ? obj[4].toString() : "");
			//目标客户标识
			custrelInstance.setDestId(obj[2] != null ? obj[2].toString() : "");
			//目标客户名称
//			custrelInstance.setDestName(obj[7] != null ? obj[7].toString() : "");
			//客户关系类型
			custrelInstance.setCustRelType(obj[3] != null ? obj[3].toString(): "");
			//客户间关系描述
			custrelInstance.setCustRelDesc(obj[4] != null ? obj[4].toString(): "");
			//客户间关系状态
			custrelInstance.setCustRelStat(obj[5] != null ? obj[5].toString(): "");
			//关系开始日期
//			custrelInstance.setCustRelStart(obj[7] != null ? obj[7].toString() : "");
			try {
				tempDate = obj[6]!=null?ConvertUtils.getDateStrToData(obj[6].toString()):null;
			} catch (ParseException e) {
				tempDate = null;
			}finally{
				custrelInstance.setCustRelStart(tempDate);
			}
			tempDate = null;
			//关系结束日期
//			custrelInstance.setCustRelEnd(obj[8] != null ? obj[8].toString() : "");
			try {
				tempDate = obj[7]!=null?ConvertUtils.getDateStrToData(obj[7].toString()):null;
			} catch (ParseException e) {
				tempDate = null;
			}finally{
				custrelInstance.setCustRelEnd(tempDate);
			}
			tempDate = null;
			//提交人
			custrelInstance.setOperator(obj[8] != null ? obj[8].toString(): "");
			//提交时间
			try {
				tempDate1 = obj[9]!=null?ConvertUtils.getStrToTimestamp2(obj[9].toString()):null;
			} catch (ParseException e) {
				tempDate1 = null;
			}finally{
				custrelInstance.setOperTime(tempDate1);
			}
			tempDate1 = null;
			//custrelInstance.setOperTime(obj[9] != null ? obj[9].toString(): "");
			//操作状态
			custrelInstance.setOperStat(obj[10] != null ? obj[10].toString(): "");
			//审批人
			custrelInstance.setApprovalOperator(obj[11] != null ? obj[11].toString(): "");
			//审批时间			
			try {
				tempDate1 = obj[12]!=null?ConvertUtils.getStrToTimestamp2(obj[12].toString()):null;
			} catch (ParseException e) {
				tempDate1 = null;
			}finally{
				custrelInstance.setApprovalTime(tempDate1);
			}
			tempDate1 = null;
			//审批状态
			custrelInstance.setApprovalStat(obj[13] != null ? obj[13].toString(): "");
			//审批意见
			custrelInstance.setApprovalNote(obj[14] != null ? obj[14].toString(): "");
			custrelInstance.setSrcName(obj[15] != null ? obj[15].toString(): "");
			custrelInstance.setDestName(obj[16] != null ? obj[16].toString(): "");
			custrelInstance.setSrcCustNo(obj[17] != null ? obj[17].toString(): "");
			custrelInstance.setDestCustNo(obj[18] != null ? obj[18].toString(): "");
			temps.add(custrelInstance);
		}

		SearchResult<CusRelationLookVO> taskVOResult = new SearchResult<CusRelationLookVO>();
		taskVOResult.setResult(temps);
		taskVOResult.setTotalCount(temp.getTotalCount());
		return taskVOResult;
	}

	

	
	
	

}
