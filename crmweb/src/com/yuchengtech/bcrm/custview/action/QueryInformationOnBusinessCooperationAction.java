package com.yuchengtech.bcrm.custview.action;

import java.text.ParseException;
import java.util.Calendar;

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
@Action(value="/queryinformationonbusinesscooperation", results={
    @Result(name="success", type="json")
})
public class QueryInformationOnBusinessCooperationAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	
	@Override
    public void prepare() {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String gp = "";//auth.getGpunitlevel();
        	ActionContext ctx = ActionContext.getContext();
        	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	 StringBuilder s = new StringBuilder("select  t2.unitname,t1.year_avg_cny,t1.profit,t1.crm_dt,trim(t1.zcflg) as zcflg,t1.agreement_id,t1.cust_id,t1.acc_no," +
        	 		"t1.acc_name,t1.org_no,trim(t1.currency) as currency,t1.bal,t1.cny_bal,t1.year_avg,t1.fact_int," +
        	 		"t1.accint,trim(t1.acc_sts) as acc_sts,t1.open_dt,t1.close_dt,t1.int_dt_ef,t1.int_dt_ee,t1.ftp,t1.ex_val," +
        	 		"t1.nor_intr,t1.acc_contribution,t1.sjno,trim(t1.incls) as incls,t1.acc_sts,t1.inmod,trim(t1.base_flg) as base_flg,t1.year_avg," +
        	 		"t1.fact_int,t1.accint,t1.acc_sts,t1.open_dt,t1.close_dt,t1.int_dt_ef,t1.int_dt_ee,t1.ftp," +
        	 		"t1.ex_val,t1.nor_intr,t1.acc_contribution,t1.sjno,t1.incls,t1.acc_sts,t1.inmod,t1.base_flg" +
        	 		" from " );
        	 	 	  for(String key:this.getJson().keySet()){
                           if(key.equals("crm_dt"))
							try {
								if(judge(this.getJson().get(key).toString()))
								      s.append("fdm.ACRM_F_AC_DEP_INFO ");
                     else {
								 s.append("fdm.ACRM_F_AC_DEP_INFO_S ");
                     }
							} catch (Exception e) {
								e.printStackTrace();
							}  
                      }
        	  s.append("t1 left join fdm.acrm_f_sm_sys_units_sta t2 on t1.org_no=t2.unitid  where  t1.cust_id='");
        	  s.append(request.getParameter("customerId")+"'");
        	  for(String key:this.getJson().keySet()){
                  if(!("").equals(this.getJson().get(key))){
                   if(key.equals("acc_sts"))
                          s.append(" and t1."+key+" ="+" '"+this.getJson().get(key)+"'");
                   else if((key.equals("crm_dt"))){
                   	 s.append(" and t1.crm_dt ="+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD')");
                   }  
              }}
        	  if(!("").equals(gp)){
        		  int i = Integer.parseInt(gp); 
        		  String level=String.valueOf(i-1);
        		  s.append (" and t1.org_no in (select unitid from fdm.acrm_f_sm_sys_units_sta where unitlevel>='"+gp+"' and level_"+level+"='"+auth.getUnitId()+"')");
        	  }
        	/*   int currentPage =this.getStart()/this.getLimit()+1;
               PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
        	   QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection(),pi); */
        	   setPrimaryKey("t1.agreement_id");
        	   /*HashMap<String, String> GreenplumMapping = new HashMap<String, String>();*/
               
//        	   addGreenplumLookup("zcflg", "ZDZC");
//        	   addGreenplumLookup("inmod", "JXFS");
//        	   addGreenplumLookup("incls", "JXZL");
//        	   addGreenplumLookup("base_flg", "SFWJBH");
//        	   addGreenplumLookup("acc_sts", "ZHZT");
//        	   addGreenplumLookup("currency", "CCY");
        	   //setBranchFileldName("t1.org_no");
        	 /*if (!GreenplumMapping.isEmpty()) {
                   for(Entry<String, String> item : GreenplumMapping.entrySet()) {
                       qh.addGreenplumLookup(item.getKey(), item.getValue());
                   }
               }
        	   setJson(qh.getJSON());
        return "success";*/
        	   SQL=s.toString();
   	           datasource = ds;
    }
	public boolean judge(String data) throws Exception{
		if(data.equals(getDate())&&!("").equals(data))
		return true; 
		else if(!data.equals(getDate())&&!("").equals(data))
		return false; 
		else if(("").equals(data))return true;
		else return true;
	}
	
    public String getDate() throws ParseException{   
  	   //DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");    
  	        Calendar ca = Calendar.getInstance();   
  	         int year = ca.get(Calendar.YEAR);//获取年份   
  	         int month=ca.get(Calendar.MONTH);//获取月份    
  	         int day=ca.get(Calendar.DATE);//获取日   
  	         String date = year + "-" + (month + 1 )+ "-" + (day-1);   
  	         
  	         //Date  date1 = format1.parse(date);  
  	         return date;   
     }
}






