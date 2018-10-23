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
import com.yuchengtech.bob.model.GeneralGrades;
import com.yuchengtech.bob.service.GeneralGradesService;

@Action("/general-grades")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "general-grades"})
})
public class GeneralGradesAction  extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{

	private static final long serialVersionUID = 8556373616115576565L;
	private GeneralGrades model = new GeneralGrades();
	private HttpServletRequest request;
	private Long ID;
	private Collection<GeneralGrades> list;
	@Autowired
	private GeneralGradesService generalGradesService;
	
    public HttpHeaders show() {
        return new DefaultHttpHeaders("show");
    }

    public HttpHeaders index() {
        list = generalGradesService.findAll();
        return new DefaultHttpHeaders("index")
            .disableCaching();
    }
    
    public String edit() {
        return "edit";
    }

    public String editNew() {
    	model = new GeneralGrades();
        return "editNew";
    }

    public String deleteConfirm() {
        return "deleteConfirm";
    }

    public String destroy() {
    	generalGradesService.remove(ID);
        addActionMessage("GeneralGrades removed successfully");
        return "success";
    }

    public DefaultHttpHeaders create() {
    	generalGradesService.save(model);
        addActionMessage("New GeneralGrades created successfully");
        return new DefaultHttpHeaders("success")
            .setLocationId(model.getID());
        
    }

    public String update() {
    	generalGradesService.save(model);
        addActionMessage("GeneralGrades updated successfully");
        return "success";
    }

    public String batchDestroy() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String idStr = request.getParameter("idStr");
        generalGradesService.batchRemove(idStr);
        addActionMessage("GeneralGrades removed successfully");
        return "success";
    }
    
    public void validate() {

    }

    public void setId(Long ID) {
        if (ID != null) {
            this.model = generalGradesService.find(ID);
        }
        this.ID = ID;
    }
    public Object getModel() {
        return (list != null ? list : model);
    }

}