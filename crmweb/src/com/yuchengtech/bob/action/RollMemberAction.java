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
import com.yuchengtech.bob.model.RollCustomerList;
import com.yuchengtech.bob.service.RollCustomerListService;

@Action("/roll-member")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "roll-member"})
})
public class RollMemberAction  extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{

    private static final long serialVersionUID = 8550661616118279889L;
    private RollCustomerList model = new RollCustomerList();
	private HttpServletRequest request;
    private Long rollId;
    private Collection<RollCustomerList> list;
    @Autowired
    private RollCustomerListService rollCustomerListService;
    
    public HttpHeaders show() {
        return new DefaultHttpHeaders("show");
    }

    public HttpHeaders index() {
        list = rollCustomerListService.findAll();
        return new DefaultHttpHeaders("index")
            .disableCaching();
    }
    
    public String edit() {
        return "edit";
    }

    public String editNew() {
        model = new RollCustomerList();
        return "editNew";
    }
    
    public String deleteConfirm() {
        return "deleteConfirm";
    }

    public String destroy() {
        rollCustomerListService.remove(rollId);
        addActionMessage("rollCustomerList removed successfully");
        return "success";
    }

    public DefaultHttpHeaders create() {
    	rollCustomerListService.save(model);
        addActionMessage("New rollCustomerList created successfully");
        return new DefaultHttpHeaders("success")
            .setLocationId(model.getRollId());
        
    }

    public String update() {
    	rollCustomerListService.save(model);
        addActionMessage("rollCustomerList updated successfully");
        return "success";
    }
    
    public String batchCreate() throws Exception {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String idStr = request.getParameter("idStr");
        String rollId = request.getParameter("rollId");
    	
        rollCustomerListService.batchAdd(idStr,rollId);
        addActionMessage("rollMember create successfully");
        return "success";
    }
    
    public String batchDestroy() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String idStr = request.getParameter("idStr");
        rollCustomerListService.batchRemove(idStr);
        addActionMessage("rollMember removed successfully");
        return "success";
    }

    public void validate() {
//        if (model.getPlanName() == null || model.getPlanName().length() ==0) {
//            addFieldError("planName", "The planName is empty");
//        }
    }

    public void setId(Long rollId) {
        if (rollId != null) {
            this.model = rollCustomerListService.find(rollId);
        }
        this.rollId = rollId;
    }
    public Object getModel() {
        return (list != null ? list : model);
    }

}