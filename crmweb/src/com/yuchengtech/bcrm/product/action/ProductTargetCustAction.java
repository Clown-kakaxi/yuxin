package com.yuchengtech.bcrm.product.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.model.OcrmFCiCustFitProd;
import com.yuchengtech.bcrm.product.service.OcrmFCiCustFitProdService;
import com.yuchengtech.bcrm.product.service.TargetCusSearchService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value="product-targetCust", results={@Result(name="success", type="json")})
public class ProductTargetCustAction  extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	private HttpServletRequest request;
	@Autowired
	private TargetCusSearchService targetCusSearchService;
	@Autowired
	private OcrmFCiCustFitProdService ocrmFCiCustFitProdService;
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String productId = request.getParameter("productId");
		String conditionAttrs = request.getParameter("conditionAttrs");
		String radio =request.getParameter("radio");
		JSONArray jaCondition = JSONArray.fromObject(conditionAttrs);
		Map<String, Object> res = targetCusSearchService.generatorSql(jaCondition,radio,productId);
		SQL=(String)res.get("SQL");
		addOracleLookup("CUST_LEV","CUST_LEVEL4");
    	addOracleLookup("IS_BUY_THE_PROD","IF_FLAG");
		datasource = ds;
    }
	
	//将产品目标客户存入目标客户表
	public void addFitCust(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String productId = request.getParameter("productId");
		String productName = request.getParameter("productName");
		String conditionAttrs = request.getParameter("conditionAttrs");
		String radio =request.getParameter("radio");
		String sql="";
		if(conditionAttrs!=null && !"".equals(conditionAttrs)){
		   JSONArray jaCondition = JSONArray.fromObject(conditionAttrs);
           JSONObject jo = (JSONObject) jaCondition.get(0);
		   String ss_col_item = jo.getString("ss_col_item");
		   if(ss_col_item!=null && !"".equals(ss_col_item)){
			   Map<String, Object> res = targetCusSearchService.generatorSql(jaCondition,radio,productId);
			   sql=(String)res.get("SQL");
		   }
		  
		}
		//先删除原来的目标客户
		String jql="delete from OcrmFCiCustFitProd c where c.prodId ='"+productId+"'";
		Map<String,Object> values=new HashMap<String,Object>();
		ocrmFCiCustFitProdService.batchUpdateByName(jql, values);
		if(sql!=null&&!"".equals(sql)){
			List<Object[]> list1 = ocrmFCiCustFitProdService.getBaseDAO().findByNativeSQLWithIndexParam(sql);
			if(list1!=null&&list1.size()>0){
				for(int i=0;i<list1.size();i++){
					Object[] oo = list1.get(i);
					//加入现在的目标客户
					OcrmFCiCustFitProd info = new OcrmFCiCustFitProd();
					info.setProdId(productId);
					info.setCustId((oo[0]==null)?"":oo[0].toString());
					info.setCtrDate(new Date());
					info.setProdName(productName);
					ocrmFCiCustFitProdService.save(info);
				}
			}
		}
	}
}
