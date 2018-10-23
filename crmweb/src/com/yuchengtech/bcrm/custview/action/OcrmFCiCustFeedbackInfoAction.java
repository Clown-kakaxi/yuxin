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
import com.yuchengtech.bcrm.custview.model.OcrmFCiCustFeedbackInfo;
import com.yuchengtech.bcrm.custview.service.OcrmFCiCustFeedbackInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 客户反馈信息Action
 * @author YOYOGI
 * 2014-7-27
 */
@Action("/ocrmFCiCustFeedbackInfo")
public class OcrmFCiCustFeedbackInfoAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  OcrmFCiCustFeedbackInfoService ocrmFCiCustFeedbackInfoService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	public void init(){
	  	model = new OcrmFCiCustFeedbackInfo();
		setCommonService(ocrmFCiCustFeedbackInfoService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
	
	/**
	 * 信息查询SQL
	 */
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custNo=request.getParameter("custNo");
    	StringBuffer sb = new  StringBuffer("SELECT * FROM OCRM_F_CI_CUST_FEEDBACK_INFO FI WHERE FI.CUST_NO = '"+custNo +"'");
    	for(String key : this.getJson().keySet()){
    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
    			if("FEEDBACK_TYPE".equals(key)){
    				sb.append(" and FI."+ key +"='"+ this.getJson().get(key) +"'");
    			}
    			if("FEEDBACK_CONT".equals(key)){
    				sb.append(" and FI."+ key +"='"+ this.getJson().get(key) +"'");
    			}
    		}
    	}
    	SQL=sb.toString();
		datasource = ds;
		setPrimaryKey("FI.CUST_NO ");
    	configCondition("FEEDBACK_TITLE","like","FEEDBACK_TITLE",DataType.String);
		configCondition("FEEDBACK_NAIYOO","like","FEEDBACK_NAIYOO",DataType.String);
		configCondition("FEEDBACK_TIME","like","FEEDBACK_TIME",DataType.Date);
	}
	
	/**
	 * 数据保存
	 */
	public DefaultHttpHeaders saveData(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		if(((OcrmFCiCustFeedbackInfo)model).getId() == null){
			String custNo=request.getParameter("custNo");
			((OcrmFCiCustFeedbackInfo)model).setCustNo(custNo);
			ocrmFCiCustFeedbackInfoService.save(model);//小写, 即调用上述private 创建的service实例
		} else if (((OcrmFCiCustFeedbackInfo)model).getId()!= null){
			String id = ((OcrmFCiCustFeedbackInfo)model).getId().toString();
			String jql = "update OcrmFCiCustFeedbackInfo o  set o.feedbackTitle=:feedbackTitle, o.feedbackType=:feedbackType, o.feedbackCont=:feedbackCont, o.feedbackNaiyoo=:feedbackNaiyoo, o.feedbackTime=:feedbackTime where o.id='"+id+"'";
			Map<String,Object> values = new HashMap<String,Object>();
			values.put("feedbackTitle", ((OcrmFCiCustFeedbackInfo)model).getFeedbackTitle());
			values.put("feedbackType", ((OcrmFCiCustFeedbackInfo)model).getFeedbackType());
			values.put("feedbackCont", ((OcrmFCiCustFeedbackInfo)model).getFeedbackCont());
			values.put("feedbackNaiyoo", ((OcrmFCiCustFeedbackInfo)model).getFeedbackNaiyoo());
			values.put("feedbackTime", ((OcrmFCiCustFeedbackInfo)model).getFeedbackTime());
			ocrmFCiCustFeedbackInfoService.batchUpdateByName(jql, values);
		}
		return new DefaultHttpHeaders("success");
	}
	
	/**
	 * 删除
	 */
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	ocrmFCiCustFeedbackInfoService.batchDel(request);
		return new DefaultHttpHeaders("success");
    }
}
