package com.yuchengtech.bcrm.custview.action;

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

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.OcrmFCiOtherBank;
import com.yuchengtech.bcrm.custview.service.OcrmFCiOtherBankService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @description 对公客户他行信息
 * @author likai
 * @since 2014/07/25
 *
 */

@Action("/ocrmFCiOtherBank")
public class OcrmFCiOtherBankAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  OcrmFCiOtherBankService  ocrmFCiOtherBankService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	@Autowired
	public void init(){
	  	model = new OcrmFCiOtherBank(); 
		setCommonService(ocrmFCiOtherBankService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog=true;
	}
	
	/**
	 *信息查询SQL
	 */
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId=request.getParameter("custId");
		StringBuilder sb = new StringBuilder(" select * from OCRM_F_CI_OTHER_BANK where CUST_ID= '"+custId+"'");
		SQL=sb.toString();
		datasource = ds;
		setPrimaryKey("UPDT_DT desc");
		configCondition("INSTN_NAME", "like", "INSTN_NAME",DataType.String);
	}
	
	/**
	 * 数据保存
	 */
	public DefaultHttpHeaders saveData(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 if(((OcrmFCiOtherBank)model).getMxtid() == null){
    		String custId=request.getParameter("custId");
 			((OcrmFCiOtherBank)model).setUsername(auth.getUsername());
 			((OcrmFCiOtherBank)model).setCustId(custId);
 			((OcrmFCiOtherBank)model).setInputDt(new Date());
 			ocrmFCiOtherBankService.save(model);
    	 } else if(((OcrmFCiOtherBank)model).getMxtid()!= null){
    		System.out.println("进入修改...");
    		String mxtid = ((OcrmFCiOtherBank)model).getMxtid().toString();
 			String jql = "update OcrmFCiOtherBank b set b.instnName=:instnName,b.isBasicBank=:isBasicBank,b.hzyears=:hzyears,b.prdUse=:prdUse,b.currentVal=:currentVal,b.periodcialVal=:periodcialVal,b.bailVal=:bailVal,b.lonVal=:lonVal,b.credAmt=:credAmt,b.credLimit=:credLimit,b.remark=:remark,b.userid=:userid,b.username=:username,b.updtDt=:updtDt where b.mxtid='"+mxtid+"'";
 	        Map<String,Object> values = new HashMap<String,Object>();
 	        values.put("instnName",((OcrmFCiOtherBank)model).getInstnName());
 	        values.put("isBasicBank",((OcrmFCiOtherBank)model).getIsBasicBank());
 	        values.put("hzyears",((OcrmFCiOtherBank)model).getHzyears());
 	        values.put("prdUse",((OcrmFCiOtherBank)model).getPrdUse());
 	        values.put("currentVal",((OcrmFCiOtherBank)model).getCurrentVal());
 	        values.put("periodcialVal",((OcrmFCiOtherBank)model).getPeriodcialVal());
 	        values.put("bailVal",((OcrmFCiOtherBank)model).getBailVal());
 	        values.put("lonVal",((OcrmFCiOtherBank)model).getLonVal());
 	        values.put("credAmt",((OcrmFCiOtherBank)model).getCredAmt());
 	        values.put("credLimit",((OcrmFCiOtherBank)model).getCredLimit());
 	        values.put("remark",((OcrmFCiOtherBank)model).getRemark());
 	        values.put("userid",auth.getUserId());
 	        values.put("username",auth.getUsername());
 	        values.put("updtDt", new Date());
 	        ocrmFCiOtherBankService.batchUpdateByName(jql, values);
    	 }
		return new DefaultHttpHeaders("success");
	}
	
	//删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	ocrmFCiOtherBankService.batchDel(request);
		return new DefaultHttpHeaders("success");
    }
}
