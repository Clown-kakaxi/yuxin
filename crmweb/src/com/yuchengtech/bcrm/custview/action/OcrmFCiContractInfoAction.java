package com.yuchengtech.bcrm.custview.action;

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
import com.yuchengtech.bcrm.custview.model.OcrmFCiContractInfo;
import com.yuchengtech.bcrm.custview.service.OcrmFCiContractInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @description 客户签约信息
 * @author likai
 * @since 2014-07-25
 */
@Action("/ocrmFCiContractInfo")
public class OcrmFCiContractInfoAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  OcrmFCiContractInfoService  ocrmFCiContractInfoService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	@Autowired
	public void init(){
	  	model = new OcrmFCiContractInfo(); 
		setCommonService(ocrmFCiContractInfoService);
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
		StringBuilder sb = new StringBuilder(" select * from OCRM_F_CI_CONTRACT_INFO where CUST_ID= '"+custId+"'");
		SQL=sb.toString();
		datasource = ds;
		setPrimaryKey("SIGN_DATE desc");
		configCondition("SIGN_NAME", "like", "SIGN_NAME",DataType.String);
		configCondition("SIGN_ORG", "like", "SIGN_ORG",DataType.String);
		configCondition("SIGN_DATE", "=", "SIGN_DATE",DataType.Date);
		configCondition("SIGN_CHANEL", "like", "SIGN_CHANEL",DataType.String);
	}
	
	/**
	 * 数据保存
	 */
	public DefaultHttpHeaders saveData(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 if(((OcrmFCiContractInfo)model).getId() == null){
 			ocrmFCiContractInfoService.save(model);
    	 } else if(((OcrmFCiContractInfo)model).getId()!=null){
    		String id = ((OcrmFCiContractInfo)model).getId().toString();
 			String jql = "update  OcrmFCiContractInfo c set c.signName=:signName,c.signOrg=:signOrg,c.signDate=:signDate,c.signChanel=:signChanel,c.signEndDate=:signEndDate,c.ogrName=:ogrName,c.attn=:attn,c.signSts=:signSts where c.id='"+id+"'";
 	        Map<String,Object> values = new HashMap<String,Object>();
 	        values.put("signName",((OcrmFCiContractInfo)model).getSignName());
 	        values.put("signOrg",((OcrmFCiContractInfo)model).getSignOrg());
 	        values.put("signDate",((OcrmFCiContractInfo)model).getSignDate());
 	        values.put("signChanel",((OcrmFCiContractInfo)model).getSignChanel());
 	        values.put("signEndDate",((OcrmFCiContractInfo)model).getSignEndDate());
 	        values.put("ogrName",((OcrmFCiContractInfo)model).getAttn());
 	        values.put("attn",((OcrmFCiContractInfo)model).getAttn());
 	        values.put("signSts",((OcrmFCiContractInfo)model).getSignSts());
 	       ocrmFCiContractInfoService.batchUpdateByName(jql, values);
    	 }
		return new DefaultHttpHeaders("success");
	}
	
	//删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	ocrmFCiContractInfoService.batchDel(request);
		return new DefaultHttpHeaders("success");
    }
}
