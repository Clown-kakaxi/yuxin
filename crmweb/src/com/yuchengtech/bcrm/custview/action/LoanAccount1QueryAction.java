package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiLoan1Act;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 贷款账户查询()
 * 
 * @author : dongyi
 * @date : 2014-07-26 
 */
@Action("/LoanAccount1Query")
public class LoanAccount1QueryAction extends CommonAction{
	

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
//    @Autowired
//    private RelaPartyInfoManagerService relapartyinfomangerservice;
    
    @Autowired
    public void init(){
        model = new AcrmFCiLoan1Act();
//        setCommonService(relapartyinfomangerservice);
    }
    
    public void prepare(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id =request.getParameter("CustId");
        StringBuffer sb1 = new StringBuffer("SELECT distinct t.*,t1.PROD_NAME FROM ACRM_F_CI_LOAN_ACT t left join OCRM_F_PD_PROD_INFO t1 on t.account_name=t1.product_id " +
        		" WHERE 1=1 and t.cust_id ='"+id+"' order by t.AMOUNT desc,t.LOAN_AVG_Y desc");
        SQL = sb1.toString();
        datasource =ds;
    }
}
