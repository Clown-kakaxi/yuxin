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
import com.yuchengtech.bob.model.CustomerEconomicCapitalCalculation;
import com.yuchengtech.bob.service.CustomerEconomicCapitalCalculationService;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 
 */
@SuppressWarnings("serial")
@Action("/custeconomiccaptlcalcu")
@Results({ @Result(name = "success", type = "redirectAction", params = {
        "actionName", "custeconomiccaptlcalcu" }) })
public class CustomerEconomicCapitalCalculationAction extends
        ValidationAwareSupport implements ModelDriven<Object>, Validateable {

    private CustomerEconomicCapitalCalculation cecc = new CustomerEconomicCapitalCalculation();
    private Collection<CustomerEconomicCapitalCalculation> ceccc;
    private Long id;
    private HttpServletRequest request;

    @Autowired
    private CustomerEconomicCapitalCalculationService ceccs=new CustomerEconomicCapitalCalculationService();

    /**
     * 单条数据展示. HTTP:GET方法 URL:/actionName/$id;
     */
    public HttpHeaders show() {
        cecc = ceccs.find(id);
        return new DefaultHttpHeaders("show");
    }

    /**
     * 数据列表查询包括全部数据，或者待条件查询。 HTTP:GET方法 URL:/actionName；
     */
    public HttpHeaders index() {
        ceccc = ceccs.findAll();
        return new DefaultHttpHeaders("index").disableCaching();
    }

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
        ceccs.remove(idStr);
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
//        String currenUserName = auth.getUsername();
        String orgId = auth.getUnitId();
        try {
            Date date=getCurrentDate();
            cecc.setCALCULATE_TIME(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cecc.setCUST_ID(currenUserId);
        cecc.setORG_ID(orgId);
        ceccs.save(cecc);
        return new DefaultHttpHeaders("success").setLocationId(cecc.getID());
    }

    /**
     * 数据修改提交 HTTP:PUT方法 URL:/WorkPlatNotice/$id
     */
    public String update() {
        ceccs.save(cecc);
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
            this.cecc = ceccs.find(id);
        }
        this.id = id;
    }

    /**
     * 
     */
    public Object getModel() {
        return (ceccc != null ? ceccc : cecc);
    }
}
