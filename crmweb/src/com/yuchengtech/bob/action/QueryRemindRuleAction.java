package com.yuchengtech.bob.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value = "/queryremindrule", results = { @Result(name = "success", type = "json") })
public class QueryRemindRuleAction extends CommonAction {

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    private HttpServletRequest request;

    public void prepare() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
        String sectionType = request.getParameter("sectionType");
        String ruleRole = request.getParameter("ruleRole");

        StringBuilder sb = new StringBuilder(
                "select wrr.* from ocrm_f_wp_remind_rule wrr where wrr.creator = '"
                        + currenUserId + "' ");
        if(!"".equals(sectionType)&&sectionType!=null){
        	sb.append(" and section_Type ='"+sectionType+"'");
        }
        if(!"".equals(ruleRole)&&ruleRole!=null){
        	sb.append(" and rule_Role ='"+ruleRole+"'");
        }

        SQL = sb.toString();
        setPrimaryKey("wrr.SECTION_TYPE");
        addOracleLookup("SECTION_TYPE", "REMIND_TYPE");
        addOracleLookup("RULE_NAME", "GROUP_STS");
        datasource = ds;
    }

}
