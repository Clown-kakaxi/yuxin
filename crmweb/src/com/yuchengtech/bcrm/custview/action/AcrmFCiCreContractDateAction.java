package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/AcrmFCiCreContractDate")
public class AcrmFCiCreContractDateAction extends CommonAction{
	    @Autowired
	    @Qualifier("dsOracle")
	    private DataSource ds;
	    
	    public void prepare(){
	        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        ActionContext ctx = ActionContext.getContext();
	    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	    	String sublimiIds =request.getParameter("SubLimitId");
	        StringBuffer sb1 = new StringBuffer("SELECT distinct t.*,t1.BAK1  FROM ACRM_F_CI_CRE_CONTRACT_STA_DT t  " +
								        		"left join acrm_f_ci_cre_contract t1 on t.sub_limit_id = t1.sub_limit_id  " +
								        		"WHERE 1 = 1  and t.SUB_limit_id = '"+ sublimiIds+"'   and t1.BAK1 = '1'  order by t.Credit_NO desc");
	        SQL = sb1.toString();
	        datasource =ds;
	    }   

}
