package com.yuchengtech.bcrm.customer.customerMktTeam.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerMktTeam.model.OcrmFCmMktTeam;
import com.yuchengtech.bcrm.customer.customerMktTeam.service.CustomerMktTeamService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * @description:客户经理团队管理成员客户信息
 * @author xiebz
 * @data 2014-07-02
 */
@ParentPackage("json-default")
@Action("/customerMktTeam")
public class CustomerMktTeamAction extends CommonAction{
	private static final long serialVersionUID = -1307317536382455940L;

	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
    
    @Autowired
    private CustomerMktTeamService service;
    
    @Autowired
    public void init(){
        model = new OcrmFCmMktTeam();
        setCommonService(service);
    }
	
	public void prepare() {
		
 		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String mktTeamId =request.getParameter("mktTeamId");//团队mktTeamId
        StringBuilder sb = new StringBuilder("select c.cust_id,c.cust_name,c.ident_type,c.ident_no,c.cust_type,c.cust_level,c.current_aum,c.total_debt," +
				"c.linkman_name,c.linkman_tel,m.institution_name as org_name,m.mgr_name "+
				" from ACRM_F_CI_CUSTOMER c " +
				" left join OCRM_F_CI_BELONG_CUSTMGR m on c.cust_id = m.cust_id" +
				" left join ocrm_f_cm_team_cust_manager tm on tm.cust_manager_id = m.mgr_id" +
				" where 1=1" +
				" and tm.mkt_team_id = '"+mktTeamId+"' and tm.cust_manager_state = '2' ");
        
        addOracleLookup("CUST_TYPE", "XD000080");
        addOracleLookup("CUST_LEVEL", "P_CUST_GRADE");
        addOracleLookup("CERT_TYPE", "PAR0100006");
        addOracleLookup("CUST_STAT","XD000081");
        addOracleLookup("IDENT_TYPE","XD000040");
        setPrimaryKey("c.cust_id DESC");
		SQL = sb.toString();
		datasource = ds;
	}
}
