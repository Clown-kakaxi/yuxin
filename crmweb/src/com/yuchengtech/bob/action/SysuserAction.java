package com.yuchengtech.bob.action;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.yuchengtech.bob.model.SystemUsers;
import com.yuchengtech.bob.service.SystemUserService;


@Action("/sysuser")
@Results({
    @Result(name="success", type="redirectAction", params = {"actionName" , "sysuser"})
})

public class SysuserAction  extends ValidationAwareSupport implements ModelDriven<Object>, Validateable{
	
	private static final long serialVersionUID = 1L;
	private SystemUsers model = new SystemUsers();
    private Long id;
    private Collection<SystemUsers> list;


    @Resource
    private SystemUserService sysservice;
    
	public Object getModel() {
		 return (list != null ? list : model);
	}
	
	/**
     * 数据列表查询包括全部数据，或者待条件查询。
     *  HTTP:GET方法 
     *  URL:/actionName；
     */
    public HttpHeaders index() {
        list = sysservice.findAll();
        return new DefaultHttpHeaders("index")
            .disableCaching();
    }

    /**
     * 单条数据展示.
     * HTTP:GET方法
     * URL:/actionName/$id;
     */
    public HttpHeaders show() {
        return new DefaultHttpHeaders("show");
    }
    
    /**
     * 请求数据编辑页面跳转。
     * HTTP:GET方法
     * URL:/actionName/$id/edit;
     */
    public String edit(){
        return "edit";
    }
    
    /**
     * 新增页面请求
     * HTTP:GET方法
     * URL:/actionName/new
     */
    public String editnew(){
        return "editnew";
    }
    
    /**
     * 请求删除页面
     * HTTP:GET方法
     * URL:/actionName/$id/deleteContirm
     */
    public String deleteConfirm(){
        return "deleteConfirm";
    }
    
    /**
     * 数据删除提交
     * HTTP:DELETE方法
     * URL:/actionName/$id
     */
    public String destroy(){
        sysservice.remove(id);
        return "success";
    }
    
    /**
     * 数据新增提交
     * HTTP:POST方法
     * URL:/actionName
     */
    public HttpHeaders create(){
        sysservice.save(model);
        return new DefaultHttpHeaders("success").setLocationId(model.getId());
    } 
    
    /**
     * 数据修改提交
     * HTTP:PUT方法
     * URL:/WorkPlatNotice/$id
     */
    public String update(){
        sysservice.save(model);
        return "success";
    }
    
    /**
     * ID参数获取方法
     * @param id
     */
    public void setId(Long id) {
        if (id != null) {
            this.model = sysservice.find(id);
        }
        this.id = id;
    }


	public void validate() {
	}
	
	




}
