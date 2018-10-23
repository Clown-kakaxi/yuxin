package com.yuchengtech.bcrm.customer.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.model.OcrmFCiGroupMember;
import com.yuchengtech.bcrm.customer.service.GroupCustMenberService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.core.QueryHelper;
/**
 * 
* @ClassName: GroupCustMenberListAction 
* @Description: 集团成员列表
* @author wangmk1 
* @date 2014-7-21 
*
 */
@Action("/groupCustMenberList")
public class GroupCustMenberListAction extends CommonAction {
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	
	@Autowired
	private GroupCustMenberService groupCustMenberService;
	
	@Autowired
	public void init(){
		model= new OcrmFCiGroupMember();
		setCommonService(groupCustMenberService);
	}
	
	@Override
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String id = request.getParameter("id");
		StringBuffer sb=new StringBuffer("SELECT m.*,a.user_name,o.org_name FROM OCRM_F_CI_GROUP_MEMBER m left join admin_auth_account a on m.cus_manager =a.account_name left join admin_auth_org o on m.main_br_id=o.org_id  WHERE m.GROUP_NO=(SELECT GROUP_NO FROM OCRM_F_CI_GROUP_INFO WHERE ID="+id+")");
		setPrimaryKey("m.ID desc ");
		SQL=sb.toString();
		datasource=ds;
		configCondition("CUST_ID","like","CUST_ID",DataType.String);
		configCondition("CORE_CUST_NO","=","CORE_CUST_NO",DataType.String);
		configCondition("CORP_NAME_UP","like","CORP_NAME_UP",DataType.String);
		configCondition("MEMBER_TYPE","like","MEMBER_TYPE",DataType.String);
		configCondition("MEMBER_SHIP","like","MEMBER_SHIP",DataType.String);
		configCondition("STOCK_RATE","like","STOCK_RATE",DataType.String);
		configCondition("CROP_CODE","like","CROP_CODE",DataType.String);
		configCondition("CUST_NAME","like","CUST_NAME",DataType.String);
		configCondition("REMARK","like","REMARK",DataType.String);
	}
	/**
	 * 批量删除 
	 * @return
	 */
	public String batchDestory(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String ids = request.getParameter("idStr");
		String custIds = request.getParameter("custIds");
		ids=ids.substring(0, ids.length()-1);
		custIds=custIds.substring(0, custIds.length()-1);
		String[] strArray = null;   
        strArray = custIds.split(",");
		String groupNo = request.getParameter("groupNo");
		//判断要删除的成员是否存在组织关系，若存在则不能删除。
    	try {
			StringBuilder sb = new StringBuilder("");
			sb.append("select from_vertex,to_vertex from OCRM_F_CI_GROUP_MEMBERSHIP where group_no= '"+groupNo+"' ");
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			//对应的root为data
			String ss= sb.toString();
			this.json=new QueryHelper(ss, ds.getConnection()).getJSON();
			List<?> lst=(List<?>) this.json.get("data");
			for(int i=0;i<lst.size(); i++){   
				@SuppressWarnings("unchecked")
				Map<String,String> map = (Map<String, String>) lst.get(i);
				String s1=map.get("FROM_VERTEX");
				String s2=map.get("TO_VERTEX");
				for(int t=0;t<strArray.length;t++){
					if(strArray[t].equals(s1)||strArray[t].equals(s2)){
						this.json.clear();
						this.json.put("data", "failure");
						return "failure";
					}
				}
			}   
		} catch (SQLException e) {
			e.printStackTrace();
		}
		groupCustMenberService.batchRemove(ids);
		this.json.clear();
		this.json.put("data", "success");
		return "success";
	}
}
