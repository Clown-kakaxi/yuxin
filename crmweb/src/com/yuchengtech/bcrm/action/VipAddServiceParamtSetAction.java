package com.yuchengtech.bcrm.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.model.OcrmFCiVipaddparamSet;
import com.yuchengtech.bcrm.service.VipAddServiceParamtSetService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@Action("/vipAddServiceParamtSetAction")
public class VipAddServiceParamtSetAction  extends CommonAction{
    @Autowired
    private VipAddServiceParamtSetService vipAddServiceParamtSetService ;
    @Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性
    
    @Autowired
	public void init(){
	  	model = new OcrmFCiVipaddparamSet(); 
		setCommonService(vipAddServiceParamtSetService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
  //（自定义）批量删除
    public String batchDestroy(){
    	   	ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			String idStr = request.getParameter("idStr");
			String jql="delete from OcrmFCiVipaddparamSet c where c.id in ("+idStr+")";
			Map<String,Object> values=new HashMap<String,Object>();
			vipAddServiceParamtSetService.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
	        return "success";
    }
}