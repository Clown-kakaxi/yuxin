package com.yuchengtech.bcrm.custview.action;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/queryinformationonbusinesscooperation4", results={
    @Result(name="success", type="json")
})
public class QueryInformationOnBusinessCooperation4Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		StringBuilder s = new StringBuilder("select t2.unitname,t1.*,trim(t1.crm_alp0mdco)as crm_alp0mdco_1 from fdm.ACRM_F_COR_ALPFP0 t1 left join  fdm.acrm_f_sm_sys_units_sta t2 on t1.alp0dpid=t2.unitid  where  t1.agreement_id='");
		s.append(request.getParameter("agreementId")+"'");
//      addGreenplumLookup("crm_alp0mdco_1", "BGYY");
		SQL=s.toString();
		datasource = ds;
    }
}



