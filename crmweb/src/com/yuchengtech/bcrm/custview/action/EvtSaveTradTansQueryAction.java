package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFEvtSaveTradTanInfo;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 存款账户查询
 * 
 * @author : dongyi
 * @date : 2014-07-25 
 */
@Action("/evtsavetradtansQuery")
public class EvtSaveTradTansQueryAction extends CommonAction{
	

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
//    @Autowired
//    private RelaPartyInfoManagerService relapartyinfomangerservice;
    
    @Autowired
    public void init(){
        model = new AcrmFEvtSaveTradTanInfo();
//        setCommonService(relapartyinfomangerservice);
    }
    
    public void prepare(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	String ids =request.getParameter("accountNo");
    	String flag = request.getParameter("flag");
    	String curr =request.getParameter("curr");
        StringBuffer sb1 = new StringBuffer("SELECT distinct t.* FROM ACRM_F_EVT_SAVE_TRAD_TANS t WHERE 1=1  and  T.ACCT in ('"+ids+"') order by t.trad_dt desc");
        for(String key:this.getJson().keySet()){
        }
        if("jgls".equals(flag)){
        	sb1.setLength(0);
        	sb1.append(
            		"SELECT distinct\n" +
            		"t.CUST_ID,\n" + 
            		"t.ACCT,\n" + 
            		"t.CURR,\n" + 
            		"t.TANS_NO,\n" + 
            		"t.TRAD_CHN,\n" + 
            		"t.TRAD_MONEY,\n" + 
            		"t.COST,\n" + 
            		"t.ACCOUNTIN_DATE,\n" + 
            		"t.TRAD_TIME,\n" + 
            		"t.HANDLER,\n" + 
            		"t.REVIEW,\n" + 
            		"t.TRAD_ABS,\n" + 
            		"t.TRAD_DT,\n" + 
            		"t.TRAD_TYPE,\n" + 
            		"t.CONTACT_TYPE\n" + 
            		"  from ACRM_F_EVT_SAVE_TRAD_TANS t\n" + 
            		"   WHERE 1=1  and  t.acct = '"+ids+"' and t.curr = '"+curr+"' order by t.trad_dt desc");
        }
        SQL = sb1.toString();
        datasource =ds;
    }
    
  //机构的历史交易流水
    public void OrgHistoryTrans(){
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	String ids =request.getParameter("accountNo");
    	String curr =request.getParameter("curr");
        StringBuffer sb1 = new StringBuffer(
        		"SELECT distinct\n" +
        		"t.CUST_ID,\n" + 
        		"t.ACCT,\n" + 
        		"t.CURR,\n" + 
        		"t.TANS_NO,\n" + 
        		"t.TRAD_CHN,\n" + 
        		"t.TRAD_MONEY,\n" + 
        		"t.COST,\n" + 
        		"t.ACCOUNTIN_DATE,\n" + 
        		"t.TRAD_TIME,\n" + 
        		"t.HANDLER,\n" + 
        		"t.REVIEW,\n" + 
        		"t.TRAD_ABS,\n" + 
        		"t.TRAD_DT,\n" + 
        		"t.TRAD_TYPE,\n" + 
        		"t.CONTACT_TYPE\n" + 
        		"  from ACRM_F_EVT_SAVE_TRAD_TANS t\n" + 
        		"   WHERE 1=1  and  t.acct = '"+ids+"' and t.curr = '"+curr+"' order by t.trad_dt desc");
     
        SQL = sb1.toString();
        datasource =ds;
    }   
}
