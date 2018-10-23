package com.yuchengtech.bcrm.custmanager.action;

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
import com.yuchengtech.bcrm.custmanager.model.TeamCustomerManager;
import com.yuchengtech.bcrm.custmanager.service.TeamCustomerManagerService;

/**
 * @describe 资讯服务导航
 * @author WillJoe
 *
 */
@SuppressWarnings("serial")
@Action("TeamCustomerManager")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "TeamCustomerManager"})
})
public class TeamCustomerManagerAction  extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{

	private static final long serialVersionUID = -2010621122837504304L;

	private Collection<TeamCustomerManager> wic;
	private Long id;
	private HttpServletRequest request;
    @Autowired
    private TeamCustomerManagerService teamCustomerManagerService = new TeamCustomerManagerService();
	private TeamCustomerManager wi = new TeamCustomerManager();
    private TeamCustomerManagerService wis;
   
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
     * 数据新增提交
     * HTTP:POST方法
     * URL:/actionName
     */
    public HttpHeaders create() throws ParseException{
    	
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        if (request.getParameter("operate").equals("add")) {
			String a = request.getParameter("userId");
			String b = request.getParameter("marketTeamId");

			JSONObject jsonObject = JSONObject.fromObject(a);
			JSONArray jarray = jsonObject.getJSONArray("userId");
			JSONObject jsonObject1 = JSONObject.fromObject(b);
			JSONArray jarray1 = jsonObject1.getJSONArray("marketTeamId");
			teamCustomerManagerService.batchSave(jarray, jarray1);
			return new DefaultHttpHeaders("success").setLocationId(wi.getId());
		}

		if (request.getParameter("operate").equals("delete")) {
			String s = request.getParameter("cbid");
			JSONObject jsonObject = JSONObject.fromObject(s);
			JSONArray jarray = jsonObject.getJSONArray("id");
			teamCustomerManagerService.remove(jarray);
		} 
        else{
//    	wis.save1(wi);
       
        }
		 return new DefaultHttpHeaders("success")
         .setLocationId(wi.getId());
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
     * 
     */
    public Object getModel() {
        return (wic != null ? wic : wi);
    }
    


}
