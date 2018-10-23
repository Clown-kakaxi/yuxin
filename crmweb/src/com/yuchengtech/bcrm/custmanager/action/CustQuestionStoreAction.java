package com.yuchengtech.bcrm.custmanager.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;

@SuppressWarnings("serial")
@Action("/custQuestionStoreAction")
public class CustQuestionStoreAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;  
	private HttpServletRequest request;
    @Autowired
 	
    public void prepare () {

    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	StringBuffer sb = new StringBuffer("  select B.*, t3.title_id,t4.paper_id,t5.result" +
        			" from (select count(distinct t1.cust_q_t_id) as count, t1.cust_select_content" +
        			" from ocrm_f_se_cust_risk_qa t1, ocrm_f_se_cust_risk_qa t2" +
        			" where t1.qa_title = t2.qa_title" +
        			" and t1.cust_select_content = t2.cust_select_content group by t1.cust_select_content  ) B" +
        			" left join ocrm_f_se_title_result t3 on t3.result_id =B.cust_select_content" +
        			" left join ocrm_f_sm_papers_question_rel t4 on t4.question_id=t3.title_id"+
        			" left join ocrm_f_se_title_result t5 on t5.result_id = B.cust_select_content"+
        			" where t4.paper_id= 1"
        			);

        	SQL=sb.toString();
        	datasource = ds;
        	try{
        		json=new QueryHelper(SQL, ds.getConnection()).getJSON();
        	}catch(Exception e){}
    }
}
