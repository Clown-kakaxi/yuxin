package com.yuchengtech.bcrm.custview.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiProductWill;
import com.yuchengtech.bcrm.custview.service.AcrmFCiProductWillService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 对私客户视图 客户产品意愿
 * @author agile
 *
 */
@SuppressWarnings("serial")
@Action("/acrmFCiProductWill")
public class AcrmFCiProductWillAction extends CommonAction{
	@Autowired
	private AcrmFCiProductWillService service;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init(){
		model = new AcrmFCiProductWill();
		setCommonService(service);
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx
	             .get(ServletActionContext.HTTP_REQUEST);
	    String customerId = request.getParameter("custId");
	    String old = request.getParameter("old");//复核流程数据查询
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ACRM_F_CI_PRODUCT_WILL  o where 1=1 ");
		if(customerId != null){
			sb.append(" and o.cust_id = '"+customerId+"'");
		}
		
		if("1".equals(old)){
			sb.setLength(0);
			sb.append("select o.*," +
					" i.FINANCIAL_PRODUCTS as FINANCIAL_PRODUCTS_OLD," +
					" i.COLLATERAL as COLLATERAL_old, " +
					" i.LOAN_TYPE as LOAN_TYPE_old, " +
					" i.PRODUCT_TYPE as PRODUCT_TYPE_old " +
					" from ACRM_F_CI_PRODUCT_WILL_TEMP  o" +
					" left join ACRM_F_CI_PRODUCT_WILL i on o.cust_id =i.cust_id  where 1=1 and o.cust_id = '"+customerId+"'");
		}
		SQL = sb.toString();
		datasource = ds;
	}	
	
	/**
	 * 发起审批工作流
	 */
	public void initFlow(){
		try {
			 ActionContext ctx = ActionContext.getContext();
		   	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		   	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   	 String custId = request.getParameter("custId");
		   	 
		   	 String financialProducts = request.getParameter("financialProducts");
		   	 String loanType = request.getParameter("loanType");
			 String collateral="";
			 String productType="";
		     String[]  collaterals  = request.getParameterValues("collateral");
		     String[]  productTypes  = request.getParameterValues("productType");
		   //当没有数据点击提交时，抛出提示信息
		     if(financialProducts==null&&loanType==null&productTypes==null&&collaterals==null){
		    	 throw new BizException(1, 0, "1002", "请选择数据，再提交审批！");
		     }
			 if(collaterals != null ){
				for(String  a :  collaterals){
					collateral += ","+a;
				}
			 }
			 if(productTypes != null ){
				 for(String  a :  productTypes){
					 productType += ","+a;
				 }
			 }
			 AcrmFCiProductWill info = (AcrmFCiProductWill)service.find(custId);	
		     if(info!=null && "1".equals(info.getState())){//0暂存，1审核，2审核结束
		    	 throw new BizException(1, 0, "1002", "正在审核中,不能重复审核...");
		     }
		   //collateraltemp用来防止collateral为空的时候调用substring(1)报错，导致程序不能运行
		     String collateraltemp="";
		     if(!"".equals(collateral)){
		    	 collateraltemp=collateral.substring(1);
		     }
		     //productTypetemp用来防止productType为空的时候调用substring(1)报错，导致程序不能运行
		     String productTypetemp="";
		     if(!"".equals(productType)){
		    	 productTypetemp=productType.substring(1);
		     }
		     //保存开通账户信息临时表
		     service.saveTemp(financialProducts,loanType,collateraltemp,productTypetemp,custId);
		     //设置复核状态
		     if(info!=null){
		    	 service.batchUpdateByName("update AcrmFCiProductWill o set o.state = '1' where o.custId = '"+info.getCustId()+"'", null);
		     }
		     
		   	 Date date = new Date();
		   	 SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		   	 String p = sdf.format(date);
		   	 String instanceid = "CPYY"+"_"+custId+"_"+p;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			 String jobName = "客户产品意愿复核_"+custId;//自定义流程名称
			 //以下方法调用流程引擎..
			 service.initWorkflowByWfidAndInstanceid("88", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
			 String nextNode = "88_a4";
		     Map<String,Object> map1=new HashMap<String,Object>();
			 map1.put("instanceid", instanceid);
		     map1.put("currNode", "88_a3");
		     map1.put("nextNode",  nextNode);
		     this.setJson(map1);
		} catch (Exception e) {
			throw new BizException(0, 1, "1002",e.getMessage());
		}
	}
}
