package com.yuchengtech.bcrm.customer.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.product.service.OcrmFPdProdShowPlanService;
import com.yuchengtech.bcrm.product.service.TargetCusSearchService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 客户视图-客户贡献度
 * @author geyu
 * 2014-7-23
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/custContriInfoQuery", results = { @Result(name = "success", type = "json")})
public class CustContriInfoQueryAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private OcrmFPdProdShowPlanService service;
	@Autowired
	private TargetCusSearchService targetCusSearchService;
	
	@Override
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    String custId=request.getParameter("custId");
   	    String groupId=request.getParameter("groupId");
   		StringBuffer sb= null;
   		//客户视图
   		if(custId != null && !"".equals(custId)){
   			sb = new StringBuffer("select * from ACRM_F_CI_CUST_CONTRIBUTION  t where t.cust_id='"+custId+"'");
   		}else if(groupId != null && !"".equals(groupId)){
   			//客户群
   			sb = new StringBuffer("select * from ACRM_F_CI_CUST_CONTRIBUTION  t where t.cust_id in "+getGroupNumber(groupId));
   		}
   		
		SQL=sb.toString();
		datasource = ds;
		
		setPrimaryKey("t.ETL_DATE desc");
	}
	
	/***
     * 根据baseId查询客户群包含客户的sql
     */
    @SuppressWarnings("unchecked")
	public String getGroupNumber(String base_id) {
		String ids = "";
		StringBuilder sb = new StringBuilder("");

		// 获取客户群类型
		String type = "";
		String custFrom = "";

		List<Object[]> list = null;
		list = service.getBaseDAO().findByNativeSQLWithIndexParam("select CUST_FROM ,GROUP_MEMBER_TYPE from OCRM_F_CI_BASE where id='"+ base_id + "'");
		if (list.size() > 0) {
			type = list.get(0)[0].toString();
			custFrom = list.get(0)[1].toString();
		}

		if ("1".equals(type) || "3".equals(type)) {// 手动添加和证件导入的，直接在关联客户表中查询
			sb.append(" (select cust_id from OCRM_F_CI_RELATE_CUST_BASE where CUST_BASE_ID='"+ base_id + "')");
		} else {// 筛选方案客户群，需要拼接客户群成员查询sql
				// 1.拼接查询条件的map
			JSONArray jaCondition = new JSONArray();
			String radio = "false";
			Map<String, Object> map = new HashMap<String, Object>();
			list = service.getBaseDAO().findByNativeSQLWithIndexParam(" select ss_col_item,ss_col_op,ss_col_value,ss_col_join from OCRM_F_A_SS_COL where ss_id=(select id from OCRM_F_CI_BASE_SEARCHSOLUTION where group_id='"+ base_id + "')");
			if (list.size() > 0) {
				for (Object[] o : list) {
					if ("true".equals(o[3].toString()))
						radio = "true";
					map.put("ss_col_item", o[0].toString());
					map.put("ss_col_op", o[1].toString());
					map.put("ss_col_value", o[2].toString());
					jaCondition.add(map);
				}
			}
			// 2.拼接sql语句
			String res = targetCusSearchService.generatorSql(jaCondition, radio);
			// 3.查询
			sb.append("( " + res);
			if ("1".equals(custFrom) || "2".equals(custFrom)) {// 客户群的类别有要求// 2：对私，1：对公
				sb.append(" and custinfo.CUST_TYPE='" + custFrom + "'");
			}
			sb.append(") ");
		}

		ids = sb.toString();
		return ids;

	}
}
