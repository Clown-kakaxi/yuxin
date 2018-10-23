package com.yuchengtech.bob.action;

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
import com.yuchengtech.bob.model.RollCustomer;
import com.yuchengtech.bob.service.RollCustomerService;

@Action("/roll-customer")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "roll-customer"})
})
public class RollCustomerAction  extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{

    private static final long serialVersionUID = 8550661616118279889L;
    private RollCustomer model = new RollCustomer();
	private HttpServletRequest request;
    private Long rollId;
    private Collection<RollCustomer> list;
    @Autowired
    private RollCustomerService rollCustomerService;
    
    public HttpHeaders show() {
        return new DefaultHttpHeaders("show");
    }

    public HttpHeaders index() {
        list = rollCustomerService.findAll();
        return new DefaultHttpHeaders("index")
            .disableCaching();
    }
    
    public String edit() {
        return "edit";
    }

    public String editNew() {
        model = new RollCustomer();
        return "editNew";
    }

    public String deleteConfirm() {
        return "deleteConfirm";
    }

    public String destroy() {
        rollCustomerService.remove(rollId);
        addActionMessage("rollCustomer removed successfully");
        return "success";
    }

    public DefaultHttpHeaders create() {
        rollCustomerService.save(model);
        addActionMessage("New rollCustomer created successfully");
        return new DefaultHttpHeaders("success")
            .setLocationId(model.getRollId());
        
    }

    public String update() {
        rollCustomerService.save(model);
        addActionMessage("rollCustomer updated successfully");
        return "success";
    }
    
    public String batchDestroy() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String idStr = request.getParameter("idStr");
        rollCustomerService.batchRemove(idStr);
        addActionMessage("PlanCustomer removed successfully");
        return "success";
    }

    public void validate() {
//        if (model.getPlanName() == null || model.getPlanName().length() ==0) {
//            addFieldError("planName", "The planName is empty");
//        }
    }

    public void setId(Long rollId) {
        if (rollId != null) {
            this.model = rollCustomerService.find(rollId);
        }
        this.rollId = rollId;
    }
    public Object getModel() {
        return (list != null ? list : model);
    }

}