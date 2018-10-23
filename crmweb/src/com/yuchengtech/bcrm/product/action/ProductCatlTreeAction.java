package com.yuchengtech.bcrm.product.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

/**
 * 
 * 银行产品树--左侧功能模块树的初始化
 * @author weilh
 * @since 2012-11-08
 */

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/productCatlTreeAction", results = { @Result(name = "success", type = "json")})
public class ProductCatlTreeAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	/**
	 *产品树信息查询SQL
	 */
	public void prepare() {
		StringBuilder sb = new StringBuilder("SELECT t.CATL_PARENT,t.CATL_CODE,t.CATL_NAME,t.PROD_VIEW,t.VIEW_DETAIL FROM OCRM_F_PD_PROD_CATL t WHERE 1>0 ORDER BY t.CATL_ORDER ASC");
	
		SQL=sb.toString();
		datasource = ds;
	}
}


