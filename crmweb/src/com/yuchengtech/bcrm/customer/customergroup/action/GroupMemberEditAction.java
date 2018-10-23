package com.yuchengtech.bcrm.customer.customergroup.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customergroup.model.OcrmFCiRelateCustBase;
import com.yuchengtech.bcrm.customer.customergroup.service.GroupMemberEditService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;

@ParentPackage("json-default")
@Action(value="/groupmemberedit", results={
    @Result(name="success", type="json")
})
public class GroupMemberEditAction extends CommonAction{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Autowired
    private GroupMemberEditService groupMemberEditService ;
	public void init(){
    	model = new OcrmFCiRelateCustBase();  
    	setCommonService(groupMemberEditService);
	}
	
	/*
	 * 查询方法,实现客户群基本信息查询
	 * */
	@Override
    public void prepare() {
		String queryCustomer = "select distinct t1.*,mgr.mgr_id,mgr.mgr_name,mgr.institution,mgr.institution_name " +
				"from ACRM_F_CI_CUSTOMER t1 inner join ocrm_f_ci_belong_custmgr mgr on mgr.cust_id = t1.cust_id   where 1>0";
		String queryGroupMember = "select t1.*,de.ident_type,de.ident_no,de.cust_type from OCRM_F_CI_RELATE_CUST_BASE t1  inner join ACRM_F_CI_CUSTOMER de on de.cust_id = t1.cust_id where 1>0";
		String querySign = request.getParameter("querySign");
		String custType = request.getParameter("custType");
		String groupId = request.getParameter("groupId");
	    StringBuilder s = new StringBuilder("");
	    if("queryGroupMember".equals(querySign)){
	    	s.append(queryGroupMember);
	    	if(null!=groupId&&!("".equals(groupId)))s.append(" and t1.cust_base_Id = '"+groupId+"' ");
	    }
	    if("queryCustomer".equals(querySign)){
		   s.append(queryCustomer);
		   
		   //判定，当前台传来的客户类型参数custType为对公或对私时，拼接条件，否则，不执行该操作
		   if("1".equals(custType)||"2".equals(custType)){
	    		s.append(" and t1.cust_type = '"+custType+"' ");
		   }
		   if(null!=groupId&&!("".equals(groupId)))
			   s.append(" and t1.cust_id not in(select cust_id from OCRM_F_CI_RELATE_CUST_BASE t where t.cust_base_id = '"+groupId+"' )");
	   }
	    for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if(key.equals("CUST_NAME"))
                    s.append(" and t1."+key+" like '%"+this.getJson().get(key)+"%'");
                else if(key.equals("CUST_ID"))
                	s.append(" and t1."+key+" = '"+this.getJson().get(key)+"'");
                else if(key.equals("CUST_TYPE"))
                	s.append(" and t1.CUST_TYPE = '"+this.getJson().get(key)+"'");
            }
        }
	    
	    //添加限制条件  如果为总行 查询全部的  如果不是，查询自己管理的
	    String level = auth.getUnitlevel();
	    if(!"1".equals(level)){
	    	s.append("  and t1.cust_id in (select cust_id from ocrm_f_ci_belong_custmgr where mgr_id='"+auth.getUserId()+"')");
	    }
	    String groupMemberType = request.getParameter("groupMemberType");
	    if("2".equals(groupMemberType)){//对私客户群	
	    	s.append(" and custInfo.cust_typ='2' ");
	    }else if("1".equals(groupMemberType)){//对公客户群	
	    	s.append(" and custInfo.cust_typ='1' ");
	    }

	    SQL=s.toString();
        setPrimaryKey("t1.CUST_ID");
        addOracleLookup("IDENT_TYPE", "XD000040");
        addOracleLookup("CUST_TYPE", "XD000080");
        datasource = ds;
          
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
		model = new OcrmFCiRelateCustBase(); 

		OcrmFCiRelateCustBase ocrmFCiRelateCustBase = (OcrmFCiRelateCustBase) model;
		
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
	    int size=jarray.size();//客户群成员数
	    
	    for(int i = 0;i<jarray.size();i++){
	    	ocrmFCiRelateCustBase.setId(null);
	    	ocrmFCiRelateCustBase.setCrateDate(new Date());
    		ocrmFCiRelateCustBase.setCreateOrg(auth.getUnitId());
    		ocrmFCiRelateCustBase.setCreateUser(auth.getUserId());
    		ocrmFCiRelateCustBase.setCustId(jarray.get(i).toString());
    		ocrmFCiRelateCustBase.setCustZhName(jarray1.get(i).toString());
    		ocrmFCiRelateCustBase.setBelongCustMgrName(jarray3.get(i).toString());
    		ocrmFCiRelateCustBase.setBelongOrgId(jarray4.get(i).toString());
    		ocrmFCiRelateCustBase.setBelongOrgName(jarray5.get(i).toString());
    		ocrmFCiRelateCustBase.setBelongCustMgrId(jarray2.get(i).toString());
    		ocrmFCiRelateCustBase.setCustBaseId(Long.parseLong(custBaseId));
    		groupMemberEditService.saveData(model);	
	    }
	    groupMemberEditService.saveDataLong(size,custBaseId);
    	addActionMessage("saveData successfully");
    	return "success";	
    	}else{
    	return "failure";
    	}	
    }
    
    /*
	 * 在此Action基础上添加方法
	 * 客户列表选择客户加入客户群
	 * yuyz
	 * */
    public String saveMember() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String json=request.getParameter("JSON");
		String custBaseIds=request.getParameter("CUST_BASE_IDS");
		String[] sarray=custBaseIds.split(","); 
    	for(int i = 0;i<sarray.length;i++){
    		JSONObject jsonObject =JSONObject.fromObject(json);
    		JSONArray custIdAr =  jsonObject.getJSONArray("cust_id");
    		JSONArray custNameAr =  jsonObject.getJSONArray("cust_zh_name");
    		JSONArray mgrIdAr =  jsonObject.getJSONArray("mgr_id");
    		JSONArray mgrNameAr =  jsonObject.getJSONArray("mgr_name");
    		JSONArray orgIdAr =  jsonObject.getJSONArray("institution");
    		JSONArray orgNameAr =  jsonObject.getJSONArray("institution_name");
    		for(int j = 0;j<custIdAr.size();j++){
    			OcrmFCiRelateCustBase model = new OcrmFCiRelateCustBase(); 
    			model.setCustBaseId(Long.parseLong(sarray[i].toString()));
    			model.setId(null);
    			model.setCrateDate(new Date());
    			model.setCreateOrg(auth.getUnitId());
    			model.setCreateUser(auth.getUserId());
    			model.setCustId(custIdAr.get(j).toString());
    			model.setCustZhName(custNameAr.get(j).toString());
    			model.setBelongCustMgrName(mgrNameAr.get(j).toString());
    			model.setBelongOrgId(orgIdAr.get(j).toString());
    			model.setBelongOrgName(orgNameAr.get(j).toString());
    			model.setBelongCustMgrId(mgrIdAr.get(j).toString());
    			groupMemberEditService.saveData(model);	
    		}
    		}
    		addActionMessage("saveData successfully");
    	return "success";
    }
    
    //客户细分查询加入群
    public HttpHeaders saveMemberBySearch() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		Connection conn = null ;
    	Statement stmt = null ;
    	ResultSet rs = null;
		
		//获取DBTABLE_ID和pramas
		String custId = request.getParameter("custId");
		String type = request.getParameter("type");
		String groupId = request.getParameter("groupId");
		String search = "";
		int i = 0;
		//查询所需信息
		if("new".equals(type)){//新增客户群
			search = "select distinct t1.cust_id,t1.cust_name, mgr.mgr_id,mgr.mgr_name,mgr.institution,mgr.institution_name " +
					"from ACRM_F_CI_CUSTOMER t1 left join ocrm_f_ci_belong_custmgr mgr on mgr.cust_id = t1.cust_id " +
					"and (mgr.main_type = '1' or mgr.main_type is null)  where 1 > 0 and t1.cust_id in ('"+custId.replace(",", "','")+"') ORDER BY t1.CUST_ID ";
		}
		if("add".equals(type)){//加入已有客户群
			search = "select distinct t1.cust_id,t1.cust_name, mgr.mgr_id,mgr.mgr_name,mgr.institution,mgr.institution_name " +
			"from ACRM_F_CI_CUSTOMER t1 left join ocrm_f_ci_belong_custmgr mgr on mgr.cust_id = t1.cust_id " +
			"and (mgr.main_type = '1' or mgr.main_type is null)  where 1 > 0 and t1.cust_id in ('"+custId.replace(",", "','")+"') " +
			"and t1.cust_id not in (select cust_id from OCRM_F_CI_RELATE_CUST_BASE t where t.cust_base_id = '"+groupId+"') ORDER BY t1.CUST_ID ";
			
		}
			 try {
		        	conn=ds.getConnection();
		    		stmt = conn.createStatement();
			        rs = stmt.executeQuery(search);
			        while(rs.next()){
			        	OcrmFCiRelateCustBase model = new OcrmFCiRelateCustBase(); 
		    			model.setCustBaseId(Long.parseLong(groupId));
		    			model.setId(null);
		    			model.setCrateDate(new Date());
		    			model.setCreateOrg(auth.getUnitId());
		    			model.setCreateUser(auth.getUserId());
		    			model.setCustId(rs.getString("cust_id"));
		    			model.setCustZhName(rs.getString("cust_name"));
		    			model.setBelongCustMgrName(rs.getString("mgr_name"));
		    			model.setBelongOrgId(rs.getString("institution"));
		    			model.setBelongOrgName(rs.getString("institution_name"));
		    			model.setBelongCustMgrId(rs.getString("mgr_id"));
		    			groupMemberEditService.saveData(model);	
			        	i++;
			        }
			 }catch (SQLException e) {
	        		throw new BizException(1,2,"1002",e.getMessage());
		        }finally{
		        	JdbcUtil.close(rs, stmt, conn);
		        }
		
		
		        if(this.json!=null)
					this.json.clear();
				else 
					this.json = new HashMap<String,Object>();  
				
				this.json.put("number", String.valueOf(i));
				
				return new DefaultHttpHeaders("success").disableCaching();
    	
    }
    //导入证件加入客户成员
    public HttpHeaders saveMemberByImp() throws SQLException{
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		Connection conn = null ;
    	Statement stmt = null ;
    	Statement stmt1 = null ;
    	ResultSet rs = null;
		
		//获取DBTABLE_ID和pramas
		String groupId = request.getParameter("groupId");
		String search = "";
		String pkHead = request.getParameter("pkHead");
		int i = 0;
		//查询所需信息
			search = "select distinct t1.cust_id,t1.cust_name, mgr.mgr_id,mgr.mgr_name,mgr.institution,mgr.institution_name " +
					"from ACRM_F_CI_CUSTOMER t1 left join ocrm_f_ci_belong_custmgr mgr on mgr.cust_id = t1.cust_id " +
					"and (mgr.main_type = '1' or mgr.main_type is null),OCRM_F_GROUP_TEMP t2  where t2.cert_type=t1.ident_type and t2.cert_num=t1.ident_no and t2.id like '"+pkHead+"%'  ORDER BY t1.CUST_ID ";
			 try {
		        	conn=ds.getConnection();
		    		stmt = conn.createStatement();
		    		stmt1 = conn.createStatement();
			        rs = stmt.executeQuery(search);
			        while(rs.next()){
			        	OcrmFCiRelateCustBase model = new OcrmFCiRelateCustBase(); 
		    			model.setCustBaseId(Long.parseLong(groupId));
		    			model.setId(null);
		    			model.setCrateDate(new Date());
		    			model.setCreateOrg(auth.getUnitId());
		    			model.setCreateUser(auth.getUserId());
		    			model.setCustId(rs.getString("cust_id"));
		    			model.setCustZhName(rs.getString("cust_name"));
		    			model.setBelongCustMgrName(rs.getString("mgr_name"));
		    			model.setBelongOrgId(rs.getString("institution"));
		    			model.setBelongOrgName(rs.getString("institution_name"));
		    			model.setBelongCustMgrId(rs.getString("mgr_id"));
		    			groupMemberEditService.saveData(model);	
			        	i++;
			        }
			        //删除本批次数据
			       String delete = "delete from OCRM_F_GROUP_TEMP where id like '"+pkHead+"%'" ;
			       stmt1.executeUpdate(delete);
			        
			 }catch (SQLException e) {
	        		throw new BizException(1,2,"1002",e.getMessage());
		        }finally{
		        	JdbcUtil.closeStatement(stmt1);
		        	JdbcUtil.close(rs, stmt, conn);
		        }
		
		
		        if(this.json!=null)
					this.json.clear();
				else 
					this.json = new HashMap<String,Object>();  
				
				this.json.put("number", String.valueOf(i));
				
				return new DefaultHttpHeaders("success").disableCaching();
    	
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
		    groupMemberEditService.remove(jarray);
	    	addActionMessage("saveData successfully");
    	    return "success";	
    	}else{
    		return "failure";
    	}	
    }
}
