package com.yuchengtech.bcrm.customer.action;

import java.util.Iterator;
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
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.service.AgileSearchService;
import com.yuchengtech.bcrm.system.model.DataSetColumn;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value="/queryagileresult", results={
    @Result(name="success", type="json")
})
public class QueryAgileResultAction extends CommonAction{
    
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;

	@Autowired
	private AgileSearchService agileSearchService;
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	@Override
    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		
		String conditionAttrs = request.getParameter("conditionAttrs");
		String results = request.getParameter("results");
		String radio = request.getParameter("radio");
		String groupParams = request.getParameter("groupParams");
		String sumParams = request.getParameter("sumParams");
		
		JSONArray jaCondition = JSONArray.fromObject(conditionAttrs);
		JSONArray jaColumns = JSONArray.fromObject(results);
		
		
		Map<String, Object> res = agileSearchService.generatorSql(jaCondition, jaColumns,radio, groupParams, sumParams);
		SQL=(String)res.get("SQL");
		//建议使用数据权限过滤器
//	    String level = auth.getUnitlevel();
//	    if(!"1".equals(level)){
//	    	SQL+= "  and custInfo.cust_id in (select cust_id from ocrm_f_ci_belong_custmgr where mgr_id='"+auth.getUserId()+"')";
//	    }
	    String groupMemberType = request.getParameter("groupMemberType");
	    if("2".equals(groupMemberType)){//对私客户群	
	    	SQL +=" and custInfo.cust_type='2' ";
	    }else if("1".equals(groupMemberType)){//对公客户群	
	    	SQL +=" and custInfo.cust_type='1' ";
	    }
		Map<DataSetColumn,String> lookups = (Map<DataSetColumn,String>)res.get("lookupColumns");
		Iterator<DataSetColumn> itl = lookups.keySet().iterator();
		while(itl.hasNext()){
			DataSetColumn dsc = itl.next();
			this.addOracleLookup(dsc.getColNameE()+"_"+lookups.get(dsc),  dsc.getNotes());
		}
		
		datasource = ds;
    }
	
}
