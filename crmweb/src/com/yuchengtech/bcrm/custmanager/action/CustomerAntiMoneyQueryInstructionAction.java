
package com.yuchengtech.bcrm.custmanager.action;


import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 
 * @description : 反洗钱指标查询-->>指标说明
 *
 * @author : zhaolong
 * @date : 2016-1-11 下午4:41:28
 */
@ParentPackage("json-default")
@Action(value = "/CustomerAntiMoneyQueryInstruction")
public class CustomerAntiMoneyQueryInstructionAction extends CommonAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 
	private HttpServletRequest request;
    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    public void prepare() {
        ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx
                .get(ServletActionContext.HTTP_REQUEST);
        String cust_id = request.getParameter("CUST_ID");
        StringBuilder sb = new StringBuilder("select i.*,a.user_name From " +
        		"ACRM_ANTI_INDEX_INSTRUCTION i " +
        		"inner join admin_auth_account a " +
        		"on a.account_name=i.instr_user " +
        		"where i.cust_id='"+cust_id+"'");
        SQL = sb.toString();
        setPrimaryKey("i.instr_time desc");
        datasource = ds;
    }
  
}

