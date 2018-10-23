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
@Action(value = "/groupMemberInfoShowQuery", results = { @Result(name = "success", type = "json") })
public class GroupMemberInfoShowQueryAction extends CommonAction {

	private HttpServletRequest request;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		// 集团客户编号
	
		String groupNo1 = request.getParameter("groupNo");
		String CUST_ZZDM=request.getParameter("CUST_ZZDM");
		String CUST_NAME=request.getParameter("CUST_NAME");
		String APP_STATUS=request.getParameter("APP_STATUS");
		StringBuffer sb = new StringBuffer(groupNo1);
		String groupNo = sb.substring(0, 7).toString();
		String app_T=sb.substring(8, 13);

		StringBuilder sqlBuilder = new StringBuilder("select t.* from (SELECT c.CUST_ZH_NAME AS CUST_ZH_NAME, "+
				" c1.CUST_ZH_NAME AS parent_cust_zh_name,"+
				" t.ID,"+
				" t.CUST_ID,"+
				" t.STOCK_RATE, "+
				" t.REMARK, "+
				" t.APP_STATUS, "+
				" t.SUBMIT_DATE,"+
				" t.MEMBER_TYPE,"+
				" t.update_date,"+
				" t.GROUP_NO,"+
				" t.UPDATE_USER_ID,"+
				" t.APP_DATE,"+
				" t.RELATION_ID,"+
				" t.HOST_OPINION,"+
				" t.CREDIT_OPINION,"+
				" t.GROUP_NAME, "+
				" t.PARENT_ID,"+
				" t.APP_USER_ID,"+
				" t.APP_USER_NAME, "+
				" t.APP_USER_ORG_ID"+
				" FROM ocrm_f_ci_group_member t"+
				" LEFT JOIN ocrm_f_ci_group_member t1 "+
				" ON t.PARENT_ID = t1.ID"+
				" LEFT JOIN OCRM_F_ci_cust_desc c"+
				" ON c.CUST_ID = t.CUST_ID "+
				" LEFT JOIN OCRM_F_CI_CUST_DESC c1"+
				" ON c1.cust_id = t1.CUST_ID"+
				" WHERE (t.app_status = '0'"+
				" OR t.app_status = '1'"+
				" OR t.app_status = '2'"+
				" OR t.app_status = '3'))t where 1>0");
		
//		if(app_T.equals("10001")||app_T.equals("00001")){
//			sqlBuilder.append("and a.app_status = '2'");
//		}
		
		if(groupNo!=null&&!groupNo.equals("")){
			sqlBuilder.append(" and t.group_no = '"+groupNo+ "' ");
		}
		if(CUST_ZZDM!=null&&!CUST_ZZDM.equals("")){
			sqlBuilder.append(" and t.cust_id like '%"+CUST_ZZDM+ "%' ");
		}
		if(CUST_NAME!=null&&!CUST_NAME.equals("")){
			sqlBuilder.append(" and t.cust_zh_name like '%"+CUST_NAME+ "%' ");
		}
		if(APP_STATUS!=null&&!APP_STATUS.equals("")){
			sqlBuilder.append(" and t.app_status = '"+APP_STATUS+ "' ");
		}
		
//		for (String key : this.getJson().keySet()) {
//			if (null != this.getJson().get(key)
//					&& !this.getJson().get(key).equals("")) {
//				if (key.equals("CUST_ZH_NAME")) {
//					sqlBuilder.append(" and a." + key + " like '%"
//							+ this.getJson().get(key) + "%' ");
//				} else if (key.equals("CUST_ZZDM")) {
//					sqlBuilder.append(" and a." + key + " = '"
//							+ this.getJson().get(key) + "' ");
//				} else if (key.equals("IS_NORMAL_CUST")) {
//					sqlBuilder.append(" and a." + key + " = '"
//							+ this.getJson().get(key) + "' ");
//				}
//			}
//		}

		SQL = sqlBuilder.toString();
		setPrimaryKey("t.cust_id desc");
//		addGreenplumLookup("HY_CLASS", "HYFL");
//		addGreenplumLookup("CUST_SCOPE", "QYGM");
//		addGreenplumLookup("CRM_SCOPE", "KHQYGM");
		addOracleLookup("APP_STATUS", "APP_STATUS");
		addOracleLookup("MEMBER_TYPE", "MEMBER_TYPE");
		addOracleLookup("RELATION_ID", "RELATION_ID");
		datasource = ds;

	}
}
