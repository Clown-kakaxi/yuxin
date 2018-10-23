package com.yuchengtech.bcrm.finService.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value = "/riskassess", results = { @Result(name = "success", type = "json") })
public class RiskAssessAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 查询当前用户所在机构的所有客户评估信息
		StringBuilder sb = new StringBuilder("");
		sb.append(" SELECT  O2.INDAGETE_QA_SCORING,O2.CUST_Q_ID,O1.CUST_ID,O1.CORE_ID SOURCE_CUST_ID,O1.CUST_ZH_NAME,O2.CUST_RISK_CHARACT,O2.EVALUATE_NAME,O2.EVALUATE_DATE,O2.LIMIT_DATE");
		sb.append(" FROM OCRM_F_CI_BELONG_CUSTMGR BELONG,OCRM_F_CI_CUST_DESC O1 ");
		sb.append(" LEFT OUTER JOIN OCRM_F_SE_CUST_RISK_INFO_LIST O2 ON O2.CUST_NO=O1.CUST_ID AND O2.HIS_FLAG='1' ");
		sb.append(" WHERE O1.CUST_ID=BELONG.CUST_ID ");
		sb.append(" AND BELONG.MGR_ID='"+auth.getUserId()+"' AND O1.CUST_TYP='1' ");
		setPrimaryKey("O1.CUST_ID");
		
		if (request.getParameter("start") != null)
			start = new Integer(request.getParameter("start")).intValue();
		if (request.getParameter("limit") != null)
			limit = new Integer(request.getParameter("limit")).intValue();

		for (String key : this.getJson().keySet()) {
			if (null != this.getJson().get(key)
					&& !this.getJson().get(key).equals("")) {
				if (key.equals("SOURCE_CUST_ID")) {
					sb.append(" AND O1.CORE_ID LIKE '%"
							+ this.getJson().get(key) + "%'");
				} else if (key.equals("CUST_ZH_NAME")) {
					sb.append(" AND O1.CUST_ZH_NAME LIKE '%"
							+ this.getJson().get(key) + "%'");
				} else if (key.equals("CERT_TYPE")) {
					sb.append(" AND O1.CERT_TYPE = '" + this.getJson().get(key)
							+ "'");
				} else if (key.equals("CERT_NUM")) {
					sb.append(" AND O1.CERT_NUM LIKE '%"
							+ this.getJson().get(key) + "%'");
				} else if (key.equals("CUST_RISK_CHARACT")) {
					sb.append(" AND O2.CUST_RISK_CHARACT = '"
							+ this.getJson().get(key) + "'");
				} else if (key.equals("EVALUATE_DATE_S")) {
					sb.append(" AND O2.EVALUATE_DATE >= TO_DATE('"
							+ this.getJson().get(key) + "','YYYY-MM-DD')");
				} else if (key.equals("EVALUATE_DATE_E")) {
					sb.append(" AND O2.EVALUATE_DATE <= TO_DATE('"
							+ this.getJson().get(key) + "','YYYY-MM-DD')");
				} else if (key.equals("IS_EVALUATE")) {
					if (this.getJson().get(key).equals("1")) {
						sb.append(" AND O1.CUST_ID IN ");
					} else if (this.getJson().get(key).equals("2")) {
						sb.append(" AND O1.CUST_ID NOT IN");
					}
					sb.append(" (SELECT CUST_NO FROM OCRM_F_SE_CUST_RISK_INFO_LIST)");
				}
			}
		}

		SQL = sb.toString();
		datasource = ds;

	}

}
