package com.yuchengtech.bcrm.custview.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.OcrmFCiOrgPrjInfo;
import com.yuchengtech.bcrm.custview.service.OcrmFCiOrgPrjInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @description 对公客户项目信息
 * @author likai
 * @since 2014/07/26
 *
 */
@Action("/ocrmFCiOrgPrjInfo")
public class OcrmFCiOrgPrjInfoAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  OcrmFCiOrgPrjInfoService  ocrmFCiOrgPrjInfoService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	@Autowired
	public void init(){
	  	model = new OcrmFCiOrgPrjInfo(); 
		setCommonService(ocrmFCiOrgPrjInfoService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
	
	/**
	 *信息查询SQL
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId=request.getParameter("custId");
		StringBuilder sb = new StringBuilder(" select * from OCRM_F_CI_ORG_PRJ_INFO where CUST_ID= '"+custId+"'");
		SQL=sb.toString();
		datasource = ds;
		setPrimaryKey("CREAT_DATE desc");
		configCondition("PRJ_NAME", "like", "PRJ_NAME",DataType.String);
	}
	
	/**
	 * 数据保存
	 */
	public DefaultHttpHeaders saveData(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 if(((OcrmFCiOrgPrjInfo)model).getId() == null){
    		String custId=request.getParameter("custId");
 			((OcrmFCiOrgPrjInfo)model).setCustId(custId);
 			((OcrmFCiOrgPrjInfo)model).setUserId(auth.getUserId());
 			ocrmFCiOrgPrjInfoService.save(model);
    	 } else if(((OcrmFCiOrgPrjInfo)model).getId()!=null){
    		String id = ((OcrmFCiOrgPrjInfo)model).getId().toString();
 			String jql = "update  OcrmFCiOrgPrjInfo p set p.prjName=:prjName,p.userName=:userName,p.creatDate=:creatDate,p.orgName=:orgName,p.userId=:userId,p.orgId=:orgId where p.id='"+id+"'";
 	        Map<String,Object> values = new HashMap<String,Object>();
 	        values.put("prjName",((OcrmFCiOrgPrjInfo)model).getPrjName());
 	        values.put("userName",((OcrmFCiOrgPrjInfo)model).getUserName());
 	        values.put("creatDate",((OcrmFCiOrgPrjInfo)model).getCreatDate());
 	        values.put("orgName",((OcrmFCiOrgPrjInfo)model).getOrgName());
 	        values.put("userId",auth.getUserId());
 	        values.put("orgId",auth.getUnitId());
 	        ocrmFCiOrgPrjInfoService.batchUpdateByName(jql, values);
    	 }
		return new DefaultHttpHeaders("success");
	}
	
	//删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	ocrmFCiOrgPrjInfoService.batchDel(request);
		return new DefaultHttpHeaders("success");
    }
}
