package com.yuchengtech.bcrm.product.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.constance.SystemConstance;

/**
 * 营销模板的产品树-只查询对私产品
 * @author geyu
 * 2014-8-13
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/perProductCatlTreeAction", results = { @Result(name = "success", type = "json")})
public class PerProductCatlTreeAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	/**
	 *产品树信息查询SQL
	 */
	public void prepare() {
		StringBuilder sb = new StringBuilder("select distinct c0.CATL_PARENT, c0.CATL_CODE, c0.CATL_NAME, c0.PROD_VIEW, c0.VIEW_DETAIL"+
									" from ocrm_f_pd_prod_catl c0"+
									" start with c0.catl_code in (select distinct c.catl_code from ocrm_f_pd_prod_catl c "+
											" inner join ocrm_f_pd_prod_info i on i.catl_code = c.catl_code " +
											" where i.type_fit_cust in ('2', '1,2'))"+
									" connect by c0.catl_code = prior c0.catl_parent");
		StringBuilder withSb = new StringBuilder();
		if("DB2".equals(SystemConstance.DB_TYPE)) {
			withSb.append("with rpl (CATL_CODE,CATL_PARENT,CATL_NAME, PROD_VIEW, VIEW_DETAIL) as (" +
					" select CATL_CODE,CATL_PARENT,CATL_NAME, PROD_VIEW, VIEW_DETAIL  from ocrm_f_pd_prod_catl  where CATL_CODE in " +
					"(select c.catl_code from ocrm_f_pd_prod_catl c  inner join ocrm_f_pd_prod_info i on i.catl_code = c.catl_code " +
					" where i.type_fit_cust in ('2', '1,2')) " +
					" union all select  child.CATL_CODE,child.CATL_PARENT,child.CATL_NAME,child.PROD_VIEW, child.VIEW_DETAIL from rpl parent, ocrm_f_pd_prod_catl child where child.CATL_CODE=parent.CATL_PARENT" +
					" ) " +
					" select distinct CATL_CODE,CATL_PARENT,CATL_NAME,PROD_VIEW, VIEW_DETAIL  from rpl");
		}
		withSQL = withSb.toString();
		SQL=sb.toString();
		datasource = ds;
	}

}
