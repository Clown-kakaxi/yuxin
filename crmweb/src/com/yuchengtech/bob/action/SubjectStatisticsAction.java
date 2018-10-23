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
import com.yuchengtech.bob.model.SubjectStatistics;
import com.yuchengtech.bob.service.SubjectStatisticsService;

@Action("/subject-statistics")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "subject-statistics"})
})
public class SubjectStatisticsAction  extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{

	private static final long serialVersionUID = 8550661616118279765L;
	private SubjectStatistics model = new SubjectStatistics();
	private HttpServletRequest request;
	private Long ID;
	private Collection<SubjectStatistics> list;
	@Autowired
	private SubjectStatisticsService subjectStatisticsService;
	
    public HttpHeaders show() {
        return new DefaultHttpHeaders("show");
    }

    public HttpHeaders index() {
        list = subjectStatisticsService.findAll();
        return new DefaultHttpHeaders("index")
            .disableCaching();
    }
    
    public String edit() {
        return "edit";
    }

    public String editNew() {
    	model = new SubjectStatistics();
        return "editNew";
    }

    public String deleteConfirm() {
        return "deleteConfirm";
    }

    public String destroy() {
    	subjectStatisticsService.remove(ID);
        addActionMessage("subjectStatistics removed successfully");
        return "success";
    }

    public DefaultHttpHeaders create() throws Exception {
    	subjectStatisticsService.save(model);
        addActionMessage("New subjectStatistics created successfully");
        return new DefaultHttpHeaders("success")
            .setLocationId(model.getSubjectId());
        
    }

    public String update() throws Exception {
    	subjectStatisticsService.save(model);
        addActionMessage("subjectStatistics updated successfully");
        return "success";
    }
    
    public String batchDestroy() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String idStr = request.getParameter("idStr");
        subjectStatisticsService.batchRemove(idStr);
        addActionMessage(" SubjectStatistics removed successfully");
        return "success";
    }
    
    public void validate() {

    }

    public void setId(Long ID) {
        if (ID != null) {
            this.model = subjectStatisticsService.find(ID);
        }
        this.ID = ID;
    }
    public Object getModel() {
        return (list != null ? list : model);
    }

}