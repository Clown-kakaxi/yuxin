package com.yuchengtech.bob.action;
import java.text.ParseException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bob.model.CostExpenseInstall;
import com.yuchengtech.bob.service.CostExpenseInstallService;

@Action("/costexpenseinstall")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "costexpenseinstall"})
})
public class CostExpenseInstallAction extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{
	private static final long serialVersionUID = -2010621122837504304L;
	private CostExpenseInstall model = new CostExpenseInstall();
    private Collection<CostExpenseInstall> list;
    private Long id;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CostExpenseInstallService costExpenseInstallService;
    
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
    	//customerBaseService.remove(id);
        return "success";
    }

    // POST /orders
    public HttpHeaders create() throws Exception {
    	//throw new Exception("false");
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        model.setCUST_ID(request.getParameter("customerId"));
        costExpenseInstallService.save(model);
        //新增
        return new DefaultHttpHeaders("success")
            .setLocationId(model.getId());
    }

    // PUT /orders/1
    public String update() throws ParseException {
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
