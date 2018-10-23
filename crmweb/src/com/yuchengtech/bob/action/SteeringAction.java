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
import com.yuchengtech.bcrm.workplat.model.WorkingplatformInfo;
import com.yuchengtech.bob.service.SteeringService;

@Action("/steering")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "steering"})
})
public class SteeringAction  extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{

	private static final long serialVersionUID = 8557655333155273765L;
	private WorkingplatformInfo model = new WorkingplatformInfo();
	private HttpServletRequest request;
	private Long ID;
	private Collection<WorkingplatformInfo> list;
	@Autowired
	private SteeringService steeringService;
	
    public HttpHeaders show() {
        return new DefaultHttpHeaders("show");
    }

    public HttpHeaders index() {
        list = steeringService.findAll();
        return new DefaultHttpHeaders("index")
            .disableCaching();
    }
    
    public String edit() {
        return "edit";
    }

    public String editNew() {
    	model = new WorkingplatformInfo();
        return "editNew";
    }

    public String deleteConfirm() {
        return "deleteConfirm";
    }

    public String destroy() {
    	steeringService.remove(ID);
        addActionMessage("WorkingplatformInfo removed successfully");
        return "success";
    }

    public DefaultHttpHeaders create() {
    	steeringService.save(model);
        addActionMessage("New WorkingplatformInfo created successfully");
        return new DefaultHttpHeaders("success")
            .setLocationId(model.getMessageId());
        
    }

    public String update() {
    	steeringService.save(model);
        addActionMessage("WorkingplatformInfo updated successfully");
        return "success";
    }
    
    public String batchDestroy() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String idStr = request.getParameter("idStr");
        steeringService.batchRemove(idStr);
        addActionMessage("WorkingplatformInfo removed successfully");
        return "success";
    }
    

    public void validate() {

    }

    public void setId(Long ID) {
        if (ID != null) {
            this.model = steeringService.find(ID);
        }
        this.ID = ID;
    }
    public Object getModel() {
        return (list != null ? list : model);
    }

}