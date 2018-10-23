package com.yuchengtech.bcrm.customer.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.service.CustomerAssignService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.SystemConstance;

/**
 * 分配机构和客户经理
 * @author songxs
 * @since 2013-1-11
 *
 */

@ParentPackage("json-default")

@Action(value="/customer_assign", results={
    @Result(name="success", type="json")})
public class CustomerAssignAction extends CommonAction {

	private HttpServletRequest request;
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Autowired
	private CustomerAssignService customerAssignService;

	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//String claimtagNo = request.getParameter("claimtagNo");
		String custManagerType = request.getParameter("custManagerType");
		
		//获取登录的用户所在机构的客户编号,获取未分配机构的客户信息
		StringBuilder sb = new StringBuilder("select t1.CUST_ID,t1.CUST_ZH_NAME,t1.cust_typ,t1.cust_lev,t1.cert_type,t1.cert_num,t3.main_type,t3.INSTITUTION_NAME ,t3.ID as DEL_ID from OCRM_F_CI_CUST_DESC t1 " 
				+"inner join OCRM_F_CI_BELONG_ORG t3 on t1.cust_id = t3.cust_id  and t3.INSTITUTION_CODE = '"+auth.getUnitId()+"'"); //属于本级机构的客户
				//" where t1.cust_id not in (select t4.CUST_ID from OCRM_F_CI_BELONG_ORG t4 inner join admin_auth_org t5 on t5.org_id = t4.institution_code and t5.up_org_id = '"+auth.getUnitId()+"') " +		//并且不属于下级机构的客户
				if("ORACLE".equals(SystemConstance.DB_TYPE)){
					sb.append(" and t1.cust_id not in (select t6.cust_id from OCRM_F_CI_BELONG_CUSTMGR t6  LEFT JOIN SYS_USERS t7 on t7.userid =  t6.MGR_ID where t7.unitid = '"+auth.getUnitId()+"' )");		//不属于本级机构客户经理的机构
				}else if("DB2".equals(SystemConstance.DB_TYPE)){
					sb.append(" where NOT exists (SELECT t6.cust_id FROM OCRM_F_CI_BELONG_CUSTMGR t6 LEFT JOIN SYS_USERS t7 ON t7.userid = t6.MGR_ID WHERE t1.cust_id=t6.cust_id  and t7.unitid = '"+auth.getUnitId()+"' )");		//不属于本级机构客户经理的机构					
				}
		if(custManagerType.equals("3")||custManagerType.equals("4")){
        	sb.append(" and t3.main_Type = '1'");

		}
		for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("CUST_ID")){
                	sb.append(" and t1."+key+" like '%"+this.getJson().get(key)+"%'");
                } else if (key.equals("CUST_ZH_NAME")) {
                	sb.append(" and t1."+key+" like '%"+this.getJson().get(key)+"%'");
                } else if(key.equals("CERT_TYPE")){
                	sb.append(" and t1."+key+" like '%"+this.getJson().get(key)+"%'");
                } else if(key.equals("CERT_NUM")){
                    sb.append(" and t1."+key+" like '%"+this.getJson().get(key)+"%'");
                } else if(key.equals("CUST_TYP")){
                    sb.append(" and t1."+key+" like '%"+this.getJson().get(key)+"%'");
                } else if(key.equals("CUST_LEV")){
                    sb.append(" and t1."+key+" like '%"+this.getJson().get(key)+"%'");
                } 
			}
        }
		setPrimaryKey("t1.CUST_ID");
  //      setBranchFileldName("t3.INSTITUTION_CODE");//查询本机构及辖内机构 
		SQL = sb.toString();
		datasource = ds;
	}
	
	/**
	 * 本机构为所选客户的主办机构的机构分配方法
	 * @return
	 * @throws Exception 
	 */
	public String assignMainOrg() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String models = request.getParameter("models");	//协办机构
		String custIds = request.getParameter("custIds");//所选择的客户ID
		String mainCode = request.getParameter("mainCode");//主办机构的code
		String mainName = request.getParameter("mainName");//主办机构的name
		String delIDs = request.getParameter("delIDs");//所选择的记录的主键ID
		String delOmainId = request.getParameter("idStr");//要删除的协办信息的机构id
		String removeOmain = request.getParameter("removeOmain");
		JSONArray jarray1 = null;
		JSONArray jarray2 = null;
		String delIDs_1 = "";
		if(!models.equals("[]")){
			jarray1 = JSONArray.fromObject(models);
		}
		if(!delOmainId.equals("")){		
			jarray2 = JSONArray.fromObject(removeOmain);
		}else{
			delOmainId ="";
		}
		customerAssignService.saveMainOrg(mainCode,mainName,custIds,delIDs,delOmainId,jarray2,jarray1,delIDs_1);
		
		return "success";
	}
	/**
	 * 本机构为所选客户的协办机构的机构分配方法
	 * @return
	 * @throws Exception 
	 */
	public String assignoMainOrg() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String models = request.getParameter("models");
		String custIds = request.getParameter("custIds");
		String delIDs_1 = request.getParameter("delIDs");
		if(!models.equals("null")){
			JSONArray jarray1 = JSONArray.fromObject(models);
			customerAssignService.saveOmainOrg(custIds,jarray1,delIDs_1);			
		}
		return "success";	
	}
	/**
	 * 本机构为所选客户的主办机构的客户分配方法
	 * @return
	 * @throws Exception 
	 */
	public String assignMainCustMgr() throws Exception {
		//取得前台传过来的参数
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String mgrModel = request.getParameter("mgrModel");//协办客户经理
		String custIds =  request.getParameter("custIds");//所选择的客户的id
		String mainMgrId = request.getParameter("mainMgrId");//主办客户经理的ID
		String mainMgrName = request.getParameter("mainMgrName");//主办客户经理的NAME
		if(!mgrModel.equals("null")){
			JSONArray jarray = JSONArray.fromObject(mgrModel);
			customerAssignService.saveOmainMgr(custIds,jarray);
		}
		customerAssignService.saveMainMgr(custIds,mainMgrId,mainMgrName);
		return "success";
	}
	/**
	 * 本机构为所选客户的协办机构的客户分配方法
	 * @return
	 * @throws Exception 
	 */
	public String assignOmainCustMgr() throws Exception{
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String mgrModel = request.getParameter("mgrModel");
		String custIds = request.getParameter("custIds");
		if(!mgrModel.equals("null")){
			JSONArray jarray = JSONArray.fromObject(mgrModel);
			customerAssignService.saveOmainMgr(custIds,jarray);	
		}
		return"success";
	}
}