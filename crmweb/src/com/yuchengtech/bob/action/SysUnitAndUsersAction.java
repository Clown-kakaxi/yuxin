package com.yuchengtech.bob.action;

import java.util.Collection;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bob.model.SystemUnitAndUsers;
import com.yuchengtech.bob.service.SystemUnitAndUsersService;

@Action("/sysunitandusers")
@Results({ @Result(name = "success", type = "redirectAction", params = {
        "actionName", "sysunitandusers" }) })
public class SysUnitAndUsersAction extends ValidationAwareSupport implements
        ModelDriven<Object>, Validateable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private SystemUnitAndUsers wri = new SystemUnitAndUsers();
    private Collection<SystemUnitAndUsers> wric;
    private Long id;
    private String userid;

    @Autowired
    private SystemUnitAndUsersService wris;

    /**
     * ID参数获取方法
     * 
     * @param userid
     */
    public void setid(Long id) {
        if (id != null) {
            this.wri = wris.find(id);
        }
        this.id = id;
    }
//    
//    public void setuserid(String userid){
//        if (userid != null) {
//            this.wri = 
//        }
//    }

    /**
     * 单条数据展示. HTTP:GET方法 URL:/actionName/$userid;
     */
    public HttpHeaders show() {
        wri = wris.findUnitsByUserid(userid);
        return new DefaultHttpHeaders("show");
    }

    /**
     * 数据列表查询包括全部数据，或者待条件查询。
     *  HTTP:GET方法 
     *  URL:/actionName；
     */
    public HttpHeaders index(){
        wric = wris.getUnitsByUserid(userid);
        return new DefaultHttpHeaders("index").disableCaching();
    }
    
    /**
     * 数据列表查询包括全部数据，或者待条件查询。 HTTP:GET方法 URL:/actionName；
     */

    /**
     * 请求数据编辑页面跳转。 HTTP:GET方法 URL:/actionName/$id/edit;
     */
    public String edit() {
        return "edit";
    }

    /**
     * 新增页面请求 HTTP:GET方法 URL:/actionName/new
     */
    public String editnew() {
        return "editnew";
    }

    /**
     * 请求删除页面 HTTP:GET方法 URL:/actionName/$id/deleteContirm
     */
    public String deleteConfirm() {
        return "deleteConfirm";
    }

    /**
     * 数据删除提交 HTTP:DELETE方法 URL:/actionName/$userid
     */
    public String destroy() {
        wris.remove(id);
        return "success";
    }

    /**
     * 数据新增提交 HTTP:POST方法 URL:/actionName
     */
    public HttpHeaders create() {
        wris.save(wri);
        return new DefaultHttpHeaders("success").setLocationId(wri.getId());
    }

    /**
     * 数据修改提交 HTTP:PUT方法 URL:/WorkPlatNotice/$id
     */
    public String put() {
        wris.save(wri);
        return "success";
    }

    public void validate() {
        // TODO Auto-generated method stub

    }

    public Object getModel() {
        // TODO Auto-generated method stub
        return (wric != null ? wric : wri);
    }

}
