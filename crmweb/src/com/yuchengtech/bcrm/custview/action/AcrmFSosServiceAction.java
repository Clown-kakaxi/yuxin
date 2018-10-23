package com.yuchengtech.bcrm.custview.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ibm.icu.text.SimpleDateFormat;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmACardService;
import com.yuchengtech.bcrm.custview.model.AcrmFSosService;
import com.yuchengtech.bcrm.custview.service.AcrmFSosServiceService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@Action("/acrmFSosService")
public class AcrmFSosServiceAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private AcrmFSosServiceService service;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	@Autowired
	public void init(){
		model = new AcrmFSosService();
		setCommonService(service);
	}
	
	/**
	 * 数据查询
	 */
	public void prepare(){
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = request.getParameter("custId");
		StringBuffer sb = new StringBuffer("select * from ACRM_F_SOS_SERVICE s where s.cust_core_id = " +
				"(select distinct c.src_sys_cust_no from ACRM_F_CI_CROSSINDEX c " +
				"where c.cust_id = '"+custId+"')");
		SQL = sb.toString();
		datasource = ds;
	}
	
	//信息保存
	public DefaultHttpHeaders saveData(){
		ActionContext ctx = ActionContext.getContext();
	   	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   		String id = ((AcrmFSosService)model).getId().toString();
		String jql = "update AcrmFSosService s set s.serviceDay=:serviceDay,s.serviceTimes=:serviceTimes,s.serviceRemnant=:serviceRemnant,s.serviceStarttime=:serviceStarttime,s.serviceEndtime=:serviceEndtime where s.id='"+id+"'";
        Map<String,Object> values = new HashMap<String,Object>();
        values.put("serviceDay",((AcrmFSosService)model).getServiceDay());
        values.put("serviceTimes",((AcrmFSosService)model).getServiceTimes());
        values.put("serviceRemnant",((AcrmFSosService)model).getServiceRemnant());
        values.put("serviceStarttime",((AcrmFSosService)model).getServiceStarttime());
        values.put("serviceEndtime",((AcrmFSosService)model).getServiceEndtime());
        service.batchUpdateByName(jql, values);
        return new DefaultHttpHeaders("success");
	}
	//信息保存
	public DefaultHttpHeaders saveVipData() throws ParseException{
		ActionContext ctx = ActionContext.getContext();
	   	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   //		String id = ((AcrmFSosService)model).getId().toString();
   		Long id=Long.parseLong(request.getParameter("name"));
   		String serviceDay1=request.getParameter("serviceday");
   		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   		Date serviceDay=(Date) sdf.parse(serviceDay1);
   		String serviceTimes1=request.getParameter("servicetimes");
   		BigDecimal serviceTimes=new BigDecimal(serviceTimes1);
   		String serviceRemnant1=request.getParameter("serviceremnant");
   		BigDecimal serviceRemnant=new BigDecimal(serviceRemnant1);
   		String serviceStart1=request.getParameter("servicestart");
   		Date serviceStart=(Date) sdf.parse(serviceStart1);
   		String serviceEndtime1=request.getParameter("serviceend");
   		Date serviceEndtime=(Date) sdf.parse(serviceEndtime1);

		String jql = "update AcrmFSosService s set s.serviceDay=:serviceDay,s.serviceTimes=:serviceTimes,s.serviceRemnant=:serviceRemnant,s.serviceStarttime=:serviceStarttime,s.serviceEndtime=:serviceEndtime where s.id='"+id+"'";
        Map<String,Object> values = new HashMap<String,Object>();
        values.put("serviceDay",serviceDay);
        values.put("serviceTimes",serviceTimes);
        values.put("serviceRemnant",serviceRemnant);
        values.put("serviceStarttime",serviceStart);
        values.put("serviceEndtime",serviceEndtime);
        service.batchUpdateByName(jql, values);
      return new DefaultHttpHeaders("success");
	}
	
	public DefaultHttpHeaders saveVip() throws ParseException{
		ActionContext ctx = ActionContext.getContext();
	   	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	   	
   		String  custCoreId=request.getParameter("custcoreid");
   		String custName=request.getParameter("custname");
   		String serviceDay1=request.getParameter("serviceday");
   		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   		Date serviceDay=(Date) sdf.parse(serviceDay1);
   		String serviceTimes1=request.getParameter("servicetimes");
   		BigDecimal serviceTimes=new BigDecimal(serviceTimes1);
   		String serviceRemnant1=request.getParameter("serviceremnant");
   		BigDecimal serviceRemnant=new BigDecimal(serviceRemnant1);
   		String serviceStart1=request.getParameter("servicestart");
   		Date serviceStarttime=(Date) sdf.parse(serviceStart1);
   		String serviceEndtime1=request.getParameter("serviceend");
   		Date serviceEndtime=(Date) sdf.parse(serviceEndtime1);
   		System.out.print(custCoreId+"----------------------------------------------------------------------------------------------------------------------------");
//   		String jql="insert into AcrmFSosService(id,custCoreId,custName,serviceDay,serviceEndtime,serviceRemnant,serviceStarttime,serviceTimes) values (?,:custCoreId,:custName,:serviceDay,:serviceEndtime,:serviceRemnant,:serviceStarttime,:serviceTimes)";
//        Map<String,Object> values = new HashMap<String,Object>();
//        values.put("custCoreId",custCoreId);
//        values.put("custName",custName);
//        values.put("serviceDay",serviceDay);
//        values.put("serviceTimes",serviceTimes);
//        values.put("serviceRemnant",serviceRemnant);
//        values.put("serviceStarttime",serviceStart);
//        values.put("serviceEndtime",serviceEndtime);
   		AcrmFSosService afss=new AcrmFSosService();
   		afss.setCustCoreId(custCoreId);
   		afss.setCustName(custName);
   		afss.setServiceDay(serviceDay);
   		afss.setServiceEndtime(serviceEndtime);
   		afss.setServiceRemnant(serviceRemnant);
   		afss.setServiceTimes(serviceTimes);
        afss.setServiceStarttime(serviceStarttime);
        service.save(afss);
        return new DefaultHttpHeaders("success");
	}
	/**
     * 删除
     */
    public String batchDestroy(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String id = request.getParameter("idStr");
        service.batchRemove(id);
        return "success";
    }
    //批量删除
    public String batchDestroyMany(){
        ActionContext ctx = ActionContext.getContext();
        request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String idStr = request.getParameter("idStr");
        String[] id=idStr.split(",");
        for(int i=0;i<id.length;i++){
        	service.batchRemove(id[i]);
        }
        
        return "success";
    }
}
