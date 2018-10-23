package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiCreContractStaDt;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 子额度起始日查询
 * 
 * @author : dongyi
 * @date : 2014-07-27 
 */
@Action("/AcrmFCiCreContractStaDt")
public class AcrmFCiCreContractStaDtAction extends CommonAction{
	

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
//    @Autowired
//    private RelaPartyInfoManagerService relapartyinfomangerservice;
    
    @Autowired
    public void init(){
        model = new AcrmFCiCreContractStaDt();
//        setCommonService(relapartyinfomangerservice);
    }
    
    public void prepare(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	String sublimiIds =request.getParameter("SubLimitId");
    	String creditno =request.getParameter("Credit_NO");
        StringBuffer sb1 = new StringBuffer("SELECT distinct t.* FROM ACRM_F_CI_CRE_CONTRACT_STA_DT t WHERE 1=1  and  t.SUB_limit_id = "+sublimiIds+" and  t.Credit_NO= "+creditno+"order by t.CONT_START_DT desc");
        for(String key:this.getJson().keySet()){
        }
        SQL = sb1.toString();
        datasource =ds;
    }
}
