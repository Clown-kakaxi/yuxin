package com.yuchengtech.bcrm.custmanager.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.service.CustQuestionService;
import com.yuchengtech.bcrm.system.model.OcrmFSeTitle;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

@SuppressWarnings("serial")
@Action("/CustQuestionAction")
public class CustQuestionAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;  
	private HttpServletRequest request;
    @Autowired
    private CustQuestionService custQuestionService ;
    @Autowired
	public void init(){
	  	model = new OcrmFSeTitle(); 
		setCommonService(custQuestionService);
	}
 	
 	public String loadTitleRs(){
 		 ActionContext ctx = ActionContext.getContext();
         request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
         String paperId = request.getParameter("paperId"); 
         if(!paperId.equals("")){
        		json = custQuestionService.loadTitleRs(paperId);
        		return "success";
         }
		return null;
         
 	}
    public void prepare () {

    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currendOrgId = auth.getUnitId();
        	StringBuffer sb = new StringBuffer("select t.* from ocrm_f_se_title t where t.title_id in "+
        			"(select r.question_id from ocrm_f_sm_papers_question_rel r where r.paper_id =1)");
        	
        	SQL=sb.toString();
        	datasource = ds;
        	try{
        		json=new QueryHelper(SQL, ds.getConnection()).getJSON();
        	}catch(Exception e){}
    }
}
