package com.yuchengtech.bcrm.customer.level.action;

/**
 * 指标树查询
 */
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuchengtech.bcrm.customer.level.service.IndexSearchService;
import com.yuchengtech.bob.action.BaseAction;

@ParentPackage("json-default")
@Action(value = "/indetree", results = { @Result(name = "success", type = "json"), })
public class IndexTreeSearchAction extends BaseAction{
	
	@Autowired
	IndexSearchService oss;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String index() throws Exception{
		setJson(oss.searchIndexTypeTree());
		return "success";
	}
	
	
}
