package com.yuchengtech.bob.action;

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
import com.yuchengtech.bob.model.CustomerRelation;
import com.yuchengtech.bob.service.CustomerRelationService;
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "CustomerRelation"})
})
public class CustomerRelationAction extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{
	private static final long serialVersionUID = -2010621122837504304L;
	private CustomerRelation model = new CustomerRelation();
    private Collection<CustomerRelation> list;
    private Long id;
    @Autowired
    private CustomerRelationService CustomerRelationService;
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

    // POST /orders
    public HttpHeaders create() throws Exception {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        if(request.getParameter("operate").equals("add")){
		    String custid=  request.getParameter("custid");
		    String custzhname=request.getParameter("custzhname");
		    String custzzdm=request.getParameter("custzzdm");
		    String shpct=request.getParameter("shpct");
		    String reladesc=request.getParameter("reladesc");
		    String relaname=request.getParameter("relaname");
		    String s1=request.getParameter("cust_zh_name");
		    String s2=request.getParameter("cust_zzdm");
		    String s=request.getParameter("cid");
		    JSONObject jsonObject =JSONObject.fromObject(s);
		    JSONArray jarray =  jsonObject.getJSONArray("id");
		    JSONObject jsonObject1 =JSONObject.fromObject(s1);
		    JSONArray jarray1 =  jsonObject1.getJSONArray("cust_zh_name");
		    JSONObject jsonObject2 =JSONObject.fromObject(s2);
		    JSONArray jarray2 =  jsonObject2.getJSONArray("cust_zzdm");
		    CustomerRelationService.batchSave(jarray,jarray1,jarray2,custid,custzhname,custzzdm,shpct,reladesc,relaname);
		    
        }
        else if(request.getParameter("operate").equals("delete")){
            String s=  request.getParameter("gxid");
            JSONObject jsonObject =JSONObject.fromObject(s);
            JSONArray jarray =  jsonObject.getJSONArray("id");
            CustomerRelationService.remove(jarray);
        }
        
        return new DefaultHttpHeaders("success")
            .setLocationId(model.getId());
    }

    // PUT /orders/1
    public String update() {
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
