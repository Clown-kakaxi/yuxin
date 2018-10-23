package com.yuchengtech.bcrm.customer.customergroup.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.yuchengtech.bcrm.customer.customergroup.service.GroupTeamMgrEditService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value="/groupteammgredit", results={
    @Result(name="success", type="json")
})
public class GroupTeamMgrEditAction extends CommonAction{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Autowired
    private GroupTeamMgrEditService groupTeamMgrEditService ;
	public void init(){
    	model = new OcrmFCiBaseMgrRelate();  
    	setCommonService(groupTeamMgrEditService);
	}
	
	/*
	 * 查询方法,实现客户群基本信息查询
	 * */
	@Override
    public void prepare() {
		String queryCustomer = "select distinct t1.*,mgr.mgr_id,mgr.mgr_name,mgr.institution,mgr.institution_name " +
				"from OCRM_F_CI_CUST_DESC t1 inner join ocrm_f_ci_belong_custmgr mgr on mgr.cust_id = t1.cust_id and mgr.main_type = '1' where 1>0";
		String queryGroupMember = "select * from OCRM_F_CI_RELATE_CUST_BASE t1 where 1>0";
		String querySign = request.getParameter("querySign");
		String groupId = request.getParameter("groupId");
	    StringBuilder s = new StringBuilder("");
	    if("queryGroupMember".equals(querySign)){
	    	s.append(queryGroupMember);
	    	if(null!=groupId&&!("".equals(groupId)))s.append(" and t1.cust_base_Id = "+groupId+"");
	   }if("queryCustomer".equals(querySign)){
		   s.append(queryCustomer);
		   if(null!=groupId&&!("".equals(groupId)))s.append(" and t1.cust_id not in(select cust_id from OCRM_F_CI_RELATE_CUST_BASE t where t.cust_base_id = "+groupId+")");
	   }
	    for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("CUST_ZH_NAME"))
                    s.append(" and t1."+key+" like '%"+this.getJson().get(key)+"%'");
                else if(key.equals("CUST_ID"))
                	s.append(" and t1."+key+" = '"+this.getJson().get(key)+"'");
                else if(key.equals("CUST_TYP"))
                	s.append(" and t1."+key+" = '"+this.getJson().get(key)+"'");
            }
        }

	    SQL=s.toString();
        setPrimaryKey("t1.CUST_ID");
          datasource = ds;
    }

	/*
	 * 对客户群成员：新增或修改方法
	 * */
    public String saveData() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custmanagerId=request.getParameter("CUSTMANAGER_ID");
    	String mainType=request.getParameter("MAIN_TYPE");
    	String orgId=request.getParameter("ORG_ID");
    	String groupBaseId=request.getParameter("groupBaseId");
		model = new OcrmFCiBaseMgrRelate(); 

		OcrmFCiBaseMgrRelate ocrmFCiBaseMgrRelate = (OcrmFCiBaseMgrRelate) model;
		
	    JSONObject jsonObject =JSONObject.fromObject(custmanagerId);
	    JSONArray jarray =  jsonObject.getJSONArray("CUSTMANAGER_ID");
	    JSONObject jsonObject1 =JSONObject.fromObject(mainType);
	    JSONArray jarray1 =  jsonObject1.getJSONArray("MAIN_TYPE");
	    JSONObject jsonObject2 =JSONObject.fromObject(orgId);
	    JSONArray jarray2 =  jsonObject2.getJSONArray("ORG_ID");
	    
	    //清空历史数据
	    String jql = "delete from OcrmFCiBaseMgrRelate p where p.custBaseId = '"+groupBaseId+"' ";
        Map<String,Object> values = new HashMap<String,Object>();
        groupTeamMgrEditService.batchUpdateByName(jql, values);
        //end
	    for(int i = 0;i<jarray.size();i++){
	    	ocrmFCiBaseMgrRelate.setId(null);
	    	ocrmFCiBaseMgrRelate.setCustBaseId(Long.parseLong(groupBaseId));
	    	ocrmFCiBaseMgrRelate.setCustMgrId(jarray.get(i).toString());
	    	ocrmFCiBaseMgrRelate.setBelongOrgId(jarray2.get(i).toString());
	    	if("".equals(jarray1.get(i).toString())){
	    		ocrmFCiBaseMgrRelate.setMainType("2");	
	    	}else{
	    		ocrmFCiBaseMgrRelate.setMainType(jarray1.get(i).toString());	
	    	}
	    	ocrmFCiBaseMgrRelate.setCreateDate(new Date());
	    	ocrmFCiBaseMgrRelate.setCreateOrg(auth.getUnitId());
	    	ocrmFCiBaseMgrRelate.setCreateUser(auth.getUserId());
	    	groupTeamMgrEditService.saveData(model);	
	    }
    	addActionMessage("saveData successfully");
    	return "success";	
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
		    groupTeamMgrEditService.remove(jarray);
	    	addActionMessage("saveData successfully");
    	    return "success";	
    	}else{
    		return "failure";
    	}	
    }
}
