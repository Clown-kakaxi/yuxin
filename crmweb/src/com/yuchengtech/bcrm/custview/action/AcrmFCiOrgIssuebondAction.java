package com.yuchengtech.bcrm.custview.action;

import java.sql.Timestamp;
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
import com.yuchengtech.bcrm.custview.model.AcrmFCiOrgIssuebond;
import com.yuchengtech.bcrm.custview.service.AcrmFCiOrgIssuebondService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @description 客户债券信息
 * @author likai
 * @since 2014-07-23
 */

@Action("/acrmFCiOrgIssuebond")
public class AcrmFCiOrgIssuebondAction extends CommonAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
    private  AcrmFCiOrgIssuebondService  acrmFCiOrgIssuebondService;
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	@Autowired
	public void init(){
	  	model = new AcrmFCiOrgIssuebond(); 
		setCommonService(acrmFCiOrgIssuebondService);
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
		StringBuilder sb = new StringBuilder(" select * from ACRM_F_CI_ORG_ISSUEBOND where CUST_ID= '"+custId+"'");
		SQL=sb.toString();
		datasource = ds;
		setPrimaryKey("LAST_UPDATE_TM desc ");
		configCondition("BOND_TYPE", "=", "BOND_TYPE",DataType.String);
		configCondition("BOND_CODE", "like", "BOND_CODE",DataType.String);
		configCondition("BOND_NAME", "like", "BOND_NAME",DataType.String);
		configCondition("IS_MARKET", "=", "IS_MARKET",DataType.String);
	}
	
	/**
	 * 数据保存
	 */
	public DefaultHttpHeaders saveData(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 
    	 //新建
    	 if(((AcrmFCiOrgIssuebond)model).getIssueBondId() == null){
    		String custId=request.getParameter("custId");
 			((AcrmFCiOrgIssuebond)model).setLastUpdateUser(auth.getUsername());
 			((AcrmFCiOrgIssuebond)model).setCustId(custId);
		    ((AcrmFCiOrgIssuebond)model).setLastUpdateTm(new Timestamp(new Date().getTime()));
 			acrmFCiOrgIssuebondService.save(model);
 		//修改
    	 } else if(((AcrmFCiOrgIssuebond)model).getIssueBondId()!=null){
    		String issueBondId = ((AcrmFCiOrgIssuebond)model).getIssueBondId().toString();
 			String jql = "update AcrmFCiOrgIssuebond b set b.bondName=:bondName,b.bondCurr=:bondCurr,b.bondType=:bondType,b.issueAmt=:issueAmt,b.bondCode=:bondCode,b.bondIntr=:bondIntr,b.bondTerm=:bondTerm,b.isMarket=:isMarket,b.remark=:remark,b.lastUpdateUser=:lastUpdateUser,b.lastUpdateTm=:lastUpdateTm where b.issueBondId='"+issueBondId+"'";
 	        Map<String,Object> values = new HashMap<String,Object>();
 	        values.put("bondName",((AcrmFCiOrgIssuebond)model).getBondName());
 	        values.put("bondCurr",((AcrmFCiOrgIssuebond)model).getBondCurr());
 	        values.put("bondType",((AcrmFCiOrgIssuebond)model).getBondType());
 	        values.put("issueAmt",((AcrmFCiOrgIssuebond)model).getIssueAmt());
 	        values.put("bondCode",((AcrmFCiOrgIssuebond)model).getBondCode());
 	        values.put("bondIntr",((AcrmFCiOrgIssuebond)model).getBondIntr());
 	        values.put("bondTerm",((AcrmFCiOrgIssuebond)model).getBondTerm());
 	        values.put("isMarket",((AcrmFCiOrgIssuebond)model).getIsMarket());
 	        values.put("remark",((AcrmFCiOrgIssuebond)model).getRemark());
 	        values.put("lastUpdateUser",auth.getUsername());
 	        values.put("lastUpdateTm", new Date());
 	        acrmFCiOrgIssuebondService.batchUpdateByName(jql, values);
    	 }
		return new DefaultHttpHeaders("success");
	}
	
	//删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	acrmFCiOrgIssuebondService.batchDel(request);
		return new DefaultHttpHeaders("success");
    }
}
