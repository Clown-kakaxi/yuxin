package com.yuchengtech.bcrm.customer.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.yuchengtech.bcrm.customer.model.GroupMember;
import com.yuchengtech.bcrm.customer.service.GroupMemberForAppService;

/**
 * @describe 资讯服务导航
 * @author WillJoe
 *
 */
@SuppressWarnings("serial")
@Action("GroupMemberForAppAction")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "GroupMemberForAppAction"})
})
public class GroupMemberForAppAction  extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{

	
	private GroupMember wi = new GroupMember();
	private Collection<GroupMember> wic;
	private Long id;
	private HttpServletRequest request;
    @Autowired
    private GroupMemberForAppService wis;
	
    /**
     * 单条数据展示.
     * HTTP:GET方法
     * URL:/actionName/$id;
     */
    public HttpHeaders show() {
    	wi = wis.find(id);    	
        return new DefaultHttpHeaders("show");
    }

    /**
     * 数据列表查询包括全部数据，或者待条件查询。
     *  HTTP:GET方法 
     *  URL:/actionName；
     */
    public HttpHeaders index() {
    	wic = wis.findAll();
        return new DefaultHttpHeaders("index")
            .disableCaching();
    }
    
    /**
     * 请求数据编辑页面跳转。
     * HTTP:GET方法
     * URL:/actionName/$id/edit;
     */
    public String edit() {
        return "edit";
    }

    /**
     * 新增页面请求
     * HTTP:GET方法
     * URL:/actionName/new
     */
    public String editNew() {
        return "editNew";
    }

    /**
     * 请求删除页面
     * HTTP:GET方法
     * URL:/actionName/$id/deleteContirm
     */
    public String deleteConfirm() {
        return "deleteConfirm";
    }

    /**
     * 数据删除提交
     * HTTP:DELETE方法
     * URL:/actionName/$id
     */
    public String destroy() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);
        String idStr = request.getParameter("idStr");
    	wis.changeSts(idStr);
        return "success";
    }

    /**
     * 数据新增提交
     * HTTP:POST方法
     * URL:/actionName
     */
    public HttpHeaders create() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        if (request.getParameter("operate").equals("app")) {
        String s = request.getParameter("cbid");
        JSONObject jsonObject = JSONObject.fromObject(s);
		JSONArray jarray = jsonObject.getJSONArray("id");
		if(jarray.size()>0){
			for(int i=0;i<jarray.size();i++){
				Long id=Long.parseLong(jarray.get(i).toString());
				wi.setId(id);
				wi.setAppStatus("2");
				wis.save(wi);
			}
		}
        }
        else{
    	wis.save(wi);
        }
        return new DefaultHttpHeaders("success")
            .setLocationId(wi.getId());
    }

    /**
     * 数据修改提交
     * HTTP:PUT方法
     * URL:/WorkPlatNotice/$id
     */
    public String update() {
       	wis.save(wi);
        return "success";
    }

    /**
     * 数据验证方法
     */
    public void validate() {
    	/**
    	 * TODO validate bussness logic.
    	 */
    }

    /**
     * ID参数获取方法
     * @param id
     */
    public void setId(Long id) {
        if (id != null) {
            this.wi = wis.find(id);
        }
        this.id = id;
    }
    
    /**
     * 
     */
    public Object getModel() {
        return (wic != null ? wic : wi);
    }
    


}
