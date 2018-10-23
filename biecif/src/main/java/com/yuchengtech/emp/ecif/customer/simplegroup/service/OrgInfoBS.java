package com.yuchengtech.emp.ecif.customer.simplegroup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.util.DictTranslationUtil;
import com.yuchengtech.emp.ecif.customer.customization.service.CustomizationQueryBS;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.OrgBasicVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.OrgRiskVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerFocusVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerProductVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerRelativeVO;

@Service
@Transactional(readOnly = true)
public class OrgInfoBS extends BaseBS<Object> {
	@Autowired
	private DictTranslationUtil dictTranslationUtil;
	
	protected static Logger log = LoggerFactory.getLogger(CustomizationQueryBS.class);
	
	/**
	 * 客户基本信息 
	 * @param firstResult 分页的开始索引
	 * @param pageSize 页面大小
	 * @param orderBy 排序字段
	 * @param orderType 排序方式
	 * @param conditionMap 搜索条件
	 * @return
	 */
	public SearchResult<OrgBasicVO> getOrgBasicVOList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String tabName) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO, A.CUST_ID FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = splitJointSql(sqlbegin.toString(), tabName, conditionMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		SearchResult<Object[]> customerResults = this.baseDAO
				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
						null);
		customerlist = customerResults.getResult();
		String custIds = "";
		for (Object[] obj : customerlist) {
			custIds = custIds + obj[1] + ",";
		}
		if(custIds.length() > 0){
			custIds = custIds.substring(0, custIds.length()-1);
			StringBuffer sqlleft = new StringBuffer();
			sqlleft.append(" SELECT A.CUST_NO,B.CUST_NAME,C.ORG_CUST_TYPE,D.IS_GROUP_CUST,E.ADMIN_ZONE,F.NATION_CODE,F.ENT_PROPERTY,F.ENT_SCALE,F.ECONOMIC_TYPE,G.BASIC_ACCT_BANK_NO,G.BASIC_ACCT_BANK_NAME,G.BASIC_ACCT_NO,G.BASIC_ACCT_NAME,A.CUST_ID,H.LIFECYCLE_STAT_TYPE ");
			sqlleft.append(" ,F.MAIN_CUST,F.MAIN_SERVICE,F.MAIN_PRODUCT,F.ZONE_CODE,F.EMPLOYEE_SCALE,F.MAIN_BUSINESS,F.ASSETS_SCALE ");
			sqlleft.append(" FROM M_CI_CUSTOMER A ");
			sqlleft.append(" LEFT JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_ORG C ON A.CUST_ID=C.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_ORG_KEYFLAG D ON A.CUST_ID=D.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_ORG_REGISTERINFO E ON A.CUST_ID=E.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_ORG F ON A.CUST_ID=F.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_ORG_BASICACCOUNT G ON A.CUST_ID=G.CUST_ID ");
			sqlleft.append(" LEFT JOIN M_CI_LIFECYCLE H ON A.CUST_ID=H.CUST_ID ");
			sqlleft.append(" WHERE A.CUST_ID IN (");
			sqlleft.append(custIds);
			sqlleft.append(") "); 
			sqlleft.append(" ORDER BY A.CUST_ID "); 
			List<Object[]> customerleftlist = this.baseDAO.findByNativeSQLWithIndexParam(sqlleft.toString());

			List<OrgBasicVO> orgBasicVOList = Lists.newArrayList();
			OrgBasicVO orgBasicVO = null;
			for (Object[] obj : customerleftlist) {
				orgBasicVO = new OrgBasicVO();
				orgBasicVO.setCustNo(obj[0] == null ? "" : obj[0] + "");// 客户号
				orgBasicVO.setName(obj[1] == null ? "" : obj[1] + "");// 客户名称
				orgBasicVO.setOrgCustType(obj[2] == null ? "" : obj[2] + "");// 机构客户类型
				orgBasicVO.setIsGroupCust(obj[3] == null ? "" : obj[3] + "");// 集团客户标志
				orgBasicVO.setAdminZone(obj[4] == null ? "" : obj[4] + "");// 行政区划
				orgBasicVO.setNationCode(obj[5] == null ? "" : obj[5] + "");// 国家代码
				orgBasicVO.setEntProperty(obj[6] == null ? "" : obj[6] + "");// 企业性质
				orgBasicVO.setEntScale(obj[7] == null ? "" : obj[7] + "");// 企业规模
				orgBasicVO.setEconomicType(obj[8] == null ? "" : obj[8] + "");// 经济类型
				orgBasicVO.setBasicAcctBankNo(obj[9] == null ? "" : obj[9] + "");// 基本账户开户行编号
				orgBasicVO.setBasicAcctBankName(obj[10] == null ? "" : obj[10] + "");// 基本账户开户行名称
				orgBasicVO.setBasicAcctNo(obj[11] == null ? "" : obj[11] + "");// 基本账户行号
				//orgBasicVO.setBasicAcctName(obj[12] == null ? "" : obj[12] + "");// 基本账户户名
				orgBasicVO.setBasicAcctName("");// 基本账户户名
				orgBasicVO.setCustId(obj[13] == null ? "" : obj[13] + "");// custId
				orgBasicVO.setLifecycleStatType(obj[14] == null ? "" : obj[14] + "");// 生命周期状态类型
				orgBasicVO.setMainCust(obj[15] == null ? "" : obj[15] + "");
				orgBasicVO.setMainService(obj[16] == null ? "" : obj[16] + "");
				orgBasicVO.setMainProduct(obj[17] == null ? "" : obj[17] + "");
				orgBasicVO.setZoneCode(obj[18] == null ? "" : obj[18] + "");
				orgBasicVO.setEmployeeScale(obj[19] == null ? "" : obj[19] + "");
				orgBasicVO.setMainBusiness(obj[20] == null ? "" : obj[20] + "");
				orgBasicVO.setAssetsScale(obj[21] == null ? "" : obj[21] + "");
				orgBasicVOList.add(orgBasicVO);
			}
			SearchResult<OrgBasicVO> orgBasicVOResult = new SearchResult<OrgBasicVO>();
			orgBasicVOList = this.dictTranslationUtil.setDictOrgBasic(orgBasicVOList);
			orgBasicVOResult.setResult(orgBasicVOList);
			orgBasicVOResult.setTotalCount(customerResults.getTotalCount());
			return orgBasicVOResult;
		}
		return null;
	}

	/**
	 * 客户关注事件
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @param tabName
	 * @return
	 */
	public SearchResult<PerFocusVO> getOrgFocusVOList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String tabName) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO,B.CUST_NAME,c.EVENT_TYPE,c.EVENT_DESC,c.EVENT_OCCUR_DATE,c.EVENT_REG_DATE,c.even_reg_oper_no,c.event_reg_branch_no,A.CUST_ID ");
		sqlbegin.append(" FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" LEFT JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_REL_CUST_EVENT E ON A.CUST_ID=E.CUST_ID ");
//		sqlbegin.append(" LEFT JOIN ATTENTEVENT D ON C.EVENT_ID=D.EVENT_ID ");
		sqlbegin.append(" LEFT JOIN M_EV_EVENT C ON C.EVENT_ID = E.EVENT_ID ");
		sqlbegin.append(" WHERE (C.EVENT_TYPE = '20' or C.EVENT_TYPE = '30') "); // 事件类型为客户关注事件类型
		String sql = "";
		try {
			sql = splitJointSql(sqlbegin.toString(), tabName, conditionMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		SearchResult<Object[]> customerResults = this.baseDAO
				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
						null);
		customerlist = customerResults.getResult();
		List<PerFocusVO> perFocusVOList = Lists.newArrayList();
		PerFocusVO perFocusVO = null;
		for (Object[] obj : customerlist) {
			perFocusVO = new PerFocusVO();
			perFocusVO.setCustNo(obj[0] == null ? "" : obj[0] + "");// 客户号
			perFocusVO.setName(obj[1] == null ? "" : obj[1] + "");// 客户名称
			perFocusVO.setEventType(obj[2] == null ? "" : obj[2] + "");//
			perFocusVO.setSubEventType(obj[3] == null ? "" : obj[3] + "");//
			perFocusVO.setEventDesc(obj[4] == null ? "" : obj[4] + "");//
			perFocusVO.setEventOccurDate(obj[5] == null ? "" : obj[5] + "");// 
			perFocusVO.setEventRegDate(obj[6] == null ? "" : obj[6] + "");// 
			perFocusVO.setEventRegTellerNo(obj[7] == null ? "" : obj[7] + "");// 
			perFocusVO.setEventRegBrc(obj[8] == null ? "" : obj[8] + "");//
			perFocusVO.setCustId(obj[9] == null ? "" : obj[9] + "");//
			perFocusVOList.add(perFocusVO);
		}
		SearchResult<PerFocusVO> perFocusVOResult = new SearchResult<PerFocusVO>();
		perFocusVOList = this.dictTranslationUtil.setDictPerFocus(perFocusVOList);
		perFocusVOResult.setResult(perFocusVOList);
		perFocusVOResult.setTotalCount(customerResults.getTotalCount());
		return perFocusVOResult;
	}

	/**
	 * 客户产品信息
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @param tabName
	 * @return
	 */
	public SearchResult<PerProductVO> getOrgProductVOList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String tabName) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO,B.CUST_NAME,D.PROD_CODE,D.PROD_NAME,D.PROD_TYPE,E.BRAND_NAME,E.BUSI_CHAR,E.PROD_CLASS,E.PROD_FORM,E.PROD_STAT,E.PROD_PATENT,E.START_DATE,E.END_DATE,E.OWN_SALE_FLAG,D.PROD_SUMMARY,A.CUST_ID ");
		sqlbegin.append(" FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" INNER JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
		sqlbegin.append(" INNER JOIN M_REL_CUST_PROD C ON A.CUST_ID=C.CUST_ID ");
		sqlbegin.append(" INNER JOIN M_PD_PRODUCT D ON C.PROD_ID=D.PROD_ID ");
		sqlbegin.append(" INNER JOIN M_PD_PRODUCT_BASICINFO E ON C.PROD_ID=E.PROD_ID ");
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = splitJointSql(sqlbegin.toString(), tabName, conditionMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		SearchResult<Object[]> customerResults = this.baseDAO
				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
						null);
		customerlist = customerResults.getResult();
		List<PerProductVO> perProductVOList = Lists.newArrayList();
		PerProductVO perProductVO = null;
		for (Object[] obj : customerlist) {
			perProductVO = new PerProductVO();
			perProductVO.setCustNo(obj[0] == null ? "" : obj[0] + "");// 客户号
			perProductVO.setName(obj[1] == null ? "" : obj[1] + "");// 客户名称
			perProductVO.setProdCode(obj[2] == null ? "" : obj[2] + "");// 产品代码
			perProductVO.setProdName(obj[3] == null ? "" : obj[3] + "");// 产品名称
			perProductVO.setProdType(obj[4] == null ? "" : obj[4] + "");// 产品类型
			perProductVO.setBrandName(obj[5] == null ? "" : obj[5] + "");// 品牌名称
			perProductVO.setBusiChar(obj[6] == null ? "" : obj[6] + "");// 业务性质
			perProductVO.setProdClass(obj[7] == null ? "" : obj[7] + "");// 产品分类
			perProductVO.setProdForm(obj[8] == null ? "" : obj[8] + "");// 产品形态
			perProductVO.setProdStat(obj[9] == null ? "" : obj[9] + "");// 产品状态
			perProductVO.setProdPatent(obj[10] == null ? "" : obj[10] + "");// 产品专利
			perProductVO.setStartDate(obj[11] == null ? "" : obj[11] + "");// 上线日期
			perProductVO.setEndDate(obj[12] == null ? "" : obj[12] + "");// 下线日期
			perProductVO.setOwnSaleFlag(obj[13] == null ? "" : obj[13] + "");// 自主营销标识
			perProductVO.setProdSummary(obj[14] == null ? "" : obj[14] + "");// 产品简介
			perProductVO.setCustId(obj[15] == null ? "" : obj[15] + "");// 客户ID
			perProductVOList.add(perProductVO);
		}
		SearchResult<PerProductVO> perProductVOResult = new SearchResult<PerProductVO>();
		perProductVOList = this.dictTranslationUtil.setDictPerProduct(perProductVOList);
		perProductVOResult.setResult(perProductVOList);
		perProductVOResult.setTotalCount(customerResults.getTotalCount());
		return perProductVOResult;
	}

	/**
	 * 客户分析信息
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @param tabName
	 * @return
	 */
	public SearchResult<OrgRiskVO> getorgAnalysisList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String tabName) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO,B.CUST_NAME, C.ORG_GRADE_TYPE, C.ORG_GRADE, C.EVALUTE_DATE, EFFECTIVE_DATE, EXPIRED_DATE,A.CUST_ID ");
		sqlbegin.append(" FROM M_CI_CUSTOMER A ");
		sqlbegin.append(" INNER JOIN M_CI_NAMETITLE B ON A.CUST_ID=B.CUST_ID ");
		sqlbegin.append(" INNER JOIN M_CI_ORG_GRADE C ON A.CUST_ID=C.CUST_ID ");
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = splitJointSql(sqlbegin.toString(), tabName, conditionMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		SearchResult<Object[]> customerResults = this.baseDAO
				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
						null);
		customerlist = customerResults.getResult();
		List<OrgRiskVO> orgRiskVOList = Lists.newArrayList();
		OrgRiskVO orgRiskVO = null;
		for (Object[] obj : customerlist) {
			orgRiskVO = new OrgRiskVO();
			orgRiskVO.setCustNo(obj[0] == null ? "" : obj[0] + "");// 客户号
			orgRiskVO.setName(obj[1] == null ? "" : obj[1] + "");// 客户名称
			orgRiskVO.setOrgGradeType(obj[2] == null ? "" : obj[2] + "");
			orgRiskVO.setOrgGrade(obj[3] == null ? "" : obj[3] + "");
			orgRiskVO.setEvaluteDate(obj[4] == null ? "" : obj[4] + "");// 客户名称
			orgRiskVO.setEffectiveDate(obj[5] == null ? "" : obj[5] + "");
			orgRiskVO.setExpiredDate(obj[6] == null ? "" : obj[6] + "");
			orgRiskVO.setCustId(obj[7] == null ? "" : obj[7] + ""); // 客户ID
			orgRiskVOList.add(orgRiskVO);
		}
		SearchResult<OrgRiskVO> orgRiskVOResult = new SearchResult<OrgRiskVO>();
		orgRiskVOList = this.dictTranslationUtil.setDictOrgRisk(orgRiskVOList);
		orgRiskVOResult.setResult(orgRiskVOList);
		orgRiskVOResult.setTotalCount(customerResults.getTotalCount());
		return orgRiskVOResult;
	}
	
	/**
	 * 客户关联信息
	 * 
	 * @param firstResult
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @param conditionMap
	 * @param tabName
	 * @return
	 */
	public SearchResult<PerRelativeVO> getorgRelativeList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, String tabName) {
		List<Object[]> customerlist = null;
		StringBuffer sqlbegin = new StringBuffer();
		sqlbegin.append(" SELECT A.CUST_NO SRCCUSTNO,N1.CUST_NAME SRCNAME,C2.CUST_NO DESTCUSTNO,N2.CUST_NAME DESTNAME,OBJ.CUST_REL_TYPE,OBJ.CUST_REL_STAT,OBJ.CUST_REL_DESC,OBJ.REL_START_DATE,OBJ.REL_END_DATE,A.CUST_ID ");
		sqlbegin.append(" FROM M_CI_CUSTREL OBJ ");
		sqlbegin.append(" LEFT JOIN M_CI_CUSTOMER A ON OBJ.SRC_CUST_ID=A.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_CI_CUSTOMER C2 ON OBJ.DEST_CUST_ID=C2.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_CI_NAMETITLE N1 ON OBJ.SRC_CUST_ID=N1.CUST_ID ");
		sqlbegin.append(" LEFT JOIN M_CI_NAMETITLE N2 ON OBJ.DEST_CUST_ID=N2.CUST_ID ");
		sqlbegin.append(" WHERE 1 = 1 ");
		String sql = "";
		try {
			sql = splitJointOrgRelativeSql(sqlbegin.toString(), tabName, conditionMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		SearchResult<Object[]> customerResults = this.baseDAO
				.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql,
						null);
		customerlist = customerResults.getResult();
		List<PerRelativeVO> perRelativeVOList = Lists.newArrayList();
		PerRelativeVO perRelativeVO = null;
		for (Object[] obj : customerlist) {
		    perRelativeVO = new PerRelativeVO();
			perRelativeVO.setSrcCustNo(obj[0] == null ? "" : obj[0] + "");// 客户号
			perRelativeVO.setSrcName(obj[1] == null ? "" : obj[1] + "");// 客户名称
			perRelativeVO.setDestCustNo(obj[2] == null ? "" : obj[2] + "");// 关联客户号
			perRelativeVO.setDestName(obj[3] == null ? "" : obj[3] + "");// 关联客户名称
			perRelativeVO.setRelType(obj[4] == null ? "" : obj[4] + "");// 关系类型
			perRelativeVO.setRelStat(obj[5] == null ? "" : obj[5] + "");// 关系状态
			perRelativeVO.setRelDesc(obj[6] == null ? "" : obj[6] + "");// 关系详细
			perRelativeVO.setStartDate(obj[7] == null ? "" : obj[7] + "");// 开始日期
			perRelativeVO.setEndDate(obj[8] == null ? "" : obj[8] + "");// 结束日期
			perRelativeVO.setCustId(obj[9] == null ? "" : obj[9] + "");// 客户ID
			perRelativeVOList.add(perRelativeVO);
		}
		SearchResult<PerRelativeVO> perRelativeVOResult = new SearchResult<PerRelativeVO>();
		perRelativeVOList = this.dictTranslationUtil.setDictPerRelative(perRelativeVOList);
		perRelativeVOResult.setResult(perRelativeVOList);
		perRelativeVOResult.setTotalCount(customerResults.getTotalCount());
		return perRelativeVOResult;
	}
	/**
	 * 客户关系要求客户号能对应正反关系查询
	 */
	@SuppressWarnings("unchecked")
	public String splitJointOrgRelativeSql(String sql, String tabName, Map<String, Object> conditionMap) throws Exception {
		Map<String, Object> fieldValue = new HashMap<String, Object>();
		fieldValue = (Map<String, Object>) conditionMap.get("fieldValues");
		StringBuffer selectCondition = new StringBuffer(); 
		if (tabName.equals("basic")) {
			String custNo = fieldValue.get("custNo") == null ? "" : fieldValue.get("custNo").toString();
			String name = fieldValue.get("name") == null ? "" : fieldValue.get("name").toString();
			String ebankRegNo = fieldValue.get("ebankRegNo") == null ? "": fieldValue.get("ebankRegNo").toString();
			if (!custNo.equals("")) {
				selectCondition.append(" AND (A.CUST_NO ='");
				selectCondition.append(custNo);
				selectCondition.append("' or  C2.CUST_NO='");
				selectCondition.append(custNo);
				selectCondition.append("') ");
			}
			if (!name.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_CI_NAMETITLE I WHERE A.CUST_ID = I.CUST_ID AND I.CUST_NAME like '%");
				selectCondition.append(name);
				selectCondition.append("%') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_CI_NAMETITLE I WHERE c2.CUST_ID = I.CUST_ID AND I.CUST_NAME like '%");
				selectCondition.append(name);
				selectCondition.append("%') ) ");
			}
			if (!ebankRegNo.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM ORGOTHERINFO J WHERE A.CUST_ID = J.CUST_ID AND J.EBANK_REG_NO = '");
				selectCondition.append(ebankRegNo);
				selectCondition.append("') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM ORGOTHERINFO J WHERE c2.CUST_ID = J.CUST_ID AND J.EBANK_REG_NO = '");
				selectCondition.append(ebankRegNo);
				selectCondition.append("') ) ");
			}
		}
		if (tabName.equals("styles")) {
			String identType = fieldValue.get("identType") == null ? "": fieldValue.get("identType").toString();
			String identNo = fieldValue.get("identNo") == null ? "": fieldValue.get("identNo").toString();
			if (!identType.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_CI_ORG_IDENTIFIER H WHERE A.CUST_ID = H.CUST_ID ");
				selectCondition.append(" AND H.IDENT_TYPE ='");
				selectCondition.append(identType);
				selectCondition.append("' ");
				if (!identNo.equals("")) {
					selectCondition.append(" AND H.IDENT_NO ='");
					selectCondition.append(identNo);
					selectCondition.append("' ");
				}
				selectCondition.append(") ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_CI_ORG_IDENTIFIER H WHERE c2.CUST_ID = H.CUST_ID ");
				selectCondition.append(" AND H.IDENT_TYPE ='");
				selectCondition.append(identType);
				selectCondition.append("' ");
				if (!identNo.equals("")) {
					selectCondition.append(" AND H.IDENT_NO ='");
					selectCondition.append(identNo);
					selectCondition.append("' ");
				}
				selectCondition.append(") ) ");
			}
		}
		if (tabName.equals("product") && fieldValue.get("productType") != null && fieldValue.get("productType").toString().trim().length() > 0) {
			String productType = fieldValue.get("productType") == null ? ""
					: fieldValue.get("productType").toString();
			String productNo = fieldValue.get("productNo") == null ? ""
					: fieldValue.get("productNo").toString();
			switch (Integer.valueOf(productType)) {
			case 0:// 存款账户CUST_CONTR_REL
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_DEPOSIT_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 存款账户账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_DEPOSIT_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') ) "); // 存款账户账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 1:// 贷款账户LOANACCOUNT
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_LOAN_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCT_NO = '" + productNo + "') "); // 贷款账户账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_LOAN_ACCT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCT_NO = '" + productNo + "') ) "); // 贷款账户账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 2:// 担保合同GUARANTYCONTRACT
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, GUARANTYCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 担保合同编号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, GUARANTYCONTRACT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') ) "); // 担保合同编号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 3:// 借款合同LOANCONTRACT
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, LOANCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 贷款合同编号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, LOANCONTRACT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') ) "); // 贷款合同编号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 4:// 理财账户信息BDSHRACCTINFO
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSHRACCTINFO G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 理财账户信息理财账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSHRACCTINFO G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') ) "); // 理财账户信息理财账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 5:// 债券持有信息BONDCONTRACT
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BONDCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ZQDM = '" + productNo + "') "); // 债券持有信息债券代码
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BONDCONTRACT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ZQDM = '" + productNo + "') ) "); // 债券持有信息债券代码
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 6:// 基金份额
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, TBSHARE G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ASSET_ACC = '" + productNo + "') "); // 基金份额理财账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, TBSHARE G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ASSET_ACC = '" + productNo + "') ) "); // 基金份额理财账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 7:// 网银协议
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, EBANKCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.IAB_ACCNO = '" + productNo + "') "); // 网银协议账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, EBANKCONTRACT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.IAB_ACCNO = '" + productNo + "') ) "); // 网银协议账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 8:// 授信协议
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, CREDITCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CREDIT_CONTR_NO = '" + productNo + "') "); // 授信协议授信合同号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, CREDITCONTRACT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CREDIT_CONTR_NO = '" + productNo + "') ) "); // 授信协议授信合同号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 9:// 黄金交易签约
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BUGOLDCONT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.GOLDACCTNO = '" + productNo + "') "); // 黄金交易签约黄金账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BUGOLDCONT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.GOLDACCTNO = '" + productNo + "') ) "); // 黄金交易签约黄金账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 10:// 网银签约
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, EBANKSIGNACCOUNT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.BANK_ACC = '" + productNo + "') "); // 网银签约银行账号
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, EBANKSIGNACCOUNT G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.BANK_ACC = '" + productNo + "') ) "); // 网银签约银行账号
				} else {
					selectCondition.append(") ) ");
				}
				break;
			case 11:// 凭证式债券BDSACCTINFO
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSACCTINFO G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 
				} else {
					selectCondition.append(") ");
				}
				selectCondition.append(" or EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSACCTINFO G WHERE F.CUST_ID = c2.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') ) "); // 
				} else {
					selectCondition.append(") ) ");
				}
				break;
			default:
				break;
			}
		}
		if (tabName.equals("verify")) {
			StringBuffer sql0 = new StringBuffer();
			sql0.append(" WHERE 1 = 1 ");
			String contmeth = fieldValue.get("Contmeth") == null ? "": fieldValue.get("Contmeth").toString();
			String address = fieldValue.get("Address") == null ? "": fieldValue.get("Address").toString();
			if (!address.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM ADDRESS G WHERE A.CUST_ID = G.CUST_ID ");
				selectCondition.append(" AND G.ADDR LIKE '%");
				selectCondition.append(address);
				selectCondition.append("%') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM ADDRESS G WHERE c2.CUST_ID = G.CUST_ID ");
				selectCondition.append(" AND G.ADDR LIKE '%");
				selectCondition.append(address);
				selectCondition.append("%') ) ");
			}
			if (!contmeth.equals("")) {
				selectCondition.append(" AND ( EXISTS(SELECT 1 FROM CONTMETH I WHERE A.CUST_ID = I.CUST_ID ");
				selectCondition.append(" AND I.CONTMETH_INFO = '");
				selectCondition.append(contmeth);
				selectCondition.append("') ");
				selectCondition.append(" or EXISTS(SELECT 1 FROM CONTMETH I WHERE c2.CUST_ID = I.CUST_ID ");
				selectCondition.append(" AND I.CONTMETH_INFO = '");
				selectCondition.append(contmeth);
				selectCondition.append("') ) ");
			}
		}
		selectCondition.append(" AND (A.CUST_TYPE = '2' or A.CUST_TYPE = '3') ORDER BY A.CUST_ID "); // 设置客户类型为机构类客户
		sql = sql + selectCondition.toString();
		return sql;
	}
	
	/**
	 * 拼接SQL
	 * @param sql
	 * @param tabName
	 * @param fieldValue
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String splitJointSql(String sql, String tabName, Map<String, Object> conditionMap) throws Exception {
		Map<String, Object> fieldValue = new HashMap<String, Object>();
		fieldValue = (Map<String, Object>) conditionMap.get("fieldValues");
		StringBuffer selectCondition = new StringBuffer(); 
		if (tabName.equals("basic")) {
			String custNo = fieldValue.get("custNo") == null ? "" : fieldValue.get("custNo").toString();
			String name = fieldValue.get("name") == null ? "" : fieldValue.get("name").toString();
			String ebankRegNo = fieldValue.get("ebankRegNo") == null ? "": fieldValue.get("ebankRegNo").toString();
			if (!custNo.equals("")) {
				selectCondition.append(" AND A.CUST_NO ='");
				selectCondition.append(custNo);
				selectCondition.append("' ");
			}
			if (!name.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_CI_NAMETITLE I WHERE A.CUST_ID = I.CUST_ID AND I.CUST_NAME like '%");
				selectCondition.append(name);
				selectCondition.append("%') ");
			}
			if (!ebankRegNo.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_CI_ORGOTHERINFO J WHERE A.CUST_ID = J.CUST_ID AND J.EBANK_REG_NO = '");
				selectCondition.append(ebankRegNo);
				selectCondition.append("') ");
			}
		}
		if (tabName.equals("styles")) {
			String identType = fieldValue.get("identType") == null ? "": fieldValue.get("identType").toString();
			String identNo = fieldValue.get("identNo") == null ? "": fieldValue.get("identNo").toString();
			if (!identType.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_CI_ORG_IDENTIFIER H WHERE A.CUST_ID = H.CUST_ID ");
				selectCondition.append(" AND H.IDENT_TYPE ='");
				selectCondition.append(identType);
				selectCondition.append("' ");
				if (!identNo.equals("")) {
					selectCondition.append(" AND H.IDENT_NO ='");
					selectCondition.append(identNo);
					selectCondition.append("' ");
				}
				selectCondition.append(") ");
			}
		}
		if (tabName.equals("product") && fieldValue.get("productType") != null && fieldValue.get("productType").toString().trim().length() > 0) {
			String productType = fieldValue.get("productType") == null ? ""
					: fieldValue.get("productType").toString();
			String productNo = fieldValue.get("productNo") == null ? ""
					: fieldValue.get("productNo").toString();
			switch (Integer.valueOf(productType)) {
			case 0:// 存款账户CUST_CONTR_REL
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_DEPOSIT_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 存款账户账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 1:// 贷款账户LOANACCOUNT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, M_AG_LOAN_ACCT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCT_NO = '" + productNo + "') "); // 贷款账户账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 2:// 担保合同GUARANTYCONTRACT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, GUARANTYCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 担保合同编号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 3:// 借款合同LOANCONTRACT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, LOANCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CONTR_NO = '" + productNo + "') "); // 贷款合同编号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 4:// 理财账户信息BDSHRACCTINFO
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSHRACCTINFO G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 理财账户信息理财账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 5:// 债券持有信息BONDCONTRACT
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BONDCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ZQDM = '" + productNo + "') "); // 债券持有信息债券代码
				} else {
					selectCondition.append(") ");
				}
				break;
			case 6:// 基金份额
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, TBSHARE G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ASSET_ACC = '" + productNo + "') "); // 基金份额理财账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 7:// 网银协议
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, EBANKCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.IAB_ACCNO = '" + productNo + "') "); // 网银协议账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 8:// 授信协议
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, CREDITCONTRACT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.CREDIT_CONTR_NO = '" + productNo + "') "); // 授信协议授信合同号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 9:// 黄金交易签约
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BUGOLDCONT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.GOLDACCTNO = '" + productNo + "') "); // 黄金交易签约黄金账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 10:// 网银签约
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, EBANKSIGNACCOUNT G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.BANK_ACC = '" + productNo + "') "); // 网银签约银行账号
				} else {
					selectCondition.append(") ");
				}
				break;
			case 11:// 凭证式债券BDSACCTINFO
				selectCondition.append(" AND EXISTS(SELECT 1 FROM M_REL_CUST_CONTR F, BDSACCTINFO G WHERE F.CUST_ID = A.CUST_ID AND F.CONTR_ID=G.CONTR_ID ");
				if (productNo != null && productNo.trim().length() > 0) {
					selectCondition.append(" AND G.ACCTNO = '" + productNo + "') "); // 
				} else {
					selectCondition.append(") ");
				}
				break;
			default:
				break;
			}
		}
		if (tabName.equals("verify")) {
			StringBuffer sql0 = new StringBuffer();
			sql0.append(" WHERE 1 = 1 ");
			String contmeth = fieldValue.get("Contmeth") == null ? "": fieldValue.get("Contmeth").toString();
			String address = fieldValue.get("Address") == null ? "": fieldValue.get("Address").toString();
			if (!address.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM ADDRESS G WHERE A.CUST_ID = G.CUST_ID ");
				selectCondition.append(" AND G.ADDR LIKE '%");
				selectCondition.append(address);
				selectCondition.append("%') ");
			}
			if (!contmeth.equals("")) {
				selectCondition.append(" AND EXISTS(SELECT 1 FROM CONTMETH I WHERE A.CUST_ID = I.CUST_ID ");
				selectCondition.append(" AND I.CONTMETH_INFO = '");
				selectCondition.append(contmeth);
				selectCondition.append("') ");
			}
		}
		selectCondition.append(" AND (A.CUST_TYPE = '2' or A.CUST_TYPE = '3') ORDER BY A.CUST_ID "); // 设置客户类型为机构类客户
		sql = sql + selectCondition.toString();
		return sql;
	}
	
	/**
	 * 获取下拉列表的信息
	 * @return
	 */
	public List<Map<String, String>> findIdentType() {
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		StringBuffer jql = new StringBuffer();
		jql.append("select obj.STD_CODE,obj.STD_CODE_DESC  from F_CD_STD_CODE obj where obj.STD_CATE = 'CD010004'");
		List<Object[]> objList = this.baseDAO.findByNativeSQLWithIndexParam(jql
				.toString());
		for (Object[] obj : objList) {
			map = Maps.newHashMap();
			map.put("id", obj[0].toString());
			map.put("text", obj[1].toString());
			results.add(map);
			map = null;
		}
		return results;
	}
	
}