package com.yuchengtech.bcrm.custview.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.service.CustMgrBelongService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.exception.BizException;
/**
 * 客户归属调整Action
 * @author wangwan
 * @since 2013-01-18
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custMgrBelongAction", results = { @Result(name = "success", type = "json") })
public class CustMgrBelongAction extends CommonAction {

	// 数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Autowired
	private CustMgrBelongService service;//调整归属客户经理service
	
	@Autowired
	public void init() {
		setCommonService(service);
		needLog = true;//新增修改删除记录是否记录日志,默认为false，不记录日志
	}
	public void prepare() {


    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String cust_id = request.getParameter("cust_id");
    	String org_id = request.getParameter("orgId");
        StringBuffer sb = new StringBuffer("select '" + cust_id + "' as cust_num,t.*,t.account_name as mgr_id,t.user_name as mgr_name,t.org_id as institution," +
        		" m.org_name institution_name  from admin_auth_account t"+
        		" left join admin_auth_org m on m.org_id = t.org_id  "+
        		"  where 1 = 1 ");
        if(!org_id.equals("")){
        	sb.append("  AND (t.org_id in ( " + org_id + "))");
        }
        SQL=sb.toString();
        datasource = ds;
	}
	
	public DefaultHttpHeaders save(){
			 
		try{
			
			//取得前台传过来的参数
			ActionContext ctx = ActionContext.getContext();
			this.request = ((HttpServletRequest)ctx.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest"));
			    
			String mainTypeOrg = this.request.getParameter("mainTypeOrg");//主办机构信息数组
			String firstOrg = this.request.getParameter("firstOrg");//新增协办机构数组
			String secondOrg = this.request.getParameter("secondOrg");//删除协办机构数组
			if(!(mainTypeOrg.equals("[]"))){
				JSONArray jarray3 = JSONArray.fromObject(mainTypeOrg);
				this.service.update(jarray3);
			} 
			  
			if(!(secondOrg.equals("[]"))){
				JSONArray jarray2 = JSONArray.fromObject(secondOrg);
				this.service.remove(jarray2);
			}
				
			if(!(firstOrg.equals("[]"))){
				JSONArray jarray = JSONArray.fromObject(firstOrg);
				this.service.save(jarray);
			} 
		    
			String addStr = this.request.getParameter("addStr");//新增协办客户经理数组
			String delStr = this.request.getParameter("delStr");//删除协办客户经理数组
			String custIds = this.request.getParameter("custIds");//客户号
			String institution = this.request.getParameter("mainMgr_institution");//主办客户经理所属机构号
			String institution_name = this.request.getParameter("mainMgr_institution_name");//主办客户经理所属机构
			String mainMgrId = this.request.getParameter("mainMgrId");//主办客户经理编号
			String mainMgrName = this.request.getParameter("mainMgrName");//主办客户经理名称
		    
		    
			if(!(addStr.equals("[]"))){
				JSONArray addArray = JSONArray.fromObject(addStr);
				this.service.saveMgr(addArray,custIds);
			} 
			if(!(delStr.equals("[]"))){
				JSONArray delArray = JSONArray.fromObject(delStr);
				this.service.removeMgr(delArray,custIds);
			} 
			if(!(mainMgrId.equals("[]")) && !(mainMgrName.equals("[]"))){
				this.service.saveMainTypeMgr(custIds,mainMgrId,mainMgrName,institution,institution_name);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return new DefaultHttpHeaders("success");
		  
	}
}
