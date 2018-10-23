package com.yuchengtech.bcrm.finService.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 财务健康诊断Action
 * @author Fan zheming
 * 2014-7-16
 */
@Action("/financialDiagnosis")
public class FinancialDiagnosisAction extends CommonAction {

	@Autowired		//注入..
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Override
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		StringBuffer sb=new StringBuffer("");
		sb.append(" SELECT B2.* FROM OCRM_F_CI_BELONG_CUSTMGR B1," +
				"(SELECT O1.CUST_ID,O1.CORE_NO SOURCE_CUST_ID,O1.CUST_NAME,t1.BAL,O4.OTHER_BANK,O5.OTHER_TYPE,VT.ID FROM ACRM_F_CI_CUSTOMER O1 " +
				"LEFT  JOIN (SELECT A1.CUST_ID, SUM(A1.AMOUNT_VALUE) OTHER_BANK FROM OCRM_F_FIN_BAL_SHEET A1 WHERE BELONG_TYPE = '1' AND ASSET_DEBT_TYPE = '1' GROUP BY A1.CUST_ID) O4 ON O1.CUST_ID = O4.CUST_ID " +
				"LEFT  JOIN (SELECT A2.CUST_ID, SUM(A2.AMOUNT_VALUE) OTHER_TYPE FROM OCRM_F_FIN_BAL_SHEET A2 WHERE BELONG_TYPE = '2' AND ASSET_DEBT_TYPE = '1' GROUP BY A2.CUST_ID) O5 ON O1.CUST_ID = O5.CUST_ID " +
				"left  join (SELECT cust_ID, sum(MS_AC_BAL) as BAL FROM ACRM_F_DP_SAVE_INFO  group by cust_ID) t1 on t1.cust_ID = O1.Cust_Id " +
				"LEFT  JOIN  OCRM_F_FIN_ANA_ADVISE_VISTA VT ON T1.CUST_ID = VT.CUST_ID WHERE O1.CUST_TYPE = '2') B2 " +
				"WHERE B1.CUST_ID = B2.CUST_ID ");
//		sb.append(" SELECT B2.*");
//		sb.append(" FROM OCRM_F_CI_BELONG_CUSTMGR B1,");
//		sb.append(" (SELECT O1.CUST_ID,O1.CORE_NO SOURCE_CUST_ID,O1.CUST_NAME,T1.BAL,O4.OTHER_BANK,O5.OTHER_TYPE");
//		sb.append(" FROM ACRM_F_CI_CUSTOMER O1");
//		sb.append(" LEFT OUTER JOIN");
//		sb.append(" (SELECT A1.CUST_ID, SUM(A1.AMOUNT_VALUE) OTHER_BANK FROM OCRM_F_FIN_BAL_SHEET A1 WHERE BELONG_TYPE = '1' AND ASSET_DEBT_TYPE = '1' GROUP BY A1.CUST_ID) O4");
//		sb.append(" ON O1.CUST_ID = O4.CUST_ID AND O1.CUST_TYPE='1' ");
//		sb.append(" LEFT OUTER JOIN");
//		sb.append(" (SELECT A2.CUST_ID, SUM(A2.AMOUNT_VALUE) OTHER_TYPE FROM OCRM_F_FIN_BAL_SHEET A2 WHERE BELONG_TYPE = '2' AND ASSET_DEBT_TYPE = '1' GROUP BY A2.CUST_ID) O5");
//		sb.append(" ON O1.CUST_ID = O5.CUST_ID");
//		sb.append(" LEFT OUTER JOIN");
//		sb.append(" (SELECT CUST_NO, SUM(MS_AV_BAL) AS BAL");
//		sb.append(" FROM ACRM_F_DP_SAVE_INFO GROUP BY CUST_NO) T1 ON T1.CUST_NO = 01.CUST_ID");
//		sb.append(" WHERE O1.CUST_TYPE = '1' ");
//		sb.append(" ) B2"); 
//		sb.append(" WHERE B1.CUST_ID=B2.CUST_ID");
		
		//权限控制
		if (request.getParameter("start") != null)
			start = new Integer(request.getParameter("start")).intValue();
		if (request.getParameter("limit") != null)
			limit = new Integer(request.getParameter("limit")).intValue();
		
		for(String key : this.getJson().keySet()){
			if(null!=this.getJson().get(key) && !this.getJson().get(key).equals("")){
				if (key.equals("CUST_ID")) {
//					System.out.println(this.getJson().get(key));
					sb.append(" AND B2.CUST_ID LIKE '%"
							+ this.getJson().get(key) + "%'");
				}
				if (key.equals("CUST_NAME")) {
					sb.append(" AND B2.CUST_NAME LIKE '%"
							+ this.getJson().get(key) + "%'");
				}
				if (key.equals("BAL")) {
					sb.append(" AND B2.BAL LIKE '%"
							+ this.getJson().get(key) + "%'");
				}
				if (key.equals("OTHER_BANK")) {
					sb.append(" AND B2.OTHER_BANK LIKE '%"
							+ this.getJson().get(key) + "%'");
				}
				if (key.equals("OTHER_TYPE")) {
					sb.append(" AND B2.OTHER_TYPE LIKE '%"
							+ this.getJson().get(key) + "%'");
				}
			}
		}
		SQL = sb.toString();	
		datasource = ds;
		setPrimaryKey("B2.CUST_ID");
//		configCondition("CUST_NAME","like","CUST_NAME",DataType.String);
//		configCondition("BAL","like","BAL",DataType.Number);
//		configCondition("OTHER_BANK","like","OHTER_BANK",DataType.Number);
//		configCondition("OTHER_TYPE","like","OHTER_TYPE",DataType.Number);
	}
}