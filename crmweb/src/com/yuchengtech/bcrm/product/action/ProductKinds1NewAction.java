package com.yuchengtech.bcrm.product.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

@ParentPackage("json-default")
@Action("/product-kinds1New")
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "product-kinds1" }) })
public class ProductKinds1NewAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	@Override
	public void prepare() {
		StringBuilder sb = new StringBuilder("SELECT DISTINCT t.CATL_CODE AS CATL_CODE,t.CATL_NAME AS CATL_NAME,t.CATL_PARENT AS CATL_PARENT,t.CATL_LEVEL AS CATL_LEVEL,t.CATL_ORDER AS CATL_ORDER,t.VIEW_DETAIL AS VIEW_DETAIL," +
				"decode(t1.catl_code,'',0,null,0 ,t1.catl_code) as CATL_PARENT," +
				"decode(t1.CATL_NAME,'','银行产品树',null,'银行产品树' ,t1.CATL_NAME) AS CATL_PARENT_NAME," +
				"t.prod_view FROM "
				+ "  ( SELECT a.* "+
				"FROM VIEW_COM_PRODUCT_CATL_TREE b, OCRM_F_PD_PROD_CATL a "+
				" WHERE b.b_catl_code = a.catl_code "+
				" order by a.catl_code) t "+
				" LEFT JOIN OCRM_F_PD_PROD_CATL t1 " +
				" ON  t.CATL_PARENT = t1.CATL_CODE WHERE 1 = 1 ");		
		for(String key:this.getJson().keySet()){			
			String s=(String)this.getJson().get(key);
			if(null!=key&&key.equals("CATL_CODE")){
				sb.append(" and t.CATL_CODE = "+s+"or t.CATL_PARENT = "+s);								
			}else if(null!=key&&key.equals("CATL_NAME")){
				sb.append(" and t.CATL_NAME like '%"+s+"%'");				
			}
		}		
		SQL = sb.toString();
		setPrimaryKey("t.CATL_CODE");
		addOracleLookup("APP_STATUS", "APP_STATUS");
		datasource = ds;
	}

}
