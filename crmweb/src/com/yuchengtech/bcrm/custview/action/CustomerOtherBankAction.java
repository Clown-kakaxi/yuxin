package com.yuchengtech.bcrm.custview.action;

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
import com.yuchengtech.bcrm.custview.model.CustomerOtherBank;
import com.yuchengtech.bcrm.custview.service.CustomerOtherBankService;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describe 客户他行信息
 * 
 */
@SuppressWarnings("serial")
@Action("/customerotherbank")
@Results({ @Result(name = "success", type = "redirectAction", params = {
        "actionName", "customerotherbank" }) })
public class CustomerOtherBankAction extends ValidationAwareSupport implements
        ModelDriven<Object>, Validateable {

    private CustomerOtherBank cob = new CustomerOtherBank();
    private Collection<CustomerOtherBank> cobc;
    private Long id;
    private HttpServletRequest request;

    @Autowired
    private CustomerOtherBankService cobs;

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
        cobs.remove(idStr);
        return "success";
    }
    
  //获取系统当前时间
    public Date getCurrentDate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
        String date = format.format(new java.util.Date()).toString();
        return (Date) format.parse(date);

    }
    
    /**
     * 数据新增提交 HTTP:POST方法 URL:/actionName
     */
    public HttpHeaders create() {
        AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
        String currenUserName=auth.getCname();
        try {
            Date date=getCurrentDate();
            cob.setUPDT_DT(date);//自动设getCname置维护时间
        } catch (Exception e) {
            e.printStackTrace();
        }
        cob.setUSERNAME(currenUserName);
        cob.setUSERID(currenUserId);//自动设置维护人员
        cobs.save(cob);
        return new DefaultHttpHeaders("success").setLocationId(cob.getID());
    }

    /**
     * 数据修改提交 HTTP:PUT方法 URL:/WorkPlatNotice/$id
     */
    public String update() {
        cobs.save(cob);
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
        return (cobc != null ? cobc : cob);
    }
}
