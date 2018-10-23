package com.yuchengtech.bcrm.product.action;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.yuchengtech.bcrm.customer.model.AgileSearchCondition;
import com.yuchengtech.bcrm.product.service.TargetCusSearchService;
@ParentPackage("json-default")
@Action(value="/targetcussearch", results={
    @Result(name="success", type="json")
})
public class TargetCusSearchAction extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{
	private static final long serialVersionUID = -2010621122837504304L;
	private AgileSearchCondition model = new AgileSearchCondition();
    private Collection<Map<String, Object>> list;
    private Long id;
    @Autowired
    private TargetCusSearchService targetCusSearchService;
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
    	//customerBaseService.remove(id);
        return "success";
    }

    // POST /orders
    public HttpHeaders create() throws Exception {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String conditionCols = request.getParameter("conditionCols");
    	String solutionID = request.getParameter("solutionID");
    	String radio=request.getParameter("radio");
    	targetCusSearchService.saveSolution(conditionCols,radio,solutionID);
        
        return new DefaultHttpHeaders("success");
    
    }

    // PUT /orders/1
    public String update() throws Exception {
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
