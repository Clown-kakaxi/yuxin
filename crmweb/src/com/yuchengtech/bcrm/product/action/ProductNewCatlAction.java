/**
 *@description 新增产品监控 action
 *@author: luyy
 *@since: 2014-06-30
 */	
package com.yuchengtech.bcrm.product.action;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.service.OcrmFPdProdFeedbackService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/productNewCatl")
public class ProductNewCatlAction extends CommonAction {
 	private HttpServletRequest request;

	@Autowired
	private OcrmFPdProdFeedbackService service;
	
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	 
	@Autowired
	public void init() {
//		model = new OcrmFPdProdFeedback();
//		setCommonService(service);
	}
   
    public void prepare() {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);

        StringBuilder sb = new StringBuilder(" select a.* from OCRM_F_PD_PROD_INFO a where catl_code is null ");
        
        for(String key : this.getJson().keySet()){
    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
				if(null!=key&&key.equals("PRODUCT_ID")){
					sb.append("  and a.PRODUCT_ID like '%"+this.getJson().get(key)+"%'  ");
				}else if(null!=key&&key.equals("PROD_NAME")){
					sb.append("  and a.PROD_NAME like '%"+this.getJson().get(key)+"%'  ");
				}
				else if(null!=key&&key.equals("PROD_START_DATE")){
					sb.append(" and  a.PROD_START_DATE= to_date('"+this.getJson().get(key)+"','yyyy-mm-dd')" );
				}else if(null!=key&&key.equals("PROD_END_DATE")){
					sb.append("  and  a.PROD_END_DATE= to_date('"+this.getJson().get(key)+"','yyyy-mm-dd')");

                }
    		}
		}
                		
        SQL = sb.toString();
        datasource = ds;
    }
   
     
    //关联类别
    public String setCatl() {

    	try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			String id = (String)request.getParameter("id");
			String catl = (String)request.getParameter("catl");
			Map<String,Object> values = new HashMap<String ,Object>();
			values.put("catlCode", BigDecimal.valueOf(Long.parseLong(catl)));
			service.batchUpdateByName(" update OcrmFPdProdInfo i set i.catlCode=:catlCode where i.productId='"+id+"'", values);
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "success";
    }
}


