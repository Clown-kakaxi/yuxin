package com.yuchengtech.bcrm.customer.action;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 灵活查询  
 *  
 * @author Sena
 */
@ParentPackage("json-default")
@Action(value = "/queryagilequery", results = { @Result(name = "success", type = "json"), })
public class QueryAgileQueryAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;

	
	private Map<String, Object> JSON;

	public Map<String, Object> getJSON() {
		return JSON;
	}

	public void setJSON(Map<String, Object> jSON) {
		JSON = jSON;
	}

	public String index() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		try {
			StringBuilder sb = new StringBuilder(
//					"SELECT id AS nodeid,name,parent_id,'1' AS tables,'' AS ename, value,'' AS ctype,'' AS NOTES FROM MTOOL_DBTABLE  where parent_id <>'84' and id<>'84' UNION SELECT id AS nodeid,col_name_c AS name,dbtable_id AS parent_id,'2' AS tables,col_name_e AS ename, '' AS value,COL_TYPE AS ctype,NOTES FROM MTOOL_DBCOL WHERE IS_ENABLE='true'"
" SELECT id AS nodeid,name,parent_id,'1' AS tables,'' AS ename, value,'' AS ctype,'' AS NOTES FROM MTOOL_DBTABLE  where parent_id ='1' or id='1' UNION SELECT id AS nodeid,col_name_c AS name,dbtable_id AS parent_id,'2' AS tables,col_name_e AS ename, '' AS value,COL_TYPE AS ctype,NOTES FROM MTOOL_DBCOL WHERE IS_ENABLE='true' " +
"and dbtable_id in (select id from MTOOL_DBTABLE where parent_id = '1' or id = '1' )"
			);
			setJSON(new QueryHelper(sb.toString(), ds.getConnection())
					.getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	public String queryAgileSolution()  {
	/*	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);*/
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = auth.getUserId();
		try {
			StringBuilder sb = new StringBuilder(
					"select * from OCRM_F_A_SEARCHSOLUTION  where 1=1");
			sb.append(" and ss_user='" + userId+"'");
			setJSON(new QueryHelper(sb.toString(), ds.getConnection())
					.getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	public String queryAgileCondition() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
		/*	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        String userId = auth.getUserId();*/
			try {
				StringBuilder sb = new StringBuilder(
						"	select t1.*,t2.COL_NAME_C,t2.COL_NAME_E,t2.COL_TYPE,t3.VALUE,t3.id as tableid from OCRM_F_A_SS_COL t1 left join MTOOL_DBCOL t2 on t1.ss_col_item=t2.ID left join  MTOOL_DBTABLE t3 on t2.DBTABLE_ID=t3.id  where 1=1");
				sb.append(" and t1.ss_id=" + request.getParameter("SS_ID"));
				setJSON(new QueryHelper(sb.toString(), ds.getConnection())
						.getJSON());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return "success";
		}
	
	//查询客户群筛选方案
	public String queryGroupCondition() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String id= request.getParameter("groupId");
		try {
			StringBuilder sb = new StringBuilder(
					"	select t1.*,t2.COL_NAME_C,t2.COL_NAME_E,t2.COL_TYPE,t3.VALUE,t3.id as tableid from OCRM_F_A_SS_COL t1 left join MTOOL_DBCOL t2 on t1.ss_col_item=t2.ID left join  MTOOL_DBTABLE t3 on t2.DBTABLE_ID=t3.id ");
			if(id!=null&&!"".equals(id))
				sb.append(" where 1=1 and t1.ss_id=(select id from  OCRM_F_CI_BASE_SEARCHSOLUTION where group_id="+id+")" );
			else
				sb.append(" where 1<0");
			setJSON(new QueryHelper(sb.toString(), ds.getConnection())
					.getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
	public String queryAgileCondition2() {
		try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			
			
			StringBuilder sb = new StringBuilder("with n(str, ori, pos) as " +
		"(values ((select ss_result from OCRM_F_A_SEARCHSOLUTION where ID=" +
		request.getParameter("agileConditionId") +
		"), 1, posstr((select ss_result from OCRM_F_A_SEARCHSOLUTION where ID=" +
		request.getParameter("agileConditionId") +
		"), ',')) " +
		"union all select str, pos+1, locate(',', str, pos+1) from n where locate(',', str, pos+1)>0) " +
		"select t3.COL_NAME_C,t3.COL_NAME_E from (select cast(t1.result as bigint) as result from (select str, ori, pos, substr(str, ori, pos-ori) as result   from  n )t1)t2 " +
		"left join MTOOL_DBCOL t3 on t2.result=t3.id");
			setJSON(new QueryHelper(sb.toString(), ds.getConnection())
					.getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return "success";
		}
	//查询表间关系
	public String queryTableRelation() {

		try {
			StringBuilder sb = new StringBuilder(
					"select t1.*,t2.value as lefttable,t3.value as righttable,t4.COL_NAME_E as leftcode,t5.COL_NAME_E as rightcode from OCRM_F_A_SS_RELATION t1 "+
					" left join MTOOL_DBTABLE t2 on t1.JOIN_LEFT_TABLE=t2.ID"+
					" left join MTOOL_DBTABLE t3 on t1.JOIN_RIGHT_TABLE=t3.ID"+
					" left join MTOOL_DBCOL t4 on t1.JOIN_LEFT_COL=t4.ID"+
					" left join MTOOL_DBCOL t5 on t1.JOIN_RIGHT_COL=t5.ID ");
			setJSON(new QueryHelper(sb.toString(), ds.getConnection())
					.getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}


}
