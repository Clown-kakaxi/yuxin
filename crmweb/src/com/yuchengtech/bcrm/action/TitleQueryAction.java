package com.yuchengtech.bcrm.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.service.TitleQueryService;
import com.yuchengtech.bcrm.system.model.OcrmFSeTitle;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.crm.exception.BizException;

@SuppressWarnings("serial")
@Action("/TitleQuery")
public class TitleQueryAction extends CommonAction{
    @Autowired
    private TitleQueryService titleQueryService ;
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
	public void init(){
	  	model = new OcrmFSeTitle(); 
		setCommonService(titleQueryService);
	}
 	
 	public String loadTitleRs(){
 		json = titleQueryService.loadTitleRs();
 		return "success";
 	}
 	
    /**
	 * 查询当前可用的风险评估试题
	 * @return
	 */
	public HttpHeaders queryCustRiskQuestion() {
		try {
			ActionContext ctx = ActionContext.getContext();
			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			String paperId = request.getParameter("paperId");
			StringBuilder sb = new StringBuilder(" select s1.paper_name,                                  "+
					"        s2.paper_id,                                    "+
					"        s2.question_id,                                 "+
					"        s2.question_order,                              "+
					"        s3.title_id,                                    "+
					"        s3.title_name,                                  "+
					" s3.title_id||'_'||s3.result_id||'_'||s3.result_scoring as result_id,     "+
					"        s3.result,                                      "+
					"        s3.result_scoring                               "+
					"   from OCRM_F_SM_PAPERS s1                             "+
					"  inner join OCRM_F_SM_PAPERS_QUESTION_REL s2           "+
					"     on s2.paper_id = s1.id                             "+
					"  inner join (select t1.title_id,                       "+
					"                     t1.title_name,                     "+
					"                     t2.result_id,                      "+
					"                     t2.result,                         "+
					"                     t2.result_scoring                  "+
					"                from OCRM_F_SE_TITLE t1                 "+
					"                left join OCRM_F_SE_TITLE_RESULT t2     "+
					"                  on t1.title_id = t2.title_id) s3      "+
					"     on s2.question_id = s3.title_id                    "+
					"     where s2.paper_id = '"+paperId+"'                  "+
					"     order by s1.paper_name,s2.question_id,s3.title_id  ");
			if(this.json!=null){
        		this.json.clear();
			}else {
        		this.json = new HashMap<String,Object>(); 
        	}
			this.json.put("json",new QueryHelper(sb.toString(), ds.getConnection()).getJSON());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return new DefaultHttpHeaders("success").disableCaching();
	}
 	
 	public String loadTitleById(){
 		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String paperId = request.getParameter("paperId");
 		json = titleQueryService.loadTitleById(paperId);
 		return "success";
 	}
 	
 	public String loadResultById(){
 		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String Id = request.getParameter("SATISFY_ID");
		String paperId = request.getParameter("paperId");
 		json = titleQueryService.loadResultById(Id,paperId);
 		return "success";
 	}
 	
}

