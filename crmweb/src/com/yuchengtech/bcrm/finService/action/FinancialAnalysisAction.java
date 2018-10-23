package com.yuchengtech.bcrm.finService.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.yuchengtech.bcrm.finService.service.FDReportService;
import com.yuchengtech.bcrm.finService.service.FinancialAnalysisService;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value = "/FinancialAnalysis", results = { @Result(name = "success", type = "json") })
@SuppressWarnings("unchecked")
public class FinancialAnalysisAction {
	@Autowired
	private HttpServletRequest request;
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;

	@Autowired
	private FinancialAnalysisService financialAnalysisService;
	
	@Autowired
	private FDReportService fDReportService;

	protected Map<String, Object> json = new HashMap<String, Object>();

	private List finIndex = new ArrayList();
	private List f2Index = new ArrayList();
	private String dataXml1 = "";
	private String dataXml2 = "";
	private String dataXml3 = "";
	private String dataXml4 = "";
	private Map valueMap = new HashMap();

	public String findFinIndex() {
		String custId = null;
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		if (request.getParameter("CUST_ID") != null) {
			custId = request.getParameter("CUST_ID");
		}
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		this.json = this.financialAnalysisService.findFinIndex(custId, auth
				.getUnitId());
		return "sucess";
	}

	public String finIndexSaveOrUpdate() {
		String custId = null;
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String f2Inde=request.getParameter("f2index");
		if (request.getParameter("CUST_ID") != null) {
			custId = request.getParameter("CUST_ID");
		}
		this.financialAnalysisService.finIndexSaveOrUpdate(custId, auth
				.getUserId(), this.finIndex, this.f2Index);
		return "sucess";
	}
	

	public String amoutValue() {
		String belongType = null;
		String assetDebtType = null;
		String custId = null;
		String assetsType = null;
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		if (request.getParameter("BELONG_TYPE") != null) {
			belongType = request.getParameter("BELONG_TYPE");
		}
		if (request.getParameter("ASSET_DEBT_TYPE") != null) {
			assetDebtType = request.getParameter("ASSET_DEBT_TYPE");
		}
		if (request.getParameter("CUST_ID") != null) {
			custId = request.getParameter("CUST_ID");
		}
		if (request.getParameter("ASSETS_TYPE") != null) {
			assetsType = request.getParameter("ASSETS_TYPE");
		}
		this.json = this.financialAnalysisService.amountValue(belongType,
				assetDebtType, custId,assetsType);

		return "sucess";
	}

	public String monthValue() {

		String ioType = null;
		String custId = null;
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		if (request.getParameter("IO_TYPE") != null) {
			ioType = request.getParameter("IO_TYPE");
		}
		if (request.getParameter("CUST_ID") != null) {
			custId = request.getParameter("CUST_ID");
		}

		this.json = this.financialAnalysisService.monthValue(ioType, custId);

		return "sucess";
	}

	public String finInfoSaveOrUpdate() {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		json.put("userId", auth.getUserId());
		this.financialAnalysisService.finInfoSaveOrUpdate(json);

		return "sucess";
	}

	public String custIoSaveOrUpdate() {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		json.put("userId", auth.getUserId());
		this.financialAnalysisService.custIoSaveOrUpdate(json);
		return "sucess";
	}
	
	public String queryReview() throws SQLException{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		String sql = " SELECT V.ID, V.FZZZCBLDP_ADVISE,V.FZSRBLDP_ADVISE,V.LDXBLDP_ADVISE, " +
				"V.CXBLDP_ADVISE,V.TZYJZCBLDPL_ADVISE,V.XFLDP_ADVISE, " +
				"V.LCCJLDP_ADVISE,V.SYLDP_ADVISE,V.ZHDP_ADVISE " +
				" FROM OCRM_F_FIN_ANA_ADVISE_VISTA V " +
				"WHERE V.CUST_ID = '"+ custId +"'";
		QueryHelper queryHelper = new QueryHelper(sql, ds.getConnection());
		if(this.json!=null){
			this.json.clear();
		}else{
			this.json = new HashMap<String, Object>();
		}
		this.json.put("json",queryHelper.getJSON());
		return "success";
	}

	public String assetXml() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		// 当前客户ID 以后前台传过来
		String custId = request.getParameter("customerId");

		AuthUser authUser = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Map map1 = new HashMap();
		map1 = this.financialAnalysisService.findAsset(custId, authUser
				.getUnitId());
		this.dataXml1 = this.getXml("客户本行资产信息", "pie", map1);

		Map map2 = new HashMap();
		map2 = this.financialAnalysisService.findOther(custId, "1","OB_ASSETS_TYPE");
		this.dataXml2 = this.getXml("客户他行资产信息", "pie", map2);

		Map map3 = new HashMap();
		map3 = this.financialAnalysisService.findOther(custId, "2","O_ASSETS_TYPE");
		this.dataXml3 = this.getXml("客户其他资产信息", "column", map3);

		Map map4 = new HashMap();
		map4 = this.financialAnalysisService.findHome(custId);
		this.dataXml4 = this.getXml("家庭月度收入信息", "column", map4);

		this.valueMap = this.financialAnalysisService.findCustAllAssetAndDebt(
				custId, authUser.getUnitId());
		return "sucess";
	}

	public String getXml(String name, String type, Map map) {
		StringBuffer sb = new StringBuffer();
		if (type.equals("pie")) {
			sb.append("<chart caption=\"" + name
					+ "\"    baseFontSize=\"12\" formatNumberScale=\"0\" > ");
		} else if (type.equals("column")) {
			sb.append("<chart caption=\"" + name + "\" subCaption=\"(单位：元)\"      formatNumberScale=\"0\">");
		}

		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			sb.append("<set label=\"" + entry.getKey() + "\"  value=\""
					+ entry.getValue() + "\"/>");
		}
		sb.append("</chart>");

		return sb.toString();
	}
	
	/**
	 * 生成报告文件Action
	 * @throws Exception
	 */
	public void createReport() throws Exception{
		ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
		.getAuthentication().getPrincipal();
//        String reportId = request.getParameter("reportId");
        String custId = request.getParameter("custId");
        String instCode = auth.getUnitId();
        String reportId = fDReportService.getReportId(custId);
        fDReportService.createReport(reportId,custId,instCode);
  
        
	}

	public String getDataXml1() {
		return dataXml1;
	}

	public String getDataXml2() {
		return dataXml2;
	}

	public String getDataXml3() {
		return dataXml3;
	}

	public String getDataXml4() {
		return dataXml4;
	}

	public Map<String, Object> getJson() {
		return json;
	}

	public void setJson(Map<String, Object> json) {
		this.json = json;
	}

	public void setAssetInfo(List assetInfo) {
		this.json.put("assetInfo", assetInfo);
	}

	public void setDebtInfo(List debtInfo) {
		this.json.put("debtInfo", debtInfo);
	}

	public void setBelongType(String belongType) {
		this.json.put("belongType", belongType);
	}

	public void setFinIndex(List finIndex) {
		this.finIndex = finIndex;
	}
	
	public void setF2Index(List f2Index) {
		this.f2Index = f2Index;
	}

	public Map getValueMap() {
		return valueMap;
	}

}
