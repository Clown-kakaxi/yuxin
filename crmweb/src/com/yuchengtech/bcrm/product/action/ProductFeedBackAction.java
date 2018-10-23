package com.yuchengtech.bcrm.product.action;


import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.model.OcrmFPdProdFeedback;
import com.yuchengtech.bcrm.product.service.OcrmFPdProdFeedbackService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/productfeedback")
/**
 *@description 新增反馈查询 action
 *@author: luyy
 *@since: 2014-06-30
 */
public class ProductFeedBackAction extends CommonAction {
 	private HttpServletRequest request;

	@Autowired
	private OcrmFPdProdFeedbackService service;
	
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	 
	@Autowired
	public void init() {
		model = new OcrmFPdProdFeedback();
		setCommonService(service);
	}
   
    public void prepare() {
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String productId = request.getParameter("productId");

        StringBuilder sb = new StringBuilder("select t.*,a.user_name as feedback_user_name,p.PROD_NAME "
                + "from OCRM_F_PD_PROD_FEEDBACK t left join admin_auth_account a on t.feedback_user = a.account_name ," +
                		"OCRM_F_PD_PROD_INFO p where t.PRODUCT_ID=p.PRODUCT_ID");
        if(productId != null ){
        	sb.append(" and t.PRODUCT_ID = '" + productId+"'");
        }
        
        //添加查询条件
        for(String key : this.getJson().keySet()){
    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
				if(null!=key&&key.equals("PROD_NAME")){
					sb.append("  and p.PROD_NAME like '%"+this.getJson().get(key)+"%'  ");
				}else if(null!=key&&key.equals("FEEDBACK_USER_NAME")){
					sb.append("  and a.user_name like '%"+this.getJson().get(key)+"%'  ");
				}else if(null!=key&&key.equals("FEEDBACK_USER")){
					sb.append("  and a.account_name = '"+this.getJson().get(key)+"'  ");
				}else if(null!=key&&key.equals("FEEDBACK_DATE")){
					sb.append(" and  t.FEEDBACK_DATE= to_date('"+this.getJson().get(key)+"','yyyy-mm-dd')" );
				}else if(null!=key&&key.equals("PRODUCT_ID")){
					sb.append("  and t.PRODUCT_ID = '"+this.getJson().get(key)+"'  ");
				}else if(null!=key){
					sb.append("  and  t."+key+" like '%"+this.getJson().get(key)+"%'  ");

                }
    		}
		}
                		
        SQL = sb.toString();
        setPrimaryKey("t.FEEDBACK_ID desc");
        datasource = ds;
    }
   
     
    //删除
    public String destroy() {

    	try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			String idStr = (String)request.getParameter("idStr");
			service.remove(Long.valueOf(idStr));
    		addActionMessage("remove productFeatureId successful");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "success";
    }
}


