package com.yuchengtech.bcrm.payOrRepay.action;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.action.BaseQueryAction;
import com.yuchengtech.bob.core.QueryHelper;

/**
 * 数据权限下拉列表查询
 * @author GUOCHI
 * @since 2012-11-28
 */


@ParentPackage("json-default")
@Action(value = "/branchSearchAction", results = { @Result(name = "success", type = "json") })
public class BranchSearchAction extends BaseQueryAction {

    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;//数据源
    public void prepare(){}
    /**
     * 分支行码值
     */
    /*@SuppressWarnings("unchecked")
	public void searchBranch() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String superUnit = request.getParameter("superUnit"); // 获取需要提交的记录id

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT UNIT.ORG_NAME as VALUE,UNIT.UNITID as KEY FROM SYS_UNITS UNIT WHERE UNITSEQ LIKE '500%'  ");
		if(null != superUnit && !superUnit.equals("")){
			sb.append(" and levelunit='3'   and superunitid ='"+ superUnit +"' ");
		}	
		else{
			sb.append(" order by UNIT.levelunit,UNIT.id ");
		}
		
		
		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("KEY", "");
//			map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
}*/
    @SuppressWarnings("unchecked")
	public void searchBranch() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT UNIT.ORG_NAME AS VALUE, UNIT.UNITID AS KEY FROM SYS_UNITS UNIT WHERE LEVELUNIT IN ('3','1')");

		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("KEY", "");
//			map.put("VALUE", "无");
			((List) json.get("data")).add(map);

		} catch (Exception e) {
		}
}
	@SuppressWarnings("unchecked")
	public void searchCQProgress() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String deptOrDra = request.getParameter("deptOrDra"); // 获取需要提交的记录id

		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT I.F_VALUE AS VALUE,I.F_CODE AS KEY FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='CQPROGRESS' ");
		if(null == deptOrDra || deptOrDra.equals("")){
			sb.append("  ");
		}	
		else if(deptOrDra .equals("0")){//存款
			sb.append(" AND I.F_CODE IN('0','2','3')");
		}
		else {
			sb.append("  AND I.F_CODE IN('1','2','4')");
		}		
		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("KEY", "");
//			map.put("VALUE", "无");
			((List) json.get("data")).add(map);
		} catch (Exception e) {
		}
	}
	@SuppressWarnings("unchecked")
	public void searchProgress() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String deptOrDra = request.getParameter("deptOrDra"); // 获取需要提交的记录id
		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT I.F_VALUE AS VALUE,I.F_CODE AS KEY FROM OCRM_SYS_LOOKUP_ITEM I WHERE I.F_LOOKUP_ID='PROGRESS' ");
		if(null !=deptOrDra && !deptOrDra.equals("")){
			if(deptOrDra .equals("001")){//拨款
				sb.append(" AND I.F_CODE IN('001','002')");
			}	
			else if(deptOrDra .equals("002")){
				sb.append("  AND I.F_CODE IN('003','004')");
			}
		}		
		else {
			sb.append(" ");
		}		
		SQL = sb.toString(); // 为父类SQL属性赋值（设置查询SQL）
		try {
			QueryHelper qh = new QueryHelper(SQL.toString(), ds.getConnection());
			json = qh.getJSON();
			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("KEY", "");
//			map.put("VALUE", "无");
			((List) json.get("data")).add(map);
		} catch (Exception e) {
		}
	}
}
