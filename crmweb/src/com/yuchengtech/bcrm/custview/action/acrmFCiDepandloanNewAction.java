package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiDepandloan;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 存贷比查询
 * 
 * @author : dongyi
 * @date : 2014-07-28 
 */
@Action("/acrmFCiDepandloanNew")
public class acrmFCiDepandloanNewAction extends CommonAction{
	

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
//    @Autowired
//    private RelaPartyInfoManagerService relapartyinfomangerservice;
    
    @Autowired
    public void init(){
        model = new AcrmFCiDepandloan();
//        setCommonService(relapartyinfomangerservice);
    }
    
    public void prepare(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	String ids =request.getParameter("CustId");
        StringBuffer sb1 = new StringBuffer("SELECT T.CUST_ID,CAST(TO_CHAR(T.SYNJCDB*100) AS NUMBER(10,1)) SYNJCDB," +
        		"CAST(TO_CHAR(T.BNNJCDB*100) AS NUMBER(10,1)) BNNJCDB,CAST(TO_CHAR(T.SYMCDB*100) AS NUMBER(10,1)) SYMCDB," +
        		"CAST(TO_CHAR(T.BNMCDB*100) AS NUMBER(10,1)) BNMCDB FROM ACRM_F_CI_DEPANDLOAN t WHERE 1=1  and  t.CUST_ID = '"+ids+"' ");
        for(String key:this.getJson().keySet()){
        }
        SQL = sb1.toString();
        datasource =ds;
    }
}
