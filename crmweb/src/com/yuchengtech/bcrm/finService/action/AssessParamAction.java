package com.yuchengtech.bcrm.finService.action;

import java.math.BigDecimal;
import java.util.Date;

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
import com.yuchengtech.bcrm.finService.model.OcrmFFmRiskParam;
import com.yuchengtech.bcrm.finService.service.AssessParamService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value = "/AssessParam", results = { @Result(name = "success", type = "json") })
public class AssessParamAction extends CommonAction {
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private AssessParamService assessParamService;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	private OcrmFFmRiskParam riskParam = new OcrmFFmRiskParam();

	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		// 查询当前用户所在机构的所有客户评估信息
		StringBuilder sb = new StringBuilder("");
		sb.append(" select A.USER_NAME,O.PARAM_ID,O.PARAM_NAME,O.INIT_SCORE,O.END_SCORE,O.UPDATE_DATE");
		sb.append(" from OCRM_F_FM_RISK_PARAM O left join ADMIN_AUTH_ACCOUNT A");
		sb.append(" on ACCOUNT_NAME = UPDATER_ID");

		setPrimaryKey("O.PARAM_ID");

		if (request.getParameter("start") != null)
			start = new Integer(request.getParameter("start")).intValue();
		if (request.getParameter("limit") != null)
			limit = new Integer(request.getParameter("limit")).intValue();
		SQL = sb.toString();
		datasource = ds;

	}

	public String saveAssessParam() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (request.getParameter("paramId") != null) {

			this.riskParam.setParamId(Long.parseLong((request
					.getParameter("paramId")).toString()));
		}

		if (request.getParameter("paramName") != null)
			this.riskParam.setParamName(request.getParameter("paramName"));

		if (request.getParameter("initScore") != null)
			this.riskParam.setInitScore(new BigDecimal(request.getParameter(
					"initScore").toString()));

		if (request.getParameter("endScore") != null)
			this.riskParam.setEndScore(new BigDecimal(request.getParameter(
					"endScore").toString()));

		this.riskParam.setUpdaterId(auth.getUserId());
		this.riskParam.setUpdateDate(new Date());
		this.assessParamService.saveAssessParam(this.riskParam);
		return "sucess";
	}
}
