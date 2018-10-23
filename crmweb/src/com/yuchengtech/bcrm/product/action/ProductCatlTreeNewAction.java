package com.yuchengtech.bcrm.product.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;


@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/productCatlTreeNewAction", results = { @Result(name = "success", type = "json")})
public class ProductCatlTreeNewAction extends CommonAction{
	//数据源
		@Autowired
		@Qualifier("dsOracle")
		private DataSource ds;
	   
		/**
		 *产品树信息查询SQL
		 */
		public void prepare() {
			StringBuilder sb = new StringBuilder("SELECT a.* "+
					" FROM VIEW_COM_PRODUCT_CATL_TREE b, OCRM_F_PD_PROD_CATL a "+
					" WHERE b.b_catl_code = a.catl_code "+
					" order by a.catl_code ");
			SQL=sb.toString();
			datasource = ds;
		}

}
