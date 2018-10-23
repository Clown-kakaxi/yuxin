package com.yuchengtech.bob.action;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bob.model.CustomerDepositProfit;
import com.yuchengtech.bob.service.CustomerDepositProfitService;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describe 存款测算
 * 
 */
@SuppressWarnings("serial")
@Action("/customerdepositprofit")
@Results({ @Result(name = "success", type = "redirectAction", params = {
        "actionName", "customerdepositprofit" }) })
public class CustomerDepositProfitAction extends ValidationAwareSupport
        implements ModelDriven<Object>, Validateable {

    private CustomerDepositProfit cdp = new CustomerDepositProfit();
    private Collection<CustomerDepositProfit> cdpc;
    private Long id;
    private HttpServletRequest request;

    @Autowired
    private CustomerDepositProfitService cdps;

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
        cdps.remove(idStr);
        return "success";
    }

    // 获取系统当前时间
    public Date getCurrentDate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
        String date = format.format(new java.util.Date()).toString();
        return (Date) format.parse(date);

    }

    /**
     * 数据新增提交 HTTP:POST方法 URL:/actionName
     */
    public HttpHeaders create() {
        AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
        // String currenUserName = auth.getUsername();
        String orgId = auth.getUnitId();
        try {
            Date date = getCurrentDate();
            cdp.setCALCULATE_TIME(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cdp.setORG_ID(orgId);
        cdp.setCUST_ID(currenUserId);
        cdps.save(cdp);
        return new DefaultHttpHeaders("success").setLocationId(cdp.getID());
    }

    /**
     * 数据修改提交 HTTP:PUT方法 URL:/WorkPlatNotice/$id
     */
    public String update() {
        cdps.save(cdp);
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
     * 
     * @param id
     */
    public void setId(Long id) {
        if (id != null) {
            this.cdp = cdps.find(id);
        }
        this.id = id;
    }

    /**
     * 
     */
    public Object getModel() {
        return (cdpc != null ? cdpc : cdp);
    }
}
