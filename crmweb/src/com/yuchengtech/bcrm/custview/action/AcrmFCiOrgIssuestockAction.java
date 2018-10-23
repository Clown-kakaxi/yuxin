package com.yuchengtech.bcrm.custview.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiOrgIssuestock;
import com.yuchengtech.bcrm.custview.service.AcrmFCiOrgIssuestockService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @description 客户股票信息
 * @author likai
 * @since 2014-07-23
 */

@Action("/acrmFCiOrgIssuestock")
public class AcrmFCiOrgIssuestockAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  AcrmFCiOrgIssuestockService  acrmFCiOrgIssuestockService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	@Autowired
	public void init(){
	  	model = new AcrmFCiOrgIssuestock(); 
		setCommonService(acrmFCiOrgIssuestockService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
	
	/**
	 *信息查询SQL
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId=request.getParameter("custId");
		StringBuilder sb = new StringBuilder(" SELECT * FROM ACRM_F_CI_ORG_ISSUESTOCK WHERE CUST_ID= '"+custId+"'");
		SQL=sb.toString();
		datasource = ds;
		setPrimaryKey("LAST_UPDATE_TM desc ");
		configCondition("STOCK_TYPE", "=", "STOCK_TYPE",DataType.String);
		configCondition("STOCK_CODE", "like", "STOCK_CODE",DataType.String);
		configCondition("STOCK_NAME", "like", "STOCK_NAME",DataType.String);
		configCondition("MARKET_PLACE", "like", "MARKET_PLACE",DataType.String);
	}
	
	/**
	 * 数据保存
	 */
	public DefaultHttpHeaders saveData(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 if(((AcrmFCiOrgIssuestock)model).getIssueStockId() == null){
    		String custId=request.getParameter("custId");
 			((AcrmFCiOrgIssuestock)model).setLastUpdateUser(auth.getUsername());
 			((AcrmFCiOrgIssuestock)model).setCustId(custId);
 			((AcrmFCiOrgIssuestock)model).setLastUpdateTm(new Timestamp(new Date().getTime()));
 			acrmFCiOrgIssuestockService.save(model);
    	 } else if(((AcrmFCiOrgIssuestock)model).getIssueStockId()!=null){
    		String issueStockId = ((AcrmFCiOrgIssuestock)model).getIssueStockId().toString();
 			String jql = "update  AcrmFCiOrgIssuestock s set s.stockType=:stockType,s.stockCode=:stockCode,s.stockName=:stockName,s.ipoDate=:ipoDate,s.flowStockNum=:flowStockNum,s.currStockNum=:currStockNum,s.netassetPerShare=:netassetPerShare,s.oncf=:oncf,s.allotmentShareAmt=:allotmentShareAmt,s.marketPlace=:marketPlace,s.remark=:remark,s.lastUpdateUser=:lastUpdateUser,s.lastUpdateTm=:lastUpdateTm where s.issueStockId='"+issueStockId+"'";
 	        Map<String,Object> values = new HashMap<String,Object>();
 	        values.put("stockType",((AcrmFCiOrgIssuestock)model).getStockType());
 	        values.put("stockCode",((AcrmFCiOrgIssuestock)model).getStockCode());
 	        values.put("stockName",((AcrmFCiOrgIssuestock)model).getStockName());
 	        values.put("ipoDate",((AcrmFCiOrgIssuestock)model).getIpoDate());
 	        values.put("flowStockNum",((AcrmFCiOrgIssuestock)model).getFlowStockNum());
 	        values.put("currStockNum",((AcrmFCiOrgIssuestock)model).getCurrStockNum());
 	        values.put("netassetPerShare",((AcrmFCiOrgIssuestock)model).getNetassetPerShare());
 	        values.put("oncf",((AcrmFCiOrgIssuestock)model).getOncf());
 	        values.put("allotmentShareAmt",((AcrmFCiOrgIssuestock)model).getAllotmentShareAmt());
 	        values.put("marketPlace",((AcrmFCiOrgIssuestock)model).getMarketPlace());
 	        values.put("remark",((AcrmFCiOrgIssuestock)model).getRemark());
 	        values.put("lastUpdateUser",auth.getUsername());
 	        values.put("lastUpdateTm", new Date());
 	        acrmFCiOrgIssuestockService.batchUpdateByName(jql, values);
    	 }
		return new DefaultHttpHeaders("success");
	}
	
	//删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	acrmFCiOrgIssuestockService.batchDel(request);
		return new DefaultHttpHeaders("success");
    }
	
	
}
