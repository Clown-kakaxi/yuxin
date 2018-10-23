package com.yuchengtech.bcrm.custview.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

/**
 * 个人理财产品树
 * @author denghj
 *
 */
@SuppressWarnings("serial")
@Action("/personalAssetsProductInfoTree")
public class PersonalAssetsProductInfoTreeAction extends CommonAction {
	//数据源
		@Autowired
		@Qualifier("dsOracle")
		private DataSource ds;
	   
		/**
		 *个人理财产品树信息查询SQL
		 */
		public void prepare() {
			StringBuilder sb = new StringBuilder("");
			sb.append("select t.catl_parent,t.catl_code,t.catl_name,t.catl_level ");
			sb.append("from (select c.catl_parent,c.catl_code,c.catl_name,'1' catl_level from OCRM_F_PD_PROD_CATL c ");
            sb.append("where c.catl_code in (select i.catl_code from OCRM_F_PD_PROD_INFO i where i.type_fit_cust in ('1,2','2') union  select l.catl_parent ");
            sb.append("from OCRM_F_PD_PROD_INFO i left join OCRM_F_PD_PROD_CATL l on i.catl_code=l.catl_code  where i.type_fit_cust in ('1,2','2')) ");      
			sb.append("union all ");   
            sb.append("select o.catl_code catl_parent,to_number(o.product_id) catl_code,o.prod_name catl_name,'2' catl_level ");
            sb.append("from OCRM_F_PD_PROD_CATL c inner join OCRM_F_PD_PROD_INFO o on c.catl_code = o.catl_code where o.product_id is not null and o.type_fit_cust in('1,2','2')) t ");
            sb.append("where 1 > 0 ");
          
			SQL=sb.toString();
			datasource = ds;
		}
}
