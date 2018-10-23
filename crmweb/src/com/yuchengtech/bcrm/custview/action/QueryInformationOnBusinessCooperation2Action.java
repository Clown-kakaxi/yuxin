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
@Action(value="/queryinformationonbusinesscooperation2", results={
    @Result(name="success", type="json")
})
public class QueryInformationOnBusinessCooperation2Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	
    @Override
    public void prepare() 
	{       AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String gp = "";//auth.getGpunitlevel();
        	ActionContext ctx = ActionContext.getContext();
        	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        	 StringBuilder s = new StringBuilder("select t2.unitname,t1.crm_dt,t1.sub_agreement_id," +
        	 		"t1.cust_id,t1.aa20acno,t1.aa20acna,t1.aa20dpid,trim(t1.aa20cu)as aa20cu," +
        	 		"t1.aa20sjno,t1.balance1,t1.balance_cny,t1.year_avg,t1.year_avg_cny," +
        	 		"t1.al30ocam,t1.aieaccrdt,trim(t1.aa20incls) as aa20incls," +
        	 		"trim(t1.aa20inmod)as aa20inmod,t1.aln0itr,t1.open_date,t1.act4date," +
        	 		"t1.aa20datef,t1.aa20datet,trim(t1.aa20acsts) as aa20acsts,t1.serialno," +
        	 		"t1.actualputoutdate,t1.maturity,t1.actualmaturity,trim(t1.conveyreturnflag) as conveyreturnflag," +
        	 		"t1.ftp,t1.abk0bar,t1.capital_occupancy,t1.contribution_a,t1.contribution_b," +
        	 		"t1.profit,t1.class_four,t1.rateterm,t1.ratechangetype,t1.finerateratio," +
        	 		"t1.aln0acnoy,t1.agreement_id  " +
        	 		"from ");
        	  for(String key:this.getJson().keySet()){
                  if(key.equals("crm_dt"))
					try {
						if(judge(this.getJson().get(key).toString()))
						      s.append("fdm.acrm_f_ac_lon_bus_cntrct_sub_d ");
            else {
						 s.append("fdm.acrm_f_ac_lon_bus_cntrct_sub_s ");
            }
					} catch (Exception e) {
						e.printStackTrace();
					}  
             }
        	 s.append("t1 left join fdm.acrm_f_sm_sys_units_sta t2 on t1.aa20dpid=t2.unitid where t1.cust_id='");
        	  s.append(request.getParameter("customerId")+"'");
        	  for(String key:this.getJson().keySet()){
                  if(!("").equals(this.getJson().get(key))){
                   if(key.equals("aa20acsts"))
                          s.append(" and t1."+key+" ="+" '"+this.getJson().get(key)+"'");
                   else if((key.equals("crm_dt"))){
                   	 s.append(" and t1.crm_dt ="+"to_date('"+this.getJson().get(key)+"', 'YYYY-MM-DD')");
                   }  
              }}
        	   //int currentPage =this.getStart()/this.getLimit()+1;
              // PagingInfo pi = new PagingInfo(this.getLimit(),currentPage);
        	   //QueryHelper qh = new QueryHelper(s.toString(), ds.getConnection(),pi); 
        	   setPrimaryKey("t1.cust_id");
      /*  	   addGreenplumLookup
        	   greenplumMapping
               HashMap<String, String> GreenplumMapping = new HashMap<String, String>();*/
               
//        	   addGreenplumLookup("aa20inmod", "JXFS");
//        	   addGreenplumLookup("aa20incls", "JXZL");
//        	   addGreenplumLookup("conveyreturnflag", "WJFLJG");
//        	   addGreenplumLookup("aa20acsts", "ZHZT");
//        	   addGreenplumLookup("aa20cu", "CCY");
        	   //setBranchFileldName("t1.aa20dpid");
        	   if(!("").equals(gp)){
         		  int i = Integer.parseInt(gp); 
         		  String level=String.valueOf(i-1);
         		  s.append (" and t1.aa20dpid in (select unitid from fdm.acrm_f_sm_sys_units_sta where unitlevel>='"+gp+"' and level_"+level+"='"+auth.getUnitId()+"')");
         	  }
        	/*   if (!GreenplumMapping.isEmpty()) {
                   for(Entry<String, String> item : GreenplumMapping.entrySet()) {
                       qh.addGreenplumLookup(item.getKey(), item.getValue());
                   }
               }*//*
        	   setJson(qh.getJSON());*/

        	   //setBranchFileldName("t1.aa20dpid");
        	   SQL=s.toString();
   	           datasource = ds;
        //return "success";
    }
	public    boolean judge(String data) throws Exception{
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
