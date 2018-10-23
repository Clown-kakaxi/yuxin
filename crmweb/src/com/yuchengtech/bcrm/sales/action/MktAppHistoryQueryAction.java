
package com.yuchengtech.bcrm.sales.action;

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

@SuppressWarnings("serial")
@Action("/mktapphistoryqueryaction")
public class MktAppHistoryQueryAction  extends CommonAction {
    
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
    	String mktId =request.getParameter("mktId");
    	StringBuffer sb = new StringBuffer(" SELECT T.*, "+
				        " USER0.username AS CHECK_USER_NAME, "+
				        " USER1.username AS APP_USER_NAME "+
				        " FROM OCRM_F_MK_ACTI_CHECK T "+
				        " INNER JOIN SYS_USERS USER0 "+
				        " ON USER0.userid = T.CHECK_USER "+
				        " INNER JOIN OCRM_F_MK_MKT_ACTIVITY ACT "+
				        " ON ACT.MKT_ACTI_ID = T.MKT_ACTI_ID "+
				        " INNER JOIN SYS_USERS USER1 "+
				        " ON USER1.userid = ACT.CREATE_USER "+
				        " WHERE 1>0 ");

   	         if(null!= mktId&&mktId.length()>0)
   	             sb.append(" and T.MKT_ACTI_ID='"+mktId+"' ");

   	         sb.append(" order by T.ACTI_CHECK_ID DESC ");

        	SQL=sb.toString();
        	datasource = ds;
    	}	
}
