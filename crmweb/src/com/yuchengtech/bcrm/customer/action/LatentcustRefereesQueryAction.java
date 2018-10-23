package com.yuchengtech.bcrm.customer.action;

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
@Action(value = "/latentcustRefereesQuery", results = { @Result(name = "success", type = "json") })
public class LatentcustRefereesQueryAction extends CommonAction {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource dsOracle;

	// 覆盖父类的prepare方法：构造查询列表数据的方法逻辑
	public void prepare() {
		ActionContext ctx = null;
		StringBuilder sb = null;
		try {
			ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			sb = new StringBuilder("");
				sb.append("select * from (  ");
				sb.append("  select c.cust_id cid,c.cust_name cname,c.create_branch_no orgid,ao.org_name corgname,'正式客户' as REFEREESTYPE from ACRM_F_CI_CUSTOMER c  ");
				sb.append("  left join ADMIN_AUTH_ORG ao on c.create_branch_no = ao.org_id  where c.cust_type='2'");
				sb.append(" union ");
				sb.append(" select pc.cus_id cid,pc.cus_name cname,pc.main_br_id orgid,ao.org_name corgname,'潜在客户' as REFEREESTYPE from ACRM_F_CI_POT_CUS_COM pc ");
				sb.append(" left join ADMIN_AUTH_ORG ao on pc.main_br_id = ao.org_id  where pc.cust_type='2' ");
				sb.append(" union ");
				sb.append(" select ac.account_name cid,ac.user_name cname,ac.org_id orgid,ao.org_name corgname ,'用户' as REFEREESTYPE from ADMIN_AUTH_ACCOUNT ac ");
				sb.append(" left join ADMIN_AUTH_ORG ao on ac.org_id = ao.org_id ");
				sb.append("	) t");
			sb.append("      where 1=1 ");
         
			// 构造SQL的查询条件(从前台页面传入的查询条件参数)
			for(String key:this.getJson().keySet()){
       		 if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
       			 if(key.equals("CID"))
       				 sb.append(" and t."+key+"= '"+this.getJson().get(key)+"'");
       			 else if(key.equals("CNAME"))
       				 sb.append(" and  t."+key+ " = '"+this.getJson().get(key)+"'");
       			 else if(key.equals("ORG_ID"))
       				 sb.append(" and t.orgid in ('"+this.json.get(key).toString().replaceAll(",", "','")+"')");
       			 else if(key.equals("MGR_ID"))
       				 sb.append(" and t.CID in ('"+this.json.get(key).toString().replaceAll(",", "','")+"')");
       		 }
       	 }

			// 设置排序
			setPrimaryKey("t.cname desc");

			// 给SQL对象赋值(完整的查询SQL)
			SQL = sb.toString();

			// 数据源
			datasource = dsOracle;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
