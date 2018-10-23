
package com.yuchengtech.bcrm.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.model.OcrmFPdProdItemRel;
import com.yuchengtech.bcrm.product.service.ProductContrastRelationService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 产品对照关系信息的新增修改删除
 * @author ZSXIN
 *
 */

@SuppressWarnings("serial")
@Action("/productContrastRelationInfo")
public class ProductContrastRelationInfoAction  extends CommonAction {
    
    private HttpServletRequest request;

	@Autowired
	private ProductContrastRelationService service;
	
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	@Autowired
	public void init() {
		model = new OcrmFPdProdItemRel();
		setCommonService(service);
	}
	/**
	 * 产品对照关系查询
	 */
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String productId =request.getParameter("productId");
    	StringBuilder sb  = new StringBuilder(
    	"select A.* from OCRM_F_PD_PROD_ITEM_REL A ");
   		sb.append("	WHERE 1=1");
   		sb.append("   and A.PRODUCT_ID = '"+productId+"'");
   		
   		addOracleLookup("REL_TYPE", "CON_TYPE");//对照类型

    	SQL=sb.toString();
    	datasource = ds;
        	
    }
   /**
    * 数据删除提交
    * HTTP:DELETE方法
    * URL:/actionName/$id
    */
   public String destroy() {
       ActionContext ctx = ActionContext.getContext();
       request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
       String idStr = request.getParameter("idStr"); //获取需要删除的控制id
       service.remove(idStr);
       return "success";
   }	
}
