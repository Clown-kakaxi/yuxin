
package com.yuchengtech.bcrm.product.action;


import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.model.OcrmFPdProdShowR;
import com.yuchengtech.bcrm.product.service.OcrmFPdProdShowRService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 产品展示的展示方案关联表关系处理
 * @author luyy
 *@since 2014-05-14
 */

@SuppressWarnings("serial")
@Action("/showTableR")
public class ProductShowTableRAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private OcrmFPdProdShowRService service;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFPdProdShowR();
		setCommonService(service);
	}
	/**
	 * 关联表查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String planId = request.getParameter("planId");
    	StringBuffer sb = new StringBuffer(" select * from OCRM_F_PD_PROD_show_R p where p.plan_id = '"+planId+"' ");
    	SQL = sb.toString();
    	datasource = ds;
    }
	
	
}
