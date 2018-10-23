package com.yuchengtech.bcrm.customer.action;

import java.text.ParseException;
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
import com.yuchengtech.bcrm.customer.service.GroupMemberService;

/**
 * @describe 资讯服务导航
 * @author WillJoe
 *
 */
@SuppressWarnings("serial")
@Action("GroupMemberAction")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "GroupMemberAction"})
})
public class GroupMemberAction  extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{

	private static final long serialVersionUID = -2010621122837504304L;

	private Collection<GroupMember> wic;
	private Long id;
	private HttpServletRequest request;
    @Autowired
    private GroupMemberService groupMemberservice = new GroupMemberService();
	private GroupMember wi = new GroupMember();
    private GroupMemberService wis;
	
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
        wis.removeSujm(Long.parseLong(idStr));
    	
//    	wis.removeSujm(id);
        return "success";
    }

    /**
     * 数据新增提交
     * HTTP:POST方法
     * URL:/actionName
     */
    public HttpHeaders create() throws ParseException{
    	
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        if(request.getParameter("operate").equals("add")){
        	String a=  request.getParameter("custId");
//        	String b=  request.getParameter("appStatus");
        	String c=  request.getParameter("groupNo");
        	String d=  request.getParameter("parentId");
        	String e=  request.getParameter("relationId");
        	String f=  request.getParameter("memberType");
        	String g=  request.getParameter("stockRate");
        	String h=  request.getParameter("remark");
        	
        	
        	 JSONObject jsonObject =JSONObject.fromObject(a);
        	 JSONArray jarray =  jsonObject.getJSONArray("custId");
//        	 JSONObject jsonObject1 =JSONObject.fromObject(b);
//        	 JSONArray jarray1 =  jsonObject1.getJSONArray("appStatus");
        	 JSONObject jsonObject2 =JSONObject.fromObject(c);
        	 JSONArray jarray2 =  jsonObject2.getJSONArray("groupNo");
        	 JSONObject jsonObject3 =JSONObject.fromObject(d);
        	 JSONArray jarray3 =  jsonObject3.getJSONArray("parentId");
        	 JSONObject jsonObject4 =JSONObject.fromObject(e);
        	 JSONArray jarray4 =  jsonObject4.getJSONArray("relationId");
        	 JSONObject jsonObject5 =JSONObject.fromObject(f);
        	 JSONArray jarray5 =  jsonObject5.getJSONArray("memberType");
        	 JSONObject jsonObject6 =JSONObject.fromObject(g);
        	 JSONArray jarray6 =  jsonObject6.getJSONArray("stockRate");
        	 JSONObject jsonObject7 =JSONObject.fromObject(h);
        	 JSONArray jarray7 =  jsonObject7.getJSONArray("remark");
        	 groupMemberservice.batchSave(jarray,jarray2,jarray3,jarray4,jarray5,jarray6,jarray7);
		    return new DefaultHttpHeaders("success")
            .setLocationId(wi.getId());
        }
        else{
            if(request.getParameter("operate").equals("remove")){
            	String beforeRemoveId=  request.getParameter("beforeRemoveId");
            	String afterRemoveId=  request.getParameter("afterRemoveId");
            	wi.setId(Long.parseLong(beforeRemoveId));
            	wi.setParentId(afterRemoveId);
            	groupMemberservice.save1(wi,afterRemoveId);
    		    return new DefaultHttpHeaders("success")
                .setLocationId(wi.getId());
            }
        	{
    	wis.save(wi);
        return new DefaultHttpHeaders("success")
            .setLocationId(wi.getId());
        
        }
        }
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
