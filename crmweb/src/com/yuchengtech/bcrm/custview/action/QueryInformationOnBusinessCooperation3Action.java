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
@Action(value="/queryinformationonbusinesscooperation3", results={
    @Result(name="success", type="json")
})
public class QueryInformationOnBusinessCooperation3Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;

	  @Override
		 public void prepare() {
        	ActionContext ctx = ActionContext.getContext();
        	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	 StringBuilder s = new StringBuilder("select t2.unitname,t1.* from fdm.v_acrm_f_ev_ac_dep t1 left join fdm.acrm_f_sm_sys_units_sta t2 on t1.org_no=t2.unitid where  t1.agreement_id='");
        	  s.append(request.getParameter("agreementId")+"'");
        	  for(String key:this.getJson().keySet()){
                  if(!("").equals(this.getJson().get(key))){
                   if(key.equals("acc7date_start"))
                          s.append(" and t1.tran_date >="+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD') ");
                   else if(key.equals("acc7date_end"))
                       s.append(" and t1.tran_date <="+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD') ");
                  /* else if((key.equals("BGN_DT_BEFORE"))){
                   	s.append(" and t1.BGN_DT =>"+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD') ");
                   } */ 
              }}
        	  /* int currentPage =this.getStart()/this.getLimit()+1;
               PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
        	   QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection(),pi); */
        	   setPrimaryKey("t1.event_id");
        	/*   setJson(qh.getJSON());
        return "success";*/
        	   SQL=s.toString();
   	           datasource = ds;
    }
}







