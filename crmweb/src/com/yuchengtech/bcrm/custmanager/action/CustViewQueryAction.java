package com.yuchengtech.bcrm.custmanager.action;

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
/**
 * 客户经理业务全景视图-概览信息
 * @author songxs
 * @since 2012-12-12
 *
 */
@ParentPackage("json-default")
@Action(value="/custViewQuery-Action", results={
    @Result(name="success", type="json")
})
public class CustViewQueryAction extends CommonAction{
    
	@Autowired
    @Qualifier("dsOracle")
	private DataSource ds;
	
	private HttpServletRequest request;
	
	@Override
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String mrgid = request.getParameter("custManagerId");
        	StringBuilder s = new StringBuilder(
        			"select count(c.id) as cust_Num," +
        			"count(case when c.main_type = 1 then 'mainCust'end) MAIN_CUST_NUM,  count(case" +
        			" when c.main_type = 2 then 'omainCust' "+
        			"  end) OMAIN_CUST_NUM," +
        			"  sum(t1.cur_year_avg) as save_year_avg,"+
        			"  sum(t1.cur_ac_bl) as save_bl," +
        			"  sum(t2.cur_year_avg) as loan_year_avg," +
        			"  sum(t2.cur_ac_bl) as loan_bl,10200 as new_create_cust ," +
        			"  6600000000 as CUST_AUM" +
        			"  from OCRM_F_CI_BELONG_CUSTMGR c "+
        			"  left join ACRM_F_CI_GK_SAVE t1 on t1.cust_id = c.cust_id " +
        			"  left join ACRM_F_CI_GK_LOAN t2 on t2.cust_id = c.cust_id "+
        			"  where c.mgr_id = '"+ mrgid+"' "

        	);
        
        	  SQL=s.toString();
  	           datasource = ds;
    }
}