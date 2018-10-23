package com.yuchengtech.bcrm.customer.action;



import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;

@ParentPackage("json-default")
@Action(value="/cusRelationshipSchedu", results={
	    @Result(name="success", type="json"),})
public class CustomerRelationshipScheduleAction extends CommonAction {
private static final long serialVersionUID = 1L;

@Autowired	
@Qualifier("dsOracle")	
private DataSource dsOracle;  


//private HttpServletRequest request;

//private Map<String, Object> map = new HashMap<String, Object>();
 	public void prepare() {	
 		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        StringBuilder sb = new StringBuilder();
             sb.append(" select * from ( ");
        	 sb.append("select c.POTENTIAL_FLAG,c.source_channel,c.cust_id,c.cust_name,c.ident_type,c.ident_no,c.cust_type,c.cust_level,c.job_type,c.indust_type," +
             		"c.linkman_name,c.linkman_tel,c.cust_stat,m.institution_name as org_name,m.mgr_name,m.mgr_id,m.INSTITUTION as org_id,m.INSTITUTION INSTITUTION,d.addr," 
             		+ "L.BL_ID,L.BL_NAME as BL_NAME,k1.is_listed_corp,o.ent_property,o.LEGAL_REPR_NAME,o.EMPLOYEE_SCALE,r.REGISTER_AREA,o.MAIN_BUSINESS ,"
             		+ "to_char(to_char(SYSDATE,'yyyy')-to_char(o.BUILD_DATE,'yyyy')) as spanyears,b.sale_amt,c.CREATE_DATE as BASIC_ACCT_OPEN_DATE, "
             		+ "p.id,p.plan_id,p.corp_profile,p.corp_culture,p.sale_estimate,p.sale_range_estimate,p.sale_area,p.purchase_area,p.settle_type_fir,p.settle_type_fir_scale,"
             		+ "p.settle_type_sec,p.settle_type_sec_scale,p.settle_type_thir,p.settle_type_thir_scale,p.receivables_cycle,p.purchase_type_fir,p.purchase_type_fir_scale,"
             		+ "p.purchase_type_sec,p.purchase_type_sec_scale,p.purchase_type_thir,p.purchase_type_thir_scale,p.accounts_payable_cycle,p.main_material,p.material_ammount,"
             		+ "p.receivables_currence,p.accounts_payable_currence,p.export_volume,p.import_volume,p.credit_level,p.cb_levle,p.line_of_credit,p.outstanding_loan,p.next_annual_time,  ");
        	 sb.append("  r1.id ANALYSIS_ID,r1.deposit_rmb_average,r1.deposit_rmb_margin,r1.deposit_rmb_proportion,r1.deposit_trade_average,r1.deposit_trade_margin,r1.deposit_trade_proportion,"
        	 		+ "r1.deposit_other_average,r1.deposit_other_margin,r1.deposit_other_proportion,r1.exchange_immediate_average,r1.exchange_immediate_margin,r1.exchange_immediate_proportion,"
        	 		+ "r1.exchange_forward_average,r1.exchange_forward_margin,r1.exchange_forward_proportion,r1.exchange_interest_average,r1.exchange_interest_margin,r1.exchange_interest_proportion,"
        	 		+ "r1.options_trading_average,r1.options_trading_margin,r1.options_trading_proportion,r1.trade_financing_average,r1.trade_financing_margin,r1.trade_financing_proportion,r1.trade_factoring_average,"
        	 		+ "r1.trade_factoring_margin,r1.trade_factoring_proportion,r1.trade_discount_average,r1.trade_discount_margin,r1.trade_discount_proportion,r1.trade_acceptance_average,r1.trade_acceptance_margin,"
        	 		+ "r1.trade_acceptance_proportion,r1.trade_credit_average,r1.trade_credit_margin,r1.trade_credit_proportion,r1.trade_guarantee_average,r1.trade_guarantee_margin,r1.trade_guarantee_proportion,"
        	 		+ "r1.loan_average,r1.loan_margin,r1.loan_proportion,r1.suit_products,r1.walletsize_products,r1.provide_products,  ");
        	 sb.append("  rn.id ANAL_ID,rn.deposit_rmb_average_ny,rn.deposit_rmb_margin_ny,rn.deposit_rmb_proport_ny,rn.deposit_trade_average_ny,rn.deposit_trade_margin_ny,rn.deposit_trade_proport_ny,"
        	 		+ "rn.deposit_other_average_ny,rn.deposit_other_margin_ny,rn.deposit_other_proport_ny,rn.exchange_immediate_average_ny,rn.exchange_immediate_margin_ny,rn.exchange_immediate_proport_ny,"
        	 		+ "rn.exchange_forward_average_ny,rn.exchange_forward_margin_ny,rn.exchange_forward_proport_ny,rn.exchange_interest_average_ny,rn.exchange_interest_margin_ny,rn.exchange_interest_proport_ny,"
        	 		+ "rn.options_trading_average_ny,rn.options_trading_margin_ny,rn.options_trading_proport_ny,rn.trade_financing_average_ny,rn.trade_financing_margin_ny,rn.trade_financing_proport_ny,"
        	 		+ "rn.trade_factoring_average_ny,rn.trade_factoring_margin_ny,rn.trade_factoring_proport_ny,rn.trade_discount_average_ny,rn.trade_discount_margin_ny,rn.trade_discount_proport_ny,"
        	 		+ "rn.trade_acceptance_average_ny,rn.trade_acceptance_margin_ny,rn.trade_acceptance_proport_ny,rn.trade_credit_average_ny,rn.trade_credit_margin_ny,rn.trade_credit_proport_ny,"
        	 		+ "rn.trade_guarantee_average_ny,rn.trade_guarantee_margin_ny,rn.trade_guarantee_proport_ny,rn.loan_average_ny,rn.loan_margin_ny,rn.loan_proport_ny,rn.suit_products_ny,rn.walletsize_products_ny,rn.provide_products_ny,"
        	 		+ " 0 as isflag,  ");
        	 sb.append(" case when N1.custcod is not null then '1' else '0' end isdebts,case when N2.custcod is not null then '1' else '0' end ismeans ");
        	 sb.append("  from ACRM_F_CI_CUSTOMER c left join OCRM_F_CI_BELONG_CUSTMGR m on c.cust_id = m.cust_id ");
        	 sb.append(" left join acrm_f_ci_org o on o.CUST_ID = c.CUST_ID ");
        	 sb.append(" LEFT JOIN ACRM_F_CI_BUSI_LINE L ON o.org_biz_cust_type = to_char(L.BL_NO) ");
        	 sb.append(" left join ACRM_F_CI_ORG_KEYFLAG k1 on k1.cust_id = c.cust_id  ");
        	 sb.append(" left join ACRM_F_CI_ORG_REGISTERINFO r on o.cust_id = r.cust_id  ");
        	 sb.append(" left join ACRM_F_CI_ORG_BUSIINFO b on b.cust_id = c.cust_id  ");
        	 sb.append(" left join OCRM_F_CI_RELATION_ANALYSIS r1 on r1.cust_id = c.cust_id ");
        	 sb.append(" left join OCRM_F_CI_RELATION_ANAL_NY rn on rn.cust_id = c.cust_id ");
        	 sb.append(" left join OCRM_F_CI_RELATIONPLAN_PATTERN p on p.cust_id = c.cust_id   ");
        	 sb.append(" left join  ( select DISTINCT CUSTCOD from ACRM_F_CI_PRO_AMOUNT T WHERE TRIM(T.PRODUCT) IN  (select trim(Per_pro) from ACRM_F_CI_PRODUCT  where TRIM(IS_debts) = '1')) N1 ON C.CUST_ID = N1.CUSTCOD  ");
        	 sb.append(" left join  ( select DISTINCT CUSTCOD from  ACRM_F_CI_PRO_AMOUNT T WHERE TRIM(T.PRODUCT) IN  (select trim(Per_pro) from ACRM_F_CI_PRODUCT  where TRIM(IS_means) = '1')) N2 ON C.CUST_ID = N2.CUSTCOD  ");
        	 sb.append(" left join ACRM_F_CI_ADDRESS d on d.cust_id=c.cust_id and d.ADDR_TYPE='02' where 1 = 1 ");
        	
        	 sb.append(" union ");
        	 
        	 sb.append(" select '' POTENTIAL_FLAG ,'' source_channel,pc.cus_id cust_id,pc.cus_name cust_name,pc.cert_type ident_type,pc.cert_code ident_no,pc.cust_type cust_type,"
        	 		+ "'' cust_level,pc.job_type job_type,pc.indust_type indust_type,pc.atten_name linkman_name,pc.atten_phone linkman_tel,pc.cust_stat cust_stat,m1.institution_name as org_name,"
        	 		+ " case when ac.user_name  is null then (select o.org_name from admin_auth_org o where o.org_id=pc.cust_mgr) else ac.user_name end mgr_name,"
        	 		+ "pc.main_br_id mgr_id,m1.INSTITUTION as org_id,m1.INSTITUTION INSTITUTION,'' addr,L1.BL_ID,L1.BL_NAME as BL_NAME,k2.is_listed_corp,o1.ent_property,pc.legal_name legal_repr_name,"
        	 		+ "o1.employee_scale,pc.cus_addr REGISTER_AREA,o1.MAIN_BUSINESS,to_char(pc.q_operateyears) spanyears,pc.q_lyearincome sale_amt,to_date('') BASIC_ACCT_OPEN_DATE,"
        	 		+ "p1.id,p1.plan_id,p1.corp_profile,p1.corp_culture,p1.sale_estimate,p1.sale_range_estimate,p1.sale_area,p1.purchase_area,p1.settle_type_fir,p1.settle_type_fir_scale,p1.settle_type_sec,"
        	 		+ "p1.settle_type_sec_scale,p1.settle_type_thir,p1.settle_type_thir_scale,p1.receivables_cycle,p1.purchase_type_fir,p1.purchase_type_fir_scale,p1.purchase_type_sec,p1.purchase_type_sec_scale,"
        	 		+ "p1.purchase_type_thir,p1.purchase_type_thir_scale,p1.accounts_payable_cycle,p1.main_material,p1.material_ammount,p1.receivables_currence,p1.accounts_payable_currence,p1.export_volume,"
        	 		+ "p1.import_volume,p1.credit_level,p1.cb_levle,p1.line_of_credit,p1.outstanding_loan,p1.next_annual_time,");
        	 sb.append("r2.id ANALYSIS_ID,r2.deposit_rmb_average,r2.deposit_rmb_margin,r2.deposit_rmb_proportion,r2.deposit_trade_average,r2.deposit_trade_margin,r2.deposit_trade_proportion,"
        	 		+ "r2.deposit_other_average,r2.deposit_other_margin,r2.deposit_other_proportion,r2.exchange_immediate_average,r2.exchange_immediate_margin,r2.exchange_immediate_proportion,"
        	 		+ "r2.exchange_forward_average,r2.exchange_forward_margin,r2.exchange_forward_proportion,r2.exchange_interest_average,r2.exchange_interest_margin,r2.exchange_interest_proportion,"
        	 		+ "r2.options_trading_average,r2.options_trading_margin,r2.options_trading_proportion,r2.trade_financing_average,r2.trade_financing_margin,r2.trade_financing_proportion,r2.trade_factoring_average,"
        	 		+ "r2.trade_factoring_margin,r2.trade_factoring_proportion,r2.trade_discount_average,r2.trade_discount_margin,r2.trade_discount_proportion,r2.trade_acceptance_average,r2.trade_acceptance_margin,"
        	 		+ "r2.trade_acceptance_proportion,r2.trade_credit_average,r2.trade_credit_margin,r2.trade_credit_proportion,r2.trade_guarantee_average,r2.trade_guarantee_margin,r2.trade_guarantee_proportion,"
        	 		+ "r2.loan_average,r2.loan_margin,r2.loan_proportion,r2.suit_products,r2.walletsize_products,r2.provide_products,");
        	 sb.append("rn2.id ANAL_ID,rn2.deposit_rmb_average_ny,rn2.deposit_rmb_margin_ny,rn2.deposit_rmb_proport_ny,rn2.deposit_trade_average_ny,rn2.deposit_trade_margin_ny,rn2.deposit_trade_proport_ny,"
        	 		+ "rn2.deposit_other_average_ny,rn2.deposit_other_margin_ny,rn2.deposit_other_proport_ny,rn2.exchange_immediate_average_ny,rn2.exchange_immediate_margin_ny,rn2.exchange_immediate_proport_ny,"
        	 		+ "rn2.exchange_forward_average_ny,rn2.exchange_forward_margin_ny,rn2.exchange_forward_proport_ny,rn2.exchange_interest_average_ny,rn2.exchange_interest_margin_ny,rn2.exchange_interest_proport_ny,"
        	 		+ "rn2.options_trading_average_ny,rn2.options_trading_margin_ny,rn2.options_trading_proport_ny,rn2.trade_financing_average_ny,rn2.trade_financing_margin_ny,rn2.trade_financing_proport_ny,"
        	 		+ "rn2.trade_factoring_average_ny,rn2.trade_factoring_margin_ny,rn2.trade_factoring_proport_ny,rn2.trade_discount_average_ny,rn2.trade_discount_margin_ny,rn2.trade_discount_proport_ny,"
        	 		+ "rn2.trade_acceptance_average_ny,rn2.trade_acceptance_margin_ny,rn2.trade_acceptance_proport_ny,rn2.trade_credit_average_ny,rn2.trade_credit_margin_ny,rn2.trade_credit_proport_ny,"
        	 		+ "rn2.trade_guarantee_average_ny,rn2.trade_guarantee_margin_ny,rn2.trade_guarantee_proport_ny,rn2.loan_average_ny,rn2.loan_margin_ny,rn2.loan_proport_ny,rn2.suit_products_ny,"
        	 		+ "rn2.walletsize_products_ny,rn2.provide_products_ny,1 as isflag, ");
        	 sb.append(" case when N3.custcod is not null then '1' else '0' end isdebts, case when N4.custcod is not null then '1' else '0' end ismeans ");
        	 sb.append("  from ACRM_F_CI_POT_CUS_COM pc ");
        	 sb.append(" left join OCRM_F_CI_BELONG_CUSTMGR m1 on pc.cus_id = m1.cust_id");
        	 sb.append(" left join acrm_f_ci_org o1 on o1.CUST_ID = pc.cus_id  ");
        	 sb.append(" left join ACRM_F_CI_ORG_KEYFLAG k2 on k2.cust_id = pc.cus_id ");
        	 sb.append(" LEFT JOIN ACRM_F_CI_BUSI_LINE L1 ON o1.org_biz_cust_type = to_char(L1.BL_NO) ");
        	 sb.append(" left join ACRM_F_CI_ORG_REGISTERINFO rr on o1.cust_id = rr.cust_id ");
        	 sb.append(" left join OCRM_F_CI_RELATIONPLAN_PATTERN p1 on p1.cust_id = pc.cus_id ");
        	 sb.append(" left join OCRM_F_CI_RELATION_ANALYSIS r2 on r2.cust_id =  pc.cus_id ");
        	 sb.append(" left join OCRM_F_CI_RELATION_ANAL_NY rn2 on rn2.cust_id =  pc.cus_id ");
        	 sb.append("  left join  ( select DISTINCT CUSTCOD from ACRM_F_CI_PRO_AMOUNT T WHERE TRIM(T.PRODUCT) IN  (select trim(Per_pro) from ACRM_F_CI_PRODUCT  where TRIM(IS_debts) = '1')) N3 ON pc.cus_id = N3.CUSTCOD ");
        	 sb.append("  left join ( select DISTINCT CUSTCOD from  ACRM_F_CI_PRO_AMOUNT T WHERE TRIM(T.PRODUCT) IN  (select trim(Per_pro) from ACRM_F_CI_PRODUCT  where TRIM(IS_means) = '1')) N4 ON pc.cus_id = N4.CUSTCOD  ");
        	 sb.append(" left join ADMIN_AUTH_ACCOUNT ac on pc.cust_mgr=ac.account_name ");
        	 sb.append(" left join ACRM_F_CI_ADDRESS d1 on d1.cust_id=pc.cus_id and d1.ADDR_TYPE='02'");
        	 sb.append(" where 1 = 1 and pc.state='0' ");
        	 sb.append(" ) al where 1=1 ");
        	 
        	 
        	 for(String key:this.getJson().keySet()){
        		 if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
        			 if(key.equals("CUST_NAME")||key.equals("IDENT_NO"))
        				 sb.append(" and al."+key+" like '%"+this.getJson().get(key)+"%'");
        			 else if(key.equals("CUST_ID")||key.equals("CUST_STAT")||"CUST_LEVEL".equals(key)||"CUST_TYPE".equals(key))
        				 sb.append(" and  al."+key+ " = '"+this.getJson().get(key)+"'");
        			 else if(key.equals("ORG_ID"))
        				 sb.append(" and al.INSTITUTION in ('"+this.json.get(key).toString().replaceAll(",", "','")+"')");
        			 else if(key.equals("MGR_ID"))
        				 sb.append(" and al.mgr_id in ('"+this.json.get(key).toString().replaceAll(",", "','")+"')");
        		 }
        	 }
        
        addOracleLookup("IDENT_TYPE", "XD000040");
        addOracleLookup("CUST_TYPE", "XD000080");
        addOracleLookup("POTENTIAL_FLAG", "XD000084");
        addOracleLookup("CUST_LEVEL", "PRE_CUST_LEVEL");
        addOracleLookup("CUST_STAT", "XD000081");
        addOracleLookup("JOB_TYPE", "PAR0400044");
        addOracleLookup("INDUST_TYPE", "XD000002");  //行业分类XD000055行业分类（主营）XD000002从事行业
        addOracleLookup("INDUST_TYPE", "XD000055");
        addOracleLookup("REGISTER_AREA", "XD000001");  //行政区划XD000001
		SQL = sb.toString();
		datasource = dsOracle;

		configCondition("POTENTIAL_FLAG","=","POTENTIAL_FLAG",DataType.String);
	}
	
}
