package com.yuchengtech.bcrm.sales.action;

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
@Action(value="/MktActivityZhiHangbackAction", results={
    @Result(name="success", type="json"),
})
public class MktActivityZhiHangBackAction extends CommonAction{

	 @Autowired
		@Qualifier("dsOracle")
		private DataSource ds;
	/**
	 * 支行查询ACTION 
	 */
	@Override
	public void prepare() {
		 AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    	String sqlapp = " select (select sysdate from dual) as NOW_DATE,a1.user_name,o.org_name,a.MKT_ACTI_ID, a.MKT_ACTI_NAME,a.MKT_ACTI_STAT,a.ASTART_DATE, a.AEND_DATE,a.CREATE_USER,a.CREATE_DATE," +
	    			"decode(t.sucess, null, 0, t.sucess) as sucess,decode(t.deliver, null, 0, t.deliver) as deliver,Round(decode(t.sucess, null, 0, t.sucess) / t1.total * 100, 4)||'%' as per " +
	    			" from OCRM_F_MK_MKT_ACTIVITY a , (select sum(case PROGRESS_STAGE when '2' then '1' else '0' end) as sucess," +
	    			" count(1) as deliver,mkt_acti_id from OCRM_F_MK_MKT_MY_ACTI group by mkt_acti_id) t ,(select count(1) as total ,mkt_acti_id from " +
	    			"ocrm_f_mk_acti_customer group by  mkt_acti_id ) t1, ADMIN_AUTH_ACCOUNT a1 " +
	    			" ,sys_units o where  a.mkt_acti_id = t.mkt_acti_id and " +
	    			" t1.mkt_acti_id = t.mkt_acti_id and a.create_user=a1.account_name and o.unitid=a.create_org";
	    	
	    	StringBuilder sb  = new StringBuilder(sqlapp);
	    	
	    	
	    	for(String key:this.getJson().keySet()){
	    		if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
	    	         if(key.equals("MKT_ACTI_NAME"))
	    	             sb.append(" and a."+key+" like  '%"+this.getJson().get(key)+"%'");
	    	         if(key.equals("ORG_ID"))
	    	             sb.append(" and a.create_org ='"+this.getJson().get(key)+"'");
	    	         if(key.equals("CREATE_USER"))
	    	             sb.append(" and a.CREATE_USER ='"+this.getJson().get(key)+"'");
	    	     }
	    	}
	    	sb.append(" and a.create_org in(select u.UNITID from sys_units u where u.UNITSEQ like '%"+auth.getUnitId()+"%')");
	    	
	       	setPrimaryKey("a.MKT_ACTI_ID asc ");

	        	SQL=sb.toString();
	        	datasource = ds;
		}

}
