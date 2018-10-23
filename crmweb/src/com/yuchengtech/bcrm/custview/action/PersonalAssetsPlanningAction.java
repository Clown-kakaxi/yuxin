package com.yuchengtech.bcrm.custview.action;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.LogService;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.exception.BizException;
import org.apache.poi.util.SystemOutLogger;
import org.apache.struts2.convention.annotation.Action;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tracy on 16/2/18.
 */
@SuppressWarnings("serial")
@Action("/personalassetsplanning")
public class PersonalAssetsPlanningAction extends CommonAction {

	private DataSource ds = LogService.dsOracle;

	/**
	 * @param custId
	 *            客户姓名,客户经理,性别
	 */
	public List<HashMap<String, Object>> searchCustNameAndMgrName(String custId) {
		StringBuffer sb = new StringBuffer(
				"select c.cust_name,m.mgr_name,p.gender,c.create_date from ACRM_F_CI_CUSTOMER c "
						+ "left join OCRM_F_CI_BELONG_CUSTMGR m on c.cust_id = m.cust_id "
						+ "left join ACRM_F_CI_PERSON p on p.cust_id = m.cust_id "
						+ "where c.cust_id = '" + custId + "'");

		List<HashMap<String, Object>> searchCustNameAndMgrName = null;
		QueryHelper query = null;
		try {
			query = new QueryHelper(sb.toString(), ds.getConnection());
			searchCustNameAndMgrName = (List<HashMap<String, Object>>) query
					.getJSON().get("data");

			System.out.print(searchCustNameAndMgrName);

			// 客户姓名
			String custName = null;
			// 客户经理
			String mgrName = null;
			// 客户性别
			String gender = null;
			// 创建日期
			String creatDate = null;

			for (HashMap<String, Object> map : searchCustNameAndMgrName) {
				custName = (String) map.get("CUST_NAME");
				mgrName = (String) map.get("MGR_NAME");
				gender = (String) map.get("GENDER");
				creatDate = (String) map.get("CREATE_DATE");
			}

			return searchCustNameAndMgrName;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1002", e.getMessage());
		}
	}

	/**
	 * @param custId
	 *            活期存款,定期存款,月得盈,富股利,保险,安富尊荣; 信用贷款,个人消费性贷款,个人经营性贷款,按揭贷款,内保贷款?
	 */

	public List<HashMap<String, Object>> searchAssetAndLoan(String custId) {

		StringBuffer sb = new StringBuffer(
				"SELECT T7.CUST_ID,T7.HQCKYE_AMT,T7.TZCK_AMT,T7.DQCKYE_AMT,T7.YDY_AMT,T7.HDL_AMT,T7.FGL_AMT,"
						+ "       T7.BX_AMT,T7.AFZR_AMT,T7.DD_AMT,T7.FHY_AMT,T6.XYDK_AMT,T6.GRXFDK_AMT,T6.GRAJDK_AMT,T6.GRZYDK_AMT,T6.GRJYXDK_AMT,T5.NBWD_AMT "
						+ " FROM (SELECT T2.CUST_ID,"
						+ "      SUM(CASE WHEN T3.PRODUCT_ID IN ('100023') THEN NVL(T3.BAL_RMB, 0) ELSE 0 END ) HQCKYE_AMT,                                                                                                                            "
						+ "      SUM(CASE WHEN TRIM(T3.PRODUCT_ID) IN ('100099') THEN NVL(BAL_RMB, 0) ELSE 0 END ) TZCK_AMT,                                                                                                                           "
						+ "      SUM(CASE WHEN T3.PRODUCT_ID IN ('100024') AND  TRIM(T3.acct_status) = 'A' THEN NVL(T3.BAL_RMB, 0) ELSE 0 END )  DQCKYE_AMT,                                                                                           "
						+ "      SUM(CASE WHEN T3.PRODUCT_ID IN ('100127') THEN NVL(T3.BAL_RMB, 0) ELSE 0 END ) YDY_AMT,                                                                                                                               "
						+ "      SUM(CASE WHEN T3.PRODUCT_ID IN ('100128') THEN NVL(T3.BAL_RMB, 0) ELSE 0 END ) HDL_AMT,                                                                                                                               "
						+ "      SUM(CASE WHEN T3.PRODUCT_ID IN ('100129') THEN NVL(T3.BAL_RMB, 0) ELSE 0 END ) FGL_AMT,                                                                                                                               "
						+ "      SUM(CASE WHEN T3.PRODUCT_ID IN ('100143') THEN NVL(T3.BAL_RMB, 0) ELSE 0 END ) BX_AMT,                                                                                                                                "
						+ "      SUM(CASE WHEN T3.PRODUCT_ID IN ('100130') THEN NVL(T3.BAL_RMB, 0) ELSE 0 END ) AFZR_AMT,                                                                                                                              "
						+ "      SUM(CASE WHEN T3.PRODUCT_ID IN ('100025') THEN NVL(T3.BAL_RMB, 0) ELSE 0 END ) DD_AMT,                                                                                                                                "
						+ "      SUM(CASE WHEN T3.PRODUCT_ID IN ('100026') THEN NVL(T3.BAL_RMB, 0) ELSE 0 END ) FHY_AMT                                                                                                                                "
						+ "  FROM ACRM_F_CI_CUSTOMER T2                                                                                                                                                                                                "
						+ "  LEFT JOIN ACRM_F_DP_SAVE_INFO T3 ON T2.CUST_ID = T3.CUST_ID                                                                                                                                                               "
						+ "  WHERE T2.CUST_ID = '"
						+ custId
						+ "' GROUP BY T2.CUST_ID ) T7                                                                                                                                                                "
						+ "  LEFT JOIN  ( SELECT T2.CUST_ID,                                                                                                                                                                                           "
						+ "                      0 XYDK_AMT,                                                                                                                                                                                           "
						+ "                      SUM(CASE WHEN TRIM(T1.PRODUCT_ID) IN ('100002') THEN NVL(t1.bal,0) ELSE 0 END )  GRXFDK_AMT,                 "
						+ "                      SUM(CASE WHEN TRIM(T1.PRODUCT_ID) IN ('100001') THEN NVL(t1.bal,0) ELSE 0 END ) GRAJDK_AMT,"
						+ "                      0 GRZYDK_AMT,                                                                                                                                                                                         "
						+ "                       SUM(CASE WHEN TRIM(T1.PRODUCT_ID) IN ('100003') THEN NVL(t1.bal,0) ELSE  0 END ) GRJYXDK_AMT                 "
						+ "                FROM ACRM_F_CI_CUSTOMER T2                                                                                                                                                                                  "
						+ "                LEFT JOIN ACRM_F_CI_ASSET_BUSI_PROTO T1 ON T2.CUST_ID = T1.CUST_ID                                                                                                                                          "
						+ "                 WHERE T2.CUST_ID = '"
						+ custId
						+ "' GROUP BY T2.CUST_ID                                                                                                                                                      "
						+ "              ) T6 ON T7.CUST_ID = T6.CUST_ID                                                                                                                                                                               "
						+ "  LEFT JOIN ( SELECT CUST_ID,SUM(NVL(T4.OSAMT_RMB,0))  NBWD_AMT                                                                                                                                                         "
						+ "                FROM ACRM_F_CI_CUSTOMER T                                                                                                                                                                                   "
						+ "                LEFT JOIN ACRM_F_CI_PRO_AMOUNT T4 ON T.CUST_ID = T4.CUSTCOD AND T4.PRODUCT = 'BG'                                                                                                                           "
						+ "                WHERE T.CUST_ID = '"
						+ custId
						+ "' GROUP BY CUST_ID                                                                                                                                                           "
						+ "             ) T5  ON T7.CUST_ID = T5.CUST_ID                                                                                                                                                                               ");

		List<HashMap<String, Object>> searchAssetAndLoan = null;
		QueryHelper query1 = null;

		try {
			query1 = new QueryHelper(sb.toString(), ds.getConnection());
			searchAssetAndLoan = (List<HashMap<String, Object>>) query1
					.getJSON().get("data");

			System.out.print(searchAssetAndLoan);

			// 活期存款
			double HQCKYE_AMT = 0;
			// 通知存款
			double TZCK_AMT = 0;
			// 定期存款
			double DQCKYE_AMT = 0;
			// 月得盈
			double YDY_AMT = 0;
			// 汇得利
			double HDL_AMT = 0;
			// 福股利
			double FGL_AMT = 0;
			// 保险
			double BX_AMT = 0;
			// 安富尊荣
			double AFZR_AMT = 0;
			// 双元货币
			double DD_AMT = 0;
			// 富汇盈
			double FHY_AMT = 0;
			// 信用贷款
			double XYDK_AMT = 0;
			// 个人房屋抵押消费贷款
			double GRXFDK_AMT = 0;
			// 个人按揭贷款
			double GRAJDK_AMT = 0;
			// 个人质押贷款
			double GRZYDK_AMT = 0;
			// 个人经营性贷款
			double GRJYXDK_AMT = 0;
			// 内保外贷
			double NBWD_AMT = 0;
			// 资产总计
			double assetTotal = 0;
			// 贷款总计
			double loanTotal = 0;

			for (HashMap<String, Object> map : searchAssetAndLoan) {
				HQCKYE_AMT = Double.parseDouble((String) map.get("HQCKYE_AMT"));
				TZCK_AMT = Double.parseDouble((String) map.get("TZCK_AMT"));
				DQCKYE_AMT = Double.parseDouble((String) map.get("DQCKYE_AMT"));

				YDY_AMT = Double.parseDouble((String) map.get("YDY_AMT"));
				FGL_AMT = Double.parseDouble((String) map.get("FGL_AMT"));
				HDL_AMT = Double.parseDouble((String) map.get("HDL_AMT"));

				BX_AMT = Double.parseDouble((String) map.get("BX_AMT"));
				AFZR_AMT = Double.parseDouble((String) map.get("AFZR_AMT"));
				DD_AMT = Double.parseDouble((String) map.get("DD_AMT"));
				FHY_AMT = Double.parseDouble((String) map.get("FHY_AMT"));

				XYDK_AMT = Double.parseDouble((String) map.get("XYDK_AMT"));

				GRXFDK_AMT = Double.parseDouble((String) map.get("GRXFDK_AMT"));
				GRAJDK_AMT = Double.parseDouble((String) map.get("GRAJDK_AMT"));
				GRZYDK_AMT = Double.parseDouble((String) map.get("GRZYDK_AMT"));

				GRJYXDK_AMT = Double.parseDouble((String) map
						.get("GRJYXDK_AMT"));
				NBWD_AMT = Double.parseDouble((String) map.get("NBWD_AMT"));

			}

			return searchAssetAndLoan;

		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1002", e.getMessage());
		}
	}

	/**
	 * @param x
	 * @param total
	 *            资产占比; 贷款占比
	 */
	public String getPercent(double x, double total) {

		String result = "";
		double x_double = x * 1.0;

		double tempresult = x / total;
		DecimalFormat df1 = new DecimalFormat("0.00%");
		result = df1.format(tempresult);

		if (total == 0.0 || total == 0) {
			result = "0.00%";
		}
		return result;
	}

	public String getMoney(double money) {
		NumberFormat nf = new DecimalFormat("#,###.####");
		String moneyStyle = nf.format(money);
		return moneyStyle;
	}

	/**
	 * @param financelId
	 *            产品编号 理财产品推荐
	 */

	public List<HashMap<String, Object>> financialProducts(String financelId) {

		StringBuffer sb = new StringBuffer(
				"select 113 PARENT_ID, wm_concat(t.prod_name) PRODUCT_NAME from OCRM_F_PD_PROD_INFO t where t.catl_code in ('113', '114', '112', '111', '117','119')  and t.product_id in"
						+ " (SELECT TRIM(REGEXP_SUBSTR(t2.RECOMMENT_PRO, '[^,]+', 1, LEVEL, 'i')) AS product_id"
						+ " FROM (SELECT CUST_ID,CASE WHEN T1.MANAGER_PRO IS NOT NULL THEN T1.MANAGER_PRO WHEN T1.MANAGER_PRO IS NULL THEN  T1.SYSTEM_PRO END RECOMMENT_PRO"
						+ "  FROM ACRM_F_CI_PER_FINANCE T1"
						+ "  WHERE FINANCE_ID = '"
						+ financelId
						+ "') T2"
						+ " CONNECT BY LEVEL <="
						+ "     LENGTH(REGEXP_REPLACE(t2.RECOMMENT_PRO, '[^,]+', '')) + 1)"
						+ " group by 113"
						+ " union"
						+ " select t1.catl_parent PARENT_ID, wm_concat(t.prod_name) PRODUCT_NAME from OCRM_F_PD_PROD_INFO t left join OCRM_F_PD_PROD_CATL t1 on t.catl_code = t1.catl_code where t1.catl_parent = '115'  and t.product_id in"
						+ "   (SELECT TRIM(REGEXP_SUBSTR(t2.RECOMMENT_PRO, '[^,]+', 1, LEVEL, 'i')) AS product_id"
						+ " FROM (SELECT CUST_ID,CASE WHEN T1.MANAGER_PRO IS NOT NULL THEN T1.MANAGER_PRO WHEN T1.MANAGER_PRO IS NULL THEN  T1.SYSTEM_PRO END RECOMMENT_PRO"
						+ "     FROM ACRM_F_CI_PER_FINANCE T1"
						+ "  WHERE FINANCE_ID = '"
						+ financelId
						+ "') T2"
						+ "    CONNECT BY LEVEL <="
						+ "    LENGTH(REGEXP_REPLACE(t2.RECOMMENT_PRO, '[^,]+', '')) + 1)"
						+ " group by t1.catl_parent"
						+ " union"
						+ " select 100 PARENT_ID, wm_concat(t.prod_name) PRODUCT_NAME from OCRM_F_PD_PROD_INFO t left join OCRM_F_PD_PROD_CATL t3 on t.catl_code = t3.catl_code"
						+ " where t3.catl_code in ('100', '107', '108') and t.product_id in"
						+ " (SELECT TRIM(REGEXP_SUBSTR(t2.RECOMMENT_PRO, '[^,]+', 1, LEVEL, 'i')) AS product_id"
						+ " FROM (SELECT CUST_ID,CASE WHEN T1.MANAGER_PRO IS NOT NULL THEN T1.MANAGER_PRO WHEN T1.MANAGER_PRO IS NULL THEN  T1.SYSTEM_PRO END RECOMMENT_PRO"
						+ "    FROM ACRM_F_CI_PER_FINANCE T1"
						+ "  WHERE FINANCE_ID = '"
						+ financelId
						+ "') T2"
						+ "  CONNECT BY LEVEL <="
						+ "            LENGTH(REGEXP_REPLACE(t2.RECOMMENT_PRO, '[^,]+', '')) + 1)"
						+ " group by 100 ");

		List<HashMap<String, Object>> financialProducts = null;
		QueryHelper query1 = null;

		try {
			query1 = new QueryHelper(sb.toString(), ds.getConnection());
			financialProducts = (List<HashMap<String, Object>>) query1
					.getJSON().get("data");

			// 产品名称
			String prodName = null;
			// 产品类型
			String prodType = null;

			for (HashMap<String, Object> map : financialProducts) {
				prodName = (String) map.get("PRODUCT_NAME");
				prodType = (String) map.get("PARENT_ID");

			}

			return financialProducts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1002", e.getMessage());
		}

	}

	public List<HashMap<String, Object>> scoreAll(String custId) {

		StringBuffer sb = new StringBuffer(
				"select t.cust_id,t.core_no,t2.score_all "
						+ " from ACRM_F_CI_customer  t"
						+ " left join (select distinct core_no,score_all from OCRM_F_CI_RISK_INFO_temp ) t2"
						+ " on t.core_no = t2.core_no where cust_id = '"
						+ custId + "'");

		List<HashMap<String, Object>> totalScore = null;
		QueryHelper query1 = null;

		try {
			query1 = new QueryHelper(sb.toString(), ds.getConnection());
			totalScore = (List<HashMap<String, Object>>) query1.getJSON().get(
					"data");

			// 产品名称
			String scoreAll = "";

			for (HashMap<String, Object> map : totalScore) {
				scoreAll = (String) map.get("SCORE_ALL");
				if (scoreAll == "null" || scoreAll.equals("null")) {
					scoreAll = "0";
				}

			}

			return totalScore;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1002", e.getMessage());
		}

	}

}