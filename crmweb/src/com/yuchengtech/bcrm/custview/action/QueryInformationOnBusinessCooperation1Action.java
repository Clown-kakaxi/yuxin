package com.yuchengtech.bcrm.custview.action;

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
@Action(value="/queryinformationonbusinesscooperation1", results={
    @Result(name="success", type="json")
})
public class QueryInformationOnBusinessCooperation1Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
    @Override
    public void prepare() 
    {
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String gp = "";//auth.getGpunitlevel();
        ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        StringBuilder s = new StringBuilder("select t1.sub_acc,t2.unitname,t1.profit,t1.crm_dt,t1.agreement_id,t1.cust_id,t1.acc_no,t1.acc_name,t1.org_no,trim(t1.currency) as currency,t1.bal,t1.cny_bal,t1.year_avg,t1.fact_int,t1.accint,trim(t1.acc_sts) as acc_sts,t1.int_dt_ef,t1.int_dt_ee,t1.ftp,t1.ex_val,t1.nor_intr,t1.acc_contribution from fdm.acrm_f_ac_dep_sub_info t1 left join fdm.acrm_f_sm_sys_units_sta t2 on t1.org_no=t2.unitid where  t1.agreement_id='");
        s.append(request.getParameter("agreementId")+"'");
//               addGreenplumLookup("acc_sts", "ZHZT");
//               addGreenplumLookup("currency", "CCY");
        setPrimaryKey("t1.sub_agreement_id");
               //setBranchFileldName("t1.org_no");
        if(!("").equals(gp)){
        	int i = Integer.parseInt(gp); 
        	String level=String.valueOf(i-1);
        	s.append (" and t1.org_no in (select unitid from fdm.acrm_f_sm_sys_units_sta where unitlevel>='"+gp+"' and level_"+level+"='"+auth.getUnitId()+"')");
        }
        SQL=s.toString();
        datasource = ds;
    }
}






