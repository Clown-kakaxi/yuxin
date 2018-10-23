package com.yuchengtech.bcrm.customer.action;

import java.text.ParseException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bcrm.customer.model.CustomerRelateCustomerBase2;
import com.yuchengtech.bcrm.customer.service.CustomerRelateCustomerBaseService;
@ParentPackage("json-default")
@Action(value="/customerrelatecustomerbase", results={
    @Result(name="success", type="json")
})
public class CustomerRelateCustomerBaseAction extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{
	private static final long serialVersionUID = -2010621122837504304L;
	private CustomerRelateCustomerBase2 model = new CustomerRelateCustomerBase2();
    private Collection<CustomerRelateCustomerBase2> list;
    private Long id;
    @Autowired
    private CustomerRelateCustomerBaseService customerRelateCustomerBaseService =new CustomerRelateCustomerBaseService();
	private HttpServletRequest request;

    // GET /orders
    public HttpHeaders index() {
    	//list=customerRelateCustomerBaseService.query(0, 10);
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
    public HttpHeaders create() throws ParseException {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        if(request.getParameter("operate").equals("add")){
		    String s=  request.getParameter("cust_id");
		    String x=request.getParameter("cbid");
		    String s1=request.getParameter("cert_num");
		    String s2=request.getParameter("cert_type");
		    String s3=request.getParameter("cust_zh_name");
		    String s4=request.getParameter("rela_create_dt");
		    JSONObject jsonObject =JSONObject.fromObject(s);
		    JSONArray jarray =  jsonObject.getJSONArray("cust_id");
		    JSONObject jsonObject1 =JSONObject.fromObject(s1);
		    JSONArray jarray1 =  jsonObject1.getJSONArray("cert_num");
		    JSONObject jsonObject2 =JSONObject.fromObject(s2);
		    JSONArray jarray2 =  jsonObject2.getJSONArray("cert_type");
		    JSONObject jsonObject3 =JSONObject.fromObject(s3);
		    JSONArray jarray3 =  jsonObject3.getJSONArray("cust_zh_name");
		    JSONObject jsonObject4 =JSONObject.fromObject(s4);
		    JSONArray jarray4 =  jsonObject4.getJSONArray("rela_create_dt");
		    customerRelateCustomerBaseService.batchSave(jarray,jarray1,jarray2,x);
        }
        else if(request.getParameter("operate").equals("delete")){
            String s=  request.getParameter("cbid");
            JSONObject jsonObject =JSONObject.fromObject(s);
            JSONArray jarray =  jsonObject.getJSONArray("id");
        	customerRelateCustomerBaseService.remove(jarray);
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
