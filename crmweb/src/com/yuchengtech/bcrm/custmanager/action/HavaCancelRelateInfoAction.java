package com.yuchengtech.bcrm.custmanager.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
/**
 * @describtion: 注销状态为已注销的关联信息查询
 * 
 * @author : dongyi
 * @date : 2014-07-24 
 */
@Action("/HavaCacelRelateInfo")
public class HavaCancelRelateInfoAction extends CommonAction {
private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    public void prepare(){
    	 /**
         *  注销状态为已注销的关系人关系企业信息查询
         */
	  ActionContext ctx = ActionContext.getContext();
	  HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	  String KeyId= request.getParameter("MAIN_ID");
      StringBuffer sb2 =new StringBuffer ("select t.* from ACRM_F_CR_RELATE_PRIVY_INFO t WHERE 1=1  and t.CANCEL_STATE='2'  "	);
      if(KeyId!=null){
    	  sb2.append(" AND t.MAIN_ID = '"+KeyId+"'");
      }
      SQL = sb2.toString();
  	  datasource =ds;
  	
    }
}