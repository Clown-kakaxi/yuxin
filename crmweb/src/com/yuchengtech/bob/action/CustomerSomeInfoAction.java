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
import com.yuchengtech.bob.model.CustomerSomeInfo;
import com.yuchengtech.bob.service.CustomerSomeInfoService;


@SuppressWarnings("serial")
@Action("/customersomeinfo")
@Results({ @Result(name = "success", type = "redirectAction", params = {
        "actionName", "customersomeinfo" }) })
public class CustomerSomeInfoAction extends ValidationAwareSupport implements
        ModelDriven<Object>, Validateable {

    private CustomerSomeInfo csi = new CustomerSomeInfo();
    private Collection<CustomerSomeInfo> csic;
    private Long id;
    private HttpServletRequest request;

    @Autowired
    private CustomerSomeInfoService csis;

    /**
     * 请求数据编辑页面跳转。 HTTP:GET方法 URL:/actionName/$id/edit;
     */
    public String edit() {
        return "edit";
    }

    /**
     * 新增页面请求 HTTP:GET方法 URL:/actionName/new
     */
    public String editNew() {
        return "editNew";
    }

    /**
     * 请求删除页面 HTTP:GET方法 URL:/actionName/$id/deleteContirm
     */
    public String deleteConfirm() {
        return "deleteConfirm";
    }

    /**
     * 数据删除提交 HTTP:DELETE方法 URL:/actionName/$id
     */
    public String destroy() {
        /******************/
        ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);
        String idStr = request.getParameter("idStr");
        /******************/
        csis.remove(idStr);
        return "success";
    }

    /**
     * 数据新增提交 HTTP:POST方法 URL:/actionName
     */
    public HttpHeaders create() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        csi.setCUST_ID(request.getParameter("cid"));
        csis.save(csi);
        return new DefaultHttpHeaders("success").setLocationId(csi.getID());
    }

    /**
     * 数据修改提交 HTTP:PUT方法 URL:/WorkPlatNotice/$id
     */
    public String update() {
        csis.save(csi);
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
     * 
     */
    public Object getModel() {
        return (csic != null ? csic : csi);
    }
}
