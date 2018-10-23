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
public class RptPersonInfoDetailBS extends BaseBS<Object> {
	
	protected static Logger log = LoggerFactory.getLogger(RptPersonInfoDetailBS.class);
	
	@Autowired
	private ResultUtil resultUtil;
	
	@Autowired
	private CodeUtil codeUtil;
	
	@Autowired
	private DictTranslationUtil dictTranslationUtil;
	
	private Map<String, String> codeMap1 = Maps.newHashMap();//年龄段
	private Map<String, String> codeMap2 = Maps.newHashMap();//最高学历
	private Map<String, String> codeMap3 = Maps.newHashMap();//AUM等级划分
	private Map<String, String> codeMap4 = Maps.newHashMap();//客户风险等级
	private Map<String, String> codeMap5 = Maps.newHashMap();//签约类型
	
	private Map<String, String> codeMapOrg = Maps.newHashMap();//

	@SuppressWarnings("unchecked")
	public SearchResult<Map<String, String>> queryRptPersonInfo(int firstResult,
			int pageSize, String orderBy, String orderType, Map<String, Object> conditionMap, String queryType){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT RPT_MONTH, RPT_TYPE, RPT_SIGN1, RPT_SIGN2, CREATE_BRANCH_NO, BRCNAME, CUST_SUM ");
		sql.append(" FROM RPT_PERSON_INFO_DETAIL, PUBBRANCHINFO WHERE CREATE_BRANCH_NO = BRCCODE ");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(conditionMap != null && conditionMap.get("fieldValues") != null){
			queryMap = (Map<String, Object>) conditionMap.get("fieldValues");
		}
		if(!ResultUtil.isEmpty(queryType)){
			sql.append(" AND RPT_TYPE = '");
			sql.append(queryType);
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("rptMonth")))){
			sql.append(" AND RPT_MONTH = '");
			sql.append(String.valueOf(queryMap.get("rptMonth")));
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("createBranchNo")))){
			String createBranchNo = "'" + String.valueOf(queryMap.get("createBranchNo")).replaceAll("\\;", "','") + "'";
			sql.append(" AND CREATE_BRANCH_NO IN (");
			sql.append(createBranchNo);
			sql.append(") ");
		}
		sql.append(" ORDER BY CREATE_BRANCH_NO, RPT_SIGN1, RPT_SIGN2 ASC ");
		SearchResult<Object[]> searchResultOjbect = this.baseDAO.findPageWithNameParamByNativeSQL(firstResult, pageSize, sql.toString(), null);
		List<Object[]> list = searchResultOjbect.getResult();
		List<Map<String, String>> returnListMaps = new ArrayList<Map<String, String>>();
		//code
		for(Object[] obj : list){
			if(queryType.equals(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_AGE_EDUCATION)){
				//个人客户维度统计表查询类型 ：10 = 年龄段分析报表&最高学历分析报表
				obj[2] = getCodeMap2(obj[2]!=null?obj[2].toString():null);
				obj[3] = getCodeMap1(obj[3]!=null?obj[3].toString():null);
			}else if(queryType.equals(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_DISTRIBUTION)){
				//个人客户维度统计表查询类型 ：20 = 客户的分布分析报表
				obj[2] = getCodeMap3(obj[2]!=null?obj[2].toString():null);
			}else if(queryType.equals(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_CSTRISKGRADE)){
				//个人客户维度统计表查询类型 ：40 = 客户风险等级分析报表
				obj[2] = getCodeMap4(obj[2]!=null?obj[2].toString():null);
			}else if(queryType.equals(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_CSTSIGN)){
				//个人客户维度统计表查询类型 ：30 = 客户签约分析报表
				obj[2] = getCodeMap3(obj[2]!=null?obj[2].toString():null);
				obj[3] = getCodeMap5(obj[3]!=null?obj[3].toString():null);
			}
		}
		//
		try{
			returnListMaps = resultUtil.listObjectToListMaps1(list, sql.toString());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if(returnListMaps != null){
			SearchResult<Map<String, String>> searchResult = new SearchResult<Map<String, String>>();
			searchResult.setResult(this.dictTranslationUtil.setDictRptPersonInfoDetail(returnListMaps));
			searchResult.setTotalCount(searchResultOjbect.getTotalCount());
			return searchResult;
		}else{
			return null;
		}
	}

	public List<Object[]> queryRptPersonInfo(String rptMonth, String createBranchNo, String queryType){
		StringBuffer sql = new StringBuffer();
		if(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_AGE_EDUCATION.equals(queryType)){
			sql.append(" SELECT CREATE_BRANCH_NO, BRCNAME, RPT_SIGN1, RPT_SIGN2, CUST_SUM ");
		} else if(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_DISTRIBUTION.equals(queryType)){
			sql.append(" SELECT CREATE_BRANCH_NO, BRCNAME, RPT_SIGN1, CUST_SUM ");
		} else if(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_CSTSIGN.equals(queryType)){
			sql.append(" SELECT RPT_SIGN2, CREATE_BRANCH_NO, BRCNAME, RPT_SIGN1, CUST_SUM ");
		} else if(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_CSTRISKGRADE.equals(queryType)){
			sql.append(" SELECT CREATE_BRANCH_NO, BRCNAME, RPT_SIGN1, CUST_SUM ");
		} else {
			sql.append(" SELECT CREATE_BRANCH_NO, RPT_SIGN1, RPT_SIGN2, CUST_SUM ");
		}
		sql.append(" FROM RPT_PERSON_INFO_DETAIL, PUBBRANCHINFO WHERE CREATE_BRANCH_NO = BRCCODE ");

		if(!ResultUtil.isEmpty(queryType)){
			sql.append(" AND RPT_TYPE = '");
			sql.append(queryType);
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(rptMonth)){
			sql.append(" AND RPT_MONTH = '");
			sql.append(rptMonth);
			sql.append("' ");
		}
		if(!ResultUtil.isEmpty(createBranchNo)){
			createBranchNo = "'" + createBranchNo.replaceAll("\\;", "','") + "'";
			sql.append(" AND CREATE_BRANCH_NO IN (");
			sql.append(createBranchNo);
			sql.append(") ");
		}
		sql.append(" ORDER BY CREATE_BRANCH_NO, RPT_SIGN1, RPT_SIGN2 ASC ");
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(sql.toString());
		return list;
	}
	
	public String export(String rptMonth, String createBranchNo, String queryType){
		List<Object[]> list = queryRptPersonInfo(rptMonth, createBranchNo, queryType);
		if(list != null && list.size() > 0){
			ExcelReadWriteUtil excelReadWriteUtil = new ExcelReadWriteUtil();
			String templateName = "";
			String reportName = "";
			if(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_AGE_EDUCATION.equals(queryType)){
				templateName = GlobalConstants.EXCEL_TEMPLATE_AGEEDUCATION;
				reportName = GlobalConstants.EXCEL_REPORT_AGEEDUCATION_CN;
			}
			if(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_DISTRIBUTION.equals(queryType)){
				templateName = GlobalConstants.EXCEL_TEMPLATE_CSTDISTRIBUTION;
				reportName = GlobalConstants.EXCEL_REPORT_CSTDISTRIBUTION_CN;
			}
			if(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_CSTSIGN.equals(queryType)){
				templateName = GlobalConstants.EXCEL_TEMPLATE_CSTSIGN;
				reportName = GlobalConstants.EXCEL_REPORT_CSTSIGN_CN;
			}
			if(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_CSTRISKGRADE.equals(queryType)){
				templateName = GlobalConstants.EXCEL_TEMPLATE_CSTRISKGRADE;
				reportName = GlobalConstants.EXCEL_REPORT_CSTRISKGRADE_CN;
			} 
			//code
			for(Object[] obj : list){
				if(queryType.equals(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_AGE_EDUCATION)){
					//个人客户维度统计表查询类型 ：10 = 年龄段分析报表&最高学历分析报表
					obj[2] = getCodeMap2(obj[2]!=null?obj[2].toString():null);
					obj[3] = getCodeMap1(obj[3]!=null?obj[3].toString():null);
				}else if(queryType.equals(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_DISTRIBUTION)){
					//个人客户维度统计表查询类型 ：20 = 客户的分布分析报表
					obj[2] = getCodeMap3(obj[2]!=null?obj[2].toString():null);
				}else if(queryType.equals(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_CSTRISKGRADE)){
					//个人客户维度统计表查询类型 ：40 = 客户风险等级分析报表
					obj[2] = getCodeMap4(obj[2]!=null?obj[2].toString():null);
				}else if(queryType.equals(ReportConstant.RPT_PERSON_INFO_DETAIL_REPORTTYPE_CSTSIGN)){
					//个人客户维度统计表查询类型 ：30 = 客户签约分析报表
					obj[0] = getCodeMap5(obj[0]!=null?obj[0].toString():null);
					obj[3] = getCodeMap3(obj[3]!=null?obj[3].toString():null);
				}
			}
			//
			String templatePath = excelReadWriteUtil.getFilePathName(templateName, GlobalConstants.EXCEL_TEMPLATE_IMPORT_PATH);
			int sheetIndex = 0, writeBeginRowIndex = 2;  // 设置写入sheetIndex 设置 写入开始行
			Workbook workbook = excelReadWriteUtil.getWorkbookByListObjectReport(sheetIndex, writeBeginRowIndex, list, templatePath, rptMonth);
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
	
	/**
	 * 年龄段
	 */
	public String getCodeMap1(String paramValue) {
		if(paramValue == null){
			return "";
		}
		//域名标识
		//String flag = "ZQ000589";
		//
		if(codeMap1 == null || codeMap1.isEmpty()){
			codeMap1 = Maps.newHashMap();
			codeMap1 = this.codeUtil.getCodeMap(GlobalConstants.CODE_STR_AGE);
		}
		String result = "";
		if(codeMap1.get(paramValue)!= null && !codeMap1.get(paramValue).equals("")){
			result = codeMap1.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
	/**
	 * 最高学历
	 */
	public String getCodeMap2(String paramValue) {
		if(paramValue == null){
			return "";
		}
		//域名标识
		//String flag = "CD010033";
		//
		if(codeMap2 == null || codeMap2.isEmpty()){
			codeMap2 = Maps.newHashMap();
			codeMap2 = this.codeUtil.getCodeMap(GlobalConstants.CODE_STR_SCHOOLFLAG);
		}
		String result = "";
		if(codeMap2.get(paramValue)!= null && !codeMap2.get(paramValue).equals("")){
			result = codeMap2.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
	/**
	 * AUM等级划分
	 */
	public String getCodeMap3(String paramValue) {
		if(paramValue == null){
			return "";
		}
		//域名标识
		//String flag = "ZQ000590";
		//
		if(codeMap3 == null || codeMap3.isEmpty()){
			codeMap3 = Maps.newHashMap();
			codeMap3 = this.codeUtil.getCodeMap(GlobalConstants.CODE_STR_AUMLV);
		}
		String result = "";
		if(codeMap3.get(paramValue)!= null && !codeMap3.get(paramValue).equals("")){
			result = codeMap3.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
	/**
	 * 客户风险等级
	 */
	public String getCodeMap4(String paramValue) {
		if(paramValue == null){
			return "";
		}
		//域名标识
		//String flag = "ZQ000380";
		//
		if(codeMap4 == null || codeMap4.isEmpty()){
			codeMap4 = Maps.newHashMap();
			codeMap4 = this.codeUtil.getCodeMap(GlobalConstants.CODE_STR_RISKLV);
		}
		String result = "";
		if(codeMap4.get(paramValue)!= null && !codeMap4.get(paramValue).equals("")){
			result = codeMap4.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
	}
	/**
	 * 签约类型
	 */
	public String getCodeMap5(String paramValue) {
		if(paramValue == null){
			return "";
		}
		//域名标识
		//String flag = "ZQ000010";
		//
		if(codeMap5 == null || codeMap5.isEmpty()){
			codeMap5 = Maps.newHashMap();
			codeMap5 = this.codeUtil.getCodeMap(GlobalConstants.CODE_STR_SIGNTYPE);//this.codeUtil.getChannelMap();
		}
		String result = "";
		if(codeMap5.get(paramValue) != null && !codeMap5.get(paramValue).equals("")){
			result = codeMap5.get(paramValue);
		}else{
			return paramValue;
		}
		return result;
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