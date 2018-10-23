package com.yuchengtech.bcrm.dynamicCrm.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.dynamicCrm.model.CustomerAttriConf;
import com.yuchengtech.bcrm.dynamicCrm.service.CustomerAttriTreeService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * 客户属性维护
 * @author 亮
 *
 */
@ParentPackage("json-default")
@Action("/customerAttriTree")
@SuppressWarnings("serial")
public class CustomerAttriTreeAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private CustomerAttriTreeService service;
	
	
	@Autowired
	public void init(){
		model = new CustomerAttriConf();
		setCommonService(service);
	}

	@Override
	public void prepare() {
		StringBuilder sb = new StringBuilder("");
		sb.append(" select t.ATTRI_ID, t.ATTRI_NAME, t.UP_ATTRI_ID, t.ATTRI_LEVEL, t.ATTRI_STATE, ");
		sb.append(" decode(a.ATTRI_NAME, '', '客户属性', null, '客户属性', a.ATTRI_NAME) AS ATTRI_PARENT_NAME ");
		sb.append(" from OCRM_F_CI_CUST_ATTRI_CONF t ");
		sb.append(" left join OCRM_F_CI_CUST_ATTRI_CONF a on a.ATTRI_ID=T.UP_ATTRI_ID ");
		sb.append(" where 1=1");		

		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String level = request.getParameter("level");
		if(level != null && !"".equals(level)){
			sb.append(" and t.ATTRI_LEVEL='" + level + "'");
		}
		
		SQL = sb.toString();
		setPrimaryKey("t.ATTRI_ID");
		datasource = ds;
	}
	
	/**
	 * 保存客户属性
	 */
	public DefaultHttpHeaders create() {
		try {
			CustomerAttriConf cac = (CustomerAttriConf) model;
			service.saveCustomerAttriConf(cac);
		} catch (BizException e) {
			throw e;
		}catch(Exception e){
    		e.printStackTrace();
    		throw new BizException(1, 2, "1002", e.getMessage());
    	}
		return new DefaultHttpHeaders("success").setLocationId(model);
	}
	
	/**
	 * 删除客户属性
	 * @param id
	 * @return 
	 */
	public String destroy(){
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		service.destroy(request);
		return "success";
	}

    public HttpHeaders getPid(){
    	try{
    		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		String pid = auth.getPid();
    		if(this.json != null){
    			this.json.clear();
    		}else{
    			this.json = new HashMap<String,Object>();  
    		}
    		this.json.put("pid", pid);
    		auth.setPid(null);
    	}catch(Exception e){
    		e.printStackTrace();
    		throw new BizException(1, 2, "1002", e.getMessage());
     	}
    	return new DefaultHttpHeaders("success").disableCaching();
	}
}
