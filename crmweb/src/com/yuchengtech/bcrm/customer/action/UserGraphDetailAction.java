package com.yuchengtech.bcrm.customer.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bcrm.customer.service.GraphOptionService;
import com.yuchengtech.bob.vo.GraphDetailVo;

@Action("graphoption")
@Results({
    @Result(name="success",type="redirectAction", params = {"actionName" , "graphoption", "success", "false"})
})
public class UserGraphDetailAction extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{
	
	private static final long serialVersionUID = -4757515058810916734L;

	private GraphDetailVo detail = new GraphDetailVo();
	
	private String id;
	
	@Autowired
	private GraphOptionService gos;
	
    public HttpHeaders show() {
    	detail = gos.searchGraphDetail(Long.valueOf(id));
    	return new DefaultHttpHeaders("show");
    }
	
    public HttpHeaders create() {
    	detail = gos.createGraph(detail);
    	return new DefaultHttpHeaders("success");
    }
    
    public String edit() {
    	detail = gos.searchGraphDetail(Long.valueOf(id));
        return "edit";
    }

    
    public String update(){
    	gos.updateGraph(detail,Long.valueOf(id));
    	return "success";
    }
    
    public String destroy(){
    	String ids[] = id.split("-");
    	for(String i : ids)
    		gos.deleteGraph(Long.valueOf(i));
    	return "success";
    }
    
	public GraphDetailVo getModel() {
		return detail;
	}

	public void validate() {
	}
	
    public void setId(String id) {
        this.id = id;
    }

}
