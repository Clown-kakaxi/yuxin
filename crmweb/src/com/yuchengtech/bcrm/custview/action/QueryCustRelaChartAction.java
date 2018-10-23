package com.yuchengtech.bcrm.custview.action;

import java.sql.SQLException;
import java.util.HashMap;

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
import com.yuchengtech.bob.core.QueryHelper;

/**
 * @describtion: 客户关联图查看
 *
 * @author : wmk
 * @date : 2014年8月26日 下午5:00:40
 */
@ParentPackage("json-default")
@Action(value = "/querycustrelachart", results = { @Result(name = "success", type = "json") })
public class QueryCustRelaChartAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	public String showchart(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");

		StringBuilder sb = new StringBuilder("select * from ACRM_F_CI_CUST_RELATE t " +
			"inner join ocrm_sys_lookup_item t1 on t1.f_lookup_id = 'CUS0100038' and t1.f_code = t.RELATIONSHIP " +
			"where t.cust_id='"+custId+"' or t.cust_no_r='"+custId+"'");
		try {
			if(this.json!=null)
	    		this.json.clear();
	    	else 
	    		this.json = new HashMap<String,Object>(); 
			this.json.put("json", new QueryHelper(sb.toString(), ds.getConnection()).getJSON());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
}
