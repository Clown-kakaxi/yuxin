package com.yuchengtech.bob.action;
/**
 * 集团视图--客户基本信息查询功能
 * */
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action(value="/groupInfoAction2", results={
    @Result(name="success", type="json")
})
public class ClientGroupInfoQuery1Action extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
	       
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 StringBuilder s = new StringBuilder("select t.id as id,t.group_type as group_type,(select u.unitname from sys_units u where u.unitid = t.group_host_org_no ) as group_host_org_no_name,(select count(*) from ocrm_f_ci_group_member mem where mem.group_no =t.group_no and (mem.member_type='1') ) as group_number1,(select count(*) from ocrm_f_ci_group_member mem where mem.group_no =t.group_no and (mem.member_type='2') ) as group_number2,t.group_name as group_name,t.group_status as group_status,t.group_root_cust_id as group_root_cust_id,t.update_date as update_date,t.group_memo as group_memo,t.group_no as group_no,t.update_user_id as update_user_id,t.group_host_org_no as group_host_org_no,t.group_root_address as group_root_address,t.creata_date as creata_date,t.create_user_id as create_user_id ,t1.cust_zh_name as cust_zh_name from ocrm_f_ci_group_info t inner join v_acrm_f_ci_cust_info t1 on t.group_root_cust_id = t1.cust_id  where  t.group_no='");
    	 String groupId = request.getParameter("groupNo");
    	 s.append(groupId+"'");

 		SQL = s.toString();  
    	setPrimaryKey("t.id desc");
    	   
   		addOracleLookup("GROUP_TYPE", "GROUP_TYP");
		addOracleLookup("GROUP_STATUS", "GROUP_STS");
		datasource = ds;
	}
}




