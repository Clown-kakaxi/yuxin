package com.yuchengtech.bcrm.serviceManage.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.serviceManage.service.CustServiceManageService;
import com.yuchengtech.bob.common.CommonAction;

@SuppressWarnings("serial")
@Action("/custServiceQuery")
public class CustServiceQueryAction extends CommonAction {
	@Autowired
	private CustServiceManageService service;
	@Autowired
	public void init() {
		setCommonService(service);
	}
	/**
	 * 客户维系记录查询
	 * @return
	 */
	public HttpHeaders queryCsInfo(){
			ActionContext ctx = ActionContext.getContext();
		 	request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		 	this.setJson(request.getParameter("condition"));	
		 	StringBuilder jqlSb = new StringBuilder(" select t from CustServiceInfo t where 1=1 ");
		 	Map<String,Object> values = new HashMap<String, Object>();
		 	
		 	String custId = request.getParameter("custId");
		 	if(null != custId && !"".equals(custId)){
		 		String key = "custId";
		 		jqlSb.append(" and t."+key+" = :"+key);
		 		values.put(key, custId);
		 	}
	    	for(String key:this.getJson().keySet()){
				if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
					jqlSb.append(" and t."+key+" = :"+key);
	            	if("id".equalsIgnoreCase(key)){
	            		values.put(key, Integer.parseInt(this.getJson().get(key).toString()));
	            	}else{
	            		values.put(key, this.getJson().get(key));
	            	}
				}
			}
	    	String jql = jqlSb.toString();
	    	return super.indexPageByJql(jql, values);
	    	
	}
	/**
	 * 被动服务信息:客服中心业务处理单信息查询
	 * @return
	 */
	public HttpHeaders queryCscInfo(){
		 	ActionContext ctx = ActionContext.getContext();
		 	request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		 	this.setJson(request.getParameter("condition"));	
		 	StringBuilder jqlSb = new StringBuilder(" select t from OcrmFCscBusForm t where 1=1 ");
		 	Map<String,Object> values = new HashMap<String, Object>();
		 	
		 	String custId = request.getParameter("custId");
		 	if(null != custId && !"".equals(custId)){
		 		String key = "custId";
		 		jqlSb.append(" and t."+key+" = :"+key);
		 		values.put(key, custId);
		 	}
	    	for(String key:this.getJson().keySet()){
				if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
					jqlSb.append(" and t."+key+" = :"+key);
	            	if("id".equalsIgnoreCase(key)){
	            		values.put(key, Integer.parseInt(this.getJson().get(key).toString()));
	            	}else{
	            		values.put(key, this.getJson().get(key));
	            	}
				}
			}
	    	String jql = jqlSb.toString();
	    	return super.indexPageByJql(jql, values);
	}
	/**
	 * 被动服务信息:信用卡业务处理单信息
	 * @return
	 */
	public HttpHeaders queryCardInfo(){
		 	ActionContext ctx = ActionContext.getContext();
		 	request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		 	this.setJson(request.getParameter("condition"));	
		 	StringBuilder jqlSb = new StringBuilder(" select t from OcrmFCscCardForm t where 1=1 ");
		 	Map<String,Object> values = new HashMap<String, Object>();
		 	
		 	String custId = request.getParameter("custId");
		 	if(null != custId && !"".equals(custId)){
		 		String key = "custId";
		 		jqlSb.append(" and t."+key+" = :"+key);
		 		values.put(key, custId);
		 	}
	    	for(String key:this.getJson().keySet()){
				if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
					jqlSb.append(" and t."+key+" = :"+key);
	            	if("id".equalsIgnoreCase(key)){
	            		values.put(key, Integer.parseInt(this.getJson().get(key).toString()));
	            	}else{
	            		values.put(key, this.getJson().get(key));
	            	}
				}
			}
	    	
	    	String jql = jqlSb.toString();
	    	return super.indexPageByJql(jql, values);
	}
	/**
	 * 预约服务信息
	 * @return
	 */
	public HttpHeaders queryAppointmentInfo(){
		 	ActionContext ctx = ActionContext.getContext();
		 	request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		 	this.setJson(request.getParameter("condition"));	
		 	StringBuilder jqlSb = new StringBuilder(" select t from AppointmentInfo t where 1=1 ");
		 	Map<String,Object> values = new HashMap<String, Object>();
		 	
		 	String custId = request.getParameter("custId");
		 	if(null != custId && !"".equals(custId)){
		 		String key = "custId";
		 		jqlSb.append(" and t."+key+" = :"+key);
		 		values.put(key, custId);
		 	}
	    	for(String key:this.getJson().keySet()){
				if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
					jqlSb.append(" and t."+key+" = :"+key);
	            	if("id".equalsIgnoreCase(key)){
	            		values.put(key, Integer.parseInt(this.getJson().get(key).toString()));
	            	}else{
	            		values.put(key, this.getJson().get(key));
	            	}
				}
			}
	    	
	    	String jql = jqlSb.toString();
	    	return super.indexPageByJql(jql, values);
	}
	
}
