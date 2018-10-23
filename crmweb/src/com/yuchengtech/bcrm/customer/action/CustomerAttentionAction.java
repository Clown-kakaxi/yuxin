package com.yuchengtech.bcrm.customer.action;

/**
 * 我关注的客户
 * 
 */


import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bcrm.customer.model.CustomerAttention;
import com.yuchengtech.bcrm.customer.service.CustomerAttentionService;
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "customerAttention"})
})
public class CustomerAttentionAction extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{
	private static final long serialVersionUID = -2010621122837504304L;
	private CustomerAttention model = new CustomerAttention();
    private Collection<CustomerAttention> list;
    private Long id;
    @Autowired
    private CustomerAttentionService customerAttentionService; //=new CustomerAttentionService();
	private HttpServletRequest request;
 
    // GET /orders
    public HttpHeaders index() {
        return new DefaultHttpHeaders("index")
            .disableCaching();
    }
	
    // GET /orders/1
    public HttpHeaders show() {
        return new DefaultHttpHeaders("show");
    }


    
    // GET /orders/1/edit
    public String edit() {
        return "edit";
    }

    // GET /orders/new
    public String editNew() {
   
        return "editNew";
    }

    // GET /orders/1/deleteConfirm
    public String deleteConfirm() {
        return "deleteConfirm";
    }

    // DELETE /orders/1
    public String destroy() {
        return "success";
    }

/*    public String batchDestroy() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String idStr = request.getParameter("idStr");
        customerAttentionService.batchRemove(idStr);
        addActionMessage("PlanCustomer removed successfully");
        return "success";
    }    */
    
    // POST /orders
    public HttpHeaders create() throws Exception {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        //新增
    	if(request.getParameter("operate").equals("add")){
    		 String s=  request.getParameter("cust_id");
             JSONObject jsonObject =JSONObject.fromObject(s);
             JSONArray jarray =  jsonObject.getJSONArray("cust_id");
 /*            String s1=  request.getParameter("cust_lev");
             JSONObject jsonObject1 =JSONObject.fromObject(s1);
             JSONArray jarray1 =  jsonObject1.getJSONArray("cust_lev");
             String s2=  request.getParameter("cust_zh_name");
             JSONObject jsonObject2 =JSONObject.fromObject(s2);
             JSONArray jarray2 =  jsonObject2.getJSONArray("cust_zh_name");
             String s3=  request.getParameter("cust_zzdm");
             JSONObject jsonObject3 =JSONObject.fromObject(s3);
             JSONArray jarray3 =  jsonObject3.getJSONArray("cust_zzdm");*/
             customerAttentionService.addAttention(jarray);
    	}
    	else if(request.getParameter("operate").equals("delete")){
    		 String s=  request.getParameter("caid");
             JSONObject jsonObject =JSONObject.fromObject(s);
             JSONArray jarray =  jsonObject.getJSONArray("id");
             customerAttentionService.batchRemove(jarray);
    		
    	}
        return new DefaultHttpHeaders("success")
            .setLocationId(model.getId());
    }

    // PUT /orders/1
    // 取消关注客户
    public String update() {
    	//customerAttentionService.update(model);
        return "success";
    }
    //校验方法
    public void validate() {

    }
    public Long getId() 
    { 
        return this.id; 
    } 

    public void setId(Long id) {
 
        this.id = id;
    }
    
    public Object getModel() 
    { 
        return (list != null ? list : model); 
    } 

    
}
