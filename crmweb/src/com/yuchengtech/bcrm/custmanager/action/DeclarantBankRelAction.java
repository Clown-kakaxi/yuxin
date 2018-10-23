package com.yuchengtech.bcrm.custmanager.action;

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
@Action(value = "/DeclarantBankRelAction", results = { @Result(name = "success", type = "json")})
public class DeclarantBankRelAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	/**
	 *产品树信息查询SQL
	 */
	public void prepare() {
		StringBuilder sb = new StringBuilder("select f_value,f_code,(case when f_code>100 then  '1' else '0' end ) as parent_code from OCRM_SYS_LOOKUP_ITEM where f_lookup_id='RELATE_DECLARANT_REL1'");
	
		SQL=sb.toString();
		datasource = ds;
		setPrimaryKey("f_code ");
	}
}


