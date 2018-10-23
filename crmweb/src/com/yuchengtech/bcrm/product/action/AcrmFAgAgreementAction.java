
package com.yuchengtech.bcrm.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 持有产品客户
 * @author xiebz
 *@since 2014-12-12
 */

@SuppressWarnings("serial")
@Action("/acrmFAgAgreement")
public class AcrmFAgAgreementAction  extends CommonAction {
    
	@Autowired
	@Qualifier("dsOracle")	
	private DataSource ds;  
	
	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	
	public void prepare(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	String pId = request.getParameter("productId");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" with amt_table as (select * from " +
    			" ( " +
    			" select  sum(o.amt) as amt,o.cust_id,o.product_id from ACRM_F_DP_SAVE_INFO o  where o.product_id ='"+pId+"' and status in ('A','I','J','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','AA','B') group by o.cust_id,o.product_id " +//负债业务协议
    			" union all " +
    			" select sum(o1.BAL) as amt,o1.cust_id,o1.product_id from  ACRM_F_CI_ASSET_BUSI_PROTO o1 where o1.product_id = '"+pId+"' AND status in ('A','I','J','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','AA','B') group by o1.cust_id,o1.product_id " +//资产业务协议
    			" union all " +
    			" select sum(o2.loan_balance_rmb) as amt,o2.cust_id,o2.product_id from ACRM_F_CL_BNK_DISC_YW o2 where o2.product_id = '"+pId+"'AND status in ('A','I','J','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','AA','B') group by o2.cust_id,o2.product_id  " +//贴现业务信息表
    			" union all " +
    			" select sum(o3.amt) as amt,o3.cust_id,o3.prod_no as product_id from OCRM_F_CI_CARD_INFO o3 where o3.prod_no = '"+pId+"' AND status in ('A','I','J','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','AA','B') group by o3.cust_id,o3.prod_no " +//借记卡信息
    			" union all " +
    			" select sum(o4.ASSURE_AMT_MAIN) as amt,o4.cust_id,o4.prod_no as product_id from ACRM_F_NI_ASSURANCE_MAIN o4 where o4.prod_no = '"+pId+"' group by o4.cust_id,o4.prod_no  " +//保险主表
    			" union all " +
    			" select sum(o5.APPR_AMT_RMB) as amt,o5.cust_id,o5.prod_no as product_id from ACRM_F_NI_ACCEPT_BILL o5 where o5.prod_no = '"+pId+"' AND status in ('A','I','J','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','AA','B') group by o5.cust_id,o5.prod_no " +//银行承兑汇票
    			" union all " +
    			" select sum(o6.OPEN_AMT_RMB) as amt,o6.cust_id,o6.prod_no as product_id from ACRM_F_NI_DOM_CREDIT o6 where o6.prod_no = '"+pId+"' AND status in ('A','I','J','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','AA','B') group by o6.cust_id,o6.prod_no " +//国内信用证
    			" union all " +
    			" select sum(o7.OPEN_AMT_RMB) as amt,o7.cust_id,o7.prod_no as product_id from ACRM_F_NI_LG o7 where o7.prod_no = '"+pId+"' AND status in ('A','I','J','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','AA','B') group by o7.cust_id,o7.prod_no  " +//保函
    			" union all " +
    			" select sum(o8.BAL_RMB) as amt,o8.cust_id,o8.prod_no as product_id from ACRM_F_NI_INTERNATIONAL_BUSI o8 where o8.prod_no = '"+pId+"' AND status in ('A','I','J','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','AA','B') group by o8.cust_id,o8.prod_no  " +//国际业务表
    			" union all " +
    			" select sum(o9.TRAD_MONEY_RMB) as amt,o9.cust_id,o9.product_id from ACRM_F_NI_DERIVATIVE_PRODUCT o9 where o9.PRODUCT_ID = '"+pId+"' AND status in ('A','I','J','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','AA','B') group by o9.cust_id,o9.PRODUCT_ID  " +//衍生产品表
    			" ) " +
    			" ) ");
    	
    	sb = sb.append(" select c.*,tb.amt,r.mgr_name,g.INSTITUTION_NAME,org.ORG_BIZ_CUST_TYPE,org.ent_scale_ck from ACRM_F_AG_AGREEMENT  t " +
    			" left join acrm_f_ci_customer c on t.cust_id = c.cust_id" +
    			" left join ocrm_f_ci_belong_custmgr r on c.cust_id = r.cust_id" +
    			" left join ocrm_f_ci_belong_org g on c.cust_id = g.cust_id" +
    			" left join acrm_f_ci_org org on c.cust_id = org.cust_id " +
    			" left join amt_table tb on tb.cust_id = t.cust_id and tb.product_id = '"+pId+"' " +
    			"  where 1=1 and t.product_id = '"+pId+"' order by c.cust_id ");
    	
    	addOracleLookup("CUST_TYPE", "XD000080");
    	SQL = sb.toString();
    	datasource = ds;
    }
}
