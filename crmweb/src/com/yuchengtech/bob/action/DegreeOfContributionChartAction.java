package com.yuchengtech.bob.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.service.CommonQueryService;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value="/degreeOfContributionChart", results={
    @Result(name="success", type="json")
})
public class DegreeOfContributionChartAction {
	
	@Autowired
    private CommonQueryService cqs;
	
	@Autowired
	private HttpServletRequest request;

	private Map<String, Object> map = new HashMap<String, Object>();
	
	private String dataXml = "";

	@SuppressWarnings("unchecked")
	public String index() {
		 ActionContext ctx = ActionContext.getContext();
		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);

		// 当前客户ID 以后前台传过来
		String custId = request.getParameter("customerId");

        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sb = new StringBuilder("select a.contri_deposit, a.contribution_loan, a.contribution_mid " +
											 "from acrm_f_ci_cust_contribution a " +
											 " " +	  
											 "where a.custom_id = '" + custId + "' and  a.etl_date  = current date -1 day ");

		cqs.setPrimaryKey("a.contri_deposit");

		map = cqs.excuteQuery(sb.toString(), 0, 100000);
		List list = (List) map.get("data");

		StringBuilder xml = new StringBuilder();
		if(list != null && list.size() > 0) {
			Map m = (Map)list.get(0);
			
			// 存款贡献度
			String ccon = (String)m.get("CONTRI_DEPOSIT");
			if(ccon == null || "".equals(ccon)) {
				ccon = "0";
			} else {
				ccon = new BigDecimal(ccon).setScale(0, BigDecimal.ROUND_HALF_UP).toString(); 
			}
			
			// 贷款贡献度
			String dcon = (String)m.get("CONTRIBUTION_LOAN");
			if(dcon == null || "".equals(dcon)) {
				dcon = "0";
			} else {
				dcon = new BigDecimal(dcon).setScale(0, BigDecimal.ROUND_HALF_UP).toString(); 
			}
			
			// 中间业务贡献度			
			String mcon = (String)m.get("CONTRIBUTION_MID");
			if(mcon == null || "".equals(mcon)) {
				mcon = "0";
			} else {
				mcon = new BigDecimal(mcon).setScale(0, BigDecimal.ROUND_HALF_UP).toString(); 
			}
			
			xml = xml.append("<chart xaxisname=\"贡献度分类\" yaxisname=\"本年累计贡献度\"  caption=\"客户贡献度\"  palette=\"1\" baseFontSize=\"13\" formatNumberScale=\"0\"> " +
							 "	 <set label=\"存款贡献度\" value=\""+ ccon +"\" /> " +
							 "	 <set label=\"贷款贡献度\" value=\""+ dcon +"\" /> " +
							 "	 <set label=\"中间业务贡献度\" value=\""+ mcon +"\" /> " +
							 "</chart> ");
		}
		
		if(list.size() > 0) {
			dataXml = xml.toString();
		}
		//system.out.printlnln(dataXml.toString());
		
		return "success";

//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setCharacterEncoding("UTF-8");
//		try {
//			if( list.size() != 0) {
//				response.getWriter().write(xml.toString());
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}
	
	public String getDataXml() {
		return dataXml;
	}
}
