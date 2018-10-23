package com.yuchengtech.bcrm.customer.customergroup.action;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customergroup.model.OcrmFCiBaseMgrRelate;
import com.yuchengtech.bcrm.customer.customergroup.service.CustMgrGroupInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value="/custmgrgroupinfo", results={
    @Result(name="success", type="json")
})
public class CustMgrGroupInfoAction extends CommonAction{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Autowired
    private CustMgrGroupInfoService custMgrGroupInfoService ;
	public void init(){
    	model = new OcrmFCiBaseMgrRelate();  
    	setCommonService(custMgrGroupInfoService);
	}
	
	
	/*
	 * 查询方法,实现客户群基本信息查询
	 * */
	@Override
    public void prepare() {
		String querySign = request.getParameter("querySign");
		String groupId=request.getParameter("groupId");
		 StringBuilder s = new StringBuilder();
		if((!"".equals(request.getParameter("groupId"))&&null!=request.getParameter("groupId"))){
			
		}
		String queryTeamMgr =   " select mm.id as USER_ID, "+
								" mm.cust_mgr_id   as CUST_MANAGER_ID, "+
								" mm.create_date, "+
								" mm.main_type, "+
								" acc.MOBILEPHONE, "+
								" acc.user_name    as CUST_MANAGER_NAME, "+
								" mm.belong_org_id as INSTITUTION, "+
								" org.org_name as WORK_UNIT "+
								" from OCRM_F_CI_BASE_MGR_RELATE mm "+
								" inner join admin_auth_account acc "+
								" on acc.account_name = mm.cust_mgr_id "+
								" inner join admin_auth_org org "+
								" on org.org_id = mm.belong_org_id "+
								" where mm.cust_base_id = '"+groupId+"'";
		
		String queryCustomer =  " select distinct mm.*,m.institution "+
								" from (select mgr.mgr_id, mgr.institution "+
								" from (select cust_id "+
								" from ocrm_f_ci_relate_cust_base "+
								" where cust_base_id = '"+groupId+"') t "+
								" inner join ocrm_f_ci_belong_custmgr mgr "+
								" on mgr.cust_id = t.cust_id "+
								" and mgr.main_type = '1') m "+
								" inner join ocrm_f_cm_cust_mgr_info mm "+
								" on m.mgr_id = mm.cust_manager_id";
		if("queryTeamMgr".equals(querySign)){
			 s.append(queryTeamMgr);
		}else{
			 s.append(queryCustomer);
		}

	    for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("CUST_MANAGER_NAME"))
                    s.append(" and mm."+key+" like '%"+this.getJson().get(key)+"%'");
                else if(key.equals("CUST_MANAGER_ID"))
                	s.append(" and mm."+key+" = '"+this.getJson().get(key)+"'");
                else if(key.equals("CUST_MANAGER_TYPE"))
                	s.append(" and mm."+key+" = '"+this.getJson().get(key)+"'");
            }
        }

    	SQL=s.toString();
    	if("queryTeamMgr".equals(querySign)){
    		setPrimaryKey("mm.cust_mgr_id");
		}else{
			setPrimaryKey("mm.cust_manager_id");
		}
    	
    	datasource = ds;
        addOracleLookup("CUST_MANAGER_TYPE", "MANAGER_TYPE");
  		addOracleLookup("CUST_MANAGER_LEVEL", "MANAGER_LEVEL");
    }

	/*
	 * 对客户群成员：新增或修改方法
	 * */
    public String saveData() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId=request.getParameter("CUST_ID");
    	String custZhName=request.getParameter("CUST_ZH_NAME");
    	String mgrId=request.getParameter("MGR_ID");
    	String mgrName=request.getParameter("MGR_NAME");
    	String institution=request.getParameter("INSTITUTION");
    	String institutionName=request.getParameter("INSTITUTION_NAME");
		String custBaseId=request.getParameter("CUST_BASE_ID");
    	if(!("".equals(custId))&&custId!=null&&custId.length()>0){
		model = new OcrmFCiBaseMgrRelate(); 

		OcrmFCiBaseMgrRelate ocrmFCiBaseMgrRelate = (OcrmFCiBaseMgrRelate) model;
		
	    JSONObject jsonObject =JSONObject.fromObject(custId);
	    JSONArray jarray =  jsonObject.getJSONArray("cust_id");
	    JSONObject jsonObject1 =JSONObject.fromObject(custZhName);
	    JSONArray jarray1 =  jsonObject1.getJSONArray("cust_zh_name");
	    JSONObject jsonObject2 =JSONObject.fromObject(mgrId);
	    JSONArray jarray2 =  jsonObject2.getJSONArray("mgr_id");
	    JSONObject jsonObject3 =JSONObject.fromObject(mgrName);
	    JSONArray jarray3 =  jsonObject3.getJSONArray("mgr_name");
	    JSONObject jsonObject4 =JSONObject.fromObject(institution);
	    JSONArray jarray4 =  jsonObject4.getJSONArray("institution");
	    JSONObject jsonObject5 =JSONObject.fromObject(institutionName);
	    JSONArray jarray5 =  jsonObject5.getJSONArray("institution_name");
	    System.out.println(jarray.size());
	    for(int i = 0;i<jarray.size();i++){
	    	ocrmFCiBaseMgrRelate.setId(null);
	    	ocrmFCiBaseMgrRelate.setCreateDate(new Date());
	    	ocrmFCiBaseMgrRelate.setCreateOrg(auth.getUnitId());
	    	ocrmFCiBaseMgrRelate.setCreateUser(auth.getUserId());
//	    	ocrmFCiBaseMgrRelate.setCustBaseId(jarray.get(i).toString());
	    	ocrmFCiBaseMgrRelate.setCustBaseId(Long.parseLong(custBaseId));
    		custMgrGroupInfoService.saveData(model);	
	    }
    	addActionMessage("saveData successfully");
    	return "success";	
    	}else{
    	return "failure";
    	}	
    }
    
    /*
     * 删除客户群成员
     * */
    public String dropData() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String delStr=request.getParameter("delStr");
    	if(!("".equals(delStr))&&delStr!=null&&delStr.length()>0){
		    JSONObject jsonObject =JSONObject.fromObject(delStr);
		    JSONArray jarray =  jsonObject.getJSONArray("id");
		    custMgrGroupInfoService.remove(jarray);
	    	addActionMessage("saveData successfully");
    	    return "success";	
    	}else{
    		return "failure";
    	}	
    }
}
