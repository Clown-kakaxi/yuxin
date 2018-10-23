package com.yuchengtech.bcrm.action;

import java.sql.Timestamp;
import java.util.Date;
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
import com.yuchengtech.bcrm.customer.model.OcrmFCiReviewContent;
import com.yuchengtech.bcrm.customer.service.WfCommentService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/wfComment")
public class WfCommentAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  WfCommentService  service;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	public void init(){
	  	model = new OcrmFCiReviewContent(); 
		setCommonService(service);
		needLog=true;
	}
	
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String taskNumber=request.getParameter("taskNumber");
		StringBuffer sb=new StringBuffer("select   t.id,t.task_number,t.username,t.commentcontent ,to_char(t.commenttime,'yyyy-mm-dd HH24:mi:ss') as commenttime from OCRM_F_CI_REVIEW_CONTENT t   where t.TASK_NUMBER = '"+taskNumber+"' and t.commentcontent is not null order by t.commenttime desc");
				
		SQL=sb.toString();
		datasource=ds;
	}
	
	public DefaultHttpHeaders save(){
		 ActionContext ctx = ActionContext.getContext();
		 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   		 String taskNumber=request.getParameter("taskNumber");
   		 String commentContent=request.getParameter("commentContent");
   		 String id=request.getParameter("id");
		((OcrmFCiReviewContent)model).setTaskNumber(taskNumber);
		((OcrmFCiReviewContent)model).setCommentcontent(commentContent);
		((OcrmFCiReviewContent)model).setUsername(auth.getUsername());
		((OcrmFCiReviewContent)model).setCommenttime(new Timestamp(System.currentTimeMillis()));
		service.save(model);
		return new DefaultHttpHeaders("success");
	}
	
}
