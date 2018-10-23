package com.yuchengtech.bcrm.workplat.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

@ParentPackage("json-default")
@Action(value = "/remindListTreeAction", 
results = { @Result(name = "success", type = "json")})
public class RemindListTreeAction extends CommonAction{
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	
	@Override
	public void prepare(){
		StringBuilder sb = new StringBuilder("select 0 as parent_id,t1.F_VALUE AS RULE_VALUE,t1.F_CODE AS RULE_CODE,t1.F_VALUE||'('||sum(case when t2.user_id is not null and t3.remind_id is null then 1 else 0 end)||')' AS t_value" +
				" from ocrm_sys_lookup_item t1" +
				" left join OCRM_F_WP_REMIND t2 on t1.f_code = t2.rule_code and t2.user_id = '"+auth.getUserId()+"'" +
				" left join OCRM_F_WP_REMIND_READ t3 on t3.remind_id = t2.info_id" +
				" where t1.f_lookup_id = 'REMIND_TYPE_QUERY'" +
				//" WHERE T1.F_CODE IN ('300', '301', '306', '307', '308', '309', '310', '311') AND T1.F_LOOKUP_ID LIKE '%REMIND_TYPE%' "+
				" group by t1.F_VALUE, t1.F_CODE" +
				" order by t1.f_code");
//		StringBuilder sb = new StringBuilder("select 0 as parent_id,it.f_value as rule_code,it.f_value||'('||count(1)||')' as t_value  from OCRM_F_WP_REMIND tt" +
//	    	 		" left join ocrm_sys_lookup_item it on tt.rule_code = it.f_code" +
//	    	 		" and F_LOOKUP_ID like '%REMIND_TYPE%'  where tt.user_id = '"+auth.getUserId()+"'" +
//	    	 		" and not exists (select *from OCRM_F_WP_REMIND_READ rr  where rr.remind_id = tt.info_id)");
//    	sb.append(" group by it.f_value");
    	SQL=sb.toString();
    	datasource = ds;
	}
}
