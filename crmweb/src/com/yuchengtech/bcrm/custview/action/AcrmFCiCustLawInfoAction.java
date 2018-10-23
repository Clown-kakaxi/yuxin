package com.yuchengtech.bcrm.custview.action;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.custview.model.AcrmFCiCustLawInfo;
import com.yuchengtech.bcrm.custview.service.AcrmFCiCustLawInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @description 客户诉讼信息
 * @author likai
 * @since 2014-08-11
 */

@Action("/acrmFCiCustLawInfo")
public class AcrmFCiCustLawInfoAction extends CommonAction{
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  AcrmFCiCustLawInfoService  acrmFCiCustLawInfoService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	@Autowired
	public void init(){
	  	model = new AcrmFCiCustLawInfo(); 
		setCommonService(acrmFCiCustLawInfoService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
	
	/**
	 *信息查询SQL
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId=request.getParameter("borrName");
		StringBuilder sb = new StringBuilder(" select * from ACRM_F_CI_CUST_LAW_INFO where BORR_NAME= '"+custId+"'");
		SQL=sb.toString();
		datasource = ds;
		setPrimaryKey("RECORD_DATE desc");
		configCondition("CUST_NAME", "like", "CUST_NAME",DataType.String);
		configCondition("MA_CASE_NO", "like", "MA_CASE_NO",DataType.String);
	}
	
	/**
	 * 数据保存
	 */
	public DefaultHttpHeaders saveData(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 if(((AcrmFCiCustLawInfo)model).getId() == null){
  			((AcrmFCiCustLawInfo)model).setRecordDate(new Date());
  			String custId=request.getParameter("borrName");
  			List<AcrmFCiCustomer>  list =acrmFCiCustLawInfoService.findByJql("select c from AcrmFCiCustomer c where c.custId='"+custId+"'", null);
  			((AcrmFCiCustLawInfo)model).setCustName(list.get(0).getCustName());
    		 acrmFCiCustLawInfoService.save(model);
    	 } else if(((AcrmFCiCustLawInfo)model).getId()!=null){
    		String id = ((AcrmFCiCustLawInfo)model).getId().toString();
 			String jql = "update  AcrmFCiCustLawInfo l set l.custName=:custName,l.othProsec=:othProsec,l.maCaseNo=:maCaseNo,l.litiSta=:litiSta,l.oriBorrAmt=:oriBorrAmt,l.balAmt=:balAmt where l.id='"+id+"'";
 	        Map<String,Object> values = new HashMap<String,Object>();
 	        values.put("custName",((AcrmFCiCustLawInfo)model).getCustName());
 	        values.put("othProsec",((AcrmFCiCustLawInfo)model).getOthProsec());
 	        values.put("maCaseNo",((AcrmFCiCustLawInfo)model).getMaCaseNo());
 	        values.put("litiSta",((AcrmFCiCustLawInfo)model).getLitiSta());
 	        values.put("oriBorrAmt",((AcrmFCiCustLawInfo)model).getOriBorrAmt());
 	        values.put("balAmt",((AcrmFCiCustLawInfo)model).getBalAmt());
 	        acrmFCiCustLawInfoService.batchUpdateByName(jql, values);
    	 }
		return new DefaultHttpHeaders("success");
	}
	
	//删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	acrmFCiCustLawInfoService.batchDel(request);
		return new DefaultHttpHeaders("success");
    }
}
