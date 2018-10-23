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
import com.yuchengtech.emp.ecif.base.common.GlobalConstants;
import com.yuchengtech.emp.ecif.base.util.CodeUtil;
import com.yuchengtech.emp.ecif.base.util.ExcelReadWriteUtil;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;

@Service
@Transactional(readOnly = true)
public class RptCorpInfoDetailBS extends BaseBS<Object> {
	
	protected static Logger log = LoggerFactory.getLogger(RptCorpInfoDetailBS.class);
	
	@Autowired
	private ResultUtil resultUtil;
	
	@Autowired
	private CodeUtil codeUtil;
	
	private Map<String, String> codeMapOrg = Maps.newHashMap();//

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> queryRptCorpInfoDetail(Map<String, Object> conditionMap){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(conditionMap != null && conditionMap.get("fieldValues") != null){
			queryMap = (Map<String, Object>) conditionMap.get("fieldValues");
		}
		//StringBuffer sql = new StringBuffer();
		String temp = 
				" (  " +
				" SELECT  " +
				" '' as CREATE_BRANCH_NO,  " +
				" '' as BRCNAME,  " +
				" '总计：' as CSTAUMTYPEDECLARE,  " +
				" CASE  " +
				" WHEN t.day_sum_cust1 IS NULL  " +
				" THEN 0  " +
				" ELSE t.day_sum_cust1  " +
				" END SUM_DAY_SUM_CUST ,  " +
				" CASE  " +
				" WHEN t.day_sum_cust1 IS NULL  " +
				" OR t2.SUM_DAY_SUM_CUST = 0  " +
				" OR t2.SUM_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_cust1*100.000/t2.SUM_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE1,  " +
				" CASE  " +
				" WHEN t.day_sum_cust1 IS NULL  " +
				" OR t2.sum_LAST_DAY_SUM_CUST = 0 " +
				" OR t2.sum_LAST_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_cust1*100.000/t2.sum_LAST_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE2,  " +
				" CASE  " +
				" WHEN t.day_sum_cust1 IS NULL  " +
				" OR t2.sum_FIRST_DAY_SUM_CUST = 0 " +
				" OR t2.sum_FIRST_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_cust1*100.000/t2.sum_FIRST_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE3,  " +
				" CASE  " +
				" WHEN t.day_sum_aum1 IS NULL  " +
				" THEN 0  " +
				" ELSE t.day_sum_aum1  " +
				" END DAY_SUM_AUM,  " +
				" CASE  " +
				" WHEN t.day_sum_aum1 IS NULL  " +
				" OR t2.sum_DAY_SUM_AUM =0  " +
				" OR t2.sum_DAY_SUM_AUM IS NULL  " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_aum1*100.000/t2.sum_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE1,  " +
				" CASE  " +
				" WHEN t.day_sum_aum1 IS NULL  " +
				" OR t2.sum_LAST_DAY_SUM_AUM = 0 " +
				" OR t2.sum_LAST_DAY_SUM_AUM IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_aum1*100.000/t2.sum_LAST_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE2,  " +
				" CASE  " +
				" WHEN t.day_sum_aum1 IS NULL  " +
				" OR t2.sum_FIRST_DAY_SUM_AUM = 0 " +
				" OR t2.sum_FIRST_DAY_SUM_AUM IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_aum1*100.000/t2.sum_FIRST_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE3/*,  " +
				" CASE  " +
				" WHEN t2.SUM_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE t2.SUM_DAY_SUM_CUST  " +
				" END sum_cust,  " +
				" t2.sum_LAST_DAY_SUM_CUST,  " +
				" t2.sum_FIRST_DAY_SUM_CUST,  " +
				" CASE  " +
				" WHEN t2.sum_DAY_SUM_AUM IS NULL " +
				" THEN 0  " +
				" ELSE t2.sum_DAY_SUM_AUM  " +
				" END sum_aum,  " +
				" t2.sum_LAST_DAY_SUM_AUM,  " +
				" t2.sum_FIRST_DAY_SUM_AUM*/  " +
				" FROM  " +
				" (  " +
				" SELECT  " +
				" sum(DAY_SUM_CUST) as day_sum_cust1, " +
				" sum(DAY_SUM_AUM) as day_sum_aum1 " +
				" FROM  " +
				" RPT_CORP_INFO_DETAIL  " +
				" WHERE  " +
				" 1=1  " ;
//				" AND RPT_DATE = '2013-07-25'  " +
//				" --AND CREATE_BRANCH_NO = '0100' " +
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("rptDate")))){
			  temp = temp +" AND RPT_DATE = '"+String.valueOf(queryMap.get("rptDate"))+"' ";
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("createBranchNo")))){
				String createBranchNo = "'" + String.valueOf(queryMap.get("createBranchNo")).replaceAll("\\;", "','") + "'";
				temp = temp +" AND CREATE_BRANCH_NO IN ("+createBranchNo+") ";
			}
			temp = temp + 
				" ) t ,  " +
				" (  " +
				" SELECT  " +
				" SUM(DAY_SUM_CUST) AS SUM_DAY_SUM_CUST, " +
				" SUM(LAST_DAY_SUM_CUST) AS sum_LAST_DAY_SUM_CUST, " +
				" SUM(FIRST_DAY_SUM_CUST) AS sum_FIRST_DAY_SUM_CUST, " +
				" SUM(DAY_SUM_AUM) AS sum_DAY_SUM_AUM, " +
				" SUM(LAST_DAY_SUM_AUM) AS sum_LAST_DAY_SUM_AUM, " +
				" SUM(FIRST_DAY_SUM_AUM) AS sum_FIRST_DAY_SUM_AUM " +
				" FROM  " +
				" RPT_CORP_INFO_DETAIL  " +
				" WHERE  " +
				" 1=1  " ;
//				" AND RPT_DATE = '2013-07-25'  " +
//				" --AND CREATE_BRANCH_NO = '0100'  " +
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("rptDate")))){
			  temp = temp +" AND RPT_DATE = '"+String.valueOf(queryMap.get("rptDate"))+"' ";
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("createBranchNo")))){
				String createBranchNo = "'" + String.valueOf(queryMap.get("createBranchNo")).replaceAll("\\;", "','") + "'";
				temp = temp +" AND CREATE_BRANCH_NO IN ("+createBranchNo+") ";
			}
			temp = temp + 
				"  " +
				" ) t2  " +
				" )  " +
				"  " +
				" UNION ALL  " +
				"  " +
				" (  " +
				" SELECT  " +
				" t. CREATE_BRANCH_NO,  " +
				" t.BRCNAME,  " +
				" (  " +
				" CASE t.AUM_TYPE  " +
				" WHEN '01'  " +
				" THEN '1万以下'  " +
				" WHEN '02'  " +
				" THEN '5万-1万（含）'  " +
				" WHEN '03'  " +
				" THEN '10万-5万（含）'  " +
				" WHEN '04'  " +
				" THEN '30万-10万（含）'  " +
				" WHEN '05'  " +
				" THEN '50万-30万（含）'  " +
				" WHEN '06'  " +
				" THEN '100万-50万（含）'  " +
				" WHEN '07'  " +
				" THEN '300万-100万（含）'  " +
				" WHEN '08'  " +
				" THEN '500万-300万（含）'  " +
				" WHEN '09'  " +
				" THEN '800万-500万（含）'  " +
				" WHEN '10'  " +
				" THEN '1000万-800万（含）'  " +
				" WHEN '11'  " +
				" THEN '3000万-1000万（含）'  " +
				" WHEN '12'  " +
				" THEN '5000万-3000万（含）'  " +
				" WHEN '13'  " +
				" THEN '8000万-5000万（含）'  " +
				" WHEN '14'  " +
				" THEN '1亿-8000万（含）'  " +
				" WHEN '15'  " +
				" THEN '3亿-1亿(含)'  " +
				" WHEN '16'  " +
				" THEN '5亿-3亿(含)'  " +
				" WHEN '17'  " +
				" THEN '10亿-5亿（含）'  " +
				" WHEN '18'  " +
				" THEN '10亿（含）以上'  " +
				" ELSE t.AUM_TYPE  " +
				" END ) CSTAUMTYPEDECLARE,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_CUST IS NULL  " +
				" THEN 0  " +
				" ELSE t.DAY_SUM_CUST  " +
				" END DAY_SUM_CUST,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_CUST IS NULL  " +
				" OR t2.SUM_DAY_SUM_CUST = 0  " +
				" OR t2.SUM_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_CUST*100.000/t2.SUM_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE1,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_CUST IS NULL  " +
				" OR t.LAST_DAY_SUM_CUST = 0  " +
				" OR t.LAST_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_CUST*100.000/t.LAST_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE2,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_CUST IS NULL  " +
				" OR t.FIRST_DAY_SUM_CUST = 0  " +
				" OR t.FIRST_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_CUST*100.000/t.FIRST_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE3,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_AUM IS NULL  " +
				" THEN 0  " +
				" ELSE t.DAY_SUM_AUM  " +
				" END DAY_SUM_AUM,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_AUM IS NULL  " +
				" OR t2.sum_DAY_SUM_AUM =0  " +
				" OR t2.sum_DAY_SUM_AUM IS NULL  " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_AUM*100.000/t2.sum_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE1,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_AUM IS NULL  " +
				" OR t.LAST_DAY_SUM_AUM = 0  " +
				" OR t.LAST_DAY_SUM_AUM IS NULL  " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_AUM*100.000/t.LAST_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE2,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_AUM IS NULL  " +
				" OR t.FIRST_DAY_SUM_AUM = 0  " +
				" OR t.FIRST_DAY_SUM_AUM IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_AUM*100.000/t.FIRST_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE3/*,  " +
				" CASE  " +
				" WHEN t2.SUM_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE t2.SUM_DAY_SUM_CUST  " +
				" END sum_cust,  " +
				" t2.sum_LAST_DAY_SUM_CUST,  " +
				" t2.sum_FIRST_DAY_SUM_CUST,  " +
				" CASE  " +
				" WHEN t2.sum_DAY_SUM_AUM IS NULL " +
				" THEN 0  " +
				" ELSE t2.sum_DAY_SUM_AUM  " +
				" END sum_aum,  " +
				" t2.sum_LAST_DAY_SUM_AUM,  " +
				" t2.sum_FIRST_DAY_SUM_AUM*/  " +
				" FROM  " +
				" (  " +
				" SELECT  " +
				" CREATE_BRANCH_NO,  " +
				" BRCNAME,  " +
				" DAY_SUM_CUST,  " +
				" AUM_TYPE,  " +
				" LAST_DAY_SUM_CUST,  " +
				" FIRST_DAY_SUM_CUST,  " +
				" DAY_SUM_AUM,  " +
				" LAST_DAY_SUM_AUM,  " +
				" FIRST_DAY_SUM_AUM,  " +
				" RPT_DATE  " +
				" FROM  " +
				" RPT_CORP_INFO_DETAIL,  " +
				" PUBBRANCHINFO  " +
				" WHERE  " +
				" CREATE_BRANCH_NO = BRCCODE  " ;
//				" AND RPT_DATE = '2013-07-25'  " +
//				" --AND CREATE_BRANCH_NO = '0100'  " +
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("rptDate")))){
			  temp = temp +" AND RPT_DATE = '"+String.valueOf(queryMap.get("rptDate"))+"' ";
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("createBranchNo")))){
				String createBranchNo = "'" + String.valueOf(queryMap.get("createBranchNo")).replaceAll("\\;", "','") + "'";
				temp = temp +" AND CREATE_BRANCH_NO IN ("+createBranchNo+") ";
			}
			temp = temp + 
				" ) t ,  " +
				" (  " +
				" SELECT  " +
				" SUM(DAY_SUM_CUST) AS SUM_DAY_SUM_CUST, " +
				" SUM(LAST_DAY_SUM_CUST) AS sum_LAST_DAY_SUM_CUST, " +
				" SUM(FIRST_DAY_SUM_CUST) AS sum_FIRST_DAY_SUM_CUST, " +
				" SUM(DAY_SUM_AUM) AS sum_DAY_SUM_AUM, " +
				" SUM(LAST_DAY_SUM_AUM) AS sum_LAST_DAY_SUM_AUM, " +
				" SUM(FIRST_DAY_SUM_AUM) AS sum_FIRST_DAY_SUM_AUM " +
				" FROM  " +
				" RPT_CORP_INFO_DETAIL  " +
				" WHERE  " +
				" 1=1  " ;
//				" AND RPT_DATE = '2013-07-25'  " +
//				" -- AND CREATE_BRANCH_NO = '0100' " +
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("rptDate")))){
			  temp = temp +" AND RPT_DATE = '"+String.valueOf(queryMap.get("rptDate"))+"' ";
			}
			if(!ResultUtil.isEmpty(String.valueOf(queryMap.get("createBranchNo")))){
				String createBranchNo = "'" + String.valueOf(queryMap.get("createBranchNo")).replaceAll("\\;", "','") + "'";
				temp = temp +" AND CREATE_BRANCH_NO IN ("+createBranchNo+") ";
			}
			temp = temp + 
				" ) t2  " +
				" order by t. CREATE_BRANCH_NO, t.AUM_TYPE " +
				" )  " ;
		
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(temp);
		List<Map<String, String>> returnListMaps = new ArrayList<Map<String, String>>();
		try{
			returnListMaps = resultUtil.listObjectsToListMaps2(list, temp);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return returnListMaps;
	}
	
	public List<Object[]> queryRptRptCorpInfoDetail(String rptDate, String createBranchNo){
		//StringBuffer sql = new StringBuffer();
		
		String temp = 
				" (  " +
				" SELECT  " +
				" '' as CREATE_BRANCH_NO,  " +
				" '' as BRCNAME,  " +
				" '总计：' as CSTAUMTYPEDECLARE,  " +
				" CASE  " +
				" WHEN t.day_sum_cust1 IS NULL  " +
				" THEN 0  " +
				" ELSE t.day_sum_cust1  " +
				" END SUM_DAY_SUM_CUST ,  " +
				" CASE  " +
				" WHEN t.day_sum_cust1 IS NULL  " +
				" OR t2.SUM_DAY_SUM_CUST = 0  " +
				" OR t2.SUM_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_cust1*100.000/t2.SUM_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE1,  " +
				" CASE  " +
				" WHEN t.day_sum_cust1 IS NULL  " +
				" OR t2.sum_LAST_DAY_SUM_CUST = 0 " +
				" OR t2.sum_LAST_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_cust1*100.000/t2.sum_LAST_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE2,  " +
				" CASE  " +
				" WHEN t.day_sum_cust1 IS NULL  " +
				" OR t2.sum_FIRST_DAY_SUM_CUST = 0 " +
				" OR t2.sum_FIRST_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_cust1*100.000/t2.sum_FIRST_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE3,  " +
				" CASE  " +
				" WHEN t.day_sum_aum1 IS NULL  " +
				" THEN 0  " +
				" ELSE t.day_sum_aum1  " +
				" END DAY_SUM_AUM,  " +
				" CASE  " +
				" WHEN t.day_sum_aum1 IS NULL  " +
				" OR t2.sum_DAY_SUM_AUM =0  " +
				" OR t2.sum_DAY_SUM_AUM IS NULL  " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_aum1*100.000/t2.sum_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE1,  " +
				" CASE  " +
				" WHEN t.day_sum_aum1 IS NULL  " +
				" OR t2.sum_LAST_DAY_SUM_AUM = 0 " +
				" OR t2.sum_LAST_DAY_SUM_AUM IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_aum1*100.000/t2.sum_LAST_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE2,  " +
				" CASE  " +
				" WHEN t.day_sum_aum1 IS NULL  " +
				" OR t2.sum_FIRST_DAY_SUM_AUM = 0 " +
				" OR t2.sum_FIRST_DAY_SUM_AUM IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.day_sum_aum1*100.000/t2.sum_FIRST_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE3/*,  " +
				" CASE  " +
				" WHEN t2.SUM_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE t2.SUM_DAY_SUM_CUST  " +
				" END sum_cust,  " +
				" t2.sum_LAST_DAY_SUM_CUST,  " +
				" t2.sum_FIRST_DAY_SUM_CUST,  " +
				" CASE  " +
				" WHEN t2.sum_DAY_SUM_AUM IS NULL " +
				" THEN 0  " +
				" ELSE t2.sum_DAY_SUM_AUM  " +
				" END sum_aum,  " +
				" t2.sum_LAST_DAY_SUM_AUM,  " +
				" t2.sum_FIRST_DAY_SUM_AUM*/  " +
				" FROM  " +
				" (  " +
				" SELECT  " +
				" sum(DAY_SUM_CUST) as day_sum_cust1, " +
				" sum(DAY_SUM_AUM) as day_sum_aum1 " +
				" FROM  " +
				" RPT_CORP_INFO_DETAIL  " +
				" WHERE  " +
				" 1=1  " ;
//				" AND RPT_DATE = '2013-07-25'  " +
//				" --AND CREATE_BRANCH_NO = '0100' " +
			if(!ResultUtil.isEmpty(rptDate)){
				temp = temp +" AND RPT_DATE = '"+rptDate+"' ";
			}
			if(!ResultUtil.isEmpty(createBranchNo)){
				createBranchNo = "'" + createBranchNo.replaceAll("\\;", "','") + "'";
				temp = temp +" AND CREATE_BRANCH_NO IN ("+createBranchNo+") ";
			}
			temp = temp + 
				" ) t ,  " +
				" (  " +
				" SELECT  " +
				" SUM(DAY_SUM_CUST) AS SUM_DAY_SUM_CUST, " +
				" SUM(LAST_DAY_SUM_CUST) AS sum_LAST_DAY_SUM_CUST, " +
				" SUM(FIRST_DAY_SUM_CUST) AS sum_FIRST_DAY_SUM_CUST, " +
				" SUM(DAY_SUM_AUM) AS sum_DAY_SUM_AUM, " +
				" SUM(LAST_DAY_SUM_AUM) AS sum_LAST_DAY_SUM_AUM, " +
				" SUM(FIRST_DAY_SUM_AUM) AS sum_FIRST_DAY_SUM_AUM " +
				" FROM  " +
				" RPT_CORP_INFO_DETAIL  " +
				" WHERE  " +
				" 1=1  " ;
//				" AND RPT_DATE = '2013-07-25'  " +
//				" --AND CREATE_BRANCH_NO = '0100'  " +
			if(!ResultUtil.isEmpty(rptDate)){
				temp = temp +" AND RPT_DATE = '"+rptDate+"' ";
			}
			if(!ResultUtil.isEmpty(createBranchNo)){
				createBranchNo = "'" + createBranchNo.replaceAll("\\;", "','") + "'";
				temp = temp +" AND CREATE_BRANCH_NO IN ("+createBranchNo+") ";
			}
			temp = temp + 
				"  " +
				" ) t2  " +
				" )  " +
				"  " +
				" UNION ALL  " +
				"  " +
				" (  " +
				" SELECT  " +
				" t. CREATE_BRANCH_NO,  " +
				" t.BRCNAME,  " +
				" (  " +
				" CASE t.AUM_TYPE  " +
				" WHEN '01'  " +
				" THEN '1万以下'  " +
				" WHEN '02'  " +
				" THEN '5万-1万（含）'  " +
				" WHEN '03'  " +
				" THEN '10万-5万（含）'  " +
				" WHEN '04'  " +
				" THEN '30万-10万（含）'  " +
				" WHEN '05'  " +
				" THEN '50万-30万（含）'  " +
				" WHEN '06'  " +
				" THEN '100万-50万（含）'  " +
				" WHEN '07'  " +
				" THEN '300万-100万（含）'  " +
				" WHEN '08'  " +
				" THEN '500万-300万（含）'  " +
				" WHEN '09'  " +
				" THEN '800万-500万（含）'  " +
				" WHEN '10'  " +
				" THEN '1000万-800万（含）'  " +
				" WHEN '11'  " +
				" THEN '3000万-1000万（含）'  " +
				" WHEN '12'  " +
				" THEN '5000万-3000万（含）'  " +
				" WHEN '13'  " +
				" THEN '8000万-5000万（含）'  " +
				" WHEN '14'  " +
				" THEN '1亿-8000万（含）'  " +
				" WHEN '15'  " +
				" THEN '3亿-1亿(含)'  " +
				" WHEN '16'  " +
				" THEN '5亿-3亿(含)'  " +
				" WHEN '17'  " +
				" THEN '10亿-5亿（含）'  " +
				" WHEN '18'  " +
				" THEN '10亿（含）以上'  " +
				" ELSE t.AUM_TYPE  " +
				" END ) CSTAUMTYPEDECLARE,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_CUST IS NULL  " +
				" THEN 0  " +
				" ELSE t.DAY_SUM_CUST  " +
				" END DAY_SUM_CUST,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_CUST IS NULL  " +
				" OR t2.SUM_DAY_SUM_CUST = 0  " +
				" OR t2.SUM_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_CUST*100.000/t2.SUM_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE1,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_CUST IS NULL  " +
				" OR t.LAST_DAY_SUM_CUST = 0  " +
				" OR t.LAST_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_CUST*100.000/t.LAST_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE2,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_CUST IS NULL  " +
				" OR t.FIRST_DAY_SUM_CUST = 0  " +
				" OR t.FIRST_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_CUST*100.000/t.FIRST_DAY_SUM_CUST, 2) AS DECIMAL(18, 2 )) " +
				" END CSTSCALE3,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_AUM IS NULL  " +
				" THEN 0  " +
				" ELSE t.DAY_SUM_AUM  " +
				" END DAY_SUM_AUM,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_AUM IS NULL  " +
				" OR t2.sum_DAY_SUM_AUM =0  " +
				" OR t2.sum_DAY_SUM_AUM IS NULL  " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_AUM*100.000/t2.sum_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE1,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_AUM IS NULL  " +
				" OR t.LAST_DAY_SUM_AUM = 0  " +
				" OR t.LAST_DAY_SUM_AUM IS NULL  " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_AUM*100.000/t.LAST_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE2,  " +
				" CASE  " +
				" WHEN t.DAY_SUM_AUM IS NULL  " +
				" OR t.FIRST_DAY_SUM_AUM = 0  " +
				" OR t.FIRST_DAY_SUM_AUM IS NULL " +
				" THEN 0  " +
				" ELSE CAST(ROUND(t.DAY_SUM_AUM*100.000/t.FIRST_DAY_SUM_AUM, 2) AS DECIMAL(18, 2)) " +
				" END AUMSCALE3/*,  " +
				" CASE  " +
				" WHEN t2.SUM_DAY_SUM_CUST IS NULL " +
				" THEN 0  " +
				" ELSE t2.SUM_DAY_SUM_CUST  " +
				" END sum_cust,  " +
				" t2.sum_LAST_DAY_SUM_CUST,  " +
				" t2.sum_FIRST_DAY_SUM_CUST,  " +
				" CASE  " +
				" WHEN t2.sum_DAY_SUM_AUM IS NULL " +
				" THEN 0  " +
				" ELSE t2.sum_DAY_SUM_AUM  " +
				" END sum_aum,  " +
				" t2.sum_LAST_DAY_SUM_AUM,  " +
				" t2.sum_FIRST_DAY_SUM_AUM*/  " +
				" FROM  " +
				" (  " +
				" SELECT  " +
				" CREATE_BRANCH_NO,  " +
				" BRCNAME,  " +
				" DAY_SUM_CUST,  " +
				" AUM_TYPE,  " +
				" LAST_DAY_SUM_CUST,  " +
				" FIRST_DAY_SUM_CUST,  " +
				" DAY_SUM_AUM,  " +
				" LAST_DAY_SUM_AUM,  " +
				" FIRST_DAY_SUM_AUM,  " +
				" RPT_DATE  " +
				" FROM  " +
				" RPT_CORP_INFO_DETAIL,  " +
				" PUBBRANCHINFO  " +
				" WHERE  " +
				" CREATE_BRANCH_NO = BRCCODE  " ;
//				" AND RPT_DATE = '2013-07-25'  " +
//				" --AND CREATE_BRANCH_NO = '0100'  " +
			if(!ResultUtil.isEmpty(rptDate)){
				temp = temp +" AND RPT_DATE = '"+rptDate+"' ";
			}
			if(!ResultUtil.isEmpty(createBranchNo)){
				createBranchNo = "'" + createBranchNo.replaceAll("\\;", "','") + "'";
				temp = temp +" AND CREATE_BRANCH_NO IN ("+createBranchNo+") ";
			}
			temp = temp + 
				" ) t ,  " +
				" (  " +
				" SELECT  " +
				" SUM(DAY_SUM_CUST) AS SUM_DAY_SUM_CUST, " +
				" SUM(LAST_DAY_SUM_CUST) AS sum_LAST_DAY_SUM_CUST, " +
				" SUM(FIRST_DAY_SUM_CUST) AS sum_FIRST_DAY_SUM_CUST, " +
				" SUM(DAY_SUM_AUM) AS sum_DAY_SUM_AUM, " +
				" SUM(LAST_DAY_SUM_AUM) AS sum_LAST_DAY_SUM_AUM, " +
				" SUM(FIRST_DAY_SUM_AUM) AS sum_FIRST_DAY_SUM_AUM " +
				" FROM  " +
				" RPT_CORP_INFO_DETAIL  " +
				" WHERE  " +
				" 1=1  " ;
//				" AND RPT_DATE = '2013-07-25'  " +
//				" -- AND CREATE_BRANCH_NO = '0100' " +
			if(!ResultUtil.isEmpty(rptDate)){
				temp = temp +" AND RPT_DATE = '"+rptDate+"' ";
			}
			if(!ResultUtil.isEmpty(createBranchNo)){
				createBranchNo = "'" + createBranchNo.replaceAll("\\;", "','") + "'";
				temp = temp +" AND CREATE_BRANCH_NO IN ("+createBranchNo+") ";
			}
			temp = temp + 
				" ) t2  " +
				" order by t. CREATE_BRANCH_NO, t.AUM_TYPE " +
				" )  " ;
		
		List<Object[]> list = this.baseDAO.findByNativeSQLWithIndexParam(temp);
		return list;
	}
	
	public String export(String rptDate, String createBranchNo) {
		List<Object[]> list = queryRptRptCorpInfoDetail(rptDate, createBranchNo);
		if(list != null && list.size() > 0){
			ExcelReadWriteUtil excelReadWriteUtil = new ExcelReadWriteUtil();
			String templateName = GlobalConstants.EXCEL_TEMPLATE_ORGCSTAUMDISTRIBUTION;
			String reportName = GlobalConstants.EXCEL_REPORT_ORGCSTAUMDISTRIBUTION_CN;
			String templatePath = excelReadWriteUtil.getFilePathName(templateName, GlobalConstants.EXCEL_TEMPLATE_IMPORT_PATH);
			int sheetIndex = 0, writeBeginRowIndex = 3;  // 设置写入sheetIndex 设置 写入开始行
			Workbook workbook = excelReadWriteUtil.getWorkbookByListObjectReport(sheetIndex, writeBeginRowIndex, list, templatePath, rptDate);
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