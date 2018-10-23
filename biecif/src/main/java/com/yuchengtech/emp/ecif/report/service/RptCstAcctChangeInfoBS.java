package com.yuchengtech.emp.ecif.report.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.DictTranslationUtil;
import com.yuchengtech.emp.ecif.base.util.ExcelReadWriteUtil;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.report.constant.ReportConstant;

@Service
@Transactional(readOnly = true)
public class RptCstAcctChangeInfoBS extends BaseBS<Object> {
	
	protected static Logger log = LoggerFactory.getLogger(RptCstAcctChangeInfoBS.class);
	
	@Autowired
	private ResultUtil resultUtil;
	
	@Autowired
	private DictTranslationUtil dictTranslationUtil;
	
	@Autowired
	private CodeUtil codeUtil;
	
	private Map<String, String> codeMapOrg = Maps.newHashMap();//

	@SuppressWarnings("unchecked")
	public SearchResult<Map<String, String>> rptCstAcctChangeInfo(int firstResult,
			int pageSize, String orderBy, String orderType, Map<String, Object> conditionMap, String custAcctSts){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(conditionMap != null && conditionMap.get("fieldValues") != null){
			queryMap = (Map<String, Object>) conditionMap.get("fieldValues");
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT RPT_DATE, CUST_ACCT_STS, CREATE_BRANCH_NO, BRANCH_NAME, ");
		sql.append(" CUST_NO, CUST_NAME, DEPOSIT_BAL, DEPOSIT_BAL_DAY_AVG, CREATE_TIME ");
		sql.append(" FROM RPT_CUST_ACCT_CHANGE_INFO WHERE 1 = 1 ");
		if(!ResultUtil.isEmpty(custAcctSts)){
			sql.append(" AND CUST_ACCT_STS = '");
			sql.append(custAcctSts);
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("srptMonth")))){
			if(ReportConstant.RPT_CUST_ACCT_CHANGE_INFO_CUST_ACCT_STS_CSTCLOSE.equals(custAcctSts)){
				sql.append(" AND RPT_DATE >= '");
			}else{
				sql.append(" AND CREATE_TIME >= '");
			}
			sql.append(String.valueOf(queryMap.get("srptMonth")));
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("erptMonth")))){
			if(ReportConstant.RPT_CUST_ACCT_CHANGE_INFO_CUST_ACCT_STS_CSTCLOSE.equals(custAcctSts)){
				sql.append(" AND RPT_DATE <= '");
			}else{
				sql.append(" AND CREATE_TIME <= '");
			}
			sql.append(String.valueOf(queryMap.get("erptMonth")));
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("createBranchNo")))){
			String createBranchNo = "'" + String.valueOf(queryMap.get("createBranchNo")).replaceAll("\\;", "','") + "'";
			sql.append(" AND CREATE_BRANCH_NO IN (");
			sql.append(createBranchNo);
			sql.append(") ");
		}
		if(ReportConstant.RPT_CUST_ACCT_CHANGE_INFO_CUST_ACCT_STS_CSTCLOSE.equals(custAcctSts)){
			sql.append(" order by RPT_DATE ");
		}else{
			sql.append(" order by CREATE_TIME ");
		}
		SearchResult<Object> searchResultOjbect = this.baseDAO.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql.toString(), null);
		List<Object> list = searchResultOjbect.getResult();
		List<Map<String, String>> returnListMaps = new ArrayList<Map<String, String>>();
		try{
			returnListMaps = resultUtil.listObjectToListMaps(list, sql.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if(returnListMaps != null){
			SearchResult<Map<String, String>> searchResult = new SearchResult<Map<String, String>>();
			searchResult.setResult(this.dictTranslationUtil.setDictRptCustAcctChangeInfo(returnListMaps));
			searchResult.setTotalCount(searchResultOjbect.getTotalCount());
			return searchResult;
		}else{
			return null;
		}
	}

	public List<Object[]> rptCstAcctChangeInfo(String custAcctSts, String srptMonth, String erptMonth, String createBranchNo){
				
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CREATE_BRANCH_NO, BRANCH_NAME, ");
		sql.append(" CUST_NO, CUST_NAME, DEPOSIT_BAL, DEPOSIT_BAL_DAY_AVG, CREATE_TIME ");
		sql.append(" FROM RPT_CUST_ACCT_CHANGE_INFO WHERE 1 = 1 ");
		if(!ResultUtil.isEmpty(custAcctSts)){
			sql.append(" AND CUST_ACCT_STS = '");
			sql.append(custAcctSts);
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(srptMonth)){
			sql.append(" AND CREATE_TIME >= '");
			sql.append(srptMonth);
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(erptMonth)){
			sql.append(" AND CREATE_TIME <= '");
			sql.append(erptMonth);
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(createBranchNo)){
			createBranchNo = "'" + createBranchNo.replaceAll("\\;", "','") + "'";
			sql.append(" AND CREATE_BRANCH_NO IN (");
			sql.append(createBranchNo);
			sql.append(") ");
		}
		sql.append(" order by CREATE_TIME ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		return list;
	}
	//销户
	public List<Object[]> rptCstAcctChangeInfo2(String custAcctSts, String srptMonth, String erptMonth, String createBranchNo){
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT CREATE_BRANCH_NO, BRANCH_NAME, ");
		sql.append(" CUST_NO, CUST_NAME, DEPOSIT_BAL_DAY_AVG, CREATE_TIME, RPT_DATE ");
		sql.append(" FROM RPT_CUST_ACCT_CHANGE_INFO WHERE 1 = 1 ");
		if(!ResultUtil.isEmpty(custAcctSts)){
			sql.append(" AND CUST_ACCT_STS = '");
			sql.append(custAcctSts);
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(srptMonth)){
			sql.append(" AND RPT_DATE >= '");
			sql.append(srptMonth);
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(erptMonth)){
			sql.append(" AND RPT_DATE <= '");
			sql.append(erptMonth);
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(createBranchNo)){
			createBranchNo = "'" + createBranchNo.replaceAll("\\;", "','") + "'";
			sql.append(" AND CREATE_BRANCH_NO IN (");
			sql.append(createBranchNo);
			sql.append(") ");
		}
		sql.append(" order by RPT_DATE ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		return list;
	}
	
	public String export(String custAcctSts, String srptMonth, String erptMonth, String createBranchNo){
		List<Object[]> list = null;//rptCstAcctChangeInfo(custAcctSts,rptDate, createBranchNo);
		if(ReportConstant.RPT_CUST_ACCT_CHANGE_INFO_CUST_ACCT_STS_CSTCLOSE.equals(custAcctSts)){
			list = rptCstAcctChangeInfo2(custAcctSts, srptMonth, erptMonth, createBranchNo);
		}else if(ReportConstant.RPT_CUST_ACCT_CHANGE_INFO_CUST_ACCT_STS_CSTOPEN.equals(custAcctSts)){
			list = rptCstAcctChangeInfo(custAcctSts, srptMonth, erptMonth, createBranchNo);
		}
		if(list != null && list.size() > 0){
			ExcelReadWriteUtil excelReadWriteUtil = new ExcelReadWriteUtil();
			String templateName = "";
			String reportName = "";
			if(ReportConstant.RPT_CUST_ACCT_CHANGE_INFO_CUST_ACCT_STS_CSTCLOSE.equals(custAcctSts)){
				templateName = GlobalConstants.EXCEL_TEMPLATE_ORGCSTCLOSE;
				reportName = GlobalConstants.EXCEL_REPORT_ORGCSTCLOSE_CN;
			}else if(ReportConstant.RPT_CUST_ACCT_CHANGE_INFO_CUST_ACCT_STS_CSTOPEN.equals(custAcctSts)){
				templateName = GlobalConstants.EXCEL_TEMPLATE_ORGCSTOPEN;
				reportName = GlobalConstants.EXCEL_REPORT_ORGCSTOPEN_CN;
			}
			String templatePath = excelReadWriteUtil.getFilePathName(templateName, GlobalConstants.EXCEL_TEMPLATE_IMPORT_PATH);
			int sheetIndex = 0, writeBeginRowIndex = 2;  // 设置写入sheetIndex 设置 写入开始行
			Workbook workbook = excelReadWriteUtil.getWorkbookByListObjectReport(sheetIndex, writeBeginRowIndex, list, templatePath, "("+srptMonth+"-"+erptMonth+")");
			String exportFileName = excelReadWriteUtil.getFilePathName(reportName, GlobalConstants.EXCEL_REPORT_FOLDER);
			try {
				excelReadWriteUtil.outputStreamExcel(exportFileName, workbook);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			return exportFileName;
		}else{
			return null;
		}
	}
	
	public String getCodeMapOrg(String paramValue) {
		if(paramValue == null){
			return "";
		}
		if(codeMapOrg == null || codeMapOrg.isEmpty()){
			codeMapOrg = Maps.newHashMap();
			codeMapOrg = this.codeUtil.getOrgMap2();//
		}
		String result = "";
		if(codeMapOrg.get(paramValue) != null && !codeMapOrg.get(paramValue).equals("")){
			result = codeMapOrg.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
}